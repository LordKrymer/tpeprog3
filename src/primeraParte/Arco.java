package primeraParte;

public class Arco<T> {

    private Integer verticeOrigen;
    private Integer verticeDestino;
    private T etiqueta;

    public Arco(int verticeOrigen, int verticeDestino, T etiqueta) {
        this.verticeOrigen = verticeOrigen;
        this.verticeDestino = verticeDestino;
        this.etiqueta = etiqueta;
    }

    public int getVerticeOrigen() {
        return verticeOrigen;
    }

    public int getVerticeDestino() {
        return verticeDestino;
    }

    public T getEtiqueta() {
        return etiqueta;
    }

    public String toString(){
        return ("S " + this.verticeOrigen.toString() + "- D " + this.verticeDestino.toString() + " weigth: "+ this.etiqueta);
    }

}