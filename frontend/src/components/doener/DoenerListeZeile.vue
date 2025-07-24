<script setup lang="ts">
import { ref } from 'vue'
import type { IDoenerDTD } from '@/stores/IDoener';
import { useDoenerStore } from '@/stores/doenerstore';
import { useLogin } from '@/stores/loginstore';

const { loginState } = useLogin()

const doenerStore = useDoenerStore()

defineProps<{
  doener: IDoenerDTD
}>();
const expanded = ref(false)

function toggle() {
  expanded.value = !expanded.value
}
function vegetarizitaetText(v: number): string {
  return ['nicht vegetarisch', 'vegetarisch', 'vegan'][v] || 'unbekannt'
}

function leihen(doenerId:number, loginName:string){
  doenerStore.leihenDoener(doenerId, loginName)
}
</script>

<template>
  <tr>
    <td>{{ doener.id }}</td>
    <td>{{ doener.bezeichnung }}</td>
    <td>{{ doener.preis }}</td>
    <td>{{ vegetarizitaetText(doener.vegetarizitaet) }}</td>
    <td>{{ doener.verfuegbar }}</td>
    <td><button @click="toggle">?</button></td>
    <td><button @click="leihen(doener.id, loginState.username)">leihen</button></td>
  </tr>
  <tr v-if="expanded">
    <td colspan="5">
      <ul>
        <li v-for="z in doener.zutaten" :key="z.ean">
          <img :src="`/images/zutaten/${z.ean}.png`" alt="icon" style="width: 32px; height: 32px; margin-right: 8px;" />
          <a :href="`https://de.wikipedia.org/wiki/Special:Search/${z.name}`" target="_blank" rel="noopener noreferrer">
            {{ z.name }}
          </a>
        </li>
      </ul>
    </td>
  </tr>
</template>
