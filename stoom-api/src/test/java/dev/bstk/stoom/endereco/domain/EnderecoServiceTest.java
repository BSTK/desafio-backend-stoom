package dev.bstk.stoom.endereco.domain;

import dev.bstk.stoom.endereco.api.request.EnderecoRequest;
import dev.bstk.stoom.endereco.domain.model.Endereco;
import dev.bstk.stoom.endereco.domain.model.EnderecoRepository;
import dev.bstk.stoom.endereco.helper.TestHelper;
import dev.bstk.stoom.helper.ModelMapperHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService service;

    @Mock
    private ModelMapperHelper mapper;

    @Mock
    private EnderecoRepository repository;

    @Mock
    private EnderecoGoogleApiService enderecoGoogleApiService;


    @Test
    @DisplayName("Deve cadastrar um endereco valido")
    public void deveCadastrarUmEnderecoValido() throws IOException {
        when(mapper.map(any(EnderecoRequest.class), eq(Endereco.class))).thenReturn(TestHelper.endereco());
        when(repository.save(any(Endereco.class))).thenReturn(TestHelper.endereco());

        final var enderecoCadastrado = service.cadastrar(TestHelper.request());

        Assertions.assertThat(enderecoCadastrado).isNotNull();

        verify(repository, times(1)).save(TestHelper.endereco());
    }

    @Test
    @DisplayName("Deve cadastrar um endereco buscando dados longetude latitude na api do Google Maps")
    public void devCadastrarUmEnderecoBuscandoDadosLongetudeLatitudeNaApiDoGoogleMaps() throws IOException {
        final var request = TestHelper.request();
        request.setLatitude("");
        request.setLongitude("");

        when(repository.save(any(Endereco.class))).thenReturn(TestHelper.endereco());
        when(mapper.map(any(EnderecoRequest.class), eq(Endereco.class))).thenReturn(TestHelper.endereco());
        when(enderecoGoogleApiService.buscarDadosEndereco(any(EnderecoRequest.class))).thenReturn(TestHelper.responseGoogleApiOK());

        final var enderecoCadastrado = service.cadastrar(request);

        Assertions.assertThat(enderecoCadastrado).isNotNull();
        Assertions.assertThat(enderecoCadastrado.getLatitude()).isNotNull();
        Assertions.assertThat(enderecoCadastrado.getLongitude()).isNotNull();

        verify(repository, times(1)).save(TestHelper.endereco());
        verify(enderecoGoogleApiService, times(1)).buscarDadosEndereco(request);
    }
}
