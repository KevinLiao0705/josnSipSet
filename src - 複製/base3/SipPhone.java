/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Administrator
 */
public class SipPhone {

    static SipPhone scla;
    Object owner;
    String lcd1;
    String lcd2;
    String status;
    int status_f;
    int linphone_connect_tim = 0;
    int holdRelease_tim;
    int ict_connect_tim = 0;
    int ict_connected_f = 0;
    String ictCommandStr = "";
    int ictCommandTim = 0;
    int ictCheckPhno_f = 0;
    String ictCheckPhno_str = "";
    int dtmf_enable_f = 0;
    String ictPreData = "";
    
    long connected_tim=0;

    int sipCommandTim = 0;
    String sipCommandStr = "";

    //===============================
    String connectId_str = "";
    String connectNo_str = "";
    String status_str = "";
    String action_str = "";
    String keypad_str = "";
    String callToStr = "";
    int keypad_tim = 0;
    int keypad_on_f = 0;
    int auto_answer_tim = 0;

    int txSipInf_step = 0;

    int menu_on_tim = 0;
    int menu_on_f = 0;

    String setId = "";
    String setting_str = "";
    int setting_tim = 0;
    int setting_on_f = 0;
    int mute_f = 0;

    byte[] sipflag = new byte[4];
    int sipStatus = 0;         //0 no raspberryPi,1:raspberry pi ready,2:linphonec load,3:pbx registed,4:on call 
    int connected_cnt = 0;      //0:no connect 1:call to 2:call from 3:connected
    int shellCommandStatus = 0;      //0:ready,1:play dial tone
    int handStatus = 0;      //0:handon,1:earPhone on,2:spaeker on
    String callto = "";
    String callfrom = "";
    String callConnectNo = "";
    String callConnectId = "";
    String callConnectName = "";
    //String callfromId = "";
    //String callfromName = "";
    //==============================
    //int speakerVolume = 4;      //mix=0,max=45
    //int earphoneVolume = 4;     //mix=0,max=45
    //int micPhoneVolume = 4;     //mix=0,max=30
    int[] outVolumeTbl = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45};
    int[] inVolumeTbl = {0, 3, 6, 9, 12, 15, 18, 21, 25, 30};
    int shlFirstIn_f = 0;
    int ngrepFirstIn_f = 0;
    int ictFirstIn_f = 0;
    int broadcast_tim = 0;
    int broadcast_f = 0;
    String[] prenoStrA = new String[10];
    int preno_inx = 0;
    int preno_cnt = 0;

    String dtmf;
    int status_tim;
    int linphone_load_f = 0;
    //============================
    Ssh sshSip = null;
    SiprxTd siprxTd = null;
    SipconTd sipconTd = null;
    int siprxTd_run_f = 0;
    int siprxTd_destroy_f = 0;
    int sipconTd_run_f = 0;
    int sipconTd_destroy_f = 0;
    SipPhoneRx sipPhoneRx;
    //============================
    Ssh sshShl = null;
    ShlrxTd shlrxTd = null;
    ShlconTd shlconTd = null;
    int shlrxTd_run_f = 0;
    int shlrxTd_destroy_f = 0;
    int shlconTd_run_f = 0;
    int shlconTd_destroy_f = 0;
    ShellRx shellRx;
    //============================
    Ssh sshNgrep = null;
    NgreprxTd ngreprxTd = null;
    NgrepconTd ngrepconTd = null;
    int ngreprxTd_run_f = 0;
    int ngreprxTd_destroy_f = 0;
    int ngrepconTd_run_f = 0;
    int ngrepconTd_destroy_f = 0;
    NgrepRx ngrepRx;
    //============================
    Ssh sshIct = null;
    IctrxTd ictrxTd = null;
    IctconTd ictconTd = null;
    int ictrxTd_run_f = 0;
    int ictrxTd_destroy_f = 0;
    int ictconTd_run_f = 0;
    int ictconTd_destroy_f = 0;
    IctRx ictRx;
    //============================

    Timer tm1 = null;//for display
    Ssocket sskio;    //from nkv6in1_io
    Ssocket sskweb; //from web
    Ssocket sskui; //from web
    int sskui_tx_cnt;

    Vt100 vtsip;
    Vt100 vtshl;
    Vt100 vtngrep;
    Vt100 vtict;

    SipPhone() {
        int i;
        for (i = 0; i < 10; i++) {
            prenoStrA[i] = "";
        }
        preno_inx = 0;
    }

    public void create() {

        int i = 0;
        if (i == 1) {
            Telnet tel = new Telnet("client", "192.168.3.230");
            return;
        }

        final SipPhone cla = this;
        cla.status_str = "Josn Sip Phone,JSPU001 Ver:1.2";
        cla.action_str = "Initial...... Please Wait";
        //=================================================
        vtsip = new Vt100(cla);
        vtsip.clr_telscr();
        vtsip.vtcmp = new Vtcmp() {
            @Override
            public void cmp() {
                if (GB.linphone_twinkle_f == 0) {
                    cla.vtcmpSip();
                } else {
                    cla.vtcmpTwinkle();
                }
            }
        };
        //linphone ssh rx thread
        if (cla.siprxTd == null) {
            cla.siprxTd = new SiprxTd(cla);
            cla.siprxTd.start();
            cla.siprxTd_run_f = 1;
            cla.siprxTd_destroy_f = 0;
        }
        //connect linphone thread
        if (cla.sipconTd == null) {
            cla.sipconTd = new SipconTd(cla);
            cla.sipconTd.start();
            cla.sipconTd_run_f = 1;
            cla.sipconTd_destroy_f = 0;
        }
        //===================================================
        vtshl = new Vt100(cla);
        vtshl.clr_telscr();
        vtshl.vtcmp = new Vtcmp() {
            @Override
            public void cmp() {
                cla.vtcmpShl();
            }
        };
        //linphone ssh rx thread
        if (cla.shlrxTd == null) {
            cla.shlrxTd = new ShlrxTd(cla);
            cla.shlrxTd.start();
            cla.shlrxTd_run_f = 1;
            cla.shlrxTd_destroy_f = 0;
        }
        //connect linphone thread
        if (cla.shlconTd == null) {
            cla.shlconTd = new ShlconTd(cla);
            cla.shlconTd.start();
            cla.shlconTd_run_f = 1;
            cla.shlconTd_destroy_f = 0;
        }
        //===================================================
        if (GB.ngrep_on_f != 0) {
            vtngrep = new Vt100(cla);
            vtngrep.clr_telscr();
            vtngrep.vtcmp = new Vtcmp() {
                @Override
                public void cmp() {
                    cla.vtcmpNgrep();
                }
            };
            //linphone ssh rx thread
            if (cla.ngreprxTd == null) {
                cla.ngreprxTd = new NgreprxTd(cla);
                cla.ngreprxTd.start();
                cla.ngreprxTd_run_f = 1;
                cla.ngreprxTd_destroy_f = 0;
            }
            //connect linphone thread
            if (cla.ngrepconTd == null) {
                cla.ngrepconTd = new NgrepconTd(cla);
                cla.ngrepconTd.start();
                cla.ngrepconTd_run_f = 1;
                cla.ngrepconTd_destroy_f = 0;
            }
        }
        //===================================================
        if (GB.ictcon_on_f != 0) {
            vtict = new Vt100(cla);
            vtict.clr_telscr();
            vtict.vtcmp = new Vtcmp() {
                @Override
                public void cmp() {
                    cla.vtcmpIct();
                }
            };
            if (cla.ictrxTd == null) {
                cla.ictrxTd = new IctrxTd(cla);
                cla.ictrxTd.start();
                cla.ictrxTd_run_f = 1;
                cla.ictrxTd_destroy_f = 0;
            }
            if (cla.ictconTd == null) {
                cla.ictconTd = new IctconTd(cla);
                cla.ictconTd.start();
                cla.ictconTd_run_f = 1;
                cla.ictconTd_destroy_f = 0;
            }
        }
        //===================================================

        //general timer
        if (cla.tm1 == null) {

            cla.tm1 = new Timer();
            //設定計時器
            //第一個參數為"欲執行的工作",會呼叫對應的run() method
            //第二個參數為程式啟動後,"延遲"指定的毫秒數後"第一次"執行該工作
            //第三個參數為每間隔多少毫秒執行該工作
            tm1.schedule(new SipPhoneTm1(cla), 1000, 20);
            //cla.tm1 = new Timer(20, new SipPhoneTm1(cla));
            //cla.tm1.start();
        }
        //for io(io,uart,i2c,spi...)
        //======================================
        sskio = new Ssocket();
        sskio.format = 1;
        sskio.rxcon_ltim = 100;//unit 10ms
        sskio.create(1234);
        sskio.sskRx = new SskRx() {
            @Override
            public void sskRx(int format) {
                cla.sskioRx(format);
            }
        };
        sskio.start();
        //for web
        //=======================================
        sskweb = new Ssocket();     //for web
        sskweb.format = 0;
        sskweb.rxcon_ltim = 100;    //unit 10ms
        sskweb.create(1235);
        sskweb.sskRx = new SskRx() {
            @Override
            public void sskRx(int format) {
                cla.sskwebRx(format);
            }
        };
        sskweb.start();
        //=====================================
        //for ui
        //======================================
        sskui = new Ssocket();
        sskui.format = 1;
        sskui.rxcon_ltim = 100;//unit 10ms
        sskui.create(1236);
        sskui.sskRx = new SskRx() {
            @Override
            public void sskRx(int format) {
                cla.sskuiRx(format);
            }
        };
        sskui.start();

    }

    void vtcmpNgrep() {
        SipPhone cla = this;
        int i = 0;
        String str;

        //============================================
        if (ngrepFirstIn_f == 0) {
            if (cla.vtngrep.ncmp("\npi@raspberrypi:~$")) {
                ngrepFirstIn_f = 1;
                str = "sudo ngrep -q \"" + GB.broadcast_comp_str + "\" port " + GB.broadcast_comp_port + "\n";
                cla.sshWriteNgrep(str);
                //System.out.println("\nNgrep Shell ready");
                return;
            }
            return;
        } else {
            broadcast_tim = 0;
            broadcast_f = 1;
        }
    }

    void vtcmpIct() {
        SipPhone cla = this;
        int i = 0;
        String str;

        //============================================
        if (ict_connected_f == 0) {
            //ictFirstIn_f = 1;
            if (cla.vtict.ncmp("\npi@raspberrypi:~$")) {
                cla.ictCommandStr = "sudo telnet " + GB.sip_server_ip + " 23" + "\n";
                cla.ictCommandTim = 0;
                return;
            }
            if (cla.vtict.ncmp("\nlogin:")) {
                cla.ictCommandStr = GB.ict_username + "\n";
                cla.ictCommandTim = 0;
                return;
            }
            if (cla.vtict.ncmp("\nPassword:")) {
                cla.ictCommandStr = GB.ict_password + "\n";
                cla.ictCommandTim = 0;
                return;
            }
            if (cla.vtict.ncmp("\nACD VERSION")) {
                cla.ictCommandStr = "ippstat" + "\n";
                cla.ictCommandTim = -100;
                return;
            }
            if (cla.vtict.ncmp("Enter your choice :")) {
                cla.ictCommandStr = "3" + "\n";
                cla.ictCommandTim = 0;
                cla.ictCheckPhno_f = 1;
                return;
            }
            if (cla.vtict.ncmp("return to menu : press ENTER")) {
                if (cla.ictCheckPhno_f == 1) {
                    String bstr = cla.ictPreData + cla.vtict.incha;
                    String[] strA = bstr.split("\n");
                    String strb;
                    GB.ictPhnos_amt = 0;
                    System.out.println("\n******************** Ict Phone no *************************");
                    for (int k = 0; k < strA.length; k++) {
                        strb = strA[k].trim();
                        if (strA[k].contains("IPv4")) {
                            String[] strB = strb.split("\\s+");
                            for (int m = 0; m < strB.length; m++) {
                                if (strB[m].equals("IPv4")) {
                                    if (!strB[m + 1].equals("|Unused")) {
                                        String phNo = "";
                                        for (int n = 1; n < strB[0].length(); n++) {
                                            phNo += strB[0].charAt(n);
                                        }
                                        System.out.println(phNo);
                                        GB.ictPhnos[GB.ictPhnos_amt++] = phNo;
                                    }
                                    break;
                                }
                            }
                            k += 0;
                        }
                    }
                    System.out.println("*********************************************");
                }
                cla.ictCheckPhno_f = 0;
                cla.ictCommandStr = "\n";
                cla.ictCommandTim = -3000;
                return;
            }

        } else {
        }
    }

    void vtcmpShl() {
        SipPhone cla = this;
        int i = 0;
        String str;
        //============================================
        if (cla.vtshl.cmp("\npi@raspberrypi:~$")) {
            if (shlFirstIn_f == 0) {
                shlFirstIn_f = 1;
                str = "sudo amixer cset numid=6 " + outVolumeTbl[GB.phset_speaker_vol] + "," + outVolumeTbl[GB.ear_speaker_vol] + "\n";
                cla.sshWriteShl(str);
                str = "sudo amixer cset numid=8 " + inVolumeTbl[GB.phset_mic_sens] + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";
                cla.sshWriteShl(str);

            }
            if (shellCommandStatus == 1) {
                shellCommandStatus = 0;
                if (sipStatus <= 3) {
                    handStatus = 0;
                }
            }
            return;
        }
        if (cla.vtshl.cmp("\nPlaying WAVE")) {
            shellCommandStatus = 1;
            return;
        }
    }

    void vtcmpTwinkle() {
        SipPhone cla = this;
        int i = 0;
        String str;
        String[] strA;
        cla.linphone_connect_tim = 0;
        //============================================
        if (cla.vtsip.ncmp("\npi@raspberrypi:~$")) {
            if (GB.syssec_f == 1) {
                if (cla.sipStatus == 0) {
                    cla.sshWriteSip("twinkle -c\n");
                    cla.status_str = "Init Sip.....";
                } else {
                    cla.sshWriteSip("twinkle -c\n");  //<<debug
                }
            }
            cla.sipStatus = 1;
            linphone_load_f = 0;
            return;
        }

        if (cla.sipStatus <= 1) {
            if (cla.vtsip.ncmp("\nTwinkle>")) {
                cla.linphone_load_f = 1;
                cla.status_str = "Registing Sip PBX.....";
                cla.sipStatus = 2;
                return;
            }
        }

        /*        
Users:
* twinkle
    Kevin <sip:301@192.168.0.45>
         */
        if (cla.sipStatus >= 0) {
            if (cla.vtsip.ncmpA("registration succeeded")) {
                cla.status_str = "Registration Ok";
                cla.action_str = "Ready";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                return;
            }

        }

        if (cla.sipStatus >= 0) {
            if (cla.vtsip.ncmpA("far end supports DTMF")) {
                cla.dtmf_enable_f = 1;
                return;
            }

        }

        if (cla.sipStatus >= 3) {
            if (cla.vtsip.ncmpA("far end answered call.")) {
                cla.sshWriteSip("hold\n");  //<<debug
                cla.sshWriteSip("retrieve\n");  //<<debug
                //cla.sshWriteSip("retrieve\n");  //<<debug
                //cla.sshWriteSip("retrieve\n");  //<<debug
                cla.sipCommandTim = 3;
                cla.sipCommandStr = "retrieve\n";
                return;
            }

        }

        if (cla.sipStatus == 3) {

            if (cla.vtsip.ncmpA("404 Not Found")) {
                cla.status_str = "Call To " + cla.callto + " Not Found";
                cla.action_str = "Ready";
                cla.callto = "";
                cla.callfrom = "";
                cla.handStatus = 0;
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.dndOff();
                return;
            }

            if (cla.vtsip.ncmpA("received * Ringing")) {
                cla.status_str = "Ringing....";
                cla.action_str = callToStr;
                cla.status_tim = 1000;
                cla.connected_cnt = 0;
                return;
            }

            if (cla.vtsip.ncmpA("200 OK\nTo: sip:*\n")) {
                strA = cla.vtsip.cmpAstr.split("@");
                if (strA.length == 2) {
                    cla.callto = strA[0];
                    cla.callfrom = "";
                    cla.status_str = "Call < " + cla.callto + " >";
                    cla.action_str = "Call " + cla.callto;
                    cla.callConnectName = strA[0];
                    cla.callConnectNo = strA[0];
                    cla.callConnectId = cla.vtsip.cmpAstr;
                    cla.status_tim = 100;
                    cla.connected_cnt = 1;
                    cla.holdRelease_tim = 0;
                    cla.sipStatus = 5;
                    return;
                }
                return;
            }

            /*
            if (cla.vtsip.ncmpB('*', "incoming call\nFrom:*<sip:*>")) {
                strA = cla.vtsip.cmpAstr.split("@");
                if (strA.length == 2) {
                    cla.callfrom = strA[0];
                    cla.callto = "";
                    cla.callConnectNo = strA[0];
                    cla.callConnectName = cla.vtsip.cmpBstr.trim();
                    cla.status_str = "Call from " + cla.callConnectName + " < " + cla.callfrom + " >";
                    cla.action_str = "Call from " + cla.callfrom;
                    cla.connected_cnt = 2;
                    cla.sipStatus = 4;
                    cla.auto_answer_tim = 0;
                    cla.status_tim = 100;
                    return;
                }
                return;
            }
             */
            if (cla.vtsip.ncmpB('*', "incoming call\nFrom:*<*>")) {
                if (cla.vtsip.cmpAstr.length() > 30) {
                    return;
                }
                if (cla.vtsip.cmpBstr.length() > 20) {
                    return;
                }
                strA = cla.vtsip.cmpAstr.split("@");
                if (strA.length == 2) {
                    cla.callfrom = strA[0];
                    String[] strB = strA[0].split(":");
                    if (strB.length == 2) {
                        cla.callfrom = strB[1];
                    } else {
                        cla.callfrom = strA[0];
                    }
                } else {
                    cla.callfrom = cla.vtsip.cmpAstr;
                }
                cla.callto = "";
                cla.callConnectNo = cla.callfrom;
                cla.callConnectName = cla.vtsip.cmpBstr.trim();
                cla.status_str = "Call from " + cla.callConnectName + " < " + cla.callfrom + " >";
                cla.action_str = "Call from " + cla.callfrom;
                cla.connected_cnt = 2;
                cla.holdRelease_tim = 0;
                cla.sipStatus = 5;
                cla.auto_answer_tim = 0;
                cla.status_tim = 100;
                return;
            }

        }

        if (cla.sipStatus >= 4) {

            if (cla.vtsip.ncmpB('*', "incoming call\nFrom:*<*>")) {
                if (cla.vtsip.cmpAstr.length() > 30) {
                    return;
                }
                if (cla.vtsip.cmpBstr.length() > 20) {
                    return;
                }
                strA = cla.vtsip.cmpAstr.split("@");
                if (strA.length == 2) {
                    cla.callfrom = strA[0];
                    String[] strB = strA[0].split(":");
                    if (strB.length == 2) {
                        cla.callfrom = strB[1];
                    } else {
                        cla.callfrom = strA[0];
                    }
                } else {
                    cla.callfrom = cla.vtsip.cmpAstr;
                }
                cla.callto = "";
                cla.callConnectNo = cla.callfrom;
                cla.callConnectName = cla.vtsip.cmpBstr.trim();
                cla.status_str = "Call from " + cla.callConnectName + " < " + cla.callfrom + " >";
                cla.action_str = "Call from " + cla.callfrom;
                cla.connected_cnt = 2;
                cla.holdRelease_tim = 0;
                cla.sipStatus = 5;
                cla.auto_answer_tim = 0;
                cla.status_tim = 100;
                return;
            }

            if (cla.vtsip.ncmpA("Line 1: answer timeout.")) {
                cla.status_str = "Answer Timeout";
                cla.action_str = "Ready";
                cla.callto = "";
                cla.callfrom = "";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                cla.handStatus = 0;
                cla.dtmf_enable_f = 0;
                cla.dndOff();
                hangOn();
                return;
            }

            if (cla.vtsip.ncmpA("Line 1: far end cancelled call.")) {
                cla.status_str = "Far End Cancelled Call";
                cla.action_str = "Ready";
                cla.callto = "";
                cla.callfrom = "";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                cla.handStatus = 0;
                cla.dtmf_enable_f = 0;
                cla.dndOff();
                hangOn();

                return;
            }

            if (cla.vtsip.ncmpA("Line 1: far end ended call.")) {
                cla.status_str = "Far End Ended Call";
                cla.action_str = "Ready";
                cla.callto = "";
                cla.callfrom = "";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                cla.handStatus = 0;
                cla.dtmf_enable_f = 0;
                cla.dndOff();
                hangOn();
                return;
            }

            if (cla.vtsip.ncmpA("call ended.")) {
                cla.status_str = "Call Ended";
                cla.action_str = "Ready";
                cla.callto = "";
                cla.callfrom = "";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                cla.handStatus = 0;
                cla.dtmf_enable_f = 0;
                cla.dndOff();
                hangOn();
                return;
            }

            if (cla.vtsip.ncmpA("Line muted.")) {
                cla.mute_f = 1;
                return;
            }

            if (cla.vtsip.ncmpA("Line unmuted.")) {
                cla.mute_f = 0;
                return;
            }

        }

        //============================================
        str = "Receiving tone ";
        str += GB.ptt_on_no;
        str += " from";
        if (cla.vtsip.cmp(str)) {
//            cla.pttOn();
            return;
        }
        //============================================
        str = "Receiving tone ";
        str += GB.ptt_off_no;
        str += " from";
        if (cla.vtsip.cmp(str)) {
//            cla.pttOff();
        }

    }

    void vtcmpSip() {
        SipPhone cla = this;
        int i = 0;
        String str;
        String[] strA;
        cla.linphone_connect_tim = 0;
        //============================================
        if (cla.vtsip.ncmp("\nSegmentation fault")) {
            cla.sipStatus = 0;
            linphone_load_f = 0;
            return;
        }

        if (cla.vtsip.ncmp("\npi@raspberrypi:~$")) {
            if (GB.syssec_f == 1) {
                if (cla.sipStatus == 0) {
                    cla.sshWriteSip("linphonec\n");
                    cla.status_str = "Init Sip.....";
                } else {
                    cla.sshWriteSip("linphonec\n");
                }
            }
            cla.sipStatus = 1;
            linphone_load_f = 0;
            return;
        }

        if (cla.sipStatus <= 1) {
            if (cla.vtsip.ncmp("\nlinphonec>")) {
                cla.linphone_load_f = 1;
                cla.status_str = "Registing Sip PBX.....";
                cla.sipStatus = 2;
                return;
            }
        }

        if (cla.sipStatus == 2) {
            if (cla.vtsip.ncmpA("Registration on <sip:*> successful.")) {
                cla.status_str = "Registration Ok On < " + cla.vtsip.cmpAstr + " >";
                cla.action_str = "Ready";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                return;
            }

        }

        if (cla.sipStatus == 3) {

            /*
            if (cla.vtsip.ncmpA("linphonec> call * ")) {
                //cla.action_str = "Call "+cla.vt.cmpAstr;
                return;
            }
             */
 /*
            if (cla.vtsip.ncmpA("Establishing call id to <sip:*@")) {
                cla.callto = cla.vtsip.cmpAstr;
                cla.callfrom = "";
                cla.status_str = "Call < " + cla.callto + " >";
                cla.action_str = "Call " + cla.callto;
                cla.callConnectNo=cla.callto;
                cla.callConnectNo=cla.callto;
                cla.status_tim = 100;
                cla.connected_cnt = 1;
                cla.sipStatus = 4;
                return;
            }
             */

 /*
            if (cla.vtsip.ncmpB('*',"Establishing call id to <sip:*>, assigned id *\n")) {
                strA = cla.vtsip.cmpBstr.split("@");
                if (strA.length == 2) {
                    cla.callto = strA[0];
                    cla.callfrom = "";
                    cla.status_str = "Call < " + cla.callto + " >";
                    cla.action_str = "Call " + cla.callto;
                    //cla.callConnectName = strA[0];
                    //cla.callConnectNo = strA[0];
                    //cla.callConnectId = cla.vtsip.cmpAstr;
                    cla.status_tim = 100;
                    cla.connected_cnt = 1;
                    //cla.sipStatus = 4;
                    return;
                }
            }
             */
            if (cla.vtsip.ncmpA("Contacting <sip:*>")) {
                strA = cla.vtsip.cmpAstr.split("@");
                if (strA.length == 2) {
                    cla.status_str = "Contacting " + strA[0];
                    cla.action_str = "Call " + strA[0];
                    cla.callto = strA[0];
                    cla.callfrom = "";
                    cla.status_tim = 100;
                }
                return;
            }

            if (cla.vtsip.ncmpA("Not Found")) {
                cla.status_str = "Call To " + cla.callto + " Not Found";
                cla.action_str = "Ready";
                cla.callto = "";
                cla.callfrom = "";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                return;
            }

            if (cla.vtsip.ncmpB('*', "Media streams established with <sip:*> for call * (audio).")) {
                strA = cla.vtsip.cmpBstr.split("@");
                if (strA.length == 2) {
                    cla.callto = strA[0];
                    cla.callfrom = "";
                    cla.status_str = "Call < " + cla.callto + " >";
                    cla.action_str = "Call " + cla.callto;
                    cla.callConnectName = strA[0];
                    cla.callConnectNo = strA[0];
                    cla.callConnectId = cla.vtsip.cmpAstr;
                    cla.status_tim = 100;
                    cla.connected_cnt = 1;
                    cla.sipStatus = 5;
                    return;
                }
            }

            if (cla.vtsip.ncmpB('*', "Receiving new incoming call from \"*\" <sip:*>, assigned id *\n")) {
                strA = cla.vtsip.cmpBstr.split("@");
                if (strA.length == 2) {
                    cla.callfrom = strA[0];
                    cla.callto = "";
                    cla.callConnectNo = strA[0];
                    cla.callConnectName = cla.vtsip.cmpCstr;
                    cla.callConnectId = cla.vtsip.cmpAstr;
                    cla.status_str = "Call from < " + cla.callfrom + " >";
                    cla.action_str = "Call from " + cla.callfrom;
                    cla.connected_cnt = 2;
                    cla.sipStatus = 5;
                    cla.auto_answer_tim = 0;
                    cla.status_tim = 100;
                    return;
                }
            }

            if (cla.vtsip.ncmpB('*', "Receiving new incoming call from <sip:*>, assigned id *\n")) {
                strA = cla.vtsip.cmpBstr.split("@");
                if (strA.length == 2) {
                    cla.callfrom = strA[0];
                    cla.callto = "";
                    cla.callConnectNo = strA[0];
                    cla.callConnectName = strA[0];
                    cla.callConnectId = cla.vtsip.cmpAstr;
                    cla.status_str = "Call from < " + cla.callfrom + " >";
                    cla.action_str = "Call from " + cla.callfrom;
                    cla.connected_cnt = 2;
                    cla.sipStatus = 5;
                    cla.auto_answer_tim = 0;
                    cla.status_tim = 100;
                    return;
                }
            }

            /*
            if (cla.vtsip.ncmpA("Receiving new incoming call from \"*\" <sip")) {
                cla.callfrom = cla.vtsip.cmpAstr;
                cla.callto = "";
                cla.status_str = "Call from < " + cla.callfrom + " >";
                cla.action_str = "Call from " + cla.callfrom;
                cla.status_tim = 100;
                cla.connected_cnt = 2;
                cla.sipStatus = 4;
                cla.auto_answer_tim = 0;
                return;
            }
             */
 /*
            if (cla.vtsip.ncmpA("Receiving new incoming call from <sip:*@")) {
                cla.callfrom = cla.vtsip.cmpAstr;
                cla.callto = "";
                cla.status_str = "Call from < " + cla.callfrom + " >";
                cla.action_str = "Call from " + cla.callfrom;
                cla.status_tim = 100;
                cla.connected_cnt = 2;
                cla.sipStatus = 4;
                cla.auto_answer_tim = 0;
                return;
            }
             */
        }

        if (cla.sipStatus >= 4) {
            //against hang on 
            /*
            if (cla.vtsip.ncmpA("Call terminated.")) {
                cla.status_str = "Call terminated";
                cla.action_str = "Ready";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                cla.hangOn();
                return;
            }
             */
            if (cla.vtsip.ncmpA("with <sip:*> ended (No error).")) {
                strA = cla.vtsip.cmpAstr.split("@");
                if (strA.length == 2) {
                    if (strA[0].equals(cla.callConnectNo)) {
                        cla.status_str = "Call terminated";
                        cla.action_str = "Ready";
                        cla.status_tim = 100;
                        cla.connected_cnt = 0;
                        cla.sipStatus = 3;
                        cla.hangOn();
                        return;
                    }
                }
            }

            if (cla.vtsip.ncmpA("Call ended")) {
                cla.status_str = "Call Ended";
                cla.action_str = "Ready";
                cla.callto = "";
                cla.callfrom = "";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                return;
            }
            /*
            if (cla.vtsip.ncmpA("Not Found")) {
                cla.status_str = "Not Found";
                cla.action_str = "Ready";
                cla.callto = "";
                cla.callfrom = "";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                return;
            }
             */
            if (cla.vtsip.ncmpA("linphonec> Remote ringing...")) {
                cla.status_str = "Call < " + cla.callto + " >" + " Ringing...";
                return;
            }
            if (cla.vtsip.ncmpA("linphonec> terminate ")) {
                cla.callto = "";
                cla.callfrom = "";
                cla.action_str = "Terminate";
                return;
            }
            if (cla.vtsip.ncmpA("linphonec> Call declined.")) {
                cla.callto = "";
                cla.callfrom = "";
                cla.status_str = "Call declined";
                cla.action_str = "Ready";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                return;
            }
            if (cla.vtsip.ncmpA("User is busy.")) {
                cla.status_str = "User is busy";
                cla.action_str = "Ready";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                return;
            }
            if (cla.vtsip.ncmpA("User is temporarily unavailable.")) {
                cla.status_str = "Unavailable";
                cla.action_str = "Ready";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                return;
            }
            if (cla.vtsip.ncmpA("Call failed.")) {
                cla.status_str = "Call failed";
                cla.action_str = "Ready";
                cla.status_tim = 100;
                cla.connected_cnt = 0;
                cla.sipStatus = 3;
                return;
            }

            /*
            if (cla.vtsip.ncmpA("Call answered by <sip:*@")) {
                cla.status_str = "Call answered by < " + cla.vtsip.cmpAstr + " >";
                return;
            }
             */
            if (cla.vtsip.ncmpB('*', "Media streams established with <sip:*> for call * (audio).")) {
                strA = cla.vtsip.cmpBstr.split("@");
                if (strA.length == 2) {
                    cla.connectNo_str = strA[0];
                    cla.status_str = "Call answered by < " + cla.connectNo_str + " >";
                    cla.connectId_str = cla.vtsip.cmpAstr;
                }
                return;
            }

            if (cla.vtsip.ncmpA("answer \nConnected.")) {
                cla.status_str = "Call from < " + cla.callfrom + " >" + " Connected";
                cla.connected_cnt = 3;
                Date dNow = new Date();
                cla.connected_tim=dNow.getTime();
                return;
            }
            if (cla.vtsip.ncmpA("Receiving tone * from")) {
                cla.dtmf = vtsip.cmpAstr;
                cla.status_str = "Received DTMF tone " + cla.dtmf;
                return;
            }

        }

        //============================================
        str = "Receiving tone ";
        str += GB.ptt_on_no;
        str += " from";
        if (cla.vtsip.cmp(str)) {
//            cla.pttOn();
            return;
        }
        //============================================
        str = "Receiving tone ";
        str += GB.ptt_off_no;
        str += " from";
        if (cla.vtsip.cmp(str)) {
//            cla.pttOff();
        }
    }

    public void sshWriteSip(String shellCommand) {
        SipPhone cla = this;
        if (cla.sshSip == null || cla.sshSip.connect_f == 0) {
            return;
        }
        try {
            cla.sshSip.outStrm.write(shellCommand.getBytes());
        } catch (IOException ex) {
        }
        try {
            cla.sshSip.outStrm.flush();
        } catch (IOException ex) {
        }
    }

    public void sshWriteNgrep(String shellCommand) {
        SipPhone cla = this;
        if (cla.sshNgrep == null || cla.sshNgrep.connect_f == 0) {
            return;
        }
        try {
            cla.sshNgrep.outStrm.write(shellCommand.getBytes());
        } catch (IOException ex) {
        }
        try {
            cla.sshNgrep.outStrm.flush();
        } catch (IOException ex) {
        }
    }

    public void sshWriteIct(String shellCommand) {
        SipPhone cla = this;
        if (cla.sshIct == null || cla.sshIct.connect_f == 0) {
            return;
        }
        try {
            cla.sshIct.outStrm.write(shellCommand.getBytes());
        } catch (IOException ex) {
        }
        try {
            cla.sshIct.outStrm.flush();
        } catch (IOException ex) {
        }
    }

    public void sshWriteShl(String shellCommand) {
        SipPhone cla = this;
        if (cla.sshShl == null || cla.sshShl.connect_f == 0) {
            return;
        }
        try {
            cla.sshShl.outStrm.write(shellCommand.getBytes());
        } catch (IOException ex) {
        }
        try {
            cla.sshShl.outStrm.flush();
        } catch (IOException ex) {
        }
    }

    public void tx_ssksipInf(Ssocket ssk) {
        int i, j, k;
        SipPhone cla = this;
        byte[] tmpbyte;
        int stx_index = 0;
        int txlen;
        ssk.stm.tbuf[stx_index++] = (byte) GB.sipmd_device_id;
        //===================================================
        ssk.stm.tbuf[stx_index++] = 0x10;       //sipphone status
        ssk.stm.tbuf[stx_index++] = (byte) 10;
        ssk.stm.tbuf[stx_index++] = (byte) cla.sipStatus;
        ssk.stm.tbuf[stx_index++] = (byte) cla.connected_cnt;
        ssk.stm.tbuf[stx_index++] = (byte) cla.handStatus;
        ssk.stm.tbuf[stx_index++] = (byte) GB.ear_speaker_vol;
        ssk.stm.tbuf[stx_index++] = (byte) GB.phset_speaker_vol;
        ssk.stm.tbuf[stx_index++] = (byte) GB.ear_mic_sens;
        ssk.stm.tbuf[stx_index++] = (byte) GB.phset_mic_sens;
        cla.sipflag[0] = 0;
        if (mute_f == 1) {
            cla.sipflag[0] += 1;
        }
        ssk.stm.tbuf[stx_index++] = (byte) cla.sipflag[0];
        ssk.stm.tbuf[stx_index++] = (byte) cla.sipflag[1];
        ssk.stm.tbuf[stx_index++] = (byte) cla.sipflag[2];
        //===================================================
        tmpbyte = cla.status_str.getBytes();
        txlen = tmpbyte.length;
        if (txlen > 40) {
            txlen = 40;
        }
        //==================
        ssk.stm.tbuf[stx_index++] = 0x11;       //
        ssk.stm.tbuf[stx_index++] = (byte) txlen;
        for (i = 0; i < txlen; i++) {
            ssk.stm.tbuf[stx_index++] = tmpbyte[i];
        }
        //===================================================
        tmpbyte = cla.action_str.getBytes();
        if (cla.keypad_on_f == 1) {
            tmpbyte = cla.keypad_str.getBytes();
        }
        if (cla.setting_on_f == 1) {
            tmpbyte = cla.setting_str.getBytes();
        }
        txlen = tmpbyte.length;
        if (txlen > 40) {
            txlen = 40;
        }
        //==================
        ssk.stm.tbuf[stx_index++] = 0x12;       //
        ssk.stm.tbuf[stx_index++] = (byte) txlen;
        for (i = 0; i < txlen; i++) {
            ssk.stm.tbuf[stx_index++] = tmpbyte[i];
        }
        //===================================================
        tmpbyte = cla.callto.getBytes();
        txlen = tmpbyte.length;
        if (txlen > 10) {
            txlen = 10;
        }
        //==================
        ssk.stm.tbuf[stx_index++] = 0x13;       //
        ssk.stm.tbuf[stx_index++] = (byte) txlen;
        for (i = 0; i < txlen; i++) {
            ssk.stm.tbuf[stx_index++] = tmpbyte[i];
        }
        //===================================================
        tmpbyte = cla.callfrom.getBytes();
        txlen = tmpbyte.length;
        if (txlen > 10) {
            txlen = 10;
        }
        //==================
        ssk.stm.tbuf[stx_index++] = 0x14;       //
        ssk.stm.tbuf[stx_index++] = (byte) txlen;
        for (i = 0; i < txlen; i++) {
            ssk.stm.tbuf[stx_index++] = tmpbyte[i];
        }
        //===================================================
        if (++txSipInf_step >= 3) {
            txSipInf_step = 0;
        }
        char[] chA;
        byte[] bytes;
        switch (txSipInf_step) {
            case 0:
                bytes = new byte[12];
                String[] slst;
                slst = GB.real_ip_str.split("\\.");
                bytes[0] = (byte) Lib.str2int(slst[0], -1, 255, 0);
                bytes[1] = (byte) Lib.str2int(slst[1], -1, 255, 0);
                bytes[2] = (byte) Lib.str2int(slst[2], -1, 255, 0);
                bytes[3] = (byte) Lib.str2int(slst[3], -1, 255, 0);
                slst = GB.real_ipmask_str.split("\\.");
                bytes[4] = (byte) Lib.str2int(slst[0], -1, 255, 0);
                bytes[5] = (byte) Lib.str2int(slst[1], -1, 255, 0);
                bytes[6] = (byte) Lib.str2int(slst[2], -1, 255, 0);
                bytes[7] = (byte) Lib.str2int(slst[3], -1, 255, 0);
                slst = GB.real_gateway_str.split("\\.");
                bytes[8] = (byte) Lib.str2int(slst[0], -1, 255, 0);
                bytes[9] = (byte) Lib.str2int(slst[1], -1, 255, 0);
                bytes[10] = (byte) Lib.str2int(slst[2], -1, 255, 0);
                bytes[11] = (byte) Lib.str2int(slst[3], -1, 255, 0);
                ssk.stm.tbuf[stx_index++] = 0x15;       //
                ssk.stm.tbuf[stx_index++] = (byte) 12;
                for (i = 0; i < 12; i++) {
                    ssk.stm.tbuf[stx_index++] = bytes[i];
                }
                chA = GB.phone_name.toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x16;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                chA = GB.phone_no.toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x17;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                chA = GB.sip_server_ip.toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x18;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                break;
            case 1:
                chA = GB.hotline_nameA[0].toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x20;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                chA = GB.hotline_nameA[1].toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x21;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                chA = GB.hotline_nameA[2].toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x22;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                chA = GB.hotline_nameA[3].toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x23;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                chA = GB.hotline_noA[0].toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x30;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                chA = GB.hotline_noA[1].toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x31;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                chA = GB.hotline_noA[2].toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x32;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                chA = GB.hotline_noA[3].toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x33;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                break;
            case 2:
                chA = GB.sipVersion.toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x40;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                chA = GB.web_password.toCharArray();
                ssk.stm.tbuf[stx_index++] = 0x41;       //
                ssk.stm.tbuf[stx_index++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    ssk.stm.tbuf[stx_index++] = (byte) chA[i];
                }
                break;
        }
        //===================================================
        ssk.stm.tbuf_byte = stx_index;
        ssk.stm.enc_mystm();

    }

    void sskuiRx(int format) {
        SipPhone cla = this;
        ssksipCmdRx(cla.sskui, 1);
    }

    void reset_network() {
        String cmdStr;
        cmdStr = "sudo ifconfig eth0 ";
        cmdStr += GB.sipmd_ip_str;
        cmdStr += " netmask ";
        cmdStr += GB.sipmd_ipmask_str;
        cmdStr += " broadcast ";
        cmdStr += GB.sipmd_gateway_str;
        Lib.exe(cmdStr);
        //============================    
    }

    void ssksipCmdRx(Ssocket ssk, int retinf) {
        SipPhone cla = this;
        String str;
        int i, j, k;
        int inx = 0;
        int cmdinx;
        int cmdlen;
        int cmd;
        int txlen;
        int txinx;
        int stx_index;
        byte[] bytes;
        int ibuf;
        ssk.datain_f = 0;
        ssk.connect_f = 1;
        String commandData;

        if (ssk.inbuf[0] != (byte) GB.sipui_device_id) //status chg
        {
            return;
        }
        inx++;
        while (inx < ssk.inbuf_len) {
            cmd = ssk.inbuf[inx];
            cmdlen = ssk.inbuf[inx + 1];
            cmdinx = inx + 2;
            switch (cmd) {
                case 0x10://get_status
                    if (retinf == 1) {
                        if (ssk.tx_start_f == 0) {
                            ssk.tx_ip = GB.sipui_ui_ip;
                            ssk.tx_port = GB.sipui_ui_port;
                            //===================================================
                            tx_ssksipInf(ssk);
                            bytes = new byte[ssk.stm.txlen];
                            for (i = 0; i < ssk.stm.txlen; i++) {
                                bytes[i] = ssk.stm.tdata[i];
                            }
                            ssk.tx_bytes = bytes;
                            ssk.tx_start_f = 3; //return ip
                            //cla.sskui.tx_start_f = 4;
                        }
                    }
                    break;
                case 0x11://direct linphone command
                    if (shellCommandStatus == 1) {
                        txShellEsc();
                    }
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = ssk.inbuf[cmdinx++];
                    }
                    cla.sshWriteSip(new String(bytes));
                    break;
                case 0x12://direct shell command
                    if (shellCommandStatus == 1) {
                        txShellEsc();
                    }
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = ssk.inbuf[cmdinx++];
                    }
                    cla.sshWriteShl(new String(bytes));
                    //System.out.println(new String(bytes));
                    break;
                case 0x13://save net address
                    ibuf = ssk.inbuf[cmdinx + 0] & 255;
                    str = (ssk.inbuf[cmdinx + 1] & 255) + ".";
                    str += (ssk.inbuf[cmdinx + 2] & 255) + ".";
                    str += (ssk.inbuf[cmdinx + 3] & 255) + ".";
                    str += (ssk.inbuf[cmdinx + 4] & 255) + "";
                    switch (ssk.inbuf[cmdinx + 0] & 255) {
                        case 0:
                            GB.sipmd_ip_str = str;
                            Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
                            Base3.scla.editNewDb("sipmd_ip_str", "" + GB.sipmd_ip_str);
                            reset_network();
                            break;
                        case 1:
                            GB.sipmd_ipmask_str = str;
                            Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
                            Base3.scla.editNewDb("sipmd_ipmask_str", "" + GB.sipmd_ipmask_str);
                            reset_network();
                            break;
                        case 2:
                            GB.sipmd_gateway_str = str;
                            Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
                            Base3.scla.editNewDb("sipmd_gateway_str", "" + GB.sipmd_gateway_str);
                            reset_network();
                            break;
                        case 255:
                            GB.sipmd_ip_str = str;
                            str = (ssk.inbuf[cmdinx + 5] & 255) + ".";
                            str += (ssk.inbuf[cmdinx + 6] & 255) + ".";
                            str += (ssk.inbuf[cmdinx + 7] & 255) + ".";
                            str += (ssk.inbuf[cmdinx + 8] & 255) + "";
                            GB.sipmd_ipmask_str = str;
                            str = (ssk.inbuf[cmdinx + 9] & 255) + ".";
                            str += (ssk.inbuf[cmdinx + 10] & 255) + ".";
                            str += (ssk.inbuf[cmdinx + 11] & 255) + ".";
                            str += (ssk.inbuf[cmdinx + 12] & 255) + "";
                            GB.sipmd_gateway_str = str;
                            Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
                            Base3.scla.editNewDb("sipmd_ip_str", "" + GB.sipmd_ip_str);
                            Base3.scla.editNewDb("sipmd_ipmask_str", "" + GB.sipmd_ipmask_str);
                            Base3.scla.editNewDb("sipmd_gateway_str", "" + GB.sipmd_gateway_str);
                            reset_network();
                            break;
                    }
                    break;
                case 0x14://sip phone command
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = ssk.inbuf[cmdinx++];
                    }
                    String phoneCommand = new String(bytes);
                    switch (phoneCommand) {
                        case "hangon": //hangon
                            hangOn();
                            break;
                        case "hangoff": //hangoff
                            hangOff(0);
                            break;
                        case "speaker": //speaker on
                            speakerOn(0);
                            break;
                        case "0":
                        case "1":
                        case "2":
                        case "3":
                        case "4":
                        case "5":
                        case "6":
                        case "7":
                        case "8":
                        case "9":
                        case "*":
                        case "#":
                        case "ok":
                            phoneKeyin(phoneCommand);
                            break;
                        case "+":
                            volumePlus();
                            break;
                        case "-":
                            volumeMinus();
                            break;
                        case "prev":
                            show_preno(0);
                            break;
                        case "up":
                            if (menu_on_f == 1) {
                                menuKeyUp();
                                break;
                            }
                            if (keypad_on_f == 1) {
                                break;
                            }
                            if (setting_on_f == 1) {
                                if (!setId.equals("prevCall")) {
                                    break;
                                }
                            }
                            show_preno(0);
                            break;
                        case "down":
                            if (menu_on_f == 1) {
                                menuKeyUp();
                                break;
                            }
                            if (keypad_on_f == 1) {
                                break;
                            }
                            if (setting_on_f == 1) {
                                if (!setId.equals("prevCall")) {
                                    break;
                                }
                            }
                            show_preno(1);
                            break;
                        case "right":
                            break;
                        case "left":
                            break;
                        case "menu":
                            break;
                        case "esc":
                            break;
                        /*    
                        case "ok":
                            if (setting_on_f == 1) {
                                settingOk();
                            }
                            keypad_on_f = 0;
                            keypad_str = "";
                            keypad_tim = 0;
                            setting_on_f = 0;
                            setting_str = "";
                            break;
                         */
                        case "mute":
                            sshWriteSip("mute\n");
                            mute_f ^= 1;

                            /*
                            if (mute_f == 1) {
                                str = "sudo amixer cset numid=8 0,0\n";
                            } else {
                                str = "sudo amixer cset numid=8 " + inVolumeTbl[GB.phset_mic_sens] + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";
                            }
                            cla.sshWriteShl(str);
                             */
                            break;
                        case "transfer":
                            transferCall();
                            break;
                        case "f1":
                            callStr(GB.hotline_noA[0]);
                            break;
                        case "f2":
                            callStr(GB.hotline_noA[1]);
                            break;
                        case "f3":
                            callStr(GB.hotline_noA[2]);
                            break;
                        case "f4":
                            callStr(GB.hotline_noA[3]);
                            break;
                        case "book":
                            break;

                    }
                    break;
                case 0x15:
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = ssk.inbuf[cmdinx++];
                    }
                    GB.phone_no = new String(bytes);
                    Base3.scla.editNewDb("phone_no", "" + GB.phone_no);
                    break;
                case 0x16:
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = ssk.inbuf[cmdinx++];
                    }
                    GB.sip_server_pin = new String(bytes);
                    Base3.scla.editNewDb("sip_server_pin", "" + GB.sip_server_pin);
                    break;
                case 0x17:
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = ssk.inbuf[cmdinx++];
                    }
                    GB.sip_server_ip = new String(bytes);
                    Base3.scla.editNewDb("sip_server_ip", "" + GB.sip_server_ip);
                    break;
                case 0x18:
                    cla.pbxRegister();
                    break;

            }
            inx = inx + cmdlen + 2;
        }

    }

    void menuKeyUp() {
    }

    void menuKeyDown() {
    }

    void settingOk() {
        if (setId.equals("prevCall")) {
            phoneKeyin("#");
            return;
        }
    }

    void show_preno(int incdec_f) {
        if (setting_on_f == 1) {
            if (setId.equals("prevCall")) {
                if (incdec_f == 0) {
                    preno_cnt++;
                } else {
                    preno_cnt--;
                }
            }
        }
        if (preno_cnt > 9) {
            preno_cnt = 0;
        }
        if (preno_cnt < 0) {
            preno_cnt = 9;
        }
        setId = "prevCall";
        setting_str = "Last " + (preno_cnt + 1) + " call: " + get_preno(preno_cnt);
        setting_on_f = 1;
        setting_tim = 0;
    }

    void save_preno(String noStr) {
        preno_inx++;
        if (preno_inx >= 10) {
            preno_inx = 0;
        }
        prenoStrA[preno_inx] = noStr;
        preno_cnt = 0;
    }

    String get_preno(int prenoCnt) {
        int i;
        i = preno_inx - prenoCnt;
        if (i < 0) {
            i += 10;
        }
        String Str = prenoStrA[i];
        return prenoStrA[i];
    }

    void txShellEsc() {
        byte[] bytes;
        bytes = new byte[2];
        bytes[0] = 0x03;
        bytes[1] = 13;
        sshWriteShl(new String(bytes));
        shellCommandStatus = 0;

    }

    void dndOn() {
        String str;
        str = "dnd -a on\n";
        sshWriteSip(str);
    }

    void dndOff() {
        String str;
        str = "dnd -a off\n";
        sshWriteSip(str);

    }

    void callStr(String noStr) {
        byte[] bytes;
        if (shellCommandStatus == 1) {
            txShellEsc();
        }
        if (setting_on_f == 1) {
            return;
        }
        if (sipStatus != 3) {
            return;
        }

        if (handStatus == 0) {
            speakerOn(1);
        }
        save_preno(noStr);
        String str;
        str = "call " + noStr + "\n";
        sshWriteSip(str);
        callToStr = "Call " + noStr;
        keypad_str = "";
        keypad_on_f = 0;
        return;
    }

    void phoneKeyin(String cmd) {
        String str;
        byte[] bytes;
        setting_tim = 0;
        if (shellCommandStatus == 1) {
            txShellEsc();
        }
        if (setting_on_f == 1) {
            while (true) {
                if (setId.equals("transfer")) {
                    if (cmd.equals("ok")) {
                        if (keypad_str.equals("")) {
                            return;
                        }
                        str = "transfer " + keypad_str + "\n";
                        sshWriteSip(str);
                        keypad_str = "";
                        hangOn();
                        return;
                    }
                    keypad_str += cmd;
                    keypad_tim = 0;
                    setting_tim = 0;
                    setting_str = "Transfer To " + keypad_str;
                    return;
                }
                if (setId.equals("prevCall")) {
                    if (!cmd.equals("ok")) {
                        return;
                    }
                    setting_on_f = 0;
                    keypad_str = get_preno(preno_cnt);
                    break;
                }
                return;
            }
        }
        if (sipStatus == 3) {
            if (cmd.equals("ok")) {
                if (keypad_str.equals("")) {
                    return;
                }
                callStr(keypad_str);
                keypad_str = "";
                return;
            }
        }

        if (sipStatus == 4) {
            if (!cmd.equals("ok")) {
                str = "dtmf " + cmd + "\n";
                sshWriteSip(str);
                keypad_str = "Send Dtmf " + cmd;
                keypad_tim = 0;
                keypad_on_f = 1;
                return;
            }
        }

        if (dtmf_enable_f == 1) {
            if (!cmd.equals("ok")) {
                str = "dtmf " + cmd + "\n";
                sshWriteSip(str);
                keypad_str = "Send Dtmf " + cmd;
                keypad_tim = 0;
                keypad_on_f = 1;
                return;
            }
        }

        keypad_str += cmd;
        keypad_tim = 0;
        keypad_on_f = 1;

    }

    void transferCall() {
        if (sipStatus <= 3) {
            return;
        }
        setting_on_f = 1;
        setting_tim = 0;
        keypad_str = "";
        setting_str = "Transfer To ";
        setId = "transfer";
    }

    void volumePlus() {
        setting_tim = 0;
        if (handStatus == 1) {
            if (setting_on_f == 1 && GB.ear_speaker_vol < 9) {
                GB.ear_speaker_vol++;
            }
            setting_str = "Ear Phone Volume= " + GB.ear_speaker_vol;
            setEarphoneVolume();
            Base3.scla.editNewDb("ear_speaker_vol", "" + GB.ear_speaker_vol);
        } else {
            if (setting_on_f == 1 && GB.phset_speaker_vol < 9) {
                GB.phset_speaker_vol++;
            }
            setting_str = "Speaker Volume= " + GB.phset_speaker_vol;
            setSpeakerVolume();
            Base3.scla.editNewDb("phset_speaker_vol", "" + GB.phset_speaker_vol);
        }
        setting_on_f = 1;
        setId = "setVolume";
    }

    void volumeMinus() {
        setting_tim = 0;
        if (handStatus == 1) {
            if (setting_on_f == 1 && GB.ear_speaker_vol > 0) {
                GB.ear_speaker_vol--;
            }
            setting_str = "Ear Phone Volume= " + GB.ear_speaker_vol;
            setEarphoneVolume();
            Base3.scla.editNewDb("ear_speaker_vol", "" + GB.ear_speaker_vol);
        } else {
            if (setting_on_f == 1 && GB.phset_speaker_vol > 0) {
                GB.phset_speaker_vol--;
            }
            setting_str = "Speaker Volume= " + GB.phset_speaker_vol;
            setSpeakerVolume();
            Base3.scla.editNewDb("phset_speaker_vol", "" + GB.phset_speaker_vol);
        }
        setting_on_f = 1;
        setId = "setVolume";

    }

    void setSpeakerVolume() {
        SipPhone cla = this;
        String str;
        txShellEsc();
        str = "sudo amixer cset numid=6 " + outVolumeTbl[GB.phset_speaker_vol] + "," + 0 + "\n";
        cla.sshWriteShl(str);
    }

    void setEarphoneVolume() {
        SipPhone cla = this;
        byte[] bytes;
        String str;
        txShellEsc();
        str = "sudo amixer cset numid=6 " + 0 + "," + outVolumeTbl[GB.ear_speaker_vol] + "\n";
        cla.sshWriteShl(str);
    }

    void hangOn() {
        setting_on_f = 0;
        keypad_on_f = 0;
        keypad_str = "";

        SipPhone cla = this;
        String str;
        //System.out.println("hang on");
        if (GB.linphone_twinkle_f == 0) {
            cla.sshWriteSip("terminate\n");
        } else {
            cla.sshWriteSip("bye\n");
        }

        txShellEsc();
        handStatus = 0;
        str = "sudo amixer cset numid=6 " + outVolumeTbl[GB.phset_speaker_vol] + "," + outVolumeTbl[GB.ear_speaker_vol] + "\n";
        cla.sshWriteShl(str);
        str = "sudo amixer cset numid=8 " + inVolumeTbl[GB.phset_mic_sens] + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";
        //str = "sudo amixer cset numid=8 " + "0" + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";
        cla.sshWriteShl(str);
        mute_f = 0;

        cla.action_str = "Ready";
        cla.status_tim = 100;
        cla.connected_cnt = 0;
        cla.sipStatus = 3;
        cla.dtmf_enable_f = 0;
        dndOff();

    }

    void hangOff(int force) {
        if (force == 0) {
            if (handStatus == 1) {
                hangOn();
                return;
            }
        }
        setting_on_f = 0;
        keypad_on_f = 0;
        keypad_str = "";

        SipPhone cla = this;
        String str;
        //System.out.println("hang off");
        txShellEsc();
        str = "sudo amixer cset numid=6 0," + outVolumeTbl[GB.ear_speaker_vol] + "\n";
        cla.sshWriteShl(str);
        str = "sudo amixer cset numid=8 " + "0" + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";
        cla.sshWriteShl(str);
        cla.sshWriteSip("answer\n");
        if (sipStatus == 3) {
            if (force == 0) {
                cla.sshWriteShl("aplay /home/pi/kevin/sipphone/sipphone_ui/dial_tone.wav\n");
            }
        } else {
        }
        handStatus = 1;
        dndOn();
    }

    void speakerOn(int force) {
        if (force == 0) {
            if (handStatus == 2) {
                hangOn();
                return;
            }
        }
        setting_on_f = 0;
        keypad_on_f = 0;
        keypad_str = "";

        SipPhone cla = this;
        String str;
        //System.out.println("speaker on");
        txShellEsc();
        str = "sudo amixer cset numid=6 " + outVolumeTbl[GB.phset_speaker_vol] + ",0\n";
        cla.sshWriteShl(str);
        str = "sudo amixer cset numid=8 " + inVolumeTbl[GB.phset_mic_sens] + "," + "0" + "\n";
        cla.sshWriteShl(str);
        cla.sshWriteSip("answer\n");
        if (sipStatus == 3) {
            if (force == 0) {
                cla.sshWriteShl("aplay /home/pi/kevin/sipphone/sipphone_ui/dial_tone.wav\n");
            }
        } else {

        }
        handStatus = 2;
        dndOn();
    }

    void sskioRx(int format) {
        SipPhone cla = this;
        String str;
        cla.sskio.datain_f = 0;
        cla.sskio.connect_f = 1;
        if (cla.sskio.inbuf[0] == (byte) GB.sipui_device_id) //from sipui
        {
            txret_ssksip_inf(cla.sskio);
            ssksipCmdRx(cla.sskio, 0);
        }
        if (cla.sskio.inbuf[0] == (byte) GB.sipmd_io_device_id) //from sipmd_io
        {
            txret_ssksip_inf(cla.sskio);
        }
        if (cla.sskio.inbuf[0] == (byte) GB.sipui_io_device_id) //from sipui_io
        {
            txret_ssksip_inf(cla.sskio);
        }
    }

    void txret_ssksip_inf(Ssocket ssk) {
        SipPhone cla = this;
        byte[] bytes;
        int i;
        tx_ssksipInf(sskio);
        try {
            for (i = 0; i < ssk.stm.txlen; i++) {
                ssk.outstr.write(ssk.stm.tdata[i]);
            }
        } catch (IOException ex) {
        }
    }

    void pbxRegister() {
        SipPhone cla = this;
        String str;
        if (cla.linphone_load_f != 0) {
            if (GB.linphone_twinkle_f == 0) {
                str = "register sip:" + GB.phone_no + "@";
                str += GB.sip_server_ip;
                str += " sip:" + GB.sip_server_ip + ' ' + GB.sip_server_pin + '\n';
                cla.sshWriteSip(str);
            } else {
                Lib.setTwincleCfg();
                cla.sshWriteSip("quit");
            }
        }

    }

    void sskwebRx(int format) {
        SipPhone cla = this;
        String str;
        cla.sskweb.datain_f = 0;
        cla.sskweb.connect_f = 1;
        byte[] bytes = new byte[cla.sskweb.inbuf_len];
        for (int i = 0; i < cla.sskweb.inbuf_len; i++) {
            bytes[i] = cla.sskweb.inbuf[i];
        }
        str = new String(bytes);
        //System.out.println(str);
        if (!str.contains("Database is changed ")) {
            return;
        }
        String[] strs = str.split(" ");
        int ibuf = Integer.parseInt(strs[3]);

        if ((ibuf & 0x08) != 0) //reboot
        {
            //System.out.println("sudo shutdown -r +0" + "  start");
            Lib.exe("sudo shutdown -r +0");
            //System.out.println("sudo shutdown -r +0" + "  end");
            return;
        }

        if ((ibuf & 0x01) != 0) //phone change
        {
            //System.out.println("load Database");
            Base3.scla.x.act(0);
            Base3.scla.x.act(1);
            Base3.scla.x.act(2);
        }
        if ((ibuf & 0x02) != 0) //phone change
        {
            pbxRegister();
        }
        if ((ibuf & 0x04) != 0) //ip change
        {
            Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
            //System.out.println("wrInterfaces " + GB.sipmd_ip_str);
        }
        if ((ibuf & 0x10) != 0) //ntp ip ip change
        {
            Lib.wNtp();
        }
    }

}

class SiprxTd extends Thread {

    SipPhone cla;

    SiprxTd(SipPhone owner) {
        cla = owner;
    }

    @Override
    public void run() { // override Thread's run()
        //Test cla=Test.thisCla;
        for (;;) {
            if (cla.siprxTd_run_f == 1) {
                if (cla.sshSip != null && cla.sshSip.connect_f == 1) {
                    try {
                        if (cla.sshSip.inStrm.available() > 0) {
                            byte[] data = new byte[cla.sshSip.inStrm.available()];
                            int nLen = cla.sshSip.inStrm.read(data);
                            if (nLen < 0) {
                            } else if (nLen != 0) {
                                cla.vtsip.dataAvailable(data);
                                cla.sipPhoneRx.sshRx(cla.vtsip.incha);
                            } else {
                            }
                        }

                    } catch (IOException ex) {
                    }
                }
                Lib.thSleep(10);
                if (cla.siprxTd_destroy_f == 1) {
                    break;
                }
            }
        }
    }
}

class SipconTd extends Thread {

    SipPhone cla;
    int dis_connect_tim = 0;

    SipconTd(SipPhone owner) {
        cla = owner;
    }

    @Override
    public void run() { // override Thread's run()
        //Test cla=Test.thisCla;
        for (;;) {
            if (cla.sipconTd_run_f == 1) {
                //==========================
                int ibuf;
                ibuf = Lib.ping(GB.sipmd_linph_ip);
                if (ibuf == 0) {
                    dis_connect_tim = 0;
                    if (cla.sshSip == null) {
                        cla.sshSip = new Ssh(GB.sipmd_linph_ip, GB.sipmd_linph_user_name, GB.sipmd_linph_password);
                        cla.sshSip.connect();
                        if (cla.sshSip.connect_f == 0) {
                            cla.sshSip = null;
                        }

                    }
                } else {
                    dis_connect_tim++;
                    if (dis_connect_tim >= 5) {
                        if (cla.sshSip != null) {
                            cla.sshSip.connect_f = 0;
                            cla.sshSip = null;
                        }
                    }
                }
                //==========================
                Lib.thSleep(100);
                if (cla.sipconTd_destroy_f == 1) {
                    break;
                }
            }
        }
    }
}

class ShlconTd extends Thread {

    SipPhone cla;
    int dis_connect_tim = 0;

    ShlconTd(SipPhone owner) {
        cla = owner;
    }

    @Override
    public void run() { // override Thread's run()
        //Test cla=Test.thisCla;
        for (;;) {
            if (cla.shlconTd_run_f == 1) {
                //==========================
                int ibuf;
                ibuf = Lib.ping(GB.sipmd_ctr_ip);
                if (ibuf == 0) {
                    dis_connect_tim = 0;
                    if (cla.sshShl == null) {
                        cla.sshShl = new Ssh(GB.sipmd_ctr_ip, GB.sipmd_ctr_user_name, GB.sipmd_ctr_password);
                        cla.sshShl.connect();
                        if (cla.sshShl.connect_f == 0) {
                            cla.sshShl = null;
                        }

                    }
                } else {
                    dis_connect_tim++;
                    if (dis_connect_tim >= 5) {
                        if (cla.sshShl != null) {
                            cla.sshShl.connect_f = 0;
                            cla.sshShl = null;
                        }
                    }
                }
                //==========================
                Lib.thSleep(100);
                if (cla.shlconTd_destroy_f == 1) {
                    break;
                }
            }
        }
    }
}

class NgrepconTd extends Thread {

    SipPhone cla;
    int dis_connect_tim = 0;

    NgrepconTd(SipPhone owner) {
        cla = owner;
    }

    @Override
    public void run() { // override Thread's run()
        //Test cla=Test.thisCla;
        for (;;) {
            if (cla.ngrepconTd_run_f == 1) {
                //==========================
                int ibuf;
                ibuf = Lib.ping(GB.sipmd_ctr_ip);
                if (ibuf == 0) {
                    dis_connect_tim = 0;
                    if (cla.sshNgrep == null) {
                        cla.sshNgrep = new Ssh(GB.sipmd_ctr_ip, GB.sipmd_ctr_user_name, GB.sipmd_ctr_password);
                        cla.sshNgrep.connect();
                        if (cla.sshNgrep.connect_f == 0) {
                            cla.sshNgrep = null;
                        }

                    }
                } else {
                    dis_connect_tim++;
                    if (dis_connect_tim >= 5) {
                        if (cla.sshNgrep != null) {
                            cla.sshNgrep.connect_f = 0;
                            cla.sshNgrep = null;
                        }
                    }
                }
                //==========================
                Lib.thSleep(100);
                if (cla.ngrepconTd_destroy_f == 1) {
                    break;
                }
            }
        }
    }
}

class IctconTd extends Thread {

    SipPhone cla;
    int dis_connect_tim = 0;

    IctconTd(SipPhone owner) {
        cla = owner;
    }

    @Override
    public void run() { // override Thread's run()
        //Test cla=Test.thisCla;
        for (;;) {
            if (cla.ictconTd_run_f == 1) {
                //==========================
                int ibuf;
                ibuf = Lib.ping(GB.sipmd_ctr_ip);
                if (ibuf == 0) {
                    dis_connect_tim = 0;
                    if (cla.sshIct == null) {
                        cla.sshIct = new Ssh(GB.sipmd_ctr_ip, GB.sipmd_ctr_user_name, GB.sipmd_ctr_password);
                        cla.sshIct.connect();
                        if (cla.sshIct.connect_f == 0) {
                            cla.sshIct = null;
                        }

                    }
                } else {
                    dis_connect_tim++;
                    if (dis_connect_tim >= 5) {
                        if (cla.sshIct != null) {
                            cla.sshIct.connect_f = 0;
                            cla.sshIct = null;
                        }
                    }
                }
                //==========================
                Lib.thSleep(100);
                if (cla.ictconTd_destroy_f == 1) {
                    break;
                }
            }
        }
    }
}

class ShlrxTd extends Thread {

    SipPhone cla;

    ShlrxTd(SipPhone owner) {
        cla = owner;
    }

    @Override
    public void run() { // override Thread's run()
        //Test cla=Test.thisCla;
        for (;;) {
            if (cla.shlrxTd_run_f == 1) {
                if (cla.sshShl != null && cla.sshShl.connect_f == 1) {
                    try {
                        if (cla.sshShl.inStrm.available() > 0) {
                            byte[] data = new byte[cla.sshShl.inStrm.available()];
                            int nLen = cla.sshShl.inStrm.read(data);
                            if (nLen < 0) {
                            } else if (nLen != 0) {
                                cla.vtshl.dataAvailable(data);            //<<debug
                                cla.shellRx.sshRx(cla.vtshl.incha);       //<<debug
                            } else {
                            }
                        }

                    } catch (IOException ex) {
                    }
                }
                Lib.thSleep(10);
                if (cla.shlrxTd_destroy_f == 1) {
                    break;
                }
            }
        }
    }
}

class NgreprxTd extends Thread {

    SipPhone cla;

    NgreprxTd(SipPhone owner) {
        cla = owner;
    }

    @Override
    public void run() { // override Thread's run()
        //Test cla=Test.thisCla;
        for (;;) {
            if (cla.ngreprxTd_run_f == 1) {
                if (cla.sshNgrep != null && cla.sshNgrep.connect_f == 1) {
                    try {
                        if (cla.sshNgrep.inStrm.available() > 0) {
                            byte[] data = new byte[cla.sshNgrep.inStrm.available()];
                            int nLen = cla.sshNgrep.inStrm.read(data);
                            if (nLen < 0) {
                            } else if (nLen != 0) {
                                cla.vtngrep.dataAvailable(data);          //debug
                                cla.ngrepRx.sshRx(cla.vtngrep.incha);     //debug
                            } else {
                            }
                        }

                    } catch (IOException ex) {
                    }
                }
                Lib.thSleep(10);
                if (cla.ngreprxTd_destroy_f == 1) {
                    break;
                }
            }
        }
    }
}

class IctrxTd extends Thread {

    SipPhone cla;

    IctrxTd(SipPhone owner) {
        cla = owner;
    }

    @Override
    public void run() { // override Thread's run()
        //Test cla=Test.thisCla;
        for (;;) {
            if (cla.ictrxTd_run_f == 1) {
                if (cla.sshIct != null && cla.sshIct.connect_f == 1) {
                    try {
                        if (cla.sshIct.inStrm.available() > 0) {
                            byte[] data = new byte[cla.sshIct.inStrm.available()];
                            int nLen = cla.sshIct.inStrm.read(data);
                            if (nLen < 0) {
                            } else if (nLen != 0) {
                                cla.vtict.dataAvailable(data);          //debug
                                cla.ictRx.sshRx(cla.vtict.incha);     //debug
                                cla.ictPreData = cla.vtict.incha;
                            } else {
                            }
                        }

                    } catch (IOException ex) {
                    }
                }
                Lib.thSleep(10);
                if (cla.ictrxTd_destroy_f == 1) {
                    break;
                }
            }
        }
    }
}


/*
class SipPhoneTm1 implements ActionListener {

    String str;
    SipPhone cla;

    SipPhoneTm1(SipPhone owner) {
        cla = owner;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        if (cla.sipStatus == 3) {
            if (--cla.status_tim < 0) {
                cla.status_tim = 0;
                Date dNow = new Date();
                //SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
                SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd  hh:mm:ss");
                cla.status_str = ft.format(dNow);
            }

        }

    }

}
 */
// unit =20ms
class SipPhoneTm1 extends TimerTask {

    String str;
    SipPhone cla;

    SipPhoneTm1(SipPhone owner) {
        cla = owner;
    }

    @Override
    //int sipStatus = 0;         //0 no raspberryPi,1:raspberry pi ready,2:linphonec load,3:pbx registed,4:ring,5:connect 
    //int connected_cnt = 0;      //0:no connect 1:call to 2:call from 3:connected
    public void run() {
        if (++cla.linphone_connect_tim > 200) {
            cla.linphone_connect_tim = 0;
            cla.sshWriteSip("\n");  //<<debug
        }

        if (cla.sipStatus == 5) {
            if (++cla.holdRelease_tim > (50 * 300)) {
                cla.holdRelease_tim = 0;
                cla.sshWriteSip("hold\n");  //<<debug
                cla.sshWriteSip("retrieve\n");  //<<debug
            }
        }

        if (++cla.ictCommandTim > 50) {
            cla.ictCommandTim = 0;
            if (!cla.ictCommandStr.equals("")) {
                cla.sshWriteIct(cla.ictCommandStr);
                cla.ictCommandStr = "";
            }
        }
        if (--cla.sipCommandTim == 0) {
            if (!cla.sipCommandStr.equals("")) {
                cla.sshWriteSip(cla.sipCommandStr);
                cla.sipCommandStr = "";
            }
        }

        if (++cla.broadcast_tim > 100) {
            cla.broadcast_f = 0;
        }
        if (cla.sipStatus == 5 && cla.connected_cnt == 2) {
            if (GB.auto_answer == 1) {
                if (cla.auto_answer_tim == GB.auto_answer_wait) {
                    cla.speakerOn(1);
                    cla.broadcast_f = 0;
                }
                cla.auto_answer_tim++;
            }
            if (cla.broadcast_f == 1) {
                cla.broadcast_f = 0;
                for (int i = 0; i < GB.ictPhnos_amt; i++) {
                    if (GB.ictPhnos[i].equals(cla.callfrom)) {
                        cla.speakerOn(1);
                    }
                }
                cla.auto_answer_tim = GB.auto_answer_wait = 1;
            }
        }

        if (++cla.keypad_tim >= 200) {
            cla.keypad_on_f = 0;
            cla.keypad_str = "";
        }
        if (++cla.setting_tim >= 200) {
            cla.setting_on_f = 0;
            cla.preno_cnt = 0;
            cla.setting_str = "";

        }
        if (cla.sipStatus == 3) {
            if (--cla.status_tim < 0) {
                cla.status_tim = 0;
                Date dNow = new Date();
                //SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
                SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
                cla.status_str = ft.format(dNow);
            }

        }

        if (cla.sipStatus == 4 && cla.connected_cnt==3) {
                Date dNow = new Date();
                Date passT=new Date(dNow.getTime()-cla.connected_tim);
                
                SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
                cla.action_str = ft.format(passT);

        }


        
        
    }

}

//at PhoneCs.java
abstract class NgrepRx {

    public abstract void sshRx(String str);
}

abstract class SipPhoneRx {

    public abstract void sshRx(String str);
}

abstract class ShellRx {

    public abstract void sshRx(String str);
}

abstract class IctRx {

    public abstract void sshRx(String str);
}
