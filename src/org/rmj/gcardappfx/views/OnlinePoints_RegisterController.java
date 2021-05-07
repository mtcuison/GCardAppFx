
package org.rmj.gcardappfx.views;

import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
import org.rmj.gcard.device.ui.GCardDevice;
import org.rmj.gcard.device.ui.GCardDeviceFactory;
import org.rmj.gcard.trans.agentFX.XMGCOnPoints;

public class OnlinePoints_RegisterController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private GridPane NodePane00;
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
    private TextField txtField06;
    @FXML
    private TextField txtField07;
    @FXML
    private RadioButton rbtn00;
    @FXML
    private RadioButton rbtn01;
    @FXML
    private TextField txtField08;
    @FXML
    private TextField txtField09;
    @FXML
    private TextField txtField10;
    @FXML
    private TextField txtField05;
    @FXML
    private Label lblServerStat;
    @FXML
    private Label lblTranStatus;
    @FXML
    private CustomTextField txtSeeks00;
    @FXML
    private CustomTextField txtSeeks01;
    
    private GCardAppFxController mainController ;
    public static final Image search = new Image("org/rmj/gcardappfx/images/search.png");
    private final String pxeModuleName = "Online Points History";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    public final static String pxeOnlinePointsEntry = "OnlinePoints_Entry.fxml";
    public final static String pxeOfflinePoints_EntryB = "OfflinePoints_EntryB.fxml";
    public final static String pxeOnlinePoints_Entry = "OnlinePoints_Entry.fxml";
    public final static String pxeCardApplicationB = "CardApplicationB.fxml";
    private static GRider poGRider;
    private XMGCOnPoints poTrans;
    private final String pxeDateFormat = "yyyy-MM-dd";
    private String pxeCurrentDate;
    private String psBranchCd = "";
    private int pnIndex = -1;
    private int pnEditMode = -1;
    private boolean pbLoaded = false;
    
    XMClient poClient;
    GCardDevice poDevice;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..", pxeModuleName, "Please inform MIS department.");
            System.exit(0);
        }
        
        pxeCurrentDate = poGRider.getServerDate().toString();
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        
        poTrans = new XMGCOnPoints(poGRider,psBranchCd, false);
        
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
        
        txtSeeks00.focusedProperty().addListener(txtSeeks_Focus);
        txtSeeks01.focusedProperty().addListener(txtSeeks_Focus);
        
        txtSeeks00.setLeft(new ImageView(search));
        txtSeeks01.setLeft(new ImageView(search));
        txtSeeks00.setOnKeyPressed(this::txtSeeks_KeyPressed);
        txtSeeks01.setOnKeyPressed(this::txtSeeks_KeyPressed);
        
        clearFields();
        loadGui();
        pbLoaded = true;
    }
    
    private void initTransaction(){
        poTrans.newTransaction();
        txtField00.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2"));
    }
    
    public void setMainController(GCardAppFxController mainController) {
    this.mainController = mainController ;
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    public void setBranchCd(String fsBranchCd) {
        this.psBranchCd = fsBranchCd;
    }

    public void loadGui(){
        mainController.cmdButton00.setText("HOME");
        mainController.cmdButton01.setText("SEARCH");
        mainController.cmdButton02.setText("CONNECT");
        mainController.cmdButton03.setText("VOID");
        mainController.cmdButton04.setText("CANCEL");
        mainController.cmdButton05.setText("BACK");
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
        mainController.tooltip04.setText("F5");
        mainController.tooltip05.setText("6");
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
        mainController.cmdButton04.setDisable(false);
        mainController.cmdButton05.setDisable(false);
        mainController.cmdButton06.setDisable(true);
        mainController.cmdButton07.setDisable(true);
        mainController.cmdButton08.setDisable(true);
        mainController.cmdButton09.setDisable(true);
        mainController.cmdButton10.setDisable(true);
        mainController.cmdButton11.setDisable(true);

        mainController.setSiteMap(12);
    }
    
    public void loadTransaction(){
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        poClient = (XMClient) poTrans.getGCard().getClient();
        poDevice = (GCardDevice) poTrans.getDeviceInfo();
        
        if (poDevice != null){
            //mac 2019.07.29
            //  if device type is none, we will load the transaction he selected on the quick browse
            //  but if qr or connected card, always load the last transaction
            if (poDevice.UIDeviceType() != GCardDeviceFactory.DeviceType.NONE){
                if (!poTrans.loadLastFromCard()){
                    ShowMessageFX.Warning(poTrans.getMessage(), pxeModuleName, "Please inform MIS department.");
                    poTrans = new XMGCOnPoints(poGRider,psBranchCd, false);
                    clearFields();
                    return;
                }
            }
            
            if ((Boolean)poDevice.getCardInfo("bIsOnline")){
                lblTranStatus.setText("ONLINE");
                lblTranStatus.setStyle("-fx-text-fill: green");
            }else{
                lblTranStatus.setText("OFFLINE");
                lblTranStatus.setStyle("-fx-text-fill: red");
            }
            
            txtField01.setText((String) poDevice.getCardInfo("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
            //txtField08.setText(CommonUtils.NumberFormat(Double.valueOf(poDevice.getCardInfo("nAvlPoint").toString()), "0.0"));
            if (Double.valueOf(poDevice.getCardInfo("nAvlPoint").toString()) 
                < Double.valueOf(poDevice.getCardInfo("nDevPoint").toString())){
            txtField08.setText(CommonUtils.NumberFormat(Double.valueOf(poDevice.getCardInfo("nAvlPoint").toString()), "0.0"));
            } else {
                txtField08.setText(CommonUtils.NumberFormat(Double.valueOf(poDevice.getCardInfo("nDevPoint").toString()), "0.0"));
            }
            
            if (poDevice.getCardInfo("cCardType").toString().equals("0")){
                rbtn00.setSelected(true);
            }else{
                rbtn01.setSelected(true);
            }
            txtField05.setText(CommonUtils.NumberFormat(Double.valueOf(poDevice.getCardInfo("nAvlPoint").toString()), "0.0"));
        }else{
            lblTranStatus.setText("UNKNOWN");
            lblTranStatus.setStyle("-fx-text-fill: black");
        
            txtField01.setText((String) poTrans.getGCard().getMaster("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
            txtField08.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getGCard().getMaster("nAvlPoint").toString()), "0.0"));
            if (poTrans.getGCard().getMaster("cCardType").toString().equals("0")){
                rbtn00.setSelected(true);
            }else{
                rbtn01.setSelected(true);
            }
            
            txtField05.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getGCard().getMaster("nAvlPoint").toString()), "0.0"));
            
        }

        txtSeeks00.setText((String) poClient.getMaster("sLastName")+ ", " +poClient.getMaster("sFrstName")+ " " +poClient.getMaster("sMiddName"));
        
        txtSeeks01.setText((String) poTrans.getMaster("sSourceNo"));
        txtField00.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2"));
        txtField02.setText((String) poClient.getMaster("sLastName")+ ", " +poClient.getMaster("sFrstName")+ " " +poClient.getMaster("sMiddName"));
        txtField03.setText((String) poClient.getMaster("sAddressx") + ", " +poClient.getTown().getMaster("sTownName") + " " +poClient.getTown().getProvince().getMaster("sProvName") +" "+ poClient.getTown().getMaster("sZippCode"));
        txtField04.setText("N-O-N-E");
        txtField05.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
        txtField06.setText((String) poTrans.getSource().getMaster("sDescript"));
        txtField07.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nTranAmtx").toString()), "#0.00"));
        
        txtField09.setText((String) poTrans.getMaster("sSourceNo"));
        txtField10.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nPointsxx").toString()), "0.0"));
                    
        pnEditMode = poTrans.getEditMode();
    
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
                        if (txtSeeks00.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtSeeks00);
                            return;
                        }
                         
                        if (poTrans.searchWithCondition("sClientNm", txtSeeks00.getText(), "a.sTransNox LIKE "+ SQLUtil.toSQL(psBranchCd + '%')) == true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtSeeks00);
                        }
                        break;
                    case 1:
                        if (txtSeeks01.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtSeeks01);
                            return;
                        }
                         
                        if (poTrans.searchWithCondition("sSourceNo", txtSeeks01.getText(), "a.sTransNox LIKE "+ SQLUtil.toSQL(psBranchCd + '%')) == true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtSeeks01);
                        }
                        break;
                }
                break;
            case "cmdbutton02":
                if (poClient == null){
                    ShowMessageFX.Warning(null, "Notice", "Please load a transaction first.");
                    return;
                }
                
                poDevice = (GCardDevice) poTrans.checkCard();
                    
                if (poDevice != null){
                    if (!String.valueOf(poTrans.getMaster("sGCardNox")).equals((String) poDevice.getCardInfo("sGCardNox"))){
                        ShowMessageFX.Warning(null, "Notice", "G-Card read is not the same with the loaded transaction.");
                        poDevice = null;
                    }
                } else
                    ShowMessageFX.Warning(null, "Warning", poTrans.getMessage());
                
                break;
            case "cmdbutton03":
                if (poDevice == null) {
                    ShowMessageFX.Warning(null, "Notice", "Please connect the client's card first.");
                    return;
                }
                 
                if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to void this transaction?")== true){
                    if (poTrans.voidTransaction((String) poTrans.getMaster("sTransNox")) == true){
                        ShowMessageFX.Information(null, pxeModuleName, "Transaction successfully voided!");
                        clearFields();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    } 
                }
                break;
            case "cmdbutton04":
                if (poDevice == null) {
                    ShowMessageFX.Warning(null, "Notice", "Please connect the client's card first.");
                    return;
                }
                
                if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to cancel this transaction?")== true){
                    if (poTrans.cancelTransaction((String) poTrans.getMaster("sTransNox")) == true){
                        ShowMessageFX.Information(null, pxeModuleName, "Transaction successfully voided!");
                        clearFields();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    } 
                }
                break;
            case "cmdbutton05":
                mainController.loadModule(pxeOnlinePoints_Entry);
                break;
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
       txtField10.setDisable(lbShow);
       
       rbtn00.setMouseTransparent(lbShow);
       rbtn01.setMouseTransparent(lbShow);
       
    }
    
    public void txtSeeks_KeyPressed(KeyEvent event) {
        TextField txtSeeks = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtSeeks.getId().substring(8, 10));
        String lsValue = txtSeeks.getText();
        
        if (event.getCode() == ENTER || event.getCode() == KeyCode.F3) {
                switch (lnIndex){
                    case 0:
                        if (lsValue.equals("")){
                             ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                             return;
                        }
                        
                        if (poTrans.searchWithCondition("sClientNm", lsValue, "a.sTransNox LIKE "+ SQLUtil.toSQL(psBranchCd+'%')) == true){
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
                        
                        if (poTrans.searchWithCondition("sSourceNo", lsValue, "a.sTransNox LIKE "+ SQLUtil.toSQL(psBranchCd+'%')) == true){
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
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        txtField00.setText("");
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText("N-O-N-E");
        txtField05.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField06.setText("");
        txtField07.setText("0.0");
        txtField08.setText("0.0");
        txtField09.setText("");
        txtField10.setText("0.0");
        lblTranStatus.setText("UNKNOWN");
        lblTranStatus.setStyle("-fx-text-fill: black");
        initTransaction();
        pnEditMode = poTrans.getEditMode();
        initButton(pnEditMode);
        
        System.setProperty("app.device.type", "");
        
        poClient = null;
        poDevice = null;
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
               case 1:
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

}
