package Perceptron;


import DataSet.DataPoint;
import java.util.ArrayList;
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
public class Perceptron {
    private HashMap<String, Integer> inputReference;
    private ArrayList<NeuronLayer> layers;
    private double endLearningRate = 0.1;
    private double startingLeringRate = 1.0;
    private double AGEING_FACTOR = 0.99;
    
    public Perceptron(int numOutputs, DataPoint exampleData) {
        inputReference = new HashMap<String, Integer>();
        layers = new ArrayList<NeuronLayer>();
        
        // build the input layer
        NeuronLayer inputLayer = new NeuronLayer();
        Iterator<String> iter = exampleData.getAttributeKeys().iterator();
        int inputIndex = 0;
        while(iter.hasNext()) {
            String key = iter.next();
            Neuron n = new Neuron();
            inputReference.put(key, inputIndex);
            inputIndex++;
            inputLayer.add(n);
        }
        int numInputs = inputLayer.size();
        int numMiddle = (numInputs+numOutputs)/2;
        
        System.out.println("numIn: " + numInputs + " numMid: " + numMiddle + " numOut: " + numOutputs);
        
        // add/build the rest of the NeuronLayers, default is 3 layers
        layers.add(inputLayer);
        layers.add(new NeuronLayer(numMiddle));
        layers.add(new NeuronLayer(numOutputs));
        if(layers.size() < 3) {
            System.err.println("Error creating perceptron, not enough layers");
            System.exit(3);
        }
    }
    
    
    /**
     * converts the DataPoint into a neuronLayer and starts the network
     * @param point 
     */
    public void input(DataPoint point) {
        
        Iterator<String> iter = point.getAttributeKeys().iterator();
        while(iter.hasNext()) {
            String inputKey = iter.next();
            // get the reference to the assigned neuron
            Integer neuronIndex = inputReference.get(inputKey);
            if(neuronIndex == null) {
                System.err.println("Invalid data point, data point must match the one used to build the perceptron");
                System.exit(1);
            }
            try{
                Double inputValue = Double.parseDouble(point.get(inputKey));
                // set the correct neurons output from the input layer
                layers.get(0).get(neuronIndex).setOuput(inputValue);
            } catch(Exception e) {
                e.printStackTrace();
                System.err.println("Invalid input format, perceptron only takes numeric data");
                System.exit(2);
            }
        }
        run();
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
        int index = this.layers.size()-1;
        if(index >= 0) {
            NeuronLayer outputLayer = this.layers.get(index);
            
            for(int i = 0; i < outputLayer.size(); ++i) {
                System.out.println("outputLayerSize: " + outputLayer.size());
                System.out.println("currentIndex: " + i);
                outputData.add(outputLayer.get(index).getOuput());
            }
        }
        return outputData;
    }
}
