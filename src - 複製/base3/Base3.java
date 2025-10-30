/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base3;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Base3 {

    static Base3 scla;//= new Base3();

    int debug_f = 0;
    int ret_f = 0;
    int ret_i;
    //===========================
    Action x;

    public Base3() {
        Base3 cla = this;
        GB.initGB();
        x = new Action(this);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int i;
        //=======================================================
        GB.os_inx = Lib.getOs();
        Base3 cla = new Base3();
        scla = cla;
        cla.x.act(0);//read setdata.xml to para[] 
        cla.x.act(1);//read database to para[] 
        cla.x.act(2);//dispatch para[] to global various
        cla.netInf(0);
        if (cla.debug_f != 1) {

            //Lib.exe("./io.sh");
            if (GB.process_inx == 0) {
                GB.sipmd_ip_str = GB.real_ip_str;
                GB.sipmd_ipmask_str = GB.real_ipmask_str;
                GB.sipmd_gateway_str = GB.real_gateway_str;
                PhoneCs phcs1 = new PhoneCs();
                phcs1.create();

            }

            if (GB.process_inx == 1) {
                GB.sipmd_ip_str = GB.real_ip_str;
                GB.sipmd_ipmask_str = GB.real_ipmask_str;
                GB.sipmd_gateway_str = GB.real_gateway_str;
                PhoneDt phDt1 = new PhoneDt(null, true);
                phDt1.title_str = "國家中山科學研究院";
                phDt1.create();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Base3.class.getName()).log(Level.SEVERE, null, ex);
                }
                phDt1.setVisible(true);
                System.exit(0);
            }

            if (GB.process_inx == 2) {

                GB.sipui_ip_str = GB.real_ip_str;
                GB.sipui_ipmask_str = GB.real_ipmask_str;
                GB.sipui_gateway_str = GB.real_gateway_str;
                PhoneUi phUi1 = new PhoneUi(null, true);
                phUi1.title_str = "國家中山科學研究院";
                phUi1.create();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Base3.class.getName()).log(Level.SEVERE, null, ex);
                }
                phUi1.setVisible(true);
                System.exit(0);
            }

            if (GB.process_inx == 3) {

                GB.sipui_ip_str = GB.real_ip_str;
                GB.sipui_ipmask_str = GB.real_ipmask_str;
                GB.sipui_gateway_str = GB.real_gateway_str;
                Phone6in1 ph6in1 = new Phone6in1(null, true);
                ph6in1.title_str = "國家中山科學研究院";
                ph6in1.create();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Base3.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("ph6in1.setVisible");


                ph6in1.setVisible(true);
                System.out.println("ph6in1.setVisible End");
                System.exit(0);
            }
            System.exit(0);

        }
    }

    void readDatabase() {
        Connection c = null;
        String dbPath = GB.setdata_db;
        try {
            File f = new File(dbPath);
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            //==============================================
            java.sql.Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM paraTable;");
            String paraName = "", paraValue = "";
            while (rs.next()) {
                paraName = rs.getString("paraName");
                paraValue = rs.getString("paraValue");
                //System.out.println( ">" + paraName+"  "+paraValue);
                //ta1.append( ">" + paraName+"  "+paraValue+"\n");
                GB.editNewPara(paraName, paraValue);

            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /*
    void wdateDb(String paraName, String paraValue) {
        Connection c = null;
        String dbPath = GB.setdata_db;
        String sql;
        try {
            File f = new File(dbPath);
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            java.sql.Statement stmt = c.createStatement();
            sql = "UPDATE paraTable set paraValue = \"";
            sql = sql + paraValue;
            sql = sql + "\" where paraName=\"";
            sql = sql + paraName;
            sql = sql + "\";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
     */
    public boolean checkDb(String paraName) {
        String dbPath = GB.setdata_db;
        Connection con = null;
        String pName;
        String pValue;
        String sbuf;
        boolean ret = false;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            con.setAutoCommit(false);
            //==============================================
            java.sql.Statement stmt = con.createStatement();
            sbuf = "SELECT * FROM paraTable;";
            ResultSet rs = stmt.executeQuery(sbuf);
            while (rs.next()) {
                pName = rs.getString("paraName");
                if (pName.equals(paraName)) {
                    ret = true;
                    break;
                }
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ret;
    }

    public int editNewDb(String paraName, String paraValue) {
        int line;
        line = editDb(paraName, paraValue);
        if (line > 0) {
            return line;
        }
        line = insertDb(paraName, paraValue);
        return line;
    }

    public int editDb(String paraName, String paraValue) {
        String dbPath = GB.setdata_db;
        Connection con;
        String sql;
        int chgLine = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            con.setAutoCommit(false);
            java.sql.Statement stmt = con.createStatement();
            //UPDATE paraTable set 
            sql = "UPDATE paraTable set paraValue = \"";
            sql = sql + paraValue;
            sql = sql + "\" where paraName=\"";
            sql = sql + paraName;
            sql = sql + "\";";
            chgLine = stmt.executeUpdate(sql);
            con.commit();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return chgLine;
    }

    //statement.executeUpdate("INSERT INTO Customers " + "VALUES (1001, 'Simpson', 'Mr.', 'Springfield', 2001)");
    public int insertDb(String paraName, String paraValue) {
        String dbPath = GB.setdata_db;
        Connection con = null;
        String sql;
        int chgLine = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            con.setAutoCommit(false);
            java.sql.Statement stmt = con.createStatement();
            sql = "INSERT INTO paraTable VALUES ('";
            sql += paraName;
            sql += "','";
            sql += paraValue;
            sql += "');";
            chgLine = stmt.executeUpdate(sql);
            con.commit();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return chgLine;
    }

    void netInf(int ww) {
        InetAddress ip;
        int i;
        int sti;
        String str = null;
        String localIp = null;
        GB.real_ip_str = Lib.rdInterfaces("address");
        GB.real_ipmask_str = Lib.rdInterfaces("netmask");
        GB.real_gateway_str = Lib.rdInterfaces("gateway");

        System.out.println("IP address : " + GB.real_ip_str);
        System.out.println("IP mask: " + GB.real_ipmask_str);
        System.out.println("Gateway address: " + GB.real_gateway_str);

        try {

            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress ia = (InetAddress) ee.nextElement();
                    str = ia.getHostAddress();
                    System.out.println(str);
                    sti = str.indexOf("192.168.");
                    if (sti >= 0) {
                        localIp = str;
                    }
                }
            }

            if (localIp == null) {
                return;
            }
            //ip = InetAddress.getLocalHost();
            //ip = InetAddress.getByName("192.168.0.57");
            ip = InetAddress.getByName(localIp);
            System.out.println("Current IP address : " + ip.getHostAddress());
            GB.real_ip_str = ip.getHostAddress();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            //if(network==null)
            //    return;
            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            StringBuilder sb = new StringBuilder();
            for (i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
            str = "";
            if (mac.length < 6) {
                GB.syssec_f = 1;
            } else {
                mac[0] ^= 0xab;
                mac[1] ^= 0x12;
                mac[2] ^= 0x83;
                mac[3] ^= 0x29;
                mac[4] ^= 0x1b;
                mac[5] ^= 0x40;
                mac[0] ^= GB.syssec_xor;
                mac[1] ^= GB.syssec_xor;
                mac[2] ^= GB.syssec_xor;
                mac[3] ^= GB.syssec_xor;
                mac[4] ^= GB.syssec_xor;
                mac[5] ^= GB.syssec_xor;
                str += Integer.toString(mac[3]);
                str += Integer.toString(mac[2]);
                str += Integer.toString(mac[5]);
                str += Integer.toString(mac[0]);
                str += Integer.toString(mac[1]);
                str += Integer.toString(mac[4]);
            }
            if (GB.syssec.equals(str)) {
                GB.syssec_f = 1;
            } else {
                if (ww != 0) {
                    editNewDb("syssec", str);

                }
            }

        } catch (UnknownHostException | SocketException e) {
        }

    }

}

//act 0: read para from setdata.xml
//act 1: read para frm database
//act 2: trans para to global various 
class Action {

    Base3 cla;

    Action(Base3 owner) {
        cla = owner;
    }

    public void act(int index) {
        String str;
        switch (index) {
            case 0: //read setdata.xml to GB.paraName[],GB.paraValue[],  
                System.out.println("Action 0");
                try {
                    FileReader reader = new FileReader(GB.setdata_xml);
                    BufferedReader br = new BufferedReader(reader);
                    String line;
                    String paraN;
                    String paraV;
                    GB.clrPara();
                    while ((line = br.readLine()) != null) {
                        if (Lib.search(line, "[", "]") == 1) {
                            paraN = Lib.retstr;
                            if (Lib.search(line, "<", ">") == 1) {
                                paraV = Lib.retstr;
                                GB.newPara(paraN, paraV);
                            }
                        }
                    }
                } catch (IOException e2) {
                    System.out.println(e2);
                }
                break;
            case 1:
                System.out.println("Action 1");
                cla.readDatabase();
                break;
            case 2:
                System.out.println("Action 2");
                for (int i = 0; i < GB.paraLen; i++) {
                    //str = GB.paraName[i] + " -----> " + GB.paraValue[i] + "\n";
                    switch (GB.paraName[i]) {
                        //============================================    
                        case "process_inx":
                            GB.process_inx = Lib.str2int(GB.paraValue[i], 0);
                            break;
                        case "interfaces_path":
                            GB.interfaces_path = GB.paraValue[i];
                            break;
                        case "syssec":
                            GB.syssec = GB.paraValue[i];
                            break;
                        //============================================    
                        case "sipmd_linph_user_name":
                            GB.sipmd_linph_user_name = GB.paraValue[i];
                            break;
                        case "sipmd_linph_password":
                            GB.sipmd_linph_user_name = GB.paraValue[i];
                            break;
                        case "sipmd_ctr_user_name":
                            GB.sipmd_ctr_user_name = GB.paraValue[i];
                            break;
                        case "sipmd_ctr_password":
                            GB.sipmd_ctr_password = GB.paraValue[i];
                            break;
                        //============================================    
                        case "sipmd_iptype":
                            GB.sipmd_iptype = GB.paraValue[i];
                            break;
                        case "sipmd_ip_str":
                            GB.sipmd_ip_str = GB.paraValue[i];
                            break;
                        case "sipmd_ipmask_str":
                            GB.sipmd_ipmask_str = GB.paraValue[i];
                            break;
                        case "sipmd_gateway_str":
                            GB.sipmd_gateway_str = GB.paraValue[i];
                            break;
                        //============================================    
                        case "phone_name":
                            GB.phone_name = GB.paraValue[i];
                            break;
                        case "phone_no":
                            GB.phone_no = GB.paraValue[i];
                            break;
                        //============================================    
                        case "sip_server_ip":
                            GB.sip_server_ip = GB.paraValue[i];
                            break;
                        case "sip_server_pin":
                            GB.sip_server_pin = GB.paraValue[i];
                            break;
                        //============================================    
                        case "auto_answer":
                            GB.auto_answer = Integer.parseInt(GB.paraValue[i]);
                            break;
                        case "auto_answer_wait":
                            GB.auto_answer_wait = Integer.parseInt(GB.paraValue[i]);
                            break;
                        case "noanswer_timeout":
                            GB.noanswer_timeout = Integer.parseInt(GB.paraValue[i]);
                            break;
                        case "ear_mic_sens":
                            GB.ear_mic_sens = Integer.parseInt(GB.paraValue[i]);
                            break;
                        case "ear_speaker_vol":
                            GB.ear_speaker_vol = Integer.parseInt(GB.paraValue[i]);
                            break;
                        case "phset_mic_sens":
                            GB.phset_mic_sens = Integer.parseInt(GB.paraValue[i]);
                            break;
                        case "phset_speaker_vol":
                            GB.phset_speaker_vol = Integer.parseInt(GB.paraValue[i]);
                            break;
                        case "broadcast_comp_str":
                            GB.broadcast_comp_str = GB.paraValue[i];
                            break;
                        case "broadcast_comp_port":
                            GB.broadcast_comp_port = GB.paraValue[i];
                            break;
                        case "hotline1_name":
                            GB.hotline_nameA[0] = GB.paraValue[i];
                            break;
                        case "hotline1_no":
                            GB.hotline_noA[0] = GB.paraValue[i];
                            break;
                        case "hotline2_name":
                            GB.hotline_nameA[1] = GB.paraValue[i];
                            break;
                        case "hotline2_no":
                            GB.hotline_noA[1] = GB.paraValue[i];
                            break;
                        case "hotline3_name":
                            GB.hotline_nameA[2] = GB.paraValue[i];
                            break;
                        case "hotline3_no":
                            GB.hotline_noA[2] = GB.paraValue[i];
                            break;
                        case "hotline4_name":
                            GB.hotline_nameA[3] = GB.paraValue[i];
                            break;
                        case "hotline4_no":
                            GB.hotline_noA[3] = GB.paraValue[i];
                            break;
                        case "hotline5_name":
                            GB.hotline_nameA[4] = GB.paraValue[i];
                            break;
                        case "hotline5_no":
                            GB.hotline_noA[4] = GB.paraValue[i];
                            break;
                        case "hotline6_name":
                            GB.hotline_nameA[5] = GB.paraValue[i];
                            break;
                        case "hotline6_no":
                            GB.hotline_noA[5] = GB.paraValue[i];
                            break;
                        case "hotline7_name":
                            GB.hotline_nameA[6] = GB.paraValue[i];
                            break;
                        case "hotline7_no":
                            GB.hotline_noA[6] = GB.paraValue[i];
                            break;
                        case "hotline8_name":
                            GB.hotline_nameA[7] = GB.paraValue[i];
                            break;
                        case "hotline8_no":
                            GB.hotline_noA[7] = GB.paraValue[i];
                            break;
                        case "web_password":
                            GB.web_password = GB.paraValue[i];
                            break;
                        //============================================    
                        //for roip    
                        case "cor_invert":
                            GB.cor_invert = GB.paraValue[i];
                            break;
                        case "first_phase_tim":
                            GB.first_phase_tim = GB.paraValue[i];
                            break;
                        case "on_min_tim":
                            GB.on_min_tim = GB.paraValue[i];
                            break;
                        case "on_max_tim":
                            GB.on_max_tim = GB.paraValue[i];
                            break;
                        case "off_min_tim":
                            GB.off_min_tim = GB.paraValue[i];
                            break;
                        case "off_max_tim":
                            GB.off_max_tim = GB.paraValue[i];
                            break;
                        case "end_phase_tim":
                            GB.end_phase_tim = GB.paraValue[i];
                            break;
                        case "act1_phase_no":
                            GB.act1_phase_no = GB.paraValue[i];
                            break;
                        case "act1_call":
                            GB.act1_call = GB.paraValue[i];
                            break;
                        case "act2_phase_no":
                            GB.act2_phase_no = GB.paraValue[i];
                            break;
                        case "act2_call":
                            GB.act2_call = GB.paraValue[i];
                            break;
                        case "cut_phase_no":
                            GB.cut_phase_no = GB.paraValue[i];
                            break;
                        case "ptt_on_no"://dtmf number
                            GB.ptt_on_no = GB.paraValue[i];
                            break;
                        case "ptt_off_no"://dtmf number
                            GB.ptt_off_no = GB.paraValue[i];
                            break;
                        //=========================================================    
                        case "sipui_iptype":
                            GB.sipui_iptype = GB.paraValue[i];
                            break;
                        case "sipui_ip_str":
                            GB.sipui_ip_str = GB.paraValue[i];
                            break;
                        case "sipuiipmask_str":
                            GB.sipui_ipmask_str = GB.paraValue[i];
                            break;
                        case "sipui_gateway_str":
                            GB.sipui_gateway_str = GB.paraValue[i];
                            break;
                        case "switch_ip_str":
                            GB.switch_ip_str = GB.paraValue[i];
                            break;
                        case "switch_ipmask_str":
                            GB.switch_ipmask_str = GB.paraValue[i];
                            break;
                        case "switch_gateway_str":
                            GB.switch_gateway_str = GB.paraValue[i];
                            break;

                        case "winFrame_bm":
                            GB.winFrame_bm = Integer.parseInt(GB.paraValue[i]);
                            break;
                        case "winFrame_wm":
                            GB.winFrame_wm = Integer.parseInt(GB.paraValue[i]);
                            break;
                        case "winFrame_hm":
                            GB.winFrame_hm = Integer.parseInt(GB.paraValue[i]);
                            break;
                        case "ict_username":
                            GB.ict_username = GB.paraValue[i];
                            break;
                        case "ict_password":
                            GB.ict_password = GB.paraValue[i];
                            break;
                        default:
                            break;
                    }

                }
                //=================================================

                break;

            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 15:
                break;
            case 16:
                break;
            case 17:
                break;

        }

    }
}
