CREATE TABLE "employee" (
                            "id" numeric(20) NOT NULL,
                            "first_name" varchar(50) NOT NULL,
                            "last_name" varchar(50) NOT NULL,
                            "email" varchar(50) UNIQUE NOT NULL,
                            "phone" varchar(15) UNIQUE NOT NULL,
                            "department_id" numeric(20) NOT NULL,
                            "delete_flag" BOOLEAN NOT NULL,
                            "creation_date" TIMESTAMP NOT NULL,
                            "modification_date" TIMESTAMP,
                            CONSTRAINT "employee_pk" PRIMARY KEY ("id")
);
CREATE SEQUENCE employee_seq START 1;


CREATE TABLE "department" (
                              "id" numeric(20) NOT NULL,
                              "name" varchar(50) NOT NULL,
                              "location" varchar(50) NOT NULL,
                              CONSTRAINT "department_pk" PRIMARY KEY ("id")
);
CREATE SEQUENCE department_seq START 1;


CREATE TABLE "employees_relation" (
                                     "superior_id" numeric(20) NOT NULL,
                                     "subordinate_id" numeric(20) NOT NULL,
                                     UNIQUE ("superior_id", "subordinate_id")
);


ALTER TABLE "employee" ADD CONSTRAINT "employee_to_department_id_fkey" FOREIGN KEY ("department_id") REFERENCES "department"("id");


ALTER TABLE "employees_relation" ADD CONSTRAINT "employee_relation_superior_id_fkey" FOREIGN KEY ("superior_id") REFERENCES "employee"("id");
ALTER TABLE "employees_relation" ADD CONSTRAINT "employee_relation_subordinate_id_fkey" FOREIGN KEY ("subordinate_id") REFERENCES "employee"("id");
