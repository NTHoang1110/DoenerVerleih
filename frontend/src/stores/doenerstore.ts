import type { IDoenerDTD } from './IDoener'
import type { IFrontendNachrichtEvent } from '@/services/IFrontendNachrichtEvent'
import { defineStore } from 'pinia'
import { reactive, ref } from 'vue'
import { Client } from '@stomp/stompjs'
import { useInfo } from '@/composables/useInfo'
import { useLogin } from './loginstore'

let stompClient: Client | null = null

export const useDoenerStore = defineStore('doenerstore', () => {
  const doenerdata = reactive({ ok: false, doenerliste: [] as IDoenerDTD[] })
  const { setzeInfo } = useInfo()
  const entlieheneDoener = ref<IDoenerDTD[]>([])
  const jwt = useLogin().loginState.jwt

  async function leihenDoener(doenerId: number, loginName: string): Promise<void> {
    try {
      const response = await fetch(`/api/entleihung`, {
        method: 'POST',
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${jwt}`
        },
        body: JSON.stringify({
          "doenerId": doenerId,
          "loginName": loginName
        })
      })
      if (!response.ok) throw new Error(await response.text())
    } catch (err: any) {
      setzeInfo('Döner konnte nicht entliehen werden: ' + err.message)
    }
  }

  async function ladeEntlieheneDoener(loginName: string): Promise<IDoenerDTD[]> {
    try {
      const response = await fetch(`/api/entleihung/${loginName}`, {
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${jwt}`
        },
      })
      if (!response.ok) { throw new Error(response.statusText) }
      else{
        entlieheneDoener.value = await response.json()
      }
      return entlieheneDoener.value
    } catch (err: any) {
      setzeInfo('Fehler beim Laden der entliehenen Döner')
      return []
    }
  }

  async function zurueckgebenDoener(doenerId: number, loginName: string): Promise<void> {
    try {
      const response = await fetch(`/api/entleihung/${loginName}/${doenerId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${jwt}`
        }
      })
      if (!response.ok) throw new Error(await response.text())
        await ladeEntlieheneDoener(loginName)
    } catch (err: any) {
      setzeInfo(err.message)
    }
  }

  async function updateDoenerListe(): Promise<void> {
    try {
      const response = await fetch('/api/doener', {
        headers: {
          "Authorization": `Bearer ${jwt}`
        },
      })
      if (!response.ok) throw new Error(response.statusText)

      doenerdata.doenerliste = await response.json()
      console.log(doenerdata.doenerliste)
      doenerdata.ok = true
      startDoenerLiveUpdate()
    } catch (err: any) {
      setzeInfo('Fehler beim Laden der Dönerliste')
    }
  }

  function startDoenerLiveUpdate(): void {
    if (stompClient && stompClient.connected) return

    stompClient = new Client({
      brokerURL: '/stompbroker',
      reconnectDelay: 5000,
      onConnect() {
        console.log('Verbunden mit STOMP Broker')

        stompClient?.subscribe('/topic/doener', (message) => {
          try {
            const event: IFrontendNachrichtEvent = JSON.parse(message.body)
            console.log('Event empfangen:', event)

            if (event.typ === 'DOENER') {
              updateDoenerListe()
            }
          } catch (err) {
            console.error('Fehler beim Verarbeiten der STOMP-Nachricht', err)
            setzeInfo('Live-Update Fehler')
          }
        })
      },
      onStompError(frame) {
        console.error('STOMP Fehler:', frame)
        setzeInfo('Verbindung zum Server fehlgeschlagen')
      }
    })

    stompClient.activate()
  }

  return { doenerdata, updateDoenerListe, ladeEntlieheneDoener, leihenDoener, zurueckgebenDoener, entlieheneDoener }
})
