package org.esvux.lienzo2D.utilidades;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
* @autor esvux
 */
public class Graphviz {

    public static void main(String args[]) {
        Graphviz gv = new Graphviz();
        gv.startGraph();
        gv.addln("graph [rankdir = \"LR\"];");
        gv.addln("ratio = auto;");
        gv.addln("node[style = \"filled, bold\" penwidth = 5 fillcolor = \"white\" fontname = \"Courier New\" shape = \"Mrecord\"];");
        gv.addln("A->B;");
        gv.endGraph();
        System.out.println(gv.getDotSource());
        String type = "png";
        File out = new File("ejemplo." + type);   // out.gif in this example
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }

    private String pathToDOT;
    private StringBuilder graph;

    public Graphviz() {
        this.pathToDOT = "/usr/bin/dot";
        graph = new StringBuilder();
    }

    public Graphviz(String pathToDOT) {
        this.pathToDOT = pathToDOT;
        graph = new StringBuilder();
    }

    public void setPathToDOT(String pathToDOT) {
        this.pathToDOT = pathToDOT;
    }

    public void startGraph() {
        graph = new StringBuilder("digraph G {\n");
    }

    public String getDotSource() {
        return graph.toString();
    }

    public void add(String line) {
        graph.append(line);
    }

    public void addln(String line) {
        graph.append(line);
        graph.append('\n');
    }

    public void addln() {
        graph.append('\n');
    }

    public void endGraph() {
        graph.append('}');
    }

    public byte[] getGraph(String dotSource, String type) {
        File dot;
        byte[] imgStream;
        try {
            dot = writeDotSourceToFile(dotSource);
            if (dot != null) {
                imgStream = getImgStream(dot, type);
                if (dot.delete() == false) {
                    System.err.println("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
                }
                return imgStream;
            }
            return null;
        } catch (java.io.IOException ioe) {
            return null;
        }
    }

    public int writeGraphToFile(byte[] img, String file) {
        File to = new File(file);
        return writeGraphToFile(img, to);
    }

    public int writeGraphToFile(byte[] img, File to) {
        try (FileOutputStream fos = new FileOutputStream(to)) {
            fos.write(img);
        } catch (java.io.IOException ioe) {
            return -1;
        }
        return 1;
    }

    private byte[] getImgStream(File dot, String type) {
        File img;
        byte[] imgStream = null;
        try {
            img = File.createTempFile("graph_", "." + type);
            Runtime rt = Runtime.getRuntime();
            // patch by Mike Chenault
            String[] args = {pathToDOT, "-T" + type, dot.getAbsolutePath(), "-o", img.getAbsolutePath()};
            Process p = rt.exec(args);
            p.waitFor();
            FileInputStream in = new FileInputStream(img.getAbsolutePath());
            imgStream = new byte[in.available()];
            // Close it if we need to
            if (in.read(imgStream) == -1) {
                in.close();
            }
            img.deleteOnExit();
        } catch (java.io.IOException ioe) {
            System.err.println("Error:    in I/O processing of tempfile in dir");
            System.err.println("       or in calling external command");
            ioe.printStackTrace();
        } catch (java.lang.InterruptedException ie) {
            System.err.println("Error: the execution of the external program was interrupted");
            ie.printStackTrace();
        }
        return imgStream;
    }

    private File writeDotSourceToFile(String str) throws java.io.IOException {
        File temp = File.createTempFile("graph_", ".dot.tmp");
        try (FileWriter fout = new FileWriter(temp)) {
            fout.write(str);
            fout.close();
        } catch (Exception e) {
            System.err.println("Error: I/O error while writing the dot source to temp file!");
            return null;
        }
        return temp;
    }

    public void readSource(String input) throws java.io.FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = new FileInputStream(input);
        try (DataInputStream dis = new DataInputStream(fis)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            dis.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        this.graph = sb;
    }

}