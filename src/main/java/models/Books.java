package models;


import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Books {

  @Id
  @Column(name = "book_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int bookId;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "about")
  private String about;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "publisher_id")
  private Publishers publisher;

  @Column(name = "pub_year")
  private Date pubYear;

  @Column(name = "isbn")
  private String isbn;

  @Column(name = "decommissioned")
  private boolean decommissioned;

  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Authors> authors = new HashSet<>();

  public Books(){}
  public Books(String title, String about, Publishers pub, Date pub_year, String isbn, boolean decommissioned)
  {
    this.title = title;
    this.about = about;
    publisher = pub;
    pubYear = pub_year;
    this.isbn = isbn;
    this.decommissioned = decommissioned;
  }

  public int getBookId() {
    return bookId;
  }
  //public void setBookId(long bookId) { this.bookId = bookId; }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public String getAbout() {
    return about;
  }
  public void setAbout(String about) {
    this.about = about;
  }

  public Publishers getPublisher() {
    return publisher;
  }
  public void setPublisher(Publishers publisher) { this.publisher = publisher; }

  public java.sql.Date getPubYear() {
    return pubYear;
  }
  public void setPubYear(java.sql.Date pubYear) {
    this.pubYear = pubYear;
  }

  public String getIsbn() {
    return isbn;
  }
  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public Set<Authors> getAuthors(){return authors;}

  public boolean getDecommissioned() {
    return decommissioned;
  }
  public void setDecommissioned(boolean decommissioned) {
    this.decommissioned = decommissioned;
  }

  @Override
  public String toString(){
    Calendar c = Calendar.getInstance();
    c.setTime(pubYear);
    int year = c.get(Calendar.YEAR);
    return "Book{Id: " + bookId + " | book name: " + title +
            " | about: " + ((about == null)? "---" : about.substring(0, 15) + "...") +
            " | publisher: " + ((publisher == null)? "---" : publisher.getPName()) +
            " | pub year: " + ((pubYear == null)? "---" : year) +
            " | ISBN: " + ((isbn == null)? "---" : isbn) +
            " | decommissioned:" + decommissioned + "}";
  }
}
