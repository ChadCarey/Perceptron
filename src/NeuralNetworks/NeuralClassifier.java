/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeuralNetworks;

import DataSet.DataPoint;
import DataSet.DataSet;
import Perceptron.Perceptron;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Uses the perceptron to classify data. Attaches meaning to the perceptron outputs
 * @author chad
 */
public class NeuralClassifier {
    public static void main(String[] args) {
        DataSet set;
        try {
            set = new DataSet("irisdata.csv", -1);
            DataSet trainingSet = set.cleanPercent(70.0);
            //DataSet trainingSet = set.removePercent(70);
            NeuralClassifier classy = new NeuralClassifier(trainingSet);
            double accuracy = classy.evaluate(set);
            System.out.println("accuracy: " + accuracy);
        } catch (IOException ex) {
            Logger.getLogger(NeuralClassifier.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(3);
        } catch (NumberFormatException ex) {
            Logger.getLogger(NeuralClassifier.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("DataSet only allows numeric attributes");
            System.exit(3);
        }
    }
    
    
    Perceptron perceptron;
    // target Values indicates the meaning of each neuron from the output layer
    ArrayList<String> targetValues;
    
    public NeuralClassifier(DataSet trainingData) {
        targetValues = new ArrayList<String>();
        
        // load and assign targets to each neuron on the output layer,
        // integers indicate the index of the neuron
        Set<String> targetSet = trainingData.getTargetValues();
        Iterator<String> iter = targetSet.iterator();
        while(iter.hasNext()) {
            String value = new String(iter.next());
            System.out.println(value);
            targetValues.add(value);
        }
        System.out.println("targetValues.size() " + targetValues.size());
        perceptron = new Perceptron(targetValues.size(), trainingData.getOne());
        this.train(trainingData);
    }

    /**
     * returns the classified class
     * @param attributes
     * @return 
     */
    public String classify(HashMap<String,Double> attributes) {
        // run the perceptron
        List<Double> outputs = perceptron.input(attributes);
        
        // find the maximum output
        int maxIndex = 0;
        Double maxClass = Double.MIN_VALUE;
        System.out.println("Outputs: " + outputs);
        for(int i = 0; i < outputs.size(); ++i) {
            if(outputs.get(i) > maxClass) {
                maxClass = outputs.get(i);
                maxIndex = i;
            }
        }
        // return the maximum output string
        // target Values indicates the meaning of each neuron from the output layer
        return targetValues.get(maxIndex);
    }

    /**
     * 
     * @param trainingData 
     */
    private void train(DataSet trainingData) {
        long count = 0;
        double minLearningRate = 0.1;
        double agingRate = 0.999;
        
        while(this.perceptron.getLearningRate() > minLearningRate) {
            //trainingData.randomize();
            count++;
            Iterator<DataPoint> iter = trainingData.iterator();
            while(iter.hasNext()) {
                DataPoint p = iter.next();

                // generate the correct outputs using the targetValues
                // target Values indicates the meaning of each neuron from the output layer
                List<Double> correctValues = new ArrayList<Double>();
                for(int i = 0; i < targetValues.size(); ++i) {
                    if(!targetValues.get(i).equals(p.getTarget())) {
                        correctValues.add(0.0);
                    } else {
                        correctValues.add(1.0);
                    }
                }

                this.perceptron.learn(p.getAttributes(), correctValues);
            }
            // age the perceptron learning rate
            this.perceptron.setLearningRate(this.perceptron.getLearningRate()*agingRate);
        }
        System.out.println("Number itterations: " + count);
    }

    private double evaluate(DataSet set) {
        double correct = 0;
        
        Iterator<DataPoint> iter = set.iterator();
        while(iter.hasNext()) {
            DataPoint p = iter.next();
            System.out.println("attributes: " + p.getAttributes());
            String output = this.classify(p.getAttributes());
            System.out.println("output:" + output + " correct: " + p.getTarget());
            if(output.equals(p.getTarget()))
                correct++;
        }
        
        return correct/set.size();
    }
}
