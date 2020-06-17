package br.com.wprosdocimo.bordados.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Configuracao(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "velocidade_maquina") val velocidadeMaquina: Int,
    @ColumnInfo(name = "tempo_troca_cor") val tempoTrocaCor: Double,
    @ColumnInfo(name = "tempo_preparacao") val tempoPreparacao: Double,
    @ColumnInfo(name = "horas_dia") val horasDias: Double,
    @ColumnInfo(name = "dias_mes") val diasMes: Int,
    @ColumnInfo(name = "salario") val salario: Double,
    @ColumnInfo(name = "manutencao") val manutencao: Double,
    @ColumnInfo(name = "aluguel") val aluguel: Double,
    @ColumnInfo(name = "luz") val luz: Double,
    @ColumnInfo(name = "agua") val agua: Double,
    @ColumnInfo(name = "telefone") val telefone: Double,
    @ColumnInfo(name = "custo_linha_bordado") val custoLinhaBordado: Double,
    @ColumnInfo(name = "qtde_linha_bordado") val qtdeLinhaBordado: Int,
    @ColumnInfo(name = "consumo_linha_bordado") val consumoLinhaBordado: Double,
    @ColumnInfo(name = "custo_linha_bobina") val custoLinhaBobina: Double,
    @ColumnInfo(name = "qtde_linha_bobina") val qtdeLinhaBobina: Int,
    @ColumnInfo(name = "consumo_linha_bobina") val consumoLinhaBobina: Double,
    @ColumnInfo(name = "custo_entretela") val custoEntretela: Double,
    @ColumnInfo(name = "largura_entretela") val larguraEntretela: Int,
    @ColumnInfo(name = "comprimento_entretela") val comprimentoEntreleta: Int
)
