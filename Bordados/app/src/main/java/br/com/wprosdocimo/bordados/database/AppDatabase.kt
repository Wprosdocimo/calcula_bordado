package br.com.wprosdocimo.bordados.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.wprosdocimo.bordados.database.dao.ConfiguracaoDao
import br.com.wprosdocimo.bordados.database.entities.Configuracao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val NOME_BANCO_DE_DADOS = "app_bordados.db"

@Database(entities = arrayOf(Configuracao::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun configuracaoDao(): ConfiguracaoDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    NOME_BANCO_DE_DADOS
                )
                .allowMainThreadQueries()
                .build()
        }
    }

//    private class AppDatabaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    var configuracaoDao = database.configuracaoDao()
//
//                    // Delete all content here.
//                    configuracaoDao.deleteAll()
//
//                    // Add sample configuracao.
//                    var configuracao = Configuracao(
//                        id = 0,
//                        velocidadeMaquina = 500,
//                        tempoTrocaCor = 1.0,
//                        tempoPreparacao = 3.0,
//                        horasDias = 5.0,
//                        diasMes = 22,
//                        salario = 1045.00,
//                        manutencao = 300.00,
//                        aluguel = 0.00,
//                        luz = 40.00,
//                        agua = 0.00,
//                        telefone = 50.00,
//                        custoLinhaBordado = 10.00,
//                        qtdeLinhaBordado = 4000,
//                        consumoLinhaBordado = 6.5,
//                        custoLinhaBobina = 30.00,
//                        qtdeLinhaBobina = 15000,
//                        consumoLinhaBobina = 2.5,
//                        custoEntretela = 12.00,
//                        larguraEntretela = 900,
//                        comprimentoEntreleta = 1000
//                    )
//                    configuracaoDao.insert(configuracao)
//                }
//            }
//        }
//    }

//    companion object {
//        // Singleton prevents multiple instances of database opening at the
//        // same time.
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(
//            context: Context,
//            scope: CoroutineScope
//        ): AppDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    NOME_BANCO_DE_DADOS
//                )
//                    .addCallback(AppDatabaseCallback(scope))
//                    .build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//    }
}

//@Database(entities = arrayOf(User::class), version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//}