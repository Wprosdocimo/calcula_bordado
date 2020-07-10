package br.com.wprosdocimo.bordados.repository

import androidx.lifecycle.LiveData
import br.com.wprosdocimo.bordados.database.dao.BastidorDao
import br.com.wprosdocimo.bordados.database.entities.Bastidor

class BastidoresRepository(private val bastidorDao: BastidorDao) {

    val bastidores: LiveData<List<Bastidor>> = bastidorDao.buscaTodos()

    fun salva(bastidor: Bastidor) {
        bastidorDao.salva(bastidor)
    }

    fun remove(bastidor: Bastidor) {
        bastidorDao.remove(bastidor)
    }
}