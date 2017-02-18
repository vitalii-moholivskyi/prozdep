/*
source /home/mogo/w/4course/prozdep/database_scripts/insert_test_data.sql
*/
INSERT INTO department VALUES(1,'Computer Sciences', '(044) 456-323');
INSERT INTO department VALUES(2,'Mathematics', '(044) 556-423');
INSERT INTO department VALUES(3,'Multimedia Systems', '(044) 546-477');
INSERT INTO department VALUES(4,'Network Technologies', '(044) 546-117');

/*Test data for table scientist*/
/*In every ten: 1,2,4 - teachers, 4,5 - postgraduates, 6,7,8,9,10 - masters*/
/*Scientist for department of Compure Sciences ids 1 - 100*/
/*INSERT INTO scientist VALUES(1,'', '(063) 380-46-57');*/
INSERT INTO scientist VALUES(1,'Тихонов Олександр Семенович', '(063) 380-46-57');
INSERT INTO scientist VALUES(2,'Загребський Сергій Олексійович', '(096) 472-94-96');
INSERT INTO scientist VALUES(3,'Непийвода Іван Іванович', '(073) 341-13-18');
INSERT INTO scientist VALUES(4,'Економненко Михайло Михайлович', '(066) 432-19-08');
INSERT INTO scientist VALUES(5,'Всевидько Галина Володимирівна', '(063) 377-46-57');
INSERT INTO scientist VALUES(6,'Черненко Тимофій Валерійович', '(096) 442-94-96');
INSERT INTO scientist VALUES(7,'Терещенко Всеволод Іванович', '(073) 341-23-18');
INSERT INTO scientist VALUES(8,'Приходько Софія Олегівна', '(073) 972-19-48');
INSERT INTO scientist VALUES(9,'Добрийдень Марта Сергіївна', '(050) 982-11-38');
INSERT INTO scientist VALUES(10,'Черепашенко Вікторія Тимофіївна', '(095) 912-19-98');

INSERT INTO teacher VALUES(1, 'Професор', 'Доктор філософії', '2000-01-31',  1);
INSERT INTO teacher VALUES(2, 'Доцент', 'Доцент', '2000-01-31', 1);
INSERT INTO teacher VALUES(3, 'Викладач', 'Доктор наук', '2000-01-31', 1);
INSERT INTO postgraduate VALUES(4, 'Застовування машинного навчання при дослідженні космосу', '2016-08-31', '2020-06-30', '2020-05-31', 1, 1);
INSERT INTO postgraduate VALUES(5, 'Використання BigData для масового впливу на населення', '2016-08-31', '2020-06-30', '2020-05-31', 2, 1);
INSERT INTO postgraduate VALUES(6, 'Розробка протоколу мережевої комунікації для колонії на Марсі', '2016-08-31', '2020-06-30', '2020-05-31', 3, 1);
INSERT INTO master VALUES(7, 'Розробка програмного забезпечення для штучних органів', '2016-08-31', '2018-06-30', 1, 1);
INSERT INTO master VALUES(8, 'Алгоритми оптимізації транспортних шляхів', '2016-08-31', '2018-06-30', 2, 1);
INSERT INTO master VALUES(9, 'Трансформування нейронних імпульсів у цифрові радіо сигнали стандарту IEEE 802.11', '2016-08-31', '2018-06-30', 3, 1);
INSERT INTO master VALUES(10,'Розробка робота-повара', '2016-08-31', '2018-06-30', 1, 1);

/*List of topics and people who works on them*/
INSERT INTO topic VALUES(1, 'Застосування інформаціних технологій при колонізації Марсу', 'NACA', '2014-01-31', '2024-01-31', 1, 1);
INSERT INTO scientist_topic VALUES(1,1);
INSERT INTO scientist_topic VALUES(4,1);
INSERT INTO scientist_topic VALUES(6,1);
INSERT INTO scientist_topic VALUES(10,1);
INSERT INTO topic VALUES(2, 'Удосконалення транстортної системи міста', 'КМДА', '2014-01-31', '2024-01-31', 1, 2);
INSERT INTO scientist_topic VALUES(2,2);
INSERT INTO scientist_topic VALUES(8,2);
INSERT INTO topic VALUES(3, 'Застосування IT у виборчих кампініях', 'Дональд Трамп', '2014-01-31', '2024-01-31', 1, 3);
INSERT INTO scientist_topic VALUES(3,3);
INSERT INTO scientist_topic VALUES(5,3);
INSERT INTO topic VALUES(4, 'Роботизація харчової промисловості', 'КиївХліб', '2014-01-31', '2024-01-31', 1, 1);
INSERT INTO scientist_topic VALUES(1,4);
INSERT INTO scientist_topic VALUES(10,4);
INSERT INTO topic VALUES(5, 'Операційна система для штучних органів', 'Клініка "Борис"', '2014-01-31', '2024-01-31', 1, 2);
INSERT INTO scientist_topic VALUES(2,5);
INSERT INTO scientist_topic VALUES(7,5);
INSERT INTO topic VALUES(6, 'Засоби телепатичного керування ком''ютерами', 'Lenovo', '2014-01-31', '2024-01-31', 1, 3);
INSERT INTO scientist_topic VALUES(3,6);
INSERT INTO scientist_topic VALUES(9,1);

/*List of paper and topic/scientist related to them*/
/*Для 1 теми*/
INSERT INTO paper VALUES(1,'Основи асинхронної комінікації', 'Наукова стаття', 2012);
INSERT INTO paper_topic VALUES(1,1);
INSERT INTO scientist_paper VALUES(6,1);

INSERT INTO paper VALUES(2,'Дослідження ефективності асинхронної комінікації на великих відстаннях','Дослідно-експериментальна розробка',2012);
INSERT INTO paper_topic VALUES(2,1);
INSERT INTO scientist_paper VALUES(6,2);

INSERT INTO paper VALUES(3,'Протокол мережевої комунікації для колонії на Марсі','Аспірантська робота',2012);
INSERT INTO paper_topic VALUES(3,1);
INSERT INTO scientist_paper VALUES(6,3);

/*Для 2 теми*/
INSERT INTO paper VALUES(4,'Огляд існуючих алгоритмів пошуку шляху','Наукова стаття',2012);
INSERT INTO paper_topic VALUES(4,2);
INSERT INTO scientist_paper VALUES(8,4);

INSERT INTO paper VALUES(5,'Моделювання транспортної системи Києва','Дослідно-експериментальна розробка',2012);
INSERT INTO paper_topic VALUES(5,2);
INSERT INTO scientist_paper VALUES(8,5);

INSERT INTO paper VALUES(6,'Побудова аогоритму оптимізації транспортних шляхів','Магістерська робота',2012);
INSERT INTO paper_topic VALUES(6,2);
INSERT INTO scientist_paper VALUES(8,6);

/*Для 3 теми*/
INSERT INTO paper VALUES(7,'Огляд методів інформаційної підтримки виборчих кампіній','Наукова стаття',2012);
INSERT INTO paper_topic VALUES(7,3);
INSERT INTO scientist_paper VALUES(5,7);

INSERT INTO paper VALUES(8,'Дослідження результатів впливу аналітичних даних отриманих за допомогою BigData на ефективність інформаційного контенту виборчої компанії','Дослідно-експериментальна розробка',2012);
INSERT INTO paper_topic VALUES(8,3);
INSERT INTO scientist_paper VALUES(5,8);

INSERT INTO paper VALUES(9,'Застосування BigData у виборчих кампініях','Магістерська робота',2012);
INSERT INTO paper_topic VALUES(9,3);
INSERT INTO scientist_paper VALUES(5,9);

/*Для 4 теми*/
INSERT INTO paper VALUES(10, 'Роль роботизації у харчовій промисловості','Наукова стаття',2012);
INSERT INTO paper_topic VALUES(10, 4);
INSERT INTO scientist_paper VALUES(10, 10);

INSERT INTO paper VALUES(11, 'Дослідження ефективності роботи робота у реальний умовах','Дослідно-експериментальна розробка',2012);
INSERT INTO paper_topic VALUES(11, 4);
INSERT INTO scientist_paper VALUES(10, 11);

INSERT INTO paper VALUES(12, 'Розробка універсального робота-повара','Магістерська робота',2012);
INSERT INTO paper_topic VALUES(12, 4);
INSERT INTO scientist_paper VALUES(10, 12);

/*Для 5 теми*/
INSERT INTO paper VALUES(13, 'Застосування програмованих біо-органів','Наукова стаття',2012);
INSERT INTO paper_topic VALUES(13, 5);
INSERT INTO scientist_paper VALUES(7, 13);

INSERT INTO paper VALUES(14, 'Прототипування роботи сердця','Дослідно-експериментальна розробка',2012);
INSERT INTO paper_topic VALUES(14, 5);
INSERT INTO scientist_paper VALUES(7, 14);

INSERT INTO paper VALUES(15, 'Операційна система для штучного сердця','Магістерська робота',2012);
INSERT INTO paper_topic VALUES(15, 5);
INSERT INTO scientist_paper VALUES(7, 15);

/*Для 6 теми*/
INSERT INTO paper VALUES(16, 'Аналіх існуючих засобів телепатичного керування ком''ютерами','Наукова стаття',2012);
INSERT INTO paper_topic VALUES(16, 6);
INSERT INTO scientist_paper VALUES(9, 16);

INSERT INTO paper VALUES(17, 'Класифікація нейронрих сигналів','Наукова стаття',2012);
INSERT INTO paper_topic VALUES(17, 6);
INSERT INTO scientist_paper VALUES(9, 17);

INSERT INTO paper VALUES(18, 'Створення аналізатора нейронних сигналів','Магістерська робота',2012);
INSERT INTO paper_topic VALUES(18, 6);
INSERT INTO scientist_paper VALUES(9, 18);
INSERT INTO scientist_paper VALUES(3, 18);

INSERT INTO paper VALUES(19, 'Програмний аналіз нейроних сигналів для управління космічною станцією','Дослідно-експериментальна розробка',2012);
INSERT INTO paper_topic VALUES(19, 6);
INSERT INTO paper_topic VALUES(19, 1);
INSERT INTO scientist_paper VALUES(9, 19);
INSERT INTO scientist_paper VALUES(3, 19);
INSERT INTO scientist_paper VALUES(1, 19);



/*Scientist for department of Mathematics ids 101 - 200*/
INSERT INTO scientist VALUES(101,'Сергієнко В''ячеслав Олександрович', '(073) 380-61-50');
INSERT INTO scientist VALUES(102,'Ножій Христина Вікторівна', '(093) 380-62-90');
INSERT INTO scientist VALUES(103,'Лампочкін Лев Леонтійович', '(066) 380-53-37');
INSERT INTO scientist VALUES(104,'Біленко Пилип Пилипович', '(093) 380-44-22');
INSERT INTO scientist VALUES(105,'Ложкіна Анастасія Олександрівна', '(050) 380-35-13');
INSERT INTO scientist VALUES(106,'Чорний Тарас Григорійович', '(095) 380-26-55');
INSERT INTO scientist VALUES(107,'Андрусяк Наталія Максимівна', '(066) 380-27-27');
INSERT INTO scientist VALUES(108,'Максименко Валерія Михайлівна', '(085) 380-98-54');
INSERT INTO scientist VALUES(109,'Мороз Олександра Павлівна', '(060) 380-89-53');
INSERT INTO scientist VALUES(110,'Козак Володимир Всеволодич', '(073) 380-70-59');
/*Scientist for department of Multimedia Systems ids 201 - 300*/
/*Scientist for department of Network Technologies ids 301 - 400*/

/*Clean database
DELETE FROM scientist_paper;
DELETE FROM paper_topic;
DELETE FROM scientist_topic;
DELETE FROM topic;
DELETE FROM paper;
DELETE FROM postgraduate;
DELETE FROM master;
DELETE FROM teacher;
DELETE FROM scientist;
DELETE FROM department;
*/
/*Наукові ступені:
Доктор філософії
Доктор наук

Вчені завання:
Доцент
Старший дослідник
Професор

Можливі посади
3. Вищі навчальні         керівник (ректор, директор, начальник
   заклади та заклади     тощо), заступник керівника (ректора,
   післядипломної освіти  директора, начальника тощо) з основного
   (їхні філії) III-IV    напряму діяльності державного вищого
   рівнів акредитації     навчального закладу або закладу
   (інститут,             післядипломної освіти (їхні філії)
   консерваторія,         III-IV рівнів акредитації; завідувач
   академія, університет  (навчальної частини), професор, доцент,
   тощо); наукові         старший викладач, викладач, асистент
   підрозділи в їхньому   кафедри (курсів); учений секретар;
   складі                 керівник (завідувач, начальник тощо),
                          заступник керівника (завідувача,
                          начальника тощо), головний науковий
                          співробітник, провідний науковий
                          співробітник, старший науковий
                          співробітник, науковий співробітник,
                          молодший науковий співробітник
                          підрозділу (відділення, відділу,
                          лабораторії, сектору, групи тощо), які
                          виконують наукову, науково-технічну або
                          науково-організаційну роботу. */

/* Типи наукових праць
Есе
Реферат
Рецензія
Переклад
Теза
Наукова стаття
Анотація
Бакалаврська робота
Дипломна робота
Дипломна робота МБА
Магістерська робота
Аспірантська робота
Докторська дисертація
Дослідно-експериментальна розробка
Консультаційний проект*/