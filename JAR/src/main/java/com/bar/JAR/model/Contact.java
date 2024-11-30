/** Model for Contact **/
package main.java.com.bar.JAR.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import jakarta.persistence.*;

@Entity
@Table(name = "contact")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "contact_id")
  private long contactID;

  @Column(name = "contact_name")
  private String contactName;
  
  @Column(name = "email")
  @Email(message = "Invalid email format")
  private String emailAddress;

@Column(name = "phone_number")
@Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Phone number must be in the format xxx-xxx-xxxx")
private String phoneNumber;

  public Contact() {
  }

  public Contact(long ContactID, String contactName, String emailAddress, String phoneNumber) {
    this.contactID = contactID;
    this.contactName = contactName;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
  }

  public long getContactID() {
    return contactID;
  }
  
  public void setContactID(long contactID) {
    this.contactID = contactID;
  }

  public String getContactName() {
    return contactName;
  }
  
  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactEmail() {
    return emailAddress;
  }
  
  public void setContactEmail(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}")) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Phone number must match the format xxx-xxx-xxxx");
        }
    }
  
}
