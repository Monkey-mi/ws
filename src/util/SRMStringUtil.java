package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SRMStringUtil {
	
	/**判断是否是数字*/
	public static boolean isNumber(String value){
		//Pattern pattern = Pattern.compile("^\\d+$|-\\d+$"); // 就是判断是否为整数
		//Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");//判断是否为小数
		Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$|^\\d+$|-\\d+$");//判断是否为数字
		Matcher isNum = pattern.matcher(value);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	public static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    } 
    /** 
     * 获得指定数目的UUID 
     * @param number int 需要获得的UUID数量 
     * @return String[] UUID数组 
     */ 
    public static String getUUID(int number){ 
        if(number < 1){ 
            return null; 
        } 
        String[] ss = new String[number]; 
        for(int i=0;i<number;i++){ 
            ss[i] = getUUID(); 
        } 
        return ss.toString(); 
    } 
    
	/**
	 * 生成特定的时间String mmsssHHMMddyyyy
	 * @author chenlong
	 * @date 2016-7-13
	 */
	public static ThreadLocal<DateFormat> mmsssHHMMddyyyy_FORMAT = new ThreadLocal<DateFormat>() {
		 
         @Override
         protected synchronized DateFormat initialValue() {
             return new SimpleDateFormat("mmsssHHMMddyyyy");
         }
     };

     public static Date parse(String dateStr) throws ParseException {
         return mmsssHHMMddyyyy_FORMAT.get().parse(dateStr);
     }

     public static String format(Date date) {
         return mmsssHHMMddyyyy_FORMAT.get().format(date);
     }
     
}
