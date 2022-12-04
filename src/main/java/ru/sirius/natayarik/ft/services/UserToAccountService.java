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
import ru.sirius.natayarik.ft.exception.PermissionDeniedException;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.CategoryRepository;
import ru.sirius.natayarik.ft.repository.UserRepository;
import ru.sirius.natayarik.ft.repository.UserToAccountRepository;

import javax.transaction.Transactional;
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


    @Transactional
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

    @Transactional
    public void addUserToAccount(long accountId, String sharedUserName) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundDataException("Not found account"));
        UserToAccountEntity userToAccountEntity = userToAccountRepository.findByAccountAndRole(accountEntity, RoleDTO.OWNER);
        if (userToAccountEntity.getUser().getId() != currentUserService.getUser().getId()) {
            throw new PermissionDeniedException("Current user don't have permission to invite users to this account"); // TODO
        }
        UserToAccountEntity created = new UserToAccountEntity();
        created.setAccount(accountEntity);
        UserEntity sharedUser = userRepository.findByName(sharedUserName);
        if (sharedUser == null) {
            throw new NotFoundDataException("Not found user with shared username");
        }
        created.setUser(sharedUser);
        created.setRole(RoleDTO.MEMBER);
        userToAccountRepository.save(created);
    }

    public boolean checkPermissionCurrentUserWithAccount(AccountEntity accountEntity) {
        return userToAccountRepository.findByAccountAndUser(accountEntity, currentUserService.getUser()) != null;
    }
}
