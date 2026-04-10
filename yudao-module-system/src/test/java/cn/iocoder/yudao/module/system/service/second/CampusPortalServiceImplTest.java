package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalGoodsPageReqVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalHomeRespVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalUserLoginReqVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalUserRegisterReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.CampusUserDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCategoryDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.CampusUserMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondCategoryMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondGoodsImageMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondGoodsMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondWantedMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.CAMPUS_PORTAL_PENDING_AUDIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampusPortalServiceImplTest {

    @Mock
    private CampusUserMapper campusUserMapper;
    @Mock
    private SecondCategoryMapper secondCategoryMapper;
    @Mock
    private SecondGoodsMapper secondGoodsMapper;
    @Mock
    private SecondGoodsImageMapper secondGoodsImageMapper;
    @Mock
    private SecondWantedMapper secondWantedMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CampusPortalServiceImpl campusPortalService;

    @Test
    void register_whenValid_thenEncodePasswordAndInsert() {
        CampusPortalUserRegisterReqVO reqVO = new CampusPortalUserRegisterReqVO();
        reqVO.setStudentNo("20230005");
        reqVO.setPassword("123456");
        reqVO.setName("测试学生");
        reqVO.setEmail("test@campus.edu.cn");
        reqVO.setMobile("13800000005");
        reqVO.setStudentCardUrl("https://example.com/card.png");
        when(passwordEncoder.encode("123456")).thenReturn("encoded-password");
        doAnswer(invocation -> {
            CampusUserDO user = invocation.getArgument(0);
            user.setId(5L);
            return 1;
        }).when(campusUserMapper).insert(any(CampusUserDO.class));

        Long userId = campusPortalService.register(reqVO);

        assertEquals(5L, userId);
        ArgumentCaptor<CampusUserDO> captor = ArgumentCaptor.forClass(CampusUserDO.class);
        verify(campusUserMapper).insert(captor.capture());
        assertEquals("encoded-password", captor.getValue().getPassword());
        assertEquals(0, captor.getValue().getAuditStatus());
    }

    @Test
    void login_whenPendingAudit_thenThrowException() {
        CampusPortalUserLoginReqVO reqVO = new CampusPortalUserLoginReqVO();
        reqVO.setStudentNo("20230003");
        reqVO.setPassword("123456");
        CampusUserDO user = new CampusUserDO();
        user.setId(3L);
        user.setStudentNo("20230003");
        user.setPassword("encoded");
        user.setStatus(1);
        user.setAuditStatus(0);
        when(campusUserMapper.selectByStudentNo("20230003")).thenReturn(user);
        when(passwordEncoder.matches("123456", "encoded")).thenReturn(true);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> campusPortalService.login(reqVO, "127.0.0.1"));

        assertEquals(CAMPUS_PORTAL_PENDING_AUDIT.getCode(), exception.getCode());
    }

    @Test
    void getHome_returnsAggregatedPortalData() {
        SecondCategoryDO category = new SecondCategoryDO();
        category.setId(1L);
        category.setName("教材书籍");
        category.setSort(1);
        SecondGoodsDO goods = new SecondGoodsDO();
        goods.setId(1L);
        goods.setSellerId(1L);
        goods.setCategoryId(1L);
        goods.setTitle("高数教材");
        goods.setPrice(BigDecimal.TEN);
        goods.setStatus(1);
        CampusUserDO seller = new CampusUserDO();
        seller.setId(1L);
        seller.setName("张三");
        when(secondGoodsMapper.selectCount(any())).thenReturn(3L);
        when(secondWantedMapper.selectCount(any())).thenReturn(2L);
        when(campusUserMapper.selectCount(any())).thenReturn(2L);
        when(secondCategoryMapper.selectListByStatus(1)).thenReturn(List.of(category));
        when(secondGoodsMapper.selectPage(any(CampusPortalGoodsPageReqVO.class), any()))
                .thenReturn(new PageResult<>(List.of(goods), 1L));
        when(campusUserMapper.selectById(1L)).thenReturn(seller);
        when(secondCategoryMapper.selectById(1L)).thenReturn(category);
        when(secondGoodsImageMapper.selectListByGoodsId(1L)).thenReturn(List.of());

        CampusPortalHomeRespVO respVO = campusPortalService.getHome();

        assertEquals(3L, respVO.getOnSaleGoodsCount());
        assertEquals(2L, respVO.getActiveWantedCount());
        assertEquals(2L, respVO.getApprovedUserCount());
        assertEquals(1, respVO.getCategories().size());
        assertEquals(1, respVO.getLatestGoods().size());
    }

    @Test
    void getGoodsPage_whenKeywordPresent_thenReturnPage() {
        CampusPortalGoodsPageReqVO reqVO = new CampusPortalGoodsPageReqVO();
        reqVO.setPageNo(1);
        reqVO.setPageSize(10);
        reqVO.setKeyword("平板");
        when(secondGoodsMapper.selectPage(any(CampusPortalGoodsPageReqVO.class), any()))
                .thenReturn(new PageResult<>(List.of(), 0L));

        PageResult<SecondGoodsDO> pageResult = campusPortalService.getGoodsPage(reqVO);

        assertEquals(0L, pageResult.getTotal());
    }

}
