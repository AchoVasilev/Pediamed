export interface UserAppointment {
    patientFirstName?: string,
    patientLastName?: string,
    appointmentCauseId: number,
    patientId?: string | null,
    eventId: any
}