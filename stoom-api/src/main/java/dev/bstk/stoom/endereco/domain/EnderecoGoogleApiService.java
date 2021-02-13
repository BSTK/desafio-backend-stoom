package dev.bstk.stoom.endereco.domain;

import dev.bstk.stoom.endereco.api.request.EnderecoRequest;
import dev.bstk.stoom.endereco.domain.integracao.EnderecoGoogleApiResponse;
import dev.bstk.stoom.helper.IntegracaoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Objects;

@Slf4j
@Service
public class EnderecoGoogleApiService {

    private static final String QUERY_PARAM_API_KEY = "key";
    private static final String QUERY_PARAM_ADDRESS = "address";

    private final RestTemplate restTemplate;

    @Value("${stoom.maps.googleapi.resource}")
    private String googleApiMapsUrl;

    @Value("${stoom.maps.googleapi.apikey}")
    private String googleApiMapsApyKey;

    @Autowired
    public EnderecoGoogleApiService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EnderecoGoogleApiResponse buscarDadosEndereco(final EnderecoRequest enderecoRequest) throws IntegracaoException {
        try {
            final var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            final var googleApiMapsRequest = new HttpEntity<>(headers);
            final var urlQueryParams = urlQueryParams(enderecoRequest);

            final var respostaApi = restTemplate.exchange(
                urlQueryParams,
                HttpMethod.GET,
                googleApiMapsRequest,
                EnderecoGoogleApiResponse.class);

            if (respostaInvalida(respostaApi)) {
                log.error("Não foi possível buscar dados");
                throw new IntegracaoException(googleApiMapsUrl, "Resposta invalida!");
            }

            return respostaApi.getBody();

        } catch (HttpClientErrorException ex) {
            log.error("Não foi possível buscar dados", ex);
            throw new IntegracaoException(googleApiMapsUrl, ex.getMessage());
        }
    }

    private String urlQueryParams(final EnderecoRequest enderecoRequest) {
        final var query = String.format("%s, %s, %s, %s",
            enderecoRequest.getRua(),
            enderecoRequest.getNumero(),
            enderecoRequest.getCidade(),
            enderecoRequest.getEstado());

        return UriComponentsBuilder.fromHttpUrl(googleApiMapsUrl)
            .queryParam(QUERY_PARAM_ADDRESS, query)
            .queryParam(QUERY_PARAM_API_KEY, googleApiMapsApyKey)
            .build()
            .toUriString();
    }

    private boolean respostaInvalida(ResponseEntity<EnderecoGoogleApiResponse> resposta){
        return Objects.isNull(resposta)
            || HttpStatus.BAD_REQUEST.equals(resposta.getStatusCode())
            || Objects.isNull(resposta.getBody());
    }

}
