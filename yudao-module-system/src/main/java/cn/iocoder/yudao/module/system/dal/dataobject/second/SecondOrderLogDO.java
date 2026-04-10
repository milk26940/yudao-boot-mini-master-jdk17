package cn.iocoder.yudao.module.system.dal.dataobject.second;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("second_order_log")
@KeySequence("second_order_log_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondOrderLogDO extends BaseDO {

    @TableId
    private Long id;

    private Long orderId;

    private Long operateUserId;

    private Integer operateRole;

    private Integer beforeStatus;

    private Integer afterStatus;

    private String remark;

}
