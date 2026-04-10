package cn.iocoder.yudao.module.system.dal.dataobject.second;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("second_comment")
@KeySequence("second_comment_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondCommentDO extends BaseDO {

    @TableId
    private Long id;

    private Long goodsId;

    private Long userId;

    private Long parentId;

    private String content;

    private Integer status;

}
