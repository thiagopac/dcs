package br.gov.mg.uberlandia.decserver.dto;

import lombok.Data;

@Data
public class AtualizacaoAcessoDTO {
    private Long cpfCnpjAcesso;
    private String nmAcesso;
    private long nrTelAcesso;
    private String dsEmailAcesso;

    public AtualizacaoAcessoDTO() {}

    public AtualizacaoAcessoDTO(Long cpfCnpjAcesso, String nmAcesso, long nrTelAcesso, String dsEmailAcesso) {
        this.cpfCnpjAcesso = cpfCnpjAcesso;
        this.nmAcesso = nmAcesso;
        this.nrTelAcesso = nrTelAcesso;
        this.dsEmailAcesso = dsEmailAcesso;
    }

}