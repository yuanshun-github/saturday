package com.example.demo.bean;

import lombok.Data;

/**
 * 资料表
 */
@Data
public class ResourceFile {

    private Integer id;

    private String grade;

    private String subject;

    private String keyword;

    private String type;

    private String fileName;

    private Integer downId;

    private Integer fileSize;

    private Integer userId;

    private Integer status;
}
