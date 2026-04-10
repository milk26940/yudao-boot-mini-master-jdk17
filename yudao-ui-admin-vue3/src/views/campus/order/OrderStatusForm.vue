<template>
  <Dialog v-model="dialogVisible" title="更新订单状态" width="620px">
    <el-form ref="formRef" :model="formData" :rules="formRules" label-width="110px">
      <el-form-item label="订单编号">
        <el-input :model-value="orderNo" disabled />
      </el-form-item>
      <el-form-item label="订单状态" prop="status">
        <el-select v-model="formData.status" placeholder="请选择订单状态" class="!w-full">
          <el-option label="待确认" :value="0" />
          <el-option label="待交易" :value="1" />
          <el-option label="已完成" :value="2" />
          <el-option label="已取消" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="操作用户编号" prop="operateUserId">
        <el-input-number v-model="formData.operateUserId" :min="1" class="!w-full" />
      </el-form-item>
      <el-form-item label="操作角色" prop="operateRole">
        <el-select v-model="formData.operateRole" placeholder="请选择操作角色" class="!w-full">
          <el-option label="买家" :value="1" />
          <el-option label="卖家" :value="2" />
          <el-option label="管理员" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" type="textarea" :rows="3" placeholder="请输入状态变更备注" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitForm">确定</el-button>
      <el-button @click="dialogVisible = false">取消</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import type { FormRules } from 'element-plus'
import * as OrderApi from '@/api/campus/order'

defineOptions({ name: 'CampusOrderStatusForm' })

const message = useMessage()

const dialogVisible = ref(false)
const formRef = ref()
const orderNo = ref('')
const formData = ref<OrderApi.SecondOrderUpdateStatusReqVO>({
  id: 0,
  status: 0,
  operateUserId: 1,
  operateRole: 3,
  remark: ''
})
const formRules = reactive<FormRules>({
  status: [{ required: true, message: '订单状态不能为空', trigger: 'change' }],
  operateUserId: [{ required: true, message: '操作用户编号不能为空', trigger: 'blur' }],
  operateRole: [{ required: true, message: '操作角色不能为空', trigger: 'change' }]
})

const open = (row: OrderApi.SecondOrderVO) => {
  dialogVisible.value = true
  orderNo.value = row.orderNo || ''
  formData.value = {
    id: row.id!,
    status: row.status,
    operateUserId: row.buyerId,
    operateRole: 3,
    remark: row.remark || ''
  }
}
defineExpose({ open })

const emit = defineEmits(['success'])
const submitForm = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  await OrderApi.updateSecondOrderStatus(formData.value)
  message.success('订单状态更新成功')
  dialogVisible.value = false
  emit('success')
}
</script>
