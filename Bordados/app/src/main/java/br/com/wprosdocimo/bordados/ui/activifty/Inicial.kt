package br.com.wprosdocimo.bordados.ui.activifty

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.database.entities.Bastidor
import br.com.wprosdocimo.bordados.database.entities.Configuracao
import br.com.wprosdocimo.bordados.extension.formataParaBrasileiro
import br.com.wprosdocimo.bordados.model.Bordado
import br.com.wprosdocimo.bordados.ui.viewmodel.BastidorViewModel
import br.com.wprosdocimo.bordados.ui.viewmodel.ConfiguracaoViewModel
import kotlinx.android.synthetic.main.inicial_activity.*
import kotlinx.android.synthetic.main.resultado_dialog.view.*
import br.com.wprosdocimo.bordados.utility.CalculaUtility


class Inicial : AppCompatActivity() {

    private lateinit var viewModelConfig: ConfiguracaoViewModel
    private lateinit var viewModelBastidores: BastidorViewModel
    private lateinit var config: Configuracao
    private lateinit var calcula: CalculaUtility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicial_activity)

        val factory = ViewModelProvider.AndroidViewModelFactory(application)
        viewModelConfig = ViewModelProvider(this, factory)
            .get(ConfiguracaoViewModel::class.java)
        viewModelBastidores = ViewModelProvider(this, factory)
            .get(BastidorViewModel::class.java)

        viewModelConfig.configs
            .observe(this, Observer {
                config = it
                calcula = CalculaUtility(config)
            })

//        populaBanco()
        configuraSpinner()
        configuraBotaoCalcular()
    }

    private fun populaBanco() {
        val configuracao = Configuracao(
            id = 0,
            lucro = 10,
            velocidadeMaquina = 500,
            tempoTrocaCor = 1.0,
            tempoPreparacao = 3.0,
            horasDias = 4.0,
            diasMes = 20,
            salario = 1045.00,
            inss = 3,
            fgts = 8,
            manutencao = 300.00,
            aluguel = 0.00,
            luz = 40.00,
            agua = 0.00,
            telefone = 50.00,
            custoLinhaBordado = 10.00,
            qtdeLinhaBordado = 4000,
            consumoLinhaBordado = 6.5,
            custoLinhaBobina = 30.00,
            qtdeLinhaBobina = 15000,
            consumoLinhaBobina = 2.5,
            custoEntretela = 12.00,
            larguraEntretela = 900,
            comprimentoEntreleta = 1000
        )
//        daoConfig.insert(configuracao)
//        daoBastidor.deleteAll()

        val bastidorA = Bastidor(id = 0, nome = "A", largura = 110, altura = 125)
//        daoBastidor.salva(bastidorA)
        val bastidorB = Bastidor(id = 0, nome = "B", largura = 140, altura = 200)
//        daoBastidor.salva(bastidorB)
        val bastidorC = Bastidor(id = 0, nome = "C", largura = 50, altura = 50)
//        daoBastidor.salva(bastidorC)
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
            val tempo_bordado = calcula.tempo_bordado(bordado)
            val custo = calcula.custo_total(
                bordado,
                quantidade
            )
            val valor = calcula.valor_final(custo)

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



    private fun configuraSpinner() {


        viewModelBastidores.bastidores
            .observe(this, Observer { bastidores ->
                ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    bastidores
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    bastidor_spinner.adapter = adapter
                }
            })
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
            R.id.menu_bastidor -> {
                val intent = Intent(this, BastidoresActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}



