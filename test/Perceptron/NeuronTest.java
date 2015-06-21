package Perceptron;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Perceptron.Neuron;
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
public class NeuronTest {
    
    public NeuronTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testCreation() {
        Neuron n = new Neuron();
        Assert.assertTrue(n != null);
    }
    
    @Test
    public void testGet() {
        Neuron n = new Neuron();
        Double weight = n.get(n);
        this.print("Generated weight: " + weight);
        Assert.assertTrue(weight != null);
    }
    
    @Test
    public void testInput() {
        Neuron n = new Neuron();
    }
    
    private void print(String str) {
        System.out.println(str);
    }
    
}
