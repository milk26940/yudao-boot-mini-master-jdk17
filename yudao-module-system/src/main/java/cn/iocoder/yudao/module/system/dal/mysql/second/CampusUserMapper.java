package cn.iocoder.yudao.module.system.dal.mysql.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.CampusUserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CampusUserMapper extends BaseMapperX<CampusUserDO> {

    default CampusUserDO selectByStudentNo(String studentNo) {
        return selectOne(CampusUserDO::getStudentNo, studentNo);
    }

    default PageResult<CampusUserDO> selectPage(CampusUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CampusUserDO>()
                .likeIfPresent(CampusUserDO::getStudentNo, reqVO.getStudentNo())
                .likeIfPresent(CampusUserDO::getName, reqVO.getName())
                .likeIfPresent(CampusUserDO::getMobile, reqVO.getMobile())
                .eqIfPresent(CampusUserDO::getAuditStatus, reqVO.getAuditStatus())
                .eqIfPresent(CampusUserDO::getStatus, reqVO.getStatus())
                .orderByDesc(CampusUserDO::getId));
    }

}
