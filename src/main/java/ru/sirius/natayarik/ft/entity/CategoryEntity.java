package ru.sirius.natayarik.ft.entity;

import ru.sirius.natayarik.ft.data.TypeDTO;

import javax.persistence.*;

/**
 * @author Egor Malko
 */
@Entity
@Table(name = "categories")
@SequenceGenerator(allocationSize = 1, name = "category_seq", sequenceName = "category_seq")
public class CategoryEntity {
   @Id
   @GeneratedValue(generator = "category_seq")
   @Column(name = "id")
   private long id;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private UserEntity user;
   @Column(name = "name")
   private String name;
   @Enumerated(value = EnumType.STRING)
   @Column(name = "type")
   private TypeDTO typeDTO;

   public CategoryEntity() {

   }

   public CategoryEntity(UserEntity user, String name, TypeDTO typeDTO) {
      this.user = user;
      this.name = name;
      this.typeDTO = typeDTO;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public UserEntity getUser() {
      return user;
   }

   public void setUser(UserEntity user) {
      this.user = user;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public TypeDTO getType() {
      return typeDTO;
   }

   public void setType(TypeDTO typeDTO) {
      this.typeDTO = typeDTO;
   }
}
