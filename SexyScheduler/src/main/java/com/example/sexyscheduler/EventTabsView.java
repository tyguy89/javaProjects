package com.example.sexyscheduler;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * View to add events containing two tabs: first tab contains view to add regular event and second tab contains view to
 * add auto scheduled event
 */
public class EventTabsView extends StackPane {
    private TabPane tabPane;
    private CreateEventViewItems createItems;
    private AddEvent addTab;
    private Tab eventTab;
    private AddAutoEvent addAutoTab;
    private RepeatEvent repeatTab;
    private DeadlineEventView deadlineTab;
    public EventTabsView(CreateEventViewItems create, AddEvent add, AddAutoEvent addAuto, RepeatEvent repeat, DeadlineEventView deadline) {
        this.addTab = add;
        this.createItems = create;
        this.addAutoTab = addAuto;
        this.repeatTab = repeat;
        this.deadlineTab = deadline;

        BorderPane root = new BorderPane();

        this.tabPane = new TabPane();
        this.eventTab = new Tab("Appointment Event", addTab);
        this.eventTab.setId("Event");
        this.eventTab.setClosable(false);
        this.tabPane.getTabs().add(this.eventTab);

        addTab.setPrefSize(400,430);

        Tab addAutoEventTab = new Tab("Automatic Event", addAutoTab);
        addAutoEventTab.setId("Auto");
        addAutoEventTab.setClosable(false);
        this.tabPane.getTabs().add(addAutoEventTab);

        Tab repeatEventTab = new Tab("Repeating Event", repeatTab);
        repeatEventTab.setId("Repeat");
        repeatEventTab.setClosable(false);
        this.tabPane.getTabs().add(repeatEventTab);

        Tab deadlineEventTab = new Tab("Deadline Event", deadlineTab);
        deadlineEventTab.setId("Deadline");
        deadlineEventTab.setClosable(false);
        this.tabPane.getTabs().add(deadlineEventTab);
        this.getStylesheets().add(this.getClass().getResource("styles.css").toExternalForm());
        tabPane.setId("cust-background");
        this.eventTab.setId("tab");
        addAutoEventTab.setId("tab");
        repeatEventTab.setId("tab");
        deadlineEventTab.setId("tab");

        root.setCenter(this.tabPane);
        this.getChildren().add(root);
    }
    public void clearAddView() {
        String selectedTab = tabPane.getSelectionModel().getSelectedItem().getId();
        if (selectedTab.equals("Event")) {
            this.addTab.clearFields();
        }
        else if (selectedTab.equals("Auto")) {
            this.addAutoTab.clearFields();
        }else if (selectedTab.equals("Repeat")) {
            this.repeatTab.clearFields();
        }
        else if (selectedTab.equals("Deadline")) {
            this.deadlineTab.clearFields();
        }
    }

}
