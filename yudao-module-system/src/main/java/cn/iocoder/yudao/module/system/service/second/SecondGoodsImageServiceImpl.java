package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.image.vo.SecondGoodsImagePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.image.vo.SecondGoodsImageSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsImageDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondGoodsImageMapper;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_GOODS_IMAGE_NOT_EXISTS;

@Service
public class SecondGoodsImageServiceImpl implements SecondGoodsImageService {

    @Resource
    private SecondGoodsImageMapper secondGoodsImageMapper;

    @Override
    public Long createGoodsImage(SecondGoodsImageSaveReqVO createReqVO) {
        SecondGoodsImageDO goodsImage = BeanUtils.toBean(createReqVO, SecondGoodsImageDO.class);
        secondGoodsImageMapper.insert(goodsImage);
        return goodsImage.getId();
    }

    @Override
    public void updateGoodsImage(SecondGoodsImageSaveReqVO updateReqVO) {
        validateGoodsImageExists(updateReqVO.getId());
        SecondGoodsImageDO updateObj = BeanUtils.toBean(updateReqVO, SecondGoodsImageDO.class);
        secondGoodsImageMapper.updateById(updateObj);
    }

    @Override
    public void deleteGoodsImage(Long id) {
        validateGoodsImageExists(id);
        secondGoodsImageMapper.deleteById(id);
    }

    @Override
    public SecondGoodsImageDO getGoodsImage(Long id) {
        return secondGoodsImageMapper.selectById(id);
    }

    @Override
    public PageResult<SecondGoodsImageDO> getGoodsImagePage(SecondGoodsImagePageReqVO pageReqVO) {
        return secondGoodsImageMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SecondGoodsImageDO> getGoodsImageListByGoodsId(Long goodsId) {
        return secondGoodsImageMapper.selectListByGoodsId(goodsId);
    }

    @VisibleForTesting
    void validateGoodsImageExists(Long id) {
        if (id == null) {
            return;
        }
        if (secondGoodsImageMapper.selectById(id) == null) {
            throw exception(SECOND_GOODS_IMAGE_NOT_EXISTS);
        }
    }

}
