package app.client.components.tarjeta;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;
import app.services.ObjGraficosService;
import app.services.RecursosService;

public class TarjetaTemplate extends JPanel{

    private static final long serialVersionUID = 8871354807207537138L;

    // Declaración Servicios y dependencias
    private TarjetaComponent tarjetaComponent;
    private ObjGraficosService sObjGraficos;
    private RecursosService sRecursos;

    // Declaración Objetos Gráficos
    private JLabel lTitulo, lImagen, lParrafo;

    // Declaración Objetos Decoradores
    private ImageIcon iDimAux;

    public TarjetaTemplate(
        TarjetaComponent tarjetaComponent, String titulo, ImageIcon iImagen, String parrafo
    ){

        this.tarjetaComponent = tarjetaComponent;
        this.tarjetaComponent.getClass();
        sObjGraficos= ObjGraficosService.getService();
        sRecursos = RecursosService.getService();

        iDimAux = new ImageIcon(
            iImagen.getImage().getScaledInstance(246, 110, Image.SCALE_AREA_AVERAGING)
        );
        lImagen= sObjGraficos.construirJLabel(null, 5, 5, 246, 110, iDimAux, null, null, null);
        this.add(lImagen);

        this.lTitulo = sObjGraficos.construirJLabel(
            titulo, -15, 120, 180, 30, null, sRecursos.getColorAzul(), null, sRecursos.getFontTitulo()
        );
        this.add(lTitulo);

        lParrafo= sObjGraficos.construirJLabel(
            parrafo, 20, 120, 206, 120, null, sRecursos.getColorGrisOscuro(), null, sRecursos.getFontPequeña()
        );
        this.add(lParrafo);

        this.setSize(256, 230);
        this.setBackground(Color.white);
        this.setLayout(null);
        this.setVisible(true);
    }
}