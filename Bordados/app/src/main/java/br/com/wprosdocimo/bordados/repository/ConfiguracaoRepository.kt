package br.com.wprosdocimo.bordados.repository

import androidx.lifecycle.LiveData
import br.com.wprosdocimo.bordados.database.dao.ConfiguracaoDao
import br.com.wprosdocimo.bordados.database.entities.Configuracao

class ConfiguracaoRepository(private val configuracaoDao: ConfiguracaoDao) {

//    val configs: LiveData<Configuracao> = configuracaoDao.getConfig()
//
//    suspend fun insert(configuracao: Configuracao) {
//        configuracaoDao.insert(configuracao)
//    }
}


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

//class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
//    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

//    suspend fun insert(word: Word) {
//        wordDao.insert(word)
//    }
//}