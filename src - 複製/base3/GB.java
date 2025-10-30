package base3;

public class GB {
    public static final int MAX_PARA_LEN = 1024;
    static int sipui_device_id = 0xd2; 
    static int sipui_io_device_id = 0xd1; 
    static int sipmd_io_device_id = 0xd4; 
    static int sipmd_device_id = 0xd3; 
    //=====================================================
    static String uiVersion="2.0";
    static String sipVersion="2.0";
    static int process_inx = 3;   //0:console sipph,1:desktop sipph,2:PhoneUi,3:Phone6in1 
    static int os_inx = 0;     //0:windows 1://linux
    //===============================
    static int ngrep_on_f = 1;
    static int linphone_twinkle_f=1;
    static int ictcon_on_f = 1;
    static int cursorOff_f = 0;
    //================================================
    static int fullScr_f = 0;
    static int frameOn_f = 0;
    static int syssec_f = 1;
    static int syssec_xor = 1;
    //================================================
    static int winFrame_bm = 30;
    static int winFrame_wm = 8;
    static int winFrame_hm = 38;
    static String syssec = "123-125-222-456-111-123";
    static String web_password = "1234";     
    
    
    //================================================
    //sipmd ui use
    //================================================
    //static String sipmd_linph_ip = "192.168.0.152";     
    static String sipmd_linph_ip = "127.0.0.1";     
    static String sipmd_linph_user_name = "pi";
    static String sipmd_linph_password = "1234";
    //
    //static String sipmd_ctr_ip = "192.168.0.152";
    static String sipmd_ctr_ip = "127.0.0.1";
    static String sipmd_ctr_user_name = "pi";
    static String sipmd_ctr_password = "1234";
    //
    //sipmd_ui tx "sipphone information" to sipui_ui over socket
    static String sipui_ui_ip = "192.168.0.151";     
    static int sipui_ui_port = 1336;   
    //================================================
    //sipui ui use
    //================================================
    //sipui_ui tx "command data" to sipmd_ui over socket
    static String sipmd_ui_ip = "192.168.0.152";     
    static int sipmd_ui_port = 1236;   
    
    
    
    //================================================
    public static String sipui_iptype = "0";
    static String sipui_ip_str = "192.168.0.150";
    static String sipui_ipmask_str = "255.255.255.0";
    static String sipui_gateway_str = "192.168.0.1";
    static String sipmd_ip_str = "192.168.0.152";  
    static String sipmd_ipmask_str = "255.255.255.0";
    static String sipmd_gateway_str = "192.168.0.1";
    static String switch_ip_str = "192.168.0.250";
    static String switch_ipmask_str = "255.255.255.0";
    static String switch_gateway_str = "192.168.0.1";
    //==============================================================================
    static int sipmd_port = 1236;   //sipui tx "command data" to sipmd(this port) over socket
    
    
    
    //==============================================================================
    
    static String ict_username = "mtcl";
    static String ict_password = "mtcl";
    
    
    static String broadcast_comp_str = "192.168.3.233";
    static String broadcast_comp_port = "5060";
    
    
    //windows debug use===================================================================
    static String setdata_xml = "./setdata.xml";
    static String setdata_db = "./setdata.db";
    static String interfaces_path = "./interfaces";
    static String twinkleCfg_path = "./twinkle.cfg";
    static String ntp_path = "./timesyncd.conf";
    //==============================================================================
    //nkv6in1_ui linux use
    //static String setdata_xml = "./setdata.xml";
    //static String setdata_db = "./setdata.db";
    //static String interfaces_path="/etc/network/interfaces";
    //static String twinkleCfg_path = "./twinkle.cfg";
    //static String ntp_path = "/etc/systemd/timesyncd.conf";
    //===============================================================================
    //sipphone_ui linux use
    //static String setdata_xml="/var/lib/tomcat9/webapps/ROOT/setdata.xml";
    //static String setdata_db="/var/lib/tomcat9/webapps/ROOT/setdata.db";
    //static String interfaces_path="/etc/network/interfaces";
    //static String twinkleCfg_path = "/home/pi/.twinkle/twinkle.cfg";
    //static String ntp_path = "/etc/systemd/timesyncd.conf";
    //===============================================================================
    
    
    
    
    static String real_ip_str = "";
    static String real_ipmask_str = "";
    static String real_gateway_str = "";
    
    

    static String[] paraName = new String[MAX_PARA_LEN];
    static String[] paraValue = new String[MAX_PARA_LEN];
    
    static String[] ictPhnos = new String[256];
    static int ictPhnos_amt=0;

    //unit =20ms 
    static int[] cortim = new int[10];
    static int corcnt = 0;
    static int cor_start_f = 0;
    static int cor_endtim = 0;
//    static String user_number = "311";
//    static String sip_proxy = "192.168.0.3";
//    static String auth_pin = "123456789";
    
    
    //static String ip_address = "192.168.0.152";
    //static String subnet_mask = "255.255.255.0";
    //static String router_ip = "192.168.0.1";
    
    
    


    //================================================
    static int paraLen = 0;
    static String ret_str;

    static int action_inx = 0;
    static int action_step = 0;
    static int action_tim = 0;
    //web use=========================================
    public static String sipmd_iptype = "0";
    //public static String sip_ip = "192.168.0.152";
    //public static String sipmd_ipmask_str = "255.255.0.0";
    //public static String sipmd_gateway_str = "192.168.0.1";
    public static String phone_name = "Kevin";
    public static String phone_no = "303";
    public static String sip_server_ip = "192.168.0.3";
    public static String sip_server_pin = "123456789";
    public static int noanswer_timeout = 60;//unit=1s
    public static int auto_answer = 1;
    public static int auto_answer_wait= 4;//unit=20ms
    public static int ear_mic_sens = 4;
    public static int ear_speaker_vol = 4;
    public static int phset_mic_sens = 4;
    public static int phset_speaker_vol = 4;
    //==================================================================
    /*
    public static String hotline1_name = "1";
    public static String hotline1_no = "301";
    public static String hotline2_name = "2";
    public static String hotline2_no = "302";
    public static String hotline3_name = "3";
    public static String hotline3_no = "303";
    public static String hotline4_name = "4";
    public static String hotline4_no = "304";
    public static String hotline5_name = "Name 5";
    public static String hotline5_no = "305";
    public static String hotline6_name = "Name 6";
    public static String hotline6_no = "306";
    public static String hotline7_name = "Name 7";
    public static String hotline7_no = "307";
    public static String hotline8_name = "Name 8";
    public static String hotline8_no = "308";
    */
    
    public static String[] hotline_nameA;
    public static String[] hotline_noA;
    //=================================================================
    public static String ptt_ctype = "0";
    public static String ptt_on_no = "1";
    public static String ptt_off_no = "2";
    public static String cor_invert = "1";
    public static String first_phase_tim = "100";
    public static String on_min_tim = "2";
    public static String on_max_tim = "40";
    public static String off_min_tim = "2";
    public static String off_max_tim = "40";
    public static String end_phase_tim = "50";
    public static String act1_phase_no = "5";
    public static String act1_call = "301";
    public static String act2_phase_no = "7";
    public static String act2_call = "303";
    public static String cut_phase_no = "9";
    //=================================================================
    public static String ntp_dns = "192.168.0.22";
    public static String ntp_adj_time = "160";

    
    /*
    static String[] configurationName = new String[]{"組態 1","組態 2","組態 3","組態 4","組態 5","組態 6",};
    static int configuration=0;
    
    static String[] confLocalIp =new String[]{"192.168.0.150","192.168.0.150","192.168.0.150","192.168.0.150","192.168.0.150","192.168.0.150"};
    static String[] confSipIp =new String[]{"192.168.0.152","192.168.0.152","192.168.0.152","192.168.0.152","192.168.0.152","192.168.0.152"};
    static String[] confSwitchIp =new String[]{"192.168.0.1","192.168.0.1","192.168.0.1","192.168.0.1","192.168.0.1","192.168.0.1"};
    static String[] confPbxIp =new String[]{"192.168.0.3","192.168.0.3","192.168.0.3","192.168.0.3","192.168.0.3","192.168.0.3"};
    static String[] confRegName =new String[]{"RegName 1","RegName 2","RegName 3","RegName 4","RegName 5","RegName 6"};
    static String[] confRegNo =new String[]{"301","302","303","304","305","306"};
    static String[][] confHotName =new String[6][4];
    static String[][] confHotNo =new String[6][4];
    */
    
    
    
    //================================================

    static void initGB() {
        GB.hotline_nameA=new String[8];
        GB.hotline_noA=new String[8];
        GB.hotline_nameA[0]="Name 1";
        GB.hotline_nameA[1]="Name 2";
        GB.hotline_nameA[2]="Name 3";
        GB.hotline_nameA[3]="Name 4";
        GB.hotline_nameA[4]="Name 5";
        GB.hotline_nameA[5]="Name 6";
        GB.hotline_nameA[6]="Name 7";
        GB.hotline_nameA[7]="Name 8";
        GB.hotline_noA[0]="301";
        GB.hotline_noA[1]="302";
        GB.hotline_noA[2]="303";
        GB.hotline_noA[3]="304";
        GB.hotline_noA[4]="305";
        GB.hotline_noA[5]="306";
        GB.hotline_noA[6]="307";
        GB.hotline_noA[7]="308";
        /*
        GB.confHotName[0]=new String[]{"1.HotName 1","1.HotName 2","1.HotName 3","1.HotName 4"};
        GB.confHotName[1]=new String[]{"2.HotName 2","2.HotName 2","2.HotName 3","2.HotName 4"};
        GB.confHotName[2]=new String[]{"3.HotName 3","3.HotName 2","3.HotName 3","3.HotName 4"};
        GB.confHotName[3]=new String[]{"4.HotName 4","4.HotName 2","4.HotName 3","4.HotName 4"};
        GB.confHotName[4]=new String[]{"5.HotName 5","5.HotName 2","5.HotName 3","5.HotName 4"};
        GB.confHotName[5]=new String[]{"6.HotName 6","6.HotName 2","6.HotName 3","6.HotName 4"};
        GB.confHotNo[0]=new String[]{"1.HotNo 1","1.HotNo 2","1.HotNo 3","1.HotNo 4"};
        GB.confHotNo[0]=new String[]{"2.HotNo 1","2.HotNo 2","2.HotNo 3","2.HotNo 4"};
        GB.confHotNo[0]=new String[]{"3.HotNo 1","3.HotNo 2","3.HotNo 3","3.HotNo 4"};
        GB.confHotNo[0]=new String[]{"4.HotNo 1","4.HotNo 2","4.HotNo 3","4.HotNo 4"};
        GB.confHotNo[0]=new String[]{"5.HotNo 1","5.HotNo 2","5.HotNo 3","5.HotNo 4"};
        GB.confHotNo[0]=new String[]{"6.HotNo 1","6.HotNo 2","6.HotNo 3","6.HotNo 4"};
        */
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
