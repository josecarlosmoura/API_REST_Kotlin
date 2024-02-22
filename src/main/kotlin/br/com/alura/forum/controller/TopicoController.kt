package br.com.alura.forum.controller

import br.com.alura.forum.dto.TopicoFormDTO
import br.com.alura.forum.dto.TopicoFormUpdateDTO
import br.com.alura.forum.dto.TopicoViewDTO
import br.com.alura.forum.service.TopicoService
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos")
class TopicoController (private val topicoService: TopicoService) {

    @GetMapping
    @Cacheable("topicos")
    fun listar(
        @RequestParam(required = false) nomeCurso:String?,
        @PageableDefault(size = 5, sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable
    ): Page<TopicoViewDTO>{
        return topicoService.listar(nomeCurso, paginacao)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoViewDTO{
        return topicoService.listarPorId(id)
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun cadastrar(
        @RequestBody @Valid dto: TopicoFormDTO,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoViewDTO> {
        val topicoViewDTO = topicoService.cadastrar(dto)

        val uri = uriBuilder.path("/topicos/${topicoViewDTO.id}").build().toUri()

        return ResponseEntity.created(uri).body(topicoViewDTO)
    }

    @PutMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun atualizar(@RequestBody @Valid dto: TopicoFormUpdateDTO): ResponseEntity<TopicoViewDTO> {
        val topicoViewDTO = topicoService.Atualizar(dto)

        return ResponseEntity.ok(topicoViewDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun deletar(@PathVariable id: Long){
        topicoService.deletar(id)
    }
}