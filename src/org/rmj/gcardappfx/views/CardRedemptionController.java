
package org.rmj.gcardappfx.views;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.CustomTextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.agentfx.ui.showFXDialog;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.appdriver.constants.GCDeviceType;
import org.rmj.appdriver.constants.GCardStatus;
import org.rmj.client.agent.XMClient;
import org.rmj.gcard.device.ui.GCardDevice;
import org.rmj.gcard.trans.agentFX.XMGCRedemption;
import org.rmj.gcard.trans.agentFX.XMGCRedemptionPromo;

public class CardRedemptionController implements Initializable {

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
    private TextField txtField05;
    @FXML
    private TextField txtField06;
    @FXML
    private TextField txtField08;
    @FXML
    private TextArea txtField09;
    @FXML
    private TextField txtField10;
    @FXML
    private TextField txtField11;
    @FXML
    private ImageView imageview;
    @FXML
    private RadioButton rbtn00;
    @FXML
    private RadioButton rbtn01;
    @FXML
    private Label lblTranStatus;
    @FXML
    private Label lblServerStat;
    @FXML
    private CustomTextField txtField07;
    @FXML
    private GridPane NodePane001;
    @FXML
    private CustomTextField txtSeeks00;
    @FXML
    private CustomTextField txtSeeks01;
    @FXML
    private ComboBox cmbCardClass;

    private GCardAppFxController mainController ;
    public static final Image search = new Image("org/rmj/gcardappfx/images/search.png");
    private final String pxeModuleName = "G-Card Reward Redemption";
    private boolean pbLoaded = false;
    public final static String pxeCardApplicationB = "CardApplicationB.fxml";
    public final static String pxeOthers = "CardOthers.fxml";
    public final static String pxeOnlinePointsEntry = "OnlinePoints_Entry.fxml";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    public final static String pxeOfflinePoints_EntryB = "OfflinePoints_EntryB.fxml";
    private final String pxeDateFormat = "yyyy-MM-dd";
    private String pxeCurrentDate;
    private int pnEditMode = -1;
    private Integer pnIndex = -1;
    private XMGCRedemption poTrans;
    private String pxeClientNm= "";
    private String pxeCardNmbr= "";
    private static GRider poGRider;
    private String psBranchCd = "";
    
    private ObservableList<String> sDeviceType = FXCollections.observableArrayList("Smartcard", "App QR Code", "Non-Chip Card");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..",pxeModuleName, "Please Inform MIS/SEG" );
            System.exit(0);
        }
        
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        
        pxeCurrentDate = poGRider.getServerDate().toString();
        
        poTrans = new XMGCRedemption(poGRider ,psBranchCd, false);
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
        txtField02.focusedProperty().addListener(txtField_Focus);
        txtField03.focusedProperty().addListener(txtField_Focus);
        txtField04.focusedProperty().addListener(txtField_Focus);
        txtField05.focusedProperty().addListener(txtField_Focus);
        txtField06.focusedProperty().addListener(txtField_Focus);
        txtField07.focusedProperty().addListener(txtField_Focus);
        txtField08.focusedProperty().addListener(txtField_Focus);
        txtField09.focusedProperty().addListener(txtArea_Focus);
        txtField10.focusedProperty().addListener(txtField_Focus);
        txtField11.focusedProperty().addListener(txtField_Focus);
        
        txtField00.setOnKeyPressed(this::txtField_KeyPressed);
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        txtField02.setOnKeyPressed(this::txtField_KeyPressed);
        txtField03.setOnKeyPressed(this::txtField_KeyPressed);
        txtField04.setOnKeyPressed(this::txtField_KeyPressed);
        txtField05.setOnKeyPressed(this::txtField_KeyPressed);
        txtField06.setOnKeyPressed(this::txtField_KeyPressed);
        txtField07.setOnKeyPressed(this::txtField_KeyPressed);
        txtField08.setOnKeyPressed(this::txtField_KeyPressed);
        txtField09.setOnKeyPressed(this::txtArea_KeyPressed);
        txtField10.setOnKeyPressed(this::txtField_KeyPressed);
        txtField11.setOnKeyPressed(this::txtField_KeyPressed);
        txtField07.setLeft(new ImageView(search));
        
        txtSeeks00.focusedProperty().addListener(txtSeeks_Focus);
        txtSeeks01.focusedProperty().addListener(txtSeeks_Focus);
        txtSeeks00.setLeft(new ImageView(search));
        txtSeeks01.setLeft(new ImageView(search));
        
        txtSeeks00.setOnKeyPressed(this::txtSeeks_Keypressed);
        txtSeeks01.setOnKeyPressed(this::txtSeeks_Keypressed);
        
        poTrans.ClearGCardProperty();
        cmbCardClass.setItems(sDeviceType);

        clearFields();
        loadGui();
        
        cmbCardClass.getSelectionModel().select(0);
        txtSeeks00.setDisable(true);
        txtSeeks01.setDisable(true);
        
        pbLoaded = true;
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
        mainController.cmdButton01.setText("CONNECT");
        mainController.cmdButton02.setText("SEARCH");
        mainController.cmdButton03.setText("SAVE");
        mainController.cmdButton04.setText("");
        mainController.cmdButton05.setText("");
        mainController.cmdButton06.setText("");
        mainController.cmdButton07.setText("");
        mainController.cmdButton08.setText("");
        mainController.cmdButton09.setText("");
        mainController.cmdButton10.setText("");
        mainController.cmdButton11.setText(CommonUtils.getConfiguration(poGRider, "BrCPNoUpdt").equals("1") ? "UPDT MOBILE" : "");
                
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
        mainController.tooltip11.setText("F12"); 

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

        mainController.setSiteMap(14);
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
                if (poTrans.connectCard() ==  true){
                    loadTransaction();
                    mainController.setFocus(txtField06);
                }else{
                    ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                }
                break;
             case "cmdbutton02":
                switch (pnIndex){
                    case 7: 
                        if(poTrans.searchField("sPromCode", txtField07.getText()) == true){
                            loadParameter(7);  
                        }else{
                            ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                            mainController.setFocus(txtField07);
                        }
                        break;
                }
                break;
             case "cmdbutton03":
                if (poTrans.getPromo().getMaster("sPromCode") == null){
                    ShowMessageFX.Warning(null, pxeModuleName, "No entry to process!");
                    return;
                }
                
                if (poTrans.saveUpdate() == true){
                    //disconnect the card
                    poTrans.releaseCard();
                    
                    ShowMessageFX.Information(null, pxeModuleName, "Redemption successfully saved!");
                    if( ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to print this transaction?")== true){
                        if (!printRedeemedItem()) return;
                        clearFields();
                    }
                }else{
                    ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                }
                break;
            case "cmdbutton04":
            case "cmdbutton05":
            case "cmdbutton06":
            case "cmdbutton07":
            case "cmdbutton08":
            case "cmdbutton09":
            case "cmdbutton10":
                break;
            case "cmdbutton11":
                String lsClientID = System.getProperty("app.client.id");
                String lsMobileNo = System.getProperty("app.gcard.mobile");
                
                JSONObject loJSON = showFXDialog.updateMobileNo(poGRider, lsClientID, lsMobileNo);
                String lsResult = (String) loJSON.get("result");
                
                if (lsResult.equalsIgnoreCase("success")){
                    mainController.lblMobileNo.setText((String) loJSON.get("mobile"));
                    System.setProperty("app.gcard.mobile", (String) loJSON.get("mobile"));
                    
                    //mac 2020.08.22
                    //reload the information
                    if (poTrans.connectCard() ==  true){
                        loadTransaction();
                        mainController.setFocus(txtField06);
                    }
                    
                    ShowMessageFX.Information((String) loJSON.get("message"), "Success", "Update Success");
                } else {                    
                    try {
                        loJSON = (JSONObject) new JSONParser().parse(loJSON.get("error").toString());
                        
                        ShowMessageFX.Warning((String) loJSON.get("message"), "Failed - " + (String) loJSON.get("code"), "Update Failed");
                    } catch (org.json.simple.parser.ParseException ex) {
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
    
    public void initButton(int fnValue) {
       boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
       
       if (lbShow) mainController.setFocus(txtField06);
       
       txtField00.setMouseTransparent(lbShow);
       txtField01.setEditable(false);
       txtField02.setEditable(false);
       txtField03.setEditable(false);
       txtField04.setEditable(false);
       txtField05.setEditable(false);
       txtField08.setEditable(false);
       txtField10.setEditable(false);
       txtField11.setEditable(false);
       
       rbtn00.setMouseTransparent(lbShow);
       rbtn01.setMouseTransparent(lbShow);
       
    }
    
    public void txtArea_KeyPressed(KeyEvent event) {
        if (event.getCode() == ENTER || event.getCode() == DOWN){
            CommonUtils.SetNextFocus((TextArea)event.getSource());
            event.consume();
        }else if (event.getCode() ==KeyCode.UP){
            CommonUtils.SetPreviousFocus((TextArea)event.getSource());
            event.consume();
        }
    }
    
    public void txtField_KeyPressed(KeyEvent event) {
        TextField txtField = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (event.getCode() == ENTER || event.getCode() == F3) {
                switch (lnIndex){
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        break;
                    case 7:
                        if (poTrans.searchField("sPromCode", lsValue)) 
                            loadParameter(7);
                        else
                            ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                        break;
                    case 8:
                    case 10:
                    case 11:
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
    
    public void clearFields() {        
        mainController.lblCardNmbr.setText("N-O-N-E");
        mainController.lblDeviceType.setText("UNKNOWN");
        mainController.lblMobileNo.setText("N-O-N-E");    
        
        pxeCardNmbr = "";
        pxeClientNm = "";
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        txtField00.setText("");
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText("N-O-N-E");
        txtField05.setText("0.0");
        txtField06.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField07.setText("");
        txtField08.setText("");
        txtField09.setText("");
        txtField10.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField11.setText("");
        loadParameter(0);
        pnEditMode = poTrans.getEditMode();
        initButton(pnEditMode);
        
        mainController.cmdButton11.setDisable(true);
        
        lblTranStatus.setText("UNKNOWN");
        lblTranStatus.setStyle("-fx-text-fill: black");
        setCardStat("-1");
    }
    
    private void loadTransaction(){
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        XMClient loClient = (XMClient) poTrans.getGCard().getClient();
        GCardDevice loDevice = (GCardDevice) poTrans.getDeviceInfo();
        
        if(!String.valueOf(loDevice.getCardInfo("cCardStat")).equals(GCardStatus.ACTIVATED)){
            ShowMessageFX.Information("Only activated G-Cards can use this feature.", pxeModuleName, "Invalid G-Card Status.");
            clearFields();
            return;
        }
        
        if ((Boolean)loDevice.getCardInfo("bIsOnline")){
            lblTranStatus.setText("ONLINE");
            lblTranStatus.setStyle("-fx-text-fill: green");
        } else{
            lblTranStatus.setText("OFFLINE");
            lblTranStatus.setStyle("-fx-text-fill: red");
        }
        
        txtField01.setText((String) loDevice.getCardInfo("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        pxeCardNmbr = txtField01.getText();
        txtField02.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
        pxeClientNm = txtField02.getText();
        txtField03.setText((String) loClient.getMaster("sAddressx") + ", " +loClient.getTown().getMaster("sTownName") + " " +loClient.getTown().getProvince().getMaster("sProvName") +" "+ loClient.getTown().getMaster("sZippCode"));
        
        txtField04.setText("N-O-N-E");
        
        //validate points: show what ever is lower
        if (Double.valueOf(loDevice.getCardInfo("nAvlPoint").toString()) 
                < Double.valueOf(loDevice.getCardInfo("nDevPoint").toString())){
            txtField05.setText(CommonUtils.NumberFormat(Double.valueOf(loDevice.getCardInfo("nAvlPoint").toString()), "0.0"));
        } else {
            txtField05.setText(CommonUtils.NumberFormat(Double.valueOf(loDevice.getCardInfo("nDevPoint").toString()), "0.0"));
        }
        
        //txtField05.setText(CommonUtils.NumberFormat(Double.valueOf(loDevice.getCardInfo("nAvlPoint").toString()), "0.0"));
        txtField06.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
        txtField09.setText((String) poTrans.getMaster("sRemarksx"));
        txtField10.setText(CommonUtils.xsDateMedium((Date) loClient.getMaster("dBirthDte")));
        
        if (loDevice.getCardInfo("cCardType").toString().equals("0")){
            rbtn00.setSelected(true);
        }else{
            rbtn01.setSelected(true);
        }
        
        mainController.lblCardNmbr.setText(System.getProperty("app.card.no"));
        mainController.lblMobileNo.setText(System.getProperty("app.gcard.mobile"));

        if (null == System.getProperty("app.device.type")){
            mainController.lblDeviceType.setText("UNKNOWN");
        } else switch (System.getProperty("app.device.type")) {
            case GCDeviceType.NONE:
                mainController.lblDeviceType.setText("NON-CHIP CARD");
                break;
            case GCDeviceType.SMARTCARD:
                mainController.lblDeviceType.setText("SMART CARD");
                break;
            case GCDeviceType.QRCODE:
                mainController.lblDeviceType.setText("APP QR CODE");
                break;
            default:
                mainController.lblDeviceType.setText("UNKNOWN");
                break;
        }
        
        mainController.cmdButton11.setDisable(!CommonUtils.getConfiguration(poGRider, "BrCPNoUpdt").equals("1"));
        
        setCardStat((String) loDevice.getCardInfo("cCardStat"));
        loClient = null;
        loDevice = null;
        
        txtField07.requestFocus();
        pnEditMode = poTrans.getEditMode();
    }
    
    private void loadParameter(int fnValue){
        switch(fnValue){
            case 0:
                poTrans.newTransaction();
                txtField00.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2"));
                break;
            case 7:
                 XMGCRedemptionPromo loPromo = (XMGCRedemptionPromo) poTrans.getPromo();
                 txtField07.setText((String) loPromo.getMaster("sPromCode"));
                 txtField08.setText((String) loPromo.getMaster("sPromDesc"));
                 txtField11.setText(CommonUtils.NumberFormat(Double.valueOf(loPromo.getMaster("nPointsxx").toString()), "0.0"));
                 
                 loPromo = null;
                break;
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
               case 2:
               case 3: 
               case 4:        
               case 5:
               case 8:
               case 10:
               case 11:    
                   break;
               case 6:
                   if (CommonUtils.isDate(lsValue, pxeDateFormat)){
                        poTrans.setMaster("dTransact", CommonUtils.toDate(lsValue));
                    } else{
                        ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                        poTrans.setMaster("dTransact", CommonUtils.toDate(pxeCurrentDate));
                    }
                    /*get the value from the class*/
                    txtField.setText(CommonUtils.xsDateLong((Date)poTrans.getMaster("dTransact")));
                    return;
               case 7:
                   if(!lsValue.equals(poTrans.getPromo().getMaster("sPromCode"))){
                        txtField.setText("");
                        poTrans.setMaster("sPromCode", "");
                        txtField08.setText("");
                        txtField11.setText("");
                     }
                    break;
                  
                default:
                   ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
                   return;
            }
           
        } else {
            switch (lnIndex){
                case 6: /*dTransact*/
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
    
    final ChangeListener<? super Boolean> txtArea_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextArea txtField = (TextArea)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
        
        if(!nv){ /*Lost Focus*/            
            switch (lnIndex){
                case 9: /*sRemarksx*/
                    if (lsValue.length() > 256) lsValue = lsValue.substring(0, 256);
                    
                    poTrans.setMaster("sRemarksx" ,CommonUtils.TitleCase(lsValue));
                    txtField.setText((String)poTrans.getMaster("sRemarksx"));
                    break;
            }
        }else{
            pnIndex = lnIndex;
            txtField.selectAll();
        }
    };
    
    private boolean printRedeemedItem(){
        JSONArray json_arr = new JSONArray();
        json_arr.clear();
        
                
        JSONObject json_obj = new JSONObject();
        XMGCRedemptionPromo loPromo = (XMGCRedemptionPromo) poTrans.getPromo();

        json_obj.put("sField01", (String) loPromo.getMaster("sPromCode"));
        json_obj.put("sField02", loPromo.getMaster("sPromDesc"));
        json_obj.put("lField01", CommonUtils.NumberFormat(Double.valueOf(loPromo.getMaster("nPointsxx").toString()), "0.0"));
        
        loPromo = null;
        json_arr.add(json_obj); 

        //Create the parameter
        Map<String, Object> params = new HashMap<>();
        params.put("sBranchNm", poGRider.getBranchName());
        params.put("sBranchAd", poGRider.getAddress());
        params.put("sCardNmbr", pxeCardNmbr);
        params.put("sClientNm", pxeClientNm);
     
        params.put("sTransNox", poTrans.getMaster("sTransNox").toString().substring(4));
        params.put("sReportDt", CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
        params.put("sPrintdBy", mainController.pxeUserName);

        try {
            InputStream stream = new ByteArrayInputStream(json_arr.toJSONString().getBytes("UTF-8"));
            JsonDataSource jrjson = new JsonDataSource(stream); 

            JasperPrint _jrprint = JasperFillManager.fillReport("d:/GGC_Java_Systems/reports/CardRedemption.jasper", params, jrjson);
            JasperViewer jv = new JasperViewer(_jrprint, false);
            jv.setVisible(true);
        } catch (JRException | UnsupportedEncodingException  ex) {
            Logger.getLogger(CardPreOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }
    
    private void txtSeeks_Keypressed(KeyEvent event){
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
                        
                        if (poTrans.searchField("sCardNmbr", lsValue.replace("-", "")) == true){
                            txtSeeks00.setText(System.getProperty("app.card.no"));
                            txtSeeks01.setText(System.getProperty("app.gcard.holder"));
                            
                            cmbCardClass.getSelectionModel().select(Integer.parseInt(System.getProperty("app.device.type")));
                            txtSeeks00.setDisable(System.getProperty("app.device.type").equals("0"));
                            txtSeeks01.setDisable(System.getProperty("app.device.type").equals("0"));
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
                        if (poTrans.searchField("sClientNm", lsValue) == true){                            
                            txtSeeks00.setText(System.getProperty("app.card.no"));
                            txtSeeks01.setText(System.getProperty("app.gcard.holder"));
                            
                            cmbCardClass.getSelectionModel().select(Integer.parseInt(System.getProperty("app.device.type")));
                            txtSeeks00.setDisable(System.getProperty("app.device.type").equals("0"));
                            txtSeeks01.setDisable(System.getProperty("app.device.type").equals("0"));
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

    @FXML
    private void cmbCardClass_Change(ActionEvent event) {
        mainController.lblCardNmbr.setText("N-O-N-E");
        mainController.lblDeviceType.setText("UNKNOWN");
        mainController.lblMobileNo.setText("N-O-N-E");
        
        poTrans.ClearGCardProperty();
                
        txtSeeks00.setText("");
        txtSeeks01.setText("");
        
        clearFields();
        
        switch (cmbCardClass.getSelectionModel().getSelectedIndex()){
            case 0:                
                txtSeeks00.setDisable(true);
                txtSeeks01.setDisable(true);
                break;
            case 1:
            case 2:
                txtSeeks00.setDisable(false);
                txtSeeks01.setDisable(false);
                txtSeeks00.requestFocus();
        }
        
        System.setProperty("app.device.type", String.valueOf(cmbCardClass.getSelectionModel().getSelectedIndex()));
    }
}
