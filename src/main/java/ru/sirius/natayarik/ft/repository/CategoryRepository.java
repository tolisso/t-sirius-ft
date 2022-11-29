package ru.sirius.natayarik.ft.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sirius.natayarik.ft.data.TypeDTO;
import ru.sirius.natayarik.ft.entity.CategoryEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;

import java.util.List;

/**
 * @author Egor Malko
 */
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByTypeDTOAndUser(TypeDTO typeDTO, UserEntity user);
}
