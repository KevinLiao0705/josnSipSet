package base3;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lib {

    static String retstr;

    static int search(String str, String st, String end) {
        int sti, endi;
        sti = str.indexOf(st);
        if (sti < 0) {
            return -1;
        }
        endi = str.indexOf(end, sti + st.length());
        if (endi < 0) {
            return -1;
        }
        retstr = str.substring(sti + st.length(), endi);
        return 1;
    }

    static int searchEnd(String str, String st, String end) {
        int sti, endi;
        sti = str.indexOf(st);
        if (sti < 0) {
            return -1;
        }
        endi = str.indexOf(end, sti + st.length());
        if (endi < 0) {
            endi = str.length();
        }
        retstr = str.substring(sti + st.length(), endi);
        return 1;
    }
    
    static String readFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return new String(bytes, 0, bytes.length, "UTF-8");
    }
    

    static int chkStrInList(String str,ArrayList<String>strA){
        if(strA==null)
            return 0;
        for(int i=0;i<strA.size();i++){
            String strb=strA.get(i);
            int inx = strb.indexOf(str);
            if(inx>=0)
                return 1;
        }
        return 0;
    }
    
    static int fsearchEnd(String fileName, String st, String end) {
        File f = new File(fileName);
        if (!f.exists()) {
            return -1;
        }
        if (f.isDirectory()) {
            return -1;
        }
        FileReader fr;
        BufferedReader br;
        String[] fields;
        String tmp;
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            while ((tmp = br.readLine()) != null) {
                if (searchEnd(tmp, st, end) == 1) {
                    fr.close();
                    br.close();
                    return 1;
                }
            }
            fr.close();
            br.close();
            return 0;
        } catch (FileNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return -1;
    }

    static int setTwincleCfg() {

        String fname;
        String bstr;
        fname = GB.twinkleCfg_path;
        
        String sipName=GB.paraSetMap.get("sipName").toString();
        String sipNo=GB.paraSetMap.get("sipNumber").toString();
        String sipServerIp=GB.paraSetMap.get("sipServerAddress").toString();
        String sipServerPin=GB.paraSetMap.get("sipServerPassword").toString();

        
        System.out.println("setTwincleCfg");
        System.out.println("===============================");
        System.out.println(sipNo);
        System.out.println(sipServerIp);
        System.out.println(sipName);
        System.out.println("===============================");
        
        try {
            FileWriter fw = new FileWriter(fname);
            //=====================
            bstr = "# USER";
            fw.write(bstr + "\n");
            //=====================
            bstr = "user_name=" + sipNo;
            fw.write(bstr + "\n");
            //=====================
            bstr = "user_domain=" + sipServerIp;
            fw.write(bstr + "\n");
            //=====================
            bstr = "user_display=" + sipName;
            fw.write(bstr + "\n");
            //=====================
            bstr = "user_organization=" + "";
            fw.write(bstr + "\n");
            //=====================
            bstr = "auth_realm=" + "";
            fw.write(bstr + "\n");
            //=====================
            bstr = "auth_name=" + sipNo;
            fw.write(bstr + "\n");
            //=====================
            bstr = "auth_pass=" + sipServerPin;
            fw.write(bstr + "\n");
            //=====================
            bstr = "# SIP SERVER";
            fw.write(bstr + "\n");
            //=====================
            bstr = "outbound_proxy=" + sipServerIp;
            fw.write(bstr + "\n");
            //=====================
            bstr = "all_requests_to_proxy=" + "no";
            fw.write(bstr + "\n");
            //=====================
            bstr = "registrar=" + sipServerIp;
            fw.write(bstr + "\n");
            //=====================
            bstr = "register_at_startup=" + "yes";
            fw.write(bstr + "\n");
            //=====================
            bstr = "registration_time=" + "3600";
            fw.write(bstr + "\n");
            //=====================
            bstr = "# RTP AUDIO";
            fw.write(bstr + "\n");
            //=====================
            bstr = "codecs=" + "g711a,g711u,gsm";
            fw.write(bstr + "\n");
            //=====================
            bstr = "ptime=" + "20";
            fw.write(bstr + "\n");
            //=====================
            bstr = "dtmf_payload_type=" + "101";
            fw.write(bstr + "\n");
            //=====================
            bstr = "dtmf_duration=" + "100";
            fw.write(bstr + "\n");
            //=====================
            bstr = "dtmf_pause=" + "40";
            fw.write(bstr + "\n");
            //=====================
            bstr = "dtmf_volume=" + "10";
            fw.write(bstr + "\n");
            //=====================
            bstr = "# SIP PROTOCOL";
            fw.write(bstr + "\n");
            //=====================
            bstr = "hold_variant=" + "rfc3264";
            fw.write(bstr + "\n");
            //=====================
            bstr = "check_max_forwards=" + "no";
            fw.write(bstr + "\n");
            //=====================
            bstr = "allow_missing_contact_reg=" + "yes";
            fw.write(bstr + "\n");
            //=====================
            bstr = "registration_time_in_contact=" + "yes";
            fw.write(bstr + "\n");
            //=====================
            bstr = "compact_headers=" + "no";
            fw.write(bstr + "\n");
            //=====================
            bstr = "use_domain_in_contact=" + "yes";
            fw.write(bstr + "\n");
            //=====================
            bstr = "allow_redirection=" + "no";
            fw.write(bstr + "\n");
            //=====================
            bstr = "ask_user_to_redirect=" + "yes";
            fw.write(bstr + "\n");
            //=====================
            bstr = "max_redirections=" + "5";
            fw.write(bstr + "\n");
            //=====================
            bstr = "ext_100rel=" + "supported";
            fw.write(bstr + "\n");
            //=====================
            bstr = "referee_hold=" + "no";
            fw.write(bstr + "\n");
            //=====================
            bstr = "referrer_hold=" + "yes";
            fw.write(bstr + "\n");
            //=====================
            bstr = "allow_refer=" + "yes";
            fw.write(bstr + "\n");
            //=====================
            bstr = "ask_user_to_refer=" + "yes";
            fw.write(bstr + "\n");
            //=====================
            bstr = "auto_refresh_refer_sub=" + "no";
            fw.write(bstr + "\n");
            //=====================
            bstr = "# NAT";
            fw.write(bstr + "\n");
            //=====================
            bstr = "nat_public_ip=" + "";
            fw.write(bstr + "\n");
            //=====================
            bstr = "#stun_server=**sip.foo.bar**:10000";
            fw.write(bstr + "\n");
            //=====================
            bstr = "# TIMERS";
            fw.write(bstr + "\n");
            //=====================
            bstr = "timer_noanswer=" + "30";
            fw.write(bstr + "\n");
            //=====================
            bstr = "timer_nat_keepalive=" + "30";
            fw.write(bstr + "\n");
            //=====================
            bstr = "# ADDRESS FORMAT";
            fw.write(bstr + "\n");
            //=====================
            bstr = "display_useronly_phone=" + "yes";
            fw.write(bstr + "\n");
            //=====================
            bstr = "numerical_user_is_phone=" + "no";
            fw.write(bstr + "\n");
            //=====================
            bstr = "# RING TONES";
            fw.write(bstr + "\n");
            //=====================
            bstr = "ringtone_file=" + "";
            fw.write(bstr + "\n");
            //=====================
            bstr = "ringback_file=" + "";
            fw.write(bstr + "\n");
            //=====================
            bstr = "# SCRIPTS";
            fw.write(bstr + "\n");
            //=====================
            bstr = "script_incoming_call=" + "";
            fw.write(bstr + "\n");
            //=====================
            //bstr="="+"";
            //fw.write(bstr+"\n");
            //=====================

            fw.flush();
            fw.close();
            return 1;
        } catch (FileNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 0;

    }

    static int wrInterfaces(String ip, String mask, String gateway) {
        String fname;
        String bstr;
        fname = GB.interfaces_path;
        int debug_i = 0;
        if (debug_i == 1) {
            return 0;
        }

        try {
            FileWriter fw = new FileWriter(fname);
            fw.write("auto lo\n");
            fw.write("iface lo inet loopback\n");
            fw.write("\n");
            fw.write("auto eth0\n");
            //if(GB.ip_type==1)
            //  fw.write("iface eth0 inet dhcp\n");
            //else
            fw.write("iface eth0 inet static\n");

            bstr = "address " + ip + "\n";
            fw.write(bstr);
            bstr = "netmask " + mask + "\n";
            fw.write(bstr);
            bstr = "gateway " + gateway + "\n";
            fw.write(bstr);
            fw.flush();
            fw.close();
            return 1;
        } catch (FileNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 0;
    }


    static int wrNtp(String ip) {
        String fname;
        String bstr;
        fname = GB.ntpConfPathName;
        System.out.println("ntpIp "+ip+ " ==>"+fname);

        try {
            FileWriter fw = new FileWriter(fname);
            fw.write("[Time]\n");
            fw.write("NTP="+ip+"\n");
            fw.flush();
            fw.close();
            return 1;
        } catch (FileNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 0;
    }
    
    
    static int writeFileLines(String fileName, ArrayList<String> lines) {
        String bstr;
        try {
            FileWriter fw = new FileWriter(fileName);
            for(int i=0;i<lines.size();i++){
                fw.write(lines.get(i)+"\n");
            }
            fw.flush();
            fw.close();
            return 1;
        } catch (FileNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 0;
    }

    
    
    static ArrayList<String> readFileLines(String fileName) {
        ArrayList<String> strA=new ArrayList<String>();
        File f = new File(fileName);
        if (f.exists() && !f.isDirectory()) {
            FileReader fr;
            BufferedReader br;
            String[] fields;
            String tmp;
            String str;

            try {
                fr = new FileReader(fileName);
                br = new BufferedReader(fr);
                while ((tmp = br.readLine()) != null) {
                    strA.add(tmp);
                }
                fr.close();
                br.close();
                return strA;
            } catch (FileNotFoundException ex) {
                return null;
            } catch (IOException ex) {
                return null;
            }

        } else {
            return null;
        }

    }
    
    
    
    static String rdInterfaces(String cmpstr) {
        String fnameInterfaces = GB.interfaces_path;
        File f = new File(fnameInterfaces);
        if (f.exists() && !f.isDirectory()) {
            FileReader fr;
            BufferedReader br;
            String[] fields;
            String tmp;
            String str;

            try {
                fr = new FileReader(fnameInterfaces);
                br = new BufferedReader(fr);
                while ((tmp = br.readLine()) != null) {
                    if (tmp.contains(cmpstr)) {
                        str = tmp.trim();
                        fields = str.split("[ ]+");
                        return fields[1];
                    }
                }
                fr.close();
                br.close();
                return null;
            } catch (FileNotFoundException ex) {
            } catch (IOException ex) {
            }

        } else {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException ex) {
            }
        }
        return null;

    }

    static public int ping(String ip, int wait_tim) {
        int i = 0;
        try {
            if (InetAddress.getByName(ip).isReachable(wait_tim)) {
                return 0;
            } else {
                return -1;
            }
        } catch (UnknownHostException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return -1;
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return -1;
        }
    }

    //Ok return 0:
    //else return 1;
    public static final int ping(String hostname) {
        try {
            if (GB.os_inx == 0) //n=tx count w=wait time
            {
                //return Runtime.getRuntime().exec("ping -n 1 -w 1000 " + hostname).waitFor();  //windows
                return ping(hostname, 1000);
            }
            if (GB.os_inx == 1) {
                return Runtime.getRuntime().exec("ping -c 1 " + hostname).waitFor();  //linux
            }
            return 1;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void thSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
        }
    }

    public static void exe(String exestr) {
        try {
            Process process = Runtime.getRuntime().exec(exestr);
        } catch (IOException ex) {
            Logger.getLogger(Lib.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static boolean chkStr2int(String str) {
        try {
            int ibuf = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean chkStrIsIp(String _str) {
        try {
            String str;
            str = _str.trim();
            String[] strA = str.split("\\.");
            if (strA.length != 4) {
                return false;
            }
            for (int i = 0; i < 4; i++) {
                if (Lib.str2int(strA[i], -1, 255, 0) == -1) {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int str2int(String str) {
        return Integer.parseInt(str);
    }

    public static int str2int(String str, int default_i) {
        try {
            int ibuf = Integer.parseInt(str);
            return ibuf;
        } catch (NumberFormatException e) {
            return default_i;
        }
    }

    public static int str2int(String str, int default_i, int max, int min) {
        try {

            int ibuf = Integer.parseInt(str);
            if (ibuf > max) {
                return default_i;
            }
            if (ibuf < min) {
                return default_i;
            }
            return ibuf;
        } catch (NumberFormatException e) {
            return default_i;
        }
    }

    static int getOs() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            return 0;
        }
        if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            return 1;
        }
        if (OS.contains("mac")) {
            return 2;
        }
        if (OS.contains("sunos")) {
            return 3;
        }
        return -1;

    }

    static public int wNtp() {
        String fname;
        String bstr;
        fname = GB.ntp_path;
        try {
            String ntpServerAddress=GB.paraSetMap.get("ntpServerAddress").toString();
            FileWriter fw = new FileWriter(fname);
            fw.write("[Time]\n");
            fw.write("NTP=" + ntpServerAddress + "\n");
            bstr = "FallbackNTP=0.debian.pool.ntp.org 1.debian.pool.ntp.org 2.debian.pool.ntp.org 3.debian.pool.ntp.org";
            fw.write(bstr);
            fw.flush();
            fw.close();
            return 1;
        } catch (FileNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 0;
    }

    static void moveMouse(Point p) {
        GraphicsEnvironment ge
                = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        // Search the devices for the one that draws the specified point.
        for (GraphicsDevice device : gs) {
            GraphicsConfiguration[] configurations
                    = device.getConfigurations();
            for (GraphicsConfiguration config : configurations) {
                Rectangle bounds = config.getBounds();
                if (bounds.contains(p)) {
                    // Set point to screen coordinates.
                    Point b = bounds.getLocation();
                    Point s = new Point(p.x - b.x, p.y - b.y);

                    try {
                        Robot r = new Robot(device);
                        r.mouseMove(s.x, s.y);
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }

                    return;
                }
            }
        }
        // Couldn't move to the point, it may be off screen.
        return;
    }

    static void dechop(byte[] hop, byte[] enckey) {
        int i, j, ibuf, ibuf1;
        for (i = 0; i < 11; i++) {
            for (j = 0; j < 48; j++) {
                ibuf = 1;
                if ((hop[3] & 0x08) != 0) {
                    ibuf = 0x10;
                }
                if ((hop[2] & 0x01) != 0) {
                    ibuf <<= 2;
                }

                if ((hop[1] & 0x01) != 0) {
                    ibuf <<= 1;
                }
                if ((hop[4] & 0x40) != 0) {
                    ibuf1 = 0x5c;
                    if ((hop[4] & 0x02) != 0) {
                        ibuf1 = 0x3a;
                    }
                } else {
                    ibuf1 = 0x2e;
                    if ((hop[4] & 0x02) != 0) {
                        ibuf1 = 0x74;
                    }
                }
                ibuf = ibuf & ibuf1;
                if (ibuf != 0) {
                    ibuf = 0x80;
                }
                ibuf ^= hop[2];
                ibuf ^= hop[4];
                ibuf ^= enckey[1];
                ibuf = ibuf << 1;
                hop[1] = (byte) (hop[1] << 1);
                hop[2] = (byte) (hop[2] << 1);
                hop[3] = (byte) (hop[3] << 1);
                hop[4] = (byte) (hop[4] << 1);
                if ((ibuf & 0x100) != 0) {
                    hop[1]++;
                }
                if ((hop[1] & 0x100) != 0) {
                    hop[2]++;
                }
                if ((hop[2] & 0x100) != 0) {
                    hop[3]++;
                }
                if ((hop[3] & 0x100) != 0) {
                    hop[4]++;
                }
                enckey[0] <<= 1;
                enckey[1] <<= 1;
                enckey[2] <<= 1;
                enckey[3] <<= 1;
                enckey[4] <<= 1;
                enckey[5] <<= 1;
                enckey[6] <<= 1;
                enckey[7] <<= 1;
                if ((enckey[7] & 0x100) != 0) {
                    enckey[0]++;
                }
                if ((enckey[0] & 0x100) != 0) {
                    enckey[1]++;
                }
                if ((enckey[1] & 0x100) != 0) {
                    enckey[2]++;
                }
                if ((enckey[2] & 0x100) != 0) {
                    enckey[3]++;
                }
                if ((enckey[3] & 0x100) != 0) {
                    enckey[4]++;
                }
                if ((enckey[4] & 0x100) != 0) {
                    enckey[5]++;
                }
                if ((enckey[5] & 0x100) != 0) {
                    enckey[6]++;
                }
                if ((enckey[6] & 0x100) != 0) {
                    enckey[7]++;
                }
            }
        }
    }

}


class KvJson {

    String jstr = "";
    int keyCnt = 0;

    KvJson() {

    }

    void jStart() {
        keyCnt = 0;
        jstr = "{";
    }

    String jEnd() {
        keyCnt = 0;
        jstr += "}";
        return jstr;
    }

    void jadd(String key, int ii) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += ii;
        keyCnt++;
    }

    void jadd(String key, long ii) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += ii;
        keyCnt++;
    }

    void jadd(String key, short ii) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += ii;
        keyCnt++;
    }

    void jadd(String key, byte ii) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += ii;
        keyCnt++;
    }

    void jadd(String key, float ff) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += ff;
        keyCnt++;
    }

    void jadd(String key, String ss) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += "\""+ss+"\"";
        keyCnt++;
    }

    void jadd(String key, long[] ia) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += "[";
        if (ia != null) {
            for (int i = 0; i < ia.length; i++) {
                if (i != 0) {
                    jstr += ",";
                }
                jstr += ia[i];
            }
        }
        jstr += "]";
        keyCnt++;
    }

    void jadd(String key, int[] ia) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += "[";
        if (ia != null) {
            for (int i = 0; i < ia.length; i++) {
                if (i != 0) {
                    jstr += ",";
                }
                jstr += ia[i];
            }
        }
        jstr += "]";
        keyCnt++;
    }

    void jadd(String key, int[] ia, int len) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += "[";
        int iaLen = len;
        if (ia != null) {
            for (int i = 0; i < iaLen; i++) {
                if (i != 0) {
                    jstr += ",";
                }
                jstr += ia[i];
            }
        }
        jstr += "]";
        keyCnt++;
    }

    void jadd(String key, short[] ia) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += "[";
        for (int i = 0; i < ia.length; i++) {
            if (i != 0) {
                jstr += ",";
            }
            jstr += ia[i];
        }
        jstr += "]";
        keyCnt++;
    }

    void jadd(String key, byte[] ia) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += "[";
        for (int i = 0; i < ia.length; i++) {
            if (i != 0) {
                jstr += ",";
            }
            jstr += ia[i];
        }
        jstr += "]";
        keyCnt++;
    }

    void jadd(String key, float[] fa) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += "[";
        for (int i = 0; i < fa.length; i++) {
            if (i != 0) {
                jstr += ",";
            }
            jstr += fa[i];
        }
        jstr += "]";
        keyCnt++;
    }

    void jadd(String key, String[] sa) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += "[";
        for (int i = 0; i < sa.length; i++) {
            if (i != 0) {
                jstr += ",";
            }
            jstr += "\""+sa[i]+"\"";
        }
        jstr += "]";
        keyCnt++;
    }

    void jadd(String key, int[][] iaa) {
        if (keyCnt != 0) {
            jstr += ",";
        }
        jstr += "\"" + key + "\":";
        jstr += "[";
        for (int i = 0; i < iaa.length; i++) {
            if (i != 0) {
                jstr += ",";
            }
            jstr += "[";
            for (int j = 0; j < iaa[i].length; j++) {
                if (j != 0) {
                    jstr += ",";
                }
                jstr += iaa[i][j];
            }
            jstr += "]";
        }
        jstr += "]";
        keyCnt++;
    }

    static String objToJson(Object inst) {
        String ss;
        byte bb;
        int ii;
        long ll;
        float ff;
        double dd;
        String[] sa;
        byte[] ba;
        int[] ia;
        long[] la;
        float[] fa;
        double[] da;
        String jstr;
        try {
            Class aClassHandle = inst.getClass();
            Field[] fields = aClassHandle.getDeclaredFields();
            String jsonStr = "{";
            for (int i = 0; i < fields.length; i++) {
                Object value = fields[i].get(inst);
                if (value == null) {
                    continue;
                }
                String keyName = "";
                if (i != 0) {
                    keyName += ",";
                }
                keyName += "\"" + fields[i].getName() + "\": ";
                if (value instanceof String) {
                    ss = (String) value;
                    jsonStr += keyName + "\"" + ss.replace("\n", "\\n") + "\"";
                } else if (value instanceof Byte) {
                    bb = (Byte) value;
                    jsonStr += keyName + bb;
                } else if (value instanceof Integer) {
                    ii = (Integer) value;
                    jsonStr += keyName + ii;
                } else if (value instanceof Long) {
                    ll = (Long) value;
                    jsonStr += keyName + ll;
                } else if (value instanceof Float) {
                    ff = (Float) value;
                    jsonStr += keyName + ff;
                } else if (value instanceof Double) {
                    dd = (Double) value;
                    jsonStr += keyName + dd;
                } else if (value instanceof String[]) {
                    sa = (String[]) value;
                    jstr = "[";
                    for (int j = 0; j < sa.length; j++) {
                        if (j != 0) {
                            jstr += ",";
                        }
                        jstr += "\"" + sa[j].replace("\n", "\\n") + "\"";
                    }
                    jstr += "]";
                    jsonStr += keyName + jstr;
                } else if (value instanceof byte[]) {
                    ba = (byte[]) value;
                    jsonStr += keyName + Arrays.toString(ba);
                } else if (value instanceof int[]) {
                    ia = (int[]) value;
                    jsonStr += keyName + Arrays.toString(ia);
                } else if (value instanceof long[]) {
                    la = (long[]) value;
                    jsonStr += keyName + Arrays.toString(la);
                } else if (value instanceof float[]) {
                    fa = (float[]) value;
                    jsonStr += keyName + Arrays.toString(fa);
                } else if (value instanceof double[]) {
                    da = (double[]) value;
                    jsonStr += keyName + Arrays.toString(da);
                } else {
                    Class vClassHandle = value.getClass();
                    String classHandleName = vClassHandle.getName();
                    if (classHandleName.contains("HashMap")) {
                        HashMap<String, Object> map = (HashMap<String, Object>) value;
                        jsonStr += keyName + "{";
                        int kinx = 0;
                        for (String key : map.keySet()) {
                            if (kinx != 0) {
                                jsonStr += ",";
                            }
                            kinx++;
                            jsonStr += "\"" + key + "\": ";
                            String tstr = KvJson.objToJson(map.get(key));
                            jsonStr += tstr;
                        }
                        String testStr = value.toString();
                        jsonStr += "}";

                    }
                    if (classHandleName.contains("kevin")) {
                        if (classHandleName.contains("[L")) {
                            Object[] objA = (Object[]) value;
                            jsonStr += keyName + "[";
                            for (int j = 0; j < objA.length; j++) {
                                if (j != 0) {
                                    jsonStr += ",";
                                }
                                String tstr = KvJson.objToJson(objA[j]);
                                jsonStr += tstr;
                            }
                            jsonStr += "]";

                        } else {
                            String subJsonStr = KvJson.objToJson(value);
                            jsonStr += keyName + subJsonStr;
                        }
                    }
                }
            }
            jsonStr += "}";
            return jsonStr;
        } catch (Exception ex) {
            Logger.getLogger(KvJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}


class ConnectCla {

    String name;
    int time = 0;
    int timeTh = 0;

    public ConnectCla(String _name, int _timeTh) {
        time = 0;
        name = _name;
        timeTh = _timeTh;
    }
}
