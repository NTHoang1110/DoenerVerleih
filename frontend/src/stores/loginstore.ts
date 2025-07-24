import { defineStore } from 'pinia'
import { reactive } from 'vue'
import { useInfo } from '@/composables/useInfo'

const { setzeInfo } = useInfo()

export const useLogin = defineStore('login', () => {
    const loginState = reactive({ username: '', loggedIn: false, jwt: '' })
    function logout() {
        loginState.username = ""
        loginState.loggedIn = false
    }

    async function login(username: string, losung: string) {
        if (username == "" || losung == "") {
            logout()
        } else {
            const response = await fetch("/api/token", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    "username": username,
                    "losung": losung
                }),
                redirect: 'error'
            })

            if (response.ok) {
                const jwtoken = await response.text()
                loginState.username = username
                loginState.loggedIn = true
                loginState.jwt = jwtoken
                setzeInfo("")
            }
            else {
                logout()
                const fehlermeldung = await response.text()
                setzeInfo(fehlermeldung)
            }
        }
    }

    return {
        loginState,
        login,
        logout
    }
})
