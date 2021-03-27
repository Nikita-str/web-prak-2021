package models;

import javax.persistence.*;

@Entity
@Table(name = "book_examples")
public class BookExamples {

  @Id
  @Column(name = "book_ex_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int bookExId;

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

  public int getBookExId() {
    return bookExId;
  }

  public Books getBook() {
    return book;
  }
  public boolean getSpare() {
    return spare;
  }
  public boolean getDecommissioned() {
    return decommissioned;
  }

  @Override
  public String toString(){
    return "Book(EX) {Id: " + bookExId + " | book name: " + book.getTitle() + " | spare: " + spare + " | decommissioned:" + decommissioned + "}";
  }
}
