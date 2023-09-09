///test22
package code;

import code.devicemanage.BlockAwakeThread;
import code.gui.FileGUI;
import code.gui.OSGUI;
import code.barecomputer.Block;
import code.barecomputer.CPU;
import code.barecomputer.Clock;
import code.barecomputer.Disk;
import code.barecomputer.Language;
import code.barecomputer.Memory;
import code.barecomputer.RandomGenerator;
import code.devicemanage.BlockWorkThread;
import code.gui.DeviceGUI;
import code.gui.DiskGUI;
import code.gui.FirstGUI;
import code.gui.JobGUI;
import code.gui.MemoryGUI;
import code.gui.ProcessGUI;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import code.memorymanage.MMU;

public class Computer {
    public static String DiskStr = "disk";
    public static String Cylinder = "cylinder_";
    public static String Track = "track_";
    public static String Sector = "sector_";
    public static int JCB_NUM = 0;
    public static int FINISH_NUM = 0;
    public static CPU cpu = new CPU();
    public static Memory memory = new Memory();
    public static Disk disk = new Disk();
    public static Clock clock = new Clock();
    public static MMU mmu = new MMU();
    public static RandomGenerator randomGenerator = new RandomGenerator();
    public static Language language = new Language();
    public static BlockWorkThread blockWorkThread = new BlockWorkThread();
    public static BlockAwakeThread blockAwakeThread = new BlockAwakeThread();
    public static FirstGUI firstGUI = new FirstGUI();
    public static OSGUI mainGUI = new OSGUI();
    public static MemoryGUI memoryGUI = new MemoryGUI();
    public static DiskGUI diskGUI = new DiskGUI();
    public static DeviceGUI deviceGUI = new DeviceGUI();
    public static JobGUI jobGUI = new JobGUI();
    public static ProcessGUI processGUI = new ProcessGUI();
    public static FileGUI fileGUI = new FileGUI();
    public static String openInfo = "";
    public static String RunInfo;
    public static String DeviceInfo;
    public static String Root;
    public static String outPath;
    public static File f;
    public static FileOutputStream fileOutputStream;
    public static PrintStream printStream;

    public Computer() {
    }

    public static void Initlize() throws IOException {
        firstGUI.setPriority(9);
        clock.setPriority(8);
        disk.doLoad();
        memory.Initialize(disk);
        Root = System.getProperty("user.dir");
        outPath = Root + File.separator + "output" + File.separator + "ProcessResults.txt";
        File f = new File(outPath);
        f.createNewFile();
        fileOutputStream = new FileOutputStream(f);
        printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);
        RunInfo = "";
        DeviceInfo = "";
    }

    public static void CreateInitFiles() {
        String Root = System.getProperty("user.dir");
        System.out.println("Root: " + Root);
        String diskPath = Root + File.separator + DiskStr;
        File diskFile = new File(diskPath);
        if (!diskFile.exists()) {
            diskFile.mkdir();
            System.out.println(diskPath + "创建成功");
        }

        for(int i = 0; i < 10; ++i) {
            String cylinderPath = diskPath + File.separator + Cylinder + i;
            File cylinderFile = new File(cylinderPath);
            if (!cylinderFile.exists()) {
                cylinderFile.mkdir();
                System.out.println(cylinderPath + "创建成功");
            }

            for(int j = 0; j < 32; ++j) {
                String trackPath = cylinderPath + File.separator + Track + String.format("%2d", j);
                File trackFile = new File(trackPath);
                if (!trackFile.exists()) {
                    trackFile.mkdir();
                    System.out.println(trackPath + "创建成功");
                }

                for(int k = 0; k < 64; ++k) {
                    String sectorPath = trackPath + File.separator + Sector + String.format("%2d", k) + ".txt";
                    File sectorFile = new File(sectorPath);
                    if (!sectorFile.isFile()) {
                        try {
                            sectorFile.createNewFile();
                            System.out.println(sectorPath + "创建成功");
                        } catch (Exception var13) {
                        }
                    }
                }
            }
        }

    }

    public static void ClearDisk() {
        String Root = System.getProperty("user.dir");
        System.out.println("Root: " + Root);
        String diskPath = Root + File.separator + DiskStr;
        File diskFile = new File(diskPath);
        if (!diskFile.exists()) {
            diskFile.mkdir();
            System.out.println(diskPath + "创建成功");
        }

        for(int i = 0; i < 10; ++i) {
            String cylinderPath = diskPath + File.separator + Cylinder + i;
            File cylinderFile = new File(cylinderPath);
            if (!cylinderFile.exists()) {
                cylinderFile.mkdir();
                System.out.println(cylinderPath + "创建成功");
            }

            for(int j = 0; j < 32; ++j) {
                String trackPath = cylinderPath + File.separator + Track + String.format("%2d", j);
                File trackFile = new File(trackPath);
                if (!trackFile.exists()) {
                    trackFile.mkdir();
                    System.out.println(trackPath + "创建成功");
                }

                for(int k = 0; k < 64; ++k) {
                    String sectorPath = trackPath + File.separator + Sector + String.format("%2d", k) + ".txt";
                    File sectorFile = new File(sectorPath);
                    if (sectorFile.isFile()) {
                        try {
                            FileWriter fileWriterA = new FileWriter(sectorFile);
                            fileWriterA.write("");
                            fileWriterA.close();
                            FileWriter fileWriterB = new FileWriter(sectorFile, true);

                            for(int r = 0; r < 256; ++r) {
                                fileWriterB.write("0000\n");
                            }

                            fileWriterB.close();
                        } catch (Exception var15) {
                        }
                    }
                }
            }

            System.out.println(i + 1 + "/10");
        }

    }

    public static void ClearFree() {
        String Root = System.getProperty("user.dir");
        System.out.println("Root: " + Root);
        String diskPath = Root + File.separator + DiskStr;
        System.out.println("为格式化后的磁盘实现成组链接表...");
        int groupNum = 0;

        for(int i = 1; i < 10; ++i) {
            String cylinderPath = diskPath + File.separator + Cylinder + i;

            for(int j = 0; j < 32; ++j) {
                if ((i != 0 || j != 0) && (i != 0 || j != 1)) {
                    if (i == 9 && j == 31) {
                        break;
                    }

                    try {
                        String trackPath = cylinderPath + File.separator + Track + String.format("%2d", j);
                        String sectorPath = trackPath + File.separator + Sector + String.format("%2d", 63) + ".txt";
                        System.out.println(sectorPath);
                        File file = new File(sectorPath);
                        FileWriter fWriterA = new FileWriter(file);
                        fWriterA.write("");
                        fWriterA.flush();
                        fWriterA.close();
                        FileWriter fWriterB = new FileWriter(file, true);
                        int sectorIndex = 63;

                        for(int index = 0; index < 256; ++index) {
                            if (index < 191) {
                                fWriterB.write("0000\n");
                            } else if (index == 191) {
                                if (i == 9 && j == 30) {
                                    fWriterB.write(Block.INT_TO_HEX(63) + "\n");
                                } else {
                                    fWriterB.write(Block.INT_TO_HEX(64) + "\n");
                                }
                            } else {
                                int blockNumber = Disk.BlockTransform_Location_To_Index(i, j, sectorIndex) + 64;
                                fWriterB.write(Block.INT_TO_HEX(blockNumber) + "\n");
                                --sectorIndex;
                            }
                        }

                        fWriterB.flush();
                        fWriterB.close();
                        ++groupNum;
                        if (groupNum % 32 == 0) {
                            System.out.println("已创建" + groupNum + "组");
                        }
                    } catch (Exception var14) {
                        var14.printStackTrace();
                    }
                }
            }
        }

        System.out.println("共创建" + groupNum + "组");
    }

    public static void ClearSuperBlock() {
        Block block = new Block(false, 1);
        block.setDataAtIndex(0, Block.INT_TO_HEX(36));
        block.setDataAtIndex(1, "FFFF");
        block.setDataAtIndex(2, "0000");
        block.setDataAtIndex(3, "0000");
        block.setDataAtIndex(4, "0FFF");
        block.setDataAtIndex(5, "0010");
        block.setDataAtIndex(6, "0000");
        block.setDataAtIndex(7, "0EC0");
        block.setDataAtIndex(8, "0000");

        int i;
        for(i = 9; i < 118; ++i) {
            block.setDataAtIndex(i, "0000");
        }

        block.setDataAtIndex(191, Block.INT_TO_HEX(60));

        for(i = 0; i < 60; ++i) {
            block.setDataAtIndex(192 + i, Block.INT_TO_HEX(2111 - i));
        }

        block.setDataAtIndex(252, "0000");
        block.setDataAtIndex(253, "0000");
        block.setDataAtIndex(254, "0000");
        block.setDataAtIndex(255, "0000");
        block.showBlock();
        block.writeBack();
        System.out.println("原厂超级块写回本地TXT中");
    }

    public static void ClearGuideBlock(Disk disk) {
        Block block = new Block(false, 0);
        block.setDataAtIndex(0, "0003");
        block.setDataAtIndex(1, "000A");
        block.setDataAtIndex(2, "3C28");
        block.setDataAtIndex(3, "321E");
        block.setDataAtIndex(4, "1314");
        block.setDataAtIndex(5, "0001");
        block.setDataAtIndex(6, "5210");
        block.setDataAtIndex(7, "221C");
        block.setDataAtIndex(8, "008C");
        block.setDataAtIndex(9, "0010");
        block.setDataAtIndex(10, "0009");
        block.setDataAtIndex(11, "000F");
        block.setDataAtIndex(12, "3C28");
        block.setDataAtIndex(13, "321E");
        block.setDataAtIndex(14, "1314");
        block.setDataAtIndex(15, "0002");
        block.setDataAtIndex(16, "5210");
        block.setDataAtIndex(17, "221C");
        block.setDataAtIndex(18, "008D");
        block.setDataAtIndex(19, "0003");
        block.setDataAtIndex(20, "0004");
        block.setDataAtIndex(21, "0022");
        block.setDataAtIndex(22, "3C28");
        block.setDataAtIndex(23, "321E");
        block.setDataAtIndex(24, "1314");
        block.setDataAtIndex(25, "0003");
        block.setDataAtIndex(26, "5210");
        block.setDataAtIndex(27, "221C");
        block.setDataAtIndex(28, "008E");
        block.setDataAtIndex(29, "0015");
        block.setDataAtIndex(30, "0007");
        block.showBlock();
        block.writeBack();
        System.out.println("作业引导块写回本地TXT中");
    }

    public static void CreateFirstInode() {
        Block block = new Block(false, 2);
        block.setDataAtIndex(0, "0000");
        block.setDataAtIndex(1, "0001");
        block.setDataAtIndex(2, "0001");
        block.setDataAtIndex(3, "0001");
        block.setDataAtIndex(4, "0001");
        block.setDataAtIndex(5, "07E4");
        block.setDataAtIndex(6, "000C");
        block.setDataAtIndex(7, "000E");
        block.setDataAtIndex(8, "0006");
        block.setDataAtIndex(9, "001E");
        block.setDataAtIndex(10, "07E5");
        block.setDataAtIndex(11, "0002");
        block.setDataAtIndex(12, "000E");
        block.setDataAtIndex(13, "0013");
        block.setDataAtIndex(14, "0014");
        block.setDataAtIndex(15, "0000");
        block.setDataAtIndex(16, "0000");
        block.setDataAtIndex(17, "0001");
        block.setDataAtIndex(18, "0804");

        int i;
        for(i = 19; i < 28; ++i) {
            block.setDataAtIndex(i, "0000");
        }

        block.setDataAtIndex(28, "7071");
        block.setDataAtIndex(29, "7273");
        block.setDataAtIndex(30, "7989");
        block.setDataAtIndex(31, "7A84");

        for(i = 32; i < 37; ++i) {
            block.setDataAtIndex(i, "0001");
        }

        block.setDataAtIndex(37, "07E4");
        block.setDataAtIndex(38, "000C");
        block.setDataAtIndex(39, "0019");
        block.setDataAtIndex(40, "0008");
        block.setDataAtIndex(41, "000A");
        block.setDataAtIndex(42, "07E5");
        block.setDataAtIndex(43, "0002");
        block.setDataAtIndex(44, "0011");
        block.setDataAtIndex(45, "0015");
        block.setDataAtIndex(46, "0020");
        block.setDataAtIndex(47, "0000");
        block.setDataAtIndex(48, "0000");
        block.setDataAtIndex(49, "0001");
        block.setDataAtIndex(50, "0805");

        for(i = 51; i < 60; ++i) {
            block.setDataAtIndex(i, "0000");
        }

        block.setDataAtIndex(60, "7071");
        block.setDataAtIndex(61, "7273");
        block.setDataAtIndex(62, "797A");
        block.setDataAtIndex(63, "7B7C");
        block.setDataAtIndex(64, "0002");
        block.setDataAtIndex(65, "0001");
        block.setDataAtIndex(66, "0001");
        block.setDataAtIndex(67, "0001");
        block.setDataAtIndex(68, "0001");
        block.setDataAtIndex(69, "07E5");
        block.setDataAtIndex(70, "0001");
        block.setDataAtIndex(71, "0001");
        block.setDataAtIndex(72, "000C");
        block.setDataAtIndex(73, "002B");
        block.setDataAtIndex(74, "07E5");
        block.setDataAtIndex(75, "0003");
        block.setDataAtIndex(76, "0001");
        block.setDataAtIndex(77, "0011");
        block.setDataAtIndex(78, "0026");
        block.setDataAtIndex(79, "0000");
        block.setDataAtIndex(80, "0000");
        block.setDataAtIndex(81, "0001");
        block.setDataAtIndex(82, "0806");
        block.setDataAtIndex(83, "0000");

        for(i = 84; i < 92; ++i) {
            block.setDataAtIndex(i, "0000");
        }

        block.setDataAtIndex(92, "8F90");
        block.setDataAtIndex(93, "7A86");
        block.setDataAtIndex(94, "8788");
        block.setDataAtIndex(95, "898A");
        block.setDataAtIndex(96, "0003");
        block.setDataAtIndex(97, "0002");
        block.setDataAtIndex(98, "0001");
        block.setDataAtIndex(99, "0001");
        block.setDataAtIndex(100, "0001");
        block.setDataAtIndex(101, "07E5");
        block.setDataAtIndex(102, "0002");
        block.setDataAtIndex(103, "0011");
        block.setDataAtIndex(104, "0008");
        block.setDataAtIndex(105, "001E");
        block.setDataAtIndex(106, "07E5");
        block.setDataAtIndex(107, "0003");
        block.setDataAtIndex(108, "0007");
        block.setDataAtIndex(109, "0016");
        block.setDataAtIndex(110, "002F");
        block.setDataAtIndex(111, "0000");
        block.setDataAtIndex(112, "0000");
        block.setDataAtIndex(113, "0005");
        block.setDataAtIndex(114, "0807");
        block.setDataAtIndex(115, "0808");
        block.setDataAtIndex(116, "0809");
        block.setDataAtIndex(117, "080A");
        block.setDataAtIndex(118, "080B");

        for(i = 119; i < 124; ++i) {
            block.setDataAtIndex(i, "0000");
        }

        block.setDataAtIndex(124, "0109");
        block.setDataAtIndex(125, "0201");
        block.setDataAtIndex(126, "0801");
        block.setDataAtIndex(127, "0001");
        String[] dev_0 = new String[]{"0004", "0003", "0001", "0001", "0001", "07E5", "0003", "0002", "000E", "001E", "07E5", "0003", "001D", "000A", "000B", "0000", "0000", "0001", "080C", "3314", "2518", "1214", "6B00"};

        for(i = 0; i < 19; ++i) {
            block.setDataAtIndex(128 + i, dev_0[i]);
        }

        i = 19;

        for(i = 0; i < 23; ++i) {
            block.setDataAtIndex(156 + i, dev_0[i]);
            ++i;
        }

        String[] dev_1 = new String[]{"0005", "0003", "0001", "0001", "0001", "07E5", "0003", "0002", "000E", "001E", "07E5", "0003", "001D", "000A", "000B", "0000", "0000", "0001", "080D", "3314", "2518", "1214", "6B01"};

        for(i = 0; i < 19; ++i) {
            block.setDataAtIndex(160 + i, dev_1[i]);
        }

        i = 19;

        for(i = 0; i < 23; ++i) {
            block.setDataAtIndex(188 + i, dev_1[i]);
            ++i;
        }

        String[] dev_2 = new String[]{"0006", "0003", "0001", "0001", "0001", "07E5", "0003", "0002", "000E", "001E", "07E5", "0003", "001D", "000A", "000B", "0000", "0000", "0001", "080E", "3314", "2518", "1214", "6B02"};

        for(i = 0; i < 19; ++i) {
            block.setDataAtIndex(192 + i, dev_2[i]);
        }

        i = 19;

        for(i = 0; i < 23; ++i) {
            block.setDataAtIndex(220 + i, dev_2[i]);
            ++i;
        }

        String[] dev_3 = new String[]{"0007", "0003", "0001", "0001", "0001", "07E5", "0003", "0002", "000E", "001E", "07E5", "0003", "001D", "000A", "000B", "0000", "0000", "0001", "080F", "3314", "2518", "1214", "6B03"};

        for(i = 0; i < 19; ++i) {
            block.setDataAtIndex(224 + i, dev_3[i]);
        }

        i = 19;

        for(int j = 0; i < 23; ++j) {
            block.setDataAtIndex(252 + j, dev_3[i]);
            ++i;
        }

        block.showBlock();
        block.writeBack();
        Block block_3 = new Block(false, 3);
        String[] dev_4 = new String[]{"0008", "0003", "0001", "0001", "0001", "07E5", "0003", "0002", "000E", "001E", "07E5", "0003", "001D", "000A", "000B", "0000", "0000", "0001", "0810", "3314", "2518", "1214", "6B04"};

        for(i = 0; i < 19; ++i) {
            block_3.setDataAtIndex(0 + i, dev_4[i]);
        }

        i = 19;

        for(i = 0; i < 23; ++i) {
            block_3.setDataAtIndex(28 + i, dev_4[i]);
            ++i;
        }

        String[] dev_5 = new String[]{"0009", "0003", "0001", "0001", "0001", "07E5", "0003", "0002", "000E", "001E", "07E5", "0003", "001D", "000A", "000B", "0000", "0000", "0001", "0811", "3314", "2518", "1214", "6B05"};

        for(i = 0; i < 19; ++i) {
            block_3.setDataAtIndex(32 + i, dev_5[i]);
        }

        i = 19;

        for(i = 0; i < 23; ++i) {
            block_3.setDataAtIndex(60 + i, dev_5[i]);
            ++i;
        }

        String[] inode0 = new String[]{"000A", "0001", "0001", "0001", "0001", "07E5", "0003", "0002", "000E", "001E", "07E5", "0003", "001D", "000A", "000B", "0000", "0000", "0001", "0812", "7071", "7273", "8B8C", "8D8E"};

        for(i = 0; i < 19; ++i) {
            block_3.setDataAtIndex(64 + i, inode0[i]);
        }

        i = 19;

        for(i = 0; i < 23; ++i) {
            block_3.setDataAtIndex(92 + i, inode0[i]);
            ++i;
        }

        String[] txt0 = new String[]{"000B", "0002", "0001", "0001", "0001", "07E5", "0003", "0002", "000E", "001E", "07E5", "0003", "001D", "000A", "000B", "0000", "0000", "0001", "0813", "3E42", "2314", "2223", "6B00"};

        for(i = 0; i < 19; ++i) {
            block_3.setDataAtIndex(96 + i, txt0[i]);
        }

        i = 19;

        for(i = 0; i < 23; ++i) {
            block_3.setDataAtIndex(124 + i, txt0[i]);
            ++i;
        }

        String[] txt1 = new String[]{"000C", "0002", "0001", "0001", "0001", "07E5", "0003", "0002", "000E", "001E", "07E5", "0003", "001D", "000A", "000B", "0000", "0000", "0001", "0814", "3E42", "2314", "2223", "6B01"};

        for(i = 0; i < 19; ++i) {
            block_3.setDataAtIndex(128 + i, txt1[i]);
        }

        i = 19;

        for(i = 0; i < 23; ++i) {
            block_3.setDataAtIndex(156 + i, txt1[i]);
            ++i;
        }

        block_3.showBlock();
        block_3.writeBack();
        System.out.println("磁盘中写入Inode成功");
        Block sblock = new Block(false, 1);
        sblock.setDataAtIndex(0, Block.INT_TO_HEX(36));
        sblock.setDataAtIndex(1, "FFFF");
        sblock.setDataAtIndex(2, "0000");
        sblock.setDataAtIndex(3, "0000");
        sblock.setDataAtIndex(4, "0FFF");
        sblock.setDataAtIndex(5, "0010");
        sblock.setDataAtIndex(6, "0000");
        sblock.setDataAtIndex(7, "03A3");
        sblock.setDataAtIndex(8, "FFF8");

        for(i = 9; i < 118; ++i) {
            sblock.setDataAtIndex(i, "0000");
        }

        sblock.setDataAtIndex(191, Block.INT_TO_HEX(43));

        for(i = 0; i < 43; ++i) {
            sblock.setDataAtIndex(192 + i, Block.INT_TO_HEX(2111 - i));
        }

        sblock.showBlock();
        sblock.writeBack();
        System.out.println("超级块中相应内容修改成功");
        Block block1 = new Block(false, 2052);
        block1.setDataAtIndex(0, "0003");
        block1.setDataAtIndex(1, "0001");
        block1.setDataAtIndex(2, "0002");
        block1.setDataAtIndex(3, "000A");
        block1.writeBack();
        Block block2 = new Block(false, 2053);
        block2.setDataAtIndex(0, "0001");
        block2.setDataAtIndex(1, "0003");
        block2.writeBack();
        Block block3 = new Block(false, 2054);
        block3.setDataAtIndex(0, "0006");
        block3.setDataAtIndex(1, "0004");
        block3.setDataAtIndex(2, "0005");
        block3.setDataAtIndex(3, "0006");
        block3.setDataAtIndex(4, "0007");
        block3.setDataAtIndex(5, "0008");
        block3.setDataAtIndex(6, "0009");
        block3.writeBack();
        Block block4 = new Block(false, 2066);
        block4.setDataAtIndex(0, "0002");
        block4.setDataAtIndex(1, "000B");
        block4.setDataAtIndex(2, "000C");
        block4.writeBack();
        System.out.println("目录及文件详细内容创建成功");
    }

    public void NULL() {
    }

    public static void main(String[] args) throws IOException {
        String Root = System.getProperty("user.dir");//获取当前工作目录路径
        String outPath = Root + File.separator + "output" + File.separator + "ProcessResults.txt";
        File f = new File(outPath);
        f.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);
        Computer computer = new Computer();
        computer.NULL();
        firstGUI.start();
        mainGUI.start();
        Initlize();
        firstGUI.yes.setEnabled(true);
        memoryGUI.start();
        diskGUI.start();
        deviceGUI.start();
        jobGUI.start();
        processGUI.start();
        fileGUI.start();
        clock.start();
        cpu.start();
        blockWorkThread.start();
        blockAwakeThread.start();
    }
}
