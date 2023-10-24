package br.gov.mg.uberlandia.decserver.dto;

public class ServidorDTO {

    private Long idSecretaria;
    private Long oidServidor;
    private Long nrCpfServidor;
    private String nmServidor;
    private Long idCargo;

    public ServidorDTO() {}

    public Long getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Long idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Long getOidServidor() {
        return oidServidor;
    }

    public void setOidServidor(Long oidServidor) {
        this.oidServidor = oidServidor;
    }

    public Long getNrCpfServidor() {
        return nrCpfServidor;
    }

    public void setNrCpfServidor(Long nrCpfServidor) {
        this.nrCpfServidor = nrCpfServidor;
    }

    public String getNmServidor() {
        return nmServidor;
    }

    public void setNmServidor(String nmServidor) {
        this.nmServidor = nmServidor;
    }

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }
}
