package com.example.springboot_vue.file_operate.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileInfo implements Serializable {

    private int id;

    private String filename;

    private String identifier;

    private Long totalSize;

    private String type;

    private String location;

    private String time;
}
