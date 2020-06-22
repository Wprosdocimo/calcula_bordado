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
    fun getConfig(): Configuracao

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(configuracao: Configuracao)

    @Query("DELETE FROM configuracao")
    fun deleteAll()
}

//@Dao
//interface UserDao {
//    @Query("SELECT * FROM user")
//    fun getAll(): List<User>
//
//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
//
//    @Insert
//    fun insertAll(vararg users: User)
//
//    @Delete
//    fun delete(user: User)
//}
