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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.client.agent.XMClient;
import org.rmj.gcard.device.ui.GCardDeviceFactory;
import org.rmj.gcard.trans.agentFX.XMGCardIssue;

public class CardIssuanceController implements Initializable {
    
    private GCardAppFxController mainController ;
    private final String pxeModuleName = "G-Card Issuance";
    public final static String pxeCardApplication = "CardApplication.fxml";
    public final static String pxeOfflinePoints_Entry = "OfflinePoints_Entry.fxml";
    public final static String pxeOfflinePoints_EntryB = "OfflinePoints_EntryB.fxml";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    public final static String pxeOnlinePoints_Entry = "OnlinePoints_Entry.fxml";
    public final static String pxeCardApplicationB = "CardApplicationB.fxml";
    private static GRider poGRider;
    public Config properties;
    private String pxeCurrentDate;
    private Integer pnEditMode = -1;
    private Integer pnIndex = -1;
    private String psBranchCd = "";
    private XMGCardIssue poTrans;
    private boolean pbLoaded = false;
    
    private ObservableList<String> sDeviceType = FXCollections.observableArrayList("Smartcard", "Non-Chip Card");
    
    @FXML
    private TextField txtField03;
    @FXML
    private TextField txtField07;
    @FXML
    private TextField txtField05;
    @FXML
    private TextField txtField06;
    @FXML
    private TextField txtField04;
    @FXML
    private RadioButton rbtn00;
    @FXML
    private RadioButton rbtn01;
    @FXML
    private TextField txtField00;
    @FXML
    private TextField txtField02;
    @FXML
    private TextField txtField01;
    @FXML
    private AnchorPane childPane;
    @FXML
    private Label lblTitle;
    @FXML
    private GridPane NodePane01;
    @FXML
    private GridPane NodePane00;
    @FXML
    private ComboBox cmbCardClass;
    @FXML
    private ImageView imageview;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        properties = new Config();
        
        if (poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..",pxeModuleName, "Please Inform MIS/SEG" );
            System.exit(0);
        }
        
        pxeCurrentDate = poGRider.getServerDate().toString();
        
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        
        poTrans = new XMGCardIssue(poGRider ,psBranchCd);
        
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
        
        txtField00.focusedProperty().addListener(txtField_Focus);
        txtField01.focusedProperty().addListener(txtField_Focus);
        txtField03.focusedProperty().addListener(txtField_Focus);
        
        txtField00.setOnKeyPressed(this::txtField_KeyPressed);
        txtField03.setOnKeyPressed(this::txtField_KeyPressed);
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        
        poTrans.ClearGCardProperty();
        cmbCardClass.setItems(sDeviceType);

        loadGui();
        clearFields();
        
        cmbCardClass.getSelectionModel().select(0);
        txtField00.setDisable(true);
        txtField00.setDisable(true);
        
        pbLoaded = true;
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
        mainController.cmdButton01.setText("CONNECT");
        mainController.cmdButton02.setText("ISSUE");
        mainController.cmdButton03.setText("");
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
        mainController.tooltip03.setText("x");
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
        mainController.cmdButton03.setDisable(true);
        mainController.cmdButton04.setDisable(true);
        mainController.cmdButton05.setDisable(true);
        mainController.cmdButton06.setDisable(true);
        mainController.cmdButton07.setDisable(true);
        mainController.cmdButton08.setDisable(true);
        mainController.cmdButton09.setDisable(true);
        mainController.cmdButton10.setDisable(true);
        mainController.cmdButton11.setDisable(true);
        mainController.setSiteMap(4);
        
        initButton(EditMode.UNKNOWN);
        
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
                if (cmbCardClass.getSelectionModel().getSelectedIndex() == 0){
                    if (poTrans.connectCard() ==  true){
                        loadTransaction();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }
                break;
            case "cmdbutton02":
                poTrans.setTranDate(SQLUtil.toDate(txtField03.getText(), SQLUtil.FORMAT_LONG_DATE));
                
                if (cmbCardClass.getSelectionModel().getSelectedIndex() == 0){
                    if(poTrans.issue() == true){
                        //disconnect the card
                        poTrans.releaseCard();
                        
                        ShowMessageFX.Information(null, pxeModuleName, "Successfully issued card!");
                        clearFields();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }else {
                    if(poTrans.issueNonChip()){
                        ShowMessageFX.Information(null, pxeModuleName, "Successfully issued card!");
                        clearFields();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }
                break;
            case "cmdbutton03":
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
    
    public void initButton(int fnValue) {
       boolean lbShow = fnValue == EditMode.READY;
       txtField03.setDisable(!lbShow);
             
       txtField02.setEditable(false);       
       txtField04.setEditable(false);       
       txtField05.setEditable(false);       
       txtField06.setEditable(false);       
       txtField07.setEditable(false);       
       
       rbtn00.setMouseTransparent(true);
       rbtn01.setMouseTransparent(true);
       
    }
    
    public void txtField_KeyPressed(KeyEvent event) {
        TextField txtField = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (event.getCode() == ENTER || event.getCode() == F3) {
            switch (lnIndex){
                case 0:
                    if (poTrans.searchWithCondition("sCardNmbr", lsValue, "")){
                        loadTransaction();
                    }else {
                        ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                        clearFields();
                    }
                    break;
                case 1:
                    if (poTrans.searchWithCondition("sClientNm", lsValue, "")){
                        loadTransaction();
                    }else {
                        ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                        clearFields();
                    }
            }
        }        
        if (event.getCode() == DOWN || event.getCode() == ENTER){
             CommonUtils.SetNextFocus(txtField);                  
        }
        
        if (event.getCode() == UP){
             CommonUtils.SetPreviousFocus(txtField);                 
        }
    }
    
    private void setCardStat(String fsValue){
        switch(fsValue){
            case "0":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/verified.png")); break;
            case "1":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/printed.png")); break;
            case "2":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/encoded.png")); break;
            case "3":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/released.png")); break;
            case "4":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/activated.png")); break;
            case "5":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/replaced.png")); break;
            case "6":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/suspended.png")); break;
            case "7":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/renewed.png")); break;
            default:
                imageview.setImage(new Image("org/rmj/gcardappfx/images/unknown.png"));      
        }
    }
    
    private void loadTransaction(){
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        XMClient loClient = (XMClient) poTrans.getClient();
        
        txtField00.setText((String) poTrans.getMaster("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        txtField01.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
        txtField02.setText((String) poTrans.getMaster("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        txtField03.setText(SQLUtil.dateFormat(poTrans.getTranDate(), SQLUtil.FORMAT_MEDIUM_DATE));
        txtField04.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
        txtField05.setText((String) loClient.getMaster("sAddressx") + ", " +loClient.getTown().getMaster("sTownName") + " " +loClient.getTown().getProvince().getMaster("sProvName") +" "+ loClient.getTown().getMaster("sZippCode"));
        txtField06.setText((String) poTrans.getBranch((String) poTrans.getMaster("sGCardNox").toString().substring(0, 4)).getMaster("sBranchNm"));
        txtField07.setText(SQLUtil.dateFormat((Date) poTrans.getApplication(psBranchCd).getMaster("dTransact"), SQLUtil.FORMAT_MEDIUM_DATE));        
        
        if (poTrans.getMaster("cCardType").toString().equals("0")){
            rbtn00.setSelected(true);
        }else{
            rbtn01.setSelected(true);
        }
        setCardStat((String) poTrans.getMaster("cCardStat"));
        
        loClient = null;
        
        pnEditMode = poTrans.getEditMode();
        initButton(pnEditMode);
    }
    
    public void clearFields() {
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        txtField00.setText("");
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText(SQLUtil.dateFormat(poTrans.getTranDate(), SQLUtil.FORMAT_MEDIUM_DATE));
        txtField04.setText("");
        txtField05.setText("");
        txtField06.setText("");
        txtField07.setText(SQLUtil.dateFormat(poTrans.getTranDate(), SQLUtil.FORMAT_MEDIUM_DATE));
        
        initButton(EditMode.UNKNOWN);
        setCardStat("-1");
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
                case 1:
                break;
                case 3:
                    Date loDate = SQLUtil.toDate(lsValue, SQLUtil.FORMAT_SHORT_DATE);
                            
                    poTrans.setTranDate(loDate == null ? poGRider.getServerDate() : loDate);
                        
                    txtField.setText(SQLUtil.dateFormat(poTrans.getTranDate(), SQLUtil.FORMAT_MEDIUM_DATE));
                    break;
                default:
                   ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
                   return;
            }
        } else {
            switch(lnIndex){
                case 3:                        
                    txtField.setText(SQLUtil.dateFormat(poTrans.getTranDate(), SQLUtil.FORMAT_SHORT_DATE));
                    break;
                default:
                   return;
            }
        }
        txtField.selectAll();
        pnIndex = lnIndex;
    };

    @FXML
    private void cmbCardClass_Change(ActionEvent event) {
        mainController.lblCardNmbr.setText("N-O-N-E");
        mainController.lblDeviceType.setText("UNKNOWN");
        mainController.lblMobileNo.setText("N-O-N-E");
        
        poTrans.ClearGCardProperty();
        txtField00.setText("");
        txtField01.setText("");
        
        clearFields();
        
        switch (cmbCardClass.getSelectionModel().getSelectedIndex()){
            case 0:
                
                mainController.cmdButton01.setDisable(false);
                txtField00.setDisable(true);
                txtField01.setDisable(true);
                break;
            case 1:
                mainController.cmdButton01.setDisable(true);
                txtField00.setDisable(false);
                txtField01.setDisable(false);
                txtField00.requestFocus();
        }
        
        System.setProperty("app.device.type", String.valueOf(cmbCardClass.getSelectionModel().getSelectedIndex()));
    }
}
