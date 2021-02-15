package dev.bstk.stoom.endereco.api;

import dev.bstk.stoom.endereco.api.request.EnderecoRequest;
import dev.bstk.stoom.endereco.api.response.EnderecoResponse;
import dev.bstk.stoom.endereco.domain.EnderecoService;
import dev.bstk.stoom.endereco.domain.model.Endereco;
import dev.bstk.stoom.endereco.domain.model.EnderecoRepository;
import dev.bstk.stoom.endereco.helper.TestHelper;
import dev.bstk.stoom.helper.ModelMapperHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnderecoResource.class)
public class EnderecoResourceTest {

    private static final String ENDPOINT_API_V1_ENDERECOS = "/api/v1/enderecos";
    private static final String ENDPOINT_API_V1_ENDERECOS_ID = "/api/v1/enderecos/{id}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnderecoService service;

    @MockBean
    private ModelMapperHelper mapper;

    @MockBean
    private EnderecoRepository repository;

    @Test
    @DisplayName("Deve cadastrar um novo endereço")
    public void deveCadastrarUmNovoEndereco() throws Exception {
        when(mapper.map(any(Endereco.class), eq(EnderecoResponse.class))).thenReturn(TestHelper.response());
        when(service.cadastrar(any(EnderecoRequest.class))).thenReturn(TestHelper.endereco());

        this.mockMvc.perform(
            post(ENDPOINT_API_V1_ENDERECOS)
            .content(TestHelper.enderecoRequest())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(header().exists(HttpHeaders.LOCATION));
    }

    @Test
    @DisplayName("Deve atualizar um endereço existente")
    public void deveAtualizarUmEnderecoExistente() throws Exception {
        final var enderecoOptional = Optional.of(TestHelper.endereco());

        when(mapper.map(any(EnderecoRequest.class), eq(Endereco.class))).thenReturn(TestHelper.endereco());
        when(mapper.map(any(Endereco.class), eq(EnderecoResponse.class))).thenReturn(TestHelper.response());
        when(service.atualizar(anyLong(), any(EnderecoRequest.class))).thenReturn(enderecoOptional);

        this.mockMvc.perform(
            put(ENDPOINT_API_V1_ENDERECOS_ID, enderecoOptional.get().getId())
                .content(TestHelper.enderecoRequest())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar status 404 ao tentar atualizar endereço")
    public void deveAtualizarUmEnderecoExistenteStatus404() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        this.mockMvc.perform(
            put(ENDPOINT_API_V1_ENDERECOS_ID, 1000)
                .content(TestHelper.enderecoRequest())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve buscar todos os enderecos cadastrados")
    public void deveBuscarTodosOsEnderecosCadastrados() throws Exception {
        final var mockEnderecos = TestHelper.enderecos();

        when(repository.findAll()).thenReturn(mockEnderecos);
        when(mapper.mapList(anyList(), eq(EnderecoResponse.class))).thenReturn(TestHelper.responses());

        this.mockMvc.perform(
            get(ENDPOINT_API_V1_ENDERECOS))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", equalTo(mockEnderecos.size())));
    }

    @Test
    @DisplayName("Deve buscar um endereco por id")
    public void deveBuscarUmEnderecoPorId() throws Exception {
        final var enderecoResponse = TestHelper.response();
        final var enderecoOptional = Optional.of(TestHelper.endereco());

        when(repository.findById(anyLong())).thenReturn(enderecoOptional);
        when(mapper.map(any(Endereco.class), eq(EnderecoResponse.class))).thenReturn(enderecoResponse);

        this.mockMvc.perform(
            get(ENDPOINT_API_V1_ENDERECOS_ID, enderecoResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.zipcode").value(enderecoResponse.getCep()))
            .andExpect(jsonPath("$.city").value(enderecoResponse.getCidade()))
            .andExpect(jsonPath("$.state").value(enderecoResponse.getEstado()))
            .andExpect(jsonPath("$.country").value(enderecoResponse.getPais()))
            .andExpect(jsonPath("$.number").value(enderecoResponse.getNumero()))
            .andExpect(jsonPath("$.streetName").value(enderecoResponse.getRua()))
            .andExpect(jsonPath("$.latitude").value(enderecoResponse.getLatitude()))
            .andExpect(jsonPath("$.longitude").value(enderecoResponse.getLongitude()))
            .andExpect(jsonPath("$.neighbourhood").value(enderecoResponse.getBairro()))
            .andExpect(jsonPath("$.complement").value(enderecoResponse.getComplemento()));
    }

    @Test
    @DisplayName("Deve retornar status 404 ao tentar buscar endereço")
    public void deveBuscarUmEnderecoPorIdStatus404() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        this.mockMvc.perform(
            get(ENDPOINT_API_V1_ENDERECOS_ID, 1000))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve excluir um endereço dado um id")
    public void deveExcluirUmEnderecoDadoUmId() throws Exception {
        final var enderecoResponse = TestHelper.response();
        final var enderecoOptional = Optional.of(TestHelper.endereco());

        when(repository.findById(anyLong())).thenReturn(enderecoOptional);
        when(mapper.mapList(anyList(), eq(EnderecoResponse.class))).thenReturn(TestHelper.responses());

        this.mockMvc.perform(
            delete(ENDPOINT_API_V1_ENDERECOS_ID, enderecoResponse.getId()))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar status 404 ao tentar excluir endereço")
    public void deveExcluirUmEnderecoDadoUmIdStatus404() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        this.mockMvc.perform(
            delete(ENDPOINT_API_V1_ENDERECOS_ID, 1000))
            .andExpect(status().isNotFound());
    }

}
