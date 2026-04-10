package cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SecondFavoriteRespVO {

    private Long id;
    private Long userId;
    private Long goodsId;
    private LocalDateTime createTime;

}
