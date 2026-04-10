<template>
  <div class="campus-goods-page">
    <header class="campus-goods-header">
      <div>
        <p>Campus Goods</p>
        <h1>校园闲置商品广场</h1>
      </div>
      <div class="campus-goods-header__actions">
        <el-button plain @click="router.push('/campus-portal')">返回首页</el-button>
        <el-button type="primary" @click="router.push('/campus-portal/login')">
          {{ campusUser ? '切换账号' : '登录校园端' }}
        </el-button>
      </div>
    </header>

    <section class="campus-goods-toolbar">
      <el-input
        v-model="query.keyword"
        placeholder="按商品名称或品牌搜索"
        clearable
        @keyup.enter="getGoodsPage"
      >
        <template #append>
          <el-button @click="getGoodsPage">搜索</el-button>
        </template>
      </el-input>
      <el-radio-group v-model="query.categoryId" @change="getGoodsPage">
        <el-radio-button :value="undefined">全部分类</el-radio-button>
        <el-radio-button v-for="category in categories" :key="category.id" :value="category.id">
          {{ category.name }}
        </el-radio-button>
      </el-radio-group>
    </section>

    <el-row :gutter="16">
      <el-col v-for="goods in goodsList" :key="goods.id" :xl="8" :md="12" :xs="24">
        <div class="campus-goods-card" @click="openGoods(goods.id)">
          <img :src="goods.imageUrls?.[0] || fallbackImage" alt="" />
          <div class="campus-goods-card__body">
            <div class="campus-goods-card__meta">
              <span>{{ goods.categoryName || '未分类' }}</span>
              <span>{{ goods.sellerName || '匿名卖家' }}</span>
            </div>
            <h3>{{ goods.title }}</h3>
            <p>{{ goods.description }}</p>
            <div class="campus-goods-card__bottom">
              <strong>￥{{ goods.price }}</strong>
              <span>{{ goods.viewCount || 0 }} 次浏览</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <div class="mt-20px flex justify-center">
      <el-pagination
        v-model:current-page="query.pageNo"
        v-model:page-size="query.pageSize"
        :total="total"
        layout="prev, pager, next"
        @current-change="getGoodsPage"
      />
    </div>

    <el-drawer v-model="detailVisible" title="商品详情" size="540px">
      <template v-if="currentGoods">
        <el-carousel v-if="currentGoods.imageUrls?.length" height="280px">
          <el-carousel-item v-for="image in currentGoods.imageUrls" :key="image">
            <img :src="image" class="w-full h-full object-cover rounded-12px" alt="" />
          </el-carousel-item>
        </el-carousel>
        <div class="mt-18px">
          <h2 class="m-0 text-24px">{{ currentGoods.title }}</h2>
          <p class="mt-10px text-14px leading-7 text-[var(--el-text-color-secondary)]">
            {{ currentGoods.description }}
          </p>
          <div class="mt-16px text-28px font-700 text-[#ea580c]">￥{{ currentGoods.price }}</div>
          <div class="mt-16px grid gap-10px text-14px">
            <div>分类：{{ currentGoods.categoryName || '未分类' }}</div>
            <div>卖家：{{ currentGoods.sellerName || '匿名卖家' }}</div>
            <div>手机号：{{ currentGoods.contactMobile || '未填写' }}</div>
            <div>QQ：{{ currentGoods.contactQq || '未填写' }}</div>
            <div>微信：{{ currentGoods.contactWechat || '未填写' }}</div>
          </div>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import {
  getCampusPortalCategories,
  getCampusPortalGoods,
  getCampusPortalGoodsPage,
  type CampusPortalCategoryVO,
  type CampusPortalGoodsVO
} from '@/api/campus/portal'
import { getCampusUser } from '@/utils/campusAuth'

defineOptions({ name: 'CampusPortalGoods' })

const router = useRouter()
const route = useRoute()
const campusUser = getCampusUser()
const fallbackImage = 'https://dummyimage.com/600x400/e5e7eb/6b7280&text=Campus+Goods'
const categories = ref<CampusPortalCategoryVO[]>([])
const goodsList = ref<CampusPortalGoodsVO[]>([])
const total = ref(0)
const detailVisible = ref(false)
const currentGoods = ref<CampusPortalGoodsVO>()
const query = reactive({
  pageNo: 1,
  pageSize: 9,
  categoryId: undefined as number | undefined,
  keyword: ''
})

const getCategories = async () => {
  categories.value = await getCampusPortalCategories()
}

const getGoodsPage = async () => {
  const page = await getCampusPortalGoodsPage(query)
  goodsList.value = page.list
  total.value = page.total
}

const openGoods = async (id: number) => {
  currentGoods.value = await getCampusPortalGoods(id)
  detailVisible.value = true
}

onMounted(async () => {
  if (route.query.categoryId) {
    query.categoryId = Number(route.query.categoryId)
  }
  await Promise.all([getCategories(), getGoodsPage()])
  if (route.query.goodsId) {
    openGoods(Number(route.query.goodsId))
  }
})
</script>

<style scoped lang="scss">
.campus-goods-page {
  min-height: 100vh;
  padding: 28px;
  background:
    radial-gradient(circle at top right, rgba(14, 165, 233, 0.12), transparent 18%),
    linear-gradient(180deg, #fffdf7, #f8fafc);
}

.campus-goods-header {
  display: flex;
  align-items: end;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 18px;

  p {
    margin: 0 0 8px;
    font-size: 12px;
    font-weight: 700;
    letter-spacing: 0.18em;
    text-transform: uppercase;
    color: #0f766e;
  }

  h1 {
    margin: 0;
    font-size: 36px;
    color: #0f172a;
  }

  &__actions {
    display: flex;
    gap: 10px;
  }
}

.campus-goods-toolbar {
  display: grid;
  gap: 14px;
  padding: 18px;
  margin-bottom: 20px;
  background: rgba(255, 255, 255, 0.92);
  border: 1px solid rgba(15, 23, 42, 0.06);
  border-radius: 20px;
}

.campus-goods-card {
  overflow: hidden;
  margin-bottom: 16px;
  background: rgba(255, 255, 255, 0.94);
  border: 1px solid rgba(15, 23, 42, 0.06);
  border-radius: 22px;
  box-shadow: 0 12px 36px rgba(148, 163, 184, 0.16);
  cursor: pointer;

  img {
    width: 100%;
    height: 220px;
    object-fit: cover;
  }

  &__body {
    padding: 18px;
  }

  &__meta,
  &__bottom {
    display: flex;
    justify-content: space-between;
    gap: 8px;
    font-size: 12px;
    color: #64748b;
  }

  h3 {
    margin: 12px 0 8px;
    color: #0f172a;
  }

  p {
    margin: 0;
    line-height: 1.7;
    color: #475569;
  }

  &__bottom {
    align-items: center;
    margin-top: 14px;

    strong {
      font-size: 24px;
      color: #ea580c;
    }
  }
}

@media (max-width: 960px) {
  .campus-goods-page {
    padding: 16px;
  }

  .campus-goods-header {
    flex-direction: column;
    align-items: start;
  }
}
</style>
