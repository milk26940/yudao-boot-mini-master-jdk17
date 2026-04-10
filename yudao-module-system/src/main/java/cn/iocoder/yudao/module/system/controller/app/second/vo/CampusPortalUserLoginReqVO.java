package cn.iocoder.yudao.module.system.controller.app.second.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "用户 App - 校园普通用户登录 Request VO")
@Data
public class CampusPortalUserLoginReqVO {

    @Schema(description = "学号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20230001")
    @NotBlank(message = "学号不能为空")
    private String studentNo;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;

}
