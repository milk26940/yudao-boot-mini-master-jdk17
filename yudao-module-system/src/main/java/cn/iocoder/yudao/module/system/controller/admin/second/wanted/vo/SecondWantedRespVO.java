package cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 求购信息 Response VO")
@Data
public class SecondWantedRespVO {

    private Long id;
    private Long userId;
    private String title;
    private String brand;
    private String model;
    private BigDecimal expectedPrice;
    private String description;
    private String contactMobile;
    private String contactQq;
    private String contactWechat;
    private Integer status;
    private LocalDateTime createTime;

}
