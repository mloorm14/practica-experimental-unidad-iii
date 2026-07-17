import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { Libro, PageResponse } from '../models/libro.model';

export type LibroRequest = Omit<Libro, 'id' | 'createdAt' | 'updatedAt'>;

@Injectable({ providedIn: 'root' })
export class LibroService {
  constructor(private http: HttpClient) {}

  listar(pagina: number = 0, tamanio: number = 10): Observable<PageResponse<Libro>> {
    const params = new HttpParams()
      .set('page', pagina)
      .set('size', tamanio);

    return this.http.get<PageResponse<Libro>>(`${environment.apiUrl}/libros`, { params });
  }

  obtenerPorId(id: number): Observable<Libro> {
    return this.http.get<Libro>(`${environment.apiUrl}/libros/${id}`);
  }

  crear(libro: LibroRequest): Observable<Libro> {
    return this.http.post<Libro>(`${environment.apiUrl}/libros`, libro);
  }

  actualizar(id: number, libro: LibroRequest): Observable<Libro> {
    return this.http.put<Libro>(`${environment.apiUrl}/libros/${id}`, libro);
  }
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/libros/${id}`);
  }
}
