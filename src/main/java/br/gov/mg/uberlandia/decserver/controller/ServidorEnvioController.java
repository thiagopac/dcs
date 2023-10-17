package br.gov.mg.uberlandia.decserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import br.gov.mg.uberlandia.decserver.dto.EnvioDTO;
import br.gov.mg.uberlandia.decserver.dto.ServidorDTO;
import br.gov.mg.uberlandia.decserver.service.EnvioService;
import br.gov.mg.uberlandia.decserver.service.ServidorService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/servidor-envio")
public class ServidorEnvioController {

    @Autowired
    private ServidorService servidorService;

    @Autowired
    private EnvioService envioService;

    private static Logger logger = LoggerFactory.getLogger(AcessoEnvioController.class);

    @ApiOperation(value = "Consultar servidores da Secretaria por CPF do servidor")
    @GetMapping("/consultar-servidores-secretaria")
    public ResponseEntity<List<ServidorDTO>> listarServidoresPorCpfECodigoSecretaria(
        @RequestParam(name = "cpf") String cpf) {
        try {
            ServidorDTO servidorDTO = servidorService.encontrarServidorPorCpf(cpf);
            if (servidorDTO != null) {

                if (servidorDTO.getIdCargo() == 1) {
                    List<ServidorDTO> servidores = servidorService.listarServidoresPorIdSecretaria(servidorDTO.getIdSecretaria());
                    return ResponseEntity.ok(servidores);    
                } else {
                    List<ServidorDTO> servidorUnico = new ArrayList<>();
                    servidorUnico.add(servidorDTO);
                    return ResponseEntity.ok(servidorUnico);    
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (ServiceException e) {
            logger.error("Erro ao listar servidores da Secretaria", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Listar envios de servidores")
    @GetMapping("/listar-envios-servidor")
    public ResponseEntity<List<EnvioDTO>> listarEnviosDeServidorPorCpf(
            @RequestParam(name = "cpf") String cpf) {
        try {
            ServidorDTO servidorDTO = servidorService.encontrarServidorPorCpf(cpf);
            if (servidorDTO != null) {
                if (servidorDTO.getIdCargo() == 1) {
                    List<ServidorDTO> servidores = servidorService.listarServidoresPorIdSecretaria(servidorDTO.getIdSecretaria());
                    List<Long> idServidores = servidores.stream().map(ServidorDTO::getOidServidor).collect(Collectors.toList());
                    List<EnvioDTO> envios = envioService.listarEnviosPorIdServidores(idServidores);
                    return ResponseEntity.ok(envios);
                } else {
                    List<EnvioDTO> envios = envioService.listarEnviosPorIdServidor(servidorDTO.getOidServidor());
                    return ResponseEntity.ok(envios);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (ServiceException e) {
            logger.error("Erro ao listar envios de servidores", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
