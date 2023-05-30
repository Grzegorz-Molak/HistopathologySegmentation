package Operations;

import java.awt.image.BufferedImage;

/**
 * Interface representing single image operation.
 * Can be used as functional interface as it contains only one abstract function.
 */
public interface Operation {
    /**
     * Performs operation on the image.
     * @param image image to be processed
     * @return processed image
     */
    BufferedImage execute(BufferedImage image);
}
