package br.gov.mg.uberlandia.decserver.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@ApiModel(description = "TABELA DE ACESSOS.")
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "ACESSOS", schema = "DB_DEC")
public class AcessosEntity extends GenericEntity{

    @Id @SequenceGenerator(name = "DB_DEC.SE_ACESSOS", sequenceName = "DB_DEC.SE_ACESSOS", allocationSize = 1)
    @GeneratedValue(generator = "DB_DEC.SE_ACESSOS", strategy = GenerationType.AUTO)
    @Column(name = "OID_ACESSO", nullable = false, length = 12)
    private Long oidAcesso;

    @Column(name = "ID_EMPRESA", nullable = false, length = 12)
    private Long idEmpresa;

    @Column(name = "NM_ACESSO", nullable = false, length = 100)
    private String nmAcesso;

    @Column(name = "NR_CPF_CNPJ_ACESSO", nullable = false, length = 15)
    private Long cpfCnpjAcesso;

    @Column(name = "NR_TEL_ACESSO", nullable = false, length = 12)
    private Long nrTelAcesso;

    @Column(name = "DS_EMAIL_ACESSO", nullable = false, length = 100)
    private String dsEmailAcesso;

    @Column(name = "ID_STATUS_ACESSO", nullable = false, length = 1)
    private Long statusAcesso;

    @Column(name = "DS_USU_ALTER", nullable = false, length = 30)
    private String dsUsuAlter;

    @Column(name = "DT_ULT_ALTER", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtUltAlter;

    @Column(name = "VS_VERSAO", nullable = false, length = 10)
    private Long vsVersao;

}
