package dev.bstk.stoom.endereco.domain;

import dev.bstk.stoom.endereco.api.request.EnderecoRequest;
import dev.bstk.stoom.endereco.domain.integracao.EnderecoDadosGeometrico;
import dev.bstk.stoom.endereco.domain.model.Endereco;
import dev.bstk.stoom.endereco.domain.model.EnderecoRepository;
import dev.bstk.stoom.helper.ModelMapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class EnderecoService {

    private final ModelMapperHelper mapper;
    private final EnderecoRepository repository;
    private final EnderecoGoogleApiService enderecoGoogleApiService;

    @Autowired
    public EnderecoService(final ModelMapperHelper mapper,
                           final EnderecoRepository repository,
                           final EnderecoGoogleApiService enderecoGoogleApiService) {
        this.mapper = mapper;
        this.repository = repository;
        this.enderecoGoogleApiService = enderecoGoogleApiService;
    }

    public Endereco cadastrar(final EnderecoRequest enderecoRequest) {
        final var enderecoRequestValidada = validarLongetudeLalitude(enderecoRequest);
        final var endereco = mapper.map(enderecoRequestValidada, Endereco.class);

        return repository.save(endereco);
    }

    public Optional<Endereco> atualizar(final Long enderecoId,
                              final EnderecoRequest enderecoRequest) {
        final var enderecoOptional = repository.findById(enderecoId);

        if (enderecoOptional.isPresent()) {
            final var enderecoRequestValidada = validarLongetudeLalitude(enderecoRequest);
            final var endereco = mapper.map(enderecoRequestValidada, Endereco.class);
            endereco.setId(enderecoId);

            final var enderecoCadastrado = repository.save(endereco);

            return Optional.of(enderecoCadastrado);
        }

        return Optional.empty();
    }

    private EnderecoRequest validarLongetudeLalitude(final EnderecoRequest enderecoRequest) {
        if (verificaLongetudeLalitude(enderecoRequest)) {
            final var dadosEndereco = enderecoGoogleApiService.buscarDadosEndereco(enderecoRequest);
            final var dadosGeometrico = new EnderecoDadosGeometrico(dadosEndereco);

            enderecoRequest.setLongitude(dadosGeometrico.getLongetude());
            enderecoRequest.setLatitude(dadosGeometrico.getLatitude());
        }

        return enderecoRequest;
    }

    private boolean verificaLongetudeLalitude(final EnderecoRequest enderecoRequest) {
        return !StringUtils.hasLength(enderecoRequest.getLatitude())
            || !StringUtils.hasLength(enderecoRequest.getLongitude());
    }

}
