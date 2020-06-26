package br.com.wprosdocimo.bordados.ui.activifty

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.database.AppDatabase
import br.com.wprosdocimo.bordados.database.entities.Configuracao
import br.com.wprosdocimo.bordados.ui.viewmodel.ConfiguracaoViewModel
import kotlinx.android.synthetic.main.configuracoes.*

class ConfigActivity : AppCompatActivity() {

    private lateinit var configuracaoViewModel: ConfiguracaoViewModel
    private lateinit var velocidade: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuracoes)

        val dao = AppDatabase.getInstance(this).configuracaoDao()
        val config = dao.getConfig()

//        Toast.makeText(this, "Id atual: ${config.id} | ${config.lucro}", Toast.LENGTH_LONG).show()

        cfg_lucro_editText.setText(config.lucro.toString())
        cfg_velocidade_editText.setText(config.velocidadeMaquina.toString())
        cfg_troca_cor_editText.setText(config.tempoTrocaCor.toString())
        cfg_preparacao_editText.setText(config.tempoPreparacao.toString())
        cfg_horas_editText.setText(config.horasDias.toString())
        cfg_dias_editText.setText(config.diasMes.toString())
        cfg_salario_editText.setText(config.salario.toString())
        cfg_manutencao_editText.setText(config.manutencao.toString())
        cfg_aluguel_editText.setText(config.aluguel.toString())
        cfg_luz_editText.setText(config.luz.toString())
        cfg_agua_editText.setText(config.agua.toString())
        cfg_telefone_editText.setText(config.telefone.toString())
        cfg_custo_cone_bordado_editText.setText(config.custoLinhaBordado.toString())
        cfg_qtde_linha_cone_bordado_editText.setText(config.qtdeLinhaBordado.toString())
        cfg_consumo_linha_bordado_editText.setText(config.consumoLinhaBordado.toString())
        cfg_custo_cone_bobina_editText.setText(config.custoLinhaBobina.toString())
        cfg_qtde_linha_cone_bobina_editText.setText(config.qtdeLinhaBobina.toString())
        cfg_consumo_linha_bobina_editText.setText(config.consumoLinhaBobina.toString())
        cfg_custo_entretela_editText.setText(config.custoEntretela.toString())
        cfg_largura_entretela_editText.setText(config.larguraEntretela.toString())
        cfg_comprimento_entretela_editText.setText(config.comprimentoEntreleta.toString())


//        val confViewModel = ViewModelProvider(this@ConfigActivity)
//            .get(ConfiguracaoViewModel::class.java)

//        configuracaoViewModel = ViewModelProvider(this)
//            .get(ConfiguracaoViewModel::class.java)
//        configuracaoViewModel.configs.observe(this, Observer { configucacoes ->
//            configucacoes?.let {
//                velocidade = it.velocidadeMaquina.toString() }
//        })

        val button = findViewById<Button>(R.id.cfg_button)
        button.setOnClickListener {
            val configNova = Configuracao(
                id = 0,
                lucro = cfg_lucro_editText.text.toString().toInt(),
                velocidadeMaquina = cfg_velocidade_editText.text.toString().toInt(),
                tempoTrocaCor = cfg_troca_cor_editText.text.toString().toDouble(),
                tempoPreparacao = cfg_preparacao_editText.text.toString().toDouble(),
                horasDias = cfg_horas_editText.text.toString().toDouble(),
                diasMes = cfg_dias_editText.text.toString().toInt(),
                salario = cfg_salario_editText.text.toString().toDouble(),
                manutencao = cfg_manutencao_editText.text.toString().toDouble(),
                aluguel = cfg_aluguel_editText.text.toString().toDouble(),
                luz = cfg_luz_editText.text.toString().toDouble(),
                agua = cfg_agua_editText.text.toString().toDouble(),
                telefone = cfg_telefone_editText.text.toString().toDouble(),
                custoLinhaBordado = cfg_custo_cone_bordado_editText.text.toString().toDouble(),
                qtdeLinhaBordado = cfg_qtde_linha_cone_bordado_editText.text.toString().toInt(),
                consumoLinhaBordado = cfg_consumo_linha_bordado_editText.text.toString().toDouble(),
                custoLinhaBobina = cfg_custo_cone_bobina_editText.text.toString().toDouble(),
                qtdeLinhaBobina = cfg_qtde_linha_cone_bobina_editText.text.toString().toInt(),
                consumoLinhaBobina = cfg_consumo_linha_bobina_editText.text.toString().toDouble(),
                custoEntretela = cfg_custo_entretela_editText.text.toString().toDouble(),
                larguraEntretela = cfg_largura_entretela_editText.text.toString().toInt(),
                comprimentoEntreleta = cfg_comprimento_entretela_editText.text.toString().toInt()
            )
            dao.insert(configNova)
//            val teste = dao.getConfig()
//            Toast.makeText(this, "Id novo: ${teste.id}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

}