package br.gov.mg.uberlandia.decserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.gov.mg.uberlandia.decserver.entity.TmpEnviosEntity;
import java.util.List;

@Repository
public interface TmpEnviosRepository extends JpaRepository<TmpEnviosEntity, Long> {

    @Query("SELECT te FROM TmpEnviosEntity te WHERE te.situacEnvio = 0 AND te.dtHrConfigEnvio < CURRENT_TIMESTAMP")
    List<TmpEnviosEntity> findEnviosProgramados();

    @Query("SELECT te FROM TmpEnviosEntity te WHERE te.situacEnvio = 1")
    List<TmpEnviosEntity> findEnviosExecutados();
    
}
