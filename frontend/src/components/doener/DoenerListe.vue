<script setup lang="ts">
import type { IDoenerDTD } from '@/stores/IDoener';
import DoenerListeZeile from './DoenerListeZeile.vue';

defineProps<{
  doener: IDoenerDTD[]
}>()

</script>
<template>
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
      <DoenerListeZeile v-for="d in doener" :key="d.id" :doener="d" />
    </tbody>
  </table>
</template>
