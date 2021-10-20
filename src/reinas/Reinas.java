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

/**
 *
 * @author Pipe_
 */
public class Reinas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //int seed = Integer.valueOf(args[0]);
        //int tablero = Integer.valueOf(args[1]);
        //int tamano_población = Integer.valueOf(args[2]);
        //int probabilidad_cruza = Integer.valueOf(args[3]);
        //int probabilidad_mutacion =Integer.valueOf(args[4]);
        //int numero_iteraciones=Integer.valueOf(args[5]);
        System.out.println("Inicio");
        int dimension, seed, iteraciones;
        double probCruza;

        List<Tablero> lista = new ArrayList<>();

        iteraciones = 1000000;
        dimension = 5;
        seed = 4;
        probCruza = 5.0 / 100.0;

        Random random = new Random();

        //inicialización de la semilla
        random.setSeed(seed);
        Tablero.dimension = dimension;

        for (int i = 1; i < iteraciones + 1; i++) {
            int rndNumber = random.nextInt(100) + 1;
            //se ingresa la seed al tablero
            Tablero test = new Tablero(rndNumber);
            //test.printTablero();
            //System.out.println("");
            lista.add(test);
        }
       
        
        System.out.println("Fin inicialización");

        List<Double> ruleta = new ArrayList<>();

        //ruleta es la suma acumulada de las proporciones
        //a mayor proporción se tiene un espacio más grande dentro de la ruleta
        System.out.println("Inicio Ruleta");
        ruleta.add(lista.get(0).getProporcion());
        for (int i = 1; i < lista.size(); i++) {            
            ruleta.add(ruleta.get(i - 1) + lista.get(i).getProporcion());
        }
        System.out.println("Fin Ruleta");

        //nota, la posición en la ruleta es la posición dentro de la lista de tableros
        //por lo que el valor de ruleta podría ser un atributo dentro del objeto tablero
        Random rnd = new Random();

        //se inicializan estas variables del objeto
        //para poder calcular nuevos valores de proporción
        Tablero.suma = 0;
        Tablero.sumaFit = 0;

        List<Tablero> hijos = new ArrayList<>();
        System.out.println("Inicio Cruza");
        for (int i = 0; i < iteraciones; i++) {

            int t1 = -1, t2 = -1;

            //guardamos la posición del objeto dentro de t1 y t2
            //como es menos -1 y hablamos de una lista, ningún objeto debería
            //llegar a ese valor
            //luego se compara ese valor para saber si los dos padres son el mismo objeto
            //se podría guardar el objeto, pero consume un poco más de memoria
            double rnd_1, rnd_2;

            while (t1 == t2) {
                rnd_1 = rnd.nextDouble();
                rnd_2 = rnd.nextDouble();
                t1 = encontrarEnRuleta(ruleta, rnd_1);
                t2 = encontrarEnRuleta(ruleta, rnd_2);
            }

            //numero random a ver si se cruzan
            double rnd_3 = rnd.nextDouble();
            if (rnd_3 <= probCruza) {
                Tablero padre = lista.get(t1);
                Tablero madre = lista.get(t2);
                //se crea un hijo a partir de los padres
                hijos.add(new Tablero(padre, madre));                
            }
        }
        int count =0;
         for(Tablero t : hijos){
            if(t.getFitness()==1)
               count++;
        }
        System.out.println("Iteraciones: "+ iteraciones);        
        System.out.println("Cruzas totales: " + hijos.size());
        System.out.println("Hijos fit 1: "+count);
        double truncate = Math.floor(count*100.0/hijos.size() * 1000.0) / 1000.0;
        System.out.println("Correspondiente al "+truncate+"%");

    }

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
}
