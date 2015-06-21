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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chad
 */
public class NeuralClassifier {
    public static void main(String[] args) {
        DataSet set;
        try {
            set = new DataSet("irisdata.csv");
            DataSet trainingSet = set.cleanPercent(70.0);
            //DataSet trainingSet = set.removePercent(70);
            NeuralClassifier classy = new NeuralClassifier(trainingSet);
            classy.classify(set.getOne());
        } catch (IOException ex) {
            Logger.getLogger(NeuralClassifier.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(3);
        }
    }
    
    
    Perceptron perceptron;
    ArrayList<String> targetValues;
    
    public NeuralClassifier(DataSet trainingData) {
        targetValues = new ArrayList<String>();
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
     * @param dataPoint
     * @return 
     */
    public String classify(DataPoint dataPoint) {
        perceptron.input(dataPoint);
        List<Double> outputs = perceptron.getOutput();
        
        int maxIndex = 0;
        Double maxClass = Double.MIN_VALUE;
        for(int i = 0; i < outputs.size(); ++i) {
            if(outputs.get(i) > maxClass) {
                maxClass = outputs.get(i);
                maxIndex = i;
            }
        }
        return targetValues.get(maxIndex);
    }

    /**
     * 
     * @param trainingData 
     */
    private void train(DataSet trainingData) {
        if(false)
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        Iterator<DataPoint> iter = trainingData.iterator();
        while(iter.hasNext()) {
            DataPoint p = iter.next();
            String output = this.classify(p);
            System.out.println(p.getTarget() + " : " + output);
        }
    }
}
