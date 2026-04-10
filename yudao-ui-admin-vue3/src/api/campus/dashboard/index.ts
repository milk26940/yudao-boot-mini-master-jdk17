import request from '@/config/axios'

export interface CampusDashboardSummaryVO {
  userCount: number
  pendingAuditUserCount: number
  goodsCount: number
  onSaleGoodsCount: number
  wantedCount: number
  activeWantedCount: number
  commentCount: number
  orderCount: number
  pendingOrderCount: number
  completedOrderCount: number
}

export const getCampusDashboardSummary = () => {
  return request.get<CampusDashboardSummaryVO>({ url: '/system/campus-dashboard/summary' })
}
