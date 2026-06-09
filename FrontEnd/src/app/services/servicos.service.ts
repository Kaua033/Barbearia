import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { ApiService } from './api.service';
import { BarberService } from '../models/booking.models';
import { SERVICES } from '../data/mock-booking.data';

export interface ServicoApi {
  id: number;
  nome: string;
  valor: number;
}

@Injectable({ providedIn: 'root' })
export class ServicosService {
  constructor(private api: ApiService) {}

  listar(): Observable<BarberService[]> {
    return this.api.get<ServicoApi[]>('/v1/servico/listar/0').pipe(
      map((lista) => {
        const valid = lista.filter((s): s is ServicoApi => s !== null);
        if (valid.length === 0) {
          return SERVICES;
        }
        return valid.map((s) => this.toBarberService(s));
      }),
      catchError(() => of(SERVICES)),
    );
  }

  private toBarberService(api: ServicoApi): BarberService {
    const iconMap: Record<string, BarberService['icon']> = {
      Corte: 'scissors',
      Barba: 'beard',
      Combo: 'combo',
      Platinado: 'color',
      Hidratação: 'hydration',
    };
    return {
      id: String(api.id),
      name: api.nome,
      duration: '',
      price: api.valor,
      icon: iconMap[api.nome] ?? 'scissors',
    };
  }
}
