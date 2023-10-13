package br.gov.mg.uberlandia.decserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.gov.mg.uberlandia.decserver.entity.AcessosEntity;
import br.gov.mg.uberlandia.decserver.entity.EmpresasEntity;

import java.util.List;

public interface EmpresasRepository extends JpaRepository<AcessosEntity, Long> {
    @Query("SELECT e FROM EmpresasEntity e " +
       "INNER JOIN AcessosEntity a ON e.oidEmpresa = a.idEmpresa " +
       "WHERE a.cpfCnpjAcesso = :cpfCnpj")
    List<EmpresasEntity> findEmpresasByCpfCnpjAcesso(@Param("cpfCnpj") String cpfCnpj);
}