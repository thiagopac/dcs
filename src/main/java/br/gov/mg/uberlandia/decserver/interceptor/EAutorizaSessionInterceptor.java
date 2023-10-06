package br.gov.mg.uberlandia.decserver.interceptor;

import br.gov.mg.uberlandia.decserver.dto.InformacoesTokenDTO;
import br.gov.mg.uberlandia.decserver.utils.JacksonUtils;
import br.gov.mg.uberlandia.decserver.utils.UsuarioTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Component
public class EAutorizaSessionInterceptor extends HandlerInterceptorAdapter {

    private static String[] publicUriPatterns = {
            "/versao",
            "/csrf",
            "/error",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Value("${server.servlet.context-path}")
    private String urlPadrao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      /*  if (isPublicRequest(request)) {
            return super.preHandle(request, response, handler);
        }

        String token = request.getHeader("X-Session-Data");
        if (token == null || "".equals(token)) {
            response.sendError(401);
            return false;
        }

        try {
            if (!isTokenValid(token, request)) {
                response.sendError(401);
                return false;
            }
        } catch (Exception e) {
            log.warn("Erro ao validar o token " + token + " causado por " + e + " - " + e.getMessage(), e);
            response.sendError(401);
            return false;
        }

        return super.preHandle(request, response, handler);*/
        return true;
    }

    private boolean isTokenValid(String token, HttpServletRequest request) {
        String[] decoded = UsuarioTokenUtils.decode(token);
        String validity = decoded[1];

        InformacoesTokenDTO info = JacksonUtils.parseJson(decoded[0], InformacoesTokenDTO.class);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(info, null, Collections.emptyList()));

        boolean temPermissao = requestMatches(request, info.getPermissoes());

        LocalDateTime parse = LocalDateTime.parse(validity);

        return temPermissao && LocalDateTime.now().isBefore(parse);
    }

    private boolean isPublicRequest(HttpServletRequest request) {
        return requestMatches(request, publicUriPatterns);
    }

    private boolean requestMatches(HttpServletRequest request, String[] _patterns) {
        return Optional.ofNullable(_patterns)
                .map(patterns -> Stream.of(patterns)
                        .anyMatch(pattern -> {
                            String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
                            return ((padronizaUrls("/" + pattern)).toLowerCase()).equals(padronizaUrls(bestMatchPattern).toLowerCase());
                        })
                ).orElse(false);
    }

    private String padronizaUrls(String url) {
        url = url.replaceAll("//", "/");
        String array[] = url.split("\\/\\{");
        return array[0].toLowerCase().trim();
    }

}