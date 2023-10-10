package br.gov.mg.uberlandia.decserver.dto;

import lombok.Data;

@Data
public class AtualizacaoAcessoDTO {
    private String cpfCnpj;
    private long nrTelAcesso;
    private String dsEmailAcesso;

    public AtualizacaoAcessoDTO() {}

    public AtualizacaoAcessoDTO(String cpfCnpj, long nrTelAcesso, String dsEmailAcesso) {
        this.cpfCnpj = cpfCnpj;
        this.nrTelAcesso = nrTelAcesso;
        this.dsEmailAcesso = dsEmailAcesso;
    }

}