import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
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

    if (!this.telefone || !this.senha) {
      this.error = 'Preencha telefone e senha.';
      return;
    }

    if (this.isRegister) {
      if (!this.nome) {
        this.error = 'Preencha o nome.';
        return;
      }

      this.loading = true;
      this.auth.register({ nome: this.nome, telefone: this.telefone, senha: this.senha }).subscribe({
        next: () => {
          this.auth.login({ nome: this.nome, telefone: this.telefone, senha: this.senha });
          this.router.navigate(['/home'], { fragment: 'agendar' });
        },
        error: () => {
          this.error = 'Erro ao cadastrar. Tente novamente.';
          this.loading = false;
        },
      });
    } else {
      this.auth.login({ nome: this.nome || this.telefone, telefone: this.telefone, senha: this.senha });
      this.router.navigate(['/home'], { fragment: 'agendar' });
    }
  }
}
