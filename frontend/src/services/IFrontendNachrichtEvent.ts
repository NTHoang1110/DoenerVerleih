export interface IFrontendNachrichtEvent {
    typ: 'DOENER'
    id: number
    operation: 'CREATE' | 'UPDATE' | 'DELETE' | 'BOOKED' | 'RETURNED'
}