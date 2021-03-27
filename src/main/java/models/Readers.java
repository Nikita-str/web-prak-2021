package models;

import javax.persistence.*;

@Entity
@Table(name = "readers")
public class Readers {

  @Id
  @Column(name = "library_card_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer libraryCardId;
  @Column(name = "second_name", nullable = false)
  private String secondName;
  @Column(name = "first_name", nullable = false)
  private String firstName;
  @Column(name = "patronymic")
  private String patronymic;
  @Column(name = "address")
  private String address;
  @Column(name = "phone_number")
  private String phoneNumber;
  @Column(name = "lib_card_passed")
  private boolean libCardPassed;

  public Integer getLibraryCardId() {
    return libraryCardId;
  }

  public String getSecondName() {
    return secondName;
  }
  public String getFirstName() {
    return firstName;
  }
  public String getPatronymic() {
    return patronymic;
  }
  public String getAddress() {
    return address;
  }
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public boolean getLibCardPassed() {
    return libCardPassed;
  }

  @Override
  public String toString(){
    String dop_str = "";
    if(libCardPassed)dop_str = "(Passed)";
    return "Readers {Lib Card Id: " + libraryCardId + " " + libCardPassed +
            " | name: " + firstName + " | second name: " + secondName + " | patronymic :" + patronymic +
            " | address: " + address + " | phone: " + phoneNumber + "}";
  }
}
