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

    private int tablero[][];
    private int reinas = 0;
    private int seed = 0;
    private int fitness = 1;
    private double fitnessProporcional = 0.0;
    private double ruleta = 0.0;
    private List<Integer> listaReinas = new ArrayList<>();

    static int suma = 0;
    static double sumaFit = 0;
    static int suma_aux = 0;
    static double sumaFit_aux = 0;
    static int dimension = 0;

    public Tablero(int seed) {

        this.seed = seed;
        inicializar();
        calcularFitness();
    }

    public Tablero(Tablero padre, Tablero madre) {

        inicializarConCruza(padre, madre);
        calcularFitness();

    }

    private void inicializar() {

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
        Collections.shuffle(this.listaReinas, new Random(this.seed));

    }

    private void inicializarConCruza(Tablero padre, Tablero madre) {

        //clonamos la lista de reinas del padre
        //de esta forma queda no referenciada
        Random rnd = new Random();
        //proporción de los padres que pasasrá al hijo
        int proporcion = (int) (dimension * rnd.nextDouble());
        //subPadre corresponde a toda la lista padre
        List<Integer> subPadre = new ArrayList<>(padre.getListaReinas());
        //subMadre va desde el valor proporción hasta el final de la lista
        List<Integer> subMadre = new ArrayList<>(padre.getListaReinas().subList(proporcion, dimension));

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

    private void calcularFitness() {
        List<Integer> colDiagonal_positiva = new ArrayList<>();
        List<Integer> colDiagonal_negativa = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {

            int fila = i;
            int columna = this.listaReinas.get(i);

            //colisiones en diagonal
            int aux = (fila - columna);
            if (colDiagonal_positiva.contains(aux)) {
                this.fitness++;
                //System.out.println("x: "+fila+" y: "+columna);
            } else {
                colDiagonal_positiva.add(aux);
                //System.out.println("x: "+fila+" y: "+columna);
            }
            //Colisionesn en diagonal invesa
            int aux2 = fila + columna;
            if (colDiagonal_negativa.contains(aux2)) {
                this.fitness++;
            } else {
                colDiagonal_negativa.add(aux2);
            }
        }

        this.suma += this.fitness;
        this.fitnessProporcional = 1.0 / (double) this.fitness;
        this.sumaFit += this.fitnessProporcional;

    }

    public void printTablero() {
        this.tablero = new int[dimension][dimension];

        //se ponen las reinas en el tablero 
        //en realidad no es necesario, pero sirve para hacer el print
        for (int i = 0; i < dimension; i++) {
            int reina = this.listaReinas.get(i);
            for (int j = 0; j < dimension; j++) {
                this.tablero[i][j] = (reina == j) ? 1 : 0;
                System.out.print(this.tablero[i][j] + " ");
            }
            System.out.println(" ");
        }

    }

    public void printFitness() {
        System.out.println("Fitness: " + this.fitness);
    }

    public double getFitness() {
        return (double) this.fitness;
    }

    public List<Integer> getListaReinas() {
        return this.listaReinas;
    }

    public double getProporcion() {
        return this.fitnessProporcional / this.sumaFit;
    }

    public void setRuleta(double numero) {
        this.ruleta = numero;
    }

}
