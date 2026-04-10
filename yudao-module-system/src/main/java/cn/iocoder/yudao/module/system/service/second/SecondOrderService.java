package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderUpdateStatusReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondOrderDO;

public interface SecondOrderService {

    Long createOrder(SecondOrderSaveReqVO createReqVO);

    void updateOrder(SecondOrderSaveReqVO updateReqVO);

    void updateOrderStatus(SecondOrderUpdateStatusReqVO reqVO);

    void deleteOrder(Long id);

    SecondOrderDO getOrder(Long id);

    PageResult<SecondOrderDO> getOrderPage(SecondOrderPageReqVO pageReqVO);

}
