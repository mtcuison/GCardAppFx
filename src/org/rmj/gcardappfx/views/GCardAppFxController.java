package org.rmj.gcardappfx.views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.constants.GCDeviceType;
import org.rmj.gcard.device.ui.GCardDeviceFactory;
import org.rmj.gcard.trans.agentFX.XMGCConnect;

public class GCardAppFxController implements Initializable {
     
    @FXML
    private Button btnMinimize;
    @FXML
    private ToggleButton btnRestoreDown;
    @FXML
    private FontAwesomeIconView cmdGlyph;
    @FXML
    private Button btnExit;
    @FXML
    private Label lblName;
    
    public Task task;
    public AnchorPane rootPane;
    public Button cmdButton00;
    public Button cmdButton05;
    public Button cmdButton04;
    public Button cmdButton03;
    public Button cmdButton02;
    public Button cmdButton07;
    public Button cmdButton06;
    public Button cmdButton11;
    public Button cmdButton10;
    public Button cmdButton09;
    public Button cmdButton08;
    public Button cmdButton01;
    public VBox dataPane;
    
    public StackPane sidePane;
    public Tooltip tooltip00;
    public Tooltip tooltip01;
    public Tooltip tooltip02;
    public Tooltip tooltip03;
    public Tooltip tooltip04;
    public Tooltip tooltip05;
    public Tooltip tooltip06;
    public Tooltip tooltip07;
    public Tooltip tooltip08;
    public Tooltip tooltip09;
    public Tooltip tooltip10;
    public Tooltip tooltip11;
    public Label lblNav;
    public Label lblArrow;
    public AnchorPane TitlePane;
    public ProgressBar progressbar;
    
    private final String pxeModuleName = "G-Card Main Window";
    public final static String pxeCardIssuance = "CardIssuance.fxml";
    public final static String pxeCardActivation = "CardActivation.fxml";
    public final static String pxeCardApplication = "CardApplication.fxml";
    public final static String pxeCardPrinting = "CardPrinting.fxml";
    public final static String pxeCardEncoding = "CardEncoding.fxml";
    public final static String pxeOfflinePointsVerif = "OfflinePoints_Verification.fxml";
    public final static String pxeCardApplication_Register = "CardApplication_Register.fxml";
    public final static String pxeCardSuspension = "CardSuspension.fxml";
    public final static String pxeOfflinePoints_Entry = "OfflinePoints_Entry.fxml";
    public final static String pxeOnlinePoints_Entry = "OnlinePoints_Entry.fxml";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    public final static String pxePointsUpdate = "PointsUpdate.fxml";
    public final static String pxeCardAppVerif = "CardApplicationVerification.fxml";
    public final static String pxeOfflinePoints_Register = "OfflinePoints_Register.fxml";
    public final static String pxeOnlinePoints_Register = "OnlinePoints_Register.fxml";
    public final static String pxeOthers = "CardOthers.fxml";
    public final static String pxeCardRedemption = "CardRedemption.fxml";
    public final static String pxeOnlinePointsEntry = "OnlinePoints_Entry.fxml";
    public final static String pxeCardApplicationB = "CardApplicationB.fxml";
    public final static String pxeCardApplication_RegisterB = "CardApplication_RegisterB.fxml";
    public final static String pxeOfflinePoints_EntryB = "OfflinePoints_EntryB.fxml";
    public final static String pxeOfflinePointsVerifB = "OfflinePoints_VerificationB.fxml";
    public final static String pxeCardPreOrder = "CardPreOrder.fxml";
    public final static String pxeOfflinePoints_RegisterM = "OfflinePoints_RegisterM.fxml";
    public final static String pxeCardConnect = "CardConnect.fxml";
    public String pxeUserName = "";
    
    private static GRider poGRider;
    public Config properties;
    
    @FXML
    private Label lblTime;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblDay;
    @FXML
    private Label lblSeconds;
    @FXML
    private GridPane ParentPane;
    @FXML
    private Tooltip tootip80;
    @FXML
    private Button btnHome;
    @FXML
    private Label lblBranch;
    @FXML
    private Label lblTasks;
    @FXML
    private Label lblNav1;
    @FXML
    public Label lblCardNmbr;
    @FXML
    private Label lblNav11;
    @FXML
    public Label lblDeviceType;
    @FXML
    private AnchorPane acBody;
    @FXML
    private Label lblNav111;
    @FXML
    public Label lblMobileNo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        properties = new Config();
        loadMainGui();
        buttonClick();
        mainKeyCode();
        PlayTime();
        loadProgressbar();
        loadMainInfo();
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    private void togButton_Click(ActionEvent event){
    String lsButtion = ((ToggleButton)event.getSource()).getId().toLowerCase();
    switch(lsButtion){
        case "btnrestoredown":
            Stage stage=(Stage) rootPane.getScene().getWindow();
               if (btnRestoreDown.isSelected()) {
                   cmdGlyph.setGlyphName("EXPAND");
                   tootip80.setText("Maximize");
                   Screen screen = Screen.getPrimary();
                   Rectangle2D bounds = screen.getVisualBounds();
                   stage.setY(bounds.getMinY());
                   stage.setX(bounds.getMinX());
                   stage.setWidth(1024);
                   stage.setHeight(740);
                   stage.centerOnScreen();
               } else {
                   cmdGlyph.setGlyphName("COMPRESS");
                   tootip80.setText("Restore down");
                   Screen screen = Screen.getPrimary();
                   Rectangle2D bounds = screen.getVisualBounds();
                   stage.setY(bounds.getMinY());
                   stage.setX(bounds.getMinX());
                   stage.setWidth(bounds.getWidth());
                   stage.setHeight(bounds.getHeight()); 
                   stage.centerOnScreen();
               }
        }
    }
    
    public void mainKeyCode(){
        rootPane.setOnKeyReleased(e -> {
            switch(e.getCode()){
                case F1:
                    break;
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
        });
    }
    
    // loading details of Button
    public void loadMainGui(){   
        switch (System.getProperty("app.main.office")){
            case "1":
                cmdButton00.setText(""); //CONNECT
                cmdButton01.setText("APPLICATION");
                cmdButton02.setText("APPLIC VERIF");
                cmdButton03.setText("PRINTING");
                cmdButton04.setText("ENCODING");
                cmdButton05.setText("ISSUANCE");
                cmdButton06.setText("ACTIVATION");
                cmdButton07.setText("SUSPENSION");
                cmdButton08.setText("OFFLINE ENTRY");
                cmdButton09.setText("OFFLINE VERIF");
                cmdButton10.setText("POINTS INQUIRY");
                cmdButton11.setText("EXIT");
                
                tooltip00.setText("F1");
                tooltip01.setText("F2");
                tooltip02.setText("F3");
                tooltip03.setText("F4");
                tooltip04.setText("F5");
                tooltip05.setText("F6");
                tooltip06.setText("F7");
                tooltip07.setText("F8");
                tooltip08.setText("F9");
                tooltip09.setText("F10");
                tooltip10.setText("F11");
                tooltip11.setText("F12"); 

                cmdButton00.setDisable(true); //false
                cmdButton01.setDisable(false);
                cmdButton02.setDisable(false);
                cmdButton03.setDisable(false);
                cmdButton04.setDisable(false);
                cmdButton05.setDisable(false);
                cmdButton06.setDisable(false);
                cmdButton07.setDisable(false);
                cmdButton08.setDisable(false);
                cmdButton09.setDisable(false);
                cmdButton10.setDisable(false);
                cmdButton11.setDisable(false);
                tootip80.setText("Restore Down");
                break;
            default:
                cmdButton00.setText(""); //CONNECT
                cmdButton01.setText("APPLICATION");
                cmdButton02.setText("ISSUANCE");
                cmdButton03.setText("ACTIVATION");
                cmdButton04.setText("SUSPENSION");
                cmdButton05.setText("ONLINE ENTRY");
                cmdButton06.setText("OFFLINE ENTRY");
                cmdButton07.setText("OFFLINE VERIF");
                cmdButton08.setText("REDEMPTION");
                cmdButton09.setText("PRE-ORDER");
                cmdButton10.setText("POINTS INQUIRY");
                cmdButton11.setText("EXIT");
                
                tooltip00.setText("F1");
                tooltip01.setText("F2");
                tooltip02.setText("F3");
                tooltip03.setText("F4");
                tooltip04.setText("F5");
                tooltip05.setText("F6");
                tooltip06.setText("F7");
                tooltip07.setText("F8");
                tooltip08.setText("F9");
                tooltip09.setText("F10");
                tooltip10.setText("F11");
                tooltip11.setText("F12"); 

                cmdButton00.setDisable(true); //
                cmdButton01.setDisable(false);
                cmdButton02.setDisable(false);
                cmdButton03.setDisable(false);
                cmdButton04.setDisable(false);
                cmdButton05.setDisable(false);
                cmdButton06.setDisable(false);
                cmdButton07.setDisable(false);
                cmdButton08.setDisable(false);
                cmdButton09.setDisable(false);
                cmdButton10.setDisable(false);
                cmdButton11.setDisable(false);
                tootip80.setText("Restore Down");
        }
        setSiteMap(-1);
    }

    public void buttonClick(){
       btnHome.setOnAction(this::cmdButton_Click);
       btnExit.setOnAction(this::cmdButton_Click);
       btnRestoreDown.setOnAction(this::togButton_Click);
       btnMinimize.setOnAction(this::cmdButton_Click);
       cmdButton00.setOnAction(this::cmdButton_Click);
       cmdButton01.setOnAction(this::cmdButton_Click);
       cmdButton02.setOnAction(this::cmdButton_Click);
       cmdButton03.setOnAction(this::cmdButton_Click);
       cmdButton04.setOnAction(this::cmdButton_Click);
       cmdButton05.setOnAction(this::cmdButton_Click);
       cmdButton06.setOnAction(this::cmdButton_Click);
       cmdButton07.setOnAction(this::cmdButton_Click);
       cmdButton08.setOnAction(this::cmdButton_Click);
       cmdButton09.setOnAction(this::cmdButton_Click);
       cmdButton10.setOnAction(this::cmdButton_Click);
       cmdButton11.setOnAction(this::cmdButton_Click);
    }
    
    public void setSiteMap(int fnValue){
        switch (fnValue){
            case 0:
                lblArrow.setText("►►");
                lblNav.setText("CARD ACTIVATION");
                break;
            case 1:
                lblArrow.setText("►►");
                lblNav.setText("GCARD APPLICATION");
                break;
            case 2:
                lblArrow.setText("►►");
                lblNav.setText("GCARD APPLICATION VERIFICATION");
                break;
            case 3:
                lblArrow.setText("►►");
                lblNav.setText("CARD ENCODING");
                break;
            case 4:
                lblArrow.setText("►►");
                lblNav.setText("CARD ISSUANCE");
                break;
            case 5:
                lblArrow.setText("►►");
                lblNav.setText("CARD PRINTING");
                break;
            case 6:
                lblArrow.setText("►►");
                lblNav.setText("CARD DE-ACTIVATION/SUSPENSION");
                break;
            case 7:
                lblArrow.setText("►►");
                lblNav.setText("OFFLINE POINTS ENTRY");
                break;
            case 8:
                lblArrow.setText("►►");
                lblNav.setText("POINTS INQUIRY");
                break;
            case 9:
                lblArrow.setText("►►");
                lblNav.setText("POINTS UPDATE");
                break;
            case 10:
                lblArrow.setText("►►");
                lblNav.setText("OFFLINE POINTS VERIFICATION");
                break;
            case 11:
                lblArrow.setText("►►");
                lblNav.setText("ONLINE POINTS ENTRY");
                break;
            case 12:
                lblArrow.setText("►►");
                lblNav.setText("ONLINE POINTS REGISTER");
                break;
            case 13:
                lblArrow.setText("►►");
                lblNav.setText("OTHERS");
                break;
            case 14:
                lblArrow.setText("►►");
                lblNav.setText("CARD REDEMPTION/ REWARD ENTRY");
                break;
            case 15:
                lblArrow.setText("►►");
                lblNav.setText("CARD APPLICATION REGISTER");
                break;
             case 16:
                lblArrow.setText("►►");
                lblNav.setText("OFFLINE POINTS REGISTER");
                break;
             case 17:
                lblArrow.setText("►►");
                lblNav.setText("G CARD PRE-ORDER ITEMS");
                break;
            case 18:
                lblArrow.setText("►►");
                lblNav.setText("G-CARD CONNECT");
                break;
            default:
                lblArrow.setText("");
                lblNav.setText("");
        }
        
        //empty the gcard info on home screen
        lblCardNmbr.setText("N-O-N-E");
        lblDeviceType.setText("UNKNOWN");
        lblMobileNo.setText("N-O-N-E");
    }
    
    public void setFocus(TextField fsTxtField){
         Platform.runLater(new Runnable() {
             
            @Override
            public void run() {
                fsTxtField.requestFocus();
            }
            });
    }
    
    public void loadProgressbar(){
        lblTasks.setVisible(true);
        progressbar.setVisible(true);
        
        Timeline task = new Timeline(
        new KeyFrame(
                Duration.ZERO,       
                new KeyValue(progressbar.progressProperty(), 0)
        ),
        new KeyFrame(
                Duration.seconds(.5), 
                new KeyValue(progressbar.progressProperty(), 1)
        )
        );
        task.playFromStart();
        
        task.setOnFinished(event -> {
		progressbar.setVisible(false);
                lblTasks.setVisible(false);
	});
    }
    
    public void loadModule(String fsValue){
        try {
            switch (fsValue){
                case pxeCardConnect: //mac 2020.03.18
                case pxeCardIssuance:
                case pxeCardActivation:
                case pxeCardSuspension:
                case pxeCardPrinting:
                case pxeCardEncoding:
                case pxeCardAppVerif:
                case pxeCardApplication:
                case pxeOfflinePointsVerif:
                case pxeOfflinePoints_Entry:
                case pxePointsInquiry:
                case pxePointsUpdate:
                case pxeOnlinePoints_Entry:
                case pxeOnlinePoints_Register:
                case pxeOthers:
                case pxeCardRedemption:
                case pxeCardApplication_Register:
                case pxeCardApplicationB:
                case pxeOfflinePoints_Register:
                case pxeCardApplication_RegisterB:
                case pxeOfflinePoints_EntryB:
                case pxeOfflinePointsVerifB:
                case pxeCardPreOrder:
                case pxeOfflinePoints_RegisterM:
                    setScene(LoadScene(fsValue)); 
            }
           
        } catch (IOException ex) {
            Logger.getLogger(GCardAppFxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cmdButton_Click(ActionEvent event) {
        String lsButton = ((Button)event.getSource()).getId().toLowerCase();
        
        if (System.getProperty("app.main.office").equals("1")){
            switch (lsButton){
                case "btnexit":
                    if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to exit?")== true){
                        CommonUtils.closeStage(btnExit);
                        break;
                    } else return;
                case "btnminimize":
                    CommonUtils.minimizeStage(btnMinimize);
                    break;
                case "cmdbutton00":
                    loadModule(pxeCardConnect);
                    break;
                case "cmdbutton01":
                    loadModule(pxeCardApplication);
                    break;
                case "cmdbutton02":
                    loadModule(pxeCardAppVerif);
                    break;
                case "cmdbutton03":
                    loadModule(pxeCardPrinting);
                    break;
                case "cmdbutton04":
                    loadModule(pxeCardEncoding);
                    break;
                case "cmdbutton05":        
                    loadModule(pxeCardIssuance);
                    break;
                case "cmdbutton06":
                    loadModule(pxeCardActivation);
                    break;
                case "cmdbutton07":
                    loadModule(pxeCardSuspension);
                    break;
                case "cmdbutton08":
                    loadModule(pxeOfflinePoints_Entry);
                    break;
                case "cmdbutton09":
                    loadModule(pxeOfflinePointsVerif);
                    break;
                case "cmdbutton10":
                    loadModule(pxePointsInquiry);
                    break;
                case "cmdbutton11":
                    if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to exit?")== true){
                        CommonUtils.closeStage(cmdButton11);
                        break;
                    } else return;
                case "btnhome":
                    dataPane.getChildren().clear();
                    buttonClick();
                    loadMainGui();
                    mainKeyCode();
                    break;
                default:
                    ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
            }
        } else {
            switch (lsButton){
                case "btnexit":
                    if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to exit?")== true){
                        CommonUtils.closeStage(btnExit);
                        break;
                    } else return;
                case "btnminimize":
                    CommonUtils.minimizeStage(btnMinimize);
                    break;
                case "cmdbutton00":
                    loadModule(pxeCardConnect);
                    break;
                case "cmdbutton01":
                    loadModule(pxeCardApplicationB);
                    break;
                case "cmdbutton02":                               
                    loadModule(pxeCardIssuance);
                    break;
                case "cmdbutton03":
                    loadModule(pxeCardActivation);
                    break;
                case "cmdbutton04":
                    loadModule(pxeCardSuspension);
                    break;
                case "cmdbutton05":    
                    loadModule(pxeOnlinePoints_Entry);
                    break;
                case "cmdbutton06":
                    loadModule(pxeOfflinePoints_EntryB);
                    break;
                case "cmdbutton07":
                    loadModule(pxeOfflinePointsVerifB);
                    break;
                case "cmdbutton08":
                    loadModule(pxeCardRedemption);
                    break;
                case "cmdbutton09":
                    loadModule(pxeCardPreOrder);
                    break;
                case "cmdbutton10":
                    loadModule(pxePointsInquiry);
                    break;
                case "cmdbutton11":
                    if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to exit?")== true){
                        CommonUtils.closeStage(cmdButton11);
                        break;
                    } else return;
                case "btnhome":
                    dataPane.getChildren().clear();
                    buttonClick();
                    loadMainGui();
                    mainKeyCode();
                    break;
                default:
                    ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
            }
        }
    }
    
    //Animation For Time Display
    public void PlayTime(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {            
        Calendar cal = Calendar.getInstance();
        int second = cal.get(Calendar.SECOND);
        
        Date date;
        date = poGRider.getServerDate();
        
        String fmtHourMin = "h:mm";
        String fmtSeconds = "ss";
        String fmtCurrentDay = "EEEE";
        
        DateFormat dftHourMin = new SimpleDateFormat(fmtHourMin);
        DateFormat dftSeconds = new SimpleDateFormat(fmtSeconds);
        DateFormat dftCurrentDay = new SimpleDateFormat(fmtCurrentDay);
        
        String formattedTime= dftHourMin.format(date);
        String formattedSec = dftSeconds.format(date);
        String formattedDay = dftCurrentDay.format(date);
        
        lblTime.setText(formattedTime);
        lblSeconds.setText(formattedSec);
        lblDay.setText(formattedDay);
        lblDate.setText(CommonUtils.xsDateLong(date));
        
    }),
         new KeyFrame(Duration.seconds(1))
    );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    private AnchorPane LoadScene(String foURL)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(foURL));
       
        Object fxObj = getController(foURL);
        fxmlLoader.setController(fxObj);
   
        AnchorPane root = (AnchorPane) fxmlLoader.load();
        loadProgressbar();
        return root;
    }
    
    public void loadMainInfo(){
        ResultSet name;
        lblBranch.setText((String) poGRider.getBranchName());
        
        String lsQuery = "SELECT" +
                            "  IFNULL(c.sCompnyNm, 'CLIENT NOT REGISTERED') sUserName" +
                        " FROM xxxSysUser a" +
                            ", Employee_Master001 b" +
                                " LEFT JOIN Client_Master c" +
                                    " ON b.sEmployID = c.sClientID" +
                        " WHERE sUserIDxx = " + SQLUtil.toSQL(poGRider.getUserID()) +
                                " AND a.sEmployNo = b.sEmployID";
        
        name= poGRider.executeQuery(lsQuery);
        try {
            if (name.next()){
                pxeUserName = name.getString("sUserName");
            }
            lblName.setText(pxeUserName);
        } catch (SQLException ex) {
            Logger.getLogger(GCardAppFxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private Object getController(String fsValue){   
        switch (fsValue){
            case pxeCardConnect:
                CardConnectController loCardConnect = new CardConnectController();
                loCardConnect.setGRider(poGRider);
                loCardConnect.setMainController(this);
                
                return loCardConnect;
            case "CardIssuance.fxml":
                CardIssuanceController loCardIssuance = new CardIssuanceController();
                loCardIssuance.setGRider(poGRider);
                loCardIssuance.setMainController(this);
                
                return loCardIssuance;
                
            case "CardActivation.fxml":
                CardActivationController loCardActivation = new CardActivationController();
                loCardActivation.setGRider(poGRider);
                loCardActivation.setMainController(this);
                
                return loCardActivation;
            
            case "CardSuspension.fxml":
                CardSuspensionController loCardSuspesion = new CardSuspensionController();
                loCardSuspesion.setGRider(poGRider);
                loCardSuspesion.setMainController(this);
                
                return loCardSuspesion;
            
            case "CardPrinting.fxml":
                CardPrintingController loCardPrintingController = new CardPrintingController();
                loCardPrintingController.setGRider(poGRider);
                loCardPrintingController.setMainController(this);
                
                return loCardPrintingController;
                
            case "CardEncoding.fxml":
                CardEncodingController loCardEncoding = new CardEncodingController();
                loCardEncoding.setGRider(poGRider);
                loCardEncoding.setMainController(this);
                
                return loCardEncoding;  
            
            case "CardApplication.fxml":
                CardApplicationController loCardApplication = new CardApplicationController();
                loCardApplication.setGRider(poGRider);
                loCardApplication.setMainController(this);
                
                return loCardApplication;
                
            case "CardApplicationB.fxml":
                CardApplicationBController loCardApplicationB = new CardApplicationBController();
                loCardApplicationB.setGRider(poGRider);
                loCardApplicationB.setMainController(this);
                
                return loCardApplicationB;
                
            case "OfflinePoints_Verification.fxml":
                OfflinePoints_VerificationController loOfflineVerif = new OfflinePoints_VerificationController();
                loOfflineVerif.setGRider(poGRider);
                loOfflineVerif.setMainController(this);
                
                return loOfflineVerif;
                
            case "CardApplicationVerification.fxml":
                CardApplicationVerificationController loCardAppVerif = new CardApplicationVerificationController();
                loCardAppVerif.setGRider(poGRider);
                loCardAppVerif.setMainController(this);
                
                return loCardAppVerif;
               
                
            case "OfflinePoints_Entry.fxml":
                OfflinePoints_EntryController loOfflinePoints_Entry = new OfflinePoints_EntryController();
                loOfflinePoints_Entry.setGRider(poGRider);
                loOfflinePoints_Entry.setMainController(this);
                
                return loOfflinePoints_Entry;
                
            case "OnlinePoints_Entry.fxml":
                OnlinePoints_EntryController loOnlinePoints_Entry = new OnlinePoints_EntryController();
                loOnlinePoints_Entry.setGRider(poGRider);
                loOnlinePoints_Entry.setMainController(this);
                
                return loOnlinePoints_Entry;
                
            case "PointsInquiry.fxml":
                PointsInquiryController loPointsInquiry = new PointsInquiryController();
                loPointsInquiry.setGRider(poGRider);
                loPointsInquiry.setMainController(this);
                
                return loPointsInquiry;
                
            case "PointsUpdate.fxml":
                PointsUpdateController loPointsUpdate = new PointsUpdateController();
                loPointsUpdate.setGRider(poGRider);
                loPointsUpdate.setMainController(this);
                
                return loPointsUpdate;
                
            case "OnlinePoints_Register.fxml":
                OnlinePoints_RegisterController loOnlinePointsRegister = new OnlinePoints_RegisterController();
                loOnlinePointsRegister.setGRider(poGRider);
                loOnlinePointsRegister.setMainController(this);
                
                return loOnlinePointsRegister;
                
            case "CardOthers.fxml":
                CardOthersController loCardOtherController = new CardOthersController();
                loCardOtherController.setMainController(this);
                
                return loCardOtherController;
            
            case "CardRedemption.fxml":
                CardRedemptionController loCardRedemption = new CardRedemptionController();;
                loCardRedemption.setGRider(poGRider);
                loCardRedemption.setMainController(this);
                
                return loCardRedemption;
            
            case "CardApplication_Register.fxml":
                CardApplication_RegisterController loCardAppRegister = new CardApplication_RegisterController();
                loCardAppRegister.setGRider(poGRider);
                loCardAppRegister.setMainController(this);
                
                return loCardAppRegister;
                
                
            case "CardApplication_RegisterB.fxml":
                CardApplication_RegisterBController loCardAppRegisterB = new CardApplication_RegisterBController();
                loCardAppRegisterB.setGRider(poGRider);
                loCardAppRegisterB.setMainController(this);
                
                return loCardAppRegisterB;
                
                
            case "OfflinePoints_Register.fxml":
                OfflinePoints_RegisterController loOfflinePoints_Register = new OfflinePoints_RegisterController();;
                loOfflinePoints_Register.setGRider(poGRider);
                loOfflinePoints_Register.setMainController(this);
                
                return loOfflinePoints_Register;
                
            case "OfflinePoints_EntryB.fxml":
                OfflinePoints_EntryBController loOfflinePoints_EntryB = new OfflinePoints_EntryBController();
                loOfflinePoints_EntryB.setGRider(poGRider);
                loOfflinePoints_EntryB.setMainController(this);
                
                return loOfflinePoints_EntryB;
            
            case "OfflinePoints_VerificationB.fxml":
                OfflinePoints_VerificationBController loOfflinePoints_VerificationB = new OfflinePoints_VerificationBController();
                loOfflinePoints_VerificationB.setGRider(poGRider);
                loOfflinePoints_VerificationB.setMainController(this);
                
                return loOfflinePoints_VerificationB;
                
            case "CardPreOrder.fxml":
                CardPreOrderController loCardPreOrder = new CardPreOrderController();
                loCardPreOrder.setGRider(poGRider);
                loCardPreOrder.setMainController(this);
                
                return loCardPreOrder;
            
            case "OfflinePoints_RegisterM.fxml":
                OfflinePoints_RegisterMController loOffPointsRegM = new OfflinePoints_RegisterMController();
                loOffPointsRegM.setGRider(poGRider);
                loOffPointsRegM.setMainController(this);
                
                return loOffPointsRegM;
            
            default:
                return null;
        }
    }
    
    private void setScene(AnchorPane foPane){
        dataPane.getChildren().clear();
        dataPane.getChildren().add(foPane);  
    }
}
