package br.com.alura.forum.dto

import br.com.alura.forum.model.StatusTopico
import java.time.LocalDateTime

data class TopicoViewDTO(
    val id: Long?,
    val titulo: String,
    val mensagem: String,
    val statusTopico: StatusTopico,
    val dataCriacao: LocalDateTime
)