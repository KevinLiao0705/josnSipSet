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
import java.net.InetAddress;
import java.net.UnknownHostException;
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
        try {
            FileWriter fw = new FileWriter(fname);
            //=====================
            bstr = "# USER";
            fw.write(bstr + "\n");
            //=====================
            bstr = "user_name=" + GB.phone_no;
            fw.write(bstr + "\n");
            //=====================
            bstr = "user_domain=" + GB.sip_server_ip;
            fw.write(bstr + "\n");
            //=====================
            bstr = "user_display=" + GB.phone_name;
            fw.write(bstr + "\n");
            //=====================
            bstr = "user_organization=" + "";
            fw.write(bstr + "\n");
            //=====================
            bstr = "auth_realm=" + "";
            fw.write(bstr + "\n");
            //=====================
            bstr = "auth_name=" + GB.phone_no;
            fw.write(bstr + "\n");
            //=====================
            bstr = "auth_pass=" + GB.sip_server_pin;
            fw.write(bstr + "\n");
            //=====================
            bstr = "# SIP SERVER";
            fw.write(bstr + "\n");
            //=====================
            bstr = "outbound_proxy=" + GB.sip_server_ip;
            fw.write(bstr + "\n");
            //=====================
            bstr = "all_requests_to_proxy=" + "no";
            fw.write(bstr + "\n");
            //=====================
            bstr = "registrar=" + GB.sip_server_ip;
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
                return ping(hostname,1000);
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
    
static public int wNtp()
   {
        String fname;
        String bstr;
        fname=GB.ntp_path;
        try
        {
            FileWriter fw = new FileWriter(fname);
            fw.write("[Time]\n");
            fw.write("ntp="+GB.ntp_dns+"\n");
            bstr="FallbackNTP=0.debian.pool.ntp.org 1.debian.pool.ntp.org 2.debian.pool.ntp.org 3.debian.pool.ntp.org";
            fw.write(bstr);
            fw.flush();
            fw.close();
            return 1;
        }
        catch (FileNotFoundException e) 
        {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        } 
        catch (IOException e) 
        {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
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

}
