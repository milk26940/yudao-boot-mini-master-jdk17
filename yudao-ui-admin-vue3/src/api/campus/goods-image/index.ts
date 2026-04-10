import request from '@/config/axios'

export interface SecondGoodsImageVO {
  id?: number
  goodsId: number
  imageUrl: string
  sort: number
  createTime?: Date
}

export const getGoodsImageListByGoodsId = (goodsId: number) => {
  return request.get<SecondGoodsImageVO[]>({
    url: '/system/second-goods-image/list-by-goods?goodsId=' + goodsId
  })
}
