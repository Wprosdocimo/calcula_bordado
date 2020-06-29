package br.com.wprosdocimo.bordados.ui.activifty

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.database.AppDatabase
import br.com.wprosdocimo.bordados.database.dao.BastidorDao
import br.com.wprosdocimo.bordados.extension.formataParaBrasileiro
import kotlinx.android.synthetic.main.activity_bastidores.*
import kotlinx.android.synthetic.main.content_bastidores.*
import kotlinx.android.synthetic.main.resultado_dialog.view.*

class BastidoresActivity : AppCompatActivity() {

    private lateinit var daoBastidor: BastidorDao
    private val bastidores by lazy { daoBastidor.buscaTodos() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Bastidores")
        setContentView(R.layout.activity_bastidores)
        setSupportActionBar(findViewById(R.id.toolbar))

        daoBastidor = AppDatabase.getInstance(this).bastidorDao()


        bastidores_listview.adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, bastidores)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val viewCriada = LayoutInflater.from(this)
                .inflate(
                    R.layout.add_bastidor_dialog,
                    window.decorView as ViewGroup,
                    false
                )

            AlertDialog.Builder(this)
                .setTitle("Adiciona bastidor")
                .setView(viewCriada)
                .setPositiveButton("Adicionar", null)
                .setNegativeButton("Cancelar", null)
                .show()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
    }
}