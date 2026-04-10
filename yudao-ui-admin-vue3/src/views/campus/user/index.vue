<template>
  <ContentWrap>
    <el-form
      ref="queryFormRef"
      :model="queryParams"
      :inline="true"
      class="-mb-15px"
      label-width="68px"
    >
      <el-form-item label="学号" prop="studentNo">
        <el-input
          v-model="queryParams.studentNo"
          placeholder="请输入学号"
          clearable
          class="!w-220px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          class="!w-220px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机号"
          clearable
          class="!w-220px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核状态" prop="auditStatus">
        <el-select
          v-model="queryParams.auditStatus"
          placeholder="请选择审核状态"
          clearable
          class="!w-220px"
        >
          <el-option label="待审核" :value="0" />
          <el-option label="已通过" :value="1" />
          <el-option label="已驳回" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="账号状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable class="!w-220px">
          <el-option label="启用" :value="CommonStatusEnum.ENABLE" />
          <el-option label="禁用" :value="CommonStatusEnum.DISABLE" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['system:campus-user:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="用户编号" align="center" prop="id" width="100" />
      <el-table-column label="学号" align="center" prop="studentNo" width="140" />
      <el-table-column label="姓名" align="center" prop="name" width="120" />
      <el-table-column label="邮箱" align="center" prop="email" min-width="180" show-overflow-tooltip />
      <el-table-column label="手机号" align="center" prop="mobile" width="120" />
      <el-table-column label="审核状态" align="center" prop="auditStatus" width="100">
        <template #default="scope">
          <el-tag :type="getAuditStatusTag(scope.row.auditStatus)">
            {{ getAuditStatusLabel(scope.row.auditStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="账号状态" align="center" prop="status" width="120">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="CommonStatusEnum.ENABLE"
            :inactive-value="CommonStatusEnum.DISABLE"
            :disabled="!checkPermi(['system:campus-user:update'])"
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="注册时间"
        align="center"
        prop="createTime"
        width="180"
        :formatter="dateFormatter"
      />
      <el-table-column label="操作" align="center" width="220">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openAudit(scope.row.id)"
            v-hasPermi="['system:campus-user:audit']"
          >
            审核
          </el-button>
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['system:campus-user:update']"
          >
            修改
          </el-button>
          <el-button link type="info" @click="openDetail(scope.row.id)">详情</el-button>
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

  <CampusUserForm ref="formRef" @success="getList" />
  <CampusUserAuditForm ref="auditFormRef" @success="getList" />

  <Dialog v-model="detailVisible" title="用户详情" width="720px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="学号">{{ detail.studentNo }}</el-descriptions-item>
      <el-descriptions-item label="姓名">{{ detail.name }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ detail.email || '-' }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{ detail.mobile || '-' }}</el-descriptions-item>
      <el-descriptions-item label="QQ">{{ detail.qq || '-' }}</el-descriptions-item>
      <el-descriptions-item label="微信">{{ detail.wechat || '-' }}</el-descriptions-item>
      <el-descriptions-item label="审核状态">
        {{ getAuditStatusLabel(detail.auditStatus) }}
      </el-descriptions-item>
      <el-descriptions-item label="账号状态">
        {{ detail.status === CommonStatusEnum.ENABLE ? '启用' : '禁用' }}
      </el-descriptions-item>
      <el-descriptions-item label="头像地址" :span="2">
        <el-link v-if="detail.avatar" :href="detail.avatar" target="_blank" type="primary">
          {{ detail.avatar }}
        </el-link>
        <span v-else>-</span>
      </el-descriptions-item>
      <el-descriptions-item label="学生证地址" :span="2">
        <div class="flex flex-col gap-8px">
          <el-link v-if="detail.studentCardUrl" :href="detail.studentCardUrl" target="_blank" type="primary">
            {{ detail.studentCardUrl }}
          </el-link>
          <span v-else>-</span>
          <el-image
            v-if="detail.studentCardUrl"
            :src="detail.studentCardUrl"
            fit="contain"
            class="h-160px w-260px border border-solid border-[var(--el-border-color)] rounded"
          />
        </div>
      </el-descriptions-item>
      <el-descriptions-item label="审核备注" :span="2">
        {{ detail.auditRemark || '-' }}
      </el-descriptions-item>
    </el-descriptions>
  </Dialog>
</template>

<script lang="ts" setup>
import { checkPermi } from '@/utils/permission'
import { CommonStatusEnum } from '@/utils/constants'
import { dateFormatter } from '@/utils/formatTime'
import * as CampusUserApi from '@/api/campus/user'
import CampusUserForm from './CampusUserForm.vue'
import CampusUserAuditForm from './CampusUserAuditForm.vue'

defineOptions({ name: 'CampusUser' })

const { t } = useI18n()
const message = useMessage()

const loading = ref(false)
const total = ref(0)
const list = ref<CampusUserApi.CampusUserVO[]>([])
const queryFormRef = ref()
const formRef = ref()
const auditFormRef = ref()
const detailVisible = ref(false)
const detail = ref<CampusUserApi.CampusUserVO>({} as CampusUserApi.CampusUserVO)
const queryParams = reactive<CampusUserApi.CampusUserPageReqVO>({
  pageNo: 1,
  pageSize: 10,
  studentNo: undefined,
  name: undefined,
  mobile: undefined,
  auditStatus: undefined,
  status: undefined
})

const getAuditStatusLabel = (status?: number) => {
  if (status === 1) return '已通过'
  if (status === 2) return '已驳回'
  return '待审核'
}

const getAuditStatusTag = (status?: number) => {
  if (status === 1) return 'success'
  if (status === 2) return 'danger'
  return 'warning'
}

const getList = async () => {
  loading.value = true
  try {
    const data = await CampusUserApi.getCampusUserPage(queryParams)
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

const openAudit = (id: number) => {
  auditFormRef.value.open(id)
}

const openDetail = async (id: number) => {
  detailVisible.value = true
  detail.value = await CampusUserApi.getCampusUser(id)
}

const handleStatusChange = async (row: CampusUserApi.CampusUserVO) => {
  try {
    await CampusUserApi.updateCampusUserStatus({ id: row.id!, status: row.status })
    message.success(t('common.updateSuccess'))
  } catch {
    row.status = row.status === CommonStatusEnum.ENABLE ? CommonStatusEnum.DISABLE : CommonStatusEnum.ENABLE
  }
}

onMounted(() => {
  getList()
})
</script>
