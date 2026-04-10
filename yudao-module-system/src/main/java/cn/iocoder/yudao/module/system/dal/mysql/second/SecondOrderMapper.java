package cn.iocoder.yudao.module.system.dal.mysql.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondOrderDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecondOrderMapper extends BaseMapperX<SecondOrderDO> {

    default PageResult<SecondOrderDO> selectPage(SecondOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SecondOrderDO>()
                .eqIfPresent(SecondOrderDO::getGoodsId, reqVO.getGoodsId())
                .eqIfPresent(SecondOrderDO::getSellerId, reqVO.getSellerId())
                .eqIfPresent(SecondOrderDO::getBuyerId, reqVO.getBuyerId())
                .eqIfPresent(SecondOrderDO::getStatus, reqVO.getStatus())
                .likeIfPresent(SecondOrderDO::getOrderNo, reqVO.getOrderNo())
                .orderByDesc(SecondOrderDO::getId));
    }

}
