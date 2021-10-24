/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reinas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static reinas.Tablero.dimension;

/**
 *
 * @author Pipe_
 */
public class Reinas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //se definen las variables a utilizar
        int tablero, seed, numero_iteraciones, auxIteracion, tamano_poblacion, mejorFitness, numero_hijo, numero_mejor_hijo;
        double probabilidad_cruza, probabilidad_mutacion;
        List<Tablero> padres = new ArrayList<>();
        List<Tablero> hijos = new ArrayList<>();
        List<Integer> reinas = null;
        List<Double> ruleta = new ArrayList<>();

        //esto sólo se hace para que no me diga que los valores no están asignados
        //es que la captura está siendo hecha en un try/catch
        tamano_poblacion = 1000;
        numero_iteraciones = 100000;
        tablero = 100;
        seed = 1;
        probabilidad_cruza = 5 / 100.0;
        probabilidad_mutacion = 5 / 100.0;
        
        //tp 100, ti 10000 t 20  s2 pc 5 pm 5 da resultado todas las iteraciones con mal fit
        
        /*
        try {
            if (args.length < 6) {
                System.err.println("El mínimo de parámetros es 6");
                System.exit(1);
            }
            seed = Integer.valueOf(args[0]);
            tablero = Integer.valueOf(args[1]);
            tamano_poblacion = Integer.valueOf(args[2]);
            probabilidad_cruza = Integer.valueOf(args[3]);
            probabilidad_mutacion = Integer.valueOf(args[4]);
            numero_iteraciones = Integer.valueOf(args[5]);

        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
         */
        
        auxIteracion = 0;
        numero_hijo = 0;
        numero_mejor_hijo = 0;
        //el mejor fitness se inicializa como max valu
        //es casi como decir "infinito"
        mejorFitness = Integer.MAX_VALUE;

        System.out.println("Inicialización");

        Random random = new Random();
        //inicialización de la semilla       
        random.setSeed(seed);
        //se guarda la dimensipon del tablero dentro del objeto
        //así es más sencillo recurrir a el dentro del objeto
        Tablero.dimension = tablero;

        for (int i = 1; i < tamano_poblacion + 1; i++) { 
            int rndNumber = random.nextInt(100) + 1;
            Tablero test = new Tablero(rndNumber);            
            padres.add(test);
        }

        System.out.println("Fin inicialización");

        //ruleta es la suma acumulada de las proporciones
        //a mayor proporción se tiene un espacio más grande dentro de la ruleta
        hacerRuleta(padres, ruleta);

        //nota, la posición en la ruleta es la posición dentro de la lista de tableros
        //por lo que el valor de ruleta podría ser un atributo dentro del objeto tablero
       
        //se inicializan estas variables del objeto
        //para poder calcular nuevos valores de proporción
        Tablero.suma = 0;
        Tablero.sumaFit = 0;

        System.out.println("Inicio Cruza");
        while (auxIteracion < numero_iteraciones && mejorFitness != 1) {

            int t1 = -1, t2 = -1;
            //guardamos la posición del objeto dentro de t1 y t2
            //como es -1 y hablamos de una lista, ningún objeto debería
            //llegar a ese valor
            //luego se compara ese valor para saber si los dos padres son el mismo objeto
            //se podría guardar el objeto, pero consume un poco más de memoria
            double rnd_1, rnd_2;

            while (t1 == t2) {
                rnd_1 = random.nextDouble();
                rnd_2 = random.nextDouble();
                t1 = encontrarEnRuleta(ruleta, rnd_1);
                t2 = encontrarEnRuleta(ruleta, rnd_2);
            }

            //numero random a ver si se cruzan
            double rnd_3 = random.nextDouble();
            
            if (rnd_3 <= probabilidad_cruza) {
                //se recuperan los padres en base a la ruleta
                Tablero padre = padres.get(t1);
                Tablero madre = padres.get(t2);

                //proporción de los genes padres que pasasrá al hijo
                int proporcion = (int) (tablero * random.nextDouble());

                //se crean dos hijos a partir de los padres      
                //se hace un if a ver si se hace o no mutación 
                int rndNumber =random.nextInt();
                Tablero h1 = probabilidad_mutacion < random.nextDouble() ? new Tablero(rndNumber, padre, madre, proporcion, true) : new Tablero( rndNumber,padre, madre, proporcion, false);
                Tablero h2 = probabilidad_mutacion < random.nextDouble() ? new Tablero( rndNumber, madre, padre, proporcion, true) : new Tablero(rndNumber, madre, padre, proporcion, false);

                hijos.add(h1);
                hijos.add(h2);
                numero_hijo += 2;

                if (h1.getFitness() < mejorFitness) {
                    mejorFitness = h1.getFitness();
                    reinas = new ArrayList<>(h1.getListaReinas());
                    numero_mejor_hijo = numero_hijo;

                }
                if (h2.getFitness() < mejorFitness) {
                    mejorFitness = h2.getFitness();
                    reinas = new ArrayList<>(h1.getListaReinas());
                    numero_mejor_hijo = numero_hijo;
                }

                //En este punto se podría elegir otro método para escoger el
                //siguiente padre, pero acá los hijos sí o sí se vuelven padres
                if (hijos.size() >= padres.size()) {
                    //se aumentan las hiteraciones
                    auxIteracion++;
                    //los hijos se vuelven los padres
                    padres = new ArrayList<>(hijos);
                    //limpiamos la lista de hijos por seguridadad;
                    hijos.clear();
                    //limpiamos la ruleta por seguridad
                    //y se genera con la población hija
                    ruleta.clear();
                    hacerRuleta(padres, ruleta);
                    //reiniciamos suma y suma fit de la clase Tablero
                    //se reinician al final pues antes se necesitan para hacer la ruleta
                    Tablero.suma = 0;
                    Tablero.sumaFit = 0;

                }
            }
        }

        System.out.println("-----------------------------------");
        System.out.println("Poblacion: " + tamano_poblacion);
        System.out.println("Iteraciones: " + numero_iteraciones);
        System.out.println("Probabilidad de cruza: " + probabilidad_cruza);
        System.out.println("Probabilidad de mutación: " + probabilidad_mutacion);
        System.out.println("-----------------------------------");
        System.out.println("Iteraciones hechas: " + auxIteracion);
        System.out.println("Hijos totales: " + numero_hijo);
        System.out.println("Hijo con mejor Fitness: " + numero_mejor_hijo);
        System.out.println("Mejor fit: " + mejorFitness);
        System.out.println("Reinas: " + reinas);
        //imprimeTablero(reinas, tablero);

    }
    /**
     * 
     * @param lista lista con los valores de ruleta
     * @param number un número de cualquier tipo
     * @return Integer con la posición del numero dentro de la lista
     */
    static private int encontrarEnRuleta(List lista, Number number) {
        //retorna el posición del elemento igual o menor dentro de la lista
        //se asume que la lista está ordenada y contine números de algúntipo
        //si el arreglo es [2,4,6,8,20]
        // al buscar 4 , retorna la posicón 1
        //al buscar 12 retorna 20 (-3) (porque 12 es menor a 20)        

        int aux = Collections.binarySearch(lista, number);
        if (aux < 0) {
            //la busqueda binaria devuelve la -(posición)-1 si el numero no está en la lista
            //pr lo que se suma 1 y se multiplica por -1 para el valor que corresponde
            aux = (aux + 1) * -1;
        }
        return aux;
    }
    /**
     * 
     * @param reinas lista con las reinas
     * @param tablero largo o ancho del tablero
     */
    static private void imprimeTablero(List<Integer> reinas, int tablero) {
        for (int i = 0; i < tablero; i++) {
            int reina = reinas.get(i);
            for (int j = 0; j < tablero; j++) {
                System.out.print((reina == j ? 1 : 0) + " ");
            }
            System.out.println(" ");
        }
    }
    
    /**
     * 
     * @param lista lista de la que se obtendran los valores de ruleta
     * @param ruleta lista en la que se almacenarán lso valores de ruleta
     */
    static private void hacerRuleta(List<Tablero> lista, List<Double> ruleta) {

        ruleta.add(lista.get(0).getProporcion());
        for (int i = 1; i < lista.size(); i++) {
            ruleta.add(ruleta.get(i - 1) + lista.get(i).getProporcion());
        }

    }
}

