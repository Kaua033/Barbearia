import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, timeout, TimeoutError, retry, throwError, timer } from 'rxjs';
import { environment } from '../../environments/environment';

const TIMEOUT = 30_000;
const RETRY_DELAY = 4_000;
const MAX_RETRIES = 2;

function retryOnTimeout<T>() {
  return retry<T>({
    count: MAX_RETRIES,
    delay: (error) => {
      if (error instanceof TimeoutError) return timer(RETRY_DELAY);
      return throwError(() => error);
    },
  });
}

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly baseUrl = environment.apiUrl;
  private warmed = false;

  constructor(private http: HttpClient) {}

  wakeUp(): void {
    if (this.warmed) return;
    this.warmed = true;
    this.http.get(`${this.baseUrl}/v1/servico/listar/0`)
      .pipe(timeout(10_000))
      .subscribe({ error: () => {} });
  }

  get<T>(path: string, params?: HttpParams): Observable<T> {
    return this.http.get<T>(`${this.baseUrl}${path}`, { params }).pipe(
      timeout(TIMEOUT), retryOnTimeout(),
    );
  }

  post<T>(path: string, body: unknown): Observable<T> {
    return this.http.post<T>(`${this.baseUrl}${path}`, body).pipe(
      timeout(TIMEOUT), retryOnTimeout(),
    );
  }

  put<T>(path: string, body: unknown): Observable<T> {
    return this.http.put<T>(`${this.baseUrl}${path}`, body).pipe(
      timeout(TIMEOUT), retryOnTimeout(),
    );
  }

  delete<T>(path: string): Observable<T> {
    return this.http.delete<T>(`${this.baseUrl}${path}`).pipe(
      timeout(TIMEOUT), retryOnTimeout(),
    );
  }
}
