package org.rmj.gcardappfx.views;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import org.controlsfx.control.textfield.CustomTextField;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.client.agent.XMClient;
import org.rmj.gcard.trans.agentFX.XMGCOffPoints;
import org.rmj.gcard.trans.agentFX.XMGCard;

public class OfflinePoints_EntryController implements Initializable {
    
    private GCardAppFxController mainController ;
    private static final Image search= new Image("org/rmj/gcardappfx/images/search.png");
    private final String pxeModuleName = "G-Card Offline Points Entry";
    public final static String pxeCardApplication = "CardApplication.fxml";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    public final static String pxeOfflinePoints_RegisterM = "OfflinePoints_RegisterM.fxml";
    public final static String pxeOnlinePointsEntry = "OnlinePoints_Entry.fxml";
    private boolean pbLoaded = false;
    private int pnIndex = -1;
    private int pnEditMode = -1;
    private static GRider poGRider;
    private final String pxeDateFormat = "yyyy-MM-dd";
    private String psBranchCd = "";
    private String pxeCurrentDate;
    private XMGCOffPoints poTrans;
    
    @FXML
    private TextField txtField00;
    @FXML
    private TextField txtField03;
    @FXML
    private TextField txtField04;
    @FXML
    private TextField txtField07;
    @FXML
    private RadioButton rbtn00;
    @FXML
    private RadioButton rbtn01;
    @FXML
    private TextArea txtField08;
    @FXML
    private CheckBox chk00;
    @FXML
    private TextField txtField09;
    @FXML
    private TextField txtField05;
    @FXML
    private Label lblTitle;
    @FXML
    private GridPane NodePane01;
    @FXML
    private GridPane NodePane00;
    @FXML
    private ImageView imageview;
    @FXML
    private TextField txtField10;
    @FXML
    private CustomTextField txtField01;
    @FXML
    private CustomTextField txtField02;
    @FXML
    private CustomTextField txtField06;
    @FXML
    private CustomTextField txtField80;
    @FXML
    private CustomTextField txtField81;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..",pxeModuleName, "Please Inform MIS/SEG" );
            System.exit(0);
        }
        pxeCurrentDate= poGRider.getServerDate().toString();
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        
        poTrans = new XMGCOffPoints(poGRider, psBranchCd, false);
        
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
        txtField01.setLeft(new ImageView(search));
        txtField02.setLeft(new ImageView(search));
        txtField06.setLeft(new ImageView(search));
        txtField80.setLeft(new ImageView(search));
        txtField81.setLeft(new ImageView(search));
        
        txtField00.focusedProperty().addListener(txtField_Focus);
        txtField01.focusedProperty().addListener(txtField_Focus);
        txtField02.focusedProperty().addListener(txtField_Focus);
        txtField03.focusedProperty().addListener(txtField_Focus);
        txtField04.focusedProperty().addListener(txtField_Focus);
        txtField05.focusedProperty().addListener(txtField_Focus);
        txtField06.focusedProperty().addListener(txtField_Focus);
        txtField07.focusedProperty().addListener(txtField_Focus);
        txtField08.focusedProperty().addListener(txtArea_Focus);
        txtField09.focusedProperty().addListener(txtField_Focus);
        txtField10.focusedProperty().addListener(txtField_Focus);
        
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
        txtField08.setOnKeyPressed(this::txtArea_KeyPressed);
        txtField09.setOnKeyPressed(this::txtField_KeyPressed);
        txtField10.setOnKeyPressed(this::txtField_KeyPressed);
 
        clearFields();
        
        loadGui();
        
        pbLoaded = true;
    }
    
    private void setTransTat(String fsValue){
        switch(fsValue){
            case "0":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/open.png")); break;
            case "1":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/closed.png")); break;
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
    
    public void setBranchCd(String fsBranchCd) {
        this.psBranchCd = fsBranchCd;
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
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
        mainController.setSiteMap(7);
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
                    case 1:
                        if (txtField01.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtField01);
                            return;
                        }
                        
                        if (poTrans.searchField("sCardNmbr", txtField01.getText().replace("-", "")) == true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField01);
                        }
                        break;
                    case 2:
                        if (txtField02.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtField02);
                            return;
                        }
                        
                        if (poTrans.searchField("sClientNm", txtField02.getText()) == true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField02);
                        }
                        break;
                    case 6:
                        if (poTrans.searchField("sDescript", txtField06.getText() == null ? "%" : txtField06.getText()) == true){
                            loadParameter(6);
                        } else
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                        break;
                    case 80:
                        if (poTrans.searchField("sBranchCd", txtField80.getText())){
                            clearFields();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField80);
                        }
                        break;
                        
                    case 81:
                        if (poTrans.searchField("sBranchNm", txtField81.getText())){
                            clearFields();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField81);
                        }
                        break;
                }
                break;
            case "cmdbutton02":
                if (poTrans.getMaster("sSourceNo").equals("")){
                     ShowMessageFX.Warning(null, pxeModuleName, "No entry to processa!");
                     return;
                }
                 
                if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to save this transaction?")== true){
                    if (poTrans.saveUpdate()==true){
                        if (chk00.isSelected() == true){
                            poTrans.setHasBonus(true);
                        }
                         ShowMessageFX.Information(null, pxeModuleName, "Successfully Saved!");
                         clearFields();
                    }else{
                         ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }
                break;
            case "cmdbutton03":
                mainController.loadModule(pxeOfflinePoints_RegisterM);
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
    
    public void loadTransaction(){
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        XMClient loClient = (XMClient) poTrans.getGCard().getClient();
        XMGCard loGCard = (XMGCard) poTrans.getGCard();
        
        txtField01.setText((String) loGCard.getMaster("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        txtField02.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName") + " " +loClient.getMaster("sMiddName"));
        txtField03.setText((String) loClient.getMaster("sAddressx") + ", " +loClient.getTown().getMaster("sTownName") + " " +loClient.getTown().getProvince().getMaster("sProvName") +" "+ loClient.getTown().getMaster("sZippCode"));
        txtField04.setText("N-O-N-E");
        txtField05.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
        txtField06.setText("");
        txtField07.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nTranAmtx").toString()), "###0.0"));
        txtField08.setText((String) poTrans.getMaster("sRemarksx"));
        txtField09.setText((String) poTrans.getMaster("sSourceNo"));
        txtField10.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nPointsxx").toString()), "0.0"));
        
        if (loGCard.getMaster("cCardType").toString().equals("0")){
            rbtn00.setSelected(true);
        }else{
            rbtn01.setSelected(true);
        }
        setTransTat((String) poTrans.getMaster("cTranStat"));
        
        loClient = null;
        loGCard = null;
        pnEditMode = poTrans.getEditMode();
    }
    
    public int loadParameter(int fnValue){
        switch (fnValue){
            case 6:
                txtField06.setText((String) poTrans.getSource().getMaster("sDescript"));
                break;
            case 0:
                poTrans.newTransaction();
                txtField80.setText((String) poTrans.getBranch().getMaster("sBranchCd"));
                txtField81.setText((String) poTrans.getBranch().getMaster("sBranchNm"));
                txtField00.setText((String) poTrans.getMaster("sTransNox").toString().substring(1, 12).replaceFirst("(\\w{5})(\\w{6})", "$1-$2"));
                break;
        }
        return fnValue;
    }
    
    public void initButton(int fnValue) {
       boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
       
       if(lbShow) mainController.setFocus(txtField01);
       txtField00.setMouseTransparent(lbShow);
       txtField03.setDisable(lbShow);
       txtField04.setDisable(lbShow);
       txtField10.setDisable(lbShow);
       txtField07.setDisable(lbShow);
       
       rbtn00.setMouseTransparent(lbShow);
       rbtn01.setMouseTransparent(lbShow);
    }
    
    public void txtField_KeyPressed(KeyEvent event) {
        TextField txtField = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (event.getCode() == ENTER || event.getCode() == KeyCode.F3) {
                switch (lnIndex){
                    case 80:
                        if (poTrans.searchField("sBranchCd", lsValue)){
                            clearFields();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                        break;
                        
                    case 81:
                        
                        if (poTrans.searchField("sBranchNm", lsValue)){
                            clearFields();
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
                        
                        if (poTrans.searchField("sCardNmbr", lsValue.replace("-", "")) == true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                        break;
                    case 2:
                        if (lsValue.equals("")){
                            return;
                        }
                        
                        if (poTrans.searchField("sClientNm", lsValue) == true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                        break;
                    case 6:
                        if (poTrans.searchField("sDescript", lsValue == null ? "%" : lsValue) == true){
                            loadParameter(6);
                        } else
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
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
    
    public void txtArea_KeyPressed(KeyEvent event) {
        if (event.getCode() == ENTER || event.getCode() == DOWN){
            CommonUtils.SetNextFocus((TextArea)event.getSource());
            event.consume();
        }else if (event.getCode() ==KeyCode.UP){
            CommonUtils.SetPreviousFocus((TextArea)event.getSource());
            event.consume();
        }
    }
    
    final ChangeListener<? super Boolean> txtArea_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextArea txtField = (TextArea)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
        
        if(!nv){ /*Lost Focus*/            
            switch (lnIndex){
                case 8: /*sRemarksx*/
                    if (lsValue.length() > 125) lsValue = lsValue.substring(0, 125);
                    
                    poTrans.setMaster("sRemarksx", CommonUtils.TitleCase(lsValue));
                    txtField.setText((String)poTrans.getMaster("sRemarksx"));
                    break;
            }
        }else{ 
            txtField.selectAll();
        }
    };
    
    public void clearFields() {
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        txtField80.setText("");
        txtField81.setText("");
        
        txtField00.setText("");
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText("N-O-N-E");
        txtField05.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField06.setText("");
        txtField07.setText("0.0");
        txtField08.setText("");
        txtField09.setText("");
        txtField10.setText("0.0");
        chk00.setSelected(false);
        loadParameter(0);
        pnEditMode = poTrans.getEditMode() ;
        initButton(pnEditMode);
        
        setTransTat("-1");
    }
    
    final ChangeListener<? super Boolean> txtField_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/
            switch (lnIndex){
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
               case 0:
               case 1:
               case 2:
               case 10:
               case 3:
               case 4:
                   break;
               case 5:
                   if (CommonUtils.isDate(txtField.getText(), pxeDateFormat)){
                        poTrans.setMaster("dTransact", CommonUtils.toDate(txtField.getText()));
                    } else{
                        ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                        poTrans.setMaster("dTransact", CommonUtils.toDate(pxeCurrentDate));
                    }
                    /*get the value from the class*/
                    txtField.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dTransact")));
                    break;
               case 6:
                   if(!lsValue.equals(poTrans.getSource().getMaster("sDescript"))){
                     poTrans.setMaster("sSourceCd", "");
                     txtField.setText("");
                   }
                   txtField10.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nPointsxx"), "0.0"));
                   break;
               case 7: /*nTranAmtx*/
                   /*double nTranAmtx = 0.0;
                    try {
                        nTranAmtx = Double.parseDouble(lsValue);
                    } catch (Exception e) {
                        nTranAmtx = 0.0;
                        txtField.setText("0.0");
                    }
                    poTrans.setMaster("nTranAmtx",  nTranAmtx);
                    txtField.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nTranAmtx"), "0.0"));
                    txtField10.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nPointsxx"), "0.0")); */
                    break;
               case 9:
                   poTrans.setMaster("sSourceNo",  lsValue);
                    if (poTrans.getMaster("nTranAmtx").equals(0.0) && !lsValue.equals("")){
                        ShowMessageFX.Warning(null, pxeModuleName, "Invalid Source No. detected!");
                        txtField07.setText("0.0");
                        txtField10.setText("0.0");
                        txtField09.setText("");
                        return;
                    }
                    
                    txtField.setText((String) poTrans.getMaster("sSourceNo"));
                    txtField07.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nTranAmtx"), "0.0"));
                    txtField10.setText(CommonUtils.NumberFormat((Double)poTrans.getMaster("nPointsxx"), "0.0"));
                   break;
                  
                default:
                   ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
                   return;
            }
           
        } else {
            switch (lnIndex){
                case 5: /*dTransact*/
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
}
