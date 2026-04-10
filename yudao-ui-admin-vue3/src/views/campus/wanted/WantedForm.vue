<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="820px">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="用户编号" prop="userId">
            <el-input-number v-model="formData.userId" :min="1" class="!w-full" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="求购状态" prop="status">
            <el-select v-model="formData.status" placeholder="请选择状态" class="!w-full">
              <el-option label="进行中" :value="0" />
              <el-option label="已完成" :value="1" />
              <el-option label="已关闭" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="求购标题" prop="title">
        <el-input v-model="formData.title" placeholder="请输入求购标题" />
      </el-form-item>
      <el-row :gutter="16">
        <el-col :span="8">
          <el-form-item label="品牌" prop="brand">
            <el-input v-model="formData.brand" placeholder="请输入品牌" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="型号" prop="model">
            <el-input v-model="formData.model" placeholder="请输入型号" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="期望价格" prop="expectedPrice">
            <el-input-number v-model="formData.expectedPrice" :min="0" :precision="2" class="!w-full" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="16">
        <el-col :span="8">
          <el-form-item label="联系电话" prop="contactMobile">
            <el-input v-model="formData.contactMobile" placeholder="请输入联系电话" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="联系QQ" prop="contactQq">
            <el-input v-model="formData.contactQq" placeholder="请输入联系QQ" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="联系微信" prop="contactWechat">
            <el-input v-model="formData.contactWechat" placeholder="请输入联系微信" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="需求描述" prop="description">
        <el-input v-model="formData.description" type="textarea" :rows="4" placeholder="请输入需求描述" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确定</el-button>
      <el-button @click="dialogVisible = false">取消</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import type { FormRules } from 'element-plus'
import * as WantedApi from '@/api/campus/wanted'

defineOptions({ name: 'CampusWantedForm' })

const { t } = useI18n()
const message = useMessage()

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formRef = ref()
const formData = ref<WantedApi.SecondWantedVO>({
  id: undefined,
  userId: 1,
  title: '',
  brand: '',
  model: '',
  expectedPrice: undefined,
  description: '',
  contactMobile: '',
  contactQq: '',
  contactWechat: '',
  status: 0
})
const formRules = reactive<FormRules>({
  userId: [{ required: true, message: '用户编号不能为空', trigger: 'blur' }],
  title: [{ required: true, message: '求购标题不能为空', trigger: 'blur' }],
  description: [{ required: true, message: '需求描述不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'change' }]
})

const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (id) {
    formLoading.value = true
    try {
      formData.value = await WantedApi.getSecondWanted(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open })

const emit = defineEmits(['success'])
const submitForm = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  formLoading.value = true
  try {
    const data = { ...formData.value }
    if (formType.value === 'create') {
      await WantedApi.createSecondWanted(data)
      message.success(t('common.createSuccess'))
    } else {
      await WantedApi.updateSecondWanted(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const resetForm = () => {
  formData.value = {
    id: undefined,
    userId: 1,
    title: '',
    brand: '',
    model: '',
    expectedPrice: undefined,
    description: '',
    contactMobile: '',
    contactQq: '',
    contactWechat: '',
    status: 0
  }
  formRef.value?.resetFields()
}
</script>
