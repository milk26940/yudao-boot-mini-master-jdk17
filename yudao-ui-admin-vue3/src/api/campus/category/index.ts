import request from '@/config/axios'

export interface SecondCategoryVO {
  id?: number
  name: string
  sort: number
  status: number
  createTime?: Date
}

export interface SecondCategoryPageReqVO extends PageParam {
  name?: string
  status?: number
}

export const getSecondCategoryPage = (params: SecondCategoryPageReqVO) => {
  return request.get({ url: '/system/second-category/page', params })
}

export const getSecondCategory = (id: number) => {
  return request.get<SecondCategoryVO>({ url: '/system/second-category/get?id=' + id })
}

export const createSecondCategory = (data: SecondCategoryVO) => {
  return request.post({ url: '/system/second-category/create', data })
}

export const updateSecondCategory = (data: SecondCategoryVO) => {
  return request.put({ url: '/system/second-category/update', data })
}

export const deleteSecondCategory = (id: number) => {
  return request.delete({ url: '/system/second-category/delete?id=' + id })
}

export const getSimpleSecondCategoryList = () => {
  return request.get<SecondCategoryVO[]>({ url: '/system/second-category/simple-list' })
}
