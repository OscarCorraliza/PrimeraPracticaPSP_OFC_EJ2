package vista;

import dao.ConexionBBDD;

public class SumaSecuencial {
    ConexionBBDD conexionBBDD = new ConexionBBDD();

    //Metodo que recoje la suma secuencia de todos los valores de tabla de regsitros y muestra dicho valor.
    public void sumar(){
        int sumaTotal = conexionBBDD.sumaSecuencial();
        System.out.println("La suma total es --> " + sumaTotal);
    }
}
