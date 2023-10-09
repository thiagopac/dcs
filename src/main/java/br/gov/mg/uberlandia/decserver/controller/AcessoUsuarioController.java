package br.gov.mg.uberlandia.decserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

}
