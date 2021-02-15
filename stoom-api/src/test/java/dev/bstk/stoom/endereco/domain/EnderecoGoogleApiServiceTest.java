package dev.bstk.stoom.endereco.domain;

import dev.bstk.stoom.endereco.domain.integracao.EnderecoGoogleApiResponse;
import dev.bstk.stoom.endereco.helper.TestHelper;
import dev.bstk.stoom.helper.IntegracaoException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnderecoGoogleApiServiceTest {

    private static final String CAMPO_GOOGLE_API_URL = "googleApiMapsUrl";
    private static final String CAMPO_GOOGLE_API_KEY = "googleApiMapsApyKey";

    private static final String CAMPO_GOOGLE_API_URL_VALOR = "https://googleapi.com";
    private static final String CAMPO_GOOGLE_API_KEY_VALOR = "umaApiKeyValida";

    @InjectMocks
    private EnderecoGoogleApiService  service;

    @Mock
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Deve buscar dados na api do google com response OK")
    public void deveBuscarDadosNaApiDoGoogleComResponseOK() throws IOException {
        when(restTemplate.exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(),
            eq(EnderecoGoogleApiResponse.class)))
            .thenReturn(ResponseEntity.ok(TestHelper.responseGoogleApiOK()));

        ReflectionTestUtils.setField(service, CAMPO_GOOGLE_API_URL, CAMPO_GOOGLE_API_URL_VALOR);
        ReflectionTestUtils.setField(service, CAMPO_GOOGLE_API_KEY, CAMPO_GOOGLE_API_KEY_VALOR);

        final var apiResponse = service.buscarDadosEndereco(TestHelper.request());

        Assertions.assertThat(apiResponse).isNotNull();

        verify(restTemplate, times(1))
            .exchange(anyString(), eq(HttpMethod.GET), any(), eq(EnderecoGoogleApiResponse.class));
    }

    @Test
    @DisplayName("Deve buscar dados na api do google com response NOK")
    public void deveBuscarDadosNaApiDoGoogleComResponseNOK() throws IOException {
        when(restTemplate.exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(),
            eq(EnderecoGoogleApiResponse.class)))
            .thenReturn(ResponseEntity.badRequest().body(TestHelper.responseGoogleApiNOK()));

        ReflectionTestUtils.setField(service, CAMPO_GOOGLE_API_URL, CAMPO_GOOGLE_API_URL_VALOR);
        ReflectionTestUtils.setField(service, CAMPO_GOOGLE_API_KEY, CAMPO_GOOGLE_API_KEY_VALOR);

        Assertions.assertThatThrownBy(() -> service.buscarDadosEndereco(TestHelper.request()))
            .isInstanceOf(IntegracaoException.class)
            .hasMessageContaining("Erro ao integrar com serviço externo");
    }

    @Test
    @DisplayName("Deve buscar dados na api do google com falha http")
    public void deveBuscarDadosNaApiDoGoogleComComFalhaHttp() {
        when(restTemplate.exchange(
            anyString(),
            eq(HttpMethod.GET),
            any(),
            eq(EnderecoGoogleApiResponse.class)))
            .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        ReflectionTestUtils.setField(service, CAMPO_GOOGLE_API_URL, CAMPO_GOOGLE_API_URL_VALOR);
        ReflectionTestUtils.setField(service, CAMPO_GOOGLE_API_KEY, CAMPO_GOOGLE_API_KEY_VALOR);

        Assertions.assertThatThrownBy(() -> service.buscarDadosEndereco(TestHelper.request()))
            .isInstanceOf(IntegracaoException.class)
            .hasMessageContaining("Erro ao integrar com serviço externo");
    }
}
