import { createRouter, createWebHistory } from 'vue-router'
import DoenerListeView from '@/views/DoenerListeView.vue'
import LoginView from '@/views/LoginView.vue'
import { useLogin } from '@/stores/loginstore'
import DoenerEntleihungView from '@/views/DoenerEntleihungView.vue'

const routes = [
    {
        path: '/',
        redirect: '/doener'
    },
    {
        path: '/doener',
        component: DoenerListeView
    },
    {
        path: '/login',
        component: LoginView
    },
    {
        path: '/entleihen',
        component: DoenerEntleihungView
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach(async (to, from) => {
    const loggedIn = useLogin()
    if (!loggedIn.loginState.loggedIn && to.path !== '/login') {
        return { path: '/login' }
    }
})

export default router
