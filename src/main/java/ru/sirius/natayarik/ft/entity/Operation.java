package ru.sirius.natayarik.ft.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Egor Malko
 */
@Entity
@Table(name = "operations")
@SequenceGenerator(allocationSize = 1, name = "operation_seq", sequenceName = "operation_seq")
public class Operation {
   @Id
   @GeneratedValue(generator = "operation_seq")
   @Column(name = "id")
   private long id;

   @ManyToOne
   @JoinColumn(name = "account_id")
   private Account account;
   @Column(name = "amount")
   private BigDecimal amount;
   @ManyToOne
   @JoinColumn(name = "category_id")
   private Category category;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public Account getAccount() {
      return account;
   }

   public void setAccount(Account account) {
      this.account = account;
   }

   public BigDecimal getAmount() {
      return amount;
   }

   public void setAmount(BigDecimal amount) {
      this.amount = amount;
   }

   public Category getCategory() {
      return category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }
}
