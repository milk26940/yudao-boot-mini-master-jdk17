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
          <el-form-item label="卖家编号" prop="sellerId">
            <el-input-number v-model="formData.sellerId" :min="1" class="!w-full" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="分类" prop="categoryId">
            <el-select v-model="formData.categoryId" placeholder="请选择分类" class="!w-full">
              <el-option
                v-for="item in categoryList"
                :key="item.id"
                :label="item.name"
                :value="item.id!"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="商品标题" prop="title">
        <el-input v-model="formData.title" placeholder="请输入商品标题" />
      </el-form-item>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="品牌" prop="brand">
            <el-input v-model="formData.brand" placeholder="请输入品牌" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="新旧程度" prop="conditionLevel">
            <el-input-number v-model="formData.conditionLevel" :min="1" :max="10" class="!w-full" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="售价" prop="price">
            <el-input-number v-model="formData.price" :min="0" :precision="2" class="!w-full" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="原价" prop="originalPrice">
            <el-input-number
              v-model="formData.originalPrice"
              :min="0"
              :precision="2"
              class="!w-full"
            />
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
          <el-form-item label="联系 QQ" prop="contactQq">
            <el-input v-model="formData.contactQq" placeholder="请输入 QQ" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="联系微信" prop="contactWechat">
            <el-input v-model="formData.contactWechat" placeholder="请输入微信" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="商品状态" prop="status">
            <el-select v-model="formData.status" placeholder="请选择状态" class="!w-full">
              <el-option label="在售" :value="0" />
              <el-option label="已售" :value="1" />
              <el-option label="下架" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发布时间" prop="publishTime">
            <el-date-picker
              v-model="formData.publishTime"
              type="datetime"
              value-format="YYYY-MM-DD HH:mm:ss"
              placeholder="请选择发布时间"
              class="!w-full"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="商品描述" prop="description">
        <el-input v-model="formData.description" type="textarea" :rows="4" placeholder="请输入商品描述" />
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
import * as CategoryApi from '@/api/campus/category'
import * as GoodsApi from '@/api/campus/goods'

defineOptions({ name: 'CampusGoodsForm' })

const { t } = useI18n()
const message = useMessage()

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formRef = ref()
const categoryList = ref<CategoryApi.SecondCategoryVO[]>([])
const formData = ref<GoodsApi.SecondGoodsVO>({
  id: undefined,
  sellerId: 1,
  categoryId: 0,
  title: '',
  brand: '',
  description: '',
  price: 0,
  originalPrice: undefined,
  conditionLevel: 9,
  contactMobile: '',
  contactQq: '',
  contactWechat: '',
  status: 0,
  publishTime: ''
})
const formRules = reactive<FormRules>({
  sellerId: [{ required: true, message: '卖家编号不能为空', trigger: 'blur' }],
  categoryId: [{ required: true, message: '分类不能为空', trigger: 'change' }],
  title: [{ required: true, message: '商品标题不能为空', trigger: 'blur' }],
  description: [{ required: true, message: '商品描述不能为空', trigger: 'blur' }],
  price: [{ required: true, message: '售价不能为空', trigger: 'blur' }],
  conditionLevel: [{ required: true, message: '新旧程度不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '商品状态不能为空', trigger: 'change' }]
})

const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  categoryList.value = await CategoryApi.getSimpleSecondCategoryList()
  if (id) {
    formLoading.value = true
    try {
      formData.value = await GoodsApi.getSecondGoods(id)
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
      await GoodsApi.createSecondGoods(data)
      message.success(t('common.createSuccess'))
    } else {
      await GoodsApi.updateSecondGoods(data)
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
    sellerId: 1,
    categoryId: 0,
    title: '',
    brand: '',
    description: '',
    price: 0,
    originalPrice: undefined,
    conditionLevel: 9,
    contactMobile: '',
    contactQq: '',
    contactWechat: '',
    status: 0,
    publishTime: ''
  }
  formRef.value?.resetFields()
}
</script>
