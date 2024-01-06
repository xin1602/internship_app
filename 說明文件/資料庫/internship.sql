-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1:3306
-- 產生時間： 2023-12-28 18:45:33
-- 伺服器版本： 8.0.31
-- PHP 版本： 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `internship`
--
use internship;

-- --------------------------------------------------------

--
-- 資料表結構 `info`
--

DROP TABLE IF EXISTS `info`;
CREATE TABLE IF NOT EXISTS `info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `使用者id` varchar(20) DEFAULT NULL,
  `姓名` varchar(50) DEFAULT NULL,
  `密碼` varchar(50) DEFAULT NULL,
  `電子郵件` varchar(50) DEFAULT NULL,
  `電話` varchar(15) DEFAULT NULL,
  `隸屬` varchar(50) DEFAULT NULL,
  `權限` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `info`
--

INSERT INTO `info` (`id`, `使用者id`, `姓名`, `密碼`, `電子郵件`, `電話`, `隸屬`, `權限`) VALUES
(1, '11044106', '李欣樺', '11044106', '11044106.com', '0900111789', '資管三甲', '學生'),
(2, '11044107', '胡文欣', '11044107', '11044107.com', '0900111111', '資管三甲', '學生'),
(3, '11044147', '林秀羽', '11044147', '11044147.com', '0900111333', '資管三甲', '學生'),
(4, '20231111', '陳依依', '20231111', '20231111.com', '0900799789', '資管系系辦', '職員');

-- --------------------------------------------------------

--
-- 資料表結構 `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE IF NOT EXISTS `job` (
  `id` int NOT NULL AUTO_INCREMENT,
  `公司` varchar(20) DEFAULT NULL,
  `地址` varchar(100) DEFAULT NULL,
  `電話` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `簡介` varchar(2000) DEFAULT NULL,
  `職位` varchar(200) DEFAULT NULL,
  `介紹` varchar(2000) DEFAULT NULL,
  `專業技能` varchar(2000) DEFAULT NULL,
  `薪資` varchar(50) DEFAULT NULL,
  `系別` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `job`
--

INSERT INTO `job` (`id`, `公司`, `地址`, `電話`, `email`, `簡介`, `職位`, `介紹`, `專業技能`, `薪資`, `系別`) VALUES
(1, '享食有限公司', '桃園市中壢區中北路33號', '0988333333', 'candy@gmail.com', '我是簡介我是簡介我是簡地景建築學系
商業設計學系
室內設計學系
建築學系介我是簡介我是簡介我是簡介', '網頁後端實習工程師', '我是介紹我是介紹我是介紹我是介紹', '我是專業技能', '時薪200元', '資訊管理學系'),
(2, '誠心有限公司', '桃園市中壢區中北路33號', '0988333333', 'candy@gmail.com', '我是簡介我是簡介我是簡介我是簡介我是簡介我是簡介', '網頁前端實習工程師', '我是介紹我是介紹我是介紹我是介紹', '我是專業技能', '時薪190元', '資訊工程學系'),
(3, '富貴有限公司', '彰化縣伸港鄉什股十街19號', '0978789789', 'a.com', '簡介\n1\n1\n1\n1\n1', '助理', '介紹\n1\n2\n3\n4\n5\n6\n78', '1.\n2.\n3.\n4.', '時薪190元', '財務金融學系'),
(4, '合氣有限公司', '彰化縣六龜區和平路19號', '0912345678', 'christinelee1114@gmail.com', '簡介\n1\n1\n1\n1\n1', '助理', '介紹\n1\n2\n3\n4\n5\n6\n78', '1.\n2.\n3.\n4.', '時薪190元', '會計學系'),
(5, '三寶有限公司', '台中市六龜區和平路19號', '0912345678', 'christinelee1114@gmail.com', '簡介\n1\n1\n1\n1\n1', '助理', '介紹\n1\n2\n3\n4\n5\n6\n78', '1.\n2.\n3.\n4.', '時薪190元', '電子工程學系'),
(6, '四海有限公司', '新北市六龜區和平路19號', '0912345678', 'christinelee1114@gmail.com', '簡介\n1\n1\n1\n1\n1', '助理', '介紹\n1\n2\n3\n4\n5\n6\n78', '1.\n2.\n3.\n4.', '時薪190元', '財經法律學系'),
(7, '五湖有限公司', '新北市六龜區和平路19號', '0912345678', 'christinelee1114@gmail.com', '簡介\n1\n1\n1\n1\n1', '助理', '介紹\n1\n2\n3\n4\n5\n6\n78', '1.\n2.\n3.\n4.', '時薪190元', '會計學系'),
(8, '六督有限公司', '台南市六龜區和平路19號', '0912345678', 'christinelee1114@gmail.com', '簡介\n1\n1\n1\n1\n1', '助理', '介紹\n1\n2\n3\n4\n5\n6\n78', '1.\n2.\n3.\n4.', '時薪190元', '電子工程學系'),
(9, '欺負有限公司', '台北市六龜區和平路19號', '0912345678', 'christinelee1114@gmail.com', '簡介\n1\n1\n1\n1\n1', '助理', '介紹\n1\n2\n3\n4\n5\n6\n78', '1.\n2.\n3.\n4.', '時薪190元', '企業管理學系'),
(10, '八寶有限公司', '高雄市六龜區和平路19號', '0912345678', 'christinelee1114@gmail.com', '簡介\n1\n1\n1\n1\n1', '助理', '介紹\n1\n2\n3\n4\n5\n6\n78', '1.\n2.\n3.\n4.', '時薪190元', '企業管理學系'),
(11, '久久有限公司', '高雄市六龜區和平路19號', '0912345678', 'christinelee1114@gmail.com', '簡介\n1\n1\n1\n1\n1', '助理', '介紹\n1\n2\n3\n4\n5\n6\n78', '1.\n2.\n3.\n4.', '時薪190元', '會計學系');






-- --------------------------------------------------------

--
-- 資料表結構 `resume`
--

DROP TABLE IF EXISTS `resume`;
CREATE TABLE IF NOT EXISTS `resume` (
  `id` int NOT NULL AUTO_INCREMENT,
  `使用者id` varchar(20) DEFAULT NULL,
  `簡歷` varchar(2000) DEFAULT NULL,
  `工作經驗` varchar(2000) DEFAULT NULL,
  `專業技能` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 傾印資料表的資料 `resume`
--

INSERT INTO `resume` (`id`, `使用者id`, `簡歷`, `工作經驗`, `專業技能`) VALUES
(1, '11044106', '我是使用者1的簡歷內容我是使用者1的簡歷內容我是使用者1的簡歷內容我是使用者1的簡歷內容我是使用者1的簡歷內容我是使用者1的簡歷內容178', '使用者1的工作經驗內容\n使用者1的工作經驗內容\n使用者1的工作經驗內容\n使用者1的工作經驗內容\n1\n4\n7\n7\n7', 'Java, Python, SQL'),
(2, '11044107', '我是使用者2的簡歷內容', '使用者2的工作經驗內容', 'C++, JavaScript, HTML'),
(4, '11044147', '我是中原大學資訊管理學系大三生。', '1.寵物店打工-小編\n2.五桐號打工', '1.行銷\n2.程式設計\n3.搖飲料\n4.JavaScript\n5.HTML');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

--
-- 資料表結構 `user_favorite`
--

DROP TABLE IF EXISTS `user_favorite`;
CREATE TABLE user_favorite (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(20),
    job_id INT,
    FOREIGN KEY (user_id) REFERENCES info(使用者id),
    FOREIGN KEY (job_id) REFERENCES job(id)
)ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;








