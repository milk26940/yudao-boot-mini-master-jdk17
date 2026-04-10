package cn.iocoder.yudao.module.system.controller.app.second.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "用户 App - 首页 Response VO")
@Data
public class CampusPortalHomeRespVO {

    @Schema(description = "已上架商品数", example = "3")
    private Long onSaleGoodsCount;

    @Schema(description = "活跃求购数", example = "2")
    private Long activeWantedCount;

    @Schema(description = "审核通过用户数", example = "2")
    private Long approvedUserCount;

    @Schema(description = "分类列表")
    private List<CampusPortalCategoryRespVO> categories;

    @Schema(description = "最新商品列表")
    private List<CampusPortalGoodsRespVO> latestGoods;

}
