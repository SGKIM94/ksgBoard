package first.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class AbstractDAO {
    protected Log log = LogFactory.getLog(AbstractDAO.class);
     
    @Autowired
    private SqlSessionTemplate sqlSession;
     
    protected void printQueryId(String queryId) {
        if(log.isDebugEnabled()){
            log.debug("\t QueryId  \t:  " + queryId);
        }
    }
     
    public Object insert(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.insert(queryId, params);
    }
     
    public Object update(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.update(queryId, params);
    }
     
    public Object delete(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.delete(queryId, params);
    }
     
    public Object selectOne(String queryId){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId);
    }
     
    public Object selectOne(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId, params);
    }
     
    @SuppressWarnings("rawtypes")
    public List selectList(String queryId){
        printQueryId(queryId);
        return sqlSession.selectList(queryId);
    }
     
    @SuppressWarnings("rawtypes")
    public List selectList(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectList(queryId,params);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map selectPagingList(String queryId, Object params){
        printQueryId(queryId);
         
        Map<String,Object> map = (Map<String,Object>)params;
        PaginationInfo paginationInfo = null;
         
        if(map.containsKey("currentPageNo") == false || StringUtils.isEmpty(map.get("currentPageNo")) == true)
            map.put("currentPageNo","1");
         
        paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(Integer.parseInt(map.get("currentPageNo").toString()));
        
        if(map.containsKey("PAGE_ROW") == false || StringUtils.isEmpty(map.get("PAGE_ROW")) == true){
            paginationInfo.setRecordCountPerPage(15);
        }
        else{
            paginationInfo.setRecordCountPerPage(Integer.parseInt(map.get("PAGE_ROW").toString()));
        }
        paginationInfo.setPageSize(10);
         
        
        
        // 현재 페이지 -1과 end를곱하면 offset이 나옴.1페이지 ->0, 2페이지 -> 15, 3페이지 -> 30 ... 
        
        int end = 15;
        map.put("LIMIT",  end);
        map.put("OFFSET",  (paginationInfo.getCurrentPageNo() - 1) * end);
        
        params = map;
         
        Map<String,Object> returnMap = new HashMap<String,Object>();
        List<Map<String,Object>> list = sqlSession.selectList(queryId,params);
         
        if(list.size() == 0){
            // 값 초기화
        	map.clear();
        	
            map.put("TOTAL_COUNT",0); 
            list.add(map);
             
            if(paginationInfo != null){
                paginationInfo.setTotalRecordCount(0);
                returnMap.put("paginationInfo", paginationInfo);
            }
        }else{
        	if(paginationInfo != null) {
        		paginationInfo.setTotalRecordCount(Integer.parseInt(list.get(0).get("TOTAL_COUNT").toString()));

        		returnMap.put("paginationInfo", paginationInfo);
        	}
        }
        returnMap.put("result", list);
        return returnMap;
    }
}
