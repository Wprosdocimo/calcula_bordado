package br.com.wprosdocimo.bordados.utility

import br.com.wprosdocimo.bordados.database.entities.Configuracao
import br.com.wprosdocimo.bordados.model.Bordado
import java.math.BigDecimal

class CalculaUtility(
    val config: Configuracao
) {
    private val ESCALA: Int = 6
    private val MESES: Int = 12
    private val MINUTOS: Int = 60
    private val MARGEM = 10

    fun valor_final(custo: BigDecimal): BigDecimal {
        val lucro_desejado = config.lucro
        val percentual = (100.0 - lucro_desejado) / 100
        val valor = custo / percentual.toBigDecimal()
        return valor
    }

    private fun calcula_custos_tempo_bordado(tempo_bordado: Double): BigDecimal {
        val horas_trabalho_dia: Double = config.horasDias
        val dias_trabalho_mes: Int = config.diasMes
        val horas_trabalho_mes: Double = dias_trabalho_mes * horas_trabalho_dia

        val custo_mao_obra = calcula_mao_de_obra(horas_trabalho_mes, tempo_bordado)
        val custos_fixos_por_bordado =
            calcula_custos_fixos(horas_trabalho_mes, tempo_bordado)

        return custo_mao_obra + custos_fixos_por_bordado
    }

    private fun calcula_custos_fixos(
        horas_trabalho_mes: Double,
        tempo_bordado: Double
    ): BigDecimal {
        val manutencao_anual: BigDecimal = config.manutencao.toBigDecimal().setScale(ESCALA)
        val luz: BigDecimal = config.luz.toBigDecimal().setScale(ESCALA)
        val agua: BigDecimal = config.agua.toBigDecimal().setScale(ESCALA)
        val aluguel: BigDecimal = config.aluguel.toBigDecimal().setScale(ESCALA)
        val telefone_internet: BigDecimal = config.telefone.toBigDecimal().setScale(ESCALA)

        val custos_fixos_mensais: BigDecimal =
            (manutencao_anual / BigDecimal(MESES).setScale(ESCALA)) +
                    luz +
                    agua +
                    aluguel +
                    telefone_internet

        val custos_fixos_por_bordado =
            ((custos_fixos_mensais / horas_trabalho_mes.toBigDecimal()) /
                    BigDecimal(MINUTOS).setScale(ESCALA)
                    ) * tempo_bordado.toBigDecimal()

        return custos_fixos_por_bordado
    }

    private fun calcula_mao_de_obra(
        horas_trabalho_mes: Double,
        tempo_bordado: Double
    ): BigDecimal {
        val salario_base: BigDecimal = config.salario.toBigDecimal().setScale(ESCALA)
        // Acrecentar FGTS e INSS na tabela de configurações
        val FGTS = 8.0 / 100
        val INSS = 3.0 / 100
        val impostos = FGTS + INSS

        // Calcular provisão de férias (1/12):
        val provisao_ferias = salario_base / BigDecimal(MESES).setScale(ESCALA)
        // Adicional de férias (1/3):
        val adicional_ferias = provisao_ferias / BigDecimal(3).setScale(ESCALA)
        // Encargos de férias:
        val encargos_ferias = (provisao_ferias + adicional_ferias) * impostos.toBigDecimal()
        // Custo mensal de férias:
        val ferias_mensal = provisao_ferias + adicional_ferias + encargos_ferias

        // Calcular provisão de 13º (1/12):
        val provisao_decimo = salario_base / BigDecimal(MESES).setScale(ESCALA)
        // Encargos do 13º
        val encargos_decimo = provisao_decimo * impostos.toBigDecimal()
        // Custo mensal do 13º
        val decimo_mensal = provisao_decimo + encargos_decimo

        // Encargos salário
        val encargos_salario = salario_base * impostos.toBigDecimal()

        // custo_mo = (salario_base + provisao_ferias + provisao_13)
        val custo_mo_mensal = (
                salario_base + encargos_salario + decimo_mensal + ferias_mensal
                )

        // horas_produtivas = 85% das horas_trabalho_mes
        val horas_produtivas = (horas_trabalho_mes * 85) / 100

        // Valor da hora = custo_mo / horas_produtivas
        val custo_hora: BigDecimal = (
                custo_mo_mensal / horas_produtivas.toBigDecimal().setScale(ESCALA)
                )

        val custo_mao_obra: BigDecimal =
            (custo_hora / BigDecimal(MINUTOS).setScale(ESCALA)) *
                    tempo_bordado.toBigDecimal()

        return custo_mao_obra
    }

    fun tempo_bordado(bordado: Bordado): Double {
        val velocidade_maquina: Int = config.velocidadeMaquina // Pontos por minuto
        val tempo_troca_cor: Double = config.tempoTrocaCor // Minutos
        val tempo_preparacao: Double = config.tempoPreparacao // Minutos

        val tempo_bordado: Double = (bordado.pontos / velocidade_maquina) +
                (tempo_troca_cor * bordado.cores) + tempo_preparacao
        return tempo_bordado
    }

    fun custo_total(
        bordado: Bordado,
        quantidade: String
    ): BigDecimal {
        val custo_linha_bordado = calcula_custo_linha_bordado(bordado)
        val custo_linha_bobina = calcula_custo_linha_bobina(bordado)
        val custo_entretela = calcula_custo_entretela(bordado)
        val tempo_bordado = tempo_bordado(bordado)
        val custos_tempo_bordado = calcula_custos_tempo_bordado(tempo_bordado)

        val custo = (
                custo_linha_bordado +
                        custo_linha_bobina +
                        custo_entretela +
                        custos_tempo_bordado
                ) * quantidade.toBigDecimal()
        return custo
    }


    private fun calcula_custo_entretela(bordado: Bordado): BigDecimal {
        val custo_metro: BigDecimal = config.custoEntretela.toBigDecimal().setScale(ESCALA)
        val largura: Int = (config.larguraEntretela + MARGEM)
        val comprimento: Int = (config.comprimentoEntreleta + MARGEM)

        val area_entretela = (comprimento * largura) / 2
        val area_bastidor = (bordado.bastidor.largura * bordado.bastidor.altura) / 2

        val quantidade_de_bastidores_por_area_de_entretela = area_entretela / area_bastidor
        val custo_entretela =
            custo_metro / quantidade_de_bastidores_por_area_de_entretela.toBigDecimal()

        return custo_entretela
    }

    private fun calcula_custo_linha_bobina(bordado: Bordado): BigDecimal {
        val custo_cone_linha: BigDecimal = config.custoLinhaBobina.toBigDecimal().setScale(ESCALA)
        val quantidade_linha_por_cone: Int = config.qtdeLinhaBobina
        val consumo_linha_por_1000_pontos: Double = config.consumoLinhaBobina

        val custo_metro_linha = custo_cone_linha / quantidade_linha_por_cone.toBigDecimal()
        val consumo_do_bordado = (bordado.pontos / 1000) * consumo_linha_por_1000_pontos
        val custo_linha_bobina = custo_metro_linha * consumo_do_bordado.toBigDecimal()
        return custo_linha_bobina
    }

    private fun calcula_custo_linha_bordado(bordado: Bordado): BigDecimal {
        val custo_cone_linha: BigDecimal = config.custoLinhaBordado.toBigDecimal().setScale(ESCALA)
        val quantidade_linha_por_cone: Int = config.qtdeLinhaBordado
        val consumo_linha_por_1000_pontos: Double = config.consumoLinhaBordado

        val custo_metro_linha = custo_cone_linha.div(quantidade_linha_por_cone.toBigDecimal())
        val consumo_do_bordado = (bordado.pontos / 1000) * consumo_linha_por_1000_pontos
        val custo_linha_bordado = custo_metro_linha.multiply(consumo_do_bordado.toBigDecimal())

        return custo_linha_bordado
    }
}