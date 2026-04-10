package cn.iocoder.yudao.module.system.controller.admin.second.goods.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 二手商品新增/修改 Request VO")
@Data
public class SecondGoodsSaveReqVO {

    @Schema(description = "商品编号", example = "1")
    private Long id;

    @Schema(description = "卖家用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "卖家用户编号不能为空")
    private Long sellerId;

    @Schema(description = "分类编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "分类编号不能为空")
    private Long categoryId;

    @Schema(description = "商品标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "九成新高等数学教材")
    @NotBlank(message = "商品标题不能为空")
    @Size(max = 100, message = "商品标题长度不能超过 100 个字符")
    private String title;

    @Schema(description = "品牌", example = "Apple")
    @Size(max = 50, message = "品牌长度不能超过 50 个字符")
    private String brand;

    @Schema(description = "商品描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "书本无明显划线，附赠习题册")
    @NotBlank(message = "商品描述不能为空")
    private String description;

    @Schema(description = "售价", requiredMode = Schema.RequiredMode.REQUIRED, example = "35.00")
    @NotNull(message = "售价不能为空")
    @DecimalMin(value = "0.00", message = "售价不能小于 0")
    private BigDecimal price;

    @Schema(description = "原价", example = "59.80")
    @DecimalMin(value = "0.00", message = "原价不能小于 0")
    private BigDecimal originalPrice;

    @Schema(description = "新旧程度", requiredMode = Schema.RequiredMode.REQUIRED, example = "8")
    @NotNull(message = "新旧程度不能为空")
    private Integer conditionLevel;

    @Schema(description = "联系电话", example = "13800000001")
    @Size(max = 20, message = "联系电话长度不能超过 20 个字符")
    private String contactMobile;

    @Schema(description = "联系 QQ", example = "123456")
    @Size(max = 20, message = "联系 QQ 长度不能超过 20 个字符")
    private String contactQq;

    @Schema(description = "联系微信", example = "wechat001")
    @Size(max = 50, message = "联系微信长度不能超过 50 个字符")
    private String contactWechat;

    @Schema(description = "商品状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "商品状态不能为空")
    private Integer status;

    @Schema(description = "发布时间", example = "2026-04-10 17:30:00")
    private LocalDateTime publishTime;

}
