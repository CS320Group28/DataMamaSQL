import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

// file: DBInterface.java
// Class for setting up and interacting with DB

class DBInterface{
    private static final String DBNAME = "p320_30";
    private Connection connection;
    
    public DBInterface(){
        try(
            BufferedReader br = new BufferedReader(
                new FileReader("config.txt")
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
                Session session = jsch.getSession(user, host, 22);
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
        Objects.requireNonNull(connection, "Connection not successfully retrieved");
    }

    public ResultSet execStatementQuery(String query) throws SQLException{
        Statement stmt = this.connection.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        stmt.close();
        return rset;
    }
    
    public void execStatementUpdate(String sql) throws SQLException{
        Statement stmt = this.connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }
}