package br.com.wprosdocimo.bordados.ui.activifty

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.model.Bastidor
import br.com.wprosdocimo.bordados.model.Bordado
import kotlinx.android.synthetic.main.inicial_activity.*


class Inicial : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var bastidor: Bastidor = Bastidor()
    // bastidores originais JANOME
    // A (110 x 125 mm)  B (140 x 200 mm) C (50 x 50 mm)
    val bastidores: ArrayList<Bastidor> = arrayListOf(
        Bastidor("A", 110, 125),
        Bastidor("B", 140, 200),
        Bastidor("C", 50, 50)
    )
    val consumo_linha_bordado_por_1000_pontos = 6.5
    val consumo_linha_bobina_por_1000_pontos = 2.5
    val custo_cone_linha_bordado = 10.0
    val custo_cone_linha_bobina = 30.0
    val quantidade_linha_bordado_por_cone = 4000
    val quantidade_linha_bobina_por_cone = 15000

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
            val bordado = Bordado(
                pontos = pontos.toInt(),
                cores = cores.toInt(),
                bastidor = bastidor
            )

            val custo_metro_linha_bordado = custo_cone_linha_bordado / quantidade_linha_bordado_por_cone
            val consumo_do_bordado = ( bordado.pontos / 1000 ) * consumo_linha_bordado_por_1000_pontos
            val custo_metro_linha_bobina = custo_cone_linha_bobina / quantidade_linha_bobina_por_cone
            val consumo_do_bobina = ( bordado.pontos / 1000 ) * consumo_linha_bobina_por_1000_pontos
            val custo_linha_bobina = custo_metro_linha_bobina * consumo_do_bobina
            val custo_linha_bordado = custo_metro_linha_bordado * consumo_do_bordado
            val custo = ( custo_linha_bordado + custo_linha_bobina ) * quantidade.toInt()

            Toast.makeText(
                this,
                "Pontos: ${bordado.pontos}," +
                        " Cores: ${bordado.cores}," +
                        " Qtde: $quantidade," +
                        " Bastidor (bordado): ${bordado.bastidor}" +
                        "Custo Calculado: $custo",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    private fun configuraSpinner() {
        setContentView(R.layout.inicial_activity)

        ArrayAdapter(this, android.R.layout.simple_spinner_item, bastidores).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bastidor_spinner.adapter = adapter
        }

        bastidor_spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        bastidor = parent!!.getItemAtPosition(position) as Bastidor
    }
}