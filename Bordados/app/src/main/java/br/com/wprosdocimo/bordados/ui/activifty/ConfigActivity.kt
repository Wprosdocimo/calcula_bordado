package br.com.wprosdocimo.bordados.ui.activifty

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.database.entities.Configuracao
import br.com.wprosdocimo.bordados.ui.viewmodel.ConfiguracaoViewModel
import kotlinx.android.synthetic.main.configuracoes.*
import kotlinx.coroutines.delay

class ConfigActivity : AppCompatActivity() {

    private lateinit var config: Configuracao
    private lateinit var viewModel: ConfiguracaoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Configurações")
        setContentView(R.layout.configuracoes)

        val factory = ViewModelProvider.AndroidViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(ConfiguracaoViewModel::class.java)
        viewModel.configs
            .observe(this, Observer {
                config = it
                populaCampos()
            })
//        configuraBotaoSalvar()
    }

//    private fun configuraBotaoSalvar() {
//        cfg_button.setOnClickListener {
//            SalvaSai()
//        }
//    }

    private fun SalvaSai() {
        val configNova = Configuracao(
            id = config.id,
            lucro = cfg_lucro.editText?.text.toString().toInt(),
            velocidadeMaquina = cfg_velocidade_editText.text.toString().toInt(),
            tempoTrocaCor = cfg_troca_cor_editText.text.toString().toDouble(),
            tempoPreparacao = cfg_preparacao_editText.text.toString().toDouble(),
            horasDias = cfg_horas_editText.text.toString().toDouble(),
            diasMes = cfg_dias_editText.text.toString().toInt(),
            salario = cfg_salario_editText.text.toString().toDouble(),
            inss = 3,
            fgts = 8,
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
        viewModel.salva(configNova)
        finish()
    }

    private fun populaCampos() {
        cfg_lucro.editText?.setText(config.lucro.toString())
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.salva_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_salva -> {
                SalvaSai()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}