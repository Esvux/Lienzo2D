package org.esvux.lienzo2D.editor;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import org.esvux.lienzo2D.AST.Lienzo;
import org.esvux.lienzo2D.compilador.ManejadorErrores;
import org.esvux.lienzo2D.compilador.TablaSimbolos;
import org.esvux.lienzo2D.interprete.Contexto;
import org.esvux.lienzo2D.interprete.Interprete;
import org.esvux.lienzo2D.interprete.LostCanvas;
import org.esvux.lienzo2D.interprete.sentencia.Sentencia;
import org.esvux.lienzo2D.parser.ParseException;
import org.esvux.lienzo2D.parser.ParserL2D;
import org.esvux.lienzo2D.utilidades.ManejadorArchivos;
import org.esvux.lienzo2D.utilidades.MyPainter;

/**
 *
 * @author esvux
 */
public class TabPage extends JScrollPane {

    private String path;
    private String file;
    private final JEditorPane text;
    private static int count = 1;
    private static Font font = new Font("Monospaced", Font.PLAIN, 12);

    public TabPage() {
        super();
        this.path = null;
        this.file = "Sin guardar-" + (count++) + ".lz*";
        Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        this.setBorder(border);
        this.text = new JEditorPane();
        this.text.setFont(font);
        this.text.setMargin(new Insets(0, 5, 2, 2));
        this.text.setEditorKit(new NumberedEditorKit());
        this.text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 114) {//F3
                    Sentencia.triggerRunPause();
                }
                if (e.getKeyCode() == 115) {//F4
                    Sentencia.setTiempoPausa(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        new MyPainter(this.text, Color.decode("#e0f2f1"));
        this.setViewportView(text);
        this.setViewportBorder(border);
    }

    /**
     * Abre un archivo en sí misma.
     *
     * @throws java.io.IOException Excepción lanzada por el manejador de
     * archivos.
     */
    public void abrir() throws IOException {
        String respuesta[] = ManejadorArchivos.abrirArchivo();
        if (respuesta != null) {
            this.file = respuesta[0];
            this.path = respuesta[1];
            this.setText(respuesta[2]);
        }
    }

    /**
     * Guarda el archivo que existe en sí misma.
     *
     * @throws java.io.IOException Excepción lanzada por el manejador de
     * archivos.
     */
    public void guardar() throws IOException {
        String nuevoPath = ManejadorArchivos.guardarArchivo(this.path, this.getText());
        if (nuevoPath != null) {
            this.path = nuevoPath;
            this.file = nuevoPath.substring(nuevoPath.lastIndexOf("/") + 1);
        }
    }

    /**
     * Guarda el archivo que existe en sí misma con otro nombre.
     *
     * @throws java.io.IOException Excepción lanzada por el manejador de
     * archivos.
     */
    public void guardarComo() throws IOException {
        String nuevoPath = ManejadorArchivos.guardarArchivoComo(this.getText());
        if (nuevoPath != null) {
            this.path = nuevoPath;
            this.file = nuevoPath.substring(nuevoPath.lastIndexOf("/") + 1);
        }
    }

    public void setPosicionCursor(int posFila, int posColumna) {
        String filas[] = this.text.getText().split("\n");
        int pos = 0;
        for (int i = 0; i < filas.length; i++) {
            pos += filas[i].length();
            if (i == posFila) {
                break;
            }
        }
        this.text.setCaretPosition(pos + posColumna);
    }

    private void informar(String respuesta) {
        JOptionPane.showMessageDialog(this, respuesta, "Publicador de lienzos", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void parserLZ() {
        Lienzo lienzo = null;
        Lienzo.resetCatalogoExtendes();
        ManejadorErrores.resetInstance();
        LostCanvas.resetInstance();
        try {
            ParserL2D parser = new ParserL2D(new java.io.StringReader(this.getText()));
            lienzo = parser.CLASE();
            if (lienzo == null) {
                ManejadorErrores.getInstance(this.getFile()).addErrorGeneral("Imposible generar el lienzo, error en el archivo");
                return;
            }
            System.out.println("Analisis concluido...");
        } catch (ParseException ex) {
            Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
        }
        TablaSimbolos ts = new TablaSimbolos();
        System.out.println("Tabla de Simbolos generada...");
        Contexto ctxInicial = new Contexto();
        ctxInicial.agregarAlContexto(lienzo, true);
        for (Lienzo extend : Lienzo.getCatalogoExtendes().values()) {
            ctxInicial.agregarAlContexto(extend, false);
        }

        System.out.println("Contexto inicial generado...");
        Interprete interprete = new Interprete(lienzo, ctxInicial);
        interprete.ejecutarMain();

        ts.generarReporte(lienzo);
        ManejadorErrores.getInstance().generarReporte(this.file);
        informar("Ha finalizado la ejecución del programa.\n" + ManejadorErrores.getInstance().getEstado());
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getText() {
        return this.text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
    }

}

class NumberedEditorKit extends StyledEditorKit {

    public ViewFactory getViewFactory() {
        return new NumberedViewFactory();
    }
}

class NumberedViewFactory implements ViewFactory {

    public View create(Element elem) {
        String kind = elem.getName();
        if (kind != null) {
            if (kind.equals(AbstractDocument.ContentElementName)) {
                return new LabelView(elem);
            } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
//              return new ParagraphView(elem);
                return new NumberedParagraphView(elem);
            } else if (kind.equals(AbstractDocument.SectionElementName)) {
                return new BoxView(elem, View.Y_AXIS);
            } else if (kind.equals(StyleConstants.ComponentElementName)) {
                return new ComponentView(elem);
            } else if (kind.equals(StyleConstants.IconElementName)) {
                return new IconView(elem);
            }
        }
        // default to text display
        return new LabelView(elem);
    }
}

class NumberedParagraphView extends ParagraphView {

    public static short NUMBERS_WIDTH = 25;

    public NumberedParagraphView(Element e) {
        super(e);
        short top = 0;
        short left = 0;
        short bottom = 0;
        short right = 0;
        this.setInsets(top, left, bottom, right);
    }

    protected void setInsets(short top, short left, short bottom,
            short right) {
        super.setInsets(top, (short) (left + NUMBERS_WIDTH),
                bottom, right);
    }

    public void paintChild(Graphics g, Rectangle r, int n) {
        super.paintChild(g, r, n);
        int previousLineCount = getPreviousLineCount();
        int numberX = r.x - getLeftInset();
        int numberY = r.y + r.height - 5;
        g.drawString(Integer.toString(previousLineCount + n + 1),
                numberX, numberY);
    }

    public int getPreviousLineCount() {
        int lineCount = 0;
        View parent = this.getParent();
        int count = parent.getViewCount();
        for (int i = 0; i < count; i++) {
            if (parent.getView(i) == this) {
                break;
            } else {
                lineCount += parent.getView(i).getViewCount();
            }
        }
        return lineCount;
    }
}
