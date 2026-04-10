package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo.SecondFavoritePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.favorite.vo.SecondFavoriteSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondFavoriteDO;

public interface SecondFavoriteService {

    Long createFavorite(SecondFavoriteSaveReqVO createReqVO);

    void deleteFavorite(Long id);

    void cancelFavorite(Long userId, Long goodsId);

    SecondFavoriteDO getFavorite(Long id);

    PageResult<SecondFavoriteDO> getFavoritePage(SecondFavoritePageReqVO pageReqVO);

}
