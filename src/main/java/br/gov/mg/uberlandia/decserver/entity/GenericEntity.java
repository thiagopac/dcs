package br.gov.mg.uberlandia.decserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class GenericEntity {

    @Column(name = "DS_USU_ALTER", nullable = false, length = 30)
    protected String dsUsuAlter;

    @Column(name = "DT_ULT_ALTER", nullable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dtUltAlter;

    @Version
    @Column(name = "VS_VERSAO", nullable = false, length = 12, insertable = false)
    protected Long vsVersao;

    @PreUpdate
    public void preUpdate() {
        dtUltAlter = new Date();
        vsVersao = vsVersao == null ? 0L : vsVersao + 1;
    }

}
