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
</body>


/*-------------Update----------------*/

public void update(UnitOfMeasurementInterface unitOfMeasurementInterface) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from unit_of_measurement where code=?");
preparedStatement.setInt(1,unitOfMeasurementInterface.getCode());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid unit of measurement code :"+unitOfMeasurementInterface.getCode());
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select code from unit_of_measurement where name=? and code!=?");
preparedStatement.setString(1,unitOfMeasurementInterface.getName());
preparedStatement.setInt(2,unitOfMeasurementInterface.getCode());
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Unit of measurement : "+unitOfMeasurementInterface.getName()+"already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update unit_of_measurement set name=? where code=?");
preparedStatement.setString(1,unitOfMeasurementInterface.getName());
preparedStatement.setInt(2,unitOfMeasurementInterface.getCode());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : update(UnitOfMeasurementInterface)--->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : update(UnitOfMeasurementInterface)--->"+exception.getMessage());
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
preparedStatement=connection.prepareStatement("select code from unit_of_measurement where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid unit of measurement code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select code from item where unit_of_measurement_code=?");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Item exists against unit of measurement code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from unit_of_measurement where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : remove(int code) --->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : remove(int code) --->"+exception.getMessage());
throw new DAOException(exception.getMessage());
}
}


/*----------------remove by name----------*/

public void removeByName(String name) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from unit_of_measurement where name=?");
preparedStatement.setString(1,name);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid unit of measurement name : "+name);
}
 int code=resultSet.getInt("code");
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select code from item where unit_of_measurement_code=?");
preparedStatement.setInt(1,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Item exists against unit of measurement code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from unit_of_measurement where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}
catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : removeByName(String name)--->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
} 
catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : removeByName(String name)--->"+exception.getMessage());
throw new DAOException(exception.getMessage());
}
}


/*------------get by code-----------*/


public UnitOfMeasurementInterface get(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from unit_of_measurement where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid unit of measurement code : "+code);
}
UnitOfMeasurementInterface unitOfMeasurementInterface;
unitOfMeasurementInterface=new UnitOfMeasurement();
unitOfMeasurementInterface.setCode(resultSet.getInt("code"));
unitOfMeasurementInterface.setName(resultSet.getString("name").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return unitOfMeasurementInterface;
}
catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : get(int code) --->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
}
 catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : get(int code) --->"+exception.getMessage());
throw new DAOException(exception.getMessage());
}
}



/*-------getByName --------------------*/

public UnitOfMeasurementInterface getByName(String name) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from unit_of_measurement where name=?");
preparedStatement.setString(1,name);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid unit of measurement : "+name);
}
UnitOfMeasurementInterface unitOfMeasurementInterface;
unitOfMeasurementInterface=new UnitOfMeasurement();
unitOfMeasurementInterface.setCode(resultSet.getInt("code"));
unitOfMeasurementInterface.setName(resultSet.getString("name").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return unitOfMeasurementInterface;
}catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : getByName(String name)--->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : getByName(String name)--->"+exception.getMessage());
throw new DAOException(exception.getMessage());
}
}


/*---------------get all-----*/

public ArrayList<UnitOfMeasurementInterface> get() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from unit_of_measurement order by name");
if(resultSet.next()==false)
{
resultSet.close();
statement.close();
connection.close();
throw new DAOException("No unit of measurement records");
}
ArrayList<UnitOfMeasurementInterface> unitOfMeasurements;
unitOfMeasurements=new ArrayList<UnitOfMeasurementInterface>();
UnitOfMeasurementInterface unitOfMeasurementInterface;
do
{
unitOfMeasurementInterface=new UnitOfMeasurement();
unitOfMeasurementInterface.setCode(resultSet.getInt("code"));
unitOfMeasurementInterface.setName(resultSet.getString("name").trim());
unitOfMeasurements.add(unitOfMeasurementInterface);
}while(resultSet.next());
resultSet.close();
statement.close();
connection.close();
return unitOfMeasurements;
}catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : get() --->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : get() --->"+exception.getMessage());
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
resultSet=statement.executeQuery("select count(*) as cnt from unit_of_measurement");
resultSet.next();
int count=resultSet.getInt("cnt");
statement.close();
resultSet.close();
connection.close();
return count;
}
catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : getCount() --->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
} 
catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : getCount() --->"+exception.getMessage());
throw new DAOException(exception.getMessage());
}
}


/*-----------get Count By name--------*/

public int getCountByName(String name) throws DAOException
{ 
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select count(*) as cnt from unit_of_measurement where name=?");
preparedStatement.setString(1,name);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
int count=resultSet.getInt("cnt");
preparedStatement.close();
resultSet.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : getCountByName(String name)--->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : getCountByName(String name)--->"+exception.getMessage());
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
preparedStatement=connection.prepareStatement("select code from unit_of_measurement where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
boolean found;
found=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return found;
}catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : exists(int code) --->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : exists(int code) --->"+exception.getMessage());
throw new DAOException(exception.getMessage());
}
}

/*-------exists by name-----*/


public boolean existsByName(String name) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from unit_of_measurement where name=?");
preparedStatement.setString(1,name);
ResultSet resultSet=preparedStatement.executeQuery();
boolean found;
found=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return found;
}catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : existsByName(String name)--->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : existsByName(String name)--->"+exception.getMessage());
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
resultSet=statement.executeQuery("select count(*) as cnt from item");
resultSet.next();
int count=resultSet.getInt("cnt");
resultSet.close();
statement.close();
if(count>0)
{ connection.close();
throw new DAOException("Items exists against unit of measurements");
}
statement=connection.createStatement();
statement.executeUpdate("delete from unit_of_measurement");
statement.close();
connection.close();
}catch(SQLException sqlException)
{
Utility.addToLog("UnitOfMeasurementDAO : removeAll() --->"+sqlException.getMessage());
throw new DAOException(sqlException.getMessage());
} catch(Exception exception)
{
Utility.addToLog("UnitOfMeasurementDAO : removeAll() --->"+exception.getMessage());
throw new DAOException(exception.getMessage());
}
}
}