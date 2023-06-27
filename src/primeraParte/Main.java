package primeraParte;

import primeraParte.servicios.ServicioBFS;
import primeraParte.servicios.ServicioCaminos;
import primeraParte.servicios.ServicioDFS;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main (String [ ] args) {

        Grafo<Integer> grafito = new GrafoDirigido<Integer>();

        grafito.agregarVertice(12);
        grafito.agregarVertice(21);
        grafito.agregarVertice(16);
        grafito.agregarVertice(3);
        grafito.agregarVertice(4);
        grafito.agregarVertice(5);
        grafito.agregarVertice(6);

        grafito.agregarArco(12,21,30);
        grafito.agregarArco(21,3,30);
        grafito.agregarArco(21,6,40);
        grafito.agregarArco(16,5,40);
        grafito.agregarArco(3,4,10);
        grafito.agregarArco(21,16,40);
        grafito.agregarArco(3,6,20);
        grafito.agregarArco(3,5,60);
        grafito.agregarArco(5,4,200);
        grafito.agregarArco(4,12,200);

        ServicioBFS<Integer> servicioVip = new ServicioBFS<>(grafito);
        ServicioCaminos caminos = new ServicioCaminos(grafito,12,12,11);
        List<Integer> lista = servicioVip.bfsForest();
        List<Integer> lista2 = servicioVip.bfsForest();

        ServicioDFS<Integer> servicioVip2 = new ServicioDFS<>(grafito);

        List<Integer> lista3 = servicioVip2.dfsForest();

        List<List<Integer>> lista4 = caminos.caminos();


        System.out.println("Lista 1");
        for( Integer i: lista)
            System.out.println(i.toString());
        System.out.println("Lista 2");
        for( Integer i: lista2)
            System.out.println(i.toString());
        System.out.println("Lista 3");
        for( Integer i: lista3)
            System.out.println(i.toString());
        System.out.println("Lista 4");
        for( List<Integer> i: lista4) {
            System.out.print("[");
            for (Integer i2 : i){
                System.out.print(i2.toString());
                System.out.print("-");
            }
            System.out.print("]");
        }

    }


}
