package br.com.alura.forum.service

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Usuario
import br.com.alura.forum.repository.ICursoRepository
import br.com.alura.forum.repository.IUsuarioRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class CursoService (
    private val cursoRepository: ICursoRepository
    ){

    fun BuscarPorId(id: Long): Curso {
        return cursoRepository.getOne(id)
    }
}
