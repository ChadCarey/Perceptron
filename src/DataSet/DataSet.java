/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author chad
 */
public class DataSet extends ArrayList<DataPoint> {

    public DataSet(String filename, int targetIndex) throws IOException {
        super();
        
        File file = new File(filename);
        BufferedReader reader = null;
        if(file.exists()) {
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException ex) {
                System.err.println("Failed to create BufferedReader");
                System.exit(2);
            }
        }
        if(reader != null) {
            // get the header
            List<String> header = this.explode(reader.readLine(), ",");
            // read in the data
            String data = null;
            while((data = reader.readLine()) != null) {
                if(!data.isEmpty()) {
                    List<String> dataPoints = this.explode(data, ",");
                    this.add(new DataPoint(header, dataPoints, targetIndex));
                }
            }
            reader.close();
        }
    }

    private DataSet() {
        super();
    }

    /**
     * removes the first p percent of this DataSet and returns the remainder
     * @param p
     * @return 
     */
    public DataSet removePercent(double p) {
        // ensure that 70.0 is calculated as 0.7
        while(p > 1.0) {
            p = p*0.1;
        }
        // get the number of items to remove
        this.randomize();
        int removeCount = (int)(this.size() * p);
        
        DataSet newSet = new DataSet();
        DataSet replacementSet = new DataSet();
        for(int i = 0; i < removeCount; ++i) {
            // first p percent goes into the newSet
            newSet.add(this.get(i));
        }
        
        // load the rest of the items into the replacmentSet
        for(int i = removeCount; i < this.size(); ++i) {
            // the other 100-p percent goes into the replacementSet
            replacementSet.add(this.get(i));
        }
        
        // now clear this set and replace it with the replacementSet
        this.clear();
        this.addAll(replacementSet);
        
        return newSet;
    }
    
    /**
     * 
     */
    public DataSet cleanPercent(double p) {
        // if the target values are doubles call the regular remove percent
        if(this.getOne().isTargetNumber()) {
            return this.removePercent(p);
        } else { 
            // else remove p percent of each target value and add it to newSet
            DataSet newSet = new DataSet();
            // add the 1-p percent to the replacment set
            DataSet replacementSet = new DataSet();
            while(p > 1.0) {
                p = p*0.1;
            }
            Set<String> targetValues = this.getTargetValues();
            Iterator <String> iter = targetValues.iterator();
            while(iter.hasNext()) {
                String targetVal = iter.next();
                DataSet s = this.getTargetWhere(targetVal);
                newSet.addAll(s.removePercent(p));
                replacementSet.addAll(s);
            }
            // replace the current set
            this.clear();
            this.addAll(replacementSet);
            return newSet;
        }
    }

    /**
     * 
     * @return 
     */
    public Set<String> getTargetValues() {
        Iterator<DataPoint> iter = this.iterator();
        Set<String> set = new HashSet<String>();
        while(iter.hasNext()) {
            set.add(new String(iter.next().targetValue));
        }
        return set;
    }

    public DataPoint getOne() {
        if(this.size() > 0)
            return this.get(0);
        else
            return null;
    }
    
    /**
     * splits the string into pieces and puts the parts into an arrayList
     * @param str
     * @param regex
     * @return 
     */
    private ArrayList<String> explode(String str, String regex) {
        ArrayList<String> output = new ArrayList<String>();
        String[] data = str.split(regex);
        for(int i = 0; i < data.length; ++i) {
            output.add(data[i]);
        }
        return output;
    }

    /**
     * 
     */
    public void randomize() {
        for(int i = 0; i < this.size(); ++i) {
            int replaceIndex = (int) (Math.random()*this.size());
            DataPoint p = this.get(i);
            this.set(i, this.get(replaceIndex));
            this.set(replaceIndex, p);
        }
    }

    /**
     * returns a DataSet where the target value equals the given string
     * @param targetVal
     * @return 
     */
    public DataSet getTargetWhere(String targetVal) {
        DataSet set = new DataSet();
        Iterator<DataPoint> iter = this.iterator();
        while(iter.hasNext()){
            DataPoint p = iter.next();
            if(p.getTarget().equals(targetVal))
                set.add(p);
        }
        return set;
    }
    
}
