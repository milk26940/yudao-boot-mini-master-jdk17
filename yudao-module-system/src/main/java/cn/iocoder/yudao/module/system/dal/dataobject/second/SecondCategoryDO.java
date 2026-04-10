package cn.iocoder.yudao.module.system.dal.dataobject.second;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("second_category")
@KeySequence("second_category_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondCategoryDO extends BaseDO {

    private Long id;

    private String name;

    private Integer sort;

    private Integer status;

}
