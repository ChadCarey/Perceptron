package Perceptron;


import DataSet.DataPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chad
 */
public class Perceptron {
    private HashMap<String, Integer> inputReference;
    private ArrayList<NeuronLayer> layers;
    private double learningRate = 0.4;
    
    /**
     * ToDo: get DataPoint out of the perceptron. 
     *      Make the class buildable using a list of integers and and list of input names
     *          also we could make it so the inputs can be created dynamically when it is not found
     * @param numOutputs
     * @param exampleData 
     */
    public Perceptron(int numOutputs, DataPoint exampleData) {
        // set up the input reference, used to determine the correct nueron for each attribute
        inputReference = new HashMap<String, Integer>();
        layers = new ArrayList<NeuronLayer>();
        
        // build the input layer
        NeuronLayer inputLayer = new NeuronLayer();
        Iterator<String> iter = exampleData.getAttributes().keySet().iterator();
        int inputIndex = 0;
        while(iter.hasNext()) {
            String key = iter.next();
            Neuron n = new Neuron();
            inputReference.put(new String(key), inputIndex);
            inputIndex++;
            inputLayer.add(n);
        }
        int numInputs = inputLayer.size();
// REMOVE LATER        
        if(numInputs != inputIndex) {
            System.err.println(this.getClass().getName() + "::numInput != inputIndex+1");
            System.err.println(numInputs + " : " + inputIndex);
            System.exit(2);
        }
            
        int numMiddle = (numInputs+numOutputs)/2;
        
        System.out.println("numIn: " + numInputs + " numMid: " + numMiddle + " numOut: " + numOutputs);
        
        // add/build the rest of the NeuronLayers, default is 3 layers
        layers.add(inputLayer);
        layers.add(new NeuronLayer(numInputs));
        //layers.add(new NeuronLayer(numMiddle));
        layers.add(new NeuronLayer(numOutputs));
        if(layers.size() < 3) {
            System.err.println("Error creating perceptron, not enough layers");
            System.exit(3);
        }
    }
    
    
    /**
     * converts the DataPoint into a neuronLayer and starts the network
     * @param attributes 
     */
    public ArrayList<Double> input(HashMap<String,Double> attributes) {
        
        // set the first layer's "outputs" to the correct inputs
        // these outputs will be passed through the neuron connections before going to the next layer
        Iterator<String> iter = attributes.keySet().iterator();
        while(iter.hasNext()) {
            String inputKey = iter.next();
            // get the reference to the assigned neuron
            Integer neuronIndex = inputReference.get(inputKey);
            if(neuronIndex == null) {
                System.err.println("Invalid data point, data point must match the one used to build the perceptron");
                System.exit(1);
            }
            try{
                Double inputValue = attributes.get(inputKey);
                // set the correct neurons output from the input layer
                layers.get(0).get(neuronIndex).setOuput(inputValue);
            } catch(Exception e) {
                e.printStackTrace();
                System.err.println("Invalid input format, perceptron only takes numeric data");
                System.exit(2);
            }
        }
        // start passing the information through the network
        run();
        //System.out.println("outputs: " + this.getOutput());
        return this.getOutput();
    }
    
    /**
     * loop through each layer, passing the last layer forward
     */
    private void run() {
        
        Iterator<NeuronLayer> iter = this.layers.iterator();
        
        // get the starting neuron layer to pass forward
        NeuronLayer last = null;
        if(iter.hasNext()) {
            last = iter.next();
        } else {
            System.err.println("failed to get starting neruonLayer");
            System.exit(1);
        }
            
        while(iter.hasNext()) {
            NeuronLayer layer = iter.next();
            layer.input(last);
            last = layer;
        }
    }
    
    /**
     * returns an array of the output results from the output layer
     */
    public ArrayList<Double> getOutput() {
        ArrayList<Double> outputData = new ArrayList();
        NeuronLayer outputLayer = this.getOutputLayer();
            
        for(int i = 0; i < outputLayer.size(); ++i) {
            outputData.add(outputLayer.get(i).getOuput());
        }
        
        return outputData;
    }

    public void learn(List<Double> correctValues) {
        // get the output layer and set the output error
        NeuronLayer last = this.getOutputLayer();
        last.generateError(correctValues);
        
        for(int i = this.layers.size()-2; i >= 0; --i) {
            NeuronLayer layer = this.layers.get(i);
            layer.learn(last, this.learningRate);
            layer.updateBias(learningRate);
            // set the layer to the last layer
            last = layer;
        }
    }

    public void learn(HashMap<String,Double> attributes, List<Double> correctValues) {
        this.input(attributes);
        this.learn(correctValues);
    }
    
    private NeuronLayer getOutputLayer() {
        int index = this.layers.size()-1;
        if(index >= 0) {
            return this.layers.get(index);
        } else {
            return null;
        }
    }
    
    public void setLearningRate(double rate) {
        this.learningRate = rate;
    }

    public double getLearningRate() {
        return this.learningRate;
    }
}
