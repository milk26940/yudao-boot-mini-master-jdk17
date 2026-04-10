package cn.iocoder.yudao.module.system.dal.mysql.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.second.comment.vo.SecondCommentPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCommentDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecondCommentMapper extends BaseMapperX<SecondCommentDO> {

    default PageResult<SecondCommentDO> selectPage(SecondCommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SecondCommentDO>()
                .eqIfPresent(SecondCommentDO::getGoodsId, reqVO.getGoodsId())
                .eqIfPresent(SecondCommentDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SecondCommentDO::getParentId, reqVO.getParentId())
                .eqIfPresent(SecondCommentDO::getStatus, reqVO.getStatus())
                .orderByDesc(SecondCommentDO::getId));
    }

}
