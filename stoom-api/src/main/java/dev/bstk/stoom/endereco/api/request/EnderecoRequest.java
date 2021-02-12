package dev.bstk.stoom.endereco.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class EnderecoRequest implements Serializable {

    @NotNull @NotEmpty
    @JsonProperty("streetName")
    private String rua;

    @NotNull @NotEmpty
    @JsonProperty("number")
    private String numero;

    @NotNull @NotEmpty
    @JsonProperty("complement")
    private String complemento;

    @NotNull @NotEmpty
    @JsonProperty("neighbourhood")
    private String bairro;

    @NotNull @NotEmpty
    @JsonProperty("city")
    private String cidade;

    @NotNull @NotEmpty
    @JsonProperty("state")
    private String estado;

    @NotNull @NotEmpty
    @JsonProperty("country")
    private String pais;

    @NotNull @NotEmpty
    @JsonProperty("zipcode")
    private String cep;

    private String latitude;
    private String longitude;

}
