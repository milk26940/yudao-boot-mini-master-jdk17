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
          <el-form-item label="商品编号" prop="goodsId">
            <el-input-number v-model="formData.goodsId" :min="1" class="!w-full" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户编号" prop="userId">
            <el-input-number v-model="formData.userId" :min="1" class="!w-full" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="父评论编号" prop="parentId">
            <el-input-number v-model="formData.parentId" :min="1" class="!w-full" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-select v-model="formData.status" placeholder="请选择状态" class="!w-full">
              <el-option label="正常" :value="0" />
              <el-option label="隐藏" :value="1" />
              <el-option label="删除" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="评论内容" prop="content">
        <el-input v-model="formData.content" type="textarea" :rows="4" placeholder="请输入评论内容" />
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
import * as CommentApi from '@/api/campus/comment'

defineOptions({ name: 'CampusCommentForm' })

const { t } = useI18n()
const message = useMessage()

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formRef = ref()
const formData = ref<CommentApi.SecondCommentVO>({
  id: undefined,
  goodsId: 1,
  userId: 1,
  parentId: undefined,
  content: '',
  status: 0
})
const formRules = reactive<FormRules>({
  goodsId: [{ required: true, message: '商品编号不能为空', trigger: 'blur' }],
  userId: [{ required: true, message: '用户编号不能为空', trigger: 'blur' }],
  content: [{ required: true, message: '评论内容不能为空', trigger: 'blur' }],
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
      formData.value = await CommentApi.getSecondComment(id)
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
      await CommentApi.createSecondComment(data)
      message.success(t('common.createSuccess'))
    } else {
      await CommentApi.updateSecondComment(data)
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
    goodsId: 1,
    userId: 1,
    parentId: undefined,
    content: '',
    status: 0
  }
  formRef.value?.resetFields()
}
</script>
