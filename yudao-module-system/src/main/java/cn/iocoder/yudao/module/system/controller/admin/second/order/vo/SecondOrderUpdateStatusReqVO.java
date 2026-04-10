package cn.iocoder.yudao.module.system.controller.admin.second.order.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SecondOrderUpdateStatusReqVO {

    @NotNull(message = "订单编号不能为空")
    private Long id;

    @NotNull(message = "订单状态不能为空")
    private Integer status;

    @NotNull(message = "操作用户编号不能为空")
    private Long operateUserId;

    @NotNull(message = "操作角色不能为空")
    private Integer operateRole;

    @Size(max = 255, message = "备注长度不能超过 255 个字符")
    private String remark;

}
