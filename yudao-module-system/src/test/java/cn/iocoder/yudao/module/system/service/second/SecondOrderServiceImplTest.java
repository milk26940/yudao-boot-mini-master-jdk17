package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderUpdateStatusReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondOrderDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondOrderLogDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondOrderLogMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondOrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_ORDER_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecondOrderServiceImplTest {

    @Mock
    private SecondOrderMapper secondOrderMapper;

    @Mock
    private SecondOrderLogMapper secondOrderLogMapper;

    @InjectMocks
    private SecondOrderServiceImpl secondOrderService;

    @Test
    void createOrder_whenOrderNoMissing_thenGenerateAndWriteLog() {
        SecondOrderSaveReqVO reqVO = buildSaveReqVO();
        doAnswer(invocation -> {
            SecondOrderDO order = invocation.getArgument(0);
            order.setId(7L);
            return 1;
        }).when(secondOrderMapper).insert(any(SecondOrderDO.class));

        Long orderId = secondOrderService.createOrder(reqVO);

        assertEquals(7L, orderId);
        ArgumentCaptor<SecondOrderDO> orderCaptor = ArgumentCaptor.forClass(SecondOrderDO.class);
        verify(secondOrderMapper).insert(orderCaptor.capture());
        assertNotNull(orderCaptor.getValue().getOrderNo());
        ArgumentCaptor<SecondOrderLogDO> logCaptor = ArgumentCaptor.forClass(SecondOrderLogDO.class);
        verify(secondOrderLogMapper).insert(logCaptor.capture());
        assertEquals(7L, logCaptor.getValue().getOrderId());
        assertEquals(2, logCaptor.getValue().getOperateRole());
    }

    @Test
    void updateOrderStatus_whenNotExists_thenThrowException() {
        when(secondOrderMapper.selectById(99L)).thenReturn(null);
        SecondOrderUpdateStatusReqVO reqVO = new SecondOrderUpdateStatusReqVO();
        reqVO.setId(99L);
        reqVO.setStatus(1);
        reqVO.setOperateUserId(2L);
        reqVO.setOperateRole(1);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> secondOrderService.updateOrderStatus(reqVO));

        assertEquals(SECOND_ORDER_NOT_EXISTS.getCode(), exception.getCode());
    }

    @Test
    void updateOrderStatus_whenValid_thenUpdateAndWriteLog() {
        SecondOrderDO order = new SecondOrderDO();
        order.setId(8L);
        order.setStatus(0);
        when(secondOrderMapper.selectById(8L)).thenReturn(order);
        SecondOrderUpdateStatusReqVO reqVO = new SecondOrderUpdateStatusReqVO();
        reqVO.setId(8L);
        reqVO.setStatus(1);
        reqVO.setOperateUserId(3L);
        reqVO.setOperateRole(1);
        reqVO.setRemark("卖家已确认");

        secondOrderService.updateOrderStatus(reqVO);

        ArgumentCaptor<SecondOrderDO> orderCaptor = ArgumentCaptor.forClass(SecondOrderDO.class);
        verify(secondOrderMapper).updateById(orderCaptor.capture());
        assertEquals(1, orderCaptor.getValue().getStatus());
        ArgumentCaptor<SecondOrderLogDO> logCaptor = ArgumentCaptor.forClass(SecondOrderLogDO.class);
        verify(secondOrderLogMapper).insert(logCaptor.capture());
        assertEquals(0, logCaptor.getValue().getBeforeStatus());
        assertEquals(1, logCaptor.getValue().getAfterStatus());
    }

    private static SecondOrderSaveReqVO buildSaveReqVO() {
        SecondOrderSaveReqVO reqVO = new SecondOrderSaveReqVO();
        reqVO.setGoodsId(1L);
        reqVO.setSellerId(1L);
        reqVO.setBuyerId(2L);
        reqVO.setOrderPrice(new BigDecimal("88.00"));
        reqVO.setDeliveryType(1);
        reqVO.setDeliveryAddress("1栋101");
        reqVO.setRemark("宿舍楼下交易");
        reqVO.setStatus(0);
        return reqVO;
    }

}
