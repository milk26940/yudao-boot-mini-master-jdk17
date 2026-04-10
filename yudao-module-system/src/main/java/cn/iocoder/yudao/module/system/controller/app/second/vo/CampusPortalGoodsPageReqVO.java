package cn.iocoder.yudao.module.system.controller.app.second.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "用户 App - 商品分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CampusPortalGoodsPageReqVO extends PageParam {

    @Schema(description = "分类编号", example = "1")
    private Long categoryId;

    @Schema(description = "关键字", example = "平板")
    private String keyword;

}
