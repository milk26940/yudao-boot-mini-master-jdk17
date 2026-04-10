package cn.iocoder.yudao.module.system.controller.admin.second.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "管理后台 - 校园用户审核 Request VO")
@Data
public class CampusUserAuditReqVO {

    @NotNull(message = "用户编号不能为空")
    private Long id;

    @NotNull(message = "审核状态不能为空")
    private Integer auditStatus;

    @Size(max = 255, message = "审核备注长度不能超过 255 个字符")
    private String auditRemark;

}
