truncate table address cascade;

insert into address(id, country, region, city, street, house, flat, created, modified) values
(4, 'Russia', null, 'Novosibirsk', null, null, null, '2021-02-08T03:01:32.592', null),
(2, null, null, 'London', null, null, null, '2021-02-08T03:01:32.592', null),
(3, null, null, 'Orel', null, null, null, '2021-02-08T03:01:32.592', null);

alter sequence address_id_seq restart with 10;
