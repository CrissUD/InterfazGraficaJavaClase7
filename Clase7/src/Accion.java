import javax.swing.ImageIcon;

public class Accion {

    private String titulo;
    private ImageIcon imagen;
    private String parrafo;

    public Accion() {

    }

    public String getParrafo() {
        return parrafo;
    }

    public void setParrafo(String párrafo) {
        this.parrafo = párrafo;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    
}