package dev.bstk.stoom.endereco.api;

import dev.bstk.stoom.endereco.api.response.EnderecoResponse;
import dev.bstk.stoom.endereco.helper.TestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnderecoResourceITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void deveBuscarUmEnderecoDadoUmIdValido() {
        Assertions
            .assertThat(this.restTemplate.getForObject(
                TestHelper.localhost(port, "/api/v1/enderecos/2"), EnderecoResponse.class))
            .isNotNull()
            .hasFieldOrProperty("rua")
            .hasFieldOrProperty("numero")
            .hasFieldOrProperty("complemento")
            .hasFieldOrProperty("bairro");
    }

}
