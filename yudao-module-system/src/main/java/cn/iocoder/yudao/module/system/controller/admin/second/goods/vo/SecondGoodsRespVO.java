package cn.iocoder.yudao.module.system.controller.admin.second.goods.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 二手商品 Response VO")
@Data
public class SecondGoodsRespVO {

    @Schema(description = "商品编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "卖家用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long sellerId;

    @Schema(description = "分类编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long categoryId;

    @Schema(description = "商品标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "九成新高等数学教材")
    private String title;

    @Schema(description = "品牌", example = "Apple")
    private String brand;

    @Schema(description = "商品描述", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "35.00")
    private BigDecimal price;

    @Schema(description = "原价", example = "59.80")
    private BigDecimal originalPrice;

    @Schema(description = "新旧程度", requiredMode = Schema.RequiredMode.REQUIRED, example = "8")
    private Integer conditionLevel;

    @Schema(description = "联系电话", example = "13800000001")
    private String contactMobile;

    @Schema(description = "联系 QQ", example = "123456")
    private String contactQq;

    @Schema(description = "联系微信", example = "wechat001")
    private String contactWechat;

    @Schema(description = "商品状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "浏览次数", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer viewCount;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
