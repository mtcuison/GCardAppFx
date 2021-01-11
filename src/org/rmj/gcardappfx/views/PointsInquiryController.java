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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import org.rmj.appdriver.constants.GCDeviceType;
import org.rmj.client.agent.XMClient;
import org.rmj.gcard.device.ui.GCardDevice;
import org.rmj.gcard.trans.agentFX.XMGCInquiry;
import org.rmj.gcard.trans.agentFX.XMGCOnPoints;

public class PointsInquiryController implements Initializable {

    @FXML
    private TableView tblUnposted;
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
    private TextField txtField07;
    @FXML
    private TextField txtField08;
    @FXML
    private RadioButton rbtn01;
    @FXML
    private TextField txtField12;
    @FXML
    private TextField txtField13;
    @FXML
    private TextField txtField14;
    @FXML
    private TextField txtField05;
    @FXML
    private TextField txtField06;
    @FXML
    private TextField txtField09;
    @FXML
    private TextField txtField15;
    @FXML
    private TextField txtField10;
    @FXML
    private TextField txtField11;
    
    private GCardAppFxController mainController ;
    public static final Image search = new Image("org/rmj/gcardappfx/images/search.png") ;
    private final String pxeModuleName = "G-Card Points Inquiry";
    public final static String pxePointsUpdate = "PointsUpdate.fxml";
    public final static String pxeCardApplication = "CardApplication.fxml";
    public final static String pxeCardApplicationB = "CardApplicationB.fxml";
    public final static String pxeOnlinePointsEntry = "OnlinePoints_Entry.fxml";
    public final static String pxeOfflinePoints_Entry = "OfflinePoints_Entry.fxml";
    public final static String pxeOfflinePoints_EntryB = "OfflinePoints_EntryB.fxml";
    private static GRider poGRider;
    private XMGCInquiry poTrans;
    private String sTranStat;
    private int pnRow = -1;
    public Config properties;
    private int pnIndex = -1;
    private int pnEditMode = -1;
    private boolean pbLoaded = false;
    private final String pxeDateFormat = "yyyy-MM-dd";
    private String psBranchCd = "";
    private ObservableList<TableModel> data = FXCollections.observableArrayList();
    private String pxeCurrentDate;
    
    @FXML
    private Label lblTitle;
    @FXML
    private GridPane NodePane00;
    @FXML
    private ImageView imageview;
    @FXML
    private RadioButton rbtn00;
    @FXML
    private Label lblUnposted;
    @FXML
    private Label lblLastTransaction;
    @FXML
    private Label lblTranStatus;
    @FXML
    private Label lblServerStat;
    @FXML
    private CustomTextField txtSeeks00;
    @FXML
    private CustomTextField txtSeeks01;
    @FXML
    private ComboBox cmbCardClass;
    
    private ObservableList<String> sDeviceType = FXCollections.observableArrayList("Smartcard", "App QR Code", "Non-Chip Card");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..",pxeModuleName, "Please Inform MIS/SEG" );
            System.exit(0);
        }
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        pxeCurrentDate = poGRider.getServerDate().toString();
        properties = new Config();
        poTrans = new XMGCInquiry(poGRider, psBranchCd , false);
        
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
        
        txtSeeks00.setOnKeyPressed(this::txtSeeks_Keypressed);
        txtSeeks01.setOnKeyPressed(this::txtSeeks_Keypressed);
        
        poTrans.ClearGCardProperty();
        cmbCardClass.setItems(sDeviceType);
        
        clearFields();
        loadGui();
        initGridView();
        
        cmbCardClass.getSelectionModel().select(0);
        txtSeeks00.setDisable(true);
        txtSeeks01.setDisable(true);
        
        pbLoaded = true;
    }
    
    public void setBranchCd(String fsBranchCd) {
        this.psBranchCd = fsBranchCd;
    }
    
    public void setMainController(GCardAppFxController mainController) {
    this.mainController = mainController ;
    }
    
    public void initGridView(){
        
        TableColumn index01 = new TableColumn("Branch");
        TableColumn index02 = new TableColumn("Trans. Date");
        TableColumn index03 = new TableColumn("Source Code");
        TableColumn index04 = new TableColumn("Source No");
        TableColumn index05 = new TableColumn("Tran Amt");
        TableColumn index06 = new TableColumn("Points");
        TableColumn index07 = new TableColumn("Status");
        
        index01.setSortable(false); index01.setResizable(false);index01.setStyle("-fx-alignment: CENTER");
        index02.setSortable(false); index02.setResizable(false);index02.setStyle("-fx-alignment: CENTER");
        index03.setSortable(false); index03.setResizable(false);index03.setStyle("-fx-alignment: CENTER");
        index04.setSortable(false); index04.setResizable(false);index04.setStyle("-fx-alignment: CENTER");
        index05.setSortable(false); index05.setResizable(false);index05.setStyle("-fx-alignment: CENTER");
        index06.setSortable(false); index06.setResizable(false);index06.setStyle("-fx-alignment: CENTER");
        index07.setSortable(false); index07.setResizable(false);index07.setStyle("-fx-alignment: CENTER");
       
        tblUnposted.getColumns().clear();        
        tblUnposted.getColumns().add(index01);
        tblUnposted.getColumns().add(index02);
        tblUnposted.getColumns().add(index03);
        tblUnposted.getColumns().add(index04);
        tblUnposted.getColumns().add(index05);
        tblUnposted.getColumns().add(index06);
        tblUnposted.getColumns().add(index07);
        
        index01.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index01"));
        index02.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index02"));
        index03.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index03"));
        index04.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index04"));
        index05.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index05"));
        index06.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index06"));
        index07.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index07"));
        
        index01.prefWidthProperty().bind(tblUnposted.widthProperty().divide(100/13));
        index02.prefWidthProperty().bind(tblUnposted.widthProperty().divide(100/13));
        index03.prefWidthProperty().bind(tblUnposted.widthProperty().divide(100/13));
        index04.prefWidthProperty().bind(tblUnposted.widthProperty().divide(100/13));
        index05.prefWidthProperty().bind(tblUnposted.widthProperty().divide(100/13));
        index06.prefWidthProperty().bind(tblUnposted.widthProperty().divide(100/13));
        index07.prefWidthProperty().bind(tblUnposted.widthProperty().divide(100/13));
        
        /*Set data source to table*/
        data.clear();
        tblUnposted.setItems(data);
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
    
    public void loadTransaction(){
        txtSeeks00.setText(System.getProperty("app.card.no"));
        txtSeeks01.setText(System.getProperty("app.gcard.holder"));

        cmbCardClass.getSelectionModel().select(Integer.parseInt(System.getProperty("app.device.type")));
        txtSeeks00.setDisable(System.getProperty("app.device.type").equals("0"));
        txtSeeks01.setDisable(System.getProperty("app.device.type").equals("0"));
        
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        
        XMClient loClient = (XMClient) poTrans.getGCard().getClient();
        GCardDevice loDevice = (GCardDevice) poTrans.getDeviceInfo();
        
        if (System.getProperty("app.card.connected").equals("1")){
            if ((Boolean)loDevice.getCardInfo("bIsOnline")){
                lblTranStatus.setText("ONLINE");
                lblTranStatus.setStyle("-fx-text-fill: green");
            }else{
                lblTranStatus.setText("OFFLINE");
                lblTranStatus.setStyle("-fx-text-fill: red");
            }
        }
        
        txtSeeks00.setText((String) loDevice.getCardInfo("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        txtField00.setText((String) loDevice.getCardInfo("sCardNmbr").toString().replaceFirst("(\\w{5})(\\w{6})(\\w+)", "$1-$2-$3"));
        txtField03.setText((String) poTrans.getBranch(loDevice.getCardInfo("sGCardNox").toString().substring(0, 4)).getBranchNm());
        txtField06.setText((String) CommonUtils.NumberFormat(Double.valueOf(loDevice.getCardInfo("nTotPoint").toString()), "0.0"));
        
        if (loDevice.getCardInfo("cCardType").toString().equals("0")){
            rbtn00.setSelected(true);
        }else{
            rbtn01.setSelected(true);
        }
        setCardStat((String) loDevice.getCardInfo("cCardStat"));

        if (!System.getProperty("app.device.type").equals("2")){
            if (Double.valueOf(loDevice.getCardInfo("nAvlPoint").toString()) 
                < Double.valueOf(loDevice.getCardInfo("nDevPoint").toString())){
                txtField05.setText((String) CommonUtils.NumberFormat(Double.valueOf(loDevice.getCardInfo("nAvlPoint").toString()), "0.0"));
            } else {
                txtField05.setText((String) CommonUtils.NumberFormat(Double.valueOf(loDevice.getCardInfo("nDevPoint").toString()), "0.0"));
            }
        } else
            txtField05.setText((String) CommonUtils.NumberFormat(Double.valueOf(loDevice.getCardInfo("nAvlPoint").toString()), "0.0"));
        
        poTrans.loadLastFromCard();
        
        txtSeeks01.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName") + " " +loClient.getMaster("sMiddName"));
        txtField01.setText((String) loClient.getMaster("sLastName")+ ", " +loClient.getMaster("sFrstName") + " " +loClient.getMaster("sMiddName"));
        txtField02.setText((String) loClient.getMaster("sAddressx") + ", " +loClient.getTown().getMaster("sTownName") + " " +loClient.getTown().getProvince().getMaster("sProvName") +" "+ loClient.getTown().getMaster("sZippCode"));
        txtField04.setText(SQLUtil.dateFormat((Date) poTrans.getGCard().getMaster("dActivate"), SQLUtil.FORMAT_LONG_DATE));
        txtField10.setText(SQLUtil.dateFormat((Date) poTrans.getGCard().getMaster("dMemberxx"), SQLUtil.FORMAT_LONG_DATE));
        txtField11.setText("N-O-N-E");
        //txtField12.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getGCard().getMaster("nPointsxx").toString()), "0.0"));
        
        /*Last Point Transaction*/
        if(poTrans.getMaster("sLastLine") == null || poTrans.getMaster("sLastLine").equals("")) {
            txtField07.setText("");
            txtField08.setText("");
            txtField09.setText("0.0");
            txtField13.setText("");
            txtField14.setText(CommonUtils.xsDateMedium((Date) CommonUtils.toDate(pxeCurrentDate)));
            txtField15.setText("0.0");
        }else {
            XMGCOnPoints loLastOnline = poTrans.getLastOnline();
            txtField07.setText((String)loLastOnline.getSource().getMaster("sDescript"));
            txtField08.setText((String)loLastOnline.getBranch().getMaster("sBranchNm"));
            txtField09.setText(CommonUtils.NumberFormat(Double.valueOf((double) loLastOnline.getMaster("nTranAmtx")), "#0.00"));
            txtField13.setText((String)loLastOnline.getMaster("sSourceNo"));
            txtField14.setText(CommonUtils.xsDateMedium((Date) loLastOnline.getMaster("dTransact")));
            txtField15.setText(CommonUtils.NumberFormat(Double.valueOf((double) loLastOnline.getMaster("nPointsxx")), "0.0"));
            loLastOnline = null;
        }
        
        //if(properties.getPropValues().equals("1")){
            if (poTrans.loadOffLineLedger() == true){
                loadOfflinePoints();
            }
        //}
        
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
        
        loClient = null;
        loDevice = null;
        pnEditMode = poTrans.getEditMode();
    }
    
    public void loadGui(){
        mainController.cmdButton00.setText("HOME");
        mainController.cmdButton01.setText("CONNECT");
        mainController.cmdButton02.setText("POINTS UPDATE");
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
        
        mainController.setSiteMap(8);
    }
    
    public void initButton(int fnValue) {
       boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
       
       if (!lbShow) mainController.setFocus(txtSeeks00);
       
       txtField00.setMouseTransparent(!lbShow);
       txtField01.setDisable(!lbShow);
       txtField02.setDisable(!lbShow);
       txtField03.setDisable(!lbShow);
       txtField04.setDisable(!lbShow);
       txtField05.setDisable(!lbShow);
       txtField06.setDisable(!lbShow);
       txtField07.setDisable(!lbShow);
       txtField08.setDisable(!lbShow);
       txtField09.setDisable(!lbShow);
       txtField10.setDisable(!lbShow);
       txtField11.setDisable(!lbShow);
       txtField12.setDisable(!lbShow);
       txtField13.setDisable(!lbShow);
       txtField14.setDisable(!lbShow);
       txtField15.setDisable(!lbShow);
       
       rbtn00.setMouseTransparent(!lbShow);
       rbtn01.setMouseTransparent(!lbShow);
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
                        //disconnect the card
                        poTrans.releaseCard();
                    }else{
                        ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                    }
                }
                break;
            case "cmdbutton02":
                mainController.loadModule(pxePointsUpdate);
                break;
            case "cmdbutton03":
            case "cmdbutton04":
            case "cmdbutton05":
            case "cmdbutton06":
            case "cmdbutton07":
            case "cmdbutton08":
            case "cmdbutton10":
            case "cmdbutton11":
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
    
    public void loadOfflinePoints(){
        int lnCtr;
        int lnRow = poTrans.getOffLineSize();
        double total = 0.0;
        
        if (lnRow == 1){
            if (poTrans.getDetail(0, "sTransNox").toString().equals("")){
               // ShowMessageFX.Warning("No unposted offline points transaction...", pxeModuleName, "Notice");
               txtField12.setText("0.0");
               initGridView();
               return;
            }
        }
        
        data.clear();
        /*ADD THE DETAIL*/
        for(lnCtr = 0; lnCtr <= lnRow -1; lnCtr++){
            total = total + (Double) poTrans.getDetail(lnCtr, "nPointsxx");
            switch((String) poTrans.getDetail(lnCtr, "cTranStat")){
                case "0":
                sTranStat = "Open"; break;
                case "1":
                    sTranStat = "Verified"; break;
                case "2":
                    sTranStat = "Posted"; break;    
                case "3":
                    sTranStat = "Cancelled"; break;
                case "4":
                    sTranStat = "Void"; break;
                default:
                    sTranStat = "Unkown";
            }
            
            data.add(new TableModel((String) poTrans.getBranch((String) poTrans.getDetail(lnCtr, "sTransNox").toString().substring(0, 4)).getBranchNm(), 
                                    CommonUtils.xsDateMedium((Date) poTrans.getDetail(lnCtr, "dTransact")),
                                    (String) poTrans.getSource((String) poTrans.getDetail(lnCtr, "sSourceCd").toString()).getDescription(),
                                    (String) poTrans.getDetail(lnCtr, "sSourceNo"),
                                    String.valueOf(CommonUtils.NumberFormat(Double.valueOf(poTrans.getDetail(lnCtr, "nTranAmtx").toString()), "#0.00")),
                                    String.valueOf(poTrans.getDetail(lnCtr, "nPointsxx")),
                                    String.valueOf(sTranStat),
                                    "",
                                    "",
                                    ""));                            
        }
        //display total offline points for posting
        txtField12.setText(CommonUtils.NumberFormat(Double.valueOf(total) , "0.0"));
    
        /*FOCUS ON FIRST ROW*/
        if (!data.isEmpty()){
            tblUnposted.getSelectionModel().select(lnRow -1);
            tblUnposted.getFocusModel().focus(lnRow -1);
            pnRow = tblUnposted.getSelectionModel().getSelectedIndex();           
        }
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
                        
                        if (poTrans.searchWithCondition("sCardNmbr", lsValue.replace("-", ""), "") ==true){                            
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
                        if (poTrans.searchWithCondition("sClientNm", lsValue, "") == true){
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
        mainController.lblCardNmbr.setText("N-O-N-E");
        mainController.lblDeviceType.setText("UNKNOWN");
        mainController.lblMobileNo.setText("N-O-N-E"); 
        
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        txtSeeks00.setText("");
        txtSeeks01.setText("");
        txtField00.setText("");
        txtField01.setText("");
        txtField02.setText("");
        txtField03.setText("");
        txtField04.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField05.setText("0.0");
        txtField06.setText("0.0");
        txtField07.setText("");
        txtField08.setText("");
        txtField09.setText("0.0");
        txtField10.setText("");
        txtField11.setText("N-O-N-E");
        txtField12.setText("0.0");
        txtField13.setText("");
        txtField14.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField15.setText("0.0");
        lblTranStatus.setText("UNKNOWN");
        lblTranStatus.setStyle("-fx-text-fill: black");
        pnEditMode = poTrans.getEditMode();
        initButton(pnEditMode);
        setCardStat("-1");
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
                mainController.cmdButton01.setDisable(false);
                break;
            case 1:
            case 2:
                mainController.cmdButton01.setDisable(true);
                txtSeeks00.setDisable(false);
                txtSeeks01.setDisable(false);
                txtSeeks00.requestFocus();
        }
        
        System.setProperty("app.device.type", String.valueOf(cmbCardClass.getSelectionModel().getSelectedIndex()));
    }
}