package primeraParte.servicios;

import primeraParte.Grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ServicioDFS<T> {
    private Grafo<?> g;

    protected HashMap<Integer, Boolean> visitados;

    public ServicioDFS(Grafo<?> grafo) {
        this.g = grafo;
        this.visitados = new HashMap<>();
    }


    public List<Integer> dfsForest() {
        this.visitados.clear();
        List<Integer> result = new ArrayList<>();
        for (Iterator<Integer> it = g.obtenerVertices(); it.hasNext();) {
            Integer value = it.next();
            Iterator<Integer> ady = g.obtenerAdyacentes(value);
            getDfsForest(value, result, ady);
        }
        return result;
    }
    private List<Integer> getDfsForest( Integer valor, List<Integer> result, Iterator<Integer> ady) {
        if (!visitados.containsKey(valor)){
            result.add(valor);
            visitados.put(valor,true);
            while (ady.hasNext()) {
                int num = ady.next();
                getDfsForest(num, result, ady) ;
            }
        }
        return result;
    }



}
