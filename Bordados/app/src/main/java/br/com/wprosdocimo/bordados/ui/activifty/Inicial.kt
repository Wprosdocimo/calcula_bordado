package br.com.wprosdocimo.bordados.ui.activifty

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
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
import br.com.wprosdocimo.bordados.utility.CalculaUtility
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.inicial_activity.*
import kotlinx.android.synthetic.main.resultado_dialog.view.*


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

        configuraSpinner()
        configuraBotaoCalcular()
    }

    private fun configuraBotaoCalcular() {
        calcula_button.setOnClickListener {
            val pontos = inicial_pontos.editText?.text.toString()
            val cores = inicial_cores.editText?.text.toString()
            val quantidade = inicial_qtde.editText?.text.toString()
            val bastidorSelecionado: Bastidor = bastidor_spinner.selectedItem as Bastidor

            if (pontos.isNullOrEmpty() || cores.isNullOrEmpty() || quantidade.isNullOrEmpty()) {
                Toast.makeText(
                    this@Inicial,
                    "Nenhum dos campos pode estar vazio",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val bordado = Bordado(
                    pontos = pontos.toInt(),
                    cores = cores.toInt(),
                    bastidor = bastidorSelecionado
                )
                val tempo_bordado = calcula.tempo_bordado(bordado)
                val custo = calcula.custo_total(
                    bordado,
                    quantidade.toInt()
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

//            AlertDialog.Builder(this)
                MaterialAlertDialogBuilder(this)
                    .setTitle("Resultado")
                    .setView(viewCriada)
                    .setPositiveButton("OK", null)
                    .show()

            }

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



