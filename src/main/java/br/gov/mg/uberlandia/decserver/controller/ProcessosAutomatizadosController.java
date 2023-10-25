package br.gov.mg.uberlandia.decserver.controller;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.gov.mg.uberlandia.decserver.dto.ErrorMessage;
import br.gov.mg.uberlandia.decserver.service.EnvioService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/processos-automatizados")
public class ProcessosAutomatizadosController {

    @Autowired
    private EnvioService envioService;

    private static Logger logger = LoggerFactory.getLogger(ProcessosAutomatizadosController.class);

    @ApiOperation(value = "Faz o envio de comunicações que estão programadas")
    @PostMapping("/executar-envios")
    public ResponseEntity<?> ExecutarEnvios() {
        try {
            envioService.executarEnviosProgramados();

            Map<String, String> response = new HashMap<>();
            response.put("message", "Envios programados foram executados com sucesso");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ServiceException e) {
            logger.error("Erro ao executar envios", e);
            
            ErrorMessage errorMessage = new ErrorMessage("INTERNAL_SERVER_ERROR", "Erro interno do servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @ApiOperation(value = "Marca como lidos os envios a partir da quantidade de dias de ciência")
    @PostMapping("/marcar-envios-lidos")
    public ResponseEntity<?> marcarEnviosLidos() {
        try {
            envioService.marcarEnviosComoLidos();

            Map<String, String> response = new HashMap<>();
            response.put("message", "Envios foram marcados como lidos com sucesso");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ServiceException e) {
            logger.error("Erro ao marcar envios como lidos", e);
            
            ErrorMessage errorMessage = new ErrorMessage("INTERNAL_SERVER_ERROR", "Erro interno do servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }



}
