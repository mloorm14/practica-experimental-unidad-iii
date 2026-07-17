import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { LibroService, LibroRequest } from '../../core/services/libro.service';

@Component({
  selector: 'app-libro-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './libro-form.html',
  styleUrl: './libro-form.scss'
})
export class LibroForm implements OnInit {
  esEdicion = signal(false);
  guardando = signal(false);
  error = signal<string | null>(null);
  private libroId: number | null = null;

  form: LibroRequest = {
    titulo: '',
    descripcion: '',
    isbn: '',
    genero: '',
    autor: '',
    anioPublicacion: new Date().getFullYear(),
    editorial: '',
    idioma: '',
    estado: '',
    stock: 0
  };

  constructor(
    private libroService: LibroService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.libroId = Number(idParam);
      this.esEdicion.set(true);
      this.libroService.obtenerPorId(this.libroId).subscribe({
        next: (libro) => {
          const { id, createdAt, updatedAt, ...resto } = libro;
          this.form = resto;
        }
      });
    }
  }

  onSubmit(): void {
    this.error.set(null);
    this.guardando.set(true);

    const peticion = this.esEdicion()
      ? this.libroService.actualizar(this.libroId!, this.form)
      : this.libroService.crear(this.form);

    peticion.subscribe({
      next: () => {
        this.guardando.set(false);
        this.router.navigate(['/libros']);
      },
      error: () => {
        this.guardando.set(false);
        this.error.set('No se pudo guardar el libro. Verifica los datos.');
      }
    });
  }
}
