<head>
package #package#;
import java.sql.*;
import java.util.*;
public class #name#DAO implements #name#DAOInterface
{
</head>


<body>

/*-----------------Add--------*/

public void add(#name#Interface m#name#Interface) throws DAOException
{ 
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from table_name where #key#=?");
preparedStatement.set#keyDataTypeFirstLetterUpperCase#(1,m#name#Interface.get#keyFirstLetterUpperCase#());
ResultSet resultSet= preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("#name# :"+m#name#Interface.get#keyFirstLetterUpperCase#()+"already exists");
}
resultSet.close();
preparedStatement.close();

preparedStatement=connection.prepareStatement("insert into table_name(<repeat>#propertyName#,</repeat>) values(<repeat>?,</repeat>)");
<repeat>
preparedStatement.set#dataTypeFirstLetterUpperCase#(#index#,m#name#Interface.get#propertyNameFirstLetterUppercase#());
</repeat>
preparedStatement.executeUpdate();
resultSet =preparedStatement.getGeneratedKeys();
if(resultSet.next())
{
//we have determined the generated primary key value
//for code and have set it in the object whose data was
//inserted in te table
m#name#Interface.set#keyFirstLetterUpperCase#(resultSet.getInt(1));
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

public void update(#name#Interface m#name#Interface) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select #key# from #name# where #key#=?");
preparedStatement.set#keyDataTypeFirstLetterUpperCase#(1,m#name#Interface.get#keyFirstLetterUpperCase#());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid #name# #key# :"+m#name#Interface.get#keyFirstLetterUpperCase#());
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select #key# from #name# where name=? and #key#!=?");
preparedStatement.set#keyDataTypeFirstLetterUpperCase#(1,m#name#Interface.get#keyFirstLetterUpperCase#());
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("#name# : "+m#name#Interface.getUniqueProperty()+"already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update #name# set <repeat>#propertyName#=?,</repeat> where #key#=?");
<repeat>
preparedStatement.set#dataTypeFirstLetterUpperCase#(#index#,m#name#Interface.get#propertyNameFirstLetterUppercase#());
</repeat>
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


/*------------remove by #key#------------*/

public void remove(#keyDataType# #key#) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select #key# from #name# where #key#=?");
preparedStatement.set#keyDataTypeFirstLetterUpperCase#(1,#key#);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid #name# #key# : "+#key#);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select #key# from child_table where #name#_#key#=?");
preparedStatement.set#keyDataTypeFirstLetterUpperCase#(1,#key#);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Child exists against #name# #key# : "+#key#);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from #name# where #key#=?");
preparedStatement.set#keyDataTypeFirstLetterUpperCase#(1,#key#);
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



/*------------get by #key#-----------*/


public #name#Interface get(#keyDataType# #key#) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from #name# where #key#=?");
preparedStatement.set#keyDataTypeFirstLetterUpperCase#(1,#key#);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid #name# #key# : "+#key#);
}
#name#Interface m#name#Interface;
m#name#Interface=new #name#();
<repeat>
m#name#Interface.set#propertyNameFirstLetterUppercase#(resultSet.get#dataTypeFirstLetterUpperCase#("#propertyName#").trim());
</repeat>
resultSet.close();
preparedStatement.close();
connection.close();
return m#name#Interface;
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

public ArrayList<#name#Interface> get() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from #name#");
if(resultSet.next()==false)
{
resultSet.close();
statement.close();
connection.close();
throw new DAOException("No #name# records");
}
ArrayList<#name#Interface> m#name#s;
m#name#s=new ArrayList<#name#Interface>();
#name#Interface m#name#Interface;
do
{
m#name#Interface=new #name#();
<repeat>
m#name#Interface.set#propertyNameFirstLetterUppercase#(resultSet.get#dataTypeFirstLetterUpperCase#("#propertyName#"));
</repeat>
m#name#s.add(m#name#Interface);
}while(resultSet.next());
resultSet.close();
statement.close();
connection.close();
return m#name#s;
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
resultSet=statement.executeQuery("select count(*) as cnt from #name#");
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


public boolean exists(#keyDataType# #key#) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select #key# from #name# where #key#=?");
preparedStatement.set#keyDataTypeFirstLetterUpperCase#(1,#key#);
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
throw new DAOException("children exists against #name#_#key#");
}
statement=connection.createStatement();
statement.executeUpdate("delete from #name#");
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

</body>