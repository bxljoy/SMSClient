package sms.client;

public class SMSSender {
    /***********************************************************************
     * 18618498228
     * ******/
    public static void main(String[] args) {
        String res = sendPost("18618498228,15652376661", "验证码为5787123,请尽快输入", "网信科技");
        System.out.println(res);
    }
    
    public static String sendPost(String number, String message, String sign) {
    	String res = HttpRequest.sendPost("http://123.57.67.94:8080/smsport/sendPost.aspx", 
        		"uid=shkh&upsd="+Str2MD5.MD532("shkh@wx")+"&sendtele="+ number +"&Msg="+message+"&sendtime=&sign="+sign); 
    	System.out.println(res);
    	return res;
    }
}
