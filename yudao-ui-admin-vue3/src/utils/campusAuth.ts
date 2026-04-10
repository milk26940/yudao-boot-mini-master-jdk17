import { useCache } from '@/hooks/web/useCache'

const { wsCache } = useCache()

const CampusUserKey = 'CAMPUS_PORTAL_USER'

export interface CampusPortalUserInfo {
  id: number
  studentNo: string
  name: string
  email: string
  mobile: string
  avatar?: string
  auditStatus: number
  status: number
}

export const getCampusUser = () => wsCache.get(CampusUserKey) as CampusPortalUserInfo | undefined

export const setCampusUser = (user: CampusPortalUserInfo) => {
  wsCache.set(CampusUserKey, user)
}

export const removeCampusUser = () => {
  wsCache.delete(CampusUserKey)
}
