package br.com.wprosdocimo.bordados.repository

import br.com.wprosdocimo.bordados.database.dao.BastidorDao
import br.com.wprosdocimo.bordados.database.entities.Bastidor

class BastidoresRepository(private val bastidorDao: BastidorDao) {

    val bastidores: List<Bastidor> = bastidorDao.buscaTodos()

    suspend fun insert(bastidor: Bastidor) {
        bastidorDao.insert(bastidor)
    }
}