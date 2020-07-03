package br.com.wprosdocimo.bordados.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.wprosdocimo.bordados.database.entities.Bastidor

@Dao
interface BastidorDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(bastidor: Bastidor)

    @Query("SELECT * FROM bastidor WHERE id = :id ")
    fun buscaPorId(id: Int): Bastidor

    @Query("SELECT * FROM bastidor ORDER BY id")
    fun buscaTodos(): LiveData<List<Bastidor>>

    @Query("DELETE FROM bastidor")
    fun deleteAll()

}