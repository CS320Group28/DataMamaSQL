package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

// file: DBInterface.java
// Class for setting up and interacting with DB

public class DBInterface{
    private static final String DBNAME = "p320_30";
    private Connection connection;
    private Session session;
    
    public DBInterface(){
        try(
            BufferedReader br = new BufferedReader(
                new FileReader("src\\com\\config.txt")
            )
        ){
            String user = br.readLine();
            String password = br.readLine();
            String driver = br.readLine();
            String host = br.readLine();
            int lport = Integer.parseInt(br.readLine());
            int rport = Integer.parseInt(br.readLine());

            //setup connection
            try{
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                JSch jsch = new JSch();
                session = jsch.getSession(user, host, 22);
                session.setPassword(password);
                session.setConfig(config);
                session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
                session.connect();

                System.out.println("Connected");
                int assigned_port = session.setPortForwardingL(lport, "localhost", rport);
                
                String url = "jdbc:postgresql://localhost:" + assigned_port + "/" + DBNAME;

                System.out.println("Port Forwarded");
                Class.forName(driver);
                this.connection = DriverManager.getConnection(url , user, password);

            }catch(Exception e){
                e.printStackTrace();
            }



        }catch(FileNotFoundException e){
            System.out.println(e.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
        Objects.requireNonNull(this.connection, "Connection not successfully retrieved");
    }
    public PreparedStatement getPreparedStatement(String statementFormat){
        try{
            return connection.prepareStatement(statementFormat);
        }catch(SQLException e){
            return null;
        }
    }

    public Statement getStatement(){
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            return null;
        }
    }

    public ResultSet execStatementQuery(PreparedStatement stmt) throws SQLException{
        return stmt.executeQuery();
    }
    
    public void execStatementUpdate(PreparedStatement stmt) throws SQLException{
        stmt.executeUpdate();
        stmt.close();
    }

    public void endSSH() throws SQLException{
        if (connection != null && !connection.isClosed()) {
            System.out.println("Closing Database Connection");
            connection.close();
        }
        if (session != null && session.isConnected()) {
            System.out.println("Closing SSH Connection");
            session.disconnect();
        }
    }
}