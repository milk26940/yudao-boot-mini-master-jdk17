package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.system.controller.admin.second.comment.vo.SecondCommentSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCommentDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondCommentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_COMMENT_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecondCommentServiceImplTest {

    @Mock
    private SecondCommentMapper secondCommentMapper;

    @InjectMocks
    private SecondCommentServiceImpl secondCommentService;

    @Test
    void createComment_returnsNewId() {
        SecondCommentSaveReqVO reqVO = buildSaveReqVO();
        doAnswer(invocation -> {
            SecondCommentDO comment = invocation.getArgument(0);
            comment.setId(5L);
            return 1;
        }).when(secondCommentMapper).insert(any(SecondCommentDO.class));

        Long commentId = secondCommentService.createComment(reqVO);

        assertEquals(5L, commentId);
    }

    @Test
    void updateComment_whenNotExists_thenThrowException() {
        SecondCommentSaveReqVO reqVO = buildSaveReqVO();
        reqVO.setId(88L);
        when(secondCommentMapper.selectById(88L)).thenReturn(null);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> secondCommentService.updateComment(reqVO));

        assertEquals(SECOND_COMMENT_NOT_EXISTS.getCode(), exception.getCode());
    }

    private static SecondCommentSaveReqVO buildSaveReqVO() {
        SecondCommentSaveReqVO reqVO = new SecondCommentSaveReqVO();
        reqVO.setGoodsId(1L);
        reqVO.setUserId(1L);
        reqVO.setParentId(0L);
        reqVO.setContent("这个商品还在吗");
        reqVO.setStatus(1);
        return reqVO;
    }

}
