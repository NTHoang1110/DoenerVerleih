<script setup lang="ts">
import { provide } from 'vue'
import { useInfo } from './composables/useInfo'
import { useLogin } from '@/stores/loginstore'
const { loginState } = useLogin()

const { info, loescheInfo, setzeInfo } = useInfo()

provide('setzeInfo', setzeInfo)
</script>

<template>
  <header class="app-header">
    <nav>
        <h1>Döner Shop</h1>
        <router-link v-if="loginState.loggedIn" to="/doener">Katalog</router-link>
        <router-link to="/entleihen">Deine Döner</router-link>
        <router-link to="/login" style="float:right;" v-if="loginState.loggedIn == true" >Logout</router-link>
        <router-link to="/login" style="float:right;" v-if="loginState.loggedIn == false" >Login</router-link>
      </nav>
  </header>

  <div v-if="info" class="notification">
    <span>{{ info }}</span>
    <button class="close-btn" @click="loescheInfo">×</button>
  </div>

  <main class="app-content">
    <router-view />
  </main>

  <footer class="app-footer">
    <p v-if="loginState.loggedIn">Eingeloggt als: {{ loginState.username }}</p>
  </footer>
</template>

<style>
nav {
  display: flex;
  justify-content: space-between;
}
/* Global header styling */
.app-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background: linear-gradient(135deg, #ff6b00, #ff8c42);
  color: white;
  padding: 1rem;
  text-align: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  height: 60px; /* Fixed height for consistent spacing */
}

.notification {
  position: fixed;
  top: 60px; /* Same as header height */
  left: 0;
  right: 0;
  background-color: #4caf50;
  color: white;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 999;
  animation: slideIn 0.3s ease-out;
}

@keyframes slideIn {
  from {
    transform: translateY(-100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.close-btn {
  background: none;
  border: none;
  color: white;
  font-size: 1.2rem;
  cursor: pointer;
  padding: 0 0.5rem;
  transition: transform 0.2s;
}

.close-btn:hover {
  transform: scale(1.2);
}

/* Adjust body and content spacing */
body {
  padding-top: 60px; /* Header height */
}

.app-content {
  padding-top: 1rem;
  max-width: 1200px;
  margin: 0 auto;
  padding: 1rem;
  padding-top: calc(60px + 1rem); /* Header height + extra space */
}

/* If notification is visible, add extra space */
body.has-notification {
  padding-top: calc(60px + 56px); /* Header + notification height */
}

.app-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(135deg, #ff6b00, #ff8c42);
  color: white;
  padding: 1rem;
  text-align: center;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  height: 60px; /* Matching header height */
  display: flex;
  align-items: center;
  justify-content: center;
}
.app-content {
  padding-bottom: 80px; /* Footer height + extra space */
}
</style>
