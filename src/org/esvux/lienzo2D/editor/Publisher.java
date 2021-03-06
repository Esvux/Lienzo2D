package org.esvux.lienzo2D.editor;

import java.util.Date;
import javax.swing.JOptionPane;
import org.esvux.lienzo2D.interprete.LostCanvas;
import org.json.simple.JSONObject;
//import org.sarina20lives.interprete.LostCanvas;

/**
 *
 * @author esvux
 */
public class Publisher extends javax.swing.JFrame {

    private static Publisher publicador;

    public static Publisher getInstance() {
        if (publicador == null) {
            publicador = new Publisher(LostCanvas.getInstance());
        }
        return publicador;
    }

    public static void showNewInstance() {
        if (publicador != null) {
            publicador.dispose();
        }
        publicador = new Publisher(LostCanvas.resetInstance());
        publicador.setVisible(true);
    }

    private Publisher(LostCanvas canvas) {
        super();
        initComponents();
        this.jScrollPane_Canvas.setViewportView(canvas);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane_Canvas = new javax.swing.JScrollPane();
        jButton_Publicar = new javax.swing.JButton();
        jTextField_Titulo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_Descripcion = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Publicador de lienzos - Lienzos 2D");
        setModalExclusionType(null);

        jButton_Publicar.setText("Publicar lienzo");
        jButton_Publicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PublicarActionPerformed(evt);
            }
        });

        jLabel1.setText("Título:");

        jLabel2.setText("Descripción:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane_Canvas)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_Descripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_Publicar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Canvas, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton_Publicar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_Descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_PublicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PublicarActionPerformed
        if (parametrosValidos()) {
            String json = crearJSON();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String respuesta = nuevoLienzo(json);
                    informar(respuesta);
                }
            }).start();
        }
    }//GEN-LAST:event_jButton_PublicarActionPerformed

    public void setCanvas(LostCanvas canvas) {
        this.jScrollPane_Canvas.setViewportView(canvas);
        this.jScrollPane_Canvas.repaint();
    }

    public LostCanvas getCanvas() {
        return (LostCanvas) this.jScrollPane_Canvas.getViewport().getView();
    }

    private boolean parametrosValidos() {
        if (jTextField_Titulo.getText().isEmpty()) {
            informar("No se puede publicar un lienzo sin título.");
            return false;
        }
        if (jTextField_Descripcion.getText().isEmpty()) {
            informar("No se puede publicar un lienzo sin descripción.");
            return false;
        }
        return true;
    }

    private String crearJSON() {
        JSONObject imagen = new JSONObject();
        imagen.put("titulo", jTextField_Titulo.getText());
        imagen.put("descripcion", jTextField_Descripcion.getText());
        imagen.put("fecha", new Date().getTime());
        imagen.put("base64", getCanvas().generarBase64());
        return imagen.toJSONString();
    }

    private void informar(String respuesta) {
        JOptionPane.showMessageDialog(this, respuesta, "Publicador de lienzos", JOptionPane.PLAIN_MESSAGE);
    }

    private static String nuevoLienzo(java.lang.String detalleLienzoJson) {
        org.esvux.lienzo2D.webservice.GestorLienzos_Service service = new org.esvux.lienzo2D.webservice.GestorLienzos_Service();
        org.esvux.lienzo2D.webservice.GestorLienzos port = service.getGestorLienzosPort();
        return port.nuevoLienzo(detalleLienzoJson);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Publicar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane_Canvas;
    private javax.swing.JTextField jTextField_Descripcion;
    private javax.swing.JTextField jTextField_Titulo;
    // End of variables declaration//GEN-END:variables

}
