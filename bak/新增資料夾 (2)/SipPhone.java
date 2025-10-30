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
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    int nowVr;
    int preVr = 0xffff;
    int nowVrVol = 0;
    int nowVrVol_f = 1;
    int nowVrVolTime = 0;

    String selfSipDispName = "";
    String selfSipNumber = "";
    String sipActName = "";
    int sipActTime;
    Ssh sshSound = null;
    
    
    int status_f;
    int cmd_cnt = 0;
    int cmd_para0 = 0;
    int cmd_para1 = 0;
    int shutDown_cnt = 0;
    int byeDelayTime = 0;
    String referNo = "";
    String referName = "";
    int referTime = 0;
    int hangonWaitTime = 0;
    int f4WaitTime = 0;
    int soundCardInit = 0;
    int soundCardInitStep = 0;

    int line2ring_f = 0;
    String laterCall = "";
    int laterCall_tim = 0;

    int auto_register_tim = GB.auto_register_time - (50 * 10);
    int linphone_connect_tim = 0;
    int holdRelease_tim;
    int ict_connect_tim = 0;
    int ict_connected_f = 0;
    String ictCommandStr = "";
    int ictCommandTim = 0;
    int ictCheckPhno_f = 0;
    String ictCheckPhno_str = "";
    int dtmf_enable_f = 0;
    int wait_dtmf_f = 0;
    String dtmfStr = "";
    String ictPreData = "";

    long connected_tim = 0;

    int sipCommandTim = 0;
    String sipCommandStr = "";

    int set_local_ip_cnt = 255;
    int set_switch_ip_cnt = 255;

    byte[] ioBuf = new byte[16];
    //===============================
    //String connectId_str = "";
    //String connectNo_str = "";
    //String status_str = "";
    //String action_str = "";
    String keypad_str = "";
    String callToStr = "";
    int keypad_tim = 0;
    int keypad_on_f = 0;
    int auto_answer_tim = 0;

    int txSipInf_step = 0;
    int txSipInf_step_wait_f = 0;

    int txSipInf_step_a = 0;
    int txSipInf_step_b = 0;

    int menu_on_tim = 0;
    int menu_on_f = 0;

    String setId = "";
    String setting_str = "";
    int setting_tim = 0;
    int setting_on_f = 0;
    int mute_f = 0;
    public int pttEn_f = 0;
    public int amaMute_f = 0;

    byte[] sipflag = new byte[4];
    //int sipStatus = 0;         //0 no raspberryPi,1:raspberry pi ready,2:linphonec load,3:pbx registed,4:on call,5:ring,
    //int connected_cnt = 0;      //0:no connect 1:call to 2:call from 3:connected
    //int handStatusTime = 0;
    //int handStatus = 0;      //0:handon,1:earPhone on,2:spaeker on
    int handStatus_pre = 0;      //0:handon,1:earPhone on,2:spaeker on
    int shellCommandStatus = 0;      //0:ready,1:play dial tone
    String callto = "";
    String callfrom = "";
    //String callConnectNo = "";
    //String callConnectName = "";
    //String callfromId = "";
    //String callfromName = "";
    //==============================
    //int speakerVolume = 4;      //mix=0,max=45
    //int earphoneVolume = 4;     //mix=0,max=45
    //int micPhoneVolume = 4;     //mix=0,max=30
    int[] outVolumeTbl = {0, 2, 5, 7, 10, 12, 15, 18, 21, 25};
    int[] inVolumeTbl = {0, 2, 4, 6, 7, 8, 9, 10, 11, 12};   //ear mic
    int[] inVolumeTblMax = {0, 2, 4, 6, 7, 8, 9, 10, 11, 12};//speaker mic
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
    int sipphone_load_f = 0;
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
    SipData sipData = new SipData();

    int piIoStatus0;
    int piIoStatus1;
    int piIoInFlag0;
    int piIoInFlag1;
    int piMcuStatus;
    int piMcuVrAdi;

    int sipPhoneDeviceId = 0x1947;
    int piIoDeviceId = 0x1946;
    int piMcuDeviceId = 0x1945;

    TrxPack tpk0;

    SipPhone() {
        int i;
        for (i = 0; i < 10; i++) {
            prenoStrA[i] = "";
        }
        tpk0 = new TrxPack(3, 0x10);
        preno_inx = 0;
    }

    public void create() {

        int i = 0;
        if (i == 1) {
            Telnet tel = new Telnet("client", "192.168.3.230");
            return;
        }

        final SipPhone cla = this;

        cla.sipData.status = "JSSIP電話 , 版本: 3.0";
        cla.sipData.action = "啟動中 ....";
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
                sskui.datain_f = 0;
                sskui.connect_f = 1;
                byte[] bts = sskui.inbuf;
                int okf = chkSipRx(sskui.inbuf, sskui.inbuf_len, 0);
                if (okf == 1) {
                    if (sskui.txMode == 0) {
                        sskui.stm.tbuf_byte = loadSipInfData(sskui.stm.tbuf, 0);
                        sskui.stm.enc_mystm();
                        sskui.tx_port = GB.sipui_ui_port;
                        sskui.tx_bytes = sskui.stm.tdata;
                        sskui.tx_len = sskui.stm.txlen;
                        sskui.txMode = 5; //return txip with len and port
                    }
                }
            }
        };
        sskui.start();

        nowVrVol = GB.phset_speaker_vol;

    }

    void vtcmpNgrep() {
        SipPhone cla = this;
        int i = 0;
        String str;

        //============================================
        if (ngrepFirstIn_f == 0) {
            if (cla.vtngrep.ncmp("@raspberrypi:~$")) {
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
            if (cla.vtict.ncmp("@raspberrypi:~$")) {
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
                    //System.out.println("\n******************** Ict Phone no *************************");
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
                                        //System.out.println(phNo);
                                        GB.ictPhnos[GB.ictPhnos_amt++] = phNo;
                                    }
                                    break;
                                }
                            }
                            k += 0;
                        }
                    }
                    //System.out.println("*********************************************");
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
        if (cla.vtshl.cmp("@raspberrypi:~$")) {
            if (shlFirstIn_f == 0) {
                shlFirstIn_f = 1;
                str = "sudo amixer cset numid=4 " + outVolumeTbl[GB.phset_speaker_vol] + "," + outVolumeTbl[GB.ear_speaker_vol] + "\n";
                cla.sshWriteShl(str);
                str = "sudo amixer cset numid=6 " + inVolumeTblMax[GB.phset_mic_sens] + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";
                cla.sshWriteShl(str);
                ioBuf[0] &= 0xfc;

            }
            /*
            if (shellCommandStatus == 1) {
                shellCommandStatus = 0;
                if (cla.sipData.phoneSta <= 3) {
                    handStatus = 0;
                }
            }
             */
            return;
        }
        if (cla.vtshl.cmp("Playing WAVE")) {
            shellCommandStatus = 1;
            return;
        }
    }

    void vtcmpTwinkle() {
        SipPhone cla = this;
        String str;
        String[] strA;
        String tmpStr;
        String[] tmpStrA;

        int inLine;
        //============================================
        if (cla.vtsip.ncmp("@raspberrypi:~$")) {
            if (GB.syssec_f == 1) {
                cla.sipAct("loadSip", null);
            }
            cla.sipData.ready();
            cla.sipData.phoneSta = 1;
            return;
        }
        if (cla.sipData.phoneSta == 1) {
            tmpStr = cla.vtsip.ncmpStar("%", " % <sip:%@%>", 80);
            if (tmpStr != null) {
                strA = tmpStr.split("%");
                if (strA.length == 3) {
                    cla.selfSipDispName = strA[0];
                    cla.selfSipNumber = strA[1];
                    return;
                }
            }

            if (cla.vtsip.ncmp("Twinkle>")) {
                if (GB.lang == 0) {
                    cla.sipData.status = "In Registing PBX ....";
                    cla.sipData.action = "Registing PBX";
                }
                if (GB.lang == 1) {
                    cla.sipData.status = "電話註冊中請稍後 ....";
                    cla.sipData.action = "註冊電話";
                }
                cla.sipData.ready();
                cla.sipData.phoneSta = 2;
            }
        }

        if (cla.sipData.phoneSta <= 2) {
            if (cla.vtsip.ncmpA(" registration succeeded")) {
                if (GB.lang == 0) {
                    cla.sipData.status = "Registration Succeeded";
                    cla.sipData.action = "";
                }
                if (GB.lang == 1) {
                    cla.sipData.status = "電話註冊成功";
                    cla.sipData.action = "";
                }
                cla.sipData.ready();//phoneSta=3;
                cla.status_tim = 50;
            }
            return;
        }

        cla.sipData.lineMessageA[0] = "";
        cla.sipData.lineMessageA[1] = "";
        for (;;) {
            if (cla.sipData.phoneSta >= 3) {
                if (cla.vtsip.ncmpA("Line 1 is now active.")) {
                    cla.sipData.nowLine = 0;
                    cla.sipData.lineMessageA[cla.sipData.nowLine] = "changeLine";
                    break;
                }
                if (cla.vtsip.ncmpA("Line 2 is now active.")) {
                    cla.sipData.nowLine = 1;
                    cla.sipData.lineMessageA[cla.sipData.nowLine] = "changeLine";
                    break;
                }
                if (cla.vtsip.ncmpA("Line 1 is already active.")) {
                    cla.sipData.nowLine = 0;
                    break;
                }
                if (cla.vtsip.ncmpA("Line 2 is already active.")) {
                    cla.sipData.nowLine = 1;
                    break;
                }
                if (cla.sipActName.equals("mute")) {
                    if (cla.vtsip.ncmpA("Line muted.")) {
                        cla.sipData.lineMessageA[cla.sipData.nowLine] = "mute";
                        break;
                    }
                    if (cla.vtsip.ncmpA("Line unmuted.")) {
                        cla.sipData.lineMessageA[cla.sipData.nowLine] = "unmute";
                        break;
                    }
                }
                if (cla.vtsip.ncmpA("bye\n" + "Twinkle>")) {
                    cla.sipData.lineMessageA[cla.sipData.nowLine] = "selfByeCall";
                    break;
                }
                //=================================================
                if (cla.sipActName.equals("hold")) {
                    if (cla.vtsip.ncmpA("hold")) {
                        cla.sipData.lineMessageA[cla.sipData.nowLine] = "hold";
                        break;
                    }
                    if (cla.vtsip.ncmpA("retrieve")) {
                        cla.sipData.lineMessageA[cla.sipData.nowLine] = "unhold";
                        break;
                    }

                }

                /*
                if (cla.vtsip.ncmpA("hold\n" + "Twinkle> \n" + "Line *: re-INVITE successful.")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "hold";
                    break;
                }
                if (cla.vtsip.ncmpA("hold\n" + "Twinkle> ")) {
                    cla.sipData.lineMessageA[cla.sipData.nowLine] = "hold";
                    break;
                }
                if (cla.vtsip.ncmpA("retrieve\n" + "Twinkle> \n" + "Line *: re-INVITE successful.")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "unhold";
                    break;
                }
                if (cla.vtsip.ncmpA("retrieve\n" + "Twinkle> ")) {
                    cla.sipData.lineMessageA[cla.sipData.nowLine] = "unhold";
                    break;
                }
                 */
                //============= call out process ================
                tmpStr = cla.vtsip.ncmpStar("%", "call %\n" + "Twinkle> \n" + "Line %: received 100 Trying", 80);
                if (tmpStr != null) {
                    tmpStrA = tmpStr.split("%");
                    inLine = Lib.str2int(tmpStrA[1], 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "callOut~" + tmpStrA[0];
                }

                if (cla.vtsip.ncmpA("Line *: received 180 Ringing")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "ringOut";
                    break;
                }

                /*
                if (cla.vtsip.ncmpA("Twinkle> \n" + "Line *: call failed.")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "callFailNoThisCall";
                    break;
                }
                 */
                if (cla.vtsip.ncmpA("Line *: call failed.\n" + "603 Decline")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "callFailFarEndNoAnswer";
                    break;
                }
                if (cla.vtsip.ncmpA("Line *: call failed.\n" + "486 Busy Here")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "callFailFarEndBusy";
                    break;
                }

                if (cla.vtsip.ncmpA("Line *: call failed.\n" + "404 Not Found")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "callFail";
                    break;
                }
                //if (cla.vtsip.ncmpA("Line *: call ended.\n" + "Twinkle>")) {
                if (cla.vtsip.ncmpA("Line *: call ended.\n")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "selfEndCall";
                    break;
                }

                tmpStr = cla.vtsip.ncmpStar("%", "Line %: far end answered call.\n" + "200 OK\n" + "To: sip:%@", 80);
                if (tmpStr != null) {
                    tmpStrA = tmpStr.split("%");
                    inLine = Lib.str2int(tmpStrA[0], 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "farEndAnswerCall~" + tmpStrA[1];
                }
                //============= callin process ================
                tmpStr = cla.vtsip.ncmpStar("%", "Line %: incoming call\nFrom:% <sip:%@%>\nTo:", 80);
                if (tmpStr != null) {
                    tmpStrA = tmpStr.split("%");
                    inLine = Lib.str2int(tmpStrA[0], 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "incomeCall~" + tmpStrA[1] + "~" + tmpStrA[2];
                }
                if (cla.vtsip.ncmpA("Line *: far end cancelled call.")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "farEndCancelCall";
                    break;
                }
                if (cla.vtsip.ncmpA("Line *: call rejected.")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "rejectRingIn";
                    break;
                }
                //same as above
                /*
                if (cla.vtsip.ncmpA("bye\n" +"Twinkle>")) {
                    cla.sipData.lineMessageA[cla.sipData.nowLine] = "selfByeCall";
                    break;
                }
                 */
                if (cla.vtsip.ncmpA("Line *: call established.")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "answerConnectCall";
                    break;
                }
                //============= connect process ================
                if (cla.vtsip.ncmpA("Line *: far end ended call.")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "farEndEndCall";
                    break;
                }
                //same in ringout
                /*
                if (cla.vtsip.ncmpA("Line *: call ended.\n" +"Twinkle>")) {
                    inLine = Lib.str2int(cla.vtsip.cmpAstr, 1) - 1;
                    cla.sipData.lineMessageA[inLine] = "selfEndCall";
                    break;
                }
                 */
            }
            break;
        }
        if (cla.sipData.lineMessageA[0].length() == 0 && cla.sipData.lineMessageA[1].length() == 0) {
            return;
        }
        int actLine;
        String actStr;
        if (cla.sipData.lineMessageA[1].length() != 0) {
            actLine = 1;
            actStr = cla.sipData.lineMessageA[1];
        } else {
            actLine = 0;
            actStr = cla.sipData.lineMessageA[0];
        }
        str = "\n====== Act Line ";
        str += (actLine + 1) + " : " + actStr;
        str += " ====== \n";
        System.out.println(str);
        strA = actStr.split("~");
        switch (strA[0]) {
            case "changeLine":
                cla.txShellEsc();
                cla.sipData.lineFlagA[0] &= 0x02;
                cla.sipData.lineFlagA[1] &= 0x02;
                break;
            case "hold":
                cla.sipData.lineFlagA[actLine] |= 1;
                break;
            case "unhold":
                cla.sipData.lineFlagA[actLine] &= 0xfe;
                break;
            case "mute":
                cla.sipData.lineFlagA[actLine] |= 2;
                break;
            case "unmute":
                cla.sipData.lineFlagA[actLine] &= 0xfd;
                break;
            //=== call out 
            case "callOut":
                cla.sipData.action = "撥打 " + strA[1];
                cla.sipData.lineStaA[actLine] = 1;
                cla.sipData.lineNameA[actLine] = strA[1];
                cla.sipData.lineNoA[actLine] = strA[1];
                cla.status_tim = 99999;
                break;
            case "ringOut":
                cla.sipData.status = "對方響鈴中....";
                cla.status_tim = 99999;
                break;
            case "callFailNoThisCall":
                cla.sipData.status = "電話號碼錯誤";
                cla.sipData.lineStaA[actLine] = 0;
                cla.status_tim = 50;
                break;
            case "callFailFarEndNoAnswer":
                cla.sipData.status = "對方逾時無接聽";
                cla.sipData.lineStaA[actLine] = 0;
                cla.status_tim = 50;
                break;
            case "callFailFarEndBusy":
                cla.sipData.status = "對方忙線中";
                cla.sipData.lineStaA[actLine] = 0;
                cla.status_tim = 50;
                break;
            case "callFail":
                cla.sipData.status = "呼叫失敗";
                cla.sipData.lineStaA[actLine] = 0;
                cla.status_tim = 50;
                break;
            case "selfEndCall":
                cla.sipData.status = "通話已中斷";
                cla.sipData.action = "取消通話";
                cla.sipData.lineStaA[actLine] = 0;
                cla.status_tim = 50;
                break;
            case "farEndAnswerCall":
                cla.sipData.status = "對方已接聽";
                cla.sipData.lineStaA[actLine] = 3;
                cla.status_tim = 50;
                Date dNow = new Date();
                cla.sipData.lineConnectTimeA[actLine] = dNow.getTime();
                break;
            //===income call 
            case "incomeCall":
                cla.sipData.status = "電話撥入 " + strA[1] + " <" + strA[2] + ">";
                cla.sipData.action = "請接電話 !";
                cla.sipData.lineStaA[actLine] = 2;
                cla.sipData.lineNameA[actLine] = strA[1];
                cla.sipData.lineNoA[actLine] = strA[2];
                cla.status_tim = 9999;
                if (strA[2].contains("*0*")) {
                    cla.phoneCommandIn("speakerAct");
                }
                cla.txShellEsc();
                break;
            case "farEndCancelCall":
                cla.sipData.status = "對方已掛斷";
                cla.sipData.action = cla.sipData.lineNoA[actLine] + " 來電無接聽";
                cla.sipData.lineStaA[actLine] = 0;
                cla.status_tim = 50;
                break;
            case "rejectRingIn":
                cla.sipData.action = "拒絕通話";
                cla.sipData.lineStaA[actLine] = 0;
                cla.status_tim = 50;
                break;
            case "selfByeCall":
                cla.sipData.action = "取消通話";
                cla.sipData.lineStaA[actLine] = 0;
                cla.status_tim = 50;
                break;
            case "answerConnectCall":
                cla.sipData.action = "通話已建立";
                cla.sipData.lineStaA[actLine] = 3;
                cla.status_tim = 50;
                dNow = new Date();
                cla.sipData.lineConnectTimeA[actLine] = dNow.getTime();

                break;
            //===connected 
            case "farEndEndCall":
                cla.sipData.status = "對方已掛斷";
                cla.sipData.lineStaA[actLine] = 0;
                cla.status_tim = 100;
                break;
            /*        
            case "selfEndCall":
                    break;
             */

        }

        //===============================================================================================
    }
    
    
    
    void vtcmpTwinklexxx() {
        SipPhone cla = this;
        int i = 0;
        String str;
        String[] strA;
        cla.linphone_connect_tim = 0;
        //============================================
        if (soundCardInit == 0) {
            switch (soundCardInitStep) {
                case 0:
                    if (cla.vtsip.ncmp("@raspberrypi:~$")) {
                        soundCardInitStep++;
                        cla.sshWriteSip("aplay -l\n");
                    }
                    break;
                case 1:
                    if (cla.vtsip.ncmpA("card 0:")) {
                        soundCardInit = 1;
                        break;
                    }
                    if (cla.vtsip.ncmpA("card *: AS311")) {
                        soundCardInit = 1;
                        System.out.println("\n sound card********************************");
                        System.out.println(cla.vtsip.cmpAstr);
                        System.out.println("*********************************************\n");
                        ArrayList<String> strC = Lib.readFileLines(GB.asound_path);
                        int chg = 0;
                        String str1 = "defaults.ctl.card " + cla.vtsip.cmpAstr;
                        String str2 = "defaults.pcm.card " + cla.vtsip.cmpAstr;
                        String str3 = "defaults.timer.card " + cla.vtsip.cmpAstr;

                        if (Lib.chkStrInList(str1, strC) == 0) {
                            chg = 1;
                        }
                        if (Lib.chkStrInList(str2, strC) == 0) {
                            chg = 1;
                        }
                        if (Lib.chkStrInList(str3, strC) == 0) {
                            chg = 1;
                        }
                        if (chg == 1) {
                            ArrayList<String> strD = new ArrayList<String>();
                            strD.add(str2);
                            strD.add(str2);
                            strD.add(str2);
                            Lib.writeFileLines(GB.asound_path, strD);
                        }
                        strC = Lib.readFileLines(GB.twinkleSys_path);
                        chg = 0;
                        for (i = 0; i < strC.size(); i++) {
                            str1 = strC.get(i);
                            if (Lib.search(str1, "dev_ringtone=alsa:plughw:", ",0") == 1) {
                                if (!Lib.retstr.equals(cla.vtsip.cmpAstr)) {
                                    str1 = "dev_ringtone=alsa:plughw:" + cla.vtsip.cmpAstr + ",0";
                                    strC.set(i, str1);
                                    chg = 1;
                                }
                            }
                            if (Lib.search(str1, "dev_speaker=alsa:plughw:", ",0") == 1) {
                                if (!Lib.retstr.equals(cla.vtsip.cmpAstr)) {
                                    str1 = "dev_speaker=alsa:plughw:" + cla.vtsip.cmpAstr + ",0";
                                    strC.set(i, str1);
                                    chg = 1;
                                }
                            }
                            if (Lib.search(str1, "dev_mic=alsa:plughw:", ",0") == 1) {
                                if (!Lib.retstr.equals(cla.vtsip.cmpAstr)) {
                                    str1 = "dev_mic=alsa:plughw:" + cla.vtsip.cmpAstr + ",0";
                                    strC.set(i, str1);
                                    chg = 1;
                                }
                            }
                        }
                        if (chg == 1) {
                            Lib.writeFileLines(GB.twinkleSys_path, strC);
                        }

                    }

                    break;
            }

            return;
        }

        if (cla.vtsip.ncmp("@raspberrypi:~$")) {
            if (GB.syssec_f == 1) {
                cla.sipAct("loadSip", null);
            }
            cla.sipData.ready();
            cla.sipData.phoneSta = 1;
            return;
        }

        if (cla.sipData.phoneSta == 1) {
            if (cla.vtsip.ncmp("Twinkle>")) {
                if (GB.lang == 0) {
                    cla.sipData.status = "In Registing PBX ....";
                    cla.sipData.action = "Registing PBX";
                }
                if (GB.lang == 1) {
                    cla.sipData.status = "電話註冊中請稍後 ....";
                    cla.sipData.action = "註冊電話";
                }
                cla.sipData.ready();
                cla.sipData.phoneSta = 2;
            }
        }

        if (cla.sipData.phoneSta <= 2) {
            if (cla.vtsip.ncmpA(" registration succeeded")) {
                if (GB.lang == 0) {
                    cla.sipData.status = "Registration Succeeded";
                    cla.sipData.action = "";
                }
                if (GB.lang == 1) {
                    cla.sipData.status = "電話註冊成功";
                    cla.sipData.action = "";
                }
                cla.sipData.ready();
                cla.sipphone_load_f = 1;
                cla.status_tim = 50;

            }
            return;
        }

        if (cla.sipData.phoneSta >= 3) {
            if (cla.vtsip.ncmpA("far end supports DTMF")) {
                cla.dtmf_enable_f = 1;
                return;
            }

            if (cla.vtsip.ncmpA("Line *: far end answered call.")) {

                cla.wait_dtmf_f = 0;
                cla.sshWriteSip("hold\n");
                cla.sshWriteSip("retrieve\n");
                cla.sipCommandTim = 3;
                cla.sipCommandStr = "retrieve\n";

                //==========================================================
                if (cla.vtsip.cmpAstr.equals("1")) {
                    cla.sipData.connectSta = 3;
                    cla.sipData.phoneSta = 4;
                    //cla.sipData.dtmfOn_f=1;
                    cla.dndOff();

                    cla.status_tim = 50;
                    cla.sipData.status = "對方已接聽";
                    cla.sipData.connectName = sipData.lineNoA[sipData.nowLine];
                    cla.sipData.connectNo = sipData.lineNoA[sipData.nowLine];
                    cla.sipData.lineStaA[cla.sipData.nowLine] = 2;

                    //==========================================================
                    Date dNow = new Date();
                    cla.connected_tim = dNow.getTime();
                    return;
                }
                if (cla.vtsip.cmpAstr.equals("2")) {
                    cla.sipData.status = "對方已接聽";
                    return;
                }

            }

            if (cla.vtsip.ncmpA("\nYou can try the following contacts:\n*\n")) {
                System.out.println("\n*********************************************");
                System.out.println(cla.vtsip.cmpAstr);
                System.out.println("*********************************************\n");
                cla.laterCall = cla.vtsip.cmpAstr;
                cla.laterCall_tim = 50;
                cla.sipData.status = "轉接 " + cla.laterCall;
                cla.sipData.action = "轉接 " + cla.laterCall;
                return;
            }

            if (cla.vtsip.ncmpA("Line *: call failed.")) {
                if (cla.vtsip.cmpAstr.equals("2")) {
                    cla.sipData.status = "呼叫失敗";
                    cla.status_tim = 50;
                    sipAct("line1", null);
                    return;
                }
                cla.sipData.status = "呼叫失敗";
                cla.sipData.action = "OK.";
                cla.status_tim = 100;
                cla.sipData.connectSta = 0;
                cla.sipData.phoneSta = 3;
                cla.sipData.lineStaA[cla.sipData.nowLine] = 0;
                cla.handStatus_pre = cla.handStatus;
                cla.hangOn();
                return;
            }

        }

        if (cla.sipData.phoneSta == 3) {

            if (cla.vtsip.ncmpA("Line 1: received 183 Session Progress")) {
                cla.sipData.status = "忙線中";
                cla.sipData.lineStaA[1] = 1;
                cla.status_tim = 200;
                cla.wait_dtmf_f = 1;
                cla.dtmfStr = "Send Dtmf ";
                cla.status_tim = 50 * 30;

                //cla.hangOnPrg();
                return;
            }

            if (cla.vtsip.ncmpA("404 Not Found")) {
                cla.sipData.status = "電話號碼 " + cla.callto + " 錯誤";
                cla.sipData.action = "OK.";
                cla.handStatus = 0;
                cla.status_tim = 100;
                cla.sipData.connectSta = 0;
                cla.dndOff();
                return;
            }
            if (cla.vtsip.ncmpA("Line *: received 180 Ringing")) {
                if (cla.vtsip.cmpAstr.equals("1")) {
                    cla.sipData.status = "響鈴....";
                    cla.status_tim = 1000;
                    cla.sipData.connectSta = 1;
                    cla.sipData.lineStaA[cla.sipData.nowLine] = 1;
                    cla.sipData.phoneSta = 5;
                    return;
                }
            }
            if (cla.vtsip.ncmpA("200 OK\nTo: sip:*\n")) {
                strA = cla.vtsip.cmpAstr.split("@");
                if (strA.length == 2) {
                    cla.callto = strA[0];
                    cla.callfrom = "";
                    cla.sipData.status = "撥打 < " + cla.callto + " >";
                    cla.sipData.action = "撥打 " + cla.callto;
                    cla.sipData.connectName = strA[0];
                    cla.sipData.connectNo = strA[0];
                    cla.status_tim = 100;
                    cla.sipData.connectSta = 1;
                    cla.holdRelease_tim = 0;
                    cla.sipData.phoneSta = 5;
                    return;
                }
                return;
            }

            if (cla.vtsip.ncmpB('*', "Line *: incoming call\nFrom:*<*>")) {

                System.out.println("************************");
                System.out.println(cla.vtsip.cmpAstr);
                System.out.println(cla.vtsip.cmpBstr);
                System.out.println(cla.vtsip.cmpCstr);

                System.out.println("************************");
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
                cla.sipData.connectNo = cla.callfrom;
                cla.sipData.connectName = cla.vtsip.cmpBstr.trim();
                cla.sipData.status = "電話撥入 " + cla.sipData.connectName + " < " + cla.sipData.connectNo + " >";
                cla.sipData.action = "請接電話 !";
                cla.sipData.connectSta = 2;
                cla.sipData.lineStaA[cla.sipData.nowLine] = 2;
                cla.holdRelease_tim = 0;
                cla.sipData.phoneSta = 5;
                cla.auto_answer_tim = 0;
                cla.status_tim = 100;
                if(cla.sipData.connectNo.contains("*0*")){
                    cla.speakerOn();
                }
                return;
            }

            if (cla.vtsip.ncmpB('*', "incoming call\nFrom:*:*<*>")) {

                if (cla.vtsip.cmpAstr.length() > 30) {
                    return;
                }
                if (cla.vtsip.cmpBstr.length() > 20) {
                    return;
                }
                int tstStr = cla.vtsip.cmpCstr.length();
                if (tstStr == 18) {
                    hangOn();
                    return;
                }

                cla.callfrom = cla.vtsip.cmpAstr;
                cla.callto = "";
                cla.sipData.connectNo = cla.callfrom;
                cla.sipData.connectName = cla.vtsip.cmpBstr.trim();
                cla.sipData.status = "電話撥入 " + cla.sipData.connectName + " < " + cla.sipData.connectNo + " >";
                cla.sipData.action = "";
                cla.sipData.connectSta = 2;
                cla.sipData.lineStaA[cla.sipData.nowLine] = 2;
                cla.holdRelease_tim = 0;
                cla.sipData.phoneSta = 5;
                cla.auto_answer_tim = 0;
                cla.status_tim = 100;
                if(cla.sipData.connectNo.contains("*0*")){
                    cla.speakerOn();
                }
                
                
                return;
            }

        }

        if (cla.sipData.phoneSta == 5) {
            if (cla.vtsip.ncmpA("Line *: call established.")) {
                if (cla.vtsip.cmpAstr.equals("1")) {
                    cla.sipData.lineStaA[0] = 2;
                    cla.sipData.phoneSta = 4;
                    //cla.sipData.dtmfOn_f =1;
                    cla.dndOff();

                    return;
                }
                if (cla.vtsip.cmpAstr.equals("2")) {
                    cla.sipData.lineStaA[1] = 3;
                    return;
                }
            }
        }

        if (cla.sipData.phoneSta >= 4) {

            if (cla.vtsip.ncmpA("Line *: received 180 Ringing")) {
                if (cla.vtsip.cmpAstr.equals("2")) {
                    cla.sipData.status = "響鈴....";
                    cla.sipData.lineStaA[1] = 1;
                    cla.status_tim = 1000;
                }
                return;
            }

            if (cla.vtsip.ncmpA("Referred-by:")) {
                if (cla.referTime > 0) {
                    cla.sipData.connectNo = cla.referNo;
                    cla.sipData.connectName = cla.referName;
                    cla.sipData.status = "轉接" + cla.referName + " < " + cla.referNo + " >";
                    cla.callfrom = cla.referNo;
                    cla.callto = cla.referNo;
                    cla.status_tim = 100;

                }

            }

            if (cla.vtsip.ncmpB('*', "Line *: incoming call\nFrom:*<*>")) {

                System.out.println("************************");
                System.out.println(cla.vtsip.cmpAstr);
                System.out.println(cla.vtsip.cmpBstr);
                System.out.println(cla.vtsip.cmpCstr);
                System.out.println("************************");

                if (cla.vtsip.cmpAstr.length() > 30) {
                    return;
                }
                if (cla.vtsip.cmpBstr.length() > 20) {
                    return;
                }

                if (cla.vtsip.cmpCstr.equals("1")) {

                    if (cla.sipData.nowLine == 0) {
                        strA = cla.vtsip.cmpAstr.split("@");
                        if (strA.length == 2) {
                            cla.referNo = strA[0];
                            String[] strB = strA[0].split(":");
                            if (strB.length == 2) {
                                cla.referNo = strB[1];
                            } else {
                                cla.referNo = strA[0];
                            }
                        } else {
                            cla.referNo = cla.vtsip.cmpAstr;
                        }
                        cla.referName = cla.vtsip.cmpBstr.trim();

                        cla.referTime = 50;
                        return;
                    }
                } else {

                    cla.referNo = cla.vtsip.cmpAstr;
                    cla.referName = cla.vtsip.cmpBstr.trim();
                    cla.sipData.action = "Line2: 電話撥入 " + cla.referName + " < " + cla.referNo + " >";
                    //cla.sipData.action = "";
                    //cla.sipData.connectSta = 2;
                    cla.sipData.lineStaA[1] = 1;
                    //cla.holdRelease_tim = 0;
                    //cla.sipData.phoneSta = 5;
                    //cla.auto_answer_tim = 0;
                    cla.status_tim = 2000;
                    cla.line2ring_f = 1;
                    
                }
                return;
            }

            if (cla.vtsip.ncmpA("Line *: call failed.")) {
                cla.sipData.status = "撥打失敗";
                cla.sipData.action = "OK.";
                cla.status_tim = 100;
                cla.sipData.connectSta = 0;
                cla.sipData.lineStaA[cla.sipData.nowLine] = 0;
                cla.sipData.phoneSta = 3;
                return;
            }

            if (cla.vtsip.ncmpA("Line *: answer timeout.")) {

                if (cla.vtsip.cmpAstr.equals("1")) {
                    cla.sipData.status = "逾時無接聽";
                    cla.sipData.action = "掛斷";
                    cla.callto = "";
                    cla.callfrom = "";
                    cla.status_tim = 100;
                    cla.sipData.connectSta = 0;
                    cla.sipData.lineStaA[cla.sipData.nowLine] = 0;
                    cla.sipData.phoneSta = 3;
                    cla.handStatus = 0;
                    cla.dtmf_enable_f = 0;
                    cla.dndOff();
                    hangOn();
                    return;
                }

                if (cla.vtsip.cmpAstr.equals("2")) {
                    if (cla.sipData.nowLine == 0) {
                        cla.line2ring_f = 0;
                        cla.status_tim = 0;
                        return;
                    }

                    cla.line2ring_f = 0;
                    cla.sipData.status = "Line2 對方已掛斷";
                    cla.status_tim = 50;
                    cla.sipAct("line1", null);
                    if (cla.sipData.lineStaA[0] == 0) {
                        cla.dndOff();
                        hangOn();
                    }
                    return;
                }

                return;
            }

            if (cla.vtsip.ncmpA("Line *: far end cancelled call.")) {
                if (cla.vtsip.cmpAstr.equals("1")) {
                    cla.sipData.status = "對方已掛斷";
                    cla.sipData.action = "OK.";
                    cla.callto = "";
                    cla.callfrom = "";
                    cla.status_tim = 100;
                    cla.sipData.connectSta = 0;
                    cla.sipData.lineStaA[cla.sipData.nowLine] = 0;
                    cla.sipData.phoneSta = 3;
                    cla.handStatus = 0;
                    cla.dtmf_enable_f = 0;
                    cla.dndOff();
                    hangOn();
                    return;
                }

                if (cla.vtsip.cmpAstr.equals("2")) {
                    if (cla.sipData.nowLine == 0) {
                        cla.line2ring_f = 0;
                        cla.status_tim = 0;
                        return;
                    }

                    cla.line2ring_f = 0;
                    cla.sipData.status = "Line2 對方已掛斷";
                    cla.status_tim = 50;
                    cla.sipAct("line1", null);
                    if (cla.sipData.lineStaA[0] == 0) {
                        cla.dndOff();
                        hangOn();
                    }
                    return;
                }

            }

            if (cla.vtsip.ncmpA("Line *: far end ended call.")) {
                if (cla.vtsip.cmpAstr.equals("1")) {
                    if (cla.sipData.nowLine == 0) {
                        cla.sipData.status = "對方已掛斷";
                        cla.sipData.action = "OK";
                        cla.callto = "";
                        cla.callfrom = "";
                        cla.status_tim = 100;
                        cla.sipData.connectSta = 0;
                        cla.sipData.lineStaA[0] = 0;
                        cla.sipData.phoneSta = 3;
                        cla.handStatus = 0;
                        cla.dtmf_enable_f = 0;
                        cla.dndOff();
                        hangOn();
                        return;
                    }
                    if (cla.sipData.nowLine == 1) {
                        cla.sipData.lineStaA[0] = 0;
                        return;
                    }

                }
                if (cla.vtsip.cmpAstr.equals("2")) {
                    if (cla.sipData.nowLine == 0) {
                        cla.line2ring_f = 0;
                        cla.status_tim = 0;
                        return;
                    }
                    cla.line2ring_f = 0;
                    cla.sipData.status = "Line2 對方已掛斷";
                    cla.status_tim = 50;
                    cla.sipAct("line1", null);
                    if (cla.sipData.lineStaA[0] == 0) {
                        cla.dndOff();
                        hangOn();
                    }
                    return;
                }

            }

            if (cla.vtsip.ncmpA("Line *: call ended.")) {
                if (cla.byeDelayTime == 0) {
                    return;
                }
                if (cla.vtsip.cmpAstr.equals("1")) {
                    cla.sipData.status = "結束通話";
                    cla.sipData.action = "OK";
                    cla.callto = "";
                    cla.callfrom = "";
                    cla.status_tim = 100;
                    cla.sipData.connectSta = 0;
                    cla.sipData.lineStaA[cla.sipData.nowLine] = 0;
                    cla.sipData.phoneSta = 3;
                    cla.handStatus = 0;
                    cla.dtmf_enable_f = 0;
                    cla.dndOff();
                    hangOn();
                    return;
                }
                if (cla.vtsip.cmpAstr.equals("2")) {
                    cla.sipData.lineStaA[cla.sipData.nowLine] = 0;
                    return;
                }
            }

            if (cla.vtsip.ncmpA("Line muted.")) {
                cla.mute_f = 1;
                return;
            }

            if (cla.vtsip.ncmpA("Line unmuted.")) {
                cla.mute_f = 0;
                return;
            }

            if (cla.vtsip.ncmpA("Line 2 is now active.")) {
                cla.sipData.nowLine = 1;
                if (cla.sipData.line2CallNo.length() == 0) {
                    return;
                }
                sipAct("call", new String[]{cla.sipData.line2CallNo});
                cla.sipData.line2CallNo = "";
                return;
            }
            if (cla.vtsip.ncmpA("Line 1 is now active.")) {
                cla.sipData.nowLine = 0;
                return;
            }
            if (cla.vtsip.ncmpA("Line 1 is already active.")) {
                cla.sipData.nowLine = 0;
                return;
            }
            if (cla.vtsip.ncmpA("Line 2 is already active.")) {
                cla.sipData.nowLine = 1;
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
    }

    void clearKeypad() {
        keypad_str = "";
        keypad_on_f = 0;

    }

    void sipTwinckleAction(String act, String[] paras) {
        SipPhone cla = this;
        String str;
        int nowLine = sipData.nowLine;

        if (act.equals("loadSip")) {
            sshWriteSip("twinkle -c\n");
            sipData.status = "Load SIP Phone";
            sipData.action = "載入SIP ....";
            return;
        }

        if (act.equals("call")) {
            str = "call " + paras[0] + "\n";
            sshWriteSip(str);
            sipData.lineNameA[nowLine] = "";
            sipData.lineNoA[nowLine] = paras[0];
            sipData.lineStaA[nowLine] = 1;
            if (GB.lang == 0) {
                sipData.status = "Call Out ....";
                sipData.action = "Call <" + paras[0] + ">";
            }
            if (GB.lang == 1) {
                sipData.status = "撥號中 ....";
                if (sipData.nowLine == 0) {
                    sipData.action = "撥打 <" + paras[0] + ">";
                } else {
                    sipData.action = "線路2: 連線到 <" + paras[0] + ">";
                }
            }
            status_tim = 100;
            clearKeypad();
            return;
        }

        if (act.equals("hold")) {
            if (sipData.lineStaA[sipData.nowLine] != 3) {
                return;
            }
            sipActName = "hold";
            sipActTime = 50;
            if ((sipData.lineFlagA[cla.sipData.nowLine] & 1) == 0) {
                sshWriteSip("hold\n");
            } else {
                sshWriteSip("retrieve\n");
            }
            return;
        }

        if (act.equals("mute")) {
            if (sipData.lineStaA[sipData.nowLine] != 3) {
                return;
            }
            sipActName = "mute";
            sipActTime = 50;
            sshWriteSip("mute\n");
            return;
        }

        if (act.equals("line2")) {
            sshWriteSip("line 2\n");
            return;
        }
        if (act.equals("bye")) {
            byeDelayTime = 50;
            sshWriteSip("bye\n");
            return;
        }
        if (act.equals("line1")) {
            sshWriteSip("line 1\n");
            return;
        }
        if (act.equals("reject")) {
            sshWriteSip("reject\n");
            return;
        }
        if (act.equals("answer")) {
            sshWriteSip("answer\n");
        }

    }
    
    
    void sipTwinckleActionxxx(String act, String[] paras) {
        SipPhone cla = this;
        String str;
        int nowLine = sipData.nowLine;
        if (act.equals("call")) {
            str = "call " + paras[0] + "\n";
            sshWriteSip(str);
            sipData.lineNameA[nowLine] = "";
            sipData.lineNoA[nowLine] = paras[0];
            sipData.lineFlagA[nowLine] = 1;
            sipData.lineStaA[nowLine] = 1;
            if (GB.lang == 0) {
                sipData.status = "Call Out ....";
                sipData.action = "Call <" + paras[0] + ">";
            }
            if (GB.lang == 1) {
                sipData.status = "撥號中 ....";
                if (sipData.nowLine == 0) {
                    sipData.action = "撥打 <" + paras[0] + ">";
                } else {
                    sipData.action = "線路2: 連線到 <" + paras[0] + ">";
                }
            }

            status_tim = 100;
            clearKeypad();
            return;
        }
        if (act.equals("loadSip")) {
            sshWriteSip("twinkle -c\n");
            sipData.status = "Load SIP Phone";
            sipData.action = "載入SIP ....";
            return;
        }
        if (act.equals("hold")) {
            if (sipData.lineStaA[nowLine] == 2) {
                sshWriteSip("hold\n");
                sipData.lineStaA[nowLine] = 3;
                return;
            }
            if (sipData.lineStaA[nowLine] == 3) {
                sshWriteSip("retrieve\n");
                sipData.lineStaA[nowLine] = 2;
                return;
            }
            return;
        }

        if (act.equals("line2")) {
            //sipData.dtmfOn_f = 1;
            sshWriteSip("line 2\n");
            //sipData.nowLine = 1;
            return;
        }
        if (act.equals("bye")) {
            byeDelayTime = 50;
            sshWriteSip("bye\n");
            return;
        }
        if (act.equals("line1")) {
            //sipData.dtmfOn_f = 0;
            sshWriteSip("line 1\n");
            //sipData.nowLine = 0;
            return;
        }
        if (act.equals("reject")) {
            sshWriteSip("reject\n");
            return;
        }

    }

    void sipLinphoneAction(String act, String[] paras) {

    }

    void sipAct(String act, String[] paras) {
        if (GB.linphone_twinkle_f == 0) {
            sipLinphoneAction(act, paras);
        }
        if (GB.linphone_twinkle_f == 1) {
            sipTwinckleAction(act, paras);
        }

    }

    public void sshWriteSip(String shellCommand) {
        System.out.print("TX: " + shellCommand);

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
            System.out.print("Cmd: "+shellCommand);
            cla.sshShl.outStrm.write(shellCommand.getBytes());
        } catch (IOException ex) {
        }
        try {
            cla.sshShl.outStrm.flush();
        } catch (IOException ex) {
        }
    }

    static public int chkCarInfLegel(int ii, int jj) {
        String str;
        str = GB.carInf_name[ii][jj];
        if (str.length() == 0) {
            return 0;
        }
        str = GB.carInf_reg[ii][jj];
        if (str.length() == 0) {
            return 0;
        }
        str = GB.carInf_regno[ii][jj];
        if (str.length() == 0) {
            return 0;
        }
        if (Lib.str2int(str, -1) < 0) {
            return 0;
        }
        str = GB.carInf_sip[ii][jj];
        if (!Lib.chkStrIsIp(str)) {
            return 0;
        }
        str = GB.carInf_pbx[ii][jj];
        if (!Lib.chkStrIsIp(str)) {
            return 0;
        }
        str = GB.carInf_local[ii][jj];
        if (!Lib.chkStrIsIp(str)) {
            return 0;
        }
        str = GB.carInf_switch[ii][jj];
        if (!Lib.chkStrIsIp(str)) {
            return 0;
        }
        return 1;
    }

    public void loadSockTx(TrxPack tpk, Ssocket ssk) {
        SipPhone cla = this;
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
            bts[inx++] = (byte) (tpk.idBase + i);
            bts[inx++] = (byte) (0xa9);
            bts[inx++] = (byte) (dlen & 255);
            bts[inx++] = (byte) ((dlen >> 8) & 255);
            for (int j = 0; j < dlen; j++) {
                bts[inx++] = tpk.txData[i][j];
            }
            tpk.txLen[i] = 0;
        }
        ssk.stm.tbuf_byte = inx;
        ssk.stm.enc_mystm();
    }

    //pi io 
    public void loadTxPiIo(TrxPack tpk, int packInx) {
        SipPhone cla = this;
        tpk.nowPack = packInx;
        tpk.loadStart();
        tpk.loadWord(cla.piIoDeviceId);
        tpk.loadWord(0xffff);
        tpk.loadWord(0xab00);
        tpk.loadWord(10);
        tpk.loadWord(0x1000);//cmd
        tpk.loadWord(0x0123);//par0
        tpk.loadWord(0x4567);//par1
        tpk.loadWord(0x89ab);//par2
        tpk.loadWord(0xcdef);//par3
        tpk.txLen[packInx] = tpk.txDataPt;
    }

    //outside mcu controller 
    public void loadTxPiUart1(TrxPack tpk, int packInx) {
        SipPhone cla = this;
        tpk.nowPack = packInx;
        tpk.loadStart();
        tpk.loadWord(cla.piMcuDeviceId);
        tpk.loadWord(0xffff);
        tpk.loadWord(0xab00);
        tpk.loadWord(10);
        tpk.loadWord(0x1000);//par0
        int ibuf = cla.handStatus;
        ibuf += cla.pttEn_f << 8;
        ibuf += cla.amaMute_f << 9;
        tpk.loadWord(ibuf);
        tpk.loadWord(0);//par1
        tpk.loadWord(0);//par2
        tpk.loadWord(0);//par3
        tpk.txLen[packInx] = tpk.txDataPt;
    }

    public int loadSipInfData(byte[] txBytes, int stInx) {
        int i;
        SipPhone cla = this;
        byte[] tmpbyte;
        int stx_index = 0;
        int txlen;
        int mm, nn;
        int nowLine = sipData.nowLine;
        //ssk.stm.tbuf[stx_index++] = (byte) GB.sipmd_device_id;
        //===================================================
        int pos = stInx;

        txBytes[pos++] = (byte) (cla.sipPhoneDeviceId & 255);
        txBytes[pos++] = (byte) ((cla.sipPhoneDeviceId >> 8) & 255);

        if(GB.prgMode==1)
            GB.real_ip_str="192.168.0.28";//<<debug
        
        String[] ipStrA = GB.real_ip_str.split("\\.");
        txBytes[pos++] = (byte) Lib.str2int(ipStrA[3], -1, 255, 0);
        txBytes[pos++] = (byte) Lib.str2int(ipStrA[2], -1, 255, 0);

        //txBytes[pos++] = (byte) 255;
        //txBytes[pos++] = (byte) 255;
        txBytes[pos++] = (byte) 0x00;
        txBytes[pos++] = (byte) 0xab;
        int tempPt = pos;
        txBytes[pos++] = (byte) 0x00;
        txBytes[pos++] = (byte) 0x00;
        txBytes[pos++] = (byte) 0x00;
        txBytes[pos++] = (byte) 0x10;
        //============================
        txBytes[pos++] = 0x00;       //ioBuf
        txBytes[pos++] = (byte) 4;
        txBytes[pos++] = (byte) ioBuf[0];
        txBytes[pos++] = (byte) ioBuf[1];
        txBytes[pos++] = (byte) ioBuf[2];
        txBytes[pos++] = (byte) ioBuf[3];
        //===================================================
        txBytes[pos++] = 0x10;       //sipphone status
        txBytes[pos++] = (byte) 10;
        txBytes[pos++] = (byte) cla.sipData.phoneSta;
        txBytes[pos++] = (byte) cla.sipData.connectSta;
        txBytes[pos++] = (byte) cla.handStatus;
        txBytes[pos++] = (byte) GB.ear_speaker_vol;
        txBytes[pos++] = (byte) GB.phset_speaker_vol;
        txBytes[pos++] = (byte) GB.ear_mic_sens;
        txBytes[pos++] = (byte) GB.phset_mic_sens;
        cla.sipflag[0] = 0;
        if (mute_f == 1) {
            cla.sipflag[0] += 0x01;
        }
        if (GB.syssec_f == 1) {
            cla.sipflag[0] += 0x02;
        }
        if (cla.sipData.nowLine == 1) {
            cla.sipflag[0] += 0x04;
        }
        if (cla.sipData.dtmfOn_f == 1) {
            cla.sipflag[0] += 0x08;
        }
        if (cla.sipData.lineStaA[0] == 3) {//hold
            cla.sipflag[0] += 0x10;
        }
        if (cla.sipData.lineStaA[1] == 3) {//hold
            cla.sipflag[0] += 0x20;
        }
        if (cla.line2ring_f == 1) {
            cla.sipflag[0] += 0x40;
        }

        txBytes[pos++] = (byte) cla.sipflag[0];
        txBytes[pos++] = (byte) cla.sipflag[1];
        txBytes[pos++] = (byte) cla.sipflag[2];
        //===================================================
        //tmpbyte = cla.status_str.getBytes();
        tmpbyte = sipData.status.getBytes();
        txlen = tmpbyte.length;
        if (txlen > 40) {
            txlen = 40;
        }
        //==================
        txBytes[pos++] = 0x11;       //
        txBytes[pos++] = (byte) txlen;
        for (i = 0; i < txlen; i++) {
            txBytes[pos++] = tmpbyte[i];
        }
        //===================================================
        //tmpbyte = cla.action_str.getBytes();
        tmpbyte = sipData.action.getBytes();
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
        txBytes[pos++] = 0x12;       //
        txBytes[pos++] = (byte) txlen;
        for (i = 0; i < txlen; i++) {
            txBytes[pos++] = tmpbyte[i];
        }
        //===================================================
        tmpbyte = cla.callto.getBytes();
        txlen = tmpbyte.length;
        if (txlen > 10) {
            txlen = 10;
        }
        //==================
        txBytes[pos++] = 0x13;       //
        txBytes[pos++] = (byte) txlen;
        for (i = 0; i < txlen; i++) {
            txBytes[pos++] = tmpbyte[i];
        }
        //===================================================
        tmpbyte = cla.callfrom.getBytes();
        txlen = tmpbyte.length;
        if (txlen > 10) {
            txlen = 10;
        }
        //==================
        txBytes[pos++] = 0x14;       //
        txBytes[pos++] = (byte) txlen;
        for (i = 0; i < txlen; i++) {
            txBytes[pos++] = tmpbyte[i];
        }
        //===================================================
        /*
        if (txSipInf_step == 5) {
            if (txSipInf_step1 >= GB.carTypeName_len) {
                txSipInf_step++;
            }
        } else {
            if (++txSipInf_step >= 7) {
                txSipInf_step = 0;
            }
            txSipInf_step1 = 0;
        }
         */

        if (txSipInf_step_wait_f == 0) {
            if (++txSipInf_step >= 7) {
                txSipInf_step = 0;
            }
            txSipInf_step_a = 0;
            txSipInf_step_b = 0;
        }
        char[] chA;
        byte[] bytes;
        switch (txSipInf_step) {
            case 0:
                txSipInf_step_wait_f = 0;
                bytes = new byte[20];
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
                
                slst = GB.sipui_ip_str.split("\\.");
                bytes[12] = (byte) Lib.str2int(slst[0], -1, 255, 0);
                bytes[13] = (byte) Lib.str2int(slst[1], -1, 255, 0);
                bytes[14] = (byte) Lib.str2int(slst[2], -1, 255, 0);
                bytes[15] = (byte) Lib.str2int(slst[3], -1, 255, 0);
                
                slst = GB.switch_ip_str.split("\\.");
                bytes[16] = (byte) Lib.str2int(slst[0], -1, 255, 0);
                bytes[17] = (byte) Lib.str2int(slst[1], -1, 255, 0);
                bytes[18] = (byte) Lib.str2int(slst[2], -1, 255, 0);
                bytes[19] = (byte) Lib.str2int(slst[3], -1, 255, 0);
                
                
                
                txBytes[pos++] = 0x15;       //
                txBytes[pos++] = (byte) 20;
                for (i = 0; i < 20; i++) {
                    txBytes[pos++] = bytes[i];
                }
                //=============================

                tmpbyte = GB.phone_name.getBytes();
                txBytes[pos++] = (byte) (0x16);       //
                txBytes[pos++] = (byte) tmpbyte.length;
                for (i = 0; i < tmpbyte.length; i++) {
                    txBytes[pos++] = tmpbyte[i];
                }

                chA = GB.phone_no.toCharArray();
                txBytes[pos++] = 0x17;       //
                txBytes[pos++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    txBytes[pos++] = (byte) chA[i];
                }
                chA = GB.sip_server_ip.toCharArray();
                txBytes[pos++] = 0x18;       //
                txBytes[pos++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    txBytes[pos++] = (byte) chA[i];
                }
                break;
            case 1:
                txSipInf_step_wait_f = 0;
                for (mm = 0; mm < 8; mm++) {

                    tmpbyte = GB.hotline_nameA[mm].getBytes();
                    txBytes[pos++] = (byte) (0x20 + mm);       //
                    txBytes[pos++] = (byte) tmpbyte.length;
                    for (i = 0; i < tmpbyte.length; i++) {
                        txBytes[pos++] = tmpbyte[i];
                    }
                }
                break;
            case 2:
                txSipInf_step_wait_f = 0;
                for (mm = 0; mm < 8; mm++) {
                    chA = GB.hotline_noA[mm].toCharArray();
                    txBytes[pos++] = (byte) (0x30 + mm);       //
                    txBytes[pos++] = (byte) chA.length;
                    for (i = 0; i < chA.length; i++) {
                        txBytes[pos++] = (byte) chA[i];
                    }
                }
                break;
            case 3:
                txSipInf_step_wait_f = 0;
                chA = GB.sipVersion.toCharArray();
                txBytes[pos++] = 0x40;       //
                txBytes[pos++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    txBytes[pos++] = (byte) chA[i];
                }
                //============================
                chA = GB.web_password.toCharArray();
                txBytes[pos++] = 0x41;       //
                txBytes[pos++] = (byte) chA.length;
                for (i = 0; i < chA.length; i++) {
                    txBytes[pos++] = (byte) chA[i];
                }
                //=============================
                txBytes[pos++] = 0x42;       //
                txBytes[pos++] = 6;       //
                txBytes[pos++] = (byte) GB.hotline_inxA[0];       //
                txBytes[pos++] = (byte) GB.hotline_inxA[1];       //
                txBytes[pos++] = (byte) GB.hotline_inxA[2];       //
                txBytes[pos++] = (byte) GB.hotline_inxA[3];       //
                txBytes[pos++] = (byte) GB.carType_inx;
                txBytes[pos++] = (byte) GB.carNo_inx;

                if (cla.set_local_ip_cnt < 10) {
                    cla.set_local_ip_cnt++;
                    System.out.println("\n**************************************************** cla.set_local_ip_f");
                    slst = GB.sipui_ip_str.split("\\.");
                    if (slst.length == 4) {
                        txBytes[pos++] = 0x43;       //
                        txBytes[pos++] = (byte) 4;
                        for (i = 0; i < 4; i++) {
                            txBytes[pos++] = (byte) Lib.str2int(slst[i], -1, 255, 0);
                        }
                    }
                }
                if (cla.set_switch_ip_cnt < 10) {
                    cla.set_switch_ip_cnt++;
                    System.out.println("\n**************************************************** cla.set_switch_ip_f");
                    slst = GB.switch_ip_str.split("\\.");
                    if (slst.length == 4) {
                        txBytes[pos++] = 0x44;       //
                        txBytes[pos++] = (byte) 4;
                        for (i = 0; i < 4; i++) {
                            txBytes[pos++] = (byte) Lib.str2int(slst[i], -1, 255, 0);
                        }
                    }
                    if (cla.set_switch_ip_cnt == 20) {
                        //Lib.exe("sudo shutdown -r +0");
                    }

                }
                txBytes[pos++] = 0x45;       //
                txBytes[pos++] = (byte) (GB.carTypeName_len + 1);       //
                txBytes[pos++] = (byte) GB.carTypeName_len;       //
                for (int ii = 0; ii < GB.carTypeName_len; ii++) {
                    txBytes[pos++] = (byte) GB.carInf_strAA[ii].size();       //
                }
                break;

            case 4:
                txSipInf_step_wait_f = 1;
                for (mm = 0; mm < 8; mm++) {
                    if (txSipInf_step_a >= 24) {
                        txSipInf_step_wait_f = 0;
                        break;
                    }
                    tmpbyte = GB.carTypeName[txSipInf_step_a].getBytes();
                    txBytes[pos++] = (byte) (0x50);       //
                    txBytes[pos++] = (byte) (tmpbyte.length + 1);
                    txBytes[pos++] = (byte) (txSipInf_step_a);       //
                    for (i = 0; i < tmpbyte.length; i++) {
                        txBytes[pos++] = tmpbyte[i];
                    }
                    txSipInf_step_a++;
                }
                break;
            case 5:
                txSipInf_step_wait_f = 1;
                int vv = 0;
                for (int kk = 0; kk < 64; kk++) {
                    if (txSipInf_step_a >= GB.carInf_strAA.length) {
                        txSipInf_step_wait_f = 0;
                        break;
                    }

                    int carInf_len = GB.carInf_strAA[txSipInf_step_a].size();
                    if (carInf_len == 0) {
                        txSipInf_step_a++;
                        txSipInf_step_b = 0;
                        if (txSipInf_step_a >= GB.carTypeName_len) {
                            txSipInf_step_wait_f = 0;
                            break;
                        }
                        continue;
                    }
                    if (txSipInf_step_b >= carInf_len) {
                        txSipInf_step_a++;
                        txSipInf_step_b = 0;
                        if (txSipInf_step_a >= GB.carTypeName_len) {
                            txSipInf_step_wait_f = 0;
                            break;
                        }
                        continue;
                    }
                    String infStr = GB.carInf_strAA[txSipInf_step_a].get(txSipInf_step_b);
                    String[] infStrA = infStr.split("~");
                    if (infStrA.length != 8) {
                        continue;
                    }
                    int index = Integer.parseInt(infStrA[0]);

                    tmpbyte = infStrA[1].getBytes();
                    txBytes[pos++] = (byte) (0x60);       //
                    txBytes[pos++] = (byte) (tmpbyte.length + 3);
                    txBytes[pos++] = (byte) (txSipInf_step_a);       //
                    txBytes[pos++] = (byte) (txSipInf_step_b);       //
                    txBytes[pos++] = (byte) (index);       //
                    for (i = 0; i < tmpbyte.length; i++) {
                        txBytes[pos++] = tmpbyte[i];
                    }
                    txSipInf_step_b++;
                    vv++;
                    if (vv >= 8) {
                        break;
                    }
                }
                break;

            case 6:
                //cla.cmd_cnt=1;
                //cla.cmd_para0=0;
                txSipInf_step_wait_f = 0;

                switch (cla.cmd_cnt) {
                    case 0:
                        break;
                    case 1:
                        nn = cla.cmd_para0;
                        mm = cla.cmd_para1;
                        txBytes[pos++] = (byte) (0xA0);       //
                        txBytes[pos++] = (byte) 3;
                        txBytes[pos++] = (byte) cla.cmd_cnt;
                        txBytes[pos++] = (byte) cla.cmd_para0;
                        txBytes[pos++] = (byte) cla.cmd_para1;

                        tmpbyte = GB.carInf_reg[nn][mm].getBytes();
                        txBytes[pos++] = (byte) (0xA1);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }
                        tmpbyte = GB.carInf_regno[nn][mm].getBytes();
                        txBytes[pos++] = (byte) (0xA2);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }
                        tmpbyte = GB.carInf_sip[nn][mm].getBytes();
                        txBytes[pos++] = (byte) (0xA3);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }

                        tmpbyte = GB.carInf_pbx[nn][mm].getBytes();
                        txBytes[pos++] = (byte) (0xA4);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }

                        tmpbyte = GB.carInf_local[nn][mm].getBytes();
                        txBytes[pos++] = (byte) (0xA5);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }

                        tmpbyte = GB.carInf_switch[nn][mm].getBytes();
                        txBytes[pos++] = (byte) (0xA6);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }
                        break;

                    case 2:
                        txBytes[pos++] = (byte) (0xA0);       //
                        txBytes[pos++] = (byte) 3;
                        txBytes[pos++] = (byte) cla.cmd_cnt;
                        txBytes[pos++] = (byte) 0;
                        txBytes[pos++] = (byte) 0;

                        tmpbyte = GB.phone_name.getBytes();
                        txBytes[pos++] = (byte) (0xA1);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }
                        tmpbyte = GB.phone_no.getBytes();
                        txBytes[pos++] = (byte) (0xA2);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }
                        tmpbyte = GB.sipmd_ip_str.getBytes();
                        txBytes[pos++] = (byte) (0xA3);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }

                        tmpbyte = GB.sip_server_ip.getBytes();
                        txBytes[pos++] = (byte) (0xA4);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }

                        tmpbyte = GB.sipui_ip_str.getBytes();
                        txBytes[pos++] = (byte) (0xA5);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }

                        tmpbyte = GB.switch_ip_str.getBytes();
                        txBytes[pos++] = (byte) (0xA6);       //
                        txBytes[pos++] = (byte) tmpbyte.length;
                        for (i = 0; i < tmpbyte.length; i++) {
                            txBytes[pos++] = tmpbyte[i];
                        }
                        break;

                }

                break;

        }
        txBytes[tempPt++] = (byte) ((pos - 8) & 255);
        txBytes[tempPt++] = (byte) (((pos - 8) >> 8) & 255);
        return pos;

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

        GB.real_ip_str = GB.sipmd_ip_str;
        GB.real_ipmask_str = GB.sipmd_ipmask_str;
        GB.real_gateway_str = GB.sipmd_gateway_str;

        //============================    
    }
    void hangOnPrg() {
        System.out.println("\n********** Hang on *********");
        setting_on_f = 0;
        keypad_on_f = 0;
        keypad_str = "";
        dtmfStr = "";
        sipData.lineFlagA[sipData.nowLine] = 0;
        sipData.handStaA[sipData.nowLine] = 0;
        txShellEsc();
        if (sipData.phoneSta < 3) {//no register
            return;
        }
        if (GB.lang == 0) {
            sipData.action = "Hang On";
        }
        if (GB.lang == 1) {
            sipData.action = "掛上電話";
        }
        status_tim = 50;
        //==============================
        SipPhone cla = this;
        int nowSta = cla.sipData.lineStaA[cla.sipData.nowLine];
        int otherSta = cla.sipData.lineStaA[cla.sipData.nowLine ^ 1];
        int otherLine = cla.sipData.nowLine ^ 1;
        if (nowSta == 1 || nowSta == 3) {//call out or connect
            sipAct("bye", null);
        }
        if (nowSta == 2) {//call in
            sipAct("reject", null);
        }
        if (otherSta != 0) {
            if (otherLine == 0) {
                sipAct("line1", null);
            } else {
                sipAct("line2", null);
            }
        }else{
            if (otherLine == 0) {
                sipAct("line1", null);
            }
        }
    }

    void hangOnPrgxxx() {
        line2ring_f = 0;

        if (sipData.phoneSta == 4) {
            if (sipData.nowLine == 1) {
                sipAct("bye", null);
                sipAct("line1", null);
                status_tim = 0;
                if (sipData.lineStaA[0] == 0) {
                    hangOn();
                }
                return;
            } else {
                hangOn();
            }
            sipAct("bye", null);
            hangOn();
            return;
        }
        if (sipData.phoneSta == 5) {
            if (sipData.connectSta == 2) {
                sipAct("reject", null);
                hangOn();
                return;
            }
            if (sipData.connectSta == 1) {
                sipAct("bye", null);
                sipAct("line1", null);
                hangOn();
                return;
            }
            sipAct("bye", null);
            sipAct("line1", null);
            hangOn();
            return;
        }
        sipAct("line1", null);
        hangOn();

    }

    int chkSipRx(byte[] bts, int btsLen, int stInx) {
        SipPhone cla = this;
        String str;
        int i, ibuf;
        int inx = stInx;
        int cmdinx;
        int cmdlen;
        int cmd;
        int btsEnd = btsLen + stInx;
        byte[] bytes;
        int clrCarType_f = 0;

        String commandData;
        int deviceId = (bts[inx + 0] & 255) + (bts[inx + 1] & 255) * 256;
        int serialId = (bts[inx + 2] & 255) + (bts[inx + 3] & 255) * 256;
        int groupId = (bts[inx + 4] & 255) + (bts[inx + 5] & 255) * 256;
        int packLen = (bts[inx + 6] & 255) + (bts[inx + 7] & 255) * 256;
        int packCmd = (bts[inx + 8] & 255) + (bts[inx + 9] & 255) * 256;
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
        while (inx < btsEnd) {
            cmd = bts[inx];
            cmdlen = bts[inx + 1];
            cmdinx = inx + 2;
            switch (cmd) {
                case 0x1f://"tick by ics"
                    int uiPort = 0;
                    String uiIpStr = "";
                    uiIpStr = (bts[cmdinx++] & 255) + ".";
                    uiIpStr += (bts[cmdinx++] & 255) + ".";
                    uiIpStr += (bts[cmdinx++] & 255) + ".";
                    uiIpStr += (bts[cmdinx++] & 255) + "";
                    uiPort = (bts[cmdinx++] & 255);
                    uiPort += (bts[cmdinx++] & 255) * 256;
                    break;

                case 0x11://direct linphone command
                    if (shellCommandStatus == 1) {
                        //txShellEsc();
                    }
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bts[cmdinx++];
                    }
                    cla.sshWriteSip(new String(bytes));
                    break;
                case 0x12://direct shell command
                    if (shellCommandStatus == 1) {
                        //txShellEsc();
                    }
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bts[cmdinx++];
                    }
                    cla.sshWriteShl(new String(bytes));
                    //System.out.println(new String(bytes));
                    break;
                case 0x13://save net address
                    ibuf = bts[cmdinx + 0] & 255;
                    str = (bts[cmdinx + 1] & 255) + ".";
                    str += (bts[cmdinx + 2] & 255) + ".";
                    str += (bts[cmdinx + 3] & 255) + ".";
                    str += (bts[cmdinx + 4] & 255) + "";
                    switch (bts[cmdinx + 0] & 255) {
                        case 0:
                            GB.sipmd_ip_str = str;
                            Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
                            Base3.scla.editNewDb("sipmd_ip_str", "" + GB.sipmd_ip_str);
                            reset_network();
                            clrCarType_f = 1;
                            //Base3.scla.netInf(0);
                            break;
                        case 1:
                            GB.sipmd_ipmask_str = str;
                            Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
                            Base3.scla.editNewDb("sipmd_ipmask_str", "" + GB.sipmd_ipmask_str);
                            reset_network();
                            //Base3.scla.netInf(0);
                            break;
                        case 2:
                            GB.sipmd_gateway_str = str;
                            Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
                            Base3.scla.editNewDb("sipmd_gateway_str", "" + GB.sipmd_gateway_str);
                            reset_network();
                            //Base3.scla.netInf(0);
                            break;
                        case 255:
                            GB.sipmd_ip_str = str;
                            str = (bts[cmdinx + 5] & 255) + ".";
                            str += (bts[cmdinx + 6] & 255) + ".";
                            str += (bts[cmdinx + 7] & 255) + ".";
                            str += (bts[cmdinx + 8] & 255) + "";
                            GB.sipmd_ipmask_str = str;
                            str = (bts[cmdinx + 9] & 255) + ".";
                            str += (bts[cmdinx + 10] & 255) + ".";
                            str += (bts[cmdinx + 11] & 255) + ".";
                            str += (bts[cmdinx + 12] & 255) + "";
                            GB.sipmd_gateway_str = str;
                            Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
                            Base3.scla.editNewDb("sipmd_ip_str", "" + GB.sipmd_ip_str);
                            Base3.scla.editNewDb("sipmd_ipmask_str", "" + GB.sipmd_ipmask_str);
                            Base3.scla.editNewDb("sipmd_gateway_str", "" + GB.sipmd_gateway_str);
                            reset_network();
                            //Base3.scla.netInf(0);
                            break;
                    }
                    break;
                case 0x14://sip phone command
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bts[cmdinx++];
                    }
                    
                    String valueStr = new String(bytes, StandardCharsets.UTF_8);
                    phoneCommandIn(valueStr);                    
                    break;
                case 0x15:
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bts[cmdinx++];
                    }
                    GB.phone_no = new String(bytes);
                    Base3.scla.editNewDb("phone_no", "" + GB.phone_no);
                    clrCarType_f = 1;
                    break;
                case 0x16:
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bts[cmdinx++];
                    }
                    GB.sip_server_pin = new String(bytes);
                    Base3.scla.editNewDb("sip_server_pin", "" + GB.sip_server_pin);
                    break;
                case 0x17:
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bts[cmdinx++];
                    }
                    GB.sip_server_ip = new String(bytes);
                    Base3.scla.editNewDb("sip_server_ip", "" + GB.sip_server_ip);
                    clrCarType_f = 1;
                    break;
                case 0x18:
                    cla.pbxRegister();
                    //cla.sshWriteSip("quit");
                    break;
                case 0x19:
                    GB.hotline_inxA[0] = bts[cmdinx + 0] & 255;
                    GB.hotline_inxA[1] = bts[cmdinx + 1] & 255;
                    GB.hotline_inxA[2] = bts[cmdinx + 2] & 255;
                    GB.hotline_inxA[3] = bts[cmdinx + 3] & 255;
                    Base3.scla.editNewDb("hotline_inxA~0", "" + GB.hotline_inxA[0]);
                    Base3.scla.editNewDb("hotline_inxA~1", "" + GB.hotline_inxA[1]);
                    Base3.scla.editNewDb("hotline_inxA~2", "" + GB.hotline_inxA[2]);
                    Base3.scla.editNewDb("hotline_inxA~3", "" + GB.hotline_inxA[3]);
                    break;
                case 0x1A:
                    cla.cmd_cnt = bts[cmdinx + 0] & 255;
                    cla.cmd_para0 = bts[cmdinx + 1] & 255;
                    cla.cmd_para1 = bts[cmdinx + 2] & 255;
                    break;
                case 0x1B:
                    int carType_inx = bts[cmdinx + 0] & 255;
                    int carNo_inx = bts[cmdinx + 1] & 255;
                    if (carType_inx > GB.carTypeName_len) {
                        break;
                    }
                    if (carType_inx > 0) {
                        if (carNo_inx > GB.carInf_len[carType_inx - 1]) {
                            break;
                        }
                    }
                    Base3.scla.editNewDb("carType_inx", "" + carType_inx);
                    Base3.scla.editNewDb("carNo_inx", "" + carNo_inx);
                    cla.sshWriteSip("quit\n");
                    setAll();
                    //cla.shutDown_cnt = 50 * 10;
                    break;
                case 0x1c:
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bts[cmdinx++];
                    }
                    GB.switch_ip_str = new String(bytes);
                    Base3.scla.editNewDb("switch_ip_str", "" + GB.switch_ip_str);
                    clrCarType_f = 1;
                    break;
                case 0x1d:
                    bytes = new byte[cmdlen];
                    for (i = 0; i < cmdlen; i++) {
                        bytes[i] = bts[cmdinx++];
                    }
                    GB.sipui_ip_str = new String(bytes);
                    GB.sipui_ip_str = GB.sipui_ip_str;
                    Base3.scla.editNewDb("no_sipui_ip_str", "" + GB.sipui_ip_str);
                    clrCarType_f = 1;
                    break;

            }
            inx = inx + cmdlen + 2;
        }

        if (clrCarType_f == 1) {
            GB.carType_inx = 0;
            GB.carNo_inx = 0;
            Base3.scla.editNewDb("carType_inx", "" + GB.carType_inx);
            Base3.scla.editNewDb("carNo_inx", "" + GB.carNo_inx);
        }
        return 1;
    }
    
    void speakerAct() {
        int line1Sta = sipData.lineStaA[0];
        int line2Sta = sipData.lineStaA[1];
        int lineSta = sipData.lineStaA[sipData.nowLine];
        int lineElse = sipData.nowLine ^ 1;
        int lineElseSta = sipData.lineStaA[lineElse];
        int handStatus = sipData.handStaA[sipData.nowLine];
        if (handStatus == 1) {
            speakerOn();
            return;
        }
        if (handStatus == 2) {
            if (lineSta == 2) {
                sipAct("answer", null);
                return;
            }
            sipData.lineFlagA[sipData.nowLine] = 0;
            speakerOff();
            if (lineSta == 0) {
                stopDialTone();
            }
            if (lineSta == 1 || lineSta == 3) { //call out or connect
                sipAct("bye", null);
            }
            if (lineElseSta >= 1) {
                if (lineElse == 0) {
                    sipAct("line1", null);
                } else {
                    sipAct("line2", null);
                }
                return;
            } else {
                if (sipData.nowLine == 1) {
                    sipAct("line1", null);
                }
            }
            return;
        }
        if (handStatus == 0) {
            if (lineSta == 0) {
                stopDialTone();
                speakerOn();
                if (!keypad_str.equals("")) {
                    callStr(keypad_str);
                    keypad_str = "";
                    return;
                }
                playDialTone();
                return;
            }
            if (lineSta == 1) { //call out
                speakerOn();
                return;
            }
            if (lineSta == 2) { //call in
                sipAct("answer", null);
                speakerOn();
                return;
            }
            if (lineSta == 3) { //call in
                speakerOn();
            }
        }
    }
    
    void earPhoneAct() {
        int line1Sta = sipData.lineStaA[0];
        int line2Sta = sipData.lineStaA[1];
        int lineSta = sipData.lineStaA[sipData.nowLine];
        int lineElse = sipData.nowLine ^ 1;
        int lineElseSta = sipData.lineStaA[lineElse];
        int handStatus = sipData.handStaA[sipData.nowLine];
        if (handStatus == 2) {
            earPhoneOn();
            return;
        }
        if (handStatus == 1) {
            if (lineSta == 2) {
                sipAct("answer", null);
                return;
            }
            sipData.lineFlagA[sipData.nowLine] = 0;
            earPhoneOff();
            if (lineSta == 0) {
                stopDialTone();
            }
            if (lineSta == 1 || lineSta == 3) { //call out or connect
                sipAct("bye", null);
            }
            if (lineElseSta >= 1) {
                if (lineElse == 0) {
                    sipAct("line1", null);
                } else {
                    sipAct("line2", null);
                }
                return;
            } else {
                if (sipData.nowLine == 1) {
                    sipAct("line1", null);
                }
            }
            return;
        }
        if (handStatus == 0) {
            if (lineSta == 0) {
                stopDialTone();
                earPhoneOn();
                if (!keypad_str.equals("")) {
                    callStr(keypad_str);
                    keypad_str = "";
                    return;
                }
                playDialTone();
                return;
            }
            if (lineSta == 1) { //call out
                earPhoneOn();
                return;
            }
            if (lineSta == 2) { //call in
                sipAct("answer", null);
                earPhoneOn();
                return;
            }
            if (lineSta == 3) { //call in
                earPhoneOn();
            }
        }
    }

    void speakerSwap() {
        int handStatus = sipData.handStaA[sipData.nowLine];
        if (handStatus != 2) {
            speakerOn();
        } else {
            speakerOff();
        }
    }

    void earPhoneSwap() {
        int handStatus = sipData.handStaA[sipData.nowLine];
        if (handStatus != 1) {
            earPhoneOn();
        } else {
            earPhoneOff();
        }
    }

    void playDialTone() {
        sshWriteSound("aplay /home/pi/kevin/sipphone/dial_tone.wav & PID=$! \n");
    }
    
    void stopDialTone() {
        byte[] bytes;
        bytes = new byte[2];
        bytes[0] = 0x03;
        bytes[1] = 13;
        sshWriteSound("kill $PID\n");
        shellCommandStatus = 0;
    }
    public void sshWriteSound(String shellCommand) {
        SipPhone cla = this;
        if (cla.sshSound == null || cla.sshSound.connect_f == 0) {
            return;
        }
        try {
            cla.sshSound.outStrm.write(shellCommand.getBytes());
        } catch (IOException ex) {
        }
        try {
            cla.sshSound.outStrm.flush();
        } catch (IOException ex) {
        }
    }
    

    void speakerOn() {
        System.out.println("\n********** speaker on *********");
        sipData.handStaA[sipData.nowLine] = 2;
        sipData.handTimeA[sipData.nowLine] = 0;
        setSpeakerVolume();        
        ioBuf[0]&=0xfc;
        ioBuf[0]|=0x03;
        
    }

    void speakerOff() {
        System.out.println("\n********** speaker off *********");
        sipData.handStaA[sipData.nowLine] = 0;
        sipData.handTimeA[sipData.nowLine] = 0;
        ioBuf[0]&=0xfc;
    }

    void earPhoneOn() {
        System.out.println("\n********** earPhone on *********");
        sipData.handStaA[sipData.nowLine] = 1;
        sipData.handTimeA[sipData.nowLine] = 0;
        setEarphoneVolume();        
        ioBuf[0]&=0xfc;
        ioBuf[0]|=0x01;
    }

    void earPhoneOff() {
        System.out.println("\n********** earPhone off *********");
        sipData.handStaA[sipData.nowLine] = 0;
        sipData.handTimeA[sipData.nowLine] = 0;
        ioBuf[0]&=0xfc;
    }

    
    

    void phoneCommandIn(String cmdStr) {
        String[] strA=cmdStr.split(" ");
        switch (strA[0]) {
            case "hangon": //hangon
                hangOnPrg();
                break;
            case "hangoff": //hangoff
                earPhoneAct();
                break;
            case "speaker": //speaker on
                speakerAct();
                break;
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8"
            + "":
            case "9":
            case "*":
            case "#":
            case "ok":
                sipData.handTimeA[sipData.nowLine] = 0;
                phoneKeyin(cmdStr);
                break;
            case "+":
                sipData.handTimeA[sipData.nowLine] = 0;
                volumePlus();
                break;
            case "-":
                sipData.handTimeA[sipData.nowLine] = 0;
                volumeMinus();
                break;
            case "prev":
                show_preno(0);
                break;
            case "up":
                if (keypad_on_f == 1) {
                    break;
                }
                if (setting_on_f == 1) {
                    if (!setId.equals("prevCall")) {
                        break;
                    }
                }
                int lineSta = sipData.lineStaA[sipData.nowLine];
                if (lineSta != 0) {
                    break;
                }
                show_preno(0);
                break;
            case "down":
                if (keypad_on_f == 1) {
                    break;
                }
                if (setting_on_f == 1) {
                    if (!setId.equals("prevCall")) {
                        break;
                    }
                }
                lineSta = sipData.lineStaA[sipData.nowLine];
                if (lineSta != 0) {
                    break;
                }
                show_preno(1);
                break;
            case "right":
                break;
            case "left":
                if (keypad_on_f == 1) {
                    if (keypad_str.length() != 0) {
                        keypad_str = keypad_str.substring(0, keypad_str.length() - 1);
                    }
                    keypad_tim = 0;
                }

                break;
            case "menu":
                break;
            case "esc":
                break;
            case "cancle":
                keypad_on_f = 0;
                keypad_str = "";
                keypad_tim = 0;
                setting_on_f = 0;
                setting_str = "";
                dtmfStr = "";
                status_tim = 0;
                break;
            case "hold":
                sipAct("hold", null);
                break;
            case "line1":
                sipAct("line1", null);
                break;
            case "line2":
                sipAct("line2", null);
                break;
            case "mute":
                sipAct("mute", null);
                break;
            case "transfer":
                transferCall();
                break;
            case "f1":
                break;
            case "f2":
                break;
            case "f3":
                break;
            case "f4":
                break;
            case "book":
                break;
            case "dtmf":
                keypad_tim = 9999;
                status_tim = 0;
                lineSta = sipData.lineStaA[sipData.nowLine];
                if (lineSta != 3) {
                    return;
                }
                if ((sipData.lineFlagA[sipData.nowLine] & 0x04) != 0) {
                    sipData.lineFlagA[sipData.nowLine] &= 0xfb;
                } else {
                    sipData.lineFlagA[sipData.nowLine] |= 0x04;
                }
                break;
            case "call":
                callStr(strA[1]);
                break;
                
            
                
                

        }

    }
    
    
    void xxxcarChg() {

        SipPhone cla = this;
        String str;
        String GB_phone_name = GB.phone_name;
        String GB_phone_no = GB.phone_no;
        String GB_sip_server_ip = GB.sip_server_ip;
        String GB_sip_server_pin = GB.sip_server_pin;
        String GB_sipmd_ip_str = GB.sipmd_ip_str;
        String GB_switch_ip_str = GB.switch_ip_str;
        String GB_sipui_ip_str = GB.sipui_ip_str;

        if (GB.carType_inx == 0 || GB.carNo_inx == 0) {
            return;
        }
        //======================================================
        //int ix = (GB.carType_inx - 1) * 8 + GB.carNo_inx - 1;

        int nn = GB.carType_inx - 1;
        int mm = GB.carNo_inx - 1;

        if (GB.carType_inx >= GB.carTypeName_len) {
            return;
        }
        if (GB.carNo_inx >= GB.carInf_len[nn]) {
            return;
        }

        str = GB.carInf_reg[nn][mm];
        if (str.length() > 0) {
            GB_phone_name = str;
        }
        str = GB.carInf_regno[nn][mm];
        if (Lib.chkStr2int(str)) {
            GB_phone_no = str;
        }
        str = GB.carInf_pbx[nn][mm];
        if (Lib.chkStrIsIp(str)) {
            GB_sip_server_ip = str;
        }
        str = GB.carInf_sip[nn][mm];
        if (Lib.chkStrIsIp(str)) {
            GB_sipmd_ip_str = str;
        }
        str = GB.carInf_switch[nn][mm];
        if (Lib.chkStrIsIp(str)) {
            GB_switch_ip_str = str;
        }
        str = GB.carInf_local[nn][mm];
        if (Lib.chkStrIsIp(str)) {
            GB_sipui_ip_str = str;
        }

        int sip_set_f = 0;
        int pbx_set_f = 0;
        if (!GB_phone_name.equals(GB.phone_name)) {
            GB.phone_name = GB_phone_name;
            pbx_set_f = 1;
        }
        if (!GB_phone_no.equals(GB.phone_no)) {
            GB.phone_no = GB_phone_no;
            pbx_set_f = 1;
        }
        if (!GB_sip_server_ip.equals(GB.sip_server_ip)) {
            GB.sip_server_ip = GB_sip_server_ip;
            pbx_set_f = 1;
        }
        if (!GB_sip_server_pin.equals(GB.sip_server_pin)) {
            GB.sip_server_pin = GB_sip_server_pin;
            pbx_set_f = 1;
        }

        if (!GB_sipmd_ip_str.equals(GB.sipmd_ip_str)) {
            GB.sipmd_ip_str = GB_sipmd_ip_str;
            sip_set_f = 1;
        }
        GB.switch_ip_str = GB_switch_ip_str;
        GB.sipui_ip_str = GB_sipui_ip_str;

        cla.set_local_ip_cnt = 0;
        cla.set_switch_ip_cnt = 0;
        //if (sip_set_f == 1) {
        Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
        Base3.scla.editNewDb("sipmd_ip_str", "" + GB.sipmd_ip_str);
        GB.real_ip_str = GB.sipmd_ip_str;
        //reset_network();
        System.out.println("\n**************************************************** Set network");
        //}
        //if (pbx_set_f == 1) {
        Lib.setTwincleCfg();
        cla.auto_register_tim = GB.auto_register_time - (50 * 10);
        //cla.pbxRegister();
        System.out.println("\n**************************************************** Set PBX");
        //Base3.scla.netInf(0);

        //}
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
        //sshWriteShl(new String(bytes));
        sshWriteShl("kill $PID\n");
        shellCommandStatus = 0;
        dndOff();

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
        //if (shellCommandStatus == 1) {
        txShellEsc();
        //}
        if (setting_on_f == 1) {
            return;
        }
        if (sipData.phoneSta != 3) {
            return;
        }
        if (handStatus == 0) {
            speakerOn();
        }
        save_preno(noStr);
        String str;

        sipAct("call", new String[]{noStr});
        return;
    }

    void ForceCallStr(String noStr) {
        String str;
        str = "call " + noStr + "\n";
        sshWriteSip(str);
        callToStr = "撥打 " + noStr;
        sipData.connectName = noStr;
        sipData.connectNo = noStr;
        callto = noStr;
        //action_str = callToStr;
        keypad_str = "";
        keypad_on_f = 0;
    }

    void phoneKeyin(String cmd) {
        String str;
        byte[] bytes;
        setting_tim = 0;
        if (shellCommandStatus == 1) {
            //txShellEsc();
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
        if (sipData.phoneSta == 3) {
            if (cmd.equals("ok")) {
                if (keypad_str.equals("")) {
                    return;
                }
                callStr(keypad_str);
                keypad_str = "";
                return;
            } else {
                if (wait_dtmf_f == 1) {
                    str = "dtmf " + cmd + "\n";
                    sshWriteSip(str);
                    dtmfStr += cmd;
                    keypad_str = dtmfStr;
                    keypad_tim = 0;
                    keypad_on_f = 1;
                    status_tim = 50 * 30;

                    return;
                }

                keypad_str += cmd;
                keypad_tim = 0;
                keypad_on_f = 1;

            }

        }

        if (sipData.phoneSta == 4) {
            if (!cmd.equals("ok")) {
                if (sipData.dtmfOn_f == 1) {
                    str = "dtmf " + cmd + "\n";
                    sshWriteSip(str);
                    keypad_str = "Send Dtmf " + cmd;
                    keypad_tim = 0;
                    keypad_on_f = 1;
                    return;
                } else {
                    
                    
                    keypad_str += cmd;
                    keypad_tim = 0;
                    keypad_on_f = 1;
                }
            } else {
                if (keypad_str.equals("")) {
                    return;
                }
                sipData.lineStaA[0] = 2;
                sipAct("line2", null);
                sipData.line2CallNo = keypad_str;
                //sipAct("call", new String[]{keypad_str});
                keypad_str = "";
            }
            return;
        }

    }

    void transferCall() {
        if (sipData.phoneSta <= 3) {
            return;
        }
        setting_on_f = 1;
        setting_tim = 0;
        keypad_str = "";
        setting_str = "Transfer To ";
        setId = "transfer";
    }

    void vrVolume() {
        if(nowVrVol_f==0)
            return;
        if (GB.phset_speaker_vol == nowVrVol) {
            nowVrVol_f=0;
            return;
        }
        nowVrVolTime++;
        if (nowVrVolTime < 8) {
            return;
        }
        nowVrVolTime = 0;
        if (GB.phset_speaker_vol < nowVrVol) {
            GB.phset_speaker_vol++;
            if (GB.phset_speaker_vol > 9) {
                GB.phset_speaker_vol = 9;
            }
        } else {
            GB.phset_speaker_vol--;
            if (GB.phset_speaker_vol < 0) {
                GB.phset_speaker_vol = 0;
            }
        }

        setting_str = "Speaker Volume= " + GB.phset_speaker_vol;
        setSpeakerVolume();
        Base3.scla.editNewDb("phset_speaker_vol", "" + GB.phset_speaker_vol);
        setting_on_f = 1;
        setId = "setVolume";
        setting_tim = 0;
        System.out.println("vrVolume set: " + GB.phset_speaker_vol);

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
        //txShellEsc();
        //str = "sudo amixer cset numid=4 " + outVolumeTbl[GB.phset_speaker_vol] + "," + outVolumeTbl[GB.phset_speaker_vol] + "\n";
        //str = "sudo amixer cset numid=4 " + 0 + "," + outVolumeTbl[GB.phset_speaker_vol] + "\n";
        str = "sudo amixer cset numid=4 " + outVolumeTbl[GB.phset_speaker_vol] + "," + 0 + "\n";
        cla.sshWriteShl(str);
    }

    void setEarphoneVolume() {
        SipPhone cla = this;
        byte[] bytes;
        String str;
        //txShellEsc();
        //str = "sudo amixer cset numid=4 " + outVolumeTbl[GB.ear_speaker_vol] + "," + outVolumeTbl[GB.ear_speaker_vol] + "\n";
        //str = "sudo amixer cset numid=4 " + outVolumeTbl[GB.ear_speaker_vol] + "," + 0 + "\n";
        str = "sudo amixer cset numid=4 " + outVolumeTbl[GB.ear_speaker_vol] + "," + 0 + "\n";
        cla.sshWriteShl(str);
    }

    void hangOnTest() {
        System.out.println("\n********** Hang on *********");
        setting_on_f = 0;
        keypad_on_f = 0;
        keypad_str = "";

        SipPhone cla = this;
        String str;
        //System.out.println("hang on");
        if (GB.linphone_twinkle_f == 0) {
            cla.sshWriteSip("terminate\n");
        } else {
            sipAct("bye", null);
            //cla.sshWriteSip("bye\n");
            //cla.sshWriteSip("line 1\n");
        }

        /*
        
        txShellEsc();
        handStatus = 0;
        str = "sudo amixer cset numid=4 " + outVolumeTbl[GB.phset_speaker_vol] + "," + outVolumeTbl[GB.ear_speaker_vol] + "\n";
        cla.sshWriteShl(str);
        str = "sudo amixer cset numid=6 " + inVolumeTblMax[GB.phset_mic_sens] + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";
        //str = "sudo amixer cset numid=6 " + "0" + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";
        cla.sshWriteShl(str);
        mute_f = 0;

        if (cla.sipData.phoneSta < 3) {
            return;
        }
        if (GB.lang == 0) {
            //cla.sipData.status = "Cutting Line ....";
            cla.sipData.action = "Hang On";
        }
        if (GB.lang == 1) {
            //cla.sipData.status = "電話切斷中 ....";
            cla.sipData.action = "掛上電話";
        }
        cla.status_tim = 50;
        cla.sipData.connectSta = 0;
        cla.sipData.phoneSta = 3;
        cla.dtmf_enable_f = 0;
        dndOff();
         */
    }

    void hangOn() {
        if (hangonWaitTime > 0) {
            return;
        }
        hangonWaitTime = 20;
        System.out.println("\n********** Hang on *********");
        setting_on_f = 0;
        keypad_on_f = 0;
        keypad_str = "";
        sipData.dtmfOn_f = 0;
        wait_dtmf_f = 0;

        SipPhone cla = this;
        String str;
        //System.out.println("hang on");
        if (GB.linphone_twinkle_f == 0) {
            cla.sshWriteSip("terminate\n");
        } else {
            sipAct("bye", null);
            cla.sshWriteSip("line 1\n");
        }

        txShellEsc();
        handStatus = 0;
        str = "sudo amixer cset numid=4 " + outVolumeTbl[GB.phset_speaker_vol] + "," + outVolumeTbl[GB.ear_speaker_vol] + "\n";
        cla.sshWriteShl(str);
        str = "sudo amixer cset numid=6 " + inVolumeTblMax[GB.phset_mic_sens] + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";
        //str = "sudo amixer cset numid=6 " + "0" + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";
        cla.sshWriteShl(str);
        ioBuf[0] &= 0xfc;

        mute_f = 0;

        if (cla.sipData.phoneSta < 3) {
            return;
        }
        if (GB.lang == 0) {
            //cla.sipData.status = "Cutting Line ....";
            cla.sipData.action = "Hang On";
        }
        if (GB.lang == 1) {
            //cla.sipData.status = "電話切斷中 ....";
            cla.sipData.action = "掛上電話";
        }
        cla.status_tim = 50;
        cla.sipData.connectSta = 0;
        cla.sipData.phoneSta = 3;
        cla.sipData.lineStaA[0] = 0;
        cla.sipData.lineStaA[1] = 0;

        cla.dtmf_enable_f = 0;
        dndOff();

    }

    void hangOff(int force) {
        System.out.println("\n********** Hang off *********\n");
        SipPhone cla = this;
        if (cla.sipData.connectSta == 2) {
            Date dNow = new Date();
            cla.connected_tim = dNow.getTime();
            cla.sipData.connectSta = 3;
            cla.sipData.connectName = cla.callfrom;
            cla.sipData.connectNo = cla.callfrom;
        }
        if (force == 0) {
            if (handStatus == 1) {
                hangOnPrg();
                return;
            }
        }
        setting_on_f = 0;
        keypad_on_f = 0;
        keypad_str = "";

        String str;
        //System.out.println("hang off");
        txShellEsc();
        str = "sudo amixer cset numid=4 " + outVolumeTbl[GB.ear_speaker_vol] + "," + 0 + "\n";
        cla.sshWriteShl(str);
        str = "sudo amixer cset numid=6 " + inVolumeTbl[GB.ear_mic_sens] + "," + inVolumeTbl[GB.ear_mic_sens] + "\n";

        cla.sshWriteShl(str);

        ioBuf[0] |= 0x01;
        ioBuf[0] &= 0xfd;

        if (handStatus == 0) {
            cla.sshWriteSip("answer\n");
        }
        if (sipData.phoneSta == 3) {
            if (force == 0) {
                cla.sshWriteShl("aplay /home/pi/kevin/sipphone/dial_tone.wav & PID=$! \n");
            }
        } else {
        }
        handStatus = 1;
        handStatusTime = 0;
        dndOn();
    }


    void sskioRx(int format) {
        SipPhone cla = this;
        String str;
        cla.sskio.datain_f = 0;
        cla.sskio.connect_f = 1;
        MyStm stm = cla.sskio.stm;
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
                deviceId = stm.readWord();
                serialId = stm.readWord();
                int groupId = stm.readWord();
                if (deviceId != 0x1946 || groupId != 0xab00) {
                    continue;
                }
                int groupLen = stm.readWord();
                int packCmd = stm.readWord();
                if (packCmd == 0x1000) {
                    piIoStatus0 = stm.readWord();
                    piIoStatus1 = stm.readWord();
                    piIoInFlag0 = stm.readWord();
                    piIoInFlag1 = stm.readWord();
                }
                loadTxPiIo(cla.tpk0, 0);
                continue;
            }
            if (packId == 0xa911) {//uart0 sip
                stm.setRdataNextPt(stm.rdataPt + packLen);
                int okf = chkSipRx(stm.rdata, packLen, stm.rdataPt);
                cla.tpk0.txLen[1] = loadSipInfData(cla.tpk0.txData[1], 0);
                continue;
            }
            if (packId == 0xa912) {//mcuIo
                stm.setRdataNextPt(stm.rdataPt + packLen);
                deviceId = stm.readWord();
                serialId = stm.readWord();
                int groupId = stm.readWord();
                if (deviceId != 0x1945 || groupId != 0xac00) {
                    continue;
                }
                int groupLen = stm.readWord();
                int packCmd = stm.readWord();
                if (packCmd == 0x1000) {
                    piMcuStatus = stm.readWord();
                    piMcuVrAdi = stm.readWord();
                    if (piMcuVrAdi > 0x3ff) {
                        piMcuVrAdi = 0x3ff;
                    }
                    nowVr = 0x3ff - piMcuVrAdi;
                    if (preVr == 0xffff) {
                        preVr = nowVr;
                    }
                    //=========================
                    int delta = nowVr - preVr;
                    if (delta < 0) {
                        delta = 0 - delta;
                    }
                    if (delta < 4) {
                        continue;
                    }
                    preVr = nowVr;
                    //=========================
                    int vol = 0;
                    for (int i = 1; i <= 9; i++) {
                        if (nowVr < (i * 102)) {
                            break;
                        }
                        vol++;
                    }
                    nowVrVolTime = 0;
                    nowVrVol = vol;
                    nowVrVol_f=1;

                }
                //loadTxPiUart1(cla.tpk0, 2);
                continue;
            }
            if (packId == 0xa913) { //uart2
                stm.setRdataNextPt(stm.rdataPt + packLen);
                continue;
            }
            break;
        }
        loadTxPiUart1(cla.tpk0, 2);
        loadSockTx(cla.tpk0, cla.sskio);
        cla.sskio.txret();
    }

    void txSock(Ssocket ssk) {
        try {
            for (int i = 0; i < ssk.stm.txlen; i++) {
                ssk.outstr.write(ssk.stm.tdata[i]);
            }
        } catch (IOException ex) {
        }
        ssk.stm.txlen = 0;
    }

    void pbxRegister() {
        SipPhone cla = this;
        String str;
        cla.sipData.phoneSta = 2;
        //if (cla.sipphone_load_f != 0) {
        if (GB.linphone_twinkle_f == 0) {
            str = "register sip:" + GB.phone_no + "@";
            str += GB.sip_server_ip;
            str += " sip:" + GB.sip_server_ip + ' ' + GB.sip_server_pin + '\n';
            cla.sshWriteSip(str);
        } else {
            Lib.setTwincleCfg();
            cla.sshWriteSip("quit\n");
        }
        //}
        cla.auto_register_tim = 0;

    }

    void reRegister() {
        SipPhone cla = this;
        String str;
        if (cla.sipphone_load_f != 0) {
            if (GB.linphone_twinkle_f == 0) {
                str = "register sip:" + GB.phone_no + "@";
                str += GB.sip_server_ip;
                str += " sip:" + GB.sip_server_ip + ' ' + GB.sip_server_pin + '\n';
                cla.sshWriteSip(str);
            } else {
                cla.sshWriteSip("register -a\n");
            }
        }
        cla.auto_register_tim = 0;

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
        System.out.println("\n*** Web Save ***\n");
        String[] strs = str.split(" ");
        int ibuf = Integer.parseInt(strs[3]);
        if ((ibuf & 0x08) != 0) //reboot
        {
            //System.out.println("sudo shutdown -r +0" + "  start");
            cla.sshWriteSip("quit\n");
            setAll();
            cla.shutDown_cnt = 50 * 10;
            //Lib.exe("sudo shutdown -r +0");
            //System.out.println("sudo shutdown -r +0" + "  end");
            return;
        }
        //=================================================================
        //setAll();
    }

    void setAll() {
        SipPhone cla = this;
        String str;

        String gb_phone_name = GB.phone_name;
        String gb_phone_no = GB.phone_no;
        String gb_sip_server_ip = GB.sip_server_ip;
        String gb_sip_server_pin = GB.sip_server_pin;

        String gb_switch_ip_str = GB.switch_ip_str;
        String gb_sipui_ip_str = GB.sipui_ip_str;

        String gb_sipmd_ip_str = GB.sipmd_ip_str;
        String gb_sipmd_ipmask_str = GB.sipmd_ipmask_str;
        String gb_sipmd_gateway_str = GB.sipmd_gateway_str;
        String gb_ntp_dns = GB.ntp_dns;

        //System.out.println("load Database");
        Base3.scla.x.act(0);
        Base3.scla.x.act(1);
        Base3.scla.x.act(2);
        //==================================================================
        int set_f = 0;
        if (!gb_sipmd_ip_str.equals(GB.sipmd_ip_str)) {
            set_f = 1;
        }
        if (!gb_sipmd_ipmask_str.equals(GB.sipmd_ipmask_str)) {
            set_f = 1;
        }
        if (!gb_sipmd_gateway_str.equals(GB.sipmd_gateway_str)) {
            set_f = 1;
        }
        if (set_f == 1) {
            Lib.wrInterfaces(GB.sipmd_ip_str, GB.sipmd_ipmask_str, GB.sipmd_gateway_str);
            reset_network();
            System.out.println("\n*** Set network ***\n");
        }
        //===============
        set_f = 0;
        if (!gb_ntp_dns.equals(GB.ntp_dns)) {
            set_f = 1;
        }
        if (set_f == 1) {
            Lib.wNtp();
            System.out.println("\n*** Set NTP ***\n");
        }
        //===============
        set_f = 0;
        if (!gb_phone_name.equals(GB.phone_name)) {
            set_f = 1;
        }
        if (!gb_phone_no.equals(GB.phone_no)) {
            set_f = 1;
        }
        if (!gb_sip_server_ip.equals(GB.sip_server_ip)) {
            set_f = 1;
        }
        if (!gb_sip_server_pin.equals(GB.sip_server_pin)) {
            set_f = 1;
        }
        if (set_f == 1) {
            System.out.println(GB.sip_server_ip);
            pbxRegister();
            System.out.println("\n*** Set PBX ***\n");
        }
        //===============
        set_f = 0;
        if (!gb_sipui_ip_str.equals(GB.sipui_ip_str)) {
            set_f = 1;
        }
        if (set_f == 1) {
            cla.set_local_ip_cnt = 0;
            System.out.println("\n*** Set Local IP ***\n");
        }
        //===============
        set_f = 0;
        if (!gb_switch_ip_str.equals(GB.switch_ip_str)) {
            set_f = 1;
        }
        if (set_f == 1) {
            cla.set_switch_ip_cnt = 0;
            System.out.println("\n*** Set Switch IP ***\n");
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
        if (cla.byeDelayTime > 0) {
            cla.byeDelayTime--;
        }
        if (cla.referTime > 0) {
            cla.referTime--;
        }

        if (cla.hangonWaitTime > 0) {
            cla.hangonWaitTime--;
        }

        if (cla.f4WaitTime > 0) {
            cla.f4WaitTime--;
        }
        cla.vrVolume();

        if (cla.sipData.phoneSta <= 3) {
            if (cla.handStatus > 0) {
                cla.handStatusTime++;
                if (cla.handStatusTime == (50 * 30)) {
                    cla.handStatus = 0;
                    cla.txShellEsc();
                }
            }
        }

        if (cla.shutDown_cnt > 0) {
            cla.shutDown_cnt--;
            if (cla.set_local_ip_cnt > 9 && cla.set_switch_ip_cnt > 9) {
                cla.shutDown_cnt = 0;
            }
            if (cla.shutDown_cnt == 0) {
                Lib.exe("sudo shutdown -r +0");
                return;
            }
        }

        if (cla.sipData.phoneSta > 3 || cla.sipData.connectSta != 0) {
            cla.auto_register_tim = 0;
        }
        if (++cla.auto_register_tim >= GB.auto_register_time) {
            cla.reRegister();
        }

        if (!cla.laterCall.equals("")) {
            if (cla.laterCall_tim != 0) {
                cla.laterCall_tim--;
                if (cla.laterCall_tim == 50) {
                    //cla.hangOnPrg();
                }
                if (cla.laterCall_tim == 0) {
                    if (cla.handStatus_pre == 1) {
                        cla.earPhoneAct();
                    }
                    if (cla.handStatus_pre == 2) {
                        cla.speakerOn();
                    }
                    cla.ForceCallStr(cla.laterCall);

                    cla.callto = cla.laterCall;
                    cla.callfrom = "";
                    //cla.sipData.status = "撥打 < " + cla.callto + " >";
                    cla.sipData.status = "響鈴....";
                    cla.sipData.action = "撥打 " + cla.callto;
                    cla.sipData.connectName = cla.laterCall;
                    cla.sipData.connectNo = cla.laterCall;
                    cla.status_tim = 100;
                    cla.sipData.connectSta = 1;
                    cla.holdRelease_tim = 0;
                    cla.sipData.phoneSta = 5;

                    cla.laterCall = "";

                }
            }

        }

        /*    
        if (cla.sipStatus == 5) {
            if (++cla.holdRelease_tim > (50 * 300)) {
                cla.holdRelease_tim = 0;
                cla.sshWriteSip("hold\n");  //<<debug
                cla.sshWriteSip("retrieve\n");  //<<debug
            }
        }
         */
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

        if (cla.sipData.phoneSta == 5 && cla.sipData.connectSta == 2) {
            if (GB.auto_answer == 1) {
                if (cla.auto_answer_tim == GB.auto_answer_wait) {
                    cla.speakerOn();
                    cla.broadcast_f = 0;
                }
                cla.auto_answer_tim++;
            }
            if (cla.broadcast_f == 1) {
                cla.broadcast_f = 0;
                for (int i = 0; i < GB.ictPhnos_amt; i++) {
                    if (GB.ictPhnos[i].equals(cla.callfrom)) {
                        cla.speakerOn();
                    }
                }
                if (cla.callfrom.equals("104")) {
                    cla.speakerOn();
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
        
        //0:ready, 1: ring out, 2:reing in, 3:connect, 4:hold 
        int lineSta = cla.sipData.lineStaA[cla.sipData.nowLine];

        if (cla.sipData.phoneSta >= 3) {
            if (--cla.status_tim < 0) {
                cla.status_tim = 0;
                if (lineSta < 3) {
                    Date dNow = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
                    cla.sipData.status = ft.format(dNow);
                    cla.sipData.action = cla.selfSipDispName + "<" + cla.selfSipNumber + "> Ready";
                } else {
                    Date dNow = new Date();
                    Date passT = new Date(dNow.getTime() - cla.sipData.lineConnectTimeA[cla.sipData.nowLine] - 3600000 * 8);
                    String nameStr = cla.sipData.lineNameA[cla.sipData.nowLine];
                    String noStr = cla.sipData.lineNoA[cla.sipData.nowLine];
                    if (--cla.status_tim < 0) {
                        cla.status_tim = 0;
                        cla.sipData.status = " 連線到 " + nameStr;
                        cla.sipData.status += " <" + noStr + ">";
                        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
                        cla.sipData.action = ft.format(passT);
                    }

                }
            }

        }
        
        
        /*
        if (cla.sipData.phoneSta == 3) {
            if (--cla.status_tim < 0) {
                cla.status_tim = 0;
                Date dNow = new Date();
                //SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
                SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
                cla.sipData.status = ft.format(dNow);
                cla.sipData.action = "Ready <" + GB.phone_no + ">";
            }

        }
        */
        if (cla.sipData.phoneSta >= 4 && cla.sipData.connectSta == 3) {
            Date dNow = new Date();
            Date passT = new Date(dNow.getTime() - cla.connected_tim - 3600000 * 8);

            String tmpStr = cla.sipData.connectName;

            if (GB.hotline_noA[GB.hotline_inxA[0]].equals(cla.sipData.connectName)) {
                tmpStr = GB.hotline_nameA[GB.hotline_inxA[0]];
            }
            if (GB.hotline_noA[GB.hotline_inxA[1]].equals(cla.sipData.connectName)) {
                tmpStr = GB.hotline_nameA[GB.hotline_inxA[1]];
            }
            if (GB.hotline_noA[GB.hotline_inxA[2]].equals(cla.sipData.connectName)) {
                tmpStr = GB.hotline_nameA[GB.hotline_inxA[2]];
            }
            if (GB.hotline_noA[GB.hotline_inxA[3]].equals(cla.sipData.connectName)) {
                tmpStr = GB.hotline_nameA[GB.hotline_inxA[3]];
            }

            if (cla.sipData.nowLine == 0) {
                if (--cla.status_tim < 0) {
                    cla.status_tim = 0;
                    cla.sipData.status = " 連線到 " + tmpStr;
                    cla.sipData.status += " <" + cla.sipData.connectNo + ">";
                    SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
                    cla.sipData.action = ft.format(passT);
                }
            }

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


class SipData {

    public String sipName = "";
    public String sipNo = "";
    public int mute_f = 0;
    public int dtmfOn_f = 0;
    public int nowLine = 0;
    public int audioStatus = 0;//0:nono 1:handFree 2:hand
    public int phoneSta = 0;  //0:none 1:pi loaded 2:twinkle loaded 3:pbx ready
    public int[] lineFlagA = new int[]{0, 0};   //0:in 1:out;
    public int[] lineStaA = new int[]{0, 0};     //0:ready, 1: ring out, 2:reing in, 3:connect, 4:hold 
    public int[] handStaA = new int[]{0, 0};     //0:ready, 1: earphone, 2:epeaker 
    public int[] handTimeA = new int[]{0, 0};     //0:ready, 1: earphone, 2:epeaker 

    public String[] lineNoA = new String[]{"", ""};
    public String[] lineNameA = new String[]{"", ""};
    public String[] lineMessageA = new String[]{"", ""};
    public long[] lineConnectTimeA = new long[]{0, 0};

    public String status = "";
    public String action = "";
    public String connectNo = "";
    public String connectName = "";
    public int connectSta = 0; //0:noconnect 1:call to 2:call from;3:connected
    public String line2CallNo = "";

    void ready() {
        phoneSta = 3;  //0:none 1:pi ready, 2:twinkle loaded, 3:pbx registed,
        mute_f = 0;
        dtmfOn_f = 0;
        nowLine = 0;
        lineFlagA[0] = 0;
        lineFlagA[1] = 0;
        lineStaA[0] = 0;
        lineStaA[0] = 0;
        lineNoA[0] = "";
        lineNoA[1] = "";
        lineNameA[0] = "";
        lineNameA[1] = "";
        connectNo = "";
        connectName = "";
        connectSta = 0; //0:noconnect 1:call to 2:call from;3:connected
        line2CallNo = "";
    }

}


class SipDataxxx {

    public String sipName = "";
    public String sipNo = "";
    public int mute_f = 0;
    public int dtmfOn_f = 0;
    public int nowLine = 0;
    public int audioStatus = 0;//0:nono 1:handFree 2:hand
    public int phoneSta = 0;  //0:none 1:sip loaded 2:registed,3:call to,call from;
    public int[] lineIoA = new int[]{0, 0};   //0:in 1:out;
    public int[] lineStaA = new int[]{0, 0};     //0:ready:1:ring;2:connect;3:hold 
    public String[] lineNoA = new String[]{"", ""};
    public String[] lineNameA = new String[]{"", ""};
    public String status = "";
    public String action = "";
    public String connectNo = "";
    public String connectName = "";
    public int connectSta = 0; //0:noconnect 1:call to 2:call from;3:connected
    public String line2CallNo = "";

    void ready() {
        phoneSta = 3;  //0:none 1:sip loaded 2:registed,3:ready,4:connect;5:ring
        mute_f = 0;
        dtmfOn_f = 0;
        nowLine = 0;
        lineIoA[0] = 0;
        lineIoA[1] = 0;
        lineStaA[0] = 0;     //0:ready:1:ring;2:connect;3:hold 
        lineStaA[1] = 0;     //0:ready:1:ring;2:connect;3:hold 
        lineNoA[0] = "";
        lineNoA[1] = "";
        lineNameA[0] = "";
        lineNameA[1] = "";
        connectNo = "";
        connectName = "";
        connectSta = 0; //0:noconnect 1:call to 2:call from;3:connected
        line2CallNo = "";

    }

}
