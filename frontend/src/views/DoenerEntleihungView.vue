<script setup lang="ts">
import type { IDoenerDTD } from '@/stores/IDoener';
import { useDoenerStore } from '@/stores/doenerstore';
import { useLogin } from '@/stores/loginstore'
import { onMounted, ref } from 'vue';

const userName = useLogin().loginState.username
const doenerStore = useDoenerStore()

onMounted(async () => {
    await doenerStore.ladeEntlieheneDoener(userName)
})

</script>

<template>
    <div>
    <div >
      <div >
        <div>
          <p class="title">Unser aktuelles Dönerangebot</p>
          <p class="heading">Nur wenige Klicks trennen Sie von Ihrem Traumdöner</p>
        </div>
      </div>
    </div>
    <div >
      <img src="@/assets/doener-ritter-tiere.png" />
    </div>
  </div>
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Bezeichnung</th>
        <th>Preis</th>
        <th>Vegetarizität</th>
        <th>Bestand</th>
        <th></th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="d in doenerStore.entlieheneDoener" >
        <td>{{ d.id }}</td>
        <td>{{ d.bezeichnung }}</td>
        <td><button @click="doenerStore.zurueckgebenDoener(d.id,userName)">zurückgeben</button></td>
      </tr>
    </tbody>
  </table>
</template>