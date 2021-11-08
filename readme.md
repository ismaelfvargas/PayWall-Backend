
>>>> inserts no banco de dados <<<<

insert into usuarios (nome, senha, area, roles)
values ('ismael.freitas@fundacred.org.br', '123456', 'FINANCEIRO E CONTROLADORIA', 'ASSISTENTE');

insert into tipo_pedido values (1, 'PAGAMENTO');
insert into tipo_pedido values (2, 'ADIANTAMENTO');
insert into tipo_pedido values (3, 'PRESTAÇÃO DE CONTAS');

insert into tipo_status values (1, 'EM ABERTO');
insert into tipo_status values (2, 'REPROVADO');
insert into tipo_status values (3, 'APROVADO');
insert into tipo_status values (4, 'LANÇADO');
insert into tipo_status values (5, 'PAGO');


select * from usuarios;

select * from pedido;

select * from tipo_pedido;

select * from tipo_status;