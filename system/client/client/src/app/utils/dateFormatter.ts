import { formatDate } from "@angular/common";
import { Injectable } from "@angular/core";
import { CalendarDateFormatter, DateFormatterParams } from "angular-calendar";

@Injectable()
export class DateFormatter extends CalendarDateFormatter {

    public override dayViewHour({ date, locale }: DateFormatterParams): string {
        return formatDate(date, "HH:mm", locale!);
    }

    public override weekViewHour({ date, locale }: DateFormatterParams): string {
        return this.dayViewHour({date, locale});
    }
}