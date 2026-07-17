import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { Libro, PageResponse } from '../models/libro.model';

@Injectable({ providedIn: 'root' })
export class LibroService {
  constructor(private http: HttpClient) {}

  listar(pagina: number = 0, tamanio: number = 10): Observable<PageResponse<Libro>> {
    const params = new HttpParams()
      .set('page', pagina)
      .set('size', tamanio);

    return this.http.get<PageResponse<Libro>>(`${environment.apiUrl}/libros`, { params });
  }
}
