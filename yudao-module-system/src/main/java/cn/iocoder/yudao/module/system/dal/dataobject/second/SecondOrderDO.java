package cn.iocoder.yudao.module.system.dal.dataobject.second;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@TableName("second_order")
@KeySequence("second_order_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondOrderDO extends BaseDO {

    @TableId
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

}
