package br.com.wprosdocimo.bordados.ui.activifty

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.model.Bordado
import kotlinx.android.synthetic.main.inicial_activity.*


class Inicial : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var bastidor = ""
    val bastidores = R.array.bastidores

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicial_activity)

        // Formato Kotlin
        ArrayAdapter.createFromResource(this,
            bastidores,
            android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bastidor_spinner.adapter = adapter
        }
        // Formato JAVA
//        val spinnerAdapter = ArrayAdapter.createFromResource(
//            this,
//            R.array.bastidores,
//            android.R.layout.simple_spinner_item
//        )
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        bastidor_spinner.adapter = spinnerAdapter

        bastidor_spinner.onItemSelectedListener = this

        calcula_button.setOnClickListener{
            val pontos = pontos_editText.text.toString()
            val cores = cores_editText.text.toString()
            val quantidade = qtde_editText.text.toString()
            val bordado = Bordado(
                pontos = pontos.toInt(),
                cores = cores.toInt()
            )
            Toast.makeText(this, "Pontos: $pontos, Cores: $cores, Qtde: $quantidade, Bastidor: $bastidor, Objeto: $bordado", Toast.LENGTH_LONG).show()
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        bastidor = parent!!.getItemAtPosition(position).toString()
    }
}