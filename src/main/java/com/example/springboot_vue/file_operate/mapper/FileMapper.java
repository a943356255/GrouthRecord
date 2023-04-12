package com.example.springboot_vue.file_operate.mapper;

import com.example.springboot_vue.file_operate.pojo.FileInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMapper {

    int insertFile(FileInfo fileInfo);

}
