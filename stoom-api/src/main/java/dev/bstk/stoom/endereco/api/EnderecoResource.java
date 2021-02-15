package dev.bstk.stoom.endereco.api;

import dev.bstk.stoom.endereco.api.request.EnderecoRequest;
import dev.bstk.stoom.endereco.api.response.EnderecoResponse;
import dev.bstk.stoom.endereco.domain.EnderecoService;
import dev.bstk.stoom.endereco.domain.model.EnderecoRepository;
import dev.bstk.stoom.helper.ModelMapperHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/enderecos")
public class EnderecoResource {

    private final EnderecoService service;
    private final ModelMapperHelper mapper;
    private final EnderecoRepository repository;

    @Autowired
    public EnderecoResource(final EnderecoService service,
                            final ModelMapperHelper mapper,
                            final EnderecoRepository repository) {
        this.mapper = mapper;
        this.service = service;
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
    public ResponseEntity<EnderecoResponse> cadastrar(@RequestBody @Valid final EnderecoRequest enderecoRequest) {
        final var enderecoCadastrado = service.cadastrar(enderecoRequest);
        final var enderecoResponse = mapper.map(enderecoCadastrado, EnderecoResponse.class);

        final var uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(enderecoResponse.getId())
            .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponse> atualizar(@PathVariable("id") final Long enderecoId,
                                                      @RequestBody @Valid final EnderecoRequest enderecoRequest) {
        final var enderecoOptional = service.atualizar(enderecoId, enderecoRequest);

        if (enderecoOptional.isPresent()) {
            final var enderecoAtualizado = enderecoOptional.get();
            final var enderecoResponse = mapper.map(enderecoAtualizado, EnderecoResponse.class);
            return ResponseEntity.ok(enderecoResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") final Long enderecoId) {
        final var enderecoOptional = repository.findById(enderecoId);

        if (enderecoOptional.isPresent()) {
            repository.deleteById(enderecoId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
