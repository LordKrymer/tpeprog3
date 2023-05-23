package primeraParte.servicios;

import primeraParte.Arco;
import primeraParte.Grafo;
import primeraParte.GrafoDirigido;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServicioBFS<T> {
    private Grafo<?> grafo;
    protected HashMap<Integer, Boolean> visitados;


    public ServicioBFS(Grafo<T> grafo) {
        this.grafo = grafo;
        this.visitados = new HashMap<>();

    }


    public List<Integer> bfsForest(Integer startingNode){

        return this.grafo.bfsForest(startingNode);
    }



    private List<Integer> getBfsForest( Integer vertice) {
        List<Integer> resultado = new ArrayList<>();
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(vertice);
        this.visitados.put(vertice, true); // marcar el vértice inicial como visitado
        while (!queue.isEmpty()) {
            Integer actual = queue.poll();
            resultado.add(actual);
            Iterator<Integer> adyacente = this.grafo.obtenerAdyacentes(actual);
            while (adyacente.hasNext()) {
                Integer ady = adyacente.next();
                if (!visitados.containsKey(ady)) {
                    visitados.put(ady, true);
                    queue.add(ady);
                }
            }
        }
        return resultado;
    }


}
