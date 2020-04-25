package com.louis.mango.admin.controller.menu;

import com.louis.mango.admin.model.menu.SysMenu;
import com.louis.mango.admin.security.utils.JwtTokenUtils;
import com.louis.mango.admin.service.menu.impl.SysMenuServiceImpl;
import com.louis.mango.common.utils.datas.MenuData;
import com.louis.mango.common.utils.datas.MenuDataUtils;
import com.louis.mango.common.utils.http.HttpResult;
import com.louis.mango.common.utils.http.HttpResultUtils;
import com.louis.mango.common.utils.http.HttpStatus;
import com.louis.mango.core.exception.BaseException;
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
        List<MenuData> listMenu = new ArrayList<>();
        for (int i=0; i<list.size(); i++) {
            MenuData menuData = new MenuData();
            menuData.setId(String.valueOf(list.get(i).getId()));
            menuData.setUpId(String.valueOf(list.get(i).getParentId()));
            menuData.setType(list.get(i).getIcon());
            if (list.get(i).getUrl() != null && !"".equals(list.get(i).getUrl())) {
                String[] names = list.get(i).getUrl().split("/");
                menuData.setName(names[names.length-1].split("\\.")[0]); // 路由名称，取vue文件名称
            }
            menuData.setText(list.get(i).getName());
            menuData.setData(list.get(i));
            listMenu.add(menuData);
        }
        MenuDataUtils menuDataUtils = new MenuDataUtils(listMenu);
        List<MenuData> tree = menuDataUtils.builTree();
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

    @PostMapping("/deleteMenu")
    public HttpResult deleteMenu(Long id) {
        List<Integer> list = new ArrayList<>();
        list.add(id.intValue());
        List<Integer> parentIdList = selectByParentId(list);
        parentIdList.addAll(list);
        boolean result = sysMenuService.deleteByPrimaryKey(parentIdList);
        if (!result) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "删除失败");
        }
        return HttpResultUtils.success();
    }

    /**
     * 递归获取所有id
     * @param parentIdList
     * @return
     */
    private List<Integer> selectByParentId(List<Integer> parentIdList) {
        List<Integer> returnList = new ArrayList<>();
        List<Integer> list = sysMenuService.selectByParentId(parentIdList);
        if (list.size() > 0) {
            returnList.addAll(list);
            selectByParentId(list);
        }
        return returnList;
    }

    @PostMapping("/updateById")
    public HttpResult updateById(HttpServletRequest request, SysMenu sysMenu) {
        sysMenu.setLastUpdateBy(JwtTokenUtils.getUsernameFromToken(JwtTokenUtils.getToken(request)));
        sysMenu.setLastUpdateTime(new Date());
        boolean result = sysMenuService.updateById(sysMenu);
        if (!result) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "更新失败");
        }
        return HttpResultUtils.success();
    }

    @GetMapping("/selectById")
    public HttpResult selectById(int id) {
        SysMenu sysMenu = sysMenuService.selectByPrimaryKey(id);
        return HttpResultUtils.success(sysMenu);
    }

}
