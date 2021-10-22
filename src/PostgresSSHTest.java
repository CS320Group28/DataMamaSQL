import com.jcraft.jsch.*;

import java.util.ArrayList;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresSSHTest {

    public static void main(String[] args) throws SQLException {

        int lport = 5432;
        String rhost = "starbug.cs.rit.edu";
        int rport = 5432;

        Connection conn = null;
        Session session = null;
        //boolean for checking if the connection is successful
        boolean connect = false;

        //connection loop for entering the user login info
        while(!connect) {

            ArrayList<String> userLogin = getLogin();
            String user = userLogin.get(0); //change to your username
            String password = userLogin.get(1); //change to your password
            String databaseName = "p320_30"; //change to your database name

            String driverName = "org.postgresql.Driver";
            try {
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                JSch jsch = new JSch();
                session = jsch.getSession(user, rhost, 22);
                session.setPassword(password);
                session.setConfig(config);
                session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
                session.connect();
                System.out.println("Connected");
                int assigned_port = session.setPortForwardingL(lport, "localhost", rport);
                System.out.println("Port Forwarded");

                // Assigned port could be different from 5432 but rarely happens
                String url = "jdbc:postgresql://localhost:" + assigned_port + "/" + databaseName;

                System.out.println("database Url: " + url);
                Properties props = new Properties();
                props.put("user", user);
                props.put("password", password);

                Class.forName(driverName);
                conn = DriverManager.getConnection(url, props);
                System.out.println("Database connection established");

                // Do something with the database....

            } catch (Exception e) {
                System.out.println("Connection Failed!");
                e.printStackTrace();
            } finally {
                if (conn != null && !conn.isClosed()) {
                    System.out.println("Closing Database Connection");
                    conn.close();
                }
                if (session != null && session.isConnected()) {
                    System.out.println("Closing SSH Connection");
                    session.disconnect();
                }
            }
            //if the connection did not fail, exit the loop
            if(conn != null){
                connect = true;
            }
        }
    }

    //user to get the login info, returns an arraylist with the username at index 0 and the password at index 1
    public static ArrayList<String> getLogin(){
        Scanner scan = new Scanner(System.in);

        ArrayList<String> userLogin = new ArrayList<>();
        System.out.println("Username: ");
        userLogin.add(scan.nextLine());
        System.out.println("Password: ");
        userLogin.add(scan.nextLine());

        return userLogin;
    }
}
