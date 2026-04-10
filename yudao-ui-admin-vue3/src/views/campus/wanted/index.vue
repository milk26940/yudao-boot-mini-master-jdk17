<template>
  <ContentWrap>
    <el-form ref="queryFormRef" :model="queryParams" :inline="true" class="-mb-15px" label-width="68px">
      <el-form-item label="求购标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入求购标题"
          clearable
          class="!w-220px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户编号" prop="userId">
        <el-input-number v-model="queryParams.userId" :min="1" class="!w-220px" />
      </el-form-item>
      <el-form-item label="品牌" prop="brand">
        <el-input
          v-model="queryParams.brand"
          placeholder="请输入品牌"
          clearable
          class="!w-220px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable class="!w-220px">
          <el-option label="进行中" :value="0" />
          <el-option label="已完成" :value="1" />
          <el-option label="已关闭" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['system:second-wanted:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="求购编号" align="center" prop="id" width="100" />
      <el-table-column label="标题" align="center" prop="title" min-width="180" show-overflow-tooltip />
      <el-table-column label="用户编号" align="center" prop="userId" width="100" />
      <el-table-column label="品牌" align="center" prop="brand" width="120" />
      <el-table-column label="型号" align="center" prop="model" width="120" />
      <el-table-column label="期望价格" align="center" prop="expectedPrice" width="100" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template #default="scope">
          <el-tag :type="getStatusTag(scope.row.status)">
            {{ getStatusLabel(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="200">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['system:second-wanted:update']"
          >
            修改
          </el-button>
          <el-button link type="info" @click="openDetail(scope.row.id)">详情</el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['system:second-wanted:delete']"
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

  <WantedForm ref="formRef" @success="getList" />

  <Dialog v-model="detailVisible" title="求购详情" width="760px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="标题">{{ detail.title }}</el-descriptions-item>
      <el-descriptions-item label="用户编号">{{ detail.userId }}</el-descriptions-item>
      <el-descriptions-item label="品牌">{{ detail.brand || '-' }}</el-descriptions-item>
      <el-descriptions-item label="型号">{{ detail.model || '-' }}</el-descriptions-item>
      <el-descriptions-item label="期望价格">{{ detail.expectedPrice ?? '-' }}</el-descriptions-item>
      <el-descriptions-item label="状态">{{ getStatusLabel(detail.status) }}</el-descriptions-item>
      <el-descriptions-item label="联系电话">{{ detail.contactMobile || '-' }}</el-descriptions-item>
      <el-descriptions-item label="联系QQ">{{ detail.contactQq || '-' }}</el-descriptions-item>
      <el-descriptions-item label="联系微信">{{ detail.contactWechat || '-' }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ detail.createTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="需求描述" :span="2">
        {{ detail.description }}
      </el-descriptions-item>
    </el-descriptions>
  </Dialog>
</template>

<script lang="ts" setup>
import { dateFormatter } from '@/utils/formatTime'
import * as WantedApi from '@/api/campus/wanted'
import WantedForm from './WantedForm.vue'

defineOptions({ name: 'CampusWanted' })

const { t } = useI18n()
const message = useMessage()

const loading = ref(false)
const total = ref(0)
const list = ref<WantedApi.SecondWantedVO[]>([])
const queryFormRef = ref()
const formRef = ref()
const detailVisible = ref(false)
const detail = ref<WantedApi.SecondWantedVO>({} as WantedApi.SecondWantedVO)
const queryParams = reactive<WantedApi.SecondWantedPageReqVO>({
  pageNo: 1,
  pageSize: 10,
  userId: undefined,
  title: undefined,
  brand: undefined,
  status: undefined
})

const getStatusLabel = (status?: number) => {
  if (status === 1) return '已完成'
  if (status === 2) return '已关闭'
  return '进行中'
}

const getStatusTag = (status?: number) => {
  if (status === 1) return 'success'
  if (status === 2) return 'info'
  return 'warning'
}

const getList = async () => {
  loading.value = true
  try {
    const data = await WantedApi.getSecondWantedPage(queryParams)
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
  detail.value = await WantedApi.getSecondWanted(id)
}

const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await WantedApi.deleteSecondWanted(id)
    message.success(t('common.delSuccess'))
    await getList()
  } catch {}
}

onMounted(() => {
  getList()
})
</script>
