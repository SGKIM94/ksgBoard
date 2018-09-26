package first.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

// �씠 媛앹껜�쓽 愿�由щ�� �뒪�봽留곸씠 �븯�룄濡� �븿 
@Component("fileUtils")
public class FileUtils {
   private static final String filePath = "/Users/paymint/Workspace/";
    
   public List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map, HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
   	Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
   	
   	MultipartFile multipartFile = null;
   	String originalFileName = null;
   	String originalFileExtension = null;
   	String storedFileName = null;
   	
   	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
       Map<String, Object> listMap = null; 
          
       
       String crea_id = (String)map.get("CREA_ID");
       String boardIdx = String.valueOf(map.get("IDX"));
       
       File file = new File(filePath);
       if(file.exists() == false){
       	file.mkdirs();
       }
       
       while(iterator.hasNext()){
       	multipartFile = multipartHttpServletRequest.getFile(iterator.next());
       	if(multipartFile.isEmpty() == false){
       		originalFileName = multipartFile.getOriginalFilename();
       		originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
       		storedFileName = CommonUtils.getRandomString() + originalFileExtension;
       		
       		file = new File(filePath + storedFileName);
       		multipartFile.transferTo(file);
       		
       		listMap = new HashMap<String,Object>();
       		listMap.put("CREA_ID", crea_id);
       		listMap.put("BOARD_IDX", boardIdx);
       		listMap.put("ORIGINAL_FILE_NAME", originalFileName);
       		listMap.put("STORED_FILE_NAME", storedFileName);
       		listMap.put("FILE_SIZE", multipartFile.getSize());
       		list.add(listMap);
       	}
       }
		return list;
	}
   
   public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
	    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
	    Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
	     
	    MultipartFile multipartFile = null;
	    String originalFileName = null;
	    String originalFileExtension = null;
	    String storedFileName = null;
	     
	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    Map<String, Object> listMap = null;
	    
	    String crea_id = (String)map.get("CREA_ID");
	    String boardIdx = String.valueOf(map.get("IDX"));
	    String requestName = null;
	    String idx = null;
	     
	     
	    while(iterator.hasNext()){
	        multipartFile = multipartHttpServletRequest.getFile(iterator.next());
	        if(multipartFile.isEmpty() == false){
	            originalFileName = multipartFile.getOriginalFilename();
	            originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
	            storedFileName = CommonUtils.getRandomString() + originalFileExtension;
	             
	            multipartFile.transferTo(new File(filePath + storedFileName));
	             
	            listMap = new HashMap<String,Object>();
	            listMap.put("CREA_ID", crea_id);
	            listMap.put("IS_NEW", "Y");
	            listMap.put("BOARD_IDX", boardIdx);
	            listMap.put("ORIGINAL_FILE_NAME", originalFileName);
	            listMap.put("STORED_FILE_NAME", storedFileName);
	            listMap.put("FILE_SIZE", multipartFile.getSize());
	            list.add(listMap);
	        }
	        else{
	        	// html �뿉�꽌 name �쓽 媛믪쓣 �꽆寃⑥＜�뒗 寃껋쓣 諛쏆쓬.
	            requestName = multipartFile.getName();
	            idx = "IDX_"+requestName.substring(requestName.indexOf("_")+1);
	            if(map.containsKey(idx) == true && map.get(idx) != null){
	                listMap = new HashMap<String,Object>();
	                listMap.put("IS_NEW", "N");
	                listMap.put("FILE_IDX", map.get(idx));
	                list.add(listMap);
	            }
	        }
	    }
	    return list;
	}

}
