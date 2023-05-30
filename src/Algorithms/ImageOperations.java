package Algorithms;

import Operations.Overlay;
import Operations.RegionGrowing;
import Operations.Threshold;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.Buffer;
import java.util.*;
import java.util.List;

/**
 * Class represents set of simple operations(without parameters) to be performed on image.
 * Also contains aliases of ALL operations available in application.
 */
public class ImageOperations{
    /**
     * The text representation of blur operation
     */
    public static final String Blur = "Blur";
    /**
     * The text representation of gaussian blur operation
     */
    public static final String GaussBlur = "Blur gaussowski";
    /**
     * The text representation of dilation operation
     */
    public static final String Dilation = "Dylatacja";
    /**
     * The text representation of K-means algorithm
     */
    public static final String Kmeans = "K-średnich";
    /**
     * The text representation of erosion operation
     */
    public static final String Erosion = "Erozja";
    /**
     * The text representation of transforming to grayscale operation
     */
    public static final String GrayScale = "Skala szarości";
    /**
     * The text representation of edge operation
     */
    public static final String Edge = "Krawędziowanie";
    /**
     * The text representation of overlay operation
     */
    public static final String Overlay = "Nakładanie";
    /**
     * The text representation of empty operation
     */
    public static final String Empty = "Puste";
    /**
     * The text representation of threshold operation
     */
    public static final String Threshold = "Progowanie";
    /**
     * The text representation of region growing algorithm
     */
    public static final String RegionGrowth = "Rozrost regionów";
       /**
     * The text representation of inverting color operation
     */
    public static final String Invert = "Odwrócenie";
    /**
     * The text representation of K-means parameter name
     */
    public static final String KmeansParameter = "K";
    /**
     * The text representation of Threshold parameter name
     */
    public static final String ThresholdParameter = "Próg";
    /**
     * The text representation of region growing parameter name
     */
    public static final String RegionParameter = "Względna różnica";
    /**
     * The text representation of wathershed algorithm
     */
    public static final String Watershed = "Wododziałowy";
    /**
     * The text representation of watershed parameter name
     */
    public static final String WatershedParameter = "Maksymalna wysokość";

    /**
     * Empty operation
     * @param image image to be processed
     * @return same image as input
     */
    public static BufferedImage doNothing(BufferedImage image){
        return image;
    }

    /**
     * Transforms image to grayscale
     * @param image image to be processed
     * @return grayscale image
     */
    public static BufferedImage grayScale(BufferedImage image) {
        var dest=new BufferedImage(image.getWidth(),image.getHeight(), image.getType());
        for (int x=0;x<image.getWidth();x++)
            for (int y=0;y<image.getHeight();y++)
            {
                Color color = new Color(image.getRGB(x,y));
                //int r = (int)(color.getRed()*0.299);
                //int g = (int)(color.getGreen()*0.587);
                //int b = (int)(color.getBlue()*0.114);
                int r = (int)(color.getRed()*0.333);
                int g = (int)(color.getGreen()*0.333);
                int b = (int)(color.getBlue()*0.333);
                Color res = new Color(r+g+b, r+g+b, r+g+b);
                dest.setRGB(x,y, res.getRGB());
            }
        return dest;
    }

    /**
     * Transforms image to inverted
     * @param image image to be processed
     * @return inverted image
     */
    public static BufferedImage invertBlack(BufferedImage image) {
        var dest=new BufferedImage(image.getWidth(),image.getHeight(), image.getType());
        for (int x=0;x<image.getWidth();x++)
            for (int y=0;y<image.getHeight();y++)
            {
                Color color = new Color(image.getRGB(x,y));
                //int r = (int)(color.getRed()*0.299);
                //int g = (int)(color.getGreen()*0.587);
                //int b = (int)(color.getBlue()*0.114);
                int r = (int)(255-color.getRed());
                int g = (int)(255-color.getGreen());
                int b = (int)(255-color.getBlue());
                Color res = new Color(r, r, r);
                dest.setRGB(x,y, res.getRGB());
            }
        return dest;
    }

    /**
     * Performs gaussian blur.
     * @param image image to be processed
     * @return gaussian blur image
     */
    public static BufferedImage blurGauss(BufferedImage image){
        float[] kernel = { 1.0f/16.0f, 2.0f/16.0f, 1.0f/16.0f,
                2.0f/16.0f, 4.0f/16.0f, 2.0f/16.0f,
                1.0f/16.0f, 2.0f/16.0f, 1.0f/16.0f
        };
        BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, kernel) );
        return op.filter( image, null );
    }

    /**
     * Performs blur
     * @param image image to be processeda
     * @return blur image
     */
    public static BufferedImage blur(BufferedImage image){
        float[] kernel = { 1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f,
                1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f,
                1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f
        };
        BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, kernel) );
        return op.filter( image, null );
    }

    /**
     * Detects edges on image.
     * Uses convolution
     * @param image image to be processed
     * @return image with edges detected
     */
    public static BufferedImage edge(BufferedImage image){
        float[] kernel = { 0.0f, -1.0f, 0.0f,
                -1.0f, 4.0f, -1.0f,
                0.0f, -1.0f, 0.0f
        };
        BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, kernel) );
        return op.filter( image, null );
    }

    /**
     * Perform erosion on image
     * @param image image to be processed
     * @return image with performed erosion
     */
    public static BufferedImage erosion(BufferedImage image){
        return morphological(image, 1);
    }

    /**
     * Performs dilation on image
     * @param image image to be processed
     * @return image with performed dilation
     */
    public static BufferedImage dilation(BufferedImage image){
        return morphological(image, 2);
    }

    /**
     * Performs morphological operation - dilation or erosion.
     * @param image image to be processed
     * @param type 1 - erosion, 2 - dilation
     * @return image with performed operation
     */
    public static BufferedImage morphological(BufferedImage image, int type){
        // 1 - erosion
        // 2 - dilation
        // #TODO ENUM
        var dest= new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
        for (int x=0;x<image.getWidth();x++)
            for (int y=0;y<image.getHeight();y++)
            {
                List<Integer> neighbourhood = new ArrayList<>();
                if(x > 0) neighbourhood.add(new Color(image.getRGB(x-1,y)).getRed());
                if(x < image.getWidth()-1) neighbourhood.add(new Color(image.getRGB(x+1,y)).getRed());
                if(y > 0) neighbourhood.add(new Color(image.getRGB(x,y-1)).getRed());
                if(y < image.getHeight()-1) neighbourhood.add(new Color(image.getRGB(x,y+1)).getRed());

                int value;
                if(type == 2)
                    value = Collections.max(neighbourhood);
                else
                    value = Collections.min(neighbourhood);

                dest.setRGB(x,y, new Color(value,value,value).getRGB());
            }
        return dest;
    }
}
