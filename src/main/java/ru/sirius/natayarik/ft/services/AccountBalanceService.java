package ru.sirius.natayarik.ft.services;

import org.springframework.stereotype.Service;
import ru.sirius.natayarik.ft.data.TypeDTO;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.entity.OperationEntity;
import ru.sirius.natayarik.ft.exception.NotFoundDataException;
import ru.sirius.natayarik.ft.repository.AccountRepository;
import ru.sirius.natayarik.ft.repository.OperationRepository;

import java.math.BigDecimal;

/**
 * @author Egor Malko
 */
@Service
public class AccountBalanceService {
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;

    public AccountBalanceService(OperationRepository operationRepository, AccountRepository accountRepository) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
    }

    public BigDecimal getSumByType(long accountId, TypeDTO type) {
        return operationRepository.findAllByAccount(accountRepository.findById(accountId).orElseThrow(() -> new NotFoundDataException("Don't find account by id")))
                .stream()
                .filter((operation) -> operation.getCategory().getType() == type)
                .map((OperationEntity::getAmount))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
    }

    public void updateBalance(long accountId) {
        AccountEntity account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundDataException("Don't find account by id"));
        account.setBalance(getSumByType(accountId, TypeDTO.INCOME).subtract(getSumByType(accountId, TypeDTO.OUTCOME)));
        accountRepository.save(account);
    }
}
