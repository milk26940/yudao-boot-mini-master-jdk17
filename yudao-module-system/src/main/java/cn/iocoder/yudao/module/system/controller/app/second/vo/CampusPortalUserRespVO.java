package cn.iocoder.yudao.module.system.controller.app.second.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 App - 校园普通用户信息 Response VO")
@Data
public class CampusPortalUserRespVO {

    @Schema(description = "用户编号", example = "1")
    private Long id;

    @Schema(description = "学号", example = "20230001")
    private String studentNo;

    @Schema(description = "姓名", example = "张三")
    private String name;

    @Schema(description = "邮箱", example = "student@campus.edu.cn")
    private String email;

    @Schema(description = "手机号", example = "13800000001")
    private String mobile;

    @Schema(description = "头像", example = "https://example.com/avatar.png")
    private String avatar;

    @Schema(description = "审核状态", example = "1")
    private Integer auditStatus;

    @Schema(description = "账号状态", example = "1")
    private Integer status;

}
