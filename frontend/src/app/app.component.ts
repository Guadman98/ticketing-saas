import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgIf } from '@angular/common';

import { AuthApiService } from './core/api/auth-api.service';
import { AuthTokenStore } from './core/api/auth-token.store';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authApi = inject(AuthApiService);
  private readonly tokenStore = inject(AuthTokenStore);

  readonly form = this.fb.nonNullable.group({
    email: ['admin@dev.local', [Validators.required, Validators.email]],
    password: ['dev', Validators.required]
  });

  token = '';
  error = '';

  login(): void {
    this.error = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.authApi.login(this.form.getRawValue()).subscribe({
      next: (response) => {
        this.token = response.accessToken;
        this.tokenStore.set(response.accessToken);
      },
      error: () => {
        this.error = 'No se pudo autenticar contra la API';
      }
    });
  }
}
