import com.desafio.tecnico.exceptions.RegraNegocioException
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.repository.ClienteRepository
import com.desafio.tecnico.service.ClienteService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.junit.jupiter.api.Test


class ClienteServiceTest {

    private val clienteRepository: ClienteRepository = mockk()
    private val clienteService = ClienteService(clienteRepository)

    @Test
    fun `deve salvar o cliente quando CPF não estiver cadastrado`() {

        val cliente = Cliente(id = 1, cpf = "12345678900", nome = "João Silva")



        every { clienteRepository.existsByCpf(cliente.cpf) } returns false
        every { clienteRepository.save(cliente) } returns cliente


        val clienteSalvo = clienteService.salvarCliente(cliente)


        assertEquals(cliente, clienteSalvo)
        verify { clienteRepository.save(cliente) }
    }

    @Test
    fun `deve lançar exceção quando CPF já estiver cadastrado`() {

        val cliente = Cliente(id = 1, cpf = "12345678900", nome = "João Silva")


        every { clienteRepository.existsByCpf(cliente.cpf) } returns true


        assertFailsWith<RegraNegocioException> {
            clienteService.salvarCliente(cliente)
        }
    }
}
