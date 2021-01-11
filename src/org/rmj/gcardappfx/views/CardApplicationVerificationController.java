
package org.rmj.gcardappfx.views;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F3;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.textfield.CustomTextField;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.StringUtil;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.agentfx.ui.showFXDialog;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.client.agent.XMClient;
import org.rmj.gcard.trans.agentFX.XMGCApplication;

public class CardApplicationVerificationController implements Initializable {

    @FXML
    private TextField txtField00;
    @FXML
    private TextField txtField01;
    @FXML
    private TextField txtField02;
    @FXML
    private TextField txtField03;
    @FXML
    private TextField txtField04;
    @FXML
    private TextField txtField05;
    @FXML
    private ComboBox cmbPurchase;
    @FXML
    private TextField txtField06;
    @FXML
    private TextField txtField07;
    @FXML
    private ComboBox cmbAppliType;
    @FXML
    private RadioButton rbtn00;
    @FXML
    private RadioButton rbtn01;
    @FXML
    private TextField txtField08;
    @FXML
    private CheckBox chk00;
    @FXML
    private CheckBox chk01;
    @FXML
    private CheckBox chk02;
    @FXML
    private CheckBox chk03;
    @FXML
    private CheckBox chk05;
    @FXML
    private CheckBox chk06;
    @FXML
    private CheckBox chk07;
    @FXML
    private CheckBox chk08;
    @FXML
    private CheckBox chk09;
    @FXML
    private CheckBox chk04;
    @FXML
    private CheckBox chk10;
    @FXML
    private CheckBox chk11;
    @FXML
    private CheckBox chk12;
    @FXML
    private CheckBox chk13;
    @FXML
    private CheckBox chk14;
    @FXML
    private CheckBox chk15;
    @FXML
    private CheckBox chk16;
    @FXML
    private CheckBox chk17;
    @FXML
    private CheckBox chk18;
    @FXML
    private CheckBox chk19;
    @FXML
    private CheckBox chk20;
    @FXML
    private CheckBox chk21;
    @FXML
    private CheckBox chk22;
    @FXML
    private CheckBox chk23;
    @FXML
    private CheckBox chk24;
    @FXML
    private Label lblTitle;
    @FXML
    private GridPane NodePane01;
    @FXML
    private GridPane NodePane00;
    @FXML
    private ImageView imageview;
    @FXML
    private CustomTextField txtSeeks00;
    @FXML
    private CustomTextField txtSeeks01;
    @FXML
    private CustomTextField txtSeeks02;
    @FXML
    private TextField txtField09;
    @FXML
    private ComboBox cmbCardClass;
    
    private GCardAppFxController mainController ;
    private static final Image search = new Image("org/rmj/gcardappfx/images/search.png");
    private final String pxeModuleName = "G-Card Application Verification";
    private boolean pbLoaded = false;
    public final static String pxeOfflinePoints_Entry = "OfflinePoints_Entry.fxml";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    public final static String pxeCardApplication = "CardApplication.fxml";
    private String psBranchCd = "";
    private static GRider poGRider;
    private Integer pnIndex = -1;
    private final String pxeDateFormat = "yyyy-MM-dd";
    private int pnEditMode = -1;
    private String pxeCurrentDate;
    private XMGCApplication poTrans;
    
    ObservableList<String> cAppType = FXCollections.observableArrayList("Replacement", "New", "Renewal");
    ObservableList<String> cModePurchase = FXCollections.observableArrayList("Cash", "Installment", "Credit Card", "Gift Coupon","GCard Points");
    ObservableList<String> cCardClass = FXCollections.observableArrayList("Smartcard", "Digital/QR Code", "Non-chip card");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..",pxeModuleName, "Please Inform MIS/SEG" );
            System.exit(0);
        }
        
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        pxeCurrentDate = poGRider.getServerDate().toString();
        
        poTrans = new XMGCApplication(poGRider, psBranchCd, false);
        
        mainController.rootPane.setOnKeyReleased(this::handleKeyCode);
        mainController.cmdButton00.setOnAction(this::cmdButton_Click);
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
        
        txtSeeks00.setLeft(new ImageView(search));
        txtSeeks01.setLeft(new ImageView(search));
        txtSeeks02.setLeft(new ImageView(search));
        
        txtSeeks00.focusedProperty().addListener(txtSeeks_Focus);
        txtSeeks01.focusedProperty().addListener(txtSeeks_Focus);
        txtSeeks02.focusedProperty().addListener(txtSeeks_Focus);

        txtSeeks00.setOnKeyPressed(this::txtSeeks_KeyPressed);
        txtSeeks01.setOnKeyPressed(this::txtSeeks_KeyPressed);
        txtSeeks02.setOnKeyPressed(this::txtSeeks_KeyPressed);
        
        clearFields();
        loadGui();
        
        pbLoaded = true;
    }
    
    public void initButton(int fnValue) {
       boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
       
       if (lbShow) mainController.setFocus(txtSeeks00);
       txtField00.setMouseTransparent(lbShow);
       txtField01.setDisable(lbShow);
       txtField02.setDisable(lbShow);
       txtField03.setDisable(lbShow);
       txtField04.setDisable(lbShow);
       txtField05.setDisable(lbShow);
       txtField06.setDisable(lbShow);
       txtField07.setDisable(lbShow);
       txtField08.setDisable(lbShow);
       txtField09.setDisable(lbShow);
       
       cmbAppliType.setMouseTransparent(lbShow);
       cmbPurchase.setMouseTransparent(lbShow);
       cmbCardClass.setMouseTransparent(lbShow);
       
       rbtn00.setMouseTransparent(lbShow);
       rbtn01.setMouseTransparent(lbShow);
       
       chk00.setMouseTransparent(lbShow);
       chk01.setMouseTransparent(lbShow);
       chk02.setMouseTransparent(lbShow);
       chk03.setMouseTransparent(lbShow);
       chk04.setMouseTransparent(lbShow);
       chk05.setMouseTransparent(lbShow);
       chk06.setMouseTransparent(lbShow);
       chk07.setMouseTransparent(lbShow);
       chk08.setMouseTransparent(lbShow);
       chk09.setMouseTransparent(lbShow);
       chk10.setMouseTransparent(lbShow);
       chk11.setMouseTransparent(lbShow);
       chk12.setMouseTransparent(lbShow);
       chk13.setMouseTransparent(lbShow);
       chk14.setMouseTransparent(lbShow);
       chk15.setMouseTransparent(lbShow);
       chk16.setMouseTransparent(lbShow);
       chk17.setMouseTransparent(lbShow);
       chk18.setMouseTransparent(lbShow);
       chk19.setMouseTransparent(lbShow);
       chk20.setMouseTransparent(lbShow);
       chk21.setMouseTransparent(lbShow);
       chk22.setMouseTransparent(lbShow);
       chk23.setMouseTransparent(lbShow);
       chk24.setMouseTransparent(lbShow);
       
    }

    public void setMainController(GCardAppFxController mainController) {
        this.mainController = mainController ;
    }
    
    public void setBranchCd(String fsBranchCd) {
        this.psBranchCd = fsBranchCd;
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    public void loadGui(){
        mainController.cmdButton00.setText("HOME");
        mainController.cmdButton01.setText("SEARCH");
        mainController.cmdButton02.setText("VERIFY");
        mainController.cmdButton03.setText("VOID");
        mainController.cmdButton04.setText("CANCEL");
        mainController.cmdButton05.setText("");
        mainController.cmdButton06.setText("");
        mainController.cmdButton07.setText("");
        mainController.cmdButton08.setText("");
        mainController.cmdButton09.setText("");
        mainController.cmdButton10.setText("");
        mainController.cmdButton11.setText("UPDT MOBILE");
        
        mainController.tooltip00.setText("F1");
        mainController.tooltip01.setText("F2");
        mainController.tooltip02.setText("F3");
        mainController.tooltip03.setText("F4");
        mainController.tooltip04.setText("F5");
        mainController.tooltip05.setText("x");
        mainController.tooltip06.setText("x");
        mainController.tooltip07.setText("x");
        mainController.tooltip08.setText("x");
        mainController.tooltip09.setText("x");
        mainController.tooltip10.setText("x");
        mainController.tooltip11.setText("F12"); 

        mainController.cmdButton00.setDisable(false);
        mainController.cmdButton01.setDisable(false);
        mainController.cmdButton02.setDisable(false);
        mainController.cmdButton03.setDisable(false);
        mainController.cmdButton04.setDisable(false);
        mainController.cmdButton05.setDisable(true);
        mainController.cmdButton06.setDisable(true);
        mainController.cmdButton07.setDisable(true);
        mainController.cmdButton08.setDisable(true);
        mainController.cmdButton09.setDisable(true);
        mainController.cmdButton10.setDisable(true);
        mainController.cmdButton11.setDisable(true);
        
        mainController.setSiteMap(2);

    }
    
    private void cmdButton_Click(ActionEvent event) {
        String lsButton = ((Button)event.getSource()).getId().toLowerCase();
        switch (lsButton){
            case "cmdbutton00":
                mainController.dataPane.getChildren().clear();
                mainController.buttonClick();
                mainController.loadMainGui();
                mainController.mainKeyCode();
                break;
            case "cmdbutton01":
                switch (pnIndex){
                    case 0:
                        if (poTrans.searchField("sBranchNm", txtSeeks00.getText()) == true){
                            clearFields();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtSeeks00);
                        }
                       break;
                    case 2:
                        if (txtSeeks02.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtSeeks02);
                            return;
                        }
                        
                        if (poTrans.SearchWithCondition("sSourceNo", txtSeeks02.getText(), "a.cTransTat = '0'")==true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtSeeks02);
                        }
                       break;
                    case 1:
                        if (txtSeeks01.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtSeeks01);
                            return;
                        }

                        if (poTrans.SearchWithCondition("sClientNm", txtSeeks01.getText(), "a.cTransTat = '0'") ==true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtSeeks01);
                        }
                        break;
                }
                break;
            case "cmdbutton02":
                if(ShowMessageFX.OkayCancel("Please make sure that the mobile number of the client was active before verifying this transaction. " +
                                            "To change the mobile number, press CANCEL and select the UPDT MOBILE button on the main screen.", 
                                            pxeModuleName, "Do you want to verify this application?") == true){
                    
                    
                    String lsCardNmbr = "";
                    if (poTrans.getMaster("cDigitalx").equals("2")){
                        lsCardNmbr = ShowMessageFX.InputText("Please encode the card number from the G-Card to be issued to the customer.", "Input", "Input Card Number");

                        if (lsCardNmbr.isEmpty()){
                            ShowMessageFX.Information("Card number is empty. Unable to continue verifying G-Card Application.", pxeModuleName, "Unable to verify G-Card Application"); 
                            return;
                        }

                        if (lsCardNmbr.length() > 14){
                            ShowMessageFX.Information("Card number is seems to long. Unable to continue verifying G-Card Application.", pxeModuleName, "Unable to verify G-Card Application"); 
                            return;
                        }

                        if (!StringUtil.isNumeric(lsCardNmbr)){
                            ShowMessageFX.Information("Card number is seems not numeric. Unable to continue verifying G-Card Application.", pxeModuleName, "Unable to verify G-Card Application"); 
                            return;
                        }
                    }
                    
                    if (poTrans.closeTransaction((String) poTrans.getMaster("sTransNox"), lsCardNmbr) == true){
                        ShowMessageFX.Information(null, pxeModuleName, "Application successfully verified!"); 
                        clearFields();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }
                break;
            case "cmdbutton03":
                if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to void this application?")== true){
                    if (poTrans.voidTransaction((String) poTrans.getMaster("sTransNox"))== true){
                        ShowMessageFX.Information(null, pxeModuleName, "Application successfully voided!"); 
                        clearFields();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }
                break;
            case "cmdbutton04":
                if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to cancel this application?")== true){
                    if (poTrans.cancelTransaction((String) poTrans.getMaster("sTransNox"))== true){
                        ShowMessageFX.Information(null, pxeModuleName, "Application successfully cancelled!"); 
                        clearFields();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }
                break;
            case "cmdbutton05":
            case "cmdbutton06":
            case "cmdbutton07":
            case "cmdbutton08":
            case "cmdbutton09":
            case "cmdbutton10":
                break;
            case "cmdbutton11": //update mobile
                JSONObject loJSON =  showFXDialog.updateMobileNo(poGRider, System.getProperty("app.client.id"), System.getProperty("app.gcard.mobile"));
                String lsResult = (String) loJSON.get("result");
                
                if (lsResult.equalsIgnoreCase("success")){
                    txtField09.setText((String) loJSON.get("mobile"));
                    
                    ShowMessageFX.Information((String) loJSON.get("message") + "\n\n" +
                                                "(Note: For remote offices, the update may not reflect immediately to your PC.\n" +
                                                "       The updated data wiil reflect soon when the main office' data was replicated in your server.)",
                                                "Success", "Update Success");
                } else {                    
                    try {
                        loJSON = (JSONObject) new JSONParser().parse(loJSON.get("error").toString());
                        
                        ShowMessageFX.Warning("Update Failed", "Failed - " + (String) loJSON.get("code"), (String) loJSON.get("message"));
                    } catch (ParseException ex) {
                        Logger.getLogger(CardConnectController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                break;
            default:
                ShowMessageFX.Warning(null, pxeModuleName, "Button with name " + lsButton + " not registered.");
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
    
    private void loadParameter(int fnValue){
        switch (fnValue){
            case 0:
                poTrans.newTransaction();
                txtSeeks00.setText((String) poTrans.getBranch().getMaster("sBranchNm"));
                txtField00.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2"));
        }
    }
    
    private void loadTransaction(){       
       XMClient loClient = (XMClient) poTrans.getClient();
       
       txtSeeks00.setText((String) poTrans.getBranch().getMaster("sBranchNm"));
       txtSeeks02.setText((String) poTrans.getMaster("sSourceNo")); 
       txtSeeks01.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
       
       txtField00.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2")); 
       txtField01.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
       if (poTrans.getMaster("sPrevGCrd")!= null){
           txtField02.setText((String) poTrans.getMaster("sPrevGCrd").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
       }else{
            txtField02.setText((String) poTrans.getMaster("sPrevGCrd"));
       }
       txtField03.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
       txtField04.setText((String) loClient.getMaster("sAddressx") + ", " +loClient.getTown().getMaster("sTownName") + " " +loClient.getTown().getProvince().getMaster("sProvName") +" "+ loClient.getTown().getMaster("sZippCode"));
       txtField05.setText((String) poTrans.getSource().getMaster("sDescript"));
       txtField06.setText((String) poTrans.getMaster("sSourceNo"));
       txtField07.setText((String) poTrans.getMaster("sNmOnCard"));
       txtField08.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nAmtPaidx").toString()), "#,##0.00"));
       
       //mac 2020-06-13
       txtField09.setText((String) loClient.getMaster("sMobileNo"));
       System.setProperty("app.client.id", (String) loClient.getMaster("sClientID"));
       System.setProperty("app.gcard.mobile", (String) loClient.getMaster("sMobileNo"));
       
       if (poTrans.getMaster("cCardType").toString().equals("0")) {
            rbtn00.setSelected(true);
        }else {
            rbtn01.setSelected(true);
       }
       
       if (poTrans.getMaster("cDigitalx") != null){
            switch (poTrans.getMaster("cDigitalx").toString()) {
             case "0":
                 cmbCardClass.getSelectionModel().select(0);
                 break;
             case "1":
                 cmbCardClass.getSelectionModel().select(1);
                 break;
             default:
                 cmbCardClass.getSelectionModel().select(2);
                 break;
             }
        }
       
       //mac 2020.0620
       //pass the fsep white and yellow
       poTrans.setWhite((int)poTrans.getMaster("nWhitexxx"));
       poTrans.setYellow((int)poTrans.getMaster("nYellowxx"));
       
       try{
            cmbAppliType.getSelectionModel().select(Integer.parseInt(poTrans.getMaster("cApplType").toString()));
        }catch (NumberFormatException ex){
            cmbAppliType.getSelectionModel().select(String.valueOf(poTrans.getMaster("cApplType").toString()));
        }
       
       try{
            cmbPurchase.getSelectionModel().select(Integer.parseInt(poTrans.getMaster("cPurcMode").toString()));
        }catch (NumberFormatException ex){
            cmbPurchase.getSelectionModel().select(String.valueOf(poTrans.getMaster("cPurcMode").toString()));
        }
       
       loadCheckInfo();
       setTransTat((String) poTrans.getMaster("cTranStat"));
       mainController.cmdButton11.setDisable(false);
       pnEditMode = poTrans.getEditMode();
    }
    
    private void loadCheckInfo(){
       String lbsReasons = poTrans.getMaster("sReasonsx").toString();
       String [] lbReasonsx = lbsReasons.split("");
       
       for (int i=0; i < lbReasonsx.length; i++)
        {
            boolean lbReasons;
            lbReasons = lbReasonsx[i].toString().equals("1");
            switch(i){
                case 0:
                    chk00.selectedProperty().setValue(lbReasons);
                    break;
                case 1:
                    chk01.selectedProperty().setValue(lbReasons);
                    break;
                case 2:
                    chk02.selectedProperty().setValue(lbReasons);
                    break;
                case 3:
                    chk03.selectedProperty().setValue(lbReasons);
                    break;
                case 4:
                    chk04.selectedProperty().setValue(lbReasons);
                    break;
                case 5:
                    chk05.selectedProperty().setValue(lbReasons);
                    break;
                case 6:
                    chk06.selectedProperty().setValue(lbReasons);
                    break;
                case 7:
                    chk07.selectedProperty().setValue(lbReasons);
                    break;
                case 8:
                    chk08.selectedProperty().setValue(lbReasons);
                    break;
                case 9:
                    chk09.selectedProperty().setValue(lbReasons);
                    break;
            }
        }
        
        String lbsSrceInfo = poTrans.getMaster("sSrceInfo").toString();
        String [] lbSrceInfo = lbsSrceInfo.split("");
        
        for (int i=0; i < lbSrceInfo.length; i++)
        {
            boolean lbsSource;
            lbsSource = lbSrceInfo[i].toString().equals("1");
            switch(i){
                case 0:
                    chk10.selectedProperty().setValue(lbsSource);
                    break;
                case 1:
                    chk11.selectedProperty().setValue(lbsSource);
                    break;
                case 2:
                    chk12.selectedProperty().setValue(lbsSource);
                    break;
                case 3:
                    chk13.selectedProperty().setValue(lbsSource);
                    break;
                case 4:
                    chk14.selectedProperty().setValue(lbsSource);
                    break;
                case 5:
                    chk15.selectedProperty().setValue(lbsSource);
                    break;
                case 6:
                    chk16.selectedProperty().setValue(lbsSource);
                    break;
                case 7:
                    chk17.selectedProperty().setValue(lbsSource);
                    break;
                case 8:
                    chk18.selectedProperty().setValue(lbsSource);
                    break;
                case 9:
                    chk19.selectedProperty().setValue(lbsSource);
                    break;
                case 10:
                    chk20.selectedProperty().setValue(lbsSource);
                    break;
                case 11:
                    chk21.selectedProperty().setValue(lbsSource);
                    break;
                case 12:
                    chk22.selectedProperty().setValue(lbsSource);
                    break;
                case 13:
                    chk23.selectedProperty().setValue(lbsSource);
                    break;
                case 14:
                    chk24.selectedProperty().setValue(lbsSource);
                    break;
            }
        }
    }
    
    public void txtSeeks_KeyPressed(KeyEvent event) {
        TextField txtSeeks = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtSeeks.getId().substring(8, 10));
        String lsValue = txtSeeks.getText();
        
        if (event.getCode() == ENTER || event.getCode() == F3) {
                switch (lnIndex){
                    case 0:
                        if (lsValue.equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            return;
                        }
                        
                        if (poTrans.searchField("sBranchNm", lsValue)==true){
                            clearFields();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                       break;
                    case 2:
                        if (lsValue.equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                        return;
                        }
                        
                        if (poTrans.SearchWithCondition("sSourceNo", lsValue, "a.cTransTat = '0'")==true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                       break;
                    case 1:
                        if (lsValue.equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            return;
                        }

                        if (poTrans.SearchWithCondition("sClientNm", lsValue, "a.cTransTat = '0'") ==true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                        break;
                }
        }        
        if (event.getCode() == DOWN || event.getCode() == ENTER){
             CommonUtils.SetNextFocus(txtSeeks);                  
        }
        if (event.getCode() == UP){
             CommonUtils.SetPreviousFocus(txtSeeks);                 
        }
    }
    
    public void clearFields() {
        txtSeeks00.setText("");
        txtSeeks01.setText("");
        txtSeeks02.setText("");
        txtField00.setText("");
        txtField01.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText("");
        txtField05.setText("");
        txtField06.setText("");
        txtField07.setText("");
        txtField08.setText("0.0");
        txtField09.setText("");
        setTransTat("-1");
        
        cmbPurchase.setItems(cModePurchase);
        cmbPurchase.getSelectionModel().select(0);
        
        cmbAppliType.setItems(cAppType);
        cmbAppliType.getSelectionModel().select(1);
        
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        cmbCardClass.setItems(cCardClass);
        cmbCardClass.getSelectionModel().select(0);
        
        mainController.cmdButton11.setDisable(false);

        loadParameter(0);
        pnEditMode = poTrans.getEditMode();
        initButton(pnEditMode);
        loadCheckInfo();
    }
    
    final ChangeListener<? super Boolean> txtSeeks_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtSeeks = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtSeeks.getId().substring(8, 10));
        String lsValue = txtSeeks.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/
            switch (lnIndex){
               case 0:
                   if(!lsValue.equals(poTrans.getBranch().getMaster("sBranchNm"))){
                     txtSeeks.setText((String) poTrans.getBranch().getMaster("sBranchNm"));
                   }
                   break;
               case 1:
               case 2:
                break;
                  
                default:
                   ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtSeeks.getId() + " not registered.");
                   return;
            }
           
        } else {
        }
        pnIndex = lnIndex;
        txtSeeks.selectAll(); 
    };
    
    private void setTransTat(String fsValue){
        switch(fsValue){
            case "0":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/open.png")); break;
            case "1":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/verified.png")); break;
            case "2":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/posted.png")); break;
            case "3":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/cancelled.png")); break;
            case "4":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/void.png")); break;
            default:
                imageview.setImage(new Image("org/rmj/gcardappfx/images/unknown.png"));      
        }
    }    
}
