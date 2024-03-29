import { AbstractControl } from "@angular/forms";

export function shouldShowErrorForControl(control: AbstractControl) {
    return (control.touched || control.dirty) && control.invalid;
}

export function parseErrorMessage(validation: string, numberOfSymbols?: number) {
    return formatString(ERROR_MESSAGES[validation], numberOfSymbols);
}

export function checkForMinLength(control: AbstractControl) {
    return control.errors?.['minlength'];
}

export function checkForMaxLength(control: AbstractControl) {
    return control.errors?.['maxlength'];
}

export function checkForRegEx(control: AbstractControl) {
    return control.errors?.['pattern'];
}

export function checkPasswordsMatch(control: AbstractControl) {
    return control.errors?.['passwordMissmatch'];
}

export function checkStartIsBefore(control: AbstractControl) {
    return control.errors?.['startIsBefore'];
}

export function checkEndIsBefore(control: AbstractControl) {
    return control.errors?.['endIsBefore'];
}

export const ERROR_MESSAGES: any = {
    required: 'Това поле е задължително!',
    minLength: 'Полето трябва да е дълго поне %d символа!',
    maxLength: 'Полето не трябва да е повече от %d символа!',
    email: 'Въведете валиден и-мейл!',
    passwordsMissmatch: 'Паролите не съвпадат',
    terms: 'Трябва да се съгласите с условията!',
    phoneRegEx: 'Въведете валиден номер!',
    startIsBefore: 'Началният час не може да е преди настоящия',
    endIsBefore: 'Крайният час не може да е преди началния',
    invalidCredentials: 'Невалиден и-мейл или парола'
}

function formatString(str: string, num?: number) {
    return str.indexOf('%d') > -1 ? str.replace('%d', num!.toString()) : str;
}