import { Injectable, signal } from '@angular/core';

export interface Notificacion {
  mensaje: string;
  tipo: 'exito' | 'error';
}

@Injectable({ providedIn: 'root' })
export class NotificationService {
  private readonly notificacionActual = signal<Notificacion | null>(null);
  readonly notificacion = this.notificacionActual.asReadonly();

  mostrarError(mensaje: string): void {
    this.notificacionActual.set({ mensaje, tipo: 'error' });
    this.autoOcultar();
  }

  mostrarExito(mensaje: string): void {
    this.notificacionActual.set({ mensaje, tipo: 'exito' });
    this.autoOcultar();
  }

  private autoOcultar(): void {
    setTimeout(() => this.notificacionActual.set(null), 4000);
  }
}
