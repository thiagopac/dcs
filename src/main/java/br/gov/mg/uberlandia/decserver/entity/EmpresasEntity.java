package br.gov.mg.uberlandia.decserver.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@ApiModel(description = "TABELA DE EMPRESAS.")
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "EMPRESAS", schema = "DB_DEC")

public class EmpresasEntity extends GenericEntity{

    @Id @SequenceGenerator(name = "DB_DEC.SE_EMPRESAS", sequenceName = "DB_DEC.SE_EMPRESAS", allocationSize = 1)
    @GeneratedValue(generator = "DB_DEC.SE_EMPRESAS", strategy = GenerationType.AUTO)
    @Column(name = "OID_EMPRESA", nullable = false, length = 12)
    private Long oidEmpresa;

    @Column(name = "NM_EMPRESA", nullable = false, length = 100)
    private String nmEmpresa;

    @Column(name = "NR_CNPJ_EMPRESA", nullable = false, length = 100)
    private Long cnpjEmpresa;

    @Column(name = "NR_TEL_EMPRESA", nullable = false, length = 12)
    private Long nrTelEmpresa;

    @Column(name = "DS_EMAIL_EMPRESA", nullable = false, length = 100)
    private String dsEmailEmpresa;

    @Column(name = "DS_USU_ALTER", nullable = false, length = 30)
    private String dsUsuAlter;

    @Column(name = "DT_ULT_ALTER", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtUltAlter;

    @Column(name = "VS_VERSAO", nullable = false, length = 10)
    private Long vsVersao;


}
