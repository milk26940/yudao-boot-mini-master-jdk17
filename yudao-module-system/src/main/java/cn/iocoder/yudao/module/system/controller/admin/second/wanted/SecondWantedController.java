package cn.iocoder.yudao.module.system.controller.admin.second.wanted;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo.SecondWantedPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo.SecondWantedRespVO;
import cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo.SecondWantedSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondWantedDO;
import cn.iocoder.yudao.module.system.service.second.SecondWantedService;
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

@Tag(name = "管理后台 - 求购信息")
@RestController
@RequestMapping("/system/second-wanted")
@Validated
public class SecondWantedController {

    @Resource
    private SecondWantedService secondWantedService;

    @PostMapping("/create")
    @Operation(summary = "创建求购信息")
    @PreAuthorize("@ss.hasPermission('system:second-wanted:create')")
    public CommonResult<Long> createWanted(@Valid @RequestBody SecondWantedSaveReqVO createReqVO) {
        return success(secondWantedService.createWanted(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新求购信息")
    @PreAuthorize("@ss.hasPermission('system:second-wanted:update')")
    public CommonResult<Boolean> updateWanted(@Valid @RequestBody SecondWantedSaveReqVO updateReqVO) {
        secondWantedService.updateWanted(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除求购信息")
    @Parameter(name = "id", description = "求购编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-wanted:delete')")
    public CommonResult<Boolean> deleteWanted(@RequestParam("id") Long id) {
        secondWantedService.deleteWanted(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得求购信息")
    @Parameter(name = "id", description = "求购编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-wanted:query')")
    public CommonResult<SecondWantedRespVO> getWanted(@RequestParam("id") Long id) {
        SecondWantedDO wanted = secondWantedService.getWanted(id);
        return success(BeanUtils.toBean(wanted, SecondWantedRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得求购信息分页")
    @PreAuthorize("@ss.hasPermission('system:second-wanted:query')")
    public CommonResult<PageResult<SecondWantedRespVO>> getWantedPage(@Validated SecondWantedPageReqVO pageReqVO) {
        PageResult<SecondWantedDO> pageResult = secondWantedService.getWantedPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SecondWantedRespVO.class));
    }

}
