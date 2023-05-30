package Operations.Watershed;
import Operations.Operation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Watershed implements Operation {
    /**
    * Minimal topograchical height
    */
    final int HMIN = 0;
    /**
    * Maximum height, is adjusted using slider in the application
    */
    int HMAX;
    
    public Watershed( int HMAX){
        this.HMAX = HMAX;
    }
    /**
    * Main code containing Watershed algorhitm implementation
    * @param ip - input image
    */
    @Override
    public BufferedImage execute(BufferedImage ip) {

        WatershedStructure watershedStructure = new WatershedStructure(ip);


        WatershedFIFO queue = new WatershedFIFO();
        int curlab = 0;

        int heightIndex1 = 0;
        int heightIndex2 = 0;

        for(int h=HMIN; h<HMAX; h++){
            for(int pixelIndex = heightIndex1 ; pixelIndex<watershedStructure.size() ; pixelIndex++)  {
                WatershedPixel p = watershedStructure.get(pixelIndex);

                if(p.getIntHeight() != h) {
                    heightIndex1 = pixelIndex;
                    break;
                }

                p.setLabelToMASK();

                Vector neighbours = p.getNeighbours();
                for(int i=0 ; i<neighbours.size() ; i++) {
                    WatershedPixel q = (WatershedPixel) neighbours.get(i);

                    if(q.getLabel()>=0) {
                        p.setDistance(1);
                        queue.fifo_add(p);
                        break;
                    }
                }
            }


            int curdist = 1;
            queue.fifo_add_FICTITIOUS();

            while(true) {
                WatershedPixel p = queue.fifo_remove();

                if(p.isFICTITIOUS())
                    if(queue.fifo_empty())
                        break;
                    else {
                        queue.fifo_add_FICTITIOUS();
                        curdist++;
                        p = queue.fifo_remove();
                    }

                Vector neighbours = p.getNeighbours();
                for(int i=0 ; i<neighbours.size() ; i++) {
                    WatershedPixel q = (WatershedPixel) neighbours.get(i);



                    if( (q.getDistance() <= curdist) &&
                            (q.getLabel()>=0) ) {

                        if(q.getLabel() > 0) {
                            if( p.isLabelMASK() )
                                p.setLabel(q.getLabel());
                            else
                            if(p.getLabel() != q.getLabel())
                                p.setLabelToWSHED();
                        }
                        else
                        if(p.isLabelMASK())
                            p.setLabelToWSHED();
                    }
                    else
                    if( q.isLabelMASK() &&
                            (q.getDistance() == 0) ) {


                        q.setDistance( curdist+1 );
                        queue.fifo_add( q );
                    }
                }

            }


            for(int pixelIndex = heightIndex2 ; pixelIndex<watershedStructure.size() ; pixelIndex++) {
                WatershedPixel p = watershedStructure.get(pixelIndex);

                if(p.getIntHeight() != h) {
                    heightIndex2 = pixelIndex;
                    break;
                }

                p.setDistance(0);

                if(p.isLabelMASK()) {
                    curlab++;
                    p.setLabel(curlab);
                    queue.fifo_add(p);


                    while(!queue.fifo_empty()) {
                        WatershedPixel q = queue.fifo_remove();

                        Vector neighbours = q.getNeighbours();

                        for(int i=0 ; i<neighbours.size() ; i++){
                            WatershedPixel r = (WatershedPixel) neighbours.get(i);

                            if( r.isLabelMASK() ) {
                                r.setLabel(curlab);
                                queue.fifo_add(r);
                            }
                        }
                    }
                }
            }
        }
        int width = ip.getWidth();
        int[] newPixels = WatershedStructure.getPixels(ip);
        for (int x=0; x<newPixels.length; x++)
            if(newPixels[x]!=122)
                newPixels[x]=0;

        for(int pixelIndex = 0 ; pixelIndex<watershedStructure.size() ; pixelIndex++) {
            WatershedPixel p = watershedStructure.get(pixelIndex);
            
            if(p.isLabelWSHED() && !p.allNeighboursAreWSHED())
                newPixels[p.getX()+p.getY()*width] = Integer.MAX_VALUE;
        }
        BufferedImage outputImage = WatershedStructure.getResult(newPixels, ip.getWidth(), ip.getHeight());
        return outputImage;
    }
}
