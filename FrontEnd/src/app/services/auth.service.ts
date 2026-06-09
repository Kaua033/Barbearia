import { Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { ApiService } from './api.service';

export interface ClienteDTO {
  id?: number;
  nome: string;
  telefone: string;
  senha: string;
}

export interface AuthState {
  id: number | null;
  nome: string;
  telefone: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly storageKey = 'atlas_user';
  readonly user = signal<AuthState | null>(this.loadUser());

  constructor(private api: ApiService) {}

  register(dto: ClienteDTO): Observable<string> {
    return this.api.post<string>('/auth/register', dto);
  }

  login(dto: ClienteDTO): void {
    const user: AuthState = { id: dto.id ?? null, nome: dto.nome, telefone: dto.telefone };
    this.saveUser(user);
    this.user.set(user);
  }

  logout(): void {
    localStorage.removeItem(this.storageKey);
    this.user.set(null);
  }

  isLoggedIn(): boolean {
    return this.user() !== null;
  }

  private saveUser(user: AuthState): void {
    localStorage.setItem(this.storageKey, JSON.stringify(user));
  }

  private loadUser(): AuthState | null {
    const raw = localStorage.getItem(this.storageKey);
    return raw ? JSON.parse(raw) : null;
  }
}
