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

public class PhoneUi extends javax.swing.JDialog {

    int debug_f = 1;
    static PhoneUi scla;
    int menu_on_f = 0;
    int message_on_f = 0;
    int cmdin_f = 0;
    int cmdin_inx = 0;
    String cmdin_str = "";
    String title_str = "title_str";
    int fullScr_f = 0;
    int frameOn_f = 1;
    int winW = 800;
    int winH = 480;
    int vlen = 16;
    String initv1_str;
    String initv2_str;
    JTextField tf1, tf2, tf3;
    Timer tm1 = null;//for display
    Timer tm2 = null;//for open stock window

    
    int ledflag, keyflag, keypush;
    
    //===============================
    int status_cnt = 0;
    int connected_cnt = 0;
    byte[] sipflag = new byte[4];
    String status_str = "";
    String action_str = "";
    String callto = "";
    String callfrom = "";
    //==============================
    

    Ssocket sskio;    //from nkv6in1_io
    Ssocket sskweb; //from web
    Ssocket ssksip;  //to sipphone get information

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

    JLabel lb1, lb2;
    JPanel pn1, pn2, pn3, pn4;
    JButton[] bta1 = new JButton[10];
    JButton[] bta2 = new JButton[2];
    JButton[] bta3 = new JButton[28];

    //static MyLayout ly=new MyLayout();
    public PhoneUi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        PhoneUi.scla = this;
        PhoneUi cla = this;
        cla.setBounds(-100, -100, 0, 0);

    }

    public void create() {
        int i;
        final PhoneUi cla = this;
        String str;
        cla.addWindowListener(new PhoneUiWinLis(cla));
        cla.setTitle("PhoneUi");
        if (frameOn_f == 0) {
            cla.setUndecorated(true);
        }
        Font myFont;
        //myFont = new Font("Serif", Font.BOLD, 24);
        PhoneUiMsLis mslis = new PhoneUiMsLis(this);
        PhoneUiKeyLis keylis = new PhoneUiKeyLis(this);
        //===============================================
        cp = cla.getContentPane();
        cp.setBackground(Color.black);
        //===============================================
        pnMain = new JPanel();
        cp.add(pnMain);

        pn1 = new JPanel();
        pnMain.add(pn1);
        pn2 = new JPanel();
        pn2.setBackground(Color.BLACK);
        pnMain.add(pn2);

        //===============================================
        myFont = new Font("Serif", Font.BOLD, 24);
        tf1 = new JTextField();
        tf1.setText("");
        tf1.setName(Integer.toString(99 * 256 + 0));
        tf1.setMargin(new Insets(0, 10, 0, 10));
        tf1.addMouseListener(mslis);
        tf1.addKeyListener(keylis);
        tf1.setFont(myFont);
        //tf1.setBackground(Color.red);
        pn1.add(tf1);

        //===============================================
        lb1 = new JLabel();
        lb1.setFont(myFont);
        lb1.setHorizontalAlignment(JLabel.CENTER);
        pn1.add(lb1);
        pn3 = new JPanel();
        pn3.setBackground(Color.BLACK);
        pn1.add(pn3);

        pn4 = new JPanel();
        pn4.setBackground(Color.BLACK);
        pn1.add(pn4);

        //===============================================
        tf2 = new JTextField();
        tf2.setText("");
        tf2.setName(Integer.toString(99 * 256 + 0));
        tf2.setMargin(new Insets(0, 10, 0, 10));
        tf2.setBackground(Color.YELLOW);
        tf2.setFont(myFont);
        tf2.setEditable(false);
        pn3.add(tf2);

        //===============================================
        tf3 = new JTextField();
        tf3.setText("");
        tf3.setName(Integer.toString(99 * 256 + 0));
        tf3.setMargin(new Insets(0, 10, 0, 10));
        tf3.setBackground(Color.YELLOW);
        tf3.setFont(myFont);
        tf3.setEditable(false);
        pn3.add(tf3);

        //===============================================
        lb2 = new JLabel();
        lb2.setFont(myFont);
        lb2.setHorizontalAlignment(JLabel.CENTER);
        lb2.setBackground(Color.CYAN);
        lb2.setOpaque(true);
        pn1.add(lb2);

        for (i = 0; i < bta1.length; i++) {
            bta1[i] = new JButton();
            bta1[i].setFont(myFont);
            bta1[i].setName(Integer.toString(1 * 256 + i));
            bta1[i].addMouseListener(mslis);
            bta1[i].setVisible(false);
            pn2.add(bta1[i]);
        }
        //=======================
        bta1[0].setText("Call A");
        bta1[1].setText("Call B");
        bta1[2].setText("Call C");

        for (i = 0; i < bta2.length; i++) {
            bta2[i] = new JButton();
            bta2[i].setFont(myFont);
            bta2[i].setName(Integer.toString(2 * 256 + i));
            bta2[i].addMouseListener(mslis);
            bta2[i].setVisible(true);
            pn1.add(bta2[i]);
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
            pn4.add(bta3[i]);
        }
        //=======================
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

        if (cla.tm1 == null) {
            cla.tm1 = new Timer(20, new PhoneUiTm1(cla));
            cla.tm1.start();
        }

        if (cla.tm2 == null) {
            cla.tm2 = new Timer(20, new PhoneUiTm2(cla));
            cla.tm2.start();
        }

        //=======================================================
        sskio = new Ssocket();
        sskio.format = 1;
        sskio.rxcon_ltim = 100;//unit 10ms
        sskio.create(1334);
        sskio.sskRx = new SskRx() {
            @Override
            public void sskRx(int format) {
                cla.sskioRx(format);
            }
        };
        sskio.start();
        //=======================================================
        sskweb = new Ssocket(); //for web
        sskweb.format = 0;
        sskweb.rxcon_ltim = 100;//unit 10ms
        sskweb.create(1335);
        sskweb.sskRx = new SskRx() {
            @Override
            public void sskRx(int format) {
                cla.sskwebRx(format);
            }
        };
        sskweb.start();
        //=====================================
        ssksip = new Ssocket(); //for sip
        ssksip.format = 1;
        ssksip.rxcon_ltim = 100;//unit 10ms
        ssksip.create(1336);
        ssksip.sskRx = new SskRx() {
            @Override
            public void sskRx(int format) {
                cla.ssksipRx(format);
            }
        };
        ssksip.start();
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

    void ssksipRx(int format) {
        PhoneUi cla = this;
        String str;
        int i, j, k;
        int inx = 0;
        int cmdinx;
        int cmdlen;
        int cmd;
        int txlen;
        int txinx;
        int stx_index;
        cla.ssksip.datain_f = 0;
        cla.ssksip.connect_f = 1;
        byte[] bytes;

        if (cla.ssksip.inbuf[0] != (byte) 0xd3) //status chg
        {
            return;
        }
        inx++;
        while (inx < cla.ssksip.inbuf_len) {
            cmd = cla.ssksip.inbuf[inx];
            cmdlen = cla.ssksip.inbuf[inx + 1];
            cmdinx = inx + 2;
            switch (cmd) {
                case 0x10://status flag
                    if(cmdlen!=6)
                      break;
                    cla.status_cnt=cla.ssksip.inbuf[cmdinx++];
                    cla.connected_cnt=cla.ssksip.inbuf[cmdinx++];
                    cla.sipflag[0]=cla.ssksip.inbuf[cmdinx++];
                    cla.sipflag[1]=cla.ssksip.inbuf[cmdinx++];
                    cla.sipflag[2]=cla.ssksip.inbuf[cmdinx++];
                    cla.sipflag[3]=cla.ssksip.inbuf[cmdinx++];
                    break;
                case 0x11://status_str
                    bytes = new byte[cmdlen];
                    for(i=0;i<cmdlen;i++)
                        bytes[i]=cla.ssksip.inbuf[cmdinx++];
                    cla.status_str = new String(bytes);
                    break;
                case 0x12://action_str
                    bytes = new byte[cmdlen];
                    for(i=0;i<cmdlen;i++)
                        bytes[i]=cla.ssksip.inbuf[cmdinx++];
                    cla.action_str = new String(bytes);
                    break;
                case 0x13://callto
                    bytes = new byte[cmdlen];
                    for(i=0;i<cmdlen;i++)
                        bytes[i]=cla.ssksip.inbuf[cmdinx++];
                    cla.callto = new String(bytes);
                    break;
                case 0x14://callfrom
                    bytes = new byte[cmdlen];
                    for(i=0;i<cmdlen;i++)
                        bytes[i]=cla.ssksip.inbuf[cmdinx++];
                    cla.callfrom = new String(bytes);
                    break;
            }
            inx = inx + cmdlen + 2;
        }
        
        //str = new String(bytes);
        //cla.tf2.setText(str);
    }

    void sskwebRx(int format) {
        PhoneUi cla = this;
        String str;
        cla.sskweb.datain_f = 0;
        cla.sskweb.connect_f = 1;
        byte[] bytes = new byte[cla.sskweb.inbuf_len];
        for (int i = 0; i < cla.sskweb.inbuf_len; i++) {
            bytes[i] = cla.sskweb.inbuf[i];
        }
        str = new String(bytes);
        cla.tf3.setText(str);
        if (str.equals("Database is changed 0")) {
        }
    }

    void sskioRx(int format) {

        PhoneUi cla = this;
        String str;
        cla.sskio.datain_f = 0;
        cla.sskio.connect_f = 1;
        if (cla.sskio.inbuf[0] == 0x50) //status chg
        {
            if (cla.sskio.inbuf[1] == 0x18) {
                keyflag = cla.sskio.inbuf[2];
                ledflag = cla.sskio.inbuf[4] * 256 + cla.sskio.inbuf[3];
                if ((keyflag & 255) != 0xff) {
                    if (keypush == 0) {
                        keypush = 1;
                        //cla.lb1.setText("Key In: " + keyflag);
                        if (cla.menu_on_f == 0 && cla.message_on_f == 0) {
                            if (cla.cmdin_f != 0) {
                                return;
                            }
                            switch (keyflag & 255) {
                                case 1://1
                                    cla.cmdin_str = "1";
                                    cla.cmdin_f = 2;
                                    break;
                                case 2://2
                                    cla.cmdin_str = "2";
                                    cla.cmdin_f = 2;
                                    break;
                                case 3://3
                                    cla.cmdin_str = "3";
                                    cla.cmdin_f = 2;
                                    break;
                                case 5://4
                                    cla.cmdin_str = "4";
                                    cla.cmdin_f = 2;
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
                                    cla.cmdin_str = "hangOff";
                                    cla.cmdin_f = 2;
                                    break;
                                case 17://upup
                                    cla.cmdin_str = "upup";
                                    cla.cmdin_f = 2;
                                    break;
                                case 18://speaker
                                    cla.cmdin_str = "hangOn";
                                    cla.cmdin_f = 2;
                                    break;
                                case 19://hangon
                                    cla.cmdin_str = "hangOn";
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
                                    break;
                                case 27://right 
                                    break;
                                case 20://ok 
                                    cla.cmdin_str = "ok";
                                    cla.cmdin_f = 2;
                                    break;
                                case 21://mute
                                    break;
                                case 22://sub 
                                    break;
                                case 23://add 
                                    break;
                                case 28://M
                                    break;
                                case 29://F
                                    break;
                                case 30://Light
                                    break;
                                case 31://book
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
                                case 13://*
                                    cla.menu1.cmdin_str = "*";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 14://0
                                    cla.menu1.cmdin_str = "0";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 15://#
                                    cla.menu1.cmdin_str = "#";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 0://f1
                                    cla.menu1.cmdin_str = "f1";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 4://f2
                                    cla.menu1.cmdin_str = "f2";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 8://f3
                                    cla.menu1.cmdin_str = "f3";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 12://f4
                                    cla.menu1.cmdin_str = "f4";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 16://hang off
                                    cla.menu1.cmdin_str = "hangOff";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 17://upup
                                    cla.menu1.cmdin_str = "upup";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 18://speaker
                                    cla.menu1.cmdin_str = "hangOn";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 19://hangon
                                    cla.menu1.cmdin_str = "hangOn";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 24://up
                                    cla.menu1.cmdin_str = "esc";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 25://left
                                    cla.menu1.cmdin_str = "return";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 26://down  
                                    break;
                                case 27://right 
                                    break;
                                case 20://ok 
                                    cla.menu1.cmdin_str = "ok";
                                    cla.menu1.cmdin_f = 2;
                                    break;
                                case 21://mute
                                    break;
                                case 22://sub 
                                    break;
                                case 23://add 
                                    break;
                                case 28://M
                                    break;
                                case 29://F
                                    break;
                                case 30://Light
                                    break;
                                case 31://book
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
                                    cla.menu1.inp1.cmdin_str = "clear";
                                    cla.menu1.inp1.cmdin_f = 2;
                                    break;
                                case 0://f1
                                    break;
                                case 4://f2
                                    break;
                                case 8://f3
                                    break;
                                case 12://f4
                                    break;
                                case 16://hang off
                                    break;
                                case 17://upup
                                    break;
                                case 18://speaker
                                    break;
                                case 19://hangon
                                    break;
                                case 24://up
                                    cla.menu1.inp1.cmdin_str = "esc";
                                    cla.menu1.inp1.cmdin_f = 2;
                                    break;
                                case 25://left
                                    cla.menu1.inp1.cmdin_str = "back";
                                    cla.menu1.inp1.cmdin_f = 2;
                                    break;
                                case 26://down  
                                    break;
                                case 27://right 
                                    break;
                                case 20://ok 
                                    cla.menu1.inp1.cmdin_str = "enter";
                                    cla.menu1.inp1.cmdin_f = 2;
                                    break;
                                case 21://mute
                                    break;
                                case 22://sub 
                                    break;
                                case 23://add 
                                    break;
                                case 28://M
                                    break;
                                case 29://F
                                    break;
                                case 30://Light
                                    break;
                                case 31://book
                                    break;

                            }

                        }

                    }

                } else {
                    keypush = 0;
                }

            }

        }

    }

    void onShow() {
        PhoneUi cla = this;
        int ibuf, jbuf;
        Rectangle r = new Rectangle();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        cla.lb1.setText(cla.title_str);
        cla.pn2.setVisible(true);
        cla.tf2.setText(cla.initv1_str);
        cla.tf3.setText(cla.initv2_str);
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
        cla.pn1.setLayout(null);
        cla.pn2.setLayout(null);
        cla.pn3.setLayout(null);
        cla.pn4.setLayout(null);

        MyLayout.ctrA[0] = cla.pnMain;
        MyLayout.rateW = 1.0;
        MyLayout.gridLy();

        MyLayout.ctrA[0] = cla.pn1;
        MyLayout.rateW = 0.8;
        MyLayout.gridLy();
        //=================================
        MyLayout.xst = MyLayout.xend;
        MyLayout.ctrA[0] = cla.pn2;
        MyLayout.rateW = 1;
        MyLayout.gridLy();

        //==============================================================
        MyLayout.ctrA[0] = cla.lb1;
        MyLayout.rateH = 0.08;
        MyLayout.gridLy();
        //=================================
        MyLayout.yst = MyLayout.yend;
        MyLayout.ctrA[0] = cla.pn3;
        MyLayout.rateH = 0.3;
        MyLayout.gridLy();
        //=================================
        MyLayout.yst = MyLayout.yend;
        MyLayout.ctrA[0] = cla.lb2;
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
        MyLayout.ctrA[0] = cla.pn4;
        MyLayout.rateH = 0.85;
        MyLayout.gridLy();
        //cla.sp1.setVisible(false);
        //=================================
        ibuf = MyLayout.yend;
        MyLayout.yst = MyLayout.yend;
        MyLayout.ctrA[0] = cla.tf1;
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
            cla.bta1[i].setBackground(Color.lightGray);
            cla.bta1[i].setVisible(true);

        }
        MyLayout.eleAmt = cla.bta1.length;
        MyLayout.xc = 1;
        MyLayout.yc = cla.bta1.length;
        //MyLayout.ym = 30;
        //MyLayout.tm = 30;
        //MyLayout.bm = 30;
        MyLayout.gridLy();
        //=================================
        MyLayout.ctrA[0] = cla.tf2;
        MyLayout.ctrA[1] = cla.tf3;
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

    }

    void cmd(String cmdstr) {
        if (cmdstr.equals("clear")) {
            cmd(2 * 256 + 0);
            return;
        }
        if (cmdstr.equals("esc")) {
            cmd(2 * 256 + 1);
            return;
        }
        if (cmdstr.equals("f1")) {
            cmd(1 * 256 + 0);
            return;
        }
        if (cmdstr.equals("f2")) {
            cmd(1 * 256 + 1);
            return;
        }
        if (cmdstr.equals("f3")) {
            cmd(1 * 256 + 2);
            return;
        }
        if (cmdstr.equals("f4")) {
            cmd(1 * 256 + 3);
            return;
        }
        if (cmdstr.equals("1")) {
            cmd(3 * 256 + 7 * 0 + 0);
            return;
        }
        if (cmdstr.equals("2")) {
            cmd(3 * 256 + 7 * 0 + 1);
            return;
        }
        if (cmdstr.equals("3")) {
            cmd(3 * 256 + 7 * 0 + 2);
            return;
        }
        if (cmdstr.equals("4")) {
            cmd(3 * 256 + 7 * 1 + 0);
            return;
        }
        if (cmdstr.equals("5")) {
            cmd(3 * 256 + 7 * 1 + 1);
            return;
        }
        if (cmdstr.equals("6")) {
            cmd(3 * 256 + 7 * 1 + 2);
            return;
        }

        if (cmdstr.equals("7")) {
            cmd(3 * 256 + 7 * 2 + 0);
            return;
        }
        if (cmdstr.equals("8")) {
            cmd(3 * 256 + 7 * 2 + 1);
            return;
        }
        if (cmdstr.equals("9")) {
            cmd(3 * 256 + 7 * 2 + 2);
            return;
        }

        if (cmdstr.equals("*")) {
            cmd(3 * 256 + 7 * 3 + 0);
            return;
        }
        if (cmdstr.equals("0")) {
            cmd(3 * 256 + 7 * 3 + 1);
            return;
        }
        if (cmdstr.equals("#")) {
            cmd(3 * 256 + 7 * 3 + 2);
            return;
        }
        if (cmdstr.equals("hangOn")) {
            cmd(3 * 256 + 7 * 3 + 3);
            return;
        }
        if (cmdstr.equals("hangOff")) {
            cmd(3 * 256 + 7 * 3 + 4);
            return;
        }

    }

    void cmd(int index) {
        PhoneUi cla = this;
        String str;

        //========================================
        switch (index) {
            //tf1
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
                break;
            case 1 * 256 + 1:
                sskweb.tx_ip = "192.168.0.30";
                sskweb.tx_port = 1334;
                sskweb.tx_str = "Tx From sskWeb";
                byte[] txbyte = new byte[4];
                txbyte[0] = 'a';
                txbyte[1] = 'b';
                txbyte[2] = 'c';
                txbyte[3] = 'd';
                sskweb.tx_bytes = txbyte;
                sskweb.tx_start_f = 2;
                //sskweb.txip("127.0.0.1","12345678",1236);
                break;
            case 1 * 256 + 2:
                break;
            case 1 * 256 + 3:   //menu_on
                break;
            //phone button    
            //=================================    
            case (3 * 256 + 7 * 0 + 0):
                break;
            case (3 * 256 + 7 * 0 + 1):
                break;
            case (3 * 256 + 7 * 0 + 2):
                break;
            case (3 * 256 + 7 * 0 + 3):
                break;
            case (3 * 256 + 7 * 0 + 4):
                break;
            case (3 * 256 + 7 * 0 + 5):
                break;
            case (3 * 256 + 7 * 0 + 6):
                break;

            case (3 * 256 + 7 * 1 + 0):
                break;
            case (3 * 256 + 7 * 1 + 1):
                break;
            case (3 * 256 + 7 * 1 + 2):
                break;
            case (3 * 256 + 7 * 1 + 3):
                break;
            case (3 * 256 + 7 * 1 + 4):
                break;
            case (3 * 256 + 7 * 1 + 5):
                break;
            case (3 * 256 + 7 * 1 + 6):
                break;

            case (3 * 256 + 7 * 2 + 0):
                break;
            case (3 * 256 + 7 * 2 + 1):
                break;
            case (3 * 256 + 7 * 2 + 2):
                break;
            case (3 * 256 + 7 * 2 + 3):
                break;
            case (3 * 256 + 7 * 2 + 4):
                break;
            case (3 * 256 + 7 * 2 + 5):
                break;
            case (3 * 256 + 7 * 2 + 6):
                break;

            case (3 * 256 + 7 * 3 + 0):
                break;
            case (3 * 256 + 7 * 3 + 1):
                break;
            case (3 * 256 + 7 * 3 + 2):
                break;
            case (3 * 256 + 7 * 3 + 3):
                break;
            case (3 * 256 + 7 * 3 + 4):
                break;
            case (3 * 256 + 7 * 3 + 5):
                int ibuf;
                ibuf = Lib.ping("192.168.0.253");

                break;
            case (3 * 256 + 7 * 3 + 6):
                break;

        }

    }

    void editpad(char ch) {

    }

}

//==================================================================================================================================================
class PhoneUiWinLis extends WindowAdapter {

    PhoneUi cla;

    PhoneUiWinLis(PhoneUi owner) {
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

class PhoneUiMsLis extends MouseAdapter {

    int enkey_f;
    PhoneUi cla;

    PhoneUiMsLis(PhoneUi owner) {
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
        //cla.cmd(index);
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

class PhoneUiKeyLis extends KeyAdapter {

    PhoneUi cla;

    PhoneUiKeyLis(PhoneUi owner) {
        cla = owner;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int index;
        index = Integer.parseInt(e.getComponent().getName());
        if (index == 4 * 256 + 0) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                String shellCommand;
                shellCommand = cla.tf1.getText() + " \n";
                cla.tf1.setText("");
            }
        }
    }

}

//for display 20ms
//========================================================
class PhoneUiTm1 implements ActionListener {

    String str;
    PhoneUi cla;
    int ssktx_tim;
    int ssktx_cnt;

    PhoneUiTm1(PhoneUi owner) {
        cla = owner;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (++ssktx_tim > 5) {
            ssktx_tim = 0;
            if (cla.ssksip.tx_start_f != 0) {
                return;
            }
            int stx_index = 0;
            cla.ssksip.stm.tbuf[stx_index++] = (byte) 0xD2;
            cla.ssksip.stm.tbuf[stx_index++] = (byte) 0x10;//fid:Get status
            cla.ssksip.stm.tbuf[stx_index++] = (byte) 0x00;//fid:len
            cla.ssksip.stm.tbuf[stx_index++] = (byte) 0x11;//fid:direct command
            cla.ssksip.stm.tbuf[stx_index++] = (byte) 9;//fid_len
            cla.ssksip.stm.tbuf[stx_index++] = (byte) 'c';
            cla.ssksip.stm.tbuf[stx_index++] = (byte) 'a';
            cla.ssksip.stm.tbuf[stx_index++] = (byte) 'l';
            cla.ssksip.stm.tbuf[stx_index++] = (byte) 'l';
            cla.ssksip.stm.tbuf[stx_index++] = (byte) ' ';
            cla.ssksip.stm.tbuf[stx_index++] = (byte) '3';
            cla.ssksip.stm.tbuf[stx_index++] = (byte) '0';
            cla.ssksip.stm.tbuf[stx_index++] = (byte) '3';
            cla.ssksip.stm.tbuf[stx_index++] = (byte) '\n';
            //==============================================
            
            
            cla.ssksip.stm.tbuf_byte = stx_index;
            cla.ssksip.stm.enc_mystm();
            
            byte[] bytes = new byte[cla.ssksip.stm.txlen];
            for (int i = 0; i < cla.ssksip.stm.txlen; i++) {
                bytes[i] = cla.ssksip.stm.tdata[i];
            }
            
            cla.ssksip.tx_bytes = bytes;
            cla.ssksip.tx_ip = "127.0.0.1";
            cla.ssksip.tx_port = 1236;
            cla.ssksip.tx_start_f = 4;
            ssktx_cnt++;

        }

        if (!cla.tf2.getText().equals(cla.status_str)) {
            cla.tf2.setText(cla.status_str);
        }
        if (!cla.tf3.getText().equals(cla.action_str)) {
            cla.tf3.setText(cla.action_str);
        }
    }

}

//for command
//========================================================
class PhoneUiTm2 implements ActionListener {

    String str;
    PhoneUi cla;

    PhoneUiTm2(PhoneUi owner) {
        cla = owner;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        if (cla.cmdin_f != 0) {
            if (cla.cmdin_f == 1) {
                cla.cmd(cla.cmdin_inx);
            }
            if (cla.cmdin_f == 2) {
                cla.cmd(cla.cmdin_str);
            }
            cla.cmdin_f = 0;
        }

    }
}
