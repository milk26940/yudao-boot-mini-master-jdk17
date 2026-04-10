package cn.iocoder.yudao.module.system.dal.mysql.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo.SecondWantedPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondWantedDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecondWantedMapper extends BaseMapperX<SecondWantedDO> {

    default PageResult<SecondWantedDO> selectPage(SecondWantedPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SecondWantedDO>()
                .eqIfPresent(SecondWantedDO::getUserId, reqVO.getUserId())
                .likeIfPresent(SecondWantedDO::getTitle, reqVO.getTitle())
                .likeIfPresent(SecondWantedDO::getBrand, reqVO.getBrand())
                .eqIfPresent(SecondWantedDO::getStatus, reqVO.getStatus())
                .orderByDesc(SecondWantedDO::getId));
    }

}
