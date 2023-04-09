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
    cabinetSchedule: CabinetSchedule,
    workDays: number[]
}

export interface CabinetScheduleResponse {
    scheduleId: string
}

export interface CabinetSchedule {
    id: string,
    scheduleAppointments: ScheduleData[],
    scheduleEvents: ScheduleData[]
}

export interface ScheduleData {
    id: string,
    startDate: string,
    endDate: string,
    title: string
}