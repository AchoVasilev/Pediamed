import { Duration } from 'moment';

export interface AuthResult {
  id: string;
  tokenModel: TokenModel;
}

export interface TokenModel {
  token: string;
  expiresAt: Duration;
}

export interface UserModel {
  id: string;
  roles: string[];
  firstName: string;
  middleName: string;
  lastName: string;
  email: string;
  phoneNUmber: string;
  patient?: PatientModel[]
}

export interface PatientModel {
  firstName: string;
  lastName: string;
  age: number;
  birthDate: string;
}
