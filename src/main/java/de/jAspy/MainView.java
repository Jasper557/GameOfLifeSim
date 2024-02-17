package de.jAspy;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    private Canvas canvas;

    private Affine affine;

    private Simulation simulation;

    private int drawMode = Simulation.ALIVE;

    public Simulation getSimulation() {
        return this.simulation;
    }

    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }

    public MainView() {
        this.canvas = new Canvas(400, 400);
        //Mouse Events
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        //Hotkey Events
        this.setOnKeyPressed(this::onKeyPressed);

        //Toolbar
        ToolBar toolbar = new Toolbar(this);


        this.getChildren().addAll(toolbar, this.canvas);

        this.simulation = new Simulation(10, 10);

        this.affine = new Affine();
        this.affine.appendScale(this.canvas.getWidth() / simulation.width, this.canvas.getHeight() / simulation.height);
    }

    private void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.D) {
            this.drawMode = Simulation.ALIVE;
            System.out.println("Draw Mode");
        } else if (e.getCode() == KeyCode.E) {
            this.drawMode = Simulation.DEAD;
            System.out.println("Erase Mode");
        }
    }

    private void handleDraw(MouseEvent e) {
        double mouseX = e.getX();
        double mouseY = e.getY();

        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);

            int simX = (int) simCoord.getX();
            int simY = (int) simCoord.getY();

            this.simulation.setState(simX, simY, drawMode);
            this.draw();

        } catch (NonInvertibleTransformException ex) {
            System.out.println("Could not invert affine transform");
        }

    }

    public void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        g.setFill(Color.BLACK);
        for (int x = 0; x < this.simulation.width; x++) {
            for (int y = 0; y < this.simulation.height; y++) {
                if (this.simulation.getState(x, y) == Simulation.ALIVE)
                    g.fillRect(x, y, 1, 1);
            }

        }

        g.setStroke(Color.GRAY);
        g.setLineWidth(0.05f);
        for (int x = 0; x <= this.simulation.width; x++) {
            g.strokeLine(x, 0, x, 10);
        }

        for (int y = 0; y <= this.simulation.height; y++) {
            g.strokeLine(0, y, 10, y);
        }
    }

}
