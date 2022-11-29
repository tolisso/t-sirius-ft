package ru.sirius.natayarik.ft.data;

/**
 * @author Egor Malko
 */

public class AccountDTO {

    private long id;
    private long userId;
    private String name;
    private CurrencyDTO currency;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
