package gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import logic.InvalidArrayException;
import logic.LogicResources;
import logic.Table;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    GridPane gridPaneInput;
    @FXML
    GridPane gridPaneOutput;
    @FXML
    ListView listViewSteps;

    @FXML
    // TODO - Solves the puzzle by brute force when called
    private void bruteForce() {
        try {
            ObservableList<Table> steps = listViewSteps.getItems();
            steps.clear();
            Table initialTable = new Table(getInputArray(), 0);
            ArrayList<Table> tables = LogicResources.getStepsToSolution(initialTable);
            steps.addAll(tables);
            listViewSteps.setItems(steps);
            setOutputGrid(tables.get(tables.size()-1));
        } catch(InvalidArrayException e) {
            callAlert("Error", "Invalid input", e.getMessage());
        }
    }

    private void callAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public ArrayList<Integer> getInputArray() throws InvalidArrayException {
        ArrayList<Integer> output = new ArrayList<>();
        try {
            for(int i = 0; i < 9; i++) {
                output.add(Integer.parseInt(((TextField) gridPaneInput.getChildren().get(i)).getText()));
            }
        } catch(Exception e) {
            throw new InvalidArrayException("Fill in all the numbers before trying to solve the puzzle");
        }
        return output;
    }

    public void setOutputGrid(Table table) {
        for(int i = 0; i < 9; i++) {
            ((Label) gridPaneOutput.getChildren().get(i)).setText(String.valueOf(table.getTable().get(i)));
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        listViewSteps.getSelectionModel().selectedItemProperty().addListener((ChangeListener<Table>) (observable, oldValue, newValue) -> {
            // What is supposed to happen when the selected item changes
            setOutputGrid(newValue);
        });

        listViewSteps.setCellFactory(lv -> new ListCell<Table>() {
            @Override
            public void updateItem(Table table, boolean empty) {
            super.updateItem(table, empty);
            if (empty) {
                setText(null);
            } else {
                StringBuilder text = new StringBuilder("Step " + table.getDepth() + " (");
                for(int i = 0; i < table.getTable().size()-1; i++) {
                    text.append(table.getTable().get(i)).append(", ");
                }
                text.append(table.getTable().get(table.getTable().size()-1)).append(")");
                setText(text.toString());
            }
            }
        });
    }
}
