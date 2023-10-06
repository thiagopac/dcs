package br.gov.mg.uberlandia.decserver.dto;

import br.gov.mg.uberlandia.decserver.utils.JsonParseable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que sera transformada em parte do token. Devem ser inclusos nessa
 * classe, atributos relacionados ao usuário logado. Esses atributos podem ser
 * utilizados em consultas, sem a necessidade de serem enviados no corpo de uma
 * requisicao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformacoesTokenDTO implements JsonParseable {

    private String oidUsuario;

    private String cpf;

    private String[] permissoes;

}
