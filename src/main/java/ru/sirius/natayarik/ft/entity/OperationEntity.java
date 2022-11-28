package ru.sirius.natayarik.ft.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Egor Malko
 */
@Entity
@Table(name = "operations")
@SequenceGenerator(allocationSize = 1, name = "operation_seq", sequenceName = "operation_seq")
public class OperationEntity {
   @Id
   @GeneratedValue(generator = "operation_seq")
   @Column(name = "id")
   private long id;

   @ManyToOne
   @JoinColumn(name = "account_id")
   private AccountEntity account;
   @Column(name = "amount")
   private BigDecimal amount;
   @ManyToOne
   @JoinColumn(name = "category_id")
   private CategoryEntity categoryEntity;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public AccountEntity getAccount() {
      return account;
   }

   public void setAccount(AccountEntity account) {
      this.account = account;
   }

   public BigDecimal getAmount() {
      return amount;
   }

   public void setAmount(BigDecimal amount) {
      this.amount = amount;
   }

   public CategoryEntity getCategory() {
      return categoryEntity;
   }

   public void setCategory(CategoryEntity categoryEntity) {
      this.categoryEntity = categoryEntity;
   }
}
