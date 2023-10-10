package br.gov.mg.uberlandia.decserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.mg.uberlandia.decserver.entity.AcessosEntity;
import java.util.List;

public interface AcessosRepository extends JpaRepository<AcessosEntity, Long> {
    List<AcessosEntity> findByCpfCnpjAcesso(String cpfCnpjAcesso);

    @Modifying
    @Query("UPDATE AcessosEntity a SET a.nrTelAcesso = :nrTelAcesso, a.dsEmailAcesso = :dsEmailAcesso WHERE a.cpfCnpjAcesso = :cpfCnpj")
    int updateNrTelAcessoAndDsEmailAcessoByCpfCnpjAcesso(
        @Param("cpfCnpj") String cpfCnpj, 
        @Param("nrTelAcesso") long nrTelAcesso, 
        @Param("dsEmailAcesso") String dsEmailAcesso);
}