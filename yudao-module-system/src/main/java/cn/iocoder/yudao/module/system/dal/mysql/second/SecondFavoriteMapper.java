package cn.iocoder.yudao.module.system.dal.mysql.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo.SecondFavoritePageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondFavoriteDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecondFavoriteMapper extends BaseMapperX<SecondFavoriteDO> {

    default SecondFavoriteDO selectByUserIdAndGoodsId(Long userId, Long goodsId) {
        return selectOne(new LambdaQueryWrapperX<SecondFavoriteDO>()
                .eq(SecondFavoriteDO::getUserId, userId)
                .eq(SecondFavoriteDO::getGoodsId, goodsId));
    }

    default PageResult<SecondFavoriteDO> selectPage(SecondFavoritePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SecondFavoriteDO>()
                .eqIfPresent(SecondFavoriteDO::getUserId, reqVO.getUserId())
                .eqIfPresent(SecondFavoriteDO::getGoodsId, reqVO.getGoodsId())
                .orderByDesc(SecondFavoriteDO::getId));
    }

}
