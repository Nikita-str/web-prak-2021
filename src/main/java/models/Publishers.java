package models;

import javax.persistence.*;

@Entity
@Table(name = "publishers")
public class Publishers {

  @Id
  @Column(name = "p_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer pId;

  @Column(name = "p_name", unique = true)
  private String pName;

  public Publishers(){}
  public Integer getPId() {
    return pId;
  }
  public String getPName() {
    return pName;
  }

  @Override public String toString(){ return "Publisher {Id: " + pId + " | name: " + pName + "}"; }
}
