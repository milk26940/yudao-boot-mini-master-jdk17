package cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 求购信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class SecondWantedPageReqVO extends PageParam {

    private Long userId;
    private String title;
    private String brand;
    private Integer status;

}
