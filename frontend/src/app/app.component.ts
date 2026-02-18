import { Component, inject } from '@angular/core';
import { DatePipe, NgFor, NgIf } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AuthApiService } from './core/api/auth-api.service';
import { AuthTokenStore } from './core/api/auth-token.store';
import { TicketApiService } from './core/api/ticket-api.service';
import { TicketPriority, TicketResponse } from './core/api/models';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, NgFor, DatePipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authApi = inject(AuthApiService);
  private readonly ticketApi = inject(TicketApiService);
  private readonly tokenStore = inject(AuthTokenStore);

  readonly priorities: TicketPriority[] = ['LOW', 'MEDIUM', 'HIGH', 'URGENT'];

  readonly loginForm = this.fb.nonNullable.group({
    email: ['admin@dev.local', [Validators.required, Validators.email]],
    password: ['dev', Validators.required]
  });

  readonly ticketForm = this.fb.nonNullable.group({
    title: ['', Validators.required],
    description: ['', Validators.required],
    priority: ['MEDIUM' as TicketPriority, Validators.required],
    tags: ['']
  });

  token = this.tokenStore.get() ?? '';
  loginError = '';
  ticketError = '';
  successMessage = '';
  loadingTickets = false;
  tickets: TicketResponse[] = [];

  get isAuthenticated(): boolean {
    return !!this.token;
  }

  login(): void {
    this.loginError = '';
    this.successMessage = '';

    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.authApi.login(this.loginForm.getRawValue()).subscribe({
      next: (response) => {
        this.token = response.accessToken;
        this.tokenStore.set(response.accessToken);
        this.successMessage = 'Sesión iniciada correctamente.';
        this.loadTickets();
      },
      error: () => {
        this.loginError = 'No se pudo autenticar contra la API';
      }
    });
  }

  logout(): void {
    this.token = '';
    this.tickets = [];
    this.tokenStore.clear();
    this.successMessage = 'Sesión cerrada.';
  }

  createTicket(): void {
    this.ticketError = '';
    this.successMessage = '';

    if (this.ticketForm.invalid) {
      this.ticketForm.markAllAsTouched();
      return;
    }

    const payload = this.ticketForm.getRawValue();

    this.ticketApi.createTicket(payload).subscribe({
      next: () => {
        this.successMessage = 'Ticket creado correctamente.';
        this.ticketForm.patchValue({
          title: '',
          description: '',
          priority: 'MEDIUM',
          tags: ''
        });
        this.loadTickets();
      },
      error: () => {
        this.ticketError = 'No se pudo crear el ticket. Verifica que estés autenticado.';
      }
    });
  }

  loadTickets(): void {
    if (!this.isAuthenticated) {
      return;
    }

    this.loadingTickets = true;
    this.ticketApi.listTickets(0, 10).subscribe({
      next: (page) => {
        this.tickets = page.content;
        this.loadingTickets = false;
      },
      error: () => {
        this.ticketError = 'No se pudo cargar la lista de tickets.';
        this.loadingTickets = false;
      }
    });
  }
}
