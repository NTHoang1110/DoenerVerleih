<script setup lang="ts">
import { onMounted, computed, ref } from 'vue'
import DoenerListe from '@/components/doener/DoenerListe.vue'

import {  useDoenerStore } from '@/stores/doenerstore';
import { useInfo } from '@/composables/useInfo';

const { info, loescheInfo, setzeInfo } = useInfo()
const doenerStore = useDoenerStore()

onMounted(() => {
  try {
    doenerStore.updateDoenerListe()
  } catch (error) {
    setzeInfo(`Fehler: ${error}`)
  }
})
const doenerliste = computed(() => doenerStore.doenerdata.doenerliste)

const eingabe = ref("")

const filteredDoenerliste = computed(() => {
  const query = eingabe.value.toLowerCase()

  return doenerStore.doenerdata.doenerliste.filter((item)=>{
    const match = item.bezeichnung.toLowerCase().includes(query)

    const matchZutat = item.zutaten?.some((zutat) =>
      zutat.name.toLowerCase().includes(query))

    return match || matchZutat
  })
})

</script>

<style scoped></style>

<template>
  <div>
    <div >
      <div >
        <div>
          <p class="title">Unser aktuelles Dönerangebot</p>
          <p class="heading">Nur wenige Klicks trennen Sie von Ihrem Traumdöner</p>
        </div>
        <div>
          <input type="text" v-model="eingabe">
          <button @click="eingabe = '' ">Reset</button>
        </div>
      </div>
    </div>
    <div >
      <img src="@/assets/doener-ritter-tiere.png" />
    </div>
  </div>
  <div>
    <DoenerListe :doener="filteredDoenerliste"></DoenerListe>
  </div>
</template>
