import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

import { environment } from '../../../environments/environment';
import { LoginRequest, Usuario } from '../models/usuario.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly usuarioActual = signal<Usuario | null>(null);
  readonly usuario = this.usuarioActual.asReadonly();

  constructor(private http: HttpClient) {}

  login(credenciales: LoginRequest): Observable<Usuario> {
    return this.http
      .post<Usuario>(`${environment.apiUrl}/auth/login`, credenciales)
      .pipe(tap((usuario) => this.usuarioActual.set(usuario)));
  }

  logout(): Observable<void> {
    return this.http
      .post<void>(`${environment.apiUrl}/auth/logout`, {})
      .pipe(tap(() => this.usuarioActual.set(null)));
  }

  estaAutenticado(): boolean {
    return this.usuarioActual() !== null;
  }
}
