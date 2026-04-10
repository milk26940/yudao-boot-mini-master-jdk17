package cn.iocoder.yudao.module.system.controller.app.second.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 App - 商品分类 Response VO")
@Data
public class CampusPortalCategoryRespVO {

    @Schema(description = "分类编号", example = "1")
    private Long id;

    @Schema(description = "分类名称", example = "教材书籍")
    private String name;

    @Schema(description = "排序", example = "1")
    private Integer sort;

}
