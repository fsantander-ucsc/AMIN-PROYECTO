/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reinas;

import java.util.ArrayList;
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
        
        //int valor = Integer.valueOf(args[0]);
         
        
        int count=0;
        System.out.println("Inicio");
        for (int i = 0; i < 10000; i++) {
            Tablero test = new Tablero(8);            
            
            if (test.getFitness() < 4) {
                //System.out.println("Iteración: "+i+" Fitnees: "+test.getFitness());
                test.printTablero();
                test.printFitness();
                count++;
            }

        }

        System.out.println(count);
        // creating an object of Random class   
        Random random = new Random();
        //Imprime n?meros entre 0 y 1
        //System.out.println(random.nextInt(2));
        
        //tambien se podr?a hacer
        //System.out.println((int) (Math.random() * (2)));
        //considerando una semilla deber?a ser
        Random seed = new Random(12);
        //System.out.println(seed.nextInt(2));

    }

}
