<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="720px">
    <el-descriptions :column="2" border class="mb-16px">
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
      <el-descriptions-item label="学生证" :span="2">
        <div class="flex flex-col gap-8px">
          <el-link :href="detail.studentCardUrl" target="_blank" type="primary">
            打开学生证图片
          </el-link>
          <el-image
            v-if="detail.studentCardUrl"
            :src="detail.studentCardUrl"
            fit="contain"
            class="h-160px w-260px border border-solid border-[var(--el-border-color)] rounded"
          />
        </div>
      </el-descriptions-item>
    </el-descriptions>

    <el-form ref="formRef" :model="formData" :rules="formRules" label-width="90px">
      <el-form-item label="审核结果" prop="auditStatus">
        <el-radio-group v-model="formData.auditStatus">
          <el-radio :value="1">通过</el-radio>
          <el-radio :value="2">驳回</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审核备注" prop="auditRemark">
        <el-input
          v-model="formData.auditRemark"
          type="textarea"
          :rows="3"
          placeholder="请输入审核备注"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitForm">提交审核</el-button>
      <el-button @click="dialogVisible = false">取消</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { CommonStatusEnum } from '@/utils/constants'
import type { FormRules } from 'element-plus'
import * as CampusUserApi from '@/api/campus/user'

defineOptions({ name: 'CampusUserAuditForm' })

const message = useMessage()

const dialogVisible = ref(false)
const dialogTitle = ref('用户审核')
const formRef = ref()
const detail = ref<CampusUserApi.CampusUserVO>({} as CampusUserApi.CampusUserVO)
const formData = ref<CampusUserApi.CampusUserAuditReqVO>({
  id: 0,
  auditStatus: 1,
  auditRemark: ''
})
const formRules = reactive<FormRules>({
  auditStatus: [{ required: true, message: '审核结果不能为空', trigger: 'change' }]
})

const getAuditStatusLabel = (status?: number) => {
  if (status === 1) return '已通过'
  if (status === 2) return '已驳回'
  return '待审核'
}

const open = async (id: number) => {
  dialogVisible.value = true
  detail.value = await CampusUserApi.getCampusUser(id)
  formData.value = {
    id,
    auditStatus: detail.value.auditStatus === 2 ? 2 : 1,
    auditRemark: detail.value.auditRemark || ''
  }
}
defineExpose({ open })

const emit = defineEmits(['success'])
const submitForm = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  await CampusUserApi.auditCampusUser(formData.value)
  message.success('审核成功')
  dialogVisible.value = false
  emit('success')
}
</script>
