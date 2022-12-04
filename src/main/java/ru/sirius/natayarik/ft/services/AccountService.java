package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.AccountConverter;
import ru.sirius.natayarik.ft.data.AccountDTO;
import ru.sirius.natayarik.ft.data.RoleDTO;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.UserToAccountEntity;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.UserToAccountRepository;

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

    public AccountDTO getAccountById(final long id) {
        return accountConverter.convertToDTO(accountRepository.findById(id).orElseThrow(() -> new NotFoundDataException("Not found account")));
    }

    public AccountDTO create(final AccountDTO accountDTO) {
        AccountEntity result = accountRepository.save(accountConverter.convertToEntity(accountDTO));
        userToAccountRepository.save(new UserToAccountEntity(currentUserService.getUser(), result, RoleDTO.OWNER));
        return accountConverter.convertToDTO(result);
    }

    public List<AccountDTO> getAll() {
        return accountRepository
                .findAllByUser(currentUserService.getUser())
                .stream()
                .map(accountConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public void delete(long accountId) {
        accountRepository.delete(accountRepository.findById(accountId).orElseThrow(() -> new NotFoundDataException("Not found account")));
    }

    public AccountDTO change(final AccountDTO accountDTO) {
        return accountConverter.convertToDTO(accountRepository.save(accountConverter.convertToEntity(accountDTO)));
    }
}
