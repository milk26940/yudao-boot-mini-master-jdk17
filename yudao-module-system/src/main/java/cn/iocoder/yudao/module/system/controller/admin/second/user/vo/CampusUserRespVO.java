package cn.iocoder.yudao.module.system.controller.admin.second.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 校园用户 Response VO")
@Data
public class CampusUserRespVO {

    private Long id;
    private String studentNo;
    private String name;
    private Integer gender;
    private String email;
    private String mobile;
    private String qq;
    private String wechat;
    private String avatar;
    private String studentCardUrl;
    private Integer auditStatus;
    private String auditRemark;
    private Integer status;
    private String loginIp;
    private LocalDateTime loginTime;
    private LocalDateTime createTime;

}
