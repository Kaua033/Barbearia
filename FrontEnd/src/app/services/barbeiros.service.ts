import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { ApiService } from './api.service';
import { Barber } from '../models/booking.models';
import { BARBERS } from '../data/mock-booking.data';

export interface BarbeiroApi {
  nome: string;
  telefone: string;
}

@Injectable({ providedIn: 'root' })
export class BarbeirosService {
  constructor(private api: ApiService) {}

  listar(): Observable<Barber[]> {
    return this.api.get<BarbeiroApi[]>('/v1/barbeiro/listar/0').pipe(
      map((lista) => {
        const valid = lista.filter((b): b is BarbeiroApi => b !== null);
        if (valid.length === 0) {
          return BARBERS;
        }
        return valid.map((b, i) => ({
          id: String(i + 1),
          name: b.nome,
          specialty: '',
          initials: b.nome.charAt(0).toUpperCase(),
        }));
      }),
      catchError(() => of(BARBERS)),
    );
  }
}
