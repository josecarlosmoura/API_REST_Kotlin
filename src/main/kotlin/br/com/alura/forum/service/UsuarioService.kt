package br.com.alura.forum.service

import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.model.Usuario
import br.com.alura.forum.repository.IUsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuarioService (
    private val usuarioRepository: IUsuarioRepository
    ): UserDetailsService{

    fun BuscarPorId(id: Long): Usuario {
        return usuarioRepository.getOne(id)
    }

    override fun loadUserByUsername(userName: String?): UserDetails {
        val usuario = usuarioRepository.findByEmail(userName) ?: throw NotFoundException("Recurso não encontrado")

        return UserDetail(usuario)
    }
}
