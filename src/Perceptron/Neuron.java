package Perceptron;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

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
    private double error;
    
    // note: a neuron is a node that knows it's output weights
    
    /**
     * 
     */
    Neuron() {
        super();
        System.out.println("HashCode: " + this.hashCode());
    }
    

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }
    
    /**
     * 
     * @param input
     * @return 
     */
    double input(NeuronLayer input, BiasNeuron bias) {
       
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
        System.out.println(this.hashCode() + " regression: " + regression);
        this.output = calculateOutput(sum);
        System.out.println("output: " + this.output + "\n");
        
        return this.getOuput();
    }
    
    /**
     * 
     * @param sum
     * @return 
     */
    private double calculateOutput(double h) {
        double e = Math.E;
        double out = 1.0/(1+Math.pow(e, -h));
        return out;
    }
    
    /**
     * 
     */
    void calculateError() {
        
    }
    
    void calculateError(Double correctValue) {
        this.error = this.getOuput() * (1-this.getOuput())*(this.getOuput()-correctValue);
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
        System.out.println("Adding new connection");
        Random randGenerator = new Random(System.nanoTime());
        double newWeight = randGenerator.nextDouble() + 0.1;
        if(newWeight > 0.5)
            newWeight -= 1.0;
        this.put(connection, newWeight);
        System.out.println("New Weight: " + newWeight + "\n");
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
