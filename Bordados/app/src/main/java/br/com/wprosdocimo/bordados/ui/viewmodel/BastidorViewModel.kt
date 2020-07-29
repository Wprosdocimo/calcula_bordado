package br.com.wprosdocimo.bordados.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.wprosdocimo.bordados.database.AppDatabase
import br.com.wprosdocimo.bordados.database.entities.Bastidor
import br.com.wprosdocimo.bordados.repository.BastidoresRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BastidorViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BastidoresRepository
    val bastidores: LiveData<List<Bastidor>>

    init {
        val dao = AppDatabase
            .getInstance(application, viewModelScope)
            .bastidorDao()
        repository = BastidoresRepository(dao)
        bastidores = repository.bastidores
    }

    fun salva(bastidor: Bastidor) = viewModelScope.launch(Dispatchers.IO) {
        repository.salva(bastidor)
    }

    fun remove(bastidor: Bastidor) = viewModelScope.launch(Dispatchers.IO) {
        repository.remove(bastidor)
    }
}