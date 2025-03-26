package com.desafio.tecnico.integrationtest

import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.TipoCartao
import com.desafio.tecnico.repository.CartaoRepository
import com.desafio.tecnico.repository.ClienteRepository
import com.desafio.tecnico.repository.SolicitacaoRepository
import com.desafio.tecnico.service.CartaoService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CartaoServiceIntegrationTest {

    @Autowired
    lateinit var cartaoService: CartaoService
    @Autowired
    lateinit var cartaoRepository: CartaoRepository
    @Autowired
    private lateinit var clienteRepository: ClienteRepository

    @Autowired
    private lateinit var solicitacaoRepository: SolicitacaoRepository

    @BeforeEach
    fun setUp() {

        clienteRepository.deleteAll()
        cartaoRepository.deleteAll()
        solicitacaoRepository.deleteAll()


        val cartao1 = Cartao(
            tipo_cartao = TipoCartao.CARTAO_SEM_ANUIDADE,
            valor_anuidade_mensal = BigDecimal.ZERO,
            valor_limite_disponivel = BigDecimal(1000),
            rendaMinima = BigDecimal(3500), //
            status = "APROVADO"
        )

        val cartao2 = Cartao(
            tipo_cartao = TipoCartao.CARTAO_DE_PARCEIROS,
            valor_anuidade_mensal = BigDecimal(10),
            valor_limite_disponivel = BigDecimal(3000),
            rendaMinima = BigDecimal(5500),
            status = "APROVADO"
        )

        val cartao3 = Cartao(
            tipo_cartao = TipoCartao.CARTAO_DE_PARCEIROS,
            valor_anuidade_mensal = BigDecimal(10),
            valor_limite_disponivel = BigDecimal(5000),
            rendaMinima = BigDecimal(7500),
            status = "APROVADO"
        )

        cartaoRepository.saveAll(listOf(cartao1, cartao2, cartao3))
    }

    @Test
    fun `deve filtrar apenas cartoes elegiveis`() {
        val cliente = Cliente(renda_mensal = BigDecimal(4000))

        val resultado = cartaoService.listarCartoesElegiveis(cliente)

        assertEquals(1, resultado.size)
        assertEquals(BigDecimal(3500), resultado[0].rendaMinima)
    }

    @Test
    fun `deve retornar lista vazia quando nao houver cartoes elegiveis`() {
        val cliente = Cliente(renda_mensal = BigDecimal(1000))

        val resultado = cartaoService.listarCartoesElegiveis(cliente)

        assertTrue(resultado.isEmpty())
    }
    @Test
    fun `deve retornar todos os cartoes elegiveis quando atender a requisitos`() {
        val cliente = Cliente(renda_mensal = BigDecimal(7500))

        val resultado = cartaoService.listarCartoesElegiveis(cliente)

        assertEquals(3, resultado.size)
        assertTrue(resultado.any() { it.rendaMinima == BigDecimal(3500) })
        assertTrue(resultado.any() { it.rendaMinima == BigDecimal(5500) })
        assertTrue(resultado.any() { it.rendaMinima == BigDecimal(7500) })
    }

}