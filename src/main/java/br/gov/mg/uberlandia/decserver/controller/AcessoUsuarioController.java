package br.gov.mg.uberlandia.decserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.gov.mg.uberlandia.decserver.dto.AcessoDTO;
import br.gov.mg.uberlandia.decserver.dto.AtualizacaoAcessoDTO;
import br.gov.mg.uberlandia.decserver.dto.EmpresaDTO;
import br.gov.mg.uberlandia.decserver.service.AcessoService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import java.util.List;

@RestController
@Data
@RequestMapping("/acessos-usuario")
public class AcessoUsuarioController {

    @Autowired
    private AcessoService acessoService;

    private static Logger logger = LoggerFactory.getLogger(AcessoEnvioController.class);

    @ApiOperation(value = "Consultar empresas por CPF ou CNPJ do usuário")
    @GetMapping("/consultar-empresas")
    public ResponseEntity<List<EmpresaDTO>> consultarEmpresasPorCpfCnpj(
            @RequestParam(name = "cpfCnpj") String cpfCnpj) {
        try {
            List<EmpresaDTO> empresasDTOList = acessoService.consultarEmpresasPorCpfCnpj(cpfCnpj);
            if (empresasDTOList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(empresasDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Verificar a existência do usuário por CPF ou CNPJ", notes = "Se o usuário existir na base DEC, ele não precisará ser atualizado, então o retorno será em branco com status 204. Se houver objeto retornado, os dados são provenientes do SIAT e as informações do usuário serão retornadas para serem exibidas e os dados preenchidos deverão ser enviados de volta para criar o acesso do usuário.")
    @GetMapping("/verificar-usuario")
    public ResponseEntity<?> verificarUsuarioPorCpfCnpj(
            @RequestParam(name = "cpfCnpj") String cpfCnpj) {
        try {
            Object resultado = acessoService.verificarUsuarioPorCpfCnpj(cpfCnpj);

            if (resultado instanceof AcessoDTO) {
                AcessoDTO acessoDTO = (AcessoDTO) resultado;
                return ResponseEntity.ok(acessoDTO);
            } else if(resultado instanceof String) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            logger.error("Erro ao verificar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Atualizar dados do usuário por CPF ou CNPJ")
    @PostMapping("/atualizar-usuario")
    public ResponseEntity<String> atualizarUsuarioPorCpfCnpj(
            @RequestBody AtualizacaoAcessoDTO atualizacaoAcessoDTO) {
        try {
            String cpfCnpjAcesso = atualizacaoAcessoDTO.getCpfCnpjAcesso();
            String nmAcesso = atualizacaoAcessoDTO.getNmAcesso();
            long nrTelAcesso = atualizacaoAcessoDTO.getNrTelAcesso();
            String dsEmailAcesso = atualizacaoAcessoDTO.getDsEmailAcesso();

            // boolean atualizado = acessoService.atualizarUsuario(cpfCnpjAcesso, nmAcesso, nrTelAcesso, dsEmailAcesso);
            boolean atualizado = acessoService.insertAcesso(cpfCnpjAcesso, nmAcesso, nrTelAcesso, dsEmailAcesso);

            if (atualizado) {
                return ResponseEntity.ok("Dados do usuário atualizados com sucesso.");
            } else {
                return new ResponseEntity<>("Usuário não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Erro ao atualizar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
