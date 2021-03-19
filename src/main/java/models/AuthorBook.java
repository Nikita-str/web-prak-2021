package models;

import javax.persistence.*;

@Entity
@Table(name = "author_book")
public class AuthorBook {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long phantomId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Books book;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id")
  private Authors author;

  public AuthorBook(){}
  public AuthorBook(Books book, Authors author)
  {
    this.book = book;
    this.author = author;
  }

  public Books getBook() {
    return book;
  }
  public void setBook(Books book) {
    this.book = book;
  }


  public Authors getAuthor() {
    return author;
  }
  public void setAuthor(Authors author) { this.author = author; }
}
