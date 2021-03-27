package models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "book_ex_history")
public class BookExHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "beh_id", nullable = false, updatable = false)
  private Integer behId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_ex_id")
  private BookExamples bookEx;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lib_card_id")
  private Readers reader;

  @Column(name = "date_of_issue", nullable = false)
  private Date dateOfIssue;
  @Column(name = "shedule_ret_date")
  private Date sheduleRetDate;
  @Column(name = "real_ret_date")
  private Date realRetDate;

  public BookExHistory(){}
  public BookExHistory(BookExamples bookExamples, Readers readers, Date dateOfIssue, Date sheduleRetDate, Date realRetDate)
  {
    this.bookEx = bookExamples;
    this.reader = readers;
    this.dateOfIssue = dateOfIssue;
    this.sheduleRetDate = sheduleRetDate;
    this.realRetDate = realRetDate;
  }

  public Integer getBehId(){return behId;}

  public BookExamples getBookEx() {
    return bookEx;
  }
  public Readers getReader() {
    return reader;
  }
  public Date getDateOfIssue() {
    return dateOfIssue;
  }
  public Date getSheduleRetDate() {
    return sheduleRetDate;
  }
  public Date getRealRetDate() {
    return realRetDate;
  }

  @Override
  public String toString(){
    return "Author {Id: " + behId + " | book ex id: " + bookEx.getBookExId() + " | reader id: " + reader.getLibraryCardId() +
            " | Issued: " + dateOfIssue + " | Shedule Ret:" + sheduleRetDate + " | Real Ret: " + realRetDate+ "}";
  }
}
