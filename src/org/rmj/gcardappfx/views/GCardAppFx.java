package org.rmj.gcardappfx.views;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rmj.appdriver.GRider;

public class GCardAppFx extends Application{
    public static GRider poGRider;
    public final static String pxeMainFormTitle = "G-CardApp Fx V 1.0";
    public final static String pxeMainForm = "GCardAppFx.fxml";
    public final static String pxeStageIcon = "org/rmj/gcardappfx/images/app_icon2.png";
    private double xOffset = 0;
    private double yOffset = 0;
    
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(pxeMainForm));
        
        //get the controller of the main interface
        GCardAppFxController loControl = new GCardAppFxController();
        
        //set the GRider Application Driver to the controller
        loControl.setGRider(poGRider);
        
        //the controller class to the main interface
        fxmlLoader.setController(loControl);
        
        //load the main interface
        Parent parent = fxmlLoader.load();
        //set the main interface as the scene
        Scene scene = new Scene(parent);
        
        //get the screen size
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(pxeStageIcon));
        stage.setTitle(pxeMainFormTitle);
        
        // set stage as maximized but not full screen
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.centerOnScreen();
        stage.show();
        
        //making the stage draggable
        loControl.TitlePane.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        loControl.TitlePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }
    
    public void setGRider(GRider foGRider){
        this.poGRider = foGRider;
    }
    
}
