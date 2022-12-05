package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.AccountConverter;
import ru.sirius.natayarik.ft.data.AccountCreateDTO;
import ru.sirius.natayarik.ft.data.AccountDTO;
import ru.sirius.natayarik.ft.data.Role;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.UserToAccountEntity;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.exception.PermissionDeniedException;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.UserToAccountRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yaroslav Ilin
 */

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final CurrentUserService currentUserService;
    private final UserToAccountRepository userToAccountRepository;


    public AccountService(AccountRepository accountRepository, AccountConverter accountConverter, UserService userService, CurrentUserService currentUserService, UserToAccountRepository userToAccountRepository) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
        this.currentUserService = currentUserService;
        this.userToAccountRepository = userToAccountRepository;
    }

    @Transactional
    public AccountDTO getAccountById(final long id) {
        return accountConverter.convertToDTO(safeGetAccount(id));
    }

    public AccountDTO create(final AccountCreateDTO accountDTO) {
        return accountConverter.convertToDTO(create(accountConverter.convertFromCreateDTO(accountDTO)));
    }

    @Transactional
    public AccountEntity create(final AccountEntity accountEntity) {
        AccountEntity result = accountRepository.save(accountEntity);
        userToAccountRepository.save(new UserToAccountEntity(currentUserService.getUser(), result, Role.OWNER));
        return result;
    }

    public List<AccountDTO> getAll() {
        return userToAccountRepository.
                findAllByUser(currentUserService.getUser())
                .stream()
                .map(entity -> accountConverter.convertToDTO(entity.getAccount()))
                .collect(Collectors.toList());

    }

    @Transactional
    public void delete(long accountId) {
        accountRepository.delete(safeGetAccount(accountId));
    }

    @Transactional
    public AccountDTO change(final AccountDTO accountDTO) {
        safeGetAccount(accountDTO.getId());
        return accountConverter.convertToDTO(accountRepository.save(accountConverter.convertToEntity(accountDTO)));
    }

    private AccountEntity safeGetAccount(final long accountId) {
        AccountEntity account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundDataException("Not found account"));
        if (userToAccountRepository.findByAccountAndUser(account, currentUserService.getUser()) == null) {
            throw new PermissionDeniedException("User doesn't have access to this account.");
        }
        return account;
    }
}
