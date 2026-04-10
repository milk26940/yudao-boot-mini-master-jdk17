package cn.iocoder.yudao.module.system.controller.admin.second.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SecondOrderRespVO {

    private Long id;
    private String orderNo;
    private Long goodsId;
    private Long sellerId;
    private Long buyerId;
    private BigDecimal orderPrice;
    private Integer deliveryType;
    private String deliveryAddress;
    private String remark;
    private Integer status;
    private LocalDateTime createTime;

}
