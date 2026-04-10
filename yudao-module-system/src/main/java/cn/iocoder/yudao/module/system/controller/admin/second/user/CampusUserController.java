package cn.iocoder.yudao.module.system.controller.admin.second.user;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserAuditReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserRespVO;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserUpdateStatusReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.CampusUserDO;
import cn.iocoder.yudao.module.system.service.second.CampusUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 校园用户")
@RestController
@RequestMapping("/system/campus-user")
@Validated
public class CampusUserController {

    @Resource
    private CampusUserService campusUserService;

    @PostMapping("/create")
    @Operation(summary = "创建校园用户")
    @PreAuthorize("@ss.hasPermission('system:campus-user:create')")
    public CommonResult<Long> createUser(@Valid @RequestBody CampusUserSaveReqVO createReqVO) {
        return success(campusUserService.createUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新校园用户")
    @PreAuthorize("@ss.hasPermission('system:campus-user:update')")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody CampusUserSaveReqVO updateReqVO) {
        campusUserService.updateUser(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新校园用户状态")
    @PreAuthorize("@ss.hasPermission('system:campus-user:update')")
    public CommonResult<Boolean> updateUserStatus(@Valid @RequestBody CampusUserUpdateStatusReqVO reqVO) {
        campusUserService.updateUserStatus(reqVO.getId(), reqVO.getStatus());
        return success(true);
    }

    @PutMapping("/audit")
    @Operation(summary = "审核校园用户")
    @PreAuthorize("@ss.hasPermission('system:campus-user:audit')")
    public CommonResult<Boolean> auditUser(@Valid @RequestBody CampusUserAuditReqVO reqVO) {
        campusUserService.auditUser(reqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得校园用户")
    @Parameter(name = "id", description = "用户编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:campus-user:query')")
    public CommonResult<CampusUserRespVO> getUser(@RequestParam("id") Long id) {
        CampusUserDO user = campusUserService.getUser(id);
        return success(BeanUtils.toBean(user, CampusUserRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得校园用户分页")
    @PreAuthorize("@ss.hasPermission('system:campus-user:query')")
    public CommonResult<PageResult<CampusUserRespVO>> getUserPage(@Validated CampusUserPageReqVO pageReqVO) {
        PageResult<CampusUserDO> pageResult = campusUserService.getUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CampusUserRespVO.class));
    }

}
