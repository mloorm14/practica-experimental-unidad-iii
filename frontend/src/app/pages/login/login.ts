import { Component, signal } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { AuthService } from '../../core/services/auth.service';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  username = '';
  password = '';
  cargando = signal(false);

  constructor(
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.cargando.set(true);

    this.authService.login({ username: this.username, password: this.password }).subscribe({
      next: () => {
        this.cargando.set(false);
        this.router.navigate(['/libros']);
      },
      error: () => {
        this.cargando.set(false);
        this.notificationService.mostrarError('Usuario o contraseña incorrectos.');
      }
    });
  }
}
