import request from '@/config/axios'

export interface SecondCommentVO {
  id?: number
  goodsId: number
  userId: number
  parentId?: number
  content: string
  status: number
  createTime?: Date
}

export interface SecondCommentPageReqVO extends PageParam {
  goodsId?: number
  userId?: number
  parentId?: number
  status?: number
}

export const getSecondCommentPage = (params: SecondCommentPageReqVO) => {
  return request.get({ url: '/system/second-comment/page', params })
}

export const getSecondComment = (id: number) => {
  return request.get<SecondCommentVO>({ url: '/system/second-comment/get?id=' + id })
}

export const createSecondComment = (data: SecondCommentVO) => {
  return request.post({ url: '/system/second-comment/create', data })
}

export const updateSecondComment = (data: SecondCommentVO) => {
  return request.put({ url: '/system/second-comment/update', data })
}

export const deleteSecondComment = (id: number) => {
  return request.delete({ url: '/system/second-comment/delete?id=' + id })
}
