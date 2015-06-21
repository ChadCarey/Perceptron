package Perceptron;


import java.util.HashMap;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chad
 */
class Neuron extends HashMap<Neuron, Double> {
    
    private double output;
    private double regression;
    
    // note: a neuron is a node that knows it's output weights
    
    /**
     * 
     */
    Neuron() {
        super();
    }
    
    /**
     * 
     * @param input
     * @return 
     */
    double input(NeuronLayer input) {
        BiasNeuron bias = input.getBias();
       
        // add the bias to the weighted sum
        double sum = bias.getOuput() * bias.get(this);
        
        // add the other wieghted sums
        Iterator<Neuron> iter = input.iterator();
        while(iter.hasNext()) {
            Neuron inputNeuron = iter.next();
            Double inputValue = inputNeuron.getOuput();
            Double inputWeight = inputNeuron.get(this);
            sum += inputValue * inputWeight;
        }
        
        
        this.regression = sum;
        this.output = calculateOutput(sum);
        
        return this.getOuput();
    }
    
    /**
     * 
     * @param sum
     * @return 
     */
    private double calculateOutput(double sum) {
        double e = Math.E;
        double out = 1.0/(1+Math.pow(e, -sum));
        return out;
    }
    
    /**
     * 
     */
    void calculateError() {
        
    }
    
    /**
     * 
     */
    void calculateNewWeights() {
        
    }
    
    /**
     * 
     * @return 
     */
    double getOuput() {
        return this.output;
    }
    
    double getRegression() {
        return this.regression;
    }
    
    /**
     * 
     * @param connection
     * @return 
     */
    double addConnection(Neuron connection) {
        // start weight at a small possitive or negative value between ~0.1 and ~0.4;
        double newWeight = Math.random();
        if(newWeight > 0.5)
            newWeight -= 1.0;
        this.put(connection, newWeight);
        return newWeight;
    }
    

    /**
     * adds a weight reference to every neuron in the given layer
     * @param keys
     * @param value
     * @return 
     */
    public void put(NeuronLayer keys, Double value) {
        Iterator<Neuron> iter = keys.iterator();
        while(iter.hasNext()) {
            Neuron n = iter.next();
            this.put(n, value);
        }
    }

    /**
     * returns the requested connection weight. 
     * Note: if no connection exists it will be created
     * @param key
     * @return connection weight
     */
    @Override
    public Double get(Object key) {
        Double weight = super.get(key); //To change body of generated methods, choose Tools | Templates.
        if(weight == null)
            weight = this.addConnection((Neuron)key);
        return weight;
    }
    
    /**
     * 
     * @param value 
     */
    void setOuput(double value) {
        this.output = value;
    }
    
}
