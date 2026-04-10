package cn.iocoder.yudao.module.system.controller.admin.second.category.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 二手商品分类分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondCategoryPageReqVO extends PageParam {

    @Schema(description = "分类名称", example = "教材")
    private String name;

    @Schema(description = "状态", example = "1")
    private Integer status;

}
