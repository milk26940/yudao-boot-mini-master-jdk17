package cn.iocoder.yudao.module.system.controller.admin.second.user.vo;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "管理后台 - 校园用户新增/修改 Request VO")
@Data
public class CampusUserSaveReqVO {

    @Schema(description = "用户编号", example = "1")
    private Long id;

    @Schema(description = "学号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20230001")
    @NotBlank(message = "学号不能为空")
    @Size(max = 30, message = "学号长度不能超过 30 个字符")
    private String studentNo;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotBlank(message = "密码不能为空")
    @Size(max = 100, message = "密码长度不能超过 100 个字符")
    private String password;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotBlank(message = "姓名不能为空")
    @Size(max = 50, message = "姓名长度不能超过 50 个字符")
    private String name;

    @Schema(description = "性别", example = "1")
    private Integer gender;

    @Schema(description = "邮箱", example = "student@example.com")
    @Size(max = 100, message = "邮箱长度不能超过 100 个字符")
    private String email;

    @Schema(description = "手机号", example = "13800000001")
    @Size(max = 20, message = "手机号长度不能超过 20 个字符")
    private String mobile;

    @Schema(description = "QQ", example = "123456")
    @Size(max = 20, message = "QQ 长度不能超过 20 个字符")
    private String qq;

    @Schema(description = "微信号", example = "wechat001")
    @Size(max = 50, message = "微信号长度不能超过 50 个字符")
    private String wechat;

    @Schema(description = "头像地址", example = "https://example.com/avatar.png")
    @Size(max = 512, message = "头像地址长度不能超过 512 个字符")
    private String avatar;

    @Schema(description = "学生证图片地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://example.com/card.png")
    @NotBlank(message = "学生证图片地址不能为空")
    @Size(max = 512, message = "学生证图片地址长度不能超过 512 个字符")
    private String studentCardUrl;

    @Schema(description = "审核状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "审核状态不能为空")
    private Integer auditStatus;

    @Schema(description = "审核备注", example = "待审核")
    @Size(max = 255, message = "审核备注长度不能超过 255 个字符")
    private String auditRemark;

    @Schema(description = "账号状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "账号状态不能为空")
    @InEnum(value = CommonStatusEnum.class, message = "账号状态必须是 {value}")
    private Integer status;

}
