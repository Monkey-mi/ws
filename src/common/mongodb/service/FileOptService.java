package common.mongodb.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import util.BeanUtil;
import util.Const;
import util.FFMpegUtil;
import util.SRMStringUtil;
import util.properties.FFMpeg;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import common.model.BusinessException;
import common.mongodb.dao.FileOptDao;
import common.mongodb.model.FileBean;
import common.mongodb.model.VideoFileBean;


@Service
public class FileOptService {
	
	private  static  final Logger logger = Logger.getLogger(FileOptService. class);
	@Autowired
	private FileOptDao fileDao;
	
	/**
	* @Description: 多条件分页查询文件信息
	* @param start 起始记录
	* size  读取数量
	* ISIMG="isimg";//是否图片，1是 ，0否
	 USE_TYPE="useType";//使用类别,见t_base_file_type
	 COM_NAME="comName";//公司名
	 COM_ID="comID";//公司ID
	 USERNAME="userName";//上传者
	 uploadDate1:Date类型，开始时间
	 uploadDate2:Date类型，结束时间
	* @return
	* @throws Exception
	* @author xufeng
	* @date 2016-9-26 
	*/
	public List<FileBean> getFileList(Map<String,Object> params) throws Exception{
		DB db = fileDao.getDB();
        List<FileBean> list=new ArrayList<FileBean>();
        // 存储fs的根节点
        GridFS gridFS = new GridFS(db, "fs");
        DBObject query  = new BasicDBObject();
        
    	for (Map.Entry<String,Object> entry : params.entrySet()) {  
           	if(!entry.getKey().equals(Const.AJAX_SERVICE_START) && !entry.getKey().equals(Const.AJAX_SERVICE_SIZE)
           			&& !entry.getKey().equals(Const.USE_PAGING) && entry.getKey().indexOf("uploadDate")<0){
           		if(!"".equals(entry.getValue()) && entry.getValue()!=null){
           			System.out.println(entry.getKey()+":"+entry.getValue());
               		query.put(entry.getKey(),entry.getValue());
           		}
           		
           	}
           }
    	if(params.get("uploadDate1")!=null && !"".equals(params.get("uploadDate1")) 
    			&&params.get("uploadDate2")!=null && !"".equals(params.get("uploadDate2"))){
    		query.put("uploadDate",new BasicDBObject("$gte",params.get("uploadDate1")).append("$lte",params.get("uploadDate2")));
  	    }else if(params.get("uploadDate1")!=null && !"".equals(params.get("uploadDate1")) ){
    		query.put("uploadDate",new BasicDBObject("$gte",params.get("uploadDate1")));
  	    }else if(params.get("uploadDate2")!=null && !"".equals(params.get("uploadDate2"))){
    		query.put("uploadDate",new BasicDBObject("$lte",params.get("uploadDate2")));
  	    }
        if(params.get(Const.AJAX_SERVICE_START)==null){
 	       params.put(Const.AJAX_SERVICE_START, 0);
 	    }
 	    if(params.get(Const.AJAX_SERVICE_SIZE)==null){
 	       params.put(Const.AJAX_SERVICE_SIZE, 20);
 	    }
        
        int filecount=gridFS.getFileList(query).count();
        params.put(Const.AJAX_SERVICE_TOTAL, filecount);
        DBCursor fileList =null;
        DBObject orderBy=new BasicDBObject("uploadDate", -1);//按照上传时间倒序
        if("true".equals(params.get(Const.USE_PAGING))){
        	fileList = gridFS.getFileList(query).sort(orderBy).skip(Integer.valueOf(params.get(Const.AJAX_SERVICE_START).toString())).limit(Integer.valueOf(params.get(Const.AJAX_SERVICE_SIZE).toString()));
        }else{
        	fileList = gridFS.getFileList(query).sort(orderBy);
        }
        while(fileList.hasNext()){
        	DBObject obj = fileList.next();
        	FileBean bean = BeanUtil.dbObject2Bean(obj, new FileBean());
        	list.add(bean);
        }
       return list;
	}
	/**保存文件，重载方法1,旧的，替换后删除*/
	public String SaveFile(byte[] buff,String originalFilename,String contentType) throws Exception{
		String fileName = SRMStringUtil.getUUID();//文件名生成器
		try {
	            DB db = fileDao.getDB();
	            // 存储fs的根节点
	            GridFS gridFS = new GridFS(db, "fs");
	            InputStream sbs = new ByteArrayInputStream(buff); 
	            GridFSInputFile gfs = gridFS.createFile(sbs);
	            gfs.put("aliases", originalFilename);
	            gfs.put("filename", fileName);
	            gfs.put("contentType",contentType);
	            gfs.save();
	            logger.debug("mongodb SaveFile:"+fileName);
	        } catch (Exception e) {
	        	throw new BusinessException(e,"mongodb在fs保存"+originalFilename+"失败");
	        }
		  return fileName;
	}
	/**
	* @Description: 保存文件，重载方法1
	* @param buff 文件流
	* @param originalFilename 原文件名
	* @param contentType 上传文件类型
	* @param 文件信息
	* @return
	* @throws Exception
	* @author xufeng
	* @date 2016-9-26 
	*/
	public String SaveFile(byte[] buff,String originalFilename,String contentType,Map<String,Object> params) throws Exception{
		String fileName = SRMStringUtil.getUUID();//文件名生成器
		try {
	            DB db = fileDao.getDB();
	            // 存储fs的根节点
	            GridFS gridFS = new GridFS(db, "fs");
	            InputStream sbs = new ByteArrayInputStream(buff); 
	            GridFSInputFile gfs = gridFS.createFile(sbs);
	            gfs.put("aliases", originalFilename);
	            gfs.put("filename", fileName);
	            gfs.put("contentType",contentType);
	           //mongo文件管理，附加的自定义字段
	            gfs.put(Const.ISIMG, params.get(Const.ISIMG));//是否图片，1是 ，0否
	            gfs.put(Const.USE_TYPE, params.get(Const.USE_TYPE));//使用类别,t_base_file_type
	            gfs.put(Const.COM_NAME, params.get(Const.COM_NAME));//公司名
	            gfs.put(Const.COM_ID, params.get(Const.COM_ID));//公司ID
	            gfs.put(Const.USERNAME, params.get(Const.USERNAME));//上传者
	            gfs.save();
	            logger.debug("mongodb SaveFile:"+fileName);
	        } catch (Exception e) {
	        	throw new BusinessException(e,"mongodb在fs保存"+originalFilename+"失败");
	        }
		  return fileName;
	}
	/**保存文件，重载方法2,旧的，替换后删除*/
	public String SaveFile(MultipartFile file) throws Exception{
		String originalFilename = file.getOriginalFilename();
		String fileName = SRMStringUtil.getUUID();//文件名生成器
		try {
	            DB db = fileDao.getDB();
	            // 存储fs的根节点
	            GridFS gridFS = new GridFS(db, "fs");
	            GridFSInputFile gfs = gridFS.createFile(file.getInputStream());
	            gfs.put("aliases", originalFilename);
	            gfs.put("filename", fileName);
	            gfs.put("contentType",file.getContentType());
	            gfs.save();
	            logger.debug("mongodb SaveFile:"+fileName);
	        } catch (Exception e) {
	        	throw new BusinessException(e,"mongodb在fs保存"+originalFilename+"失败");
	        }
		  return fileName;
	}
	/**
	* @Description: 保存文件，重载方法2
	* @param file 上传文件对象
	* @param params 文件信息
	* @return
	* @throws Exception
	* @author xufeng
	* @date 2016-9-26 
	*/
	public String SaveFile(MultipartFile file,Map<String,Object> params) throws Exception{
		String originalFilename = file.getOriginalFilename();
		String fileName = SRMStringUtil.getUUID();//文件名生成器
		try {
            DB db = fileDao.getDB();
            // 存储fs的根节点
            GridFS gridFS = new GridFS(db, "fs");
            GridFSInputFile gfs = gridFS.createFile(file.getInputStream());
            gfs.put("aliases", originalFilename);
            gfs.put("filename", fileName);
            gfs.put("contentType",file.getContentType());
            //mongo文件管理，附加的自定义字段
            gfs.put(Const.ISIMG, params.get(Const.ISIMG));//是否图片，1是 ，0否
            gfs.put(Const.USE_TYPE, params.get(Const.USE_TYPE));//使用类别t_base_file_type
            gfs.put(Const.COM_NAME, params.get(Const.COM_NAME));//公司名
            gfs.put(Const.COM_ID, params.get(Const.COM_ID));//公司ID
            gfs.put(Const.USERNAME, params.get(Const.USERNAME));//上传者
            gfs.save();
            logger.debug("mongodb SaveFile:"+fileName);
        } catch (Exception e) {
        	throw new BusinessException(e,"mongodb在fs保存"+originalFilename+"失败");
        }
		 return fileName;
	}
	/**
	* @Description: 保存文件，重载方法2
	* @param file 上传文件对象
	* @param params 文件信息
	* @return
	* @throws Exception
	* @author xufeng
	* @date 2016-9-26 
	*/
	public String SaveFile(InputStream is,Map<String,Object> params) throws Exception{
		String fileName = SRMStringUtil.getUUID();//文件名生成器
		try {
            DB db = fileDao.getDB();
            // 存储fs的根节点
            GridFS gridFS = new GridFS(db, "fs");
            GridFSInputFile gfs = gridFS.createFile(is);
            gfs.put("aliases", params.get("originalFilename"));
            gfs.put("filename", fileName);
            gfs.put("contentType","application/octet-stream");
            //mongo文件管理，附加的自定义字段
            gfs.put(Const.ISIMG, params.get(Const.ISIMG));//是否图片，1是 ，0否
            gfs.put(Const.USE_TYPE, params.get(Const.USE_TYPE));//使用类别t_base_file_type
            gfs.put(Const.COM_NAME, params.get(Const.COM_NAME));//公司名
            gfs.put(Const.COM_ID, params.get(Const.COM_ID));//公司ID
            gfs.put(Const.USERNAME, params.get(Const.USERNAME));//上传者
            gfs.save();
            logger.debug("mongodb SaveFile:"+fileName);
        } catch (Exception e) {
        	throw new BusinessException(e,"mongodb在fs保存"+params.get("originalFilename")+"失败");
        }
		 return fileName;
	}
	public GridFSDBFile retrieveFileOne(String filename){
		return this.retrieveFileOne("fs", filename);
	} 
	// 取出文件
    public GridFSDBFile retrieveFileOne(String collectionName, String filename) {
        try {
            DB db = fileDao.getDB();
            // 获取fs的根节点
            GridFS gridFS = new GridFS(db, collectionName);
            GridFSDBFile dbfile = gridFS.findOne(filename);
            if (dbfile != null) {
                return dbfile;
            }
        } catch (Exception e) {
        	throw new BusinessException(e,"mongodb获取从"+collectionName+"获取"+filename+"文件失败");
        }
        return null;
    }
    
    //删除文件
    public void deleteFileByName(String filename) {
        if(filename!=null){
        	try {
                DB db = fileDao.getDB();
                
                // 获取fs的根节点
                GridFS gridFS = new GridFS(db, "fs");
                //删除Mongo上的文件
                gridFS.remove(filename);
                logger.debug("mongodb deleteFileByName:"+filename);
            } catch (Exception e) {
                throw new BusinessException(e,"mongodb从fs删除"+filename+"失败");
            }
        }
    	
    }
    /**保存文件，重载方法3,旧的，替换后删除*/
	public String SaveFile(File tempfile){
		String fileName=SRMStringUtil.getUUID();
		try {
			DB db = fileDao.getDB();
	        // 存储fs的根节点
	        GridFS gridFS = new GridFS(db, "fs");
	        GridFSInputFile gfs = gridFS.createFile(tempfile);
	        gfs.put("aliases", tempfile.getName());
	        gfs.put("filename", fileName);
	        gfs.put("contentType","application/octet-stream");
	        gfs.save();
	        logger.debug("mongodb SaveFile:"+fileName);
		} catch (Exception e) {
        	throw new BusinessException(e,"mongodb在fs保存"+fileName+"失败");
        }
		return fileName;
	}
	/**
	* @Description: 保存文件，重载方法3
	* @param file 上传文件对象
	* @param params 文件信息
	* @return
	* @throws Exception
	* @author xufeng
	* @date 2016-9-26 
	*/
	public String SaveFile(File tempfile,Map<String,Object> params){
		String fileName=SRMStringUtil.getUUID();
		try {
			DB db = fileDao.getDB();
	        // 存储fs的根节点
	        GridFS gridFS = new GridFS(db, "fs");
	        GridFSInputFile gfs = gridFS.createFile(tempfile);
	        gfs.put("aliases", tempfile.getName());
	        gfs.put("filename", fileName);
	        gfs.put("contentType","application/octet-stream");
	      //mongo文件管理，附加的自定义字段
            gfs.put(Const.ISIMG, Integer.parseInt(params.get(Const.ISIMG).toString()));//是否图片，1是 ，0否
            gfs.put(Const.USE_TYPE, Integer.parseInt(params.get(Const.USE_TYPE).toString()));//使用类别t_base_file_type
            gfs.put(Const.COM_NAME, params.get(Const.COM_NAME));//公司名
            gfs.put(Const.COM_ID, params.get(Const.COM_ID));//公司ID
            gfs.put(Const.USERNAME, params.get(Const.USERNAME));//上传者
	        gfs.save();
	        logger.debug("mongodb SaveFile:"+fileName);
		} catch (Exception e) {
        	throw new BusinessException(e,"mongodb在fs保存"+fileName+"失败");
        }
		return fileName;
	}
	
	public VideoFileBean addTaskVideoFile(byte[] buff,String originalFilename,String contentType,Map<String,Object> params) throws IOException{
		/*String fileName = SRMStringUtil.getUUID();//文件名生成器
*/		//1.字节转化为临时文件
		//重命名，以防重名
		FFMpeg ffmpeg =FFMpeg.getInstance();
		String serialName = SRMStringUtil.getUUID();
		String temppath = ffmpeg.getOriginvideo_temp_path();
		OutputStream outputStream = null;
		File tempVideoFile = new File(temppath+serialName+"."+contentType);
		tempVideoFile.setReadable(true,false);
		tempVideoFile.setWritable(true, false);
		File file = new File(tempVideoFile.getParent());
		if(!file.exists()){
        	file.mkdirs();
          }
		outputStream = new FileOutputStream(tempVideoFile);
		if(buff!=null){
			outputStream.write(buff);
        	outputStream.flush();
        	outputStream.close();
		}
		VideoFileBean fileBean = this.SaveVideoFile(tempVideoFile,params);
		tempVideoFile.delete();
		return fileBean;
	}
	
	public VideoFileBean SaveVideoFile(File tempVideoFile,Map<String,Object> params){
		//2.开始视频转码,通过FFMpeg工具
		String originFileUri=tempVideoFile.getAbsolutePath();
		//视频截图
		String imgFilePath=FFMpegUtil.makeScreenCut(originFileUri);
		File imgFile=new File(imgFilePath);
        if(!imgFile.exists()){
        	throw new BusinessException("图片保存失败");
        }
        //视频转码===>FLV格式
        String videoFilePath=FFMpegUtil.videoTransfer(originFileUri);
        File videoFile=new File(videoFilePath);
        if(!videoFile.exists()){
        	throw new BusinessException("视频保存失败");
        }
        //3.将视频截图和视频文件保存至MongoDB中
		VideoFileBean fileBean = new VideoFileBean();
		int a = 1;
		int b = 35;
		params.put(Const.ISIMG,a);
		params.put(Const.USE_TYPE,b);
		fileBean.setImage_file(this.SaveFile(imgFile,params));
		a = 0;
		b = 37;
		params.put(Const.ISIMG,a);
		params.put(Const.USE_TYPE,b);
		String videoname=this.SaveFile(videoFile,params);
        fileBean.setVideo_file(videoname);
        logger.debug("mongodb SaveVideoFile:"+videoname);
        imgFile.delete();
        videoFile.delete();
		return fileBean;
	}
	
	public VideoFileBean SaveVideoFile(File tempVideoFile){
		//2.开始视频转码,通过FFMpeg工具
        String originFileUri=tempVideoFile.getAbsolutePath();
        //视频截图
        String imgFilePath=FFMpegUtil.makeScreenCut(originFileUri);
        File imgFile=new File(imgFilePath);
        if(!imgFile.exists()){
        	throw new BusinessException("图片保存失败");
        }
        //视频转码===>FLV格式
        String videoFilePath=FFMpegUtil.videoTransfer(originFileUri);
        File videoFile=new File(videoFilePath);
        if(!videoFile.exists()){
        	throw new BusinessException("图片保存失败");
        }
        
        //3.将视频截图和视频文件保存至MongoDB中
        VideoFileBean fileBean = new VideoFileBean();     
        fileBean.setImage_file(this.SaveFile(imgFile));
        String videoname=this.SaveFile(videoFile);
        fileBean.setVideo_file(videoname);
        //logger.debug("mongodb SaveVideoFile:"+videoname);
        //删除文件
        imgFile.delete();
        videoFile.delete();
		return fileBean;
	}
}
