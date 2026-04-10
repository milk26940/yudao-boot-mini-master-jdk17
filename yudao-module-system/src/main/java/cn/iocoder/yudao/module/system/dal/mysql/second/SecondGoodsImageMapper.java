package cn.iocoder.yudao.module.system.dal.mysql.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.second.image.vo.SecondGoodsImagePageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsImageDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SecondGoodsImageMapper extends BaseMapperX<SecondGoodsImageDO> {

    default PageResult<SecondGoodsImageDO> selectPage(SecondGoodsImagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SecondGoodsImageDO>()
                .eqIfPresent(SecondGoodsImageDO::getGoodsId, reqVO.getGoodsId())
                .orderByAsc(SecondGoodsImageDO::getSort)
                .orderByDesc(SecondGoodsImageDO::getId));
    }

    default List<SecondGoodsImageDO> selectListByGoodsId(Long goodsId) {
        return selectList(new LambdaQueryWrapperX<SecondGoodsImageDO>()
                .eq(SecondGoodsImageDO::getGoodsId, goodsId)
                .orderByAsc(SecondGoodsImageDO::getSort)
                .orderByDesc(SecondGoodsImageDO::getId));
    }

}
