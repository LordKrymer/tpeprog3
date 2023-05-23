package primeraParte.servicios;

import primeraParte.Grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicioDFS<T> {
    private Grafo<?> grafo;

    protected HashMap<Integer, Boolean> visitados;

    public ServicioDFS(Grafo<?> grafo) {
        this.grafo = grafo;
        this.visitados = new HashMap<>();
    }


    public List<Integer> dfsForest() {
        return this.grafo.dfsForest();
    }



}
