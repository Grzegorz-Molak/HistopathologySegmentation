package View;

import Algorithms.ImageOperations;
import Operations.Threshold;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

/**
 * Class representing application GUI
 */
public class App extends JFrame {
    /**
     * Panel to store source and result image
     */
    JPanel images;
    /**
     * Left panel to set the segmentation process
     */
    ParameterSetting settingPanel;
    /**
     * Source image
     * Swing doesn't work with BufferedImage but rather with JLabel with JImageIcon
     */
    JLabel sourceIcon;
    /**
     * Result image
     * Swing doesn't work with BufferedImage but rather with JLabel with JImageIcon
     */
    JLabel resultIcon;
    /**
     * For selecting and saving image
     */
    JFileChooser fileChooser;
    /**
     * Menu bar, on top
     */
    JMenuBar menuBar;
    /**
     * Menu element
     */
    JMenu menu;
    /**
     * Menu element to open new image
     */
    JMenuItem openFile;
    /**
     * Menu element to save result image
     */
    JMenuItem saveFile;

    /**
     * Initializes GUI.
     * Sets window properties, sets Panels where they should be
     */
    public void createAndShowGUI() {
        setLayout(new BorderLayout());
        setTitle("Segmentacja");
        createImagesPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(images, BorderLayout.CENTER);
        images.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(settingPanel, BorderLayout.WEST);
        pack();
        setVisible(true);
    }

    /**
     * Refreshed view
     */
    public void refresh(){
        revalidate();
        repaint();
    }

    /**
     * Updates result image after performed segmentation
     */
    public void updateResult(){
        images.remove(1);
        images.add(resultIcon);
        revalidate();
        repaint();
    }

    /**
     * Initializes central panel with images
     */
    public void createImagesPanel(){
        images = new JPanel();
        images.setLayout(new GridLayout(0,2));
        if(sourceIcon != null) {
            images.add(sourceIcon);
            images.add(resultIcon);
        }
    }

    /**
     * Updates central panel with images after new image is selected
     */
    public void updateImagesPanel(){
        images.removeAll();
        images.add(sourceIcon);
        images.add(resultIcon);
        revalidate();
        repaint();
    }

    public JLabel getSourceIcon() {
        return sourceIcon;
    }

    public void setSourceIcon(JLabel sourceIcon) {
        this.sourceIcon = sourceIcon;
    }

    public JLabel getResultIcon() {
        return resultIcon;
    }

    public void setResultIcon(JLabel resultIcon) {
        this.resultIcon = resultIcon;
    }

    public JPanel getImages() {
        return images;
    }

    public void setImages(JPanel images) {
        this.images = images;
    }

    public ParameterSetting getSettingPanel() {
        return settingPanel;
    }

    public void setSettingPanel(ParameterSetting settingPanel) {
        this.settingPanel = settingPanel;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public void setFileChooser(JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public JMenuBar getAppMenuBar() {
        return menuBar;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public JMenu getMenu() {
        return menu;
    }

    public void setMenu(JMenu menu) {
        this.menu = menu;
    }

    public JMenuItem getOpenFile() {
        return openFile;
    }

    public void setOpenFile(JMenuItem openFile) {
        this.openFile = openFile;
    }

    public JMenuItem getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(JMenuItem saveFile) {
        this.saveFile = saveFile;
    }
}

