package models;

import javax.persistence.*;

@Entity
@Table(name = "public.book_examples")
public class BookExamples {

  @Id
  @Column(name = "book_ex_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long bookExId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Books book;

  @Column(name = "spare")
  private boolean spare;

  @Column(name = "decommissioned")
  private boolean decommissioned;

  public  BookExamples(){}
  public  BookExamples(Books book, boolean spare, boolean decommissioned)
  {
    this.book = book;
    this.spare = spare;
    this.decommissioned = decommissioned;
  }

  public long getBookExId() {
    return bookExId;
  }
  //public void setBookExId(long bookExId) {this.bookExId = bookExId;}

  public Books getBook() {
    return book;
  }
  public void setBook(Books book) {
    this.book = book;
  }

  public boolean getSpare() {
    return spare;
  }
  public void setSpare(boolean spare) {
    this.spare = spare;
  }

  public boolean getDecommissioned() {
    return decommissioned;
  }
  public void setDecommissioned(boolean decommissioned) {
    this.decommissioned = decommissioned;
  }
}
