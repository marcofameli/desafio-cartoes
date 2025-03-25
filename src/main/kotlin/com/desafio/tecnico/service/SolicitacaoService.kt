package com.desafio.tecnico.service

import com.desafio.tecnico.dto.SolitacaoResponseDTO
import com.desafio.tecnico.exceptions.RegraNegocioException
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.Solicitacao
import com.desafio.tecnico.repository.SolicitacaoRepository
import org.springframework.http.ResponseEntity
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
    fun processarSolicitacao(cliente: Cliente, numeroSolicitacao: UUID): ResponseEntity<SolitacaoResponseDTO> {
        val clienteSalvo = clienteService.salvarCliente(cliente)

        val cartoesElegiveis = cartaoService.listarCartoesElegiveis(clienteSalvo)

        if (cartoesElegiveis.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        val solicitacao = Solicitacao()
        solicitacao.numeroSolicitacao = numeroSolicitacao
        solicitacao.dataSolicitacao = LocalDateTime.now()
        solicitacao.cartoes = cartoesElegiveis
        solicitacao.cliente = clienteSalvo

        solicitacaoRepository.save(solicitacao)

        // Retorna 200 OK com o corpo no formato SolitacaoResponseDTO
        val resposta = respostaService.criarResposta(clienteSalvo, numeroSolicitacao, cartoesElegiveis)

        return ResponseEntity.ok(resposta)
    }
}
