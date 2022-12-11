package ru.sirius.natayarik.ft.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.Type;
import ru.sirius.natayarik.ft.services.CategoryService;
import ru.sirius.natayarik.ft.services.UserToAccountService;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties= {"spring.autoconfigure.exclude=org.telegram.telegrambots.starter.TelegramBotStarterConfiguration"})
public class CategoryControllerTest {
    private final CategoryController categoryController;

    private final CategoryService categoryService;
    private final UserToAccountService userToAccountService;

    public CategoryControllerTest() {
        this.categoryService = Mockito.mock(CategoryService.class);
        this.userToAccountService = Mockito.mock(UserToAccountService.class);

        this.categoryController = new CategoryController(categoryService, userToAccountService);
    }

    @Test
    public void testGetCategoryFromId() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1);
        categoryDTO.setName("name");
        categoryDTO.setUserId(100);
        categoryDTO.setType(Type.OUTCOME);

        Mockito.when(categoryService.getFromId(1)).thenReturn(categoryDTO);

        Assertions.assertEquals(categoryDTO, categoryController.getCategoryFromId(1));
    }


}
