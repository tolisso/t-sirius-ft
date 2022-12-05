package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.UserConverter;
import ru.sirius.natayarik.ft.data.Type;
import ru.sirius.natayarik.ft.data.UserDTO;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.CategoryEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.CategoryRepository;
import ru.sirius.natayarik.ft.repository.UserRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * @author Yaroslav Ilin
 */

@Service
public class InitializationUserService {
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final CategoryRepository categoryRepository;

    public InitializationUserService(
            AccountService accountService,
            AccountRepository accountRepository,
            UserRepository userRepository,
            UserConverter userConverter,
            CategoryRepository categoryRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
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
            accountService.create(account);
        }
    }

    private void createDefaultCategories(UserEntity userEntity) {
        if (categoryRepository.findAllByUser(userEntity).isEmpty()) {
            saveCategory(userEntity, "Зарплата", Type.INCOME);
            saveCategory(userEntity, "Подработка", Type.INCOME);
            saveCategory(userEntity, "Подарок", Type.INCOME);
            saveCategory(userEntity, "Капитализация", Type.INCOME);
            saveCategory(userEntity, "Супермаркеты", Type.OUTCOME);
            saveCategory(userEntity, "Переводы", Type.OUTCOME);
            saveCategory(userEntity, "Транспорт", Type.OUTCOME);
            saveCategory(userEntity, "Другое", Type.OUTCOME);
        }
    }

    private void saveCategory(UserEntity userEntity, String name, Type type) {
        categoryRepository.save(new CategoryEntity(userEntity, name, type));
    }
}
