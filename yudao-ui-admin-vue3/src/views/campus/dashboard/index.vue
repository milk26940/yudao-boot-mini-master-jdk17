<template>
  <div class="campus-dashboard">
    <ContentWrap>
      <div class="mb-16px flex items-center justify-between">
        <div>
          <div class="text-20px font-700 text-[var(--el-text-color-primary)]">校园二手交易管理看板</div>
          <div class="mt-6px text-13px text-[var(--el-text-color-secondary)]">
            聚合查看用户审核、商品流转、求购活跃度和订单成交情况。
          </div>
        </div>
        <el-button type="primary" @click="getSummary">
          <Icon icon="ep:refresh" class="mr-5px" />
          刷新数据
        </el-button>
      </div>

      <el-row :gutter="16">
        <el-col v-for="item in summaryCards" :key="item.title" :xl="6" :lg="8" :md="12" :sm="12" :xs="24">
          <el-card shadow="hover" class="mb-16px">
            <el-skeleton :loading="loading" animated>
              <div class="flex items-start justify-between">
                <div>
                  <div class="text-13px text-[var(--el-text-color-secondary)]">{{ item.title }}</div>
                  <div class="mt-10px text-28px font-700 text-[var(--el-text-color-primary)]">
                    {{ item.value }}
                  </div>
                  <div class="mt-10px text-12px text-[var(--el-text-color-secondary)]">
                    {{ item.tip }}
                  </div>
                </div>
                <div :class="['dashboard-icon', item.iconClass]">
                  <Icon :icon="item.icon" :size="22" />
                </div>
              </div>
            </el-skeleton>
          </el-card>
        </el-col>
      </el-row>
    </ContentWrap>

    <el-row :gutter="16">
      <el-col :xl="14" :lg="14" :md="24" :sm="24" :xs="24">
        <ContentWrap class="mb-16px">
          <template #header>
            <div class="flex items-center justify-between">
              <span>快捷入口</span>
              <span class="text-12px text-[var(--el-text-color-secondary)]">常用管理页面直达</span>
            </div>
          </template>
          <el-row :gutter="12">
            <el-col v-for="action in quickActions" :key="action.title" :span="12">
              <div class="quick-action" @click="handleShortcut(action.path)">
                <div class="quick-action__icon">
                  <Icon :icon="action.icon" :size="20" />
                </div>
                <div>
                  <div class="text-14px font-600 text-[var(--el-text-color-primary)]">{{ action.title }}</div>
                  <div class="mt-4px text-12px text-[var(--el-text-color-secondary)]">{{ action.desc }}</div>
                </div>
              </div>
            </el-col>
          </el-row>
        </ContentWrap>
      </el-col>

      <el-col :xl="10" :lg="10" :md="24" :sm="24" :xs="24">
        <ContentWrap class="mb-16px">
          <template #header>
            <div class="flex items-center justify-between">
              <span>待办提醒</span>
              <span class="text-12px text-[var(--el-text-color-secondary)]">优先处理的后台事项</span>
            </div>
          </template>
          <el-skeleton :loading="loading" animated>
            <div class="todo-item">
              <div>
                <div class="todo-item__title">待审核注册</div>
                <div class="todo-item__desc">优先核验学生身份信息，避免影响登录流程。</div>
              </div>
              <el-tag type="danger">{{ summary.pendingAuditUserCount }}</el-tag>
            </div>
            <div class="todo-item">
              <div>
                <div class="todo-item__title">待确认订单</div>
                <div class="todo-item__desc">需要卖家确认或后台关注的交易订单数量。</div>
              </div>
              <el-tag type="warning">{{ summary.pendingOrderCount }}</el-tag>
            </div>
            <div class="todo-item">
              <div>
                <div class="todo-item__title">活跃求购</div>
                <div class="todo-item__desc">当前仍在进行中的求购需求，可作为推荐和撮合依据。</div>
              </div>
              <el-tag type="success">{{ summary.activeWantedCount }}</el-tag>
            </div>
          </el-skeleton>
        </ContentWrap>

        <ContentWrap>
          <template #header>
            <div class="flex items-center justify-between">
              <span>业务概览</span>
              <span class="text-12px text-[var(--el-text-color-secondary)]">适合答辩时快速讲解业务闭环</span>
            </div>
          </template>
          <el-skeleton :loading="loading" animated>
            <div class="overview-list">
              <div class="overview-item">
                <span>审核通过用户占比</span>
                <strong>{{ getRate(summary.userCount - summary.pendingAuditUserCount, summary.userCount) }}</strong>
              </div>
              <div class="overview-item">
                <span>在售商品占比</span>
                <strong>{{ getRate(summary.onSaleGoodsCount, summary.goodsCount) }}</strong>
              </div>
              <div class="overview-item">
                <span>求购活跃占比</span>
                <strong>{{ getRate(summary.activeWantedCount, summary.wantedCount) }}</strong>
              </div>
              <div class="overview-item">
                <span>订单完成占比</span>
                <strong>{{ getRate(summary.completedOrderCount, summary.orderCount) }}</strong>
              </div>
            </div>
          </el-skeleton>
        </ContentWrap>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import * as CampusDashboardApi from '@/api/campus/dashboard'

defineOptions({ name: 'CampusDashboard' })

const router = useRouter()
const loading = ref(false)
const summary = reactive<CampusDashboardApi.CampusDashboardSummaryVO>({
  userCount: 0,
  pendingAuditUserCount: 0,
  goodsCount: 0,
  onSaleGoodsCount: 0,
  wantedCount: 0,
  activeWantedCount: 0,
  commentCount: 0,
  orderCount: 0,
  pendingOrderCount: 0,
  completedOrderCount: 0
})

const summaryCards = computed(() => [
  {
    title: '校园用户',
    value: summary.userCount,
    tip: `待审核 ${summary.pendingAuditUserCount} 人`,
    icon: 'ep:user',
    iconClass: 'bg-blue'
  },
  {
    title: '二手商品',
    value: summary.goodsCount,
    tip: `在售 ${summary.onSaleGoodsCount} 件`,
    icon: 'ep:goods',
    iconClass: 'bg-orange'
  },
  {
    title: '求购信息',
    value: summary.wantedCount,
    tip: `进行中 ${summary.activeWantedCount} 条`,
    icon: 'ep:shopping-cart-full',
    iconClass: 'bg-green'
  },
  {
    title: '评论总量',
    value: summary.commentCount,
    tip: '包含评论和回复内容',
    icon: 'ep:chat-line-square',
    iconClass: 'bg-cyan'
  },
  {
    title: '订单总量',
    value: summary.orderCount,
    tip: `待确认 ${summary.pendingOrderCount} 单`,
    icon: 'ep:tickets',
    iconClass: 'bg-purple'
  },
  {
    title: '已完成订单',
    value: summary.completedOrderCount,
    tip: '体现系统交易闭环完成度',
    icon: 'ep:circle-check',
    iconClass: 'bg-emerald'
  }
])

const quickActions = [
  { title: '注册审核', desc: '查看学生注册申请并完成审核', icon: 'ep:user-filled', path: '/campus/user' },
  { title: '商品管理', desc: '集中维护在售和已售商品', icon: 'ep:goods', path: '/campus/goods' },
  { title: '求购管理', desc: '查看用户主动发布的求购需求', icon: 'ep:shopping-cart-full', path: '/campus/wanted' },
  { title: '订单管理', desc: '跟踪交易流转和订单状态', icon: 'ep:tickets', path: '/campus/order' }
]

const getSummary = async () => {
  loading.value = true
  try {
    Object.assign(summary, await CampusDashboardApi.getCampusDashboardSummary())
  } finally {
    loading.value = false
  }
}

const handleShortcut = (path: string) => {
  router.push(path)
}

const getRate = (value: number, total: number) => {
  if (!total) {
    return '0%'
  }
  return `${((value / total) * 100).toFixed(0)}%`
}

onMounted(() => {
  getSummary()
})
</script>

<style lang="scss" scoped>
.campus-dashboard {
  .dashboard-icon {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 44px;
    height: 44px;
    border-radius: 14px;
    color: #fff;
  }

  .bg-blue {
    background: linear-gradient(135deg, #3b82f6, #2563eb);
  }

  .bg-orange {
    background: linear-gradient(135deg, #f59e0b, #ea580c);
  }

  .bg-green {
    background: linear-gradient(135deg, #22c55e, #16a34a);
  }

  .bg-cyan {
    background: linear-gradient(135deg, #06b6d4, #0891b2);
  }

  .bg-purple {
    background: linear-gradient(135deg, #8b5cf6, #7c3aed);
  }

  .bg-emerald {
    background: linear-gradient(135deg, #10b981, #059669);
  }

  .quick-action {
    display: flex;
    gap: 12px;
    align-items: center;
    padding: 14px;
    margin-bottom: 12px;
    border: 1px solid var(--el-border-color-light);
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.2s ease;

    &:hover {
      border-color: var(--el-color-primary-light-5);
      background: var(--el-fill-color-light);
    }

    &__icon {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 38px;
      height: 38px;
      border-radius: 10px;
      background: var(--el-color-primary-light-9);
      color: var(--el-color-primary);
    }
  }

  .todo-item {
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    padding: 12px 0;
    border-bottom: 1px solid var(--el-border-color-lighter);

    &:last-child {
      border-bottom: none;
      padding-bottom: 0;
    }

    &__title {
      font-size: 14px;
      font-weight: 600;
      color: var(--el-text-color-primary);
    }

    &__desc {
      margin-top: 6px;
      font-size: 12px;
      color: var(--el-text-color-secondary);
      line-height: 1.6;
    }
  }

  .overview-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
  }

  .overview-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 14px;
    border-radius: 10px;
    background: var(--el-fill-color-light);
    font-size: 14px;

    strong {
      font-size: 16px;
      color: var(--el-color-primary);
    }
  }
}
</style>
