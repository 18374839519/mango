package com.louis.mango.admin.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.louis.mango.admin.model.user.SysUser;
import com.louis.mango.admin.service.user.impl.SysUserServiceImpl;
import com.louis.mango.common.utils.datas.GlobalContents;
import com.louis.mango.common.utils.export.CreateExcelFileUtils;
import com.louis.mango.common.utils.export.FileUtils;
import com.louis.mango.common.utils.http.HttpResult;
import com.louis.mango.common.utils.http.HttpResultUtils;
import com.louis.mango.common.utils.http.HttpStatus;
import com.louis.mango.common.utils.time.TimeUtils;
import com.louis.mango.core.exception.BaseException;
import com.louis.mango.core.page.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class SysUserController {

    private Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserServiceImpl sysUserServiceImpl;

    @SuppressWarnings("unchecked")
    @GetMapping("/selectAllUserByPage")
    public HttpResult selectAll(PageRequest pageRequest, SysUser sysUser) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<SysUser> userList = sysUserServiceImpl.selectAllByPage(sysUser);
        PageInfo<SysUser> pageInfo = new PageInfo(userList);
        return HttpResultUtils.success(pageInfo);
    }

    /**
     * 单sheet页数据导出
     * @param response
     * @param sysUser
     */
    @GetMapping("/exportUser")
    public void exportUser(HttpServletResponse response, SysUser sysUser) {
        List<SysUser> listUser = sysUserServiceImpl.selectAll(sysUser);

        logger.info("excel导出开始...");
        String[] titleStr = GlobalContents.userTitleStr; // 表头
        String fileName = "用户表";

        List<List<String>> listData = new ArrayList<>();
        for (int i=0; i<listUser.size(); i++) {
            List<String> listStr = new ArrayList<>();
            listStr.add(listUser.get(i).getId().toString());
            listStr.add(listUser.get(i).getName());
            listStr.add(listUser.get(i).getNickName());
            listStr.add(listUser.get(i).getEmail());
            listStr.add(listUser.get(i).getMobile());
            listStr.add(listUser.get(i).getStatus() == 0 ? "禁用":"正常");
            listStr.add(String.valueOf(listUser.get(i).getDeptId()));
            listStr.add(listUser.get(i).getCreateBy());
            listStr.add(TimeUtils.formatDate(listUser.get(i).getCreateTime()));
            listStr.add(listUser.get(i).getLastUpdateBy());
            listStr.add(listUser.get(i).getLastUpdateTime() == null ? null:TimeUtils.formatDate(listUser.get(i).getLastUpdateTime()));
            listStr.add(listUser.get(i).getDelFlag() == -1 ? "已删除":"正常");
            listData.add(listStr);
        }

        File file = CreateExcelFileUtils.createExcelFile(titleStr, listData, fileName);
        logger.info("excel导出完成...");
        logger.info("excel表格下载...");
        FileUtils.downloadFile(response, file, file.getName());
    }

    /**
     * 多sheet页数据导出
     * @param response
     * @param sysUser
     */
    @GetMapping("/exportUserMoreSheet")
    public void exportUserMoreSheet(HttpServletResponse response, SysUser sysUser) {
        List<SysUser> listUser = sysUserServiceImpl.selectAll(sysUser);

        logger.info("excel导出开始...");
        String[] titleStr = GlobalContents.userTitleStr; // 表头
        String fileName = "用户表";

        List<List<List<String>>> dataList = new ArrayList<>();
        List<List<String>> listData = new ArrayList<>();
        List<String> listStr = new ArrayList<>();

        for (int i=0; i<listUser.size(); i++) {
            listStr.add(listUser.get(i).getId().toString());
            listStr.add(listUser.get(i).getName());
            listStr.add(listUser.get(i).getNickName());
            listStr.add(listUser.get(i).getEmail());
            listStr.add(listUser.get(i).getMobile());
            listStr.add(listUser.get(i).getStatus() == 0 ? "禁用":"正常");
            listStr.add(String.valueOf(listUser.get(i).getDeptId()));
            listStr.add(listUser.get(i).getCreateBy());
            listStr.add(TimeUtils.formatDate(listUser.get(i).getCreateTime()));
            listStr.add(listUser.get(i).getLastUpdateBy());
            listStr.add(listUser.get(i).getLastUpdateTime() == null ? null:TimeUtils.formatDate(listUser.get(i).getLastUpdateTime()));
            listStr.add(listUser.get(i).getDelFlag() == -1 ? "已删除":"正常");
            listData.add(listStr);
        }

        dataList.add(listData);
        dataList.add(listData);
        dataList.add(listData);

        String[] sheetName = {"test1", "test2", "test3"};

        File file = CreateExcelFileUtils.createExcelFileMoreSheet(titleStr, sheetName, dataList, fileName);
        logger.info("excel导出完成...");
        logger.info("excel表格下载...");
        FileUtils.downloadFile(response, file, file.getName());
    }

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    @PostMapping("/addUser")
    public HttpResult addUser(SysUser sysUser) {
        // 检查用户名是否存在
        int checkUserName = sysUserServiceImpl.checkUserName(sysUser.getName());
        if (checkUserName > 0) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "用户名已存在");
        }
        // 检查昵称是否存在
        int checkNickName = sysUserServiceImpl.checkNickName(sysUser.getNickName());
        if (checkNickName > 0) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "昵称已存在");
        }
        // 添加用户
        int result = sysUserServiceImpl.addUser(sysUser);
        if (result < 1) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "用户添加失败");
        }
        return HttpResultUtils.success();
    }
}
