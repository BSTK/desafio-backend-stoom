package dev.bstk.stoom.endereco.domain.integracao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class EnderecoGoogleApiResult implements Serializable {

    @JsonProperty("geometry")
    private EnderecoGoogleApiGeometry dadoGeometrico;

}
