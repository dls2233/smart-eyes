package com.example.smarteyes.mapper;

import com.example.smarteyes.domain.dto.DataVo;
import com.example.smarteyes.domain.dto.resultDto;
import com.example.smarteyes.domain.pojo.result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;

import java.util.List;

/**
 * @author 黄一峰
 * @date 2024/4/16 15:00
 * @description DataMapper
 */

@Mapper
public interface DataMapper {
    List<result> getData();

    resultDto getData1(Integer uid);

    String selectId(int i);

    List<resultDto> selectall(String id);

    void insertDatas(@Param("timestamp") Integer timestamp, @Param("message") String message, @Param("service") String service);

    List<DataVo> getDataView();
}
