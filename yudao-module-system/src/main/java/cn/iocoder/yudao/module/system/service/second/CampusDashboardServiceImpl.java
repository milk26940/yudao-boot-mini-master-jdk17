package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.second.dashboard.vo.CampusDashboardSummaryRespVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.CampusUserDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCommentDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondOrderDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondWantedDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.CampusUserMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondCommentMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondGoodsMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondOrderMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondWantedMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CampusDashboardServiceImpl implements CampusDashboardService {

    private static final Integer USER_AUDIT_PENDING = 0;
    private static final Integer GOODS_ON_SALE = 1;
    private static final Integer WANTED_ACTIVE = 1;
    private static final Integer ORDER_PENDING = 0;
    private static final Integer ORDER_COMPLETED = 2;

    @Resource
    private CampusUserMapper campusUserMapper;

    @Resource
    private SecondGoodsMapper secondGoodsMapper;

    @Resource
    private SecondWantedMapper secondWantedMapper;

    @Resource
    private SecondCommentMapper secondCommentMapper;

    @Resource
    private SecondOrderMapper secondOrderMapper;

    @Override
    public CampusDashboardSummaryRespVO getSummary() {
        CampusDashboardSummaryRespVO summary = new CampusDashboardSummaryRespVO();
        summary.setUserCount(campusUserMapper.selectCount(new LambdaQueryWrapperX<>()));
        summary.setPendingAuditUserCount(campusUserMapper.selectCount(
                new LambdaQueryWrapperX<CampusUserDO>().eq(CampusUserDO::getAuditStatus, USER_AUDIT_PENDING)));
        summary.setGoodsCount(secondGoodsMapper.selectCount(new LambdaQueryWrapperX<>()));
        summary.setOnSaleGoodsCount(secondGoodsMapper.selectCount(
                new LambdaQueryWrapperX<SecondGoodsDO>().eq(SecondGoodsDO::getStatus, GOODS_ON_SALE)));
        summary.setWantedCount(secondWantedMapper.selectCount(new LambdaQueryWrapperX<>()));
        summary.setActiveWantedCount(secondWantedMapper.selectCount(
                new LambdaQueryWrapperX<SecondWantedDO>().eq(SecondWantedDO::getStatus, WANTED_ACTIVE)));
        summary.setCommentCount(secondCommentMapper.selectCount(new LambdaQueryWrapperX<SecondCommentDO>()));
        summary.setOrderCount(secondOrderMapper.selectCount(new LambdaQueryWrapperX<>()));
        summary.setPendingOrderCount(secondOrderMapper.selectCount(
                new LambdaQueryWrapperX<SecondOrderDO>().eq(SecondOrderDO::getStatus, ORDER_PENDING)));
        summary.setCompletedOrderCount(secondOrderMapper.selectCount(
                new LambdaQueryWrapperX<SecondOrderDO>().eq(SecondOrderDO::getStatus, ORDER_COMPLETED)));
        return summary;
    }

}
