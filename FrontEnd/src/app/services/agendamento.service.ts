import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

export interface AgedamentoDTO {
  clienteId: number;
  barbeiroId: number;
  servicoId: number;
  dataHora: string;
  status: string;
}

export interface AgedamentoModel {
  id: number;
  dataHora: string;
  status: string;
  clienteModel: unknown;
  servicoModel: unknown;
  barbeiroModel: unknown;
}

@Injectable({ providedIn: 'root' })
export class AgendamentoService {
  constructor(private api: ApiService) {}

  criar(dto: AgedamentoDTO): Observable<AgedamentoModel[]> {
    return this.api.post<AgedamentoModel[]>('/v1/agendamento/criar', dto);
  }

  listar(id: number): Observable<AgedamentoModel[]> {
    return this.api.get<AgedamentoModel[]>(`/v1/agendamento/lsitar/${id}`);
  }
}
