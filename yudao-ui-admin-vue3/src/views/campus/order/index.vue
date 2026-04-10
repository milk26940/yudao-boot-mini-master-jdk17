<template>
  <ContentWrap>
    <el-form ref="queryFormRef" :model="queryParams" :inline="true" class="-mb-15px" label-width="80px">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          class="!w-220px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品编号" prop="goodsId">
        <el-input-number v-model="queryParams.goodsId" :min="1" class="!w-220px" />
      </el-form-item>
      <el-form-item label="卖家编号" prop="sellerId">
        <el-input-number v-model="queryParams.sellerId" :min="1" class="!w-220px" />
      </el-form-item>
      <el-form-item label="买家编号" prop="buyerId">
        <el-input-number v-model="queryParams.buyerId" :min="1" class="!w-220px" />
      </el-form-item>
      <el-form-item label="订单状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择订单状态" clearable class="!w-220px">
          <el-option label="待确认" :value="0" />
          <el-option label="待交易" :value="1" />
          <el-option label="已完成" :value="2" />
          <el-option label="已取消" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button type="primary" plain @click="openForm('create')" v-hasPermi="['system:second-order:create']">
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="订单编号" align="center" prop="orderNo" min-width="180" show-overflow-tooltip />
      <el-table-column label="商品编号" align="center" prop="goodsId" width="100" />
      <el-table-column label="卖家编号" align="center" prop="sellerId" width="100" />
      <el-table-column label="买家编号" align="center" prop="buyerId" width="100" />
      <el-table-column label="订单金额" align="center" prop="orderPrice" width="100" />
      <el-table-column label="交易方式" align="center" prop="deliveryType" width="120">
        <template #default="scope">{{ getDeliveryLabel(scope.row.deliveryType) }}</template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="status" width="100">
        <template #default="scope">
          <el-tag :type="getStatusTag(scope.row.status)">{{ getStatusLabel(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="250">
        <template #default="scope">
          <el-button link type="primary" @click="openStatusForm(scope.row)" v-hasPermi="['system:second-order:update']">
            更新状态
          </el-button>
          <el-button link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="['system:second-order:update']">
            修改
          </el-button>
          <el-button link type="info" @click="openDetail(scope.row.id)">详情</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row.id)" v-hasPermi="['system:second-order:delete']">
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

  <OrderForm ref="formRef" @success="getList" />
  <OrderStatusForm ref="statusFormRef" @success="getList" />

  <Dialog v-model="detailVisible" title="订单详情" width="760px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="订单编号">{{ detail.orderNo }}</el-descriptions-item>
      <el-descriptions-item label="订单状态">{{ getStatusLabel(detail.status) }}</el-descriptions-item>
      <el-descriptions-item label="商品编号">{{ detail.goodsId }}</el-descriptions-item>
      <el-descriptions-item label="卖家编号">{{ detail.sellerId }}</el-descriptions-item>
      <el-descriptions-item label="买家编号">{{ detail.buyerId }}</el-descriptions-item>
      <el-descriptions-item label="订单金额">{{ detail.orderPrice }}</el-descriptions-item>
      <el-descriptions-item label="交易方式">{{ getDeliveryLabel(detail.deliveryType) }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ detail.createTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="交易地址" :span="2">{{ detail.deliveryAddress || '-' }}</el-descriptions-item>
      <el-descriptions-item label="备注" :span="2">{{ detail.remark || '-' }}</el-descriptions-item>
      <el-descriptions-item label="状态日志" :span="2">
        当前后端暂未提供日志查询接口，后续接入后可在此展示订单状态变更记录。
      </el-descriptions-item>
    </el-descriptions>
  </Dialog>
</template>

<script lang="ts" setup>
import { dateFormatter } from '@/utils/formatTime'
import * as OrderApi from '@/api/campus/order'
import OrderForm from './OrderForm.vue'
import OrderStatusForm from './OrderStatusForm.vue'

defineOptions({ name: 'CampusOrder' })

const { t } = useI18n()
const message = useMessage()

const loading = ref(false)
const total = ref(0)
const list = ref<OrderApi.SecondOrderVO[]>([])
const queryFormRef = ref()
const formRef = ref()
const statusFormRef = ref()
const detailVisible = ref(false)
const detail = ref<OrderApi.SecondOrderVO>({} as OrderApi.SecondOrderVO)
const queryParams = reactive<OrderApi.SecondOrderPageReqVO>({
  pageNo: 1,
  pageSize: 10,
  goodsId: undefined,
  sellerId: undefined,
  buyerId: undefined,
  status: undefined,
  orderNo: undefined
})

const getStatusLabel = (status?: number) => {
  if (status === 1) return '待交易'
  if (status === 2) return '已完成'
  if (status === 3) return '已取消'
  return '待确认'
}

const getStatusTag = (status?: number) => {
  if (status === 1) return 'warning'
  if (status === 2) return 'success'
  if (status === 3) return 'info'
  return 'danger'
}

const getDeliveryLabel = (deliveryType?: number) => {
  return deliveryType === 1 ? '送货到宿舍' : '线下当面交易'
}

const getList = async () => {
  loading.value = true
  try {
    const data = await OrderApi.getSecondOrderPage(queryParams)
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

const openStatusForm = (row: OrderApi.SecondOrderVO) => {
  statusFormRef.value.open(row)
}

const openDetail = async (id: number) => {
  detailVisible.value = true
  detail.value = await OrderApi.getSecondOrder(id)
}

const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await OrderApi.deleteSecondOrder(id)
    message.success(t('common.delSuccess'))
    await getList()
  } catch {}
}

onMounted(() => {
  getList()
})
</script>
