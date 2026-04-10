package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo.SecondWantedPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.wanted.vo.SecondWantedSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondWantedDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondWantedMapper;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_WANTED_NOT_EXISTS;

@Service
public class SecondWantedServiceImpl implements SecondWantedService {

    @Resource
    private SecondWantedMapper secondWantedMapper;

    @Override
    public Long createWanted(SecondWantedSaveReqVO createReqVO) {
        SecondWantedDO wanted = BeanUtils.toBean(createReqVO, SecondWantedDO.class);
        secondWantedMapper.insert(wanted);
        return wanted.getId();
    }

    @Override
    public void updateWanted(SecondWantedSaveReqVO updateReqVO) {
        validateWantedExists(updateReqVO.getId());
        SecondWantedDO updateObj = BeanUtils.toBean(updateReqVO, SecondWantedDO.class);
        secondWantedMapper.updateById(updateObj);
    }

    @Override
    public void deleteWanted(Long id) {
        validateWantedExists(id);
        secondWantedMapper.deleteById(id);
    }

    @Override
    public SecondWantedDO getWanted(Long id) {
        return secondWantedMapper.selectById(id);
    }

    @Override
    public PageResult<SecondWantedDO> getWantedPage(SecondWantedPageReqVO pageReqVO) {
        return secondWantedMapper.selectPage(pageReqVO);
    }

    @VisibleForTesting
    void validateWantedExists(Long id) {
        if (id == null) {
            return;
        }
        if (secondWantedMapper.selectById(id) == null) {
            throw exception(SECOND_WANTED_NOT_EXISTS);
        }
    }

}
