package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo.SecondFavoritePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo.SecondFavoriteSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondFavoriteDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondFavoriteMapper;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_FAVORITE_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_FAVORITE_NOT_EXISTS;

@Service
public class SecondFavoriteServiceImpl implements SecondFavoriteService {

    @Resource
    private SecondFavoriteMapper secondFavoriteMapper;

    @Override
    public Long createFavorite(SecondFavoriteSaveReqVO createReqVO) {
        validateFavoriteUnique(createReqVO.getUserId(), createReqVO.getGoodsId());
        SecondFavoriteDO favorite = BeanUtils.toBean(createReqVO, SecondFavoriteDO.class);
        secondFavoriteMapper.insert(favorite);
        return favorite.getId();
    }

    @Override
    public void deleteFavorite(Long id) {
        validateFavoriteExists(id);
        secondFavoriteMapper.deleteById(id);
    }

    @Override
    public void cancelFavorite(Long userId, Long goodsId) {
        SecondFavoriteDO favorite = secondFavoriteMapper.selectByUserIdAndGoodsId(userId, goodsId);
        if (favorite == null) {
            throw exception(SECOND_FAVORITE_NOT_EXISTS);
        }
        secondFavoriteMapper.deleteById(favorite.getId());
    }

    @Override
    public SecondFavoriteDO getFavorite(Long id) {
        return secondFavoriteMapper.selectById(id);
    }

    @Override
    public PageResult<SecondFavoriteDO> getFavoritePage(SecondFavoritePageReqVO pageReqVO) {
        return secondFavoriteMapper.selectPage(pageReqVO);
    }

    @VisibleForTesting
    void validateFavoriteExists(Long id) {
        if (id == null) {
            return;
        }
        if (secondFavoriteMapper.selectById(id) == null) {
            throw exception(SECOND_FAVORITE_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    void validateFavoriteUnique(Long userId, Long goodsId) {
        if (secondFavoriteMapper.selectByUserIdAndGoodsId(userId, goodsId) != null) {
            throw exception(SECOND_FAVORITE_EXISTS);
        }
    }

}
