export interface EventData {
    hours: string,
    intervals: number
}

export interface EventDataInput {
    date: string,
    eventData: EventData[]
}

export interface CabinetResponse {
    id: number,
    name: string,
    city: string
}