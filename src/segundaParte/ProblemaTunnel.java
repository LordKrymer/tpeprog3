package segundaParte;

import primeraParte.Arco;
import primeraParte.Grafo;
import primeraParte.GrafoNoDirigido;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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

    protected Grafo<Integer> g;
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

            estacionesVisitadas.add(estacionActual);
            findMinimumPath(estacionActual,0,estacionesVisitadas,new ArrayList<>(), new ArrayList<>());
            estacionesVisitadas.remove(estacionActual);
        }


        this.resultado.forEach(x -> {
            if (!result.contains(x)) {
                result.add(x);
            }
        });

        result.forEach(x->arcosUtilizados.add(g.obtenerArco(x.first,x.second)));
        return arcosUtilizados;
    }

    private void findMinimumPath(Integer estacionActual,Integer currentLength,ArrayList<Integer> estacionesVisitadas, ArrayList<Arco<?>> currentPath, ArrayList<IntegerPair> tuneles){
        iteraciones++;

        if (!estacionesVisitadas.contains(estacionActual))
            estacionesVisitadas.add(estacionActual);

        if (currentLength > minimumPathLength) return;

        if (estacionesVisitadas.size() == g.cantidadVertices()){
            if (currentLength < minimumPathLength){
                resultado.clear();
                resultado.addAll(tuneles);
                minimumPathLength = currentLength;
                return;
            }
        }
        for(Iterator<Arco<Integer>> it = g.obtenerArcos(estacionActual); it.hasNext();){

            Arco<?> currentEdge = it.next();

            if (!currentPath.contains(currentEdge)){

                IntegerPair tunnel = new IntegerPair(currentEdge.getVerticeDestino(),currentEdge.getVerticeOrigen());

                if (!tuneles.contains(tunnel))
                    currentLength += (Integer) currentEdge.getEtiqueta();

                tuneles.add(tunnel);
                currentPath.add(currentEdge);

                findMinimumPath(currentEdge.getVerticeDestino(),currentLength,estacionesVisitadas,currentPath,tuneles);
                if (estacionVisitada(estacionActual,tuneles))
                    estacionesVisitadas.remove((Integer)currentEdge.getVerticeDestino());

                tuneles.remove(tunnel);
                currentPath.remove(currentEdge);

                if (!tuneles.contains(tunnel))
                    currentLength -= (Integer) currentEdge.getEtiqueta();

            }
        }
    }

    private Boolean estacionVisitada(Integer estacion, ArrayList<IntegerPair> tuneles){
        for (IntegerPair p: tuneles){
            if (p.first == estacion || p.second == estacion) return true;
        }
        return false;
    }

    public Integer getIteraciones(){
        return this.iteraciones;
    }

    public Integer getMinimumPathLength() {
        return this.minimumPathLength;
    }


   /* public ArrayList<Arco<?>> greedySolution () {

    }
*/
}
