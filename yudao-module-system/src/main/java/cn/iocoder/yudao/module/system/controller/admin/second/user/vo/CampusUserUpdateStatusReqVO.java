package cn.iocoder.yudao.module.system.controller.admin.second.user.vo;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - 校园用户更新状态 Request VO")
@Data
public class CampusUserUpdateStatusReqVO {

    @NotNull(message = "用户编号不能为空")
    private Long id;

    @NotNull(message = "账号状态不能为空")
    @InEnum(value = CommonStatusEnum.class, message = "账号状态必须是 {value}")
    private Integer status;

}
