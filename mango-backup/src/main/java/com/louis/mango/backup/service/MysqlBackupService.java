package com.louis.mango.backup.service;

/**
 * Mysql命令行备份恢复服务
 */
public interface MysqlBackupService {

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
    boolean backup(String host, String userName, String password, String backupFolderPath, String fileName, String databaseName) throws Exception;

    /**
     *
     * @param restoreFilePath 备份的路径
     * @param host host地址（本机或远程）
     * @param userName 数据库用户名
     * @param password 数据库密码
     * @param databaseName 需要备份的数据库名称
     * @return
     */
    boolean restore(String restoreFilePath, String host, String userName, String password, String databaseName) throws Exception;
}
