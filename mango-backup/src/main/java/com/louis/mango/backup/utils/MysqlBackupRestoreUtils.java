package com.louis.mango.backup.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class MysqlBackupRestoreUtils {

    private static Logger logger = LoggerFactory.getLogger(MysqlBackupRestoreUtils.class);

    /**
     * 备份数据库
     * @param host host地址（本机或远程）
     * @param userName 数据库用户名
     * @param password 数据库密码
     * @param backupFolderPath 备份的路径
     * @param fileName 备份的文件名
     * @param databaseName 需要备份的数据库名称
     * @return
     */
    public static boolean backup(String host, String userName, String password, String backupFolderPath,
                                 String fileName, String databaseName) throws Exception {
        File backupFolderFile = new File(backupFolderPath);
        if (!backupFolderFile.exists()) {
            // 如果目录不存在则创建
            backupFolderFile.mkdirs();
        }
        if (!backupFolderPath.endsWith(File.separator) && !backupFolderPath.endsWith("/")) {
            backupFolderPath = backupFolderPath + File.separator;
        }

        // 拼接命令行的命令
        String backupFilePath = backupFolderPath + fileName;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysqldump --opt ").append(" --add-drop-database ").append(" --add-drop-table ")
                .append(" -h").append(host).append(" -u").append(userName).append(" -p").append(password)
                .append(" --result-file=").append(backupFilePath).append(" --default-character-set=utf8 ").append(databaseName);

        // 外部调用执行 exe 文件的 java api
        Process process = Runtime.getRuntime().exec(getCommand(stringBuilder.toString()));
        if (process.waitFor() == 0) {
            // 0 表示线程正常终止
            logger.info("数据已备份到 " + backupFilePath + " 文件中");
            return true;
        }
        return false;
    }

    /**
     * 还原数据库
     * @param restoreFilePath 数据库备份的脚本路径
     * @param host IP地址
     * @param databaseName 数据库名称
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    public static boolean restore(String restoreFilePath, String host, String userName, String password, String databaseName)
            throws Exception {
        File restoreFile = new File(restoreFilePath);
        if (restoreFile.isDirectory()) {
            for (File file : restoreFile.listFiles()) {
                if (file.exists() && file.getPath().endsWith(".sql")) {
                    restoreFilePath = file.getAbsolutePath();
                    break;
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysql -h").append(host).append(" -u").append(userName).append(" -p").append(password);
        stringBuilder.append(" ").append(databaseName).append(" < ").append(restoreFilePath);
        try {
            Process process = Runtime.getRuntime().exec(getCommand(stringBuilder.toString()));
            if (process.waitFor() == 0) {
                logger.info("数据已从 " + restoreFilePath + " 导入到数据库中");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String[] getCommand(String command) {
        String os = System.getProperty("os.name");
        String shell = "/bin/bash";
        String c = "-c";
        if(os.toLowerCase().startsWith("win")){
            shell = "cmd";
            c = "/c";
        }
        String[] cmd = { shell, c, command };
        return cmd;
    }
}
