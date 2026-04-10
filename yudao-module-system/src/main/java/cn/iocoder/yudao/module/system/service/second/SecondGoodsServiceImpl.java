package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.goods.vo.SecondGoodsPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.goods.vo.SecondGoodsSaveReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondGoodsMapper;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_GOODS_NOT_EXISTS;

@Service
public class SecondGoodsServiceImpl implements SecondGoodsService {

    @Resource
    private SecondGoodsMapper secondGoodsMapper;

    @Override
    public Long createGoods(SecondGoodsSaveReqVO createReqVO) {
        SecondGoodsDO goods = BeanUtils.toBean(createReqVO, SecondGoodsDO.class);
        if (goods.getPublishTime() == null) {
            goods.setPublishTime(LocalDateTime.now());
        }
        if (goods.getViewCount() == null) {
            goods.setViewCount(0);
        }
        secondGoodsMapper.insert(goods);
        return goods.getId();
    }

    @Override
    public void updateGoods(SecondGoodsSaveReqVO updateReqVO) {
        validateGoodsExists(updateReqVO.getId());
        SecondGoodsDO updateObj = BeanUtils.toBean(updateReqVO, SecondGoodsDO.class);
        secondGoodsMapper.updateById(updateObj);
    }

    @Override
    public void deleteGoods(Long id) {
        validateGoodsExists(id);
        secondGoodsMapper.deleteById(id);
    }

    @Override
    public SecondGoodsDO getGoods(Long id) {
        return secondGoodsMapper.selectById(id);
    }

    @Override
    public PageResult<SecondGoodsDO> getGoodsPage(SecondGoodsPageReqVO pageReqVO) {
        return secondGoodsMapper.selectPage(pageReqVO);
    }

    @VisibleForTesting
    void validateGoodsExists(Long id) {
        if (id == null) {
            return;
        }
        if (secondGoodsMapper.selectById(id) == null) {
            throw exception(SECOND_GOODS_NOT_EXISTS);
        }
    }

}
