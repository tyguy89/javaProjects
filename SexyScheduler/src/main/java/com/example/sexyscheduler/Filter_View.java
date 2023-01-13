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
    private CalendarModel model;
    Button save;
    ComboBox<String> filtersToChoseFrom;
    ObservableList<String> filterNames;
    private Stage popUp;
    Button editor = new Button("Edit Filters");


    /**
     * Creates the filter view
     *
     */
    public Filter_View(IModel imodel,CalendarModel model) {
        this.model = model;
        this.imodel = imodel;
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
        //filterEditor.getChildren().add(editor);
        editor.setOnAction(e->{
            filterEditorWindow();
            show();});


        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        filterList = model.filterList;
        for(String filter: filterList){
            CheckBox c = new CheckBox(filter);
            c.setOnAction(e->filterClicked(c));
            filters.getChildren().add(c);
        }

        filterNames = FXCollections.observableList(filterList);
        filtersToChoseFrom = new ComboBox<>();
        filtersToChoseFrom.setItems(filterNames);
        filtersToChoseFrom.getSelectionModel().selectFirst();

        boundingBox.getChildren().addAll(filters, clearFilters);
        this.getChildren().add(boundingBox);

        addButtons();

    }

    /**
     * Method that creates the Filter Editor view after clicking
     * the "Edit Filters" button.
     */
    private void filterEditorWindow(){
        VBox items = new VBox(10);
        items.setPrefSize(500,400);
        items.setSpacing(30);
        items.setPadding(new Insets(50));

        Label filter_label = new Label("Enter Filter to be Added: ");
        TextField textField = new TextField();

        Label select = new Label("Select Filter to be Removed: ");
        ComboBox<String> tagCombo = new ComboBox<>();
        ArrayList<String> tags = filterList;
        ObservableList<String> tagList = FXCollections.observableList(tags);
        tagCombo.setItems(tagList);
        tagCombo.setPrefWidth(Double.MAX_VALUE);
        tagCombo.setMaxWidth(200);

        HBox hb = new HBox();
        hb.getChildren().addAll(filter_label, textField);

        HBox hb2 = new HBox();
        hb2.getChildren().addAll(select, tagCombo);

        VBox buttons = new VBox(10);
        Button add = new Button("Add Filter");
        add.setMinSize(100,60);
        Button remove = new Button("Remove Filter");
        remove.setId("cust-button");
        remove.setMinSize(100,60);
        buttons.getChildren().addAll(hb2, add, remove);

        hb.setAlignment(Pos.BASELINE_CENTER);
        buttons.setAlignment(Pos.BASELINE_CENTER);

        items.getChildren().addAll(hb,buttons);

        this.popUp = new Stage();
        this.popUp.setMaxWidth(500);
        this.popUp.setMaxHeight(400);
        this.popUp.setTitle("Filter Editor");
        Scene filter_edits = new Scene(items);
        filter_edits.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        items.setId("cust-background");
        add.setId("cust-button");
        remove.setId("cust-button");
        this.popUp.setScene(filter_edits);


        // add the add_filter and remove_filter events to the respective buttons.
        add.setOnAction(e->{
            if (!Objects.equals(textField.getText(), "")) {
                textField.getText();
                String filter = textField.getText();
                this.add_filter(filter);
                model.notifySubscribers();
            }
            show();
        });

        remove.setOnAction(e->{
            String filter = tagCombo.getValue();
            this.remove_filter(filter);
            model.notifySubscribers();
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
        model.notifySubscribers();

        c.setOnAction(e->filterClicked(c));

    }

    public void filterClicked(CheckBox c){
        if (c.isSelected()) {
            imodel.addSelectedFilter(c.getText());
        }
        else if (!c.isSelected()) {
            imodel.removeSelectedFilter(c.getText());
        }
    }

    public void addButtons(){
        Button allSelector = new Button();
        allSelector.setOnAction(e->addAll());
        allSelector.setText("Select All");
        Button deSelector = new Button();
        deSelector.setOnAction(e->removeAll());
        deSelector.setText("Unselect All");
        allSelector.setId("cust-button");
        deSelector.setId("cust-button");

        setColor.setId("cust-button");
        editor.setId("cust-button");

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
        allFilters.clear();
        imodel.notifyFiltersChanged();
    }

    /**
     * creates the popup pane for when set color is clicked
     */
    public void colourSelectPopUp(){
        VBox items = new VBox(10);
        items.setPrefSize(300,400);
        items.setSpacing(15);
        items.setPadding(new Insets(50));

        VBox buttons = new VBox(10);
        save = new Button("save");
        save.setMinSize(100,60);
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        save.setId("cust-button");
        colorPicker.setId("cust-button");
        colorPicker.setMinSize(100,60);
        buttons.getChildren().addAll(save, colorPicker);



        filtersToChoseFrom.setMinSize(100,60);
        items.setAlignment(Pos.BASELINE_CENTER);
        buttons.setAlignment(Pos.BASELINE_CENTER);
        items.getChildren().addAll(filtersToChoseFrom,colorPicker,save);

        this.popUp = new Stage();
        this.popUp.setMaxWidth(300);
        this.popUp.setMaxHeight(400);
        this.popUp.setTitle("Color Picker");
        Scene colors = new Scene(items);
        this.popUp.setScene(colors);
        colors.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        items.setId("cust-background");

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
        model.setFilterColorByName(filterName,color);
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
        imodel.notifyFiltersChanged();
    }


}
