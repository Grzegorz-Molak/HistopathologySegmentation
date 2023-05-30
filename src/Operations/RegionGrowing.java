package Operations;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Class representing region growing algorithm.
 */
public class RegionGrowing implements Operation {
    /**
     * Class representing Point
     */
    class Point{
        /**
         * [x] coordinate
         */
        int x;
        /**
         * [y] coordinate
         */
        int y;

        /**
         * Constructor specifying x and y
         * @param x [x] coordinate
         * @param y [y] coordinate
         */
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Macro for visited point
     */
    final boolean VISITED = true;
    /**
     * Macro for unvisited point
     */
    final boolean NOT_VISITED = false;
    /**
     * Maximum local threshold.
     * Maximum difference between starting point and investigated point for point to be included in region.
     */
    int threshold;
    /**
     * Source image
     */
    BufferedImage image;
    /**
     * Result image
     */
    BufferedImage dest;
    /**
     * Array containing information if pixel was already visited
     */
    boolean[] visited;
    /**
     * Candidate points to the region
     */
    Deque<Point> candidates;
    /**
     * Color of starting point of the region
     */
    Color regionColor;

    /**
     * Class constructor specifying maximum local threshold
     * @param threshold - maximum local threshold
     */
    public RegionGrowing(int threshold){
        this.threshold = threshold;
    }

    /**
     * Performs segmentation
     * @param image image to be processed
     * @return processed image
     */
    @Override
    public BufferedImage execute(BufferedImage image) {
        this.image = image;
        dest= new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
        candidates = new ArrayDeque<>();
        //candidates.clear();
        visited = new boolean[image.getHeight()*image.getWidth()];
        Arrays.fill(visited, NOT_VISITED);

        for(int x = 0; x < image.getWidth(); x++){
            for(int y = 0; y < image.getHeight(); y++){
                if(visited[image.getWidth()*y+x] == NOT_VISITED){
                    regionColor = new Color(image.getRGB(x,y));
                    candidates.add(new Point(x,y));
                    grow();
                }
            }
        }
        return dest;
    }

    /**
     * Checks if pixel belongs to region and if so, adds neighbourhood to list of candidates
     */
    public void grow(){
        while(!candidates.isEmpty()){
            Point point = candidates.pollFirst();
            int x = point.x;
            int y = point.y;
            if(x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight())
                continue;
            if(visited[image.getWidth()*y+x] == VISITED)
                continue;
            visited[image.getWidth()*y+x] = VISITED;
            Color pointColor = new Color(image.getRGB(x,y));
            if(Math.abs(regionColor.getRed() - pointColor.getRed()) <= threshold){
                dest.setRGB(x,y, regionColor.getRGB());
                candidates.add(new Point(x-1,y-1));
                candidates.add(new Point(x  ,y-1));
                candidates.add(new Point(x+1,y-1));
                candidates.add(new Point(x-1,y));
                candidates.add(new Point(x+1,y));
                candidates.add(new Point(x-1,y+1));
                candidates.add(new Point(x  ,y+1));
                candidates.add(new Point(x+1,y+1));
                }
            }
        }
}


