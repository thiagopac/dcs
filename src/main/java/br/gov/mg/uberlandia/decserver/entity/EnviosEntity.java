package br.gov.mg.uberlandia.decserver.entity;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Clob;
import java.util.Date;

@ApiModel(description = "TABELA DE ENVIOS.")
@Data
@Entity
@Table(name = "ENVIOS", schema = "DB_DEC")
public class EnviosEntity extends GenericEntity{


    @Id @SequenceGenerator(name = "DB_DEC.ACESSOS", sequenceName = "DB_DEC.ACESSOS", allocationSize = 1)
    @GeneratedValue(generator = "DB_DEC.ACESSOS", strategy = GenerationType.AUTO)
    @Column(name = "OID_ACESSOS", nullable = false, length = 12)
    private Long oidEnvios;

    @Column(name = "OID_EMPRESA", nullable = false, length = 12)
    private Long oidEmpresa;

    @Column(name = "TP_ENVIO", nullable = false, length = 1)
    private Long tpEnvio;

    @Column(name = "DT_HR_ENVIO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtHrEnvio;

    @Column(name = "QT_DIAS_CIENCIA", nullable = false, length = 2)
    private Long qtDiasCiencia;

    @Column(name = "DS_TITULO_ENVIO", nullable = false, length = 30)
    private String dsTituloEnvio;

    @Column(name = "DS_COMUNIC_ENVIO", nullable = false)
    private Clob dsComunicEnvio;

    @Column(name = "USU_CONFIG_ENVIO", nullable = true, length = 30)
    private String usuConfigEnvio;

    @Column(name = "DT_HR_CONFIG_ENVIO", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtHrConfigEnvio;

    @Column(name = "CPF_CNPJ_ENVIO", nullable = false, length = 15)
    private String cpfCnpjEnvio;

    @Column(name = "STATUS_ENVIO", nullable = false, length = 1)
    private Long statusEnvio;

    @Column(name = "DS_USU_ALTER", nullable = false, length = 30)
    private String dsUsuAlter;

    @Column(name = "DT_ULT_ALTER", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtUltAlter;

    @Column(name = "VS_VERSAO", nullable = false, length = 10)
    private Long vsVersao;

}
