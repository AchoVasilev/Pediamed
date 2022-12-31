import { FormGroup } from "@angular/forms";

const util = require('node:util');

export function shouldShowErrorForControl(control: string, form: FormGroup) {
    return form.controls[control].touched && form.controls[control].invalid;
}

export function parseErrorMessage(control: string, numberOfSymbols: number) {
    return util.format(ERROR_MESSAGES[control], numberOfSymbols);
}

export const ERROR_MESSAGES: any = {
    required: 'Това поле е задължително!',
    minLength: 'Полето трябва да е дълго поне %d символа!',
    maxLength: 'Полето не трябва да е по-дълго повече от %d символа!',
    email: 'Въведете валиден и-мейл!',
    passwordMatch: 'Паролите не съвпадат'
}

