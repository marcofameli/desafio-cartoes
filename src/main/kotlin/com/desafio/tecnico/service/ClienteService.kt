package com.desafio.tecnico.service

import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.repository.ClienteRepository
import org.springframework.stereotype.Service

@Service
class ClienteService(private val clienteRepository: ClienteRepository) {

    fun salvarCliente(cliente: Cliente): Cliente {
        val clienteJaExiste = clienteRepository.getByCpf(cliente.cpf) ?: return clienteRepository.save(cliente)

        clienteJaExiste.renda_mensal = cliente.renda_mensal

        return clienteRepository.save(clienteJaExiste)

    }
}
