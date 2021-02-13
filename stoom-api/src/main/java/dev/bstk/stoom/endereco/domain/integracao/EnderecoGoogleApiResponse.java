package dev.bstk.stoom.endereco.domain.integracao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class EnderecoGoogleApiResponse implements Serializable {

    @JsonProperty("results")
    private List<EnderecoGoogleApiResult> resultado;

    @JsonProperty("status")
    private String status;

}
