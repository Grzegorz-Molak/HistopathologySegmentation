package Operations.Watershed;

import java.awt.image.BufferedImage;
import java.lang.*;
import java.util.*;
import java.awt.*;


/**
 *  WatershedStructure contains the pixels
 *  of the image sorted by their grayscale value with an access to their neighbours.
 */
public class WatershedStructure {

    private Vector watershedStructure;

    public static int[] getPixels(BufferedImage ip){
        int[] result = new int[ip.getWidth()*ip.getHeight()];
        for (int y =0; y<ip.getHeight();y++){
            for (int x=0;x<ip.getWidth();x++){
                result[x+y*ip.getHeight()] = (int) ip.getRGB(x,y);
            }
        }
        return result;
    }

    public static BufferedImage getResult(int[] pixels, int width, int height){
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y =0; y<height;y++){
            for (int x=0;x<width;x++){
                result.setRGB(x,y, (int) pixels[x+y*height]);
            }
        }
        return result;
    }
    /**
    * Creates a Vector with the pixels
    * of the image sorted by their grayscale value with an access to their neighbours
    * @param ip - input image
    */
    public WatershedStructure(BufferedImage ip) {
        int[] pixels = getPixels(ip);
        Rectangle r = new Rectangle(ip.getWidth(), ip.getHeight());
        int width = ip.getWidth();
        int offset, topOffset, bottomOffset, i;

        watershedStructure = new Vector(r.width*r.height);

        for (int y=r.y; y<(r.y+r.height); y++) {
            offset = y*width;


            for (int x=r.x; x<(r.x+r.width); x++) {
                i = offset + x;

                int indiceY = y-r.y;
                int indiceX = x-r.x;

                watershedStructure.add(new WatershedPixel(indiceX, indiceY, pixels[i]));
            }
        }

        for (int y=0; y<r.height; y++) {

            offset = y*width;
            topOffset = offset+width;
            bottomOffset = offset-width;


            for (int x=0; x<r.width; x++) {
                WatershedPixel currentPixel = (WatershedPixel)watershedStructure.get(x+offset);

                if(x+1<r.width) {
                    currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x+1+offset));

                    if(y-1>=0)
                        currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x+1+bottomOffset));

                    if(y+1<r.height)
                        currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x+1+topOffset));
                }

                if(x-1>=0) {
                    currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x-1+offset));

                    if(y-1>=0)
                        currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x-1+bottomOffset));

                    if(y+1<r.height)
                        currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x-1+topOffset));
                }

                if(y-1>=0)
                    currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x+bottomOffset));

                if(y+1<r.height)
                    currentPixel.addNeighbour((WatershedPixel)watershedStructure.get(x+topOffset));
            }
        }

        Collections.sort(watershedStructure);
    }

    public String toString() {
        StringBuffer ret = new StringBuffer();

        for(int i=0; i<watershedStructure.size() ; i++) {
            ret.append( ((WatershedPixel) watershedStructure.get(i)).toString() );
            ret.append( "\n" );
            ret.append( "Neighbours :\n" );

            Vector neighbours = ((WatershedPixel) watershedStructure.get(i)).getNeighbours();

            for(int j=0 ; j<neighbours.size() ; j++) {
                ret.append( ((WatershedPixel) neighbours.get(j)).toString() );
                ret.append( "\n" );
            }
            ret.append( "\n" );
        }
        return ret.toString();
    }

    public int size() {
        return watershedStructure.size();
    }

    public WatershedPixel get(int i) {
        return (WatershedPixel) watershedStructure.get(i);
    }
}
