package cn.iocoder.yudao.module.system.controller.admin.second.order;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderRespVO;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderUpdateStatusReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondOrderDO;
import cn.iocoder.yudao.module.system.service.second.SecondOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 二手交易订单")
@RestController
@RequestMapping("/system/second-order")
@Validated
public class SecondOrderController {

    @Resource
    private SecondOrderService secondOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建订单")
    @PreAuthorize("@ss.hasPermission('system:second-order:create')")
    public CommonResult<Long> createOrder(@Valid @RequestBody SecondOrderSaveReqVO createReqVO) {
        return success(secondOrderService.createOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新订单")
    @PreAuthorize("@ss.hasPermission('system:second-order:update')")
    public CommonResult<Boolean> updateOrder(@Valid @RequestBody SecondOrderSaveReqVO updateReqVO) {
        secondOrderService.updateOrder(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新订单状态")
    @PreAuthorize("@ss.hasPermission('system:second-order:update')")
    public CommonResult<Boolean> updateOrderStatus(@Valid @RequestBody SecondOrderUpdateStatusReqVO reqVO) {
        secondOrderService.updateOrderStatus(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除订单")
    @Parameter(name = "id", description = "订单编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-order:delete')")
    public CommonResult<Boolean> deleteOrder(@RequestParam("id") Long id) {
        secondOrderService.deleteOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得订单")
    @Parameter(name = "id", description = "订单编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-order:query')")
    public CommonResult<SecondOrderRespVO> getOrder(@RequestParam("id") Long id) {
        SecondOrderDO order = secondOrderService.getOrder(id);
        return success(BeanUtils.toBean(order, SecondOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得订单分页")
    @PreAuthorize("@ss.hasPermission('system:second-order:query')")
    public CommonResult<PageResult<SecondOrderRespVO>> getOrderPage(@Validated SecondOrderPageReqVO pageReqVO) {
        PageResult<SecondOrderDO> pageResult = secondOrderService.getOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SecondOrderRespVO.class));
    }

}
