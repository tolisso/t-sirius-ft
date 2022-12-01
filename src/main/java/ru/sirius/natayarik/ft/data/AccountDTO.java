package ru.sirius.natayarik.ft.data;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author Egor Malko
 */

public class AccountDTO extends BaseDTO {
    @Schema(description = "Id пользователя - владельца кошелька")
    private long userId;
    @NotBlank
    @Schema(description = "Имя кошелька")
    private String name;
    @Schema(description = "Валюта") // Не на что не влияет сейчас!
    private CurrencyDTO currency;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }
}
