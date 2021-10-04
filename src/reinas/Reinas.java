/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reinas;

import java.util.ArrayList;
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
        // creating an object of Random class   
        Random random = new Random();
        //Imprime n?meros entre 0 y 1
        System.out.println(random.nextInt(2));
        //tambien se podr?a hacer

        System.out.println((int) (Math.random() * (2)));

        //considerando una semilla deber?a ser
        Random seed = new Random(12);
        System.out.println(seed.nextInt(2));

        //inicializar un vector de N t?rminos
        int dimension = 3;
        List<Integer> tablero = new ArrayList<>();

        int reinas = 0;
        //inicializamos todas las posiciones dentro del vencto a 0
        for (int i = 0; i < dimension * dimension; i++) {
            tablero.add(0);
        }

        while (dimension != reinas) {
            //genermos posiciones entre 0 y n 
            //le damos un valor random entre 0 y 1
            //donde 0 = nada, 1 = reina
            int posicion = random.nextInt(dimension * dimension);
            // si la posici?n dentro del tablero est? en 0 se pone una reina
            //y se aumenta el contador
            //si est? en 0 se busca otra posici?n

            if (tablero.get(posicion) == 0) {
                tablero.set(posicion, 1);
                reinas++;
            }
        }
        System.out.println(tablero.size());
        System.out.println(tablero);

        //como sabemos la dimension del tablero 
        //es f?cil saber que cada n posiciones comienza una fila nueva 
        //por lo que para encontrar colision horizontal s?lo revisamos cada n valores
        //para saber cuando comienza una nueva fila podemos utilizar el m?dulo y la posic?n 
        // revisamos cada fila y si al finalizar la suma de reinas es mayor a 1 
        //hay una colici?n
        /**
         * Colision horizontal
         */
        int fitnes = 0;
        int colisiones = 0;

        for (int i = 0; i < tablero.size(); i++) {

            //como los valores de reinas son 1 y el resto es 0, s?lo sumamos
            colisiones += tablero.get(i);
            // si el modulo es 0 sabemos que lleamos a un extremo del tablero
            if ((i + 1) % dimension == 0) {
                //si las colisiones son mayores a 1, pues hay una colision
                //un 1 indica que hay una reina
                if (colisiones > 1) {
                    fitnes += colisiones;
                }
                //inicializaciones las colisiones para la siguiente ronda
                colisiones = 0;

            }
        }
        //nota, tambien se podr?a simplemente agregar la unica reina al fitnes
        //de no haber ninguna colisi?n, pues se van a tener dimensiones * 4 para el fitnes perfecto
        //porque se encontrarian n horizontales, n verticales, n diagonales, n la otra diagonal

        /**
         * Colsi?n vertiva
         */
        //como es un vector es un poco m?s complicado
        //pero la verdad es que s?lo hay que hacer 
        //un for hasta "dimension" y sumar tantas "dimensiones" al i que se vea para recorrer en vertical
        //el an?lisis ser? el mismo que el del horizontal
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                //recorremos verticalmente el tablero
                colisiones += tablero.get(i + j * dimension);
            }
            //si las colisiones son mayores a 1, pues hay una colision
            //un 1 indica que hay una reina
            if (colisiones > 1) {
                fitnes += colisiones;

            }
            //inicializaciones las colisiones para la siguiente ronda
            colisiones = 0;
        }
        /**
         * diagonal 1
         */

        for (int i = 0; i < dimension; i++) {
            
        }

    }

}
