package home.local.vtbtest.mapper;

import home.local.vtbtest.dto.AbstractDto;
import home.local.vtbtest.entity.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);
}