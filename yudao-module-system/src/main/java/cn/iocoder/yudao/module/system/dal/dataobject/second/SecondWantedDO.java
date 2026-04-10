package cn.iocoder.yudao.module.system.dal.dataobject.second;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@TableName("second_wanted")
@KeySequence("second_wanted_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondWantedDO extends BaseDO {

    @TableId
    private Long id;

    private Long userId;

    private String title;

    private String brand;

    private String model;

    private BigDecimal expectedPrice;

    private String description;

    private String contactMobile;

    private String contactQq;

    private String contactWechat;

    private Integer status;

}
