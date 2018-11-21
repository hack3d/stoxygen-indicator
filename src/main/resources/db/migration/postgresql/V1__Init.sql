create table bond (
    bonds_id  serial not null,
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

create table exchange (
    exchanges_id  serial not null,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    country_code varchar(4) not null,
    interval_delay integer default 0,
    name varchar(255) not null,
    symbol varchar(255) not null,
    primary key (exchanges_id)
);

create table exchange_bonds (
    exchanges_exchanges_id int4 not null,
    bonds_bonds_id int4 not null,
    primary key (exchanges_exchanges_id, bonds_bonds_id)
);

create table indicator (
    indicators_id  serial not null,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    indicator_name varchar(255) not null,
    indicator_symbol varchar(255) not null,
    primary key (indicators_id)
);

create table indicator_bond_setting (
    indicator_bond_settings_id  serial not null,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    indicator_key varchar(255) not null,
    indicator_value varchar(255) not null,
    bonds_bonds_id int4,
    indicator_configuration_indicator_configurations_id int4,
    primary key (indicator_bond_settings_id)
);

create table indicator_configuration (
    indicator_configurations_id  serial not null,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    indicator_configuration_name uuid not null,
    indicators_indicators_id int4,
    primary key (indicator_configurations_id)
);

create table macd_data (
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    macd_data_id  serial not null,
    macd_data_point float4 not null,
    macd_signal float4,
    timestamp timestamp not null,
    aggregate varchar(255) not null,
    indicator_configuration_indicator_configurations_id int4,
    primary key (macd_data_id)
);

alter table exchange_bonds add constraint FKm5a9ds39gckypvgescolen53h foreign key (bonds_bonds_id) references bond (bonds_id);
alter table exchange_bonds add constraint FK6yy0ft6s7anj8loeb0g0310p9 foreign key (exchanges_exchanges_id) references exchange (exchanges_id);
alter table indicator_bond_setting add constraint FKd33u58684smdmvnmwejt03ani foreign key (bonds_bonds_id) references bond (bonds_id);
alter table indicator_bond_setting add constraint FKnfavlct18ekgw52cqwar6kf31 foreign key (indicator_configuration_indicator_configurations_id) references indicator_configuration (indicator_configurations_id);
alter table indicator_configuration add constraint FKd8p7ebq4u3p7lhanayao1j8g2 foreign key (indicators_indicators_id) references indicator (indicators_id);
alter table macd_data add constraint FKnhjtipo8tfc0kf5930tg3kbu2 foreign key (indicator_configuration_indicator_configurations_id) references indicator_configuration (indicator_configurations_id);
