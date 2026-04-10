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
          <el-form-item label="订单编号" prop="orderNo">
            <el-input v-model="formData.orderNo" placeholder="创建时可留空自动生成" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商品编号" prop="goodsId">
            <el-input-number v-model="formData.goodsId" :min="1" class="!w-full" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="卖家编号" prop="sellerId">
            <el-input-number v-model="formData.sellerId" :min="1" class="!w-full" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="买家编号" prop="buyerId">
            <el-input-number v-model="formData.buyerId" :min="1" class="!w-full" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="订单金额" prop="orderPrice">
            <el-input-number v-model="formData.orderPrice" :min="0" :precision="2" class="!w-full" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="交易方式" prop="deliveryType">
            <el-select v-model="formData.deliveryType" placeholder="请选择交易方式" class="!w-full">
              <el-option label="线下当面交易" :value="0" />
              <el-option label="送货到宿舍" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="交易地址" prop="deliveryAddress">
        <el-input v-model="formData.deliveryAddress" placeholder="请输入交易地址" />
      </el-form-item>
      <el-row :gutter="16">
        <el-col :span="12">
          <el-form-item label="订单状态" prop="status">
            <el-select v-model="formData.status" placeholder="请选择订单状态" class="!w-full">
              <el-option label="待确认" :value="0" />
              <el-option label="待交易" :value="1" />
              <el-option label="已完成" :value="2" />
              <el-option label="已取消" :value="3" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" type="textarea" :rows="3" placeholder="请输入备注" />
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
import * as OrderApi from '@/api/campus/order'

defineOptions({ name: 'CampusOrderForm' })

const { t } = useI18n()
const message = useMessage()

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formRef = ref()
const formData = ref<OrderApi.SecondOrderVO>({
  id: undefined,
  orderNo: '',
  goodsId: 1,
  sellerId: 1,
  buyerId: 1,
  orderPrice: 0,
  deliveryType: 0,
  deliveryAddress: '',
  remark: '',
  status: 0
})
const formRules = reactive<FormRules>({
  goodsId: [{ required: true, message: '商品编号不能为空', trigger: 'blur' }],
  sellerId: [{ required: true, message: '卖家编号不能为空', trigger: 'blur' }],
  buyerId: [{ required: true, message: '买家编号不能为空', trigger: 'blur' }],
  orderPrice: [{ required: true, message: '订单金额不能为空', trigger: 'blur' }],
  deliveryType: [{ required: true, message: '交易方式不能为空', trigger: 'change' }],
  status: [{ required: true, message: '订单状态不能为空', trigger: 'change' }]
})

const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (id) {
    formLoading.value = true
    try {
      formData.value = await OrderApi.getSecondOrder(id)
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
    if (!data.orderNo) delete data.orderNo
    if (formType.value === 'create') {
      await OrderApi.createSecondOrder(data)
      message.success(t('common.createSuccess'))
    } else {
      await OrderApi.updateSecondOrder(data)
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
    orderNo: '',
    goodsId: 1,
    sellerId: 1,
    buyerId: 1,
    orderPrice: 0,
    deliveryType: 0,
    deliveryAddress: '',
    remark: '',
    status: 0
  }
  formRef.value?.resetFields()
}
</script>
