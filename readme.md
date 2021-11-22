
>>>> inserts no banco de dados <<<<

insert into usuarios (nome, senha, area, roles)
values ('ismael.freitas@fundacred.org.br', '123456', 'FINANCEIRO E CONTROLADORIA', 'ASSISTENTE');

insert into usuarios (nome, senha, area, roles)
values ('nivio.delgado@fundacred.org.br', '123456', 'FINANCEIRO E CONTROLADORIA', 'DIRETOR');

insert into usuarios (nome, senha, area, roles)
values ('patricia.kniest@fundacred.org.br', '123456', 'FINANCEIRO E CONTROLADORIA', 'GERENTE');

insert into usuarios (nome, senha, area, roles)
values ('leon.arthur@fundacred.org.br', '123456', 'FINANCEIRO E CONTROLADORIA', 'USER');

insert into tipo_pedido values (1, 'PAGAMENTO');
insert into tipo_pedido values (2, 'ADIANTAMENTO');
insert into tipo_pedido values (3, 'PRESTAÇÃO DE CONTAS');

insert into tipo_status values (1, 'EM ABERTO');
insert into tipo_status values (2, 'REPROVADO');
insert into tipo_status values (3, 'APROVADO');
insert into tipo_status values (4, 'LANÇADO');
insert into tipo_status values (5, 'PAGO');

insert into tipo_status_adto values (1, 'AGUARDANDO PRESTAÇÃO DE CONTAS');
insert into tipo_status_adto values (2, 'PRESTADO CONTAS');

select * from usuarios;

select * from pedido;

select * from tipo_pedido;

select * from tipo_status;

select * from files;

commit;