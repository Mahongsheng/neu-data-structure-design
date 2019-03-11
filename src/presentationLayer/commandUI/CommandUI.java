package presentationLayer.commandUI;

import businessLayer.admin.AdminOpr;
import businessLayer.graph.Graph;
import businessLayer.graph.Notice;
import businessLayer.parking.ParkingLog;
import businessLayer.parking.ParkingLot;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 这是一个命令行界面
 * @author 软英1702 马洪升 20175188
 */
public class CommandUI {
    static Graph graph = new Graph();
    static ParkingLot parkingLot = new ParkingLot();
    //主菜单
    public static void menu() {
        AdminOpr adminOpr = new AdminOpr();
        boolean flag = true;
        StringBuilder sb = new StringBuilder();
        sb.append("=================================\n");
        sb.append("|                               |\n");
        sb.append("|      欢迎使用景区信息管理系统     |\n");
        sb.append("|        ***请选择菜单***         |\n");
        sb.append("|                               |\n");
        sb.append("=================================\n");
        sb.append("1.管理员登录\n");
        sb.append("2.输出景区景点分布图\n");
        sb.append("3.景点查找\n");
        sb.append("4.热门景点排序\n");
        sb.append("5.输出导游线路图\n");
        sb.append("6.查询景点最短路径\n");
        sb.append("7.查询通知通告\n");
        sb.append("0.退出系统\n");
        while (flag) {
            System.out.print(sb.toString());
            Scanner scanner = new Scanner(System.in);
            int operation = scanner.nextInt();
            switch (operation) {
                case 1:
                    while (true) {
                        System.out.println("请输入用户名： ");
                        String name = scanner.next();
                        System.out.println("请输入密码： ");
                        String pwd = scanner.next();
                        if (adminOpr.login(name, pwd)) {
                            adminUI();
                            break;
                        } else {
                            System.out.println("您输入的用户名或密码错误，请重新输入");
                        }
                    }
                    break;
                case 2:
                    graph.ALGraph.outputGraph();
                    graph.ALGraph.outputMatrix();
                    break;
                case 3:
                    while (true) {
                        System.out.println("请输入关键字： ");
                        String name = scanner.next();
                        if (graph.findScenic(name)) {
                            System.out.println("是否继续查找(Y/N)？");
                            String choose = scanner.next();
                            if (choose.equals("N")) {
                                break;
                            }
                        }
                    }
                    break;
                case 4:
                    while (true) {
                        System.out.println("1.按照热度排序");
                        System.out.println("2.按照岔路数排序");
                        int choose = scanner.nextInt();
                        if (choose == 1) {
                            graph.sortByPop();
                            System.out.println("是否继续查找(Y/N)？");
                            String choose1 = scanner.next();
                            if (choose1.equals("N")) {
                                break;
                            }
                        } else if (choose == 2) {
                            graph.sortByWay();
                            System.out.println("是否继续查找(Y/N)？");
                            String choose1 = scanner.next();
                            if (choose1.equals("N")) {
                                break;
                            }
                        } else {
                            System.out.println("无效操作！");
                        }
                    }
                    break;
                case 5:
                    try {
                        System.out.println("请输入景点： ");
                        String one = scanner.next();
                        System.out.println("请输入景点： ");
                        String other = scanner.next();
                        for (String s : graph.findGuideLoop(one,other)){
                            System.out.println(s + " ->");
                        }
                        System.out.println();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    while (true) {
                        System.out.println("请输入起始点： ");
                        String one = scanner.next();
                        System.out.println("请输入终点： ");
                        String other = scanner.next();
                        graph.findShotestPath(one, other);
//                        graph.findSho();
                        System.out.println("是否继续查找(Y/N)？");
                        String choose = scanner.next();
                        if (choose.equals("Y")) {

                        } else if (choose.equals("N")) {
                            break;
                        } else {
                            System.out.println("无效操作！");
                        }
                    }

                    break;
                case 7:
                    for (Notice notice  : graph.notices){
                        System.out.println(notice.getTime() + " " + notice.getContent()); ;
                    }
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    System.out.println("无效的操作！");
                    break;
            }
        }
    }
    //管理员菜单
    public static void adminUI() {
        boolean flag = true;
        StringBuilder sb = new StringBuilder();
        sb.append("=================================\n");
        sb.append("|                               |\n");
        sb.append("|           管理员系统            |\n");
        sb.append("|                               |\n");
        sb.append("=================================\n");
        sb.append("1.景点更新\n");
        sb.append("2.景点删除\n");
        sb.append("3.道路更新\n");
        sb.append("4.道路删除\n");
        sb.append("5.发布通知通告\n");
        sb.append("6.停车场管理\n");
        sb.append("0.退出登录\n");
        while (flag) {
            System.out.print(sb.toString());
            Scanner sc = new Scanner(System.in);
            int opr = sc.nextInt();
            sc.nextLine();
            switch (opr) {
                case 1:
                    System.out.println("请输入景点名称： ");
                    String name = sc.next();
                    System.out.println("请输入景点简介： ");
                    String introduction = sc.next();
                    int popularity = 0;
                    boolean hasRestAreas = true;
                    boolean hasToilets = true;
                    int x;
                    int y;
                    while (true) {
                        try {
                            System.out.println("请输入景点热度（整数）： ");
                            popularity = sc.nextInt();
                            System.out.println("景区是否有休息区（是或否）？");
                            String hasRestArea = sc.next();
                            System.out.println("景区是否有卫生间（是或否）？");
                            String hasToilet = sc.next();
                            if (hasRestArea.equals("是")) {
                                hasRestAreas = true;
                            } else if (hasRestArea.equals("否")) {
                                hasRestAreas = true;
                            }
                            if (hasToilet.equals("是")) {
                                hasToilets = true;
                            } else if (hasToilet.equals("否")) {
                                hasToilets = true;

                            }
                            System.out.println("景区x坐标： ");
                            x = sc.nextInt();
                            System.out.println("景区y坐标： ");
                            y = sc.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("您输入的不是整数，请重新输入");
                        }
                    }
                    if (graph.addScenicVertex(name, introduction, popularity, hasRestAreas, hasToilets, x, y)) {
                        System.out.println("添加成功！");
                        graph.initializeALGraph();

                    } else {
                        System.out.println("添加失败！");
                    }
                    adminUI();
                    break;
                case 2:
                    System.out.println("请输入景点名称： ");
                    String deleteName = sc.next();
                    if (graph.deleteScenicVertex(deleteName)) {
                        System.out.println("删除成功！");
                        graph.initializeALGraph();
                    } else {
                        System.out.println("删除失败！");
                    }
                    adminUI();
                    break;
                case 3:
                    System.out.println("请输入路径起点名称： ");
                    String pathName1 = sc.next();
                    System.out.println("请输入路径终点名称： ");
                    String pathName2 = sc.next();
                    int weight = 0;
                    float timeCost = 0;
                    while (true) {
                        try {
                            System.out.println("请输入距离（整数）： ");
                            weight = sc.nextInt();
                            System.out.println("请输入时间花费（小数）？");
                            timeCost = sc.nextFloat();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("您输入的不是数字，请重新输入");
                        }
                    }
                    if (graph.addScenicPath(pathName1, pathName2, weight, timeCost)) {
                        System.out.println("添加成功！");
                        graph.initializeALGraph();

                    } else {
                        System.out.println("添加失败！");
                    }
                    adminUI();
                    break;
                case 4:
                    System.out.println("请输入路径起点名称： ");
                    String pathName3 = sc.next();
                    System.out.println("请输入路径终点名称： ");
                    String pathName4 = sc.next();

                    if (graph.deleteScenicPath(pathName3, pathName4)) {
                        System.out.println("删除成功！");
                        graph.initializeALGraph();

                    } else {
                        System.out.println("删除失败！");
                    }
                    adminUI();
                    break;
                case 5:
                    System.out.println("请输入公告内容： ");
                    String content = sc.next();
                    graph.addNotice(content);
                    adminUI();
                    break;
                case 6:
                    parkingMenu();
                    break;
                case 0:
                    flag = false;
                    menu();
                    break;
                default:
                    System.out.println("无效的操作！");
                    break;
            }
        }
    }
    //停车场菜单
    public static void parkingMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("1.车辆停放登记\n");
        sb.append("2.车辆离开登记\n");
        sb.append("3.检查所有停放车辆\n");
        sb.append("4.查询历史记录\n");
        sb.append("5.退出\n");
        Scanner sc = new Scanner(System.in);
        System.out.print(sb.toString());
        String in = sc.nextLine();
        switch (in) {
            case "1":
                System.out.println("输入停放车辆的车牌");
                String plateNumber = sc.nextLine();
                parkingLot.parkCar(plateNumber);
                parkingMenu();
                break;
            case "2":
                System.out.println("输入离开车辆的车牌");
                plateNumber = sc.nextLine();
                parkingLot.leaverCar(plateNumber);
                parkingMenu();
                break;
            case "3":
                parkingLot.printAllParkingCars();
                parkingMenu();
                break;
            case "4":
                ParkingLog parkingLog = new ParkingLog();
                parkingLog.findAllLog();
                parkingMenu();
                break;
            case "5":
                return;
            default:
                System.out.println("请重新输入！");
                parkingMenu();
                break;
        }
    }
}
