package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.module.system.controller.admin.second.dashboard.vo.CampusDashboardSummaryRespVO;
import cn.iocoder.yudao.module.system.dal.mysql.second.CampusUserMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondCommentMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondGoodsMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondOrderMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondWantedMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampusDashboardServiceImplTest {

    @Mock
    private CampusUserMapper campusUserMapper;

    @Mock
    private SecondGoodsMapper secondGoodsMapper;

    @Mock
    private SecondWantedMapper secondWantedMapper;

    @Mock
    private SecondCommentMapper secondCommentMapper;

    @Mock
    private SecondOrderMapper secondOrderMapper;

    @InjectMocks
    private CampusDashboardServiceImpl campusDashboardService;

    @Test
    void getSummary_returnsAggregatedCounts() {
        when(campusUserMapper.selectCount(any(Wrapper.class))).thenReturn(4L, 1L);
        when(secondGoodsMapper.selectCount(any(Wrapper.class))).thenReturn(8L, 5L);
        when(secondWantedMapper.selectCount(any(Wrapper.class))).thenReturn(6L, 3L);
        when(secondCommentMapper.selectCount(any(Wrapper.class))).thenReturn(9L);
        when(secondOrderMapper.selectCount(any(Wrapper.class))).thenReturn(7L, 2L, 4L);

        CampusDashboardSummaryRespVO summary = campusDashboardService.getSummary();

        assertEquals(4L, summary.getUserCount());
        assertEquals(1L, summary.getPendingAuditUserCount());
        assertEquals(8L, summary.getGoodsCount());
        assertEquals(5L, summary.getOnSaleGoodsCount());
        assertEquals(6L, summary.getWantedCount());
        assertEquals(3L, summary.getActiveWantedCount());
        assertEquals(9L, summary.getCommentCount());
        assertEquals(7L, summary.getOrderCount());
        assertEquals(2L, summary.getPendingOrderCount());
        assertEquals(4L, summary.getCompletedOrderCount());
    }

}
