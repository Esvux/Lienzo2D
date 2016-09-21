package org.esvux.lienzo2D.editor;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.esvux.lienzo2D.AST.Lienzo;
import org.esvux.lienzo2D.compilador.ManejadorErrores;
import org.esvux.lienzo2D.compilador.TablaSimbolos;
import org.esvux.lienzo2D.interprete.sentencia.Sentencia;
import org.esvux.lienzo2D.utilidades.ManejadorArchivos;

/**
 *
 * @author esvux
 */
public class Editor extends javax.swing.JFrame {

    //<editor-fold defaultstate="collapsed" desc=" Código generado para inicializar el formulario ">
    private static Editor editor;

    public static Editor getInstance() {
        if (editor == null) {
            editor = new Editor();
        }
        return editor;
    }
    
    String ultimoLienzoAnalizado = null;
    
    private Editor() {
        initComponents();
        ManejadorArchivos.setup("Archivos de lienzos", "LZ", "lz");
        TabPage tab = new TabPage();
        jTabbedPane_Archivos.add(tab.getFile(), tab);
    }

    public void setPosicion(String archivo, int linea, int columna) {
        int max = jTabbedPane_Archivos.getTabCount();
        for (int i = 0; i < max; i++) {
            String pestania = jTabbedPane_Archivos.getTitleAt(i);
            if (pestania.compareToIgnoreCase(archivo) == 0) {
                jTabbedPane_Archivos.setSelectedIndex(i);
                TabPage tab = (TabPage) jTabbedPane_Archivos.getSelectedComponent();
                tab.setPosicionCursor(linea, columna);
                return;
            }
        }
    }

    private void informar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Editor de lienzos", JOptionPane.PLAIN_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_BotonesCompilacion = new javax.swing.JPanel();
        jButton_Compilar = new javax.swing.JButton();
        jButton_TablaSimbolos = new javax.swing.JButton();
        jButton_Errores = new javax.swing.JButton();
        jButton_Debugger = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        jScrollPane_Consola = new javax.swing.JScrollPane();
        jTextArea_Consola = new javax.swing.JTextArea();
        jTabbedPane_Archivos = new javax.swing.JTabbedPane();
        jSeparator_Principal = new javax.swing.JSeparator();
        jPanel_BotonesArchivos = new javax.swing.JPanel();
        jButton_Nuevo = new javax.swing.JButton();
        jButton_Abrir = new javax.swing.JButton();
        jButton_Guardar = new javax.swing.JButton();
        jButton_GuardarComo = new javax.swing.JButton();
        jButton_Cerrar = new javax.swing.JButton();
        jSeparator_Archivo = new javax.swing.JSeparator();
        jButton_Dibujar = new javax.swing.JButton();
        jButton_Configurar = new javax.swing.JButton();
        jSeparator_Configs = new javax.swing.JSeparator();
        jButton_AcercaDe = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Editor - Lienzos 2D");
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel_BotonesCompilacion.setBackground(new java.awt.Color(255, 255, 255));

        jButton_Compilar.setBackground(java.awt.Color.white);
        jButton_Compilar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_play.png"))); // NOI18N
        jButton_Compilar.setToolTipText("Compilar archivo");
        jButton_Compilar.setBorderPainted(false);
        jButton_Compilar.setContentAreaFilled(false);
        jButton_Compilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CompilarActionPerformed(evt);
            }
        });

        jButton_TablaSimbolos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_tabla.png"))); // NOI18N
        jButton_TablaSimbolos.setToolTipText("Tabla de simbolos");
        jButton_TablaSimbolos.setBorderPainted(false);
        jButton_TablaSimbolos.setContentAreaFilled(false);
        jButton_TablaSimbolos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_TablaSimbolosActionPerformed(evt);
            }
        });

        jButton_Errores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_logerror.png"))); // NOI18N
        jButton_Errores.setToolTipText("Reporte de errores");
        jButton_Errores.setBorderPainted(false);
        jButton_Errores.setContentAreaFilled(false);
        jButton_Errores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ErroresActionPerformed(evt);
            }
        });

        jButton_Debugger.setBackground(java.awt.Color.white);
        jButton_Debugger.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_debug.png"))); // NOI18N
        jButton_Debugger.setToolTipText("Debuggear archivo");
        jButton_Debugger.setBorderPainted(false);
        jButton_Debugger.setContentAreaFilled(false);
        jButton_Debugger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DebuggerActionPerformed(evt);
            }
        });

        jSlider1.setMaximum(10000);
        jSlider1.setMinimum(10);
        jSlider1.setOrientation(javax.swing.JSlider.VERTICAL);
        jSlider1.setValue(1000);
        jSlider1.setBorder(null);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel_BotonesCompilacionLayout = new javax.swing.GroupLayout(jPanel_BotonesCompilacion);
        jPanel_BotonesCompilacion.setLayout(jPanel_BotonesCompilacionLayout);
        jPanel_BotonesCompilacionLayout.setHorizontalGroup(
            jPanel_BotonesCompilacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_BotonesCompilacionLayout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addGroup(jPanel_BotonesCompilacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_Compilar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_TablaSimbolos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Errores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_BotonesCompilacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jButton_Debugger, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel_BotonesCompilacionLayout.setVerticalGroup(
            jPanel_BotonesCompilacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BotonesCompilacionLayout.createSequentialGroup()
                .addComponent(jButton_Compilar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_TablaSimbolos, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Errores, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Debugger, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane_Consola.setBorder(null);

        jTextArea_Consola.setEditable(false);
        jTextArea_Consola.setColumns(20);
        jTextArea_Consola.setRows(5);
        jTextArea_Consola.setText("> Organización de Lenguajes y Compiladores 2 - Bienvenid@\n> ");
        jScrollPane_Consola.setViewportView(jTextArea_Consola);

        jTabbedPane_Archivos.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane_Archivos.setBorder(null);

        jPanel_BotonesArchivos.setBackground(new java.awt.Color(254, 254, 254));
        jPanel_BotonesArchivos.setBorder(null);

        jButton_Nuevo.setBackground(new java.awt.Color(255, 255, 255));
        jButton_Nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_nuevo.png"))); // NOI18N
        jButton_Nuevo.setToolTipText("Nuevo archivo");
        jButton_Nuevo.setBorderPainted(false);
        jButton_Nuevo.setContentAreaFilled(false);
        jButton_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NuevoActionPerformed(evt);
            }
        });

        jButton_Abrir.setBackground(new java.awt.Color(255, 255, 255));
        jButton_Abrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_abrir.png"))); // NOI18N
        jButton_Abrir.setToolTipText("Abrir archivo");
        jButton_Abrir.setBorderPainted(false);
        jButton_Abrir.setContentAreaFilled(false);
        jButton_Abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AbrirActionPerformed(evt);
            }
        });

        jButton_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_guardar.png"))); // NOI18N
        jButton_Guardar.setToolTipText("Guardar archivo");
        jButton_Guardar.setBorder(null);
        jButton_Guardar.setBorderPainted(false);
        jButton_Guardar.setContentAreaFilled(false);
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });

        jButton_GuardarComo.setBackground(java.awt.Color.white);
        jButton_GuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_guardarComo.png"))); // NOI18N
        jButton_GuardarComo.setToolTipText("Guardar como...");
        jButton_GuardarComo.setBorderPainted(false);
        jButton_GuardarComo.setContentAreaFilled(false);
        jButton_GuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarComoActionPerformed(evt);
            }
        });

        jButton_Cerrar.setBackground(java.awt.Color.white);
        jButton_Cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_error.png"))); // NOI18N
        jButton_Cerrar.setToolTipText("Cerrar archivo");
        jButton_Cerrar.setBorderPainted(false);
        jButton_Cerrar.setContentAreaFilled(false);
        jButton_Cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CerrarActionPerformed(evt);
            }
        });

        jButton_Dibujar.setBackground(java.awt.Color.white);
        jButton_Dibujar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_paleta.png"))); // NOI18N
        jButton_Dibujar.setToolTipText("Lienzos");
        jButton_Dibujar.setBorderPainted(false);
        jButton_Dibujar.setContentAreaFilled(false);
        jButton_Dibujar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DibujarActionPerformed(evt);
            }
        });

        jButton_Configurar.setBackground(java.awt.Color.white);
        jButton_Configurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/icono_config.png"))); // NOI18N
        jButton_Configurar.setToolTipText("Configuraciones");
        jButton_Configurar.setBorderPainted(false);
        jButton_Configurar.setContentAreaFilled(false);
        jButton_Configurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ConfigurarActionPerformed(evt);
            }
        });

        jButton_AcercaDe.setBackground(new java.awt.Color(255, 255, 255));
        jButton_AcercaDe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/esvux/lienzo2D/recursos/iconos/sol.png"))); // NOI18N
        jButton_AcercaDe.setBorder(null);
        jButton_AcercaDe.setBorderPainted(false);
        jButton_AcercaDe.setContentAreaFilled(false);
        jButton_AcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AcercaDeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_BotonesArchivosLayout = new javax.swing.GroupLayout(jPanel_BotonesArchivos);
        jPanel_BotonesArchivos.setLayout(jPanel_BotonesArchivosLayout);
        jPanel_BotonesArchivosLayout.setHorizontalGroup(
            jPanel_BotonesArchivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton_Nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton_Abrir, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton_GuardarComo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton_Cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton_Dibujar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton_Configurar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton_AcercaDe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jSeparator_Archivo)
            .addComponent(jSeparator_Configs)
        );
        jPanel_BotonesArchivosLayout.setVerticalGroup(
            jPanel_BotonesArchivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BotonesArchivosLayout.createSequentialGroup()
                .addComponent(jButton_Nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Abrir, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_GuardarComo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator_Archivo, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Dibujar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Configurar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator_Configs, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_AcercaDe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_Consola, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_BotonesArchivos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane_Archivos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_BotonesCompilacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator_Principal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane_Archivos)
                    .addComponent(jPanel_BotonesCompilacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel_BotonesArchivos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator_Principal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane_Consola, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //</editor-fold>

    //<editor-fold desc=" Control de los botones de la aplicación ">

    private void jButton_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NuevoActionPerformed
        TabPage tab = new TabPage();
        jTabbedPane_Archivos.add(tab.getFile(), tab);
        jTabbedPane_Archivos.setSelectedIndex(jTabbedPane_Archivos.getTabCount() - 1);
    }//GEN-LAST:event_jButton_NuevoActionPerformed

    private void jButton_AbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AbrirActionPerformed
        operarArchivos(ABRIR);
    }//GEN-LAST:event_jButton_AbrirActionPerformed

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        operarArchivos(GUARDAR);
    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void jButton_GuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarComoActionPerformed
        operarArchivos(GUARDAR_COMO);
    }//GEN-LAST:event_jButton_GuardarComoActionPerformed

    private void jButton_CerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CerrarActionPerformed
        int selected = jTabbedPane_Archivos.getSelectedIndex();
        if (selected >= 0) {
            jTabbedPane_Archivos.remove(selected);
        }
    }//GEN-LAST:event_jButton_CerrarActionPerformed

    private void jButton_CompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CompilarActionPerformed
        Sentencia.noDebug();
        compilar();
    }//GEN-LAST:event_jButton_CompilarActionPerformed

    private void jButton_ErroresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ErroresActionPerformed
        if (ultimoLienzoAnalizado == null) {
            logAdvertencia("No se ha analizado ningún archivo, no se mostrará ningún reporte de errores.");
            return;
        }
        abrirReporteErrores(ultimoLienzoAnalizado);
    }//GEN-LAST:event_jButton_ErroresActionPerformed

    private void jButton_TablaSimbolosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_TablaSimbolosActionPerformed
        if (ultimoLienzoAnalizado == null) {
            logAdvertencia("No se ha analizado ningún archivo, no se mostrará ningún reporte de tabla de símbolos.");
            return;
        }
        abrirReporteTablaDeSimbolos();
    }//GEN-LAST:event_jButton_TablaSimbolosActionPerformed

    private void jButton_DibujarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DibujarActionPerformed
        Publisher.getInstance().setVisible(true);
    }//GEN-LAST:event_jButton_DibujarActionPerformed

    private void jButton_AcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AcercaDeActionPerformed
        new About(this, true).setVisible(true);
    }//GEN-LAST:event_jButton_AcercaDeActionPerformed

    private void jButton_ConfigurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ConfigurarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_ConfigurarActionPerformed

    private void jButton_DebuggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DebuggerActionPerformed
        Sentencia.debug();
        compilar();
    }//GEN-LAST:event_jButton_DebuggerActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        int pausa = jSlider1.getValue();
        Sentencia.setTiempoPausa(pausa);
    }//GEN-LAST:event_jSlider1StateChanged

    private void compilar() {
        int selected = jTabbedPane_Archivos.getSelectedIndex();
        if (selected < 0) {
            return;
        }
        Publisher.showNewInstance();
        TabPage tab = (TabPage) jTabbedPane_Archivos.getSelectedComponent();
        if (tab.getPath() == null) {
            informar("Antes de compilar es necesario guardar el archivo.");
            return;
        }
        int fin = tab.getPath().lastIndexOf("/");
        String nuevaRaiz = tab.getPath().substring(0, fin) + "/";
        Lienzo.RAIZ = nuevaRaiz;
        ultimoLienzoAnalizado = tab.getFile();
        new Thread(new Runnable() {
            @Override
            public void run() {
                tab.parserLZ();
            }
        }).start();
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Main y declaraciones de la interfaz gráfica ">
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("GTK+".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Editor l2d = Editor.getInstance();
                l2d.jScrollPane_Consola.setViewportBorder(null);
                l2d.getContentPane().setBackground(Color.white);
                l2d.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Abrir;
    private javax.swing.JButton jButton_AcercaDe;
    private javax.swing.JButton jButton_Cerrar;
    private javax.swing.JButton jButton_Compilar;
    private javax.swing.JButton jButton_Configurar;
    private javax.swing.JButton jButton_Debugger;
    private javax.swing.JButton jButton_Dibujar;
    private javax.swing.JButton jButton_Errores;
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JButton jButton_GuardarComo;
    private javax.swing.JButton jButton_Nuevo;
    private javax.swing.JButton jButton_TablaSimbolos;
    private javax.swing.JPanel jPanel_BotonesArchivos;
    private javax.swing.JPanel jPanel_BotonesCompilacion;
    private javax.swing.JScrollPane jScrollPane_Consola;
    private javax.swing.JSeparator jSeparator_Archivo;
    private javax.swing.JSeparator jSeparator_Configs;
    private javax.swing.JSeparator jSeparator_Principal;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTabbedPane jTabbedPane_Archivos;
    private javax.swing.JTextArea jTextArea_Consola;
    // End of variables declaration//GEN-END:variables


    private static final int ABRIR = 0;
    private static final int GUARDAR = 1;
    private static final int GUARDAR_COMO = 2;
    private static final int LEVEL_INFO = 0;
    private static final int LEVEL_WARNING = 1;
    private static final int LEVEL_ERROR = 2;
    private static final String[] LEVELS = {"INFO", "OJO", "ERROR"};

    private void logAdvertencia(String log) {
        log(log, LEVEL_WARNING);
    }

    private void logError(String log) {
        log(log, LEVEL_ERROR);
    }

    private void logInfo(String log) {
        log(log, LEVEL_INFO);
    }

    private void log(String message, int level) {
        jTextArea_Consola.append("[" + LEVELS[level] + "]\t" + message + "\n> ");
    }

    public void operarArchivos(int operacion) {
        int selected = jTabbedPane_Archivos.getSelectedIndex();
        if (selected < 0) {
            return;
        }
        TabPage tab = (TabPage) jTabbedPane_Archivos.getSelectedComponent();
        try {
            switch (operacion) {
                case ABRIR:
                    tab.abrir();
                    logInfo("Abierto:\t" + tab.getFile());
                    break;
                case GUARDAR:
                    tab.guardar();
                    logInfo("Guardado:\t" + tab.getFile());
                    break;
                case GUARDAR_COMO:
                    tab.guardarComo();
                    logInfo("Guardado:\t" + tab.getPath());
                    break;
            }
            jTabbedPane_Archivos.setTitleAt(selected, tab.getFile());
            jTabbedPane_Archivos.setToolTipTextAt(selected, tab.getPath());
        } catch (IOException ex) {
            logError(ex.getMessage());
        }
    }

    private void abrirReporteErrores(String lienzoAnalizado) {
        try {
            String rutaReporte = ManejadorErrores.getInstance().generarReporte(lienzoAnalizado);
            if (rutaReporte == null) {
                logError("Es imposible generar el reporte de errores.");
                return;
            }
            File path = new File(rutaReporte);
            if (path.exists()) {
                Desktop.getDesktop().open(path);
                logInfo("Abriendo el reporte de errores.");
            } else {
                logError("Error al abrir el reporte de errores, no existe el archivo.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void abrirReporteTablaDeSimbolos() {
        try {
            File path = new File(TablaSimbolos.REPORTE);
            if (path.exists()) {
                Desktop.getDesktop().open(path.getAbsoluteFile());
                logInfo("Abriendo el reporte de la tabla de símbolos.");
            } else {
                logError("Error al abrir el reporte de la tabla de símbolos, no existe el archivo.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //</editor-fold>
}
