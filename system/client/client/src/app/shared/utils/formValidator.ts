import { FormGroup } from "@angular/forms";

export function shouldShowErrorForControl(control: string, formGroup: FormGroup) {
    return formGroup.controls[control].touched && formGroup.controls[control].invalid;
}

export function parseErrorMessage(validation: string, numberOfSymbols?: number) {
    return formatString(ERROR_MESSAGES[validation], numberOfSymbols);
}

export function checkForMinLength(control: string, formGroup: FormGroup) {
    return formGroup.controls[control].errors?.['minlength'];
}

export function checkForMaxLength(control: string, formGroup: FormGroup) {
    return formGroup.controls[control].errors?.['maxlength'];
}

export const ERROR_MESSAGES: any = {
    required: 'Това поле е задължително!',
    minLength: 'Полето трябва да е дълго поне %d символа!',
    maxLength: 'Полето не трябва да е повече от %d символа!',
    email: 'Въведете валиден и-мейл!',
    passwordMatch: 'Паролите не съвпадат'
}

function formatString(str: string, num?: number) {
    return str.indexOf('%d') > -1 ? str.replace('%d', num!.toString()) : str;
}