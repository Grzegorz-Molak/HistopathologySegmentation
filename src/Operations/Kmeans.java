package Operations;

import java.awt.image.BufferedImage;
import java.util.Arrays;
/**
* K-means algorithm implementation
*/
public class Kmeans implements Operation {

    Cluster[] clusters;
    int k;
    /**
    * Method contating K-means algorhitm implementation
    * @param image - input image
    */
    @Override
    public BufferedImage execute(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        clusters = createClusters(image,k);
        int[] lut = new int[w*h];
        Arrays.fill(lut, -1);
        boolean pixelChangedCluster = true;
        int loops = 0;
        while (pixelChangedCluster) {
            pixelChangedCluster = false;
            loops++;
            for (int y=0;y<h;y++) {
                for (int x=0;x<w;x++) {
                    int pixel = image.getRGB(x, y);
                    Cluster cluster = findMinimalCluster(pixel);
                    if (lut[w*y+x]!=cluster.getId()) {
                        pixelChangedCluster = true;
                        lut[w*y+x] = cluster.getId();
                    }
                }
            }
            for (int i=0;i<clusters.length;i++) {
                clusters[i].clear();
            }
            for (int y=0;y<h;y++) {
                for (int x=0;x<w;x++) {
                    int clusterId = lut[w*y+x];
                    clusters[clusterId].addPixel(
                            image.getRGB(x, y));
                }
            }
        }
        BufferedImage result = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_RGB);
        for (int y=0;y<h;y++) {
            for (int x=0;x<w;x++) {
                int clusterId = lut[w*y+x];
                result.setRGB(x, y, clusters[clusterId].getRGB());
            }
        }
        return result;
    }

    public Kmeans(int k ) {
        this.k  = k;
    }
    /**
    * Creates center and assigns pixels to clusters
    * @param image - input image
    */
    public BufferedImage calculate(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        clusters = createClusters(image,k);
        int[] lut = new int[w*h];
        Arrays.fill(lut, -1);
        boolean pixelChangedCluster = true;
        int loops = 0;
        while (pixelChangedCluster) {
            pixelChangedCluster = false;
            loops++;
            for (int y=0;y<h;y++) {
                for (int x=0;x<w;x++) {
                    int pixel = image.getRGB(x, y);
                    Cluster cluster = findMinimalCluster(pixel);
                }
            }
            for (int i=0;i<clusters.length;i++) {
                clusters[i].clear();
            }
            for (int y=0;y<h;y++) {
                for (int x=0;x<w;x++) {
                    int clusterId = lut[w*y+x];
                    clusters[clusterId].addPixel(
                            image.getRGB(x, y));
                }
            }
        }
        BufferedImage result = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_RGB);
        for (int y=0;y<h;y++) {
            for (int x=0;x<w;x++) {
                int clusterId = lut[w*y+x];
                result.setRGB(x, y, clusters[clusterId].getRGB());
            }
        }
        return result;
    }
    /**
    * Creates Clusters
    * @param image - input image
    * @param k - number of clusters/colors
    */
    public Cluster[] createClusters(BufferedImage image, int k) {
        Cluster[] result = new Cluster[k];
        int x = 0; int y = 0;
        int dx = image.getWidth()/k;
        int dy = image.getHeight()/k;
        for (int i=0;i<k;i++) {
            result[i] = new Cluster(i,image.getRGB(x, y));
            x+=dx; y+=dy;
        }
        return result;
    }
    /**
    * Finds closest cluster for a given RGB value
    * @param rgb - RGB color value
    */
    public Cluster findMinimalCluster(int rgb) {
        Cluster cluster = null;
        int min = Integer.MAX_VALUE;
        for (int i=0;i<clusters.length;i++) {
            int distance = clusters[i].distance(rgb);
            if (distance<min) {
                min = distance;
                cluster = clusters[i];
            }
        }
        return cluster;
    }
    class Cluster {
        int id;
        int pixelCount;
        int red;
        int green;
        int blue;
        int reds;
        int greens;
        int blues;

        public Cluster(int id, int rgb) {
            int r = rgb>>16&0x000000FF;
            int g = rgb>> 8&0x000000FF;
            int b = rgb>> 0&0x000000FF;
            red = r;
            green = g;
            blue = b;
            this.id = id;
            addPixel(rgb);
        }

        public void clear() {
            red = 0;
            green = 0;
            blue = 0;
            reds = 0;
            greens = 0;
            blues = 0;
            pixelCount = 0;
        }

        int getId() {
            return id;
        }

        int getRGB() {
            int r = reds / pixelCount;
            int g = greens / pixelCount;
            int b = blues / pixelCount;
            return 0xff000000|r<<16|g<<8|b;
        }
        void addPixel(int color) {
            int r = color>>16&0x000000FF;
            int g = color>> 8&0x000000FF;
            int b = color>> 0&0x000000FF;
            reds+=r;
            greens+=g;
            blues+=b;
            pixelCount++;
            red   = reds/pixelCount;
            green = greens/pixelCount;
            blue  = blues/pixelCount;
        }
        int distance(int color) {
            int r = color>>16&0x000000FF;
            int g = color>> 8&0x000000FF;
            int b = color>> 0&0x000000FF;
            int rx = Math.abs(red-r);
            int gx = Math.abs(green-g);
            int bx = Math.abs(blue-b);
            int d = (rx+gx+bx) / 3;
            return d;
        }
    }

}
