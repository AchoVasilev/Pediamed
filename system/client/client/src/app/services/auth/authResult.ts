import { Duration } from "moment";

export interface AuthResult {
    id: string,
    firstName: string,
    lastName: string,
    roles: string[],
    email: string,
    tokenModel: TokenModel
}

export interface TokenModel {
    token: string,
    expiresAt: Duration
}