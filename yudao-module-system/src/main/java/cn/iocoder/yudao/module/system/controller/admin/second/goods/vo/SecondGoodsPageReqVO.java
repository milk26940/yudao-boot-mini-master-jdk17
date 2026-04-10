package cn.iocoder.yudao.module.system.controller.admin.second.goods.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 二手商品分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondGoodsPageReqVO extends PageParam {

    @Schema(description = "卖家用户编号", example = "1")
    private Long sellerId;

    @Schema(description = "分类编号", example = "2")
    private Long categoryId;

    @Schema(description = "商品标题", example = "教材")
    private String title;

    @Schema(description = "商品状态", example = "1")
    private Integer status;

}
