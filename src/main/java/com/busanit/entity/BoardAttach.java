package com.busanit.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class BoardAttach extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ano;

    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
