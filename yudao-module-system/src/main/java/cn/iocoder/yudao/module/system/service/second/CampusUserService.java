package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserAuditReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.CampusUserDO;

public interface CampusUserService {

    Long createUser(CampusUserSaveReqVO createReqVO);

    void updateUser(CampusUserSaveReqVO updateReqVO);

    void updateUserStatus(Long id, Integer status);

    void auditUser(CampusUserAuditReqVO auditReqVO);

    CampusUserDO getUser(Long id);

    PageResult<CampusUserDO> getUserPage(CampusUserPageReqVO pageReqVO);

}
