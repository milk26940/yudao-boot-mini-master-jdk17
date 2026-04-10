package cn.iocoder.yudao.module.system.dal.mysql.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.second.goods.vo.SecondGoodsPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecondGoodsMapper extends BaseMapperX<SecondGoodsDO> {

    default PageResult<SecondGoodsDO> selectPage(SecondGoodsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SecondGoodsDO>()
                .eqIfPresent(SecondGoodsDO::getSellerId, reqVO.getSellerId())
                .eqIfPresent(SecondGoodsDO::getCategoryId, reqVO.getCategoryId())
                .likeIfPresent(SecondGoodsDO::getTitle, reqVO.getTitle())
                .eqIfPresent(SecondGoodsDO::getStatus, reqVO.getStatus())
                .orderByDesc(SecondGoodsDO::getPublishTime)
                .orderByDesc(SecondGoodsDO::getId));
    }

}
