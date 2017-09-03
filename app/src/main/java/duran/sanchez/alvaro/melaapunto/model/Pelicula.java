package duran.sanchez.alvaro.melaapunto.model;

import android.net.Uri;

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
    private int tag;
    private String imagePath;
    public Pelicula() {
    }

    public Pelicula(int id, String titulo, int nota, String descripción, String recomendadaPor, boolean vista, int tag, String imagePath) {
        this.id = id;
        this.titulo = titulo;
        this.nota = nota;
        this.descripción = descripción;
        this.recomendadaPor = recomendadaPor;
        this.vista = vista;
        this.tag = tag;
        this.imagePath = imagePath;
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

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
