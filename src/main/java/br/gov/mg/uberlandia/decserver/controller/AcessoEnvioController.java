package br.gov.mg.uberlandia.decserver.controller;

import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.gov.mg.uberlandia.decserver.dto.EnviosNaoLidosDTO;
import br.gov.mg.uberlandia.decserver.dto.EnvioDTO;
import br.gov.mg.uberlandia.decserver.service.EnvioService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/acesso-envio")
public class AcessoEnvioController {

    @Autowired
    private EnvioService envioService;

    private static Logger logger = LoggerFactory.getLogger(AcessoEnvioController.class);
    

    @ApiOperation(value = "Consultar empresas com envios não lidos")
    @GetMapping("/consultar-empresas-nao-lidos")
    public ResponseEntity<List<EnviosNaoLidosDTO>> consultarEmpresasNaoLidosPorCpfCnpj(
            @RequestParam(name = "cpfCnpj") String cpfCnpj) {
        try {
            List<EnviosNaoLidosDTO> empresasNaoLidos = envioService.consultarEmpresasNaoLidosPorCpfCnpj(cpfCnpj);
            return ResponseEntity.ok(empresasNaoLidos);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    @ApiOperation(value = "Listar envios por empresa")
    @GetMapping("/listar-envios-empresa")
    public ResponseEntity<List<EnvioDTO>> listarEnviosPorIdEmpresa(
            @RequestParam(name = "idEmpresa") long idEmpresa) {
        try {
            List<EnvioDTO> enviosParaEmpresa = envioService.listarEnviosPorIdEmpresa(idEmpresa);
            return ResponseEntity.ok(enviosParaEmpresa);
        } catch (ServiceException e) {
            logger.error("Erro ao listar envios por empresa", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Mostrar dados de um envio por ID")
    @GetMapping("/mostrar-envio")
    public ResponseEntity<EnvioDTO> mostrarEnvioPorId(
            @RequestParam(name = "idEnvio") long idEnvio) {
        try {
            EnvioDTO envio = envioService.mostrarEnvioPorId(idEnvio);

            if (envio != null) {
                return ResponseEntity.ok(envio);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ServiceException e) {
            logger.error("Erro ao mostrar envio por ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}