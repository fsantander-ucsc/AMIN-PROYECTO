# Amin-proyecto-1!

Desarrollar un programa seleccionando un lenguaje de programación entre C, C++, Java (en este caso Java), Python o Ruby que implemente el problema de las N-Reinas usando algoritmos genéticos. Primero se debe definir como modelar el tablero en un cromosoma a través de una estructura de datos, como por ejemplo, un vector. El código debe de tener al menos las siguientes funciones:


# Archivos

Está disponible todo el código fuente para descargar y compilar, pero también está el archivo ya compilado en:

> dist->Reinas.jar

## Ejecución

   

    java -jar .\Reinas.jar <Valor de la semilla><Tamaño del tablero><Tamaño de la población><Probabilidad de cruza><Probabilidad de mutación><Número de iteraciones>

Donde:

 - **Valor de la semilla** :  Valor entero
- **Tamaño del tablero**: Dimensiones del tablero, en caso de ser un tablero de 8x8 se debe ingresar 8.
- **Tamaño de la población**: Entero que representa la cantidad de individuos a crear y replicar.
- **Probabilidad de cruza**: Valor entero entre 0 y 100, que representa la probabilidad de que dos individuos se crucen y generen descendencia
- **Probabilidad de mutación**: Valor entre 0 y 100 que representa la probabilidad de que la descendencia genere una mutación de sus genes
- **Número de iteraciones**: Numero entero que representa la cantidad de itereaciones a realizar, el programa se ejecutará hasta encontrar esta limitante o hasta encontrar un individuo con fitness 1.

## Ejemplo de Ejecución
    
    java -jar .\Reinas.jar 12 20 100 5 5 10000
