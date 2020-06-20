package br.com.wprosdocimo.bordados.ui.activifty

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import br.com.wprosdocimo.bordados.R
import br.com.wprosdocimo.bordados.ui.viewmodel.ConfiguracaoViewModel

class ConfigActivity : AppCompatActivity() {

    private lateinit var configuracaoViewModel: ConfiguracaoViewModel
    private lateinit var velocidade: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuracoes)

//        val confViewModel = ViewModelProvider(this@ConfigActivity)
//            .get(ConfiguracaoViewModel::class.java)

//        configuracaoViewModel = ViewModelProvider(this)
//            .get(ConfiguracaoViewModel::class.java)
//        configuracaoViewModel.configs.observe(this, Observer { configucacoes ->
//            configucacoes?.let {
//                velocidade = it.velocidadeMaquina.toString() }
//        })
//
//        val button = findViewById<Button>(R.id.cfg_button)
//        button.setOnClickListener {
////            finish()
//            Toast.makeText(this, "Velocidade: ${velocidade}", Toast.LENGTH_LONG)
//                .show()
//        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}