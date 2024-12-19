package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var authHeader = request.getHeader("Authorization");//.replace("Bearer ", "");
//        if (token == null || token == ""){
//            throw new RuntimeException("El token enviado no es válido.");
//        }
        if (authHeader != null){

            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token);
            if (nombreUsuario != null){
                //token valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities()); //forzamos un inicio de sesión
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
