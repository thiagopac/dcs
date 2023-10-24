package br.gov.mg.uberlandia.decserver.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;
import java.util.Date;
import javax.persistence.*;

@ApiModel(description = "TABELA DE REGISTROS TEMPORÁRIOS PARA GERAR ENVIOS.")
@Data
@Entity
@Table(name = "TMP_ENVIOS", schema = "DB_DEC")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ENVIO_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
public class TmpEnviosEntity extends EnviosEntity {

    @Column(name = "SITUAC_ENVIO", nullable = false, length = 1)
    private Long situacEnvio;
    
    @Transient
    private Date dtHrEnvio;
}
