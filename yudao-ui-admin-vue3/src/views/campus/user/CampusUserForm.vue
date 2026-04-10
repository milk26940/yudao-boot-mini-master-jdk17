<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="760px">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="学号" prop="studentNo">
            <el-input v-model="formData.studentNo" placeholder="请输入学号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="formData.name" placeholder="请输入姓名" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input v-model="formData.password" show-password type="password" placeholder="请输入密码" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="formData.gender" placeholder="请选择性别">
              <el-option label="未知" :value="0" />
              <el-option label="男" :value="1" />
              <el-option label="女" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="formData.email" placeholder="请输入邮箱" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号" prop="mobile">
            <el-input v-model="formData.mobile" placeholder="请输入手机号" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="QQ" prop="qq">
            <el-input v-model="formData.qq" placeholder="请输入 QQ" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="微信" prop="wechat">
            <el-input v-model="formData.wechat" placeholder="请输入微信号" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="头像地址" prop="avatar">
        <el-input v-model="formData.avatar" placeholder="请输入头像地址" />
      </el-form-item>
      <el-form-item label="学生证地址" prop="studentCardUrl">
        <el-input v-model="formData.studentCardUrl" placeholder="请输入学生证图片地址" />
      </el-form-item>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="审核状态" prop="auditStatus">
            <el-select v-model="formData.auditStatus" placeholder="请选择审核状态">
              <el-option label="待审核" :value="0" />
              <el-option label="已通过" :value="1" />
              <el-option label="已驳回" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="账号状态" prop="status">
            <el-radio-group v-model="formData.status">
              <el-radio :value="CommonStatusEnum.ENABLE">启用</el-radio>
              <el-radio :value="CommonStatusEnum.DISABLE">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="审核备注" prop="auditRemark">
        <el-input v-model="formData.auditRemark" type="textarea" :rows="3" placeholder="请输入审核备注" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确定</el-button>
      <el-button @click="dialogVisible = false">取消</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { CommonStatusEnum } from '@/utils/constants'
import type { FormRules } from 'element-plus'
import * as CampusUserApi from '@/api/campus/user'

defineOptions({ name: 'CampusUserForm' })

const { t } = useI18n()
const message = useMessage()

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formRef = ref()
const formData = ref<CampusUserApi.CampusUserVO>({
  id: undefined,
  studentNo: '',
  password: '',
  name: '',
  gender: 0,
  email: '',
  mobile: '',
  qq: '',
  wechat: '',
  avatar: '',
  studentCardUrl: '',
  auditStatus: 0,
  auditRemark: '',
  status: CommonStatusEnum.ENABLE
})
const formRules = reactive<FormRules>({
  studentNo: [{ required: true, message: '学号不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
  studentCardUrl: [{ required: true, message: '学生证地址不能为空', trigger: 'blur' }],
  auditStatus: [{ required: true, message: '审核状态不能为空', trigger: 'change' }],
  status: [{ required: true, message: '账号状态不能为空', trigger: 'change' }]
})

const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (id) {
    formLoading.value = true
    try {
      const user = await CampusUserApi.getCampusUser(id)
      formData.value = {
        ...user,
        password: ''
      }
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
      await CampusUserApi.createCampusUser(data)
      message.success(t('common.createSuccess'))
    } else {
      await CampusUserApi.updateCampusUser(data)
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
    studentNo: '',
    password: '',
    name: '',
    gender: 0,
    email: '',
    mobile: '',
    qq: '',
    wechat: '',
    avatar: '',
    studentCardUrl: '',
    auditStatus: 0,
    auditRemark: '',
    status: CommonStatusEnum.ENABLE
  }
  formRef.value?.resetFields()
}
</script>
