package com.exam.portal.interfaces;
import java.io.*;
public interface MemberInterface extends Serializable
{
public void setCode(int code);
public void setFirstName(String firstName);
public void setLastName(String lastName);
public void setAddress(String address);
public void setDateOfBirth(String dateOfBirth);
public void setEmail(String email);
public void setPassword(String password);
public void setBlocked(Boolean blocked);
public int getCode();
public String getFirstName();
public String getLastName();
public String getAddress();
public String getDateOfBirth();
public String getEmail();
public String getPassword();
public Boolean getBlocked();

}
