package primeraParte.servicios;

import primeraParte.Arco;
import primeraParte.Grafo;

import java.util.*;

public class ServicioCaminos {

    private Grafo<?> grafo;
    private int origen;
    private int destino;
    private int lim;
    protected List<List<Integer>> resultados;

    // Servicio caminos
    public ServicioCaminos(Grafo<?> grafo, int origen, int destino, int lim) {
        this.grafo = grafo;
        this.origen = origen;
        this.destino = destino;
        this.lim = lim;
        this.resultados = new ArrayList<>();
    }

    public List<List<Integer>> caminos() {
        HashSet<String> visitedEdges = new HashSet<>();
        this.resultados.clear();
        if (grafo.contieneVertice(this.origen)) {
            ArrayList<Integer> path = new ArrayList();
            path.add(origen);
            return this.getPaths(this.origen, this.lim, visitedEdges, path);
        }
        return new ArrayList<>();
    }

    protected List<List<Integer>> getPaths(int origin, Integer limit, HashSet<String> visitedEdges, ArrayList<Integer> actualPath) {
        if (limit < 0 ) return null;
        if (this.destino == origin && limit >= 0) {
            this.resultados.add(new ArrayList<>(actualPath));
        }
        Iterator<? extends Arco<?>> ady = grafo.obtenerArcos(origin);
        while (ady.hasNext()) {
            Arco<Integer> value = (Arco<Integer>) ady.next();
            if (!visitedEdges.contains(serializeEdge(value))) {
                actualPath.add(value.getVerticeDestino());
                visitedEdges.add(serializeEdge(value));
                this.getPaths(value.getVerticeDestino(),limit-1, visitedEdges, actualPath);
                actualPath.remove(actualPath.size()-1);
                visitedEdges.remove(serializeEdge(value));
            }
        }
        return this.resultados;
    }

    private String serializeEdge (Arco edge) {
        return String.format("v1:%d-v2:%d", edge.getVerticeOrigen(), edge.getVerticeDestino());
    }
}
