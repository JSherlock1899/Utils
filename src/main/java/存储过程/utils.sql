/*
Navicat MySQL Data Transfer

Source Server         : 开发环境
Source Server Version : 80017
Source Host           : 39.107.90.231:3306
Source Database       : score_analysis

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2020-10-15 10:35:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Procedure structure for add_grade
-- ----------------------------
DROP PROCEDURE IF EXISTS `add_grade`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `add_grade`()
BEGIN

DECLARE id INT DEFAULT 17000700 ;
WHILE id < 17000800 DO
	SET id = id + 1;
	INSERT INTO 17_students_grades_20190728
VALUES
	(
		id,
		'1708',
		generateGrade (),
		generateGrade (),
		generateGrade (),
		generateGrade (),
		generateGrade (),
		generateGrade (),
		generateGrade (),
		generateGrade (),
		generateGrade (),
		generateGrade (),
		0
	);
UPDATE 17_students_grades_20190728 s SET total_point_grades = calculateTotalGrade(id) WHERE s.id = id;
END WHILE;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for add_stu
-- ----------------------------
DROP PROCEDURE IF EXISTS `add_stu`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `add_stu`()
BEGIN
	DECLARE i int default 17000700;
	DECLARE sex char(3);
    # 设置自动提交为false
    set autocommit = 0;  
    # 开启循环
		REPEAT
        set i = i+1;
				#随机性别
				SET sex=SUBSTRING('男女',floor(1+2*RAND()),1);
				INSERT INTO 17_students_basic_info (id,password,name,sex,nation,id_card,enter_time,telephone,native_place,school,classNumber) VALUES(i,'123',generateName(),sex,'汉',generateIDCard(),'2017',generatePhone(),generateNativePlace(),'光明中学','1708');
				COMMIT;
    UNTIL i = 17000800
    END REPEAT;  
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for add_teacher
-- ----------------------------
DROP PROCEDURE IF EXISTS `add_teacher`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `add_teacher`()
BEGIN
	DECLARE i int default 0;
	DECLARE sex char(3);
    # 设置自动提交为false
    set autocommit = 0;  
    # 开启循环
		REPEAT
        set i = i+1;
				#随机性别
				SET sex=SUBSTRING('男女',floor(1+2*RAND()),1);
				INSERT INTO teacher_basic_info (id,password,name,sex,nation,id_card,telephone,native_place,role) VALUES(i,'123',generateName(),sex,'汉',generateIDCard(),generatePhone(),generateNativePlace(),'2');
				COMMIT;
    UNTIL i = 100
    END REPEAT;  
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for calculateTotalGrade
-- ----------------------------
DROP PROCEDURE IF EXISTS `calculateTotalGrade`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `calculateTotalGrade`(IN id int,IN student_table VARCHAR(50), OUT grade INT)
BEGIN

DECLARE grade INT DEFAULT 0 ; 
SET @sql01=CONCAT("SELECT(chinese_grades + math_grades + english_grades + physics_grades + chemistry_grades + biology_grades + politics_grades + geography_grades + history_grades + technology_grades) AS total_grades INTO grade 
FROM ",student_table," s WHERE s.id = id ; ;");
PREPARE stmt FROM @sql01;
EXECUTE stmt;
	 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for class_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `class_ranking`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `class_ranking`(IN grade_table VARCHAR(50),IN ranking_table VARCHAR(50),IN student_grade VARCHAR(50),IN student_ranking VARCHAR(50),IN classNumber VARCHAR(10))
BEGIN
	#学生id
	DECLARE stu_id CHAR(20);
	#学生考试排名
	DECLARE stu_ranking INT;
	#越界标志
	DECLARE rs INT DEFAULT 1;
	#定义游标
	DECLARE getClassRanking CURSOR FOR SELECT id,ranking FROM ranking_class_view;
	#结果取到空时将越界标志设为0
	DECLARE CONTINUE HANDLER FOR NOT FOUND  SET rs := 0;
	#拼接SQL语句
	SET @sql01 = CONCAT("CREATE VIEW ranking_class_view AS select id,(select count(",student_grade,")+1 from ",grade_table," s where s.",student_grade,">t.",student_grade," AND classNumber = ",classNumber,") as ranking  from ",grade_table," t WHERE classNumber = " ,classNumber," order by t.",student_grade," desc;");	
	PREPARE stmt FROM @sql01;         -- 预处理动态sql语句
  EXECUTE stmt ;                        -- 执行sql语句
	#开启游标
	OPEN getClassRanking;
	#循环插入学生的成绩排名
	WHILE rs=1 DO
			FETCH getClassRanking INTO stu_id,stu_ranking;
			SET @update = CONCAT("UPDATE ",ranking_table," s SET ",student_ranking," = ",stu_ranking," WHERE s.id = ",stu_id,";");
			PREPARE stmt FROM @update;         -- 预处理动态sql语句
			EXECUTE stmt ;                        -- 执行sql语句
	END WHILE;
	#关闭游标
	CLOSE getClassRanking;
	DROP VIEW ranking_class_view;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for clearTable
-- ----------------------------
DROP PROCEDURE IF EXISTS `clearTable`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `clearTable`()
BEGIN
	DROP VIEW IF EXISTS ranking_view;
TRUNCATE TABLE 18_students_grades_20190928;
	TRUNCATE TABLE 18_students_ranking_20190928;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for initClassTable
-- ----------------------------
DROP PROCEDURE IF EXISTS `initClassTable`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `initClassTable`(IN class_table VARCHAR(50),IN startYear VARCHAR(50))
BEGIN
	DECLARE stu_id INT DEFAULT 0;
	DECLARE class_number VARCHAR(50) DEFAULT '';
	#越界标志
	DECLARE rs INT DEFAULT 1;
	#定义游标
	DECLARE getClass CURSOR FOR SELECT classNumber FROM ClassNumber_view;
	#结果取到空时将越界标志设为0
	DECLARE CONTINUE HANDLER FOR NOT FOUND  SET rs := 0;

	SET @sql01 = CONCAT("CREATE VIEW ClassNumber_view AS SELECT classNumber from class_basic_info WHERE start_year=",startYear,";");
	PREPARE stmt FROM @sql01;
	EXECUTE stmt;
	OPEN getClass;
	WHILE rs=1 DO
		FETCH getClass INTO class_number;
		SET @sql02 = CONCAT("INSERT INTO ",class_table ," (classNumber) VALUES(",class_number,");");
		PREPARE stmt FROM @sql02;
		EXECUTE stmt;
	END WHILE;
	DROP VIEW ClassNumber_view;
	CLOSE getClass;
	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for initRankingTable
-- ----------------------------
DROP PROCEDURE IF EXISTS `initRankingTable`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `initRankingTable`(IN student_table VARCHAR(50),IN ranking_table VARCHAR(50))
BEGIN
	DECLARE stu_id INT DEFAULT 0;
	DECLARE class_number VARCHAR(50) DEFAULT '';
	#越界标志
	DECLARE rs INT DEFAULT 1;
	#定义游标
	DECLARE getIdAndClass CURSOR FOR SELECT id,classNumber FROM idAndClassNumber_view;
	#结果取到空时将越界标志设为0
	DECLARE CONTINUE HANDLER FOR NOT FOUND  SET rs := 0;

	SET @sql01 = CONCAT("CREATE VIEW idAndClassNumber_view AS SELECT id,classNumber from ",student_table,";");
	#SELECT @sql01;
	PREPARE stmt FROM @sql01;
	EXECUTE stmt;
	OPEN getIdAndClass;
	WHILE rs=1 DO
		FETCH getIdAndClass INTO stu_id,class_number;
		SET @sql02 = CONCAT("INSERT INTO ",ranking_table," (id,classNumber) VALUES(",stu_id,",",class_number,");");
		#SELECT @sql02;
		PREPARE stmt FROM @sql02;
		EXECUTE stmt;
	END WHILE;
	DROP VIEW idAndClassNumber_view;
	CLOSE getIdAndClass;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for insertClassGrade
-- ----------------------------
DROP PROCEDURE IF EXISTS `insertClassGrade`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `insertClassGrade`(IN subjects VARCHAR(50),IN avgSubject VARCHAR(50),IN exam_table VARCHAR(50),IN class_grade_table VARCHAR(50),IN start_year VARCHAR(10))
BEGIN
	#平均成绩
	DECLARE averageGrade FLOAT DEFAULT 0;
	#班级
	DECLARE class_id VARCHAR(10) DEFAULT '';
	#越界标志
	DECLARE rs INT DEFAULT 1;
	#定义游标
	DECLARE getClass CURSOR FOR select classNumber from class_view; 
	#结果取到空时将越界标志设为0
	DECLARE CONTINUE HANDLER FOR NOT FOUND  SET rs := 0;
	SET @sql01 = CONCAT("CREATE VIEW class_view AS SELECT classNumber FROM class_basic_info WHERE start_year = ",start_year);
	#预处理动态sql
	PREPARE stmt from @sql01;
	EXECUTE stmt;
	#开启游标
	OPEN getClass;
	WHILE rs=1 DO
		FETCH getClass INTO class_id;
		#计算平均成绩
		SET @select = CONCAT("select AVG(",subjects,") INTO @avg from class_basic_info c left join ",exam_table," s on c
	.classNumber = s.classNumber where s.classNumber = ",class_id,";");
		PREPARE stmt FROM @select;
		EXECUTE stmt;
		SET averageGrade = @avg;
		SET @update = CONCAT("UPDATE ",class_grade_table," c SET ",avgSubject," = ",averageGrade," WHERE c.classNumber = ",class_id,";");
				PREPARE stmt FROM @update;         -- 预处理动态sql语句
				EXECUTE stmt ; 
	END WHILE;
	DROP VIEW class_view;
	CLOSE getClass;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for school_ranking
-- ----------------------------
DROP PROCEDURE IF EXISTS `school_ranking`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `school_ranking`(IN grade_table VARCHAR(50),IN ranking_table VARCHAR(50),IN student_grade VARCHAR(50),IN student_ranking VARCHAR(50),IN classNumber VARCHAR(10))
BEGIN
	#学生id
	DECLARE stu_id CHAR(20);
	#学生考试排名
	DECLARE stu_ranking INT;
	#越界标志
	DECLARE rs INT DEFAULT 1;
	#定义游标
	DECLARE getRanking CURSOR FOR SELECT id,ranking FROM ranking_view;
	#结果取到空时将越界标志设为0
	DECLARE CONTINUE HANDLER FOR NOT FOUND  SET rs := 0;
	#拼接SQL语句
	SET @select = CONCAT("CREATE VIEW ranking_view AS select id,(select count(",student_grade,")+1 from ",grade_table," s where s.",student_grade,">t.",student_grade,") as ranking  from ",grade_table," t WHERE classNumber = " ,classNumber," order by t.",student_grade," desc;");
	PREPARE stmt FROM @select;         -- 预处理动态sql语句
  EXECUTE stmt ;                        -- 执行sql语句
	#开启游标
	OPEN getRanking;
	#循环插入学生的成绩排名
	WHILE rs=1 DO
			FETCH getRanking INTO stu_id,stu_ranking;
			SET @update = CONCAT("UPDATE ",ranking_table," s SET ",student_ranking," = ",stu_ranking," WHERE s.id = ",stu_id,";");
			PREPARE stmt FROM @update;         -- 预处理动态sql语句
			EXECUTE stmt ;                        -- 执行sql语句
	END WHILE;
	DROP VIEW ranking_view;
	#关闭游标
	CLOSE getRanking;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for calculateTotalGrade
-- ----------------------------
DROP FUNCTION IF EXISTS `calculateTotalGrade`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `calculateTotalGrade`(id int) RETURNS int(11)
BEGIN

DECLARE grade INT DEFAULT 0 ; 
SELECT
	(
		chinese_grades + math_grades + english_grades + physics_grades + chemistry_grades + biology_grades + politics_grades + geography_grades + history_grades + technology_grades
	) AS total_grade INTO grade
FROM
	17_students_grades_20190728 s
WHERE
	s.id = id ; 
	RETURN grade ; 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for generateGrade
-- ----------------------------
DROP FUNCTION IF EXISTS `generateGrade`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `generateGrade`() RETURNS int(11)
BEGIN 
	DECLARE grade INT DEFAULT 0;
	SET grade = FLOOR(RAND() * 101);
	RETURN grade;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for generateIDCard
-- ----------------------------
DROP FUNCTION IF EXISTS `generateIDCard`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `generateIDCard`() RETURNS char(18) CHARSET utf8
BEGIN
	#Routine body goes here...
	#声明身份证字符串
	DECLARE id_card CHAR(18) DEFAULT '';
	DECLARE content CHAR(10) DEFAULT '0123456789';
	DECLARE xcontent CHAR(11) DEFAULT '0123456789X';
	DECLARE i int DEFAULT 1;
  DECLARE len int DEFAULT LENGTH(content);
	WHILE i<18 DO
	SET i = i + 1;
	SET id_card = CONCAT(id_card,SUBSTRING(content , FLOOR(RAND()*10 + 1) , 1));
	END WHILE;
	SET id_card = CONCAT(id_card,SUBSTRING(xcontent,FLOOR(RAND() * 11 + 1), 1));
	RETURN id_card;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for generateName
-- ----------------------------
DROP FUNCTION IF EXISTS `generateName`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `generateName`() RETURNS varchar(255) CHARSET utf8
    DETERMINISTIC
BEGIN
DECLARE xing varchar(2056) DEFAULT '赵钱孙李周郑王冯陈楮卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闽席季麻强贾路娄危江童颜郭梅盛林刁锺徐丘骆高夏蔡田樊胡凌霍虞万支柯昝管卢莫经裘缪干解应宗丁宣贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁';

DECLARE ming varchar(2056) DEFAULT '嘉懿煜城懿轩烨伟苑博伟泽熠彤鸿煊博涛烨霖烨华煜祺智宸正豪昊然明杰诚立轩立辉峻熙弘文熠彤鸿煊烨霖哲瀚鑫鹏致远俊驰雨泽烨磊晟睿天佑文昊修洁黎昕远航旭尧鸿涛伟祺轩越泽浩宇瑾瑜皓轩擎苍擎宇志泽睿渊楷瑞轩弘文哲瀚雨泽鑫磊梦琪忆之桃慕青问兰尔岚元香初夏沛菡傲珊曼文乐菱痴珊恨玉惜文香寒新柔语蓉海安夜蓉涵柏水桃醉蓝春儿语琴从彤傲晴语兰又菱碧彤元霜怜梦紫寒妙彤曼易南莲紫翠雨寒易烟如萱若南寻真晓亦向珊慕灵以蕊寻雁映易雪柳孤岚笑霜海云凝天沛珊寒云冰旋宛儿绿真盼儿晓霜碧凡夏菡曼香若烟半梦雅绿冰蓝灵槐平安书翠翠风香巧代云梦曼幼翠友巧听寒梦柏醉易访旋亦玉凌萱访卉怀亦笑蓝春翠靖柏夜蕾冰夏梦松书雪乐枫念薇靖雁寻春恨山从寒忆香觅波静曼凡旋以亦念露芷蕾千兰新波代真新蕾雁玉冷卉紫山千琴恨天傲芙盼山怀蝶冰兰山柏翠萱乐丹翠柔谷山之瑶冰露尔珍谷雪乐萱涵菡海莲傲蕾青槐冬儿易梦惜雪宛海之柔夏青亦瑶妙菡春竹修杰伟诚建辉晋鹏天磊绍辉泽洋明轩健柏煊昊强伟宸博超君浩子骞明辉鹏涛炎彬鹤轩越彬风华靖琪明诚高格光华国源宇晗昱涵润翰飞翰海昊乾浩博和安弘博鸿朗华奥华灿嘉慕坚秉建明金鑫锦程瑾瑜鹏经赋景同靖琪君昊俊明季同开济凯安康成乐语力勤良哲理群茂彦敏博明达朋义彭泽鹏举濮存溥心璞瑜浦泽奇邃祥荣轩';

DECLARE l_xing int DEFAULT LENGTH(xing) / 3; # 这里的长度不是字符串的字数,而是此字符串的占的容量大小,一个汉字占3个字节
DECLARE l_ming int DEFAULT LENGTH(ming) / 3;

DECLARE return_str varchar(255) DEFAULT '';



# 先选出姓
SET return_str = CONCAT(return_str, SUBSTRING(xing, FLOOR(1 + RAND() * l_xing), 1));


#再选出名
SET return_str = CONCAT(return_str, SUBSTRING(ming, FLOOR(1 + RAND() * l_ming), 1));


IF RAND()>0.400 THEN
#再选出名
SET return_str = CONCAT(return_str, SUBSTRING(ming, FLOOR(1 + RAND() * l_ming), 1));
END IF ;
RETURN return_str;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for generateNativePlace
-- ----------------------------
DROP FUNCTION IF EXISTS `generateNativePlace`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `generateNativePlace`() RETURNS varchar(10) CHARSET utf8
BEGIN
	DECLARE content VARCHAR(200) DEFAULT '北京,天津,上海,重庆,河北,山西,辽宁,吉林,江苏,浙江,安徽,内蒙,福建,江西,山东,河南,湖北,湖南,广东,海南,四川,贵州,云南,陕西,甘肃,青海,台湾,广西,西藏,宁夏,新疆,香港,澳门,黑龙江';
	DECLARE province VARCHAR(10) DEFAULT SUBSTRING(content, 1+(FLOOR(1 + (RAND() * 30))*3), 2);
	RETURN province;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for generatePhone
-- ----------------------------
DROP FUNCTION IF EXISTS `generatePhone`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `generatePhone`() RETURNS char(11) CHARSET utf8
    DETERMINISTIC
BEGIN
    DECLARE head VARCHAR(100) DEFAULT '000,156,136,176,183';
    
    DECLARE content CHAR(10) DEFAULT '0123456789';
    
    DECLARE phone CHAR(11) DEFAULT substring(head, 1+(FLOOR(1 + (RAND() * 3))*4), 3);
    
    DECLARE i int DEFAULT 1;
    
    DECLARE len int DEFAULT LENGTH(content);
    WHILE i<9 DO
        SET i=i+1;
        SET phone = CONCAT(phone, substring(content, floor(1 + RAND() * len), 1));
    END WHILE;
    
    RETURN phone;
END
;;
DELIMITER ;
