package com.busanit.controller;

import com.busanit.domain.BoardDTO;
import com.busanit.entity.Board;
import com.busanit.entity.BoardAttach;
import com.busanit.repository.BoardRepository;
import com.busanit.repository.ReplyRepository;
import com.busanit.service.BoardAttachService;
import com.busanit.service.BoardService;
import com.busanit.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
@AllArgsConstructor
@Log4j2
public class BoardController {

    private BoardRepository boardRepository;
    private BoardService boardService;
    private BoardAttachService attachService;

    @GetMapping("/register")
    public String registerForm() {
        return "board/register";
    }

    @PostMapping("/register")
    public String register(BoardDTO boardDto) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setWriter(boardDto.getWriter());
        board.setAttachList(boardDto.getUploadFiles());

        List<BoardAttach> attachList = new ArrayList<>();
        for(BoardAttach attach :board.getAttachList()){
            log.info("board:"+board);
            log.info("fileName :"+ attach.getFileName());
            attach.setBoard(board);
            attach.setFileName(attach.getFileName());
            attachList.add(attach);
        }

//        boardRepository.save(board);

        return "redirect:/board/list";
    }

    @GetMapping("/update")
    public String updateForm(Long bno, Model model) {
        Board board = boardRepository.findByBno(bno);

        model.addAttribute("board", board);
        return "board/update";
    }

    @PostMapping("/update")
    public String update(BoardDTO boardDto) {
        Board board = new Board();
        board.setBno(boardDto.getBno());
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setWriter(boardDto.getWriter());

        boardRepository.save(board);

        return "redirect:/board/view?bno=" + board.getBno();
    }

    @GetMapping("/delete")
    public String delete(Long bno) {
        boardRepository.deleteById(bno);

        return "redirect:/board/list";
    }

//    @GetMapping("/list")
//    public String list(Model model) {
//        List<Board> boardList = boardRepository.findAll();
//        model.addAttribute("list", boardList);
//
//        return "board/list";
//    }

//    @GetMapping("/list")
//    public String list(Model model, String keyword,
//                       @PageableDefault(size = 5, sort = "bno",
//                               direction = Sort.Direction.DESC)Pageable pageable) {
//
//        model.addAttribute("list",
//                boardService.getBoardList(pageable));
//
//        return "board/list";
//    }

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "") String searchType,
                       @RequestParam(defaultValue = "") String keyword,
                       @PageableDefault(size = 5, sort = "bno",
                            direction = Sort.Direction.DESC)Pageable pageable) {

        if (searchType.equals("title")) {
            model.addAttribute("list",
                    boardService.getBoardTitleList(keyword, pageable));
        } else if (searchType.equals("content")) {
            model.addAttribute("list",
                    boardService.getBoardContentList(keyword, pageable));
        } else {
            model.addAttribute("list",
                    boardService.getBoardList(pageable));
        }
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "board/list";
    }

    @GetMapping("/view")
    public String view(Long bno, Model model) {
        Board board = boardRepository.findByBno(bno);

        model.addAttribute("board", board);
//        model.addAttribute("reply", replyService.getList(bno));

        return "board/view";
    }
}







