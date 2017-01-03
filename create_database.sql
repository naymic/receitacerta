-- phpMyAdmin SQL Dump
-- version 4.6.4deb1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 17, 2016 at 03:18 AM
-- Server version: 5.6.30-1
-- PHP Version: 7.0.10-1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `receita_certa`
--

-- --------------------------------------------------------

--
-- Table structure for table `agrega`
--

DROP TABLE IF EXISTS `agrega`;
CREATE TABLE `agrega` (
  `id` int(11) NOT NULL,
  `receitas_id` int(11) NOT NULL,
  `receitas_id1` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `avalia`
--

DROP TABLE IF EXISTS `avalia`;
CREATE TABLE `avalia` (
  `id` int(11) NOT NULL,
  `receitas_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `valor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `estoque`
--

DROP TABLE IF EXISTS `estoque`;
CREATE TABLE `estoque` (
  `id` int(11) NOT NULL,
  `ingredientes_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `data_insercao` date NOT NULL,
  `data_retirada` date DEFAULT NULL,
  `validade` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ingredientes`
--

DROP TABLE IF EXISTS `ingredientes`;
CREATE TABLE `ingredientes` (
  `id` int(11) NOT NULL,
  `nome` varchar(60) CHARACTER SET latin1 NOT NULL,
  `calorias` double NOT NULL,
  `ingredientes_unidades_id` int(11) NOT NULL,
  `ingredientes_tipo_id` int(11) NOT NULL,
  `ingrediente_armazenamentos_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Stand-in structure for view `ingredientes_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `ingredientes_view`;
CREATE TABLE `ingredientes_view` (
`id` int(11)
,`nome` varchar(60)
,`calorias` double
,`ingredientes_unidades_id` varchar(45)
,`ingredientes_tipo_id` varchar(45)
,`ingrediente_armazenamentos_id` varchar(60)
);

-- --------------------------------------------------------

--
-- Table structure for table `ingrediente_armazenamentos`
--

DROP TABLE IF EXISTS `ingrediente_armazenamentos`;
CREATE TABLE `ingrediente_armazenamentos` (
  `id` int(11) NOT NULL,
  `nome_armazenamento` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ingrediente_tipo`
--

DROP TABLE IF EXISTS `ingrediente_tipo`;
CREATE TABLE `ingrediente_tipo` (
  `id` int(11) NOT NULL,
  `nome_tipo` varchar(45) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `ingrediente_unidades`
--

DROP TABLE IF EXISTS `ingrediente_unidades`;
CREATE TABLE `ingrediente_unidades` (
  `id` int(11) NOT NULL,
  `nome_unidade` varchar(45) NOT NULL,
  `descricao` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pertence`
--

DROP TABLE IF EXISTS `pertence`;
CREATE TABLE `pertence` (
  `id` int(11) NOT NULL,
  `ingredientes_id` int(11) NOT NULL,
  `receitas_id` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `planejamentos`
--

DROP TABLE IF EXISTS `planejamentos`;
CREATE TABLE `planejamentos` (
  `receitas_id` int(11) NOT NULL,
  `plano_receitas_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `planos_receitas`
--

DROP TABLE IF EXISTS `planos_receitas`;
CREATE TABLE `planos_receitas` (
  `id` int(11) NOT NULL,
  `data_criacao` time NOT NULL,
  `usuario_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `receitas`
--

DROP TABLE IF EXISTS `receitas`;
CREATE TABLE `receitas` (
  `id` int(11) NOT NULL,
  `nome` varchar(75) NOT NULL,
  `tempo_preparo` int(11) NOT NULL,
  `rendimento` varchar(45) DEFAULT NULL,
  `receita_dificuldades_id` int(11) DEFAULT NULL,
  `receita_tipos_id` int(11) NOT NULL,
  `receita_rendimento_tipos_id` int(11) NOT NULL,
  `receita_rendimento_tipos_valor` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `caminho_foto` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='R';

-- --------------------------------------------------------

--
-- Table structure for table `receita_dificuldades`
--

DROP TABLE IF EXISTS `receita_dificuldades`;
CREATE TABLE `receita_dificuldades` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='FUTURO';

-- --------------------------------------------------------

--
-- Table structure for table `receita_passos`
--

DROP TABLE IF EXISTS `receita_passos`;
CREATE TABLE `receita_passos` (
  `id` int(11) NOT NULL,
  `receitas_id` int(11) NOT NULL,
  `nr_passo` int(11) NOT NULL,
  `texto` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `receita_rendimento_tipos`
--

DROP TABLE IF EXISTS `receita_rendimento_tipos`;
CREATE TABLE `receita_rendimento_tipos` (
  `id` int(11) NOT NULL,
  `tipo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `receita_tipos`
--

DROP TABLE IF EXISTS `receita_tipos`;
CREATE TABLE `receita_tipos` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `email` varchar(45) NOT NULL,
  `senha` varchar(70) NOT NULL,
  `celular` varchar(45) DEFAULT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `sobrenome` varchar(45) DEFAULT NULL,
  `caminho_foto` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Stand-in structure for view `view_receitas`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `view_receitas`;
CREATE TABLE `view_receitas` (
`id` int(11)
,`nome` varchar(75)
,`tempo_preparo` int(11)
,`rendimento` varchar(45)
,`receita_tipos_id` varchar(45)
,`receita_rendimento_tipos_id` varchar(45)
,`receita_rendimento_tipos_valor` int(11)
,`usuario_id` int(11)
,`caminho_foto` varchar(100)
,`total_cal` double
);

-- --------------------------------------------------------

--
-- Structure for view `ingredientes_view`
--
DROP TABLE IF EXISTS `ingredientes_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`receita_certa`@`localhost` SQL SECURITY DEFINER VIEW `ingredientes_view`  AS  select `i`.`id` AS `id`,`i`.`nome` AS `nome`,`i`.`calorias` AS `calorias`,`iu`.`nome_unidade` AS `ingredientes_unidades_id`,`it`.`nome_tipo` AS `ingredientes_tipo_id`,`ia`.`nome_armazenamento` AS `ingrediente_armazenamentos_id` from (((`ingredientes` `i` join `ingrediente_unidades` `iu` on((`i`.`ingredientes_unidades_id` = `iu`.`id`))) join `ingrediente_tipo` `it` on((`i`.`ingredientes_tipo_id` = `it`.`id`))) join `ingrediente_armazenamentos` `ia` on((`i`.`ingrediente_armazenamentos_id` = `ia`.`id`))) where 1 group by `i`.`id` ;

-- --------------------------------------------------------

--
-- Structure for view `view_receitas`
--
DROP TABLE IF EXISTS `view_receitas`;

CREATE ALGORITHM=UNDEFINED DEFINER=`receita_certa`@`localhost` SQL SECURITY DEFINER VIEW `view_receitas`  AS  select `r`.`id` AS `id`,`r`.`nome` AS `nome`,`r`.`tempo_preparo` AS `tempo_preparo`,`r`.`rendimento` AS `rendimento`,`rt`.`nome` AS `receita_tipos_id`,`rrt`.`tipo` AS `receita_rendimento_tipos_id`,`r`.`receita_rendimento_tipos_valor` AS `receita_rendimento_tipos_valor`,`r`.`usuario_id` AS `usuario_id`,`r`.`caminho_foto` AS `caminho_foto`,(sum((`i`.`calorias` * `p`.`quantidade`)) / `r`.`receita_rendimento_tipos_valor`) AS `total_cal` from ((((`receitas` `r` join `receita_rendimento_tipos` `rrt` on((`r`.`receita_rendimento_tipos_id` = `rrt`.`id`))) join `receita_tipos` `rt` on((`r`.`receita_tipos_id` = `rt`.`id`))) left join `pertence` `p` on((`p`.`receitas_id` = `r`.`id`))) left join `ingredientes` `i` on((`p`.`ingredientes_id` = `i`.`id`))) group by `r`.`id` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `agrega`
--
ALTER TABLE `agrega`
  ADD PRIMARY KEY (`receitas_id`,`receitas_id1`),
  ADD KEY `fk_receitas_has_receitas_receitas2_idx` (`receitas_id1`),
  ADD KEY `fk_receitas_has_receitas_receitas1_idx` (`receitas_id`);

--
-- Indexes for table `avalia`
--
ALTER TABLE `avalia`
  ADD PRIMARY KEY (`receitas_id`,`usuario_id`),
  ADD KEY `fk_receitas_has_usuario_usuario1_idx` (`usuario_id`),
  ADD KEY `fk_receitas_has_usuario_receitas1_idx` (`receitas_id`);

--
-- Indexes for table `estoque`
--
ALTER TABLE `estoque`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ingredientes_has_usuario_usuario1_idx` (`usuario_id`),
  ADD KEY `fk_ingredientes_has_usuario_ingredientes1_idx` (`ingredientes_id`);

--
-- Indexes for table `ingredientes`
--
ALTER TABLE `ingredientes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ingredientes_ingredientes_unidades1_idx` (`ingredientes_unidades_id`),
  ADD KEY `fk_ingredientes_ingredientes_tipo1_idx` (`ingredientes_tipo_id`),
  ADD KEY `fk_ingredientes_ingrediente_armazenamentos1_idx` (`ingrediente_armazenamentos_id`);
ALTER TABLE `ingredientes` ADD FULLTEXT KEY `nome` (`nome`);

--
-- Indexes for table `ingrediente_armazenamentos`
--
ALTER TABLE `ingrediente_armazenamentos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ingrediente_tipo`
--
ALTER TABLE `ingrediente_tipo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ingrediente_unidades`
--
ALTER TABLE `ingrediente_unidades`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pertence`
--
ALTER TABLE `pertence`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ingredientes_has_receitas_receitas1_idx` (`receitas_id`),
  ADD KEY `fk_ingredientes_has_receitas_ingredientes1_idx` (`ingredientes_id`);

--
-- Indexes for table `planejamentos`
--
ALTER TABLE `planejamentos`
  ADD PRIMARY KEY (`receitas_id`,`plano_receitas_id`),
  ADD KEY `fk_receitas_has_plano_receitas_plano_receitas1_idx` (`plano_receitas_id`),
  ADD KEY `fk_receitas_has_plano_receitas_receitas1_idx` (`receitas_id`);

--
-- Indexes for table `planos_receitas`
--
ALTER TABLE `planos_receitas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_plano_receitas_usuario1_idx` (`usuario_id`);

--
-- Indexes for table `receitas`
--
ALTER TABLE `receitas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_receitas_receita_dificuldades_idx` (`receita_dificuldades_id`),
  ADD KEY `fk_receitas_receita_tipos1_idx` (`receita_tipos_id`),
  ADD KEY `fk_receitas_receita_rendimento_tipos1_idx` (`receita_rendimento_tipos_id`),
  ADD KEY `fk_receitas_usuario1_idx` (`usuario_id`);
ALTER TABLE `receitas` ADD FULLTEXT KEY `nome_index` (`nome`);

--
-- Indexes for table `receita_dificuldades`
--
ALTER TABLE `receita_dificuldades`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `receita_passos`
--
ALTER TABLE `receita_passos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_receita_passos_receitas1_idx` (`receitas_id`);

--
-- Indexes for table `receita_rendimento_tipos`
--
ALTER TABLE `receita_rendimento_tipos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `receita_tipos`
--
ALTER TABLE `receita_tipos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `agrega`
--
ALTER TABLE `agrega`
  ADD CONSTRAINT `fk_receitas_has_receitas_receitas1` FOREIGN KEY (`receitas_id`) REFERENCES `receitas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_receitas_has_receitas_receitas2` FOREIGN KEY (`receitas_id1`) REFERENCES `receitas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `avalia`
--
ALTER TABLE `avalia`
  ADD CONSTRAINT `fk_receitas_has_usuario_receitas1` FOREIGN KEY (`receitas_id`) REFERENCES `receitas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_receitas_has_usuario_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `estoque`
--
ALTER TABLE `estoque`
  ADD CONSTRAINT `fk_ingredientes_has_usuario_ingredientes1` FOREIGN KEY (`ingredientes_id`) REFERENCES `ingredientes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ingredientes_has_usuario_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `ingredientes`
--
ALTER TABLE `ingredientes`
  ADD CONSTRAINT `fk_ingredientes_ingrediente_armazenamentos1` FOREIGN KEY (`ingrediente_armazenamentos_id`) REFERENCES `ingrediente_armazenamentos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ingredientes_ingredientes_tipo1` FOREIGN KEY (`ingredientes_tipo_id`) REFERENCES `ingrediente_tipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ingredientes_ingredientes_unidades1` FOREIGN KEY (`ingredientes_unidades_id`) REFERENCES `ingrediente_unidades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `pertence`
--
ALTER TABLE `pertence`
  ADD CONSTRAINT `fk_ingredientes_has_receitas_ingredientes1` FOREIGN KEY (`ingredientes_id`) REFERENCES `ingredientes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ingredientes_has_receitas_receitas1` FOREIGN KEY (`receitas_id`) REFERENCES `receitas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `planejamentos`
--
ALTER TABLE `planejamentos`
  ADD CONSTRAINT `fk_receitas_has_plano_receitas_plano_receitas1` FOREIGN KEY (`plano_receitas_id`) REFERENCES `planos_receitas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_receitas_has_plano_receitas_receitas1` FOREIGN KEY (`receitas_id`) REFERENCES `receitas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `planos_receitas`
--
ALTER TABLE `planos_receitas`
  ADD CONSTRAINT `fk_plano_receitas_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `receitas`
--
ALTER TABLE `receitas`
  ADD CONSTRAINT `fk_receitas_receita_dificuldades` FOREIGN KEY (`receita_dificuldades_id`) REFERENCES `receita_dificuldades` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_receitas_receita_rendimento_tipos1` FOREIGN KEY (`receita_rendimento_tipos_id`) REFERENCES `receita_rendimento_tipos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_receitas_receita_tipos1` FOREIGN KEY (`receita_tipos_id`) REFERENCES `receita_tipos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_receitas_usuario1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `receita_passos`
--
ALTER TABLE `receita_passos`
  ADD CONSTRAINT `fk_receita_passos_receitas1` FOREIGN KEY (`receitas_id`) REFERENCES `receitas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
