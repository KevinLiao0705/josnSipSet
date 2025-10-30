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
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;

public class Phone6in1 extends javax.swing.JDialog {

    String change_switch_ip_str = "";
    int change_switch_ip_step = 0;
    int viewDebugPanel_f = 1;
    int viewDialPanel_f = 0;
    int preConfiguration = 0;
    int winW = 800;
    int winH = 480;
    int switch_led_flag;
    int debug_f = 1;
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

    int s0p0_txnone_tim = 0;
    int switch_rxed_f = 0;
    int switch_rxed_tim = 0;
    int displayShow_f = 0;

    int ledflag, keyflag, keypush;
    int ssksip_tx_tim;
    //===============================
    int sipStatus = 0;         //0 no raspberryPi,1:raspberry pi ready,2:linphonec load,3:pbx registed,4:on call 

    int connected_cnt = 0;
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
    byte[] uiCommand = new byte[64];
    ;
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
    int view_page = 1;
    int view_page_cnt = 0;
    //int view_page_cntlm = 1;

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
    JPanel pnLeft, pnRight, pnLcd, pnKeypad, pnDebug, pnInf, pnInput, pnView;
    JButton[] bta1 = new JButton[4];
    JButton[] bta2 = new JButton[2];
    JButton[] bta3 = new JButton[28];
    JButton[] bta4 = new JButton[4];
    JLabel[] lba1 = new JLabel[6];
    JButton[] btaView = new JButton[8];
    JLabel[] lbaView = new JLabel[3];

    //static MyLayout ly=new MyLayout();
    public Phone6in1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        Phone6in1.scla = this;
        Phone6in1 cla = this;
        cla.setBounds(-100, -100, 0, 0);
    }

    public void create() {
        int i;
        final Phone6in1 cla = this;
        String str;
        Font myFont;

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
        lbTitle.setFont(myFont);
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

        //===============================================
        tfLcd1 = new JTextField(); //lcd first line
        tfLcd1.setText("");
        tfLcd1.setName(Integer.toString(99 * 256 + 0));
        tfLcd1.setMargin(new Insets(0, 10, 0, 10));
        tfLcd1.setBackground(Color.YELLOW);
        tfLcd1.setFont(new Font("Serif", Font.BOLD, 32));
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
            pnRight.add(bta1[i]);
        }
        //=======================
        bta1[0].setText(GB.hotline_nameA[0]);
        bta1[1].setText(GB.hotline_nameA[1]);
        bta1[2].setText(GB.hotline_nameA[2]);
        bta1[3].setText(GB.hotline_nameA[3]);
        //bta1[3].setIcon(imgSet);

        for (i = 0; i < bta2.length; i++) {
            bta2[i] = new JButton();
            bta2[i].setFont(myFont);
            bta2[i].setName(Integer.toString(2 * 256 + i));
            bta2[i].addMouseListener(mslis);
            bta2[i].setVisible(true);
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
            pnInf.add(lba1[i]);
        }
        //=======================
        lba1[0].setText("  使用者");
        lba1[2].setText("  SIP電話IP");
        lba1[4].setText("  ICS IP");

        for (i = 0; i < btaView.length; i++) {
            btaView[i] = new JButton();
            btaView[i].setFont(new Font("Serif", Font.BOLD, 32));
            btaView[i].setName(Integer.toString(6 * 256 + i));
            btaView[i].addMouseListener(mslis);
            btaView[i].setHorizontalAlignment(JButton.LEFT);
            pnView.add(btaView[i]);
        }
        for (i = 0; i < lbaView.length; i++) {
            lbaView[i] = new JLabel();
            lbaView[i].setFont(new Font("Serif", Font.BOLD, 32));
            lbaView[i].addMouseListener(mslis);
            pnView.add(lbaView[i]);
        }
        lbaView[1].setHorizontalAlignment(JLabel.RIGHT);
        lbaView[2].setText("數字鍵選擇");
        //=======================

        if (viewDialPanel_f == 0) {
            pnKeypad.setVisible(false);
            pnInf.setVisible(true);
        } else {
            pnKeypad.setVisible(true);
            pnInf.setVisible(false);
        }

        //bta3[3 * i + 5].setBackground(Color.yellow);
        myFont = new Font("Serif", Font.BOLD, 24);
        for (i = 0; i < bta4.length; i++) {
            bta4[i] = new JButton();
            bta4[i].setFont(myFont);
            bta4[i].setName(Integer.toString(4 * 256 + i));
            bta4[i].setBackground(Color.LIGHT_GRAY);
            bta4[i].addMouseListener(mslis);
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
        trxPack0 = new TrxPack();
        trxPack0.amt = 4;
        trxPack0.txData = new byte[trxPack0.amt][];
        for (i = 0; i < trxPack0.amt; i++) {
            trxPack0.txData[i] = new byte[4096];
        }
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
                cla.siprx_byip(format);
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
        str = "4. 返回";
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

        if (cla.tm1 == null) {      //for all sip command by ip & display
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

    }

    int sipIp_rxed_f = 0;
    int sipIp_rxed_tim = 0;

    void siprx_byip(int format) {
        Phone6in1 cla = this;
        cla.sskip_sip.datain_f = 0;
        cla.sskip_sip.connect_f = 1;
        siprx_dec(cla.sskip_sip.inbuf, 0, cla.sskip_sip.inbuf_len);
        sipIp_rxed_f = 1;
        sipIp_rxed_tim = 0;
    }

    int sipInf_rxed_f = 0;
    int sipInf_rxed_tim = 0;

    void siprx_dec(byte[] bytes_in, int stInx, int sipinf_len) {
        int i, j, k;
        Phone6in1 cla = this;
        String str;
        int inx = stInx;
        int endInx = inx + sipinf_len;
        int cmdinx;
        int cmdlen;
        int cmd;
        byte[] bytes;
        if (bytes_in[inx++] != (byte) GB.sipmd_device_id) {
            return;
        }
        while (inx < endInx - 3) {
            cmd = bytes_in[inx];
            cmdlen = bytes_in[inx + 1];
            cmdinx = inx + 2;
            switch (cmd) {
                case 0x10://status flag
                    sipInf_rxed_f = 1;
                    sipInf_rxed_tim = 0;
                    cla.sipStatus = bytes_in[cmdinx++];
                    cla.connected_cnt = bytes_in[cmdinx++];
                    cla.handStatus = bytes_in[cmdinx++];
                    cla.earPhone_volume = bytes_in[cmdinx++];
                    cla.speaker_volume = bytes_in[cmdinx++];
                    cla.ear_mic_sen = bytes_in[cmdinx++];
                    cla.phset_mic_sen = bytes_in[cmdinx++];
                    cla.sipflag[0] = bytes_in[cmdinx++];
                    cla.sipflag[1] = bytes_in[cmdinx++];
                    cla.sipflag[2] = bytes_in[cmdinx++];
                    if ((cla.sipflag[0] & 0x01) != 0) {
                        mute_f = 1;
                    } else {
                        mute_f = 0;
                    }
                    break;
                case 0x11://status_str
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    cla.status_str = new String(bytes);
                    break;
                case 0x12://action_str
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    cla.action_str = new String(bytes);
                    break;
                case 0x13://callto
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    cla.callto = new String(bytes);
                    break;
                case 0x14://callfrom
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    cla.callfrom = new String(bytes);
                    break;
                case 0x15://sip_phone_address
                    String sip_ip;
                    String sip_ipmask;
                    String sip_gateway;

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
                    GB.sipmd_ip_str = sip_ip;
                    GB.sipmd_ui_ip = sip_ip;
                    GB.sipmd_ipmask_str = sip_ipmask;
                    GB.sipmd_gateway_str = sip_gateway;

                    break;

                case 0x16://sip_phone_name
                    String phoneName = "";
                    for (i = 0; i < cmdlen; i++) {
                        phoneName += (char) bytes_in[cmdinx++];
                    }
                    GB.phone_name = phoneName;
                    break;
                case 0x17://sip_phone_no
                    String phoneNo = "";
                    for (i = 0; i < cmdlen; i++) {
                        phoneNo += (char) bytes_in[cmdinx++];
                    }
                    GB.phone_no = phoneNo;
                    break;
                case 0x18://pbx address
                    String pbxAddress = "";
                    for (i = 0; i < cmdlen; i++) {
                        pbxAddress += (char) bytes_in[cmdinx++];
                    }
                    GB.sip_server_ip = pbxAddress;
                    break;
                case 0x20://hotline1_name
                case 0x21://hotline2_name
                case 0x22://hotline3_name
                case 0x23://hotline4_name
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bytes_in[cmdinx++];
                    }
                    String hotName = "";
                    //hotName = new String(bytes, "Cp1251");
                    hotName = new String(bytes, Charset.forName("UTF-8"));
                    /*
                    for (i = 0; i < cmdlen; i++) {
                        hotName += (char) bytes_in[cmdinx++];
                    }
                     */
                    switch (cmd) {
                        case 0x20:
                            GB.hotline_nameA[0] = hotName;
                            break;
                        case 0x21:
                            GB.hotline_nameA[1] = hotName;
                            break;
                        case 0x22:
                            GB.hotline_nameA[2] = hotName;
                            break;
                        case 0x23:
                            GB.hotline_nameA[3] = hotName;
                            break;
                    }
                    break;

                case 0x30://hotline1_no
                case 0x31://hotline2_no
                case 0x32://hotline3_no
                case 0x33://hotline4_no
                    String hotNo = "";
                    for (i = 0; i < cmdlen; i++) {
                        hotNo += (char) bytes_in[cmdinx++];
                    }
                    switch (cmd) {
                        case 0x30:
                            GB.hotline_noA[0] = hotNo;
                            break;
                        case 0x31:
                            GB.hotline_noA[1] = hotNo;
                            break;
                        case 0x32:
                            GB.hotline_noA[2] = hotNo;
                            break;
                        case 0x33:
                            GB.hotline_noA[3] = hotNo;
                            break;
                    }
                    break;
                case 0x40://version
                case 0x41://web_password
                    String strTemp = "";
                    for (i = 0; i < cmdlen; i++) {
                        strTemp += (char) bytes_in[cmdinx++];
                    }
                    switch (cmd) {
                        case 0x40:
                            GB.sipVersion = strTemp;
                            break;
                        case 0x41:
                            GB.web_password = strTemp;
                            break;
                    }
                    break;

                default:
                    cmdlen = 9999;
                    break;

            }
            inx = inx + cmdlen + 2;
        }

    }

    void decS0p0(byte[] rdata, int stInx, int len) {
        Phone6in1 cla = this;
        int i, j;
        String str;
//        if (cla.reboot_f == 1) {
//            return;
//        }
        if (len > 0) {
            //System.out.println("" + cla.sskio0.inbuf_len);
            byte[] bytes = new byte[len];
            for (i = 0; i < len; i++) {
                bytes[i] = rdata[i + stInx];
            }
            str = new String(bytes);
            System.out.println(str);

            String[] strA;
            String[] strB;
            int du_f;
            strA = str.split("\n");
            for (j = 0; j < strA.length; j++) {
                strB = strA[j].split("\\s+");
                if (strB.length != 4) {
                    continue;
                }
                du_f = 2;
                if (strB[3].equals("up")) {
                    du_f = 1;
                }
                if (strB[3].equals("down")) {
                    du_f = 0;
                }
                if (du_f == 2) {
                    continue;
                }
                if (strB[0].equals("vlan1.1")) {
                    //cla.switchIp = strB[1];
                    GB.switch_ip_str = strB[1];
                }
                if (strB[0].charAt(0) != 'f') {
                    continue;
                }
                if (strB[0].charAt(1) != 'e') {
                    continue;
                }
                if (strB[0].charAt(2) < '1') {
                    continue;
                }
                if (strB[0].charAt(2) > '8') {
                    continue;
                }
                int inx = strB[0].charAt(2) - '1';
                cla.switchFlag[inx] = du_f;
            }
            s0p0_txnone_tim = 0;

            /*
            strA = str.split("\n");
            for (j = 0; j < strA.length; j++) {
                if (strA[j].contains("Username:")) {
                    //txret_switch(cla.sskio0, "root");
                    txpackStr(trxPack0, 0, "root");
                    switch_rxed_f = 1;
                    switch_rxed_tim = 0;
                    break;
                }

                if (strA[j].contains("login:")) {
                    //txret_switch(cla.sskio0, "root");
                    txpackStr(trxPack0, 0, "root");
                    switch_rxed_f = 1;
                    switch_rxed_tim = 0;
                    break;
                }

                if (strA[j].contains("Password: ")) {
                    //txret_switch(cla.sskio0, "Airmoni:22991660");
                    cla.switchPassw_str = "Airmoni:22991660";

                    //cla.switchPassw_str+=(char)0x1d;
                    //cla.switchPassw_str+="[24;80R";
                    cla.switchPassw_inx = 0;

                    //txpackStr(trxPack0, 0, cla.switchPassw_str);
                    //txpackStr(trxPack0, 0, "Airmoni:");
                    switch_rxed_f = 1;
                    switch_rxed_tim = 0;
                    break;
                }
                if (strA[j].contains("switch_a>")) {
                    //txret_switch(cla.sskio0, "show ip interface brief");
                    if (cla.change_switch_ip_str.equals("")) {
                        txpackStr(trxPack0, 0, "show ip interface brief");
                        switch_rxed_f = 1;
                        switch_rxed_tim = 0;
                    }
                    break;
                }
            }

             */
            while (true) {

                if (str.contains("Username:")) {
                    //txret_switch(cla.sskio0, "root");
                    txpackStr(trxPack0, 0, "root");
                    switch_rxed_f = 1;
                    switch_rxed_tim = 0;
                    easy_password_f = 0;
                    break;
                }
                if (str.contains("login:")) {
                    //txret_switch(cla.sskio0, "root");
                    txpackStr(trxPack0, 0, "root\n");
                    switch_rxed_f = 1;
                    switch_rxed_tim = 0;
                    easy_password_f = 1;
                    break;
                }

                if (str.contains("Password:")) {
                    if (easy_password_f == 0) {
                        cla.switchPassw_str = "Airmoni:22991660\n";
                        txpackStr(trxPack0, 0, cla.switchPassw_str);
                        cla.switchPassw_str = "";
                    } else {
                        cla.switchPassw_str = "Airmoni:22991660\n";
                        cla.switchPassw_inx = 0;
                    }
                    switch_rxed_f = 1;
                    switch_rxed_tim = 0;
                    break;
                }

                if (cla.reboot_f == 1) {
                    return;
                }

                if (str.contains("switch_a>")) {
                    //txret_switch(cla.sskio0, "show ip interface brief");
                    if (cla.change_switch_ip_str.equals("")) {
                        txpackStr(trxPack0, 0, "show ip interface brief");
                        switch_rxed_f = 1;
                        switch_rxed_tim = 0;
                    }
                    break;
                }
                if (str.contains("switch_a#")) {
                    //txret_switch(cla.sskio0, "show ip interface brief");
                    if (cla.change_switch_ip_str.equals("")) {
                        txpackStr(trxPack0, 0, "q\n");
                        //txpackStr(trxPack0, 0, "show ip interface brief");
                        //switch_rxed_f = 1;
                        switch_rxed_tim = 0;
                    }
                    break;
                }

                break;

            }

        } else {

            if (switch_rxed_f == 1) {
                if (!cla.change_switch_ip_str.equals("")) {
                    switch_rxed_tim = 0;
                    if (++s0p0_txnone_tim > 80) {
                        s0p0_txnone_tim = 0;
                        switch (cla.change_switch_ip_step) {
                            case 0:
                                txpackStr(trxPack0, 0, "\n");
                                break;
                            case 1:
                                txpackStr(trxPack0, 0, "enable\n");
                                break;
                            case 2:
                                txpackStr(trxPack0, 0, "configure terminal\n");
                                break;
                            case 3:
                                String ipStr = "ip address " + cla.change_switch_ip_str + "/";
                                if (GB.switch_ipmask_str.equals("255.255.255.0")) {
                                    ipStr += "24\n";
                                } else {
                                    ipStr += "16\n";
                                }
                                txpackStr(trxPack0, 0, ipStr);
                                break;
                            case 4:
                                txpackStr(trxPack0, 0, "q\n");
                                break;
                            case 5:
                                txpackStr(trxPack0, 0, "write memory\n");
                                break;
                            case 6:
                                break;
                            case 7:
                                txpackStr(trxPack0, 0, "q\n");
                                cla.change_switch_ip_str = "";
                                break;
                            default:
                                cla.change_switch_ip_str = "";
                                break;
                        }
                        cla.change_switch_ip_step++;
                    }
                    return;

                }
            }

            if (!cla.switchPassw_str.equals("")) {
                cla.debug_key_f = 0;
                if (++s0p0_txnone_tim > 5) {
                    s0p0_txnone_tim = 0;
                    if (cla.switchPassw_inx >= cla.switchPassw_str.length()) {
                        cla.switchPassw_str = "";
                        byte[] tt = new byte[100];
                        int inx = 0;
                        tt[inx++] = 0x1b;
                        tt[inx++] = '[';
                        tt[inx++] = '2';
                        tt[inx++] = '4';
                        tt[inx++] = ';';
                        tt[inx++] = '8';
                        tt[inx++] = '0';
                        tt[inx++] = 'R';
                        txpackBytes(trxPack0, 0, tt, inx);
                        s0p0_txnone_tim = -2000;
                        return;
                    }
                    String strb = "" + cla.switchPassw_str.charAt(cla.switchPassw_inx);
                    txpackStr(trxPack0, 0, strb);
                    cla.switchPassw_inx++;
                    System.out.println(strb);
                }
                return;
            }
            if (cla.reboot_f == 1) {
                return;
            }
            if (++s0p0_txnone_tim > 100) {
                s0p0_txnone_tim = 0;
                if (cla.change_switch_ip_str.equals("")) {
                    txpackStr(trxPack0, 0, "\n");
                }
            }
            debug_cnt = s0p0_txnone_tim;
        }
    }

    void txretKeypad(TrxPack trxp, int pInx) {
        int i;
        byte[] txdata = new byte[20];
        int inx = 0;
        Phone6in1 cla = this;
        switch_led_flag = 0;
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
        //switch_led_flag=0x0000;
        //switch_led_flag bit0-15 =gygygygygygygygy 
        txdata[inx++] = (byte) GB.sipui_device_id;
        txdata[inx++] = (byte) 0x18;//function id
        txdata[inx++] = 1; //0 cpl led 1:set led 2:inc backLight
        txdata[inx++] = (byte) ((switch_led_flag >> 0) & 255);
        txdata[inx++] = (byte) ((switch_led_flag >> 8) & 255);
        txdata[inx++] = (byte) ((handStatus << 4) + sipStatus);
        txdata[inx++] = (byte) (connected_cnt);
        txdata[inx++] = cla.sipflag[0];

        txpackBytes(trxp, pInx, txdata, inx);
    }

    void txretSip(TrxPack trxp, int pInx) {     //
        int i;
        byte[] txdata = new byte[4096];
        int inx = 0;
        Phone6in1 cla = this;
        byte[] bytes;

        txdata[inx++] = (byte) GB.sipui_device_id;
        txdata[inx++] = (byte) 0x10;//fid:Get status
        txdata[inx++] = (byte) 0x00;//fid:len
        //==============================================
        if (cla.uiCommand_len != 0) {                  //fid=0x13 
            for (i = 0; i < cla.uiCommand_len; i++) {
                txdata[inx++] = cla.uiCommand[i];
            }
            cla.uiCommand_len = 0;
        }
        //===============================================
        if (!cla.sipCommand.equals("")) {
            txdata[inx++] = (byte) 0x11;//fid:direct linphone command
            txdata[inx++] = (byte) cla.sipCommand.length();
            bytes = cla.sipCommand.getBytes();
            for (i = 0; i < bytes.length; i++) {
                txdata[inx++] = bytes[i];
            }
            cla.sipCommand = "";
        }
        //==============================================
        if (!cla.shellCommand.equals("")) {
            txdata[inx++] = (byte) 0x12;//fid:direct shell command
            txdata[inx++] = (byte) cla.shellCommand.length();
            bytes = cla.shellCommand.getBytes();
            for (i = 0; i < bytes.length; i++) {
                txdata[inx++] = bytes[i];
            }
            cla.shellCommand = "";
        }
        //==============================================
        txpackBytes(trxp, pInx, txdata, inx);
    }

    int sipUart_rxed_f = 0;
    int sipUart_rxed_tim = 0;
    int keypad_rxed_f = 0;
    int keypad_rxed_tim = 0;

    void decS0p1(byte[] rdata, int stInx, int len) {
        Phone6in1 cla = this;
        String str;
        if (len == 0) {
            return;
        }
        if (rdata[stInx + 0] == (byte) 0x50) //key pad information
        {
            txretKeypad(trxPack0, 1);
            if (rdata[stInx + 1] == 0x18) {
                keyflag = rdata[stInx + 2];
                ledflag = rdata[stInx + 4] * 256 + rdata[stInx + 3];
                dec_keypad();
                keypad_rxed_f = 1;
                keypad_rxed_tim = 0;
                //========================================================
            }
        }
        if (rdata[stInx + 0] == (byte) GB.sipmd_device_id) //sipmd information
        {
            txretSip(trxPack0, 1);
            siprx_dec(rdata, stInx, len);
            siptx_byuart_stop_tim = 0;
            sipUart_rxed_f = 1;
            sipUart_rxed_tim = 0;
        }

    }

    void decS0p2(byte[] rdata, int stInx, int len) {
        Phone6in1 cla = this;
        String str;
        if (len == 0) {
            return;
        }
        if (rdata[stInx + 0] == (byte) 0x50) //key pad information
        {
            txretKeypad(trxPack0, 2);
            if (rdata[stInx + 1] == 0x18) {
                keyflag = rdata[stInx + 2];
                ledflag = rdata[stInx + 4] * 256 + rdata[stInx + 3];
                dec_keypad();
                keypad_rxed_f = 1;
                keypad_rxed_tim = 0;
                //========================================================
            }
        }
        if (rdata[stInx + 0] == (byte) GB.sipmd_device_id) //sipmd information
        {
            txretSip(trxPack0, 2);
            siprx_dec(rdata, stInx, len);
            siptx_byuart_stop_tim = 0;
            sipUart_rxed_f = 1;
            sipUart_rxed_tim = 0;
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

    void sskio0Rx(int format) {
        Phone6in1 cla = this;
        String str;
        int i, j, k;
        byte packId;
        int packLen;
        int packStart;
        int chks0;
        int chks1;
        sock0_rxed_f = 1;
        sock0_rxed_tim = 0;
        cla.sskio0.datain_f = 0;
        cla.sskio0.connect_f = 1;
        byte[] rdata = cla.sskio0.stm.rdata;
        int dataInx = cla.sskio0.stm.rxInx;
        int dataLen = cla.sskio0.stm.rxlen;

        if (rdata[dataInx++] == (byte) 0xf0) {

            while (true) {
                packId = rdata[dataInx++];
                packLen = rdata[dataInx++];
                packLen += rdata[dataInx++] * 256;
                packStart = dataInx;
                chks0 = 0xab;
                chks1 = 0x00;
                for (i = 0; i < packLen; i++) {
                    chks0 ^= rdata[dataInx];
                    chks1 += rdata[dataInx];
                    dataInx++;
                }
                if (((chks0 ^ rdata[dataInx++]) & 0xff) != 0) {
                    break;
                }
                if (((chks1 ^ rdata[dataInx++]) & 0xff) != 0) {
                    break;
                }
                switch (packId) {
                    case 0x10:
                        sock0p0_rxed_f = 1;
                        sock0p0_rxed_tim = 0;
                        decS0p0(rdata, packStart, packLen);
                        break;
                    case 0x11:
                        sock0p1_rxed_f = 1;
                        sock0p1_rxed_tim = 0;
                        decS0p1(rdata, packStart, packLen);
                        break;
                    case 0x12:
                        sock0p2_rxed_f = 1;
                        sock0p2_rxed_tim = 0;
                        decS0p2(rdata, packStart, packLen);
                        break;
                }
                if (dataInx < dataLen) {
                    continue;
                }

                break;
            }
        }
        txret_sock(cla.sskio0, cla.trxPack0);
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
                ssk.stm.tbuf[stx_index++] = trxp.id[i];
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
        ssk.stm.tbuf[stx_index++] = (byte) GB.sipui_device_id;
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

    void txret_ssksip_cmd(Ssocket ssk) {
        Phone6in1 cla = this;
        byte[] bytes;
        int i;
        tx_ssksipCmd(ssk);
        /*
        int stx_index = 0;
        ssk.stm.tbuf[stx_index++] = (byte) cla.device_id;
        ssk.stm.tbuf[stx_index++] = (byte) 0x18;//function id
        ssk.stm.tbuf[stx_index++] = 2;
        ssk.stm.tbuf[stx_index++] = (byte) ((switch_led_flag >> 0) & 255);
        ssk.stm.tbuf[stx_index++] = (byte) ((switch_led_flag >> 8) & 255);
        ssk.stm.tbuf_byte = stx_index;
        ssk.stm.enc_mystm();
         */
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
                        keyStr = "f1";
                        break;
                    case 4://f2
                        keyStr = "f2";
                        break;
                    case 8://f3
                        keyStr = "f3";
                        break;
                    case 12://f4
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
                        keyStr = "transfer";
                        break;

                }

                if (cla.password_on_f == 1) {
                    cla.inp1.cmdin_str = keyStr;
                    cla.inp1.cmdin_f = 2;
                    return;
                }

                if (cla.selfTest_on_f == 1) {
                    if (cla.stest1.cmdin_f != 0) {
                        return;
                    }
                    switch (keyStr) {
                        case "0"://0
                            cla.stest1.cmdin_str = "ok";
                            cla.stest1.cmdin_f = 2;
                            break;
                        case "ok"://ok 
                            cla.stest1.cmdin_str = "ok";
                            cla.stest1.cmdin_f = 2;
                            break;
                        case "1"://1 
                            cla.stest1.cmdin_str = "pageUp";
                            cla.stest1.cmdin_f = 2;
                            break;
                        case "2"://2 
                            cla.stest1.cmdin_str = "pageDown";
                            cla.stest1.cmdin_f = 2;
                            break;
                    }
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
        cla.pnInput.setLayout(null);
        cla.pnView.setLayout(null);

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
        MyLayout.gridLy();

        System.arraycopy(cla.lbaView, 0, MyLayout.ctrA, 0, 2);
        MyLayout.eleAmt = 2;
        MyLayout.xc = 2;
        MyLayout.yc = 1;
        MyLayout.rateH = 0.08;
        MyLayout.gridLy();

        MyLayout.yst = MyLayout.yend;
        System.arraycopy(cla.btaView, 0, MyLayout.ctrA, 0, cla.btaView.length);
        MyLayout.eleAmt = cla.btaView.length;
        MyLayout.xc = 2;
        MyLayout.yc = cla.btaView.length / 2;
        MyLayout.rateH = 0.9;
        MyLayout.gridLy();

        MyLayout.yst = MyLayout.yend;
        System.arraycopy(cla.lbaView, 2, MyLayout.ctrA, 0, 1);
        MyLayout.eleAmt = 1;
        MyLayout.xc = 1;
        MyLayout.yc = 1;
        MyLayout.rateH = 1;
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
        mListTmp.mdataList.get(0).mlist.mdataList.get(0).obj = GB.sipui_ip_str;
        mListTmp.mdataList.get(0).mlist.mdataList.get(1).obj = GB.sipui_ipmask_str;
        mListTmp.mdataList.get(0).mlist.mdataList.get(2).obj = GB.sipui_gateway_str;
        mListTmp.mdataList.get(1).mlist.mdataList.get(0).obj = GB.sipmd_ip_str;
        mListTmp.mdataList.get(1).mlist.mdataList.get(1).obj = GB.sipmd_ipmask_str;
        mListTmp.mdataList.get(1).mlist.mdataList.get(2).obj = GB.sipmd_gateway_str;
        mListTmp.mdataList.get(2).mlist.mdataList.get(0).obj = GB.switch_ip_str;
        mListTmp.mdataList.get(2).mlist.mdataList.get(1).obj = GB.switch_ipmask_str;
        mListTmp.mdataList.get(2).mlist.mdataList.get(2).obj = GB.switch_gateway_str;
        mListTmp.mdataList.get(3).mlist.mdataList.get(0).obj = GB.phone_no;
        mListTmp.mdataList.get(3).mlist.mdataList.get(1).obj = GB.sip_server_pin;
        mListTmp.mdataList.get(3).mlist.mdataList.get(2).obj = GB.sip_server_ip;

        cla.menu1.onShow();
        cla.menu_on_f = 1;
        cla.menu1.setVisible(true);
        cla.menu_on_f = 0;

        int setf;
        inx = 0;
        //======================================================================================
        setf = 0;
        if (!mListTmp.mdataList.get(3).mlist.mdataList.get(0).obj.equals(GB.phone_no)) {
            setf = 1;
        }
        if (!mListTmp.mdataList.get(3).mlist.mdataList.get(1).obj.equals(GB.sip_server_pin)) {
            setf = 1;
        }
        if (!mListTmp.mdataList.get(3).mlist.mdataList.get(2).obj.equals(GB.sip_server_ip)) {
            setf = 1;
        }
        if (setf == 1) {
            GB.phone_no = (String) mListTmp.mdataList.get(3).mlist.mdataList.get(0).obj;
            GB.sip_server_pin = (String) mListTmp.mdataList.get(3).mlist.mdataList.get(1).obj;
            GB.sip_server_ip = (String) mListTmp.mdataList.get(3).mlist.mdataList.get(2).obj;

            bytes = GB.phone_no.getBytes();
            cla.uiCommand[inx++] = (byte) 0x15;
            cla.uiCommand[inx++] = (byte) bytes.length;
            for (int i = 0; i < bytes.length; i++) {
                cla.uiCommand[inx++] = bytes[i];
            }
            //
            bytes = GB.sip_server_pin.getBytes();
            cla.uiCommand[inx++] = (byte) 0x16;
            cla.uiCommand[inx++] = (byte) bytes.length;
            for (int i = 0; i < bytes.length; i++) {
                cla.uiCommand[inx++] = bytes[i];
            }
            //
            bytes = GB.sip_server_ip.getBytes();
            cla.uiCommand[inx++] = (byte) 0x17;
            cla.uiCommand[inx++] = (byte) bytes.length;
            for (int i = 0; i < bytes.length; i++) {
                cla.uiCommand[inx++] = bytes[i];
            }
            //
            bytes = "pbxReg".getBytes();
            cla.uiCommand[inx++] = (byte) 0x18;
            cla.uiCommand[inx++] = (byte) bytes.length;
            for (int i = 0; i < bytes.length; i++) {
                cla.uiCommand[inx++] = bytes[i];
            }
            //
        }
        //======================================================================================
        setf = 0;
        if (!mListTmp.mdataList.get(2).mlist.mdataList.get(0).obj.equals(GB.switch_ip_str)) {
            setf = 1;
        }
        if (!mListTmp.mdataList.get(2).mlist.mdataList.get(1).obj.equals(GB.switch_ipmask_str)) {
            setf = 1;
        }
        if (!mListTmp.mdataList.get(2).mlist.mdataList.get(2).obj.equals(GB.switch_gateway_str)) {
            setf = 1;
        }
        if (setf == 1) {
            GB.switch_ip_str = (String) mListTmp.mdataList.get(2).mlist.mdataList.get(0).obj;
            GB.switch_ipmask_str = (String) mListTmp.mdataList.get(2).mlist.mdataList.get(1).obj;
            GB.switch_gateway_str = (String) mListTmp.mdataList.get(2).mlist.mdataList.get(2).obj;
            cla.change_switch_ip_str = GB.switch_ip_str;
            cla.change_switch_ip_step = 0;
        }
        //=======================================================================================
        setf = 0;
        if (!mListTmp.mdataList.get(0).mlist.mdataList.get(0).obj.equals(GB.sipui_ip_str)) {
            setf = 1;
        }
        if (!mListTmp.mdataList.get(0).mlist.mdataList.get(1).obj.equals(GB.sipui_ipmask_str)) {
            setf = 1;
        }
        if (!mListTmp.mdataList.get(0).mlist.mdataList.get(2).obj.equals(GB.sipui_gateway_str)) {
            setf = 1;
        }
        if (setf == 1) {
            GB.sipui_ip_str = (String) mListTmp.mdataList.get(0).mlist.mdataList.get(0).obj;
            GB.sipui_ipmask_str = (String) mListTmp.mdataList.get(0).mlist.mdataList.get(1).obj;
            GB.sipui_gateway_str = (String) mListTmp.mdataList.get(0).mlist.mdataList.get(2).obj;
            Lib.wrInterfaces(GB.sipui_ip_str, GB.sipui_ipmask_str, GB.sipui_gateway_str);
        }
        setf = 0;
        if (!mListTmp.mdataList.get(1).mlist.mdataList.get(0).obj.equals(GB.sipmd_ip_str)) {
            setf = 1;
        }
        if (!mListTmp.mdataList.get(1).mlist.mdataList.get(1).obj.equals(GB.sipmd_ipmask_str)) {
            setf = 1;
        }
        if (!mListTmp.mdataList.get(1).mlist.mdataList.get(2).obj.equals(GB.sipmd_gateway_str)) {
            setf = 1;
        }
        if (setf == 1) {
            GB.sipmd_ip_str = (String) mListTmp.mdataList.get(1).mlist.mdataList.get(0).obj;
            GB.sipmd_ipmask_str = (String) mListTmp.mdataList.get(1).mlist.mdataList.get(1).obj;
            GB.sipmd_gateway_str = (String) mListTmp.mdataList.get(1).mlist.mdataList.get(2).obj;
            cla.uiCommand[inx++] = (byte) 0x13;
            cla.uiCommand[inx++] = (byte) 13;
            //=======================================
            cla.uiCommand[inx++] = (byte) 255;//all net address
            slst = GB.sipmd_ip_str.split("\\.");
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[0], -1, 255, 0);
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[1], -1, 255, 0);
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[2], -1, 255, 0);
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[3], -1, 255, 0);
            slst = GB.sipmd_ipmask_str.split("\\.");
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[0], -1, 255, 0);
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[1], -1, 255, 0);
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[2], -1, 255, 0);
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[3], -1, 255, 0);
            slst = GB.sipmd_gateway_str.split("\\.");
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[0], -1, 255, 0);
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[1], -1, 255, 0);
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[2], -1, 255, 0);
            cla.uiCommand[inx++] = (byte) Lib.str2int(slst[3], -1, 255, 0);
            //==============================================
        }
        cla.uiCommand_len = inx;

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
                //cla.dispose();
                //cla.shellCommand="sudo reboot \n";
                //cla.shellCommand = "ls \n";
                //Lib.exe("sudo reboot");
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

    }

    void saveConfiguration(int inx) {
        //GB.configuration = inx;
    }

    void preSaveConfiguration(int inx) {
        Phone6in1 cla = this;
        cla.preConfiguration = inx;
        cla.view_page_cnt = 1;
    }

    void patchCmd(String cmdstr) {
        Phone6in1 cla = this;
        chkSysPassword(cmdstr);

        if (cmdstr.equals("view")) {
            pnView_on_f ^= 1;
            if (pnView_on_f == 1) {
                pnView.setVisible(true);
                pnLeft.setVisible(false);
                view_page = 1;
                view_page_cnt = 0;
            } else {
                pnView.setVisible(false);
                pnLeft.setVisible(true);
                view_page = 1;
                view_page_cnt = 0;
            }
        }

        if (cla.pnView_on_f == 1) {

            if (cmdstr.equals("f4")) {
                if(view_page<=1){
                    cla.pnView_on_f = 0;
                    pnView.setVisible(false);
                    pnLeft.setVisible(true);
                    return;
                }
                view_page=view_page/10;
                return;
                
            }

            int num=0;
            for(int i=1;i<=8;i++){
                if(cmdstr.equals(""+i)){
                    num=i;
                    break;
                }    
            }
            switch(view_page){
                case 1:
                    if(num>0){
                      view_page=view_page*10+num;    
                    }
                    break;
                
            }            
            /*
            if (cmdstr.equals("f1")) {
                if (view_page == 4 && view_page_cnt == 1) {
                    saveConfiguration(preConfiguration);
                    view_page_cnt = 0;
                    return;
                }

                if (--view_page < 0) {
                    view_page = 5;
                    view_page_cnt = 0;
                }

            }
            if (cmdstr.equals("f2")) {
                if (++view_page >= 6) {
                    view_page = 0;
                    view_page_cnt = 0;
                }
            }
            if (view_page == 4) {
                
                
                
                if (cmdstr.equals("1")) {
                    preSaveConfiguration(0);
                }
                if (cmdstr.equals("2")) {
                    preSaveConfiguration(1);
                }
                if (cmdstr.equals("3")) {
                    preSaveConfiguration(2);
                }
                if (cmdstr.equals("4")) {
                    preSaveConfiguration(3);
                }
                if (cmdstr.equals("5")) {
                    preSaveConfiguration(4);
                }
                if (cmdstr.equals("6")) {
                    preSaveConfiguration(5);
                }

                if (cmdstr.equals("f4") && cla.view_page_cnt == 0) {
                    cla.pnView_on_f = 0;
                    pnView.setVisible(false);
                    pnLeft.setVisible(true);
                    return;
                }
                if (cmdstr.equals("f4") && cla.view_page_cnt == 1) {
                    cla.view_page_cnt = 0;

                }
                return;

            }
            if (view_page == 5) {
                if (cmdstr.equals("1")) {
                    //saveConfiguration(0);
                }
            }

            if (cmdstr.equals("f3")) {

            }
            if (cmdstr.equals("f4")) {
                cla.pnView_on_f = 0;
                pnView.setVisible(false);
                pnLeft.setVisible(true);

            }
            */
            return;
        }
        if (cmdstr.equals("menu")) {
            if (sipStatus <= 3) {

                inp1 = new Input(null, true);
                inp1.create();
                inp1.title_str = "請輸入密碼";
                inp1.initv_str = "";
                inp1.onShow();
                password_on_f = 1;
                inp1.setVisible(true);
                password_on_f = 0;
                if (Input.ret_f == 0) {
                    return;
                }
                if (Input.ret_str.equals("16020039")) {
                    menuPrg();
                    return;
                }
                if (Input.ret_str.equals(GB.web_password)) {
                    menuPrg();
                    return;
                }
            }
            return;
        }
        phoneKey(cmdstr);
        
        

    }

    void strCommand(String cmdstr) {
        patchCmd(cmdstr);
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
                patchCmd("f1");
                break;
            case 1 * 256 + 1:
                patchCmd("f2");
                break;
            case 1 * 256 + 2:
                patchCmd("f3");
                break;
            case 1 * 256 + 3:   //menu_on
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
                cla.viewDialPanel_f ^= 1;
                if (viewDialPanel_f == 0) {
                    pnKeypad.setVisible(false);
                    pnInf.setVisible(true);
                } else {
                    pnKeypad.setVisible(true);
                    pnInf.setVisible(false);
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

        if (sipStatus == 3) {
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

    public void tx_ssksipCmd(Ssocket ssk) {
        int i;
        Phone6in1 cla = this;
        byte[] bytes;
        int stx_index = 0;
        ssk.stm.tbuf[stx_index++] = (byte) GB.sipui_device_id;
        ssk.stm.tbuf[stx_index++] = (byte) 0x10;//fid:Get status
        ssk.stm.tbuf[stx_index++] = (byte) 0x00;//fid:len
        //==============================================
        if (cla.uiCommand_len != 0) {                  //fid=0x13 
            for (i = 0; i < cla.uiCommand_len; i++) {
                ssk.stm.tbuf[stx_index++] = cla.uiCommand[i];
            }
            cla.uiCommand_len = 0;
        }
        //===============================================
        if (!cla.sipCommand.equals("")) {
            ssk.stm.tbuf[stx_index++] = (byte) 0x11;//fid:direct linphone command
            ssk.stm.tbuf[stx_index++] = (byte) cla.sipCommand.length();
            bytes = cla.sipCommand.getBytes();
            for (i = 0; i < bytes.length; i++) {
                ssk.stm.tbuf[stx_index++] = bytes[i];
            }
            cla.sipCommand = "";
        }
        //==============================================
        if (!cla.shellCommand.equals("")) {
            ssk.stm.tbuf[stx_index++] = (byte) 0x12;//fid:direct shell command
            ssk.stm.tbuf[stx_index++] = (byte) cla.shellCommand.length();
            bytes = cla.shellCommand.getBytes();
            for (i = 0; i < bytes.length; i++) {
                ssk.stm.tbuf[stx_index++] = bytes[i];
            }
            cla.shellCommand = "";
        }
        //==============================================
        ssk.stm.tbuf_byte = stx_index;
        ssk.stm.enc_mystm();

    }

    public void siptx_byip_cmd(Ssocket ssk, String ip, int port, int tx_start) {
        Phone6in1 cla = this;
        byte[] bytes;
        int i;
        if (++ssksip_tx_tim >= 1) {
            ssksip_tx_tim = 0;
            if (cla.sskip_sip.tx_start_f != 0) {
                return;
            }
            tx_ssksipCmd(ssk);
            bytes = new byte[ssk.stm.txlen];
            for (i = 0; i < ssk.stm.txlen; i++) {
                bytes[i] = ssk.stm.tdata[i];
            }
            ssk.tx_bytes = bytes;
            ssk.tx_ip = ip;     //GB.sipui_ip_str;
            ssk.tx_port = port; //1236;
            ssk.tx_start_f = tx_start;//4;

        }

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

    Phone6in1Tm1(Phone6in1 owner) {
        cla = owner;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (cla.displayShow_f == 0) {
            return;
        }
        if (++cla.siptx_byuart_stop_tim > 50) {
            cla.siptx_byuart_stop_tim = 50;
            if (cla.sipIp_rxed_f == 1) {
                cla.siptx_byip_cmd(cla.sskip_sip, GB.sipmd_ui_ip, GB.sipmd_ui_port, 4);
            } else {
                if (cla.sipIp_rxed_tim >= 50) {
                    cla.siptx_byip_cmd(cla.sskip_sip, GB.sipmd_ui_ip, GB.sipmd_ui_port, 4);
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
        if (++cla.switch_rxed_tim >= 400) {
            cla.switch_rxed_f = 0;
        }
        if (++cla.keypad_rxed_tim >= 50) {
            cla.keypad_rxed_f = 0;
        }
        if (++cla.sipUart_rxed_tim >= 50) {
            cla.sipUart_rxed_f = 0;
        }
        if (++cla.sipIp_rxed_tim >= 50) {
            cla.sipIp_rxed_f = 0;
        }
        if (++cla.sipInf_rxed_tim >= 50) {
            cla.sipInf_rxed_f = 0;
            cla.sipStatus = 0;
            cla.connected_cnt = 0;
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
        if (cla.bta4[0].getBackground() != cl) {
            cla.bta4[0].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;      //SIP READY
        if (cla.sipmd_ping_f == 1) {
            cl = Color.YELLOW;
            if (cla.sipInf_rxed_f == 1) {
                if (cla.sipUart_rxed_f == 1 || cla.sipIp_rxed_f == 1) {
                    cl = Color.GREEN;
                }
            }
        }
        if (cla.bta4[1].getBackground() != cl) {
            cla.bta4[1].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;      //PBX Ready
        if (cla.sipServer_ping_f == 1) {
            cl = Color.YELLOW;
            if (cla.sipStatus >= 3) {
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
        if (cla.bta4[3].getBackground() != cl) {
            cla.bta4[3].setBackground(cl);
        }
        //===================

        str = GB.phone_name + " ( " + GB.phone_no + " )";
        if (!cla.lba1[1].getText().equals(str)) {
            cla.lba1[1].setText(str);
        }

        str = GB.sipmd_ip_str;
        if (!cla.lba1[3].getText().equals(str)) {
            cla.lba1[3].setText(str);
        }

        str = GB.sip_server_ip;
        if (!cla.lba1[5].getText().equals(str)) {
            cla.lba1[5].setText(str);
        }

        str = " 來電號碼 : " + cla.callfrom;
        if (cla.callfrom.equals("")) {
            str = "";
        }
        //str=""+cla.debug_cnt;//<<debug
        //str = cla.debug_str;
        if (!cla.lbStatus.getText().equals(str)) {
            cla.lbStatus.setText(str);
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
        str = "";
        if (cla.sipUart_rxed_f == 1 || cla.sipIp_rxed_f == 1) {
            str = cla.status_str;
        }

        if (!cla.tfLcd1.getText().equals(str)) {
            cla.tfLcd1.setText(str);
        }
        if (cla.keypad_tim == 0) {
            cla.keypad_str = "";
            str = "";
            if (cla.sipUart_rxed_f == 1 || cla.sipIp_rxed_f == 1) {
                str = cla.action_str;
            }

            if (!cla.tfLcd2.getText().equals(str)) {
                cla.tfLcd2.setText(str);
            }
        } else {
            cla.keypad_tim--;
            if (!cla.tfLcd2.getText().equals(cla.keypad_str)) {
                cla.tfLcd2.setText(cla.keypad_str);
            }

        }

        /*
        if (cla.pnView_on_f == 1) {
            str = "";
            if (cla.view_page_cntlm > 1) 
                str = "上一頁";
            if (!cla.bta1[0].getText().equals(str)) {
                cla.bta1[0].setText(str);
                
            str = "";
            if (cla.view_page_cntlm > 1) 
                str = "下一頁";
            if (!cla.bta1[1].getText().equals(str)) {
                cla.bta1[1].setText(str);
                
            str = "";
            if (cla.view_page <=1) 
                str = "儲存";
            if (!cla.bta1[2].getText().equals(str)) {
                cla.bta1[2].setText(str);
            
            str = "離開";
            if (!cla.bta1[3].getText().equals(str)) {
                cla.bta1[3].setText(str);
            
            
        }
        */
        /*
        if (cla.pnView_on_f == 0) {
            str = GB.hotline_nameA[0];
            if (!cla.bta1[0].getText().equals(str)) {
                cla.bta1[0].setText(str);
            str = GB.hotline_nameA[1];
            if (!cla.bta1[1].getText().equals(str)) {
                cla.bta1[1].setText(str);
            str = GB.hotline_nameA[2];
            if (!cla.bta1[2].getText().equals(str)) {
                cla.bta1[2].setText(str);
            str = GB.hotline_nameA[3];
            if (!cla.bta1[3].getText().equals(str)) {
                cla.bta1[3].setText(str);
        }    
        */    
        //=========================================    
        String[] strA = new String[10];
        for (int i = 0; i < 10; i++) {
            strA[i] = "";
        }
        switch (cla.view_page) {
                
                /*
                switch (cla.view_page_cnt) {
                    case 0:
                        strA[0] = "版本";
                        strA[1] = "1";
                        strA[2] = "UI : " + GB.uiVersion;
                        strA[3] = "Sip : " + GB.sipVersion;
                        break;
                    case 1:
                        strA[0] = "本機";
                        strA[1] = "2";
                        strA[2] = "ip : " + GB.sipui_ip_str;
                        strA[3] = "netmask : " + GB.sipui_ipmask_str;
                        strA[4] = "gateway : " + GB.sipui_gateway_str;
                        break;
                    case 2:
                        strA[0] = "IP電話";
                        strA[1] = "3";
                        strA[2] = "ip : " + GB.sipmd_ip_str;
                        strA[3] = "netmask : " + GB.sipmd_ipmask_str;
                        strA[4] = "gateway : " + GB.sipmd_gateway_str;
                        break;
                    case 3:
                        strA[0] = "交換器";
                        strA[1] = "4";
                        strA[1] = "ip : " + GB.switch_ip_str;
                        strA[2] = "netmask : " + GB.switch_ipmask_str;
                        strA[3] = "gateway : " + GB.switch_gateway_str;
                        break;
                }
                */
            case 1:
                strA[0] = "快速設定";
                strA[1] = "";
                strA[2] = "1. 熱鍵 1 設定";
                strA[3] = "2. 熱鍵 2 設定";
                strA[4] = "3. 熱鍵 3 設定";
                strA[5] = "4. 熱鍵 4 設定";
                strA[6] = "5. 選擇車型";
                strA[7] = "6. 選擇車號";
                strA[8] = "7. 系統重啟";
                strA[9] = "8. 顯示資訊";
                break;
            case 11:
                //strA[0] = "熱鍵 "+(cla.view_page/10)+" 設定";
                strA[1] = "";
                strA[2] = "1. "+GB.hotline_nameA[0]+" ( "+GB.hotline_noA[0]+" ) ";
                strA[3] = "2. "+GB.hotline_nameA[1]+" ( "+GB.hotline_noA[1]+" ) ";
                strA[4] = "3. "+GB.hotline_nameA[2]+" ( "+GB.hotline_noA[2]+" ) ";
                strA[5] = "4. "+GB.hotline_nameA[3]+" ( "+GB.hotline_noA[3]+" ) ";
                strA[6] = "5. "+GB.hotline_nameA[4]+" ( "+GB.hotline_noA[4]+" ) ";
                strA[7] = "6. "+GB.hotline_nameA[5]+" ( "+GB.hotline_noA[5]+" ) ";
                strA[8] = "7. "+GB.hotline_nameA[6]+" ( "+GB.hotline_noA[6]+" ) ";
                strA[9] = "8. "+GB.hotline_nameA[7]+" ( "+GB.hotline_noA[7]+" ) ";
                break;
                
                
                
        }
        for (int i = 0; i < 2; i++) {
            str = strA[i];
            if (!cla.lbaView[i].getText().equals(str)) {
                cla.lbaView[i].setText(str);
            }
        }
        for (int i = 0; i < 8; i++) {
            str = strA[i + 2];
            if (!cla.btaView[i].getText().equals(str)) {
                cla.btaView[i].setText(str);
            }
        }

        if (cla.reboot_f == 1) {
            cla.reboot_step++;

            switch (cla.reboot_step) {
                case 10:
                    cla.shellCommand = "sudo reboot \n";
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
                    Lib.exe("sudo reboot");
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
    byte[] id = {0x10, 0x11, 0x12, 0x13};
    int[] txLen = {0, 0, 0, 0};
    byte[][] txData;
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
                ibuf = Lib.ping(GB.sipmd_ip_str);
                if (ibuf == 0) { //ok
                    cla.sipmd_ping_f = 1;
                    cla.sipmd_ping_cnt = 0;
                } else {
                    if (++cla.sipmd_ping_cnt >= 2) {
                        cla.sipmd_ping_f = 0;
                    }
                }
                //==========================
                ibuf = Lib.ping(GB.sip_server_ip);
                if (ibuf == 0) { //ok
                    cla.sipServer_ping_f = 1;
                    cla.sipServer_ping_cnt = 0;
                } else {
                    if (++cla.sipServer_ping_cnt >= 2) {
                        cla.sipServer_ping_f = 0;
                    }
                }
                //==========================
                ibuf = Lib.ping(GB.switch_ip_str);
                if (ibuf == 0) { //ok
                    cla.switch_ping_f = 1;
                    cla.switch_ping_cnt = 0;
                } else {
                    if (++cla.switch_ping_cnt >= 2) {
                        cla.switch_ping_f = 0;
                    }
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
