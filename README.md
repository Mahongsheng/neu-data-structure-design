# 软件名称：大型景区管理系统
东北大学数据库课程设计作业，使用JavaFX作为前端界面，有需要的学弟学妹可以直接看一看~
----------------------------------------------------------------------------------------------------------
## 一、运行环境：
安装了JRE1.7+环境的所有操作系统

## 二、使用说明：
点击Main类进入程序即可运行命令行界面或GUI界面
管理员用户名为：mhs
管理员密码为：123456

## 三、包含功能：
（1）输出景区分布图
系统应将景点和路径进行结合，生成邻接表以及邻接矩阵，并以较为直观的形式展现给游客以及管理员。
（2）景点搜索
系统应采取合理的方式进行景点的搜索，当游客输入的数据为空时应给予提示。当游客输入关键词时，系统应输出所有包含该关键词的景点。
（3）景点排序
系统应根据景点的热度以及景点的岔路数来进行快速地排序，并展示给游客。
（4）查询景点间最短路径
系统应根据游客输入的两个景点来查询最短路径，并将路径展示给游客。
（5）查询导游路线图
系统应根据游客输入的起始景点和终止景点来规划一条导游路线图，如果不存在应给予相应提示。
（6）景点、路径的增加与删除
系统应可以对景点和路径进行增加和删除操作并且将数据持久化。
（7）发布并展示公告
系统向游客展示公告，并且通过管理员来发布公告，公告数据应持久化。
（8）管理停车场：停车、出车
系统应通过管理员来进行停车场的停车和出车管理，当停车场满后应将车停入便道。停车场只有一个大门，且为一条狭长通道。
（9）管理员登录

## 四、API文档
AdjacencyList类：traverseENode 遍历边, addENode 加边, outputGraph 输出邻接表, outputMatrix 输出邻接矩阵,
returnGraph 返回邻接矩阵, addNewVNode 添加结点, deleteVNode 删除结点, addNewENode 添加新边,
deleteENode 删除边, findScenic 找景点, sortByPop 按照热度排序, sortByWay 按照岔路排序

Graph类：initializeALGraph 初始化图, addScenicVertex 加景点, deleteScenicVertex 删景点,
addScenicPath 加路, deleteScenicPath 删路, findScenic 找景点, sortByPop 按照热度排序,
sortByWay 按照岔路数排序, findShotestPath 找最短路径, findGuideLoop 找导游图, findSho 通过弗洛伊德输出可达矩阵,
addNotice 添加公告, updateNotice 更新公告

ParkingLot类：parkCar 停车， leaverCar 出车，isParkingAll 停车场是否满，isWaitEmpty便道是否为空，
ifCarIDExist 车牌号是否已存在，putIntoList 放入车牌号，chgPositionInWaiting 改变便道中车的位置，printAllParkingCars 输出所有车辆
