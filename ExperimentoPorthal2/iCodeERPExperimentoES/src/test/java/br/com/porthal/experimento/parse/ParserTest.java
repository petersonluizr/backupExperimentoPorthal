/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.porthal.experimento.parse;

import br.com.porthal.experimento.entity.NotaFiscal;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

/**
 *
 * @author douglas
 */
public class ParserTest {
    
    public ParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    @Test
    public void testGetNFs() {
        System.out.println("getNFs");
        String xml = "1;A;0;1;João;0;0;10/10/2017;1;João;229.42;20.0;249.42;{1;azul 25x25;batata;1;20.0;5.29;14.71;20.0;,2;vermelho 25x25;pimentão;10;220.0;5.29;214.71;50.0}\n" +
"2;S;1;1;João;2;1;10/11/2017;1;João;40.0;20.0;60.0;{3;verde 25x25;morango;1;10.0;0;10.0;20.0;,4;rosa 25x25;power ranger;10;40.0;10.0;30.0;50.0}";
        String expResult = "";
        ArrayList<NotaFiscal> result = Parser.getNFs(xml);
    }
    
}
