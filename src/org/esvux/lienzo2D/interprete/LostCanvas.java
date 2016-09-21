package org.esvux.lienzo2D.interprete;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

/**
 *
* @autor esvux
 */
public class LostCanvas extends Canvas {

    private static LostCanvas canvas;

    public static LostCanvas getInstance() {
        if (canvas == null) {
            canvas = new LostCanvas();
        }
        return canvas;
    }

    public static LostCanvas resetInstance() {
        canvas = new LostCanvas();
        return canvas;
    }

    private final List<Figura> elementos;

    public LostCanvas() {
        this.setSize(800, 500);
        this.elementos = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        //Dibujando el fondo...
        g2D.setColor(Color.white);
        g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        //Dibujando todos los elementos...
        synchronized (elementos) {
            for (Figura elemento : elementos) {
                g2D.setColor(elemento.color);
                if (elemento.tipo == Figura.CIRCULO) {
                    g2D.fillOval(elemento.x, elemento.y, elemento.radio, elemento.radio);
                } else if (elemento.tipo == Figura.OVALO) {
                    g2D.fillOval(elemento.x, elemento.y, elemento.ancho, elemento.alto);
                } else if (elemento.tipo == Figura.RECTANGULO) {
                    g2D.fillRect(elemento.x, elemento.y, elemento.ancho, elemento.alto);
                } else if (elemento.tipo == Figura.TEXTO) {
                    g2D.drawString(elemento.texto, elemento.x, elemento.y);
                }
            }
        }
    }

    public String generarBase64() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D nuevo = buffer.createGraphics();
            nuevo.setColor(Color.white);
            nuevo.fillRect(0, 0, this.getWidth(), this.getHeight());
            for (Figura elemento : elementos) {
                nuevo.setColor(elemento.color);
                if (elemento.tipo == Figura.CIRCULO) {
                    nuevo.fillOval(elemento.x, elemento.y, elemento.radio*2, elemento.radio*2);
                } else if (elemento.tipo == Figura.OVALO) {
                    nuevo.fillOval(elemento.x, elemento.y, elemento.ancho, elemento.alto);
                } else if (elemento.tipo == Figura.RECTANGULO) {
                    nuevo.fillRect(elemento.x, elemento.y, elemento.ancho, elemento.alto);
                } else if (elemento.tipo == Figura.TEXTO) {
                    nuevo.drawString(elemento.texto, elemento.x, elemento.y);
                }
            }
            ImageIO.write(buffer, "png", baos);
            baos.flush();
            String varBytes = Base64.encodeBase64String(baos.toByteArray());
            baos.close();
            return varBytes;
        } catch (Exception ex) {
            System.err.println("Imposible generar el texto BASE64 de la imagen, " + ex.getMessage());
            return null;
        }
    }

    public void addRectangulo(int x, int y, int ancho, int alto, Color color) {
        Figura nuevo = new Figura(Figura.RECTANGULO, x, y, alto, ancho, 0, "", color);
        elementos.add(nuevo);
        this.repaint();
    }

    public void addOvalo(int x, int y, int ancho, int alto, Color color) {
        Figura nuevo = new Figura(Figura.OVALO, x, y, alto, ancho, 0, "", color);
        elementos.add(nuevo);
        this.repaint();
    }

    public void addCirculo(int x, int y, int diametro, Color color) {
        Figura nuevo = new Figura(Figura.CIRCULO, x, y, 0, 0, diametro, "", color);
        elementos.add(nuevo);
        this.repaint();
    }

    public void addTexto(int x, int y, String texto, Color color) {
        Figura nuevo = new Figura(Figura.TEXTO, x, y, 0, 0, 0, texto, color);
        elementos.add(nuevo);
        this.repaint();
    }

}

class Figura {

    static final int RECTANGULO = 0;
    static final int OVALO = 1;
    static final int CIRCULO = 2;
    static final int TEXTO = 3;

    int tipo;
    int x, y;
    int alto, ancho, radio;
    String texto;
    Color color;

    public Figura(int tipo, int x, int y, int alto, int ancho, int radio, String texto, Color color) {
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.alto = alto;
        this.ancho = ancho;
        this.radio = radio;
        this.texto = texto;
        this.color = color;
    }

}
