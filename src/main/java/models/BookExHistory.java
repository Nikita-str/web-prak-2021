package models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "public.book_ex_history")
public class BookExHistory {

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

  BookExHistory(){}
  BookExHistory(BookExamples bookExamples, Readers readers, Date dateOfIssue, Date sheduleRetDate, Date realRetDate)
  {
    this.bookEx = bookExamples;
    this.reader = readers;
    this.dateOfIssue = dateOfIssue;
    this.sheduleRetDate = sheduleRetDate;
    this.realRetDate = realRetDate;
  }

  public BookExamples getBookEx() {
    return bookEx;
  }
  public void setBookEx(BookExamples bookEx) {
    this.bookEx = bookEx;
  }

  public Readers getReader() {
    return reader;
  }
  public void setReader(Readers reader) {
    this.reader = reader;
  }

  public Date getDateOfIssue() {
    return dateOfIssue;
  }
  public void setDateOfIssue(java.sql.Date dateOfIssue) {
    this.dateOfIssue = dateOfIssue;
  }

  public Date getSheduleRetDate() {
    return sheduleRetDate;
  }
  public void setSheduleRetDate(Date sheduleRetDate) {
    this.sheduleRetDate = sheduleRetDate;
  }

  public Date getRealRetDate() {
    return realRetDate;
  }
  public void setRealRetDate(Date realRetDate) {
    this.realRetDate = realRetDate;
  }

}
