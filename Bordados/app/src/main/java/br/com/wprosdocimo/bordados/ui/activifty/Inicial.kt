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

            val custo = calcula_custo_total(
                bordado,
                quantidade
            )

            Toast.makeText(
                this,
                "Pontos: ${bordado.pontos}," +
                        " Cores: ${bordado.cores}," +
                        " Qtde: $quantidade," +
                        " Bastidor (bordado): ${bordado.bastidor}" +
                        " Custo Calculado: $custo",
                Toast.LENGTH_LONG
            )
                .show()
        }
    }

    private fun calcula_custo_total(
        bordado: Bordado,
        quantidade: String
    ): Double {
        val custo_linha_bordado = calcula_custo_linha_bordado(bordado)
        val custo_linha_bobina = calcula_custo_linha_bobina(bordado)
        val custo_entretela = calcula_custo_entretela(bordado)
        val custo = (
                custo_linha_bordado + custo_linha_bobina + custo_entretela
                ) * quantidade.toInt()
        return custo
    }

    private fun calcula_custo_entretela(bordado: Bordado): Double {
        val custo_metro = 12.0
        val largura = 900 // milimetros
        val comprimento = 1000 // milimetros

        val area_entretela = (comprimento * largura) / 2
        val area_bastidor = (bordado.bastidor.largura * bordado.bastidor.altura) / 2

        val quantidade_de_bastidores_por_area_de_entretela = area_entretela / area_bastidor

        val custo_entretela = custo_metro / quantidade_de_bastidores_por_area_de_entretela

        return custo_entretela
    }

    private fun calcula_custo_linha_bobina(bordado: Bordado): Double {
        val custo_cone_linha = 30.0
        val quantidade_linha_por_cone = 15000
        val consumo_linha_por_1000_pontos = 2.5

        val custo_metro_linha = custo_cone_linha / quantidade_linha_por_cone
        val consumo_do_bordado = (bordado.pontos / 1000) * consumo_linha_por_1000_pontos
        val custo_linha_bobina = custo_metro_linha * consumo_do_bordado
        return custo_linha_bobina
    }

    private fun calcula_custo_linha_bordado(bordado: Bordado): Double {
        val custo_cone_linha: Double = 10.0
        val quantidade_linha_por_cone: Int = 4000
        val consumo_linha_por_1000_pontos: Double = 6.5

        val custo_metro_linha = custo_cone_linha / quantidade_linha_por_cone
        val consumo_do_bordado = (bordado.pontos / 1000) * consumo_linha_por_1000_pontos
        val custo_linha_bordado = custo_metro_linha * consumo_do_bordado
        return custo_linha_bordado
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