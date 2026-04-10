package cn.iocoder.yudao.module.system.dal.dataobject.second;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@TableName("campus_user")
@KeySequence("campus_user_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class CampusUserDO extends BaseDO {

    @TableId
    private Long id;

    private String studentNo;

    private String password;

    private String name;

    private Integer gender;

    private String email;

    private String mobile;

    private String qq;

    private String wechat;

    private String avatar;

    private String studentCardUrl;

    private Integer auditStatus;

    private String auditRemark;

    private Integer status;

    private String loginIp;

    private LocalDateTime loginTime;

}
