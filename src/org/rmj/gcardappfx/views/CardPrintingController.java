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
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.client.agent.XMClient;
import org.rmj.gcard.trans.agentFX.XMGCApplication;
import org.rmj.gcard.trans.agentFX.XMGCardPrint;


public class CardPrintingController implements Initializable {

    @FXML
    private GridPane NodePane01;
    @FXML
    private TextField txtField03;
    @FXML
    private TextField txtField04;
    @FXML
    private TextField txtField05;
    @FXML
    private TextField txtField06;
    @FXML
    private TextField txtField07;
    @FXML
    private ComboBox cmbAppType;
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
    private TextField txtField11;
    @FXML
    private GridPane NodePane00;
    @FXML
    private TextField txtField01;
    
    private GCardAppFxController mainController ;
    private static final Image search = new Image("org/rmj/gcardappfx/images/search.png");
    private final String pxeModuleName = "G-Card Printing";
    private boolean pbLoaded = false;
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    public final static String pxeOfflinePoints_Entry = "OfflinePoints_Entry.fxml";
    public final static String pxeCardApplication = "CardApplication.fxml";
    ObservableList<String> cAppType = FXCollections.observableArrayList("Replacement", "New", "Renewal");
    private Integer pnEditMode = -1;
    private Integer pnIndex = -1;
    private String psBranchCd = "";
    private static GRider poGRider;
    private final String pxeDateFormat = "yyyy-MM-dd";
    private XMGCardPrint poTrans;
    private String pxeCurrentDate;
    @FXML
    private Label lblTitle;
    @FXML
    private ImageView imageview;
    @FXML
    private CustomTextField txtField00;
    @FXML
    private CustomTextField txtField02;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..",pxeModuleName, "Please Inform MIS/SEG" );
            System.exit(0);
        }
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        pxeCurrentDate = poGRider.getServerDate().toString();
        
        poTrans = new XMGCardPrint(poGRider, psBranchCd);
        
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
        
        txtField00.setLeft(new ImageView(search));
        txtField02.setLeft(new ImageView(search));

        txtField00.setOnKeyPressed(this::txtField_KeyPressed);
        txtField01.setOnKeyPressed(this::txtField_KeyPressed);
        txtField02.setOnKeyPressed(this::txtField_KeyPressed);
        
        cmbAppType.setItems(cAppType);
        cmbAppType.getSelectionModel().select(1);

        clearFields();
        loadGui();
        
        pbLoaded = true;
    
    }
    
    public void setMainController(GCardAppFxController mainController) {
    this.mainController = mainController ;
    }
    
    public void initButton(int fnValue) {
       boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
       
       if (!lbShow) mainController.setFocus(txtField00);
       
       txtField03.setMouseTransparent(!lbShow);
       txtField04.setDisable(!lbShow);
       txtField05.setDisable(!lbShow);
       txtField06.setDisable(!lbShow);
       txtField07.setDisable(!lbShow);
       txtField08.setDisable(!lbShow);
       txtField09.setDisable(!lbShow);
       txtField10.setDisable(!lbShow);
       txtField11.setDisable(!lbShow);
       
       rbtn00.setMouseTransparent(!lbShow);
       rbtn01.setMouseTransparent(!lbShow);
       
       cmbAppType.setMouseTransparent(!lbShow);
    }
    
    public void setBranchCd(String fsBranchCd) {
        this.psBranchCd = fsBranchCd;
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
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    public void loadGui(){
        mainController.cmdButton00.setText("HOME");
        mainController.cmdButton01.setText("SEARCH");
        mainController.cmdButton02.setText("PRINT");
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
        
        mainController.setSiteMap(5);

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
                        
                        if (poTrans.searchWithCondition("sApplicNo", txtField00.getText().replace("-", ""), "")== true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField00);
                        }
                        break;
                    case 2:
                        if (txtField02.getText().equals("")){
                            ShowMessageFX.Warning(null, pxeModuleName, "Please input text to search!");
                            mainController.setFocus(txtField02);
                            return;
                        }
                        
                        if (poTrans.searchWithCondition("sClientNm", txtField02.getText(), "")== true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            mainController.setFocus(txtField02);
                        }
                        break;
                }
                break;
            case "cmdbutton02":
                if(ShowMessageFX.OkayCancel(null, pxeModuleName, "Do you want to tag this application as printed?")==true){
                     if (poTrans.print() == true){
                        ShowMessageFX.Information(null, pxeModuleName, "Application successfully tag as printed!");
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
                        
                        if (poTrans.searchWithCondition("sApplicNo", lsValue.replace("-", ""), "")== true){
                            loadTransaction();
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
                        
                        if (poTrans.searchWithCondition("sClientNm", lsValue, "")== true){
                            loadTransaction();
                        }else{
                            ShowMessageFX.Information(null, "Notice", poTrans.getMessage());
                            return;
                        }
                        break;
                    case 1:
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
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        txtField00.setText("");
        txtField01.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText("");
        txtField05.setText("");
        txtField06.setText("");
        txtField07.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField08.setText("N-O-N-E");
        txtField09.setText("");
        txtField10.setText("");
        txtField11.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        pnEditMode = poTrans.getEditMode();
        initButton(pnEditMode);
        setCardStat("-1");
    }
    
    private void loadTransaction(){
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        XMClient loClient = (XMClient) poTrans.getClient();
        XMGCApplication loApplication = (XMGCApplication) poTrans.getApplication();
        
        txtField00.setText((String) poTrans.getMaster("sApplicNo").toString().replaceFirst("(\\w{6})(\\w{6})", "$1-$2"));
        txtField02.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
        txtField03.setText((String) poTrans.getMaster("sApplicNo").toString().replaceFirst("(\\w{6})(\\w{6})", "$1-$2"));
        txtField04.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName")+ " " +loClient.getMaster("sMiddName"));
        txtField05.setText((String) loClient.getMaster("sAddressx") + ", " +loClient.getTown().getMaster("sTownName") + " " +loClient.getTown().getProvince().getMaster("sProvName") +" "+ loClient.getTown().getMaster("sZippCode"));
        txtField06.setText((String) poTrans.getBranch((String) poTrans.getMaster("sGCardNox").toString().substring(0, 4)).getMaster("sBranchNm"));
        txtField07.setText(CommonUtils.xsDateMedium((Date) loApplication.getMaster("dTransact")));
        txtField08.setText("N-O-N-E");
        txtField09.setText((String) poTrans.getMaster("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        txtField10.setText((String) loApplication.getMaster("sNmOnCard"));
        txtField11.setText(CommonUtils.xsDateMedium((Date) poTrans.getMaster("dMemberxx")));
        
        if (poTrans.getMaster("cCardType").toString().equals("0")){
            rbtn00.setSelected(true);
        }else{
            rbtn01.setSelected(true);
        }
        
        try{
            cmbAppType.getSelectionModel().select(Integer.parseInt(loApplication.getMaster("cApplType").toString()));
        }catch (NumberFormatException ex){
            cmbAppType.getSelectionModel().select(String.valueOf(loApplication.getMaster("cApplType").toString()));
        }
        
        setCardStat((String) poTrans.getMaster("cCardStat"));
        
        loClient = null;
        loApplication = null;
        pnEditMode = poTrans.getEditMode();
    
    }
    
    final ChangeListener<? super Boolean> txtField_Focus = (o,ov,nv)->{
        if (!pbLoaded) return;
        
        TextField txtField = (TextField)((ReadOnlyBooleanPropertyBase)o).getBean();
        int lnIndex = Integer.parseInt(txtField.getId().substring(8, 10));
        String lsValue = txtField.getText();
        
        if (lsValue == null) return;
            
        if(!nv){ /*Lost Focus*/
            switch (lnIndex){
               case 1:
                   if (CommonUtils.isDate(lsValue, pxeDateFormat)){
                        poTrans.setTranDate(CommonUtils.toDate(lsValue));
                    } else{
                        ShowMessageFX.Warning("Invalid date entry.", pxeModuleName, "Date format must be yyyy-MM-dd (e.g. 1991-07-07)");
                        poTrans.setTranDate(CommonUtils.toDate(pxeCurrentDate));
                    }
                    txtField.setText(CommonUtils.xsDateMedium((Date) poTrans.getTranDate()));
                    return;
               case 0:
               case 2:
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
        }
        pnIndex = lnIndex;
        txtField.selectAll(); 
    };
}
