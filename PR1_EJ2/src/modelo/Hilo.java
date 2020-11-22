package modelo;

import dao.ConexionBBDD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Hilo extends Thread{
    ConexionBBDD conexionBBDD = new ConexionBBDD();
    int contador, principio = 0, incrementoBucle = contador / 5, fin = incrementoBucle, contadorIdParaHilo;
    int suma, ingreso;
    private int contadorTotal = conexionBBDD.recogerNumeroTotalDeIngresos();

    //Constructor al que le pasamos como parametros el principio y el fin del numeros de registrso de la tabla ingresos va a sumar cada hilo.
    public Hilo (int principio, int fin){
        this.principio = principio;
        this.fin = fin;
    }

    @Override
    public void run() {
        super.run();

        leerYsumarIngresos();
    }

    //Método por el cual cada hilo va a acceder a la base de datos y va a recoger el valor de la tabla de registros unicamente
    // de los empleados que necesita, para ello accedemos al valor por el id del cliente que le pasamos por parametros al constructor.
    //El ultimo if es para leer el ultimo resgistro de toda la vase de datos para que no se quede sin ser leido.
    public Integer leerYsumarIngresos(){
        try {
            while (principio != fin){
                contadorIdParaHilo = principio;

                    Connection conexionAuxiliar = conexionBBDD.getConexion();
                    Statement statement= conexionAuxiliar.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT INGRESOS FROM `EMPLEADOS` WHERE `ID` = " + contadorIdParaHilo);
                    while(resultSet.next()) {
                        ingreso = resultSet.getInt("INGRESOS");
                        suma += ingreso;
                    }
                    contadorIdParaHilo++;
                    principio++;
                if (principio == contadorTotal){
                    resultSet = statement.executeQuery("SELECT INGRESOS FROM `EMPLEADOS` WHERE `ID` = " + contadorTotal);
                    while(resultSet.next()) {
                        ingreso = resultSet.getInt("INGRESOS");
                        suma += ingreso;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Ha ocurrido un probelma, no se ha podido conectar a la base de datos.");
        }
        return suma;
    }
}
