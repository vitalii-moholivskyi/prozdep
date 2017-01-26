CREATE TABLE department(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL
);

CREATE TABLE scientist(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL
);

CREATE TABLE topic(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  client VARCHAR(255) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE,
  department_id INT NOT NULL,
  chief_scientist_id INT NOT NULL,
  FOREIGN KEY (department_id)
        REFERENCES department(id),
  FOREIGN KEY (chief_scientist_id)
        REFERENCES scientist(id)
);

CREATE TABLE paper(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL,
  year INT NOT NULL
);

CREATE TABLE paper_topic(
  paper_id INT,
  topic_id INT,
  PRIMARY KEY(paper_id, topic_id),
  FOREIGN KEY (paper_id)
        REFERENCES paper(id),
  FOREIGN KEY (topic_id)
        REFERENCES topic(id)
);

CREATE TABLE scientist_topic(
  scientist_id INT,
  topic_id INT,
  PRIMARY KEY(scientist_id, topic_id),
  FOREIGN KEY (scientist_id)
        REFERENCES scientist(id),
  FOREIGN KEY (topic_id)
        REFERENCES topic(id)
);

CREATE TABLE scientist_paper(
  scientist_id INT,
  paper_id INT,
  PRIMARY KEY(scientist_id, paper_id),
  FOREIGN KEY (scientist_id)
        REFERENCES scientist(id),
  FOREIGN KEY (paper_id)
        REFERENCES paper(id)
);

CREATE TABLE teacher(
  department_id INT,
  scientist_id INT,
  position VARCHAR(255) NOT NULL,
  degree VARCHAR(255) NOT NULL,
  start_date DATE NOT NULL,
  PRIMARY KEY(department_id, scientist_id),
  FOREIGN KEY (department_id)
        REFERENCES department(id),
  FOREIGN KEY (scientist_id)
        REFERENCES scientist(id)
);

CREATE TABLE master(
  department_id INT,
  scientist_id INT,
  topic VARCHAR(255) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  teacher_id INT,
  PRIMARY KEY(department_id, scientist_id),
  FOREIGN KEY (department_id)
        REFERENCES department(id),
  FOREIGN KEY (scientist_id)
        REFERENCES scientist(id)
);

CREATE TABLE postgraduate(
  department_id INT,
  scientist_id INT,
  topic VARCHAR(255) NOT NULL,
  start_date DATE NOT NULL,
  protection_date DATE NOT NULL,
  teacher_id INT,
  PRIMARY KEY(department_id, scientist_id),
  FOREIGN KEY (department_id)
        REFERENCES department(id),
  FOREIGN KEY (scientist_id)
        REFERENCES scientist(id)
);
