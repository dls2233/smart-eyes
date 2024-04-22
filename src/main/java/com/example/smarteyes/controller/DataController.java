package com.example.smarteyes.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.smarteyes.common.RedisCache;
import com.example.smarteyes.common.Result;
import com.example.smarteyes.domain.dto.resultDto;
import com.example.smarteyes.mapper.DataMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 黄一峰
 * @date 2024/4/16 14:32
 * @description DataController
 */
@RestController
@CrossOrigin //所有域名均可访问该类下所有接口
@RequestMapping("/data")
public class DataController {
    @Autowired
    public DataMapper dataMapper;

    @Autowired
    public RedisCache redisCache;

    @Autowired
    public RedisTemplate redisTemplate;
    @GetMapping("/get")
    public Result dataGet(){
        List<resultDto> dataList = dataMapper.getData()
                .stream()
                .map(result -> {
                    resultDto resultDto = new resultDto();
                    resultDto.setId(result.getId());
                    resultDto.setSource(result.getSource());
                    resultDto.setScore(Double.parseDouble(result.getScore()));
                    return resultDto;
                }).collect(Collectors.toList());
        Map<String, Double> map = new HashMap<>();
        for(resultDto resultDto : dataList){
            map.put(resultDto.getSource(), map.getOrDefault(resultDto.getSource(),0.0)+resultDto.getScore());
        }
        System.out.println(map);
        return Result.success(map);
    }

    @GetMapping("/get1")
    public Result dataGet1(){
        //redis记录轮询次数
        if(redisCache.getCacheObject("num") == null){
            redisCache.setCacheObject("num", 1);
            String id = dataMapper.selectId(1);
            List<String> ids = new ArrayList<>();
            ids.add(id);
            redisCache.setCacheList("already", ids);
            List<resultDto> list = dataMapper.selectall(id);
            return Result.success(list);
        }else{
            int num = redisCache.getCacheObject("num");
            List<String> already = redisCache.getCacheList("already");
            while(true){
                String str = dataMapper.selectId(num);
                if(already.contains(str)){
                    num++;
                    redisCache.setCacheObject("num", num);
                }else{
                    already.add(str);
                    redisCache.setCacheList("already", already);
                    return Result.success(dataMapper.selectall(str));
                }
            }
        }
    }
    @GetMapping("/get2")
    public Result dataGet2(){
        List<resultDto> dataList = dataMapper.getData()
                .stream()
                .map(result -> {
                    resultDto resultDto = new resultDto();
                    resultDto.setId(result.getId());
                    resultDto.setSource(result.getSource());
                    resultDto.setScore(Double.parseDouble(result.getScore()));
                    return resultDto;
                }).toList();
        Map<String, Double> map = new HashMap<>();
        for(resultDto resultDto : dataList){
            map.put(resultDto.getId(),0.0);
            if(resultDto.getScore().equals("NO_FAULT")){
                map.put(resultDto.getId(), map.get(resultDto.getId()) + resultDto.getScore());
            }
        }
        System.out.println(map);
        return Result.success("操作成功",dataList);
    }
}
