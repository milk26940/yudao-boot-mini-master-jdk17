package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalGoodsPageReqVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalGoodsRespVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalHomeRespVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalUserLoginReqVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalUserRegisterReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.CampusUserDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCategoryDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsDO;

import java.util.List;

public interface CampusPortalService {

    Long register(CampusPortalUserRegisterReqVO reqVO);

    CampusUserDO login(CampusPortalUserLoginReqVO reqVO, String loginIp);

    CampusPortalHomeRespVO getHome();

    List<SecondCategoryDO> getEnabledCategories();

    PageResult<SecondGoodsDO> getGoodsPage(CampusPortalGoodsPageReqVO reqVO);

    SecondGoodsDO getGoods(Long id);

    List<CampusPortalGoodsRespVO> buildGoodsRespList(List<SecondGoodsDO> goodsList);

    CampusPortalGoodsRespVO buildGoodsResp(SecondGoodsDO goods);

}
