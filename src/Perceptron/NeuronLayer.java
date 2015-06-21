package Perceptron;


import java.util.ArrayList;
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
class NeuronLayer extends ArrayList<Neuron> {
    private BiasNeuron biasNeuron = new BiasNeuron(); // this is the bias for this layer

    NeuronLayer() {
        super();
    }

    NeuronLayer(int numNeurons) {
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
            neuron.input(layer);
        }
    }
    
}
