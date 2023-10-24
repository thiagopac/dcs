package br.gov.mg.uberlandia.decserver.entity;

import io.swagger.annotations.ApiModel;
import javax.persistence.*;
import lombok.*;
import java.util.Date;

@ApiModel(description = "TABELA DE RELACIONAMENTO DE SERVIDORES.")
@Data
@Entity
@Table(name = "REL_SERVIDORES", schema = "DB_DEC")
public class RelServidoresEntity extends GenericEntity {

    @Id
    @SequenceGenerator(name = "DB_DEC.SE_REL_SERVIDORES", sequenceName = "DB_DEC.SE_REL_SERVIDORES", allocationSize = 1)
    @GeneratedValue(generator = "DB_DEC.SE_REL_SERVIDORES", strategy = GenerationType.AUTO)
    @Column(name = "OID_SERVIDOR", nullable = false, length = 12)
    private Long oidServidor;

    @Column(name = "ID_SECRETARIA", nullable = true, length = 12)
    private Long idSecretaria;

    @Column(name = "NR_CPF_SERVIDOR", nullable = false, length = 15)
    private Long nrCpfServidor;

    @Column(name = "NM_SERVIDOR", nullable = true, length = 255)
    private String nmServidor;

    @Column(name = "ID_CARGO", nullable = true, length = 12)
    private Long idCargo;

    @Column(name = "DS_USU_ALTER", nullable = false, length = 30)
    private String dsUsuAlter;

    @Column(name = "DT_ULT_ALTER", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtUltAlter;

    @Column(name = "VS_VERSAO", nullable = false, length = 10)
    private Long vsVersao;
}
