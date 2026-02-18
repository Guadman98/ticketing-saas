import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

import { environment } from '../../../environments/environment';
import { CreateTicketRequest, PageResponse, TicketResponse } from './models';

@Injectable({ providedIn: 'root' })
export class TicketApiService {
  private readonly http = inject(HttpClient);

  createTicket(payload: CreateTicketRequest) {
    return this.http.post<TicketResponse>(`${environment.apiBaseUrl}/tickets`, payload);
  }

  listTickets(page = 0, size = 10) {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<PageResponse<TicketResponse>>(`${environment.apiBaseUrl}/tickets`, { params });
  }
}
