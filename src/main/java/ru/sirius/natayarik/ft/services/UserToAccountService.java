package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.CategoryConverter;
import ru.sirius.natayarik.ft.data.CategoryDTO;
import ru.sirius.natayarik.ft.data.RoleDTO;
import ru.sirius.natayarik.ft.data.TypeDTO;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.UserEntity;
import ru.sirius.natayarik.ft.entity.UserToAccountEntity;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.CategoryRepository;
import ru.sirius.natayarik.ft.repository.UserRepository;
import ru.sirius.natayarik.ft.repository.UserToAccountRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yaroslav Ilin
 */

@Service
public class UserToAccountService {
    private final UserToAccountRepository userToAccountRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryConverter categoryConverter;
    private final CurrentUserService currentUserService;

    public UserToAccountService(UserToAccountRepository userToAccountRepository,
                                AccountRepository accountRepository,
                                CategoryRepository categoryRepository,
                                UserRepository userRepository,
                                CategoryConverter categoryConverter,
                                CurrentUserService currentUserService) {
        this.userToAccountRepository = userToAccountRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.categoryConverter = categoryConverter;
        this.currentUserService = currentUserService;
    }


    public List<CategoryDTO> getAllCategoriesFromAccount(long accountId, TypeDTO typeDto) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundDataException("Not found account"));
        UserToAccountEntity userToAccountEntity = userToAccountRepository.findByAccountAndRole(account, RoleDTO.OWNER);
        if (userToAccountEntity == null) {
            throw new NotFoundDataException("Not found mapping user to account entity");
        }
        UserEntity user = userRepository.findById(userToAccountEntity.getUser().getId())
                .orElseThrow(() -> new NotFoundDataException("Not found account owner"));
        return categoryRepository.findAllByTypeDTOAndUser(typeDto, user)
                .stream().map(categoryConverter::convertToDTO).collect(Collectors.toList());
    }

    public void addUserToAccount(long accountId) {
        UserToAccountEntity created = new UserToAccountEntity();
        created.setAccount(accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundDataException("Not found account")));
        created.setUser(currentUserService.getUser());
        created.setRole(RoleDTO.MEMBER);
        userToAccountRepository.save(created);
    }
}
