package br.gov.mg.uberlandia.decserver.dto;

import lombok.Data;

@Data
public class EnviosNaoLidosDTO {
    private Long idEmpresa;
    private Long oidEmpresa;
    private String nomeEmpresa;
    private String cnpjEmpresa;
    private Long nrTelEmpresa;
    private String dsEmailEmpresa;
    private Long quantidadeNaoLidos;

    public EnviosNaoLidosDTO(
            Long idEmpresa, 
            Long oidEmpresa, 
            String nomeEmpresa, 
            String cnpjEmpresa, 
            Long nrTelEmpresa, 
            String dsEmailEmpresa, 
            Long quantidadeNaoLidos) {
        this.idEmpresa = idEmpresa;
        this.oidEmpresa = oidEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
        this.nrTelEmpresa = nrTelEmpresa;
        this.dsEmailEmpresa = dsEmailEmpresa;
        this.quantidadeNaoLidos = quantidadeNaoLidos;
    }
}