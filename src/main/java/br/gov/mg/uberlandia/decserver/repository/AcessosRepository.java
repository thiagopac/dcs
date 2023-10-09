package br.gov.mg.uberlandia.decserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.gov.mg.uberlandia.decserver.entity.AcessosEntity;
import java.util.List;

public interface AcessosRepository extends JpaRepository<AcessosEntity, Long> {
    List<AcessosEntity> findByCpfCnpjAcesso(String cpfCnpjAcesso);
}