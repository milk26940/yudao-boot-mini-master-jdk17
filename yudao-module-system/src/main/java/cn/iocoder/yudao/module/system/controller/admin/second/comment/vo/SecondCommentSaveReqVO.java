package cn.iocoder.yudao.module.system.controller.admin.second.comment.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SecondCommentSaveReqVO {

    private Long id;

    @NotNull(message = "商品编号不能为空")
    private Long goodsId;

    @NotNull(message = "评论用户编号不能为空")
    private Long userId;

    private Long parentId;

    @NotBlank(message = "评论内容不能为空")
    @Size(max = 500, message = "评论内容长度不能超过 500 个字符")
    private String content;

    @NotNull(message = "状态不能为空")
    private Integer status;

}
