package cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 求购信息新增/修改 Request VO")
@Data
public class SecondWantedSaveReqVO {

    private Long id;

    @NotNull(message = "发布用户编号不能为空")
    private Long userId;

    @NotBlank(message = "求购标题不能为空")
    @Size(max = 100, message = "求购标题长度不能超过 100 个字符")
    private String title;

    @Size(max = 50, message = "品牌长度不能超过 50 个字符")
    private String brand;

    @Size(max = 50, message = "型号长度不能超过 50 个字符")
    private String model;

    @DecimalMin(value = "0.00", message = "期望价格不能小于 0")
    private BigDecimal expectedPrice;

    @NotBlank(message = "需求描述不能为空")
    private String description;

    @Size(max = 20, message = "联系电话长度不能超过 20 个字符")
    private String contactMobile;

    @Size(max = 20, message = "联系 QQ 长度不能超过 20 个字符")
    private String contactQq;

    @Size(max = 50, message = "联系微信长度不能超过 50 个字符")
    private String contactWechat;

    @NotNull(message = "状态不能为空")
    private Integer status;

}
