package br.gov.mg.uberlandia.decserver.dto;

public class EmpresaDTO {
    private Long oidEmpresa;
    private String nmEmpresa;
    private String cnpjEmpresa;
    private Long nrTelEmpresa;
    private String dsEmailEmpresa;

    public EmpresaDTO(){}

    public EmpresaDTO(Long oidEmpresa, String nmEmpresa, String cnpjEmpresa, Long nrTelEmpresa, String dsEmailEmpresa) {
        this.oidEmpresa = oidEmpresa;
        this.nmEmpresa = nmEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
        this.nrTelEmpresa = nrTelEmpresa;
        this.dsEmailEmpresa = dsEmailEmpresa;
    }

    public Long getOidEmpresa() {
        return oidEmpresa;
    }

    public void setOidEmpresa(Long oidEmpresa) {
        this.oidEmpresa = oidEmpresa;
    }

    public String getNmEmpresa() {
        return nmEmpresa;
    }

    public void setNmEmpresa(String nmEmpresa) {
        this.nmEmpresa = nmEmpresa;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public Long getNrTelEmpresa() {
        return nrTelEmpresa;
    }

    public void setNrTelEmpresa(Long nrTelEmpresa) {
        this.nrTelEmpresa = nrTelEmpresa;
    }

    public String getDsEmailEmpresa() {
        return dsEmailEmpresa;
    }

    public void setDsEmailEmpresa(String dsEmailEmpresa) {
        this.dsEmailEmpresa = dsEmailEmpresa;
    }
    
}

