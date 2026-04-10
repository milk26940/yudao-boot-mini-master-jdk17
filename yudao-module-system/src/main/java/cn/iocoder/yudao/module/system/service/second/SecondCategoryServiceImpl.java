package cn.iocoder.yudao.module.system.service.second;

import com.google.common.annotations.VisibleForTesting;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.category.vo.SecondCategoryPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.category.vo.SecondCategorySaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCategoryDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondCategoryMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.iocoder.yudao.framework.common.enums.CommonStatusEnum.ENABLE;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_CATEGORY_NOT_EXISTS;

@Service
public class SecondCategoryServiceImpl implements SecondCategoryService {

    @Resource
    private SecondCategoryMapper secondCategoryMapper;

    @Override
    public Long createCategory(SecondCategorySaveReqVO createReqVO) {
        SecondCategoryDO category = BeanUtils.toBean(createReqVO, SecondCategoryDO.class);
        secondCategoryMapper.insert(category);
        return category.getId();
    }

    @Override
    public void updateCategory(SecondCategorySaveReqVO updateReqVO) {
        validateCategoryExists(updateReqVO.getId());
        SecondCategoryDO updateObj = BeanUtils.toBean(updateReqVO, SecondCategoryDO.class);
        secondCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteCategory(Long id) {
        validateCategoryExists(id);
        secondCategoryMapper.deleteById(id);
    }

    @Override
    public SecondCategoryDO getCategory(Long id) {
        return secondCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<SecondCategoryDO> getCategoryPage(SecondCategoryPageReqVO pageReqVO) {
        return secondCategoryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SecondCategoryDO> getEnabledCategoryList() {
        return secondCategoryMapper.selectListByStatus(ENABLE.getStatus());
    }

    @VisibleForTesting
    void validateCategoryExists(Long id) {
        if (id == null) {
            return;
        }
        if (secondCategoryMapper.selectById(id) == null) {
            throw exception(SECOND_CATEGORY_NOT_EXISTS);
        }
    }

}
