package models;

import javax.persistence.*;

@Entity
@Table(name = "public.publishers")
public class Publishers {

  @Id
  @Column(name = "p_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long pId;

  @Column(name = "p_name", unique = true)
  private String pName;

  public Publishers(){}
  public Publishers(String pub_name){this.pName = pub_name;}

  public long getPId() {
    return pId;
  }
  //public void setPId(long pId) { this.pId = pId; }

  public String getPName() {
    return pName;
  }
  public void setPName(String pName) {
    this.pName = pName;
  }

}
