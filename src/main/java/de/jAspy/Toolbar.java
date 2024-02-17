package de.jAspy;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {

    private final MainView mainView;

    public Toolbar(MainView mainView) {
        this.mainView = mainView;
        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);
        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);

        this.getItems().addAll(draw, erase, step);
    }

    private void handleStep(ActionEvent e) {
        this.mainView.getSimulation().step();
        this.mainView.draw();
    }

    private void handleDraw(ActionEvent e) {
        this.mainView.setDrawMode(Simulation.ALIVE);
        this.mainView.draw();
    }

    private void handleErase(ActionEvent e) {
        this.mainView.setDrawMode(Simulation.DEAD);
        this.mainView.draw();
    }

}
