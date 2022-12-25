/*
tjb404
11294509
Tyler Boechler
CMPT 381
 */

package com.example.a3tjb404;

import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Visual representation for all ToolUI objects
 */
public class ToolPalette extends StackPane implements iModelListener, ModelListener {
    private InteractionModel iModel;
    private final ArrayList<UITool> tools;
    public double boxSize;
    public VBox root;
    private SMModel model;

    /**
     * Creates new toolpallete object
     * @param width
     * @param height
     * @throws IOException
     */
    public ToolPalette(Double width, Double height) throws IOException {
        this.setPrefSize(width, height);
        this.setMinSize(width, boxSize*4);

        this.boxSize = width-2;

        tools = new ArrayList<UITool>(3);

        //Pick selected tool action
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getX() <  boxSize && mouseEvent.getY() < (boxSize+root.spacingProperty().get()) * 3) {
                int boxId = (int) (mouseEvent.getY()/(boxSize + root.spacingProperty().get()));
                iModel.setTool(tools.get(boxId));
            }
        });

        //make and display tools
        tools.add(new UITool(0, new Image("pointer.png"), 0, 0, boxSize, boxSize));
        tools.add(new UITool(1, new Image("crosshair.png"), 0, boxSize, boxSize, boxSize));
        tools.add(new UITool(2, new Image("box.png"), 0, 2*boxSize, boxSize, boxSize));
        root = new VBox();

        root.getChildren().addAll(tools);
        root.setPrefSize(this.widthProperty().doubleValue(), this.heightProperty().doubleValue());
        this.getChildren().add(root);
        root.setSpacing(50);
    }


    private void toolsChanged() {
        for (UITool tool: tools) {
            tool.draw();
        }
    }

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
        iModel.selectedTool = tools.get(0);
    }

    @Override
    public void iModelChanged() {
        toolsChanged();
    }

    @Override
    public void editControllerState(AppController.State state) {}//unused

    public void setController(AppController controller) {}

    @Override
    public void modelChanged() {}

    @Override
    public void modelNotNull() {}

    public void setModel(SMModel model) {
        this.model = model;
    }}


