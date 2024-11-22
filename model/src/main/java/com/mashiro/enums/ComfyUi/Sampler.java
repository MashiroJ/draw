package com.mashiro.enums.ComfyUi;  

import com.baomidou.mybatisplus.annotation.EnumValue;  
import com.fasterxml.jackson.annotation.JsonValue;  
import com.mashiro.exception.DrawException;  
import com.mashiro.result.ResultCodeEnum;  
import com.mashiro.enums.BaseEnum;  

public enum Sampler implements BaseEnum {  
    EULER(1, "euler"),  
    EULER_CFG_PP(2, "euler_cfg_pp"),  
    EULER_ANCESTRAL(3, "euler_ancestral"),  
    EULER_ANCESTRAL_CFG_PP(4, "euler_ancestral_cfg_pp"),  
    HEUN(5, "heun"),  
    HEUNPP2(6, "heunpp2"),  
    DPM_2(7, "dpm_2"),  
    DPM_2_ANCESTRAL(8, "dpm_2_ancestral"),  
    LMS(9, "lms"),  
    DPM_FAST(10, "dpm_fast"),  
    DPM_ADAPTIVE(11, "dpm_adaptive"),  
    DPMPP_2S_ANCESTRAL(12, "dpmpp_2s_ancestral"),  
    DPMPP_2S_ANCESTRAL_CFG_PP(13, "dpmpp_2s_ancestral_cfg_pp"),  
    DPMPP_SDE(14, "dpmpp_sde"),  
    DPMPP_SDE_GPU(15, "dpmpp_sde_gpu"),  
    DPMPP_2M(16, "dpmpp_2m"),  
    DPMPP_2M_CFG_PP(17, "dpmpp_2m_cfg_pp"),  
    DPMPP_2M_SDE(18, "dpmpp_2m_sde"),  
    DPMPP_2M_SDE_GPU(19, "dpmpp_2m_sde_gpu"),  
    DPMPP_3M_SDE(20, "dpmpp_3m_sde"),  
    DPMPP_3M_SDE_GPU(21, "dpmpp_3m_sde_gpu"),  
    DDPM(22, "ddpm"),  
    LCM(23, "lcm"),  
    IPNDM(24, "ipndm"),  
    IPNDM_V(25, "ipndm_v"),  
    DEIS(26, "deis"),  
    DDIM(27, "ddim"),  
    UNI_PC(28, "uni_pc"),  
    UNI_PC_BH2(29, "uni_pc_bh2");  

    @EnumValue  
    private Integer code;  
    @JsonValue  
    private String name;  

    Sampler(Integer code, String name) {  
        this.code = code;  
        this.name = name;  
    }  

    @Override  
    public Integer getCode() {  
        return this.code;  
    }  

    @Override  
    public String getName() {  
        return this.name;  
    }  

    /**  
     * 根据采样器代码返回对应的Sampler枚举实例  
     * 如果找不到匹配的采样器代码，则抛出参数错误的异常  
     *  
     * @param code 采样器代码，用于识别特定的采样器  
     * @return 对应采样器代码的Sampler枚举实例  
     * @throws DrawException 如果采样器代码不存在，则抛出此异常  
     */  
    public static Sampler fromCode(Integer code) {  
        for (Sampler sampler : values()) {  
            if (sampler.getCode().equals(code)) {  
                return sampler;  
            }  
        }  
        throw new DrawException(ResultCodeEnum.PARAM_ERROR);  
    }  
}