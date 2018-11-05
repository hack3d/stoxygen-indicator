INSERT INTO `bond` (`bonds_id`, `bond_name`, `bond_isin`, `bond_state`, `insert_timestamp`, `insert_user`, `modify_timestamp`, `modify_user`, `crypto_pair`) VALUES
(1,'Bitcoin/Euro','XFC000000001',0,'2017-12-09 18:55:05','admin','2018-03-30 20:00:17','admin','btceur');

INSERT INTO `indicator` (`indicators_id`, `indicator_name`, `indicator_symbol`, `insert_timestamp`, `insert_user`, `modify_timestamp`, `modify_user`) VALUES
(1, 'MACD', 'macd', now(), 'admin', now(), 'admin');

/*
INSERT INTO `indicator_configuration` (`indicator_configurations_id`, `indicator_configuration_name`, `indicators_indicators_id`, `insert_timestamp`, `insert_user`, `modify_timestamp`, `modify_user`) VALUES
(1, '2D1EBC5B7D2741979CF0E84451C5BBB1', 1, now(), 'admin', now(), 'admin');

INSERT INTO `indicator_bond_setting` (`indicator_bond_settings_id`, `indicator_key`, `indicator_value`, `bonds_bonds_id`, `indicator_configuration_indicator_configurations_id`, `insert_timestamp`, `insert_user`, `modify_timestamp`, `modify_user`) VALUES
(1, 'signal', '9', 1, 1, now(), 'admin', now(), 'admin'),
(2, 'fast_macd', '12', 1, 1, now(), 'admin', now(), 'admin'),
(3, 'slow_macd', '26', 1, 1, now(), 'admin', now(), 'admin');
*/