package Perceptron;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Perceptron.Perceptron;
import DataSet.DataPoint;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chad
 */
public class PerceptronTest {
    private DataPoint exampleData;
    
    public PerceptronTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        exampleData = new DataPoint();
        for(int i = 1; i <= 3; ++i)
            exampleData.put("test_"+i, i+Math.random());
        exampleData.setTarget("target", "value");
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testBuild() {
        Perceptron p = new Perceptron(3, exampleData);
        Assert.assertTrue(p != null);
    }
    
    @Test
    public void testInput() {
        int numOutputs = 3;
        Perceptron p = new Perceptron(numOutputs, exampleData);
        p.input(exampleData);
        List<Double> output = p.getOutput();
        Assert.assertTrue(output != null);
        Assert.assertTrue(output.size() == numOutputs);
    }
    
}
