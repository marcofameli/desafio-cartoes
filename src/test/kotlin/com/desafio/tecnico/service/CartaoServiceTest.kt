package com.desafio.tecnico.service

import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.TipoCartao
import com.desafio.tecnico.repository.CartaoRepository
import com.desafio.tecnico.service.Elegibilidade.RegraElegibilidade
import io.mockk.every
import io.mockk.mockk
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CartaoServiceTest {

    private val cartaoRepository: CartaoRepository = mockk()
    private val regraElegibilidade: RegraElegibilidade = mockk()
    private val cartaoService = CartaoService(cartaoRepository, listOf(regraElegibilidade))

    @Test
    fun `deve retornar cartões elegíveis com base nas regras de elegibilidade e renda do cliente`() {
        val cliente = Cliente(renda_mensal = BigDecimal(5000), idade = 28)

        val cartao1 = Cartao(tipo_cartao = TipoCartao.CARTAO_SEM_ANUIDADE)
        val cartao2 = Cartao(tipo_cartao = TipoCartao.CARTAO_COM_CASHBACK)


        every { cartaoRepository.listarCartoesElegiveis(cliente.renda_mensal) } returns listOf(cartao1, cartao2)
        every { regraElegibilidade.verificarElegibilidade(cliente, cartao1) } returns true
        every { regraElegibilidade.verificarElegibilidade(cliente, cartao2) } returns true


        val cartoesElegiveis = cartaoService.listarCartoesElegiveis(cliente)


        assertEquals(2, cartoesElegiveis.size)
        assertTrue(cartoesElegiveis.contains(cartao1))
        assertTrue(cartoesElegiveis.contains(cartao2))
    }
}