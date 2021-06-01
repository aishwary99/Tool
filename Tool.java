import java.util.*;
import java.sql.*;
import java.io.*;
public class Tool 
{
private Connection connection;
private DatabaseMetaData databaseMetaData;
private PreparedStatement preparedStatement;
private ResultSet resultSet;
private ResultSet resultSetForTable;
private ResultSetMetaData resultSetMetaData;
private RandomAccessFile raf;
private String fileName;
private String tableName;
private String columnName;
private String columnType;
private String className;
private List<String> columnNames;
private List<String> columnTypes;
private String driver;
private String connectionString;
private String databaseName;
private File file;
private String username;
private String password;
public Tool() throws Exception	
{
this.username="showcase";
this.password="thinkingmachines";
this.databaseName="showcasedb";
this.driver="com.mysql.jdbc.Driver";
this.connectionString="jdbc:mysql://localhost:3306/"+this.databaseName;
Class.forName(this.driver);	//specifying the driver class for mysql
this.connection=DriverManager.getConnection(this.connectionString,this.username,this.password); 	//connection string for mysql
this.databaseMetaData=connection.getMetaData();		//to know all about database meta data on specified connection
this.preparedStatement=null;
this.resultSet=null;
this.resultSetForTable=null;
this.resultSetMetaData=null;
this.raf=null;
this.className="";
this.fileName="";
this.tableName="";
this.columnName="";
this.columnType="";
this.file=null;
this.columnNames=new ArrayList<String>();		//for storing column names
this.columnTypes=new ArrayList<String>();		//for storing column types
String tableNames[]={"TABLE"};
this.resultSet=this.databaseMetaData.getTables(null,null,"%",tableNames);
generateSetterGetter();
}
//generateSetterGetter will do the magic.
public void generateSetterGetter()
{
try
{
//on every iteration we will fetch table names
while(this.resultSet.next())
{
tableName=this.resultSet.getString("TABLE_NAME");
fileName=tableName.substring(0,1).toUpperCase()+tableName.substring(1).toLowerCase()+".java";
file=new File(fileName);	
raf=new RandomAccessFile(file,"rw");	//opening file with read/write permissions
preparedStatement=connection.prepareStatement("select * from "+tableName);
resultSetForTable=preparedStatement.executeQuery();
resultSetMetaData=resultSetForTable.getMetaData();
for(int index=1;index<=resultSetMetaData.getColumnCount();index++)
{
columnNames.add(resultSetMetaData.getColumnName(index).trim().toLowerCase());
columnTypes.add(resultSetMetaData.getColumnTypeName(index).trim().toLowerCase());
}
//defining class
className=tableName.substring(0,1).toUpperCase()+tableName.substring(1).toLowerCase();
raf.writeBytes("public class "+className+"\n{\n");
for(int index=0;index<columnNames.size();index++)
{
columnName=columnNames.get(index);
columnType=columnTypes.get(index);
String lineToBeWritten="";
if(columnType.toLowerCase().equals("integer") || columnType.toLowerCase().equals("int"))
{
lineToBeWritten="private Integer "+columnName+";\n";
}
else if(columnType.toLowerCase().equals("string") || columnType.toLowerCase().equals("char") || columnType.toLowerCase().equals("varchar"))
{
lineToBeWritten="private String "+columnName+";\n";
}
else if(columnType.toLowerCase().equals("long"))
{
lineToBeWritten="private Long "+columnName+";\n";
}
raf.writeBytes(lineToBeWritten);
}
//code to write constructor starts here...
raf.writeBytes("public "+className+"()\n{\n");
String lineToBeWritten="";
for(int index=0;index<columnNames.size();index++)
{
columnName=columnNames.get(index).toLowerCase();
columnType=columnTypes.get(index).toLowerCase();
lineToBeWritten="";
if(columnType.equals("int") || columnType.equals("integer"))
{
lineToBeWritten="this."+columnName+"=0;\n";
}
else if(columnType.equals("string") || columnType.equals("char") || columnType.equals("long"))
{
lineToBeWritten="this."+columnName+"=null;\n";
}
raf.writeBytes(lineToBeWritten);
}
raf.writeBytes("}\n");
//code to write setter and getter methods starts here...
for(int index=0;index<columnNames.size();index++)
{
columnName=columnNames.get(index).toLowerCase();
columnType=columnTypes.get(index).toLowerCase();
String setter="public void set"+columnName.substring(0,1).toUpperCase()+columnName.substring(1).toLowerCase()+"(";
String getter="public ";
if(columnType.equals("int") || columnType.equals("integer"))
{
setter+="Integer "+columnName+")\n";
getter+="Integer get"+columnName.substring(0,1).toUpperCase()+columnName.substring(1).toLowerCase()+"()\n{";
}
else if(columnType.toLowerCase().equals("string") || columnType.toLowerCase().equals("char") || columnType.toLowerCase().equals("varchar"))
{
setter+="String "+columnName+")\n";
getter+="String get"+columnName.substring(0,1).toUpperCase()+columnName.substring(1).toLowerCase()+"()\n{";
}
else if(columnType.toLowerCase().equals("long"))
{
setter+="Long "+columnName+")\n";
getter+="Long get"+columnName.substring(0,1).toUpperCase()+columnName.substring(1).toLowerCase()+"()\n{";
}
setter+="{\nthis."+columnName+"="+columnName+";\n}\n";
getter+="\nreturn this."+columnName+";\n}\n";
raf.writeBytes(setter);
raf.writeBytes(getter);
}
raf.writeBytes("}");
}//outer while loop ends here...
raf.close();
}catch(Exception exception)
{
exception.printStackTrace();		//to trace the line on which exception is raised
}
}
public static void main(String data[]) throws Exception
{
new Tool();
}
}