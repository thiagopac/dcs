package br.gov.mg.uberlandia.decserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.gov.mg.uberlandia.decserver.entity.EnviosEntity;
import java.util.List;

@Repository
public interface EnviosRepository extends JpaRepository<EnviosEntity, Long> {

    @Query("SELECT e.idEmpresa AS idEmpresa, emp.oidEmpresa AS OID_EMPRESA, emp.nmEmpresa AS NM_EMPRESA, emp.cnpjEmpresa AS CNPJ_EMPRESA, emp.nrTelEmpresa AS NR_TEL_EMPRESA, emp.dsEmailEmpresa AS DS_EMAIL_EMPRESA, COUNT(e) AS quantidadeNaoLidos " +
           "FROM EnviosEntity e " +
           "JOIN AcessosEntity a ON e.idEmpresa = a.idEmpresa " +
           "JOIN EmpresasEntity emp ON a.idEmpresa = emp.oidEmpresa " +
           "WHERE a.cpfCnpjAcesso = :cpfCnpj " +
           "AND e.statusEnvio = 0 " +
           "GROUP BY e.idEmpresa, emp.oidEmpresa, emp.nmEmpresa, emp.cnpjEmpresa, emp.nrTelEmpresa, emp.dsEmailEmpresa")
    List<Object[]> countNaoLidosPorEmpresa(@Param("cpfCnpj") String cpfCnpj);

    @Query("SELECT e FROM EnviosEntity e WHERE (:status IS NULL OR e.statusEnvio = :status) AND e.idEmpresa = :idEmpresa")
    Page<EnviosEntity> listarEnviosPorIdEmpresa(@Param("idEmpresa") Long idEmpresa, @Param("status") Long status, Pageable pageable);
    
}
