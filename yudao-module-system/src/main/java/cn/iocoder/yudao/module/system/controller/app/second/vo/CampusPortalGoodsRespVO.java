package cn.iocoder.yudao.module.system.controller.app.second.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "用户 App - 商品信息 Response VO")
@Data
public class CampusPortalGoodsRespVO {

    @Schema(description = "商品编号", example = "1")
    private Long id;

    @Schema(description = "卖家编号", example = "1")
    private Long sellerId;

    @Schema(description = "卖家姓名", example = "张三")
    private String sellerName;

    @Schema(description = "分类编号", example = "1")
    private Long categoryId;

    @Schema(description = "分类名称", example = "教材书籍")
    private String categoryName;

    @Schema(description = "商品标题", example = "高等数学教材")
    private String title;

    @Schema(description = "品牌", example = "同济大学出版社")
    private String brand;

    @Schema(description = "商品描述")
    private String description;

    @Schema(description = "售价", example = "25.00")
    private BigDecimal price;

    @Schema(description = "原价", example = "58.00")
    private BigDecimal originalPrice;

    @Schema(description = "新旧程度", example = "2")
    private Integer conditionLevel;

    @Schema(description = "联系电话", example = "13800000001")
    private String contactMobile;

    @Schema(description = "联系 QQ", example = "10001")
    private String contactQq;

    @Schema(description = "联系微信", example = "zhangsan_wechat")
    private String contactWechat;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "浏览量", example = "36")
    private Integer viewCount;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "图片地址列表")
    private List<String> imageUrls;

}
