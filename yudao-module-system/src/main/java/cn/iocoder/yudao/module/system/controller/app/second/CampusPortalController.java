package cn.iocoder.yudao.module.system.controller.app.second;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalCategoryRespVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalGoodsPageReqVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalGoodsRespVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalHomeRespVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalUserLoginReqVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalUserRegisterReqVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalUserRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.CampusUserDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCategoryDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsDO;
import cn.iocoder.yudao.module.system.service.second.CampusPortalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 校园二手交易门户")
@RestController
@RequestMapping("/system/campus-portal")
@Validated
public class CampusPortalController {

    @Resource
    private CampusPortalService campusPortalService;

    @PostMapping("/auth/register")
    @Operation(summary = "普通用户注册")
    @PermitAll
    public CommonResult<Long> register(@Valid @RequestBody CampusPortalUserRegisterReqVO reqVO) {
        return success(campusPortalService.register(reqVO));
    }

    @PostMapping("/auth/login")
    @Operation(summary = "普通用户登录")
    @PermitAll
    public CommonResult<CampusPortalUserRespVO> login(@Valid @RequestBody CampusPortalUserLoginReqVO reqVO,
                                                       HttpServletRequest request) {
        CampusUserDO user = campusPortalService.login(reqVO, request.getRemoteAddr());
        return success(BeanUtils.toBean(user, CampusPortalUserRespVO.class));
    }

    @GetMapping("/home")
    @Operation(summary = "获取门户首页数据")
    @PermitAll
    public CommonResult<CampusPortalHomeRespVO> getHome() {
        return success(campusPortalService.getHome());
    }

    @GetMapping("/category/list")
    @Operation(summary = "获取可用商品分类列表")
    @PermitAll
    public CommonResult<List<CampusPortalCategoryRespVO>> getCategoryList() {
        List<SecondCategoryDO> categories = campusPortalService.getEnabledCategories();
        return success(BeanUtils.toBean(categories, CampusPortalCategoryRespVO.class));
    }

    @GetMapping("/goods/page")
    @Operation(summary = "分页获取上架商品")
    @PermitAll
    public CommonResult<PageResult<CampusPortalGoodsRespVO>> getGoodsPage(@Validated CampusPortalGoodsPageReqVO reqVO) {
        PageResult<SecondGoodsDO> pageResult = campusPortalService.getGoodsPage(reqVO);
        return success(new PageResult<>(campusPortalService.buildGoodsRespList(pageResult.getList()),
                pageResult.getTotal()));
    }

    @GetMapping("/goods/get")
    @Operation(summary = "获取商品详情")
    @PermitAll
    public CommonResult<CampusPortalGoodsRespVO> getGoods(@RequestParam("id") Long id) {
        return success(campusPortalService.buildGoodsResp(campusPortalService.getGoods(id)));
    }

}
