import request from '@/config/axios'

export interface SecondGoodsVO {
  id?: number
  sellerId: number
  categoryId: number
  title: string
  brand?: string
  description: string
  price: number
  originalPrice?: number
  conditionLevel: number
  contactMobile?: string
  contactQq?: string
  contactWechat?: string
  status: number
  viewCount?: number
  publishTime?: string
  createTime?: Date
}

export interface SecondGoodsPageReqVO extends PageParam {
  sellerId?: number
  categoryId?: number
  title?: string
  status?: number
}

export const getSecondGoodsPage = (params: SecondGoodsPageReqVO) => {
  return request.get({ url: '/system/second-goods/page', params })
}

export const getSecondGoods = (id: number) => {
  return request.get<SecondGoodsVO>({ url: '/system/second-goods/get?id=' + id })
}

export const createSecondGoods = (data: SecondGoodsVO) => {
  return request.post({ url: '/system/second-goods/create', data })
}

export const updateSecondGoods = (data: SecondGoodsVO) => {
  return request.put({ url: '/system/second-goods/update', data })
}

export const deleteSecondGoods = (id: number) => {
  return request.delete({ url: '/system/second-goods/delete?id=' + id })
}
