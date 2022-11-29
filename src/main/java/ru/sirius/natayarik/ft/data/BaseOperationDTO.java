package ru.sirius.natayarik.ft.data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Yaroslav Ilin
 */


public class BaseOperationDTO extends BaseDTO{
    protected long accountId;
    protected BigDecimal amount;
    protected ZonedDateTime creationDate;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
