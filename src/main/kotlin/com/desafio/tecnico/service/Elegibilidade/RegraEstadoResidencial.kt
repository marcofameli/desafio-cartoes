package com.desafio.tecnico.service.Elegibilidade

import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.TipoCartao
import org.springframework.stereotype.Component

@Component
class RegraEstadoResidencial : RegraElegibilidade {
    override fun verificarElegibilidade(cliente: Cliente, cartao: Cartao): Boolean {
        return if (cliente.uf == "SP") {
            if (cliente.idade in 25..30) {
                true
            } else {
                cartao.tipo_cartao == TipoCartao.CARTAO_COM_CASHBACK || cartao.tipo_cartao == TipoCartao.CARTAO_SEM_ANUIDADE
            }
        } else {
            true
        }
    }
}