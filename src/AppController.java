import Algorithms.ImageOperations;
import Operations.*;
import Operations.Watershed.Watershed;
import View.App;
import View.OperationView;
import View.ParameterSetting;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class that runs and controls application
 */
public class AppController implements ActionListener {
    /**
     * Image the application is working on.
     * Is being chosen by user via File manager.
     */
    BufferedImage imageSource;
    /**
     * Result of user's operations.
     * If no operations have been performed, it is a copy of source image.
     */
    BufferedImage imageResult;
    /**
     * Contains all GUI
     */
    App app;
    /**
     * Performs all the operations on sourceImage
     */
    Segmentation segmentation;

    /**
     * Base Constructor.
     * Sets GUI ready and runs it, sets workspace images to empty.
     */
    public AppController(){
        app = new App();
        readImage(null);
        setImageIcons();
        setParameterSetting();
        setFileChooser();
        setMenu();
        app.createAndShowGUI();
        segmentation = new Segmentation(imageSource);

    }

    /**
     * Reads image froms specific file.
     * If null, sets white rectangles as images. It happens at beginning of the program, before user has selected any file.
     * @param file - path to the image
     */
    public void readImage(File file) {
        try {
            final int WIDTH = 600;
            final int HEIGHT = 600;
            if(file == null) {
                imageSource = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                imageResult = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                Graphics2D original = imageSource.createGraphics();
                Graphics2D destination = imageResult.createGraphics();
                original.setColor(Color.WHITE);
                original.fillRect(0,0,WIDTH,HEIGHT);
                destination.setColor(Color.WHITE);
                destination.fillRect(0,0,WIDTH,HEIGHT);
            }
            else {
                Image source = ImageIO.read(file).getScaledInstance(WIDTH, HEIGHT, BufferedImage.SCALE_SMOOTH);
                Image result = ImageIO.read(file).getScaledInstance(WIDTH, HEIGHT, BufferedImage.SCALE_SMOOTH);
                imageSource = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                imageResult = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                Graphics2D original = imageSource.createGraphics();
                Graphics2D destination = imageResult.createGraphics();
                original.drawImage(source, 0, 0, null);
                original.dispose();
                destination.drawImage(result, 0, 0, null);
                destination.dispose();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Sets images to be displayed by GUI.
     */
    public void setImageIcons(){
        app.setSourceIcon(new JLabel(new ImageIcon(imageSource)));
        app.setResultIcon(new JLabel(new ImageIcon(imageResult)));
    }

    /**
     * Updates result in GUI class to be displayed.
     */
    public void updateResult(){
        app.setResultIcon(new JLabel(new ImageIcon(imageResult)));
        app.updateResult();
    }

    /**
     * Initializes setting section of GUI.
     * Sets available operations of every section, adds action listeners, runs GUI.
     */
    public void setParameterSetting(){
        app.setSettingPanel(new ParameterSetting());

        //Operations available to user
        var preOperations = new String[]{ImageOperations.GrayScale, ImageOperations.Blur, ImageOperations.GaussBlur,
                ImageOperations.Dilation, ImageOperations.Erosion};
        var operations = new String[]{ImageOperations.Empty, ImageOperations.Threshold, ImageOperations.RegionGrowth, ImageOperations.Kmeans, ImageOperations.Watershed};
        var postOperations = new String[]{ImageOperations.GrayScale, ImageOperations.Blur, ImageOperations.GaussBlur,
                ImageOperations.Dilation, ImageOperations.Erosion,
                ImageOperations.Threshold, ImageOperations.Edge, ImageOperations.Overlay, ImageOperations.Invert};
        app.getSettingPanel().getPreprocessing().setOperationOptions(preOperations);
        app.getSettingPanel().getProcessing().setOperationOptions(operations);
        app.getSettingPanel().getPostprocessing().setOperationOptions(postOperations);

        app.getSettingPanel().display();

        //Add action listeners
        app.getSettingPanel().getIsRealTime().addActionListener(e -> run());
        app.getSettingPanel().getPreprocessing().getAddOperationButton().addActionListener(this);
        app.getSettingPanel().getProcessing().getChooseOperation().addActionListener(this);
        app.getSettingPanel().getPostprocessing().getAddOperationButton().addActionListener(this);
        app.getSettingPanel().getRun().addActionListener(this);

    }

    /**
     * Sets file chooser to start in app folder and to choose files only.
     */
    public void setFileChooser(){
        app.setFileChooser(new JFileChooser(System.getProperty("user.dir")));
        app.getFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    /**
     * Initializes menu.
     */
    public void setMenu(){
        app.setMenuBar(new JMenuBar());
        app.setMenu(new JMenu("Plik"));

        app.setOpenFile(new JMenuItem("Otwórz plik"));
        app.getOpenFile().addActionListener(e -> readFile());
        app.getMenu().add(app.getOpenFile());

        app.setSaveFile(new JMenuItem("Zapisz plik"));
        app.getSaveFile().addActionListener(e -> saveFile());
        app.getMenu().add(app.getSaveFile());
        app.getAppMenuBar().add(app.getMenu());
        app.setJMenuBar(app.getAppMenuBar());
    }

    /**
     * Runs file chooser to get image.
     */
    public void readFile(){
            int returnVal = app.getFileChooser().showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File file = app.getFileChooser().getSelectedFile();
                readImage(file);
                setImageIcons();
                app.updateImagesPanel();
                run();
            }
    }

    /**
     * Saves result image in file system.
     */
    public void saveFile(){
        app.getFileChooser().setDialogTitle("Wybierz gdzie zapisać plik");

        int userSelection = app.getFileChooser().showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = app.getFileChooser().getSelectedFile();
            try {
                ImageIO.write(imageResult, "png", new File(fileToSave.getAbsolutePath()));
            }
            catch (IOException e){
                System.out.println(e);
            }
        }
    }

    /**
     * Performs segmentation, updates visible results on the screen.
     */
    public void run(){
        resetSegmentation();
        segmentation.performSegmentation();
        imageResult = segmentation.getResultImage();
        updateResult();
    }

    /**
     * Reacts to user actions on the graphical interface.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj == app.getSettingPanel().getPreprocessing().getAddOperationButton()) {
            String name = (String) app.getSettingPanel().getPreprocessing().getChooseOperation().getSelectedItem();
            if(name != null) {
                var operationView = getOperationViewByName(name);
                operationView.getDelete().addActionListener(
                        elem -> removeOperation(1, operationView));
                app.getSettingPanel().getPreprocessing().addOperation(operationView);
            }
        }
        else if(obj == app.getSettingPanel().getProcessing().getChooseOperation()){
            String name = (String) app.getSettingPanel().getProcessing().getChooseOperation().getSelectedItem();
            if(name != null) {
                var operationView = getOperationViewByName(name);
                app.getSettingPanel().getProcessing().setOperation(operationView);
            }
        }
        else if(obj == app.getSettingPanel().getPostprocessing().getAddOperationButton()){
            String name = (String) app.getSettingPanel().getPostprocessing().getChooseOperation().getSelectedItem();
            if(name != null){
                var operationView = getOperationViewByName(name);
                operationView.getDelete().addActionListener(
                        elem -> removeOperation(3, operationView));
                app.getSettingPanel().getPostprocessing().addOperation(operationView);
            }
        }
        else if(obj == app.getSettingPanel().getRun()){
            run();
        }

        if(app.getSettingPanel().getIsRealTime().isSelected() && obj != app.getSettingPanel().getRun()) run();
    }

    /**
     * Gets information about operation stored in graphical operation block
     * @param view graphical block that contains information about stored operation
     * @return An operation to be performed
     */
    public Operation getOperationByView(OperationView view){
        switch(view.getOperationName()){
            case ImageOperations.Blur ->{
                return ImageOperations::blur;
            }
            case ImageOperations.GaussBlur ->{
                return ImageOperations::blurGauss;
            }
            case ImageOperations.Dilation ->{
                return ImageOperations::dilation;
            }
            case ImageOperations.Erosion ->{
                return ImageOperations::erosion;
            }
            case ImageOperations.GrayScale ->{
                return ImageOperations::grayScale;
            }
            case ImageOperations.Edge ->{
                return ImageOperations::edge;
            }
            case ImageOperations.Empty ->{
                return ImageOperations::doNothing;
            }
            case ImageOperations.Threshold ->{
                return new Threshold(view.getParamSet().getValue());
            }
            case ImageOperations.RegionGrowth ->{
                return new RegionGrowing(view.getParamSet().getValue());
            }
            case ImageOperations.Kmeans -> {
                return new Kmeans(view.getParamSet().getValue());
            }
            case ImageOperations.Watershed -> {
                return new Watershed(view.getParamSet().getValue());
            }
            case ImageOperations.Overlay ->{
                return new Overlay(imageSource);
            }
            case ImageOperations.Invert ->{
                return ImageOperations::invertBlack;
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Creates a graphical operation block to be displayed depending on its name.
     * @param name name of the operation
     * @return graphical operation block of specific operation
     */
    public OperationView getOperationViewByName(String name){
        if(!name.equals(ImageOperations.Threshold) && !name.equals(ImageOperations.RegionGrowth) && !name.equals((ImageOperations.Kmeans)) && !name.equals((ImageOperations.Watershed))){
            return new OperationView(name);
        }
        else{
            app.getSettingPanel().getIsRealTime().setEnabled(true);
            var result = new OperationView(name);
            if(name.equals(ImageOperations.Threshold)){
                result.setParamName(ImageOperations.ThresholdParameter);
                result.setParamSet(createSlider(0,255,50,5));
            }
            else if(name.equals(ImageOperations.RegionGrowth)){
                result.setParamName(ImageOperations.RegionParameter);
                result.setParamSet(createSlider(0,255,50,5));
            }
            else if (name.equals((ImageOperations.Kmeans))){
                result.setParamName(ImageOperations.KmeansParameter);
                result.setParamSet(createSlider(1,7,2,1));
                app.getSettingPanel().getIsRealTime().setSelected(false);
                app.getSettingPanel().getIsRealTime().setEnabled(false);

            }
            else if (name.equals(ImageOperations.Watershed)){
                result.setParamName(ImageOperations.WatershedParameter);
                result.setParamSet(createSlider(150, 300, 20, 5));
            }
            return result;
        }
    }

    /**
     * Creates a JSlider with parameters.
     * @param min minimum value
     * @param max maximum value
     * @param major big tick spacing
     * @param minor minor tick spacing
     * @return JSlider with chosen parameters
     */
    public JSlider createSlider(int min, int max, int major, int minor){
        var slider = new JSlider(min, max);
        slider.setMajorTickSpacing(major);
        slider.setMinorTickSpacing(minor);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(elem -> {
            if(app.getSettingPanel().getIsRealTime().isSelected()) run();
        });
        return slider;
    }

    /**
     * Resets segmentation class so it can perform another segmentation.
     * Clears all segmentation class fields and fills with present(actual) operations chosen by user.
     */
    private void resetSegmentation(){
        segmentation.getPreprocessing().clear();
        segmentation.getProcessing().clear();
        segmentation.getPostprocessing().clear();
        for(var comp : app.getSettingPanel().getPreprocessing().getContent().getComponents()){
            if(comp instanceof OperationView){
                segmentation.getPreprocessing().add(getOperationByView((OperationView) comp));
            }
        }
        for(var comp : app.getSettingPanel().getProcessing().getContent().getComponents()){
            if(comp instanceof OperationView){
                segmentation.getProcessing().add(getOperationByView((OperationView) comp));
            }
        }
        for(var comp : app.getSettingPanel().getPostprocessing().getContent().getComponents()){
            if(comp instanceof OperationView){
                segmentation.getPostprocessing().add(getOperationByView((OperationView) comp));
            }
        }
        segmentation.setSourceImage(imageSource);
    }

    /**
     * Removes operation( [-] clicked).
     * If live segmentation is on, refreshes image.
     * @param where Preprocessing panel(1) or Postprocessing panel(else)
     * @param operation - graphical operation block to be removed.
     */
    private void removeOperation(int where, OperationView operation){
        if(where == 1) app.getSettingPanel().getPreprocessing().getContent().remove(operation);
        else app.getSettingPanel().getPostprocessing().getContent().remove(operation);
        app.refresh();
        if(app.getSettingPanel().getIsRealTime().isSelected()) run();
    }


}
