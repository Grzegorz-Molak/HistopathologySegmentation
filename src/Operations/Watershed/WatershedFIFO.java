package Operations.Watershed;

import java.util.*;

/**
* FIFO queue implementation for Watershed algorhytm
*/
public class WatershedFIFO {
    
    private LinkedList watershedFIFO;
    
    public WatershedFIFO() {
        watershedFIFO = new LinkedList();
    }

    public void fifo_add(WatershedPixel p) {
        watershedFIFO.addFirst(p);
    }

    public WatershedPixel fifo_remove() {
        return (WatershedPixel) watershedFIFO.removeLast();
    }

    public boolean fifo_empty() {
        return watershedFIFO.isEmpty();
    }

    public void fifo_add_FICTITIOUS() {
        watershedFIFO.addFirst(new WatershedPixel());
    }

    public String toString() {
        StringBuffer ret = new StringBuffer();
        for(int i=0; i<watershedFIFO.size(); i++) {
            ret.append( ((WatershedPixel)watershedFIFO.get(i)).toString() );
            ret.append( "\n" );
        }

        return ret.toString();
    }
}
