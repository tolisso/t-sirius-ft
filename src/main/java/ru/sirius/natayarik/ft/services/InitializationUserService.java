package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.UserConverter;
import ru.sirius.natayarik.ft.data.TypeDTO;
import ru.sirius.natayarik.ft.data.UserDTO;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.CategoryEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.CategoryRepository;
import ru.sirius.natayarik.ft.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@Service
public class InitializationUserService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CategoryRepository categoryRepository;

    public InitializationUserService(
            AccountRepository accountRepository,
            UserRepository userRepository,
            UserConverter userConverter,
            CategoryRepository categoryRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.categoryRepository = categoryRepository;
    }

    public UserEntity initializationUser(UserDTO userDTO) {
        UserEntity userEntity = userConverter.convertToEntity(userDTO);
        UserEntity user = userRepository.findByName(userEntity.getName());
        if (user == null) {
            user = userRepository.save(userEntity);
            createDefaultAccount(userEntity);
            createDefaultCategories(userEntity);
        }
        return user;
    }

    private void createDefaultAccount(UserEntity userEntity) {
        if (accountRepository.findAllByUser(userEntity).isEmpty()) {
            AccountEntity account = new AccountEntity();
            account.setBalance(new BigDecimal(0));
            account.setName("Кошелек 1");
            account.setUser(userEntity);
            accountRepository.save(account);
        }
    }

    private void createDefaultCategories(UserEntity userEntity) {
        if (categoryRepository.findAllByUser(userEntity).isEmpty()) {
            saveCategory(userEntity, "Зарплата", TypeDTO.INCOME);
            saveCategory(userEntity, "Подработка", TypeDTO.INCOME);
            saveCategory(userEntity, "Подарок", TypeDTO.INCOME);
            saveCategory(userEntity, "Капитализация", TypeDTO.INCOME);
            saveCategory(userEntity, "Супермаркеты", TypeDTO.OUTCOME);
            saveCategory(userEntity, "Переводы", TypeDTO.OUTCOME);
            saveCategory(userEntity, "Транспорт", TypeDTO.OUTCOME);
            saveCategory(userEntity, "Другое", TypeDTO.OUTCOME);
        }
    }

    private void saveCategory(UserEntity userEntity, String name, TypeDTO typeDTO) {
        categoryRepository.save(new CategoryEntity(userEntity, name, typeDTO));
    }
}
