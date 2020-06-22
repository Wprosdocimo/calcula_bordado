package br.com.wprosdocimo.bordados.ui.activifty

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.database.AppDatabase
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
            finish()
        }
    }

}