<template>
  <ContentWrap>
    <el-form ref="queryFormRef" :model="queryParams" :inline="true" class="-mb-15px" label-width="80px">
      <el-form-item label="商品编号" prop="goodsId">
        <el-input-number v-model="queryParams.goodsId" :min="1" class="!w-220px" />
      </el-form-item>
      <el-form-item label="用户编号" prop="userId">
        <el-input-number v-model="queryParams.userId" :min="1" class="!w-220px" />
      </el-form-item>
      <el-form-item label="父评论编号" prop="parentId">
        <el-input-number v-model="queryParams.parentId" :min="1" class="!w-220px" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable class="!w-220px">
          <el-option label="正常" :value="0" />
          <el-option label="隐藏" :value="1" />
          <el-option label="删除" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button type="primary" plain @click="openForm('create')" v-hasPermi="['system:second-comment:create']">
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="评论编号" align="center" prop="id" width="100" />
      <el-table-column label="商品编号" align="center" prop="goodsId" width="100" />
      <el-table-column label="用户编号" align="center" prop="userId" width="100" />
      <el-table-column label="父评论编号" align="center" prop="parentId" width="110">
        <template #default="scope">{{ scope.row.parentId ?? '-' }}</template>
      </el-table-column>
      <el-table-column label="评论内容" align="center" prop="content" min-width="240" show-overflow-tooltip />
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
          <el-button link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="['system:second-comment:update']">
            修改
          </el-button>
          <el-button link type="info" @click="openDetail(scope.row.id)">详情</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['system:second-comment:delete']">
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

  <CommentForm ref="formRef" @success="getList" />

  <Dialog v-model="detailVisible" title="评论详情" width="720px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="评论编号">{{ detail.id }}</el-descriptions-item>
      <el-descriptions-item label="商品编号">{{ detail.goodsId }}</el-descriptions-item>
      <el-descriptions-item label="用户编号">{{ detail.userId }}</el-descriptions-item>
      <el-descriptions-item label="父评论编号">{{ detail.parentId ?? '-' }}</el-descriptions-item>
      <el-descriptions-item label="状态">{{ getStatusLabel(detail.status) }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ detail.createTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="评论内容" :span="2">{{ detail.content }}</el-descriptions-item>
    </el-descriptions>
  </Dialog>
</template>

<script lang="ts" setup>
import { dateFormatter } from '@/utils/formatTime'
import * as CommentApi from '@/api/campus/comment'
import CommentForm from './CommentForm.vue'

defineOptions({ name: 'CampusComment' })

const { t } = useI18n()
const message = useMessage()

const loading = ref(false)
const total = ref(0)
const list = ref<CommentApi.SecondCommentVO[]>([])
const queryFormRef = ref()
const formRef = ref()
const detailVisible = ref(false)
const detail = ref<CommentApi.SecondCommentVO>({} as CommentApi.SecondCommentVO)
const queryParams = reactive<CommentApi.SecondCommentPageReqVO>({
  pageNo: 1,
  pageSize: 10,
  goodsId: undefined,
  userId: undefined,
  parentId: undefined,
  status: undefined
})

const getStatusLabel = (status?: number) => {
  if (status === 1) return '隐藏'
  if (status === 2) return '删除'
  return '正常'
}

const getStatusTag = (status?: number) => {
  if (status === 1) return 'warning'
  if (status === 2) return 'danger'
  return 'success'
}

const getList = async () => {
  loading.value = true
  try {
    const data = await CommentApi.getSecondCommentPage(queryParams)
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
  detail.value = await CommentApi.getSecondComment(id)
}

const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await CommentApi.deleteSecondComment(id)
    message.success(t('common.delSuccess'))
    await getList()
  } catch {}
}

onMounted(() => {
  getList()
})
</script>
