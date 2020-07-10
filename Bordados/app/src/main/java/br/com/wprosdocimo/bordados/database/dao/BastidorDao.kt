package br.com.wprosdocimo.bordados.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.wprosdocimo.bordados.database.entities.Bastidor

@Dao
interface BastidorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(bastidor: Bastidor)

    @Delete
    fun remove(bastidor: Bastidor)

    @Query("SELECT * FROM bastidor WHERE id = :id ")
    fun buscaPorId(id: Int): Bastidor

    @Query("SELECT * FROM bastidor ORDER BY id")
    fun buscaTodos(): LiveData<List<Bastidor>>

    @Query("DELETE FROM bastidor")
    fun deleteAll()
}