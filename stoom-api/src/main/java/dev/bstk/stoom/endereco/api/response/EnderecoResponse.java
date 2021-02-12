package dev.bstk.stoom.endereco.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class EnderecoResponse implements Serializable {

    private Long id;
    private String latitude;
    private String longitude;

    @JsonProperty("streetName")
    private String rua;

    @JsonProperty("number")
    private String numero;

    @JsonProperty("complement")
    private String complemento;

    @JsonProperty("neighbourhood")
    private String bairro;

    @JsonProperty("city")
    private String cidade;

    @JsonProperty("state")
    private String estado;

    @JsonProperty("country")
    private String pais;

    @JsonProperty("zipcode")
    private String cep;

}
