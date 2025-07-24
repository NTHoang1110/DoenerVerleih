<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useInfo } from '@/composables/useInfo'
import { useLogin } from '@/stores/loginstore'
import { useRouter } from 'vue-router'

const { info, loescheInfo, setzeInfo } = useInfo()
const { loginState, login, logout } = useLogin()

const username = ref('')
const losung = ref('')

const router = useRouter()

onMounted(() => {
  logout()
})
async function handleLogin() {
  await login(username.value, losung.value)

  if (!loginState.loggedIn) {
    setzeInfo('Login fehlgeschlagen')
    losung.value = ''
  } else {
    setzeInfo('')
    router.push('/doener')
  }
}
</script>

<template>
  <div v-if="info" class="notification">
    <span>{{ info }}</span>
    <button class="close-btn" @click="loescheInfo">×</button>
  </div>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Username:</label><br />
        <input v-model="username" type="text" placeholder="Enter username" />
      </div>
      <div class="form-group">
        <label for="password">Password:</label><br />
        <input v-model="losung" type="password" placeholder="Enter password" />
      </div>
      <button type="submit">Login</button>
    </form>
  </div>
</template>
