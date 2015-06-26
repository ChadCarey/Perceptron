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
        //System.out.println("HashCode: " + this.hashCode());
    }
    
    /**
     * returns the hash code for the Neuron. 
     * This must be here so that Neurons will have a hash code like an object
     * rather than like a HashMap
     * @return 
     */
    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    /**
     * 
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }
    
    /**
     * 
     * @param input
     * @return 
     */
    public double input(NeuronLayer input, BiasNeuron bias) {
       
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
        //System.out.println(this.hashCode() + " regression: " + regression);
        this.output = calculateOutput(sum);
        //System.out.println("output: " + this.output + "\n");
        
        return this.getOuput();
    }
    
    /**
     * 
     * @param sum
     * @return 
     */
    protected double calculateOutput(double h) {
        double e = Math.E;
        double out = 1.0/(1+Math.pow(e, -h));
        return out;
    }
    
    /*LEARNING METHODS*/
    
    /**
     * 
     * @param connectedLayer
     * @param learningRate 
     */
    public void learn(NeuronLayer connectedLayer, double learningRate) {
        this.calculateError(connectedLayer);
        this.calculateWeights(connectedLayer, learningRate);
    }
    
    /**
     * 
     * @param connectedLayer
     * @param learningRate 
     */
    protected void calculateWeights(NeuronLayer connectedLayer, double learningRate) {
        // neuron_l:weight = neuron_l:weight - learningRate * neuron_r:error * neuron_l:output
        Iterator<Neuron> iter = connectedLayer.iterator();
        while(iter.hasNext()) {
            Neuron nR = iter.next();
            double newWeight = this.get(nR) - learningRate*nR.getError()*this.getOuput();
            this.put(nR, newWeight);
        }
    }
    
    /**
     * 
     * @param connectedLayer 
     */
    protected void calculateError(NeuronLayer connectedLayer) {
        // this.error = this.output(1-this.output)*sum(weightlr*errorr)
        double weightedErrorSum = 0.0;
        Iterator<Neuron> iter = connectedLayer.iterator();
        while(iter.hasNext()) {
            Neuron neuron = iter.next();
            weightedErrorSum += this.get(neuron) * neuron.getError();
        }
        this.error = this.getOuput()*(1-this.getOuput())*weightedErrorSum;
    }
    
    /**
     * 
     * @param correctValue 
     */
    public void calculateOutputError(Double correctValue) {
        this.error = this.getOuput() * (1-this.getOuput())*(this.getOuput()-correctValue);
    }
    
    /* GETTERS */
    
    
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
     * @return 
     */
    public double getOuput() {
        return this.output;
    }
    
    /**
     * 
     * @return 
     */
    private double getError() {
        return this.error;
    }
    
    /**
     * 
     * @return 
     */
    public double getRegression() {
        return this.regression;
    }
    
    /**
     * 
     * @param connection
     * @return 
     */
    double addConnection(Neuron connection) {
        // start weight at a small possitive or negative value between ~0.1 and ~0.4;
        //System.out.println("Adding new connection");
        Random randGenerator = new Random(System.nanoTime());
        double newWeight = randGenerator.nextDouble() + 0.1;
        if(newWeight > 0.5)
            newWeight -= 1.0;
        this.put(connection, newWeight);
        //System.out.println("New Weight: " + newWeight + "\n");
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
     * 
     * @param value 
     */
    void setOuput(double value) {
        this.output = value;
    }
    
}
