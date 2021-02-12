package dev.bstk.stoom.endereco.api;

import dev.bstk.stoom.endereco.api.request.EnderecoRequest;
import dev.bstk.stoom.endereco.api.response.EnderecoResponse;
import dev.bstk.stoom.endereco.domain.EnderecoRepository;
import dev.bstk.stoom.helper.ModelMapperHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/enderecos")
public class EnderecoResource {

    private final ModelMapperHelper mapper;
    private final EnderecoRepository repository;

    @Autowired
    public EnderecoResource(final ModelMapperHelper mapper,
                            final EnderecoRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }


    @GetMapping
    public ResponseEntity<List<EnderecoResponse>> buscarTodos() {
        final var enderecos = repository.findAll();
        final var enderecosResponse = mapper.mapList(enderecos, EnderecoResponse.class);
        return ResponseEntity.ok(enderecosResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponse> buscarPorId(@PathVariable("id") final Long enderecoId) {
        final var enderecoOptional = repository.findById(enderecoId);

        if (enderecoOptional.isPresent()) {
            final var endereco = enderecoOptional.get();
            final var enderecoResponse = mapper.map(endereco, EnderecoResponse.class);
            return ResponseEntity.ok(enderecoResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EnderecoResponse> cadastrar(@RequestBody @Valid final EnderecoRequest endereco,
                                                      final HttpServletResponse response) {
        log.info("Cadastrando endereço = {}", endereco);
        final var enderecoResponse = new EnderecoResponse();

        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(enderecoResponse.getId())
            .toUri();

        response.setStatus(HttpStatus.CREATED.value());
        response.setHeader(HttpHeaders.LOCATION, uri.toASCIIString());

        return ResponseEntity.ok(enderecoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponse> atualizar(@PathVariable("id") final Long enderecoId,
                                                      @RequestBody @Valid final EnderecoRequest endereco) {
        log.info("Atualizando endereço = {}", endereco);

        final var enderecoResponse = new EnderecoResponse();
        return ResponseEntity.ok(enderecoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") final Long id) {
        log.info("Excluindo id = {}", id);
        return ResponseEntity.noContent().build();
    }
}
