package com.desafio.tecnico.service

import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.TipoCartao
import com.desafio.tecnico.service.Elegibilidade.RegraEstadoResidencial
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal

class RegraEstadoResidencialTest {

    private val regra = RegraEstadoResidencial()


    private fun criarCliente(uf: String, idade: Int): Cliente {
        return Cliente(
            uf = uf,
            idade = idade,
            nome = "Teste",
            cpf = "12345678901",
            renda_mensal = BigDecimal("10000.00")
        )
    }


    private fun criarCartao(tipo: TipoCartao): Cartao {
        return Cartao(
            tipo_cartao = tipo,
            valor_limite_disponivel = BigDecimal("1000.00"),
            valor_anuidade_mensal = if (tipo == TipoCartao.CARTAO_SEM_ANUIDADE) BigDecimal.ZERO else BigDecimal("50.00"),
            rendaMinima = BigDecimal("3500.00")
        )
    }


    @Test
    fun `cliente de SP entre 25-30 anos pode ter qualquer tipo de cartao`() {
        val cliente = criarCliente(uf = "SP", idade = 28)

        val cartaoSemAnuidade = criarCartao(TipoCartao.CARTAO_SEM_ANUIDADE)
        val cartaoCashback = criarCartao(TipoCartao.CARTAO_COM_CASHBACK)
        val cartaoParceiros = criarCartao(TipoCartao.CARTAO_DE_PARCEIROS)

        assertAll(
            { assertTrue(regra.verificarElegibilidade(cliente, cartaoSemAnuidade)) },
            { assertTrue(regra.verificarElegibilidade(cliente, cartaoCashback)) },
            { assertTrue(regra.verificarElegibilidade(cliente, cartaoParceiros)) }
        )
    }

    @Test
    fun `cliente de SP com mais de 30 anos so pode cartao sem anuidade ou com cashback`() {
        val cliente = criarCliente(uf = "SP", idade = 35)

        val cartaoSemAnuidade = criarCartao(TipoCartao.CARTAO_SEM_ANUIDADE)
        val cartaoCashback = criarCartao(TipoCartao.CARTAO_COM_CASHBACK)
        val cartaoParceiros = criarCartao(TipoCartao.CARTAO_DE_PARCEIROS)

        assertAll(
            { assertTrue(regra.verificarElegibilidade(cliente, cartaoSemAnuidade)) },
            { assertTrue(regra.verificarElegibilidade(cliente, cartaoCashback)) },
            { assertFalse(regra.verificarElegibilidade(cliente, cartaoParceiros)) }
        )
    }

    @Test
    fun `cliente de SP com menos de 25 anos so pode cartao sem anuidade`() {
        val cliente = criarCliente(uf = "SP", idade = 20)

        val cartaoSemAnuidade = criarCartao(TipoCartao.CARTAO_SEM_ANUIDADE)
        val cartaoParceiros = criarCartao(TipoCartao.CARTAO_DE_PARCEIROS)

        assertAll(
            { assertTrue(regra.verificarElegibilidade(cliente, cartaoSemAnuidade)) },
            { assertFalse(regra.verificarElegibilidade(cliente, cartaoParceiros)) }
        )
    }

    @Test
    fun `cliente de RJ pode ter qualquer tipo de cartao independente da idade`() {
        val cliente = criarCliente(uf = "RJ", idade = 40)

        val cartaoParceiros = criarCartao(TipoCartao.CARTAO_DE_PARCEIROS)
        assertTrue(regra.verificarElegibilidade(cliente, cartaoParceiros))
    }

    @Test
    fun `cliente de MG pode ter cartao com cashback`() {
        val cliente = criarCliente(uf = "MG", idade = 22)

        val cartaoCashback = criarCartao(TipoCartao.CARTAO_COM_CASHBACK)
        assertTrue(regra.verificarElegibilidade(cliente, cartaoCashback))
    }


    @Test
    fun `cliente de SP com exatamente 25 anos pode qualquer cartao`() {
        val cliente = criarCliente(uf = "SP", idade = 25)
        val cartaoParceiros = criarCartao(TipoCartao.CARTAO_DE_PARCEIROS)
        assertTrue(regra.verificarElegibilidade(cliente, cartaoParceiros))
    }
}

