package com.desafio.tecnico.exceptions

import com.desafio.tecnico.dto.DetalheErroDTO
import com.desafio.tecnico.dto.ProblemDetailsDTO
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun MethodArgumentNotValidException(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ProblemDetailsDTO> {
        val detalheErro = DetalheErroDTO(
            app = "Cartoes",
            tipo_erro = "DADOS_INVALIDOS",
            mensagem_interna = "Os dados informados são inválidos. Verifique os campos obrigatórios e formatos esperados."
        )

        val problem = ProblemDetailsDTO(
            codigo = "400",
            mensagem = "Requisição Inválida",
            detalhe_erro = detalheErro
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem)
    }


    @ExceptionHandler(RegraNegocioException::class)
    fun handleRegraNegocioException(ex: RegraNegocioException, request: WebRequest): ResponseEntity<ProblemDetailsDTO> {
        val detalheErro = DetalheErroDTO(
            app = "Cartoes",
            tipo_erro = "REGRA_NEGOCIO_NAO_ATENDIDA",
            mensagem_interna = ex.message ?: "Cliente não atende aos critérios de elegibilidade para o cartão."
        )

        val problem = ProblemDetailsDTO(
            codigo = "422",  // Código 422
            mensagem = "Regra de Negócio Não Atendida",
            detalhe_erro = detalheErro
        )

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem)
    }


    @ExceptionHandler(Exception::class)
    fun handleInternalServerError(ex: Exception, request: WebRequest): ResponseEntity<ProblemDetailsDTO> {
        val detalheErro = DetalheErroDTO(
            app = "Cartoes",
            tipo_erro = "SERVICO_INDISPONIVEL",
            mensagem_interna = "Tivemos um problema, mas fique tranquilo que nosso time já foi avisado."
        )

        val problem = ProblemDetailsDTO(
            codigo = "500",  // Código 500
            mensagem = "Um erro inesperado ocorreu.",
            detalhe_erro = detalheErro
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem)
    }
}
