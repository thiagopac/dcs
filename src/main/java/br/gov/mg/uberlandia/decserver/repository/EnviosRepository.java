package br.gov.mg.uberlandia.decserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.gov.mg.uberlandia.decserver.entity.EnviosEntity;
import java.util.Date;
import java.util.List;

@Repository
public interface EnviosRepository extends JpaRepository<EnviosEntity, Long> {

    @Query("SELECT COUNT(e) FROM EnviosEntity e WHERE e.statusEnvio = 0 AND e.tpEnvio = 0")
    Long countTotalMassNaoLidos();

    @Query("SELECT e.idEmpresa AS idEmpresa, emp.oidEmpresa AS OID_EMPRESA, emp.nmEmpresa AS NM_EMPRESA, emp.cnpjEmpresa AS CNPJ_EMPRESA, emp.nrTelEmpresa AS NR_TEL_EMPRESA, emp.dsEmailEmpresa AS DS_EMAIL_EMPRESA, COUNT(e) AS quantidadeNaoLidos " +
       "FROM EnviosEntity e " +
       "JOIN AcessosEntity a ON e.idEmpresa = a.idEmpresa " +
       "JOIN EmpresasEntity emp ON a.idEmpresa = emp.oidEmpresa " +
       "WHERE a.cpfCnpjAcesso = :cpfCnpj " +
       "AND emp.oidEmpresa = :oidEmpresa " +
       "AND e.statusEnvio = 0 " +
       "GROUP BY e.idEmpresa, emp.oidEmpresa, emp.nmEmpresa, emp.cnpjEmpresa, emp.nrTelEmpresa, emp.dsEmailEmpresa")
    List<Object[]> countNaoLidosPorEmpresa(@Param("cpfCnpj") Long cpfCnpj, @Param("oidEmpresa") Long oidEmpresa);

    @Query("SELECT e FROM EnviosEntity e " +
           "WHERE (:status IS NULL OR e.statusEnvio = :status) " +
           "AND (e.idEmpresa = :idEmpresa OR e.idEmpresa = 0) " +
           "AND (e.tpEnvio = 1 OR e.tpEnvio = 0) " +
           "ORDER BY e.id DESC")
    Page<EnviosEntity> listarEnviosPorIdEmpresa(@Param("idEmpresa") Long idEmpresa, @Param("status") Long status, Pageable pageable);

    @Query("SELECT e FROM EnviosEntity e WHERE e.id = :idEnvio")
    EnviosEntity findByIdEnvio(@Param("idEnvio") Long idEnvio);

    @Modifying
    @Query("UPDATE EnviosEntity e SET e.statusEnvio = 1, e.dsUsuAlter = :nmAcesso, e.dtUltAlter = :dataAtual WHERE e.id = :id AND :read = 1 AND e.statusEnvio = 0")
    void atualizarStatusEnvioSeNecessario(
            @Param("id") Long id,
            @Param("read") int read,
            @Param("nmAcesso") String nmAcesso,
            @Param("dataAtual") Date dataAtual
    );

    @Query("SELECT e FROM EnviosEntity e WHERE (:status IS NULL OR e.statusEnvio = :status) AND e.usuConfigEnvio = :usuConfigEnvio")
    Page<EnviosEntity> findByUsuConfigEnvioAndStatusEnvio(@Param("usuConfigEnvio") String usuConfigEnvio, @Param("status") Long status, Pageable pageable);
    
    @Query("SELECT e FROM EnviosEntity e WHERE (:status IS NULL OR e.statusEnvio = :status) AND e.usuConfigEnvio IN :usuConfigEnvioList")
    Page<EnviosEntity> findByUsuConfigEnvioInAndStatusEnvio(@Param("usuConfigEnvioList") List<String> usuConfigEnvioList, @Param("status") Long status, Pageable pageable);

    @Query("SELECT e FROM EnviosEntity e " +
           "WHERE e.statusEnvio = 0 " +
           "AND e.dtHrEnvio + e.qtDiasCiencia <= :dataAtual")
    List<EnviosEntity> findEnviosElegiveisParaMarcarComoLidos(@Param("dataAtual") Date dataAtual);
}
