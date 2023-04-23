import { formatDate } from "@angular/common";
import { Injectable } from "@angular/core";
import { CalendarDateFormatter, DateFormatterParams } from "angular-calendar";

@Injectable({
    providedIn: 'root'
})
export class DateFormatter extends CalendarDateFormatter {

    public override dayViewHour({ date, locale }: DateFormatterParams): string {
        return formatDate(date, "HH:mm", locale!);
    }

    public override weekViewHour({ date, locale }: DateFormatterParams): string {
        return this.dayViewHour({date, locale});
    }

    public formatCurrentDate(date: Date, locale: string) {
        return formatDate(date, 'dd/MM/yyyy', locale);
    }
}