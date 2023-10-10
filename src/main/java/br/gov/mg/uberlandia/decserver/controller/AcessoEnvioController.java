package br.gov.mg.uberlandia.decserver.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.gov.mg.uberlandia.decserver.dto.EnviosNaoLidosDTO;
import br.gov.mg.uberlandia.decserver.repository.EnviosRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/acesso-envio")
public class AcessoEnvioController {

    @Autowired
    private EnviosRepository enviosRepository;

    @ApiOperation(value = "Consultar empresas com envios não lidos")
    @GetMapping("/consultar-empresas-nao-lidos")
    public ResponseEntity<List<EnviosNaoLidosDTO>> consultarEmpresasNaoLidosPorCpfCnpj(
            @RequestParam(name = "cpfCnpj") String cpfCnpj) {
        try {
            List<Object[]> resultados = enviosRepository.countNaoLidosPorEmpresa(cpfCnpj);
            List<EnviosNaoLidosDTO> empresasNaoLidos = new ArrayList<>();

            for (Object[] resultado : resultados) {
                Long idEmpresa = (Long) resultado[0];
                Long oidEmpresa = (Long) resultado[1];
                String nomeEmpresa = (String) resultado[2];
                String cnpjEmpresa = (String) resultado[3];
                Long nrTelEmpresa = (Long) resultado[4];
                String dsEmailEmpresa = (String) resultado[5];
                Long quantidadeNaoLidos = (Long) resultado[6];
                
                empresasNaoLidos.add(new EnviosNaoLidosDTO(idEmpresa, oidEmpresa, nomeEmpresa, cnpjEmpresa, nrTelEmpresa, dsEmailEmpresa, quantidadeNaoLidos));
            }

            return ResponseEntity.ok(empresasNaoLidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}