package br.gov.mg.uberlandia.decserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.gov.mg.uberlandia.decserver.dto.EnvioDTO;
import br.gov.mg.uberlandia.decserver.dto.EnviosNaoLidosDTO;
import br.gov.mg.uberlandia.decserver.entity.EnviosEntity;
import br.gov.mg.uberlandia.decserver.repository.EnviosRepository;

@Service
public class EnvioService {

    @Autowired
    private EnviosRepository enviosRepository;

    public List<EnviosNaoLidosDTO> consultarEmpresasNaoLidosPorCpfCnpj(String cpfCnpj) {
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

            return empresasNaoLidos;
        } catch (Exception e) {
            throw new ServiceException("Erro ao consultar empresas com envios não lidos.", e);
        }
    }

    public Page<EnvioDTO> listarEnviosPorIdEmpresa(long idEmpresa, Long status, Pageable pageable) {
        try {
            Page<EnviosEntity> enviosEntitiesPage = enviosRepository.listarEnviosPorIdEmpresa(idEmpresa, status, pageable);
            List<EnvioDTO> enviosParaEmpresa = new ArrayList<>();
            
            for (EnviosEntity envioEntity : enviosEntitiesPage) {
                
                Long oidEnvio = envioEntity.getOidEnvio();
                Long _idEmpresa = envioEntity.getIdEmpresa();
                Long tpEnvio = envioEntity.getTpEnvio();
                Date dtHrEnvio = envioEntity.getDtHrEnvio();
                Long qtDiasCiencia = envioEntity.getQtDiasCiencia();
                String dsTituloEnvio = envioEntity.getDsTituloEnvio();
                String dsComunicEnvio = envioEntity.getDsComunicEnvio();
                String usuConfigEnvio = envioEntity.getUsuConfigEnvio();
                Date dtHrConfigEnvio = envioEntity.getDtHrConfigEnvio();
                String cpfCnpjEnvio = envioEntity.getCpfCnpjEnvio();
                Long statusEnvio = envioEntity.getStatusEnvio();
                String dsUsuAlter = envioEntity.getDsUsuAlter();
                Date dtUltAlter = envioEntity.getDtUltAlter();
                Long vsVersao = envioEntity.getVsVersao();
    
                EnvioDTO envioDTO = new EnvioDTO(
                    oidEnvio,
                    _idEmpresa,
                    tpEnvio,
                    dtHrEnvio,
                    qtDiasCiencia,
                    dsTituloEnvio,
                    dsComunicEnvio,
                    usuConfigEnvio,
                    dtHrConfigEnvio,
                    cpfCnpjEnvio,
                    statusEnvio,
                    dsUsuAlter,
                    dtUltAlter,
                    vsVersao
                );

                enviosParaEmpresa.add(envioDTO);
            }
            
            return new PageImpl<>(enviosParaEmpresa, pageable, enviosEntitiesPage.getTotalElements());
        } catch (Exception e) {
            throw new ServiceException("Erro ao listar envios por ID da empresa.", e);
        }
    }

    public EnvioDTO mostrarEnvioPorId(long idEnvio) {
        try {
            EnviosEntity envioEntity = enviosRepository.findById(idEnvio).orElse(null);

            if (envioEntity != null) {
                return converterParaEnvioDTO(envioEntity);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar envio por ID.", e);
        }
    }

    private EnvioDTO converterParaEnvioDTO(EnviosEntity envioEntity) {
        return new EnvioDTO(
            envioEntity.getOidEnvio(),
            envioEntity.getIdEmpresa(),
            envioEntity.getTpEnvio(),
            envioEntity.getDtHrEnvio(),
            envioEntity.getQtDiasCiencia(),
            envioEntity.getDsTituloEnvio(),
            envioEntity.getDsComunicEnvio(),
            envioEntity.getUsuConfigEnvio(),
            envioEntity.getDtHrConfigEnvio(),
            envioEntity.getCpfCnpjEnvio(),
            envioEntity.getStatusEnvio(),
            envioEntity.getDsUsuAlter(),
            envioEntity.getDtUltAlter(),
            envioEntity.getVsVersao()
        );
    }
}
