package cn.iocoder.yudao.module.system.controller.admin.second.category;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.category.vo.SecondCategoryPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.category.vo.SecondCategoryRespVO;
import cn.iocoder.yudao.module.system.controller.admin.second.category.vo.SecondCategorySaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCategoryDO;
import cn.iocoder.yudao.module.system.service.second.SecondCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 二手商品分类")
@RestController
@RequestMapping("/system/second-category")
@Validated
public class SecondCategoryController {

    @Resource
    private SecondCategoryService secondCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建二手商品分类")
    @PreAuthorize("@ss.hasPermission('system:second-category:create')")
    public CommonResult<Long> createCategory(@Valid @RequestBody SecondCategorySaveReqVO createReqVO) {
        return success(secondCategoryService.createCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新二手商品分类")
    @PreAuthorize("@ss.hasPermission('system:second-category:update')")
    public CommonResult<Boolean> updateCategory(@Valid @RequestBody SecondCategorySaveReqVO updateReqVO) {
        secondCategoryService.updateCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除二手商品分类")
    @Parameter(name = "id", description = "分类编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-category:delete')")
    public CommonResult<Boolean> deleteCategory(@RequestParam("id") Long id) {
        secondCategoryService.deleteCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得二手商品分类")
    @Parameter(name = "id", description = "分类编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-category:query')")
    public CommonResult<SecondCategoryRespVO> getCategory(@RequestParam("id") Long id) {
        SecondCategoryDO category = secondCategoryService.getCategory(id);
        return success(BeanUtils.toBean(category, SecondCategoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得二手商品分类分页")
    @PreAuthorize("@ss.hasPermission('system:second-category:query')")
    public CommonResult<PageResult<SecondCategoryRespVO>> getCategoryPage(@Validated SecondCategoryPageReqVO pageReqVO) {
        PageResult<SecondCategoryDO> pageResult = secondCategoryService.getCategoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SecondCategoryRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得启用的二手商品分类列表")
    public CommonResult<List<SecondCategoryRespVO>> getSimpleCategoryList() {
        return success(BeanUtils.toBean(secondCategoryService.getEnabledCategoryList(), SecondCategoryRespVO.class));
    }

}
