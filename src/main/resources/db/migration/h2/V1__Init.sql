create table bond (
    bonds_id integer generated by default as identity,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    bond_isin varchar(255) not null,
    bond_name varchar(255) not null,
    bond_state boolean not null,
    crypto_pair varchar(255) not null,
    primary key (bonds_id)
);

create table indicator (
    indicators_id integer generated by default as identity,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    indicator_name varchar(255) not null,
    indicator_symbol varchar(255) not null,
    primary key (indicators_id)
);

create table indicator_bond_setting (
    indicator_bond_settings_id integer generated by default as identity,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    indicator_key varchar(255) not null,
    indicator_value varchar(255) not null,
    bonds_bonds_id integer,
    indicator_configuration_indicator_configurations_id integer,
    primary key (indicator_bond_settings_id)
);

create table indicator_configuration (
    indicator_configurations_id integer generated by default as identity,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    indicator_configuration_name binary not null,
    indicators_indicators_id integer,
    primary key (indicator_configurations_id)
);

create table macd_data (
    time timestamp not null,
    bond_id int not null,
    exchange_id int not null,
    signal numeric(10,10) not null,
    fast_macd numeric(10,10) not null,
    slow_macd numeric(10,10) not null
);

alter table indicator_bond_setting add constraint FKd33u58684smdmvnmwejt03ani foreign key (bonds_bonds_id) references bond;
alter table indicator_bond_setting add constraint FKnfavlct18ekgw52cqwar6kf31 foreign key (indicator_configuration_indicator_configurations_id) references indicator_configuration;
alter table indicator_configuration add constraint FKd8p7ebq4u3p7lhanayao1j8g2 foreign key (indicators_indicators_id) references indicator;
