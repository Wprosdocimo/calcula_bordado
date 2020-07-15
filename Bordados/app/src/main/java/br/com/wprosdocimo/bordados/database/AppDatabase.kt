package br.com.wprosdocimo.bordados.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.wprosdocimo.bordados.database.dao.BastidorDao
import br.com.wprosdocimo.bordados.database.dao.ConfiguracaoDao
import br.com.wprosdocimo.bordados.database.entities.Bastidor
import br.com.wprosdocimo.bordados.database.entities.Configuracao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val NOME_BANCO_DE_DADOS = "app_bordados.db"

@Database(
    entities = arrayOf(Configuracao::class, Bastidor::class),
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun configuracaoDao(): ConfiguracaoDao
    abstract fun bastidorDao(): BastidorDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
//            if (::instancia.isInitialized) return instancia
//            instancia = Room.databaseBuilder(
//                context,
//                AppDatabase::class.java,
//                NOME_BANCO_DE_DADOS
//            )
//                .addCallback(AppDatabaseCallback(scope))
//                .fallbackToDestructiveMigration()
//                .build()
//
//            return instancia

            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    NOME_BANCO_DE_DADOS
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        val configuracaoDao = database.configuracaoDao()
                        val bastidorDao = database.bastidorDao()

                        // Add sample configuracao.
                        val configuracao = Configuracao(
                            id = 0,
                            lucro = 10,
                            velocidadeMaquina = 500,
                            tempoTrocaCor = 1.0,
                            tempoPreparacao = 3.0,
                            horasDias = 4.0,
                            diasMes = 20,
                            salario = 1045.00,
                            inss = 3,
                            fgts = 8,
                            manutencao = 300.00,
                            aluguel = 0.00,
                            luz = 40.00,
                            agua = 0.00,
                            telefone = 50.00,
                            custoLinhaBordado = 10.00,
                            qtdeLinhaBordado = 4000,
                            consumoLinhaBordado = 6.5,
                            custoLinhaBobina = 30.00,
                            qtdeLinhaBobina = 15000,
                            consumoLinhaBobina = 2.5,
                            custoEntretela = 12.00,
                            larguraEntretela = 900,
                            comprimentoEntreleta = 1000
                        )
                        val bastidorA = Bastidor(id = 0, nome = "A", largura = 110, altura = 125)
                        val bastidorB = Bastidor(id = 0, nome = "B", largura = 140, altura = 200)
                        val bastidorC = Bastidor(id = 0, nome = "C", largura = 50, altura = 50)

                        AsyncTask.execute {
                            // Delete all content here.
//                            configuracaoDao.deleteAll()
//                            bastidorDao.deleteAll()
                            // Popula dados novos
                            configuracaoDao.salva(configuracao)
                            bastidorDao.salva(bastidorA)
                            bastidorDao.salva(bastidorB)
                            bastidorDao.salva(bastidorC)
                        }

                    }
                }
            }
        }
    }
}