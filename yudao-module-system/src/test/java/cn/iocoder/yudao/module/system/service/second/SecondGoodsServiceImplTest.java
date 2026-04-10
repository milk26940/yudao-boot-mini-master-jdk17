package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.system.controller.admin.second.goods.vo.SecondGoodsSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondGoodsMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_GOODS_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecondGoodsServiceImplTest {

    @Mock
    private SecondGoodsMapper secondGoodsMapper;

    @InjectMocks
    private SecondGoodsServiceImpl secondGoodsService;

    @Test
    void createGoods_whenPublishTimeMissing_thenFillDefaults() {
        SecondGoodsSaveReqVO reqVO = buildSaveReqVO();
        doAnswer(invocation -> {
            SecondGoodsDO goods = invocation.getArgument(0);
            goods.setId(1L);
            return 1;
        }).when(secondGoodsMapper).insert(any(SecondGoodsDO.class));

        Long goodsId = secondGoodsService.createGoods(reqVO);

        assertEquals(1L, goodsId);
        ArgumentCaptor<SecondGoodsDO> captor = ArgumentCaptor.forClass(SecondGoodsDO.class);
        verify(secondGoodsMapper).insert(captor.capture());
        assertEquals(0, captor.getValue().getViewCount());
        assertNotNull(captor.getValue().getPublishTime());
    }

    @Test
    void updateGoods_whenNotExists_thenThrowException() {
        SecondGoodsSaveReqVO reqVO = buildSaveReqVO();
        reqVO.setId(9L);
        when(secondGoodsMapper.selectById(9L)).thenReturn(null);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> secondGoodsService.updateGoods(reqVO));

        assertEquals(SECOND_GOODS_NOT_EXISTS.getCode(), exception.getCode());
    }

    private static SecondGoodsSaveReqVO buildSaveReqVO() {
        SecondGoodsSaveReqVO reqVO = new SecondGoodsSaveReqVO();
        reqVO.setSellerId(1L);
        reqVO.setCategoryId(2L);
        reqVO.setTitle("九成新高数教材");
        reqVO.setBrand("同济");
        reqVO.setDescription("书本保存完好");
        reqVO.setPrice(new BigDecimal("35.00"));
        reqVO.setOriginalPrice(new BigDecimal("59.80"));
        reqVO.setConditionLevel(8);
        reqVO.setContactMobile("13800000001");
        reqVO.setContactQq("123456");
        reqVO.setContactWechat("wechat001");
        reqVO.setStatus(1);
        return reqVO;
    }

}
