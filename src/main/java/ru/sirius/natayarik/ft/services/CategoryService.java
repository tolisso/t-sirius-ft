package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.CategoryConverter;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.TypeDTO;
import ru.sirius.natayarik.ft.repository.CategoryRepository;

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

   public CategoryService(CategoryRepository categoryRepository, UserService userService, CategoryConverter categoryConverter) {
      this.categoryRepository = categoryRepository;
      this.userService = userService;
      this.categoryConverter = categoryConverter;
   }

   public CategoryDTO create(CategoryDTO category) {
      return categoryConverter.convertToDTO(categoryRepository.save(categoryConverter.convertToEntity(category)));
   }

   public List<CategoryDTO> getAll(TypeDTO typeDTO, long userId) {
        /*return List.of(new CategoryDTO(0, 0, "Salary", TypeDTO.INCOME),
                new CategoryDTO(1, 228, "Gift", TypeDTO.OUTCOME),
                new CategoryDTO(2, 123, "Medicine", TypeDTO.OUTCOME));*/
      return categoryRepository.findAllByTypeDTOAndUser(typeDTO, userService.getUserFromId(userId)).stream().map(categoryConverter::convertToDTO).collect(Collectors.toList());
   }

   public CategoryDTO getFromId(long categoryId) {
      //return new CategoryDTO(categoryId, 324124, "KEKLOL", TypeDTO.INCOME);
      return categoryConverter.convertToDTO(categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("not found category by id")));
   }

   public void delete(long categoryId) {
      categoryRepository.delete(categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("not found category by id")));
   }

   public CategoryDTO change(CategoryDTO category) {
      return categoryConverter.convertToDTO(categoryRepository.save(categoryConverter.convertToEntity(category)));
   }
}

