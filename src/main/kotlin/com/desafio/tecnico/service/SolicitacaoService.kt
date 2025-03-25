package com.desafio.tecnico.service

import com.desafio.tecnico.dto.SolitacaoResponse
import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.Solicitacao
import com.desafio.tecnico.repository.SolicitacaoRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class SolicitacaoService(
    private val cartaoService: CartaoService,
    private val respostaService: RespostaService,
    private val clienteService: ClienteService,
    private val solicitacaoRepository: SolicitacaoRepository
) {
    fun processarSolicitacao(cliente: Cliente, numeroSolicitacao: UUID): SolitacaoResponse {
        val clienteSalvo = clienteService.salvarCliente(cliente)

        val cartoesElegiveis = cartaoService.listarCartoesElegiveis(clienteSalvo)

        val solicitacao = Solicitacao()
        solicitacao.numeroSolicitacao = numeroSolicitacao
        solicitacao.dataSolicitacao = LocalDateTime.now()
        solicitacao.cartoes = cartoesElegiveis
        solicitacao.cliente = clienteSalvo

        solicitacaoRepository.save(solicitacao)


        return respostaService.criarResposta(clienteSalvo, numeroSolicitacao, cartoesElegiveis)

    }

}
