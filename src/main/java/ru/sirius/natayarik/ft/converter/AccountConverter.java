package ru.sirius.natayarik.ft.converter;

import org.springframework.stereotype.Component;
import ru.sirius.natayarik.ft.data.AccountDTO;
import ru.sirius.natayarik.ft.entity.AccountEntity;
import ru.sirius.natayarik.ft.repository.UserRepository;

/**
 * @author Egor Malko
 */
@Component
public class AccountConverter {
    private final UserRepository userRepository;

    public AccountConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AccountDTO convertToDTO(AccountEntity accountEntity) {
        AccountDTO result = new AccountDTO();
        result.setId(accountEntity.getId());
        result.setName(accountEntity.getName());
        result.setUserId(accountEntity.getUser().getId());
        //result.setCurrency();
        return result;
    }

    public AccountEntity convertToEntity(AccountDTO accountDTO) {
        AccountEntity result = new AccountEntity();
        result.setId(accountDTO.getId());
        result.setName(accountDTO.getName());
        result.setUser(userRepository.findById(accountDTO.getUserId()).orElseThrow(() -> new RuntimeException("not found user by id " + accountDTO.getUserId())));//TODO разобраться с currency и balance
        return result;
    }
}
