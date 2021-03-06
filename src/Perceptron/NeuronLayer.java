package Perceptron;


import java.util.ArrayList;
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
class NeuronLayer extends ArrayList<Neuron> {
    private BiasNeuron biasNeuron = new BiasNeuron(); // this is the bias for this layer

    NeuronLayer() {
        super();
    }

    NeuronLayer(int numNeurons) {
        super();
        for(int i = 0; i < numNeurons; ++i)
            this.add(new Neuron());
    }
    
    BiasNeuron getBias() {
        return this.biasNeuron;
    }
    
    void input(NeuronLayer layer) {
        Iterator<Neuron> iter = this.iterator();
        while(iter.hasNext()) {
            Neuron neuron = iter.next();
            neuron.input(layer, this.getBias());
        }
    }

    void generateError(List<Double> correctValues) {
           // loop through the neurons
        for(int i = 0; i < this.size() && i < correctValues.size(); ++i) {
            this.get(i).calculateOutputError(correctValues.get(i));
        }
    }

    void learn(NeuronLayer connectedLayer, double currentLearningRate) {
        
        // itterate throught each neuron, passing each the connected layer and the currentLearningRate
        Iterator<Neuron> iter = this.iterator();
        while(iter.hasNext()) {
            Neuron n = iter.next();
            n.learn(connectedLayer, currentLearningRate);
        }
    }
    
    void updateBias(double currentLearningRate) {
        // update the bias inputing to this layer
        this.getBias().learn(this, currentLearningRate);
    }
    
}
