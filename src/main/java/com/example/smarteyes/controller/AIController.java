package com.example.smarteyes.controller;

import com.example.smarteyes.common.Result;
import com.example.smarteyes.common.util.CSVUtils;
import com.example.smarteyes.common.util.V4OkHttpClientTest;
import com.example.smarteyes.domain.dto.DataVo;
import com.example.smarteyes.mapper.DataMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.core.httpclient.ApacheHttpClientTransport;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.AbstractDocument;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 黄一峰
 * @date 2024/5/6 19:16
 * @description AIController
 */
@RestController
@RequestMapping("/ai")
public class AIController {
    @Resource
    private V4OkHttpClientTest v4OkHttpClientTest;

    @Autowired
    public DataMapper dataMapper;
    @PostMapping("/answer")
    public Result getAnswer(@RequestPart("file") MultipartFile file) {
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
            String response = "如下故障分析日志中，可能存在哪些系统问题以及该如何解决避免这些问题: \n" +  lists.toString();
            ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
            PrintStream cacheStream = new PrintStream(baoStream);//临时输出
            PrintStream oldStream = System.out;//缓存系统输出
            System.setOut(cacheStream);
            System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
            V4OkHttpClientTest.testInvoke(response);
            String result = baoStream.toString();
            System.setOut(oldStream);
            System.out.println(result);
            // 截取 JSON 数据部分
            String json = result.substring(result.indexOf('{')); // 从第一个 { 开始截取

            // 使用 Gson 解析 JSON 字符串
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

            // 获取 "content" 字段的内容
            JsonArray choices = jsonObject.getAsJsonObject("data").getAsJsonArray("choices");
            JsonObject firstChoice = choices.get(0).getAsJsonObject();
            JsonObject message = firstChoice.getAsJsonObject("message");
            String content = message.get("content").getAsString();
            // 输出 content 的内容
            System.out.println("Content 的内容为：" + content);
            return Result.success(content);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error();
        }
    }
}
