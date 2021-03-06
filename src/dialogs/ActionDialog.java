/*
 * Auteur: Jorne Biccler
 * Project: ugentopoly
 * Vak: Programmeren 2
 */
package dialogs;

import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Basis uitbreiding van Stage die een dialoog meet een ja en neen knop
 * weergeeft, Er moet een extra Eventhandler megegeven worden voor deze knoppen
 * (de Handle methode wordt dan opgeroepen als er geklikt wordt waarna het
 * dialoogje gesloten wordt). Indien deze dialog met de close button gesloten
 * wordt dan wordt standaard de extraNoHandler ook uitegevoerd.
 *
 * @author Jorne Biccler
 */
public class ActionDialog extends Stage {

    private final ActionDialogCompanion companion;

    public ActionDialog(String infoProp, String actionInfo,
            EventHandler<Event> extraYesHandler, final EventHandler<Event> extraNoHandler,
            BooleanProperty disableYesButton) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("ActionDialog.fxml"));
            this.companion = new ActionDialogCompanion(infoProp, actionInfo, extraYesHandler, extraNoHandler, disableYesButton);
            loader.setController(companion);
            Parent root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            setScene(scene);
            initStyle(StageStyle.UTILITY);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initModality(Modality.APPLICATION_MODAL);
        setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                extraNoHandler.handle(t);
            }
        });
    }

}
