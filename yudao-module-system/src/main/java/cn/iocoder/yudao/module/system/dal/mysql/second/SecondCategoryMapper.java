package cn.iocoder.yudao.module.system.dal.mysql.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.second.category.vo.SecondCategoryPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SecondCategoryMapper extends BaseMapperX<SecondCategoryDO> {

    default PageResult<SecondCategoryDO> selectPage(SecondCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SecondCategoryDO>()
                .likeIfPresent(SecondCategoryDO::getName, reqVO.getName())
                .eqIfPresent(SecondCategoryDO::getStatus, reqVO.getStatus())
                .orderByAsc(SecondCategoryDO::getSort)
                .orderByDesc(SecondCategoryDO::getId));
    }

    default List<SecondCategoryDO> selectListByStatus(Integer status) {
        return selectList(new LambdaQueryWrapperX<SecondCategoryDO>()
                .eqIfPresent(SecondCategoryDO::getStatus, status)
                .orderByAsc(SecondCategoryDO::getSort)
                .orderByDesc(SecondCategoryDO::getId));
    }

}
