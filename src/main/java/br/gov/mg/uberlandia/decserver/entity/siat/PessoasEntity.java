package br.gov.mg.uberlandia.decserver.entity.siat;

import javax.persistence.*;

@Entity
@Table(name = "TBLPES", schema = "SIATUDI")
public class PessoasEntity {

    @Id
    @Column(name = "CODPES")
    private Long codPes;

    @Column(name = "CPFCNPJ")
    private String cpfCnpj;

    @Column(name = "NOMRAZCOM")
    private String nomRazCom;

    public Long getCodPes() {
        return codPes;
    }

    public void setCodPes(Long codPes) {
        this.codPes = codPes;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNomRazCom() {
        return nomRazCom;
    }

    public void setNomRazCom(String nomRazCom) {
        this.nomRazCom = nomRazCom;
    }
}