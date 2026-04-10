package cn.iocoder.yudao.module.system.dal.dataobject.second;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("second_goods_image")
@KeySequence("second_goods_image_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondGoodsImageDO extends BaseDO {

    @TableId
    private Long id;

    private Long goodsId;

    private String imageUrl;

    private Integer sort;

}
