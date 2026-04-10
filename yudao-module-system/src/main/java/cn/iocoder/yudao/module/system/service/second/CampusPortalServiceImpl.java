package cn.iocoder.yudao.module.system.service.second;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalCategoryRespVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalGoodsPageReqVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalGoodsRespVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalHomeRespVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalUserLoginReqVO;
import cn.iocoder.yudao.module.system.controller.app.second.vo.CampusPortalUserRegisterReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.CampusUserDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondCategoryDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondGoodsDO;
import cn.iocoder.yudao.module.system.dal.dataobject.second.SecondWantedDO;
import cn.iocoder.yudao.module.system.dal.mysql.second.CampusUserMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondCategoryMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondGoodsImageMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondGoodsMapper;
import cn.iocoder.yudao.module.system.dal.mysql.second.SecondWantedMapper;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.CAMPUS_PORTAL_AUDIT_REJECTED;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.CAMPUS_PORTAL_BAD_CREDENTIALS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.CAMPUS_PORTAL_DISABLED;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.CAMPUS_PORTAL_PENDING_AUDIT;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.CAMPUS_USER_STUDENT_NO_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SECOND_GOODS_NOT_EXISTS;

@Service
public class CampusPortalServiceImpl implements CampusPortalService {

    private static final Integer CAMPUS_USER_AUDIT_PENDING = 0;
    private static final Integer CAMPUS_USER_AUDIT_APPROVED = 1;
    private static final Integer CAMPUS_USER_AUDIT_REJECTED_VALUE = 2;
    private static final Integer CAMPUS_USER_STATUS_ENABLED = 1;
    private static final Integer SECOND_CATEGORY_STATUS_ENABLED = 1;
    private static final Integer SECOND_GOODS_STATUS_ON_SALE = 1;
    private static final Integer SECOND_WANTED_STATUS_ACTIVE = 1;

    @Resource
    private CampusUserMapper campusUserMapper;

    @Resource
    private SecondCategoryMapper secondCategoryMapper;

    @Resource
    private SecondGoodsMapper secondGoodsMapper;

    @Resource
    private SecondGoodsImageMapper secondGoodsImageMapper;

    @Resource
    private SecondWantedMapper secondWantedMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Long register(CampusPortalUserRegisterReqVO reqVO) {
        validateStudentNoUnique(reqVO.getStudentNo());
        CampusUserDO user = BeanUtils.toBean(reqVO, CampusUserDO.class);
        user.setPassword(passwordEncoder.encode(reqVO.getPassword()));
        user.setAuditStatus(CAMPUS_USER_AUDIT_PENDING);
        user.setAuditRemark("待审核");
        user.setStatus(CAMPUS_USER_STATUS_ENABLED);
        campusUserMapper.insert(user);
        return user.getId();
    }

    @Override
    public CampusUserDO login(CampusPortalUserLoginReqVO reqVO, String loginIp) {
        CampusUserDO user = campusUserMapper.selectByStudentNo(reqVO.getStudentNo());
        if (user == null || !passwordEncoder.matches(reqVO.getPassword(), user.getPassword())) {
            throw exception(CAMPUS_PORTAL_BAD_CREDENTIALS);
        }
        if (!CAMPUS_USER_STATUS_ENABLED.equals(user.getStatus())) {
            throw exception(CAMPUS_PORTAL_DISABLED);
        }
        if (CAMPUS_USER_AUDIT_PENDING.equals(user.getAuditStatus())) {
            throw exception(CAMPUS_PORTAL_PENDING_AUDIT);
        }
        if (CAMPUS_USER_AUDIT_REJECTED_VALUE.equals(user.getAuditStatus())) {
            throw exception(CAMPUS_PORTAL_AUDIT_REJECTED);
        }
        CampusUserDO updateObj = new CampusUserDO();
        updateObj.setId(user.getId());
        updateObj.setLoginIp(loginIp);
        updateObj.setLoginTime(LocalDateTime.now());
        campusUserMapper.updateById(updateObj);
        user.setLoginIp(loginIp);
        user.setLoginTime(updateObj.getLoginTime());
        return user;
    }

    @Override
    public CampusPortalHomeRespVO getHome() {
        CampusPortalHomeRespVO respVO = new CampusPortalHomeRespVO();
        respVO.setOnSaleGoodsCount(secondGoodsMapper.selectCount(new LambdaQueryWrapperX<SecondGoodsDO>()
                .eq(SecondGoodsDO::getStatus, SECOND_GOODS_STATUS_ON_SALE)));
        respVO.setActiveWantedCount(secondWantedMapper.selectCount(new LambdaQueryWrapperX<SecondWantedDO>()
                .eq(SecondWantedDO::getStatus, SECOND_WANTED_STATUS_ACTIVE)));
        respVO.setApprovedUserCount(campusUserMapper.selectCount(new LambdaQueryWrapperX<CampusUserDO>()
                .eq(CampusUserDO::getAuditStatus, CAMPUS_USER_AUDIT_APPROVED)));
        respVO.setCategories(BeanUtils.toBean(getEnabledCategories(), CampusPortalCategoryRespVO.class));
        PageResult<SecondGoodsDO> latestPage = getGoodsPage(buildLatestGoodsPageReqVO());
        respVO.setLatestGoods(buildGoodsRespList(latestPage.getList()));
        return respVO;
    }

    @Override
    public List<SecondCategoryDO> getEnabledCategories() {
        return secondCategoryMapper.selectListByStatus(SECOND_CATEGORY_STATUS_ENABLED);
    }

    @Override
    public PageResult<SecondGoodsDO> getGoodsPage(CampusPortalGoodsPageReqVO reqVO) {
        LambdaQueryWrapperX<SecondGoodsDO> queryWrapper = new LambdaQueryWrapperX<SecondGoodsDO>()
                .eq(SecondGoodsDO::getStatus, SECOND_GOODS_STATUS_ON_SALE)
                .eqIfPresent(SecondGoodsDO::getCategoryId, reqVO.getCategoryId())
                .orderByDesc(SecondGoodsDO::getPublishTime)
                .orderByDesc(SecondGoodsDO::getId);
        if (reqVO.getKeyword() != null && !reqVO.getKeyword().trim().isEmpty()) {
            queryWrapper.and(wrapper -> wrapper.like(SecondGoodsDO::getTitle, reqVO.getKeyword())
                    .or()
                    .like(SecondGoodsDO::getBrand, reqVO.getKeyword()));
        }
        return secondGoodsMapper.selectPage(reqVO, queryWrapper);
    }

    @Override
    public SecondGoodsDO getGoods(Long id) {
        SecondGoodsDO goods = secondGoodsMapper.selectById(id);
        if (goods == null || !SECOND_GOODS_STATUS_ON_SALE.equals(goods.getStatus())) {
            throw exception(SECOND_GOODS_NOT_EXISTS);
        }
        return goods;
    }

    @Override
    public List<CampusPortalGoodsRespVO> buildGoodsRespList(List<SecondGoodsDO> goodsList) {
        return goodsList.stream().map(this::buildGoodsResp).toList();
    }

    @Override
    public CampusPortalGoodsRespVO buildGoodsResp(SecondGoodsDO goods) {
        CampusPortalGoodsRespVO respVO = BeanUtils.toBean(goods, CampusPortalGoodsRespVO.class);
        CampusUserDO seller = campusUserMapper.selectById(goods.getSellerId());
        if (seller != null) {
            respVO.setSellerName(seller.getName());
        }
        SecondCategoryDO category = secondCategoryMapper.selectById(goods.getCategoryId());
        if (category != null) {
            respVO.setCategoryName(category.getName());
        }
        respVO.setImageUrls(secondGoodsImageMapper.selectListByGoodsId(goods.getId()).stream()
                .map(image -> image.getImageUrl())
                .toList());
        return respVO;
    }

    private CampusPortalGoodsPageReqVO buildLatestGoodsPageReqVO() {
        CampusPortalGoodsPageReqVO reqVO = new CampusPortalGoodsPageReqVO();
        reqVO.setPageNo(1);
        reqVO.setPageSize(6);
        return reqVO;
    }

    private void validateStudentNoUnique(String studentNo) {
        if (campusUserMapper.selectByStudentNo(studentNo) != null) {
            throw exception(CAMPUS_USER_STUDENT_NO_EXISTS);
        }
    }

}
