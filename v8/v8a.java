package forritun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class v8a {
    public static void main(String[] args) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:company.db");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Salary FROM EMPLOYEE");
            int count = 0;
            double sum = 0;
            while(rs.next()){
                sum += rs.getDouble(1);
                count++;
            }
            rs.close();
            System.out.print("Meðallaun eru: " + sum/count);
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        finally{
            try {
                if(connection != null) connection.close();
            }
            catch(SQLException e){
                System.err.println(e);
            }
        }
    }
}