package br.com.wprosdocimo.bordados.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.wprosdocimo.bordados.database.AppDatabase
import br.com.wprosdocimo.bordados.database.entities.Configuracao
import br.com.wprosdocimo.bordados.repository.ConfiguracaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConfiguracaoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ConfiguracaoRepository
    val configs: LiveData<Configuracao>

    init {
        val configuracaoDao = AppDatabase
            .getInstance(application)
            .configuracaoDao()
        repository = ConfiguracaoRepository(configuracaoDao)
        configs = repository.configs
    }

    fun salva(configuracao: Configuracao) = viewModelScope
        .launch(Dispatchers.IO) {
            repository.salva(configuracao)
        }
}