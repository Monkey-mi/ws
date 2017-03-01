/**    
 * 文件名：DataTrans.java    
 *    
 * 版本信息：    
 * 日期：2016-4-21    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**    
 * 数据格式转换类
 * 项目名称：outsideeasy    
 * 类名称：DataTrans    
 * 创建人：mishengliang    
 * 创建时间：2016-4-21 下午1:35:31    
 * 修改人：mishengliang    
 * 修改时间：2016-4-21 下午1:35:31    
 * @version     
 *     
 */
public class DataTrans {
	/**
	 * String转Integer
	*transToIntegerFromString
	*@param data
	*@return
	*Integer
	*@author mishengliang
	*2016-5-24下午7:13:09
	 */
	public static Integer transToIntegerFromString(String data){
		if(data != null && data != ""){
			return Integer.parseInt(data);
		}
		return null;
	} 
	
	/**
	 * String转Float
	*transToFloatFromString
	*@param data
	*@return
	*Float
	*@author mishengliang
	*2016-5-24下午7:14:00
	 */
	public static Float transToFloatFromString(String data){
		if(data != null && data != ""){
			return Float.parseFloat(data);
		}
		return null;
	}
	
	/**
	 * String转Double
	*transToDoubleFromString
	*@param data
	*@return
	*Double
	*@author mishengliang
	*2016-5-24下午7:15:34
	 */
	public static Double transToDoubleFromString(String data){
		if(data != null && data != ""){
			return Double.parseDouble(data);
		}
		return null;
	}
	
	public static Date transToDateFromString(String data) throws ParseException{
		if(data != null && data != ""){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.parse(data);
		}
		return null;
	}
	
}
