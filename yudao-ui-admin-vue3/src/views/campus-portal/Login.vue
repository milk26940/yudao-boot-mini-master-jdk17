<template>
  <div class="campus-auth-page">
    <div class="campus-auth-card">
      <div class="campus-auth-card__intro">
        <p>Campus Login</p>
        <h1>用学号登录校园二手交易门户</h1>
        <span>审核通过后即可浏览商品、查看联系方式和参与后续交易演示。</span>
      </div>
      <el-form ref="formRef" :model="formData" :rules="rules" label-position="top">
        <el-form-item label="学号" prop="studentNo">
          <el-input v-model="formData.studentNo" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="formData.password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-button type="primary" class="w-full!" :loading="loading" @click="handleLogin">
          登录校园端
        </el-button>
        <div class="campus-auth-card__footer">
          <el-button text @click="router.push('/campus-portal')">返回首页</el-button>
          <el-button text type="primary" @click="router.push('/campus-portal/register')">
            去注册
          </el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { loginCampusUser } from '@/api/campus/portal'
import { setCampusUser } from '@/utils/campusAuth'

defineOptions({ name: 'CampusPortalLogin' })

const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)
const formData = reactive({
  studentNo: '',
  password: ''
})

const rules: FormRules = {
  studentNo: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) {
    return
  }
  loading.value = true
  try {
    const user = await loginCampusUser(formData)
    setCampusUser(user)
    ElMessage.success('校园端登录成功')
    router.push('/campus-portal/goods')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.campus-auth-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  background:
    radial-gradient(circle at top left, rgba(249, 115, 22, 0.16), transparent 24%),
    linear-gradient(135deg, #f8fafc, #fff7ed 48%, #ecfeff);
}

.campus-auth-card {
  width: min(460px, 100%);
  padding: 28px;
  background: rgba(255, 255, 255, 0.92);
  border: 1px solid rgba(15, 23, 42, 0.08);
  border-radius: 28px;
  box-shadow: 0 20px 60px rgba(148, 163, 184, 0.2);

  &__intro {
    margin-bottom: 20px;

    p {
      margin: 0 0 8px;
      font-size: 12px;
      font-weight: 700;
      letter-spacing: 0.18em;
      text-transform: uppercase;
      color: #ea580c;
    }

    h1 {
      margin: 0;
      font-size: 30px;
      line-height: 1.15;
      color: #0f172a;
    }

    span {
      display: block;
      margin-top: 10px;
      line-height: 1.7;
      color: #64748b;
    }
  }

  &__footer {
    display: flex;
    justify-content: space-between;
    margin-top: 10px;
  }
}
</style>
