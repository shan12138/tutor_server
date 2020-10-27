insert into resource(id, path, hierarchyLevel, name, description) values(1, 'menu', 1, 'menu', '访问菜单管理');
insert into resource(id, path, hierarchyLevel, name, description) values(2, 'teacher', 1, 'teacher', '教师');
insert into resource(id, path, hierarchyLevel, name, description) values(3, 'parent', 1, 'parent', '家长');
insert into resource(id, path, hierarchyLevel, name, description) values(4, 'course', 1, 'course', '课程');
insert into resource(id, path, hierarchyLevel, name, description) values(5, 'question', 1, 'question', '题目');
insert into resource(id, path, hierarchyLevel, name, description) values(6, 'courseHourRecord', 1, 'courseHourRecord', '课时');
insert into resource(id, path, hierarchyLevel, name, description) values(7, 'educationResource', 1, 'educationResource', '教学资源');
insert into resource(id, path, hierarchyLevel, name, description) values(8, 'lesson', 1, 'lesson', '一堂课');
insert into resource(id, path, hierarchyLevel, name, description) values(9, 'addressChange', 1, 'addressChange', '地址变更');
insert into resource(id, path, hierarchyLevel, name, description) values(10, 'schedule', 1, 'schedule', '课表');
insert into resource(id, path, hierarchyLevel, name, description) values(11, 'wrongBook', 1, 'wrongBook', '错题本');
insert into resource(id, path, hierarchyLevel, name, description) values(12, 'role', 1, 'role', '角色');
insert into resource(id, path, hierarchyLevel, name, description) values(13, 'staff', 1, 'staff', '员工');
insert into resource(id, path, hierarchyLevel, name, description) values(14, 'subject', 1, 'subject', '科目');
insert into resource(id, path, hierarchyLevel, name, description) values(15, 'grade', 1, 'grade', '年级');
insert into resource(id, path, hierarchyLevel, name, description) values(16, 'product', 1, 'product', '产品');
insert into resource(id, path, hierarchyLevel, name, description) values(17, 'partner', 1, 'partner', '合伙人');
insert into resource(id, path, hierarchyLevel, name, description) values(18, 'consultant', 1, 'consultant', '咨询师');
insert into resource(id, path, hierarchyLevel, name, description) values(19, 'withdraw', 1, 'withdraw', '提现记录');
insert into resource(id, path, hierarchyLevel, name, description) values(20, 'invitationGift', 1, 'invitationGift', '邀请信息');
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(21, 'menu/teacherManage', 2, 'teacherManage', '教师管理', 1);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(22, 'menu/parentManage', 2, 'parentManage', '家长管理', 1);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(23, 'menu/courseManage', 2, 'courseManage', '课程管理', 1);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(24, 'menu/systemManage', 2, 'systemManage', '系统管理', 1);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(25, 'menu/businessManage', 2, 'businessManage', '商务管理', 1);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(26, 'menu/financialManage', 2, 'financialManage', '财务管理', 1);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(27, 'teacher/course', 2, 'course', '课程', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(28, 'teacher/invitation', 2, 'invitation', '邀请信息', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(29, 'teacher/level', 2, 'level', '等级', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(30, 'teacher/commission', 2, 'commission', '佣金', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(31, 'teacher/contract', 2, 'contract', '合同', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(32, 'teacher/score', 2, 'score', '测试成绩', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(33, 'teacher/platformIntroduction', 2, 'platformIntroduction', '平台介绍', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(34, 'teacher/platformInfo', 2, 'platformInfo', '平台信息', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(35, 'teacher/label', 2, 'label', '标签', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(36, 'teacher/student', 2, 'student', '学生', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(37, 'parent/course', 2, 'course', '课程', 3);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(38, 'parent/invitation', 2, 'invitation', '邀请信息', 3);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(39, 'parent/address', 2, 'address', '地址', 3);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(40, 'parent/level', 2, 'level', '等级', 3);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(41, 'parent/commission', 2, 'commission', '佣金', 3);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(42, 'parent/student', 2, 'student', '学生', 3);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(43, 'course/teacher', 2, 'teacher', '教师', 4);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(44, 'course/thisWeek', 2, 'thisWeek', '当周', 4);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(45, 'course/courseWare', 2, 'courseWare', '课件', 4);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(46, 'course/', 2, 'teacher', '教师', 4);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(47, 'course/checkRecord', 2, 'checkRecord', '签到记录', 4);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(48, 'course/parentEvaluation', 2, 'parentEvaluation', '家长评价', 4);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(49, 'course/teacherEvaluation', 2, 'teacherEvaluation', '教师评价', 4);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(50, 'role/permission', 2, 'permission', '权限', 12);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(51, 'staff/password', 2, 'password', '密码', 13);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(52, 'partner/parent', 2, 'parent', '家长', 17);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(53, 'partner/teacher', 2, 'teacher', '教师', 17);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(54, 'withdraw/online', 2, 'online', '线上', 19);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(55, 'withdraw/offline', 2, 'offline', '线下', 19);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(56, 'invitationGift/teacher', 2, 'teacher', '教师', 20);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(57, 'invitationGift/parent', 2, 'parent', '家长', 20);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(58, 'partner/parent/account', 3, 'account', '账号', 52);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(59, 'menu/teacherManage/formal', 3, 'formal', '正式教师', 21);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(60, 'menu/teacherManage/application', 3, 'application', '教师申请', 21);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(61, 'menu/teacherManage/test', 3, 'test', '教师测试', 21);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(62, 'menu/parentManage/allocation', 3, 'allocation', '资源分配', 22);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(63, 'menu/parentManage/formal', 3, 'formal', '正式家长', 22);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(64, 'menu/parentManage/application', 3, 'application', '家长申请', 22);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(65, 'menu/courseManage/addressChange', 3, 'addressChange', '地址变更', 23);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(66, 'menu/courseManage/course', 3, 'course', '课程管理', 23);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(67, 'menu/courseManage/thisWeekCourse', 3, 'thisWeekCourse', '当周课程', 23);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(68, 'menu/courseManage/educationResource', 3, 'educationResource', '教学资源', 23);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(69, 'menu/courseManage/wrongBook', 3, 'wrongBook', '错题本', 23);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(70, 'menu/systemManage/role', 3, 'role', '角色管理', 24);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(71, 'menu/systemManage/staff', 3, 'staff', '员工管理', 24);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(72, 'menu/systemManage/subject', 3, 'subject', '科目数据管理', 24);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(73, 'menu/systemManage/grade', 3, 'grade', '年级数据管理', 24);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(74, 'menu/systemManage/product', 3, 'product', '产品数据管理', 24);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(75, 'menu/systemManage/parentLevel', 3, 'parentLevel', '家长等级管理', 24);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(76, 'menu/systemManage/teacherLevel', 3, 'teacherLevel', '教师等级管理', 24);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(77, 'menu/businessManage/partner', 3, 'partner', '合伙人', 25);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(78, 'menu/businessManage/parent', 3, 'parent', '受邀家长', 25);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(79, 'menu/businessManage/teacher', 3, 'teacher', '受邀教师', 25);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(80, 'menu/financialManage/withdraw', 3, 'withdraw', '提现', 26);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(81, 'menu/financialManage/teacherGift', 3, 'teacherGift', '邀请家长赠礼', 26);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(82, 'menu/financialManage/parentGift', 3, 'parentGift', '邀请教师赠礼', 26);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(83, 'product/attribute', 2, 'attribute', '产品属性', 16);
insert into resource(id, path, hierarchyLevel, name, description)
values(84, 'department', 1, 'department', '部门');
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(85, 'menu/systemManage/department', 3, 'department', '组织架构管理', 24);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(86, 'teacher/formal', 2, 'formal', '正式教师', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(87, 'teacher/application', 2, 'application', '教师申请', 2);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(88, 'parent/formal', 2, 'formal', '正式家长', 3);
insert into resource(id, path, hierarchyLevel, name, description, parentId)
values(89, 'parent/application', 2, 'application', '家长申请', 3);