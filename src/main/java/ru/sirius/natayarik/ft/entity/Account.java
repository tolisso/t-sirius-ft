package ru.sirius.natayarik.ft.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Egor Malko
 */
@Entity
@Table(name = "accounts")
@SequenceGenerator(allocationSize = 1, name = "account_seq", sequenceName = "account_seq")
public class Account {
    @Id
    @GeneratedValue(generator = "account_seq")
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "name")
    private String name;
    @Column(name = "balance")
    private BigDecimal balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
