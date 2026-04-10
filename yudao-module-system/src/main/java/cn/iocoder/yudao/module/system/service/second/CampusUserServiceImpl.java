package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserAuditReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.user.vo.CampusUserSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.CampusUserDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.CampusUserMapper;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.CAMPUS_USER_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.CAMPUS_USER_STUDENT_NO_EXISTS;

@Service
public class CampusUserServiceImpl implements CampusUserService {

    @Resource
    private CampusUserMapper campusUserMapper;

    @Override
    public Long createUser(CampusUserSaveReqVO createReqVO) {
        validateStudentNoUnique(null, createReqVO.getStudentNo());
        CampusUserDO user = BeanUtils.toBean(createReqVO, CampusUserDO.class);
        campusUserMapper.insert(user);
        return user.getId();
    }

    @Override
    public void updateUser(CampusUserSaveReqVO updateReqVO) {
        validateUserExists(updateReqVO.getId());
        validateStudentNoUnique(updateReqVO.getId(), updateReqVO.getStudentNo());
        CampusUserDO updateObj = BeanUtils.toBean(updateReqVO, CampusUserDO.class);
        campusUserMapper.updateById(updateObj);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        CampusUserDO user = validateUserExists(id);
        if (user.getStatus() != null && user.getStatus().equals(status)) {
            return;
        }
        CampusUserDO updateObj = new CampusUserDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        campusUserMapper.updateById(updateObj);
    }

    @Override
    public void auditUser(CampusUserAuditReqVO auditReqVO) {
        validateUserExists(auditReqVO.getId());
        CampusUserDO updateObj = new CampusUserDO();
        updateObj.setId(auditReqVO.getId());
        updateObj.setAuditStatus(auditReqVO.getAuditStatus());
        updateObj.setAuditRemark(auditReqVO.getAuditRemark());
        campusUserMapper.updateById(updateObj);
    }

    @Override
    public CampusUserDO getUser(Long id) {
        return campusUserMapper.selectById(id);
    }

    @Override
    public PageResult<CampusUserDO> getUserPage(CampusUserPageReqVO pageReqVO) {
        return campusUserMapper.selectPage(pageReqVO);
    }

    @VisibleForTesting
    CampusUserDO validateUserExists(Long id) {
        if (id == null) {
            return null;
        }
        CampusUserDO user = campusUserMapper.selectById(id);
        if (user == null) {
            throw exception(CAMPUS_USER_NOT_EXISTS);
        }
        return user;
    }

    @VisibleForTesting
    void validateStudentNoUnique(Long id, String studentNo) {
        CampusUserDO user = campusUserMapper.selectByStudentNo(studentNo);
        if (user == null) {
            return;
        }
        if (id == null || !user.getId().equals(id)) {
            throw exception(CAMPUS_USER_STUDENT_NO_EXISTS);
        }
    }

}
