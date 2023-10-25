package br.gov.mg.uberlandia.decserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.gov.mg.uberlandia.decserver.dto.EmpresaDTO;
import br.gov.mg.uberlandia.decserver.dto.EnvioDTO;
import br.gov.mg.uberlandia.decserver.dto.EnviosNaoLidosDTO;
import br.gov.mg.uberlandia.decserver.entity.AcessosEntity;
import br.gov.mg.uberlandia.decserver.entity.EmpresasEntity;
import br.gov.mg.uberlandia.decserver.entity.EnviosEntity;
import br.gov.mg.uberlandia.decserver.entity.RelServidoresEntity;
import br.gov.mg.uberlandia.decserver.entity.TmpEnviosEntity;
import br.gov.mg.uberlandia.decserver.repository.AcessosRepository;
import br.gov.mg.uberlandia.decserver.repository.EmpresasRepository;
import br.gov.mg.uberlandia.decserver.repository.EnviosRepository;
import br.gov.mg.uberlandia.decserver.repository.RelServidoresRepository;
import br.gov.mg.uberlandia.decserver.repository.TmpEnviosRepository;

@Service
public class EnvioService {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private EnviosRepository enviosRepository;

    @Autowired
    private AcessosRepository acessosRepository;

    @Autowired
    private RelServidoresRepository relServidoresRepository;

    @Autowired
    private EmpresasRepository empresasRepository;

    @Autowired
    private TmpEnviosRepository tmpEnviosRepository;

    public List<EnviosNaoLidosDTO> consultarEmpresasNaoLidosPorCpfCnpj(String cpfCnpj) {
        try {
            
            Long cpfCnpjLong = Long.parseLong(cpfCnpj);

            List<EmpresaDTO> empresasAcesso = acessoService.consultarEmpresasPorCpfCnpj(cpfCnpj);
            Long totalMassNaoLidos = enviosRepository.countTotalMassNaoLidos();
            List<EnviosNaoLidosDTO> empresasNaoLidos = new ArrayList<>();
            Set<Long> empresasContabilizadas = new HashSet<>();

            for (EmpresaDTO empresa : empresasAcesso) {
                Long oidEmpresa = empresa.getOidEmpresa();
                List<Object[]> resultados = enviosRepository.countNaoLidosPorEmpresa(cpfCnpjLong, oidEmpresa);
                
                for (Object[] resultado : resultados) {
                    Long idEmpresa = (Long) resultado[0];
                    Long quantidadeNaoLidos = (Long) resultado[6] + totalMassNaoLidos;
                    
                    if (quantidadeNaoLidos > 0) {

                        empresasNaoLidos.add(new EnviosNaoLidosDTO(idEmpresa, oidEmpresa, empresa.getNmEmpresa(), empresa.getCnpjEmpresa(), empresa.getNrTelEmpresa(), empresa.getDsEmailEmpresa(), quantidadeNaoLidos));
                        empresasContabilizadas.add(oidEmpresa);
                    }
                }
            }

            for (EmpresaDTO empresa : empresasAcesso) {
                Long oidEmpresa = empresa.getOidEmpresa();
                if (!empresasContabilizadas.contains(oidEmpresa)) {
                    if (totalMassNaoLidos > 0) {
                        empresasNaoLidos.add(new EnviosNaoLidosDTO(null, oidEmpresa, empresa.getNmEmpresa(), empresa.getCnpjEmpresa(), empresa.getNrTelEmpresa(), empresa.getDsEmailEmpresa(), totalMassNaoLidos));
                    }
                }
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
                Date dtHrEnvio = envioEntity.getDtHrEnvio();
                String dsTituloEnvio = envioEntity.getDsTituloEnvio();
                Long statusEnvio = envioEntity.getStatusEnvio();

                EnvioDTO envioDTO = new EnvioDTO();
    
                envioDTO.setOidEnvio(oidEnvio);
                envioDTO.setDtHrEnvio(dtHrEnvio);
                envioDTO.setDsTituloEnvio(dsTituloEnvio);
                envioDTO.setStatusEnvio(statusEnvio);
                
                enviosParaEmpresa.add(envioDTO);
            }
            
            return new PageImpl<>(enviosParaEmpresa, pageable, enviosEntitiesPage.getTotalElements());
        } catch (Exception e) {
            throw new ServiceException("Erro ao listar envios por ID da empresa.", e);
        }
    }

    @Transactional
    public EnvioDTO mostrarEnvioContribuintePorId(long idEnvio, String cpfCnpjAcesso) {
        try {

            Long cpfCnpjAcessoLong = Long.parseLong(cpfCnpjAcesso);

            EnviosEntity enviosEntity = enviosRepository.findByIdEnvio(idEnvio);

            AcessosEntity acessosEntity = acessosRepository.findByCpfCnpjAcessoAndIdEmpresa(cpfCnpjAcessoLong, enviosEntity.getIdEmpresa());

            if (acessosEntity != null && enviosEntity.getTpEnvio() == 1) {
                enviosRepository.atualizarStatusEnvioSeNecessario(idEnvio, 1, acessosEntity.getNmAcesso(), new Date());
            }

            EnvioDTO envioDTO = new EnvioDTO(); 
            envioDTO.setOidEnvio(enviosEntity.getOidEnvio());
            envioDTO.setDtHrEnvio(enviosEntity.getDtHrEnvio());
            envioDTO.setDsTituloEnvio(enviosEntity.getDsTituloEnvio());
            envioDTO.setDsComunicEnvio(enviosEntity.getDsComunicEnvio());
            envioDTO.setStatusEnvio(enviosEntity.getStatusEnvio());
            envioDTO.setQtDiasCiencia(enviosEntity.getQtDiasCiencia());
            
            return envioDTO;
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar envio por ID.", e);
        }
    }

    public EnvioDTO mostrarEnvioPmuPorId(long idEnvio, String cpf) {
        try {
            EnviosEntity enviosEntity = enviosRepository.findByIdEnvio(idEnvio);
            EnvioDTO envioDTO = this.convertToEnvioDTO(enviosEntity);
            
            return envioDTO;
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar envio por ID.", e);
        }
    }

    public Page<EnvioDTO> listarEnviosPorIdServidores(List<Long> idServidores, Long status, Pageable pageable) {
        List<String> cpfsServidores = relServidoresRepository.findCpfByOidServidorIn(idServidores);
        Page<EnviosEntity> enviosPage = enviosRepository.findByUsuConfigEnvioInAndStatusEnvio(cpfsServidores, status, pageable);
        return enviosPage.map(this::convertToEnvioDTO);
    }

    private EnvioDTO convertToEnvioDTO(EnviosEntity envioEntity) {
        if (envioEntity != null) {
            EnvioDTO envioDTO = new EnvioDTO();
            envioDTO.setOidEnvio(envioEntity.getOidEnvio());
            envioDTO.setDtHrEnvio(envioEntity.getDtHrEnvio());
            envioDTO.setDsTituloEnvio(envioEntity.getDsTituloEnvio());
            envioDTO.setDsComunicEnvio(envioEntity.getDsComunicEnvio());
            envioDTO.setStatusEnvio(envioEntity.getStatusEnvio());
            envioDTO.setQtDiasCiencia(envioEntity.getQtDiasCiencia());
            envioDTO.setTpEnvio(envioEntity.getTpEnvio());
            envioDTO.setUsuConfigEnvio(envioEntity.getUsuConfigEnvio());
            envioDTO.setDtHrConfigEnvio(envioEntity.getDtHrConfigEnvio());
            envioDTO.setCpfCnpjEnvio(envioEntity.getCpfCnpjEnvio());
            envioDTO.setDsUsuAlter(envioEntity.getDsUsuAlter());
            envioDTO.setDtUltAlter(envioEntity.getDtUltAlter());
            envioDTO.setVsVersao(envioEntity.getVsVersao());
            envioDTO.setNrProtocolo(envioEntity.getNrProtocolo());
            envioDTO.setIdSecretaria(envioEntity.getIdSecretaria());
            
            return envioDTO;
        } else {
            return null;
        }
    }

    private EnvioDTO convertToEnvioDTO(TmpEnviosEntity tmpEnvioEntity) {
        if (tmpEnvioEntity != null) {
            EnvioDTO envioDTO = new EnvioDTO();
            envioDTO.setDsTituloEnvio(tmpEnvioEntity.getDsTituloEnvio());
            envioDTO.setSituacEnvio(tmpEnvioEntity.getSituacEnvio());
            envioDTO.setDsComunicEnvio(tmpEnvioEntity.getDsComunicEnvio());
            envioDTO.setStatusEnvio(tmpEnvioEntity.getStatusEnvio());
            envioDTO.setQtDiasCiencia(tmpEnvioEntity.getQtDiasCiencia());
            envioDTO.setTpEnvio(tmpEnvioEntity.getTpEnvio());
            envioDTO.setUsuConfigEnvio(tmpEnvioEntity.getUsuConfigEnvio());
            envioDTO.setDtHrConfigEnvio(tmpEnvioEntity.getDtHrConfigEnvio());
            envioDTO.setCpfCnpjEnvio(tmpEnvioEntity.getCpfCnpjEnvio());
            envioDTO.setDsUsuAlter(tmpEnvioEntity.getDsUsuAlter());
            envioDTO.setDtUltAlter(tmpEnvioEntity.getDtUltAlter());
            envioDTO.setVsVersao(tmpEnvioEntity.getVsVersao());
            envioDTO.setNrProtocolo(tmpEnvioEntity.getNrProtocolo());
            envioDTO.setIdSecretaria(tmpEnvioEntity.getIdSecretaria());
            
            return envioDTO;
        } else {
            return null;
        }
    }

    @Transactional
    public EnvioDTO criarEnvio(EnvioDTO novoEnvioDTO) {
        try {

            Long cpfCnpjEnvio = novoEnvioDTO.getCpfCnpjEnvio();
            String cpfServidor = novoEnvioDTO.getCpfServidor();
            Long cpfServidorLong = Long.parseLong(cpfServidor);
            EmpresasEntity empresa = new EmpresasEntity();

            if (cpfCnpjEnvio > 0) {
                empresa = empresasRepository.findByCpfCnpjEmpresa(cpfCnpjEnvio);
                if (empresa == null) {
                    throw new RuntimeException("Empresa não encontrada com o CPF/CNPJ fornecido: " + cpfCnpjEnvioLong);
                }
            } else {
                empresa = new EmpresasEntity();
                empresa.setOidEmpresa(0L);
            }

            RelServidoresEntity servidor = relServidoresRepository.findByNrCpfServidor(cpfServidorLong);
            if (servidor == null) {
                throw new RuntimeException("Servidor não encontrado com o CPF fornecido: " + cpfServidor);
            }

            TmpEnviosEntity tmpEnvioEntity = new TmpEnviosEntity();
            tmpEnvioEntity.setDsTituloEnvio(novoEnvioDTO.getDsTituloEnvio());
            tmpEnvioEntity.setDsComunicEnvio(novoEnvioDTO.getDsComunicEnvio());
            tmpEnvioEntity.setStatusEnvio(0L);
            tmpEnvioEntity.setQtDiasCiencia(novoEnvioDTO.getQtDiasCiencia());
            tmpEnvioEntity.setTpEnvio(novoEnvioDTO.getTpEnvio());
            tmpEnvioEntity.setUsuConfigEnvio(cpfServidor);
            tmpEnvioEntity.setDtHrConfigEnvio(novoEnvioDTO.getDtHrConfigEnvio());
            tmpEnvioEntity.setCpfCnpjEnvio(novoEnvioDTO.getCpfCnpjEnvio());
            tmpEnvioEntity.setDsUsuAlter(servidor.getNmServidor());
            tmpEnvioEntity.setDtUltAlter(new Date());
            tmpEnvioEntity.setVsVersao(0L);
            tmpEnvioEntity.setNrProtocolo(novoEnvioDTO.getNrProtocolo());
            tmpEnvioEntity.setIdSecretaria(servidor.getIdSecretaria());
            tmpEnvioEntity.setIdEmpresa(empresa.getOidEmpresa());
            tmpEnvioEntity.setSituacEnvio(0L);

            tmpEnvioEntity = tmpEnviosRepository.save(tmpEnvioEntity);

            return convertToEnvioDTO(tmpEnvioEntity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar envio.", e);
        }
    }

    @Transactional
    public void executarEnviosProgramados() {
        try {
            StringBuilder log = new StringBuilder("Envios programados processados: ");

            List<TmpEnviosEntity> enviosProgramados = tmpEnviosRepository.findEnviosProgramados();

            this.limparEnviosExecutados();
            log.append("Registros enviados anteriormente foram excluídos.");
            
            
            for (TmpEnviosEntity envioProgramado : enviosProgramados) {
                try {
                    EnviosEntity novoEnvio = new EnviosEntity();
                    novoEnvio.setIdEmpresa(envioProgramado.getIdEmpresa());
                    novoEnvio.setTpEnvio(envioProgramado.getTpEnvio());
                    novoEnvio.setQtDiasCiencia(envioProgramado.getQtDiasCiencia());
                    novoEnvio.setDsTituloEnvio(envioProgramado.getDsTituloEnvio());
                    novoEnvio.setDsComunicEnvio(envioProgramado.getDsComunicEnvio());
                    novoEnvio.setUsuConfigEnvio(envioProgramado.getUsuConfigEnvio());
                    novoEnvio.setDtHrConfigEnvio(envioProgramado.getDtHrConfigEnvio());
                    novoEnvio.setCpfCnpjEnvio(envioProgramado.getCpfCnpjEnvio());
                    novoEnvio.setDsUsuAlter(envioProgramado.getDsUsuAlter());
                    novoEnvio.setStatusEnvio(0L);
                    novoEnvio.setNrProtocolo(envioProgramado.getNrProtocolo());
                    novoEnvio.setIdSecretaria(envioProgramado.getIdSecretaria());
                    novoEnvio.setVsVersao(0L);
                    novoEnvio.setDtUltAlter(new Date());
                    novoEnvio.setDtHrEnvio(new Date());
                    
                    enviosRepository.save(novoEnvio);
                    
                    envioProgramado.setSituacEnvio(1L);
                    tmpEnviosRepository.save(envioProgramado);
                    
                    log.append("Envio DtUltAlter: ").append(envioProgramado.getDtUltAlter()).append(", ");
                } catch (Exception ex) {
                    log.append("Erro no processamento do envio com DtUltAlter: ").append(envioProgramado.getDtUltAlter()).append(", ");
                }
            }
            
        } catch (Exception e) {
            throw new ServiceException("Erro ao executar envios programados.", e);
        }
    }

    @Transactional
    public void limparEnviosExecutados() {
        try {
            List<TmpEnviosEntity> enviosEnviados = tmpEnviosRepository.findEnviosExecutados();
            tmpEnviosRepository.deleteAll(enviosEnviados);
        } catch (Exception e) {
            throw new ServiceException("Erro ao limpar envios enviados.", e);
        }
    }


}
