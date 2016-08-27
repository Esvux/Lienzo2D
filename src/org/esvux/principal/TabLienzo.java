package org.esvux.principal;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 *
 * @author esvux
 */
public class TabLienzo extends JScrollPane{
    private String path;
    private String name;
    private JTextArea text;
    
    public TabLienzo() {
        super();
        Border border = BorderFactory.createLineBorder(Color.white);
        this.setBorder(border);
        text = new JTextArea();
        text.setBorder(border);
        this.setViewportView(text);
        this.setViewportBorder(border);        
    }
    
}
