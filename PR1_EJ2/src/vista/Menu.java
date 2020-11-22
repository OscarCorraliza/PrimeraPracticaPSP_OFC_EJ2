package vista;

import dao.ConexionBBDD;
import modelo.Hilo;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Menu {
    Semaphore semaphore = new Semaphore(1);
    ConexionBBDD conexionBBDD = new ConexionBBDD();
    Scanner scanner = new Scanner(System.in);
    SumaSecuencial sumaSecuencial = new SumaSecuencial();

    private int contador = conexionBBDD.recogerNumeroTotalDeIngresos();
    private int incrementoHilo = contador / 5, resto = contador % 5;
    private int principio = 0, fin = incrementoHilo, sumaTotal = 0;
    private int principioHilo = 0, finHilo = 0;

    private String opcion;

    //Método que muestra el menu con sus respectivas opciones y las ejecuta.
    public void mostrarMenu(){
            do {
                System.out.println("0.Salir.\n1.Sumar todos los ingresos de manera secuencial.\n2.Sumas todos los ingresos con 5 hilos.");
                System.out.println("Deseo la opcion --> ");
                opcion = scanner.nextLine();
                switch (opcion) {
                    case "0" -> System.out.println("Ha salido, hasta pronto!!");

                    //Ejecuta el metodo sumar de la clase SumaSecuencial.
                    case "1" -> {
                        sumaSecuencial.sumar();
                    }

                    //Ejecutamos la suma total de los registros de la tabla registros con 5 hilos. Utilizamos un bucle for para crear los 5 hilos y le pasamos
                    // por parametros la información correspondiente para cada hilo.
                    //El if else se utiliza para que el ultimo hilo recorra el resto de los registros, en caso de que la division de el numero total de resgitros con los 5 hilos no sea excata.
                    case "2" -> {
                        try {
                            long inicio = System.currentTimeMillis();
                            for (int i = 0; i < 5; i++) {
                                principioHilo = principio + incrementoHilo * i;
                                if(i != 4){
                                    finHilo = fin * (i+1);
                                }else{
                                    finHilo = fin * (i+1+resto);
                                }
                                semaphore.acquire();
                                Hilo hilo = new Hilo(principioHilo, finHilo);
                                semaphore.release();
                                sumaTotal += hilo.leerYsumarIngresos();
                            }

                            long fin = System.currentTimeMillis();
                            double tiempo = (double) ((fin - inicio));

                            System.out.println("La suma total es de " + sumaTotal);
                            System.out.println("Han tardado un total de " + tiempo + " de milisegundos.");

                        } catch (InterruptedException e) {
                            System.out.println("Demasiadas conexiones simultaneas.");
                        }
                    }
                    default -> System.out.println("Opcion incorrecta, por favor introduzca una opción válida.\n");
                }
            } while (opcion.compareTo("0") != 0);
    }
}
