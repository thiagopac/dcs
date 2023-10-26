package br.gov.mg.uberlandia.decserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.gov.mg.uberlandia.decserver.entity.TmpEnviosEntity;
import java.util.List;

@Repository
public interface TmpEnviosRepository extends JpaRepository<TmpEnviosEntity, Long> {

    @Query("SELECT te FROM TmpEnviosEntity te WHERE te.situacEnvio = 0 AND te.dtHrConfigEnvio < CURRENT_TIMESTAMP")
    List<TmpEnviosEntity> findEnviosProgramados();

    @Query("SELECT te FROM TmpEnviosEntity te WHERE te.situacEnvio = 1")
    List<TmpEnviosEntity> findEnviosExecutados();

    @Query("SELECT te FROM TmpEnviosEntity te WHERE (:status IS NULL OR te.statusEnvio = :status) AND te.situacEnvio = 0 AND te.usuConfigEnvio IN :usuConfigEnvioList")
    Page<TmpEnviosEntity> findByUsuConfigEnvioInAndStatusEnvio(@Param("usuConfigEnvioList") List<String> usuConfigEnvioList, @Param("status") Long status, Pageable pageable);
    
}
