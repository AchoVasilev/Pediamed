import { AbstractControl, ValidatorFn } from "@angular/forms";
import { isBefore, parse } from "date-fns";
import { Constants } from "./constants";

export function validateStartDate(date: string) {
    const validatorFn: ValidatorFn = (startTime: AbstractControl) => {
        
        const startDateTime = parse(`${date} ${startTime.value}`, Constants.dateTimePattern, new Date());
        const eventDate = parse(date, Constants.datePattern, new Date());
        
        if(isBefore(startDateTime, eventDate)) {
            return {
                startIsBefore: true
            }
        }

        return null;
    }

    return validatorFn;
}

export function validateEndDate(date: string, startTime: AbstractControl) {
    const validatorFn: ValidatorFn = (endTime: AbstractControl) => {
        const startDateTime = parse(`${date} ${startTime.value}`, Constants.dateTimePattern, new Date());
        const endDateTime = parse(`${date} ${endTime.value}`, Constants.dateTimePattern, new Date());
        
        if(isBefore(endDateTime, startDateTime)) {
            return {
                endIsBefore: true
            }
        }

        return null;
    }

    return validatorFn;
}