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

/**
 *
 * @author Pipe_
 */
public class Tablero {

    private int tablero[][];
    private int reinas = 0;
    private int dimension = 0;
    private int fitness = 0;

    private List<Integer> listaReinas = new ArrayList<>();

    public Tablero(int dimension) {

        this.dimension = dimension;
        inicializar();

    }

    private void inicializar() {

        // se inicializan las reinas en modo de un vector 
        // la posición i representa la columna 
        // el valor en la columna representa la fila
        //creo que esta inicialización no admite coliciones horizontales ni verticales 
        for (int i = 0; i < this.dimension; i++) {
            this.listaReinas.add(i);
        }
        //se revuelven las reinas para que se posicionen de manera randomica
        Collections.shuffle(this.listaReinas);

        List<Integer> colDiagonal_positiva = new ArrayList<>();
        List<Integer> colDiagonal_negativa = new ArrayList<>();        
        

        for (int i = 0; i < this.dimension; i++) {

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

    }

    public void printTablero() {
        this.tablero = new int[this.dimension][this.dimension];

        //se ponen las reinas en el tablero 
        //en realidad no es necesario, pero sirve para hacer el print
        for (int i = 0; i < this.dimension; i++) {
            int reina = this.listaReinas.get(i);
            for (int j = 0; j < this.dimension; j++) {
                this.tablero[i][j] = (reina == j) ? 1 : 0;
                System.out.print(this.tablero[i][j] + " ");
            }
            System.out.println(" ");
        }

    }

    public void printFitness() {
        System.out.println("Fitness: " + this.fitness);
    }

    public int getFitness() {
        return this.fitness;
    }

    public List<Integer> getListaReinas() {
        return this.listaReinas;
    }
}
