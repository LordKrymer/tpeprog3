package segundaParte;

import primeraParte.Arco;

import java.util.ArrayList;

public class MainSegundaParte {
    public static void main (String [ ] args) {

        System.out.println("-----------------------------------------DATASET 1 ----------------------------------------------");

        long startTimeBacktracking = System.nanoTime();
        ProblemaTunnel resolucion = new ProblemaTunnel("src/segundaParte/datasets/dataset1.txt");
        ArrayList resultado = resolucion.findMinimumPath();
        System.out.println("longitud = "+ resolucion.getMinimumPathLength());
        System.out.println("iteraciones = "+ resolucion.getIteraciones());
        resultado.forEach(System.out::println);
        long endTimeBacktracking = System.nanoTime();
        long totalTimeBacktracking = endTimeBacktracking - startTimeBacktracking;
        System.out.println("Tiempo de ejecución backtracking : " + totalTimeBacktracking + " nanosegundos");

        System.out.println("-----------------------------------------DATASET 2----------------------------------------------");

        long startTimeBacktracking2 = System.nanoTime();
        ProblemaTunnel resolucion2 = new ProblemaTunnel("src/segundaParte/datasets/dataset2.txt");
        ArrayList resultado2 = resolucion2.findMinimumPath();
        System.out.println("longitud = "+ resolucion2.getMinimumPathLength());
        System.out.println("iteraciones = "+ resolucion2.getIteraciones());
        resultado2.forEach(System.out::println);
        long endTimeBacktracking2 = System.nanoTime();
        long totalTimeBacktracking2 = endTimeBacktracking2 - startTimeBacktracking2;
        System.out.println("Tiempo de ejecución backtracking2 : " + totalTimeBacktracking2 + " nanosegundos");

/*        System.out.println("-----------------------------------------DATASET 3----------------------------------------------");

        long startTimeBacktracking3 = System.nanoTime();
        ProblemaTunnel resolucion3 = new ProblemaTunnel("src/segundaParte/datasets/dataset3.txt");
        ArrayList resultado3 = resolucion3.findMinimumPath();
        System.out.println("longitud = "+ resolucion3.getMinimumPathLength());
        System.out.println("iteraciones = "+ resolucion3.getIteraciones());
        resultado3.forEach(System.out::println);
        long endTimeBacktracking3 = System.nanoTime();
        long totalTimeBacktracking3 = endTimeBacktracking3 - startTimeBacktracking3;
        System.out.println("Tiempo de ejecución backtracking2 : " + totalTimeBacktracking3 + " nanosegundos");*/

        System.out.println("-----------------------------------------Greedy----------------------------------------------");

        System.out.println("-----------------------------------------DATASET 1 ----------------------------------------------");
        ProblemaTunnel greedy1 = new ProblemaTunnel("src/segundaParte/datasets/dataset1.txt");
        System.out.println(greedy1.greedySolution());
        System.out.println("iteraciones: " + greedy1.getIteraciones());
        System.out.println("Longitud: " + greedy1.getMinimumPathLength());
        System.out.println("-----------------------------------------DATASET 2 ----------------------------------------------");

        ProblemaTunnel greedy2 =new ProblemaTunnel("src/segundaParte/datasets/dataset2.txt");
        System.out.println(greedy2.greedySolution());
        System.out.println("iteraciones: " + greedy2.getIteraciones());
        System.out.println("Longitud: " + greedy2.getMinimumPathLength());

        System.out.println("-----------------------------------------DATASET 3 ----------------------------------------------");
        ProblemaTunnel greedy3 =new ProblemaTunnel("src/segundaParte/datasets/dataset3.txt");
        System.out.println(greedy3.greedySolution());
        System.out.println("iteraciones: " + greedy3.getIteraciones());
        System.out.println("Longitud: " + greedy3.getMinimumPathLength());

    }
}
