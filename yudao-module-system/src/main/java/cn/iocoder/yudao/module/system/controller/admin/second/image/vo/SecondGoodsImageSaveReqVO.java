package cn.iocoder.yudao.module.system.controller.admin.second.image.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SecondGoodsImageSaveReqVO {

    private Long id;

    @NotNull(message = "商品编号不能为空")
    private Long goodsId;

    @NotBlank(message = "图片地址不能为空")
    @Size(max = 512, message = "图片地址长度不能超过 512 个字符")
    private String imageUrl;

    @NotNull(message = "排序不能为空")
    private Integer sort;

}
