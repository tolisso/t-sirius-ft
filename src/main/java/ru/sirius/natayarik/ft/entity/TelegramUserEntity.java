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
    private Long userId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private BotState state;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BotState getState() {
        return state;
    }

    public void setState(BotState state) {
        this.state = state;
    }
}
