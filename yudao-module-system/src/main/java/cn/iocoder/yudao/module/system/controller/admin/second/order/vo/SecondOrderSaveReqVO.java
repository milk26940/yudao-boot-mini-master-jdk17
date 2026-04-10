package cn.iocoder.yudao.module.system.controller.admin.second.order.vo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SecondOrderSaveReqVO {

    private Long id;

    private String orderNo;

    @NotNull(message = "商品编号不能为空")
    private Long goodsId;

    @NotNull(message = "卖家编号不能为空")
    private Long sellerId;

    @NotNull(message = "买家编号不能为空")
    private Long buyerId;

    @NotNull(message = "成交价格不能为空")
    @DecimalMin(value = "0.00", message = "成交价格不能小于 0")
    private BigDecimal orderPrice;

    @NotNull(message = "交易方式不能为空")
    private Integer deliveryType;

    @Size(max = 255, message = "交易地址长度不能超过 255 个字符")
    private String deliveryAddress;

    @Size(max = 255, message = "备注长度不能超过 255 个字符")
    private String remark;

    @NotNull(message = "订单状态不能为空")
    private Integer status;

}
