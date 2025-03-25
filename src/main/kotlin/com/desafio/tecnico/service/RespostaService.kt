package com.desafio.tecnico.service

import com.desafio.tecnico.dto.CartaoOfertadoResponse
import com.desafio.tecnico.dto.ClienteResponse
import com.desafio.tecnico.dto.SolitacaoResponse
import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class RespostaService {
    fun criarResposta(cliente: Cliente, numeroSolicitacao: UUID, cartoes: List<Cartao>): SolitacaoResponse {
        return SolitacaoResponse(
            numero_solicitacao = numeroSolicitacao,
            data_solicitacao = LocalDateTime.now(),
            cliente = ClienteResponse(
                nome = cliente.nome,
                cpf = cliente.cpf,
                idade = cliente.idade,
                data_nascimento = cliente.data_nascimento,
                uf = cliente.uf,
                renda_mensal = cliente.renda_mensal,
                email = cliente.email,
                telefone_whatsapp = cliente.telefone_whatsapp
            ),
            cartoes_ofertados = cartoes.map { cartao ->
                CartaoOfertadoResponse(
                    tipoCartao = cartao.tipo_cartao,
                    valor_anuidade_mensal = cartao.valor_anuidade_mensal,
                    valor_limite_disponivel = cartao.valor_limite_disponivel,
                    status = cartao.status
                )
            }
        )
    }
}
