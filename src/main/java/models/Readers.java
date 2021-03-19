package models;

import javax.persistence.*;

@Entity
@Table(name = "public.readers")
public class Readers {

  @Id
  @Column(name = "library_card_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long libraryCardId;
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

  public long getLibraryCardId() {
    return libraryCardId;
  }
  public void setLibraryCardId(long libraryCardId) {
    this.libraryCardId = libraryCardId;
  }

  public String getSecondName() {
    return secondName;
  }
  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getPatronymic() {
    return patronymic;
  }
  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }

  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public boolean getLibCardPassed() {
    return libCardPassed;
  }
  public void setLibCardPassed(boolean libCardPassed) {
    this.libCardPassed = libCardPassed;
  }
}
