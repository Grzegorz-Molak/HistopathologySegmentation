package Operations;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represent operation to overlay two images.
 */
public class Overlay implements Operation{
    /**
     * Source image
     */
    BufferedImage toBeOverlayed;

    /**
     * Class constructor specifying source image.
     * @param toBeOverlayed source image
     */
    public Overlay(BufferedImage toBeOverlayed){
        this.toBeOverlayed = toBeOverlayed;
    }

    /**
     * Performs overlaying of the image with given mask.
     * Mask has to be a binary image. Source image pixels where mask is white will be changed to red, otherwise will not be changed.
     * @param image image to be processed
     * @return image with red mask representing edges(potentially)
     */
    @Override
    public BufferedImage execute(BufferedImage image) {
        var dest=new BufferedImage(toBeOverlayed.getWidth(),toBeOverlayed.getHeight(),toBeOverlayed.getType());
        for (int x=0;x<toBeOverlayed.getWidth();x++)
            for (int y=0;y<toBeOverlayed.getHeight();y++)
            {
                Color colorMask = new Color(image.getRGB(x,y));
                if(colorMask.getRed() == 255){
                    dest.setRGB(x,y, new Color(255,0,0).getRGB());
                }
                else{
                    dest.setRGB(x,y,toBeOverlayed.getRGB(x,y));
                }

            }
        return dest;
    }
}
