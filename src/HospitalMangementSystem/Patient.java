package HospitalMangementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
   private Connection connection;
   private Scanner scanner;
   public  Patient(Connection connection, Scanner scanner){
       this.connection=connection;
       this.scanner=scanner;
   }
   public void add_patient(){
       System.out.println("Enter Patient's Name : ");
       String name = scanner.next();
       System.out.println("Enter Patient's Age : ");
       int age = scanner.nextInt();
       System.out.println("Enter Patient's Gender : ");
       String gender = scanner.next();

       try{
           String query = "insert into patients(name, age, gender) values(?, ?, ?)";
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setString(1,name);
           preparedStatement.setInt(2,age);
           preparedStatement.setString(3,gender);
           int affectedRows = preparedStatement.executeUpdate();
           if(affectedRows>0)
           {
               System.out.println("Patient Information Recorded Successfully");
           }
           else {
               System.out.println("Failed to add !!!");
           }

       }catch (SQLException e) {
           e.printStackTrace();
       }
   }
   public void view_patient()
   {
       String query = "select * from patients";
       try{
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           ResultSet resultSet = preparedStatement.executeQuery();
           System.out.println("Patients Data:");
           System.out.println("+-------------+--------------------------------+----------+----------+");
           System.out.println("| Patient Id  |      Name                      | Age      | Gender   |");
           System.out.println("+-------------+--------------------------------+----------+----------+");
           while(resultSet.next()){
               int p_id = resultSet.getInt("p_id");
               String name = resultSet.getString("name");
               int age = resultSet.getInt("age");
               String gender = resultSet.getString("gender");
               System.out.printf("|%-14s|%-32s|%-10s|%-10s|\n",p_id, name, age, gender);
               System.out.println("+-------------+--------------------------------+----------+----------+");
           }
       }catch(SQLException e){
           e.printStackTrace();
       }
   }
   public boolean Check_Patient_byID(int p_id){
       String query = "select * from patients where p_id=?";
       try
       {
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1,p_id);
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

