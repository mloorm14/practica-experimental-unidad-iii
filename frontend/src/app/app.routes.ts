import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Libros } from './pages/libros/libros';
import { LibroForm } from './pages/libro-form/libro-form';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'libros', component: Libros, canActivate: [authGuard] },
  { path: 'libros/nuevo', component: LibroForm, canActivate: [authGuard] },
  { path: 'libros/:id/editar', component: LibroForm, canActivate: [authGuard] }
];
