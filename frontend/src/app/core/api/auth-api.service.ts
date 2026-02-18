import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../../environments/environment';
import { LoginRequest, LoginResponse } from './models';

@Injectable({ providedIn: 'root' })
export class AuthApiService {
  private readonly http = inject(HttpClient);

  login(payload: LoginRequest) {
    return this.http.post<LoginResponse>(`${environment.apiBaseUrl}/auth/login`, payload);
  }
}
