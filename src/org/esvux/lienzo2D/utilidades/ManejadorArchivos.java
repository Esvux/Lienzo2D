package org.esvux.lienzo2D.utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author esvux
 */
public class ManejadorArchivos {
    
    private static JFileChooser selectorArchivo;
    private static final String FILTRO_DESC = "Archivos de texto";
    private static final String FILTRO_EXT[] = {"LZ", "lz"};
    
    /**
     * Configura el filtro para la gestion de los archivos en el programa, si no
     * se ha configurado, se asume que la extension por defecto es txt, definida
     * por las constantes FILTRO_DESC y FILTRO_EXT.
     * @param descripcion descripción para el filtro.
     * @param extensiones posibles extensiones reconocidas por el filtro.
     */
    public static void setup(String descripcion, String ... extensiones)
    {
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(descripcion, extensiones);
        selectorArchivo = new JFileChooser();
        selectorArchivo.setFileFilter(filtro);
    }
        
    /**
     * Despliega un cuadro de dialogo para poder abrir un archivo, si el filtro
     * no ha sido configurado previamente, mostrará todos los archivos.
     * @return Array de String que en la posición 0 guarda el nombre del archivo
     * seleccionado, en la posición 1 guarda el path absoluto y en la posición 2
     * almacena el contenido leído del archivo.
     * @throws java.io.IOException El archivo que se desea abrir es inaccesible o no existe.
     */
    public static String[] abrirArchivo() throws IOException
    {
        preManagment();
        int resultado = selectorArchivo.showOpenDialog(null);
        if(resultado == JFileChooser.APPROVE_OPTION)
        {
            File path = selectorArchivo.getSelectedFile().getAbsoluteFile();
            String texto = abrir(path);
            String respuesta[] = new String[3];
            respuesta[0] = path.getName();
            respuesta[1] = path.getAbsolutePath();
            respuesta[2] = texto;
            return respuesta;
        }
        return null;
    }
    
    /**
     * Si el archivo es nuevo (Sin guardar), se despliega un dialogo para elegir
     * el nombre y destino del archivo para ser guardado.
     * @param path Direccion en la que se desea guardar el archivo.
     * @param contenido Contenido del archivo a guardar.
     * @return Un String que representa la ruta del archivo guardado o null si no
     * se pudo guardar el archivo.
     * @throws java.io.IOException Ocurrió un error y es imposible escribir el archivo.
     */
    public static String guardarArchivo(String path, String contenido) throws IOException
    {
        preManagment();
        if(path==null)
        {
            return guardarArchivoComo(contenido);
        }
        return guardar(new File(path), contenido);
    }
    
    /**
     * Despliega directamente el dialogo para elegir el nombre y destino del archivo
     * para ser guardado
     * @param contenido Contenido del archivo a guardar.
     * @return Un String que representa la ruta del archivo guardado o null si no
     * se pudo guardar el archivo.
     * @throws java.io.IOException Ocurrió un error y no es posible escribir el 
     * archivo con el nuevo nombre.
     */
    public static String guardarArchivoComo(String contenido) throws IOException
    {
        preManagment();
        int resultado = selectorArchivo.showSaveDialog(null);
        File tempPath;
        if (resultado == JFileChooser.APPROVE_OPTION)
        {
            tempPath = selectorArchivo.getSelectedFile().getAbsoluteFile();
            return guardar(tempPath, contenido);
        }
        return null;
    }
        
    /**
     * Abre un archivo existente.
     * @param path Direccion del archivo que se desea abrir.
     * @return El texto contenido en el archivo.
     * @throws java.io.IOException Ocurre cuando no encuentra el archivo que se 
     * desea abrir o el archivo no es accesible por la aplicación.
     */
    public static String abrir(File path) throws IOException
    {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader entrada = new BufferedReader(fr);
            String lineaNueva;
            String texto = "";
            while((lineaNueva = entrada.readLine()) != null){
                texto += lineaNueva+"\n";
            }
            entrada.close();
            return texto;
        }catch(IOException ex){
            throw ex;
        }
    }
    
    /**
     * Crea o guarda un archivo.
     * @param path Direccion en donde se va a guardar el archivo.
     * @param contenido Texto que se desea guardar.
     * @return Ruta en donde se ha guardado el archivo o null si ocurre algun problema.
     * @throws java.io.IOException Ocurre cuando no es posible acceder al archivo 
     * que se desea guardar.
     */
    public static String guardar(File path, String contenido) throws IOException
    {
        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            salida.print(contenido);
            salida.close();
            bw.close();
            return path.getAbsolutePath();
        } catch (IOException ex) {
            throw ex;
        }        
    }
    
    /**
     * Configura el filtro por defecto de ser necesario.
     */
    private static void preManagment()
    {
        if(selectorArchivo==null)
        {
            System.err.println("Configurando filtros de archivos por defecto (" + FILTRO_DESC + ").");
            setup(FILTRO_DESC, FILTRO_EXT);
        }
    }
    
}
