import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class test {



        public static void main (String[] args){
            try {

                Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.247/austin_atagallu", "root", "t1tan1um");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select * from Members");

                while (resultSet.next()) {
                    System.out.println(resultSet.getString("Name"));
                }

            } catch(Exception e){
                    e.printStackTrace();
                }

    }
    }

