package cn.iocoder.yudao.module.system.service.second;

import cn.hutool.core.util.RandomUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderSaveReqVO;
import cn.iocoder.yudao.module.system.controller.admin.second.order.vo.SecondOrderUpdateStatusReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondOrderDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondOrderLogDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondOrderLogMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondOrderMapper;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_ORDER_NOT_EXISTS;

@Service
public class SecondOrderServiceImpl implements SecondOrderService {

    private static final DateTimeFormatter ORDER_NO_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Resource
    private SecondOrderMapper secondOrderMapper;

    @Resource
    private SecondOrderLogMapper secondOrderLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(SecondOrderSaveReqVO createReqVO) {
        SecondOrderDO order = BeanUtils.toBean(createReqVO, SecondOrderDO.class);
        if (order.getOrderNo() == null || order.getOrderNo().isBlank()) {
            order.setOrderNo(generateOrderNo());
        }
        secondOrderMapper.insert(order);
        createOrderLog(order.getId(), createReqVO.getBuyerId(), 2, null, order.getStatus(), createReqVO.getRemark());
        return order.getId();
    }

    @Override
    public void updateOrder(SecondOrderSaveReqVO updateReqVO) {
        validateOrderExists(updateReqVO.getId());
        SecondOrderDO updateObj = BeanUtils.toBean(updateReqVO, SecondOrderDO.class);
        secondOrderMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(SecondOrderUpdateStatusReqVO reqVO) {
        SecondOrderDO order = validateOrderExists(reqVO.getId());
        if (order.getStatus() != null && order.getStatus().equals(reqVO.getStatus())) {
            return;
        }
        Integer beforeStatus = order.getStatus();
        SecondOrderDO updateObj = new SecondOrderDO();
        updateObj.setId(reqVO.getId());
        updateObj.setStatus(reqVO.getStatus());
        secondOrderMapper.updateById(updateObj);
        createOrderLog(reqVO.getId(), reqVO.getOperateUserId(), reqVO.getOperateRole(), beforeStatus, reqVO.getStatus(), reqVO.getRemark());
    }

    @Override
    public void deleteOrder(Long id) {
        validateOrderExists(id);
        secondOrderMapper.deleteById(id);
    }

    @Override
    public SecondOrderDO getOrder(Long id) {
        return secondOrderMapper.selectById(id);
    }

    @Override
    public PageResult<SecondOrderDO> getOrderPage(SecondOrderPageReqVO pageReqVO) {
        return secondOrderMapper.selectPage(pageReqVO);
    }

    @VisibleForTesting
    SecondOrderDO validateOrderExists(Long id) {
        if (id == null) {
            return null;
        }
        SecondOrderDO order = secondOrderMapper.selectById(id);
        if (order == null) {
            throw exception(SECOND_ORDER_NOT_EXISTS);
        }
        return order;
    }

    private void createOrderLog(Long orderId, Long operateUserId, Integer operateRole,
                                Integer beforeStatus, Integer afterStatus, String remark) {
        SecondOrderLogDO log = new SecondOrderLogDO();
        log.setOrderId(orderId);
        log.setOperateUserId(operateUserId);
        log.setOperateRole(operateRole);
        log.setBeforeStatus(beforeStatus);
        log.setAfterStatus(afterStatus);
        log.setRemark(remark);
        secondOrderLogMapper.insert(log);
    }

    private String generateOrderNo() {
        return "SO" + LocalDateTime.now().format(ORDER_NO_TIME_FORMATTER) + RandomUtil.randomNumbers(4);
    }

}
