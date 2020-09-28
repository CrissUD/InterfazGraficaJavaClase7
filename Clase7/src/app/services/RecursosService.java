package app.services;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import java.io.File;
import java.io.IOException;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class RecursosService {

  private Color colorPrincipal, colorSecundario, colorGrisOscuro, colorGrisClaro;
  private Font fontTPrincipal, fontTitulo, fontSubtitulo;
  private Font fontMediana, fontLigera;
  private Cursor cMano;
  private Border bInferiorAzul, bGris;
  private ImageIcon iCerrar, iMinimizar;

  static private RecursosService servicio;

  private RecursosService() {
    generarFuentes();

    this.crearColores();
    this.crearFuentes();
    this.crearCursores();
    this.crearBordes();
    this.crearImagenes();
  }

  private void crearColores() {
    colorPrincipal = new Color(60, 78, 120);
    colorSecundario = new Color(151, 0, 158);
    colorGrisOscuro = new Color(80, 80, 80);
    colorGrisClaro = new Color(249, 246, 249);
  }

  private void crearFuentes() {
    fontTPrincipal = new Font("Rockwell Extra Bold", Font.PLAIN, 20);
    fontTitulo = new Font("Calibri (Cuerpo)", Font.BOLD, 17);
    fontSubtitulo = new Font("Forte", Font.PLAIN, 13);
    fontMediana = new Font("LuzSans-Book", Font.PLAIN, 15);
    fontLigera = new Font("LuzSans-Book", Font.PLAIN, 12);
  }

  private void crearCursores() { cMano = new Cursor(Cursor.HAND_CURSOR); }

  private void crearBordes() {
    bInferiorAzul = BorderFactory.createMatteBorder(0, 0, 2, 0, colorPrincipal);
    bGris = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true);
  }

  private void crearImagenes() {
    iCerrar = new ImageIcon("Clase7/resources/images/cerrar.png");
    iMinimizar = new ImageIcon("Clase7/resources/images/minimizar.png");
  }

  private void generarFuentes() {
    try {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont( Font.createFont(
          Font.TRUETYPE_FONT,
          new File("Clase7/resources/fonts/LUZRO.ttf")
      ));
    } catch (IOException | FontFormatException e) {
      System.out.println(e);
    }
  }

  public Color getColorPrincipal() { return colorPrincipal; }
  
  public Color getColorGrisOscuro() { return colorGrisOscuro; }
  
  public Color getColorSecundario(){ return colorSecundario; }
  
  public Color getColorGrisClaro(){ return colorGrisClaro; }

  public Font getFontTPrincipal() { return fontTPrincipal; }

  public Font getFontTitulo() { return fontTitulo; }

  public Font getFontSubtitulo() { return fontSubtitulo; }

  public Font getFontMediana() { return fontMediana; }

  public Font getFontLigera(){ return fontLigera; }

  public Cursor getCMano() { return cMano; }

  public Border getBInferiorAzul() { return bInferiorAzul; }

  public Border getBGris(){ return bGris; }

  public ImageIcon getICerrar() { return iCerrar; }

  public ImageIcon getIMinimizar() { return iMinimizar; }

  public static RecursosService getService() {
    if (servicio == null) servicio = new RecursosService();
    return servicio;
  }
}