
>>>> inserts no banco de dados <<<<

insert into usuarios (nome, senha, area, roles)
values ('ismael.freitas@fundacred.org.br', '123456', 'FINANCEIRO E CONTROLADORIA', 'ASSISTENTE');

insert into usuarios (nome, senha, area, roles)
values ('nivio.delgado@fundacred.org.br', '123456', 'FINANCEIRO E CONTROLADORIA', 'DIRETOR');

insert into usuarios (nome, senha, area, roles)
values ('patricia.kniest@fundacred.org.br', '123456', 'FINANCEIRO E CONTROLADORIA', 'GERENTE');

insert into usuarios (nome, senha, area, roles)
values ('leon.arthur@fundacred.org.br', '123456', 'FINANCEIRO E CONTROLADORIA', 'USER');

insert into tipo_pedidos values (1, 'PAGAMENTO');
insert into tipo_pedidos values (2, 'ADIANTAMENTO');
insert into tipo_pedidos values (3, 'PRESTAÇÃO DE CONTAS');

insert into status_solicitacoes values (1, 'EM ABERTO');
insert into status_solicitacoes values (2, 'REPROVADO');
insert into status_solicitacoes values (3, 'APROVADO');
insert into status_solicitacoes values (4, 'LANÇADO');
insert into status_solicitacoes values (5, 'PAGO');

insert into status_adiantamentos values (1, 'AGUARDANDO PRESTAÇÃO DE CONTAS');
insert into status_adiantamentos values (2, 'PRESTADO CONTAS');

select * from usuarios;

select * from pedido;

select * from tipo_pedido;

select * from tipo_status;

select * from files;

commit;