package br.gov.mg.uberlandia.decserver.entity;

import io.swagger.annotations.ApiModel;
import javax.persistence.*;
import lombok.*;
import java.util.Date;

@ApiModel(description = "TABELA DE EMPRESAS.")
@Data
@Entity
@Table(name = "EMPRESAS", schema = "DB_DEC")

public class EmpresasEntity extends GenericEntity{

    @Id @SequenceGenerator(name = "DB_DEC.EMPRESAS", sequenceName = "DB_DEC.EMPRESAS", allocationSize = 1)
    @GeneratedValue(generator = "DB_DEC.EMPRESAS", strategy = GenerationType.AUTO)
    @Column(name = "OID_EMPRESAS", nullable = false, length = 12)
    private Long oidEmpresas;

    @Column(name = "NM_EMPRESA", nullable = false, length = 100)
    private String nmEmpresa;

    @Column(name = "CNPJ_EMPRESA", nullable = false, length = 100)
    private String cnpjEmpresa;

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
