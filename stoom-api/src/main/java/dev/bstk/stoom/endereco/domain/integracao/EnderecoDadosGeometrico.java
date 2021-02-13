package dev.bstk.stoom.endereco.domain.integracao;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class EnderecoDadosGeometrico implements Serializable {

    private final String latitude;
    private final String longetude;

    public EnderecoDadosGeometrico(final EnderecoGoogleApiResponse apiResponse) {
        if (apiResponse.getResultado().isEmpty()) {
            throw new IllegalArgumentException("EnderecoGoogleApiResponse inválido!");
        }

        final var dadoGeometrico = apiResponse.getResultado().get(0).getDadoGeometrico();
        this.latitude = String.valueOf(dadoGeometrico.getLocalizacao().getLatitude());
        this.longetude = String.valueOf(dadoGeometrico.getLocalizacao().getLongetude());
    }
}
