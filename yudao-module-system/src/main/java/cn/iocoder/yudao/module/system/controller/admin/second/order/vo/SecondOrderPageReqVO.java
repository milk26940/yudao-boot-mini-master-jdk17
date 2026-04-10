package cn.iocoder.yudao.module.system.controller.admin.second.order.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SecondOrderPageReqVO extends PageParam {

    private Long goodsId;
    private Long sellerId;
    private Long buyerId;
    private Integer status;
    private String orderNo;

}
