import { Duration } from "moment";

export interface AuthResult {
    id: string,
    firstName: string,
    lastName: string,
    role: string,
    email: string,
    token: string,
    expiresAt: Duration
}