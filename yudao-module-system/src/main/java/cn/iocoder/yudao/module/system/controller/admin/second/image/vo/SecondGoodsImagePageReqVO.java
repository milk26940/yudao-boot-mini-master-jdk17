package cn.iocoder.yudao.module.system.controller.admin.second.image.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SecondGoodsImagePageReqVO extends PageParam {

    @NotNull(message = "商品编号不能为空")
    private Long goodsId;

}
