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

class ConfiguracaoViewModel(application: Application) : AndroidViewModel(application)  {

    private val repository: ConfiguracaoRepository
    val configs: LiveData<Configuracao>

    init {
        val configuracaoDao = AppDatabase.getDatabase(application, viewModelScope).configuracaoDao()
        repository = ConfiguracaoRepository(configuracaoDao)
        configs = repository.configs
    }

    fun insert(configuracao: Configuracao) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(configuracao)
    }

}


//class WordViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val repository: WordRepository
//    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
//    // - We can put an observer on the data (instead of polling for changes) and only update the
//    //   the UI when the data actually changes.
//    // - Repository is completely separated from the UI through the ViewModel.
//    val allWords: LiveData<List<Word>>
//
//    init {
//        val wordsDao = WordRoomDatabase.getDatabase(application).wordDao()
//        repository = WordRepository(wordsDao)
//        allWords = repository.allWords
//    }
//
//    /**
//     * Launching a new coroutine to insert the data in a non-blocking way
//     */
//    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(word)
//    }
//}