package ru.sirius.natayarik.ft.entity;

import ru.sirius.natayarik.ft.botapi.BotState;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Egor Malko
 */
@Entity
@Table(name = "telegram_users")
public class TelegramUserEntity implements Serializable {
    @Id
    @Column(name = "telegram_user_id", nullable = false)
    private String userId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private BotState state;

    @OneToOne
    @JoinColumn(name = "current_account_id")
    private AccountEntity accountEntity;

    public TelegramUserEntity() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public BotState getState() {
        return state;
    }

    public void setState(BotState state) {
        this.state = state;
    }
}