package com.louis.mango.admin.controller.menu;

import com.louis.mango.admin.model.menu.SysMenu;
import com.louis.mango.admin.security.utils.JwtTokenUtils;
import com.louis.mango.admin.security.utils.SecurityUtils;
import com.louis.mango.admin.service.menu.impl.SysMenuServiceImpl;
import com.louis.mango.common.utils.datas.TreeData;
import com.louis.mango.common.utils.datas.TreeDataUtils;
import com.louis.mango.common.utils.http.HttpResult;
import com.louis.mango.common.utils.http.HttpResultUtils;
import com.louis.mango.common.utils.http.HttpStatus;
import com.louis.mango.core.exception.BaseException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @GetMapping("/getAllMenu")
    public HttpResult getAllMenu() {
        List<SysMenu> list = sysMenuService.selectAll();
        List<TreeData> listMenu = new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            TreeData treeData = new TreeData();
            treeData.setId(list.get(i).getId().toString());
            treeData.setUpId(list.get(i).getParentId().toString());
            treeData.setLabel(list.get(i).getName());
            treeData.setData(list);
            listMenu.add(treeData);
        }
        TreeDataUtils treeDataUtils = new TreeDataUtils(listMenu);
        List<TreeData> tree = treeDataUtils.builTree();
        return HttpResultUtils.success(tree);
    }

    @PostMapping("/insertMenu")
    public HttpResult insertMenu(HttpServletRequest request, SysMenu sysMenu) {
        sysMenu.setCreateBy(JwtTokenUtils.getUsernameFromToken(JwtTokenUtils.getToken(request)));
        sysMenu.setCreateTime(new Date());
        sysMenu.setDelFlag(0);
        boolean result = sysMenuService.insert(sysMenu);
        if (!result) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "添加失败");
        }
        return HttpResultUtils.success();
    }

    @GetMapping("/checkMenuName")
    public HttpResult checkMenuName(String name, int parentId) {
        int result = sysMenuService.checkMenuName(name, parentId);
        if (result > 0) {
            return HttpResultUtils.success(false);
        }
        return HttpResultUtils.success(true);
    }
}
