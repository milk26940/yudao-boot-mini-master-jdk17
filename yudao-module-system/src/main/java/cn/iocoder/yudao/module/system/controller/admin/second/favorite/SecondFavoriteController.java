package cn.iocoder.yudao.module.system.controller.admin.second.favorite;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo.SecondFavoritePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo.SecondFavoriteRespVO;
import cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo.SecondFavoriteSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondFavoriteDO;
import cn.iocoder.yudao.module.system.service.second.SecondFavoriteService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 商品收藏")
@RestController
@RequestMapping("/system/second-favorite")
@Validated
public class SecondFavoriteController {

    @Resource
    private SecondFavoriteService secondFavoriteService;

    @PostMapping("/create")
    @Operation(summary = "创建收藏")
    @PreAuthorize("@ss.hasPermission('system:second-favorite:create')")
    public CommonResult<Long> createFavorite(@Valid @RequestBody SecondFavoriteSaveReqVO createReqVO) {
        return success(secondFavoriteService.createFavorite(createReqVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收藏")
    @Parameter(name = "id", description = "收藏编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-favorite:delete')")
    public CommonResult<Boolean> deleteFavorite(@RequestParam("id") Long id) {
        secondFavoriteService.deleteFavorite(id);
        return success(true);
    }

    @DeleteMapping("/cancel")
    @Operation(summary = "取消收藏")
    @PreAuthorize("@ss.hasPermission('system:second-favorite:delete')")
    public CommonResult<Boolean> cancelFavorite(@RequestParam("userId") Long userId,
                                                @RequestParam("goodsId") Long goodsId) {
        secondFavoriteService.cancelFavorite(userId, goodsId);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收藏")
    @Parameter(name = "id", description = "收藏编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-favorite:query')")
    public CommonResult<SecondFavoriteRespVO> getFavorite(@RequestParam("id") Long id) {
        SecondFavoriteDO favorite = secondFavoriteService.getFavorite(id);
        return success(BeanUtils.toBean(favorite, SecondFavoriteRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收藏分页")
    @PreAuthorize("@ss.hasPermission('system:second-favorite:query')")
    public CommonResult<PageResult<SecondFavoriteRespVO>> getFavoritePage(@Validated SecondFavoritePageReqVO pageReqVO) {
        PageResult<SecondFavoriteDO> pageResult = secondFavoriteService.getFavoritePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SecondFavoriteRespVO.class));
    }

}
