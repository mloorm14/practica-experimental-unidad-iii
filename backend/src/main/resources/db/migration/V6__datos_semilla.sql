-- ============================================================
-- Seeder de datos semilla para el sistema de gestión bibliotecaria.
-- ≥50 registros realistas y coherentes entre sí.
-- ============================================================

-- ============================================================
-- 1. Editoriales (12 registros)
-- ============================================================
INSERT INTO editoriales (nombre) VALUES
('Editorial Planeta'),
('Penguin Random House Grupo Editorial'),
('Editorial Santillana'),
('Editorial Alma'),
('Ediciones B'),
('Debolsillo'),
('Editorial Norma'),
('Anagrama'),
('Tusquets Editores'),
('Editorial Suramericana'),
('Grupo Editorial Océano'),
('Alianza Editorial');

-- ============================================================
-- 2. Idiomas (8 registros)
-- ============================================================
INSERT INTO idiomas (nombre) VALUES
('Español'),
('Inglés'),
('Francés'),
('Alemán'),
('Portugués'),
('Italiano'),
('Japonés'),
('Chino Mandarín');

-- ============================================================
-- 3. Estados de libro (6 registros)
-- ============================================================
INSERT INTO estados_libro (nombre) VALUES
('Disponible'),
('Prestado'),
('En reparación'),
('Reservado'),
('Deteriorado'),
('Perdido');

-- ============================================================
-- 4. Libros (30 registros) — total general: 56 registros
-- ============================================================
INSERT INTO libros (titulo, descripcion, isbn, genero, autor, anio_publicacion, editorial_id, idioma_id, estado_id, stock) VALUES
('Cien años de soledad',
 'Novela que narra la historia de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo.',
 '978-0-06-088328-7', 'Novela', 'Gabriel García Márquez', 1967, 1, 1, 1, 5),

('Don Quijote de la Mancha',
 'Obra cumbre de la literatura española que narra las aventuras de un hidalgo que enloquece leyendo libros de caballerías.',
 '978-84-204-1214-6', 'Novela', 'Miguel de Cervantes', 1605, 1, 1, 1, 3),

('1984',
 'Distopía que describe un régimen totalitario que controla cada aspecto de la vida de sus ciudadanos.',
 '978-0-451-52493-5', 'Ciencia ficción', 'George Orwell', 1949, 2, 2, 1, 4),

('El principito',
 'Cuento filosófico que narra las aventuras de un pequeño príncipe que viaja entre planetas.',
 '978-0-15-601398-9', 'Fábula', 'Antoine de Saint-Exupéry', 1943, 3, 3, 1, 6),

('Crimen y castigo',
 'Novela psicológica que sigue a un estudiante que comete un asesinato y enfrenta las consecuencias morales.',
 '978-0-14-044913-6', 'Novela', 'Fiódor Dostoyevski', 1866, 4, 2, 1, 3),

('La sombra del viento',
 'Novela ambientada en la Barcelona de posguerra alrededor de un libro maldito.',
 '978-84-08-04327-0', 'Misterio', 'Carlos Ruiz Zafón', 2001, 5, 1, 1, 4),

('Harry Potter y la piedra filosofal',
 'El joven Harry descubre que es mago y es aceptado en el Colegio Hogwarts de Magia y Hechicería.',
 '978-84-7888-445-5', 'Fantasía', 'J.K. Rowling', 1997, 6, 2, 1, 7),

('El código Da Vinci',
 'Novela de misterio que entrelaza arte, religión y una conspiración milenaria.',
 '978-84-08-04342-3', 'Thriller', 'Dan Brown', 2003, 2, 2, 1, 3),

('Fahrenheit 451',
 'Distopía donde los libros están prohibidos y los bomberos se encargan de quemarlos.',
 '978-1-4516-7331-9', 'Ciencia ficción', 'Ray Bradbury', 1953, 7, 2, 1, 2),

('Orgullo y prejuicio',
 'Novela de época que narra la historia de Elizabeth Bennet y el señor Darcy.',
 '978-0-14-143951-8', 'Romance', 'Jane Austen', 1813, 8, 2, 1, 4),

('La casa de los espíritus',
 'Saga familiar que mezcla realismo mágico y la historia política de un país latinoamericano.',
 '978-84-01-24234-0', 'Novela', 'Isabel Allende', 1982, 9, 1, 1, 3),

('Rayuela',
 'Novela experimental que puede leerse en múltiples órdenes.',
 '978-84-376-0457-2', 'Novela', 'Julio Cortázar', 1963, 1, 1, 1, 2),

('El amor en los tiempos del cólera',
 'Historia de amor que abarca más de cincuenta años en una ciudad caribeña.',
 '978-0-06-088328-8', 'Novela', 'Gabriel García Márquez', 1985, 1, 1, 1, 3),

('Kafka en la orilla',
 'Novela que entrelaza las historias de un joven que huye de casa y un hombre mayor.',
 '978-84-8310-228-2', 'Realismo mágico', 'Haruki Murakami', 2002, 10, 7, 1, 2),

('Los juegos del hambre',
 'En un futuro distópico, jóvenes son obligados a luchar a muerte en un espectáculo televisado.',
 '978-84-272-0195-7', 'Ciencia ficción', 'Suzanne Collins', 2008, 11, 1, 1, 5),

('La biblia de los caídos',
 'Novela de fantasía urbana sobre ángeles caídos en el inframundo.',
 '978-84-9932-465-9', 'Fantasía', 'Fernando Trujillo', 2011, 12, 1, 1, 2),

('Berserk: El incremento',
 'Manga de fantasía oscura que sigue las aventuras del guerrero Guts.',
 '978-84-473-7226-1', 'Manga', 'Kentaro Miura', 1990, 11, 7, 1, 3),

('El alquimista',
 'Fábula sobre un pastor que viaja de España a Egipto en busca de un tesoro.',
 '978-0-06-112241-5', 'Fábula', 'Paulo Coelho', 1988, 2, 1, 1, 4),

('Sapiens: De animales a dioses',
 'Historia de la humanidad desde la prehistoria hasta la era digital.',
 '978-84-9992-474-0', 'Historia', 'Yuval Noah Harari', 2011, 12, 2, 1, 3),

('El principito (edición bilingüe)',
 'Edición bilingüe francés-español del clásico de Saint-Exupéry.',
 '978-2-07-061275-8', 'Fábula', 'Antoine de Saint-Exupéry', 1943, 4, 3, 1, 2),

('It',
 'Novela de terror sobre una entidad maligno que adopta la forma de payaso.',
 '978-1-5011-4297-0', 'Terror', 'Stephen King', 1986, 2, 2, 1, 3),

('Neuromante',
 'Novela pionera del ciberpunk que explora el hackeo y la inteligencia artificial.',
 '978-0-441-56959-5', 'Ciencia ficción', 'William Gibson', 1984, 7, 2, 1, 2),

('El nombre de la rosa',
 'Novela histórica ambientada en una abadía medieval con un monje detective.',
 '978-84-204-7155-5', 'Misterio', 'Umberto Eco', 1980, 10, 6, 1, 3),

('Los pilares de la tierra',
 'Saga medieval sobre la construcción de una catedral en la Inglaterra del siglo XII.',
 '978-84-01-35219-6', 'Histórica', 'Ken Follett', 1989, 11, 2, 1, 4),

('Pedro Páramo',
 'Novela breve que narra la búsqueda de un hijo hacia su padre en el pueblo de Comala.',
 '978-968-411-555-4', 'Novela', 'Juan Rulfo', 1955, 3, 1, 1, 2),

('El Hobbit',
 'Bilbo Bolsón emprende un viaje épico para recuperar un tesoro custodiado por un dragón.',
 '978-0-547-92822-7', 'Fantasía', 'J.R.R. Tolkien', 1937, 2, 2, 1, 5),

('La ciudad y los perros',
 'Novela que retrata la violencia y la corrupción en una academia militar.',
 '978-84-204-7164-7', 'Novela', 'Mario Vargas Llosa', 1963, 1, 1, 1, 2),

('El señor de los anillos: La comunidad del anillo',
 'Primer tomo de la trilogía épica de fantasía sobre la búsqueda del Anillo Único.',
 '978-0-618-64015-7', 'Fantasía', 'J.R.R. Tolkien', 1954, 2, 2, 1, 6),

('Crepúsculo',
 'Historia de amor entre una adolescente humana y un vampiro.',
 '978-0-316-03837-9', 'Romance', 'Stephenie Meyer', 2005, 2, 2, 1, 3),

('El guardian entre el centeno',
 'Novela de coming-of-age sobre un adolescente rebelde en Nueva York.',
 '978-0-316-76948-0', 'Novela', 'J.D. Salinger', 1951, 8, 2, 1, 2),

('Dune',
 'Épica de ciencia ficción sobre un planeta desértico y su especia más valiosa.',
 '978-0-441-17271-9', 'Ciencia ficción', 'Frank Herbert', 1965, 2, 2, 1, 3),

('La ladrona de libros',
 'Novela ambientada en la Alemania nazi contada por la muerte.',
 '978-0-316-01781-7', 'Histórica', 'Markus Zusak', 2005, 2, 2, 1, 4),

('Treinta y tres',
 'Novela de misterio sobre un detective que resuelve un caso en una isla.',
 '978-84-08-12345-6', 'Misterio', 'Agatha Christie', 1934, 12, 2, 1, 2),

('El viejo y el mar',
 'Novela breve sobre la lucha de un pescador cubano contra un enorme marlín.',
 '978-0-684-80122-3', 'Novela', 'Ernest Hemingway', 1952, 2, 2, 1, 3),

('Crónica de una muerte anunciada',
 'Novela breve que narra un asesinato anunciado desde su desenlace.',
 '978-0-06-088328-9', 'Novela', 'Gabriel García Márquez', 1981, 1, 1, 1, 2),

('Matar a un ruiseñor',
 'Novela sobre la injusticia racial en el sur de Estados Unidos.',
 '978-0-06-093546-7', 'Novela', 'Harper Lee', 1960, 2, 2, 1, 3),

('El retrato de Dorian Gray',
 'Novela filosófica sobre un joven cuyo retrato envejece en su lugar.',
 '978-0-14-143957-0', 'Novela', 'Oscar Wilde', 1890, 4, 2, 1, 2),

('Viaje al centro de la tierra',
 'Novela de ciencia ficción sobre un viaje subterráneo a través de la Tierra.',
 '978-0-14-044799-6', 'Ciencia ficción', 'Julio Verne', 1864, 11, 3, 1, 2),

('La guerra de los mundos',
 'Novela de invasión alienígena en la Inglaterra victoriana.',
 '978-0-14-144058-3', 'Ciencia ficción', 'H.G. Wells', 1898, 11, 2, 1, 2),

('Frankenstein',
 'Novela gótica sobre un científico que crea un ser viviente.',
 '978-0-14-143947-1', 'Terror', 'Mary Shelley', 1818, 4, 2, 1, 2),

('Los cuatro acuerdos',
 'Guía de vida basada en la sabiduría totema.',
 '978-1-878424-31-0', 'Autoayuda', 'Don Miguel Ruiz', 1997, 11, 1, 1, 4),

('21 lecciones para el siglo XXI',
 'Análisis de los desafíos contemporáneos de la humanidad.',
 '978-84-9992-678-0', 'Ensayo', 'Yuval Noah Harari', 2018, 12, 2, 1, 3),

('La guerra no tiene rostro de mujer',
 'Novela que narra la experiencia de mujeres partisanas en la Segunda Guerra Mundial.',
 '978-84-204-7234-8', 'Novela', 'Svetlana Aleksiévich', 1985, 9, 4, 1, 2),

('Ensayo sobre la ceguera',
 'Novela alegórica sobre una epidemia de ceguera blanca.',
 '978-84-204-7168-5', 'Novela', 'José Saramago', 1995, 5, 1, 1, 3),

('El túnel',
 'Novela psicológica sobre un pintor obsesionado con una mujer.',
 '978-84-204-7169-2', 'Novela', 'Ernesto Sábato', 1948, 1, 1, 1, 2),

('Rebelión en la granja',
 'Fábula política sobre una revolución de animales en una granja.',
 '978-0-451-52634-2', 'Fábula', 'George Orwell', 1945, 2, 2, 1, 3),

('La náusea',
 'Novela existencialista sobre la crisis de un historiador.',
 '978-2-07-036002-1', 'Novela', 'Jean-Paul Sartre', 1938, 9, 3, 1, 1),

('Crónica del pájaro que da vueltas sobre el mundo',
 'Novela surrealista sobre un pianista que pierde sus dedos.',
 '978-84-8310-229-9', 'Realismo mágico', 'Haruki Murakami', 1994, 10, 7, 1, 2),

('El laberinto del fauno',
 'Novela de fantasía ambientada en la España franquista.',
 '978-84-08-06789-3', 'Fantasía', 'Guillermo del Toro', 2006, 11, 1, 1, 2),

('El coronel no tiene quien le escriba',
 'Novela breve sobre un coronel retirado que espera una pensión.',
 '978-0-06-088329-0', 'Novela', 'Gabriel García Márquez', 1961, 1, 1, 1, 2),

('Ficciones',
 'Colección de cuentos fantásticos y metaficción.',
 '978-0-8021-3030-3', 'Cuentos', 'Jorge Luis Borges', 1944, 1, 1, 1, 2),

('Toda la luz que no podemos ver',
 'Novela que entrelaza las historias de una niña francesa ciega y un joven alemán durante la WWII.',
 '978-1-5011-7321-9', 'Histórica', 'Anthony Doerr', 2014, 2, 2, 1, 3),

('El psicoanalista',
 'Novela de misterio sobre un psicoanalista que recibe amenazas anónimas.',
 '978-84-9932-567-5', 'Thriller', 'John Katzenbach', 2002, 11, 1, 1, 2),

('La insoportable levedad del ser',
 'Novela filosófica sobre el amor, la política y la existencia en la Praga de 1968.',
 '978-0-06-112008-4', 'Novela', 'Milan Kundera', 1984, 9, 4, 1, 2);
