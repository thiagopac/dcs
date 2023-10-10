package br.gov.mg.uberlandia.decserver.entity.siat;

import javax.persistence.*;

@Entity
@Table(name = "PESSOAS", schema = "DB_PESSOA")
public class PessoasEntity {

    @Id
    @Column(name = "OID_PESSOAS")
    private Long oidPessoas;

    @Column(name = "NR_CGC_CPF_PESSOA")
    private String nrCgcCpfPessoa;

    @Column(name = "DV_CGC_CPF_PESSOA")
    private String dvCgcCpfPessoa;

    @Column(name = "NM_PESSOA")
    private String nmPessoa;

    @Column(name = "DS_EMAIL")
    private String dsEmail;

    public Long getOidPessoas() {
        return oidPessoas;
    }
    
    public String getNrCgcCpfPessoa() {
        return nrCgcCpfPessoa;
    }
    
    public String getDvCgcCpfPessoa() {
        return dvCgcCpfPessoa;
    }
    
    public String getNmPessoa() {
        return nmPessoa;
    }
    
    public String getDsEmail() {
        return dsEmail;
    }

}