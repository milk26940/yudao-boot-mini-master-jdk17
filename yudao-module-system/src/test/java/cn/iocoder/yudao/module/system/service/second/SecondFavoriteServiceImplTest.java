package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo.SecondFavoriteSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondFavoriteDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondFavoriteMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_FAVORITE_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_FAVORITE_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecondFavoriteServiceImplTest {

    @Mock
    private SecondFavoriteMapper secondFavoriteMapper;

    @InjectMocks
    private SecondFavoriteServiceImpl secondFavoriteService;

    @Test
    void createFavorite_whenAlreadyExists_thenThrowException() {
        SecondFavoriteSaveReqVO reqVO = buildSaveReqVO();
        SecondFavoriteDO favorite = new SecondFavoriteDO();
        favorite.setId(1L);
        when(secondFavoriteMapper.selectByUserIdAndGoodsId(1L, 2L)).thenReturn(favorite);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> secondFavoriteService.createFavorite(reqVO));

        assertEquals(SECOND_FAVORITE_EXISTS.getCode(), exception.getCode());
    }

    @Test
    void createFavorite_whenValid_thenReturnId() {
        SecondFavoriteSaveReqVO reqVO = buildSaveReqVO();
        doAnswer(invocation -> {
            SecondFavoriteDO favorite = invocation.getArgument(0);
            favorite.setId(9L);
            return 1;
        }).when(secondFavoriteMapper).insert(any(SecondFavoriteDO.class));

        Long favoriteId = secondFavoriteService.createFavorite(reqVO);

        assertEquals(9L, favoriteId);
    }

    @Test
    void cancelFavorite_whenNotExists_thenThrowException() {
        when(secondFavoriteMapper.selectByUserIdAndGoodsId(1L, 2L)).thenReturn(null);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> secondFavoriteService.cancelFavorite(1L, 2L));

        assertEquals(SECOND_FAVORITE_NOT_EXISTS.getCode(), exception.getCode());
    }

    private static SecondFavoriteSaveReqVO buildSaveReqVO() {
        SecondFavoriteSaveReqVO reqVO = new SecondFavoriteSaveReqVO();
        reqVO.setUserId(1L);
        reqVO.setGoodsId(2L);
        return reqVO;
    }

}
