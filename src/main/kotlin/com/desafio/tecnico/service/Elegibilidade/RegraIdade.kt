package com.desafio.tecnico.service.Elegibilidade

import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.TipoCartao
import org.springframework.stereotype.Component

@Component
class RegraIdade : RegraElegibilidade {
    override fun verificarElegibilidade(cliente: Cliente, cartao: Cartao): Boolean {
        return if (cliente.idade in 19..24) {
            cartao.tipo_cartao == TipoCartao.CARTAO_SEM_ANUIDADE
        } else {
            true
        }

    }
}