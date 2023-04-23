import { AbstractControl, ValidatorFn } from "@angular/forms";
import { isBefore, parse } from "date-fns";

export function validateStartDate(startTime: AbstractControl) {
    const validatorFn: ValidatorFn = () => {
        const startDateTime = parse(startTime.value, "dd/MM/yyyy HH:mm", new Date());
        if(isBefore(startDateTime, new Date())) {
            return {
                startIsBefore: true
            }
        }

        return null;
    }

    return validatorFn;
}

export function validateEndDate(endTime: AbstractControl) {
    const validatorFn: ValidatorFn = (startTime: AbstractControl) => {
        const startDateTime = parse(startTime.value, "dd/MM/yyyy HH:mm", new Date());
        const endDateTime = parse(endTime.value, "dd/MM/yyyy HH:mm", new Date());

        if(isBefore(endDateTime, startDateTime)) {
            return {
                endIsBefore: true
            }
        }

        return null;
    }

    return validatorFn;
}