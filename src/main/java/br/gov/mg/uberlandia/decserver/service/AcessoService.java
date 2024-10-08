package br.gov.mg.uberlandia.decserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.gov.mg.uberlandia.decserver.dto.AcessoDTO;
import br.gov.mg.uberlandia.decserver.dto.EmpresaDTO;
import br.gov.mg.uberlandia.decserver.entity.AcessosEntity;
import br.gov.mg.uberlandia.decserver.entity.EmpresasEntity;
import br.gov.mg.uberlandia.decserver.entity.siat.PessoasEntity;
import br.gov.mg.uberlandia.decserver.repository.AcessosRepository;
import br.gov.mg.uberlandia.decserver.repository.EmpresasRepository;
import br.gov.mg.uberlandia.decserver.repository.siat.PessoasRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class AcessoService {

    @Autowired
    private AcessosRepository acessoRepository;

    @Autowired
    private PessoasRepository pessoasRepository;

    @Autowired
    private EmpresasRepository empresasRepository;

    public List<EmpresaDTO> consultarEmpresasPorCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.isEmpty()) {
            throw new IllegalArgumentException("CPF/CNPJ não pode ser nulo ou vazio");
        }

        Long cpfCnpjLong = Long.parseLong(cpfCnpj);
    
        List<EmpresasEntity> empresasList = empresasRepository.findEmpresasByCpfCnpjAcesso(cpfCnpjLong);
    
        List<EmpresaDTO> empresasDTOList = new ArrayList<>();
        for (EmpresasEntity empresaEntity : empresasList) {
            EmpresaDTO empresaDTO = new EmpresaDTO();
            empresaDTO.setOidEmpresa(empresaEntity.getOidEmpresa());
            empresaDTO.setNmEmpresa(empresaEntity.getNmEmpresa());
            empresaDTO.setCnpjEmpresa(empresaEntity.getCnpjEmpresa());
            empresaDTO.setNrTelEmpresa(empresaEntity.getNrTelEmpresa());
            empresaDTO.setDsEmailEmpresa(empresaEntity.getDsEmailEmpresa());
            empresasDTOList.add(empresaDTO);
        }
    
        return empresasDTOList;
    }

    public Object verificarUsuarioPorCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null || cpfCnpj.length() < 2) {
            return null;
        }

        Long cpfCnpjLong = Long.parseLong(cpfCnpj);
    
        List<AcessosEntity> acessos = acessoRepository.findByCpfCnpjAcesso(cpfCnpjLong);
    
        if (!acessos.isEmpty()) {
            return null;
        }
    
        PessoasEntity pessoa = pessoasRepository.findByCpfCnpj(cpfCnpj);
    
        if (pessoa != null) {
            return new AcessoDTO(pessoa.getNomRazCom(), cpfCnpjLong);
        } else {
            return "CPF/CNPJ não encontrado em nossa base de dados";
        }
    }
    
    @Transactional
    public boolean atualizarUsuario(String cpfCnpj, String nmAcesso, long nrTelAcesso, String dsEmailAcesso) {
        if (cpfCnpj == null || nmAcesso == null || dsEmailAcesso == null) {
            return false;
        }

        if (cpfCnpj.length() < 2) {
            return false;
        }

        if (nrTelAcesso < 0) {
            return false;
        }

        Long cpfCnpjLong = Long.parseLong(cpfCnpj);

        int rowsUpdated = acessoRepository.updateNmTelAndEmailByCpfCnpjAcesso(cpfCnpjLong, nmAcesso, nrTelAcesso, dsEmailAcesso);

        return rowsUpdated > 0;
    }

    @Transactional
    public boolean insertAcesso(Long cpfCnpj, String nmAcesso, long nrTelAcesso, String dsEmailAcesso) {
        try {
            if (cpfCnpj == null || nmAcesso == null || dsEmailAcesso == null) {
                return false;
            }

            AcessosEntity acesso = new AcessosEntity();
            acesso.setCpfCnpjAcesso(cpfCnpj);
            acesso.setNmAcesso(dsEmailAcesso);
            acesso.setNrTelAcesso(nrTelAcesso);
            acesso.setDsEmailAcesso(dsEmailAcesso);
            acesso.setIdEmpresa(0L);
            acesso.setStatusAcesso(1L);
            acesso.setDsUsuAlter(String.valueOf(cpfCnpj));

            acessoRepository.save(acesso);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
