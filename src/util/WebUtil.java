package util;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;


public class WebUtil {
	private static final String BuildNo = "V2.10-BNO20140401";
	private static ObjectMapper objectMapper = null;
	private static ApplicationContext appCtx;
	private static ApplicationContext appCtxPlatForm;
	private static Map<String, ApplicationContext> appCtxMap = new HashMap<String, ApplicationContext>();
	
	private static String DB_TYPE = Const.DB_TYPE_MYSQL;
	private static Properties jdbcProperties;
	private static boolean inited = true;
	
	private static boolean debug;
	
	private static boolean needVCode;
	
		
	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public static void setObjectMapper(ObjectMapper objectMapper) {
		WebUtil.objectMapper = objectMapper;
	}

	public  static boolean existsFile(String fileName){
		String loaderPath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		loaderPath = loaderPath.endsWith("/")?loaderPath.replace("/WEB-INF/classes/", ""):loaderPath.replace("/WEB-INF/classes", "");
		loaderPath = (fileName.startsWith("/")?loaderPath:loaderPath+"/")+fileName;
		File file=new File(loaderPath);
		return file.exists();
	}

	public static void doWithKeyEntry(){
		//检查DEBUG状态
		String ktp = "DEV";
		//KeyType为=Dev的都是开发用狗
		if(ktp.equals("DEV")){
			setDebug(true);
		}
	}
	
	
	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static <T> boolean isEmpty(T pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection<?>) {
			if (((Collection<?>) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map<?,?>) {
			if (((Map<?,?>) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}
	public static Object getJavaBeanByMap(Map<String,Object> params,@SuppressWarnings("rawtypes") Class c){
		Object o = null;
		try {
			o = c.newInstance();
		Method[] methods=c.getMethods();
		for(Method method:methods){
			String methodName=method.getName();
			if(methodName.startsWith("set")){
				String propertyName=methodName.substring(3).toLowerCase(Locale.getDefault());
				Object value=params.get(propertyName);
				if(value!=null){
					method.invoke(o, value.getClass().cast(value));
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return o;
	}
	
	
	/*
	 * 标准的ResponseMap结构如下
	 * {
	 * 		success:true,
	 * 		ajaxErrorCode:AJAX_ERR_CODE_100_OK,
	 * 		message:'',
	 * 		data:'',
	 * 		total:0
	 * }
	 */
	public static Map<String,Object> getDefaultResponseMap(){
		Map<String,Object> respMap = new HashMap<String,Object>();
		respMap.put(Const.AJAX_SERVICE_SUCCESS, true);
		respMap.put(Const.AJAX_SERVICE_MESSAGE,Const.AJAX_COMPLETE_MSG);
		respMap.put(Const.AJAX_SERVICE_TOTAL,0);
		respMap.put(Const.AJAX_ERR_CODE,Const.AJAX_ERR_CODE_200_OK);
		respMap.put(Const.AJAX_DATA_ROOT, "");
		return respMap;
	}
	//从Request中一次读取参数到Map中
	public static Map<String,Object> getDefaultParamsMap(HttpServletRequest request){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		@SuppressWarnings("unchecked")
		Enumeration<String> pNames = request.getParameterNames();
		while(pNames.hasMoreElements()){
			String pName =pNames.nextElement();
			paramMap.put(pName, request.getParameter(pName));
		}
		//清除掉几个非查询参数
		paramMap.remove(Const.AJAX_SERVICE_METHOD);
		paramMap.remove(Const.AJAX_SERVICE_MODEL);
		paramMap.remove(Const.VALIDATE_VTEXT0);
		paramMap.remove(Const.VALIDATE_VTEXT1);
		paramMap.remove(Const.VALIDATE_FIELDS);
		paramMap.remove(Const.AJAX_DATA_ROOT);
		
		return paramMap;
	}
	//从Request中一次读取参数到Map中
	public static Map<String,Object> getServiceParamsMap(HttpServletRequest request){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		@SuppressWarnings("unchecked")
		Enumeration<String> pNames = request.getParameterNames();
		while(pNames.hasMoreElements()){
			String pName =pNames.nextElement();
			paramMap.put(pName, request.getParameter(pName));
		}
		//清除掉几个参数
		paramMap.remove(Const.AJAX_SERVICE_METHOD);
		paramMap.remove(Const.AJAX_SERVICE_MODEL);
		paramMap.remove(Const.VALIDATE_VTEXT0);
		paramMap.remove(Const.VALIDATE_VTEXT1);
		paramMap.remove(Const.VALIDATE_FIELDS);
		return paramMap;
	}
	
	//产生标准错误消息
	public static void makeErrorMsg(Exception e,Map<String,Object> respMap){
		StringBuilder sb = new StringBuilder();
		sb.append("错误发生在:<br/>");
		StackTraceElement[] stacks = e.getStackTrace();
		for(StackTraceElement stack:stacks){
			sb.append(stack.toString()).append("<br/>");
		}
		sb.append("错误详情:<br/>");
		sb.append(e.getMessage());
		respMap.put(Const.AJAX_SERVICE_SUCCESS, false);
		respMap.put(Const.AJAX_SERVICE_MESSAGE,sb.toString());
	}
	
	public static void setAppCtxPlatForm(ApplicationContext appCtxPlatForm) {
		WebUtil.appCtxPlatForm = appCtxPlatForm;
		WebUtil.appCtxMap.put("PlatForm", appCtx);
	}

	public static ApplicationContext getAppCtxPlatForm() {
		return appCtxPlatForm;
	}

	public static void setAppCtx(ApplicationContext appCtx) {
		WebUtil.appCtx = appCtx;
		WebUtil.appCtxMap.put("GinPlus", appCtx);
	}

	public static void setAppCtx(String key, ApplicationContext appCtx) {
		WebUtil.appCtxMap.put(key, appCtx);
	}
	
	public static ApplicationContext getAppCtx() {
		return appCtx;
	}

	public static ApplicationContext getAppCtx(String key) {
		return WebUtil.appCtxMap.get(key);
	}
	
	private static void setDB_TYPE(String dB_TYPE) {
		DB_TYPE = dB_TYPE;
	}

	@SuppressWarnings("unused")
	private static String getDB_TYPE() {
		return DB_TYPE;
	}

	public static void setInited(boolean inited) {
		WebUtil.inited = inited;
	}

	public static boolean isInited() {
		return inited;
	}

	public static String getBuildno() {
		return BuildNo;
	}

	public static void setDebug(boolean debug) {
		WebUtil.debug = debug;
	}

	public static boolean isDebug() {
		return debug;
	}

	public static void setJdbcProperties(Properties jdbcProps) {
		jdbcProperties = jdbcProps;
		String dbUrl = jdbcProperties.getProperty("jdbc.databaseurl").toUpperCase();
		
	}

	public static Properties getJdbcProperties() {
		return jdbcProperties;
	}

	public static void setNeedVCode(boolean needVCode) {
		WebUtil.needVCode = needVCode;
	}

	public static boolean isNeedVCode() {
		return needVCode;
	}
	
	/**
	 * 获取项目在服务其中的真实路径的工具类
	 * 
	 * 这是在web项目中，获取项目实际路径的最佳方式，在windows和linux系统下均可正常使用
	 * 
	 */
	public static String getRootPath() {
		
		String classPath = WebUtil.class.getClassLoader().getResource("/").getPath();
		String rootPath = "";
		//windows下
		if(isWindowServer()){
			rootPath = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}else{
			rootPath = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}
	
	public static String getUpLoadFileRoot(){
		//windows下 "\\".equals(File.separator)
		String root="D:/srmfile/";
		//linux下
		if(!isWindowServer()){
			root= "/home/srmfile/";
		}
		return root;
	}
	public static String getIpAddr(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	/**
	* @Description: 判断是否window服务器
	* @return 是 true/否 false
	* @author xufeng
	* @date 2016-5-30 
	*/
	public static boolean isWindowServer(){
		if("/".equals(File.separator)){
			return false;
		}else{
			return true;
		}
	}
}
