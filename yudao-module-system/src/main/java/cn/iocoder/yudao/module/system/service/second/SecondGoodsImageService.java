package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.second.image.vo.SecondGoodsImagePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.image.vo.SecondGoodsImageSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsImageDO;

import java.util.List;

public interface SecondGoodsImageService {

    Long createGoodsImage(SecondGoodsImageSaveReqVO createReqVO);

    void updateGoodsImage(SecondGoodsImageSaveReqVO updateReqVO);

    void deleteGoodsImage(Long id);

    SecondGoodsImageDO getGoodsImage(Long id);

    PageResult<SecondGoodsImageDO> getGoodsImagePage(SecondGoodsImagePageReqVO pageReqVO);

    List<SecondGoodsImageDO> getGoodsImageListByGoodsId(Long goodsId);

}
