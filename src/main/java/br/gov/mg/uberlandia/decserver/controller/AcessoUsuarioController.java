package br.gov.mg.uberlandia.decserver.controller;

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

    @ApiOperation(value = "Consultar empresas por CPF ou CNPJ do usuário")
    @GetMapping("/consultar-empresas")
    public ResponseEntity<List<EmpresaDTO>> consultarEmpresasPorCpfCnpj(
            @RequestParam(name = "cpfCnpj") String cpfCnpj) {
        try {
            List<EmpresaDTO> empresas = acessoService.consultarEmpresasPorCpfCnpj(cpfCnpj);
            return ResponseEntity.ok(empresas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Verificar a existência do usuário por CPF ou CNPJ")
    @GetMapping("/verificar-usuario")
    public ResponseEntity<AcessoDTO> verificarUsuarioPorCpfCnpj(
            @RequestParam(name = "cpfCnpj") String cpfCnpj) {
        try {
            Object resultado = acessoService.verificarUsuarioPorCpfCnpj(cpfCnpj);
            
            if (resultado instanceof AcessoDTO) {
                AcessoDTO acessoDTO = (AcessoDTO) resultado;
                return ResponseEntity.ok(acessoDTO);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Atualizar dados do usuário por CPF ou CNPJ")
    @PostMapping("/atualizar-usuario")
    public ResponseEntity<String> atualizarUsuarioPorCpfCnpj(
            @RequestBody AtualizacaoAcessoDTO atualizacaoAcessoDTO) {
        try {
            String cpfCnpj = atualizacaoAcessoDTO.getCpfCnpj();
            long nrTelAcesso = atualizacaoAcessoDTO.getNrTelAcesso();
            String dsEmailAcesso = atualizacaoAcessoDTO.getDsEmailAcesso();

            boolean atualizado = acessoService.atualizarUsuario(cpfCnpj, nrTelAcesso, dsEmailAcesso);

            if (atualizado) {
                return ResponseEntity.ok("Dados do usuário atualizados com sucesso.");
            } else {
                return new ResponseEntity<>("Usuário não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
