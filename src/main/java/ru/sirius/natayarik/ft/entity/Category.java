package ru.sirius.natayarik.ft.entity;

import ru.sirius.natayarik.ft.data.Type;

import javax.persistence.*;

/**
 * @author Egor Malko
 */
@Entity
@Table(name = "categories")
@SequenceGenerator(allocationSize = 1, name = "category_seq", sequenceName = "category_seq")
public class Category {
   @Id
   @GeneratedValue(generator = "category_seq")
   @Column(name = "id")
   private long id;

   @JoinColumn(name = "user_id")
   @ManyToOne
   private User user;

   @Column(name = "name")
   private String name;

   @Enumerated(value = EnumType.STRING)
   @Column(name = "type")
   private Type type;

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

   public Type getType() {
      return type;
   }

   public void setType(Type type) {
      this.type = type;
   }
}
