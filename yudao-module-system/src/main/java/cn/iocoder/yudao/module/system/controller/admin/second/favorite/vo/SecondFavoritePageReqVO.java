package cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SecondFavoritePageReqVO extends PageParam {

    private Long userId;
    private Long goodsId;

}
