package com.mashiro.dto;

import lombok.Data;

@Data
public class SuperText2ImgRequest {
    private SuperDrawDto drawDto;
    private Integer imageSize;
    private Integer checkpoint;
    private Integer sampler;
    private Integer scheduler;


    @Override
    public String toString() {
        return "SuperText2ImgRequest{" +
                "drawDto=" + drawDto +
                ", imageSize=" + imageSize +
                ", checkpoint=" + checkpoint +
                ", sampler=" + sampler +
                ", scheduler=" + scheduler +
                '}';
    }
}
