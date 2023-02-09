export interface EventData {
    hours: string,
    intervals: number
}

export interface EventDataInput {
    date: string,
    eventData: EventData[],
    cabinetName: string
}

export interface EventDataCreate {
    startDateTime: string,
    endDateTime: string,
    intervals: number
    cabinetName: string
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

export interface EventRes {
    id: string,
    startDate: Date,
    endDate: Date,
}