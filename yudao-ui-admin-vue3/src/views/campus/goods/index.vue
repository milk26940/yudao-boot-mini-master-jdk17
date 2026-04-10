<template>
  <ContentWrap>
    <el-form ref="queryFormRef" :model="queryParams" :inline="true" class="-mb-15px" label-width="68px">
      <el-form-item label="商品标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入商品标题"
          clearable
          class="!w-220px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select v-model="queryParams.categoryId" placeholder="请选择分类" clearable class="!w-220px">
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.name"
            :value="item.id!"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="卖家编号" prop="sellerId">
        <el-input-number v-model="queryParams.sellerId" :min="1" class="!w-220px" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable class="!w-220px">
          <el-option label="在售" :value="0" />
          <el-option label="已售" :value="1" />
          <el-option label="下架" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['system:second-goods:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="商品编号" align="center" prop="id" width="100" />
      <el-table-column label="商品标题" align="center" prop="title" min-width="180" show-overflow-tooltip />
      <el-table-column label="分类编号" align="center" prop="categoryId" width="100" />
      <el-table-column label="卖家编号" align="center" prop="sellerId" width="100" />
      <el-table-column label="售价" align="center" prop="price" width="100" />
      <el-table-column label="新旧程度" align="center" prop="conditionLevel" width="100" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope">
          <el-tag :type="getStatusTag(scope.row.status)">
            {{ getStatusLabel(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="publishTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="200">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['system:second-goods:update']"
          >
            修改
          </el-button>
          <el-button link type="info" @click="openDetail(scope.row.id)">详情</el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['system:second-goods:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <GoodsForm ref="formRef" @success="getList" />

  <Dialog v-model="detailVisible" title="商品详情" width="900px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="标题">{{ detail.title }}</el-descriptions-item>
      <el-descriptions-item label="品牌">{{ detail.brand || '-' }}</el-descriptions-item>
      <el-descriptions-item label="卖家编号">{{ detail.sellerId }}</el-descriptions-item>
      <el-descriptions-item label="分类编号">{{ detail.categoryId }}</el-descriptions-item>
      <el-descriptions-item label="售价">{{ detail.price }}</el-descriptions-item>
      <el-descriptions-item label="原价">{{ detail.originalPrice ?? '-' }}</el-descriptions-item>
      <el-descriptions-item label="新旧程度">{{ detail.conditionLevel }}</el-descriptions-item>
      <el-descriptions-item label="浏览次数">{{ detail.viewCount ?? 0 }}</el-descriptions-item>
      <el-descriptions-item label="联系电话">{{ detail.contactMobile || '-' }}</el-descriptions-item>
      <el-descriptions-item label="联系QQ">{{ detail.contactQq || '-' }}</el-descriptions-item>
      <el-descriptions-item label="联系微信">{{ detail.contactWechat || '-' }}</el-descriptions-item>
      <el-descriptions-item label="状态">{{ getStatusLabel(detail.status) }}</el-descriptions-item>
      <el-descriptions-item label="商品描述" :span="2">
        {{ detail.description }}
      </el-descriptions-item>
      <el-descriptions-item label="商品图片" :span="2">
        <div class="flex flex-wrap gap-12px">
          <el-empty v-if="imageList.length === 0" description="暂无图片" />
          <el-image
            v-for="image in imageList"
            v-else
            :key="image.id"
            :src="image.imageUrl"
            fit="cover"
            class="h-120px w-120px rounded border border-solid border-[var(--el-border-color)]"
          />
        </div>
      </el-descriptions-item>
    </el-descriptions>
  </Dialog>
</template>

<script lang="ts" setup>
import { dateFormatter } from '@/utils/formatTime'
import * as CategoryApi from '@/api/campus/category'
import * as GoodsApi from '@/api/campus/goods'
import * as GoodsImageApi from '@/api/campus/goods-image'
import GoodsForm from './GoodsForm.vue'

defineOptions({ name: 'CampusGoods' })

const { t } = useI18n()
const message = useMessage()

const loading = ref(false)
const total = ref(0)
const list = ref<GoodsApi.SecondGoodsVO[]>([])
const categoryList = ref<CategoryApi.SecondCategoryVO[]>([])
const queryFormRef = ref()
const formRef = ref()
const detailVisible = ref(false)
const detail = ref<GoodsApi.SecondGoodsVO>({} as GoodsApi.SecondGoodsVO)
const imageList = ref<GoodsImageApi.SecondGoodsImageVO[]>([])
const queryParams = reactive<GoodsApi.SecondGoodsPageReqVO>({
  pageNo: 1,
  pageSize: 10,
  sellerId: undefined,
  categoryId: undefined,
  title: undefined,
  status: undefined
})

const getStatusLabel = (status?: number) => {
  if (status === 1) return '已售'
  if (status === 2) return '下架'
  return '在售'
}

const getStatusTag = (status?: number) => {
  if (status === 1) return 'success'
  if (status === 2) return 'info'
  return 'warning'
}

const getList = async () => {
  loading.value = true
  try {
    const data = await GoodsApi.getSecondGoodsPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

const resetQuery = () => {
  queryFormRef.value?.resetFields()
  handleQuery()
}

const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

const openDetail = async (id: number) => {
  detailVisible.value = true
  detail.value = await GoodsApi.getSecondGoods(id)
  imageList.value = await GoodsImageApi.getGoodsImageListByGoodsId(id)
}

const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await GoodsApi.deleteSecondGoods(id)
    message.success(t('common.delSuccess'))
    await getList()
  } catch {}
}

onMounted(async () => {
  categoryList.value = await CategoryApi.getSimpleSecondCategoryList()
  getList()
})
</script>
