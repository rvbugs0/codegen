
package com.exam.portal.dao;
import java.sql.*;
import java.util.*;
public class MemberDAO implements MemberDAOInterface
{


/*-----------------Add--------*/

public void add(MemberInterface mMemberInterface) throws DAOException
{ 
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from table_name where code=?");
preparedStatement.setInt(1,mMemberInterface.getCode());
ResultSet resultSet= preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Member :"+mMemberInterface.getCode()+"already exists");
}
resultSet.close();
preparedStatement.close();

preparedStatement=connection.prepareStatement("insert into table_name(code,firstName,lastName,email,blocked,dateOfBirth,password,address) values(?,?,?,?,?,?,?,?)");
preparedStatement.setInt(1,mMemberInterface.getCode());
preparedStatement.setString(2,mMemberInterface.getFirstName());
preparedStatement.setString(3,mMemberInterface.getLastName());
preparedStatement.setString(4,mMemberInterface.getEmail());
preparedStatement.setBoolean(5,mMemberInterface.getBlocked());
preparedStatement.setString(6,mMemberInterface.getDateOfBirth());
preparedStatement.setString(7,mMemberInterface.getPassword());
preparedStatement.setString(8,mMemberInterface.getAddress());
preparedStatement.executeUpdate();
resultSet =preparedStatement.getGeneratedKeys();
if(resultSet.next())
{
//we have determined the generated primary key value
//for code and have set it in the object whose data was
//inserted in te table
mMemberInterface.setCode(resultSet.getInt(1));
}
resultSet.close();
preparedStatement.close();
connection.close();
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



/*-------------Update----------------*/

public void update(MemberInterface mMember) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from Member where code=?");
preparedStatement.setInt(1,mMember.getCode());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Member code :"+mMember.getCode());
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select code from Member where name=? and code!=?");
preparedStatement.setInt(1,mMemberInterface.getCode());
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Member : "+mMemberInterface.getUniqueProperty()+"already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update Member set code=?,firstName=?,lastName=?,email=?,blocked=?,dateOfBirth=?,password=?,address=? where code=?");
preparedStatement.setInt(1,mMember.getCode());
preparedStatement.setString(2,mMember.getFirstName());
preparedStatement.setString(3,mMember.getLastName());
preparedStatement.setString(4,mMember.getEmail());
preparedStatement.setBoolean(5,mMember.getBlocked());
preparedStatement.setString(6,mMember.getDateOfBirth());
preparedStatement.setString(7,mMember.getPassword());
preparedStatement.setString(8,mMember.getAddress());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}


/*------------remove by code------------*/

public void remove(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from Member where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Member code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select code from child_table where Member_code=?");
preparedStatement.setint(1,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Child exists against Member code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from Member where code=?");
preparedStatement.setint(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



/*------------get by code-----------*/


public MemberInterface get(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from Member where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Member code : "+code);
}
MemberInterface mMember;
mMember=new Member();
mMember.setInt(resultSet.getInt("code").trim());
mMember.setString(resultSet.getString("firstName").trim());
mMember.setString(resultSet.getString("lastName").trim());
mMember.setString(resultSet.getString("email").trim());
mMember.setBoolean(resultSet.getBoolean("blocked").trim());
mMember.setString(resultSet.getString("dateOfBirth").trim());
mMember.setString(resultSet.getString("password").trim());
mMember.setString(resultSet.getString("address").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return unitOfMeasurementInterface;
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
 catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}




/*---------------get all-----*/

public ArrayList<MemberInterface> get() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from Member");
if(resultSet.next()==false)
{
resultSet.close();
statement.close();
connection.close();
throw new DAOException("No Member records");
}
ArrayList<MemberInterface> mMembers;
mMembers=new ArrayList<MemberInterface>();
MemberInterface mMemberInterface;
do
{
mMemberInterface=new Member();
mMemberInterface.setInt(resultSet.getInt("code"));
mMemberInterface.setString(resultSet.getString("firstName"));
mMemberInterface.setString(resultSet.getString("lastName"));
mMemberInterface.setString(resultSet.getString("email"));
mMemberInterface.setBoolean(resultSet.getBoolean("blocked"));
mMemberInterface.setString(resultSet.getString("dateOfBirth"));
mMemberInterface.setString(resultSet.getString("password"));
mMemberInterface.setString(resultSet.getString("address"));
mMembers.add(mMemberInterface);
}while(resultSet.next());
resultSet.close();
statement.close();
connection.close();
return mMembers;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



/*---------------------getCount-----------*/

public long getCount() throws DAOException
{ 
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(*) as cnt from Member");
resultSet.next();
int count=resultSet.getInt("cnt");
statement.close();
resultSet.close();
connection.close();
return count;
}
catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} 
catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



/*-------------exists--------*/


public boolean exists(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from Member where code=?");
preparedStatement.setint(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
boolean found;
found=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return found;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}



/*------------remove all-----------*/

public void removeAll() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement;
ResultSet resultSet;
statement=connection.createStatement();
resultSet=statement.executeQuery("select count(*) as cnt from child_table");
resultSet.next();
int count=resultSet.getInt("cnt");
resultSet.close();
statement.close();
if(count>0)
{ connection.close();
throw new DAOException("children exists against Member_code");
}
statement=connection.createStatement();
statement.executeUpdate("delete from Member");
statement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{

throw new DAOException(exception.getMessage());
}
}
}

