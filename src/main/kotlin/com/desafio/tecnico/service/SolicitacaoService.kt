package com.desafio.tecnico.service

import com.desafio.tecnico.dto.SolicitacaoResponseDTO
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
    fun processarSolicitacao(cliente: Cliente, numeroSolicitacao: UUID): ResponseEntity<SolicitacaoResponseDTO> {
        val clienteSalvo = clienteService.salvarCliente(cliente)

        val cartoesElegiveis = cartaoService.listarCartoesElegiveis(clienteSalvo)

        if (cartoesElegiveis.isEmpty()) {
            return ResponseEntity.noContent().build()
        }


        val solicitacao = Solicitacao(
            numeroSolicitacao = numeroSolicitacao,
            dataSolicitacao = LocalDateTime.now(),
            cartoes = cartoesElegiveis,
            cliente = clienteSalvo,
        )


        solicitacaoRepository.save(solicitacao)


        val resposta = respostaService.criarResposta(clienteSalvo, numeroSolicitacao, cartoesElegiveis)

        return ResponseEntity.ok(resposta)
    }

}
