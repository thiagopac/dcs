package br.gov.mg.uberlandia.decserver.dto;

import lombok.Data;

@Data
public class AcessoDTO {
    private Long oidAcesso;
    private Long idEmpresa;
    private String nmAcesso;
    private Long cpfCnpjAcesso;
    private Long nrTelAcesso;
    private String dsEmailAcesso;
    private Long statusAcesso;

    public AcessoDTO() {};

    public AcessoDTO(String nmAcesso, Long cpfCnpjAcesso) {
        this.nmAcesso = nmAcesso;
        this.cpfCnpjAcesso = cpfCnpjAcesso;
    }

    public AcessoDTO(Long oidAcesso, Long idEmpresa, String nmAcesso, Long cpfCnpjAcesso, 
                     Long nrTelAcesso, String dsEmailAcesso, Long statusAcesso) {
        this.oidAcesso = oidAcesso;
        this.idEmpresa = idEmpresa;
        this.nmAcesso = nmAcesso;
        this.cpfCnpjAcesso = cpfCnpjAcesso;
        this.nrTelAcesso = nrTelAcesso;
        this.dsEmailAcesso = dsEmailAcesso;
        this.statusAcesso = statusAcesso;
    }
}