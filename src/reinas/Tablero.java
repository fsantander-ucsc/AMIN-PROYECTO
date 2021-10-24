/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reinas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Pipe_
 */
public class Tablero {

    private int reinas = 0;
    
    //el valor perfecto de fitnes es 1, cualquier otro valor ya entra en decadencia
    private int fitness = 1;
    //el proporcional a mayor valor es mejor
    private double fitnessProporcional = 1.0;
    private List<Integer> listaReinas = new ArrayList<>();

    static int suma = 0;
    static double sumaFit = 0;
    //valor por defecto 4, porque es el mínimo que regreesa una solución
    //así el programa tampoco cae por tener dimensión 0, sería raro
    static int dimension = 4;

    public Tablero(int seed) {
        inicializar(seed);
        calcularFitness();
    }

    /**
     *
     * @param seed semilla del tablero, basicamente para hacer el shuffle
     * @param padre Tablero Padre
     * @param madre Tablero Madre
     * @param proporcion Proporción en la que se dividirán los genes
     * @param mutacion booleano que indica si ocurre o no una mutación de genes
     */
    public Tablero(int seed, Tablero padre, Tablero madre, int proporcion, boolean mutacion) {

        inicializarConCruza(padre, madre, proporcion);

        if (mutacion) {
            mutacion(seed);
        }

        calcularFitness();
    }

    /**
     *
     * @param seed semilla de la inicialización, se usa para el shuffle
     */
    private void inicializar(int seed) {

        // se inicializan las reinas en modo de un vector 
        // la posición i representa la columna 
        // el valor en la columna representa la fila
        //creo que esta inicialización no admite coliciones horizontales ni verticales 
        for (int i = 0; i < dimension; i++) {
            this.listaReinas.add(i);
        }
        //se revuelven las reinas para que se posicionen de manera randomica
        //al agregar un segundo parámetro de tipo random, podemos poner una seed en 
        // al revolver los item y siempre se revolverán igual        

        Collections.shuffle(this.listaReinas, new Random(seed));

    }

    private void inicializarConCruza(Tablero padre, Tablero madre, int proporcion) {

        //clonamos la lista de reinas del padre
        //de esta forma queda no referenciada       
        //subPadre corresponde a toda la lista padre
        List<Integer> subPadre = new ArrayList<>(padre.getListaReinas());
        //subMadre va desde el valor proporción hasta el final de la lista
        List<Integer> subMadre = new ArrayList<>(madre.getListaReinas().subList(proporcion, dimension));

        //agregamos TODA la proporción del padre al hijo
        //y removemos el elemento de la lista subPadre
        for (int i = 0; i < proporcion; i++) {
            int reina = padre.getListaReinas().get(i);
            this.listaReinas.add(reina);
            //se TIENE que buscar el index y eliminar el mismo
            subPadre.remove(subPadre.indexOf(reina));
        }

        //eliminamos las coincidencias de lo que queda del padre
        //de este modo el padre SOLO tendrá cosas que no tenga la lista subMadre
        for (int i = 0; i < subPadre.size(); i++) {
            if (subMadre.contains(subPadre.get(i))) {
                subPadre.remove(i);
                i--;
            }
        }

        //recorremos la lista subMadre
        //si el elemento de subMadre está en la lista hija
        //no se agrega y se pone el primer elemento de lo que queda del padre
        //al mismo tiempo se remueve
        for (int i = 0; i < subMadre.size(); i++) {
            if (this.listaReinas.contains(subMadre.get(i))) {
                this.listaReinas.add(subPadre.get(0));
                subPadre.remove(0);
            } else {
                this.listaReinas.add(subMadre.get(i));
            }
        }

    }

    /**
     *
     * @param seed semilla de la mutación, de esta forma se generan numero
     * aleatoreos siempre
     */
    private void mutacion(int seed) {
        //intercambia dos reinas para generar una mutación

        Random rnd = new Random();
        rnd.setSeed(seed);

        int rnd_1 = 0, rnd_2 = 0;

        while (rnd_1 == rnd_2) {
            rnd_1 = rnd.nextInt(dimension);
            rnd_2 = rnd.nextInt(dimension);
        }

        Collections.swap(this.listaReinas, rnd_1, rnd_2);
    }

    private void calcularFitness() {

        //toma la lista de reinas del objeto y calcula el fitness del tablero generado
        //en this.fitness se guarda el fitness del tablero
        //a mayor valor, peor es el fitness
        List<Integer> colDiagonal_positiva = new ArrayList<>();
        List<Integer> colDiagonal_negativa = new ArrayList<>();

        //se revisan ambas diagonales en un mismo ciclo
        for (int i = 0; i < dimension; i++) {

            int fila = i;
            int columna = this.listaReinas.get(i);

            //colisiones en diagonal
            int aux = (fila - columna);
            if (colDiagonal_positiva.contains(aux)) {
                this.fitness++;
            } else {
                colDiagonal_positiva.add(aux);
            }
            //Colisiones en diagonal invesa
            int aux2 = fila + columna;
            if (colDiagonal_negativa.contains(aux2)) {
                this.fitness++;
            } else {
                colDiagonal_negativa.add(aux2);
            }
        }

        //de manera estática se almacena la suma de todos los fitnes
        this.suma += this.fitness;
        //para el objeto también se almacena el fitness proporcional
        this.fitnessProporcional = 1.0 / (double) this.fitness;
        //sumaFit es lo mismo que suma, pero con los valores proporcionales
        //se pueden usar ambos en diferentes escenarios
        this.sumaFit += this.fitnessProporcional;

    }

    public void printTablero() {

        //imprime el tablero y ya, no hay mucho que hacer
        //se ponen las reinas en el tablero 
        //en realidad no es necesario, pero sirve para hacer el print
        for (int i = 0; i < dimension; i++) {
            int reina = this.listaReinas.get(i);
            for (int j = 0; j < dimension; j++) {
                System.out.print((reina == j ? 1 : 0) + " ");
            }
            System.out.println(" ");
        }

    }

    public void printFitness() {
        System.out.println("Fitness: " + this.fitness);
    }

    public int getFitness() {
        return (int) this.fitness;
    }

    public List<Integer> getListaReinas() {
        return this.listaReinas;
    }

    public double getProporcion() {
        return this.fitnessProporcional / this.sumaFit;
    }
}
