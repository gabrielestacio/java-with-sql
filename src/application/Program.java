package application;

import db.DB;
import db.DbException;
import db.DbIntegrityException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
            //DB.closeConnection();
        }

        //Inserir dados
        //SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        PreparedStatement statement = null;
        /*try{
            statement = connection.prepareStatement(
                    "INSERT INTO seller(name, email, birthdate, basesalary, departmentid) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS //Inserir dados retornando id do dado inserido
            );
            statement.setString(1, "Carl Purple");
            statement.setString(2, "carl@gmail.com");
            statement.setDate(3, new java.sql.Date(format.parse("22/04/1985").getTime())); //ATENÇÂO NESSE DATE
            statement.setDouble(4, 3000.0);
            statement.setInt(5, 4);

            int rowsAffected = statement.executeUpdate();

            //Inserir dados retornando id do dado inserido
            if(rowsAffected > 0){
                ResultSet set = statement.getGeneratedKeys();
                while(set.next()){
                    int id = set.getInt(1);
                    System.out.println("Done! Id = " + id);
                }
            } else
                System.out.println("No row affected!");
        } catch (SQLException | ParseException e){
            throw new DbException(e.getMessage());
        } finally {*/
            //DB.closeStatement(statement);
            //DB.closeConnection();
        //}

        //Atualizar dados
        /*try{
            statement = connection.prepareStatement(
                    "UPDATE seller SET basesalary = basesalary + ? WHERE (departmentid = ?)"
            );
            statement.setDouble(1, 200.0);
            statement.setInt(2, 2);

            int rowsAffected = statement.executeUpdate();

            System.out.println("Done! Rows Affected: " + rowsAffected);
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }*/

        //Deletar dados
        /*try {
            statement = connection.prepareStatement(
                    "DELETE FROM seller WHERE id = ?"
            );

            statement.setInt(1, 8);
            int rowsAffected = statement.executeUpdate();
            System.out.println("Done! Rows Affected: " + rowsAffected);

            //Problema de chave estrangeira se eu apagar o dado "pai"
            /*statement = connection.prepareStatement(
                    "DELETE FROM department WHERE id = ?"
            );
            statement.setInt(1, 2);
            rowsAffected = statement.executeUpdate();*/
        /*} catch (SQLException e){
            throw new DbIntegrityException(e.getMessage());
        }*/

        //Transações
;
        try {
            connection.setAutoCommit(false);

            query = connection.createStatement();
            int rows1 = query.executeUpdate(
                    "UPDATE seller SET basesalary = 2090 WHERE departmentid = 1"
            );

            //Erro na transação
            /*int x = 1;
            if(x < 2){
                throw new SQLException("fake error");
            }*/

            int rows2 = query.executeUpdate(
                    "UPDATE seller SET basesalary = 3090 WHERE departmentid = 2"
            );

            System.out.println("Rows Affected: " + rows1);
            System.out.println("Rows Affected: " + rows2);

            connection.commit();
        } catch (SQLException e){
            try {
                connection.rollback();
                throw new DbException(e.getMessage());
            } catch (SQLException ex) {
                throw new DbException(ex.getMessage());
            }
        }
    }
}
