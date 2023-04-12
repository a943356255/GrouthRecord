package com.example.springboot_vue.file_operate.service.impl;

import com.example.springboot_vue.file_operate.mapper.FileMapper;
import com.example.springboot_vue.file_operate.pojo.FileInfo;
import com.example.springboot_vue.file_operate.service.ChunkService;
import com.example.springboot_vue.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChunkServiceImpl implements ChunkService {

    @Autowired
    FileMapper fileMapper;

    @Override
    public void insert(String filename, String filePath) {

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilename(filename);
        fileInfo.setLocation(filePath);
        fileInfo.setTime(Utils.getTime());
        fileInfo.setType(Utils.getType(filename));

        fileMapper.insertFile(fileInfo);
    }
}
