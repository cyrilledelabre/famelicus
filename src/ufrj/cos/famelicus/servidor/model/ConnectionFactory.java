//Objetivo: Factory para encapsular a criacao de conexoes com o banco de da-
//dos.
//
//Autor: Bruno Fraga
//Data: 17/02/2015
//
//Design Pattern: Factory

package ufrj.cos.famelicus.servidor.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static final String DATABASE_PATH = "jdbc:mysql://127.0.0.1/";
	public static final String DATABASE_NAME = "famelicus_bd";
	private static final String USER = "root";
	private static final String PASSWORD = "Amfn12092";//pVOldbAMJa2B
	
	
	private static ConnectionFactory INSTANCE = null;

    //singleton design pattern....we have only one class and only one connection.
    public static ConnectionFactory getInstance() {			
            if (INSTANCE == null){
            	INSTANCE = new ConnectionFactory();	
            }
            return INSTANCE;
    }


	public Connection getConnection() {

		// tentando abrir uma conexao
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conexao = DriverManager.getConnection(DATABASE_PATH + DATABASE_NAME, USER, PASSWORD);
			System.out.println("Conectado!");
			return conexao;	
		} catch (SQLException e){
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}