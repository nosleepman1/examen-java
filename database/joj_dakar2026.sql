
DROP DATABASE IF EXISTS joj_dakar2026;
CREATE DATABASE joj_dakar2026;
USE joj_dakar2026;

-- ---------------------------------------------------------------------------
-- Tables
-- ---------------------------------------------------------------------------

CREATE TABLE utilisateur (
    id_utilisateur INT AUTO_INCREMENT PRIMARY KEY,
    nom_complet     VARCHAR(100) NOT NULL,
    login           VARCHAR(50)  NOT NULL UNIQUE,
    mot_de_passe    VARCHAR(100) NOT NULL,
    role            VARCHAR(20)  NOT NULL
);

CREATE TABLE pays (
    id_pays    INT AUTO_INCREMENT PRIMARY KEY,
    nom_pays   VARCHAR(100) NOT NULL,
    continent  VARCHAR(50)  NOT NULL
);

CREATE TABLE discipline (
    id_discipline   INT AUTO_INCREMENT PRIMARY KEY,
    nom_discipline  VARCHAR(100) NOT NULL,
    description     VARCHAR(255)
);

CREATE TABLE athlete (
    id_athlete      INT AUTO_INCREMENT PRIMARY KEY,
    nom             VARCHAR(50) NOT NULL,
    prenom          VARCHAR(50) NOT NULL,
    sexe            VARCHAR(1)  NOT NULL,
    date_naissance  DATE        NOT NULL,
    id_pays         INT NOT NULL,
    id_discipline   INT NOT NULL,
    CONSTRAINT fk_athlete_pays FOREIGN KEY (id_pays) REFERENCES pays(id_pays),
    CONSTRAINT fk_athlete_discipline FOREIGN KEY (id_discipline) REFERENCES discipline(id_discipline)
);

CREATE TABLE competition (
    id_competition    INT AUTO_INCREMENT PRIMARY KEY,
    nom_competition   VARCHAR(100) NOT NULL,
    date_competition  DATE NOT NULL,
    lieu              VARCHAR(30) NOT NULL,
    id_discipline     INT NOT NULL,
    CONSTRAINT fk_competition_discipline FOREIGN KEY (id_discipline) REFERENCES discipline(id_discipline)
);

CREATE TABLE resultat (
    id_resultat     INT AUTO_INCREMENT PRIMARY KEY,
    id_athlete      INT NOT NULL,
    id_competition  INT NOT NULL,
    score           DOUBLE NOT NULL,
    rang            INT NOT NULL,
    CONSTRAINT fk_resultat_athlete FOREIGN KEY (id_athlete) REFERENCES athlete(id_athlete),
    CONSTRAINT fk_resultat_competition FOREIGN KEY (id_competition) REFERENCES competition(id_competition)
);

-- ---------------------------------------------------------------------------
-- Donnees de test
-- ---------------------------------------------------------------------------

INSERT INTO utilisateur (nom_complet, login, mot_de_passe, role) VALUES
('Administrateur Systeme', 'admin', 'admin123', 'ADMIN'),
('Fatou Diop', 'fdiop', 'passer123', 'UTILISATEUR');

INSERT INTO pays (nom_pays, continent) VALUES
('Senegal', 'Afrique'),
('France', 'Europe'),
('Maroc', 'Afrique');

INSERT INTO discipline (nom_discipline, description) VALUES
('Athletisme', 'Courses, sauts et lancers'),
('Natation', 'Epreuves en bassin'),
('Judo', 'Sport de combat olympique');

INSERT INTO athlete (nom, prenom, sexe, date_naissance, id_pays, id_discipline) VALUES
('Ndiaye', 'Amadou', 'M', '2009-03-12', 1, 1),
('Faye', 'Awa', 'F', '2008-11-02', 1, 2),
('Dubois', 'Leo', 'M', '2009-07-19', 2, 1),
('Benali', 'Sara', 'F', '2008-05-27', 3, 3);

INSERT INTO competition (nom_competition, date_competition, lieu, id_discipline) VALUES
('100m Garcons', '2026-05-10', 'Dakar', 1),
('200m Nage Libre Filles', '2026-05-11', 'Diamniadio', 2),
('Judo -60kg Filles', '2026-05-12', 'Saly', 3);

INSERT INTO resultat (id_athlete, id_competition, score, rang) VALUES
(1, 1, 10.85, 1),
(3, 1, 10.97, 2),
(2, 2, 120.30, 1),
(4, 3, 95.00, 1);
