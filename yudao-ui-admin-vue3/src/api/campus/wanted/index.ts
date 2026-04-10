import request from '@/config/axios'

export interface SecondWantedVO {
  id?: number
  userId: number
  title: string
  brand?: string
  model?: string
  expectedPrice?: number
  description: string
  contactMobile?: string
  contactQq?: string
  contactWechat?: string
  status: number
  createTime?: Date
}

export interface SecondWantedPageReqVO extends PageParam {
  userId?: number
  title?: string
  brand?: string
  status?: number
}

export const getSecondWantedPage = (params: SecondWantedPageReqVO) => {
  return request.get({ url: '/system/second-wanted/page', params })
}

export const getSecondWanted = (id: number) => {
  return request.get<SecondWantedVO>({ url: '/system/second-wanted/get?id=' + id })
}

export const createSecondWanted = (data: SecondWantedVO) => {
  return request.post({ url: '/system/second-wanted/create', data })
}

export const updateSecondWanted = (data: SecondWantedVO) => {
  return request.put({ url: '/system/second-wanted/update', data })
}

export const deleteSecondWanted = (id: number) => {
  return request.delete({ url: '/system/second-wanted/delete?id=' + id })
}
