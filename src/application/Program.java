package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) {
        Connection connection = DB.getConnection();
        Statement query = null;
        ResultSet result = null;

        //Recuperar dados
        try{
            query = connection.createStatement();
            result = query.executeQuery("SELECT * FROM DEPARTMENT");
            while(result.next()){
                System.out.println(result.getInt("Id") + ", " + result.getString("Name"));
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(query);
            DB.closeResultSet(result);
            DB.closeConnection();
        }
    }
}
