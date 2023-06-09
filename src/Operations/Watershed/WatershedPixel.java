package Operations.Watershed;

import java.lang.*;
import java.util.*;


/**
* WatershedPixel class is used an adaption for the algorhitm to work efficiently and have all necessary information
*/
public class WatershedPixel implements Comparable {
    /** Value used to initialise the image */
    final static int INIT = -1;
    /** Value used to indicate the new pixels that
     *  are going to be processed (intial value
     *  at each level)
     */
    final static int MASK = -2;
    /** Value indicating that the pixel belongs
     *  to a watershed.
     */
    final static int WSHED = 0;
    /** 
    * Fictitious pixel 
    */
    final static int FICTITIOUS = -3;

    /** 
    * x coordinate of the pixel 
    */
    private int x;
    /**
    * y coordinate of the pixel 
    */
    private int y;
    /**
    * grayscale value of the pixel 
    */
    private int height;
    /**
    * Label used in the Watershed immersion algorithm 
    */
    private int label;
    /**
    * Distance used for working on pixels 
    */
    private int dist;

    /**
    * Neighbours 
    */
    private Vector neighbours;

    public WatershedPixel(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        label = INIT;
        dist = 0;
        neighbours = new Vector(8);
    }

    public WatershedPixel() {
        label = FICTITIOUS;
    }

    public void addNeighbour(WatershedPixel neighbour) {
        neighbours.add(neighbour);
    }

    public Vector getNeighbours() {
        return neighbours;
    }



    public String toString() {
        return new String("("+x+","+y+"), height : "+getIntHeight()+", label : "+label+", distance : "+dist);
    }

    public final int getIntHeight() {
        return (int) height&0xff;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public int compareTo(Object o) {
        if(!(o instanceof WatershedPixel))
            throw new ClassCastException();

        WatershedPixel obj =  (WatershedPixel) o;

        if( obj.getIntHeight() < getIntHeight() )
            return 1;

        if( obj.getIntHeight() > getIntHeight() )
            return -1;

        return 0;
    }

    public void setLabel(int label) {
        this.label = label;
    }


    public void setLabelToMASK() {
        label = MASK;
    }

    public void setLabelToWSHED() {
        label = WSHED;
    }



    public boolean isLabelMASK() {
        return label == MASK;
    }
    public boolean isLabelWSHED() {
        return label == WSHED;
    }

    public int getLabel() {
        return label;
    }

    public void setDistance(int distance) {
        dist = distance;
    }

    public int getDistance() {
        return dist;
    }

    public boolean isFICTITIOUS() {
        return label == FICTITIOUS;
    }

    public boolean allNeighboursAreWSHED() {
        for(int i=0 ; i<neighbours.size() ; i++) {
            WatershedPixel r = (WatershedPixel) neighbours.get(i);

            if( !r.isLabelWSHED() )
                return false;
        }
        return true;
    }
}
