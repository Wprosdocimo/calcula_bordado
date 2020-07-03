package br.com.wprosdocimo.bordados.ui.activifty

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.database.entities.Bastidor
import br.com.wprosdocimo.bordados.ui.viewmodel.BastidorViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.add_bastidor_dialog.*
import kotlinx.android.synthetic.main.content_bastidores.*

class BastidoresActivity : AppCompatActivity() {

//    private lateinit var daoBastidor: BastidorDao
//    private val bastidores by lazy { daoBastidor.buscaTodos() }

    private lateinit var viewModelBastidores: BastidorViewModel
//    private lateinit var bastidores: List<Bastidor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Bastidores")
        setContentView(R.layout.activity_bastidores)
        setSupportActionBar(findViewById(R.id.toolbar))

//        daoBastidor = AppDatabase.getInstance(this).bastidorDao()

        val factory = ViewModelProvider.AndroidViewModelFactory(application)
        viewModelBastidores = ViewModelProvider(this, factory)
            .get(BastidorViewModel::class.java)
        viewModelBastidores.bastidores
            .observe(this, Observer { bastidores ->
                bastidores_listview.adapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, bastidores)
            })



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
                .setPositiveButton("Adicionar",
                    DialogInterface.OnClickListener { dialog, id ->
                        val text = nome_bastidor_editText.text.toString()
                        Toast
                            .makeText(this, "teste: $text", Toast.LENGTH_LONG)
                            .show()
                        //                        val novoBastidor = Bastidor(
//                            id = 0,
//                            nome = nome_bastidor_editText.text.toString(),
//                            largura = largura_bastidor_editText.text.toString().toInt(),
//                            altura = altura_bastidor_editText.text.toString().toInt()
//                        )
//
//                        viewModelBastidores.insert(novoBastidor)
                })
            .setNegativeButton("Cancelar", null)
                .show()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
    }
}