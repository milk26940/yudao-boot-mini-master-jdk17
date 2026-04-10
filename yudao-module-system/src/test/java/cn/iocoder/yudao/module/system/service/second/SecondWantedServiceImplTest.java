package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo.SecondWantedSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondWantedDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondWantedMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_WANTED_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecondWantedServiceImplTest {

    @Mock
    private SecondWantedMapper secondWantedMapper;

    @InjectMocks
    private SecondWantedServiceImpl secondWantedService;

    @Test
    void createWanted_returnsNewId() {
        SecondWantedSaveReqVO reqVO = buildSaveReqVO();
        doAnswer(invocation -> {
            SecondWantedDO wanted = invocation.getArgument(0);
            wanted.setId(3L);
            return 1;
        }).when(secondWantedMapper).insert(any(SecondWantedDO.class));

        Long wantedId = secondWantedService.createWanted(reqVO);

        assertEquals(3L, wantedId);
    }

    @Test
    void updateWanted_whenNotExists_thenThrowException() {
        SecondWantedSaveReqVO reqVO = buildSaveReqVO();
        reqVO.setId(99L);
        when(secondWantedMapper.selectById(99L)).thenReturn(null);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> secondWantedService.updateWanted(reqVO));

        assertEquals(SECOND_WANTED_NOT_EXISTS.getCode(), exception.getCode());
    }

    private static SecondWantedSaveReqVO buildSaveReqVO() {
        SecondWantedSaveReqVO reqVO = new SecondWantedSaveReqVO();
        reqVO.setUserId(1L);
        reqVO.setTitle("求购二手自行车");
        reqVO.setBrand("捷安特");
        reqVO.setModel("ATX 660");
        reqVO.setExpectedPrice(new BigDecimal("300.00"));
        reqVO.setDescription("希望车况良好");
        reqVO.setContactMobile("13800000001");
        reqVO.setContactQq("123456");
        reqVO.setContactWechat("wechat001");
        reqVO.setStatus(1);
        return reqVO;
    }

}
