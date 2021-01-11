package org.rmj.gcardappfx.views;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.F3;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.textfield.CustomTextField;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.client.agent.XMClient;
import org.rmj.gcard.trans.agentFX.XMGCApplication;

public class CardApplicationController implements Initializable {

    @FXML
    private TextField txtField00;
    @FXML
    private TextField txtField01;
    @FXML
    private TextField txtField02;
    @FXML
    private TextField txtField04;
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
    private TextField txtField09;
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
    private CustomTextField txtField05;
    @FXML
    private CustomTextField txtField08;
    @FXML
    private CustomTextField txtField80;
    @FXML
    private CustomTextField txtField81;
    @FXML
    private CustomTextField txtField03;
    @FXML
    private ComboBox cmbCardClass;
    
    private GCardAppFxController mainController ;
    public static final Image search = new Image("org/rmj/gcardappfx/images/search.png");
    private ToggleGroup cardType;
    private final String pxeModuleName = "G-Card Application";
    public final static String pxeCardApplication_Register = "CardApplication_Register.fxml";
    public final static String pxeOfflinePoints_Entry = "OfflinePoints_Entry.fxml";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    public final static String pxeOnlinePointsEntry = "OnlinePoints_Entry.fxml";
    private String psBranchCd = "";
    private static GRider poGRider;
    private Integer pnIndex = -1;
    private final String pxeDateFormat = "yyyy-MM-dd";
    private int pnEditMode = -1;
    private String pxeCurrentDate;
    private XMGCApplication poTrans;
    private boolean pbLoaded = false;
    
    ObservableList<String> cAppType = FXCollections.observableArrayList("Replacement", "New", "Renewal");
    ObservableList<String> cModePurchase = FXCollections.observableArrayList("Cash", "Installment", "Credit Card", "Gift Coupon","GCard Points");
    ObservableList<String> cCardClass = FXCollections.observableArrayList("Smartcard", "Digital/QR Code");

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
        
        txtField80.focusedProperty().addListener(txtField_Focus);
        txtField81.focusedProperty().addListener(txtField_Focus);
        
        txtField00.focusedProperty().addListener(txtField_Focus);
        txtField01.focusedProperty().addListener(txtField_Focus);
        txtField02.focusedProperty().addListener(txtField_Focus);
        txtField03.focusedProperty().addListener(txtField_Focus);
        txtField04.focusedProperty().addListener(txtField_Focus);
        txtField05.focusedProperty().addListener(txtField_Focus);
        txtField06.focusedProperty().addListener(txtField_Focus);
        txtField07.focusedProperty().addListener(txtField_Focus);
        txtField08.focusedProperty().addListener(txtField_Focus);
        txtField09.focusedProperty().addListener(txtField_Focus);
        
        txtField80.setOnKeyPressed(this::txtField_KeyPressed);
        txtField81.setOnKeyPressed(this::txtField_KeyPressed);
        
        txtField00.setOnKeyPressed(this::txtField_KeyPressed);
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        txtField02.setOnKeyPressed(this::txtField_KeyPressed);
        txtField03.setOnKeyPressed(this::txtField_KeyPressed);
        txtField04.setOnKeyPressed(this::txtField_KeyPressed);
        txtField05.setOnKeyPressed(this::txtField_KeyPressed);
        txtField06.setOnKeyPressed(this::txtField_KeyPressed);
        txtField07.setOnKeyPressed(this::txtField_KeyPressed);
        txtField08.setOnKeyPressed(this::txtField_KeyPressed);
        txtField09.setOnKeyPressed(this::txtField_KeyPressed);
        
        txtField80.setLeft(new ImageView(search));
        txtField81.setLeft(new ImageView(search));
        txtField03.setLeft(new ImageView(search));
        txtField05.setLeft(new ImageView(search));
        txtField08.setLeft(new ImageView(search));
        
        String lsAllwNoChip = CommonUtils.getConfiguration(poGRider, "dNoChipGC", psBranchCd);
        
        if (!lsAllwNoChip.equals("1900-01-01")){
            if (SQLUtil.toDate(lsAllwNoChip, SQLUtil.FORMAT_SHORT_DATE).before(poGRider.getServerDate()) ||
                SQLUtil.toDate(lsAllwNoChip, SQLUtil.FORMAT_SHORT_DATE).equals(poGRider.getServerDate())){
                cCardClass = FXCollections.observableArrayList("Smartcard", "Digital/QR Code", "Non-chip card");
            }
        }
        
        loadGui();
        clearFields();
        
        pbLoaded = true;
    }
    
    public void setBranchCd(String fsBranchCd) {
        this.psBranchCd = fsBranchCd;
    }
    
    public int loadParameter(int fnValue){
        switch(fnValue){
            case 0:
                poTrans.newTransaction();
                txtField80.setText((String) poTrans.getBranch().getMaster("sBranchCd"));
                txtField81.setText((String) poTrans.getBranch().getMaster("sBranchNm"));
                txtField00.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2"));
                break;
            case 5:
                txtField05.setText((String) poTrans.getSource().getMaster("sDescript"));
                break;
            case 8:
                txtField08.setText((String) poTrans.getMCModel().getMaster("sModelNme"));
                break;
            default:
        }
        return fnValue;
    }
    
    public void initButton(int fnValue) {
           boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
           if(lbShow) mainController.setFocus(txtField01);
           txtField00.setMouseTransparent(lbShow);
           txtField04.setDisable(lbShow);
    }

    public void setMainController(GCardAppFxController mainController) {
    this.mainController = mainController ;
    }    
    
    public void loadGui(){
        mainController.cmdButton00.setText("HOME");
        mainController.cmdButton01.setText("SEARCH");
        mainController.cmdButton02.setText("SAVE");
        mainController.cmdButton03.setText("HISTORY");
        mainController.cmdButton04.setText("");
        mainController.cmdButton05.setText("");
        mainController.cmdButton06.setText("");
        mainController.cmdButton07.setText("");
        mainController.cmdButton08.setText("");
        mainController.cmdButton09.setText("");
        mainController.cmdButton10.setText("");
        mainController.cmdButton11.setText("");
        
        mainController.tooltip00.setText("F1");
        mainController.tooltip01.setText("F2");
        mainController.tooltip02.setText("F3");
        mainController.tooltip03.setText("F4");
        mainController.tooltip04.setText("x");
        mainController.tooltip05.setText("x");
        mainController.tooltip06.setText("x");
        mainController.tooltip07.setText("x");
        mainController.tooltip08.setText("x");
        mainController.tooltip09.setText("x");
        mainController.tooltip10.setText("x");
        mainController.tooltip11.setText("x"); 

        mainController.cmdButton00.setDisable(false);
        mainController.cmdButton01.setDisable(false);
        mainController.cmdButton02.setDisable(false);
        mainController.cmdButton03.setDisable(false);
        mainController.cmdButton04.setDisable(true);
        mainController.cmdButton05.setDisable(true);
        mainController.cmdButton06.setDisable(true);
        mainController.cmdButton07.setDisable(true);
        mainController.cmdButton08.setDisable(true);
        mainController.cmdButton09.setDisable(true);
        mainController.cmdButton10.setDisable(true);
        mainController.cmdButton11.setDisable(true);
        
        mainController.setSiteMap(1);

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
    
    private void loadTransaction(){
        XMClient loClient = (XMClient) poTrans.getClient();
       
       txtField01.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
       if (poTrans.getMaster("sPrevGCrd")!= null){
           txtField02.setText((String) poTrans.getMaster("sPrevGCrd").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
       }else{
            txtField02.setText((String) poTrans.getMaster("sPrevGCrd"));
       }
       txtField03.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
       txtField04.setText((String) loClient.getMaster("sAddressx") + ", " +loClient.getTown().getMaster("sTownName") + " " +loClient.getTown().getProvince().getMaster("sProvName") +" "+ loClient.getTown().getMaster("sZippCode"));
       txtField05.setText((String) poTrans.getMaster("sSourceCd"));
       txtField06.setText((String) poTrans.getMaster("sSourceNo"));
       txtField07.setText((String) poTrans.getMaster("sNmOnCard"));
       txtField08.setText((String) poTrans.getMCModel().getMaster("sModelNme"));
       txtField09.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nAmtPaidx").toString()), "###0.00"));
        
       if (poTrans.getMaster("cCardType").toString().equals("0")) {
            rbtn00.setSelected(true);
        }else {
            rbtn01.setSelected(true);
       }
       
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
       if (poTrans.getMaster("cTranStat") == null){
            setTransTat("0");
       }else{
        setTransTat((String) poTrans.getMaster("cTranStat"));
       }
       pnEditMode = poTrans.getEditMode();
    }
    
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
                switch(pnIndex){
                    case 3:
                        if (txtField03.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtField03);
                            return;
                        }

                        if (poTrans.searchField("sClientNm", txtField03.getText()) ==true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField03);
                        }
                        break;
                    case 5:
                        if (poTrans.searchField("sDescript", txtField05.getText()) ==true){
                            loadParameter(5);
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField05);
                        }
                        break;
                    case 8:
                        if (poTrans.searchField("sModelNme", txtField08.getText()+"%") ==true){
                            loadParameter(8);
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField08);
                        }
                        break;
                    case 80:
                        if (poTrans.searchField("sBranchCD", txtField80.getText()) ==true){
                            clearFields();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField80);
                        }
                        break;
                    case 81:                        
                        if (poTrans.searchField("sBranchNm", txtField81.getText()) ==true){
                            clearFields();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField81);
                        }
                        break;
                }
                break;
            case "cmdbutton02":
                if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to save this application?")== true){
                    sendOtherInfo();
                    if (poTrans.saveUpdate()==true){
                       ShowMessageFX.Information(null, pxeModuleName, "Application successfully saved!");

                       //mac 2019.07.15
                       //display the QR Code of G-Card Application

                       if (poTrans.getMaster("cDigitalx").toString().equals("1")){
                            if (poTrans.displayGCardApp())
                                ShowMessageFX.Information(null, pxeModuleName, poTrans.getMessage());
                            else
                                ShowMessageFX.Error(null, pxeModuleName, poTrans.getMessage());
                       }
                       clearFields();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }
                break;
            case "cmdbutton03":
                mainController.loadModule(pxeCardApplication_Register);
                break;
            case "cmdbutton04":
            case "cmdbutton05":
            case "cmdbutton06":
            case "cmdbutton07":
            case "cmdbutton08":
            case "cmdbutton09":
            case "cmdbutton10":
            case "cmdbutton11":
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
    
    private void sendOtherInfo(){
        poTrans.setMaster("sSrceInfo", getSourceInfo());
        poTrans.setMaster("sReasonsx", getReasons());
        
        if(rbtn00.isSelected() == true){
            poTrans.setMaster("cCardType", String.valueOf("0"));
        }else if(rbtn01.isSelected() == true){
            poTrans.setMaster("cCardType", String.valueOf("1"));
        }
        
        poTrans.setMaster("cDigitalx", String.valueOf(cmbCardClass.getSelectionModel().getSelectedIndex())); 
        poTrans.setMaster("cPurcMode", String.valueOf(cmbPurchase.getSelectionModel().getSelectedIndex()));
        poTrans.setMaster("cApplType", String.valueOf(cmbAppliType.getSelectionModel().getSelectedIndex()));
    }
    
    public void txtField_KeyPressed(KeyEvent event) {
        TextField txtField = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (event.getCode() == ENTER || event.getCode() == F3) {
                switch (lnIndex){
                    case 3:
                        if (lsValue.equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            return;
                        }

                        if (poTrans.searchField("sClientNm", lsValue) ==true){
                            loadTransaction();
                        }else{
                            return;
                        }
                        break;
                    case 5:
                        if (poTrans.searchField("sDescript", lsValue) ==true){
                            loadParameter(5);
                        }else{
                            return;
                        }
                        break;
                    case 8:
                        if (lsValue.equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            return;
                        }
                        
                        if (poTrans.searchField("sModelNme", lsValue) ==true){
                            loadParameter(8);
                        }else{
                            return;
                        }
                        break;
                    case 80:
                        if (lsValue.equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            return;
                        }
                        
                        if (poTrans.searchField("sBranchCD", lsValue) ==true){
                            clearFields();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                        break;
                    case 81:
                        if (lsValue.equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            return;
                        }
                        
                        if (poTrans.searchField("sBranchNm", lsValue) ==true){
                            clearFields();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                        break;
                }
        }        
        if (event.getCode() == DOWN || event.getCode() == ENTER){
             CommonUtils.SetNextFocus(txtField);                  
        }
        if (event.getCode() == UP){
             CommonUtils.SetPreviousFocus(txtField);                 
        }
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    public void clearFields() {
        txtField80.setText("");
        txtField81.setText("");
        txtField01.setText(CommonUtils.xsDateLong(CommonUtils.toDate(pxeCurrentDate)));
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText("");
        txtField05.setText("");
        txtField06.setText("");
        txtField07.setText("");
        txtField08.setText("");
        txtField09.setText("0.0");
        
        cardType = new ToggleGroup();
        rbtn00.setToggleGroup(cardType);
        rbtn01.setToggleGroup(cardType);
        
        cmbPurchase.setItems(cModePurchase);
        cmbPurchase.getSelectionModel().select(0);
        
        cmbAppliType.setItems(cAppType);
        cmbAppliType.getSelectionModel().select(1);
        
        //todo:
        //validate if the branch is allowed to issue non-chip card
        cmbCardClass.setItems(cCardClass);
        cmbCardClass.getSelectionModel().select(0);
        
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        loadParameter(0);
        pnEditMode = poTrans.getEditMode();
        initButton(pnEditMode);
        setTransTat("-1");
        
        loadCheckInfo();
    }
    
    final ChangeListener<? super Boolean> txtField_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/
            switch (lnIndex){
               case 0:
                   break;
               case 80:
                   if(!lsValue.equals(poTrans.getBranch().getMaster("sBranchCd"))){
                     txtField.setText((String) poTrans.getBranch().getMaster("sBranchCd"));
                   }
                   break;
               case 81:
                   if(!lsValue.equals(poTrans.getBranch().getMaster("sBranchNm"))){
                     txtField.setText((String) poTrans.getBranch().getMaster("sBranchNm"));
                   }
                   break;
               case 2:
                    poTrans.setMaster("sPrevGCrd", lsValue.replaceAll("-", ""));
                    txtField02.setText((String) poTrans.getMaster("sPrevGCrd"));
                    break;
               case 3: 
               case 4:
                   break;
               case 5:
                   if(!lsValue.equals(poTrans.getSource().getMaster("sDescript"))){
                     txtField.setText("");
                     poTrans.setMaster("sSourceCd", "");
                   }
                   break;
               case 6:
                   poTrans.setMaster("sSourceNo", lsValue);
                   break;
               case 7:
                   poTrans.setMaster("sNmOnCard", lsValue);
                   break;
               case 8:
                   if(!lsValue.equals(poTrans.getMCModel().getMaster("sModelNme"))){
                     txtField.setText("");
                     poTrans.setMaster("sModelIDx", "");
                   }
                   break;
               case 1:
                   if (CommonUtils.isDate(lsValue, pxeDateFormat)){
                        poTrans.setMaster("dTransact", CommonUtils.toDate(lsValue));
                    } else{
                        ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                        poTrans.setMaster("dTransact", CommonUtils.toDate(pxeCurrentDate));
                    }
                    /*get the value from the class*/
                    txtField.setText(CommonUtils.xsDateLong((Date)poTrans.getMaster("dTransact")));
                    return;
               case 9: /* nAmtPaidx */
                   double nAmtPaidx = 0.00;
                    try {
                        /*this must be numeric*/
                        nAmtPaidx = Double.parseDouble(lsValue);
                    } catch (NumberFormatException e) {
                        nAmtPaidx = 0.00;
                        txtField.setText("0.00");
                    }
                    poTrans.setMaster("nAmtPaidx",  nAmtPaidx);
                    txtField.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nAmtPaidx"), "0.00"));
                    break;
                  
                default:
                   ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
                   return;
            }
           
        } else{
            switch (lnIndex){
                case 1: /*dTransact*/
                    try{
                        txtField.setText(CommonUtils.xsDateShort(lsValue));
                    }catch(ParseException e){
                        ShowMessageFX.Error(e.getMessage(), pxeModuleName, null);
                    }
                    txtField.selectAll();
                    break;
                default:
            }
            pnIndex = lnIndex;
            txtField.selectAll();
        }
    };
    
    
    public String getReasons(){
        String[] arrReasons = new String[10];
        
        arrReasons[0]= (chk00.isSelected() == true ? "1" : "0");
        arrReasons[1]= (chk01.isSelected() == true ? "1" : "0");
        arrReasons[2]= (chk02.isSelected() == true ? "1" : "0");
        arrReasons[3]= (chk03.isSelected() == true ? "1" : "0");
        arrReasons[4]= (chk04.isSelected() == true ? "1" : "0");
        arrReasons[5]= (chk05.isSelected() == true ? "1" : "0");
        arrReasons[6]= (chk06.isSelected() == true ? "1" : "0");
        arrReasons[7]= (chk07.isSelected() == true ? "1" : "0");
        arrReasons[8]= (chk08.isSelected() == true ? "1" : "0");
        arrReasons[9]= (chk09.isSelected() == true ? "1" : "0");
         
        String sReasonx =  String.join("", arrReasons);
        return sReasonx;
    } 
    
    public String getSourceInfo(){
        String[] arrSourceInfo = new String[15];

        arrSourceInfo[0]= (chk10.isSelected() == true ? "1" : "0");
        arrSourceInfo[1]= (chk11.isSelected() == true ? "1" : "0");
        arrSourceInfo[2]= (chk12.isSelected() == true ? "1" : "0");
        arrSourceInfo[3]= (chk13.isSelected() == true ? "1" : "0");
        arrSourceInfo[4]= (chk14.isSelected() == true ? "1" : "0");
        arrSourceInfo[5]= (chk15.isSelected() == true ? "1" : "0");
        arrSourceInfo[6]= (chk16.isSelected() == true ? "1" : "0");
        arrSourceInfo[7]= (chk17.isSelected() == true ? "1" : "0");
        arrSourceInfo[8]= (chk18.isSelected() == true ? "1" : "0");
        arrSourceInfo[9]= (chk19.isSelected() == true ? "1" : "0");
        arrSourceInfo[10]= (chk20.isSelected() == true ? "1" : "0");
        arrSourceInfo[11]= (chk21.isSelected() == true ? "1" : "0");
        arrSourceInfo[12]= (chk22.isSelected() == true ? "1" : "0");
        arrSourceInfo[13]= (chk23.isSelected() == true ? "1" : "0");
        arrSourceInfo[14]= (chk24.isSelected() == true ? "1" : "0");
        
        String sSourceInfo =  String.join("", arrSourceInfo);
        return sSourceInfo;
    }
}
