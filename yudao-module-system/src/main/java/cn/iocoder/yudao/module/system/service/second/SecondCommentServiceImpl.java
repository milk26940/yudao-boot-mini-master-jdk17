package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.comment.vo.SecondCommentPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.comment.vo.SecondCommentSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCommentDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondCommentMapper;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_COMMENT_NOT_EXISTS;

@Service
public class SecondCommentServiceImpl implements SecondCommentService {

    @Resource
    private SecondCommentMapper secondCommentMapper;

    @Override
    public Long createComment(SecondCommentSaveReqVO createReqVO) {
        SecondCommentDO comment = BeanUtils.toBean(createReqVO, SecondCommentDO.class);
        secondCommentMapper.insert(comment);
        return comment.getId();
    }

    @Override
    public void updateComment(SecondCommentSaveReqVO updateReqVO) {
        validateCommentExists(updateReqVO.getId());
        SecondCommentDO updateObj = BeanUtils.toBean(updateReqVO, SecondCommentDO.class);
        secondCommentMapper.updateById(updateObj);
    }

    @Override
    public void deleteComment(Long id) {
        validateCommentExists(id);
        secondCommentMapper.deleteById(id);
    }

    @Override
    public SecondCommentDO getComment(Long id) {
        return secondCommentMapper.selectById(id);
    }

    @Override
    public PageResult<SecondCommentDO> getCommentPage(SecondCommentPageReqVO pageReqVO) {
        return secondCommentMapper.selectPage(pageReqVO);
    }

    @VisibleForTesting
    void validateCommentExists(Long id) {
        if (id == null) {
            return;
        }
        if (secondCommentMapper.selectById(id) == null) {
            throw exception(SECOND_COMMENT_NOT_EXISTS);
        }
    }

}
