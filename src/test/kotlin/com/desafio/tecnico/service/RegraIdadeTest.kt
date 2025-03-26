package com.desafio.tecnico.service

import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.TipoCartao
import com.desafio.tecnico.service.Elegibilidade.RegraIdade
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class RegraIdadeTest {
    private val regra = RegraIdade()

    @Test
    fun `deve retornar true para idade maior que 18 e menor que 25 com cartão sem anuidade`() {
        val cliente = Cliente(idade = 20)
        val cartao = Cartao(tipo_cartao = TipoCartao.CARTAO_SEM_ANUIDADE)
        assertTrue(regra.verificarElegibilidade(cliente, cartao))
    }

    @Test
    fun `deve retornar false para idade maior que 18 e menor que 25 com cartão com cashback`() {
        val cliente = Cliente(idade = 22)
        val cartao = Cartao(tipo_cartao = TipoCartao.CARTAO_COM_CASHBACK)
        assertFalse(regra.verificarElegibilidade(cliente, cartao))
    }

    @Test
    fun `deve retornar false para idade maior que 18 e menor que 25 com cartão de parceiros`() {
        val cliente = Cliente(idade = 22)
        val cartao = Cartao(tipo_cartao = TipoCartao.CARTAO_DE_PARCEIROS)
        assertFalse(regra.verificarElegibilidade(cliente, cartao))
    }

    @Test
    fun `deve retornar true para idade maior que 25 com qualquer tipo de cartão`() {
        val cliente = Cliente(idade = 30)
        val cartaoComAnuidade = Cartao(tipo_cartao = TipoCartao.CARTAO_COM_CASHBACK)
        val cartaoSemAnuidade = Cartao(tipo_cartao = TipoCartao.CARTAO_SEM_ANUIDADE)
        val cartaoDeParceiros = Cartao(tipo_cartao = TipoCartao.CARTAO_DE_PARCEIROS)

        assertTrue(regra.verificarElegibilidade(cliente, cartaoComAnuidade))
        assertTrue(regra.verificarElegibilidade(cliente, cartaoSemAnuidade))
        assertTrue(regra.verificarElegibilidade(cliente, cartaoDeParceiros))
    }
}

