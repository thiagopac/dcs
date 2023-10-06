package br.gov.mg.uberlandia.decserver.utils;

import br.gov.mg.uberlandia.decserver.dto.InformacoesTokenDTO;
import br.gov.mg.uberlandia.decserver.exception.BusinessException;
import com.google.gson.Gson;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import javax.servlet.http.HttpServletRequest;

public class UsuarioTokenUtils {

    private static final String SEPARATOR = ";";

    public static String[] decode(String token) {
        Jwt decode = JwtHelper.decode(token);
        String claims = decode.getClaims();
        String[] split = claims.split(SEPARATOR);
        return split;
    }

    public static String getCpf(HttpServletRequest request){
        String token = request.getHeader("X-Session-Data");
        if (token == null) {
            throw new BusinessException("ERRO_AUTENTICACAO", "Erro ao autenticar sessão, token está nulo!");
        }
        InformacoesTokenDTO informacoesTokenDTO = getInformacoesToken(token);
        if(informacoesTokenDTO != null && informacoesTokenDTO.getCpf() != null){
            String cpf = informacoesTokenDTO.getCpf();

            if(cpf.isEmpty()) {
                throw new BusinessException("ERRO_AUTENTICACAO", "Erro ao autenticar sessão, cpf não encontrado!");
            }
            return cpf;
        }
        return null;
    }

    public static Long getOidUsuario(HttpServletRequest request){
        String token = request.getHeader("X-Session-Data");
        if (token == null) {
            throw new BusinessException("ERRO_AUTENTICACAO", "Erro ao autenticar sessão, token está nulo!");
        }
        InformacoesTokenDTO informacoesTokenDTO = getInformacoesToken(token);
        if(informacoesTokenDTO != null && informacoesTokenDTO.getOidUsuario() != null){
            Long oidUsuario = Long.parseLong(informacoesTokenDTO.getOidUsuario());

            if(oidUsuario == null) {
                throw new BusinessException("ERRO_AUTENTICACAO", "Erro ao autenticar sessão, oidUsuario não encontrado!");
            }
            return oidUsuario;
        }
        return null;
    }

    private static InformacoesTokenDTO getInformacoesToken(String token){
        String[] decoded = decode(token);
        Gson gson = new Gson();
        return gson.fromJson(decoded[0], InformacoesTokenDTO.class);
    }

}
