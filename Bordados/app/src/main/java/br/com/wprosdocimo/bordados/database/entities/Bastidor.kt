package br.com.wprosdocimo.bordados.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 data class Bastidor(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "altura") val altura: Int,
    @ColumnInfo(name = "largura") val largura: Int
){
    override fun toString(): String {
        if ( nome == "" ) {
            return nome
        }
        return "$nome ( $altura x $largura mm) ($id)"
    }
}