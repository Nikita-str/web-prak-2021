package models;


import DAO.StdImpl.StdDAO_Factory;

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

  public String getTitle() {
    return title;
  }
  public String getAbout() {
    return about;
  }
  public Publishers getPublisher() {
    return publisher;
  }
  public java.sql.Date getPubYear() {
    return pubYear;
  }
  public String getIsbn() {
    return isbn;
  }
  public boolean getDecommissioned() {
    return decommissioned;
  }

  public boolean ExistSpare() {
    return StdDAO_Factory.getInstance().getBookDao().GetBookEx(this, true, true).size()>0;
  }

  public int GetFirstSpare() {
    return StdDAO_Factory.getInstance().getBookDao().GetBookEx(this, true, true).get(0).getBookExId();
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
