package cn.iocoder.yudao.module.system.dal.dataobject.second;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("second_goods")
@KeySequence("second_goods_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondGoodsDO extends BaseDO {

    @TableId
    private Long id;

    private Long sellerId;

    private Long categoryId;

    private String title;

    private String brand;

    private String description;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private Integer conditionLevel;

    private String contactMobile;

    private String contactQq;

    private String contactWechat;

    private Integer status;

    private Integer viewCount;

    private LocalDateTime publishTime;

}
