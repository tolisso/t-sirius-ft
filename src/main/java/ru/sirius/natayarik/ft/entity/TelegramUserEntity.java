package ru.sirius.natayarik.ft.entity;

import ru.sirius.natayarik.ft.botapi.BotState;

import javax.persistence.*;

/**
 * @author Egor Malko
 */
@Entity
@Table(name = "telegram_users")
public class TelegramUserEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private BotState state;

    public TelegramUserEntity() {

    }

    public TelegramUserEntity(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BotState getState() {
        return state;
    }

    public void setState(BotState state) {
        this.state = state;
    }
}
