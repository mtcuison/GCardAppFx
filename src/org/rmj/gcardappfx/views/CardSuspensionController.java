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
import org.rmj.gcard.trans.agentFX.XMGCardSuspend;

public class CardSuspensionController implements Initializable {

    @FXML
    private TextField txtField04;
    @FXML
    private TextField txtField05;
    @FXML
    private TextField txtField06;
    @FXML
    private TextField txtField07;
    @FXML
    private TextField txtField10;
    @FXML
    private TextField txtField11;
    @FXML
    private RadioButton rbtn00;
    @FXML
    private RadioButton rbtn01;
    @FXML
    private TextArea txtField02;
    
    private GCardAppFxController mainController ;
    public static final Image search = new Image("org/rmj/gcardappfx/images/search.png");
    private final String pxeModuleName = "G-Card Suspension";
    private boolean pbLoaded = false;
    private static GRider poGRider;
    private XMGCardSuspend poTrans;
    private Integer pnEditMode = -1;
    private Integer pnIndex = -1;
    private String psBranchCd = "";
    private final String pxeDateFormat = "yyyy-MM-dd";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    public final static String pxeOfflinePoints_Entry = "OfflinePoints_Entry.fxml";
    public final static String pxeCardApplication = "CardApplication.fxml";
    public final static String pxeOnlinePointsEntry = "OnlinePoints_Entry.fxml";
    public final static String pxeOfflinePoints_EntryB = "OfflinePoints_EntryB.fxml";
    public final static String pxeCardApplicationB = "CardApplicationB.fxml";
    private String pxeCurrentDate;
    public Config properties;
    @FXML
    private Label lblTitle;
    @FXML
    private GridPane NodePane01;
    @FXML
    private GridPane NodePane00;
    @FXML
    private TextField txtField08;
    @FXML
    private TextField txtField09;
    @FXML
    private ImageView imageview;
    @FXML
    private TextField txtField01;
    @FXML
    private CustomTextField txtField00;
    @FXML
    private CustomTextField txtField03;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..",pxeModuleName, "Please Inform MIS/SEG" );
            System.exit(0);
        }
        
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        pxeCurrentDate = poGRider.getServerDate().toString();
        
        poTrans = new XMGCardSuspend(poGRider, psBranchCd);
        properties = new Config();
        
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
        txtField02.focusedProperty().addListener(txtArea_Focus);
        txtField03.focusedProperty().addListener(txtField_Focus);

        txtField00.setOnKeyPressed(this::txtField_KeyPressed);
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        txtField02.setOnKeyPressed(this::txtArea_KeyPressed);
        txtField03.setOnKeyPressed(this::txtField_KeyPressed);
        txtField00.setLeft(new ImageView(search));
        txtField03.setLeft(new ImageView(search));
        
        clearFields();
        loadGui();
        
        pbLoaded = true;
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
    
    public void setMainController(GCardAppFxController mainController) {
    this.mainController = mainController ;
    }
    
    public void setBranchCd(String fsBranchCd) {
        this.psBranchCd = fsBranchCd;
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    public void initButton(int fnValue) {
       boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
       
       if (!lbShow) mainController.setFocus(txtField00);
       txtField04.setMouseTransparent(!lbShow);
       txtField05.setDisable(!lbShow);
       txtField06.setDisable(!lbShow);
       txtField07.setDisable(!lbShow);
       txtField08.setDisable(!lbShow);
       txtField09.setDisable(!lbShow);
       txtField10.setDisable(!lbShow);
       txtField11.setDisable(!lbShow);
       
       rbtn00.setMouseTransparent(!lbShow);
       rbtn01.setMouseTransparent(!lbShow);
    }
    
    public void loadGui(){
        mainController.cmdButton00.setText("HOME");
        mainController.cmdButton01.setText("SEARCH");
        mainController.cmdButton02.setText("SUSPEND");
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
            
        mainController.setSiteMap(6);
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
                        if (txtField00.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtField00);
                            return;
                        }
                        
                        if(poTrans.searchWithCondition("sCardNmbr", txtField00.getText().replace("-", ""), "") == true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField00);
                        }
                        break;
                    case 3:
                        if (txtField03.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtField03);
                            return;
                        }
                        
                        if(poTrans.searchWithCondition("sClientNm", txtField03.getText(), "") == true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField03);
                        }
                        break;
                }
                break;
            case "cmdbutton02":
                if (poTrans.deactivate()==  true){
                    ShowMessageFX.Warning(null, pxeModuleName, "Card successfully de-activated!");
                    clearFields();
                }else{
                    ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                }
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
    
    private void loadTransaction(){
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        XMClient loClient = (XMClient) poTrans.getClient();
        
        txtField00.setText((String) poTrans.getMaster("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        txtField01.setText(CommonUtils.xsDateMedium((Date) CommonUtils.toDate(pxeCurrentDate)));
        txtField02.setText("");
        txtField03.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
        txtField04.setText((String) poTrans.getMaster("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        txtField05.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
        txtField06.setText((String) loClient.getMaster("sAddressx") + ", " +loClient.getTown().getMaster("sTownName") + " " +loClient.getTown().getProvince().getMaster("sProvName") +" "+ loClient.getTown().getMaster("sZippCode"));
        txtField07.setText((String) poTrans.getBranch((String) poTrans.getMaster("sGCardNox").toString().substring(0, 4)).getMaster("sBranchNm"));
        txtField08.setText(CommonUtils.xsDateMedium((Date) poTrans.getApplication(psBranchCd).getMaster("dTransact")));
        txtField09.setText("N-O-N-E");
        txtField10.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nPointsxx").toString()), "0.0"));
        txtField11.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nPointsxx").toString()), "0.0"));
        
        if (poTrans.getMaster("cCardType").toString().equals("0")){
            rbtn00.setSelected(true);
        }else{
            rbtn01.setSelected(true);
        }
        
        setCardStat((String) poTrans.getMaster("cCardStat"));
        
        loClient = null;
        pnEditMode = poTrans.getEditMode();
    
    }
    
    public void txtField_KeyPressed(KeyEvent event) {
        TextField txtField = (TextField)event.getSource();        
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (event.getCode() == ENTER || event.getCode() == F3) {
                switch (lnIndex){
                    case 0:
                        if (lsValue.equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            return;    
                        }
                        
                       if(poTrans.searchWithCondition("sCardNmbr", lsValue.replace("-", ""), "") == true){
                            loadTransaction();  
                        }else{
                           ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                       }
                        break;
                    case 3:
                        
                        if (lsValue.equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            return;    
                        }
                        
                        if(poTrans.searchWithCondition("sClientNm", txtField03.getText(), "") == true){
                            loadTransaction();  
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                        break;
                    case 1:
                    case 2:
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
    
    public void clearFields() {
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        txtField00.setText("");
        txtField01.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText("");
        txtField05.setText("");
        txtField07.setText("");
        txtField06.setText("");
        txtField07.setText("");
        txtField08.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField09.setText("N-O-N-E");
        txtField10.setText("0.0");
        txtField11.setText("0.0");
        pnEditMode = poTrans.getEditMode();
        initButton(pnEditMode);
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
                   break;
               case 1: /*setTranDate*/
                   if (CommonUtils.isDate(lsValue, pxeDateFormat)){
                        poTrans.setTranDate(CommonUtils.toDate(lsValue));
                    } else{
                        ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                        poTrans.setTranDate(CommonUtils.toDate(pxeCurrentDate));
                    }
                    txtField.setText(CommonUtils.xsDateMedium((Date) CommonUtils.toDate(lsValue)));
                    break;
               case 3:
                break;
                  
                default:
                   ShowMessageFX.Warning(null, pxeModuleName, "Text field with name " + txtField.getId() + " not registered.");
                   return;
            }
           
        } else {
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
            txtField.selectAll(); 
            pnIndex = lnIndex;
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
                case 2: /*sRemarksx*/
                    if (lsValue.length() > 256) lsValue = lsValue.substring(0, 256);
                    
                    poTrans.setRemarks( CommonUtils.TitleCase(lsValue));
                    txtField.setText((String) CommonUtils.TitleCase(lsValue));
                    break;
            }
        }else{ 
            txtField.selectAll();
        }
    };
}
