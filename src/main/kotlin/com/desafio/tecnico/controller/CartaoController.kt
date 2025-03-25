package com.desafio.tecnico.controller

import com.desafio.tecnico.dto.CartaoOfertadoResponse
import com.desafio.tecnico.dto.ClienteResponse
import com.desafio.tecnico.dto.SolitacaoResponse
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.service.CartaoService
import com.desafio.tecnico.service.ClienteService
import com.desafio.tecnico.service.SolicitacaoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/cartoes")
class CartaoController(
    private val solicitacaoService: SolicitacaoService,
) {
    @PostMapping
    fun verificarCartoesElegiveis(@RequestBody cliente: Cliente): ResponseEntity<SolitacaoResponse> {

        val numeroSolicitacao = UUID.randomUUID()

//        val cartoes = solicitacaoService.processarSolicitacao(cliente, numeroSolicitacao)
//
//
//        val clienteSalvo = clienteService.salvarCliente(cliente)

        val response = solicitacaoService.processarSolicitacao(cliente, numeroSolicitacao)


        return ResponseEntity.ok(response)
    }
}