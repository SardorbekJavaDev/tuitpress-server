package uz.tuit.press.config.filter;

import uz.tuit.press.config.details.CustomUserDetails;
import uz.tuit.press.dto.UserJWTDTO;
import uz.tuit.press.exception.AppBadRequestException;
import uz.tuit.press.service.details.CustomUserDetailsService;
import uz.tuit.press.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (Optional.ofNullable(header).isEmpty() || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

            UserJWTDTO jwtDTO = JwtUtil.decode(token);

            String email = jwtDTO.getEmail();
            CustomUserDetails details = customUserDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(details,
                    null, details.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ArrayIndexOutOfBoundsException | AppBadRequestException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);

    }
}
