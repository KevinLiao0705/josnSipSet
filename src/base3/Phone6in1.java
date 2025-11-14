/*
    Cisco Catalyst 9200CX Compact Series Switches password is "A123456789a"

 */
package base3;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
//import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import org.json.JSONArray;
import org.json.JSONObject;

public class Phone6in1 extends javax.swing.JDialog {

    int sipUiDeviceId = 0x1847;
    int piIoDeviceId = 0x1846;
    int piMcuDeviceId = 0x1845;
    int sipPhoneDeviceId = 0x1947;
    int switchLedMcuDeviceId = 0x1944;
    int switchLed = 0;
    int switchLedF = 0;
    int reDirection_f = 0;
    int cmd_cnt = 0;
    int cmd_para0 = 0;
    int cmd_para1 = 0;
    int flash_f = 0;
    int printEerorFirst_f = 0;

    int set_cnt = 0;
    int set_para0 = 0;
    int set_para1 = 0;
    int phSyssec_f = 0;
    int set_sipLocal_ip_f = 1;
    int set_sipSwithc_ip_f = 1;
    int piIoFlag = 0;

    public int testUart0_f = 0;
    public int testUart1_f = 0;
    public int testUart2_f = 0;
    public String sipVersion = "";

    public int[] hotline_inxA = {0, 1, 2, 3};
    public int carType_inx = 0;
    public int carNo_inx = 0;
    public int debug_view_mod = 0;
    public String sel_reg = "kevin";
    public String sel_regno = "301";
    public String sel_sip_ip = "sip_ip";
    public String sel_pbx_ip = "pbx ip";
    public String sel_switch_ip = "switch ip";
    public String sel_local_ip = "local ip";
    public String[] functionKey = new String[4];

    JTextPane tp1;
    JScrollPane scroll;

    public String realSipPhoneIp = "0.0.0.0";
    public String realSipPhoneName = "----";
    public String realSipPhoneNo = "----";
    public String realSipPhoneNetMask = "255.255.0.0";
    public String realSipPhoneGateWay = "192.168.0.1";
    public String realSwitchIp = "0.0.0.0";
    public String realSipServerIp = "0.0.0.0";
    public String[] carSetArray = new String[7];
    public String carTypeNameNo = "";
    //public String[][] viewPageStrAA = new String[8][];
    public ArrayList<String>[] viewPageStrAA = new ArrayList[8];
    public String viewPageCarType = "";
    public String viewPageCarNo = "";
    int[] debug_intA = new int[256];
    int debug_int_inx = 0;

    String change_switch_ip_str = "";
    int change_switch_ip_step = 0;
    int viewDebugPanel_f = 1;
    int infPanelCnt = 0;
    int preConfiguration = 0;
    int set_wait_tim = 0;
    int set_switch_ip_f = 0;
    int set_local_ip_f = 0;
    int nowLine = 0;
    Color buttonColor;

    int set_switch_ip_tim = 0;
    int set_local_ip_tim = 0;

    int sip_ok_f = 0;
    int pbx_ok_f = 0;

    int saveWait_f = 0;

    int winW = 800;
    int winH = 480;
    int switch_led_flag;
    int debug_f = 0;
    int debug_cnt = 0;
    String debug_str = "";
    int reboot_f = 0;
    int reboot_step = 0;
    static Phone6in1 scla;
    int siptx_byuart_stop_tim;
    int menu_on_f = 0;
    int message_on_f = 0;
    int selfTest_on_f = 0;
    int cmdin_f = 0;
    int cmdin_inx = 0;
    int viewStatus_f = 0;
    String cmdin_str = "";
    String title_str = "title_str";
    int vlen = 16;
    String initv1_str;
    String initv2_str;
    JTextField tfCommand, tfLcd1, tfLcd2;
    Timer tm1 = null;//for display
    Timer tm2 = null;//for open stock window

    int debug_key_f = 0;
    int sipmd_ping_f = 0;
    int sipServer_ping_f = 0;
    int switch_ping_f = 0;
    int easy_password_f = 0;

    int sipmd_ping_cnt = 0;
    int sipServer_ping_cnt = 0;
    int switch_ping_cnt = 0;

    String switchPassw_str = "";
    int switchPassw_inx = 0;

    PingTd pingTd = null;
    int pingTd_run_f = 0;
    int pingTd_destroy_f = 0;

    PingServer pingServer = null;
    int pingServer_run_f = 0;
    int pingServer_destroy_f = 0;

    int s0p3_txnone_tim = 0;
    int readSwitchIpTime = 0;
    int switch_rxed_f = 0;
    int switch_rxed_tim = 0;
    int displayShow_f = 0;
    String switchCommand = "";
    int switchCommandTime = 0;

    int ledflag, keyflag, keypush;
    int ssksip_tx_tim;
    //===============================
    int phoneSta = 0;         //0 no raspberryPi,1:raspberry pi ready,2:linphonec load,3:pbx registed,4:on call,5:ring 

    int lineSta = 0;
    int mute_f = 0;

    int handStatus = 0;    //0:all 0ff 1:earphone 2:epeaker

    int earPhone_volume = 0;
    int speaker_volume = 0;
    int ear_mic_sen = 0;
    int phset_mic_sen = 0;

    int[] switchFlag = new int[20];
    String switchIp = "";

    byte[] sipflag = new byte[4];
    String status_str = "";
    String action_str = "";
    String callto = "";
    String callfrom = "";
    int keypad_tim = 0;
    String keypad_str = "";
    Color clButtonInit;

    Input inp1;
    int password_on_f = 0;
    //==============================
    String sipCommand = "";
    String shellCommand = "";
    byte[] uiCommand = new byte[256];
    int uiCommand_len = 0;

    TrxPack trxPack0;
    Ssocket sskio0;    //from nkv6in1_io
    Ssocket sskip_sip;  //to sipphone get information

    Menu menu1;
    String m_local_ip_str;
    String m_local_ipmask_str;
    String m_local_gateway_str;
    String m_sip_ip_str;
    String m_sip_ipmask_str;
    String m_sip_gateway_str;
    String m_switch_ip_str;
    String m_switch_ipmask_str;
    String m_switch_gateway_str;
    String m_phone_no;
    String m_sip_server_pin;
    String m_sip_server_ip;
    int viewPageInx = 1;
    //int view_page_cnt = 0;
    int view_page_cntb = 0;
    int view_page_cntlm = 1;
    int view_page_cntlmb = 1;

    int viewPageStack = 0;
    int[] viewPageCntA = new int[8];
    int[] viewPageLmA = new int[8];

    int viewInf = 0;

    String[] ctNameA;
    String[] ctNoA;
    int pageSelect;

    Message mes1;
    SelfTest stest1;
    int pnView_on_f = 0;
    static ImageIcon imgSet = new ImageIcon("./img/set.png");
    static ImageIcon imgUp = new ImageIcon("./img/up.png");
    static ImageIcon imgDown = new ImageIcon("./img/down.png");
    static ImageIcon imgLeft = new ImageIcon("./img/left.png");
    static ImageIcon imgRight = new ImageIcon("./img/right.png");
    static ImageIcon imgEnter = new ImageIcon("./img/enter.png");
    static ImageIcon imgAdd = new ImageIcon("./img/add.png");
    static ImageIcon imgSub = new ImageIcon("./img/sub.png");

    static ImageIcon imgHangOn = new ImageIcon("./img/hang_on.png");
    static ImageIcon imgHangOff = new ImageIcon("./img/hang_off.png");
    static ImageIcon imgConvert = new ImageIcon("./img/convert.png");
    static ImageIcon imgMail = new ImageIcon("./img/mail.png");
    static ImageIcon imgMuteSpeaker = new ImageIcon("./img/mute_speaker.png");
    static ImageIcon imgMuteMic = new ImageIcon("./img/mute_mic.png");
    static ImageIcon imgSpeaker = new ImageIcon("./img/speaker.png");
    static ImageIcon imgTel1 = new ImageIcon("./img/tel1.png");
    static ImageIcon imgTel2 = new ImageIcon("./img/tel2.png");
    static ImageIcon imgTel3 = new ImageIcon("./img/tel3.png");
    static ImageIcon imgTel4 = new ImageIcon("./img/tel4.png");
    static ImageIcon imgTel5 = new ImageIcon("./img/tel5.png");
    static ImageIcon imgTel6 = new ImageIcon("./img/tel6.png");
    static ImageIcon imgTel7 = new ImageIcon("./img/tel7.png");
    static ImageIcon imgTel8 = new ImageIcon("./img/tel8.png");

    //===========================
    Container cp;
    JPanel pnMain;

    JLabel lbTitle, lbStatus;
    JPanel pnLeft, pnRight, pnLcd, pnKeypad, pnDebug, pnInf, pnInput, pnView, pnViewButton, pnWarn;

    JButton[] bta1 = new JButton[4];
    JButton[] bta2 = new JButton[2];
    JButton[] bta3 = new JButton[28];
    JButton[] bta4 = new JButton[4];
    JLabel[] lba1 = new JLabel[6];
    JLabel[] lba2 = new JLabel[4];
    JButton[] btaView = new JButton[10];
    JLabel[] lbaView = new JLabel[3];

    ArrayList<Integer> carInfLegel = new ArrayList();

    //static MyLayout ly=new MyLayout();
    public Phone6in1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        Phone6in1.scla = this;
        Phone6in1 cla = this;
        cla.setBounds(-100, -100, 0, 0);
        for (int i = 0; i < viewPageStrAA.length; i++) {
            viewPageStrAA[i] = new ArrayList<String>();
        }
    }

    void reset_network() {
        String sysIpAddr = GB.paraSetMap.get("systemIpAddress").toString();
        String sysNetMask = GB.paraSetMap.get("systemNetMask").toString();
        String sysGateWay = GB.paraSetMap.get("systemGateWay").toString();
        if (GB.prgMode >= 2) {
            String cmdStr;
            cmdStr = "sudo ifconfig eth0 ";
            cmdStr += sysIpAddr;
            cmdStr += " netmask ";
            cmdStr += sysNetMask;
            cmdStr += " broadcast ";
            cmdStr += sysGateWay;
            Lib.exe(cmdStr);
            GB.realIpAddress = sysIpAddr;
            GB.realNetMask = sysNetMask;
            GB.realGateWay = sysGateWay;
        }

        //============================    
    }

    public void transSipPhoneUiData(JSONObject outJson) {

        //public int phoneSta = 0;  //0 no raspberryPi,1:raspberry pi ready,2:linphonec load,3:pbx registed
        //public int[] lineFlagA = new int[]{0, 0};   //hold,mute,dtmf
        //public int[] lineStaA = new int[]{0, 0};     //0:ready, 1: ring out, 2:ring in, 3:connect, 4:hold 
        //public int[] handStaA = new int[]{0, 0};     //0:ready, 1: earphone, 2:epeaker 
        try {
            KvJson kj = new KvJson();
            kj.jStart();
            kj.jadd("realIp", GB.realIpAddress);
            kj.jadd("realMac", GB.macStr);

            int ipMode = (int) GB.paraSetMap.get("ipMode");
            kj.jadd("ipMode", ipMode);

            String carTypeName = GB.paraSetMap.get("nowCarTypeName").toString();
            kj.jadd("carTypeName", carTypeName);
            String carTypeNo = GB.paraSetMap.get("nowCarTypeNo").toString();
            kj.jadd("carTypeNo", carTypeNo);

            kj.jadd("sipName", realSipPhoneName);
            kj.jadd("sipNo", realSipPhoneNo);
            kj.jadd("sipPhoneIp", realSipPhoneIp);
            kj.jadd("sipServerIp", realSipServerIp);
            String setVersion = GB.paraSetMap.get("version").toString();
            kj.jadd("version", GB.version+"-"+setVersion);

            String switchIp = GB.paraSetMap.get("switchIpAddress").toString();
            String ntpIp = GB.paraSetMap.get("ntpServerAddress").toString();

            if (ipMode == 1) {
                String nowCarTypeName = GB.paraSetMap.get("nowCarTypeName").toString();
                String nowCarTypeNo = GB.paraSetMap.get("nowCarTypeNo").toString();
                String carTypeNameNo = checkCarTypeExist(nowCarTypeName, nowCarTypeNo);
                if (carTypeNameNo.length() != 0) {
                    switchIp = carSetArray[5];
                    ntpIp = carSetArray[6];
                }
            }

            kj.jadd("switchIp", switchIp);
            kj.jadd("ntpIp", ntpIp);
            kj.jEnd();
            JSONObject jsonObj = new JSONObject(kj.jstr);
            outJson.put("sipphoneUiData", jsonObj);

        } catch (Exception ex) {
            if (this.printEerorFirst_f == 0) {
                ex.printStackTrace();
            }
            this.printEerorFirst_f = 1;

        }
    }

    static public JSONObject wsCallBack(String userName, JSONObject mesJson, String actStr, JSONObject outJson) {
        try {
            String act = (String) mesJson.get("act");
            if (act.equals("tick")) {
                scla.transSipPhoneUiData(outJson);
                return outJson;
            }

            Object obj = null;
            try {
                obj = mesJson.get("paras");
            } catch (Exception ex) {

            }
            JSONArray paras = null;
            if (obj != null) {
                paras = (JSONArray) obj;
            }
            outJson.put("status", "ok");
            if (act.equals("phoneCommand")) {
                String phoneCommand = paras.get(0).toString();
                SipPhone.scla.phoneCommandIn(phoneCommand);
                return outJson;
            }
            if (act.equals("sipCommandDirect")) {
                String sipCommand = paras.get(0).toString();
                SipPhone.scla.sshWriteSip(sipCommand);
                return outJson;
            }

        } catch (Exception ex) {

        }
        return outJson;

    }

    public void create() {
        int i;
        final Phone6in1 cla = this;
        String str;
        Font myFont;

        GB.loadParaSet();
        cla.transParaSet();
        try {
            Path file = Paths.get(GB.paraSetPath);
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            GB.preParaSetTime = attr.lastModifiedTime().toString();
        } catch (Exception ex) {

        }

        // Create a new blank cursor.
        if (GB.cursorOff_f == 1) {
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
            cla.getContentPane().setCursor(blankCursor);
        }

        cla.setTitle("Phone6in1");
        if (GB.frameOn_f == 0) {
            cla.setUndecorated(true);
        }
        cla.addWindowListener(new Phone6in1WinLis(cla));    //window event listen        
        Phone6in1MsLis mslis = new Phone6in1MsLis(this);
        Phone6in1KeyLis keylis = new Phone6in1KeyLis(this);
        //===============================================
        cp = cla.getContentPane();
        cp.setBackground(Color.black);
        //===============================================
        pnMain = new JPanel();      //base panel
        cp.add(pnMain);
        //===============================================
        pnLeft = new JPanel();         //left panel 
        pnMain.add(pnLeft);
        pnView = new JPanel();         //left panel 
        pnMain.add(pnView);
        pnView.setVisible(false);
        pnView_on_f = 0;
        //===============================================
        pnRight = new JPanel();         //right panel
        pnRight.setBackground(Color.BLACK);
        pnMain.add(pnRight);
        //===============================================
        pnDebug = new JPanel();         //left panel
        pnDebug.setBackground(Color.BLACK);
        pnLeft.add(pnDebug);

        //===============================================
        pnInput = new JPanel();         //left panel
        pnInput.setBackground(Color.BLACK);
        pnLeft.add(pnInput);

        //===============================================
        myFont = new Font("Serif", Font.BOLD, 24);
        //===============================================
        tfCommand = new JTextField();     //input text line
        tfCommand.setText("");
        tfCommand.setName(Integer.toString(4 * 256 + 0));
        tfCommand.setMargin(new Insets(0, 10, 0, 10));
        tfCommand.addMouseListener(mslis);
        tfCommand.addKeyListener(keylis);
        tfCommand.setFont(myFont);
        //tfCommand.setBackground(Color.red);
        pnInput.add(tfCommand);
        //===============================================
        lbTitle = new JLabel();     //title
        lbTitle.setFont(new Font("Serif", Font.BOLD, 36));
        lbTitle.setHorizontalAlignment(JLabel.CENTER);
        pnLeft.add(lbTitle);
        //===============================================
        pnLcd = new JPanel();     //lcd display panel         
        pnLcd.setBackground(Color.BLACK);
        pnLeft.add(pnLcd);
        //===============================================
        pnKeypad = new JPanel();     //keypad panel
        pnKeypad.setBackground(Color.BLACK);
        pnLeft.add(pnKeypad);

        pnInf = new JPanel();     //Inf panel
        pnInf.setBackground(Color.CYAN);
        pnLeft.add(pnInf);

        pnWarn = new JPanel();     //Inf panel
        pnWarn.setBackground(Color.MAGENTA);
        pnLeft.add(pnWarn);

        //===============================================
        tfLcd1 = new JTextField(); //lcd first line
        tfLcd1.setText("");
        tfLcd1.setName(Integer.toString(99 * 256 + 0));
        tfLcd1.setMargin(new Insets(0, 10, 0, 10));
        tfLcd1.setBackground(Color.YELLOW);
        tfLcd1.setFont(new Font("Serif", Font.BOLD, 36));
        tfLcd1.setEditable(false);
        pnLcd.add(tfLcd1);
        //===============================================
        tfLcd2 = new JTextField(); //lcd second line
        tfLcd2.setText("");
        tfLcd2.setName(Integer.toString(99 * 256 + 0));
        tfLcd2.setMargin(new Insets(0, 10, 0, 10));
        tfLcd2.setBackground(Color.YELLOW);
        tfLcd2.setFont(new Font("Serif", Font.BOLD, 36));
        tfLcd2.setEditable(false);
        pnLcd.add(tfLcd2);
        //===============================================
        lbStatus = new JLabel();     //status bar
        lbStatus.setFont(new Font("Serif", Font.BOLD, 36));
        //lbStatus.setHorizontalAlignment(JLabel.CENTER);
        lbStatus.setBackground(Color.CYAN);
        lbStatus.setOpaque(true);
        pnLeft.add(lbStatus);
        //===============================================

        for (i = 0; i < bta1.length; i++) {
            bta1[i] = new JButton();
            bta1[i].setFont(myFont);
            bta1[i].setName(Integer.toString(1 * 256 + i));
            bta1[i].addMouseListener(mslis);
            bta1[i].setVisible(false);
            bta1[i].setFocusable(false);
            bta1[i].setText("");
            pnRight.add(bta1[i]);
        }
        //=======================

        //bta1[3].setIcon(imgSet);
        cla.buttonColor = bta1[0].getBackground();

        for (i = 0; i < bta2.length; i++) {
            bta2[i] = new JButton();
            bta2[i].setFont(myFont);
            bta2[i].setName(Integer.toString(2 * 256 + i));
            bta2[i].addMouseListener(mslis);
            bta2[i].setVisible(true);
            bta2[i].setFocusable(false);
            pnInput.add(bta2[i]);
        }
        //=======================
        bta2[0].setText("Enter");
        bta2[1].setText("ESC");

        //=======================
        for (i = 0; i < bta3.length; i++) {
            bta3[i] = new JButton();
            bta3[i].setFont(myFont);
            bta3[i].setName(Integer.toString(3 * 256 + i));
            bta3[i].addMouseListener(mslis);
            bta3[i].setFocusable(false);
            pnKeypad.add(bta3[i]);
        }

        //=======================
        clButtonInit = bta3[0].getBackground();
        i = 7;
        bta3[0 * i + 0].setText("1");
        bta3[0 * i + 1].setText("2");
        bta3[0 * i + 2].setText("3");
        bta3[0 * i + 3].setText(" ");
        bta3[0 * i + 4].setText(" ");
        bta3[0 * i + 5].setText(" ");
        bta3[0 * i + 6].setText(" ");

        bta3[0 * i + 3].setIcon(imgConvert);
        bta3[0 * i + 4].setIcon(imgUp);
        bta3[0 * i + 5].setIcon(imgMuteMic);
        bta3[0 * i + 6].setIcon(imgTel1);

        bta3[1 * i + 0].setText("4");
        bta3[1 * i + 1].setText("5");
        bta3[1 * i + 2].setText("6");
        bta3[1 * i + 3].setText(" ");
        bta3[1 * i + 4].setText(" ");
        bta3[1 * i + 5].setText(" ");
        bta3[1 * i + 6].setText(" ");

        bta3[1 * i + 3].setIcon(imgLeft);
        bta3[1 * i + 4].setIcon(imgEnter);
        bta3[1 * i + 5].setIcon(imgRight);
        bta3[1 * i + 6].setIcon(imgTel2);

        bta3[2 * i + 0].setText("7");
        bta3[2 * i + 1].setText("8");
        bta3[2 * i + 2].setText("9");
        bta3[2 * i + 3].setText(" ");
        bta3[2 * i + 4].setText(" ");
        bta3[2 * i + 5].setText(" ");
        bta3[2 * i + 6].setText(" ");

        bta3[2 * i + 3].setIcon(imgSub);
        bta3[2 * i + 4].setIcon(imgDown);
        bta3[2 * i + 5].setIcon(imgAdd);
        bta3[2 * i + 6].setIcon(imgTel3);

        bta3[3 * i + 0].setText("*");
        bta3[3 * i + 1].setText("0");
        bta3[3 * i + 2].setText("#");
        bta3[3 * i + 3].setText(" ");
        bta3[3 * i + 4].setText(" ");
        bta3[3 * i + 5].setText(" ");
        bta3[3 * i + 6].setText(" ");

        bta3[3 * i + 3].setIcon(imgHangOn);
        bta3[3 * i + 4].setIcon(imgHangOff);
        bta3[3 * i + 5].setIcon(imgSpeaker);
        bta3[3 * i + 6].setIcon(imgTel4);

        for (i = 0; i < lba1.length; i++) {
            lba1[i] = new JLabel();
            lba1[i].setFont(new Font("Serif", Font.BOLD, 32));
            lba1[i].setName(Integer.toString(5 * 256 + i));
            lba1[i].addMouseListener(mslis);
            lba1[i].setBackground(Color.CYAN);
            lba1[i].setOpaque(true);

            pnInf.add(lba1[i]);
        }
        //=======================
        lba1[0].setText("  使用者");
        lba1[2].setText("  SIP電話IP");
        lba1[4].setText("  ICS IP");

        for (i = 0; i < lba2.length; i++) {
            lba2[i] = new JLabel();
            lba2[i].setFont(new Font("Serif", Font.BOLD, 32));
            lba2[i].setHorizontalAlignment(SwingConstants.CENTER);
            cla.lba2[i].setForeground(Color.BLACK);

            pnWarn.add(lba2[i]);
        }

        lba2[0].setText("");
        lba2[1].setText("網路連線中斷");
        lba2[2].setText("請檢查外部網路連線");
        lba2[3].setText("");

        pnViewButton = new JPanel();         //left panel 
        pnView.add(pnViewButton);

        for (i = 0; i < btaView.length; i++) {
            btaView[i] = new JButton();
            btaView[i].setFont(new Font("Serif", Font.BOLD, 32));
            btaView[i].setName(Integer.toString(6 * 256 + i));
            btaView[i].addMouseListener(mslis);
            btaView[i].setHorizontalAlignment(JButton.LEFT);
            pnViewButton.add(btaView[i]);
        }

        EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
        tp1 = new JTextPane();
        tp1.setBorder(eb);
        tp1.setBackground(Color.BLACK);
        tp1.setFont(myFont);
        //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        tp1.setMargin(new Insets(5, 5, 5, 5));

        scroll = new JScrollPane(tp1);
        pnView.add(scroll); //Object of Jpanel        

        for (i = 0; i < lbaView.length; i++) {
            lbaView[i] = new JLabel();
            lbaView[i].setFont(new Font("Serif", Font.BOLD, 32));
            lbaView[i].addMouseListener(mslis);
            pnView.add(lbaView[i]);
        }
        lbaView[1].setHorizontalAlignment(JLabel.RIGHT);
        lbaView[2].setText("數字鍵選擇");
        //=======================
        cla.setInfPanel();

        //bta3[3 * i + 5].setBackground(Color.yellow);
        myFont = new Font("Serif", Font.BOLD, 24);
        for (i = 0; i < bta4.length; i++) {
            bta4[i] = new JButton();
            bta4[i].setFont(myFont);
            bta4[i].setName(Integer.toString(4 * 256 + i));
            bta4[i].setBackground(Color.LIGHT_GRAY);
            bta4[i].addMouseListener(mslis);
            bta4[i].setFocusable(false);
            pnDebug.add(bta4[i]);
        }
        //=======================
        bta4[0].setText("系統");
        bta4[1].setText("話機");
        bta4[2].setText("連線狀態");
        bta4[3].setText("交換器");
        /*
        bta4[0].setText("EXIO");
        bta4[1].setText("UARTIO");
        bta4[2].setText("USBIO1");
        bta4[3].setText("USBIO2");
        bta4[4].setText("SWITCH");
        bta4[5].setText("KPAD");
        bta4[6].setText("SIPU");
        bta4[7].setText("PHINF");
        bta4[8].setText("PBX");
        bta4[9].setText("OFF");
        bta4[10].setText("CALL");
        bta4[11].setText("KEYPAD");
         */

        //=======================================================
        trxPack0 = new TrxPack(5, 0x10);
        //=======================================================
        sskio0 = new Ssocket();
        sskio0.format = 1;
        sskio0.rxcon_ltim = 100;//unit 10ms
        sskio0.create(1232);
        sskio0.sskRx = new SskRx() {
            @Override
            public void sskRx(int format) {
                cla.sskio0Rx(format);
            }
        };
        sskio0.start();
        //=======================================================
        sskip_sip = new Ssocket(); //for sip
        sskip_sip.format = 1;
        sskip_sip.rxcon_ltim = 100;//unit 10ms
        sskip_sip.create(1336);
        sskip_sip.sskRx = new SskRx() {
            @Override
            public void sskRx(int format) {
                cla.sipRxByIp();
            }
        };
        sskip_sip.start();
        //=====================================

        menu1 = new Menu(null, true);
        menu1.create();
        MenuList menuTmp;
        //=====================================
        m_local_ip_str = "";
        m_local_ipmask_str = "";
        m_local_gateway_str = "";
        m_sip_ip_str = "";
        m_sip_ipmask_str = "";
        m_sip_gateway_str = "";
        m_switch_ip_str = "";
        m_switch_ipmask_str = "";
        m_switch_gateway_str = "";
        m_phone_no = "";
        m_sip_server_pin = "";
        m_sip_server_ip = "";

        //=========================================
        menu1.menuRoot = new MenuList("設定", 1);
        menuTmp = menu1.menuRoot;
        menuTmp.preMenuList = null;

        str = "1. 本機網路設定";
        menuTmp.add(str, 1);
        str = "2. SIP電話網路設定";
        menuTmp.add(str, 1);
        str = "3. SWITCH網路設定";
        menuTmp.add(str, 1);
        str = "4. PBX 設定";
        menuTmp.add(str, 1);
        str = "5. 自測";
        menuTmp.add(str, 0);
        str = "6. 重新開機";
        menuTmp.add(str, 0);
        //str = "6. 返回";
        //menuTmp.add(str, 2);
        //===============================================
        menu1.menuRoot.mdataList.get(0).mlist = new MenuList("本機網路設定", 1);
        menuTmp = menu1.menuRoot.mdataList.get(0).mlist;
        menuTmp.preMenuList = menu1.menuRoot;
        str = "1. IP 設定";
        //menuTmp.add(str, 3, 9, m_local_ip);
        menuTmp.add(str, 3, 8, m_local_ip_str);
        str = "2. Netmask 設定";
        menuTmp.add(str, 3, 8, m_local_ipmask_str);
        str = "3. Gateway 設定";
        menuTmp.add(str, 3, 8, m_local_gateway_str);
        str = "4. 返回";
        menuTmp.add(str, 2);
        //=================================================
        menu1.menuRoot.mdataList.get(1).mlist = new MenuList("SIP電話網路設定", 1);
        menuTmp = menu1.menuRoot.mdataList.get(1).mlist;
        menuTmp.preMenuList = menu1.menuRoot;
        str = "1. IP 設定";
        menuTmp.add(str, 3, 8, m_sip_ip_str);
        str = "2. Netmask 設定";
        menuTmp.add(str, 3, 8, m_sip_ipmask_str);
        str = "3. Gateway 設定";
        menuTmp.add(str, 3, 8, m_sip_gateway_str);
        str = "4. 返回";
        menuTmp.add(str, 2);
        //=================================================
        menu1.menuRoot.mdataList.get(2).mlist = new MenuList("SWITCH網路設定", 1);
        menuTmp = menu1.menuRoot.mdataList.get(2).mlist;
        menuTmp.preMenuList = menu1.menuRoot;
        str = "1. IP 設定";
        menuTmp.add(str, 3, 8, m_switch_ip_str);
        str = "2. Netmask 設定";
        menuTmp.add(str, 3, 8, m_switch_ipmask_str);
        str = "3. Gateway 設定";
        menuTmp.add(str, 3, 8, m_switch_gateway_str);
        str = "4. Switch 外部設定";
        menuTmp.add(str, 0);
        str = "5. 返回";
        menuTmp.add(str, 2);
        //=================================================
        menu1.menuRoot.mdataList.get(3).mlist = new MenuList("PBX 設定", 1);
        menuTmp = menu1.menuRoot.mdataList.get(3).mlist;
        menuTmp.preMenuList = menu1.menuRoot;
        str = "1. 分機號碼 設定";
        menuTmp.add(str, 3, 0, m_phone_no);
        str = "2. 註冊密碼 設定";
        menuTmp.add(str, 3, 0, m_sip_server_pin);
        str = "3. PBX IP 設定";
        menuTmp.add(str, 3, 8, m_sip_server_ip);
        str = "4. 返回";
        menuTmp.add(str, 2);
        //=================================================

        if (cla.tm1 == null) {
            cla.tm1 = new Timer(20, new Phone6in1Tm1(cla));
            cla.tm1.start();
        }

        if (cla.tm2 == null) {      //proceed command
            cla.tm2 = new Timer(20, new Phone6in1Tm2(cla));
            cla.tm2.start();
        }

        if (cla.pingTd == null) {
            cla.pingTd = new PingTd(cla);
            cla.pingTd.start();
            cla.pingTd_run_f = 1;
            cla.pingTd_destroy_f = 0;
        }
        if (cla.pingServer == null) {
            cla.pingServer = new PingServer(cla);
            cla.pingServer.start();
            cla.pingServer_run_f = 1;
            cla.pingServer_destroy_f = 0;
        }

        realSipPhoneIp = GB.paraSetMap.get("sipphoneIpAddress").toString();

    }

    void setInfPanel() {
        if (infPanelCnt == 0) {
            pnInf.setVisible(true);
            pnKeypad.setVisible(false);
            pnWarn.setVisible(false);
        }
        if (infPanelCnt == 1) {
            pnInf.setVisible(false);
            pnKeypad.setVisible(true);
            pnWarn.setVisible(false);
        }
        if (infPanelCnt == 2) {
            pnInf.setVisible(false);
            pnKeypad.setVisible(false);
            pnWarn.setVisible(true);
        }
    }

    int sipIp_rxed_f = 0;
    int sipIp_rxed_tim = 0;

    void sipRxByIp() {
        Phone6in1 cla = this;
        cla.sskip_sip.datain_f = 0;
        cla.sskip_sip.connect_f = 1;
        int okf = sipRxDec(cla.sskip_sip.inbuf, 0, cla.sskip_sip.inbuf_len);
        if (okf == 1) {
            sipIp_rxed_f = 1;
            sipIp_rxed_tim = 0;
        }
    }

    int sipInf_rxed_f = 0;
    int sipInf_rxed_tim = 0;

    int sipRxDecCheck(byte[] bytes_in, int stInx, int sipinf_len) {
        int i, j, k;
        Phone6in1 cla = this;
        String str;
        int inx = stInx;
        int endInx = inx + sipinf_len;
        int cmdinx;
        int cmdlen;
        int cmd;
        byte[] bytes;

        int deviceId = (bytes_in[inx + 0] & 255) + (bytes_in[inx + 1] & 255) * 256;
        int serialId = (bytes_in[inx + 2] & 255) + (bytes_in[inx + 3] & 255) * 256;
        int groupId = (bytes_in[inx + 4] & 255) + (bytes_in[inx + 5] & 255) * 256;
        int packLen = (bytes_in[inx + 6] & 255) + (bytes_in[inx + 7] & 255) * 256;
        int packCmd = (bytes_in[inx + 8] & 255) + (bytes_in[inx + 9] & 255) * 256;
        if (deviceId != sipPhoneDeviceId) {
            return 0;
        }
        if (groupId != 0xab00) {
            return 0;
        }
        if (packCmd != 0x1000) {
            return 0;
        }

        inx += 10;
        while (inx < endInx - 3) {
            cmd = bytes_in[inx] & 255;
            cmdlen = bytes_in[inx + 1];
            cmdinx = inx + 2;
            switch (cmd) {
                case 0x00://for other side io use;
                    break;
                case 0x01://for this side io use;
                    break;
                case 0x10://status flag
                    break;
                case 0x11://status_str
                    break;
                case 0x12://action_str
                    break;
                case 0x13://callto
                    break;
                case 0x14://callfrom
                    break;
                case 0x15://sip_phone_address
                    String sip_ip;
                    String sip_ipmask;
                    String sip_gateway;
                    String sipLocal_ip;
                    String sipSwitch_ip;
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    sip_ip = "" + (bytes[0] & 255);
                    sip_ip += "." + (bytes[1] & 255);
                    sip_ip += "." + (bytes[2] & 255);
                    sip_ip += "." + (bytes[3] & 255);
                    sip_ipmask = "" + (bytes[4] & 255);
                    sip_ipmask += "." + (bytes[5] & 255);
                    sip_ipmask += "." + (bytes[6] & 255);
                    sip_ipmask += "." + (bytes[7] & 255);
                    sip_gateway = "" + (bytes[8] & 255);
                    sip_gateway += "." + (bytes[9] & 255);
                    sip_gateway += "." + (bytes[10] & 255);
                    sip_gateway += "." + (bytes[11] & 255);
                    realSipPhoneIp = sip_ip;
                    realSipPhoneNetMask = sip_ipmask;
                    realSipPhoneGateWay = sip_gateway;
                    break;
                default:
                    cmdlen = 9999;
                    break;

            }
            inx = inx + cmdlen + 2;
        }
        return 1;
    }

    int sipRxDec(byte[] bytes_in, int stInx, int sipinf_len) {
        int i, j, k;
        Phone6in1 cla = this;
        String str;
        int inx = stInx;
        int endInx = inx + sipinf_len;
        int cmdinx;
        int cmdlen;
        int cmd;
        byte[] bytes;
        int deviceId = (bytes_in[inx + 0] & 255) + (bytes_in[inx + 1] & 255) * 256;
        int serialId = (bytes_in[inx + 2] & 255) + (bytes_in[inx + 3] & 255) * 256;
        int groupId = (bytes_in[inx + 4] & 255) + (bytes_in[inx + 5] & 255) * 256;
        int packLen = (bytes_in[inx + 6] & 255) + (bytes_in[inx + 7] & 255) * 256;
        int packCmd = (bytes_in[inx + 8] & 255) + (bytes_in[inx + 9] & 255) * 256;
        if (deviceId != sipPhoneDeviceId) {
            return 0;
        }
        if (groupId != 0xab00) {
            return 0;
        }
        if (packCmd != 0x1000) {
            return 0;
        }
        inx += 10;
        while (inx < endInx - 3) {
            cmd = bytes_in[inx] & 255;
            cmdlen = bytes_in[inx + 1];
            cmdinx = inx + 2;
            if (debug_int_inx < 256) {
                debug_intA[debug_int_inx++] = cmd;
            } else {
                debug_int_inx = 300;

            }
            switch (cmd) {
                case 0x00://for other side io use;
                    break;
                case 0x01://for this side io use;
                    break;

                case 0x10://status flag
                    sipInf_rxed_f = 1;
                    sipInf_rxed_tim = 0;
                    cla.phoneSta = bytes_in[cmdinx++];
                    cla.lineSta = bytes_in[cmdinx++];
                    int handSta = bytes_in[cmdinx++];
                    cla.earPhone_volume = bytes_in[cmdinx++];
                    cla.speaker_volume = bytes_in[cmdinx++];
                    cla.ear_mic_sen = bytes_in[cmdinx++];
                    cla.phset_mic_sen = bytes_in[cmdinx++];
                    cla.sipflag[0] = bytes_in[cmdinx++];//
                    cla.sipflag[1] = bytes_in[cmdinx++];
                    cla.sipflag[2] = bytes_in[cmdinx++];
                    if ((cla.sipflag[0] & 0x01) != 0) {
                        mute_f = 1;
                    } else {
                        mute_f = 0;
                    }

                    if ((cla.sipflag[0] & 0x80) != 0) {
                        reDirection_f = 1;
                    } else {
                        reDirection_f = 0;
                    }

                    if ((cla.sipflag[0] & 0x04) != 0) {
                        nowLine = 1;
                    } else {
                        nowLine = 0;
                    }
                    if (nowLine == 1) {
                        cla.handStatus = (handSta >> 4) & 3;
                    } else {
                        cla.handStatus = handSta & 3;
                    }

                    if ((cla.sipflag[0] & 0x02) != 0) {
                        cla.phSyssec_f = 1;
                    } else {
                        cla.phSyssec_f = 0;
                    }
                    break;
                case 0x11://status_str
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    cla.status_str = new String(bytes, Charset.forName("UTF-8"));
                    break;
                case 0x12://action_str
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    cla.action_str = new String(bytes, Charset.forName("UTF-8"));
                    break;
                case 0x13://callto
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    cla.callto = new String(bytes, Charset.forName("UTF-8"));
                    break;
                case 0x14://callfrom
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    cla.callfrom = new String(bytes, Charset.forName("UTF-8"));
                    break;
                case 0x15://sip_phone_address
                    String sip_ip;
                    String sip_ipmask;
                    String sip_gateway;
                    String sipLocal_ip;
                    String sipSwitch_ip;

                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    sip_ip = "" + (bytes[0] & 255);
                    sip_ip += "." + (bytes[1] & 255);
                    sip_ip += "." + (bytes[2] & 255);
                    sip_ip += "." + (bytes[3] & 255);
                    sip_ipmask = "" + (bytes[4] & 255);
                    sip_ipmask += "." + (bytes[5] & 255);
                    sip_ipmask += "." + (bytes[6] & 255);
                    sip_ipmask += "." + (bytes[7] & 255);
                    sip_gateway = "" + (bytes[8] & 255);
                    sip_gateway += "." + (bytes[9] & 255);
                    sip_gateway += "." + (bytes[10] & 255);
                    sip_gateway += "." + (bytes[11] & 255);

                    //realSipPhoneIp = sip_ip;
                    //realSipPhoneNetMask = sip_ipmask;
                    //realSipPhoneGateWay = sip_gateway;
                    break;

                case 0x16://sip_phone_name
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    realSipPhoneName = new String(bytes, Charset.forName("UTF-8"));
                    break;
                case 0x17://sip_phone_no
                    String phoneNo = "";
                    for (i = 0; i < cmdlen; i++) {
                        phoneNo += (char) bytes_in[cmdinx++];
                    }
                    this.realSipPhoneNo = phoneNo;
                    break;
                case 0x18://pbx address
                    String pbxAddress = "";
                    for (i = 0; i < cmdlen; i++) {
                        pbxAddress += (char) bytes_in[cmdinx++];
                    }
                    cla.realSipServerIp = pbxAddress;
                    break;
                case 0x20://hotline1_name
                case 0x21://hotline2_name
                case 0x22://hotline3_name
                case 0x23://hotline4_name
                case 0x24://hotline1_name
                case 0x25://hotline2_name
                case 0x26://hotline3_name
                case 0x27://hotline4_name
                    break;

                case 0x30://hotline1_no
                case 0x31://hotline2_no
                case 0x32://hotline3_no
                case 0x33://hotline4_no
                case 0x34://hotline1_no
                case 0x35://hotline2_no
                case 0x36://hotline3_no
                case 0x37://hotline4_no
                    break;
                case 0x40://version
                case 0x41://web_password
                    String strTemp = "";
                    for (i = 0; i < cmdlen; i++) {
                        strTemp += (char) bytes_in[cmdinx++];
                    }
                    switch (cmd) {
                        case 0x40:
                            sipVersion = strTemp;
                            break;
                        case 0x41:
                            //GB.web_password = strTemp;
                            break;
                    }
                    break;
                case 0x42://
                    break;
                case 0x43://set local ip
                    /*
                    set_local_ip_f = 0;
                    if (set_local_ip_tim > (50 * 3)) {
                        String ipStr = "";
                        ipStr += "" + (bytes_in[cmdinx++] & 255);
                        ipStr += "." + (bytes_in[cmdinx++] & 255);
                        ipStr += "." + (bytes_in[cmdinx++] & 255);
                        ipStr += "." + (bytes_in[cmdinx++] & 255);
                        GB.sipui_ip_str = ipStr;
                        Lib.wrInterfaces(GB.sipui_ip_str, GB.sipui_ipmask_str, GB.sipui_gateway_str);
                        reset_network();
                    }
                    set_local_ip_tim = 0;
                     */
                    break;
                case 0x44://set switch ip
                    break;
                case 0x45:
                    break;
                case 0x50:
                    break;
                case 0x60:
                    break;
                case 0xa0:
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    cla.cmd_cnt = bytes[0] & 255;
                    cla.cmd_para0 = bytes[1] & 255;
                    cla.cmd_para1 = bytes[2] & 255;
                    break;
                case 0xa1:
                    break;
                case 0xa2:
                    break;
                case 0xa3:
                    break;
                case 0xa4:
                    break;
                case 0xa5:
                    break;
                case 0xa6:
                    break;

                default:

                    cmdlen = 9999;
                    break;

            }
            inx = inx + cmdlen + 2;
        }
        return 1;
    }

    void decS0U2(MyStm stm, int len) {
        Phone6in1 cla = this;
        int i, j;
        String str;
//        if (cla.reboot_f == 1) {
//            return;
//        }
        if (len > 0) {
            //System.out.println("" + cla.sskio0.inbuf_len);
            stm.tmpAddPt = 0;
            int deviceId = stm.readTmpWord();

            byte[] bytes = new byte[len];
            for (i = 0; i < len; i++) {
                bytes[i] = (byte) stm.readTmpByte();
            }
            str = new String(bytes);
            System.out.println(str);

            String[] strA;
            String[] strB;
            int du_f;
            strA = str.split("\n");
            for (j = 0; j < strA.length; j++) {
                strB = strA[j].split("\\s+");
                if (strB.length != 6) {
                    continue;
                }
                du_f = 2;
                if (strB[5].equals("up")) {
                    du_f = 1;
                }
                if (strB[5].equals("down")) {
                    du_f = 0;
                }
                if (du_f == 2) {
                    continue;
                }
                if (strB[0].equals("Vlan1")) {
                    realSwitchIp = strB[1];
                    switch_rxed_f = 1;
                    switch_rxed_tim = 0;

                }
            }

            s0p3_txnone_tim = 0;
            while (true) {

                if (cla.reboot_f == 1) {
                    return;
                }
                if (str.contains("Would you like to enter the initial configuration dialog? [yes/no]:")) {
                    txpackStr(trxPack0, 3, "yes\r\n");
                    switchCommand = "";
                    s0p3_txnone_tim = 0;
                    return;
                    
                    
                }
                if (str.contains("Switch>")) {
                    if (cla.change_switch_ip_str.equals("")) {
                        switchCommand = "readStatus";
                        switchCommandTime = 100;
                    } else {
                        switchCommand = "enable";
                        switchCommandTime = 100;
                    }
                    break;
                }
                if (str.contains("Password:")) {
                    switchCommand = "enterPassword";
                    switchCommandTime = 100;
                    break;
                }
                if (str.contains("Switch#")) {
                    if (cla.change_switch_ip_str.equals("")) {
                        switchCommand = "readStatus";
                        switchCommandTime = 100;
                    } else {
                        switchCommand = "setIp";
                        switchCommandTime = 100;
                        cla.change_switch_ip_step = 0;
                    }
                    break;
                }
                if (str.contains("Switch(config)#")) {
                    if (cla.change_switch_ip_str.equals("")) {
                        switchCommand = "end";
                        switchCommandTime = 100;
                    }
                }

                break;

            }

        } else {
            if (cla.reboot_f == 1) {
                return;
            }
            if (!switchCommand.equals("")) {
                if (switchCommandTime > 0) {
                    switchCommandTime--;
                    if (switchCommandTime == 0) {
                        if (switchCommand.equals("end")) {
                            txpackStr(trxPack0, 3, "end\r\n");
                            switchCommand = "";
                            s0p3_txnone_tim = 0;
                            return;
                        }
                        if (switchCommand.equals("readStatus")) {
                            txpackStr(trxPack0, 3, "show ip interface brief\r\n");
                            switchCommand = "";
                            s0p3_txnone_tim = 0;
                            return;
                        }
                        if (switchCommand.equals("enable")) {
                            txpackStr(trxPack0, 3, "enable\r\n");
                            switchCommand = "";
                            s0p3_txnone_tim = 0;
                            return;
                        }

                        if (switchCommand.equals("enterPassword")) {
                            txpackStr(trxPack0, 3, "A123456789a\r\n");
                            switchCommand = "";
                            s0p3_txnone_tim = 0;
                            return;
                        }
                        if (switchCommand.equals("setIp")) {
                            if (cla.change_switch_ip_step == 0) {
                                txpackStr(trxPack0, 3, "configure terminal\r\n");
                                switchCommandTime = 200;
                                cla.change_switch_ip_step++;
                                return;
                            }
                            if (cla.change_switch_ip_step == 1) {
                                txpackStr(trxPack0, 3, "interface vlan1\r\n");
                                switchCommandTime = 200;
                                cla.change_switch_ip_step++;
                                return;
                            }
                            if (cla.change_switch_ip_step == 2) {
                                txpackStr(trxPack0, 3, "ip address " + cla.change_switch_ip_str + " 255.255.0.0\r\n");
                                switchCommandTime = 200;
                                cla.change_switch_ip_step++;
                                return;
                            }
                            if (cla.change_switch_ip_step == 3) {
                                txpackStr(trxPack0, 3, "no shutdown\r\n");
                                switchCommandTime = 200;
                                cla.change_switch_ip_step++;
                                return;
                            }
                            if (cla.change_switch_ip_step == 4) {
                                txpackStr(trxPack0, 3, "end\r\n");
                                switchCommandTime = 200;
                                cla.change_switch_ip_step++;
                                cla.change_switch_ip_str = "";
                                switchCommand = "";
                                s0p3_txnone_tim = 0;
                                return;
                            }
                        }

                    }
                }
            } else {
                if (++s0p3_txnone_tim > 100) {
                    s0p3_txnone_tim = 0;
                    if (cla.change_switch_ip_str.equals("")) {
                        txpackStr(trxPack0, 3, "\r\n");
                        /*
                        byte[] bytes=new byte[10];
                        bytes[0]=(byte)0x0a;
                        bytes[1]=(byte)0x0d;
                        bytes[2]=(byte)0x0a;
                        bytes[3]=(byte)0x0d;
                        bytes[4]=(byte)0x0a;
                        bytes[5]=(byte)0x0d;
                        bytes[6]=(byte)0x0a;
                        bytes[7]=(byte)0x0d;
                        bytes[8]=(byte)0x0a;
                        bytes[9]=(byte)0x0d;
                        txpackBytes(trxPack0, 3, bytes,10);
                         */

                    }
                }
            }
            debug_cnt = s0p3_txnone_tim;
        }
    }

    public void loadTxPiUart1(TrxPack tpk, int packInx) {    //mcu
        Phone6in1 cla = this;
        int i;
        switch_led_flag = 0;//00:dark, 01:red, 10:green 11:green flash
        if ((switchLed & 0x01) != 0) {
            switch_led_flag |= 0x0002;
        }
        if ((switchLed & 0x02) != 0) {
            switch_led_flag |= 0x0008;
        }
        if ((switchLed & 0x04) != 0) {
            switch_led_flag |= 0x0020;
        }
        if ((switchLed & 0x08) != 0) {
            switch_led_flag |= 0x0080;
        }
        if ((switchLed & 0x10) != 0) {
            switch_led_flag |= 0x0200;
        }
        if ((switchLed & 0x20) != 0) {
            switch_led_flag |= 0x0800;
        }

        if (cla.sipUart_rxed_f != 0 || cla.sipInf_rxed_f != 0) {
            switch_led_flag |= 0x8000;
        }
        switch_led_flag |= 0x2000;


        /*
        for (i = 0; i < 8; i++) {
            switch_led_flag = switch_led_flag << 2;
            if (cla.switchFlag[7 - i] == 1) {
                switch_led_flag += 1;
            } else {
                switch_led_flag += 0;
            }
        }
        if (cla.reboot_f == 1) {
            switch_led_flag = 0x0000;
            cla.switchFlag[0] = 0;
            cla.switchFlag[1] = 0;
            cla.switchFlag[2] = 0;
            cla.switchFlag[3] = 0;
            cla.switchFlag[4] = 0;
            cla.switchFlag[5] = 0;
            cla.switchFlag[6] = 0;
            cla.switchFlag[7] = 0;
        }
         */
        tpk.nowPack = packInx;
        tpk.loadStart();
        tpk.loadWord(cla.piMcuDeviceId);
        tpk.loadWord(0xffff);
        tpk.loadWord(0xab00);
        tpk.loadWord(10);
        tpk.loadWord(0x1000);//par0
        //switch_led_flag=0x0000;
        //switch_led_flag bit0-15 =gygygygygygygygy 
        int ibuf = phoneSta + (handStatus << 4) + (lineSta << 8);
        tpk.loadWord(switch_led_flag);
        tpk.loadWord(ibuf);
        ibuf = cla.sipflag[0];
        tpk.loadWord(ibuf);
        tpk.loadWord(0x0000);
        tpk.txLen[packInx] = tpk.txDataPt;
    }

    public void loadTxPiUart2(TrxPack tpk, int packInx) {    //switch
        Phone6in1 cla = this;
        tpk.nowPack = packInx;
        tpk.loadStart();
        tpk.loadWord(0x0000);
        tpk.loadWord(0xffff);
        tpk.loadWord(0xab00);
        tpk.loadWord(10);
        tpk.loadWord(0x1000);//par0
        int ibuf = cla.handStatus;
        tpk.loadWord(ibuf);
        tpk.loadWord(0);//par1
        tpk.loadWord(0);//par2
        tpk.loadWord(0);//par3
        tpk.txLen[packInx] = tpk.txDataPt;
    }

    int sipUart_rxed_f = 0;
    int sipUart_rxed_tim = 0;
    int sipUartSock_rxed_f = 0;
    int sipUartSock_rxed_tim = 0;

    int keypad_rxed_f = 0;
    int keypad_rxed_tim = 0;

    void decS0U1(MyStm stm, int len) {
        Phone6in1 cla = this;
        String str;
        if (len == 0) {
            return;
        }
        stm.tmpAddPt = 0;
        int deviceId = stm.readTmpWord();
        if (deviceId != cla.piMcuDeviceId) {
            return;
        }
        int serialId = stm.readTmpWord();
        int groupId = stm.readTmpWord();
        if (groupId != 0xab00) {
            return;
        }
        int groupLen = stm.readTmpWord();
        int cmd = stm.readTmpWord();
        int para0 = stm.readTmpWord();
        int para1 = stm.readTmpWord();
        int para2 = stm.readTmpWord();
        int para3 = stm.readTmpWord();
        if (cmd == 0x1000) {
            keyflag = para0;
            ledflag = para1;
            dec_keypad();
            keypad_rxed_f = 1;
            keypad_rxed_tim = 0;
            sock0p2_rxed_f = 1;
            sock0p2_rxed_tim = 0;

        }
    }

    void decS0U3(MyStm stm, int len) {
        Phone6in1 cla = this;
        String str;
        if (len == 0) {
            return;
        }
        stm.tmpAddPt = 0;
        int deviceId = stm.readTmpWord();
        if (deviceId != cla.switchLedMcuDeviceId) {
            return;
        }
        int serialId = stm.readTmpWord();
        int groupId = stm.readTmpWord();
        if (groupId != 0xab00) {
            return;
        }

        int groupLen = stm.readTmpWord();
        int cmd = stm.readTmpWord();
        int para0 = stm.readTmpWord();
        int para1 = stm.readTmpWord();
        int para2 = stm.readTmpWord();
        int para3 = stm.readTmpWord();
        if (cmd == 0x1000) {
            switchLed = para0;
            switchLedF = para1;
            sock0p4_rxed_f = 1;
            sock0p4_rxed_tim = 0;

        }
    }

    void decS0U0(MyStm stm, int len) {
        if (len == 0) {
            return;
        }
        sock0p1_rxed_f = 1;
        sock0p1_rxed_tim = 0;
        int okf = sipRxDecCheck(stm.rdata, stm.tmpBasePt, len);
        if (okf == 1) {
            sipUartSock_rxed_f = 1;
            sipUartSock_rxed_tim = 0;
        }

        if (sipIp_rxed_f != 1) {
            okf = sipRxDec(stm.rdata, stm.tmpBasePt, len);
            if (okf == 1) {
                siptx_byuart_stop_tim = 0;
                sipUart_rxed_f = 1;
                sipUart_rxed_tim = 0;

            }
        }
    }

    int sock0_rxed_f = 0;
    int sock0_rxed_tim = 0;
    int sock0p0_rxed_f = 0;
    int sock0p0_rxed_tim = 0;
    int sock0p1_rxed_f = 0;
    int sock0p1_rxed_tim = 0;
    int sock0p2_rxed_f = 0;
    int sock0p2_rxed_tim = 0;
    int sock0p3_rxed_f = 0;
    int sock0p3_rxed_tim = 0;
    int sock0p4_rxed_f = 0;
    int sock0p4_rxed_tim = 0;

    void sskio0Rx(int format) {
        Phone6in1 cla = this;
        String str;
        sock0_rxed_f = 1;
        sock0_rxed_tim = 0;
        cla.sskio0.datain_f = 0;
        cla.sskio0.connect_f = 1;

        MyStm stm = cla.sskio0.stm;
        int rxLen = stm.rxlen;
        stm.setRdataPt(0);
        int deviceId = stm.readWord();
        int serialId = stm.readWord();
        if (deviceId != cla.piIoDeviceId) {
            return;
        }
        int packId = stm.readWord();
        if (packId != 0xa9ff) {
            return;
        }
        int packLen = stm.readWord();
        if (packLen > 4000) {
            return;
        }
        stm.setRdataNextPt(stm.rdataPt);
        for (;;) {
            stm.rdataPt = stm.rdataNextPt;
            if ((stm.rdataPt) + 4 > rxLen) {
                break;
            }
            if (stm.rdataPt > 4000) {
                break;
            }
            packId = stm.readWord();
            packLen = stm.readWord();
            if (packId == 0xa910) {//piIo
                stm.setRdataNextPt(stm.rdataPt + packLen);
                sock0p0_rxed_f = 1;
                sock0p0_rxed_tim = 0;
                deviceId = stm.readWord();
                serialId = stm.readWord();
                int groupId = stm.readWord();
                if (deviceId != cla.piIoDeviceId || groupId != 0xab00) {
                    continue;
                }
                int groupLen = stm.readWord();
                int packCmd = stm.readWord();
                if (packCmd == 0x1000) {
                }
                continue;
            }
            if (packId == 0xa911) {//sip
                stm.setRdataNextPt(stm.rdataPt + packLen);
                stm.tmpBasePt = stm.rdataPt;
                decS0U0(stm, packLen);
                continue;
            }
            if (packId == 0xa912) {//keyPad
                stm.setRdataNextPt(stm.rdataPt + packLen);
                stm.tmpBasePt = stm.rdataPt;
                decS0U1(stm, packLen);
                continue;
            }
            if (packId == 0xa913) { //switc
                stm.setRdataNextPt(stm.rdataPt + packLen);
                sock0p3_rxed_f = 1;
                sock0p3_rxed_tim = 0;
                stm.tmpBasePt = stm.rdataPt;
                decS0U2(stm, packLen);
                continue;
            }
            if (packId == 0xa914) { //switch led
                stm.setRdataNextPt(stm.rdataPt + packLen);
                sock0p4_rxed_f = 1;
                sock0p4_rxed_tim = 0;
                stm.tmpBasePt = stm.rdataPt;
                decS0U3(stm, packLen);
                continue;
            }
            break;
        }
        loadTxPiIo(cla.trxPack0, 0);
        if (cla.sipIp_rxed_f != 1) {
            trxPack0.txLen[1] = loadTxSipMdData(trxPack0.txData[1], 0);
        }
        loadTxPiUart1(cla.trxPack0, 2);//keypad
        loadSockTx(cla.trxPack0, cla.sskio0);
        cla.sskio0.txret();
    }

    public void loadTxPiIo(TrxPack tpk, int packInx) {
        Phone6in1 cla = this;
        tpk.nowPack = packInx;
        tpk.loadStart();
        tpk.loadWord(cla.piIoDeviceId);
        tpk.loadWord(0xffff);
        tpk.loadWord(0xab00);
        tpk.loadWord(10);
        tpk.loadWord(0x1000);//cmd
        tpk.loadWord(cla.piIoFlag);//par0
        tpk.loadWord(0x0000);//par1
        tpk.loadWord(0x0000);//par2
        tpk.loadWord(0x0000);//par3
        tpk.txLen[packInx] = tpk.txDataPt;
    }

    public void loadSockTx(TrxPack tpk, Ssocket ssk) {
        Phone6in1 cla = this;
        int blen = 0;
        for (int i = 0; i < tpk.amt; i++) {
            blen += tpk.txLen[i];
        }
        blen += tpk.amt * 4;
        int inx = 0;
        byte[] bts = ssk.stm.tbuf;
        bts[inx++] = (byte) (cla.piIoDeviceId & 255);
        bts[inx++] = (byte) ((cla.piIoDeviceId >> 8) & 255);
        bts[inx++] = (byte) (0xff);
        bts[inx++] = (byte) (0xff);
        bts[inx++] = (byte) (0xff);
        bts[inx++] = (byte) (0xa9);
        bts[inx++] = (byte) (blen & 255);
        bts[inx++] = (byte) ((blen >> 8) & 255);
        for (int i = 0; i < tpk.amt; i++) {
            int dlen = tpk.txLen[i];
            int testi = 0;
            if (i == 3 && dlen != 0) {
                testi = 1;;
            }
            if (dlen != 0) {
                bts[inx++] = (byte) (tpk.idBase + i);
                bts[inx++] = (byte) (0xa9);
                bts[inx++] = (byte) (dlen & 255);
                bts[inx++] = (byte) ((dlen >> 8) & 255);
                for (int j = 0; j < dlen; j++) {
                    bts[inx++] = tpk.txData[i][j];
                }
                tpk.txLen[i] = 0;
            }
        }
        ssk.stm.tbuf_byte = inx;
        ssk.stm.enc_mystm();
    }

    void txpackStr(TrxPack trxp, int inx, String str) {
        if (inx >= trxp.amt) {
            return;
        }
        byte[] bytes;
        int i;
        int stx_index = 0;
        int strLen = str.length();
        for (i = 0; i < strLen; i++) {
            trxp.txData[inx][stx_index++] = (byte) str.charAt(i);
        }
        trxp.txLen[inx] = stx_index;
        System.out.println("txSwitchTick");
    }

    void txpackBytes(TrxPack trxp, int inx, byte[] data, int len) {
        int i;
        if (inx >= trxp.amt) {
            return;
        }
        int stx_index = 0;
        for (i = 0; i < len; i++) {
            trxp.txData[inx][stx_index++] = data[i];
        }
        trxp.txLen[inx] = stx_index;
    }

    void txret_switch(Ssocket ssk, String str) {
        Phone6in1 cla = this;
        byte[] bytes;
        int i;
        int stx_index = 0;
        int strLen = str.length();
        for (i = 0; i < strLen; i++) {
            ssk.stm.tbuf[stx_index++] = (byte) str.charAt(i);
        }
        ssk.stm.tbuf_byte = stx_index;
        ssk.stm.enc_mystm();
        try {
            for (i = 0; i < ssk.stm.txlen; i++) {
                ssk.outstr.write(ssk.stm.tdata[i]);
            }
        } catch (IOException ex) {
        }
    }

    void txret_sock(Ssocket ssk, TrxPack trxp) {
        Phone6in1 cla = this;
        byte[] bytes;
        int i, j;
        int stx_index = 0;
        byte chks0;
        byte chks1;
        int allLen = 0;
        for (i = 0; i < trxp.amt; i++) {
            allLen += trxp.txLen[i];
        }
        if (allLen > trxp.lenLim) {
            ssk.stm.tbuf[stx_index++] = (byte) 0xf0;
            ssk.stm.tbuf[stx_index++] = (byte) 0x00;
            ssk.stm.tbuf[stx_index++] = (byte) 0x02;
            ssk.stm.tbuf[stx_index++] = (byte) 0x00;
            chks0 = (byte) 0xab;
            chks1 = (byte) 0x00;
            ssk.stm.tbuf[stx_index++] = (byte) 0x00; //mcmd   
            chks0 ^= (byte) 0x00;
            chks1 += (byte) 0x00;
            ssk.stm.tbuf[stx_index++] = (byte) 0x0f; //scmd len override  
            chks0 ^= (byte) 0x0f;
            chks1 += (byte) 0x0f;
            ssk.stm.tbuf[stx_index++] = chks0;
            ssk.stm.tbuf[stx_index++] = chks1;
            ssk.stm.tbuf[stx_index++] = chks0;
            ssk.stm.tbuf[stx_index++] = chks1;
        } else {
            ssk.stm.tbuf[stx_index++] = (byte) 0xf0;
            for (i = 0; i < trxp.amt; i++) {
                ssk.stm.tbuf[stx_index++] = (byte) (trxp.idBase + i);
                ssk.stm.tbuf[stx_index++] = (byte) (trxp.txLen[i] & 255);
                ssk.stm.tbuf[stx_index++] = (byte) (trxp.txLen[i] >> 8);
                chks0 = (byte) 0xab;
                chks1 = (byte) 0x00;
                for (j = 0; j < trxp.txLen[i]; j++) {
                    ssk.stm.tbuf[stx_index++] = trxp.txData[i][j];
                    chks0 ^= trxp.txData[i][j];
                    chks1 += trxp.txData[i][j];
                }
                ssk.stm.tbuf[stx_index++] = chks0;
                ssk.stm.tbuf[stx_index++] = chks1;

            }
        }
        ssk.stm.tbuf_byte = stx_index;
        ssk.stm.enc_myPack();
        for (i = 0; i < trxp.amt; i++) {
            trxp.txLen[i] = 0;
        }
        try {
            for (i = 0; i < ssk.stm.txlen; i++) {
                ssk.outstr.write(ssk.stm.tdata[i]);
            }
        } catch (IOException ex) {
        }
    }

    void txret_keypad_inf(Ssocket ssk) {
        Phone6in1 cla = this;
        byte[] bytes;
        int i;
        int stx_index = 0;
        switch_led_flag = 0xffff;
        ssk.stm.tbuf[stx_index++] = (byte) GB.sipUiDeviceId;
        ssk.stm.tbuf[stx_index++] = (byte) 0x18;//function id
        ssk.stm.tbuf[stx_index++] = 2;
        ssk.stm.tbuf[stx_index++] = (byte) ((switch_led_flag >> 0) & 255);
        ssk.stm.tbuf[stx_index++] = (byte) ((switch_led_flag >> 8) & 255);
        ssk.stm.tbuf_byte = stx_index;
        ssk.stm.enc_mystm();
        try {
            for (i = 0; i < ssk.stm.txlen; i++) {
                ssk.outstr.write(ssk.stm.tdata[i]);
            }
        } catch (IOException ex) {
        }
    }

    //=====================================================
    char[] sysPassword = new char[20];

    void chkSysPassword(String str) {
        int i;
        int len = str.length();
        for (i = 0; i < len; i++) {
            chkSysPassword(str.charAt(i));
        }
    }

    void chkSysPassword(char ch) {
        Phone6in1 cla = this;
        int i;
        for (i = 8; i >= 0; i--) {
            sysPassword[i + 1] = sysPassword[i];
        }
        sysPassword[0] = ch;
        String str = "";
        for (i = 5; i >= 0; i--) {
            str += sysPassword[i];
        }
        if (str.equals("1234ok")) {
            /*
            viewDebugPanel_f ^= 1;
            if (viewDebugPanel_f == 1) {
                pnDebug.setVisible(true);
                pnInput.setVisible(false);
            } else {
                pnDebug.setVisible(false);
                pnInput.setVisible(true);
            }
            keypad_str = "";
             */
        }

        if (str.equals("****ok")) {
            /*
            keypad_str = "";
            cla.dispose();
             */
        }

    }
    //=====================================================

    void dec_keypad() {
        Phone6in1 cla = this;
        String str;
        String keyStr = "";
        /*        
        if (cla.menu_on_f == 0 && cla.message_on_f == 1) {
            cla.mes1.cmdin_str = cmdstr;
            cla.mes1.cmdin_f = 2;
            return;
        }
        if (cla.menu_on_f == 1 && cla.menu1.input_on_f == 0) {

            cla.menu1.cmdin_str = cmdstr;
            cla.menu1.cmdin_f = 2;
            return;
        }
        if (cla.menu_on_f == 1 && cla.menu1.input_on_f == 1) {
            cla.menu1.inp1.cmdin_str = cmdstr;
            cla.menu1.inp1.cmdin_f = 2;
            return;
        }
         */

        if ((keyflag & 255) != 0xff) {
            if (keypush == 0) {
                keypush = 1;

                switch (keyflag & 255) {
                    case 1://1
                        keyStr = "1";
                        break;
                    case 2://2
                        keyStr = "2";
                        break;
                    case 3://3
                        keyStr = "3";
                        break;
                    case 5://4
                        keyStr = "4";
                        break;
                    case 6://5
                        keyStr = "5";
                        break;
                    case 7://6
                        keyStr = "6";
                        break;
                    case 9://7
                        keyStr = "7";
                        break;
                    case 10://8
                        keyStr = "8";
                        break;
                    case 11://9
                        keyStr = "9";
                        break;
                    case 13://*
                        keyStr = "*";
                        break;
                    case 14://0
                        keyStr = "0";
                        break;
                    case 15://#
                        keyStr = "#";
                        break;
                    case 0://f1
                        //actCommand("f1");
                        keyStr = "f1";
                        break;
                    case 4://f2
                        //actCommand("f2");
                        keyStr = "f2";
                        break;
                    case 8://f3
                        //actCommand("f3");
                        keyStr = "f3";
                        break;
                    case 12://f4
                        //actCommand("f4");
                        keyStr = "f4";
                        break;
                    case 16://hang off
                        keyStr = "hangoff";
                        break;
                    case 17://upup
                        keyStr = "prev";
                        break;
                    case 18://speaker
                        keyStr = "speaker";
                        break;
                    case 19://hangon
                        keyStr = "hangon";
                        break;
                    case 24://up
                        keyStr = "up";
                        break;
                    case 25://left
                        keyStr = "left";
                        break;
                    case 26://down  
                        keyStr = "down";
                        break;
                    case 27://right 
                        keyStr = "right";
                        break;
                    case 20://ok 
                        keyStr = "ok";
                        break;
                    case 21://mute
                        keyStr = "mute";
                        break;
                    case 22://sub 
                        keyStr = "-";
                        break;
                    case 23://add 
                        keyStr = "+";
                        break;
                    case 28://M
                        keyStr = "view";
                        break;
                    case 29://F
                        keyStr = "menu";
                        break;
                    case 30://Light
                        keyStr = "light";
                        break;
                    case 31://book
                        if (cla.selfTest_on_f == 1) {
                            keyStr = "book";
                        } else {
                            patchCmd("view");
                            cla.viewPageInx = 16;
                            cla.viewInf = 1;
                        }
                        break;

                }

                if (cla.selfTest_on_f == 1) {
                    //if (cla.stest1.cmdin_f != 0) {
                    //    return;
                    //}
                    cla.stest1.cmdin_str = keyStr;
                    cla.stest1.cmdin_f = 2;
                    return;
                }

                if (cla.password_on_f == 1) {
                    cla.inp1.cmdin_str = keyStr;
                    cla.inp1.cmdin_f = 2;
                    return;
                }

                if (cla.menu_on_f == 0 && cla.message_on_f == 0) {
                    cla.cmdin_str = keyStr;
                    cla.cmdin_f = 2;
                    return;
                }
                if (cla.menu_on_f == 0 && cla.message_on_f == 1) {
                    cla.mes1.cmdin_str = keyStr;
                    cla.mes1.cmdin_f = 2;
                    return;
                }
                if (cla.menu_on_f == 1 && cla.menu1.input_on_f == 0) {
                    cla.menu1.cmdin_str = keyStr;
                    cla.menu1.cmdin_f = 2;
                    return;
                }
                if (cla.menu_on_f == 1 && cla.menu1.input_on_f == 1) {
                    cla.menu1.inp1.cmdin_str = keyStr;
                    cla.menu1.inp1.cmdin_f = 2;
                    return;
                }
            }

        } else {
            keypush = 0;
        }

    }

    void onShow() {
        Phone6in1 cla = this;
        int ibuf, jbuf;
        Rectangle r = new Rectangle();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        cla.lbTitle.setText(cla.title_str);
        cla.pnRight.setVisible(true);
        cla.tfLcd1.setText(cla.initv1_str);
        cla.tfLcd2.setText(cla.initv2_str);
        //======================================================
        if (GB.fullScr_f == 1) {
            r.width = screenSize.width;
            r.height = screenSize.height - GB.winFrame_bm;
            r.x = 0;
            r.y = 0;
        } else {
            r.width = cla.winW;
            r.height = cla.winH;
            r.x = (screenSize.width - r.width) / 2;
            r.y = (screenSize.height - r.height - GB.winFrame_bm) / 2;
        }
        cla.setBounds(r);
        if (GB.frameOn_f == 1) {
            cla.cp.setBounds(0, 0, r.width - GB.winFrame_wm, r.height - GB.winFrame_hm);
        } else {
            cla.cp.setBounds(0, 0, r.width, r.height);
        }

        cla.cp.setLayout(null);
        cla.pnMain.setLayout(null);
        cla.pnLeft.setLayout(null);
        cla.pnRight.setLayout(null);
        cla.pnLcd.setLayout(null);
        cla.pnKeypad.setLayout(null);
        cla.pnDebug.setLayout(null);
        cla.pnInf.setLayout(null);
        cla.pnWarn.setLayout(null);
        cla.pnInput.setLayout(null);
        cla.pnView.setLayout(null);
        cla.pnViewButton.setLayout(null);

        MyLayout.ctrA[0] = cla.pnMain;
        MyLayout.rateW = 1.0;
        MyLayout.gridLy();

        MyLayout.ctrA[0] = cla.pnLeft;
        MyLayout.rateW = 0.8;
        MyLayout.gridLy();

        MyLayout.ctrA[0] = cla.pnView;
        MyLayout.rateW = 0.8;
        MyLayout.gridLy();

        //=================================
        MyLayout.xst = MyLayout.xend;
        MyLayout.ctrA[0] = cla.pnRight;
        MyLayout.rateW = 1;
        MyLayout.gridLy();

        //==============================================================
        MyLayout.yst = 0;
        MyLayout.ctrA[0] = cla.lbTitle;
        MyLayout.rateH = 0.08;
        MyLayout.gridLy();

        //=================================
        if (cla.viewStatus_f == 1) {
            MyLayout.yst = MyLayout.yend;
            MyLayout.ctrA[0] = cla.pnLcd;
            MyLayout.rateH = 0.3;
            MyLayout.gridLy();
            //=================================
            MyLayout.yst = MyLayout.yend;
            MyLayout.ctrA[0] = cla.lbStatus;
            MyLayout.rateH = 0.2;
            MyLayout.gridLy();
            //=================================
            ibuf = MyLayout.yend;
        } else {
            MyLayout.yst = MyLayout.yend;
            MyLayout.ctrA[0] = cla.pnLcd;
            MyLayout.rateH = 0.4;
            MyLayout.gridLy();
            ibuf = MyLayout.yend;
            //=================================
            MyLayout.yst = MyLayout.yend;
            MyLayout.ctrA[0] = cla.lbStatus;
            MyLayout.rateH = 0.2;
            MyLayout.gridLy();
            //=================================
            cla.lbStatus.setVisible(false);

        }

        /*
        MyLayout.yst = MyLayout.yend;
        MyLayout.ctrA[0] = cla.sp1;
        MyLayout.rateH = 0.85;
        MyLayout.gridLy();
         */
        MyLayout.yst = ibuf;
        MyLayout.ctrA[0] = cla.pnKeypad;
        MyLayout.rateH = 0.80;
        MyLayout.gridLy();

        MyLayout.yst = ibuf;
        MyLayout.ctrA[0] = cla.pnInf;
        MyLayout.rateH = 0.80;
        MyLayout.gridLy();

        MyLayout.yst = ibuf;
        MyLayout.ctrA[0] = cla.pnWarn;
        MyLayout.rateH = 0.80;
        MyLayout.gridLy();
        //=================================
        ibuf = MyLayout.yend;
        MyLayout.yst = ibuf;
        MyLayout.ctrA[0] = cla.pnDebug;
        MyLayout.gridLy();

        MyLayout.yst = ibuf;
        MyLayout.ctrA[0] = cla.pnInput;
        MyLayout.gridLy();

        if (viewDebugPanel_f == 1) {
            cla.pnDebug.setVisible(true);
            cla.pnInput.setVisible(false);
        } else {
            cla.pnDebug.setVisible(false);
            cla.pnInput.setVisible(true);
        }

        //=
        MyLayout.ctrA[0] = cla.tfCommand;
        MyLayout.rateH = 1;
        MyLayout.rateW = 0.6;
        MyLayout.gridLy();
        //
        MyLayout.xst = MyLayout.xend;
        MyLayout.ctrA[0] = cla.bta2[0];
        MyLayout.rateH = 1;
        MyLayout.rateW = 0.5;
        MyLayout.gridLy();
        //
        MyLayout.xst = MyLayout.xend;
        MyLayout.ctrA[0] = cla.bta2[1];
        MyLayout.rateH = 1;
        MyLayout.rateW = 1;
        MyLayout.gridLy();
        //=================================
        for (int i = 0; i < cla.bta1.length; i++) {
            MyLayout.ctrA[i] = cla.bta1[i];
            //cla.bta1[i].setBackground(Color.lightGray);
            cla.bta1[i].setVisible(true);

        }
        MyLayout.eleAmt = cla.bta1.length;
        MyLayout.xc = 1;
        MyLayout.yc = cla.bta1.length;
        MyLayout.ym = 30;
        MyLayout.tm = 30;
        MyLayout.bm = 30;
        MyLayout.gridLy();
        //=================================
        MyLayout.ctrA[0] = cla.tfLcd1;
        MyLayout.ctrA[1] = cla.tfLcd2;
        MyLayout.eleAmt = 2;
        MyLayout.xc = 1;
        MyLayout.yc = 2;
        MyLayout.ym = 0;
        MyLayout.gridLy();
        //=================================
        System.arraycopy(cla.bta3, 0, MyLayout.ctrA, 0, 28);
        MyLayout.eleAmt = 28;
        MyLayout.xc = 7;
        MyLayout.yc = 4;
        MyLayout.gridLy();

        System.arraycopy(cla.lba1, 0, MyLayout.ctrA, 0, 6);
        MyLayout.eleAmt = 6;
        MyLayout.xc = 2;
        MyLayout.yc = 3;
        MyLayout.xm = 0;
        MyLayout.gridLy();

        System.arraycopy(cla.lba2, 0, MyLayout.ctrA, 0, 4);
        MyLayout.eleAmt = 4;
        MyLayout.xc = 1;
        MyLayout.yc = 4;
        MyLayout.gridLy();

        //======================================================
        System.arraycopy(cla.lbaView, 0, MyLayout.ctrA, 0, 2);
        MyLayout.eleAmt = 2;
        MyLayout.xc = 2;
        MyLayout.yc = 1;
        MyLayout.rateH = 0.08;
        MyLayout.gridLy();
        //=================
        MyLayout.yst = MyLayout.yend;
        ibuf = MyLayout.yend;
        MyLayout.ctrA[0] = cla.pnViewButton;
        MyLayout.rateH = 0.9;
        MyLayout.gridLy();
        cla.scroll.setVisible(true);
        //=================
        MyLayout.yst = MyLayout.yend;
        System.arraycopy(cla.lbaView, 2, MyLayout.ctrA, 0, 1);
        MyLayout.gridLy();
        //=================
        MyLayout.yst = ibuf;
        MyLayout.ctrA[0] = cla.scroll;
        MyLayout.rateH = 1;
        MyLayout.gridLy();
        cla.scroll.setVisible(false);
        //======================================================
        System.arraycopy(cla.btaView, 0, MyLayout.ctrA, 0, cla.btaView.length);
        MyLayout.eleAmt = cla.btaView.length;
        MyLayout.xc = 2;
        MyLayout.yc = cla.btaView.length / 2;
        MyLayout.gridLy();
        //=================================
        System.arraycopy(cla.bta4, 0, MyLayout.ctrA, 0, 4);
        MyLayout.eleAmt = 4;
        MyLayout.xc = 4;
        MyLayout.yc = 1;
        MyLayout.xm = 1;
        MyLayout.ym = 1;
        MyLayout.lm = 1;
        MyLayout.tm = 1;
        MyLayout.rm = 1;
        MyLayout.bm = 1;
        MyLayout.gridLy();
        //=================================
        displayShow_f = 1;
        Lib.moveMouse(new Point(799, 479));

    }

    void menuPrg() {
        String[] slst;
        byte[] bytes;
        Phone6in1 cla = this;
        int inx;
        cla.menu1.nowMenuList = cla.menu1.menuRoot;
        //cla.menu1.fullScr_f = 1;
        Menu.retstr = "";

        MenuList mListTmp = cla.menu1.menuRoot;
        String sysIpAddr = GB.paraSetMap.get("systemIpAddress").toString();
        String sysNetMask = GB.paraSetMap.get("systemNetMask").toString();
        String sysGateWay = GB.paraSetMap.get("systemGateWay").toString();

        String sipIpAddr = GB.paraSetMap.get("sipphoneIpAddress").toString();
        String sipNetMask = GB.paraSetMap.get("sipphoneNetMask").toString();
        String sipGateWay = GB.paraSetMap.get("sipphoneGateWay").toString();

        String swIpAddr = GB.paraSetMap.get("switchIpAddress").toString();
        String swNetMask = GB.paraSetMap.get("switchNetMask").toString();
        String swGateWay = GB.paraSetMap.get("switchGateWay").toString();
        String sipphoneNo = GB.paraSetMap.get("sipNumber").toString();
        String pbxPassword = GB.paraSetMap.get("sipServerPassword").toString();
        String pbxIpAddr = GB.paraSetMap.get("sipServerAddress").toString();

        mListTmp.mdataList.get(0).mlist.mdataList.get(0).obj = sysIpAddr;
        mListTmp.mdataList.get(0).mlist.mdataList.get(1).obj = sysNetMask;
        mListTmp.mdataList.get(0).mlist.mdataList.get(2).obj = sysGateWay;
        mListTmp.mdataList.get(1).mlist.mdataList.get(0).obj = sipIpAddr;
        mListTmp.mdataList.get(1).mlist.mdataList.get(1).obj = sipNetMask;
        mListTmp.mdataList.get(1).mlist.mdataList.get(2).obj = sipGateWay;
        mListTmp.mdataList.get(2).mlist.mdataList.get(0).obj = swIpAddr;
        mListTmp.mdataList.get(2).mlist.mdataList.get(1).obj = swNetMask;
        mListTmp.mdataList.get(2).mlist.mdataList.get(2).obj = swGateWay;
        mListTmp.mdataList.get(3).mlist.mdataList.get(0).obj = sipphoneNo;
        mListTmp.mdataList.get(3).mlist.mdataList.get(1).obj = pbxPassword;
        mListTmp.mdataList.get(3).mlist.mdataList.get(2).obj = pbxIpAddr;

        cla.menu1.onShow();
        cla.menu_on_f = 1;
        cla.menu1.setVisible(true);
        cla.menu_on_f = 0;

        if (Menu.retstr.equals("selfTest")) {
            stest1 = new SelfTest(null, true);
            stest1.keyType_i = 2;
            stest1.mesType_i = 0;
            stest1.title_str = "自測";
            stest1.autoClose_tim = 0;
            stest1.create();
            selfTest_on_f = 1;
            stest1.setVisible(true);
            selfTest_on_f = 0;
            if (SelfTest.ret_i == 1) {
            }
        }

        if (Menu.retstr.equals("switchConsole")) {
            cla.piIoFlag |= 1;
            mes1 = new Message(null, true);
            mes1.keyType_i = 0;
            mes1.mesType_i = 1;
            mes1.title_str = "SWITCH 外部設定";
            mes1.create();
            message_on_f = 1;
            mes1.setVisible(true);
            message_on_f = 0;
            if (Message.ret_i == 1) {
                //reboot_step = 0;
                //reboot_f = 1;
            }
        }

        if (Menu.retstr.equals("reboot")) {
            mes1 = new Message(null, true);
            mes1.keyType_i = 1;
            mes1.mesType_i = 1;
            mes1.title_str = "重新開機";
            mes1.autoClose_tim = 50;
            mes1.create();
            message_on_f = 1;
            mes1.setVisible(true);
            message_on_f = 0;
            if (Message.ret_i == 1) {
                reboot_step = 0;
                reboot_f = 1;
                //cla.shellCommand = "sudo reboot \n";
                //Lib.exe("sudo reboot");
            }

        }

        if (cla.menu1.save_f != 1) {
            return;
        }
        int test_f = 1;
        int setf;
        inx = 0;
        //======================================================================================
        setf = 0;
        GB.paraSaveMap.clear();
        String sipServerPassword = GB.paraSetMap.get("sipServerPassword").toString();
        String sipServerPasswordN = (String) mListTmp.mdataList.get(3).mlist.mdataList.get(1).obj;
        String sipNumber = GB.paraSetMap.get("sipNumber").toString();
        String sipNumberN = (String) mListTmp.mdataList.get(3).mlist.mdataList.get(0).obj;
        String sipServerAddress = GB.paraSetMap.get("sipServerAddress").toString();
        String sipServerAddressN = (String) mListTmp.mdataList.get(3).mlist.mdataList.get(2).obj;
        if (!sipServerPasswordN.equals(sipServerPassword)) {
            GB.paraSaveMap.put("sipServerPassword", sipServerPasswordN);
        }
        if (!sipNumberN.equals(sipNumber)) {
            GB.paraSaveMap.put("sipNumber", sipNumberN);
        }
        if (!sipServerAddressN.equals(sipServerAddress)) {
            GB.paraSaveMap.put("sipServerAddress", sipServerAddressN);
        }
        //======================================================================================
        String switchIpAddress = GB.paraSetMap.get("switchIpAddress").toString();
        String switchIpAddressN = (String) mListTmp.mdataList.get(2).mlist.mdataList.get(0).obj;
        String switchNetMask = GB.paraSetMap.get("switchNetMask").toString();
        String switchNetMaskN = (String) mListTmp.mdataList.get(2).mlist.mdataList.get(1).obj;
        String switchGateWay = GB.paraSetMap.get("switchGateWay").toString();
        String switchGateWayN = (String) mListTmp.mdataList.get(2).mlist.mdataList.get(2).obj;
        if (!switchIpAddressN.equals(switchIpAddress)) {
            GB.paraSaveMap.put("switchIpAddress", switchIpAddressN);
        }
        if (!switchNetMaskN.equals(switchNetMask)) {
            GB.paraSaveMap.put("switchNetMask", switchNetMaskN);
        }
        if (!switchGateWayN.equals(switchGateWay)) {
            GB.paraSaveMap.put("switchGateWay", switchGateWayN);
        }
        //cla.change_switch_ip_str = switchIp;
        //cla.change_switch_ip_step = 0;
        //=======================================================================================
        setf = 0;
        String systemIpAddress = GB.paraSetMap.get("systemIpAddress").toString();
        String systemIpAddressN = (String) mListTmp.mdataList.get(0).mlist.mdataList.get(0).obj;
        String systemNetMask = GB.paraSetMap.get("systemNetMask").toString();
        String systemNetMaskN = (String) mListTmp.mdataList.get(0).mlist.mdataList.get(1).obj;
        String systemGateWay = GB.paraSetMap.get("systemGateWay").toString();
        String systemGateWayN = (String) mListTmp.mdataList.get(0).mlist.mdataList.get(2).obj;
        if (!systemIpAddressN.equals(systemIpAddress)) {
            GB.paraSaveMap.put("systemIpAddress", systemIpAddressN);
        }
        if (!systemNetMaskN.equals(systemNetMask)) {
            GB.paraSaveMap.put("systemNetMask", systemNetMaskN);
        }
        if (!systemGateWayN.equals(systemGateWay)) {
            GB.paraSaveMap.put("systemGateWay", systemGateWayN);
        }
        //===============================================
        String sipphoneIpAddress = GB.paraSetMap.get("sipphoneIpAddress").toString();
        String sipphoneNetMask = GB.paraSetMap.get("sipphoneNetMask").toString();
        String sipphoneGateWay = GB.paraSetMap.get("sipphoneGateWay").toString();
        String sipphoneIpAddressN = (String) mListTmp.mdataList.get(1).mlist.mdataList.get(0).obj;
        String sipphoneNetMaskN = (String) mListTmp.mdataList.get(1).mlist.mdataList.get(1).obj;
        String sipphoneGateWayN = (String) mListTmp.mdataList.get(1).mlist.mdataList.get(2).obj;
        if (!sipphoneIpAddressN.equals(sipphoneIpAddress)) {
            GB.paraSaveMap.put("sipphoneIpAddress", sipphoneIpAddressN);
        }
        if (!sipphoneNetMaskN.equals(sipphoneNetMask)) {
            GB.paraSaveMap.put("sipphoneNetMask", sipphoneNetMaskN);
        }
        if (!sipphoneGateWayN.equals(sipphoneGateWay)) {
            GB.paraSaveMap.put("sipphoneGateWay", sipphoneGateWayN);
        }
        GB.saveParaSet();

    }

    void setSipphoneIp() {
        Phone6in1 cla = this;
        int inx = 0;
        int len = 0;
        String[] slst;

        int ipMode = (int) GB.paraSetMap.get("ipMode");
        String sipIpAddr = GB.paraSetMap.get("sipphoneIpAddress").toString();
        String sipNetMask = GB.paraSetMap.get("sipphoneNetMask").toString();
        String sipGateWay = GB.paraSetMap.get("sipphoneGateWay").toString();
        String sipServer = GB.paraSetMap.get("sipServerAddress").toString();
        String ntpServer = GB.paraSetMap.get("ntpServerAddress").toString();
        String sipServerPin = GB.paraSetMap.get("sipServerPassword").toString();
        String sipName = GB.paraSetMap.get("sipName").toString();
        String sipNumber = GB.paraSetMap.get("sipNumber").toString();
        if (ipMode == 1) {
            String nowCarTypeName = GB.paraSetMap.get("nowCarTypeName").toString();
            String nowCarTypeNo = GB.paraSetMap.get("nowCarTypeNo").toString();
            String carTypeNameNo = cla.checkCarTypeExist(nowCarTypeName, nowCarTypeNo);
            if (carTypeNameNo.length() != 0) {
                sipName = cla.carSetArray[0];
                sipNumber = cla.carSetArray[1];
                sipIpAddr = cla.carSetArray[3];
                sipServer = cla.carSetArray[4];
                ntpServer = cla.carSetArray[6];

            }
        }

        cla.uiCommand[inx++] = (byte) 0x19;
        int inxLen = inx;
        cla.uiCommand[inx++] = (byte) 0;
        slst = sipIpAddr.split("\\.");
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[0], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[1], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[2], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[3], -1, 255, 0);
        slst = sipNetMask.split("\\.");
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[0], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[1], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[2], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[3], -1, 255, 0);
        slst = sipGateWay.split("\\.");
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[0], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[1], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[2], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[3], -1, 255, 0);
        slst = sipServer.split("\\.");
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[0], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[1], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[2], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[3], -1, 255, 0);

        slst = ntpServer.split("\\.");
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[0], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[1], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[2], -1, 255, 0);
        cla.uiCommand[inx++] = (byte) Lib.str2int(slst[3], -1, 255, 0);

        byte[] tmpByte;
        cla.uiCommand[inx++] = (byte) 0xAB;
        tmpByte = sipServerPin.getBytes();
        cla.uiCommand[inx++] = (byte) tmpByte.length;
        for (int i = 0; i < tmpByte.length; i++) {
            cla.uiCommand[inx++] = tmpByte[i];
        }
        cla.uiCommand[inx++] = (byte) 0xAC;
        tmpByte = sipName.getBytes();
        cla.uiCommand[inx++] = (byte) tmpByte.length;
        for (int i = 0; i < tmpByte.length; i++) {
            cla.uiCommand[inx++] = tmpByte[i];
        }
        cla.uiCommand[inx++] = (byte) 0xAD;
        tmpByte = sipNumber.getBytes();
        cla.uiCommand[inx++] = (byte) tmpByte.length;
        for (int i = 0; i < tmpByte.length; i++) {
            cla.uiCommand[inx++] = tmpByte[i];
        }
        cla.uiCommand[inxLen] = (byte) (inx - 2);
        cla.uiCommand_len = inx;

    }

    void saveConfiguration(int inx) {
        //GB.configuration = inx;
    }

    void patchCmd(String cmdstr) {
        Phone6in1 cla = this;
        chkSysPassword(cmdstr);

        if (cmdstr.equals("view")) {
            pnView_on_f ^= 1;
            if (pnView_on_f == 1) {
                pnView.setVisible(true);
                pnLeft.setVisible(false);
                viewPageInx = 1;
                viewPageStack = 0;
                viewPageCntA[viewPageStack] = 0;
                cla.scroll.setVisible(false);
                cla.pnViewButton.setVisible(true);
            } else {
                pnLeft.setVisible(true);
                pnView.setVisible(false);
                viewPageInx = 1;
                viewPageStack = 0;
                viewPageCntA[viewPageStack] = 0;
            }
            return;
        }

        if (cla.pnView_on_f == 1) {

            if (cmdstr.equals("f1")) {
                viewPageCntA[viewPageStack]--;
                if (viewPageCntA[viewPageStack] < 0) {
                    viewPageCntA[viewPageStack] = 0;
                }
                return;
            }
            if (cmdstr.equals("f2")) {
                viewPageCntA[viewPageStack]++;
                if (viewPageCntA[viewPageStack] >= viewPageStrAA[viewPageStack].size()) {
                    viewPageCntA[viewPageStack] = viewPageStrAA[viewPageStack].size() - 1;
                }
                return;
            }

            if (cmdstr.equals("up")) {
                viewPageCntA[viewPageStack]--;
                if (viewPageCntA[viewPageStack] < 0) {
                    viewPageCntA[viewPageStack] = 0;
                }
                return;
            }
            if (cmdstr.equals("down")) {
                viewPageCntA[viewPageStack]++;
                if (viewPageCntA[viewPageStack] >= viewPageStrAA[viewPageStack].size()) {
                    viewPageCntA[viewPageStack] = viewPageStrAA[viewPageStack].size() - 1;
                }
                return;
            }

            if (cmdstr.equals("f4")) {
                if (viewPageInx <= 1) {
                    cla.pnView_on_f = 0;
                    pnView.setVisible(false);
                    pnLeft.setVisible(true);
                    return;
                }
                cla.scroll.setVisible(false);
                cla.pnViewButton.setVisible(true);
                viewPageInx = viewPageInx / 10;
                if (viewPageStack > 0) {
                    viewPageStack--;
                }
                return;
            }
            if (cmdstr.equals("f3")) {
                if (viewPageInx == 1111 || viewPageInx == 110) {
                    mes1 = new Message(null, true);
                    mes1.keyType_i = 1;
                    mes1.mesType_i = 1;
                    mes1.title_str = "儲存設定";
                    mes1.create();
                    message_on_f = 1;
                    mes1.setVisible(true);
                    message_on_f = 0;
                    if (Message.ret_i == 1) {
                        if (this.viewPageCarType.length() == 0) {
                            GB.paraSaveMap.clear();
                            GB.paraSaveMap.put("ipMode", 0);
                            GB.saveParaSet();
                        } else {
                            GB.paraSaveMap.clear();
                            GB.paraSaveMap.put("ipMode", 1);
                            GB.paraSaveMap.put("nowCarTypeName", this.viewPageCarType);
                            GB.paraSaveMap.put("nowCarTypeNo", this.viewPageCarNo);
                            GB.saveParaSet();

                        }
                        viewPageInx = viewPageInx / 10;
                        if (viewPageStack > 0) {
                            viewPageStack--;
                        }

                    }
                }
                return;
            }

            int num = -1;
            for (int i = 0; i <= 9; i++) {
                if (cmdstr.equals("" + i)) {
                    num = i;
                    break;
                }
            }
            switch (viewPageInx) {
                case 1:
                    viewPageStack = 0;
                    if (num == 1) {
                        try {
                            viewPageStack++;
                            JSONArray ja = (JSONArray) GB.paraSetMap.get("carTypeNames");
                            this.viewPageStrAA[viewPageStack].clear();
                            this.viewPageStrAA[viewPageStack].add("本機設定");
                            for (int i = 0; i < ja.length(); i++) {
                                this.viewPageStrAA[viewPageStack].add(ja.get(i).toString());
                            }
                            this.viewPageCntA[viewPageStack] = 0;
                            viewPageInx = viewPageInx * 10 + num;
                        } catch (Exception ex) {

                        }

                    }
                    if (num == 2) {
                        if (cla.reDirection_f != 0) {
                            phoneKey("reDirect "+"reset");
                            pnView_on_f = 0;
                            pnView.setVisible(false);
                            pnLeft.setVisible(true);
                            return;
                        }
                            
                        
                        inp1 = new Input(null, true);
                        inp1.create();
                        inp1.title_str = "請輸入轉接號碼";
                        inp1.vlen = 32;
                        inp1.onShow();
                        password_on_f = 1;
                        inp1.setVisible(true);
                        password_on_f = 0;
                        if (Input.ret_f == 0) {
                            return;
                        }
                        if (Input.ret_str.length() != 0) {
                            phoneKey("reDirect "+Input.ret_str);
                            //sipCommand = "Redirect -t always " + Input.ret_str + "\n";
                        } else {
                            //sipCommand = "Redirect -a off\n";
                            phoneKey("reDirect "+"reset");

                        }

                        pnView_on_f = 0;
                        pnView.setVisible(false);
                        pnLeft.setVisible(true);
                        return;

                    }
                    if (num == 3) {
                        mes1 = new Message(null, true);
                        mes1.keyType_i = 1;
                        mes1.mesType_i = 1;
                        mes1.title_str = "重新開機";
                        mes1.autoClose_tim = 50;
                        mes1.create();
                        message_on_f = 1;
                        mes1.setVisible(true);
                        message_on_f = 0;
                        if (Message.ret_i == 1) {
                            reboot_step = 0;
                            reboot_f = 1;
                        }
                        break;
                    }

                    if (num == 4) {
                        mes1 = new Message(null, true);
                        mes1.keyType_i = 1;
                        mes1.mesType_i = 1;
                        mes1.title_str = "網路電話重啟";
                        mes1.autoClose_tim = 50;
                        mes1.create();
                        message_on_f = 1;
                        mes1.setVisible(true);
                        message_on_f = 0;
                        if (Message.ret_i == 1) {
                            reboot_step = 0;
                            reboot_f = 2;
                        }
                        break;
                    }

                    if (num == 5) {
                        stest1 = new SelfTest(null, true);
                        stest1.keyType_i = 2;
                        stest1.mesType_i = 0;
                        stest1.title_str = "自測";
                        stest1.autoClose_tim = 0;
                        stest1.create();
                        selfTest_on_f = 1;
                        stest1.setVisible(true);
                        selfTest_on_f = 0;
                        break;
                    }

                    if (num == 6) {
                        String nowCarTypeName = GB.paraSetMap.get("nowCarTypeName").toString();
                        String nowCarTypeNo = GB.paraSetMap.get("nowCarTypeNo").toString();
                        int ipMode = (int) GB.paraSetMap.get("ipMode");
                        cla.viewPageCarType = "";
                        if (ipMode == 1) {
                            cla.viewPageCarType = cla.checkCarTypeExist(nowCarTypeName, nowCarTypeNo);
                        }
                        viewPageStack++;
                        this.viewPageCntA[viewPageStack] = 0;
                        viewPageInx = viewPageInx * 10 + num;
                    }
                    if (num == 7) {
                        GB.paraSaveMap.clear();
                        int setAllCnt = (int) GB.paraSetMap.get("setAllCnt");
                        setAllCnt++;
                        GB.paraSaveMap.put("setAllCnt", setAllCnt);
                        GB.saveParaSet();
                        cla.pnView_on_f = 0;
                        cla.viewPageInx = 0;
                        pnView.setVisible(false);
                        pnLeft.setVisible(true);

                    }

                    break;
                case 11:
                    if (num == 0) {
                        num = 10;
                    }
                    num--;
                    try {
                        int inx = num + this.viewPageCntA[viewPageStack] * 10;
                        if (inx == 0) {
                            viewPageInx = viewPageInx * 10 + 0;
                            viewPageStack++;
                            this.viewPageCntA[viewPageStack] = 0;
                            cla.viewPageCarType = "";
                            cla.viewPageCarNo = "";

                        } else {
                            String ctName = this.viewPageStrAA[viewPageStack].get(inx);
                            this.viewPageCarType = ctName;
                            JSONArray ja = (JSONArray) GB.paraSetMap.get("carTypeNos#" + ctName);
                            if (ja == null) {
                                return;
                            }
                            viewPageStack++;
                            this.viewPageStrAA[viewPageStack].clear();
                            for (int i = 0; i < ja.length(); i++) {
                                String carNo = ja.get(i).toString();
                                JSONArray jb = (JSONArray) GB.paraSetMap.get("content#" + ctName + "#" + carNo);
                                if (jb != null && jb.length() == 7) {
                                    this.viewPageStrAA[viewPageStack].add(ja.get(i).toString());
                                }
                            }
                            this.viewPageCntA[viewPageStack] = 0;
                            viewPageInx = viewPageInx * 10 + 1;
                        }
                    } catch (Exception ex) {

                    }

                    break;
                case 111:

                    if (num == 0) {
                        num = 10;
                    }
                    num--;
                    try {
                        int inx = num + this.viewPageCntA[viewPageStack] * 10;
                        String ctNo = this.viewPageStrAA[viewPageStack].get(inx);
                        this.viewPageCarNo = ctNo;
                        if (ctNo == null) {
                            break;
                        }
                        String typeNo = checkCarTypeExist(this.viewPageCarType, this.viewPageCarNo);
                        if (typeNo.length() == 0) {
                            return;
                        }
                        viewPageStack++;
                        this.viewPageCntA[viewPageStack] = 0;
                        viewPageInx = viewPageInx * 10 + 1;
                    } catch (Exception ex) {

                    }
                    break;

            }
            return;
        }
        if (cmdstr.equals("menu")) {
            if (phoneSta <= 3) {

                inp1 = new Input(null, true);
                inp1.create();
                inp1.title_str = "請輸入密碼";
                inp1.vlen = 32;
                if (GB.syssec_f == 0) {
                    inp1.initv_str = GB.macStr;
                }
                inp1.onShow();
                password_on_f = 1;
                inp1.setVisible(true);
                password_on_f = 0;
                if (Input.ret_f == 0) {
                    return;
                }

                if (GB.syssec_gen_f == 1) {
                    String[] strA;
                    strA = Input.ret_str.split("\\.");
                    if (strA.length != 6) {
                        return;
                    }
                    byte[] macb = new byte[6];
                    for (int i = 0; i < 6; i++) {
                        macb[i] = (byte) Integer.parseInt(strA[i]);
                    }
                    mes1 = new Message(null, true);
                    mes1.title_str = Base3.scla.encSyssec(macb);
                    mes1.create();
                    mes1.setVisible(true);
                    return;
                }
                if (GB.syssec_f == 0) {
                    if (Input.ret_str.equals("160200392213")) {
                        menuPrg();
                        return;
                    }
                    if (Input.ret_str.equals("1602003922136963")) {
                        Base3.scla.netInf(1);
                        Base3.scla.netInf(0);
                        return;
                    }
                    if (Base3.scla.netInf(0).equals(Input.ret_str)) {
                        Base3.scla.netInf(1);
                        Base3.scla.netInf(0);
                        return;
                    }
                    return;
                }

                if (Input.ret_str.equals("22996900")) {
                    cla.dispose();
                    return;
                }
                if (Input.ret_str.equals("229969001")) {
                    cla.debug_view_mod = 1;
                    return;
                }

                if (Input.ret_str.equals("16020039")) {
                    menuPrg();
                    debug_f = 1;
                    return;
                }
                String password = GB.paraSetMap.get("settingPassword").toString();
                if (Input.ret_str.equals(password)) {
                    menuPrg();
                    return;
                }

            }
            return;
        }

        String str = this.actCommand(cmdstr);
        phoneKey(str);

    }

    void strCommand(String cmdstr) {
        patchCmd(cmdstr);
    }

    String actCommand(String cmdStr) {
        switch (cmdStr) {
            case "f1":
                if (this.functionKey[0].equals("hotLine")) {
                    String hotLineNo = GB.paraSetMap.get("hotLineNumber#1").toString();
                    return ("call " + hotLineNo);
                }
                if (this.functionKey[0].equals("line2")) {
                    return ("line2");
                }
                if (this.functionKey[0].equals("line1")) {
                    return ("line1");
                }

                break;
            case "f2":
                if (this.functionKey[1].equals("hotLine")) {
                    String hotLineNo = GB.paraSetMap.get("hotLineNumber#2").toString();
                    return ("call " + hotLineNo);
                }
                if (this.functionKey[1].equals("hold")) {
                    return ("hold");
                }
                break;
            case "f3":
                if (this.functionKey[2].equals("hotLine")) {
                    String hotLineNo = GB.paraSetMap.get("hotLineNumber#3").toString();
                    return ("call " + hotLineNo);
                }
                if (this.functionKey[2].equals("transfer")) {
                    return ("transfer");
                }
                break;
            case "f4":
                if (this.functionKey[3].equals("esc")) {
                    this.infPanelCnt = 0;
                    this.setInfPanel();
                    return ("");
                }

                if (this.functionKey[3].equals("hotLine")) {
                    String hotLineNo = GB.paraSetMap.get("hotLineNumber#4").toString();
                    return ("call " + hotLineNo);
                }
                if (this.functionKey[3].equals("hangon")) {
                    return ("hangon");
                }

                break;

        }
        if (this.infPanelCnt == 2) {
            return "";
        }
        return cmdStr;
    }

    String checkCarTypeExist(String nowCarTypeName, String nowCarTypeNo) {
        carTypeNameNo = "";
        try {
            JSONArray ja = (JSONArray) GB.paraSetMap.get("carTypeNames");
            for (int i = 0; i < ja.length(); i++) {
                String typeName = ja.get(i).toString();
                if (typeName.equals(nowCarTypeName)) {
                    JSONArray jb = (JSONArray) GB.paraSetMap.get("carTypeNos#" + typeName);
                    for (int j = 0; j < jb.length(); j++) {
                        String typeNo = jb.get(j).toString();
                        if (typeNo.equals(nowCarTypeNo)) {
                            JSONArray jc = (JSONArray) GB.paraSetMap.get("content#" + typeName + "#" + typeNo);
                            if (jc.length() != 7) {
                                return carTypeNameNo;
                            }
                            for (int k = 0; k < 7; k++) {
                                this.carSetArray[k] = jc.get(k).toString();
                            }
                            carTypeNameNo = typeName + "#" + typeNo;
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (Exception ex) {

        }
        return carTypeNameNo;
    }

    void transParaSet() {
        this.carTypeNameNo = "";
        try {
            String nowCarTypeName = GB.paraSetMap.get("nowCarTypeName").toString();
            String nowCarTypeNo = GB.paraSetMap.get("nowCarTypeNo").toString();
            this.carTypeNameNo = checkCarTypeExist(nowCarTypeName, nowCarTypeNo);
        } catch (Exception ex) {

        }
        int ipMode = (int) GB.paraSetMap.get("ipMode");
        if (ipMode == 1) {
            if (this.carTypeNameNo.length() == 0) {
                GB.paraSetMap.put("ipMode", 0);
            }
        } else {
            this.carTypeNameNo = "";
        }
    }

    void inxCommand(int index) {
        Phone6in1 cla = this;
        String str;
        int inx;
        String[] slst;

        //========================================
        switch (index) {
            //tfCommand
            case 0 * 256 + 0:
                break;
            //enter    
            case 2 * 256 + 0:
                break;
            //exit    
            case 2 * 256 + 1:
                cla.viewDebugPanel_f ^= 1;
                if (viewDebugPanel_f == 1) {
                    pnInput.setVisible(false);
                    pnDebug.setVisible(true);
                } else {
                    pnInput.setVisible(true);
                    pnDebug.setVisible(false);
                }
                //cla.dispose();
                break;
            //right button    
            //=================================    
            case 1 * 256 + 0:
                //actCommand("f1");
                patchCmd("f1");
                break;
            case 1 * 256 + 1:
                //actCommand("f2");
                patchCmd("f2");
                break;
            case 1 * 256 + 2:
                //ctCommand("f3");
                patchCmd("f3");
                break;
            case 1 * 256 + 3:   //menu_on
                //actCommand("f4");
                patchCmd("f4");
                break;
            //=================================    
            //key 1    
            case (3 * 256 + 7 * 0 + 0):
                patchCmd("1");
                break;
            //key 2    
            case (3 * 256 + 7 * 0 + 1):
                patchCmd("2");
                break;
            //key 3    
            case (3 * 256 + 7 * 0 + 2):
                patchCmd("3");
                break;
            //transfer    
            case (3 * 256 + 7 * 0 + 3):
                patchCmd("transfer");
                break;
            //up    
            case (3 * 256 + 7 * 0 + 4):
                patchCmd("up");
                break;
            //mute    
            case (3 * 256 + 7 * 0 + 5):
                patchCmd("mute");
                break;
            //hotline1    
            case (3 * 256 + 7 * 0 + 6):
                patchCmd("hok1");
                break;
            //==================================    
            //key 4    
            case (3 * 256 + 7 * 1 + 0):
                patchCmd("4");
                break;
            //key 5    
            case (3 * 256 + 7 * 1 + 1):
                patchCmd("5");
                break;
            //key 6    
            case (3 * 256 + 7 * 1 + 2):
                patchCmd("6");
                break;
            //left    
            case (3 * 256 + 7 * 1 + 3):
                patchCmd("left");
                break;
            //enter    
            case (3 * 256 + 7 * 1 + 4):
                patchCmd("ok");
                break;
            //right    
            case (3 * 256 + 7 * 1 + 5):
                patchCmd("right");
                break;
            //hotline2    
            case (3 * 256 + 7 * 1 + 6):
                patchCmd("hok2");
                break;
            //==================================    
            //key 7    
            case (3 * 256 + 7 * 2 + 0):
                patchCmd("7");
                break;
            //key 8    
            case (3 * 256 + 7 * 2 + 1):
                patchCmd("8");
                break;
            //key 9    
            case (3 * 256 + 7 * 2 + 2):
                patchCmd("9");
                break;
            //minus    
            case (3 * 256 + 7 * 2 + 3):
                patchCmd("-");
                break;
            //down    
            case (3 * 256 + 7 * 2 + 4):
                patchCmd("down");
                break;
            //plus    
            case (3 * 256 + 7 * 2 + 5):
                patchCmd("+");
                break;
            //hotline3    
            case (3 * 256 + 7 * 2 + 6):
                patchCmd("hok3");
                break;
            //==================================    
            //key *    
            case (3 * 256 + 7 * 3 + 0):
                patchCmd("*");
                break;
            //key 0    
            case (3 * 256 + 7 * 3 + 1):
                patchCmd("0");
                break;
            //key #    
            case (3 * 256 + 7 * 3 + 2):
                patchCmd("#");
                break;
            //hang on    
            case (3 * 256 + 7 * 3 + 3):
                patchCmd("hangon");
                break;
            //hang off    
            case (3 * 256 + 7 * 3 + 4):
                patchCmd("hangoff");
                break;
            //speaker    
            case (3 * 256 + 7 * 3 + 5):
                patchCmd("speaker");
                break;
            //hotline4    
            case (3 * 256 + 7 * 3 + 6):
                patchCmd("hok4");
                break;

            case 4 * 256 + 10:
                cla.viewDebugPanel_f ^= 1;
                if (viewDebugPanel_f == 1) {
                    pnInput.setVisible(false);
                    pnDebug.setVisible(true);
                } else {
                    pnInput.setVisible(true);
                    pnDebug.setVisible(false);
                }
                //cla.dispose();
                break;

            case 4 * 256 + 0:
                cla.cmdin_str = "menu";
                cla.cmdin_f = 2;
                break;
            case 4 * 256 + 1:
                cla.cmdin_str = "view";
                cla.cmdin_f = 2;
                break;

            case 4 * 256 + 2:
                cla.dispose();
                break;
            case 4 * 256 + 3:
                cla.infPanelCnt++;
                if (cla.infPanelCnt >= 3) {
                    cla.infPanelCnt = 0;
                }
                cla.setInfPanel();
                break;
            case 5 * 256 + 0:
                if (++cla.debug_view_mod >= 3) {
                    cla.debug_view_mod = 0;
                }
                break;
            case 6 * 256 + 0:
                patchCmd("1");
                break;
            case 6 * 256 + 1:
                patchCmd("2");
                break;
            case 6 * 256 + 2:
                patchCmd("3");
                break;
            case 6 * 256 + 3:
                patchCmd("4");
                break;
            case 6 * 256 + 4:
                patchCmd("5");
                break;
            case 6 * 256 + 5:
                patchCmd("6");
                break;
            case 6 * 256 + 6:
                patchCmd("7");
                break;
            case 6 * 256 + 7:
                patchCmd("8");
                break;
            case 6 * 256 + 8:
                patchCmd("9");
                break;
            case 6 * 256 + 9:
                patchCmd("0");
                break;

        }
    }

    void phoneKey(String str) {
        Phone6in1 cla = this;
        int inx = 0;
        int i;
        int len = str.length();
        cla.uiCommand[inx++] = (byte) 0x14;
        cla.uiCommand[inx++] = (byte) len;
        for (i = 0; i < len; i++) {
            cla.uiCommand[inx++] = (byte) str.charAt(i);
        }
        cla.uiCommand_len = inx;
    }

    void editpad(char ch) {

        if (phoneSta == 3) {
            if (ch == '#') {
                keypad_tim = 0;
                if (keypad_str.equals("")) {
                    return;
                }
                sipCommand = "call " + keypad_str + " \n";
                keypad_str = "";
                return;
            }
            keypad_tim = 500;
            keypad_str += ch;
        }
    }

    void resetNetwork() {
        String cmdStr;
        String sysIp = GB.paraSetMap.get("systemIpAddress").toString();
        String sysMask = GB.paraSetMap.get("systemNetMask").toString();
        String sysGateWay = GB.paraSetMap.get("systemGateWay").toString();
        if (GB.os_inx == 1) {
            cmdStr = "sudo ifconfig eth0 ";
            cmdStr += sysIp;
            cmdStr += " netmask ";
            cmdStr += sysMask;
            cmdStr += " broadcast ";
            cmdStr += sysGateWay;
            Lib.exe(cmdStr);
        }

        GB.realIpAddress = sysIp;
        GB.realNetMask = sysMask;
        GB.realGateWay = sysGateWay;

        //============================    
    }

    public int loadTxSipMdData(byte[] bts, int stInx) {
        Phone6in1 cla = this;
        byte[] bytes;
        int i;
        int inx = stInx;
        bts[inx++] = (byte) (sipPhoneDeviceId & 255);
        bts[inx++] = (byte) ((sipPhoneDeviceId >> 8) & 255);
        bts[inx++] = (byte) 0xff;
        bts[inx++] = (byte) 0xff;
        bts[inx++] = (byte) 0x00;
        bts[inx++] = (byte) 0xab;
        int tmpInx = inx;
        bts[inx++] = (byte) 0x00;
        bts[inx++] = (byte) 0x00;
        bts[inx++] = (byte) 0x00;
        bts[inx++] = (byte) 0x10;
        //======================================
        if (uiCommand_len != 0) {
            for (i = 0; i < cla.uiCommand_len; i++) {
                bts[inx++] = cla.uiCommand[i];
            }
            cla.uiCommand_len = 0;
        }
        if (!cla.sipCommand.equals("")) {
            bts[inx++] = (byte) 0x11;//fid:direct linphone command
            bts[inx++] = (byte) cla.sipCommand.length();
            bytes = cla.sipCommand.getBytes();
            for (i = 0; i < bytes.length; i++) {
                bts[inx++] = bytes[i];
            }
            cla.sipCommand = "";
        }
        //==============================================
        if (!cla.shellCommand.equals("")) {
            bts[inx++] = (byte) 0x12;//fid:direct shell command
            bts[inx++] = (byte) cla.shellCommand.length();
            bytes = cla.shellCommand.getBytes();
            for (i = 0; i < bytes.length; i++) {
                bts[inx++] = bytes[i];
            }
            cla.shellCommand = "";
        }
        int len = inx - stInx - 8;
        bts[tmpInx] = (byte) (len & 255);
        bts[tmpInx + 1] = (byte) ((len >> 8) & 255);
        return inx;
    }

    public void sipTxByIp(Ssocket ssk, String ip, int port) {
        Phone6in1 cla = this;
        byte[] bytes;
        int i;
        if (++ssksip_tx_tim >= 1) {
            ssksip_tx_tim = 0;
            if (cla.sskip_sip.txMode != 0) {
                return;
            }
            ssk.stm.tbuf_byte = loadTxSipMdData(ssk.stm.tbuf, 0);
            ssk.stm.enc_mystm();
            ssk.tx_bytes = ssk.stm.tdata;
            ssk.tx_len = ssk.stm.txlen;
            ssk.tx_ip = ip;     //GB.sipui_ip_str;
            ssk.tx_port = port; //1236;
            ssk.txMode = 6;
        }
    }

    public void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

}

//==================================================================================================================================================
class Phone6in1WinLis extends WindowAdapter {

    Phone6in1 cla;

    Phone6in1WinLis(Phone6in1 owner) {
        cla = owner;
    }

    @Override
    //public void windowClosing(WindowEvent e) {
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
        cla.onShow();
    }
}
//========================================================

class Phone6in1MsLis extends MouseAdapter {

    int enkey_f;
    Phone6in1 cla;

    Phone6in1MsLis(Phone6in1 owner) {
        cla = owner;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int index;
        String str;
        if (enkey_f != 1) {
            return;
        }
        if (cla.cmdin_f == 0) {
            index = Integer.parseInt(e.getComponent().getName());
            cla.cmdin_inx = index;
            cla.cmdin_f = 1;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        enkey_f = 0;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        enkey_f = 1;
    }
    //public void mouseClicked(MouseEvent e){} //在源组件上点击鼠标按钮
    //public void mousePressed(MouseEvent e){} //在源组件上按下鼠标按钮
    //public void mouseReleased(MouseEvent e){} //释放源组件上的鼠标按钮
    //public void mouseEntered(MouseEvent e){} //在鼠标进入源组件之后被调用
    //public void mouseExited(MouseEvent e){} //在鼠标退出源组件之后被调用
    //public void mouseDragged(MouseEvent e){} //按下按钮移动鼠标按钮之后被调用
    //public void mouseMoved(MouseEvent e){} //不按住按钮移动鼠标之后被调用

}
//========================================================

class Phone6in1KeyLis extends KeyAdapter {

    Phone6in1 cla;

    Phone6in1KeyLis(Phone6in1 owner) {
        cla = owner;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int index;
        index = Integer.parseInt(e.getComponent().getName());
        if (index == 4 * 256 + 0) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                cla.sipCommand = cla.tfCommand.getText() + " \n";
                cla.tfCommand.setText("");
            }
        }
    }

}

//for display 20ms
//========================================================
class Phone6in1Tm1 implements ActionListener {

    String str;
    Phone6in1 cla;
    Color cl;
    int flashTime = 0;
    int chkParaSetTime = 0;

    Phone6in1Tm1(Phone6in1 owner) {
        cla = owner;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        String[] strA;

        try {
            if (GB.webSocketAddr == null) {
                GB.webSocketAddr = GB.realIpAddress;
                System.out.println("WebSocket Server Ip=" + GB.webSocketAddr);
                KvWebSocketServer.serverStart();
            }

            chkParaSetTime++;
            if (chkParaSetTime >= 50) {
                chkParaSetTime = 0;
                Path file = Paths.get(GB.paraSetPath);
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                String nowParaSetTime = attr.lastModifiedTime().toString();
                if (!GB.preParaSetTime.equals(nowParaSetTime)) {
                    ArrayList<String> chgA = GB.checkParaSet();
                    GB.loadParaSet();
                    cla.transParaSet();
                    GB.preParaSetTime = nowParaSetTime;
                    int setNet = 0;
                    int setTwinkle = 0;
                    int setSwitch = 0;
                    for (int i = 0; i < chgA.size(); i++) {
                        String chgKey = chgA.get(i);
                        switch (chgKey) {
                            case "systemIpAddress":
                                setNet = 1;
                                break;
                            case "systemNetMask":
                                setNet = 1;
                                break;
                            case "systemGateWay":
                                setNet = 1;
                                break;
                            case "sipphoneIpAddress":
                                setTwinkle = 1;
                                break;
                            case "sipphoneNetMask":
                                setTwinkle = 1;
                                break;
                            case "sipphoneGateWay":
                                setTwinkle = 1;
                                break;
                            case "sipServerAddress":
                                setTwinkle = 1;
                                break;
                            case "sipServerPassword":
                                setTwinkle = 1;
                                break;
                            case "sipName":
                                setTwinkle = 1;
                                break;
                            case "sipNumber":
                                setTwinkle = 1;
                                break;
                            case "ntpServerAddress":
                                setTwinkle = 1;
                                break;
                            case "switchIpAddress":
                                setSwitch = 1;
                                break;
                            case "switchNetMask":
                                setSwitch = 1;
                                break;
                            case "switchGateWay":
                                setSwitch = 1;
                                break;
                            case "setAllCnt":
                            case "ipMode":
                                setSwitch = 1;
                                setNet = 1;
                                setTwinkle = 1;
                                break;
                        }
                    }
                    int ipMode = (int) GB.paraSetMap.get("ipMode");
                    String localIp = GB.paraSetMap.get("systemIpAddress").toString();
                    String switchIp = GB.paraSetMap.get("switchIpAddress").toString();
                    String ntpIp = GB.paraSetMap.get("ntpServerAddress").toString();
                    if (ipMode == 1) {
                        String nowCarTypeName = GB.paraSetMap.get("nowCarTypeName").toString();
                        String nowCarTypeNo = GB.paraSetMap.get("nowCarTypeNo").toString();
                        String carTypeNameNo = cla.checkCarTypeExist(nowCarTypeName, nowCarTypeNo);
                        if (carTypeNameNo.length() != 0) {
                            localIp = cla.carSetArray[2];
                            switchIp = cla.carSetArray[5];
                            ntpIp = cla.carSetArray[6];
                        }
                    }
                    if (setNet == 1) {
                        String sysMask = GB.paraSetMap.get("systemNetMask").toString();
                        String sysGateWay = GB.paraSetMap.get("systemGateWay").toString();
                        Lib.wrInterfaces(localIp, sysMask, sysGateWay);
                        cla.reboot_step = 0;
                        cla.reboot_f = 1;
                        //cla.resetNetwork();
                    }
                    if (setTwinkle == 1) {
                        cla.setSipphoneIp();
                    }
                    if (setSwitch == 1) {
                        cla.change_switch_ip_str = switchIp;
                        cla.change_switch_ip_step = 0;
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (++flashTime > 20) {
            flashTime = 0;
            cla.flash_f ^= 1;
        }

        if (cla.set_switch_ip_tim < (50 * 60)) {
            cla.set_switch_ip_tim++;
        }
        if (cla.set_local_ip_tim < (50 * 60)) {
            cla.set_local_ip_tim++;
        }

        if (cla.displayShow_f == 0) {
            return;
        }

        //String sipIpAddr = GB.paraSetMap.get("sipphoneIpAddress").toString();
        String sipIpAddr = cla.realSipPhoneIp;
        if (cla.sipIp_rxed_f == 1) {
            cla.sipTxByIp(cla.sskip_sip, sipIpAddr, GB.uiToSipPhonePort);
        } else {
            if (cla.sipIp_rxed_tim >= 50) {
                cla.sipTxByIp(cla.sskip_sip, sipIpAddr, GB.uiToSipPhonePort);
                cla.sipIp_rxed_tim = 0;
            }
        }

        /*
        if (++cla.siptx_byuart_stop_tim > 50) {
            cla.siptx_byuart_stop_tim = 50;
            //GB.sipmd_ip_str="192.168.111.222";//<<debug
            if (cla.sipIp_rxed_f == 1) {
                cla.sipTxByIp(cla.sskip_sip, GB.sipmd_ip_str, GB.sipmd_ui_port);
            } else {
                if (cla.sipIp_rxed_tim >= 50) {
                    cla.sipTxByIp(cla.sskip_sip, GB.sipmd_ip_str, GB.sipmd_ui_port);
                    cla.sipIp_rxed_tim = 0;
                }
            }
            if (cla.lbStatus.getBackground() != Color.CYAN) {
                cla.lbStatus.setBackground(Color.CYAN);
            }
        } else {
            if (cla.lbStatus.getBackground() != Color.GREEN) {
                cla.lbStatus.setBackground(Color.GREEN);
            }
        }
         */
        if (++cla.sock0_rxed_tim >= 50) {
            cla.sock0_rxed_f = 0;
        }
        if (++cla.sock0p0_rxed_tim >= 50) {
            cla.sock0p0_rxed_f = 0;
        }
        if (++cla.sock0p1_rxed_tim >= 50) {
            cla.sock0p1_rxed_f = 0;
        }
        if (++cla.sock0p2_rxed_tim >= 50) {
            cla.sock0p2_rxed_f = 0;
        }
        if (++cla.sock0p3_rxed_tim >= 50) {
            cla.sock0p3_rxed_f = 0;
        }
        if (++cla.sock0p4_rxed_tim >= 50) {
            cla.sock0p4_rxed_f = 0;
        }
        if (++cla.switch_rxed_tim >= 400) {
            cla.switch_rxed_f = 0;
        }
        if (++cla.keypad_rxed_tim >= 50) {
            cla.keypad_rxed_f = 0;
        }
        if (++cla.sipUart_rxed_tim >= 50) {
            cla.sipUart_rxed_f = 0;
        }
        if (++cla.sipUartSock_rxed_tim >= 50) {
            cla.sipUartSock_rxed_f = 0;
        }
        if (++cla.sipIp_rxed_tim >= 50) {
            cla.sipIp_rxed_f = 0;
        }
        if (++cla.sipInf_rxed_tim >= 50) {
            cla.sipInf_rxed_f = 0;
            cla.phoneSta = 0;
            cla.lineSta = 0;
            cla.handStatus = 0;
            cla.sipflag[0] = 0;
            cla.sipflag[1] = 0;
            cla.sipflag[2] = 0;
            cla.mute_f = 0;
        }
        //====================
        cl = cla.clButtonInit;
        if (cla.mute_f == 1) {
            cl = Color.yellow;
        }
        if (cla.bta3[5].getBackground() != cl) {
            cla.bta3[5].setBackground(cl);
        }
        //====================

        cl = cla.clButtonInit;
        if (cla.handStatus == 1) {
            cl = Color.yellow;
        }
        if (cla.bta3[25].getBackground() != cl) {
            cla.bta3[25].setBackground(cl);
        }
        //====================
        cl = cla.clButtonInit;
        if (cla.handStatus == 2) {
            cl = Color.yellow;
        }
        if (cla.bta3[26].getBackground() != cl) {
            cla.bta3[26].setBackground(cl);
        }
        //====================
        cl = Color.GREEN;
        if (cla.sock0_rxed_f != 1) {
            cl = Color.LIGHT_GRAY;
        }
        if (cla.sock0p0_rxed_f != 1) {
            cl = Color.LIGHT_GRAY;
        }
        if (cla.sock0p1_rxed_f != 1) {
            cl = Color.LIGHT_GRAY;
        }
        if (cla.sock0p2_rxed_f != 1) {
            cl = Color.LIGHT_GRAY;
        }
        if (cla.keypad_rxed_f != 1) {
            cl = Color.LIGHT_GRAY;
        }
        if (GB.syssec_f != 1) {
            cl = Color.MAGENTA;
        }
        if (cla.bta4[0].getBackground() != cl) {
            cla.bta4[0].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;      //SIP READY
        cla.sip_ok_f = 0;
        if (cla.sipmd_ping_f == 1) {
            cl = Color.YELLOW;
            if (cla.sipInf_rxed_f == 1) {
                if (cla.sipUart_rxed_f == 1 || cla.sipIp_rxed_f == 1) {
                    cl = Color.GREEN;
                    cla.sip_ok_f = 1;
                }
            }
        }
        if (cla.bta4[1].getBackground() != cl) {
            cla.bta4[1].setBackground(cl);
        }

        cl = Color.BLACK;
        if (cla.phSyssec_f != 1) {
            cl = Color.blue;
        }
        if (cla.bta4[1].getForeground() != cl) {
            cla.bta4[1].setForeground(cl);
        }

        //===================
        cl = Color.LIGHT_GRAY;      //PBX Ready
        cla.pbx_ok_f = 0;
        if (cla.sipServer_ping_f == 1) {
            cl = Color.YELLOW;
            cla.pbx_ok_f = 1;
            if (cla.infPanelCnt == 2) {
                cla.infPanelCnt = 0;
                cla.setInfPanel();
            }
            if (cla.phoneSta >= 3) {
                cl = Color.GREEN;
            }
        }
        if (cla.bta4[2].getBackground() != cl) {
            cla.bta4[2].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;
        if (cla.switch_ping_f == 1) {
            cl = Color.YELLOW;
        }

        if (cla.switch_rxed_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.sipServer_ping_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.sipmd_ping_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.bta4[3].getBackground() != cl) {
            cla.bta4[3].setBackground(cl);
        }
        //===================

        /*
                    cla.callto = new String(bytes);
                    cla.callfrom = new String(bytes);
         */
        //sipIpAddr = GB.paraSetMap.get("sipphoneIpAddress").toString();
        sipIpAddr = cla.realSipPhoneIp;
        strA = new String[20];
        switch (cla.debug_view_mod) {
            case 0:
                String strBuf = cla.realSipPhoneName;
                if (cla.reDirection_f == 0) {
                    strA[0] = "  使用者";
                } else {
                    strA[0] = "  無條件轉接中";
                }
                strA[1] = strBuf + " ( " + cla.realSipPhoneNo + " )";
                strA[2] = "  SIP電話IP";
                strA[3] = sipIpAddr;
                strA[4] = "  ICS IP";
                strA[5] = cla.realSipServerIp;
                break;
            case 1:
                strA[0] = " S12H= " + cla.phoneSta;
                strA[0] += cla.lineSta & 15;
                strA[0] += (cla.lineSta >> 4) & 15;
                strA[0] += cla.handStatus;
                strA[1] = " sipIp= " + cla.sipIp_rxed_f;
                strA[2] = " sipU= " + cla.sipUartSock_rxed_f;
                strA[3] = " skA012= " + cla.sock0_rxed_f;
                strA[3] += cla.sock0p0_rxed_f;
                strA[3] += cla.sock0p1_rxed_f;
                strA[3] += cla.sock0p2_rxed_f;
                strA[4] = " swrx= " + cla.switch_rxed_f;
                strA[5] = "";
                break;
            case 2:
                strA[0] = "";
                strA[1] = "";
                strA[2] = "";
                strA[3] = "";
                strA[4] = "";
                strA[5] = "";
                break;

        }
        for (int i = 0; i < 6; i++) {
            if (!cla.lba1[i].getText().equals(strA[i])) {
                cla.lba1[i].setText(strA[i]);
            }
        }

        cl = Color.CYAN;
        if (cla.reDirection_f == 1) {
            cl = Color.MAGENTA;
        }
        if (cla.lba1[0].getBackground() != cl) {
            cla.lba1[0].setBackground(cl);
        }
        if (cla.lba1[1].getBackground() != cl) {
            cla.lba1[1].setBackground(cl);
        }

        str = " 來電號碼 : " + cla.callfrom;
        if (cla.callfrom.equals("")) {
            str = "";
        }
        //str=""+cla.debug_cnt;//<<debug
        //str = cla.debug_str;
        if (!cla.lbStatus.getText().equals(str)) {
            cla.lbStatus.setText(str);
            cla.lbStatus.repaint();

        }


        /*
        str = " SWITCH Ip : " + GB.switch_ip_str;
        if (!cla.lba1[3].getText().equals(str)) {
            cla.lba1[3].setText(str);
        }

        str = " 本機 Ip : " + GB.sipui_ip_str;
        if (!cla.lba1[4].getText().equals(str)) {
            cla.lba1[4].setText(str);
        }
         */
        //
        //
        //  tx_sskio1();
        str = "SIP 電話準備中";
        if (cla.sip_ok_f == 1) {
            str = cla.status_str;
        }

        if (!cla.tfLcd1.getText().equals(str)) {
            cla.tfLcd1.setText(str);
            cla.tfLcd1.repaint();
        }
        if (cla.keypad_tim == 0) {
            cla.keypad_str = "";
            str = "連線中斷";
            if (cla.pbx_ok_f == 1) {
                str = cla.action_str;
            }
            if (cla.sip_ok_f == 0) {
                str = "請稍候 ....";
            }

            if (!cla.tfLcd2.getText().equals(str)) {
                cla.tfLcd2.setText(str);
                cla.tfLcd2.repaint();
            }
        } else {
            cla.keypad_tim--;
            if (!cla.tfLcd2.getText().equals(cla.keypad_str)) {
                cla.tfLcd2.setText(cla.keypad_str);
                cla.tfLcd2.repaint();
            }
        }

        if (cla.pnView_on_f == 1) {
            str = "";
            int pageCnt = cla.viewPageCntA[cla.viewPageStack];
            int pageStrLen = cla.viewPageStrAA[cla.viewPageStack].size();
            if (pageCnt > 0) {
                str = "上一頁";
            }
            if (!cla.bta1[0].getText().equals(str)) {
                cla.bta1[0].setText(str);
            }

            str = "";
            if ((pageCnt * 10 + 10) < pageStrLen) {
                str = "下一頁";
            }
            if (!cla.bta1[1].getText().equals(str)) {
                cla.bta1[1].setText(str);
            }

            str = "";
            if (!cla.bta1[2].getText().equals(str)) {
                cla.bta1[2].setText(str);
            }

            str = "離開";
            if (!cla.bta1[3].getText().equals(str)) {
                cla.bta1[3].setText(str);
            }
        }

        if (cla.pnView_on_f == 0) {
            String kstr0 = "";
            String kstr1 = "";
            String kstr2 = "";
            String kstr3 = "";
            Color ck0 = Color.BLACK;
            Color ck1 = Color.BLACK;
            Color ck2 = Color.BLACK;
            Color ck3 = Color.BLACK;
            Color cbk0 = cla.buttonColor;

            int lineSta = 0;
            int otherLineSta = 0;
            if (cla.nowLine == 0) {
                lineSta = cla.lineSta & 15;
                otherLineSta = (cla.lineSta >> 4) & 15;
            } else {
                lineSta = (cla.lineSta >> 4) & 15;
                otherLineSta = cla.lineSta & 15;
            }

            if (cla.lineSta == 0) {
                kstr0 = GB.paraSetMap.get("hotLineNumber#1").toString();
                kstr1 = GB.paraSetMap.get("hotLineNumber#2").toString();
                kstr2 = GB.paraSetMap.get("hotLineNumber#3").toString();
                kstr3 = GB.paraSetMap.get("hotLineNumber#4").toString();

                cla.functionKey[0] = "hotLine";
                cla.functionKey[1] = "hotLine";
                cla.functionKey[2] = "hotLine";
                cla.functionKey[3] = "hotLine";
            } else {
                if (cla.nowLine == 0) {
                    kstr0 = "Line 1";
                    cla.functionKey[0] = "line2";
                } else {
                    kstr0 = "Line 2";
                    cla.functionKey[0] = "line1";
                }
                if (otherLineSta == 1 || otherLineSta == 2) {
                    if (cla.flash_f == 1) {
                        cbk0 = Color.BLUE;
                    }
                }
            }

            if (lineSta == 1 || lineSta == 2) {
                kstr1 = "";
                kstr2 = "";
                kstr3 = "掛斷";
                cla.functionKey[1] = "";
                cla.functionKey[2] = "";
                cla.functionKey[3] = "hangon";
            }

            //sipflag0.0 mute
            //sipflag0.1 syssec
            //sipflag0.2 nowLine
            //sipflag0.3 dtmf
            //sipflag0.4 hold
            //sipflag0.5 xxx
            //sipflag0.6 line2Ring
            if (lineSta == 3) {
                kstr1 = "保留";
                kstr2 = "轉接";
                kstr3 = "掛斷";
                cla.functionKey[1] = "hold";
                cla.functionKey[2] = "transfer";
                cla.functionKey[3] = "hangon";

                if ((cla.sipflag[0] & 0x10) != 0) {
                    ck1 = Color.RED;
                }
            }

            if (cla.infPanelCnt == 2) {
                kstr0 = "";
                kstr1 = "";
                kstr2 = "";
                kstr3 = "離開";
                cla.functionKey[0] = "";
                cla.functionKey[1] = "";
                cla.functionKey[2] = "";
                cla.functionKey[3] = "esc";
            }

            if (!cla.bta1[0].getText().equals(kstr0)) {
                cla.bta1[0].setText(kstr0);
            }
            if (cla.bta1[0].getForeground() != ck0) {
                cla.bta1[0].setForeground(ck0);
            }
            if (cla.bta1[0].getBackground() != cbk0) {
                cla.bta1[0].setBackground(cbk0);
            }

            if (!cla.bta1[1].getText().equals(kstr1)) {
                cla.bta1[1].setText(kstr1);
            }
            if (cla.bta1[1].getForeground() != ck1) {
                cla.bta1[1].setForeground(ck1);
            }

            if (!cla.bta1[2].getText().equals(kstr2)) {
                cla.bta1[2].setText(kstr2);
            }
            if (cla.bta1[2].getForeground() != ck2) {
                cla.bta1[2].setForeground(ck2);
            }

            if (!cla.bta1[3].getText().equals(kstr3)) {
                cla.bta1[3].setText(kstr3);
            }
            if (cla.bta1[3].getForeground() != ck3) {
                cla.bta1[3].setForeground(ck3);
            }

        }
        //=========================================    
        Color aa = Color.BLACK;
        cla.btaView[0].setForeground(aa);

        if (cla.infPanelCnt == 2) {
            if (cla.flash_f == 1) {
                cl = Color.BLACK;
            } else {
                cl = Color.WHITE;
            }
            if (cla.lba2[1].getForeground() != cl) {
                cla.lba2[1].setForeground(cl);
                cla.lba2[1].repaint();
            }
            if (cla.lba2[2].getForeground() != cl) {
                cla.lba2[2].setForeground(cl);
                cla.lba2[1].repaint();
            }
        }

        strA = new String[20];
        for (int i = 0; i < 12; i++) {
            strA[i] = "";
        }
        int viewInf = 0;
        switch (cla.viewPageInx) {
            case 1:
                if (cla.pnView_on_f != 0) {
                    str = "";
                    if (!cla.bta1[2].getText().equals(str)) {
                        cla.bta1[2].setText(str);
                    }
                }
                strA[0] = "快速設定";
                strA[1] = "";
                strA[2] = "1. 選擇車型車號";
                strA[3] = "2. 無條件轉接";
                strA[4] = "3. 系統重啟";
                strA[5] = "4. SIP電話重啟";
                strA[6] = "5. 自測";
                strA[7] = "6. 資訊顯示";
                strA[8] = "7. 全部重設";
                strA[9] = "";
                strA[10] = "";
                strA[11] = "";
                strA[12] = "數字鍵選擇";
                break;
            case 11:
                strA[0] = "選擇車型   第" + (cla.viewPageCntA[cla.viewPageStack] + 1) + "頁";
                strA[1] = cla.carTypeNameNo;
                int pageCnt = cla.viewPageCntA[cla.viewPageStack];
                int inx = pageCnt * 10;
                for (int i = 0; i < 10; i++) {
                    if (inx >= cla.viewPageStrAA[cla.viewPageStack].size()) {
                        break;
                    }
                    int ii = i + 1;
                    if (ii == 10) {
                        ii = 0;
                    }
                    strA[2 + i] = ii + ". " + cla.viewPageStrAA[cla.viewPageStack].get(inx);
                    inx++;
                }

                strA[12] = "數字鍵選擇";
                break;
            case 111:
                strA[0] = "選擇車號   第" + (cla.viewPageCntA[cla.viewPageStack] + 1) + "頁";
                strA[1] = "";
                pageCnt = cla.viewPageCntA[cla.viewPageStack];
                inx = pageCnt * 10;
                for (int i = 0; i < 10; i++) {
                    if (inx >= cla.viewPageStrAA[cla.viewPageStack].size()) {
                        break;
                    }
                    int ii = i + 1;
                    if (ii == 10) {
                        ii = 0;
                    }
                    strA[2 + i] = ii + ". " + cla.viewPageStrAA[cla.viewPageStack].get(inx);
                    inx++;
                }
                strA[12] = "數字鍵選擇";
                break;
            case 1111:
                str = "儲存";
                if (!cla.bta1[2].getText().equals(str)) {
                    cla.bta1[2].setText(str);
                }
                strA[0] = "資訊";
                viewInf = 2;
                break;

            case 110:
                str = "儲存";
                if (!cla.bta1[2].getText().equals(str)) {
                    cla.bta1[2].setText(str);
                }
                strA[0] = "資訊";
                viewInf = 2;
                break;
            case 16:
                strA[0] = "資訊";
                viewInf = 1;
                break;
        }
        str = strA[0];
        if (!cla.lbaView[0].getText().equals(str)) {
            cla.lbaView[0].setText(str);
        }
        str = strA[1];
        if (!cla.lbaView[1].getText().equals(str)) {
            cla.lbaView[1].setText(str);
        }
        for (int i = 0; i < 10; i++) {
            cl = Color.BLACK;
            str = strA[i + 2];
            if (!cla.btaView[i].getText().equals(str)) {
                cla.btaView[i].setText(str);
            }
            if (cla.btaView[i].getForeground() != cl) {
                cla.btaView[i].setForeground(cl);
            }
        }
        if (cla.scroll.isVisible()) {
            if (viewInf == 0) {
                cla.scroll.setVisible(false);
                cla.pnViewButton.setVisible(true);
            }
        } else {

            if (viewInf == 1) {
                cla.scroll.setVisible(true);
                cla.pnViewButton.setVisible(false);
                cla.tp1.setText("");
                String[] strB = new String[12];
                if (cla.viewPageCarType.length() == 0) {
                    strB[0] = "版本: " + GB.paraSetMap.get("version").toString() + "\n";
                    strB[1] = "車型: " + "---" + "\n";
                    strB[2] = "車號: " + "---" + "\n";
                    strB[3] = "註冊名稱: " + cla.realSipPhoneName + "\n";
                    strB[4] = "註冊號碼: " + cla.realSipPhoneNo + "\n";
                    strB[5] = "本機 IP: " + GB.realIpAddress + "\n";
                    strB[6] = "電話 IP: " + cla.realSipPhoneIp + "\n";
                    strB[7] = "PBX IP: " + cla.realSipServerIp + "\n";
                    strB[8] = "交換器 IP: " + cla.realSwitchIp + "\n";
                    strB[9] = "NTP IP: " + GB.paraSetMap.get("ntpServerAddress").toString() + "\n";
                } else {
                    strB[0] = "版本: " + GB.paraSetMap.get("version").toString() + "\n";
                    strB[1] = "車型: " + cla.viewPageCarType + "\n";
                    strB[2] = "車號: " + cla.viewPageCarNo + "\n";
                    strB[3] = "註冊名稱: " + cla.realSipPhoneName + "\n";
                    strB[4] = "註冊號碼: " + cla.realSipPhoneNo + "\n";
                    strB[5] = "本機 IP: " + GB.realIpAddress + "\n";
                    strB[6] = "電話 IP: " + cla.realSipPhoneIp + "\n";
                    strB[7] = "PBX IP: " + cla.realSipServerIp + "\n";
                    strB[8] = "交換器 IP: " + cla.realSwitchIp + "\n";
                    strB[9] = "NTP IP: " + cla.carSetArray[6] + "\n";
                }
                cla.appendToPane(cla.tp1, strB[0], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[1], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[2], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[3], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[4], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[5], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[6], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[7], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[8], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[9], Color.WHITE);
            }

            if (viewInf == 2) {
                cla.scroll.setVisible(true);
                cla.pnViewButton.setVisible(false);
                cla.tp1.setText("");
                String[] strB = new String[12];
                if (cla.viewPageCarType.length() == 0) {
                    strB[0] = "版本: " + GB.paraSetMap.get("version").toString() + "\n";
                    strB[1] = "車型: " + "---" + "\n";
                    strB[2] = "車號: " + "---" + "\n";
                    strB[3] = "註冊名稱: " + GB.paraSetMap.get("sipName").toString() + "\n";
                    strB[4] = "註冊號碼: " + GB.paraSetMap.get("sipNumber").toString() + "\n";
                    strB[5] = "本機 IP: " + GB.paraSetMap.get("systemIpAddress").toString() + "\n";
                    strB[6] = "電話 IP: " + GB.paraSetMap.get("sipphoneIpAddress").toString() + "\n";
                    strB[7] = "PBX IP: " + GB.paraSetMap.get("sipServerAddress").toString() + "\n";
                    strB[8] = "交換器 IP: " + GB.paraSetMap.get("switchIpAddress").toString() + "\n";
                    strB[9] = "NTP IP: " + GB.paraSetMap.get("ntpServerAddress").toString() + "\n";
                } else {
                    strB[0] = "版本: " + GB.paraSetMap.get("version").toString() + "\n";
                    strB[1] = "車型: " + cla.viewPageCarType + "\n";
                    strB[2] = "車號: " + cla.viewPageCarNo + "\n";
                    strB[3] = "註冊名稱: " + cla.carSetArray[0] + "\n";
                    strB[4] = "註冊號碼: " + cla.carSetArray[1] + "\n";
                    strB[5] = "本機 IP: " + cla.carSetArray[2] + "\n";
                    strB[6] = "電話 IP: " + cla.carSetArray[3] + "\n";
                    strB[7] = "PBX IP: " + cla.carSetArray[4] + "\n";
                    strB[8] = "交換器 IP: " + cla.carSetArray[5] + "\n";
                    strB[9] = "NTP IP: " + cla.carSetArray[6] + "\n";
                }
                cla.appendToPane(cla.tp1, strB[0], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[1], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[2], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[3], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[4], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[5], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[6], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[7], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[8], Color.WHITE);
                cla.appendToPane(cla.tp1, strB[9], Color.WHITE);
            }
        }

        if (cla.reboot_f == 1) {
            cla.reboot_step++;

            switch (cla.reboot_step) {
                case 10:
                    //cla.shellCommand = "sudo reboot \n";
                    break;
                case 20:
                    cla.txpackStr(cla.trxPack0, 0, "\n");
                    break;
                case 30:
                    cla.txpackStr(cla.trxPack0, 0, "enable\n");
                    break;
                case 200:
                    cla.txpackStr(cla.trxPack0, 0, "\n");
                    break;
                case 210:
                    cla.txpackStr(cla.trxPack0, 0, "reload\n");
                    break;
                case 250:
                    if (GB.os_inx == 1) {
                        Lib.exe("sudo reboot");
                    }
                    cla.reboot_f = 0;
                    break;
            }

        }

        if (cla.reboot_f == 2) {
            cla.reboot_step++;
            switch (cla.reboot_step) {
                case 3:
                    cla.shellCommand = "sudo reboot \n";
                    cla.reboot_f = 0;
                    break;
            }

        }

    }

//==============================================================
}

//for command
//========================================================
class Phone6in1Tm2 implements ActionListener {

    String str;
    Phone6in1 cla;
    int debug = 0;
    int debug1 = 0;

    Phone6in1Tm2(Phone6in1 owner) {
        cla = owner;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        debug++;
        if (debug == 50) {
            //cla.lbTitle.setText("Timer2 X 50 : " + debug1++);
            debug = 0;
        }
        if (cla.cmdin_f != 0) {
            if (cla.cmdin_f == 1) {
                cla.inxCommand(cla.cmdin_inx);  //mouse index in
            }
            if (cla.cmdin_f == 2) {
                cla.strCommand(cla.cmdin_str);
            }
            cla.cmdin_f = 0;
        }

    }
}

class TrxPack {

    int lenLim = 2000;
    int format = 0xf0;
    int amt = 0;
    int idBase = 0;
    int[] txLen;
    byte[][] txData;
    int txDataPt = 0;
    int nowPack = 0;

    TrxPack(int _amt, int _idBase) {
        amt = _amt;
        idBase = idBase;
        txLen = new int[amt];
        txData = new byte[amt][];
        for (int i = 0; i < amt; i++) {
            txData[i] = new byte[4096];
        }
    }

    void loadStart() {
        txDataPt = 0;
    }

    void setTxDataPt(int pt) {
        txDataPt = pt;
    }

    public void loadWord(int ib) {
        txData[nowPack][txDataPt++] = (byte) (ib & 255);
        txData[nowPack][txDataPt++] = (byte) ((ib >> 8) & 255);

    }

    public void loadInt(int ib) {
        txData[nowPack][txDataPt++] = (byte) (ib & 255);
        txData[nowPack][txDataPt++] = (byte) ((ib >> 8) & 255);
        txData[nowPack][txDataPt++] = (byte) ((ib >> 16) & 255);
        txData[nowPack][txDataPt++] = (byte) ((ib >> 24) & 255);
    }

    public void loadByte(int ib) {
        txData[nowPack][txDataPt++] = (byte) (ib & 255);
    }

}

class PingTd extends Thread {

    Phone6in1 cla;
    int dis_connect_tim = 0;

    PingTd(Phone6in1 owner) {
        cla = owner;
    }

    @Override
    public void run() { // override Thread's run()
        //Test cla=Test.thisCla;
        for (;;) {
            if (cla.pingTd_run_f == 1) {
                //==========================
                int ibuf;
                //String sipIpAddr = GB.paraSetMap.get("sipphoneIpAddress").toString();
                String sipIpAddr = cla.realSipPhoneIp;
                ibuf = Lib.ping(sipIpAddr);
                if (ibuf == 0) { //ok
                    cla.sipmd_ping_f = 1;
                    cla.sipmd_ping_cnt = 0;
                } else {
                    if (++cla.sipmd_ping_cnt >= 2) {
                        cla.sipmd_ping_f = 0;
                    }
                }
                //==========================
                for (;;) {
                    String switchIp;
                    if (cla.switch_rxed_f == 1) {
                        switchIp = cla.realSwitchIp;
                    } else {
                        Object obj = GB.paraSetMap.get("switchIpAddress");
                        if (obj == null) {
                            break;
                        }
                        switchIp = GB.paraSetMap.get("switchIpAddress").toString();
                    }
                    String[] ipA = switchIp.split("\\.");
                    if (ipA.length == 4) {
                        ibuf = Lib.ping(switchIp);
                        if (ibuf == 0) { //ok
                            cla.switch_ping_f = 1;
                            cla.switch_ping_cnt = 0;
                        } else {
                            if (++cla.switch_ping_cnt >= 2) {
                                cla.switch_ping_f = 0;
                            }
                        }
                    } else {
                        if (++cla.switch_ping_cnt >= 2) {
                            cla.switch_ping_f = 0;

                        }
                    }
                    break;
                }
                //==========================
                Lib.thSleep(200);
                if (cla.pingTd_destroy_f == 1) {
                    break;
                }
            }
        }
    }
}

class PingServer extends Thread {

    Phone6in1 cla;
    int dis_connect_tim = 0;

    PingServer(Phone6in1 owner) {
        cla = owner;
    }

    @Override
    public void run() { // override Thread's run()
        //Test cla=Test.thisCla;
        for (;;) {
            if (cla.pingServer_run_f == 1) {
                //==========================
                int ibuf = Lib.ping(cla.realSipServerIp);
                if (ibuf == 0) { //ok
                    cla.sipServer_ping_f = 1;
                    cla.sipServer_ping_cnt = 0;
                } else {
                    if (++cla.sipServer_ping_cnt >= 2) {
                        if (cla.sipServer_ping_f == 1) {
                            cla.infPanelCnt = 2;
                            cla.setInfPanel();
                        }
                        cla.sipServer_ping_f = 0;
                    }
                }
                //==========================
                Lib.thSleep(200);
                if (cla.pingServer_destroy_f == 1) {
                    break;
                }
            }
        }
    }
}
