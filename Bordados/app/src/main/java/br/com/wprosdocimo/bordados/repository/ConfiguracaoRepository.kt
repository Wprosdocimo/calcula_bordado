package br.com.wprosdocimo.bordados.repository

import androidx.lifecycle.LiveData
import br.com.wprosdocimo.bordados.database.dao.ConfiguracaoDao
import br.com.wprosdocimo.bordados.database.entities.Configuracao

class ConfiguracaoRepository(private val configuracaoDao: ConfiguracaoDao) {

    val configs: LiveData<Configuracao> = configuracaoDao.getConfig()

    fun salva(configuracao: Configuracao) {
        configuracaoDao.salva(configuracao)
    }
}