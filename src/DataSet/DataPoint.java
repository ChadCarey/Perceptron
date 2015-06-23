/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author chad
 */
public class DataPoint {
    
    HashMap<String, Double> attributes = new HashMap<String, Double>();
    String targetName = null;
    String targetValue = null;
    
    public DataPoint() {
        super();
    }

    DataPoint(List<String> header, List<String> data, int targetIndex) throws NumberFormatException {
        super();
        Iterator<String> headIter = header.iterator();
        Iterator<String> dataIter = data.iterator();
        int index = 0;
        if(targetIndex < 0 || targetIndex > data.size()-1) {
            targetIndex = data.size()-1;
        }
        while(headIter.hasNext() && dataIter.hasNext()) {
            String key = headIter.next();
            String strValue = dataIter.next();
            try {
                // if this is not the targetIndex add to attributes
                if(index != targetIndex) {
                    // try to parse the double
                    Double value = Double.parseDouble(strValue);
                    this.put(key, value);
                } else {
                    // else set targetName to key and targetValue to value
                    this.setTarget(key, strValue);
                }
            } catch(NumberFormatException e) {
                System.err.print("DataPoint only allows numberic atributes, got " + strValue);
                throw e;
            }
            index++;
        }
    }

    public String getTargetName() {
        return this.targetName;
    }
    
    public String getTarget() {
        return this.targetValue;
    }

    public HashMap<String,Double> getAttributes() {
        return attributes;
    }

    public void put(String string, Double data) {
        this.attributes.put(string, data);
    }

    public void setTarget(String target, Double value) {
        if(target != null && value != null ) {
            this.setTarget(target, value+"");
        }
    }
    
    public void setTarget(String target, String value) {
        if(target != null && value != null ) {
            this.targetName = target;
            this.targetValue = value;
        }
    }
    
    /**
     * attempts to convert the target into a double and return it
     * @return the targetValue as a double
     * @throws NumberFormatException 
     */
    public Double getDoubleTarget() throws NumberFormatException {
        Double value = Double.parseDouble(targetValue);
        return value;
    }

    /**
     * checks to see if the target value is a numeric string
     * @return 
     */
    public boolean isTargetNumber() {
        boolean isNumber = true;
        try{
            Double.parseDouble(targetValue);
        } catch (Exception e) {
            isNumber = false;
        }
        return isNumber;
    }

}
