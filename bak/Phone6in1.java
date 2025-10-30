package base3;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
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
import java.io.IOException;
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

    int fullScr_f = 1;
    int frameOn_f = 0;
    int viewDebugPanel_f = 1;
    int winW = 1024;
    int winH = 800;
    int switch_led_flag;
    int debug_f = 1;
    static Phone6in1 scla;
    int siptx_byuart_stop_tim;
    int menu_on_f = 0;
    int message_on_f = 0;
    int cmdin_f = 0;
    int cmdin_inx = 0;
    String cmdin_str = "";
    String title_str = "title_str";
    int vlen = 16;
    String initv1_str;
    String initv2_str;
    JTextField tfCommand, tfLcd1, tfLcd2;
    Timer tm1 = null;//for display
    Timer tm2 = null;//for open stock window

    int ledflag, keyflag, keypush;
    int ssksip_tx_tim;
    //===============================
    int sipStatus = 0;         //0 no raspberryPi,1:raspberry pi ready,2:linphonec load,3:pbx registed,4:on call 

    int connected_cnt = 0;

    int handStatus = 0;    //0:all 0ff 1:earphone 2:epeaker

    int earPhone_volume = 0;
    int micPhone_volume = 0;
    int speaker_volume = 0;

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

    Message mes1;

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
    JPanel pnLeft, pnRight, pnLcd, pnKeypad, pnDebug;
    JButton[] bta1 = new JButton[4];
    JButton[] bta2 = new JButton[2];
    JButton[] bta3 = new JButton[28];
    JButton[] bta4 = new JButton[16];

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
        cla.setTitle("Phone6in1");
        if (frameOn_f == 0) {
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
        //===============================================
        pnRight = new JPanel();         //right panel
        pnRight.setBackground(Color.BLACK);
        pnMain.add(pnRight);
        //===============================================
        pnDebug = new JPanel();         //left panel
        pnDebug.setBackground(Color.BLACK);
        pnLeft.add(pnDebug);
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
        pnLeft.add(tfCommand);
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
        //===============================================
        tfLcd1 = new JTextField(); //lcd first line
        tfLcd1.setText("");
        tfLcd1.setName(Integer.toString(99 * 256 + 0));
        tfLcd1.setMargin(new Insets(0, 10, 0, 10));
        tfLcd1.setBackground(Color.YELLOW);
        tfLcd1.setFont(myFont);
        tfLcd1.setEditable(false);
        pnLcd.add(tfLcd1);
        //===============================================
        tfLcd2 = new JTextField(); //lcd second line
        tfLcd2.setText("");
        tfLcd2.setName(Integer.toString(99 * 256 + 0));
        tfLcd2.setMargin(new Insets(0, 10, 0, 10));
        tfLcd2.setBackground(Color.YELLOW);
        tfLcd2.setFont(myFont);
        tfLcd2.setEditable(false);
        pnLcd.add(tfLcd2);
        //===============================================
        lbStatus = new JLabel();     //status bar
        lbStatus.setFont(myFont);
        lbStatus.setHorizontalAlignment(JLabel.CENTER);
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
        bta1[0].setText(GB.hotline1_name);
        bta1[1].setText(GB.hotline2_name);
        bta1[2].setText(GB.hotline3_name);
        bta1[3].setIcon(imgSet);

        for (i = 0; i < bta2.length; i++) {
            bta2[i] = new JButton();
            bta2[i].setFont(myFont);
            bta2[i].setName(Integer.toString(2 * 256 + i));
            bta2[i].addMouseListener(mslis);
            bta2[i].setVisible(true);
            pnLeft.add(bta2[i]);
        }
        //=======================
        bta2[0].setText("Clear");
        bta2[1].setText("Exit");

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

        //bta3[3 * i + 5].setBackground(Color.yellow);
        myFont = new Font("Serif", Font.PLAIN, 12);
        for (i = 0; i < bta4.length; i++) {
            bta4[i] = new JButton();
            bta4[i].setFont(myFont);
            bta4[i].setName(Integer.toString(4 * 256 + i));
            bta4[i].setBackground(Color.LIGHT_GRAY);
            bta4[i].addMouseListener(mslis);
            pnDebug.add(bta4[i]);
        }
        //=======================
        bta4[0].setText("sock0");
        bta4[1].setText("sock0p0");
        bta4[2].setText("sock0p1");
        bta4[3].setText("sock0p2");
        bta4[4].setText("switch");
        bta4[5].setText("keypad");
        bta4[6].setText("sipUart");
        bta4[7].setText("sipIp");
        bta4[8].setText("sipInf");

        if (cla.tm1 == null) {      //for all sip command by ip & display
            cla.tm1 = new Timer(20, new Phone6in1Tm1(cla));
            cla.tm1.start();
        }
        if (cla.tm2 == null) {      //proceed command
            cla.tm2 = new Timer(20, new Phone6in1Tm2(cla));
            cla.tm2.start();
        }

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

        //=========================================
        menu1.menuRoot = new MenuList("設定", 1);
        menuTmp = menu1.menuRoot;
        menuTmp.preMenuList = null;

        str = "1. 本機網路設定";
        menuTmp.add(str, 1);
        str = "2. SIP電話網路設定";
        menuTmp.add(str, 1);
        str = "3. 閘道器網路設定";
        menuTmp.add(str, 1);
        str = "4. 自測";
        menuTmp.add(str, 0);
        str = "5. 重新開機";
        menuTmp.add(str, 0);
        str = "6. 返回";
        menuTmp.add(str, 2);
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
        menu1.menuRoot.mdataList.get(2).mlist = new MenuList("閘道器網路設定", 1);
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
                    cla.micPhone_volume = bytes_in[cmdinx++];
                    cla.sipflag[0] = bytes_in[cmdinx++];
                    cla.sipflag[1] = bytes_in[cmdinx++];
                    cla.sipflag[2] = bytes_in[cmdinx++];
                    cla.sipflag[3] = bytes_in[cmdinx++];
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
                    GB.sipmd_ipmask_str = sip_ipmask;
                    GB.sipmd_gateway_str = sip_gateway;

                    break;
                default:
                    cmdlen = 9999;
                    break;

            }
            inx = inx + cmdlen + 2;
        }

    }

    int s0p0_txnone_tim = 0;
    int switch_rxed_f = 0;
    int switch_rxed_tim = 0;

    void decS0p0(byte[] rdata, int stInx, int len) {
        Phone6in1 cla = this;
        int i, j;
        String str;
        if (len > 0) {
            //System.out.println("" + cla.sskio0.inbuf_len);
            switch_rxed_f = 1;
            switch_rxed_tim = 0;
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
                    cla.switchIp = strB[1];
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
            while (true) {
                if (str.contains("Username:")) {
                    //txret_switch(cla.sskio0, "root");
                    txpackStr(trxPack0, 0, "root");
                    break;
                }
                if (str.contains("Password:")) {
                    //txret_switch(cla.sskio0, "Airmoni:22991660");
                    txpackStr(trxPack0, 0, "Airmoni:22991660");
                    break;
                }
                if (str.contains("switch_a>")) {
                    //txret_switch(cla.sskio0, "show ip interface brief");
                    txpackStr(trxPack0, 0, "show ip interface brief");
                    break;
                }
                break;
            }
        } else {
            if (++s0p0_txnone_tim > 100) {
                s0p0_txnone_tim = 0;
                //txret_switch(cla.sskio0, "\n");
                txpackStr(trxPack0, 0, "\n");
            }
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
            if (cla.switchFlag[i] == 1) {
                switch_led_flag += 1;
            } else {
                switch_led_flag += 2;
            }
        }
        //switch_led_flag=0x0000;
        //switch_led_flag bit0-15 =gygygygygygygygy 
        txdata[inx++] = (byte) GB.sipui_device_id;
        txdata[inx++] = (byte) 0x18;//function id
        txdata[inx++] = 1; //0 cpl led 1:set led 2:inc backLight
        txdata[inx++] = (byte) ((switch_led_flag >> 0) & 255);
        txdata[inx++] = (byte) ((switch_led_flag >> 8) & 255);
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

    char[] sysPassword = new char[20];

    void chkSysPassword(char ch) {
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
            viewDebugPanel_f ^= 1;
            if (viewDebugPanel_f == 1) {
                pnDebug.setVisible(true);
                lbTitle.setVisible(false);
            } else {
                pnDebug.setVisible(false);
                lbTitle.setVisible(true);
            }
            keypad_str = "";

        }

    }

    void dec_keypad1() {
        Phone6in1 cla = this;
        String str;

        if ((keyflag & 255) != 0xff) {
            if (keypush == 0) {
                keypush = 1;

                //cla.lbTitle.setText("Key In: " + keyflag);
                if (cla.menu_on_f == 0 && cla.message_on_f == 0) {
                    if (cla.cmdin_f != 0) {
                        return;
                    }
                    switch (keyflag & 255) {
                        case 1://1
                            cla.cmdin_str = "1";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 2://2
                            cla.cmdin_str = "2";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 3://3
                            cla.cmdin_str = "3";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 5://4
                            cla.cmdin_str = "4";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 6://5
                            cla.cmdin_str = "5";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 7://6
                            cla.cmdin_str = "6";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 9://7
                            cla.cmdin_str = "7";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 10://8
                            cla.cmdin_str = "8";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 11://9
                            cla.cmdin_str = "9";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 13://*
                            cla.cmdin_str = "*";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 14://0
                            cla.cmdin_str = "0";
                            cla.cmdin_f = 2;
                            chkSysPassword(cla.cmdin_str.charAt(0));
                            break;
                        case 15://#
                            cla.cmdin_str = "#";
                            cla.cmdin_f = 2;
                            break;
                        case 0://f1
                            cla.cmdin_str = "f1";
                            cla.cmdin_f = 2;
                            break;
                        case 4://f2
                            cla.cmdin_str = "f2";
                            cla.cmdin_f = 2;
                            break;
                        case 8://f3
                            cla.cmdin_str = "f3";
                            cla.cmdin_f = 2;
                            break;
                        case 12://f4
                            cla.cmdin_str = "f4";
                            cla.cmdin_f = 2;
                            break;
                        case 16://hang off
                            cla.cmdin_str = "hangoff";
                            cla.cmdin_f = 2;
                            break;
                        case 17://upup
                            cla.cmdin_str = "prev";
                            cla.cmdin_f = 2;
                            break;
                        case 18://speaker
                            cla.cmdin_str = "speaker";
                            cla.cmdin_f = 2;
                            break;
                        case 19://hangon
                            cla.cmdin_str = "hangon";
                            cla.cmdin_f = 2;
                            break;
                        case 24://up
                            cla.cmdin_str = "up";
                            cla.cmdin_f = 2;
                            break;
                        case 25://left
                            cla.cmdin_str = "left";
                            cla.cmdin_f = 2;
                            break;
                        case 26://down  
                            cla.cmdin_str = "down";
                            cla.cmdin_f = 2;
                            break;
                        case 27://right 
                            cla.cmdin_str = "right";
                            cla.cmdin_f = 2;
                            break;
                        case 20://ok 
                            cla.cmdin_str = "ok";
                            cla.cmdin_f = 2;
                            chkSysPassword('o');
                            chkSysPassword('k');
                            break;
                        case 21://mute
                            cla.cmdin_str = "mute";
                            cla.cmdin_f = 2;
                            break;
                        case 22://sub 
                            cla.cmdin_str = "-";
                            cla.cmdin_f = 2;
                            break;
                        case 23://add 
                            cla.cmdin_str = "+";
                            cla.cmdin_f = 2;
                            break;
                        case 28://M
                            cla.cmdin_str = "menu";
                            cla.cmdin_f = 2;
                            break;
                        case 29://F
                            cla.cmdin_str = "transfer";
                            cla.cmdin_f = 2;
                            break;
                        case 30://Light
                            break;
                        case 31://book
                            cla.cmdin_str = "book";
                            cla.cmdin_f = 2;
                            break;

                    }

                }

                if (cla.menu_on_f == 0 && cla.message_on_f == 1) {
                    if (cla.mes1.cmdin_f != 0) {
                        return;
                    }
                    switch (keyflag & 255) {
                        case 20://ok 
                            cla.mes1.cmdin_str = "ok";
                            cla.mes1.cmdin_f = 2;
                            break;
                        case 25://left
                            cla.mes1.cmdin_str = "no";
                            cla.mes1.cmdin_f = 2;
                            break;
                        case 24://up
                            cla.cmdin_str = "no";
                            cla.cmdin_f = 2;
                            break;
                    }
                }

                if (cla.menu_on_f == 1 && cla.menu1.input_on_f == 0) {
                    if (cla.menu1.cmdin_f != 0) {
                        return;
                    }
                    switch (keyflag & 255) {
                        case 1://1
                            cla.menu1.cmdin_str = "1";
                            cla.menu1.cmdin_f = 2;
                            break;
                        case 2://2
                            cla.menu1.cmdin_str = "2";
                            cla.menu1.cmdin_f = 2;
                            break;
                        case 3://3
                            cla.menu1.cmdin_str = "3";
                            cla.menu1.cmdin_f = 2;
                            break;
                        case 5://4
                            cla.menu1.cmdin_str = "4";
                            cla.menu1.cmdin_f = 2;
                            break;
                        case 6://5
                            cla.menu1.cmdin_str = "5";
                            cla.menu1.cmdin_f = 2;
                            break;
                        case 7://6
                            cla.menu1.cmdin_str = "6";
                            cla.menu1.cmdin_f = 2;
                            break;
                        case 9://7
                            cla.menu1.cmdin_str = "7";
                            cla.menu1.cmdin_f = 2;
                            break;
                        case 10://8
                            cla.menu1.cmdin_str = "8";
                            cla.menu1.cmdin_f = 2;
                            break;
                        case 11://9
                            cla.menu1.cmdin_str = "9";
                            cla.menu1.cmdin_f = 2;
                            break;
                        case 14://0
                            cla.menu1.cmdin_str = "0";
                            cla.menu1.cmdin_f = 2;
                            break;
                    }

                }
                if (cla.menu_on_f == 1 && cla.menu1.input_on_f == 1) {
                    if (cla.menu1.inp1.cmdin_f != 0) {
                        return;
                    }
                    switch (keyflag & 255) {
                        case 1://1
                            cla.menu1.inp1.cmdin_str = "1";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 2://2
                            cla.menu1.inp1.cmdin_str = "2";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 3://3
                            cla.menu1.inp1.cmdin_str = "3";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 5://4
                            cla.menu1.inp1.cmdin_str = "4";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 6://5
                            cla.menu1.inp1.cmdin_str = "5";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 7://6
                            cla.menu1.inp1.cmdin_str = "6";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 9://7
                            cla.menu1.inp1.cmdin_str = "7";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 10://8
                            cla.menu1.inp1.cmdin_str = "8";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 11://9
                            cla.menu1.inp1.cmdin_str = "9";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 13://*
                            cla.menu1.inp1.cmdin_str = ".";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 14://0
                            cla.menu1.inp1.cmdin_str = "0";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 15://#
                            break;
                        case 0://f1
                            cla.menu1.inp1.cmdin_str = "back";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 4://f2
                            cla.menu1.inp1.cmdin_str = "clear";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 8://f3
                            cla.menu1.inp1.cmdin_str = "enter";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;
                        case 12://f4
                            cla.menu1.inp1.cmdin_str = "esc";
                            cla.menu1.inp1.cmdin_f = 2;
                            break;

                    }

                }

            }

        } else {
            keypush = 0;
        }

    }

    void dec_keypad() {
        Phone6in1 cla = this;
        String str;

        if ((keyflag & 255) != 0xff) {
            if (keypush == 0) {
                keypush = 1;

                switch (keyflag & 255) {
                    case 1://1
                        cla.cmdin_str = "1";
                        cla.cmdin_f = 2;
                        chkSysPassword(cla.cmdin_str.charAt(0));
                        break;
                    case 2://2
                        cla.cmdin_str = "2";
                        cla.cmdin_f = 2;
                        chkSysPassword(cla.cmdin_str.charAt(0));
                        break;
                    case 3://3
                        cla.cmdin_str = "3";
                        cla.cmdin_f = 2;
                        chkSysPassword(cla.cmdin_str.charAt(0));
                        break;
                    case 5://4
                        cla.cmdin_str = "4";
                        cla.cmdin_f = 2;
                        chkSysPassword(cla.cmdin_str.charAt(0));
                        break;
                    case 6://5
                        cla.cmdin_str = "5";
                        cla.cmdin_f = 2;
                        break;
                    case 7://6
                        cla.cmdin_str = "6";
                        cla.cmdin_f = 2;
                        break;
                    case 9://7
                        cla.cmdin_str = "7";
                        cla.cmdin_f = 2;
                        break;
                    case 10://8
                        cla.cmdin_str = "8";
                        cla.cmdin_f = 2;
                        break;
                    case 11://9
                        cla.cmdin_str = "9";
                        cla.cmdin_f = 2;
                        break;
                    case 13://*
                        cla.cmdin_str = "*";
                        cla.cmdin_f = 2;
                        break;
                    case 14://0
                        cla.cmdin_str = "0";
                        cla.cmdin_f = 2;
                        break;
                    case 15://#
                        cla.cmdin_str = "#";
                        cla.cmdin_f = 2;
                        break;
                    case 0://f1
                        cla.cmdin_str = "f1";
                        cla.cmdin_f = 2;
                        break;
                    case 4://f2
                        cla.cmdin_str = "f2";
                        cla.cmdin_f = 2;
                        break;
                    case 8://f3
                        cla.cmdin_str = "f3";
                        cla.cmdin_f = 2;
                        break;
                    case 12://f4
                        cla.cmdin_str = "f4";
                        cla.cmdin_f = 2;
                        break;
                    case 16://hang off
                        cla.cmdin_str = "hangoff";
                        cla.cmdin_f = 2;
                        break;
                    case 17://upup
                        cla.cmdin_str = "prev";
                        cla.cmdin_f = 2;
                        break;
                    case 18://speaker
                        cla.cmdin_str = "speaker";
                        cla.cmdin_f = 2;
                        break;
                    case 19://hangon
                        cla.cmdin_str = "hangon";
                        cla.cmdin_f = 2;
                        break;
                    case 24://up
                        cla.cmdin_str = "up";
                        cla.cmdin_f = 2;
                        break;
                    case 25://left
                        cla.cmdin_str = "left";
                        cla.cmdin_f = 2;
                        break;
                    case 26://down  
                        cla.cmdin_str = "down";
                        cla.cmdin_f = 2;
                        break;
                    case 27://right 
                        cla.cmdin_str = "right";
                        cla.cmdin_f = 2;
                        break;
                    case 20://ok 
                        cla.cmdin_str = "ok";
                        cla.cmdin_f = 2;
                        break;
                    case 21://mute
                        cla.cmdin_str = "mute";
                        cla.cmdin_f = 2;
                        break;
                    case 22://sub 
                        cla.cmdin_str = "-";
                        cla.cmdin_f = 2;
                        break;
                    case 23://add 
                        cla.cmdin_str = "+";
                        cla.cmdin_f = 2;
                        break;
                    case 28://M
                        cla.cmdin_str = "menu";
                        cla.cmdin_f = 2;
                        break;
                    case 29://F
                        cla.cmdin_str = "transfer";
                        cla.cmdin_f = 2;
                        break;
                    case 30://Light
                        break;
                    case 31://book
                        cla.cmdin_str = "book";
                        cla.cmdin_f = 2;
                        break;
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
        if (cla.fullScr_f == 1) {
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
        if (frameOn_f == 1) {
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

        MyLayout.ctrA[0] = cla.pnMain;
        MyLayout.rateW = 1.0;
        MyLayout.gridLy();

        MyLayout.ctrA[0] = cla.pnLeft;
        MyLayout.rateW = 0.8;
        MyLayout.gridLy();
        //=================================
        MyLayout.xst = MyLayout.xend;
        MyLayout.ctrA[0] = cla.pnRight;
        MyLayout.rateW = 1;
        MyLayout.gridLy();

        //==============================================================
        MyLayout.ctrA[0] = cla.pnDebug;
        MyLayout.rateH = 0.08;
        MyLayout.gridLy();
        MyLayout.yst = 0;
        MyLayout.ctrA[0] = cla.lbTitle;
        MyLayout.rateH = 0.08;
        MyLayout.gridLy();
        if (viewDebugPanel_f == 1) {
            cla.pnDebug.setVisible(true);
            cla.lbTitle.setVisible(false);
        } else {
            cla.pnDebug.setVisible(false);
            cla.lbTitle.setVisible(true);

        }

        //=================================
        MyLayout.yst = MyLayout.yend;
        MyLayout.ctrA[0] = cla.pnLcd;
        MyLayout.rateH = 0.3;
        MyLayout.gridLy();
        //=================================
        MyLayout.yst = MyLayout.yend;
        MyLayout.ctrA[0] = cla.lbStatus;
        MyLayout.rateH = 0.12;
        MyLayout.gridLy();
        //=================================
        ibuf = MyLayout.yend;
        /*
        MyLayout.yst = MyLayout.yend;
        MyLayout.ctrA[0] = cla.sp1;
        MyLayout.rateH = 0.85;
        MyLayout.gridLy();
         */

        MyLayout.yst = ibuf;
        MyLayout.ctrA[0] = cla.pnKeypad;
        MyLayout.rateH = 0.85;
        MyLayout.gridLy();
        //cla.sp1.setVisible(false);
        //=================================
        ibuf = MyLayout.yend;
        MyLayout.yst = MyLayout.yend;
        MyLayout.ctrA[0] = cla.tfCommand;
        MyLayout.rateH = 1;
        MyLayout.rateW = 0.6;
        MyLayout.gridLy();
        //
        MyLayout.yst = ibuf;
        MyLayout.xst = MyLayout.xend;
        MyLayout.ctrA[0] = cla.bta2[0];
        MyLayout.rateH = 1;
        MyLayout.rateW = 0.5;
        MyLayout.gridLy();
        //
        MyLayout.yst = ibuf;
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
        //=================================
        System.arraycopy(cla.bta4, 0, MyLayout.ctrA, 0, 16);
        MyLayout.eleAmt = 16;
        MyLayout.xc = 8;
        MyLayout.yc = 2;
        MyLayout.gridLy();
        //=================================

    }

    void strCommand(String cmdstr) {
        patchCmd(cmdstr);
    }


    void keyCommandxxx(int index) {
        Phone6in1 cla = this;
        String str;
        int inx;
        String[] slst;

        //========================================
        switch (index) {
            //tfCommand
            case 0 * 256 + 0:
                break;
            //clear    
            case 2 * 256 + 0:
                break;
            //exit    
            case 2 * 256 + 1:
                cla.dispose();
                break;
            //right button    
            //=================================    
            case 1 * 256 + 0:
                if (cla.sipStatus == 3) {
                    sipCommand = "call " + GB.hotline1_no + " \n";
                }
                break;
            case 1 * 256 + 1:
                if (cla.sipStatus == 3) {
                    sipCommand = "call " + GB.hotline2_no + " \n";
                }
                break;
            case 1 * 256 + 2:
                if (cla.sipStatus == 3) {
                    sipCommand = "call " + GB.hotline3_no + " \n";
                }
                break;

            case 1 * 256 + 3:   //menu_on
                //cla.lbTitle.setText("Key index: " + index);

                if (cla.sipStatus != 4) {

                    cla.menu1.nowMenuList = cla.menu1.menuRoot;
                    cla.menu1.fullScr_f = 1;
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

                    cla.menu1.onShow();
                    cla.menu_on_f = 1;
                    cla.menu1.setVisible(true);
                    cla.menu_on_f = 0;

                    int setf = 0;
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
                        inx = 0;
                        cla.uiCommand[inx++] = (byte) 0x13;
                        cla.uiCommand[inx++] = (byte) 13;
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
                        cla.uiCommand_len = inx;
                        //==============================================
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
                            //cla.dispose();
                            //cla.shellCommand="sudo reboot \n";
                            cla.shellCommand = "ls \n";
                            Lib.exe("sudo reboot");
                        }

                    }
                }
                break;
            //=================================    
            //key 1    
            case (3 * 256 + 7 * 0 + 0):
                editpad('1');
                cla.chkSysPassword('1');
                break;
            //key 2    
            case (3 * 256 + 7 * 0 + 1):
                editpad('2');
                cla.chkSysPassword('2');
                break;
            //key 3    
            case (3 * 256 + 7 * 0 + 2):
                editpad('3');
                cla.chkSysPassword('3');
                break;
            //transfer    
            case (3 * 256 + 7 * 0 + 3):
                break;
            //up    
            case (3 * 256 + 7 * 0 + 4):
                break;
            //mute    
            case (3 * 256 + 7 * 0 + 5):
                break;
            //hotline1    
            case (3 * 256 + 7 * 0 + 6):
                sipCommand = "call " + GB.hotline1_no + " \n";
                cla.keypad_str = "";
                cla.keypad_tim = 0;
                break;
            //==================================    
            //key 4    
            case (3 * 256 + 7 * 1 + 0):
                editpad('4');
                cla.chkSysPassword('4');
                break;
            //key 5    
            case (3 * 256 + 7 * 1 + 1):
                editpad('5');
                cla.chkSysPassword('5');
                break;
            //key 6    
            case (3 * 256 + 7 * 1 + 2):
                editpad('6');
                cla.chkSysPassword('6');
                break;
            //left    
            case (3 * 256 + 7 * 1 + 3):
                break;
            //enter    
            case (3 * 256 + 7 * 1 + 4):
                cla.chkSysPassword('o');
                cla.chkSysPassword('k');
                break;
            //right    
            case (3 * 256 + 7 * 1 + 5):
                break;
            //hotline2    
            case (3 * 256 + 7 * 1 + 6):
                sipCommand = "call " + GB.hotline2_no + " \n";
                cla.keypad_str = "";
                cla.keypad_tim = 0;
                break;
            //==================================    
            //key 7    
            case (3 * 256 + 7 * 2 + 0):
                editpad('7');
                cla.chkSysPassword('7');
                break;
            //key 8    
            case (3 * 256 + 7 * 2 + 1):
                editpad('8');
                cla.chkSysPassword('8');
                break;
            //key 9    
            case (3 * 256 + 7 * 2 + 2):
                editpad('9');
                cla.chkSysPassword('9');
                break;
            //minus    
            case (3 * 256 + 7 * 2 + 3):
                break;
            //down    
            case (3 * 256 + 7 * 2 + 4):
                break;
            //plus    
            case (3 * 256 + 7 * 2 + 5):
                break;
            //hotline3    
            case (3 * 256 + 7 * 2 + 6):
                sipCommand = "call " + GB.hotline3_no + " \n";
                cla.keypad_str = "";
                cla.keypad_tim = 0;
                break;
            //==================================    
            //key *    
            case (3 * 256 + 7 * 3 + 0):
                editpad('.');
                break;
            //key 0    
            case (3 * 256 + 7 * 3 + 1):
                editpad('0');
                break;
            //key #    
            case (3 * 256 + 7 * 3 + 2):
                editpad('#');
                break;
            //hang on    
            case (3 * 256 + 7 * 3 + 3):
                //cla.speakerStatus=0;
                //sipCommand = "terminate" + " \n";
                cla.keypad_str = "";
                cla.keypad_tim = 0;
                inx = 0;
                cla.uiCommand[inx++] = (byte) 0x14;
                cla.uiCommand[inx++] = (byte) 1;
                cla.uiCommand[inx++] = (byte) 0;
                cla.uiCommand_len = inx;
                break;
            //hang off    
            case (3 * 256 + 7 * 3 + 4):
                //cla.speakerStatus=1;
                //cla.shellCommand = "aplay /usr/share/sounds/linphone/hello16000.wav\n";
                //sipCommand = "answer" + " \n";
                cla.keypad_str = "";
                cla.keypad_tim = 0;
                //====================================================================
                inx = 0;
                cla.uiCommand[inx++] = (byte) 0x14;
                cla.uiCommand[inx++] = (byte) 1;
                cla.uiCommand[inx++] = (byte) 1;
                cla.uiCommand_len = inx;
                break;
            //speaker    
            case (3 * 256 + 7 * 3 + 5):
                //cla.speakerStatus=2;
                //sipCommand = "answer" + " \n";
                cla.keypad_str = "";
                cla.keypad_tim = 0;
                inx = 0;
                cla.uiCommand[inx++] = (byte) 0x14;
                cla.uiCommand[inx++] = (byte) 1;
                cla.uiCommand[inx++] = (byte) 2;
                cla.uiCommand_len = inx;
                break;
            //hotline4    
            case (3 * 256 + 7 * 3 + 6):
                sipCommand = "call " + GB.hotline4_no + " \n";
                cla.keypad_str = "";
                cla.keypad_tim = 0;
                break;

        }

    }

    void patchCmd(String cmdstr){
        
        
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
            //clear    
            case 2 * 256 + 0:
                break;
            //exit    
            case 2 * 256 + 1:
                cla.dispose();
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
                patchCmd("menu");
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
        if (++cla.siptx_byuart_stop_tim > 50) {
            cla.siptx_byuart_stop_tim = 50;
            cla.siptx_byip_cmd(cla.sskip_sip, GB.sipmd_ip_str, GB.sipmd_port, 4);
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
        if (++cla.switch_rxed_tim >= 200) {
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
        cl = Color.LIGHT_GRAY;
        if (cla.sock0_rxed_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.bta4[0].getBackground() != cl) {
            cla.bta4[0].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;
        if (cla.sock0p0_rxed_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.bta4[1].getBackground() != cl) {
            cla.bta4[1].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;
        if (cla.sock0p1_rxed_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.bta4[2].getBackground() != cl) {
            cla.bta4[2].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;
        if (cla.sock0p2_rxed_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.bta4[3].getBackground() != cl) {
            cla.bta4[3].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;
        if (cla.switch_rxed_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.bta4[4].getBackground() != cl) {
            cla.bta4[4].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;
        if (cla.keypad_rxed_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.bta4[5].getBackground() != cl) {
            cla.bta4[5].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;
        if (cla.sipUart_rxed_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.bta4[6].getBackground() != cl) {
            cla.bta4[6].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;
        if (cla.sipIp_rxed_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.bta4[7].getBackground() != cl) {
            cla.bta4[7].setBackground(cl);
        }
        //===================
        cl = Color.LIGHT_GRAY;
        if (cla.sipInf_rxed_f == 1) {
            cl = Color.GREEN;
        }
        if (cla.bta4[8].getBackground() != cl) {
            cla.bta4[8].setBackground(cl);
        }
        //
        str = "ss=" + cla.sipStatus;
        if (!cla.bta4[10].getText().equals(str)) {
            cla.bta4[10].setText(str);
        }
        //
        str = "sc=" + cla.connected_cnt;
        if (!cla.bta4[11].getText().equals(str)) {
            cla.bta4[11].setText(str);
        }
        //
        str = "hs=" + cla.handStatus;
        if (!cla.bta4[12].getText().equals(str)) {
            cla.bta4[12].setText(str);
        }
        str = "ev=" + cla.earPhone_volume;
        if (!cla.bta4[13].getText().equals(str)) {
            cla.bta4[13].setText(str);
        }
        str = "sv=" + cla.speaker_volume;
        if (!cla.bta4[14].getText().equals(str)) {
            cla.bta4[14].setText(str);
        }
        str = "mv=" + cla.micPhone_volume;
        if (!cla.bta4[15].getText().equals(str)) {
            cla.bta4[15].setText(str);
        }
        //
        //

        //  tx_sskio1();
        if (!cla.tfLcd1.getText().equals(cla.status_str)) {
            cla.tfLcd1.setText(cla.status_str);
        }
        if (cla.keypad_tim == 0) {
            cla.keypad_str = "";
            if (!cla.tfLcd2.getText().equals(cla.action_str)) {
                cla.tfLcd2.setText(cla.action_str);
            }
        } else {
            cla.keypad_tim--;
            if (!cla.tfLcd2.getText().equals(cla.keypad_str)) {
                cla.tfLcd2.setText(cla.keypad_str);
            }

        }

    }

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
