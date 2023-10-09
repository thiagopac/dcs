package br.gov.mg.uberlandia.decserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.gov.mg.uberlandia.decserver.entity.AcessosEntity;
import java.util.List;

public interface AcessoRepository extends JpaRepository<AcessosEntity, Long> {
    List<AcessosEntity> findByCpfCnpjAcesso(String cpfCnpjAcesso);
}