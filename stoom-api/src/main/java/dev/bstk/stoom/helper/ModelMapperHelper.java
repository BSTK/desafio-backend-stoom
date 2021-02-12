package dev.bstk.stoom.helper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapperHelper {

    private final ModelMapper mapper;

    @Autowired
    public ModelMapperHelper(final ModelMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T map(final Object source, final Class<T> clazz) {
        return mapper.map(source, clazz);
    }

    public <S, T> List<T> mapList(final List<S> source, final Class<T> clazz) {
        return source
            .stream()
            .map(element -> mapper.map(element, clazz))
            .collect(Collectors.toList());
    }

}
