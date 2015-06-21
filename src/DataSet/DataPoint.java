/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.util.HashMap;

/**
 * 
 * @author chad
 */
public class DataPoint {
    
    HashMap<String, Double> attributes = new HashMap<String, Double>();
    String targetName = null;
    Object targetValue = null;
    
    public DataPoint() {
        super();
    }

    public String getTargetName() {
        return this.targetName;
    }
    
    public Object getTarget() {
        return this.targetValue;
    }

    public HashMap<String,Double> getAttributes() {
        return attributes;
    }

    public void put(String string, double data) {
        this.attributes.put(string, data);
    }

    public void setTarget(String target, Object value) {
        if(target != null && value != null ) {
            this.targetName = target;
            this.targetValue = value;
        }
    }

}
