
USE biblioteca_api;

-- =====================================
-- TABELA DE AUTORES
-- =====================================

CREATE TABLE autor (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       nome VARCHAR(100) NOT NULL,
                       nacionalidade VARCHAR(50)
);

-- =====================================
-- TABELA DE CATEGORIAS
-- =====================================

CREATE TABLE categoria (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           nome VARCHAR(50) NOT NULL
);

-- =====================================
-- TABELA DE LIVROS
-- =====================================

CREATE TABLE livro (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       titulo VARCHAR(150) NOT NULL,
                       ano_publicacao INT,
                       quantidade INT,
                       autor_id BIGINT,
                       categoria_id BIGINT,

                       CONSTRAINT fk_autor
                           FOREIGN KEY (autor_id)
                               REFERENCES autor(id),

                       CONSTRAINT fk_categoria
                           FOREIGN KEY (categoria_id)
                               REFERENCES categoria(id)
);

-- =====================================
-- INSERTS AUTORES
-- =====================================

INSERT INTO autor (nome, nacionalidade) VALUES
                                            ('Machado de Assis', 'Brasileiro'),
                                            ('Clarice Lispector', 'Brasileira'),
                                            ('J. K. Rowling', 'Britânica'),
                                            ('George Orwell', 'Britânico'),
                                            ('J. R. R. Tolkien', 'Britânico'),
                                            ('Stephen King', 'Americano'),
                                            ('Agatha Christie', 'Britânica'),
                                            ('Paulo Coelho', 'Brasileiro'),
                                            ('Dan Brown', 'Americano'),
                                            ('C. S. Lewis', 'Britânico');

-- =====================================
-- INSERTS CATEGORIAS
-- =====================================

INSERT INTO categoria (nome) VALUES
                                 ('Romance'),
                                 ('Fantasia'),
                                 ('Terror'),
                                 ('Ficção Científica'),
                                 ('Mistério'),
                                 ('Drama'),
                                 ('Aventura'),
                                 ('Suspense'),
                                 ('Literatura Brasileira'),
                                 ('Infantil');

-- =====================================
-- INSERTS LIVROS
-- =====================================

INSERT INTO livro
(titulo, ano_publicacao, quantidade, autor_id, categoria_id)
VALUES
    ('Dom Casmurro', 1899, 5, 1, 9),
    ('A Hora da Estrela', 1977, 3, 2, 6),
    ('Harry Potter e a Pedra Filosofal', 1997, 10, 3, 2),
    ('1984', 1949, 4, 4, 4),
    ('O Senhor dos Anéis', 1954, 7, 5, 2),
    ('It: A Coisa', 1986, 2, 6, 3),
    ('Assassinato no Expresso do Oriente', 1934, 6, 7, 5),
    ('O Alquimista', 1988, 8, 8, 1),
    ('Código Da Vinci', 2003, 9, 9, 8),
    ('As Crônicas de Nárnia', 1950, 5, 10, 10);

select * from livro;