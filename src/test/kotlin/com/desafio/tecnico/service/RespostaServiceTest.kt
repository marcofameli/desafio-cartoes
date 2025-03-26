import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.TipoCartao
import com.desafio.tecnico.service.RespostaService
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals

class RespostaServiceTest {

    private val respostaService = RespostaService()

    @Test
    fun `deve criar resposta corretamente com dados do cliente e cartoes ofertados`() {

        val cliente = Cliente(
            id = 1,
            cpf = "12345678900",
            nome = "João Silva",
            idade = 28,
            data_nascimento = LocalDate.of(1995, 3, 25),
            uf = "SP",
            renda_mensal = BigDecimal(5000),
            email = "joao.silva@email.com",
            telefone_whatsapp = "11987654321"
        )
        val cartao1 = Cartao(
            tipo_cartao = TipoCartao.CARTAO_SEM_ANUIDADE,
            valor_anuidade_mensal = BigDecimal(0),
            valor_limite_disponivel = BigDecimal(1000.0),
            status = "Ativo"
        )
        val cartao2 = Cartao(
            tipo_cartao = TipoCartao.CARTAO_COM_CASHBACK,
            valor_anuidade_mensal = BigDecimal(20.0),
            valor_limite_disponivel = BigDecimal(2000.0),
            status = "Ativo"
        )
        val numeroSolicitacao = UUID.randomUUID()


        val resposta = respostaService.criarResposta(cliente, numeroSolicitacao, listOf(cartao1, cartao2))


        assertEquals(numeroSolicitacao, resposta.numero_solicitacao)
        assertEquals(cliente.nome, resposta.cliente.nome)
        assertEquals(cliente.cpf, resposta.cliente.cpf)
        assertEquals(cliente.idade, resposta.cliente.idade)
        assertEquals(cliente.data_nascimento, resposta.cliente.data_nascimento)
        assertEquals(cliente.uf, resposta.cliente.uf)
        assertEquals(cliente.renda_mensal, resposta.cliente.renda_mensal)
        assertEquals(cliente.email, resposta.cliente.email)
        assertEquals(cliente.telefone_whatsapp, resposta.cliente.telefone_whatsapp)
        assertEquals(2, resposta.cartoes_ofertados.size)


        val cartaoResponse1 = resposta.cartoes_ofertados[0]
        assertEquals(cartao1.tipo_cartao, cartaoResponse1.tipoCartao)
        assertEquals(cartao1.valor_anuidade_mensal, cartaoResponse1.valor_anuidade_mensal)
        assertEquals(cartao1.valor_limite_disponivel, cartaoResponse1.valor_limite_disponivel)
        assertEquals(cartao1.status, cartaoResponse1.status)

        val cartaoResponse2 = resposta.cartoes_ofertados[1]
        assertEquals(cartao2.tipo_cartao, cartaoResponse2.tipoCartao)
        assertEquals(cartao2.valor_anuidade_mensal, cartaoResponse2.valor_anuidade_mensal)
        assertEquals(cartao2.valor_limite_disponivel, cartaoResponse2.valor_limite_disponivel)
        assertEquals(cartao2.status, cartaoResponse2.status)
    }
}
