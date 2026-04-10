<template>
  <div class="campus-portal-page">
    <section class="portal-hero">
      <div class="portal-hero__content">
        <p class="portal-hero__eyebrow">Campus ReUse</p>
        <h1>校园二手交易，把闲置变成下一位同学的刚需。</h1>
        <p class="portal-hero__desc">
          面向校园场景的轻量交易门户，支持实名审核、分类浏览、求购互通和宿舍交易演示。
        </p>
        <div class="portal-hero__actions">
          <el-button type="primary" size="large" @click="router.push('/campus-portal/goods')">
            开始浏览
          </el-button>
          <el-button size="large" plain @click="router.push('/campus-portal/register')">
            学生注册
          </el-button>
        </div>
      </div>
      <div class="portal-hero__panel">
        <div class="hero-metric">
          <span>在售商品</span>
          <strong>{{ home.onSaleGoodsCount }}</strong>
        </div>
        <div class="hero-metric">
          <span>活跃求购</span>
          <strong>{{ home.activeWantedCount }}</strong>
        </div>
        <div class="hero-metric">
          <span>已审核用户</span>
          <strong>{{ home.approvedUserCount }}</strong>
        </div>
        <div class="hero-user" v-if="campusUser">
          <div class="hero-user__title">当前校园用户</div>
          <div class="hero-user__name">{{ campusUser.name }} / {{ campusUser.studentNo }}</div>
          <el-button text type="primary" @click="logoutCampusUser">退出校园端</el-button>
        </div>
        <div class="hero-user" v-else>
          <div class="hero-user__title">还没登录校园端？</div>
          <div class="hero-user__name">先注册审核，再用学号登录。</div>
          <el-button text type="primary" @click="router.push('/campus-portal/login')">去登录</el-button>
        </div>
      </div>
    </section>

    <section class="portal-section">
      <div class="portal-section__header">
        <h2>热门分类</h2>
        <span>先从高频校园商品开始浏览</span>
      </div>
      <div class="portal-category-grid">
        <button
          v-for="category in home.categories"
          :key="category.id"
          class="portal-category-card"
          @click="goGoods(category.id)"
        >
          <span>{{ category.name }}</span>
          <Icon icon="ep:arrow-right-bold" />
        </button>
      </div>
    </section>

    <section class="portal-section">
      <div class="portal-section__header">
        <h2>最新上架</h2>
        <span>适合答辩演示的第一屏商品流</span>
      </div>
      <el-row :gutter="16">
        <el-col v-for="goods in home.latestGoods" :key="goods.id" :xl="8" :md="12" :xs="24">
          <div class="portal-goods-card" @click="goGoods(undefined, goods.id)">
            <img :src="goods.imageUrls?.[0] || fallbackImage" alt="" />
            <div class="portal-goods-card__body">
              <div class="portal-goods-card__meta">
                <span>{{ goods.categoryName || '未分类' }}</span>
                <span>{{ goods.sellerName || '匿名卖家' }}</span>
              </div>
              <h3>{{ goods.title }}</h3>
              <p>{{ goods.description }}</p>
              <div class="portal-goods-card__price">
                <strong>￥{{ goods.price }}</strong>
                <span v-if="goods.originalPrice">原价 ￥{{ goods.originalPrice }}</span>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { getCampusPortalHome, type CampusPortalHomeVO } from '@/api/campus/portal'
import { getCampusUser, removeCampusUser } from '@/utils/campusAuth'

defineOptions({ name: 'CampusPortalHome' })

const router = useRouter()
const fallbackImage = 'https://dummyimage.com/600x400/e5e7eb/6b7280&text=Campus+Goods'
const campusUser = ref(getCampusUser())
const home = reactive<CampusPortalHomeVO>({
  onSaleGoodsCount: 0,
  activeWantedCount: 0,
  approvedUserCount: 0,
  categories: [],
  latestGoods: []
})

const getHome = async () => {
  Object.assign(home, await getCampusPortalHome())
}

const goGoods = (categoryId?: number, goodsId?: number) => {
  router.push({
    path: '/campus-portal/goods',
    query: {
      ...(categoryId ? { categoryId } : {}),
      ...(goodsId ? { goodsId } : {})
    }
  })
}

const logoutCampusUser = () => {
  removeCampusUser()
  campusUser.value = undefined
  ElMessage.success('校园端登录状态已退出')
}

onMounted(() => {
  getHome()
})
</script>

<style scoped lang="scss">
.campus-portal-page {
  min-height: 100vh;
  padding: 32px;
  background:
    radial-gradient(circle at top left, rgba(251, 191, 36, 0.24), transparent 28%),
    radial-gradient(circle at top right, rgba(34, 197, 94, 0.18), transparent 24%),
    linear-gradient(180deg, #fffdf8 0%, #f8fafc 100%);
}

.portal-hero {
  display: grid;
  grid-template-columns: minmax(0, 1.4fr) minmax(280px, 420px);
  gap: 20px;
  padding: 28px;
  background: rgba(255, 255, 255, 0.86);
  border: 1px solid rgba(15, 23, 42, 0.08);
  border-radius: 28px;
  box-shadow: 0 20px 60px rgba(148, 163, 184, 0.18);

  &__eyebrow {
    margin-bottom: 12px;
    font-size: 13px;
    font-weight: 700;
    letter-spacing: 0.24em;
    text-transform: uppercase;
    color: #0f766e;
  }

  h1 {
    max-width: 10ch;
    margin: 0;
    font-size: clamp(2rem, 4vw, 4rem);
    line-height: 1.05;
    color: #111827;
  }

  &__desc {
    max-width: 640px;
    margin: 18px 0 0;
    font-size: 16px;
    line-height: 1.8;
    color: #475569;
  }

  &__actions {
    display: flex;
    gap: 12px;
    margin-top: 24px;
  }

  &__panel {
    display: grid;
    gap: 14px;
    padding: 20px;
    background: linear-gradient(145deg, #0f172a, #1e293b);
    color: #fff;
    border-radius: 24px;
  }
}

.hero-metric,
.hero-user {
  padding: 16px;
  background: rgba(255, 255, 255, 0.08);
  border-radius: 18px;

  span,
  &__title {
    font-size: 13px;
    color: rgba(255, 255, 255, 0.72);
  }

  strong,
  &__name {
    display: block;
    margin-top: 8px;
    font-size: 28px;
    font-weight: 700;
    color: #fff;
  }
}

.hero-user__name {
  font-size: 16px;
}

.portal-section {
  margin-top: 28px;
  padding: 24px;
  background: rgba(255, 255, 255, 0.82);
  border: 1px solid rgba(15, 23, 42, 0.06);
  border-radius: 24px;

  &__header {
    display: flex;
    align-items: end;
    justify-content: space-between;
    gap: 12px;
    margin-bottom: 18px;

    h2 {
      margin: 0;
      font-size: 24px;
      color: #111827;
    }

    span {
      font-size: 13px;
      color: #64748b;
    }
  }
}

.portal-category-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 12px;
}

.portal-category-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 18px;
  background: linear-gradient(135deg, #f8fafc, #fef3c7);
  border: none;
  border-radius: 18px;
  font-size: 15px;
  font-weight: 600;
  color: #1f2937;
  cursor: pointer;
}

.portal-goods-card {
  overflow: hidden;
  margin-bottom: 16px;
  border-radius: 22px;
  background: #fff;
  box-shadow: 0 12px 36px rgba(148, 163, 184, 0.18);
  cursor: pointer;

  img {
    width: 100%;
    height: 220px;
    object-fit: cover;
    display: block;
  }

  &__body {
    padding: 18px;
  }

  &__meta {
    display: flex;
    justify-content: space-between;
    gap: 8px;
    font-size: 12px;
    color: #64748b;
  }

  h3 {
    margin: 12px 0 8px;
    font-size: 18px;
    color: #0f172a;
  }

  p {
    margin: 0;
    font-size: 13px;
    line-height: 1.7;
    color: #475569;
  }

  &__price {
    display: flex;
    align-items: baseline;
    gap: 10px;
    margin-top: 14px;

    strong {
      font-size: 24px;
      color: #ea580c;
    }

    span {
      font-size: 12px;
      color: #94a3b8;
      text-decoration: line-through;
    }
  }
}

@media (max-width: 960px) {
  .campus-portal-page {
    padding: 16px;
  }

  .portal-hero {
    grid-template-columns: 1fr;
  }
}
</style>
