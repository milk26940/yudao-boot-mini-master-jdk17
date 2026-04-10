package cn.iocoder.yudao.module.system.controller.admin.second.goods;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.goods.vo.SecondGoodsPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.goods.vo.SecondGoodsRespVO;
import cn.iocoder.yudao.module.system.controller.admin.second.goods.vo.SecondGoodsSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsDO;
import cn.iocoder.yudao.module.system.service.second.SecondGoodsService;
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

@Tag(name = "管理后台 - 二手商品")
@RestController
@RequestMapping("/system/second-goods")
@Validated
public class SecondGoodsController {

    @Resource
    private SecondGoodsService secondGoodsService;

    @PostMapping("/create")
    @Operation(summary = "创建二手商品")
    @PreAuthorize("@ss.hasPermission('system:second-goods:create')")
    public CommonResult<Long> createGoods(@Valid @RequestBody SecondGoodsSaveReqVO createReqVO) {
        return success(secondGoodsService.createGoods(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新二手商品")
    @PreAuthorize("@ss.hasPermission('system:second-goods:update')")
    public CommonResult<Boolean> updateGoods(@Valid @RequestBody SecondGoodsSaveReqVO updateReqVO) {
        secondGoodsService.updateGoods(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除二手商品")
    @Parameter(name = "id", description = "商品编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-goods:delete')")
    public CommonResult<Boolean> deleteGoods(@RequestParam("id") Long id) {
        secondGoodsService.deleteGoods(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得二手商品")
    @Parameter(name = "id", description = "商品编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-goods:query')")
    public CommonResult<SecondGoodsRespVO> getGoods(@RequestParam("id") Long id) {
        SecondGoodsDO goods = secondGoodsService.getGoods(id);
        return success(BeanUtils.toBean(goods, SecondGoodsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得二手商品分页")
    @PreAuthorize("@ss.hasPermission('system:second-goods:query')")
    public CommonResult<PageResult<SecondGoodsRespVO>> getGoodsPage(@Validated SecondGoodsPageReqVO pageReqVO) {
        PageResult<SecondGoodsDO> pageResult = secondGoodsService.getGoodsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SecondGoodsRespVO.class));
    }

}
