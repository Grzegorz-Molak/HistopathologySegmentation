package View;

import Algorithms.ImageOperations;
import Operations.Operation;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Class representing single block of operations.
 * Blocks of operations are preprocessing, processing and postprocessing.
 */
public class ParameterSection extends JPanel {
    /**
     * To scroll block if there are too many operations.
     */
    JScrollPane scrollPane;
    /**
     * Title (pre,-,post)processing.
     */
    JPanel title;
    /**
     * Stores operation views.
     */
    JPanel content;
    /**
     * Allows to add new, chosen operation.
     */
    JPanel addContent;
    /**
     * Chooses new operation to be added.
     */
    JComboBox<String> chooseOperation;
    /**
     * A [+] button to add operation
     */
    JButton addOperationButton;
    /**
     * Options of operations to be chosen
     */
    String[] OperationOptions;
    /**
     * Height of block (in px)
     */
    int height;
    /**
     * Width of block (in px)
     */
    int width;

    /**
     * Class constructor specifying title, height and width
     * @param title pre,-,post + processing
     * @param width width of block
     * @param height height of block
     */
    public ParameterSection(String title, int width, int height){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.title = new JPanel();
        this.title.add(new JLabel(title));
        this.width = width;
        this.height = height;
        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(width,height));

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        addContent = new JPanel();
        addOperationButton = new JButton("+");
    }

    /**
     * Creates and adds elements in correct order.
     */
    public void draw(){
        createContents();
        add(title);
        add(scrollPane);
        add(addContent);
    }

    /**
     * Creates block for adding new operation
     */
    private void createContents(){
        addContent.setLayout(new FlowLayout());
        chooseOperation = new JComboBox<>(OperationOptions);
        chooseOperation.setSelectedIndex(0);
        addContent.add(chooseOperation);
        addContent.add(addOperationButton);
        addContent.setBorder(new CompoundBorder(new EmptyBorder(5,30,5,30),
                BorderFactory.createLoweredBevelBorder()));
        scrollPane.setViewportView(content);
    }

    /**
     * Adds a single operation to the content and refreshes view.
     * @param operation operation view to be added
     */
    public void addOperation(OperationView operation){
        operation.draw();
        content.add(operation);
        refresh();
    }

    public void removeOperation(OperationView operation){
        getContent().remove(operation);
        refresh();
    }

    /**
     * Sets this block as processing block.
     * It doesnt have [+] and [-] buttons, and chooses operation differently.
     */
    public void setAsProcessing(){
        remove(2);
        content.add(chooseOperation);
        var emptyOperationView = new OperationView(ImageOperations.Empty);
        emptyOperationView.draw();
        emptyOperationView.getOperation().remove(1);
        content.add(emptyOperationView);
    }

    /**
     * Draws a proper PROCESSING operation view
     * @param operation algorithm for processing.
     */
    public void setOperation(OperationView operation){
        operation.draw();
        if(!operation.getOperationName().equals(ImageOperations.Empty)) operation.leaveOnlyParameter();
        else operation.getOperation().remove(1);
        content.remove(1);
        content.add(operation);
        refresh();
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public JPanel getTitle() {
        return title;
    }

    public void setTitle(JPanel title) {
        this.title = title;
    }

    public JPanel getContent() {
        return content;
    }

    public void setContent(JPanel content) {
        this.content = content;
    }

    public JPanel getAddContent() {
        return addContent;
    }

    public void setAddContent(JPanel addContent) {
        this.addContent = addContent;
    }

    public JComboBox<String> getChooseOperation() {
        return chooseOperation;
    }

    public void setChooseOperation(JComboBox<String> chooseOperation) {
        this.chooseOperation = chooseOperation;
    }

    public JButton getAddOperationButton() {
        return addOperationButton;
    }

    public void setAddOperationButton(JButton addOperationButton) {
        this.addOperationButton = addOperationButton;
    }

    public String[] getOperationOptions() {
        return OperationOptions;
    }

    public void setOperationOptions(String[] operationOptions) {
        OperationOptions = operationOptions;
    }

    /**
     * Refreshes view to be seen by user.
     */
    private void refresh(){
        revalidate();
        repaint();
    }
}
