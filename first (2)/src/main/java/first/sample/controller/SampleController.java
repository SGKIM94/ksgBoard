package first.sample.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import first.common.common.CommandMap;
import first.sample.service.SampleService;
import first.sample.spring.UserInfo;

@RequestMapping("/*")
@Controller
public class SampleController {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "sampleService")
	private SampleService sampleService;

	@RequestMapping(value = "/openBoardList.do")
	public ModelAndView openBoardList(CommandMap commandMap, HttpSession session) {
		ModelAndView mv = null;

		try {
			if (session.getAttribute("userInfo") != null)
				mv = new ModelAndView("/sample/boardList");
			else
				mv = new ModelAndView("/user/userLogin");

			Map<String, Object> resultMap = sampleService.selectBoardList(commandMap.getMap());
			mv.addObject("list", resultMap.get("result"));
			mv.addObject("paginationInfo", (PaginationInfo) resultMap.get("paginationInfo"));
		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/testMapArgumentResolver.do")
	public ModelAndView testMapArgumentResolver(CommandMap commandMap) {
		ModelAndView mv = new ModelAndView("");

		try {
			if (commandMap.isEmpty() == false) {
				Iterator<Entry<String, Object>> iterator = commandMap.getMap().entrySet().iterator();
				Entry<String, Object> entry = null;
				while (iterator.hasNext()) {
					entry = iterator.next();
					log.debug("key : " + entry.getKey() + ", value : " + entry.getValue());
				}
			}
		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/openBoardWrite.do")
	public ModelAndView openBoardWrite(CommandMap commandMap) {
		ModelAndView mv = null;
		try {
			mv = new ModelAndView("/sample/boardWrite");

		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}

		return mv;
	}

	@RequestMapping(value = "/insertBoard.do")
	public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request, HttpSession session) {
		ModelAndView mv = null;
		try {
			mv = new ModelAndView("redirect:/openBoardList.do");
			UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
			commandMap.put("CREA_ID", userInfo.getUid());

			sampleService.insertBoard(commandMap.getMap(), request);

		} catch (Exception e) {
			mv = new ModelAndView("/error/file_error");
			return mv;
		}

		return mv;
	}

	@RequestMapping(value = "/openBoardDetail.do")
	public ModelAndView openBoardDetail(CommandMap commandMap, HttpSession session) {
		ModelAndView mv = null;

		try {
			mv = new ModelAndView("/sample/boardDetail");
			UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

			Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
//      게시판 데이터 가져오기  
			mv.addObject("map", map.get("map"));
//      파일리스트 가져오기
			mv.addObject("list", map.get("list"));
//      Comet 값 가져오기 
			mv.addObject("comment", map.get("comment"));
//		유저 값 가져오기        
			mv.addObject("userInfo", userInfo);

		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/openBoardUpdate.do")
	public ModelAndView openBoardUpdate(CommandMap commandMap) {
		ModelAndView mv = null;

		try {
			mv = new ModelAndView("/sample/boardUpdate");

			Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
			mv.addObject("map", map.get("map"));
			mv.addObject("list", map.get("list"));
			mv.addObject("comment", map.get("comment"));

		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/updateBoard.do")
	public ModelAndView updateBoard(CommandMap commandMap, HttpServletRequest request) {
		ModelAndView mv = null;

		try {
			mv = new ModelAndView("redirect:/openBoardDetail.do");
			System.out.println(commandMap.get("CREA_ID"));

			sampleService.updateBoard(commandMap.getMap(), request);

			mv.addObject("IDX", commandMap.get("IDX"));
		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}

		return mv;
	}

	@RequestMapping(value = "/deleteBoard.do")
	public ModelAndView deleteBoard(CommandMap commandMap) {
		ModelAndView mv = null;

		try {
			mv = new ModelAndView("redirect:/openBoardList.do");

			sampleService.deleteBoard(commandMap.getMap());

		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}

		return mv;
	}

//    댓글 달기 위한 컨트롤
	@RequestMapping(value = "/writeComment.do")
	public ModelAndView writeComment(CommandMap commandMap) {
		ModelAndView mv = null;

		try {
			mv = new ModelAndView("redirect:/openBoardDetail.do");
			sampleService.writeComment(commandMap.getMap());

			mv.addObject("IDX", commandMap.get("IDX"));

		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}

		return mv;
	}

	@RequestMapping(value = "/deleteComment.do")
	public ModelAndView deleteComment(CommandMap commandMap) {
		ModelAndView mv = null;

		try {
			mv = new ModelAndView("redirect:/openBoardDetail.do");
			sampleService.deleteComment(commandMap.getMap());

			mv.addObject("IDX", commandMap.get("IDX"));
		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}

		return mv;
	}

	@RequestMapping(value = "/updateComment.do")
	public ModelAndView updateComment(CommandMap commandMap) {

		ModelAndView mv = null;

		try {
			mv = new ModelAndView("redirect:/openBoardDetail.do");

			sampleService.updateComment(commandMap.getMap());
			mv.addObject("IDX", commandMap.get("IDX"));

		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}
		return mv;
	}

//   회원가입을 위한 컨트롤 
	@RequestMapping(value = "/openUserJoin.do")
	public ModelAndView openUserJoin(CommandMap commandMap) {
		ModelAndView mv = null;

		try {
			mv = new ModelAndView("/user/userJoin");

			mv.addObject("error", commandMap.get("error"));
		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}

		return mv;
	}

	@RequestMapping(value = "/joinUser.do")
	public ModelAndView joinUser(CommandMap commandMap) {

		ModelAndView mv = null;

		try {
			String error = sampleService.joinUser(commandMap.getMap());

			if (error.equals("COMPLETE")) {
				mv = new ModelAndView("redirect:/openBoardList.do");
			} else {
				mv = new ModelAndView("redirect:/openUserJoin.do");
				mv.addObject("error", error);
			}
		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/openUserLogin.do")
	public ModelAndView openUserlogin(CommandMap commandMap) {
		ModelAndView mv = null;

		try {
			mv = new ModelAndView("/user/userLogin");

			mv.addObject("error", commandMap.get("error"));

		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}
		return mv;
	}

	@RequestMapping(value = "/loginUser.do")
	public ModelAndView loginUser(CommandMap commandMap, HttpSession session) {

		ModelAndView mv = null;

		try {
			UserInfo userInfo = sampleService.loginUser(commandMap.getMap());

			if (!userInfo.isError()) {
				session.setAttribute("userInfo", userInfo);
				mv = new ModelAndView("redirect:/openBoardList.do");
			} else {
				mv = new ModelAndView("redirect:/openUserLogin.do");
				mv.addObject("error", userInfo.isError());
			}
		} catch (Exception e) {
			mv = new ModelAndView("/error/login_error");
			return mv;
		}

		return mv;
	}

	@RequestMapping(value = "/logoutUser.do")
	public ModelAndView logoutUser(CommandMap commandMap, HttpSession session) {
		ModelAndView mv = null;

		try {
			mv = new ModelAndView("/user/userLogin");
			

		} catch (Exception e) {
			mv = new ModelAndView("/error/any_error");
			return mv;
		}
		session.invalidate();
		return mv;
	}
	
	
}