import { MatSnackBar, MatSnackBarConfig } from "@angular/material/snack-bar";
const btnLabel: string = 'Затвори';
const duration: number = 5000;
const config: MatSnackBarConfig<any> = {
    duration: duration,
    verticalPosition: 'top',
    horizontalPosition: 'center',
};

export function openSnackBar(snackBar: MatSnackBar, message: string) {
    snackBar.open(message, btnLabel, config);
}