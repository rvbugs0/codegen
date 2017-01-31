
package com.exam.portal.dao;
import com.exam.portal.interfaces.*;
public class Member implements MemberInterface
{
public boolean equals(Object object)
{ 
if(!(object instanceof MemberInterface))
{
return false;
}
MemberInterface mMemberInterface;
mMemberInterface=(MemberInterface)object;
return this.code==mMemberInterface.getCode();
}

private int code;
private String firstName;
private String lastName;
private String email;
private String password;
private String dateOfBirth;
private Boolean blocked;
private String address;
public void setCode(int code){ this.code=code;}
public void setFirstName(String firstName){ this.firstName=firstName;}
public void setLastName(String lastName){ this.lastName=lastName;}
public void setEmail(String email){ this.email=email;}
public void setPassword(String password){ this.password=password;}
public void setDateOfBirth(String dateOfBirth){ this.dateOfBirth=dateOfBirth;}
public void setBlocked(Boolean blocked){ this.blocked=blocked;}
public void setAddress(String address){ this.address=address;}
public int getCode(){return this.code;}
public String getFirstName(){return this.firstName;}
public String getLastName(){return this.lastName;}
public String getEmail(){return this.email;}
public String getPassword(){return this.password;}
public String getDateOfBirth(){return this.dateOfBirth;}
public Boolean getBlocked(){return this.blocked;}
public String getAddress(){return this.address;}

}
