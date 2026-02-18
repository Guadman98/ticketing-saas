import { Injectable } from '@angular/core';

const TOKEN_KEY = 'ticketing_access_token';

@Injectable({ providedIn: 'root' })
export class AuthTokenStore {
  get(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  set(token: string): void {
    localStorage.setItem(TOKEN_KEY, token);
  }

  clear(): void {
    localStorage.removeItem(TOKEN_KEY);
  }
}
