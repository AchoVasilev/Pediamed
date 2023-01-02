import { Duration } from "moment";

export interface AuthResult {
    token: string,
    expiresAt: Duration
}