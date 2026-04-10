package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo.SecondWantedPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo.SecondWantedSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondWantedDO;

public interface SecondWantedService {

    Long createWanted(SecondWantedSaveReqVO createReqVO);

    void updateWanted(SecondWantedSaveReqVO updateReqVO);

    void deleteWanted(Long id);

    SecondWantedDO getWanted(Long id);

    PageResult<SecondWantedDO> getWantedPage(SecondWantedPageReqVO pageReqVO);

}
