package org.zerock.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.domain.vo.BoardVO;
import org.zerock.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("list......" + cri);
		model.addAttribute("list", boardService.getList(cri));
		model.addAttribute("pageMaker", new PageDTO(cri, boardService.getTotal(cri)));
	}


	@GetMapping({ "/get", "/modify" })
	@PreAuthorize("isAuthenticated()")
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info(bno);
		model.addAttribute("board", boardService.get(bno));
	}

	@PreAuthorize("principal.username == #board.emp_no")
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rtts) {
		log.info("modify.......................");
		
		log.info("modify......................." + board);
		
		if (boardService.modify(board)) {
			rtts.addFlashAttribute("result", "success");
		}

		return "redirect:/board/list" + cri.getListLink();
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("b_no") Long b_no, @ModelAttribute("cri") Criteria cri, RedirectAttributes rtts) {
		log.info("remove.......................");
		
		boardService.remove(b_no);
		
//		List<BoardAttachVO> attaList 
//		= boardService.getAttachList(bno);
		
//		if (boardService.remove(bno)) {
//			deleteFiles(attaList);
//			rtts.addFlashAttribute("result", "success");
//		}

		return "redirect:/board/list" + cri.getListLink();
	}

	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rtts) {
		log.info("register : " + board);

//		if (board.getAttachList() != null) {
//			board.getAttachList().forEach(attach -> {
//				log.info(attach);
//			});
//		}

		boardService.register(board);
		rtts.addFlashAttribute("result", board.getB_no());
		return "redirect:/board/list";
	}

	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void register() {
		log.info("........register");
	}

	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAttachList(Long bno) {
		return "";
	}
	
//
//	private void deleteFiles(List<BoardAttachVO> attachList) {
//
//		if (attachList == null || attachList.size() == 0) {
//			return;
//		}
//
//		log.info("delete attach files...................");
//
//		attachList.forEach(attach -> {
//			try {
//				String path = "C:\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_"
//						+ attach.getFileName();
//				Path file = Paths.get(path);
//				Files.deleteIfExists(file);
//				if (Files.probeContentType(file).startsWith("image")) {
//					String thumNailPath = "C:\\upload\\" + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_"
//							+ attach.getFileName();
//					Path thumNail = Paths.get(thumNailPath);
//					Files.delete(thumNail);
//				}
//
//			} catch (Exception e) {
//
//			}
//
//		});
//
//	}

}
