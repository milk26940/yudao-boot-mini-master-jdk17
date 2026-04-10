package cn.iocoder.yudao.module.system.dal.dataobject.second;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("second_favorite")
@KeySequence("second_favorite_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondFavoriteDO extends BaseDO {

    @TableId
    private Long id;

    private Long userId;

    private Long goodsId;

}
