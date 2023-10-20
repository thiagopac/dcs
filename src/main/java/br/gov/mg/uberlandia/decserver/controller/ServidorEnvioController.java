package br.gov.mg.uberlandia.decserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            if (servidorDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (servidorDTO.getIdCargo() == 1) {
                List<ServidorDTO> servidores = servidorService.listarServidoresPorIdSecretaria(servidorDTO.getIdSecretaria());
                return ResponseEntity.ok(servidores);    
            } else {
                List<ServidorDTO> servidorUnico = new ArrayList<>();
                servidorUnico.add(servidorDTO);
                return ResponseEntity.ok(servidorUnico);    
            }
        } catch (ServiceException e) {
            logger.error("Erro ao listar servidores da Secretaria", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Listar envios de servidores")
    @GetMapping("/listar-envios-servidor")
    public ResponseEntity<Page<EnvioDTO>> listarEnviosDeServidorPorCpf(
            @RequestParam(name = "cpf") String cpf,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "oidServidor", required = false) Long oidServidor,
            @RequestParam(name = "status", required = false) Long status) {
        try {
            if (cpf == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            ServidorDTO servidorDTO = servidorService.encontrarServidorPorCpf(cpf);
            if (servidorDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            List<Long> idServidores = new ArrayList<>();
            if (servidorDTO.getIdCargo() == 1) {
                if (oidServidor != null) {
                    ServidorDTO servidorPorOid = servidorService.encontrarServidorPorOidServidor(oidServidor);
                    if (servidorPorOid.getIdSecretaria() != servidorDTO.getIdSecretaria()) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                    } else {
                        idServidores.add(oidServidor);
                    }
                } else {
                    List<ServidorDTO> servidores = servidorService.listarServidoresPorIdSecretaria(servidorDTO.getIdSecretaria());
                    idServidores = servidores.stream().map(ServidorDTO::getOidServidor).collect(Collectors.toList());
                }
            } else {
                idServidores.add(servidorDTO.getOidServidor());
            }

            if (idServidores.isEmpty()) {
                return ResponseEntity.ok(Page.empty());
            }

            return ResponseEntity.ok(envioService.listarEnviosPorIdServidores(idServidores, status, PageRequest.of(page, size)));
        } catch (ServiceException e) {
            logger.error("Erro ao listar envios de servidores", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Mostrar dados de um envio por ID")
    @GetMapping("/mostrar-envio")
    public ResponseEntity<EnvioDTO> mostrarEnvioPorId(
            @RequestParam(name = "idEnvio") long idEnvio,
            @RequestParam(name = "cpf") String cpf) {
        try {

            if (cpf == null || cpf.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            ServidorDTO servidorDTO = servidorService.encontrarServidorPorCpf(cpf);

            if (servidorDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            EnvioDTO envio = envioService.mostrarEnvioPmuPorId(idEnvio, cpf);

            if (envio != null) {
                return ResponseEntity.ok(envio);
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (ServiceException e) {
            logger.error("Erro ao mostrar envio por ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("Erro inesperado ao mostrar envio por ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Criar um novo envio")
    @PostMapping("/criar-envio")
    public ResponseEntity<EnvioDTO> criarEnvio(@RequestBody EnvioDTO novoEnvioDTO) {
        try {
            EnvioDTO envioCriado = envioService.criarEnvio(novoEnvioDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(envioCriado);
        } catch (ServiceException e) {
            logger.error("Erro ao criar envio", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
