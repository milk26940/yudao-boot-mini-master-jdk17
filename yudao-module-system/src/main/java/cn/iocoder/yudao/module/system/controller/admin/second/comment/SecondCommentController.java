package cn.iocoder.yudao.module.system.controller.admin.second.comment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.comment.vo.SecondCommentPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.comment.vo.SecondCommentRespVO;
import cn.iocoder.yudao.module.system.controller.admin.second.comment.vo.SecondCommentSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCommentDO;
import cn.iocoder.yudao.module.system.service.second.SecondCommentService;
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

@Tag(name = "管理后台 - 商品评论")
@RestController
@RequestMapping("/system/second-comment")
@Validated
public class SecondCommentController {

    @Resource
    private SecondCommentService secondCommentService;

    @PostMapping("/create")
    @Operation(summary = "创建商品评论")
    @PreAuthorize("@ss.hasPermission('system:second-comment:create')")
    public CommonResult<Long> createComment(@Valid @RequestBody SecondCommentSaveReqVO createReqVO) {
        return success(secondCommentService.createComment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新商品评论")
    @PreAuthorize("@ss.hasPermission('system:second-comment:update')")
    public CommonResult<Boolean> updateComment(@Valid @RequestBody SecondCommentSaveReqVO updateReqVO) {
        secondCommentService.updateComment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除商品评论")
    @Parameter(name = "id", description = "评论编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-comment:delete')")
    public CommonResult<Boolean> deleteComment(@RequestParam("id") Long id) {
        secondCommentService.deleteComment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得商品评论")
    @Parameter(name = "id", description = "评论编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('system:second-comment:query')")
    public CommonResult<SecondCommentRespVO> getComment(@RequestParam("id") Long id) {
        SecondCommentDO comment = secondCommentService.getComment(id);
        return success(BeanUtils.toBean(comment, SecondCommentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得商品评论分页")
    @PreAuthorize("@ss.hasPermission('system:second-comment:query')")
    public CommonResult<PageResult<SecondCommentRespVO>> getCommentPage(@Validated SecondCommentPageReqVO pageReqVO) {
        PageResult<SecondCommentDO> pageResult = secondCommentService.getCommentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SecondCommentRespVO.class));
    }

}
