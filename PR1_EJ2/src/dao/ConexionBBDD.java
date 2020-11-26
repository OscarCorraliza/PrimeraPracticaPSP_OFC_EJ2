package dao;

import java.sql.*;

public class ConexionBBDD {
    Connection connection = null;

    //Método que se conecta a la base de datos.
    public Connection getConexion() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/BBDD_PSP_1", "DAM2020_PSP", "DAM2020_PSP");

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un probelma, no se ha podido conectar a la base de datos.");
        }
        return connection;
    }

    //Método que devuelve el numero total de registros que se encuentran en ese momento en la base de datos.
    public int recogerNumeroTotalDeIngresos() {
        int contador = 0;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/BBDD_PSP_1", "DAM2020_PSP", "DAM2020_PSP");
            Statement statement= connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT INGRESOS FROM `EMPLEADOS`");
            while(resultSet.next()) {
                contador++;
            }
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un probelma, no se ha podido conectar a la base de datos.");
        }
        return contador;
    }

    //Método que suma secuencialmente todos los valores de la table registros de la base de datos y devuelve dicha suma y muestra el tiempo que ha empleado.
    public int sumaSecuencial() {
        long inicio = System.currentTimeMillis();
        int ingreso, sumaIngreso = 0;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/BBDD_PSP_1", "DAM2020_PSP", "DAM2020_PSP");
            Statement statement= connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT INGRESOS FROM `EMPLEADOS`");
            while(resultSet.next()) {
                ingreso = resultSet.getInt("INGRESOS");
                sumaIngreso += ingreso;
            }
        } catch (SQLException e) {
            System.out.println("Ha ocurrido un problema, no se ha podido conectar a la base de datos.");
        }

        long fin = System.currentTimeMillis();
        double tiempo = (double) ((fin - inicio));

        System.out.println("Ha tardado un total de " + tiempo +" milisegundos");
        return sumaIngreso;
    }
}
