<template>
  <div class="campus-auth-page">
    <div class="campus-auth-card">
      <div class="campus-auth-card__intro">
        <p>Campus Register</p>
        <h1>提交学生信息，等待管理员审核后登录</h1>
        <span>这版先用图片地址模拟学生证上传，最后联调时可以换成真实上传返回的 URL。</span>
      </div>
      <el-form ref="formRef" :model="formData" :rules="rules" label-position="top">
        <el-row :gutter="12">
          <el-col :md="12" :xs="24">
            <el-form-item label="学号" prop="studentNo">
              <el-input v-model="formData.studentNo" />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="formData.name" />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="formData.email" />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="手机号" prop="mobile">
              <el-input v-model="formData.mobile" />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="密码" prop="password">
              <el-input v-model="formData.password" show-password />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="性别">
              <el-select v-model="formData.gender">
                <el-option :value="1" label="男" />
                <el-option :value="2" label="女" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="QQ">
              <el-input v-model="formData.qq" />
            </el-form-item>
          </el-col>
          <el-col :md="12" :xs="24">
            <el-form-item label="微信号">
              <el-input v-model="formData.wechat" />
            </el-form-item>
          </el-col>
          <el-col :xs="24">
            <el-form-item label="学生证图片地址" prop="studentCardUrl">
              <el-input
                v-model="formData.studentCardUrl"
                placeholder="例如 http://127.0.0.1:48080/admin-api/infra/file/1/get/xxx.jpg"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-button type="primary" class="w-full!" :loading="loading" @click="handleRegister">
          提交注册申请
        </el-button>
        <div class="campus-auth-card__footer">
          <el-button text @click="router.push('/campus-portal')">返回首页</el-button>
          <el-button text type="primary" @click="router.push('/campus-portal/login')">
            去登录
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
import { registerCampusUser } from '@/api/campus/portal'

defineOptions({ name: 'CampusPortalRegister' })

const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)
const formData = reactive({
  studentNo: '',
  password: '',
  name: '',
  gender: 1,
  email: '',
  mobile: '',
  qq: '',
  wechat: '',
  avatar: '',
  studentCardUrl: ''
})

const rules: FormRules = {
  studentNo: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
  mobile: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  studentCardUrl: [{ required: true, message: '请输入学生证图片地址', trigger: 'blur' }]
}

const handleRegister = async () => {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) {
    return
  }
  loading.value = true
  try {
    await registerCampusUser(formData)
    ElMessage.success('注册申请已提交，请等待管理员审核')
    router.push('/campus-portal/login')
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
    radial-gradient(circle at top left, rgba(20, 184, 166, 0.18), transparent 22%),
    linear-gradient(135deg, #f8fafc, #f0fdf4 48%, #fff7ed);
}

.campus-auth-card {
  width: min(760px, 100%);
  padding: 28px;
  background: rgba(255, 255, 255, 0.94);
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
      color: #0f766e;
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
