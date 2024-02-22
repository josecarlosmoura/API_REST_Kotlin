package br.com.alura.forum.mapper

import br.com.alura.forum.dto.TopicoViewDTO
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Component

@Component
class TopicoViewMapper: Mapper<Topico, TopicoViewDTO> {

    override fun map(t: Topico): TopicoViewDTO {
        return TopicoViewDTO(
            id = t.id,
            titulo = t.titulo,
            mensagem = t.mensagem,
            dataCriacao = t.dataCriacao,
            statusTopico = t.status
        )
    }
}