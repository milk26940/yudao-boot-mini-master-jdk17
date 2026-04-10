package cn.iocoder.yudao.module.system.controller.admin.second.image.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SecondGoodsImageRespVO {

    private Long id;
    private Long goodsId;
    private String imageUrl;
    private Integer sort;
    private LocalDateTime createTime;

}
