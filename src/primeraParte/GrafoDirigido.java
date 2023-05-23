package primeraParte;

import primeraParte.Arco;
import primeraParte.Grafo;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GrafoDirigido<T> implements Grafo<T> {

        protected HashMap<Integer, LinkedList<Arco<T>>> vertices;

        protected HashMap<Integer, Boolean> visitados;

        public GrafoDirigido() {
            this.vertices = new HashMap<>();
            this.visitados = new HashMap<>();
        }


        @Override//agregarVertice(int verticeId): la complejidad temporal es O(1), ya que solo se agrega un nuevo elemento al mapa vertices.
        public void agregarVertice(int verticeId) {
            this.vertices.put(verticeId, new LinkedList<>());
        }

        @Override//borrarVertice(int verticeId): la complejidad temporal es O(N^2), porque en el peor de los casos donde el grafo es pesado y cada uno de los N vertices tiene N aristas
        public void borrarVertice(int verticeId) {
            for (Map.Entry<Integer, LinkedList<Arco<T>>> entry : vertices.entrySet()) {
                for (Arco<T> adyasente : entry.getValue())
                    if (existeArco(adyasente.getVerticeOrigen(), verticeId)) {
                        this.borrarArco(adyasente.getVerticeOrigen(), verticeId);
                    }
            }
            vertices.remove(verticeId);
        }

        @Override//agregarArco(int verticeId1, int verticeId2, T etiqueta): la complejidad temporal es O(1), ya que solo se agrega un nuevo elemento a la lista de adyacencia correspondiente en el mapa vertices.
        public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {
            vertices.get(verticeId1).add((new Arco<>(verticeId1, verticeId2, etiqueta)));
        }

        @Override//borrarArco(int verticeId1, int verticeId2): la complejidad temporal es O(N), donde N es el número de arcos en el grafo. Esto se debe a que se debe buscar el arco en la lista de adyacencia correspondiente en el mapa vertices.
        public void borrarArco(int verticeId1, int verticeId2) {
            vertices.get(verticeId1).remove(obtenerArco(verticeId1, verticeId2));
        }

        @Override//contieneVertice(int verticeId): la complejidad temporal es O(1), ya que se usa el método containsKey() en el mapa vertices.
        public boolean contieneVertice(int verticeId) {
            return this.vertices.containsKey(verticeId);
        }

        @Override//existeArco(int verticeId1, int verticeId2): la complejidad temporal es O(E), donde E es el número de arcos en el grafo. Esto se debe a que se debe buscar el arco en la lista de adyacencia correspondiente en el mapa vertices.
        public boolean existeArco(int verticeId1, int verticeId2) {
            return obtenerArco(verticeId1, verticeId2) != null;
        }

        @Override//obtenerArco(int verticeId1, int verticeId2): la complejidad temporal es O(E), donde E es el número de arcos en la lista de adyacencia correspondiente en el mapa vertices. Esto se debe a que se debe buscar el arco en la lista de adyacencia correspondiente en el mapa vertices.
        public Arco<T> obtenerArco(int verticeId1, int verticeId2) {
            LinkedList<Arco<T>> arcosDeV1 = vertices.get(verticeId1);
            if (arcosDeV1 != null) {
                for (Arco<T> i : arcosDeV1) {
                    if (i.getVerticeDestino() == verticeId2) {
                        return i;
                    }
                }
            }
            return null;
        }

        @Override//cantidadVertices(): la complejidad temporal es O(1), ya que se usa el método size() en el mapa vertices.
        public int cantidadVertices() {
            return vertices.size();
        }


        @Override//cantidadArcos(): la complejidad temporal es O(E), donde E es el número de arcos en el grafo. Esto se debe a que se debe contar cada arco en cada lista de adyacencia en el mapa vertices.
        public int cantidadArcos() {
            int cantidad = 0;
            for (Map.Entry<Integer, LinkedList<Arco<T>>> entry : vertices.entrySet()) {
                cantidad += entry.getValue().size();
            }
            return cantidad;
        }

        @Override//obtenerVertices(): la complejidad temporal es O(V), donde V es el número de vértices en el grafo. Esto se debe a que se debe iterar por cada entrada en el mapa vertices y devolver la clave correspondiente.
        public Iterator<Integer> obtenerVertices() {
            return vertices.keySet().iterator();
        }

        @Override//obtenerAdyacentes(int verticeId): la complejidad temporal es O(E), donde E es el número de arcos en la lista de adyacencia correspondiente en el mapa vertices. Esto se debe a que se debe iterar por cada arco en la lista de adyacencia y devolver el vértice destino.
        public Iterator<Integer> obtenerAdyacentes(int verticeId) {
            List<Arco<T>> arcos = vertices.get(verticeId);
            List<Integer> adyacentes = new ArrayList<>();
            for (Arco<T> arco : arcos) {
                adyacentes.add(arco.getVerticeDestino());
            }
            return adyacentes.iterator();
        }

        @Override//obtenerArcos(): la complejidad temporal es O(E), donde E es el número de arcos en el grafo. Esto se debe a que se debe iterar por cada entrada en el mapa vertices y por cada arco en cada lista de adyacencia y devolver cada arco.
        public Iterator<Arco<T>> obtenerArcos() {
            LinkedList<Arco<T>> arcosResult = new LinkedList<>();
            for (Map.Entry<Integer, LinkedList<Arco<T>>> entry : vertices.entrySet()) {
                arcosResult.addAll(entry.getValue());
            }
            return arcosResult.iterator();
        }

        @Override//obtenerArcos(int verticeId): la complejidad temporal es O(E), donde E es el número de arcos en el grafo
        public Iterator<Arco<T>> obtenerArcos(int verticeId) {
            LinkedList<Arco<T>> arcosVertice = new LinkedList<>(vertices.get(verticeId));
            return arcosVertice.iterator();
        }

        public Integer obtenerVerticeRandom() {
            Integer[] keys = vertices.keySet().stream().mapToInt(Integer::intValue).boxed().toArray(Integer[]::new);
            return keys[(int) (Math.random() * keys.length)];
        }




    private List<Integer> getBfsForest( Integer startingNode) {
        List<Integer> resultado = new ArrayList<>();
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(startingNode);
        this.visitados.put(startingNode, true); // marcar el vértice inicial como visitado
        while (!queue.isEmpty()) {
            Integer actual = queue.poll();
            resultado.add(actual);
            Iterator<Integer> adyacente = this.obtenerAdyacentes(actual);
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



    public List<Integer> bfsForest(Integer vertice){
        this.visitados.clear();
        if (this.contieneVertice(vertice))
            return getBfsForest(vertice);
        else
            return bfsForest(12);
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



    @Override
    public List<Integer> dfsForest() {
        this.visitados.clear();
        List<Integer> result = new ArrayList<>();
        for (Integer entry : vertices.keySet()) {
            System.out.println(entry);
            Iterator<Integer> ady = this.obtenerAdyacentes(entry);
            getDfsForest(entry, result, ady);
        }
        return result;
    }



}


