package br.com.wprosdocimo.bordados.ui.activifty

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.database.AppDatabase
import br.com.wprosdocimo.bordados.database.dao.ConfiguracaoDao
import br.com.wprosdocimo.bordados.extension.formataParaBrasileiro
import br.com.wprosdocimo.bordados.model.Bastidor
import br.com.wprosdocimo.bordados.model.Bordado
import kotlinx.android.synthetic.main.inicial_activity.*
import kotlinx.android.synthetic.main.resultado_dialog.view.*
import java.math.BigDecimal


//class Inicial : AppCompatActivity(), AdapterView.OnItemSelectedListener {
class Inicial : AppCompatActivity() {
    // bastidores originais JANOME
    // A (110 x 125 mm)  B (140 x 200 mm) C (50 x 50 mm)
    val bastidores: ArrayList<Bastidor> = arrayListOf(
        Bastidor("A", 110, 125),
        Bastidor("B", 140, 200),
        Bastidor("C", 50, 50)
    )
    private lateinit var dao: ConfiguracaoDao
    private val config by lazy { dao.getConfig() }
    private val ESCALA: Int = 6
    private val MESES: Int = 12
    private val MINUTOS: Int = 60

//    val db = Room.databaseBuilder(
//        applicationContext,
//        AppDatabase::class.java, "bordados"
//    ).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dao = AppDatabase.getInstance(this).configuracaoDao()

        configuraSpinner()
        configuraBotaoCalcular()
    }

    private fun configuraBotaoCalcular() {
        calcula_button.setOnClickListener {
            val pontos = pontos_editText.text.toString()
            val cores = cores_editText.text.toString()
            val quantidade = qtde_editText.text.toString()
            val bastidorSelecionado: Bastidor = bastidor_spinner.selectedItem as Bastidor

            val bordado = Bordado(
                pontos = pontos.toInt(),
                cores = cores.toInt(),
                bastidor = bastidorSelecionado
            )
            val tempo_bordado = calcula_tempo_bordado(bordado)
            val custo = calcula_custo_total(
                bordado,
                quantidade
            )
            val valor = calcula_valor_final(custo)

            val viewCriada = LayoutInflater.from(this)
                .inflate(
                    R.layout.resultado_dialog,
                    window.decorView as ViewGroup,
                    false
                )
            viewCriada.custo_calculado.text = custo.formataParaBrasileiro()
            viewCriada.preco_minimo.text = valor.formataParaBrasileiro()
            viewCriada.tempo_bordado.text = (tempo_bordado * quantidade.toInt()).toString()

            AlertDialog.Builder(this)
                .setTitle("Resultado")
                .setView(viewCriada)
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun calcula_valor_final(custo: BigDecimal): BigDecimal {
        // Adiciona margem de lucro
        val lucro_desejado = 10 // 10%
        val percentual = (100.0 - lucro_desejado) / 100
        val valor = custo / percentual.toBigDecimal()
        return valor
    }

    private fun calcula_custos_tempo_bordado(tempo_bordado: Double): BigDecimal {
        val horas_trabalho_dia: Double = config.horasDias
        val dias_trabalho_mes: Int = config.diasMes
        val horas_trabalho_mes: Double = dias_trabalho_mes * horas_trabalho_dia

        val custo_mao_obra = calcula_mao_de_obra(horas_trabalho_mes, tempo_bordado)
        val custos_fixos_por_bordado =
            calcula_custos_fixos(horas_trabalho_mes, tempo_bordado)

        return custo_mao_obra + custos_fixos_por_bordado
    }


    private fun calcula_custos_fixos(
        horas_trabalho_mes: Double,
        tempo_bordado: Double
    ): BigDecimal {
        val manutencao_anual: BigDecimal = config.manutencao.toBigDecimal().setScale(ESCALA)
        val luz: BigDecimal = config.luz.toBigDecimal().setScale(ESCALA)
        val agua: BigDecimal = config.agua.toBigDecimal().setScale(ESCALA)
        val aluguel: BigDecimal = config.aluguel.toBigDecimal().setScale(ESCALA)
        val telefone_internet: BigDecimal = config.telefone.toBigDecimal().setScale(ESCALA)

        val custos_fixos_mensais: BigDecimal =
            (manutencao_anual / BigDecimal(MESES).setScale(ESCALA)) +
                    luz +
                    agua +
                    aluguel +
                    telefone_internet

        val custos_fixos_por_bordado =
            ((custos_fixos_mensais / horas_trabalho_mes.toBigDecimal()) /
                    BigDecimal(MINUTOS).setScale(ESCALA)
                    ) * tempo_bordado.toBigDecimal()

        return custos_fixos_por_bordado
    }

    private fun calcula_mao_de_obra(
        horas_trabalho_mes: Double,
        tempo_bordado: Double
    ): BigDecimal {
        val salario_base: BigDecimal = config.salario.toBigDecimal().setScale(ESCALA)
        val valor_hora_pessoa: BigDecimal = salario_base / horas_trabalho_mes.toBigDecimal()

        val custo_mao_obra: BigDecimal =
            (valor_hora_pessoa / BigDecimal(MINUTOS).setScale(ESCALA)) *
                    tempo_bordado.toBigDecimal()
        return custo_mao_obra
    }

    private fun calcula_tempo_bordado(bordado: Bordado): Double {
        val velocidade_maquina: Int = config.velocidadeMaquina // Pontos por minuto
        val tempo_troca_cor: Double = config.tempoTrocaCor // Minutos
        val tempo_preparacao: Double = config.tempoPreparacao // Minutos

        val tempo_bordado: Double = (bordado.pontos / velocidade_maquina) +
                (tempo_troca_cor * bordado.cores) + tempo_preparacao
        return tempo_bordado
    }

    private fun calcula_custo_total(
        bordado: Bordado,
        quantidade: String
    ): BigDecimal {
        val custo_linha_bordado = calcula_custo_linha_bordado(bordado)
        val custo_linha_bobina = calcula_custo_linha_bobina(bordado)
        val custo_entretela = calcula_custo_entretela(bordado)
        val tempo_bordado = calcula_tempo_bordado(bordado)
        val custos_tempo_bordado = calcula_custos_tempo_bordado(tempo_bordado)

        val custo = (
                custo_linha_bordado +
                        custo_linha_bobina +
                        custo_entretela +
                        custos_tempo_bordado
                ) * quantidade.toBigDecimal()
        return custo
    }

    private fun calcula_custo_entretela(bordado: Bordado): BigDecimal {
        val custo_metro: BigDecimal = config.custoEntretela.toBigDecimal().setScale(ESCALA)
        val largura: Int = config.larguraEntretela
        val comprimento: Int = config.comprimentoEntreleta

        val area_entretela = (comprimento * largura) / 2
        val area_bastidor = (bordado.bastidor.largura * bordado.bastidor.altura) / 2

        val quantidade_de_bastidores_por_area_de_entretela = area_entretela / area_bastidor
        val custo_entretela =
            custo_metro / quantidade_de_bastidores_por_area_de_entretela.toBigDecimal()

        return custo_entretela
    }

    private fun calcula_custo_linha_bobina(bordado: Bordado): BigDecimal {
        val custo_cone_linha: BigDecimal = config.custoLinhaBobina.toBigDecimal().setScale(ESCALA)
        val quantidade_linha_por_cone: Int = config.qtdeLinhaBobina
        val consumo_linha_por_1000_pontos: Double = config.consumoLinhaBobina

        val custo_metro_linha = custo_cone_linha / quantidade_linha_por_cone.toBigDecimal()
        val consumo_do_bordado = (bordado.pontos / 1000) * consumo_linha_por_1000_pontos
        val custo_linha_bobina = custo_metro_linha * consumo_do_bordado.toBigDecimal()
        return custo_linha_bobina
    }

    private fun calcula_custo_linha_bordado(bordado: Bordado): BigDecimal {
        val custo_cone_linha: BigDecimal = config.custoLinhaBordado.toBigDecimal().setScale(ESCALA)
        val quantidade_linha_por_cone: Int = config.qtdeLinhaBordado
        val consumo_linha_por_1000_pontos: Double = config.consumoLinhaBordado

        val custo_metro_linha = custo_cone_linha.div(quantidade_linha_por_cone.toBigDecimal())
        val consumo_do_bordado = (bordado.pontos / 1000) * consumo_linha_por_1000_pontos
        val custo_linha_bordado = custo_metro_linha.multiply(consumo_do_bordado.toBigDecimal())

        return custo_linha_bordado
    }

    private fun configuraSpinner() {
        setContentView(R.layout.inicial_activity)

        ArrayAdapter(this, android.R.layout.simple_spinner_item, bastidores).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bastidor_spinner.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.inicial_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_cfg -> {
                val intent = Intent(this, ConfigActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}



