package com.desafio.tecnico.service

import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.repository.ClienteRepository
import org.springframework.stereotype.Service

@Service
class ClienteService(private val clienteRepository: ClienteRepository) {

    fun salvarCliente(cliente: Cliente): Cliente {
        return clienteRepository.findByCpf(cliente.cpf) ?: clienteRepository.save(cliente)
    }

}