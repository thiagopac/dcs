package br.gov.mg.uberlandia.decserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

}
