package models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Authors {

  @Id
  @Column(name = "author_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer authorId;
  @Column(name = "second_name", nullable = false)
  private String secondName;
  @Column(name = "first_name", nullable = false)
  private String firstName;
  @Column(name = "patronymic")
  private String patronymic;

  public Authors() { }

  public Authors(String s_name, String f_name, String patr)
  {
    this.secondName = s_name;
    this.firstName = f_name;
    this.patronymic = patr;
  }

  public Integer getAuthorId() { return authorId; }

  public String getSecondName() {
    return secondName;
  }
  public String getFirstName() {
    return firstName;
  }
  public String getPatronymic() {
    return patronymic;
  }

  @Override
  public String toString(){
    return "Author {Id: " + authorId + " | name: " + firstName +
            " | second name: " + secondName + " | patronymic :" + patronymic + "}";
  }
}
