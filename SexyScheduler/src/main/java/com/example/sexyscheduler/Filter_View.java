package com.example.sexyscheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Objects;

public class Filter_View extends StackPane {

    FlowPane clearFilters = new FlowPane();
    FlowPane filters = new FlowPane();
    HBox boundingBox = new HBox(30);
    HBox buttonBox = new HBox(5);
    FlowPane filterEditor = new FlowPane();
    public int filterHeight;
    ArrayList<String> filterList = new ArrayList<>();
    Button setColor = new Button("Set Color");
    private IModel imodel;
    Button save;
    ComboBox<String> filtersToChoseFrom;
    ObservableList<String> filterNames;
    private Stage popUp;
    Button editor = new Button("Edit Filters");


    /**
     * Creates the filter view
     *
     */
    public Filter_View() {
        filters.setPadding(new Insets(10));
        filters.setVgap(5);
        filters.setHgap(5);
        clearFilters.setPadding(new Insets(10));
        clearFilters.setVgap(5);
        clearFilters.setHgap(5);
        HBox.setHgrow(filters, Priority.ALWAYS);
        clearFilters.setAlignment(Pos.CENTER_RIGHT);
        filters.setAlignment(Pos.CENTER_LEFT);
        filterEditor.setAlignment(Pos.CENTER);
        boundingBox.getChildren().addAll(filters, clearFilters);
        this.getChildren().add(boundingBox);
        //filterEditor.getChildren().add(editor);
        editor.setOnAction(e->{
            filterEditorWindow();
            show();});
    }

    /**
     * Method that creates the Filter Editor view after clicking
     * the "Edit Filters" button.
     */
    private void filterEditorWindow(){
        VBox items = new VBox(10);
        items.setPrefSize(400,300);
        items.setSpacing(50);
        items.setPadding(new Insets(50));

        Label filter_label = new Label("Enter Filter Name: ");
        TextField textField = new TextField();
        textField.setMinSize(100, 25);
        HBox hb = new HBox();
        hb.getChildren().addAll(filter_label, textField);


        VBox buttons = new VBox(10);
        Button add = new Button("Add Filter");
        add.setMinSize(100,25);
        Button remove = new Button("Remove Filter");
        remove.setMinSize(100,25);
        buttons.getChildren().addAll(add, remove);

        hb.setAlignment(Pos.BASELINE_CENTER);
        buttons.setAlignment(Pos.BASELINE_CENTER);

        items.getChildren().addAll(hb,buttons);

        this.popUp = new Stage();
        this.popUp.setMaxWidth(400);
        this.popUp.setMaxHeight(300);
        this.popUp.setTitle("Filter Editor");
        Scene filter_edits = new Scene(items);
        this.popUp.setScene(filter_edits);


        // add the add_filter and remove_filter events to the respective buttons.
        add.setOnAction(e->{
            if (textField.getText() != null) {
                String filter = textField.getText();
                this.add_filter(filter);
                //TODO: Has to update the tags in the create event view
            }
            show();
        });

        remove.setOnAction(e->{
            if (textField.getText() != null) {
                String filter = textField.getText();
                this.remove_filter(filter);
                //TODO: Has to update the tags in the create event view
            }
            show();
        });
    }

    /**
     * Adds a filter to the "Filters" view and creates the "Unselect All"
     * and "Select All" buttons once the first filter is added.
     * @param new_filter name of the new filter to be added.
     */
    public void add_filter(String new_filter) {

        // if filter already exists then return
        for (int i = 0; i < filters.getChildren().size(); i++) {
            CheckBox duplicate = (CheckBox) filters.getChildren().get(i);
            if (Objects.equals(duplicate.getText(), new_filter)) {
                return;
            }
        }

        // creates the new checkbox
        CheckBox c = new CheckBox(new_filter);
        filters.getChildren().add(c);
        filterList.add(new_filter);
        filterNames = FXCollections.observableList(filterList);
        filtersToChoseFrom = new ComboBox<>();
        filtersToChoseFrom.setItems(filterNames);

        EventHandler<ActionEvent> checkbox_event = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e)
            {
                if (c.isSelected()) {
                    imodel.addSelectedFilter(c.getText());
                }
                else if (!c.isSelected()) {
                    imodel.removeSelectedFilter(c.getText());
                }
            }
        };
        c.setOnAction(checkbox_event);


        // creates the "Unselect All" and "Select All" button only once, when the first filter is added
        if (filters.getChildren().size() == 1) {

            Button allSelector = new Button();
            allSelector.setText("Select All");
            Button deSelector = new Button();
            deSelector.setText("Unselect All");
            filterHeight = (int) boundingBox.getHeight();
            VBox editBox = new VBox(5);
            VBox selectBox = new VBox(5);
            HBox buttonBox = new HBox(5);
            editBox.getChildren().addAll(setColor,editor);
            selectBox.getChildren().addAll(allSelector,deSelector);
            buttonBox.getChildren().addAll(editBox,selectBox);
            setColor.setPrefWidth(110);
            editor.setPrefWidth(110);
            allSelector.setPrefWidth(110);
            deSelector.setPrefWidth(110);
            clearFilters.getChildren().addAll(buttonBox);
            allSelector.setOnAction(e->addAll());
            deSelector.setOnAction(e->removeAll());
            setColor.setOnAction(e->{
                colourSelectPopUp();
                show();});

        }
    }

    /**
     * Selects all filters in the "Filters" view.
     */
    private void addAll() {
        ArrayList<String> allFilters = imodel.getSelectedFilters();
        for (int i = 0; i < filters.getChildren().size(); i++) {
            if (filters.getChildren().get(i) instanceof CheckBox) {
                CheckBox change = (CheckBox) filters.getChildren().get(i);
                change.setSelected(true);
                imodel.addSelectedFilter(filters.getChildren().get(i).toString());
                allFilters.add(filterList.get(i));
            }
        }

        imodel.notifyFiltersChanged();
    }

    /**
     * Removes all selected filters in the "Filters" view.
     */
    public void removeAll(){
        ArrayList<String> allFilters = imodel.getSelectedFilters();
        for (int i = 0; i < filters.getChildren().size(); i++) {
            if (filters.getChildren().get(i) instanceof CheckBox) {
                CheckBox change = (CheckBox) filters.getChildren().get(i);
                change.setSelected(false);
            }
        }

        for (int i = 0; i < allFilters.size(); i++) {
            allFilters.remove(i);
        }
        imodel.notifyFiltersChanged();
    }

    /**
     * creates the popup pane for when set color is clicked
     */
    public void colourSelectPopUp(){
        BorderPane colorSelectRoot = new BorderPane();
        colorSelectRoot.setPrefSize(100,150);
        VBox items = new VBox(10);
        items.setPrefSize(100,150);
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        save = new Button("save");
        save.setMinSize(100,25);
        filtersToChoseFrom.setMinSize(100,25);
        colorPicker.setMinSize(100,25);
        items.getChildren().addAll(filtersToChoseFrom,colorPicker,save);
        colorSelectRoot.getChildren().add(items);

        this.popUp = new Stage();
        this.popUp.setMaxWidth(100);
        this.popUp.setMaxHeight(150);
        this.popUp.setTitle("Color Picker");
        Scene colors = new Scene(colorSelectRoot);
        this.popUp.setScene(colors);

        save.setOnAction(e->{
            String colorString = "rgb("+(int)(colorPicker.getValue().getRed()*255)+","+(int)(colorPicker.getValue().getGreen()*255)+","+(int)(colorPicker.getValue().getBlue()*255)+")";
            sendColorIModel(filtersToChoseFrom.getSelectionModel().getSelectedItem(),colorString);
            show();
        });


    }

    /**
     * shows/hides the color select pop up on setColor and save
     */
    public void show(){
        if(!this.popUp.isShowing()){
            this.popUp.show();
        }else{
            this.popUp.hide();
        }
    }

    /**
     * @param filterName: name of the filter
     * @param color: color selected
     * updates the color for the corresponding filter in the i-model
     */
    public void sendColorIModel(String filterName, String color){
        imodel.setFilterColorByName(filterName,color);
    }

    /**
     * Removes the filter "old_filter" from the "Filters" view,
     * Also removes the "Unselect All" and "Select All" button if there are no more filters left.
     */
    public void remove_filter(String old_filter) {

        // removes the old filter
        for (int i = 0; i < filters.getChildren().size(); i++) {
            CheckBox delete = (CheckBox) filters.getChildren().get(i);
            if (Objects.equals(delete.getText(), old_filter)) {
                filters.getChildren().remove(i);
                filterList.remove(i);
            }
        }

        // removes the "Unselect All" and "Select All" button if there are no more filters
        if (filters.getChildren().size() == 0) {
            clearFilters.getChildren().remove(0);
            clearFilters.getChildren().remove(0);
            clearFilters.getChildren().remove(0);
        }
        imodel.notifyColorsChanged();
    }

    public void setIModel(IModel newIModel){
        imodel = newIModel;
    }

}
