package cn.iocoder.yudao.module.system.controller.admin.second.dashboard;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.second.dashboard.vo.CampusDashboardSummaryRespVO;
import cn.iocoder.yudao.module.system.service.second.CampusDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 校园二手交易看板")
@RestController
@RequestMapping("/system/campus-dashboard")
@Validated
public class CampusDashboardController {

    @Resource
    private CampusDashboardService campusDashboardService;

    @GetMapping("/summary")
    @Operation(summary = "获得校园二手交易看板摘要")
    @PreAuthorize("@ss.hasPermission('system:campus-dashboard:query')")
    public CommonResult<CampusDashboardSummaryRespVO> getSummary() {
        return success(campusDashboardService.getSummary());
    }

}
