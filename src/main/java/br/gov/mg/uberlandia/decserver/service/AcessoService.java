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
        List<EmpresasEntity> empresasList = empresasRepository.findEmpresasByCpfCnpjAcesso(cpfCnpj);
    
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
        int length = cpfCnpj.length();
        
        if (length < 2) {
            return null;
        }
    
        String numeroBase = cpfCnpj.substring(0, length - 2);
        String dvCgcCpfPessoa = cpfCnpj.substring(length - 2);
    
        List<AcessosEntity> acessos = acessoRepository.findByCpfCnpjAcesso(cpfCnpj);
    
        if (!acessos.isEmpty()) {
            return null;
        } else {
            PessoasEntity pessoa = pessoasRepository.findByNrCgcCpfPessoaAndDvCgcCpfPessoa(numeroBase, dvCgcCpfPessoa);
            if (pessoa != null) {
                return new AcessoDTO(pessoa.getNmPessoa(), cpfCnpj, pessoa.getDsEmail());
            } else {
                return null;
            }
        }
    }
    
    @Transactional
    public boolean atualizarUsuario(String cpfCnpj, long nrTelAcesso, String dsEmailAcesso) {
        int length = cpfCnpj.length();

        if (length < 2) {
            return false;
        }

        int rowsUpdated = acessoRepository.updateNrTelAcessoAndDsEmailAcessoByCpfCnpjAcesso(cpfCnpj, nrTelAcesso, dsEmailAcesso);

        return rowsUpdated > 0;
    }
}
