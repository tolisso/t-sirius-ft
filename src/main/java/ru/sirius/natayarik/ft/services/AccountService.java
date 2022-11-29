package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.converter.AccountConverter;
import ru.sirius.natayarik.ft.data.AccountDTO;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @author Yaroslav Ilin
 */

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final UserService userService;
    private final CurrentUserService currentUserService;


    public AccountService(AccountRepository accountRepository, AccountConverter accountConverter, UserService userService, CurrentUserService currentUserService) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
        this.userService = userService;
        this.currentUserService = currentUserService;
    }

    public AccountDTO getAccountById(final long id) {
        return accountConverter.convertToDTO(accountRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public AccountDTO create(final AccountDTO accountDTO) {
        return accountConverter.convertToDTO(accountRepository.save(accountConverter.convertToEntity(accountDTO)));
    }

    public List<AccountDTO> getAll() {
        List<AccountDTO> result = accountRepository
                .findAllByUser(userService.getUserFromId(currentUserService.getUser().getId()))
                .stream()
                .map(accountConverter::convertToDTO)
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            return List.of(accountConverter.convertToDTO(createDefaultAccount()));
        } else {
            return result;
        }
    }

    public void delete(long accountId) {
        accountRepository.delete(accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("not found account by id")));
    }

    public AccountDTO change(final AccountDTO accountDTO) {
        return accountConverter.convertToDTO(accountRepository.save(accountConverter.convertToEntity(accountDTO)));
    }

    public AccountEntity createDefaultAccount() {
        AccountEntity account = new AccountEntity();
        account.setBalance(new BigDecimal(0));
        account.setName("Кошелек 1");
        account.setUser(currentUserService.getUser());
        return accountRepository.save(account);
    }
}
