package org.rmj.gcardappfx.views;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.rmj.appdriver.GRider;
import org.rmj.appdriver.SQLUtil;
import org.rmj.appdriver.agentfx.CommonUtils;
import org.rmj.appdriver.agentfx.ShowMessageFX;
import org.rmj.appdriver.constants.EditMode;
import org.rmj.client.agent.XMClient;
import org.rmj.gcard.device.ui.GCardDevice;
import org.rmj.gcard.trans.agentFX.XMGCPreOrder;


public class CardPreOrderController implements Initializable {

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
    private TextField txtField07;
    @FXML
    private ImageView imageview;
    @FXML
    private RadioButton rbtn00;
    @FXML
    private RadioButton rbtn01;
    @FXML
    private TextField txtField10;
    
    private GCardAppFxController mainController ;
    private final String pxeModuleName = "G-Card Pre-Ordered Items";
    private boolean pbLoaded = false;
    public final static String pxeCardApplicationB = "CardApplicationB.fxml";
    public final static String pxeOthers = "CardOthers.fxml";
    public final static String pxeOnlinePointsEntry = "OnlinePoints_Entry.fxml";
    public final static String pxePointsInquiry = "PointsInquiry.fxml";
    public final static String pxeOfflinePoints_EntryB = "OfflinePoints_EntryB.fxml";
    private ObservableList<TableModel> data = FXCollections.observableArrayList();
    private String pxeCurrentDate;
    private String pxeCardNmbr = "";
    private String pxeClientNm = "";
    private final String pxeDateFormat = "yyyy-MM-dd";
    private int pnEditMode = -1;
    private XMGCPreOrder poTrans;
    private static GRider poGRider;
    private String psBranchCd = "";
    @FXML
    private Label lblTranStatus;
    @FXML
    private Label lblUnposted;
    @FXML
    private TableView tblOrdered;
    @FXML
    private Label labelTotal;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblServerStat;
    @FXML
    private TextField txtField08;
    @FXML
    private TextField txtField09;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblLocationStat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (poGRider == null){
            ShowMessageFX.Warning("GhostRider Application not set..",pxeModuleName, "Please Inform MIS/SEG" );
            System.exit(0);
        }
        
        if (psBranchCd.equals("")) psBranchCd = poGRider.getBranchCode();
        
        pxeCurrentDate = poGRider.getServerDate().toString();
        
        poTrans = new XMGCPreOrder(poGRider ,psBranchCd, false);
        
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

        clearFields();
        loadGui();
        
        pbLoaded = true;
    }
    
    public void setBranchCd(String fsBranchCd) {
        this.psBranchCd = fsBranchCd;
    }

    public void setMainController(GCardAppFxController mainController) {
    this.mainController = mainController ;
    }
    
    public void setGRider(GRider foGRider){this.poGRider = foGRider;}
    
    public void loadGui(){
        mainController.cmdButton00.setText("HOME");
        mainController.cmdButton01.setText("CONNECT");
        mainController.cmdButton02.setText("RELEASE");
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

        mainController.setSiteMap(17);
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
                if (poTrans.connectCard()== true){
                    loadTransaction();
                }else{
                    ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
                }
                break;
            case "cmdbutton02":
                if (poTrans.saveUpdate() == true){
                   ShowMessageFX.Information(null, pxeModuleName, "Transaction successfully redeemed!");
                   if( ShowMessageFX.YesNo(null, pxeModuleName, "Do you want to print this transasction?")== true){
                       if (!printPreOrder()) return;
                   }
                   clearFields();
                }else{
                   ShowMessageFX.Warning(null, pxeModuleName, poTrans.getMessage());
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
       boolean lbShow = (fnValue == EditMode.ADDNEW || fnValue == EditMode.UPDATE);
       
       if (lbShow) mainController.setFocus(txtField06);
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
    
    public void clearFields() {
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
        txtField08.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField09.setText(CommonUtils.xsDateMedium(CommonUtils.toDate(pxeCurrentDate)));
        txtField10.setText("");
        lblTotal.setText("0.0");
        lblTranStatus.setText("UNKNOWN");
        lblTranStatus.setStyle("-fx-text-fill: black");
        initButton(0);
        initGridView();
        setTransTat("-1");
        setPlaceOrder("-1");
    }
    
    private void loadTransaction(){
        rbtn00.setSelected(false);
        rbtn01.setSelected(false);
        GCardDevice loDevice = (GCardDevice) poTrans.getDeviceInfo();
        XMClient loClient = (XMClient) poTrans.getGCard().getClient();
        
        if ((Boolean)loDevice.getCardInfo("bIsOnline")){
            lblTranStatus.setText("ONLINE");
            lblTranStatus.setStyle("-fx-text-fill: green");
        }else{
            lblTranStatus.setText("OFFLINE");
            lblTranStatus.setStyle("-fx-text-fill: red");
        }
        
        txtField00.setText(poTrans.getMaster("sTransNox").toString().substring(4));
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
        
        txtField06.setText(SQLUtil.dateFormat(CommonUtils.toDate((String)poTrans.getMaster("dPickupxx")), SQLUtil.FORMAT_LONG_DATE));
        txtField07.setText((String) poTrans.getBranch((String) poTrans.getMaster("sBranchCd").toString()).getMaster("sBranchNm"));
        txtField08.setText(CommonUtils.xsDateMedium((Date) loClient.getMaster("dBirthDte")));
        txtField09.setText(SQLUtil.dateFormat(CommonUtils.toDate((String)poTrans.getMaster("dPlacOrdr")), SQLUtil.FORMAT_LONG_DATE));
        txtField10.setText((String) poTrans.getMaster("sReferNox"));
        
        lblTotal.setText(CommonUtils.NumberFormat(Double.valueOf(poTrans.getMaster("nPointsxx").toString()), "0.0"));
        
        if (loDevice.getCardInfo("cCardType").toString().equals("0")){
            rbtn00.setSelected(true);
        }else{
            rbtn01.setSelected(true);
        }
        
        setTransTat((String) poTrans.getMaster("cTranStat"));
        setPlaceOrder((String) poTrans.getMaster("cPlcOrder"));
        
        loClient = null;
        loDevice = null;
        
        loadDetail();
        pnEditMode = 0;
    }
    
    private void loadDetail(){
        int lnCtr;
        int lnRow = poTrans.ItemCount();
        int pnRow;
        double lnRowTotal = 0.0;
        
        JSONParser parser = new JSONParser();
        
        data.clear();
        /*ADD THE DETAIL*/
        
          for(lnCtr = 0; lnCtr <= lnRow -1; lnCtr++){
                try {
                    JSONObject json = (JSONObject) parser.parse(String.valueOf(poTrans.getDetail(lnCtr, (String) poTrans.getMaster("sPromoIDx"))));
                    String sPromoIDx = (String) json.get("sPromoIDx");
                    String nItemQtyx=(String) json.get("nItemQtyx");
                    String nPointsxx=(String) json.get("nPointsxx");
                    
                    lnRowTotal = Integer.valueOf(nItemQtyx) * Double.parseDouble(nPointsxx);
                    data.add(new TableModel((String.valueOf(lnCtr + 1)),
                                   (String) sPromoIDx,
                                    (String) poTrans.getPromo((String) sPromoIDx.toString()).getMaster("sPromDesc"),
                                    Integer.valueOf(nItemQtyx).toString(),
                                    Double.valueOf(nPointsxx).toString(),
                                    CommonUtils.NumberFormat(lnRowTotal, "0.00"),
                                    "",
                                    "",
                                    "",
                                    "")); 
                } catch (ParseException ex) {
                    Logger.getLogger(CardPreOrderController.class.getName()).log(Level.SEVERE, null, ex);
                }                           
        } 
    
        /*FOCUS ON FIRST ROW*/
        if (!data.isEmpty()){
            tblOrdered.getSelectionModel().select(lnRow -1);
            tblOrdered.getFocusModel().focus(lnRow -1);
            
            pnRow = tblOrdered.getSelectionModel().getSelectedIndex();           
        } 
    }
    
    private boolean printPreOrder(){
        JSONArray json_arr = new JSONArray();
        json_arr.clear();
        
        JSONParser parser = new JSONParser();
        
        for( int lnCtr = 0; lnCtr <= poTrans.ItemCount() -1; lnCtr++){
            try {
                JSONObject json = (JSONObject) parser.parse(String.valueOf(poTrans.getDetail(lnCtr, (String) poTrans.getMaster("sPromoIDx"))));
                String sPromoIDx = (String) json.get("sPromoIDx");
                String nItemQtyx=(String) json.get("nItemQtyx");
                String nPointsxx=(String) json.get("nPointsxx"); 
                
                JSONObject json_obj = new JSONObject();
                
                json_obj.put("sField05", (String.valueOf(lnCtr + 1)));
                json_obj.put("sField01", (String) sPromoIDx);
                json_obj.put("sField02", poTrans.getPromo((String) sPromoIDx.toString()).getMaster("sPromDesc"));
                json_obj.put("nField01", (String) nItemQtyx);
                json_obj.put("lField01", (String) nPointsxx);
                json_arr.add(json_obj);

            } catch (ParseException ex) {
                Logger.getLogger(CardPreOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }                           
        } 
        //Create the parameter
        Map<String, Object> params = new HashMap<>();
        params.put("sBranchNm", poGRider.getBranchName());
        params.put("sBranchAd", poGRider.getAddress());
        params.put("sCardNmbr", pxeCardNmbr);
        params.put("sClientNm", pxeClientNm);
     
        params.put("sTransNox", poTrans.getMaster("sTransNox").toString().substring(4));
        params.put("sReportDt", SQLUtil.dateFormat(CommonUtils.toDate((String)poTrans.getMaster("dPickupxx")), SQLUtil.FORMAT_LONG_DATE));
        params.put("sPrintdBy", mainController.pxeUserName);

        try {
            InputStream stream = new ByteArrayInputStream(json_arr.toJSONString().getBytes("UTF-8"));
            JsonDataSource jrjson = new JsonDataSource(stream); 

            JasperPrint _jrprint = JasperFillManager.fillReport("d:/GGC_Java_Systems/reports/CardPreOrder.jasper", params, jrjson);
            JasperViewer jv = new JasperViewer(_jrprint, false);
            jv.setVisible(true);
        } catch (JRException | UnsupportedEncodingException  ex) {
            Logger.getLogger(CardPreOrderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }
    
    public void initGridView(){
        
        TableColumn index01 = new TableColumn("No");
        TableColumn index02 = new TableColumn("Promo Code");
        TableColumn index03 = new TableColumn("Description");
        TableColumn index04 = new TableColumn("Quantity");
        TableColumn index05 = new TableColumn("Points");
        TableColumn index06 = new TableColumn("Total");
        
        index01.setSortable(false); index01.setResizable(false);index01.setStyle("-fx-alignment: CENTER");
        index02.setSortable(false); index02.setResizable(false);index02.setStyle("-fx-alignment: CENTER");
        index03.setSortable(false); index03.setResizable(false);index03.setStyle("-fx-alignment: CENTER");
        index04.setSortable(false); index04.setResizable(false);index04.setStyle("-fx-alignment: CENTER");
        index05.setSortable(false); index05.setResizable(false);index05.setStyle("-fx-alignment: CENTER");
        index06.setSortable(false); index06.setResizable(false);index06.setStyle("-fx-alignment: CENTER");
       
        tblOrdered.getColumns().clear();
        tblOrdered.getColumns().add(index01);
        tblOrdered.getColumns().add(index02);
        tblOrdered.getColumns().add(index03);
        tblOrdered.getColumns().add(index04);
        tblOrdered.getColumns().add(index05);
        tblOrdered.getColumns().add(index06);
        
        index01.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index01"));
        index02.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index02"));
        index03.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index03"));
        index04.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index04"));
        index05.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index05"));
        index06.setCellValueFactory(new PropertyValueFactory<org.rmj.gcardappfx.views.TableModel,String>("index06"));
        
        index01.prefWidthProperty().bind(tblOrdered.widthProperty().divide(100/12));
        index02.prefWidthProperty().bind(tblOrdered.widthProperty().divide(100/20));
        index03.prefWidthProperty().bind(tblOrdered.widthProperty().divide(100/24));
        index04.prefWidthProperty().bind(tblOrdered.widthProperty().divide(100/16));
        index05.prefWidthProperty().bind(tblOrdered.widthProperty().divide(100/12));
        index06.prefWidthProperty().bind(tblOrdered.widthProperty().divide(100/12));
        
        /*Set data source to table*/
        data.clear();
        tblOrdered.setItems(data);
    }
    
    private void setTransTat(String fsValue){
        switch(fsValue){
            case "0":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/open.png")); break;
            case "1":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/placed.png")); break;
            case "2":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/claimed.png")); break;
            case "3":
                imageview.setImage(new Image("org/rmj/gcardappfx/images/cancelled.png")); break;
            default:
                imageview.setImage(new Image("org/rmj/gcardappfx/images/unknown.png"));      
        }
    }
    
    private void setPlaceOrder(String fsValue){
        switch(fsValue){
            case "1":
                lblLocationStat.setText("On Process"); break;
            case "2":
                lblLocationStat.setText("on Transit"); break;
            case "3":
                lblLocationStat.setText("In Stock"); break;
            default:
                lblLocationStat.setText("Unknown"); 
        }
    }    
}
