import { computed, reactive, readonly } from 'vue'

const state = reactive<{ info: string }>({ info: 'Willkommen, Döner-Community!' })

export function useInfo() {
  function loescheInfo(): void {
    state.info = ''
  }
  function setzeInfo(msg: string): void {
    state.info = msg
  }

  return {
    info: readonly(computed(() => state.info)),
    loescheInfo,
    setzeInfo,
  }
}
