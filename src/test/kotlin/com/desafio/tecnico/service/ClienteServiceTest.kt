import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.repository.ClienteRepository
import com.desafio.tecnico.service.ClienteService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.test.assertEquals


class ClienteServiceTest {

    private val clienteRepository: ClienteRepository = mockk()
    private val clienteService = ClienteService(clienteRepository)

    @Test
    fun `deve salvar o cliente quando CPF não estiver cadastrado`() {

        val cliente = Cliente(id = 1, cpf = "12345678900", nome = "João Silva")



        every { clienteRepository.getByCpf(cliente.cpf) } returns cliente
        every { clienteRepository.save(cliente) } returns cliente


        val clienteSalvo = clienteService.salvarCliente(cliente)


        assertEquals(cliente, clienteSalvo)
        verify { clienteRepository.save(cliente) }
    }

    @Test
    fun `deve atualizar a renda mensal do cliente e criar uma solicitação nova quando CPF já estiver cadastrado`() {

        val clienteExistente =
            Cliente(id = 1, cpf = "12345678900", nome = "João Silva", renda_mensal = BigDecimal(2000))
        val clienteAtualizado =
            Cliente(id = 1, cpf = "12345678900", nome = "João Silva", renda_mensal = BigDecimal(3000))

        every { clienteRepository.getByCpf(clienteExistente.cpf) } returns clienteExistente
        every { clienteRepository.save(any<Cliente>()) } returns clienteAtualizado

        val clienteSalvo = clienteService.salvarCliente(clienteAtualizado)

        assertEquals(clienteAtualizado.renda_mensal, clienteSalvo.renda_mensal)
        verify { clienteRepository.save(clienteExistente) }
    }
}

