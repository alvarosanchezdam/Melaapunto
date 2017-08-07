package duran.sanchez.alvaro.melaapunto.model;

/**
 * Created by Álvaro on 07/08/2017.
 */

public class Pelicula {
    private int id;
    private String titulo;
    private int nota;
    private String descripción;
    private String recomendadaPor;
    private boolean vista;

    public Pelicula() {
    }

    public Pelicula(int id, String titulo, int nota, String descripción, String recomendadaPor, boolean vista) {
        this.id = id;
        this.titulo = titulo;
        this.nota = nota;
        this.descripción = descripción;
        this.recomendadaPor = recomendadaPor;
        this.vista = vista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getRecomendadaPor() {
        return recomendadaPor;
    }

    public void setRecomendadaPor(String recomendadaPor) {
        this.recomendadaPor = recomendadaPor;
    }

    public boolean isVista() {
        return vista;
    }

    public void setVista(boolean vista) {
        this.vista = vista;
    }
}
