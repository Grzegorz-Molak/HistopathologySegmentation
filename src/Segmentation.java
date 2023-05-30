import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import Operations.*;

/**
 * Class representing the whole segmentation process.
 * It allows to load list of needed operations into it, load source image and perform loaded operations to return result image.
 */
public class Segmentation {
    /**
     * Image to be segmented.
     */
    BufferedImage sourceImage;
    /**
     * Segmented Image.
     */
    BufferedImage resultImage;
    /**
     * Operations to be performed before segmentation algorithm.
     */
    List<Operation> preprocessing;
    /**
     * Segmentation algorithm.
     */
    List<Operation> processing;
    /**
     * Operation to be performed after segmentation algorithm.
     */
    List<Operation> postprocessing;

    /**
     * Class constructor specifying source image.
     * @param sourceImage - image to be processed
     */
    public Segmentation(BufferedImage sourceImage){
        this.sourceImage = sourceImage;
        this.resultImage = sourceImage;
        preprocessing = new ArrayList<>();
        processing = new ArrayList<>();
        postprocessing = new ArrayList<>();
    }

    /**
     * Runs all saved operations on source image.
     * Operations save the results in resultImage field.
     */
    public void performSegmentation(){
        resultImage = copyImage(sourceImage);
        for(var operation : preprocessing){
            resultImage = operation.execute(resultImage);
        }
        for(var operation : processing){
            resultImage = operation.execute(resultImage);
        }
        for(var operation : postprocessing){
            resultImage = operation.execute(resultImage);
        }
    }

    /**
     * Makes a deep copy of image
     * @param source image to be copied
     * @return copied image
     */
    public BufferedImage copyImage(BufferedImage source){
        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = result.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return result;
    }

    public void addOperation(Operation operation, int where){
        if(where == 0) preprocessing.add(operation);
        if(where == 1) processing.add(operation);
        if(where == 2) postprocessing.add(operation);
    }

    public BufferedImage overlayImages(){
        var dest=new BufferedImage(sourceImage.getWidth(),sourceImage.getHeight(),sourceImage.getType());
        for (int x=0;x<sourceImage.getWidth();x++)
            for (int y=0;y<sourceImage.getHeight();y++)
            {
                Color colorMask = new Color(resultImage.getRGB(x,y));
                if(colorMask.getRed() == 255){
                    dest.setRGB(x,y, new Color(255,0,0).getRGB());
                }
                else{
                    dest.setRGB(x,y,sourceImage.getRGB(x,y));
                }

            }
        return dest;
    }

    public List<Operation> getPreprocessing() {
        return preprocessing;
    }

    public void setPreprocessing(List<Operation> preprocessing) {
        this.preprocessing = preprocessing;
    }

    public List<Operation> getProcessing() {
        return processing;
    }

    public void setProcessing(List<Operation> processing) {
        this.processing = processing;
    }

    public List<Operation> getPostprocessing() {
        return postprocessing;
    }

    public void setPostprocessing(List<Operation> postprocessing) {
        this.postprocessing = postprocessing;
    }

    public BufferedImage getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(BufferedImage sourceImage) {
        this.sourceImage = sourceImage;
    }

    public BufferedImage getResultImage(){
        return resultImage;
    }

    public void setResultImage(BufferedImage resultImage) {
        this.resultImage = resultImage;
    }
}
