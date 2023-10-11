package br.gov.mg.uberlandia.decserver.dto;

import lombok.Data;

@Data
public class AtualizacaoAcessoDTO {
    private String cpfCnpjAcesso;
    private String nmAcesso;
    private long nrTelAcesso;
    private String dsEmailAcesso;

    public AtualizacaoAcessoDTO() {}

    public AtualizacaoAcessoDTO(String cpfCnpjAcesso, String nmAcesso, long nrTelAcesso, String dsEmailAcesso) {
        this.cpfCnpjAcesso = cpfCnpjAcesso;
        this.nmAcesso = nmAcesso;
        this.nrTelAcesso = nrTelAcesso;
        this.dsEmailAcesso = dsEmailAcesso;
    }

}