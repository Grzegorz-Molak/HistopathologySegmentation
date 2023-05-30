package Operations;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class representing threshold operation.
 */
public class Threshold implements Operation{
    /**
     * Border between black and white point
     * from 0 to 255
     */
    int threshold;

    /**
     * Class constructor specifying threshold
     * @param threshold border between white and black point
     */
    public Threshold(int threshold){
        this.threshold = threshold;
    }

    /**
     * Performs operation
     * @param image image to be processed
     * @return processed image
     */
    @Override
    public BufferedImage execute(BufferedImage image) {
        var dest=new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
        for (int x=0;x<image.getWidth();x++)
            for (int y=0;y<image.getHeight();y++)
            {
                Color color = new Color(image.getRGB(x,y));
                if(color.getRed() > threshold){
                    dest.setRGB(x,y, Color.BLACK.getRGB());
                }
                else{
                    dest.setRGB(x,y, Color.WHITE.getRGB());
                }
            }
        return dest;
    }

}
