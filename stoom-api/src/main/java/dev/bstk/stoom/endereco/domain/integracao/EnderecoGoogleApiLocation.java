package dev.bstk.stoom.endereco.domain.integracao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class EnderecoGoogleApiLocation implements Serializable {

    @JsonProperty("lat")
    private Double latitude;

    @JsonProperty("lng")
    private Double longetude;
}
