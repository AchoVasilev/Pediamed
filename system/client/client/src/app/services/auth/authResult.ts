
export interface AuthResult {
  user: UserModel;
  tokenModel: TokenModel;
}

export interface TokenModel {
  token: string;
  expiresAt: string;
}

export interface UserModel {
  id: string;
  roles: string[];
  firstName: string;
  middleName?: string;
  lastName: string;
  email: string;
  phoneNumber: string;
}

export interface PatientModel {
  firstName: string;
  lastName: string;
  age: number;
  birthDate: string;
}
