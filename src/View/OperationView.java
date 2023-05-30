package View;

import Operations.Operation;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Class representing Graphical operation block.
 * It contains of operation name, delete button and possibly parameter name and slider.
 */
public class OperationView extends JPanel {
    /**
     * Holds operation name and delete button
     */
    JPanel operation;
    /**
     * Operation name
     */
    String operationName;
    /**
     * To delete operation from list
     */
    JButton delete;
    /**
     * Hold parameter name and slider
     */
    JPanel parameter;
    /**
     * Parameter name
     */
    String paramName;
    /**
     * Slider with settings of operation
     */
    JSlider paramSet;

    /**
     * Class constructor consisting of name of the operation.
     * @param operationName name of the operation
     */
    public OperationView(String operationName){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        operation = new JPanel();
        operation.setLayout(new FlowLayout());

        parameter = new JPanel();
        parameter.setLayout(new BoxLayout(parameter, BoxLayout.Y_AXIS));
        this.operationName =operationName;
        setBorder(new CompoundBorder(
                new EmptyBorder(5,5,5,5), BorderFactory.createRaisedBevelBorder()));
        delete = new JButton("-");

    }

    /**
     * Draws all elements in themselves and itself(JPanels) in good order
     */
    public void draw(){
        operation.setBorder(new EmptyBorder(5,5,5,5));
        operation.add(new JLabel(operationName, SwingConstants.CENTER));
        operation.add(delete);
        add(operation);
        if(paramName != null){
            parameter.add(new JLabel(paramName));

            parameter.add(paramSet);
            add(parameter);
        }
    }

    /**
     * Leaves only parameter block.
     * Needed at processing block, where name of operation is stored differently
     */
    public void leaveOnlyParameter(){
        remove(0);
    }
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public JSlider getParamSet() {
        return paramSet;
    }

    public void setParamSet(JSlider paramSet) {
        this.paramSet = paramSet;
    }

    public JButton getDelete() {
        return delete;
    }

    public void setDelete(JButton delete) {
        this.delete = delete;
    }

    public JPanel getOperation() {
        return operation;
    }

    public void setOperation(JPanel operation) {
        this.operation = operation;
    }

    public JPanel getParameter() {
        return parameter;
    }

    public void setParameter(JPanel parameter) {
        this.parameter = parameter;
    }
}
