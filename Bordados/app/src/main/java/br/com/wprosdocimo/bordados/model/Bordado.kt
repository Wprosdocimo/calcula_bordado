package br.com.wprosdocimo.bordados.model

class Bordado(
    val pontos: Int,
    val cores: Int,
    val bastidor: Bastidor = Bastidor("A", 200, 200)
) {
}