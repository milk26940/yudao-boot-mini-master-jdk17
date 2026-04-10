package cn.iocoder.yudao.module.system.controller.admin.second.image;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.image.vo.SecondGoodsImagePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.image.vo.SecondGoodsImageRespVO;
import cn.iocoder.yudao.module.system.controller.admin.second.image.vo.SecondGoodsImageSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsImageDO;
import cn.iocoder.yudao.module.system.service.second.SecondGoodsImageService;
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

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 商品图片")
@RestController
@RequestMapping("/system/second-goods-image")
@Validated
public class SecondGoodsImageController {

    @Resource
    private SecondGoodsImageService secondGoodsImageService;

    @PostMapping("/create")
    @Operation(summary = "创建商品图片")
    @PreAuthorize("@ss.hasPermission('system:second-goods-image:create')")
    public CommonResult<Long> createGoodsImage(@Valid @RequestBody SecondGoodsImageSaveReqVO createReqVO) {
        return success(secondGoodsImageService.createGoodsImage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新商品图片")
    @PreAuthorize("@ss.hasPermission('system:second-goods-image:update')")
    public CommonResult<Boolean> updateGoodsImage(@Valid @RequestBody SecondGoodsImageSaveReqVO updateReqVO) {
        secondGoodsImageService.updateGoodsImage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除商品图片")
    @Parameter(name = "id", description = "图片编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-goods-image:delete')")
    public CommonResult<Boolean> deleteGoodsImage(@RequestParam("id") Long id) {
        secondGoodsImageService.deleteGoodsImage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得商品图片")
    @Parameter(name = "id", description = "图片编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-goods-image:query')")
    public CommonResult<SecondGoodsImageRespVO> getGoodsImage(@RequestParam("id") Long id) {
        SecondGoodsImageDO goodsImage = secondGoodsImageService.getGoodsImage(id);
        return success(BeanUtils.toBean(goodsImage, SecondGoodsImageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得商品图片分页")
    @PreAuthorize("@ss.hasPermission('system:second-goods-image:query')")
    public CommonResult<PageResult<SecondGoodsImageRespVO>> getGoodsImagePage(@Validated SecondGoodsImagePageReqVO pageReqVO) {
        PageResult<SecondGoodsImageDO> pageResult = secondGoodsImageService.getGoodsImagePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SecondGoodsImageRespVO.class));
    }

    @GetMapping("/list-by-goods")
    @Operation(summary = "按商品获得图片列表")
    @Parameter(name = "goodsId", description = "商品编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-goods-image:query')")
    public CommonResult<List<SecondGoodsImageRespVO>> getGoodsImageListByGoodsId(@RequestParam("goodsId") Long goodsId) {
        return success(BeanUtils.toBean(secondGoodsImageService.getGoodsImageListByGoodsId(goodsId),
                SecondGoodsImageRespVO.class));
    }

}
