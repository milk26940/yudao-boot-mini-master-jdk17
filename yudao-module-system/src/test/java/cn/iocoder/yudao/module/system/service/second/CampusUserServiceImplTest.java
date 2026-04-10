package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserAuditReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.CampusUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.CampusUserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.CAMPUS_USER_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.CAMPUS_USER_STUDENT_NO_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampusUserServiceImplTest {

    @Mock
    private CampusUserMapper campusUserMapper;

    @InjectMocks
    private CampusUserServiceImpl campusUserService;

    @Test
    void createUser_whenStudentNoExists_thenThrowException() {
        CampusUserSaveReqVO reqVO = buildSaveReqVO();
        CampusUserDO existUser = new CampusUserDO();
        existUser.setId(1L);
        when(campusUserMapper.selectByStudentNo("20230001")).thenReturn(existUser);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> campusUserService.createUser(reqVO));

        assertEquals(CAMPUS_USER_STUDENT_NO_EXISTS.getCode(), exception.getCode());
    }

    @Test
    void auditUser_whenUserExists_thenUpdateAuditFields() {
        CampusUserAuditReqVO reqVO = new CampusUserAuditReqVO();
        reqVO.setId(2L);
        reqVO.setAuditStatus(1);
        reqVO.setAuditRemark("审核通过");
        CampusUserDO user = new CampusUserDO();
        user.setId(2L);
        when(campusUserMapper.selectById(2L)).thenReturn(user);

        campusUserService.auditUser(reqVO);

        ArgumentCaptor<CampusUserDO> captor = ArgumentCaptor.forClass(CampusUserDO.class);
        verify(campusUserMapper).updateById(captor.capture());
        assertEquals(2L, captor.getValue().getId());
        assertEquals(1, captor.getValue().getAuditStatus());
        assertEquals("审核通过", captor.getValue().getAuditRemark());
    }

    @Test
    void updateUserStatus_whenNotExists_thenThrowException() {
        when(campusUserMapper.selectById(9L)).thenReturn(null);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> campusUserService.updateUserStatus(9L, 0));

        assertEquals(CAMPUS_USER_NOT_EXISTS.getCode(), exception.getCode());
    }

    @Test
    void createUser_whenValid_thenReturnId() {
        CampusUserSaveReqVO reqVO = buildSaveReqVO();
        doAnswer(invocation -> {
            CampusUserDO user = invocation.getArgument(0);
            user.setId(6L);
            return 1;
        }).when(campusUserMapper).insert(any(CampusUserDO.class));

        Long userId = campusUserService.createUser(reqVO);

        assertEquals(6L, userId);
    }

    private static CampusUserSaveReqVO buildSaveReqVO() {
        CampusUserSaveReqVO reqVO = new CampusUserSaveReqVO();
        reqVO.setStudentNo("20230001");
        reqVO.setPassword("123456");
        reqVO.setName("张三");
        reqVO.setGender(1);
        reqVO.setEmail("student@example.com");
        reqVO.setMobile("13800000001");
        reqVO.setQq("123456");
        reqVO.setWechat("wechat001");
        reqVO.setAvatar("https://example.com/avatar.png");
        reqVO.setStudentCardUrl("https://example.com/card.png");
        reqVO.setAuditStatus(0);
        reqVO.setAuditRemark("待审核");
        reqVO.setStatus(1);
        return reqVO;
    }

}
