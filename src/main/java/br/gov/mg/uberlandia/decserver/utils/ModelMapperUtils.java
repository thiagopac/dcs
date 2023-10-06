package br.gov.mg.uberlandia.decserver.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperUtils {

    private static final ModelMapper defaultModelMapper = modelMapper();

    public static ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static <T> T map(Object source, T destination) {
        defaultModelMapper.map(source, destination);
        return destination;
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> defaultModelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
