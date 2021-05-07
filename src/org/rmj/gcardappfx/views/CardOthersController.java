package org.rmj.gcardappfx.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import org.rmj.appdriver.agentfx.ShowMessageFX;


public class CardOthersController implements Initializable {
    
    private GCardAppFxController mainController ;
    private final String pxeModuleName = "Other Transaction";
    private final String pxeCardRedemption = "CardRedemption.fxml";
    private final String pxeCardPreOrder = "CardPreOrder.fxml";
    public final static String pxeOfflinePointsVerifB = "OfflinePoints_VerificationB.fxml";
    private boolean pbLoaded = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        mainController.rootPane.setOnKeyReleased(this::handleKeyCode);
        mainController.cmdButton01.setOnAction(this::cmdButton_Click);
        mainController.cmdButton02.setOnAction(this::cmdButton_Click);
        mainController.cmdButton03.setOnAction(this::cmdButton_Click);
        mainController.cmdButton04.setOnAction(this::cmdButton_Click);
        mainController.cmdButton05.setOnAction(this::cmdButton_Click);
        mainController.cmdButton06.setOnAction(this::cmdButton_Click);
        mainController.cmdButton07.setOnAction(this::cmdButton_Click);
        mainController.cmdButton08.setOnAction(this::cmdButton_Click);
        mainController.cmdButton09.setOnAction(this::cmdButton_Click);
        mainController.cmdButton10.setOnAction(this::cmdButton_Click);
        mainController.cmdButton11.setOnAction(this::cmdButton_Click);
        loadGui();
    }
    
    public void setMainController(GCardAppFxController mainController) {
    this.mainController = mainController ;
    }
     
     public void loadGui(){
        mainController.cmdButton00.setText("");
        mainController.cmdButton01.setText("");
        mainController.cmdButton02.setText("");
        mainController.cmdButton03.setText("REDEMPTION");
        mainController.cmdButton04.setText("PRE-ORDER");
        mainController.cmdButton05.setText("");
        mainController.cmdButton06.setText("");
        mainController.cmdButton07.setText("");
        mainController.cmdButton08.setText("OFF VERIF");
        mainController.cmdButton09.setText("");
        mainController.cmdButton10.setText("");
        mainController.cmdButton11.setText("HOME");

        mainController.tooltip00.setText("x");
        mainController.tooltip01.setText("x");
        mainController.tooltip02.setText("x");
        mainController.tooltip03.setText("F4");
        mainController.tooltip04.setText("F5");
        mainController.tooltip05.setText("x");
        mainController.tooltip06.setText("x");
        mainController.tooltip07.setText("x");
        mainController.tooltip08.setText("F9");
        mainController.tooltip09.setText("x");
        mainController.tooltip10.setText("x");
        mainController.tooltip11.setText("F12"); 

        mainController.cmdButton00.setDisable(true);
        mainController.cmdButton01.setDisable(true);
        mainController.cmdButton02.setDisable(true);
        mainController.cmdButton03.setDisable(false);
        mainController.cmdButton04.setDisable(false);
        mainController.cmdButton05.setDisable(true);
        mainController.cmdButton06.setDisable(true);
        mainController.cmdButton07.setDisable(true);
        mainController.cmdButton08.setDisable(false);
        mainController.cmdButton09.setDisable(true);
        mainController.cmdButton10.setDisable(true);
        mainController.cmdButton11.setDisable(false);

        mainController.setSiteMap(13);
    }
    
    private void cmdButton_Click(ActionEvent event) {
        String lsButton = ((Button)event.getSource()).getId().toLowerCase();
        switch (lsButton){
            case "cmdbutton00":
                break;
            case "cmdbutton01":
                break;
             case "cmdbutton02":
                break;
             case "cmdbutton03":
                 mainController.loadModule(pxeCardRedemption);
                 break;
             case "cmdbutton04":
                 mainController.loadModule(pxeCardPreOrder);
                 break;
             case "cmdbutton05":
                 break;
             case "cmdbutton06":
                 break;
             case "cmdbutton07":
                 break;
             case "cmdbutton08":
                 mainController.loadModule(pxeOfflinePointsVerifB);
                 break;
             case "cmdbutton09":
                 break;
             case "cmdbutton10":
                 break;
             case "cmdbutton11":
                 mainController.dataPane.getChildren().clear();
                 mainController.buttonClick();
                 mainController.loadMainGui(); 
                 mainController.mainKeyCode();
                 break;
            default:
                ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
                return;
        }
    }
    
    public void handleKeyCode(KeyEvent event) {
        switch(event.getCode()){
            case F1:
            case F2:
            case F3:
            case F4:
            case F5:
            case F6:
            case F7:
            case F8:
            case F9:
            case F10:
            case F11:
            case F12:
            case ESCAPE:
                break; 
        }
    }
}
