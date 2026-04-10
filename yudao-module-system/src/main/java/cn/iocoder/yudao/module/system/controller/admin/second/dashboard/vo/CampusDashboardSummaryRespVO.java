package cn.iocoder.yudao.module.system.controller.admin.second.dashboard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 校园二手交易看板摘要 Response VO")
@Data
public class CampusDashboardSummaryRespVO {

    @Schema(description = "用户总数", example = "120")
    private Long userCount;

    @Schema(description = "待审核用户数", example = "8")
    private Long pendingAuditUserCount;

    @Schema(description = "商品总数", example = "86")
    private Long goodsCount;

    @Schema(description = "在售商品数", example = "52")
    private Long onSaleGoodsCount;

    @Schema(description = "求购总数", example = "23")
    private Long wantedCount;

    @Schema(description = "进行中求购数", example = "15")
    private Long activeWantedCount;

    @Schema(description = "评论总数", example = "38")
    private Long commentCount;

    @Schema(description = "订单总数", example = "19")
    private Long orderCount;

    @Schema(description = "待确认订单数", example = "4")
    private Long pendingOrderCount;

    @Schema(description = "已完成订单数", example = "10")
    private Long completedOrderCount;

}
