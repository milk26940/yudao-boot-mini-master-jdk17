package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.module.system.controller.admin.second.category.vo.SecondCategorySaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCategoryDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondCategoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_CATEGORY_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecondCategoryServiceImplTest {

    @Mock
    private SecondCategoryMapper secondCategoryMapper;

    @InjectMocks
    private SecondCategoryServiceImpl secondCategoryService;

    @Test
    void createCategory_returnsNewId() {
        SecondCategorySaveReqVO reqVO = new SecondCategorySaveReqVO();
        reqVO.setName("教材书籍");
        reqVO.setSort(1);
        reqVO.setStatus(1);

        doAnswer(invocation -> {
            SecondCategoryDO category = invocation.getArgument(0);
            category.setId(1L);
            return 1;
        }).when(secondCategoryMapper).insert(any(SecondCategoryDO.class));

        Long id = secondCategoryService.createCategory(reqVO);

        assertEquals(1L, id);
    }

    @Test
    void updateCategory_whenNotExists_thenThrowException() {
        SecondCategorySaveReqVO reqVO = new SecondCategorySaveReqVO();
        reqVO.setId(999L);
        reqVO.setName("教材书籍");
        reqVO.setSort(1);
        reqVO.setStatus(1);
        when(secondCategoryMapper.selectById(999L)).thenReturn(null);

        ServiceException ex = assertThrows(ServiceException.class,
                () -> secondCategoryService.updateCategory(reqVO));

        assertEquals(SECOND_CATEGORY_NOT_EXISTS.getCode(), ex.getCode());
    }
}
