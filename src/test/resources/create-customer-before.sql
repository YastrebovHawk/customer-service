truncate table customer cascade;

insert into customer (id, registred_address_id, actual_address_id, first_name, last_name, middle_name, sex) values
(4, 4, 2, 'Dima', 'Yastrebov', 'An', 'male'),
(2, 2, 4, 'Pupa', 'Po', 'An', 'female'),
(3, 3, 3, 'Po', 'Po', 'An', 'female');

alter sequence customer_id_seq restart with 10;