package com.desafio.tecnico.controller

import com.desafio.tecnico.dto.CartaoOfertadoResponse
import com.desafio.tecnico.dto.ClienteResponse
import com.desafio.tecnico.dto.SolitacaoResponse
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.service.CartaoService
import com.desafio.tecnico.service.ClienteService
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
    private val cartaoService: CartaoService,
    private val clienteService: ClienteService

) {

    @PostMapping
    fun verificarCartoesElegiveis(@RequestBody cliente: Cliente): ResponseEntity<SolitacaoResponse> {

        val clienteSalvo = clienteService.salvarCliente(cliente)

        val cartoes = cartaoService.listarCartoesElegiveis(clienteSalvo)

        val response = SolitacaoResponse(
            numero_solicitacao = UUID.randomUUID(),
            data_solicitacao = LocalDateTime.now(),
            cliente = ClienteResponse(
                nome = clienteSalvo.nome,
                cpf = clienteSalvo.cpf,
                idade = clienteSalvo.idade,
                data_nascimento = clienteSalvo.data_nascimento,
                uf = clienteSalvo.uf,
                renda_mensal = clienteSalvo.renda_mensal,
                email = clienteSalvo.email,
                telefone_whatsapp = clienteSalvo.telefone_whatsapp
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

        return ResponseEntity.ok(response)
    }
}

