package cn.iocoder.yudao.module.system.controller.admin.second.comment.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SecondCommentPageReqVO extends PageParam {

    private Long goodsId;
    private Long userId;
    private Long parentId;
    private Integer status;

}
