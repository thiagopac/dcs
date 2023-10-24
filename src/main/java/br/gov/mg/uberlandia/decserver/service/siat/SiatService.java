package br.gov.mg.uberlandia.decserver.service.siat;

import br.gov.mg.uberlandia.decserver.entity.siat.PessoasEntity;
import br.gov.mg.uberlandia.decserver.entity.AcessosEntity;
import br.gov.mg.uberlandia.decserver.repository.siat.PessoasRepository;
import br.gov.mg.uberlandia.decserver.repository.AcessosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiatService {

    private final PessoasRepository pessoasRepository;
    private final AcessosRepository acessosRepository;

    @Autowired
    public SiatService(PessoasRepository pessoasRepository, AcessosRepository acessosRepository) {
        this.pessoasRepository = pessoasRepository;
        this.acessosRepository = acessosRepository;
    }

    public void buscarEAtualizarAcesso(String cpfCnpj, long nrTelAcesso, String dsEmailAcesso) {
        PessoasEntity pessoasEntity = pessoasRepository.findByCpfCnpj(cpfCnpj);

        Long cpfCnpjLong = Long.parseLong(cpfCnpj);
    
        if (pessoasEntity != null) {
            List<AcessosEntity> acessosEntities = acessosRepository.findByCpfCnpjAcesso(cpfCnpjLong);
    
            if (acessosEntities.isEmpty()) {
                AcessosEntity acessosEntity = new AcessosEntity();
                acessosEntity.setNmAcesso(pessoasEntity.getNomRazCom());
                acessosEntity.setCpfCnpjAcesso(cpfCnpjLong);
                acessosEntity.setNrTelAcesso(nrTelAcesso);
                acessosEntity.setDsEmailAcesso(dsEmailAcesso);
                acessosEntity.setStatusAcesso(1L);
                acessosRepository.save(acessosEntity);
            } else {
                AcessosEntity acessosEntity = acessosEntities.get(0);
                acessosEntity.setNmAcesso(pessoasEntity.getNomRazCom());
                acessosEntity.setNrTelAcesso(nrTelAcesso);
                acessosEntity.setDsEmailAcesso(dsEmailAcesso);
                acessosEntity.setStatusAcesso(1L);
                acessosRepository.save(acessosEntity);
            }
        }
    }
}
