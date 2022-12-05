package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.CategoryConverter;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.Type;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.repository.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yaroslav Ilin
 */

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final CategoryConverter categoryConverter;
    private final CurrentUserService currentUserService;

    public CategoryService(CategoryRepository categoryRepository, UserService userService, CategoryConverter categoryConverter, CurrentUserService currentUserService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.categoryConverter = categoryConverter;
        this.currentUserService = currentUserService;
    }

    public CategoryDTO create(CategoryDTO category) {
        return categoryConverter.convertToDTO(categoryRepository.save(categoryConverter.convertToEntity(category)));
    }

    public List<CategoryDTO> getAll(Type type) {
       return categoryRepository
               .findAllByTypeAndUser(
                       type, currentUserService.getUser())
               .stream()
               .map(categoryConverter::convertToDTO)
               .collect(Collectors.toList());

    }

    public CategoryDTO getFromId(long categoryId) {
        return categoryConverter.convertToDTO(categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundDataException("Not found category")));
    }

    @Transactional
    public void delete(long categoryId) {
        categoryRepository.delete(categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundDataException("Not found category")));
    }

    public CategoryDTO change(CategoryDTO category) {
        return categoryConverter.convertToDTO(categoryRepository.save(categoryConverter.convertToEntity(category)));
    }
}

