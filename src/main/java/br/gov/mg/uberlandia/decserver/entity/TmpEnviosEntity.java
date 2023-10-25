package br.gov.mg.uberlandia.decserver.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;
import java.util.Date;
import javax.persistence.*;

@ApiModel(description = "TABELA DE REGISTROS TEMPORÁRIOS PARA GERAR ENVIOS.")
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "TMP_ENVIOS", schema = "DB_DEC")
public class TmpEnviosEntity extends GenericEntity {

    @Id
    @SequenceGenerator(name = "DB_DEC.SE_TMP_ENVIOS", sequenceName = "DB_DEC.SE_TMP_ENVIOS", allocationSize = 1)
    @GeneratedValue(generator = "DB_DEC.SE_TMP_ENVIOS", strategy = GenerationType.AUTO)
    @Column(name = "OID_TMP_ENVIO", nullable = false, length = 12)
    private Long oidTmpEnvio;

    @Column(name = "ID_EMPRESA", nullable = false, length = 12)
    private Long idEmpresa;

    @Column(name = "TP_ENVIO", nullable = false, length = 1)
    private Long tpEnvio;

    @Column(name = "ID_SITUAC_ENVIO", nullable = false, length = 1)
    private Long situacEnvio;

    @Column(name = "QT_DIAS_CIENCIA", nullable = false, length = 2)
    private Long qtDiasCiencia;

    @Column(name = "DS_TITULO_ENVIO", nullable = false, length = 30)
    private String dsTituloEnvio;

    @Column(name = "DS_COMUNIC_ENVIO", nullable = false)
    private String dsComunicEnvio;

    @Column(name = "NM_USU_CONFIG_ENVIO", nullable = true, length = 30)
    private String usuConfigEnvio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_HR_CONFIG_ENVIO", nullable = true)
    private Date dtHrConfigEnvio;

    @Column(name = "NR_CPF_CNPJ_ENVIO", nullable = false, length = 15)
    private Long cpfCnpjEnvio;

    @Column(name = "ID_STATUS_ENVIO", nullable = false, length = 1)
    private Long statusEnvio;

    @Column(name = "DS_USU_ALTER", nullable = false, length = 30)
    private String dsUsuAlter;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ULT_ALTER", nullable = false)
    private Date dtUltAlter;

    @Column(name = "VS_VERSAO", nullable = false, length = 10)
    private Long vsVersao;

    @Column(name = "NR_PROTOCOLO", nullable = true, length = 12)
    private Long nrProtocolo;

    @Column(name = "ID_SECRETARIA", nullable = true, length = 12)
    private Long idSecretaria;
    
}
