package org.example.service.authorization

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.example.domain.users.users
import java.util.Date

val SECRET_KEY = "cat_red"
class GenerateToken {
    fun Token_User(user: users): String {
        val now = System.currentTimeMillis()
        return Jwts.builder()
            .setSubject(user.id_users.toString())
            .claim("username", user.username)
            .claim("role", user.role)
            .setIssuedAt(Date(now))
            .setExpiration(Date(now + 3600_000)) // 1 час
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact()
    }
    fun verifyToken(token: String): Claims? {
        return try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            null // Неверный токен или истек
        }
    }
}