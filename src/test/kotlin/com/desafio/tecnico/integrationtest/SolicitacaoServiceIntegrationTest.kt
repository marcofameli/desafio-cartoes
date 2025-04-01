package com.desafio.tecnico.integration

import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.TipoCartao
import com.desafio.tecnico.repository.CartaoRepository
import com.desafio.tecnico.repository.ClienteRepository
import com.desafio.tecnico.repository.SolicitacaoRepository
import com.desafio.tecnico.service.SolicitacaoService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
@ActiveProfiles("test")
class SolicitacaoServiceIntegrationTest {

    @Autowired
    private lateinit var solicitacaoService: SolicitacaoService

    @Autowired
    private lateinit var clienteRepository: ClienteRepository

    @Autowired
    private lateinit var solicitacaoRepository: SolicitacaoRepository

    @Autowired
    private lateinit var cartaoRepository: CartaoRepository

    private lateinit var cartaoElegivel: Cartao

    @BeforeEach
    fun setUp() {
        solicitacaoRepository.deleteAll()
        clienteRepository.deleteAll()
        cartaoRepository.deleteAll()

        cartaoElegivel = Cartao(
            tipo_cartao = TipoCartao.CARTAO_SEM_ANUIDADE,
            valor_anuidade_mensal = BigDecimal.ZERO,
            valor_limite_disponivel = BigDecimal(1000),
            rendaMinima = BigDecimal(3500),
            status = "APROVADO"
        )
        cartaoRepository.save(cartaoElegivel)
    }

    @Test
    fun `deve processar solicitacao com sucesso para cliente elegível`() {

        val cliente = Cliente(
            cpf = "123.456.789-10",
            nome = "João Silva",
            idade = 18,
            data_nascimento = LocalDate.of(1995, 3, 25),
            uf = "SP",
            renda_mensal = BigDecimal(3500),
            email = "joao.silva@email.com",
            telefone_whatsapp = "11987654321"
        )

        val numeroSolicitacao = UUID.randomUUID()


        val response = solicitacaoService.processarSolicitacao(cliente, numeroSolicitacao)


        assertEquals(200, response.statusCodeValue)
        assertNotNull(response.body)


        val clienteSalvo = clienteRepository.getByCpf(cliente.cpf)
        assertNotNull(clienteSalvo)

    }
    @Transactional
    @Test
    fun `deve retornar no content quando nenhum cartão for elegível`() {

        val cliente = Cliente(
            cpf = "123.456.789-10",
            nome = "Maria Souza",
            idade = 25,
            data_nascimento = LocalDate.of(1998, 5, 15),
            uf = "RJ",
            renda_mensal = BigDecimal(1000),
            email = "maria.souza@email.com",
            telefone_whatsapp = "21987654321"
        )

        val numeroSolicitacao = UUID.randomUUID()


        val response = solicitacaoService.processarSolicitacao(cliente, numeroSolicitacao)


        assertEquals(204, response.statusCodeValue)
    }

    @Test
    fun `deve atualizar a renda mensal do cliente e criar uma solicitação nova quando CPF já estiver cadastrado`() {

        val clienteExistente = Cliente(
            cpf = "123.456.789-10",
            nome = "Cliente Existente",
            idade = 30,
            data_nascimento = LocalDate.of(1993, 1, 1),
            uf = "MG",
            renda_mensal = BigDecimal(6000),
            email = "existente@email.com",
            telefone_whatsapp = "31987654321"
        )

        clienteRepository.save(clienteExistente)


        val clienteDuplicado = Cliente(
            cpf = "123.456.789-10",
            nome = "Cliente Existente",
            idade = 30,
            data_nascimento = LocalDate.of(1993, 1, 1),
            uf = "MG",
            renda_mensal = BigDecimal(10000),
            email = "existente@email.com",
            telefone_whatsapp = "31987654321"
        )

        val numeroSolicitacao = UUID.randomUUID()

        solicitacaoService.processarSolicitacao(clienteDuplicado, numeroSolicitacao)

        val clienteAtualizado = clienteRepository.getByCpf(clienteDuplicado.cpf)
        assertEquals(clienteDuplicado.cpf, clienteAtualizado?.cpf)

    }
}