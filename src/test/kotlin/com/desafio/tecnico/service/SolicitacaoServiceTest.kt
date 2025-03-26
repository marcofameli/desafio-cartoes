package com.desafio.tecnico.service

import com.desafio.tecnico.exceptions.RegraNegocioException
import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.TipoCartao
import com.desafio.tecnico.repository.SolicitacaoRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SolicitacaoServiceTest {

    private lateinit var solicitacaoService: SolicitacaoService
    private lateinit var cartaoService: CartaoService
    private lateinit var respostaService: RespostaService
    private lateinit var clienteService: ClienteService
    private lateinit var solicitacaoRepository: SolicitacaoRepository

    @BeforeEach
    fun setUp() {
        cartaoService = mockk()
        respostaService = mockk()
        clienteService = mockk()
        solicitacaoRepository = mockk()

        solicitacaoService = SolicitacaoService(
            cartaoService,
            respostaService,
            clienteService,
            solicitacaoRepository
        )
    }


    @Test
    fun `deve retornar no content quando nenhum cartão elegível for encontrado`() {

        val cliente = Cliente(
            id = 1,
            cpf = "12345678900",
            nome = "João Silva",
            idade = 28,
            data_nascimento = LocalDate.of(1995, 3, 25),
            uf = "SP",
            renda_mensal = BigDecimal(5000.0),
            email = "joao.silva@email.com",
            telefone_whatsapp = "11987654321"
        )
        val numeroSolicitacao = UUID.randomUUID()


        every { clienteService.salvarCliente(cliente) } returns cliente
        every { cartaoService.listarCartoesElegiveis(cliente) } returns emptyList()


        val response = solicitacaoService.processarSolicitacao(cliente, numeroSolicitacao)


        assertEquals(204, response.statusCodeValue)
    }

    @Test
    fun `deve lançar RegraNegocioException quando cliente já existir com o mesmo CPF`() {

        val cliente = Cliente(
            id = 1,
            cpf = "12345678900",
            nome = "João Silva",
            idade = 18,
            data_nascimento = LocalDate.of(1995, 3, 25),
            uf = "SP",
            renda_mensal = BigDecimal(2000.0),
            email = "joao.silva@email.com",
            telefone_whatsapp = "11987654321"
        )

        every { clienteService.salvarCliente(cliente) } throws RegraNegocioException("CPF já cadastrado.")


        assertFailsWith<RegraNegocioException>("CPF já cadastrado.") {
            solicitacaoService.processarSolicitacao(cliente, UUID.randomUUID())
        }

    }

    @Test
    fun `deve retornar status 200 OK quando há cartões elegíveis`() {
        val cliente = Cliente(
            id = 1,
            cpf = "12345678900",
            nome = "João Silva",
            idade = 28,
            data_nascimento = LocalDate.of(1995, 3, 25),
            uf = "SP",
            renda_mensal = BigDecimal(5000.0),
            email = "joao.silva@email.com",
            telefone_whatsapp = "11987654321"
        )

        val cartaoElegivel = Cartao(
            id = 1,
            tipo_cartao = TipoCartao.CARTAO_SEM_ANUIDADE,
            valor_anuidade_mensal = BigDecimal.ZERO,
            valor_limite_disponivel = BigDecimal(1000),
            rendaMinima = BigDecimal(3500),
            status = "APROVADO"
        )

        val numeroSolicitacao = UUID.randomUUID()
        val listaCartoes = listOf(cartaoElegivel)

        every { clienteService.salvarCliente(cliente) } returns cliente
        every { cartaoService.listarCartoesElegiveis(cliente) } returns listaCartoes
        every { solicitacaoRepository.save(any()) } returns mockk()
        every { respostaService.criarResposta(cliente, numeroSolicitacao, listaCartoes) } returns mockk()

        val response = solicitacaoService.processarSolicitacao(cliente, numeroSolicitacao)

        assertEquals(200, response.statusCodeValue)
    }

    @Test
    fun `deve retornar status 400 Bad Request quando ocorrer erro de validação`() {
        val cliente = Cliente(
            id = 1,
            cpf = "",
            nome = "João Silva",
            idade = 28,
            data_nascimento = LocalDate.of(1995, 3, 25),
            uf = "SP",
            renda_mensal = BigDecimal(5000.0),
            email = "joao.silva@email.com",
            telefone_whatsapp = "11987654321"
        )

        val numeroSolicitacao = UUID.randomUUID()

        every { clienteService.salvarCliente(cliente) } throws IllegalArgumentException("Dados inválidos")

        try {
            solicitacaoService.processarSolicitacao(cliente, numeroSolicitacao)
        } catch (ex: IllegalArgumentException) {
            assertEquals("Dados inválidos", ex.message)
        }
    }

    @Test
    fun `deve retornar status 500 Internal Server Error quando ocorrer erro interno`() {
        val cliente = Cliente(
            id = 1,
            cpf = "12345678900",
            nome = "João Silva",
            idade = 28,
            data_nascimento = LocalDate.of(1995, 3, 25),
            uf = "SP",
            renda_mensal = BigDecimal(5000.0),
            email = "joao.silva@email.com",
            telefone_whatsapp = "11987654321"
        )

        val numeroSolicitacao = UUID.randomUUID()

        every { clienteService.salvarCliente(cliente) } throws RuntimeException("Erro interno do servidor")

        try {
            solicitacaoService.processarSolicitacao(cliente, numeroSolicitacao)
        } catch (ex: RuntimeException) {
            assertEquals("Erro interno do servidor", ex.message)
        }
    }

    @Test
    fun `Deve lançar exceção quando cliente tiver CPF inválido`() {
        val cliente = Cliente(nome = "João", cpf = "123", idade = 30, uf = "SP")
        val numeroSolicitacao = UUID.randomUUID()


        every { clienteService.salvarCliente(cliente) } throws IllegalArgumentException("CPF inválido")


        val exception = assertFailsWith<IllegalArgumentException> {
            solicitacaoService.processarSolicitacao(cliente, numeroSolicitacao)
        }


        assertEquals("CPF inválido", exception.message)
    }
}

