import { Component, inject } from '@angular/core';
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
export class LoginComponent {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);

  nome = '';
  telefone = '';
  senha = '';
  isRegister = false;
  error = '';
  loading = false;

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
          this.error = '';
          this.isRegister = false;
          this.senha = '';
          this.error = 'Conta criada com sucesso! Faça login.';
          this.loading = false;
        },
        error: (err) => {
          this.error = err instanceof TimeoutError ? 'O servidor está demorando. Tente novamente.' : 'Erro ao cadastrar. Tente novamente.';
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
        next: () => {
          this.router.navigate(['/home'], { fragment: 'agendar' });
        },
        error: (err) => {
          if (err instanceof TimeoutError) this.error = 'O servidor está demorando. Tente novamente.';
          else if (err.status === 401) this.error = 'Credenciais inválidas.';
          else this.error = 'Erro ao conectar ao servidor.';
          this.loading = false;
        },
      });
    }
  }
}
