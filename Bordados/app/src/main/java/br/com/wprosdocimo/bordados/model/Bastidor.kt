package br.com.wprosdocimo.bordados.model

class Bastidor(
    val nome: String = "",
    val altura: Int = 0,
    val largura: Int = 0
) {
    override fun toString(): String {
        if ( nome == "" ) {
            return nome
        }
        return "$nome ( $altura x $largura mm)"
    }
}