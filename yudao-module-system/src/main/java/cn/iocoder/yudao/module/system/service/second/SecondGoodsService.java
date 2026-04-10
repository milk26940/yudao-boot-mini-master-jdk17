package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.second.goods.vo.SecondGoodsPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.goods.vo.SecondGoodsSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsDO;

public interface SecondGoodsService {

    Long createGoods(SecondGoodsSaveReqVO createReqVO);

    void updateGoods(SecondGoodsSaveReqVO updateReqVO);

    void deleteGoods(Long id);

    SecondGoodsDO getGoods(Long id);

    PageResult<SecondGoodsDO> getGoodsPage(SecondGoodsPageReqVO pageReqVO);

}
