/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.Properties;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>This is a <b>Singleton class</b> that holds the database connection.  It reads the
 * driver and connection string from the database.properties file.
 * 
 * <p><b>It is highly encouraged to use this class to get the Connection object and
 * Statement object to work with database in this whole project.</b>
 * 
 * <p>This should act as a single point of entry to database</p>
 * @author LabAutomationTeam
 */
public class DatabaseConnection {
    
    private static DatabaseConnection dbcon = null;
    private String connectionString;    
    private Connection con;
    private Statement stmt;
 
    private DatabaseConnection(){
    
        try {
            readProperties();
            Class.forName("com.mysql.jdbc.Driver");            
            this.con = DriverManager.getConnection(this.connectionString,"root","");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found.  Database Connection denied");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static DatabaseConnection getConnection(){
            
          if(dbcon == null){
              dbcon = new DatabaseConnection();
          }
          return dbcon;
    }
    
    public Statement getStatement(){
        
        return this.stmt;
    }
    
    public String getConnectionString(){
    
        return this.connectionString;
    }
    
    private void readProperties(){
        
        String configfile = "resources/database.properties";
        Properties props;
	InputStream is;
        
        props = new Properties();
	is = new BufferedInputStream(this.getClass().getClassLoader().getResourceAsStream(configfile));
        
        try{
            props.load(is);
            this.connectionString = props.getProperty("connection");
            
        }catch(Exception ex){
            
            System.out.println("There is an error getting Database Connection");
        }
    }
}
