package org.esvux.lienzo2D.tests;

import org.esvux.lienzo2D.compilador.ErrorLienzo;
import org.esvux.lienzo2D.compilador.ManejadorErrores;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author esvux
 */
public class PruebasUtilidades {
    
    public PruebasUtilidades() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void test_ManejadorErrores_GeneracionReporte()
    {
        String canvas = "Lienzo1.lz";
        ManejadorErrores manager = ManejadorErrores.getInstance(canvas);
        manager.addErrorSintactico(3, 14, "Se esperaba '$'");
        String resultado = manager.generarReporte(canvas);
        assertTrue(resultado.contains("correctamente"));
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
