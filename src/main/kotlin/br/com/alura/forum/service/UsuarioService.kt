package br.com.alura.forum.service

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Usuario
import br.com.alura.forum.repository.IUsuarioRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService (
    private val usuarioRepository: IUsuarioRepository
    ){

    fun BuscarPorId(id: Long): Usuario {
        return usuarioRepository.getOne(id)
    }
}
