package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Class represents a graphical block to set all settings on performed image.
 */
public class ParameterSetting extends JPanel {
    /**
     * Allows to choose between real time and on-demand processing
     */
    JCheckBox isRealTime;
    /**
     * Block of preprocessing
     */
    ParameterSection preprocessing;
    /**
     * Block of processing
     */
    ParameterSection processing;
    /**
     * Block of postprocessing
     */
    ParameterSection postprocessing;
    /**
     * Informs application to run segmentation on-demand.
     */
    JButton run;

    /**
     * Basic class constructor.
     * Sets itself layout and creates 3 blocks for stages of processing
     */
    public ParameterSetting(){
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        preprocessing = new ParameterSection("Preprocessing", 250, 175);
        processing = new ParameterSection("Processing", 250, 125);
        postprocessing = new ParameterSection("Postprocessing", 250, 175);
    }

    /**
     * Draws all elements on itself.
     */
    public void display(){
        JPanel realtime = new JPanel();
        isRealTime = new JCheckBox();
        realtime.add(new JLabel("Edycja w czasie rzeczywistym"));
        realtime.add(isRealTime);
        add(realtime);

        preprocessing.draw();
        processing.draw();
        processing.setAsProcessing();
        postprocessing.draw();
        add(preprocessing);
        add(processing);
        add(postprocessing);
        run = new JButton("Run");
        var runPanel = new JPanel();
        runPanel.add(run);
        add(runPanel);

        revalidate();
        repaint();
    }

    public void addOperation(int where,OperationView operation){
        if(where == 0){
            preprocessing.add(operation, preprocessing.getComponentCount()-1);
        }
        else if(where == 1) {
            operation.getOperation().remove(1);
            operation.revalidate();
            operation.repaint();
            processing.remove(2);
            processing.add(operation);
        }
        else{
            postprocessing.add(operation, postprocessing.getComponentCount()-1);
        }
        revalidate();
        repaint();
    }
    public ParameterSection getPreprocessing() {
        return preprocessing;
    }
    public ParameterSection getProcessing() {
        return processing;
    }

    public void setProcessing(ParameterSection processing) {
        this.processing = processing;
    }

    public ParameterSection getPostprocessing() {
        return postprocessing;
    }

    public void setPostprocessing(ParameterSection postprocessing) {
        this.postprocessing = postprocessing;
    }

    public JButton getRun() {
        return run;
    }

    public void setRun(JButton run) {
        this.run = run;
    }
    public JCheckBox getIsRealTime() {
        return isRealTime;
    }

    public void setIsRealTime(JCheckBox isRealTime) {
        this.isRealTime = isRealTime;
    }
}
