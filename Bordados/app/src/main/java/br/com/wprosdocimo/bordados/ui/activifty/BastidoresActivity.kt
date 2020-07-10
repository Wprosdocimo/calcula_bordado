package br.com.wprosdocimo.bordados.ui.activifty

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.database.entities.Bastidor
import br.com.wprosdocimo.bordados.ui.viewmodel.BastidorViewModel
import kotlinx.android.synthetic.main.activity_bastidores.*
import kotlinx.android.synthetic.main.add_bastidor_dialog.view.*
import kotlinx.android.synthetic.main.content_bastidores.*

class BastidoresActivity : AppCompatActivity() {

    private lateinit var viewModel: BastidorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Bastidores")
        setContentView(R.layout.activity_bastidores)
        setSupportActionBar(findViewById(R.id.toolbar))
        val factory = ViewModelProvider.AndroidViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)
            .get(BastidorViewModel::class.java)
        viewModel.bastidores
            .observe(this, Observer { bastidores ->
                configuraListView(bastidores)
            })
        configuraFAB()
    }

    private fun configuraFAB() {
        fab.setOnClickListener {
            chamaDialog()
        }
    }

    private fun configuraListView(bastidores: List<Bastidor>) {
        bastidores_listview.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, bastidores
        )
        bastidores_listview.setOnItemClickListener { parent, view, position, id ->
            val bastidorSelecionado = bastidores[position]
            chamaDialog("edita", bastidorSelecionado)
        }
        bastidores_listview.setOnItemLongClickListener { _, _, position, _ ->
            val bastidorSelecionado = bastidores[position]
            viewModel.remove(bastidorSelecionado)
            return@setOnItemLongClickListener true
        }
    }

    private fun chamaDialog(
        tipo: String? = null,
        bastidor: Bastidor? = null
    ) {
        val viewCriada = LayoutInflater.from(this)
            .inflate(R.layout.add_bastidor_dialog, null)
        val campoNome = viewCriada.nome_bastidor_editText
        val campoLargura = viewCriada.largura_bastidor_editText
        val campoAltura = viewCriada.altura_bastidor_editText
        var titulo = "Adiciona bastidor"
        var tituloBotaoPositivo = "Adicionar"
        var id = 0

        if (tipo.equals("edita")) {
            titulo = "Edita bastidor"
            tituloBotaoPositivo = "Alterar"
            if (bastidor != null) {
                id = bastidor.id
                campoNome.setText(bastidor.nome)
                campoAltura.setText(bastidor.altura.toString())
                campoLargura.setText(bastidor.largura.toString())
            } else {
                Toast.makeText(
                    application,
                    "Não foi encontrado o bastidor selecionado!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(tituloBotaoPositivo) { _, _ ->
                val nome = campoNome.text.toString()
                val largura = campoLargura.text.toString()
                val altura = campoAltura.text.toString()
                val novoBastidor = Bastidor(
                    id = id,
                    nome = nome,
                    largura = largura.toInt(),
                    altura = altura.toInt()
                )
                viewModel.salva(novoBastidor)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}