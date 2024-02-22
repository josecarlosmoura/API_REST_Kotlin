package br.com.alura.forum.service

import br.com.alura.forum.dto.TopicoFormDTO
import br.com.alura.forum.dto.TopicoFormUpdateDTO
import br.com.alura.forum.dto.TopicoViewDTO
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.Topico
import br.com.alura.forum.repository.ITopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

@Service
class TopicoService (
    private val topicoRepository: ITopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Tópico não encontrado!"
    ) {

    fun listar(
        nomeCurso: String?,
        paginacao: Pageable
    ): Page<TopicoViewDTO> {
        val topicos = if (nomeCurso == null){
            topicoRepository.findAll(paginacao)
        } else {
            topicoRepository.findByCursoNome(nomeCurso, paginacao)
        }
        return topicos.map { t ->  topicoViewMapper.map(t) }
    }

    fun listarPorId(id: Long): TopicoViewDTO {
        val topicoBuscado = topicoRepository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}

        return topicoViewMapper.map(topicoBuscado)
    }

    fun cadastrar(dto: TopicoFormDTO): TopicoViewDTO {
        val topicoCriado = topicoFormMapper.map(dto)
        topicoRepository.save(topicoCriado)

        return topicoViewMapper.map(topicoCriado)
    }

    fun Atualizar(dto: TopicoFormUpdateDTO): TopicoViewDTO {
        val topicoBuscado = topicoRepository.findById(dto.id).orElseThrow{NotFoundException(notFoundMessage)}

        topicoBuscado.titulo = dto.titulo
        topicoBuscado.mensagem = dto.mensagem

        return topicoViewMapper.map(topicoBuscado)
    }

    fun deletar(id: Long) {
        topicoRepository.deleteById(id)
    }

}