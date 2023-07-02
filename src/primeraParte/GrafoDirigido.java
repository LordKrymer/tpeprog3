package primeraParte;

import java.util.*;

public class GrafoDirigido<T> implements Grafo<T> {
        protected Integer cantidadArcos;
        protected HashMap<Integer, LinkedList<Arco<T>>> vertices;

        public GrafoDirigido() {
            this.vertices = new HashMap<>();
            this.cantidadArcos = 0;
        }


        @Override//agregarVertice(int verticeId): la complejidad temporal es O(1), ya que solo se agrega un nuevo elemento al mapa vertices.
        public void agregarVertice(int verticeId) {
            if (!vertices.containsKey(verticeId))
                this.vertices.put(verticeId, new LinkedList<>());
        }

        @Override//borrarVertice(int verticeId): la complejidad temporal es O(N^2), porque en el peor de los casos donde el grafo es pesado y cada uno de los N vertices tiene N aristas
        public void borrarVertice(int verticeId) {
            for (Map.Entry<Integer, LinkedList<Arco<T>>> entry : vertices.entrySet()) {
                for (Arco<T> adyacente : entry.getValue())
                        this.borrarArco(adyacente.getVerticeOrigen(), verticeId);
            }
            cantidadArcos -= vertices.get(verticeId).size();
            vertices.remove(verticeId);
        }

        @Override//agregarArco(int verticeId1, int verticeId2, T etiqueta): la complejidad temporal es O(1), ya que solo se agrega un nuevo elemento a la lista de adyacencia correspondiente en el mapa vertices.
        public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {

            if (vertices.containsKey(verticeId1) && vertices.containsKey(verticeId2) && !existeArco(verticeId1,verticeId2)) {
                vertices.get(verticeId1).add((new Arco<>(verticeId1, verticeId2, etiqueta)));
                this.cantidadArcos++;
            }
        }

        @Override//borrarArco(int verticeId1, int verticeId2): la complejidad temporal es O(N), donde N es el número de arcos en el grafo. Esto se debe a que se debe buscar el arco en la lista de adyacencia correspondiente en el mapa vertices.
        public void borrarArco(int verticeId1, int verticeId2) {
            vertices.get(verticeId1).removeIf(x -> x.getVerticeDestino() == verticeId2);
            cantidadArcos--;
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
            return this.cantidadArcos;
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

        @Override//obtenerArcos(int verticeId): la complejidad temporal es O(1)
        public Iterator<Arco<T>> obtenerArcos(int verticeId) {
            return vertices.get(verticeId).iterator();
        }

        public Integer obtenerVerticeRandom() {
            Integer[] keys = vertices.keySet().stream().mapToInt(Integer::intValue).boxed().toArray(Integer[]::new);
            return keys[(int) (Math.random() * keys.length)];
        }

        public ArrayList<Arco<?>> getAllEdges(){
            ArrayList<Arco<?>> resultado = new ArrayList<>();
            vertices.forEach( (x,y) -> {
                    resultado.addAll(y);
            });
            return new ArrayList<>(resultado.stream().sorted().toList());
        }
}


