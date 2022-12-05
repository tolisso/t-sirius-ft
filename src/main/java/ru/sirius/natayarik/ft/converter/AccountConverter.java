package ru.sirius.natayarik.ft.converter;

import org.springframework.stereotype.Component;
import ru.sirius.natayarik.ft.data.AccountDTO;
import ru.sirius.natayarik.ft.data.Currency;
import ru.sirius.natayarik.ft.data.Type;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.repository.UserRepository;
import ru.sirius.natayarik.ft.services.AccountBalanceService;


/**
 * @author Egor Malko
 */
@Component
public class AccountConverter {
    private final UserRepository userRepository;
    private final AccountBalanceService accountBalanceService;

    public AccountConverter(UserRepository userRepository, AccountBalanceService accountBalanceService) {
        this.userRepository = userRepository;
        this.accountBalanceService = accountBalanceService;
    }

    public AccountDTO convertToDTO(AccountEntity accountEntity) {
        AccountDTO result = new AccountDTO();
        result.setId(accountEntity.getId());
        result.setName(accountEntity.getName());
        result.setUserId(accountEntity.getUser().getId());
        result.setCurrency(Currency.RUSSIAN_RUBLE);
        result.setBalance(accountEntity.getBalance());

        result.setIncome(accountBalanceService.getSumByType(accountEntity.getId(), Type.INCOME));
        result.setOutcome(accountBalanceService.getSumByType(accountEntity.getId(), Type.OUTCOME));
        return result;
    }

    public AccountEntity convertToEntity(AccountDTO accountDTO) {
        AccountEntity result = new AccountEntity();
        result.setId(accountDTO.getId());
        result.setName(accountDTO.getName());
        result.setUser(userRepository.findById(accountDTO.getUserId()).orElseThrow(() -> new NotFoundDataException("not found user by id " + accountDTO.getUserId())));
        result.setBalance(accountDTO.getBalance());
        return result;
    }
}
