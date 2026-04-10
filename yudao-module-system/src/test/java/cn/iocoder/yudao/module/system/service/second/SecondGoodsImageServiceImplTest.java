package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.system.controller.admin.second.image.vo.SecondGoodsImageSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsImageDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondGoodsImageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_GOODS_IMAGE_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecondGoodsImageServiceImplTest {

    @Mock
    private SecondGoodsImageMapper secondGoodsImageMapper;

    @InjectMocks
    private SecondGoodsImageServiceImpl secondGoodsImageService;

    @Test
    void createGoodsImage_returnsNewId() {
        SecondGoodsImageSaveReqVO reqVO = buildSaveReqVO();
        doAnswer(invocation -> {
            SecondGoodsImageDO goodsImage = invocation.getArgument(0);
            goodsImage.setId(6L);
            return 1;
        }).when(secondGoodsImageMapper).insert(any(SecondGoodsImageDO.class));

        Long imageId = secondGoodsImageService.createGoodsImage(reqVO);

        assertEquals(6L, imageId);
    }

    @Test
    void updateGoodsImage_whenNotExists_thenThrowException() {
        SecondGoodsImageSaveReqVO reqVO = buildSaveReqVO();
        reqVO.setId(99L);
        when(secondGoodsImageMapper.selectById(99L)).thenReturn(null);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> secondGoodsImageService.updateGoodsImage(reqVO));

        assertEquals(SECOND_GOODS_IMAGE_NOT_EXISTS.getCode(), exception.getCode());
    }

    private static SecondGoodsImageSaveReqVO buildSaveReqVO() {
        SecondGoodsImageSaveReqVO reqVO = new SecondGoodsImageSaveReqVO();
        reqVO.setGoodsId(1L);
        reqVO.setImageUrl("https://example.com/goods-1.png");
        reqVO.setSort(1);
        return reqVO;
    }

}
