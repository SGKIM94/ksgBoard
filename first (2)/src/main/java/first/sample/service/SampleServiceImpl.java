package first.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import first.common.util.FileUtils;
import first.sample.dao.SampleDAO;
import first.sample.spring.UserInfo;

@Service("sampleService")
public class SampleServiceImpl implements SampleService {
	Logger log = Logger.getLogger(this.getClass());
	
	// passowrd Encoding
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Resource(name = "fileUtils")
	private FileUtils fileUtils;

	@Resource(name = "sampleDAO")
	private SampleDAO sampleDAO;

	@Override
	public Map<String, Object> selectBoardList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardList(map);
	}

	@Override
	public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		sampleDAO.insertBoard(map);
		
		
		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for (int i = 0, size = list.size(); i < size; i++) {
			sampleDAO.insertFile(list.get(i));
		}
	}

	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		sampleDAO.updateHitCnt(map);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> tempMap = sampleDAO.selectBoardDetail(map);

		resultMap.put("map", tempMap);

		// Get File List
		List<Map<String, Object>> list = sampleDAO.selectFileList(map);
		resultMap.put("list", list);

		List<Map<String, Object>> comment = sampleDAO.selectCommentList(map);
		resultMap.put("comment", comment);

		return resultMap;
	}

	@Override
	public void updateBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		sampleDAO.updateBoard(map);

		sampleDAO.deleteFileList(map);
		List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String, Object> tempMap = null;
		for (int i = 0, size = list.size(); i < size; i++) {
			tempMap = list.get(i);
			if (tempMap.get("IS_NEW").equals("Y")) {
				sampleDAO.insertFile(tempMap);
			} else {
				sampleDAO.updateFile(tempMap);
			}
		}
	}

	@Override
	public List<Map<String, Object>> selectCommentList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectCommentList(map);
	}

	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		sampleDAO.deleteBoard(map);
	}

	@Override
	public void writeComment(Map<String, Object> map) throws Exception {
		sampleDAO.insertComment(map);
	}

	@Override
	public void deleteComment(Map<String, Object> map) throws Exception {
		sampleDAO.deleteComment(map);
	}

	@Override
	public void updateComment(Map<String, Object> map) throws Exception {
		sampleDAO.updateComment(map);
	}

	@Override
	public String joinUser(Map<String, Object> map) throws Exception {
		if (!(sampleDAO.selectUserID(map) == null)) {
			return "ID";
		} else if (!(sampleDAO.selectUserEmail(map) == null)) {
			return "EMAIL";
		} else if (!(sampleDAO.selectUserNickname(map) == null)) {
			return "NAME";
		} else {			
			
			String encPassword = passwordEncoder.encode((String)map.get("UPW"));
			
			System.out.println("encPassword = " + encPassword);
			map.put("UPW", encPassword);
			sampleDAO.insertUser(map);
			return "COMPLETE";
		}
	}

	@Override
	public UserInfo loginUser(Map<String, Object> map) throws Exception {
		if (map == null) {
			System.out.println("NULL");
		} else {
			System.out.println("NOT NULL");
		}
		
		Map<String, Object> tempMap =  sampleDAO.selectUserID(map);
						
		UserInfo userInfo = null;
	
		
		if (passwordEncoder.matches(map.get("UPW").toString(), tempMap.get("UPW").toString())) {
			userInfo = new UserInfo(tempMap.get("UID").toString(), Integer.parseInt(tempMap.get("IDX").toString()),
					tempMap.get("UNICK").toString());
			
			userInfo.setError(false);
			return userInfo;
		} else {
			userInfo = new UserInfo(true);
			return userInfo;
		}
	}
}
