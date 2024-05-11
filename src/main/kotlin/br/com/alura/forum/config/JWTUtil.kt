package br.com.alura.forum.config

import br.com.alura.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Encoders
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
class JWTUtil (
    private val usuarioService: UsuarioService,
    private val expiration : Long = 60000,
    @Value("\${jwt.secret}") private var secret: String
){

    private val key = SecretKeySpec(Encoders.BASE64.encode(secret.toByteArray()).toByteArray(), "HmacSHA512")
    //private val key = Keys.hmacShaKeyFor(Encoders.BASE64.encode(secret.toByteArray()).toByteArray())

    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String? {
        return Jwts.builder()
            .subject(username)
            .claim("role", authorities)
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key)
            .compact()
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parser()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(jwt)
            .body
            .subject

        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }

//    fun isValid(jwt: String?): Boolean {
//        return try {
//            Jwts.parser().setSigningKey(key).build().parseClaimsJwt(jwt);
//            true
//        } catch (e: IllegalArgumentException){
//            false
//        }
//    }
//
//    fun getAuthentication(jwt: String?) : Authentication {
//        val username = Jwts.parser().setSigningKey(key).build().parseClaimsJwt(jwt).body.subject
//        return UsernamePasswordAuthenticationToken(username, null, null)
//    }
}