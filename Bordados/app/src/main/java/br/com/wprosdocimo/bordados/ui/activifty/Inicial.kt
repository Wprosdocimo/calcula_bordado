package br.com.wprosdocimo.bordados.ui.activifty

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.extension.formataParaBrasileiro
import br.com.wprosdocimo.bordados.model.Bastidor
import br.com.wprosdocimo.bordados.model.Bordado
import kotlinx.android.synthetic.main.inicial_activity.*
import java.math.BigDecimal

//class Inicial : AppCompatActivity(), AdapterView.OnItemSelectedListener {
class Inicial : AppCompatActivity() {
    var bastidor: Bastidor = Bastidor()
    // bastidores originais JANOME
    // A (110 x 125 mm)  B (140 x 200 mm) C (50 x 50 mm)
    val bastidores: ArrayList<Bastidor> = arrayListOf(
        Bastidor("A", 110, 125),
        Bastidor("B", 140, 200),
        Bastidor("C", 50, 50)
    )
//    val db = Room.databaseBuilder(
//        applicationContext,
//        AppDatabase::class.java, "bordados"
//    ).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            Toast.makeText(
                this,
                "Pontos: ${bordado.pontos}," +
                        " Cores: ${bordado.cores}," +
                        " Qtde: $quantidade," +
                        " Bastidor (bordado): ${bordado.bastidor}" +
                        " Tempo de bordado: $tempo_bordado min." +
                        " Custo Calculado: ${custo.formataParaBrasileiro()}" +
                        " Valor: ${valor.formataParaBrasileiro()}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun calcula_valor_final(custo: BigDecimal): BigDecimal {
        // Adiciona margem de lucro
        val lucro_desejado = 10 // 10%
        // Calculo de percentual básico
        // custo * (lucro_desejado + 100%)
        // Markup 10% do valor final
        // custo / (100% - lucro_desejado)
        val percentual = (100.0 - lucro_desejado) / 100
        val valor = custo / percentual.toBigDecimal()
        return valor
    }

    private fun calcula_custos_tempo_bordado(tempo_bordado: Double): BigDecimal {
        val horas_trabalho_dia = 4.0 // Horas
        val dias_trabalho_mes = 20 // dias
        val horas_trabalho_mes = dias_trabalho_mes * horas_trabalho_dia

        val custo_mao_obra = calcula_mao_de_obra(horas_trabalho_mes, tempo_bordado)
        val custos_fixos_por_bordado =
            calcula_custos_fixos(horas_trabalho_mes, tempo_bordado)

        return custo_mao_obra + custos_fixos_por_bordado
    }

    private fun calcula_custos_fixos(
        horas_trabalho_mes: Double,
        tempo_bordado: Double
    ): BigDecimal {
        val manutencao_anual = BigDecimal("300.0000") // Reais
        val luz = BigDecimal("40.0000") // Reais
        val agua = BigDecimal.ZERO // Reais
        val aluguel = BigDecimal.ZERO // Reais
        val telefone_internet = BigDecimal("50.0000") // Reais
        // X = 25 + 40 + 50 = 115
        val custos_fixos_mensais = (manutencao_anual / BigDecimal("12.0000")) + luz +
                agua + aluguel + telefone_internet

        // X = ((115/80)/60) * (20 + 3 + 2) = ((1,4375)/60) * 25 = 0.024 * 25 = 0,60
        val custos_fixos_por_bordado =
            ((custos_fixos_mensais / horas_trabalho_mes.toBigDecimal()) / BigDecimal("60.0000")) * tempo_bordado.toBigDecimal()
        return custos_fixos_por_bordado
    }

    private fun calcula_mao_de_obra(
        horas_trabalho_mes: Double,
        tempo_bordado: Double
    ): BigDecimal {
        val salario_base = BigDecimal("1045.000000") // Reais
        val valor_hora_pessoa = salario_base / horas_trabalho_mes.toBigDecimal()
        // X = ((1045 / 80)/60) * 25 = 13,0625 / 60 * 25 = 0,217708 * 25 = 5,44
        val custo_mao_obra =
            (valor_hora_pessoa / BigDecimal("60.00")) * tempo_bordado.toBigDecimal()
        return custo_mao_obra
    }

    private fun calcula_tempo_bordado(bordado: Bordado): Double {
        val velocidade_maquina = 500 // Pontos por minuto
        val tempo_troca_cor = 1.0 // Minutos
        val tempo_preparacao = 3.0 // Minutos

        val tempo_bordado = (bordado.pontos / velocidade_maquina) +
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

//        Toast.makeText(this,
//                "Custos: ${custo_linha_bordado} + " +
//                "${custo_linha_bobina} + " +
//                "${custo_entretela} + " +
//                "${custos_tempo_bordado} = " +
//                        "${custo}",
//            Toast.LENGTH_LONG
//        ).show()
        return custo
    }

    private fun calcula_custo_entretela(bordado: Bordado): BigDecimal {
        val custo_metro = BigDecimal("12.000000")
        val largura = 900 // milimetros
        val comprimento = 1000 // milimetros

        val area_entretela = (comprimento * largura) / 2
        val area_bastidor = (bordado.bastidor.largura * bordado.bastidor.altura) / 2

        val quantidade_de_bastidores_por_area_de_entretela = area_entretela / area_bastidor

        val custo_entretela = custo_metro / quantidade_de_bastidores_por_area_de_entretela.toBigDecimal()

        return custo_entretela
    }

    private fun calcula_custo_linha_bobina(bordado: Bordado): BigDecimal {
        val custo_cone_linha = BigDecimal("30.000000")
        val quantidade_linha_por_cone = 15000
        val consumo_linha_por_1000_pontos = 2.5

        val custo_metro_linha = custo_cone_linha / quantidade_linha_por_cone.toBigDecimal()
        val consumo_do_bordado = (bordado.pontos / 1000) * consumo_linha_por_1000_pontos
        val custo_linha_bobina = custo_metro_linha * consumo_do_bordado.toBigDecimal()
        return custo_linha_bobina
    }

    private fun calcula_custo_linha_bordado(bordado: Bordado): BigDecimal {
        val custo_cone_linha = BigDecimal("10.000000")
        val quantidade_linha_por_cone: Int = 4000
        val consumo_linha_por_1000_pontos: Double = 6.5

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

//        bastidor_spinner.onItemSelectedListener = this
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



