package HospitalMangementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
    private Connection connection;

    public  Doctors(Connection connection){
        this.connection=connection;

    }
    public void view_doctors()
    {
        String query = "select * from doctors";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors Data:");
            System.out.println("+-------------+--------------------------------+--------------------+");
            System.out.println("| Doctor Id   |      Name                      | specialty          |");
            System.out.println("+-------------+--------------------------------+--------------------+");
            while(resultSet.next()){
                int d_id = resultSet.getInt("d_id");
                String name = resultSet.getString("name");
                String specialty = resultSet.getString("specialty");
                System.out.printf("|%-13s|%-32s|%-20s|\n",d_id, name, specialty);
                System.out.println("+-------------+--------------------------------+--------------------+");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean Check_Doctor_byID(int d_id){
        String query = "select * from doctors where d_id=?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,d_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return  true;
            }else
            {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


}
