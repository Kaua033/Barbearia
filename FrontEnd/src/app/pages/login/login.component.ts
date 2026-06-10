import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { TimeoutError } from 'rxjs';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);

  nome = '';
  telefone = '';
  senha = '';
  isRegister = false;
  error = '';
  loading = false;
  warming = false;

  ngOnInit(): void {
    this.warming = true;
    this.auth.wakeUp();
    setTimeout(() => this.warming = false, 3_000);
  }

  submit(): void {
    this.error = '';
    this.loading = true;

    if (this.isRegister) {
      if (!this.nome || !this.telefone || !this.senha) {
        this.error = 'Preencha todos os campos.';
        this.loading = false;
        return;
      }

      this.auth.register({ nome: this.nome, telefone: this.telefone, senha: this.senha }).subscribe({
        next: () => {
          this.isRegister = false;
          this.senha = '';
          this.error = 'Conta criada com sucesso! Faça login.';
          this.loading = false;
        },
        error: (err) => {
          this.error = err instanceof TimeoutError
            ? 'Servidor demorou para responder. Tente novamente.'
            : 'Erro ao cadastrar. Tente novamente.';
          this.loading = false;
        },
      });
    } else {
      if (!this.telefone || !this.senha) {
        this.error = 'Preencha os campos.';
        this.loading = false;
        return;
      }

      this.auth.login(this.telefone, this.senha).subscribe({
        next: (user) => {
          this.loading = false;
          if (user?.id) {
            this.router.navigate(['/home'], { fragment: 'agendar' });
          } else {
            this.error = 'Resposta inválida do servidor.';
          }
        },
        error: (err) => {
          console.error('[Login] erro completo:', err);
          if (err instanceof TimeoutError) this.error = 'Servidor demorou para responder. Tente novamente.';
          else if (err.status === 401) this.error = 'Credenciais inválidas.';
          else this.error = `Erro ${err.status ?? 'rede'}: ${err.statusText || err.message || 'servidor indisponível'}`;
          this.loading = false;
        },
      });
    }
  }
}
