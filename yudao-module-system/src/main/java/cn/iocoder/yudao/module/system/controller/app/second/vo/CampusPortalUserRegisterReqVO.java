package cn.iocoder.yudao.module.system.controller.app.second.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "用户 App - 校园普通用户注册 Request VO")
@Data
public class CampusPortalUserRegisterReqVO {

    @Schema(description = "学号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20230005")
    @NotBlank(message = "学号不能为空")
    private String studentNo;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Schema(description = "性别", example = "1")
    private Integer gender;

    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "student@campus.edu.cn")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13800000001")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @Schema(description = "QQ", example = "123456")
    private String qq;

    @Schema(description = "微信号", example = "wechat001")
    private String wechat;

    @Schema(description = "头像地址", example = "https://example.com/avatar.png")
    private String avatar;

    @Schema(description = "学生证图片地址", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "https://example.com/student-card.png")
    @NotBlank(message = "学生证图片不能为空")
    private String studentCardUrl;

}
