import request from '@/config/axios'

export interface CampusUserVO {
  id?: number
  studentNo: string
  password?: string
  name: string
  gender?: number
  email?: string
  mobile?: string
  qq?: string
  wechat?: string
  avatar?: string
  studentCardUrl: string
  auditStatus: number
  auditRemark?: string
  status: number
  loginIp?: string
  loginTime?: Date
  createTime?: Date
}

export interface CampusUserPageReqVO extends PageParam {
  studentNo?: string
  name?: string
  mobile?: string
  auditStatus?: number
  status?: number
}

export interface CampusUserUpdateStatusReqVO {
  id: number
  status: number
}

export interface CampusUserAuditReqVO {
  id: number
  auditStatus: number
  auditRemark?: string
}

export const getCampusUserPage = (params: CampusUserPageReqVO) => {
  return request.get({ url: '/system/campus-user/page', params })
}

export const getCampusUser = (id: number) => {
  return request.get<CampusUserVO>({ url: '/system/campus-user/get?id=' + id })
}

export const createCampusUser = (data: CampusUserVO) => {
  return request.post({ url: '/system/campus-user/create', data })
}

export const updateCampusUser = (data: CampusUserVO) => {
  return request.put({ url: '/system/campus-user/update', data })
}

export const updateCampusUserStatus = (data: CampusUserUpdateStatusReqVO) => {
  return request.put({ url: '/system/campus-user/update-status', data })
}

export const auditCampusUser = (data: CampusUserAuditReqVO) => {
  return request.put({ url: '/system/campus-user/audit', data })
}
