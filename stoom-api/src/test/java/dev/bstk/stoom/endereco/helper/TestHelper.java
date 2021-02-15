package dev.bstk.stoom.endereco.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.bstk.stoom.endereco.api.request.EnderecoRequest;
import dev.bstk.stoom.endereco.api.response.EnderecoResponse;
import dev.bstk.stoom.endereco.domain.integracao.EnderecoGoogleApiResponse;
import dev.bstk.stoom.endereco.domain.model.Endereco;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class TestHelper {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private TestHelper() {}

    public static Endereco endereco() throws IOException {
        return enderecos().get(0);
    }

    public static List<Endereco> enderecos() throws IOException {
        final var enderecosJson = new File("src/test/resources/endereco/enderecos.json");
        final var enderecos = JSON_MAPPER.readValue(enderecosJson, Endereco[].class);
        return Arrays.asList(enderecos);
    }

    public static List<EnderecoResponse> responses() throws IOException {
        final var enderecosJson = new File("src/test/resources/endereco/enderecos-response.json");
        final var enderecos = JSON_MAPPER.readValue(enderecosJson, EnderecoResponse[].class);
        return Arrays.asList(enderecos);
    }

    public static EnderecoGoogleApiResponse responseGoogleApiOK() throws IOException {
        final var googleApiOK = new File("src/test/resources/google-api/google-api-ok-response.json");
        return JSON_MAPPER.readValue(googleApiOK, EnderecoGoogleApiResponse.class);
    }

    public static EnderecoGoogleApiResponse responseGoogleApiNOK() throws IOException {
        final var googleApiNOK = new File("src/test/resources/google-api/google-api-nok-response.json");
        return JSON_MAPPER.readValue(googleApiNOK, EnderecoGoogleApiResponse.class);
    }

    public static EnderecoRequest request() throws IOException {
        final var enderecosJson = new File("src/test/resources/endereco/endereco-request.json");
        return JSON_MAPPER.readValue(enderecosJson, EnderecoRequest.class);
    }

    public static EnderecoResponse response() throws IOException {
        return responses().get(0);
    }

    public static String enderecoRequest() throws IOException {
        return JSON_MAPPER.writeValueAsString(request());
    }

}
