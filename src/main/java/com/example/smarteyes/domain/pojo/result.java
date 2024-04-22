package com.example.smarteyes.domain.pojo;

import lombok.Data;

/**
 * @author 黄一峰
 * @date 2024/4/16 14:39
 * @description result
 */
@Data
public class result {
    private String id;

    private String source;

    private String score;

    @Override
    public String toString() {
        return "result{" +
                "id='" + id + '\'' +
                ", source='" + source + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
