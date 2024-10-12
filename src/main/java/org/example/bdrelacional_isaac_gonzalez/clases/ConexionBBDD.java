package org.example.bdrelacional_isaac_gonzalez.clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBBDD {
    private static Connection con;


    public static Connection conectar() throws ClassNotFoundException, SQLException, IOException {

        Properties properties= new Properties();
        String host="";
        String port="";
        String name="";
        String username="";
        String password="";
        boolean conect=false;
        try {
            properties.load(new FileInputStream(new File("src/main/resources/configuration/database.properties")));

            //System.out.println(properties.get("driver"));
            host=String.valueOf(properties.get("host"));
            port=String.valueOf(properties.get("port"));
            name=String.valueOf(properties.get("name"));
            username=String.valueOf(properties.get("username"));
            password=String.valueOf(properties.get("password"));
            //System.out.println(host+"  "+port+"  "+name+"  "+username+"  "+password);
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                    username, password);

            return con;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block


            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            return null;
        }
    }

    public static void desconectar() throws SQLException {
        con.close();
    }

}
