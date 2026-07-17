import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Libros } from './pages/libros/libros';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'libros', component: Libros, canActivate: [authGuard] }
];
