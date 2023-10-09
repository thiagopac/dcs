package br.gov.mg.uberlandia.decserver.repository.siat;

import br.gov.mg.uberlandia.decserver.entity.siat.PessoasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoasRepository extends JpaRepository<PessoasEntity, Long> {
    
    PessoasEntity findByNrCgcCpfPessoaAndDvCgcCpfPessoa(String nrCgcCpfPessoa, String dvCgcCpfPessoa);

}