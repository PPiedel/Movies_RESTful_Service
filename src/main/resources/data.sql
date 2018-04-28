INSERT INTO genre(id,name) VALUES (1,'Comedy');
INSERT INTO genre(id,name) VALUES (2,'Drama');
INSERT INTO genre(id,name) VALUES (3,'Action');
INSERT INTO genre(id,name) VALUES (4,'Romance');
INSERT INTO genre(id,name) VALUES (5,'Thriller');
INSERT INTO genre(id,name) VALUES (6,'Historical');

INSERT INTO production_company(id,name) VALUES (1,'WaterGate Productions');
INSERT INTO production_company(id,name) VALUES (2,'Looking Glass Studios');
INSERT INTO production_company(id,name) VALUES (3,'Continuum Films');
INSERT INTO production_company(id,name) VALUES (4,'Off the Spectrum Productions');
INSERT INTO production_company(id,name) VALUES (5,'Sandstone Films');
INSERT INTO production_company(id,name) VALUES (6,'DNA Productions');
INSERT INTO production_company(id,name) VALUES (7,'Acid Rain Films');
INSERT INTO production_company(id,name) VALUES (8,'Enclave Productions');

INSERT INTO production_country(id,iso_code,name) VALUES (1,'US','United States');
INSERT INTO production_country(id,iso_code,name) VALUES (2,'GB','Great Britain');
INSERT INTO production_country(id,iso_code,name) VALUES (3,'NO','Norway');
INSERT INTO production_country(id,iso_code,name) VALUES (4,'PL','Poland');
INSERT INTO production_country(id,iso_code,name) VALUES (5,'DE','Germany');
INSERT INTO production_country(id,iso_code,name) VALUES (6,'FR','France');
INSERT INTO production_country(id,iso_code,name) VALUES (7,'IS','Iceland');

INSERT INTO movie(id,title,duration,release_date) VALUES (1,'The Shawshank Redemption',165,'1994-09-22');

INSERT INTO movies_genres(movie_id,genre_id) VALUES (1,1);
INSERT INTO movies_genres(movie_id,genre_id) VALUES (1,2);
INSERT INTO movies_genres(movie_id,genre_id) VALUES (1,3);

INSERT INTO MOVIES_PRODUCTION_COMPANIES(movie_id,production_company_id) VALUES (1,1);
INSERT INTO MOVIES_PRODUCTION_COMPANIES(movie_id,production_company_id) VALUES (1,2);

INSERT INTO MOVIES_PRODUCTION_COUNTRIES(movie_id,production_country_id)VALUES (1,1);
INSERT INTO MOVIES_PRODUCTION_COUNTRIES(movie_id,production_country_id)VALUES (1,2);
INSERT INTO MOVIES_PRODUCTION_COUNTRIES(movie_id,production_country_id)VALUES (1,3);
