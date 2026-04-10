import request from '@/config/axios'

export interface SecondOrderVO {
  id?: number
  orderNo?: string
  goodsId: number
  sellerId: number
  buyerId: number
  orderPrice: number
  deliveryType: number
  deliveryAddress?: string
  remark?: string
  status: number
  createTime?: Date
}

export interface SecondOrderPageReqVO extends PageParam {
  goodsId?: number
  sellerId?: number
  buyerId?: number
  status?: number
  orderNo?: string
}

export interface SecondOrderUpdateStatusReqVO {
  id: number
  status: number
  operateUserId: number
  operateRole: number
  remark?: string
}

export const getSecondOrderPage = (params: SecondOrderPageReqVO) => {
  return request.get({ url: '/system/second-order/page', params })
}

export const getSecondOrder = (id: number) => {
  return request.get<SecondOrderVO>({ url: '/system/second-order/get?id=' + id })
}

export const createSecondOrder = (data: SecondOrderVO) => {
  return request.post({ url: '/system/second-order/create', data })
}

export const updateSecondOrder = (data: SecondOrderVO) => {
  return request.put({ url: '/system/second-order/update', data })
}

export const deleteSecondOrder = (id: number) => {
  return request.delete({ url: '/system/second-order/delete?id=' + id })
}

export const updateSecondOrderStatus = (data: SecondOrderUpdateStatusReqVO) => {
  return request.put({ url: '/system/second-order/update-status', data })
}
