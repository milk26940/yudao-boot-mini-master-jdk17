package cn.iocoder.yudao.module.system.controller.admin.second.user.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 校园用户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CampusUserPageReqVO extends PageParam {

    @Schema(description = "学号", example = "20230001")
    private String studentNo;

    @Schema(description = "姓名", example = "张三")
    private String name;

    @Schema(description = "手机号", example = "13800000001")
    private String mobile;

    @Schema(description = "审核状态", example = "1")
    private Integer auditStatus;

    @Schema(description = "账号状态", example = "1")
    private Integer status;

}
