package ec.edu.uteq.pfcbackend.repository;

import ec.edu.uteq.pfcbackend.entity.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class LibroRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configurarPropiedades(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired
    private LibroRepository libroRepository;

    private Libro libroDePrueba;

    @BeforeEach
    void setUp() {
        libroRepository.deleteAll();
        libroDePrueba = Libro.builder()
                .titulo("Cien años de soledad")
                .descripcion("Novela de Gabriel García Márquez")
                .isbn("978-0307474728")
                .genero("Realismo mágico")
                .autor("Gabriel García Márquez")
                .anioPublicacion(1967)
                .editorial("Sudamericana")
                .idioma("Español")
                .estado("Disponible")
                .stock(10)
                .build();
        libroRepository.save(libroDePrueba);
    }

    @Test
    void guardaYRecuperaUnLibroPorId() {
        Optional<Libro> encontrado = libroRepository.findById(libroDePrueba.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getTitulo()).isEqualTo("Cien años de soledad");
        assertThat(encontrado.get().getIsbn()).isEqualTo("978-0307474728");
        assertThat(encontrado.get().getCreatedAt()).isNotNull();
        assertThat(encontrado.get().getUpdatedAt()).isNotNull();
    }

    @Test
    void devuelveVacioSiElIdNoExiste() {
        Optional<Libro> resultado = libroRepository.findById(999L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void encuentraLibroPorIsbnIgnorandoMayusculas() {
        Optional<Libro> resultado = libroRepository.findByIsbnIgnoreCase("978-0307474728");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTitulo()).isEqualTo("Cien años de soledad");
    }

    @Test
    void noEncuentraLibroConIsbnInexistente() {
        Optional<Libro> resultado = libroRepository.findByIsbnIgnoreCase("000-0000000000");

        assertThat(resultado).isEmpty();
    }

    @Test
    void buscaLibrosPorTituloParcialIgnorandoMayusculas() {
        libroRepository.save(Libro.builder()
                .titulo("El amor en los tiempos del cólera")
                .isbn("978-8497592208")
                .autor("Gabriel García Márquez")
                .anioPublicacion(1985)
                .stock(5)
                .build());

        Pageable pageable = PageRequest.of(0, 10);
        Page<Libro> resultado = libroRepository.findByTituloContainingIgnoreCase("años", pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).getTitulo()).isEqualTo("Cien años de soledad");
    }

    @Test
    void listaTodosLosLibrosConPaginacion() {
        libroRepository.save(Libro.builder()
                .titulo("Rayuela")
                .isbn("978-8437604572")
                .autor("Julio Cortázar")
                .anioPublicacion(1963)
                .stock(3)
                .build());

        Page<Libro> resultado = libroRepository.findAll(PageRequest.of(0, 10));

        assertThat(resultado.getTotalElements()).isEqualTo(2);
    }

    @Test
    void eliminaUnLibroCorrectamente() {
        libroRepository.deleteById(libroDePrueba.getId());

        Optional<Libro> resultado = libroRepository.findById(libroDePrueba.getId());
        assertThat(resultado).isEmpty();
    }
}