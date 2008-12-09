CREATE TABLE FreeSideContents (
  id INTEGER  NOT NULL AUTO_INCREMENT,
  priority INTEGER  NOT NULL,
  title TINYTEXT NOT NULL,
  contents TEXT NOT NULL,
  PRIMARY KEY(id)
)

CREATE TABLE Image (
  id INTEGER  NOT NULL AUTO_INCREMENT,
  name TINYTEXT NOT NULL,
  image MEDIUMBLOB NOT NULL,
  thumbnail BLOB NULL,
  date TIMESTAMP NOT NULL,
  PRIMARY KEY(id)
)

CREATE TABLE Setting (
  id INTEGER  NOT NULL AUTO_INCREMENT,
  uid TINYTEXT NOT NULL,
  pass TINYTEXT NOT NULL,
  blogname TINYTEXT NOT NULL,
  description TINYTEXT NOT NULL,
  about TINYTEXT NOT NULL,
  blogurl TINYTEXT NOT NULL,
  listnum INTEGER  NOT NULL,
  PRIMARY KEY(id)
)

CREATE TABLE FreeContents (
  id INTEGER  NOT NULL AUTO_INCREMENT,
  priority INTEGER  NOT NULL,
  title TINYTEXT NOT NULL,
  contents TEXT NOT NULL,
  PRIMARY KEY(id)
)

CREATE TABLE Article (
  id INTEGER  NOT NULL AUTO_INCREMENT,
  title TINYTEXT NOT NULL,
  contents TEXT NOT NULL,
  date TIMESTAMP NOT NULL,
  PRIMARY KEY(id)
)

CREATE TABLE Category (
  id INTEGER  NOT NULL AUTO_INCREMENT,
  name TINYTEXT NOT NULL,
  PRIMARY KEY(id)
)

CREATE TABLE Response (
  id INTEGER NOT NULL AUTO_INCREMENT,
  articleid INTEGER  NOT NULL,
  name TINYTEXT NOT NULL,
  contents TINYTEXT NOT NULL,
  date TIMESTAMP NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(articleid)
    REFERENCES Article(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)

CREATE TABLE Trackback (
  id INTEGER NOT NULL AUTO_INCREMENT,
  articleid INTEGER  NOT NULL,
  title TINYTEXT NOT NULL,
  excerpt TINYTEXT NOT NULL,
  url TINYTEXT NOT NULL,
  blogname TINYTEXT NOT NULL,
  date TIMESTAMP NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(articleid)
    REFERENCES Article(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)

CREATE TABLE ArticleToCategory (
  id INTEGER  NOT NULL AUTO_INCREMENT,
  articleid INTEGER  NOT NULL,
  categoryid INTEGER  NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(articleid)
    REFERENCES Article(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(categoryid)
    REFERENCES Category(id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)

CREATE TABLE ImageToCategory (
  id INTEGER  NOT NULL AUTO_INCREMENT,
  categoryid INTEGER  NOT NULL,
  imageid INTEGER  NOT NULL,
  PRIMARY KEY(id)
)