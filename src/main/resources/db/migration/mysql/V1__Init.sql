create table bond (
    bonds_id integer not null auto_increment,
    insert_timestamp datetime,
    insert_user varchar(255),
    modify_timestamp datetime,
    modify_user varchar(255),
    bond_isin varchar(255) not null,
    bond_name varchar(255) not null,
    bond_state boolean not null,
    crypto_pair varchar(255) not null,
    primary key (bonds_id)
) ENGINE=InnoDB;

create table indicator (
    indicators_id integer not null auto_increment,
    insert_timestamp datetime,
    insert_user varchar(255),
    modify_timestamp datetime,
    modify_user varchar(255),
    indicator_name varchar(255) not null,
    indicator_symbol varchar(255) not null,
    primary key (indicators_id)
) ENGINE=InnoDB;

create table indicator_bond_setting (
    indicator_bond_settings_id integer not null auto_increment,
    insert_timestamp datetime,
    insert_user varchar(255),
    modify_timestamp datetime,
    modify_user varchar(255),
    indicator_key varchar(255) not null,
    indicator_value varchar(255) not null,
    bonds_bonds_id integer,
    indicator_configuration_indicator_configurations_id integer,
    primary key (indicator_bond_settings_id)
) ENGINE=InnoDB;

create table indicator_configuration (
    indicator_configurations_id integer not null auto_increment,
    insert_timestamp datetime,
    insert_user varchar(255),
    modify_timestamp datetime,
    modify_user varchar(255),
    indicator_configuration_name binary(16) not null,
    indicators_indicators_id integer,
    primary key (indicator_configurations_id)
) ENGINE=InnoDB;

create table macd_data (
    time datetime not null,
    insert_timestamp datetime,
    insert_user varchar(255),
    modify_timestamp datetime,
    modify_user varchar(255),
    exchange_id integer not null,
    macd_signal float not null,
    fast_macd float not null,
    slow_macd float not null,
    bonds_bonds_id integer,
    primary key (time)
) ENGINE=InnoDB;

alter table indicator_bond_setting add constraint FKd33u58684smdmvnmwejt03ani foreign key (bonds_bonds_id) references bond (bonds_id);
alter table indicator_bond_setting add constraint FKnfavlct18ekgw52cqwar6kf31 foreign key (indicator_configuration_indicator_configurations_id) references indicator_configuration (indicator_configurations_id);
alter table indicator_configuration add constraint FKd8p7ebq4u3p7lhanayao1j8g2 foreign key (indicators_indicators_id) references indicator (indicators_id);
