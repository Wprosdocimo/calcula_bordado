package br.com.wprosdocimo.bordados.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.wprosdocimo.bordados.database.entities.Configuracao

@Dao
interface ConfiguracaoDao {
    @Query("SELECT * FROM configuracao ORDER BY id DESC LIMIT 1")
    fun getConfig(): LiveData<Configuracao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(configuracao: Configuracao)

    @Query("DELETE FROM configuracao")
    fun deleteAll()
}
