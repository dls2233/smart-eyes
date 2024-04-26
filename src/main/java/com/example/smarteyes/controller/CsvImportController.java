package com.example.smarteyes.controller;

import com.example.smarteyes.common.Result;
import com.example.smarteyes.common.util.CSVUtils;
import com.example.smarteyes.domain.dto.DataVo;
import com.example.smarteyes.mapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 黄一峰
 * @date 2024/4/25 21:04
 * @description csv导入
 */
@RestController
@RequestMapping("/csv")
public class CsvImportController {

    @Autowired
    public DataMapper dataMapper;
    @PostMapping("/import")
    public Result csvImport(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            File csvFile = CSVUtils.uploadFile(file);
            List<DataVo> lists = Objects.requireNonNull(CSVUtils.readCSV(csvFile.getPath(), 3))
                    .stream()
                    .map(list  -> {
                        String[] strs = list.toString().replace("[", "").replace("]","").split(",");
                        DataVo dataVo = new DataVo();
                        dataVo.setTimestamp(Integer.valueOf(strs[0]));
                        dataVo.setMessage(strs[1]);
                        dataVo.setService(strs[2]);
                        return dataVo;
                    }).collect(Collectors.toList());
            //遍历查看
            lists.stream().forEach(dataVo -> System.out.println("data:" + dataVo.toString()));
            for(DataVo dataVo : lists){
                dataMapper.insertDatas(dataVo.getTimestamp(), dataVo.getMessage(), dataVo.getService());
            }
            return Result.success("导入成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("导入失败");
        }
    }

}
