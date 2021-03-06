/*
 * Auteur: Jorne Biccler
 * Project: ugentopoly
 * Vak: Programmeren 2
 */
package infoholders;

import basicgameinfo.Space;
import basicgameinfo.SpaceType;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import monopoly.*;

/**
 * Klasse die een VBox uitbreidt, en waarvan een object standaard twee kinderen
 * heeft: een VBox viewBox, en een VBox labelBox. Deze klasse houdt ook de
 * positie en het space-object waarvoor hij informatie levert bij.
 *
 * @author Jorne Biccler
 */
public abstract class InfoBox extends VBox  {

    private final int position;
    protected final Space space;
    private final InfoBoxCompanion companion;
    protected final String propString;
    
    public InfoBox(Space space, String propString) {
        this.position = space.getPosition();
        this.propString = propString;
        this.space = space;
        setVisible(false);
        try {
            FXMLLoader loader = new FXMLLoader(
                    InfoBox.class.getResource("InfoBox.fxml"));
            loader.setRoot(this);
            this.companion = new InfoBoxCompanion(propString);
            loader.setController(companion);
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        companion.getBottomBox().getStyleClass().add("bottomBox");
    }

    public int getPosition() {
        return position;
    }

    /**
     * methode die de ImageBox opvult.
     */
    protected void addNodeViewBox(Node node) {
       companion.getViewBox().getChildren().add(node);
    }

    /**
     * methode die een extra kind toevoegt aan de LabelBox
     */
    protected void addNodeBottomBox(Node node) {
        companion.getBottomBox().getChildren().add(node);
    }

    /**
     * methode die een LabelBox aanmaakt met de afbeelding horende bij het
     * gegeven pad, en het gegeven partner object en deze in de bottomBox
     * plaatst.
     */
    protected void createLabelBox(LabelBoxCompanion labelBoxCompanion, String labelBoxFxmlPath) {
        VBox labelBox = new LabelBox(labelBoxCompanion, labelBoxFxmlPath);
        addNodeBottomBox(labelBox);
    }

    public SpaceType getSpaceType() {
        return space.getType();
    }

    public String getPropString() {
        return propString;
    }

    public abstract void doAction(GameModel gameModel);
    
}
