insert into permission(id, name, description, actionId, resourceId) values(1, 'menu/teacherManage:access', '教师管理', 1, 21);
insert into permission(id, name, description, actionId, resourceId, parentId) values(2, 'menu/teacherManage/formal:access', '正式教师', 1, 59, 1);
insert into permission(id, name, description, actionId, resourceId, parentId) values(3, 'menu/teacherManage/application:access', '教师申请', 1, 60, 1);
insert into permission(id, name, description, actionId, resourceId, parentId) values(4, 'menu/teacherManage/test:access', '教师测试', 1, 61, 1);
insert into permission(id, name, description, actionId, resourceId) values(5, 'menu/parentManage:access', '家长管理', 1, 22);
insert into permission(id, name, description, actionId, resourceId, parentId) values(6, 'menu/parentManage/allocation:access', '资源分配', 1, 62, 5);
insert into permission(id, name, description, actionId, resourceId, parentId) values(7, 'menu/parentManage/formal:access', '正式家长', 1, 63, 5);
insert into permission(id, name, description, actionId, resourceId, parentId) values(8, 'menu/parentManage/application:access', '家长申请', 1, 64, 5);
insert into permission(id, name, description, actionId, resourceId) values(9, 'menu/courseManage:access', '教学管理', 1, 23);
insert into permission(id, name, description, actionId, resourceId, parentId) values(10, 'menu/courseManage/addressChange:access', '地址变更', 1, 65, 9);
insert into permission(id, name, description, actionId, resourceId, parentId) values(11, 'menu/courseManage/course:access', '课程', 1, 66, 9);
insert into permission(id, name, description, actionId, resourceId, parentId) values(12, 'menu/courseManage/thisWeekCourse:access', '当周课程', 1, 67, 9);
insert into permission(id, name, description, actionId, resourceId, parentId) values(13, 'menu/courseManage/educationResource:access', '教学资源', 1, 68, 9);
insert into permission(id, name, description, actionId, resourceId, parentId) values(14, 'menu/courseManage/wrongBook:access', '错题本', 1, 69, 9);
insert into permission(id, name, description, actionId, resourceId) values(15, 'menu/systemManage:access', '系统管理', 1, 24);
insert into permission(id, name, description, actionId, resourceId, parentId) values(16, 'menu/systemManage/role:access', '角色', 1, 70, 15);
insert into permission(id, name, description, actionId, resourceId, parentId) values(17, 'menu/systemManage/staff:access', '员工', 1, 71, 15);
insert into permission(id, name, description, actionId, resourceId, parentId) values(18, 'menu/systemManage/subject:access', '科目', 1, 72, 15);
insert into permission(id, name, description, actionId, resourceId, parentId) values(19, 'menu/systemManage/grade:access', '年级', 1, 73, 15);
insert into permission(id, name, description, actionId, resourceId, parentId) values(20, 'menu/systemManage/product:access', '产品', 1, 74, 15);
insert into permission(id, name, description, actionId, resourceId, parentId) values(21, 'menu/systemManage/parentLevel:access', '家长等级', 1, 75, 15);
insert into permission(id, name, description, actionId, resourceId, parentId) values(22, 'menu/systemManage/teacherLevel:access', '教师等级', 1, 76, 15);
insert into permission(id, name, description, actionId, resourceId) values(23, 'menu/businessManage:access', '商务管理', 1, 25);
insert into permission(id, name, description, actionId, resourceId, parentId) values(24, 'menu/businessManage/partner:access', '合伙人', 1, 77, 23);
insert into permission(id, name, description, actionId, resourceId, parentId) values(25, 'menu/businessManage/parent:access', '受邀家长', 1, 78, 23);
insert into permission(id, name, description, actionId, resourceId, parentId) values(26, 'menu/businessManage/teacher:access', '受邀教师', 1, 79, 23);
insert into permission(id, name, description, actionId, resourceId) values(27, 'menu/financialManage:access', '财务管理', 1, 26);
insert into permission(id, name, description, actionId, resourceId, parentId) values(28, 'menu/financialManage/withdraw:access', '提现', 1, 80, 27);
insert into permission(id, name, description, actionId, resourceId, parentId) values(29, 'menu/financialManage/teacherGift:access', '邀请教师赠礼', 1, 81, 27);
insert into permission(id, name, description, actionId, resourceId, parentId) values(30, 'menu/financialManage/parentGift:access', '邀请家长赠礼', 1, 82, 27);
insert into permission(id, name, description, actionId, resourceId, parentId) values(31, 'teacher/formal:list', '查看教师列表', 2, 86, 2);
insert into permission(id, name, description, actionId, resourceId, parentId) values(32, 'teacher/formal:read', '查看教师详情', 3, 86, 2);
insert into permission(id, name, description, actionId, resourceId, parentId) values(33, 'teacher/platformIntroduction:update', '修改平台介绍', 5, 33, 2);
insert into permission(id, name, description, actionId, resourceId, parentId) values(34, 'teacher/platformInfo:update', '修改平台信息', 5, 34, 2);
insert into permission(id, name, description, actionId, resourceId, parentId) values(35, 'teacher/score:list', '查看教师测试历史成绩列表', 2, 32, 2);
insert into permission(id, name, description, actionId, resourceId, parentId) values(36, 'teacher/course:list', '查看教师所授课程列表', 2, 27, 2);
insert into permission(id, name, description, actionId, resourceId, parentId) values(37, 'teacher/contract:create', '上传教师合同', 4, 31, 2);
insert into permission(id, name, description, actionId, resourceId, parentId) values(38, 'teacher/contract:list', '获取教师合同列表', 2, 31, 2);
insert into permission(id, name, description, actionId, resourceId, parentId) values(39, 'teacher:update', '审核教师信息', 5, 2, 3);
insert into permission(id, name, description, actionId, resourceId, parentId) values(40, 'teacher:disableEnable', '禁用/启用教师', 7, 2, 2);
insert into permission(id, name, description, actionId, resourceId, parentId) values(41, 'question:create', '上传教师测试题目', 4, 5, 4);
insert into permission(id, name, description, actionId, resourceId, parentId) values(42, 'parent/invitation:list', '查看受邀家长列表', 2, 38, 6);
insert into permission(id, name, description, actionId, resourceId, parentId) values(43, 'parent/formal:list', '查看家长列表', 2, 88, 7);
insert into permission(id, name, description, actionId, resourceId, parentId) values(44, 'parent/formal:read', '查看家长详情', 3, 88, 7);
insert into permission(id, name, description, actionId, resourceId, parentId) values(45, 'parent/invitation:update', '修改家长邀请信息', 5, 38, 7);
insert into permission(id, name, description, actionId, resourceId, parentId) values(46, 'parent/course:list', '查看家长所购买课程列表', 2, 37, 7);
insert into permission(id, name, description, actionId, resourceId, parentId) values(47, 'parent/student:list', '查看家长子女列表', 2, 42, 7);
insert into permission(id, name, description, actionId, resourceId, parentId) values(48, 'parent:update', '审核家长信息', 5, 3, 8);
insert into permission(id, name, description, actionId, resourceId, parentId) values(49, 'parent:disableEnable', '禁用/启用家长', 7, 3, 7);
insert into permission(id, name, description, actionId, resourceId, parentId) values(50, 'course:create', '新增课程', 4, 4, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(51, 'course:read', '查看课程详情', 3, 4, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(52, 'course/teacher:update', '设置课程教师', 5, 43, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(53, 'course:delete', '删除课程', 6, 4, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(54, 'course/courseWare:create', '上传课程课件', 4, 45, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(55, 'course/checkRecord:update', '修改课程签到记录', 5, 47, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(56, 'course/parentEvaluation:update', '审核课程家长评价', 5, 48, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(57, 'course/teacherEvaluation:update', '审核课程教员评价', 5, 49, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(58, 'course/thisWeek:list', '查看当周课程', 2, 44, 12);
insert into permission(id, name, description, actionId, resourceId, parentId) values(59, 'wrongBook:list', '查看错题本记录', 2, 11, 14);
insert into permission(id, name, description, actionId, resourceId, parentId) values(60, 'wrongBook:read', '查看错题本详情', 3, 11, 14);
insert into permission(id, name, description, actionId, resourceId, parentId) values(61, 'educationResource:list', '查看教学资源列表', 2, 7, 13);
insert into permission(id, name, description, actionId, resourceId, parentId) values(62, 'educationResource:delete', '删除教学资源', 6, 7, 13);
insert into permission(id, name, description, actionId, resourceId, parentId) values(63, 'educationResource:create', '上传教学资源', 4, 7, 13);
insert into permission(id, name, description, actionId, resourceId, parentId) values(64, 'addressChange:list', '查看地址变更列表', 2, 9, 10);
insert into permission(id, name, description, actionId, resourceId, parentId) values(65, 'addressChange:update', '审核地址变更', 5, 9, 10);
insert into permission(id, name, description, actionId, resourceId, parentId) values(66, 'role:list', '查看角色列表', 2, 12, 16);
insert into permission(id, name, description, actionId, resourceId, parentId) values(67, 'role/permission:read', '查看角色具有权限', 3, 50, 16);
insert into permission(id, name, description, actionId, resourceId, parentId) values(68, 'role:update', '修改角色', 5, 12, 16);
insert into permission(id, name, description, actionId, resourceId, parentId) values(69, 'role:create', '新建角色', 4, 12, 16);
insert into permission(id, name, description, actionId, resourceId, parentId) values(70, 'role:delete', '删除角色', 6, 12, 16);
insert into permission(id, name, description, actionId, resourceId, parentId) values(71, 'staff:list', '查看员工列表', 2, 13, 17);
insert into permission(id, name, description, actionId, resourceId, parentId) values(72, 'staff:update', '修改员工', 5, 13, 17);
insert into permission(id, name, description, actionId, resourceId, parentId) values(73, 'staff:delete', '删除员工', 6, 13, 17);
insert into permission(id, name, description, actionId, resourceId, parentId) values(74, 'staff:create', '新建员工', 4, 13, 17);
insert into permission(id, name, description, actionId, resourceId, parentId) values(75, 'staff/password:update', '修改员工密码', 5, 51, 17);
insert into permission(id, name, description, actionId, resourceId, parentId) values(76, 'subject:list', '查看科目列表', 2, 14, 18);
insert into permission(id, name, description, actionId, resourceId, parentId) values(77, 'subject:update', '修改科目', 5, 14, 18);
insert into permission(id, name, description, actionId, resourceId, parentId) values(78, 'subject:create', '新增科目', 4, 14, 18);
insert into permission(id, name, description, actionId, resourceId, parentId) values(79, 'subject:delete', '删除科目', 6, 14, 18);
insert into permission(id, name, description, actionId, resourceId, parentId) values(80, 'subject:disableEnable', '禁用/启用科目', 7, 14, 18);
insert into permission(id, name, description, actionId, resourceId, parentId) values(81, 'grade:list', '查看年级列表', 2, 15, 19);
insert into permission(id, name, description, actionId, resourceId, parentId) values(82, 'grade:update', '修改年级', 5, 15, 19);
insert into permission(id, name, description, actionId, resourceId, parentId) values(83, 'grade:create', '新增年级', 4, 15, 19);
insert into permission(id, name, description, actionId, resourceId, parentId) values(84, 'grade:delete', '删除年级', 6, 15, 19);
insert into permission(id, name, description, actionId, resourceId, parentId) values(85, 'grade:disableEnable', '禁用/启用年级', 7, 15, 19);
insert into permission(id, name, description, actionId, resourceId, parentId) values(86, 'product:list', '查看产品列表', 2, 16, 20);
insert into permission(id, name, description, actionId, resourceId, parentId) values(87, 'product:update', '修改产品', 5, 16, 20);
insert into permission(id, name, description, actionId, resourceId, parentId) values(88, 'product:create', '新增产品', 4, 16, 20);
insert into permission(id, name, description, actionId, resourceId, parentId) values(89, 'product:delete', '删除产品', 6, 16, 20);
insert into permission(id, name, description, actionId, resourceId, parentId) values(90, 'product:disableEnable', '禁用/启用产品', 7, 16, 20);
insert into permission(id, name, description, actionId, resourceId, parentId) values(91, 'parent/level:list', '查看家长等级列表', 2, 40, 21);
insert into permission(id, name, description, actionId, resourceId, parentId) values(92, 'parent/level:create', '新增家长等级', 4, 40, 21);
insert into permission(id, name, description, actionId, resourceId, parentId) values(93, 'parent/level:update', '修改家长等级', 5, 40, 21);
insert into permission(id, name, description, actionId, resourceId, parentId) values(94, 'parent/level:delete', '删除家长等级', 6, 40, 21);
insert into permission(id, name, description, actionId, resourceId, parentId) values(95, 'teacher/level:list', '查看教师等级列表', 2, 29, 22);
insert into permission(id, name, description, actionId, resourceId, parentId) values(96, 'teacher/level:create', '新增教师等级', 4, 29, 22);
insert into permission(id, name, description, actionId, resourceId, parentId) values(97, 'teacher/level:update', '修改教师等级', 5, 29, 22);
insert into permission(id, name, description, actionId, resourceId, parentId) values(98, 'teacher/level:delete', '删除教师等级', 6, 29, 22);
insert into permission(id, name, description, actionId, resourceId, parentId) values(99, 'partner:list', '查看合伙人列表', 2, 17, 24);
insert into permission(id, name, description, actionId, resourceId, parentId) values(100, 'partner:create', '新增合伙人', 4, 17, 24);
insert into permission(id, name, description, actionId, resourceId, parentId) values(101, 'partner:disableEnable', '禁用/启用合伙人', 7, 17, 24);
insert into permission(id, name, description, actionId, resourceId, parentId) values(102, 'partner/parent:list', '查看受邀家长列表', 2, 52, 25);
insert into permission(id, name, description, actionId, resourceId, parentId) values(103, 'partner/parent:read', '查看受邀家长详情', 3, 52, 25);
insert into permission(id, name, description, actionId, resourceId, parentId) values(104, 'consultant:create', '分配咨询师', 4, 18, 25);
insert into permission(id, name, description, actionId, resourceId, parentId) values(105, 'partner/parent/account:create', '匹配家长账号', 4, 58, 25);
insert into permission(id, name, description, actionId, resourceId, parentId) values(106, 'partner/teacher:list', '查看受邀教师列表', 2, 53, 26);
insert into permission(id, name, description, actionId, resourceId, parentId) values(107, 'withdraw:list', '查看提现记录列表', 2, 19, 28);
insert into permission(id, name, description, actionId, resourceId, parentId) values(108, 'withdraw/online:create', '线上发放', 4, 54, 28);
insert into permission(id, name, description, actionId, resourceId, parentId) values(109, 'withdraw/offline:create', '线下发放', 4, 55, 28);
insert into permission(id, name, description, actionId, resourceId, parentId) values(110, 'invitationGift/teacher:list', '查看邀请教师赠礼列表', 2, 56, 29);
insert into permission(id, name, description, actionId, resourceId, parentId) values(111, 'invitationGift/teacher:update', '发放邀请教师赠礼', 5, 56, 29);
insert into permission(id, name, description, actionId, resourceId, parentId) values(112, 'invitationGift/parent:list', '查看邀请家长赠礼列表', 2, 57, 30);
insert into permission(id, name, description, actionId, resourceId, parentId) values(113, 'invitationGift/parent:update', '发放邀请家长赠礼', 5, 57, 30);
insert into permission(id, name, description, actionId, resourceId, parentId) values(114, 'product/attribute:list', '查看产品字段列表', 2, 83, 20);
insert into permission(id, name, description, actionId, resourceId, parentId) values(115, 'product/attribute:update', '修改产品字段', 5, 83, 20);
insert into permission(id, name, description, actionId, resourceId, parentId) values(116, 'product/attribute:create', '新增产品字段', 4, 83, 20);
insert into permission(id, name, description, actionId, resourceId, parentId) values(117, 'product/attribute:delete', '删除产品字段', 6, 83, 20);
insert into permission(id, name, description, actionId, resourceId, parentId) values(118, 'parent/invitation:update', '修改受邀家长状态', 5, 38, 6);
insert into permission(id, name, description, actionId, resourceId, parentId) values(119, 'teacher/application:list', '查看教师列表', 2, 87, 3);
insert into permission(id, name, description, actionId, resourceId, parentId) values(120, 'teacher/application:read', '查看教师详情', 3, 87, 3);
insert into permission(id, name, description, actionId, resourceId, parentId) values(121, 'teacher/platformIntroduction:update', '修改平台介绍', 5, 33, 3);
insert into permission(id, name, description, actionId, resourceId, parentId) values(122, 'teacher/platformInfo:update', '修改平台信息', 5, 34, 3);
insert into permission(id, name, description, actionId, resourceId, parentId) values(123, 'teacher/score:list', '查看教师测试历史成绩列表', 2, 32, 3);
insert into permission(id, name, description, actionId, resourceId, parentId) values(124, 'parent/application:list', '查看家长列表', 2, 89, 8);
insert into permission(id, name, description, actionId, resourceId, parentId) values(125, 'parent/application:read', '查看家长详情', 3, 89, 8);
insert into permission(id, name, description, actionId, resourceId, parentId) values(126, 'parent/student:list', '查看家长子女列表', 2, 42, 8);
insert into permission(id, name, description, actionId, resourceId, parentId) values(127, 'courseHourRecord:list', '查看课时记录', 2, 6, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(128, 'courseHourRecord:create', '增加/赠送课时', 4, 6, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(129, 'lesson:list', '查看课表记录', 2, 8, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(130, 'lesson:create', '增加排课', 4, 8, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(131, 'lesson:update', '修改排课', 5, 8, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(132, 'course:list', '查看课程列表', 2, 4, 11);
insert into permission(id, name, description, actionId, resourceId, parentId) values(133, 'menu/systemManage/department:access', '部门', 1, 85, 15);
insert into permission(id, name, description, actionId, resourceId, parentId) values(134, 'department:list', '查看部门列表', 2, 83, 133);
insert into permission(id, name, description, actionId, resourceId, parentId) values(135, 'department:create', '添加部门', 4, 83, 133);
insert into permission(id, name, description, actionId, resourceId, parentId) values(136, 'department:update', '修改部门', 5, 83, 133);
insert into permission(id, name, description, actionId, resourceId, parentId) values(137, 'department:delete', '删除部门', 6, 83, 133);


UPDATE permission SET TYPE = 'menu' WHERE id <= 30;
UPDATE permission SET TYPE = 'menu' WHERE id = 133;

INSERT INTO role_permission(roleId, permissionId) VALUES(100, 31);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 32);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 33);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 34);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 35);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 36);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 37);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 38);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 39);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 40);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 41);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 42);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 43);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 44);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 45);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 46);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 47);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 48);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 49);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 50);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 51);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 52);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 53);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 54);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 55);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 56);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 57);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 58);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 59);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 60);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 61);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 62);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 63);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 64);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 65);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 66);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 67);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 68);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 69);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 70);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 71);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 72);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 73);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 74);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 75);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 76);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 77);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 78);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 79);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 80);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 81);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 82);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 83);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 84);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 85);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 86);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 87);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 88);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 89);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 90);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 91);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 92);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 93);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 94);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 95);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 96);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 97);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 98);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 99);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 100);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 101);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 102);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 103);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 104);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 105);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 106);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 107);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 108);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 109);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 110);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 111);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 112);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 113);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 114);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 115);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 116);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 117);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 118);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 119);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 120);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 121);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 122);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 123);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 124);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 125);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 126);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 127);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 128);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 129);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 130);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 131);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 132);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 133);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 134);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 135);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 136);
INSERT INTO role_permission(roleId, permissionId) VALUES(100, 137);