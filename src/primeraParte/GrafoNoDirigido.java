package primeraParte;

public class GrafoNoDirigido<T> extends GrafoDirigido<T>{
    @Override
    public void agregarArco(int verticeId1, int verticeId2, T etiqueta){
        super.agregarArco(verticeId1,verticeId2,etiqueta);
        super.agregarArco(verticeId2,verticeId1,etiqueta);
    }
    @Override
    public void borrarArco(int verticeId1, int verticeId2) {
        vertices.get(verticeId1).remove(obtenerArco(verticeId1, verticeId2));
        vertices.get(verticeId2).remove(obtenerArco(verticeId2, verticeId1));
    }
}
