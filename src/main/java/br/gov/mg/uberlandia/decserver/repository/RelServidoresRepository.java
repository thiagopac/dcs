package br.gov.mg.uberlandia.decserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.gov.mg.uberlandia.decserver.entity.RelServidoresEntity;
import java.util.List;

public interface RelServidoresRepository extends JpaRepository<RelServidoresEntity, Long> {
    
    RelServidoresEntity findByOidServidor(Long oidServidor);

    RelServidoresEntity findByNrCpfServidor(String cpf);
    
    List<RelServidoresEntity> findByIdSecretaria(Long idSecretaria);

    @Query("SELECT r.nrCpfServidor FROM RelServidoresEntity r WHERE r.oidServidor IN :oidServidores")
    List<String> findCpfByOidServidorIn(@Param("oidServidores") List<Long> oidServidores);

    @Query("SELECT r.nrCpfServidor FROM RelServidoresEntity r WHERE r.oidServidor = :oidServidor")
    List<String> findCpfByOidServidor(Long oidServidor);
    
}