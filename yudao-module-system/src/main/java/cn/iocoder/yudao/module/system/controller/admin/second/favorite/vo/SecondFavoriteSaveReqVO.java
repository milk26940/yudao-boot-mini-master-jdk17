package cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SecondFavoriteSaveReqVO {

    private Long id;

    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @NotNull(message = "商品编号不能为空")
    private Long goodsId;

}
