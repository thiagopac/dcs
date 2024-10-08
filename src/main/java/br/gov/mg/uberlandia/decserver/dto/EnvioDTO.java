package br.gov.mg.uberlandia.decserver.dto;

import java.util.Date;

public class EnvioDTO {
    private Long oidEnvio;
    private Long idEmpresa;
    private Long tpEnvio;
    private Date dtHrEnvio;
    private Long qtDiasCiencia;
    private String dsTituloEnvio;
    private String dsComunicEnvio;
    private String usuConfigEnvio;
    private Date dtHrConfigEnvio;
    private Long cpfCnpjEnvio;
    private Long statusEnvio;
    private Long situacEnvio;
    private String dsUsuAlter;
    private Date dtUltAlter;
    private Long vsVersao;
    public Long nrProtocolo;
    public Long idSecretaria;
    public String cpfServidor;

    private String estado;

    public EnvioDTO() {}
    
    public Long getOidEnvio() {
        return oidEnvio;
    }

    public void setOidEnvio(Long oidEnvio) {
        this.oidEnvio = oidEnvio;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getTpEnvio() {
        return tpEnvio;
    }

    public void setTpEnvio(Long tpEnvio) {
        this.tpEnvio = tpEnvio;
    }

    public Date getDtHrEnvio() {
        return dtHrEnvio;
    }

    public void setDtHrEnvio(Date dtHrEnvio) {
        this.dtHrEnvio = dtHrEnvio;
    }

    public Long getQtDiasCiencia() {
        return qtDiasCiencia;
    }

    public void setQtDiasCiencia(Long qtDiasCiencia) {
        this.qtDiasCiencia = qtDiasCiencia;
    }

    public String getDsTituloEnvio() {
        return dsTituloEnvio;
    }

    public void setDsTituloEnvio(String dsTituloEnvio) {
        this.dsTituloEnvio = dsTituloEnvio;
    }

    public String getDsComunicEnvio() {
        return dsComunicEnvio;
    }

    public void setDsComunicEnvio(String dsComunicEnvio) {
        this.dsComunicEnvio = dsComunicEnvio;
    }

    public String getUsuConfigEnvio() {
        return usuConfigEnvio;
    }

    public void setUsuConfigEnvio(String usuConfigEnvio) {
        this.usuConfigEnvio = usuConfigEnvio;
    }

    public Date getDtHrConfigEnvio() {
        return dtHrConfigEnvio;
    }

    public void setDtHrConfigEnvio(Date dtHrConfigEnvio) {
        this.dtHrConfigEnvio = dtHrConfigEnvio;
    }

    public Long getCpfCnpjEnvio() {
        return cpfCnpjEnvio;
    }

    public void setCpfCnpjEnvio(Long cpfCnpjEnvio) {
        this.cpfCnpjEnvio = cpfCnpjEnvio;
    }

    public Long getStatusEnvio() {
        return statusEnvio;
    }

    public void setStatusEnvio(Long statusEnvio) {
        this.statusEnvio = statusEnvio;
    }

    public Long getSituacEnvio() {
        return situacEnvio;
    }
    
    public void setSituacEnvio(Long situacEnvio) {
        this.situacEnvio = situacEnvio;
    }

    public String getDsUsuAlter() {
        return dsUsuAlter;
    }

    public void setDsUsuAlter(String dsUsuAlter) {
        this.dsUsuAlter = dsUsuAlter;
    }

    public Date getDtUltAlter() {
        return dtUltAlter;
    }

    public void setDtUltAlter(Date dtUltAlter) {
        this.dtUltAlter = dtUltAlter;
    }

    public Long getVsVersao() {
        return vsVersao;
    }

    public void setVsVersao(Long vsVersao) {
        this.vsVersao = vsVersao;
    }

    public Long getNrProtocolo() {
        return nrProtocolo;
    }

    public void setNrProtocolo(Long nrProtocolo) {
        this.nrProtocolo = nrProtocolo;
    }

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getCpfServidor() {
        return cpfServidor;
    }

    public void setCpfServidor(String cpfServidor) {
        this.cpfServidor = cpfServidor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}