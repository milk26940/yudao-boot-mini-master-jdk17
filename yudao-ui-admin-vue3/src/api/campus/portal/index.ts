import request from '@/config/axios'

export interface CampusPortalUserVO {
  id: number
  studentNo: string
  name: string
  email: string
  mobile: string
  avatar?: string
  auditStatus: number
  status: number
}

export interface CampusPortalRegisterVO {
  studentNo: string
  password: string
  name: string
  gender?: number
  email: string
  mobile: string
  qq?: string
  wechat?: string
  avatar?: string
  studentCardUrl: string
}

export interface CampusPortalLoginVO {
  studentNo: string
  password: string
}

export interface CampusPortalCategoryVO {
  id: number
  name: string
  sort: number
}

export interface CampusPortalGoodsVO {
  id: number
  sellerId: number
  sellerName?: string
  categoryId: number
  categoryName?: string
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
  viewCount: number
  publishTime?: string
  imageUrls: string[]
}

export interface CampusPortalGoodsPageReqVO extends PageParam {
  categoryId?: number
  keyword?: string
}

export interface CampusPortalHomeVO {
  onSaleGoodsCount: number
  activeWantedCount: number
  approvedUserCount: number
  categories: CampusPortalCategoryVO[]
  latestGoods: CampusPortalGoodsVO[]
}

const publicHeaders = {
  isToken: false
}

export const registerCampusUser = (data: CampusPortalRegisterVO) => {
  return request.post<number>({
    url: '/system/campus-portal/auth/register',
    data,
    headers: publicHeaders
  })
}

export const loginCampusUser = (data: CampusPortalLoginVO) => {
  return request.post<CampusPortalUserVO>({
    url: '/system/campus-portal/auth/login',
    data,
    headers: publicHeaders
  })
}

export const getCampusPortalHome = () => {
  return request.get<CampusPortalHomeVO>({
    url: '/system/campus-portal/home',
    headers: publicHeaders
  })
}

export const getCampusPortalCategories = () => {
  return request.get<CampusPortalCategoryVO[]>({
    url: '/system/campus-portal/category/list',
    headers: publicHeaders
  })
}

export const getCampusPortalGoodsPage = (params: CampusPortalGoodsPageReqVO) => {
  return request.get({
    url: '/system/campus-portal/goods/page',
    params,
    headers: publicHeaders
  })
}

export const getCampusPortalGoods = (id: number) => {
  return request.get<CampusPortalGoodsVO>({
    url: '/system/campus-portal/goods/get?id=' + id,
    headers: publicHeaders
  })
}
