package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.second.comment.vo.SecondCommentPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.comment.vo.SecondCommentSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCommentDO;

public interface SecondCommentService {

    Long createComment(SecondCommentSaveReqVO createReqVO);

    void updateComment(SecondCommentSaveReqVO updateReqVO);

    void deleteComment(Long id);

    SecondCommentDO getComment(Long id);

    PageResult<SecondCommentDO> getCommentPage(SecondCommentPageReqVO pageReqVO);

}
