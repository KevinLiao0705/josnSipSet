package base3;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import static java.lang.Double.isNaN;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhoneCs {
    static PhoneCs scla;
    String title_str = "title_str";
    int fullScr_f = 0;
    int winW = 1600;
    int winH = 800;
    int debug_f = 1;
    Timer tm1 = null;
    SipPhone sipPhone;
    Ssocket sskio;    //from nkv6in1_io
    Ssocket sskweb; //from web
    //===========================
    public PhoneCs() {
        PhoneCs.scla = this;
    }
    protected void finalize() {
        PhoneCs cla = this;
        try {
            super.finalize();
            if (cla.sipPhone.sshSip != null) {
                cla.sipPhone.sshSip.mSession.disconnect();
                cla.sipPhone.sshSip.mChannel.disconnect();
                cla.sipPhone.sshSip = null;
            }
        } catch (Throwable ex) {
            Logger.getLogger(PhoneCs.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void create() {
        //=======================================================
        final PhoneCs cla = this;
        sipPhone = new SipPhone();
        sipPhone.create();
        sipPhone.sipPhoneRx = new SipPhoneRx() {
            @Override
            public void sshRx(String str) {
                System.out.print(str);
            }
        };
        sipPhone.shellRx = new ShellRx() {
            @Override
            public void sshRx(String str) {
                System.out.print(str);
            }
        };
        sipPhone.ngrepRx = new NgrepRx() {
            @Override
            public void sshRx(String str) {
                System.out.print(str);
            }
        };
        
        sipPhone.ictRx = new IctRx() {
            @Override
            public void sshRx(String str) {
                System.out.print(str);
            }
        };
        
        //=======================================================
        
        if (cla.tm1 == null) {
            cla.tm1 = new Timer();
            cla.tm1.schedule(new PhoneCsTm1(cla), 0, 20);
        }

        String str;
        while (true) {
            Scanner input = new Scanner(System.in);
            str = input.nextLine();
            //System.out.println(str);

            if (str.equals("exit")) {
                if (cla.sipPhone.sipStatus >= 2) {
                    str = "quit" + " \n";
                    cla.sipPhone.sshWriteSip(str);
                    Lib.thSleep(1000);
                }
                if (cla.sipPhone.sshSip != null) {
                    cla.sipPhone.sshSip.mSession.disconnect();
                    cla.sipPhone.sshSip.mChannel.disconnect();
                    cla.sipPhone.sshSip = null;
                }
                System.exit(0);
                break;
            }
            if (str.equals("passLinphone")) {
                Base3.scla.netInf(1);
                str += "Enable OK";
            }

            if (str.equals("disLinphone")) {
                Base3.scla.editNewDb("syssec", "");
                str += "Disable OK";
                return;
            }

            if (str.equals("test1")) {
                str = "terminate" + '\n';
                cla.sipPhone.sshWriteSip(str);
                str = "call 301" + '\n';
                cla.sipPhone.sshWriteSip(str);
                continue;
            }

            if (str.equals("test2")) {
                str = "terminate" + '\n';
                cla.sipPhone.sshWriteSip(str);
                str = "call 302" + '\n';
                cla.sipPhone.sshWriteSip(str);
                continue;
            }
            if (str.equals("test3")) {
                Base3.scla.x.act(1);    //read database to para
                Base3.scla.x.act(2);
                continue;
            }
            str += "\n";
            cla.sipPhone.sshWriteSip(str);
        }

    }


    void cor_act(int lh, int period) {
        int sta;
        sta = lh;
        if (!GB.cor_invert.equals(0)) {
            sta = lh ^ 1;
        }
        if (GB.cor_start_f == 0) {
            if (sta == 0) {
                return;
            }
            if (period >= Lib.str2int(GB.first_phase_tim, 0)) {
                GB.cor_start_f = 1;
                GB.corcnt = 0;
            }
            return;
        }
        if (sta == 0) {
            if (period > Lib.str2int(GB.on_max_tim, 0)) {
                GB.cor_start_f = 0;
            }
            if (period < Lib.str2int(GB.on_min_tim, 0)) {
                GB.cor_start_f = 0;
            }
        } else {
            if (period > Lib.str2int(GB.off_max_tim, 0)) {
                GB.cor_start_f = 0;
            }
            if (period < Lib.str2int(GB.off_min_tim, 0)) {
                GB.cor_start_f = 0;
            }
        }
        if (GB.cor_start_f == 0) {
            GB.corcnt = 0;
            return;
        }
        GB.corcnt++;
        GB.cor_endtim = 0;
    }

}

//20ms
class PhoneCsTm1 extends TimerTask {

    String str;
    PhoneCs cla;
    static int tm1_cnt = 0;

    PhoneCsTm1(PhoneCs owner) {
        cla = owner;
    }

    @Override
    public void run() {
        String str;
        int ibuf;
        switch (GB.action_inx) {
            case 0:
                break;
            case 1:         //no answer time out
                switch (GB.action_step) {
                    case 0:
                        ibuf = GB.noanswer_timeout;
                        if (ibuf == 0) {
                            GB.action_inx = 0;
                            break;
                        }
                        ibuf *= 50;
                        if (++GB.action_tim < ibuf) {
                            break;
                        }
                        str = "terminate" + '\n';
                        cla.sipPhone.sshWriteSip(str);
                        GB.action_inx = 0;
                        break;
                }
                break;

        }
        //===================================================
        GB.cor_endtim++;
        if (GB.cor_start_f == 0) {
            return;
        }
        if (GB.cor_endtim == Lib.str2int(GB.end_phase_tim,0)) {
            if (GB.corcnt == Lib.str2int(GB.act1_phase_no,0)) {
                str = "Act1 Action";
                System.out.println(str);
                GB.cor_start_f = 0;
                str = "call " + GB.act1_call + '\n';
                cla.sipPhone.sshWriteSip(str);
                return;
            }
            if (GB.corcnt == Lib.str2int(GB.act2_phase_no,0)) {
                str = "Act2 Action";
                System.out.println(str);
                GB.cor_start_f = 0;
                str = "call " + GB.act2_call + '\n';
                cla.sipPhone.sshWriteSip(str);
                return;
            }
            if (GB.corcnt == Lib.str2int(GB.cut_phase_no,0)) {
                str = "Cut Action";
                System.out.println(str);
                GB.cor_start_f = 0;
                str = "terminate" + '\n';
                cla.sipPhone.sshWriteSip(str);
                return;
            }
        }
        //==============================================
    }
}
