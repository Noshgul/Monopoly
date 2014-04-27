/*
 * Auteur: Jorne Biccler
 * Project: ugentopoly
 * Vak: Programmeren 2
 *//*
 * Auteur: Jorne Biccler
 * Project: ugentopoly
 * Vak: Programmeren 2
 */
package spacebox;

import infoholders.InfoBox;
import java.io.IOException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import monopoly.GameModel;
import monopoly.Player;

/**
 * Uitbreiding van Pane die i.h.b. een positie bijhoudt en van deze gebruik
 * maakt bij het luisteren naar een ActiveBoundedIntegerModel, ook kunnen
 * spaceBoxen 'getoggled' worden bij het aanklikken van het vakje, dit geeft ook
 * aanleiding tot een vernieuwing van de styleClasses.
 *
 * @author Jorne Biccler
 */
public class SpaceBox extends FlowPane implements InvalidationListener {

    private GameModel gameModel;
    private final int position;
    private Toggled toggle;
    private final SpaceBoxPos spaceBoxPos;
    private final SpaceBoxCompanion companion;

    private InfoBox infoBox;

    /**
     * De constructor zorgt ervoor dat het fxml bestand ingeladen wordt en in
     * het beijzonder dat de hoogte en breedte zoals ze in de spaceBoxPos
     * gespecificieerd zijn wordt.
     */
    public SpaceBox(int position, GameModel gameModel, SpaceBoxPos spaceBoxPos, InfoBox infoBox) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("SpaceBox.fxml"));
            loader.setRoot(this);
            this.companion = new SpaceBoxCompanion();
            loader.setController(companion);
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.spaceBoxPos = spaceBoxPos;
        this.position = position;
        this.toggle = Toggled.UNTOGGLED;
        this.gameModel = gameModel;
        this.infoBox = infoBox;
        infoBox.setVisible(false);
        setOrientation(spaceBoxPos.getOrientation());
        setPrefWidth(spaceBoxPos.getWidth());
        setPrefHeight(spaceBoxPos.getHeight());
        getStyleClass().add(toggle.getCSSClass());
        getStyleClass().add(spaceBoxPos.getCSSClass());
        this.gameModel.addListener(this);
        setOnMouseClicked(new spaceBoxClickHandler());
        for (Player pl : gameModel.getPossibleModels()) {
            if (pl.getCurrentPosition() == position) {
                this.getChildren().add(pl.getTokenImageView());
            }
        }
        updateSelection();
    }

    public InfoBox getInfoBox() {
        return infoBox;
    }

    public SpaceBoxPos getSpaceBoxPos() {
        return spaceBoxPos;
    }
    
    
    

    /**
     * Methode die het uitzicht van een spaceBox aanpast als het model
     * verandert.
     */
    @Override
    public void invalidated(Observable o) {
        if (gameModel.getModelPosition()== position
                && !getChildren().contains(gameModel.getCurrentPlayer().getTokenImageView())) {
            ImageView temp = gameModel.getCurrentPlayer().getTokenImageView();
            temp.setRotate(spaceBoxPos.getRotate());
            getChildren().add(temp);
        }

        updateSelection();
        if (gameModel.getModelPosition() == position
                && gameModel.isCanDoAction()) {
            gameModel.setCanDoAction(false);
            infoBox.doAction(gameModel);
        }
    }

    /**
     * Methode die de toggle-mode en het model aanpast als er op het vakje
     * geklikt wordt.
     */
    private class spaceBoxClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent t) {
            if (t.getButton() == MouseButton.PRIMARY) {
                invertSelection();
            }
        }
    }

    private void invertSelection() {
        if (toggle == Toggled.UNTOGGLED) {
            gameModel.setSelectedPosition(position);

        } else {
            gameModel.setSelectedPosition(gameModel.getModelPosition());
        }
    }

    private void updateSelection() {
        while(getStyleClass().remove(toggle.getCSSClass()));
        if (position != gameModel.getSelectedPosition()) {
            toggle = Toggled.UNTOGGLED;
        } else {
            toggle = Toggled.TOGGLED;
        }
        
        getStyleClass().add(toggle.getCSSClass());
        if (toggle == Toggled.TOGGLED) {
            infoBox.setVisible(true);
        } else {
            infoBox.setVisible(false);
        }
    }

}
