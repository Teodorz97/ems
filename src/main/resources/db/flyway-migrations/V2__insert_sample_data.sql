INSERT INTO department (id,"name","location") VALUES
(nextval('department_seq'),'Wealth Management','Frankfurt'),
(nextval('department_seq'),'Private Port','Frankfurt'),
(nextval('department_seq'),'UCB','Sofia');

INSERT INTO employee (id,first_name,last_name,email,phone,department_id,delete_flag,creation_date,modification_date) VALUES
(nextval('employee_seq'),'Hristo','Ivanov','hristo.ivanov@test.com','+359881739027',1,false,CURRENT_TIMESTAMP,NULL),
(nextval('employee_seq'),'Ivan','Markov','ivan.markov@test.com','+359871523978',3,false,CURRENT_TIMESTAMP,NULL),
(nextval('employee_seq'),'Filipa','Petkova','filipa.petkova@test.com','+359886249030',2,false,CURRENT_TIMESTAMP,NULL),
(nextval('employee_seq'),'Teodor','Zahariev','teodor.zahariev@test.com','+359887147121',1,false,CURRENT_TIMESTAMP,NULL),
(nextval('employee_seq'),'Simeon','Tashev','simeon.tashev@test.com','+359610743237',1,false,CURRENT_TIMESTAMP,NULL),
(nextval('employee_seq'),'Yordan','Terziev','yordan.terziev@test.com','+359874901852',1,false,CURRENT_TIMESTAMP,NULL),
(nextval('employee_seq'),'Yavor','Grigorov','yavor.grigorov@test.com','+359874900788',2,false,CURRENT_TIMESTAMP,NULL);

INSERT INTO employees_relation (superior_id,subordinate_id) VALUES
(1,4),
(1,5),
(5,4),
(4,6),
(5,6),
(3,7);