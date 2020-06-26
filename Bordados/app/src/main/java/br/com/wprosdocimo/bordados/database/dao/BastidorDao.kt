package br.com.wprosdocimo.bordados.database.dao

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
    fun buscaTodos(): List<Bastidor>

    @Query("DELETE FROM bastidor")
    fun deleteAll()

}

//@Dao
//interface ConfiguracaoDao {
//    @Query("SELECT * FROM configuracao ORDER BY id DESC LIMIT 1")
//    fun getConfig(): Configuracao
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insert(configuracao: Configuracao)
//
//    @Query("DELETE FROM configuracao")
//    fun deleteAll()
//}