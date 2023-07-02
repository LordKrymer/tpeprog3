package segundaParte;

import primeraParte.Arco;
import primeraParte.GrafoNoDirigido;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public class ProblemaTunnel {

    private class IntegerPair{
        private Integer first;
        private Integer second;

        public IntegerPair(Integer first, Integer second){
            this.first = first;
            this.second= second;
        }
        public Integer getFirst(){return this.first;}
        public Integer getSecond(){return this.second;}

        public String toString(){
            return ("S " + this.first.toString() + "- D " + this.second.toString());
        }

        public boolean equals(Object o){
            return ( ((IntegerPair) o).getFirst().equals(this.first) && ((IntegerPair) o).getSecond().equals(this.second) ||  ((IntegerPair) o).getFirst().equals(this.second) && ((IntegerPair) o).getSecond().equals(this.first)) ;
        }
    }

    protected GrafoNoDirigido<Integer> g;
    protected CSVReader reader;
    protected ArrayList<IntegerPair> resultado = new ArrayList<>();

    protected Integer iteraciones = 0;


    protected Integer minimumPathLength = Integer.MAX_VALUE;

    ProblemaTunnel(String path){
        this.g = new GrafoNoDirigido<Integer>();
        this.reader = new CSVReader(path);
        reader.read(g);
    }

    public ArrayList<Arco<?>> findMinimumPath(){
        ArrayList<Integer> estacionesVisitadas = new ArrayList<>();
        ArrayList<IntegerPair> result = new ArrayList<>();
        ArrayList<Arco<?>> arcosUtilizados = new ArrayList<>();

        for (Iterator<Integer> it = g.obtenerVertices(); it.hasNext();){
            Integer estacionActual = it.next();

            findMinimumPath(estacionActual,0,estacionesVisitadas,new ArrayList<>(), new ArrayList<>());
            estacionesVisitadas.remove(estacionActual);
        }

        this.resultado.forEach(x -> {
            if (!result.contains(x))
                result.add(x);

        });

        result.forEach(x->arcosUtilizados.add(g.obtenerArco(x.first,x.second)));
        return arcosUtilizados;
    }

    private void findMinimumPath(Integer estacionActual,Integer currentLength,ArrayList<Integer> estacionesVisitadas, ArrayList<Arco<?>> currentPath, ArrayList<IntegerPair> tuneles){

        estacionesVisitadas.add(estacionActual);

        if (currentLength >= minimumPathLength) return;

        if (estacionesVisitadas.stream().distinct().toList().size() == g.cantidadVertices()){
            if (currentLength < minimumPathLength){
                resultado.clear();
                resultado.addAll(tuneles);
                minimumPathLength = currentLength;
                return;
            }
        }
        iteraciones++;
        for(Iterator<Arco<Integer>> it = g.obtenerArcos(estacionActual); it.hasNext();){

            Arco<?> currentEdge = it.next();

            if (!currentPath.contains(currentEdge)){

                IntegerPair tunnel = new IntegerPair(currentEdge.getVerticeOrigen(),currentEdge.getVerticeDestino());

                if (!tuneles.contains(tunnel))
                    currentLength += (Integer) currentEdge.getEtiqueta();

                tuneles.add(tunnel);
                currentPath.add(currentEdge);

                findMinimumPath(currentEdge.getVerticeDestino(),currentLength,estacionesVisitadas,currentPath,tuneles);
                estacionesVisitadas.remove((Integer) currentEdge.getVerticeDestino());

                tuneles.remove(tunnel);
                currentPath.remove(currentEdge);

                if (!tuneles.contains(tunnel))
                    currentLength -= (Integer) currentEdge.getEtiqueta();

            }
        }

    }

    public Integer getIteraciones(){
        return this.iteraciones;
    }

    public Integer getMinimumPathLength() {
        return this.minimumPathLength;
    }


    public ArrayList<Arco<?>> greedySolution () {
        ArrayList<Arco<?>> arcos = g.getAllEdges();
        HashSet<Integer> nodos = new HashSet<>();
        ArrayList<Arco<?>> solucion = new ArrayList<>();
        Arco<?> primerNodo = arcos.get(0);
        nodos.add(primerNodo.getVerticeDestino());
        nodos.add(primerNodo.getVerticeOrigen());
        solucion.add(primerNodo);

/*
        Este loop permite encontrar mejores caminos a costo de tiempo de computacion. Podria ser reemplazado por el que se encuentra debajo.
* */
        for(int i =0; i< g.cantidadArcos(); i++){
            for (Arco<?> x : arcos){
                iteraciones++;
                if( nodos.contains(x.getVerticeOrigen()) && !nodos.contains(x.getVerticeDestino())){
                    solucion.add(x);
                    nodos.add(x.getVerticeDestino());
                    break;
                }
            }
        }


/*
       Al reemplazar el loop ubicado encima de este por el loop comentado, se pueden reducir significativamente la cantidad de iteraciones pero reduciendo la eficacia de los caminos encontrados.
*/

/*       arcos.forEach(x -> {
            iteraciones++;
            if( nodos.contains(x.getVerticeOrigen()) && !nodos.contains(x.getVerticeDestino())){
                solucion.add(x);
                nodos.add(x.getVerticeDestino());

            }
        });*/

        AtomicReference<Integer> longitud = new AtomicReference<>(0);
        solucion.forEach(x -> {
            longitud.updateAndGet(v -> v + ((Integer) x.getEtiqueta()));});
        if (nodos.size() == this.g.cantidadVertices()){
            this.minimumPathLength = longitud.getPlain();
            return solucion;
        }
        else {
            this.minimumPathLength = -1;
            return null;
        }

    }
}
