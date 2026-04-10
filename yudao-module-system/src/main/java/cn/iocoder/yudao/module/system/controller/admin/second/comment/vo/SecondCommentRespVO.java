package cn.iocoder.yudao.module.system.controller.admin.second.comment.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SecondCommentRespVO {

    private Long id;
    private Long goodsId;
    private Long userId;
    private Long parentId;
    private String content;
    private Integer status;
    private LocalDateTime createTime;

}
