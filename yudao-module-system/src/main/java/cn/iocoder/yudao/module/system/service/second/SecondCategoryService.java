package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.second.category.vo.SecondCategoryPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.category.vo.SecondCategorySaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCategoryDO;

import java.util.List;

public interface SecondCategoryService {

    Long createCategory(SecondCategorySaveReqVO createReqVO);

    void updateCategory(SecondCategorySaveReqVO updateReqVO);

    void deleteCategory(Long id);

    SecondCategoryDO getCategory(Long id);

    PageResult<SecondCategoryDO> getCategoryPage(SecondCategoryPageReqVO pageReqVO);

    List<SecondCategoryDO> getEnabledCategoryList();

}
