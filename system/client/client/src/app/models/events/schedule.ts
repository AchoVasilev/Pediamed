export interface EventData {
    hours: string,
    intervals: number
}

export interface EventDataInput {
    date: string,
    eventData: EventData[]
}

export interface EventDataCreate {
    date: string,
    startHour: string,
    endHour: string,
    intervals: number
}

export interface CabinetResponse {
    id: number,
    name: string,
    city: string
    CabinetSchedule: CabinetSchedule
}

export interface CabinetSchedule {
    id: string,
    scheduleAppointments: ScheduleAppointment[],
    scheduleEvents: ScheduleEvent[]
}

export interface ScheduleAppointment {
    id: string,
    startDate: Date,
    endDate: Date,
}

export interface ScheduleEvent {
    id: string,
    startDate: Date,
    endDate: Date,
}