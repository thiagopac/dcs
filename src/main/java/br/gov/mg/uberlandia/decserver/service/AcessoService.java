package br.gov.mg.uberlandia.decserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.gov.mg.uberlandia.decserver.dto.EmpresaDTO;
import br.gov.mg.uberlandia.decserver.entity.AcessosEntity;
import br.gov.mg.uberlandia.decserver.repository.AcessoRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcessoService {

    @Autowired
    private AcessoRepository acessoRepository;

    public List<EmpresaDTO> consultarEmpresasPorCpfCnpj(String cpfCnpj) {
        List<AcessosEntity> acessos = acessoRepository.findByCpfCnpjAcesso(cpfCnpj);

        List<EmpresaDTO> empresasDTO = convertAcessosToEmpresasDTO(acessos);

        return empresasDTO;
    }

    private List<EmpresaDTO> convertAcessosToEmpresasDTO(List<AcessosEntity> acessos) {
        return acessos.stream()
                .map(this::mapAcessosEntityToEmpresaDTO)
                .collect(Collectors.toList());
    }

    private EmpresaDTO mapAcessosEntityToEmpresaDTO(AcessosEntity acesso) {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setOidEmpresa(acesso.getIdEmpresa());
        empresaDTO.setNmEmpresa(acesso.getNmAcesso());
        empresaDTO.setCnpjEmpresa(acesso.getCpfCnpjAcesso());
        empresaDTO.setNrTelEmpresa(acesso.getNrTelAcesso());
        empresaDTO.setDsEmailEmpresa(acesso.getDsEmailAcesso());
        return empresaDTO;
    }
}
