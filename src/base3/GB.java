package base3;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

///copy rinback.wav to usr/share/twinkle
public class GB {

    //0: win.ui, 1: win.sip, 2: pi.ui, 3: pi.sip  
    static int prgMode = 3;

    
    
    static int syssec_f = 1;
    static int syssec_xor = 0;
    static int ngrep_on_f = 0;
    static int pbxConOn_f = 0;

    static int cursorOff_f = 0;
    static int fullScr_f = 0;
    static int frameOn_f = 0;
    static int winFrame_bm = 30;
    static int winFrame_wm = 8;
    static int winFrame_hm = 38;

    public static final int MAX_PARA_LEN = 8192;
    static int sipUiDeviceId = 0xd2;
    static int lang = 1;//0:english 1:chinese 
    static String paraSetPath = "";
    //=====================================================
    static int process_inx = 3;   //0:console sipph,1:desktop sipph,2:PhoneUi,3:Phone6in1 
    static int os_inx = 0;     //0:windows 1://linux    
    //===============================
    //================================================
    static String macStr;
    static int syssec_gen_f = 0;
    static String syssec = "123-125-222-456-111-123";

    //================================================
    //sipmd ui use
    //================================================
    static String twinkleDeviceIp = "192.168.0.85";//just win use     
    static String twinkleDeviceName = "pi";
    static String twinkleDevicePassword = "123456789";
    static int sipToUiSocketPort = 1336;
    //================================================
    static int uiToSipPhonePort = 1236;
    //==============================================================================
    static String alueIcsUserName = "mtcl";
    static String alueIcsPassword = "mtcl";
    //windows debug use===================================================================
    static String setdata_xml = "./setdata.xml";
    static String setdata_db = "./setdata.db";
    static String interfaces_path = "./interfaces";
    static String twinkleCfg_path = "./twinkle.cfg";
    static String ntp_path = "./timesyncd.conf";
    //==============================================================================
    static String realIpAddress = "";
    static String realNetMask = "";
    static String realGateWay = "";

    static String[] paraName = new String[MAX_PARA_LEN];
    static String[] paraValue = new String[MAX_PARA_LEN];
    static String[] icsPhnos = new String[256];
    static int icsPhnos_amt = 0;
    //================================================
    static int paraLen = 0;
    static String ret_str;

    static int action_inx = 0;
    static int action_step = 0;
    static int action_tim = 0;
    //web use=========================================
    public static int ear_mic_sens = 9;
    public static int phset_mic_sens = 9;
    //==================================================================

    //public static int carTypeName_len=0;
    public static ArrayList<String>[] carInf_strAA;
    public static Map<String, String> nameMap = new HashMap();
    public static Map<String, String> carTypeMap = new HashMap();

    //=================================================================
    public static Map<String, Object> paraSetMap = new HashMap();
    public static HashMap<String, ConnectCla> connectMap = new HashMap();
    public static String webSocketAddr = null;
    public static int webSocketPort = 8899;

    public static String asound_path = "";
    public static String twinkleSys_path = "";
    public static String preParaSetTime = "";

    public static HashMap<String, Object> paraSaveMap = new HashMap();


    
    static void saveParaSet() {
        if(GB.paraSaveMap.size()==0)
            return;
        try {
            Gson gson = new Gson();
            String content = Lib.readFile(GB.paraSetPath);
            JsonObject jsPara = JsonParser.parseString(content).getAsJsonObject();
            //=======================================================================
            String[] strA;
            String keyName="";
            String dataType="str";
            for (String key : GB.paraSaveMap.keySet()) {
                strA = key.split("~");
                keyName=strA[0];
                if(strA.length==2){
                    dataType=strA[1];
                }
                Object value=GB.paraSaveMap.get(key);
                jsPara.add(keyName, gson.toJsonTree(value));
            }
            GB.paraSaveMap.clear();
            //=======================================================================
            content = jsPara.toString();
            BufferedWriter outf = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(GB.paraSetPath), "UTF-8"));
            try {
                outf.write(content);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                outf.close();
                //Path file = Paths.get(GB.paraSetPath);
                //BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                //GB.preParaSetTime = attr.lastModifiedTime().toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
    
    static void loadParaSet() {
        try {
            String content = Lib.readFile(GB.paraSetPath);
            GB.paraSetMap.clear();
            JSONObject jsPara = new JSONObject(content);
            Iterator<String> it = jsPara.keys();
            while (it.hasNext()) {
                String key = it.next();
                GB.paraSetMap.put(key, jsPara.get(key));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    static ArrayList<String> checkParaSet() {
        Map<String, Object> paraCheckMap = new HashMap();
        ArrayList<String> editA=new ArrayList<String>();             
        try {
            String content = Lib.readFile(GB.paraSetPath);
            paraCheckMap.clear();
            JSONObject jsPara = new JSONObject(content);
            Iterator<String> it = jsPara.keys();
            while (it.hasNext()) {
                String key = it.next();
                paraCheckMap.put(key, jsPara.get(key));
            }
            
            for(String key:GB.paraSetMap.keySet()){
                Object nobj= paraCheckMap.get(key);
                Object oobj= GB.paraSetMap.get(key);
                String[] strA=key.split("~");
                if(strA[0].equals("dsc"))
                    continue;
                if(oobj!= null && nobj !=null ){
                    if(!oobj.equals(nobj)){
                        String className=oobj.getClass().getSimpleName();
                        if(className.equals("JSONArray"))
                            continue;
                        editA.add(key);
                    }
                }
            }
            
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return editA;
        
        
        

    }
    
    
    static void initGB() {
        if (GB.prgMode == 0) {
            GB.process_inx = 3;   //0:console sipph,1:desktop sipph,2:PhoneUi,3:Phone6in1 
            GB.os_inx = 0;     //0:windows 1://linux    
            GB.setdata_xml = "./setdata.xml";
            GB.setdata_db = "./setdata.db";
            GB.interfaces_path = "./interfaces";
            GB.twinkleCfg_path = "./twinkle.cfg";
            GB.ntp_path = "./timesyncd.conf";
            GB.paraSetPath = "./paraSetUi.json";
        }
        if (GB.prgMode == 1) {
            GB.process_inx = 0;   //0:console sipph,1:desktop sipph,2:PhoneUi,3:Phone6in1 
            GB.os_inx = 0;     //0:windows 1://linux    
            GB.setdata_xml = "./setdata.xml";
            GB.setdata_db = "./setdata.db";
            GB.interfaces_path = "./interfaces";
            GB.twinkleCfg_path = "./twinkle.cfg";
            GB.ntp_path = "./timesyncd.conf";
            GB.asound_path = "./asound.conf";
            GB.twinkleSys_path = "./twinkle.sys";
            GB.paraSetPath = "./paraSetSip.json";

        }
        if (GB.prgMode == 2) {
            GB.process_inx = 3;   //0:console sipph,1:desktop sipph,2:PhoneUi,3:Phone6in1 
            GB.os_inx = 1;     //0:windows 1://linux    
            GB.setdata_xml = "./setdata.xml";
            GB.setdata_db = "./setdata.db";
            GB.interfaces_path = "/etc/network/interfaces";
            GB.twinkleCfg_path = "./twinkle.cfg";
            GB.ntp_path = "/etc/systemd/timesyncd.conf";
            GB.twinkleDeviceIp = "127.0.0.1";
            GB.paraSetPath = "/home/pi/kevin/sipui2in1/paraSetUi.json";
        }
        if (GB.prgMode == 3) {
            GB.process_inx = 0;   //0:console sipph,1:desktop sipph,2:PhoneUi,3:Phone6in1 
            GB.os_inx = 1;     //0:windows 1://linux    
            GB.setdata_xml = "/var/lib/tomcat9/webapps/ROOT/setdata.xml";
            GB.setdata_db = "/var/lib/tomcat9/webapps/ROOT/setdata.db";
            GB.interfaces_path = "/etc/network/interfaces";
            GB.twinkleCfg_path = "/home/pi/.twinkle/twinkle.cfg";
            GB.ntp_path = "/etc/systemd/timesyncd.conf";
            GB.asound_path = "/etc/asound.conf";
            GB.twinkleSys_path = "/home/pi/.twinkle/twinkle.sys";
            GB.twinkleDeviceIp = "127.0.0.1";
            GB.paraSetPath = "/home/pi/kevin/sipphone/paraSetSip.json";
        }

        //=============================================================
    }

    public static void loadPara2Form() {

        Class type;
        Object obj;
        int i;
        String str;
        String[] strA;
        String[][] strAA;

        //java.lang.reflect.Field[] f3 = cla.getClass().getDeclaredFields();
        java.lang.reflect.Field[] f3 = GB.class.getDeclaredFields();
        for (i = 0; i < f3.length; i++) {
            f3[i].setAccessible(true);
            try {
                obj = f3[i].get(GB.class);

                if (obj instanceof String[][]) {
                    str = f3[i].getName();
                    strAA = (String[][]) obj;
                    for (int j = 0; j < paraLen; j++) {
                        String[] sbufA;
                        sbufA = paraName[j].split("~");
                        if (sbufA.length == 3) {
                            if (str.equals(sbufA[0])) {
                                byte[] bytes = new byte[paraValue[j].length()];
                                String str1 = paraValue[j];
                                for (int m = 0; m < str1.length(); m++) {
                                    bytes[m] = (byte) str1.charAt(m);
                                }
                                strAA[Integer.parseInt(sbufA[1])][Integer.parseInt(sbufA[2])] = new String(bytes, Charset.forName("UTF-8"));
                            }
                        }
                    }
                } else if (obj instanceof String[]) {
                    str = f3[i].getName();
                    strA = (String[]) obj;
                    for (int j = 0; j < paraLen; j++) {
                        String[] sbufA;
                        sbufA = paraName[j].split("~");
                        if (sbufA.length == 2) {
                            if (str.equals(sbufA[0])) {
                                byte[] bytes = new byte[paraValue[j].length()];
                                String str1 = paraValue[j];
                                for (int m = 0; m < str1.length(); m++) {
                                    bytes[m] = (byte) str1.charAt(m);
                                }
                                strA[Integer.parseInt(sbufA[1])] = new String(bytes, Charset.forName("UTF-8"));
                            }
                        }
                    }
                } else if (obj instanceof int[]) {
                    str = f3[i].getName();
                    int[] intA = (int[]) obj;
                    for (int j = 0; j < paraLen; j++) {
                        String[] sbufA;
                        sbufA = paraName[j].split("~");
                        if (sbufA.length == 2) {
                            if (str.equals(sbufA[0])) {
                                intA[Integer.parseInt(sbufA[1])] = Integer.parseInt(paraValue[j]);
                            }
                        }
                    }
                } else if (obj instanceof String) {
                    str = f3[i].getName();

                    for (int j = 0; j < paraLen; j++) {
                        if (str.equals(paraName[j])) {
                            byte[] bytes = new byte[paraValue[j].length()];
                            String str1 = paraValue[j];
                            for (int m = 0; m < str1.length(); m++) {
                                bytes[m] = (byte) str1.charAt(m);
                            }
                            f3[i].set(GB.class, new String(bytes, Charset.forName("UTF-8")));
                        }
                    }
                } else if (obj instanceof Integer) {
                    str = f3[i].getName();
                    for (int j = 0; j < paraLen; j++) {
                        if (str.equals(paraName[j])) {
                            f3[i].set(GB.class, Integer.parseInt(paraValue[j]));
                        }
                    }
                } else {

                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(GB.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    static void clrPara() {
        for (int i = 0; i < GB.paraLen; i++) {
            GB.paraName[i] = null;
            GB.paraValue[i] = null;
            GB.paraLen = 0;
        }
    }

    static int newPara(String name, String value) {
        if (paraLen >= MAX_PARA_LEN) {
            return 0;
        }
        paraName[paraLen] = name;
        paraValue[paraLen] = value;
        paraLen++;
        return 1;
    }

    static int editPara(String name, String value) {
        int i;
        for (i = 0; i < paraLen; i++) {
            if (paraName[i].equals(name)) {
                paraValue[i] = value;
                return 1;
            }
        }
        return 0;
    }

    static int editNewPara(String name, String value) {
        if (editPara(name, value) == 0) {
            return newPara(name, value);
        }
        return 1;
    }

    static String getPara(String name) {
        int i;
        for (i = 0; i < paraLen; i++) {
            if (paraName[i].equals(name)) {
                return paraValue[i];
            }
        }
        return null;
    }

    static int deletePara(String name) {
        int i;
        for (i = 0; i < paraLen; i++) {
            if (paraName[i].equals(name)) {
                i++;
                for (; i < paraLen; i++) {
                    paraName[i - 1] = paraName[i];
                    paraValue[i - 1] = paraValue[i];
                }
                paraLen--;
                return 1;
            }
        }
        return 0;
    }

}
