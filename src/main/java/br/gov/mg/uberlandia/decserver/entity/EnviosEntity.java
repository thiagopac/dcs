package br.gov.mg.uberlandia.decserver.entity;

import io.swagger.annotations.ApiModel;
import javax.persistence.*;
import lombok.*;
import java.util.Date;

@ApiModel(description = "TABELA DE ENVIOS.")
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "ENVIOS", schema = "DB_DEC")
public class EnviosEntity extends GenericEntity{


    @Id @SequenceGenerator(name = "DB_DEC.SE_ENVIOS", sequenceName = "DB_DEC.SE_ENVIOS", allocationSize = 1)
    @GeneratedValue(generator = "DB_DEC.SE_ENVIOS", strategy = GenerationType.AUTO)
    @Column(name = "OID_ENVIO", nullable = false, length = 12)
    private Long oidEnvio;

    @Column(name = "ID_EMPRESA", nullable = false, length = 12)
    private Long idEmpresa;

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
    private String dsComunicEnvio;

    @Column(name = "NM_USU_CONFIG_ENVIO", nullable = true, length = 30)
    private String usuConfigEnvio;

    @Column(name = "DT_HR_CONFIG_ENVIO", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtHrConfigEnvio;

    @Column(name = "NR_CPF_CNPJ_ENVIO", nullable = false, length = 15)
    private Long cpfCnpjEnvio;

    @Column(name = "ID_STATUS_ENVIO", nullable = false, length = 1)
    private Long statusEnvio;

    @Column(name = "DS_USU_ALTER", nullable = false, length = 30)
    private String dsUsuAlter;

    @Column(name = "DT_ULT_ALTER", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtUltAlter;

    @Column(name = "VS_VERSAO", nullable = false, length = 10)
    private Long vsVersao;

    @Column(name = "NR_PROTOCOLO", nullable = true, length = 12)
    private Long nrProtocolo;

    @Column(name = "ID_SECRETARIA", nullable = true, length = 12)
    private Long idSecretaria;

}
