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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.gov.mg.uberlandia.decserver.dto.EmpresaDTO;
import br.gov.mg.uberlandia.decserver.dto.EnvioDTO;
import br.gov.mg.uberlandia.decserver.dto.EnviosNaoLidosDTO;
import br.gov.mg.uberlandia.decserver.dto.ServidorDTO;
import br.gov.mg.uberlandia.decserver.entity.AcessosEntity;
import br.gov.mg.uberlandia.decserver.entity.EmpresasEntity;
import br.gov.mg.uberlandia.decserver.entity.EnviosEntity;
import br.gov.mg.uberlandia.decserver.entity.RelServidoresEntity;
import br.gov.mg.uberlandia.decserver.repository.AcessosRepository;
import br.gov.mg.uberlandia.decserver.repository.EmpresasRepository;
import br.gov.mg.uberlandia.decserver.repository.EnviosRepository;
import br.gov.mg.uberlandia.decserver.repository.RelServidoresRepository;

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

    public List<EnviosNaoLidosDTO> consultarEmpresasNaoLidosPorCpfCnpj(String cpfCnpj) {
        try {
            List<EmpresaDTO> empresasAcesso = acessoService.consultarEmpresasPorCpfCnpj(cpfCnpj);
            Long totalMassNaoLidos = enviosRepository.countTotalMassNaoLidos();
            List<EnviosNaoLidosDTO> empresasNaoLidos = new ArrayList<>();
            Set<Long> empresasContabilizadas = new HashSet<>();

            for (EmpresaDTO empresa : empresasAcesso) {
                Long oidEmpresa = empresa.getOidEmpresa();
                List<Object[]> resultados = enviosRepository.countNaoLidosPorEmpresa(cpfCnpj, oidEmpresa);
                
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

            EnviosEntity enviosEntity = enviosRepository.findByIdEnvio(idEnvio);

            AcessosEntity acessosEntity = acessosRepository.findByCpfCnpjAcessoAndIdEmpresa(cpfCnpjAcesso, enviosEntity.getIdEmpresa());

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

    @Transactional
    public EnvioDTO criarEnvio(EnvioDTO novoEnvioDTO) {
        try {

            String cpfCnpjEnvio = novoEnvioDTO.getCpfCnpjEnvio();
            EmpresasEntity empresa = new EmpresasEntity();

            if (cpfCnpjEnvio != null && !cpfCnpjEnvio.equals("0")) {
                empresa = empresasRepository.findByCpfCnpjEmpresa(cpfCnpjEnvio);
                if (empresa == null) {
                    throw new RuntimeException("Empresa não encontrada com o CPF/CNPJ fornecido: " + cpfCnpjEnvio);
                }
            } else {
                empresa = new EmpresasEntity();
                empresa.setOidEmpresa(0L);
            }

            String cpfServidor = novoEnvioDTO.getCpfServidor();
            RelServidoresEntity servidor = relServidoresRepository.findByNrCpfServidor(cpfServidor);
            if (servidor == null) {
                throw new RuntimeException("Servidor não encontrado com o CPF fornecido: " + cpfServidor);
            }

            EnviosEntity envioEntity = new EnviosEntity();
            envioEntity.setDtHrEnvio(new Date());
            envioEntity.setDsTituloEnvio(novoEnvioDTO.getDsTituloEnvio());
            envioEntity.setDsComunicEnvio(novoEnvioDTO.getDsComunicEnvio());
            envioEntity.setStatusEnvio(0L);
            envioEntity.setQtDiasCiencia(novoEnvioDTO.getQtDiasCiencia());
            envioEntity.setTpEnvio(novoEnvioDTO.getTpEnvio());
            envioEntity.setUsuConfigEnvio(servidor.getNrCpfServidor());
            envioEntity.setDtHrConfigEnvio(novoEnvioDTO.getDtHrConfigEnvio());
            envioEntity.setCpfCnpjEnvio(novoEnvioDTO.getCpfCnpjEnvio());
            envioEntity.setDsUsuAlter(servidor.getNmServidor());
            envioEntity.setDtUltAlter(new Date());
            envioEntity.setVsVersao(1L);
            envioEntity.setNrProtocolo(novoEnvioDTO.getNrProtocolo());
            envioEntity.setIdSecretaria(servidor.getIdSecretaria());
            envioEntity.setIdEmpresa(empresa.getOidEmpresa());

            envioEntity = enviosRepository.save(envioEntity);

            return convertToEnvioDTO(envioEntity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar envio.", e);
        }
    }


}
