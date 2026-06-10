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

  wakeUp(): void {
    this.api.wakeUp();
  }

  register(dto: ClienteDTO): Observable<string> {
    return this.api.post<string>('/auth/register', dto);
  }

  login(nome: string, senha: string): Observable<AuthState> {
    console.log('[AuthService] login chamado:', nome);
    return this.api.post<AuthState>('/auth/login', { nome, senha }).pipe(
      tap({
        next: (user) => {
          console.log('[AuthService] login resposta:', user);
          this.saveUser(user);
          this.user.set(user);
        },
        error: (err) => console.error('[AuthService] login erro:', err),
      })
    );
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
