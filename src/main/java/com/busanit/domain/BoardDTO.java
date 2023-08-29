package com.busanit.domain;

import com.busanit.entity.BoardAttach;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardDTO {

    private Long bno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    private List<BoardAttach> uploadFiles;

    private int replyCount;     // 해당 게시글의 댓글 수
}
